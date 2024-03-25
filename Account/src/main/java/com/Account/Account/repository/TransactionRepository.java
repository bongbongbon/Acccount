package com.Account.Account.repository;

import com.Account.Account.domain.Account;
import com.Account.Account.domain.AccountUser;
import com.Account.Account.domain.Transaction;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository
        extends JpaRepository<Transaction, Long> { //첫번째 타입은 repostory가 활용할 entity 두번째 프라이머리키의 타입

}
