package org.akash.service;

import org.akash.dto.PayerDTO;
import org.akash.dto.SpendPointsRequestDTO;
import org.akash.dto.TransactionRequestDTO;
import org.akash.entity.Transaction;

import java.util.List;
import java.util.Map;

public interface FetchRewardsService {
    List<PayerDTO> spendPoints(SpendPointsRequestDTO points);

    void addTransaction(TransactionRequestDTO newTransaction);

    List<Transaction> getAllTransactions();

    Map<String, Long> getAllPayerBalances();
}
