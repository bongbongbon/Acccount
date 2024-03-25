package com.Account.Account.controller;

import com.Account.Account.domain.Account;
import com.Account.Account.dto.AccountDto;
import com.Account.Account.dto.AccountInfo;
import com.Account.Account.dto.CreateAccount;
import com.Account.Account.dto.DeleteAccount;
import com.Account.Account.service.AccountService;
import com.Account.Account.service.RedisTestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final RedisTestService redisTestService;

    @PostMapping("/account")
    public CreateAccount.Response createAccount(
            @RequestBody @Valid CreateAccount.Request request
            ) {

        return CreateAccount.Response.from(accountService.createAccount(
                request.getUserId(),
                request.getInitialBalance()
        ));
    }

    @DeleteMapping("/account")
    public DeleteAccount.Response deleteAccount(
            @RequestBody @Valid DeleteAccount.Request request
    ) {

        return DeleteAccount.Response.from(accountService.deleteAccount(
                request.getUserId(),
                request.getAccountNumber()
        ));
    }

    @GetMapping("/account")
    public List<AccountInfo> getAccountsByUserId(
            @RequestParam("user_id") Long userId
    ){
        return accountService.getAccountsByUserId(userId)
                .stream().map(accountDto -> AccountInfo.builder()
                        .accountNumber(accountDto.getAccountNumber())
                        .balance(accountDto.getBalance())
                        .build())
                .collect(Collectors.toList());

    }

    @GetMapping("/get-lock")
    public String getLock() {
       return redisTestService.getLock();
    }



    @GetMapping("/account/{id}")
    public Account getAccount(@PathVariable("id") Long id) {
        return accountService.getAccount(id);
    }


}
