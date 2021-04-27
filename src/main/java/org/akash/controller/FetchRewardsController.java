package org.akash.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.akash.dto.PayerDTO;
import org.akash.dto.SpendPointsRequestDTO;
import org.akash.dto.TransactionRequestDTO;
import org.akash.service.FetchRewardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "Fetch-Rewards related end-points")
public class FetchRewardsController {
    @Autowired
    private FetchRewardsService fetchRewardsService;

    @GetMapping("/payer/balances")
    @ApiOperation(value = "Get points balance"
            , notes = "Returns pair of payer and it's current balance from the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
    })
    Map<String, Long> allPayerBalances() {
        return fetchRewardsService.getAllPayerBalances();
    }

    @PostMapping(path = "/payer/transaction", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "Add transactions"
            , notes = "Add transactions for a specific payer and date")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Transaction Added"),
            @ApiResponse(code = 400, message = "BAD_REQUEST")
    })
    void addTransaction(@RequestBody TransactionRequestDTO newTransaction) {
        fetchRewardsService.addTransaction(newTransaction);
    }

    @PostMapping(path = "/consumer/points", consumes = "application/json", produces = "application/json")
    @ApiOperation(value = "Spend points"
            , notes = "Spend points using the rules above and return a list of \u200B{ \"payer\": <string>, \"points\": <integer> }\u200B for each call.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "BAD_REQUEST")
    })
    List<PayerDTO> spendPoints(@RequestBody SpendPointsRequestDTO points) {
        return fetchRewardsService.spendPoints(points);
    }
}
