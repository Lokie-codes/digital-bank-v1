package com.digitalbank.accounts_service.repositories;

import com.digitalbank.accounts_service.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByAccountNumber(int accountNumber);
}
