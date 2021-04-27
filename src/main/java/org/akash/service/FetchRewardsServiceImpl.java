package org.akash.service;

import org.akash.dto.PayerDTO;
import org.akash.dto.SpendPointsRequestDTO;
import org.akash.dto.TransactionRequestDTO;
import org.akash.entity.Payer;
import org.akash.entity.Transaction;
import org.akash.exception.BadRequestException;
import org.akash.repository.PayerRepository;
import org.akash.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class FetchRewardsServiceImpl implements FetchRewardsService{

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    PayerRepository payerRepository;

    /**
     * step 1: get the records for transaction and payer balance
     * step 2: case consideration
     *          case 1: if total points are more than spending points
     *                      > some transaction cannot be fully processed as few points remain for transaction but spending points are over
     *                      Ex: Transaction  Payer   Points       Time
     *                              T1       abc     500    Ordered-accordingly
     *                             spend1            200        ||
     *                             spend2            200        ||
     *                      > here spend2 should consider the balance for the abc and not the transaction amount.
     *          case 2: if total points are less than spending points than throw Bad request error
     * step 3: update the records for transaction and payer balance
     */
    @Override
    public List<PayerDTO> spendPoints(SpendPointsRequestDTO points) {
        int spendingPoints = points.getPoints();
        long totalPoints = 0;
        HashMap<String, Long> balanceMap = new HashMap<>();
        HashMap<String, Long> resultMap = new HashMap<>();
        List<PayerDTO> resultList = new ArrayList<>();

        Optional<List<Transaction>> optionalTransactions = transactionRepository.findAllNotProcessed();
        Iterable<Payer> iterablePayerBalance = payerRepository.findAll();
        Iterator<Payer> payerIterator = iterablePayerBalance.iterator();
        List<Transaction> transactions;

        if (optionalTransactions.isPresent()) {
            transactions = optionalTransactions.get();
        } else {
            throw new BadRequestException("No transactions to process");
        }

        while (payerIterator.hasNext()) {
            Payer payer = payerIterator.next();
            balanceMap.put(payer.getPayer(), payer.getPoints());
            totalPoints += payer.getPoints();
        }
        if (totalPoints >= spendingPoints) {
            for (Transaction currTransaction : transactions) {
                long currPoints = Math.min(currTransaction.getPoints(), balanceMap.get(currTransaction.getPayer()));
                if (currPoints <= spendingPoints) {
                    spendingPoints -= currPoints;
                    balanceMap.put(currTransaction.getPayer(), balanceMap.get(currTransaction.getPayer()) - currPoints);
                    resultMap.put(currTransaction.getPayer(), resultMap.getOrDefault(currTransaction.getPayer(), 0l) - currPoints);
                    currTransaction.setProcessed(true);
                    transactionRepository.save(currTransaction);
                } else {
                    balanceMap.put(currTransaction.getPayer(), balanceMap.get(currTransaction.getPayer()) - spendingPoints);
                    resultMap.put(currTransaction.getPayer(), resultMap.getOrDefault(currTransaction.getPayer(), 0l) - spendingPoints);
                    break;
                }
            }

            Iterator<Payer> payerIteratorUpdate = iterablePayerBalance.iterator();
            while (payerIteratorUpdate.hasNext()) {
                Payer payerBalance = payerIteratorUpdate.next();
                payerBalance.setPoints(balanceMap.get(payerBalance.getPayer()));
                payerRepository.save(payerBalance);
            }
            for(Map.Entry<String,Long> payer : resultMap.entrySet()){
                resultList.add(new PayerDTO(payer.getKey(),payer.getValue()));
            }
            return resultList;
        } else {
            throw new BadRequestException("Not enough balance");
        }
    }

    @Override
    public void addTransaction(TransactionRequestDTO newTransaction) {
        Transaction transaction = new Transaction();
        transaction.setPayer(newTransaction.getPayer());
        transaction.setPoints(newTransaction.getPoints());
        transaction.setTimestamp(newTransaction.getTimestamp());
        Payer payer;
        long totalBalance;

        Optional<Payer> optionalPayer = payerRepository.findByName(transaction.getPayer());
        if(optionalPayer.isPresent()){
            payer = optionalPayer.get();
            totalBalance = payer.getPoints() + transaction.getPoints();
            if (totalBalance >= 0){
                payer.setPoints(totalBalance);
            }
            else{
                throw new BadRequestException("Insufficient points");
            }
        }else {
            payer = new Payer();
            payer.setPayer(transaction.getPayer());
            totalBalance = transaction.getPoints();
            if (totalBalance >= 0){
                payer.setPoints(totalBalance);
            }
            else{
                throw new BadRequestException("Insufficient points");
            }
        }
        payerRepository.save(payer);
        transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return (List<Transaction>) transactionRepository.findAll();
    }

    @Override
    public Map<String, Long> getAllPayerBalances() {
        List<Payer> payerList = (List<Payer>) payerRepository.findAll();
        Map<String, Long> resultMap = payerList.stream().collect(Collectors.toMap(Payer::getPayer, Payer::getPoints));
        return resultMap;
    }
}
