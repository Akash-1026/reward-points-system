package org.akash.controller;

import org.akash.dto.BalanceResponseDTO;
import org.akash.dto.SpendPointsRequestDTO;
import org.akash.dto.SpendPointsResponseDTO;
import org.akash.dto.TransactionRequestDTO;
import org.akash.entity.Payer;
import org.akash.entity.Transaction;
import org.akash.service.FetchRewardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FetchRewardsController {
    @Autowired
    private FetchRewardsService fetchRewardsService;

    @GetMapping("/payer/balances")
    BalanceResponseDTO allPayerBalances() {
        return fetchRewardsService.getAllPayerBalances();
    }

    @PostMapping(path = "/payer/transaction", consumes = "application/json", produces = "application/json")
    Transaction addTransaction(@RequestBody TransactionRequestDTO newTransaction) {
        return fetchRewardsService.addTransaction(newTransaction);
    }

    @PostMapping(path = "/consumer/points", consumes = "application/json", produces = "application/json")
    SpendPointsResponseDTO spendPoints(@RequestBody SpendPointsRequestDTO points) {
        return fetchRewardsService.spendPoints(points);
    }
}
