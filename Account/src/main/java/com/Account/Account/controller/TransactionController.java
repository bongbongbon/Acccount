package com.Account.Account.controller;

import com.Account.Account.domain.Account;
import com.Account.Account.dto.TransactionDto;
import com.Account.Account.dto.UseBalance;
import com.Account.Account.exception.AccountException;
import com.Account.Account.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 잔액 관련 컨트롤러
 * 1. 잔액 사용
 * 2. 잔액 사용 취소
 * 3. 거래 확인
 **/
@Slf4j
@RestController
@RequiredArgsConstructor
public class TransactionController {
    private TransactionService transactionService;

    @PostMapping("/transaction/use")
    public UseBalance.Response useBalance(
            @Valid @RequestBody UseBalance.Request request
    ) {

        try {
             return UseBalance.Response.from(
                    transactionService.useBalance(request.getUserId(),
                            request.getAccountNumber(), request.getAmount())
            );
        }catch (AccountException e) {
            log.error("Failed to use balance. ");

            transactionService.saveFailedUseTransaction(
                    request.getAccountNumber(),
                    request.getAmount()
            );
            throw e;
        }
    }
}
