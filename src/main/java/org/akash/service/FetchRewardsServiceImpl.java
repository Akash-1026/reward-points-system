package org.akash.service;

import net.bytebuddy.asm.Advice;
import org.akash.dto.BalanceResponseDTO;
import org.akash.dto.SpendPointsRequestDTO;
import org.akash.dto.SpendPointsResponseDTO;
import org.akash.dto.TransactionRequestDTO;
import org.akash.entity.Payer;
import org.akash.entity.Transaction;
import org.akash.exception.BadRequestException;
import org.akash.repository.PayerRepository;
import org.akash.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FetchRewardsServiceImpl implements FetchRewardsService{

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    PayerRepository payerRepository;

    @Override
    public SpendPointsResponseDTO spendPoints(SpendPointsRequestDTO points) {
        try {
            HashMap<String, Long> balanceMap = new HashMap<>();
            HashMap<String, Long> resultMap = new HashMap<>();


            // Get all transactions that are not processed
            Optional<List<Transaction>> optionalTransactions = transactionRepository.findAllNotProcessed();
            Iterable<Payer> iterablePayerBalance = payerRepository.findAll();
            Iterator<Payer> payerBalanceIterator = iterablePayerBalance.iterator();
            List<Transaction> transactions = new ArrayList<Transaction>();


            if (optionalTransactions.isPresent()) {
                transactions = optionalTransactions.get();
            } else {
                throw new BadRequestException("Not enough rewards point balance");

            }

            int spendingPoints = points.getPoints();
            long totalPoints = 0;

            while (payerBalanceIterator.hasNext()) {
                Payer payerBalance = payerBalanceIterator.next();
                balanceMap.put(payerBalance.getName(), payerBalance.getBalance());
                totalPoints += payerBalance.getBalance();
            }

            // Main points spending Logic
            if (totalPoints >= spendingPoints) {
//                long remainingPoints = spendingPoints;
                for (Transaction currTransaction : transactions) {
                    /**case consideration: After spending x points, if customer wants to spend y points with having previously added transactions.
                     * Once the transactions added if it's not processed fully, it should not be marked processed.
                     * Ex: Transaction  Payer   Points  Time
                     *          T1       abc     500    Ordered-accordingly
                     *         spend1            200        ||
                     *         spend2            200        ||
                     * here spend2 should consider the balance for the abc and not the transaction amount.
                     */

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

                // Save updated data from the hashmap back to database
                Iterator<Payer> payerIterator = iterablePayerBalance.iterator();
                while (payerIterator.hasNext()) {
                    Payer payerBalance = payerIterator.next();
                    payerBalance.setBalance(balanceMap.get(payerBalance.getName()));
                    payerRepository.save(payerBalance);
                }

                // Create success response
                SpendPointsResponseDTO responseMessage = new SpendPointsResponseDTO();
                responseMessage.setSuccess(true);
                responseMessage.setMessage("Rewards spent!");
                responseMessage.setError("");
                responseMessage.setStatus(HttpStatus.OK);
                responseMessage.setResponse(resultMap);
                return responseMessage;
            } else {
                // Create error response
                SpendPointsResponseDTO responseMessage = new SpendPointsResponseDTO();
                responseMessage.setSuccess(false);
                responseMessage.setMessage("Not enough balance");
                responseMessage.setError("Logic error with the rewards spent");
                responseMessage.setStatus(HttpStatus.BAD_REQUEST);
                responseMessage.setResponse(null);
                return responseMessage;
            }

        }catch(Exception e){
            // Create error response
//            log.error("Error Spending Points", e);
            SpendPointsResponseDTO responseMessage = new SpendPointsResponseDTO();
            responseMessage.setSuccess(false);
            responseMessage.setMessage("Error spending order");
            responseMessage.setError(e.getMessage());
            responseMessage.setStatus(HttpStatus.BAD_REQUEST);
            responseMessage.setResponse(null);
            return responseMessage;
        }
    }

    @Override
    public Transaction addTransaction(TransactionRequestDTO newTransaction) {
        Transaction transaction = new Transaction();
        transaction.setPayer(newTransaction.getPayer());
        transaction.setPoints(newTransaction.getPoints());
        transaction.setTimestamp(newTransaction.getDate());
        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return null;
    }

    @Override
    public BalanceResponseDTO getAllPayerBalances() {
        try {
            HashMap<String, Long> balanceMap = new HashMap<String, Long>();
            Iterator<Payer> payerBalanceIterator = payerRepository.findAll().iterator();
            List<Object> response = new ArrayList();


            while (payerBalanceIterator.hasNext()) {
                Payer payerBalance = payerBalanceIterator.next();
                balanceMap.put(payerBalance.getName(), payerBalance.getBalance());
            }

            // Create success response
            BalanceResponseDTO responseMessage = new BalanceResponseDTO();
            responseMessage.setSuccess(true);
            responseMessage.setMessage("Rewards spent!");
            responseMessage.setError("");
            responseMessage.setStatus(HttpStatus.OK);
            responseMessage.setResponse(balanceMap);
            return responseMessage;


        }catch(Exception e){
            // Create error response
//            log.error("Error getting balance",e);
            BalanceResponseDTO responseMessage = new BalanceResponseDTO();
            responseMessage.setSuccess(false);
            responseMessage.setMessage("Error getting balance!");
            responseMessage.setError(e.getMessage());
            responseMessage.setStatus(HttpStatus.BAD_REQUEST);
            responseMessage.setResponse(new HashMap());
            return responseMessage;
        }

    }
}
