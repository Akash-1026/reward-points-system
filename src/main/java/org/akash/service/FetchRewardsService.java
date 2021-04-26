package org.akash.service;

import org.akash.dto.SpendPointsRequestDTO;
import org.akash.dto.SpendPointsResponseDTO;
import org.akash.dto.TransactionRequestDTO;
import org.akash.entity.Payer;
import org.akash.entity.Transaction;

import java.util.List;

public interface FetchRewardsService {
    SpendPointsResponseDTO spendPoints(SpendPointsRequestDTO points);

    void addTransaction(TransactionRequestDTO newTransaction);

    List<Transaction> getAllTransactions();

    List<Payer> getAllPayerBalances();
}
