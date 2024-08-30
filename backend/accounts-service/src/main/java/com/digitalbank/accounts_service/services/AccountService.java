package com.digitalbank.accounts_service.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.digitalbank.accounts_service.DTO.AccountDTO;
import com.digitalbank.accounts_service.DTO.UserDTO;
import com.digitalbank.accounts_service.models.Account;
import com.digitalbank.accounts_service.models.User;
import com.digitalbank.accounts_service.repositories.AccountRepository;
import com.digitalbank.accounts_service.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class AccountService {
    User user1 = new User(1L, "samsher007@google.com", "Sam", "Bahadur", "+91987654321", "samy69", "Sheila@143");
    User user2 = new User(2L, "lokeshs@yahoo.com", "Lokesh", "S", "+918884227157", "lokiee", "Lokesh@13579");
    User user3 = new User(3L, "yuva.shree@gmail.com", "Yuvashree", "Ramachandran", "+919988776655", "yuva", "Yuva@111");


    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
    }

    public ResponseEntity<?> createAccount(Account account) {
        if(account.getCustomerId() == null) {
            Map<String, String> errors = new HashMap<>();
            errors.put("customerId", "The customer id is mandatory");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(errors);
        } else if(!userRepository.existsById(account.getCustomerId())) {
            Map<String, String> errors = new HashMap<>();
            errors.put("customerId", "Customer with customer id "+ account.getCustomerId() + " does not exist");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(errors);
        }

        if(account.getAccountType() == null) {
            Map<String, String> errors = new HashMap<>();
            errors.put("accountType", "The account type is mandatory");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(errors);
        }
        if(account.getCurrency() == null) {
            account.setCurrency("INR");
        }
        if(account.getBalance() == null) {
            account.setBalance(BigDecimal.valueOf(0));
        }
        int accountNumber = generateUniqueAccountNumber();
        account.setAccountNumber(accountNumber);


        Account registeredAccount =  accountRepository.save(account);
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountId(registeredAccount.getId());
        accountDTO.setAccountType(account.getAccountType());
        accountDTO.setCurrency(account.getCurrency());
        accountDTO.setBalance(account.getBalance());
        accountDTO.setAccountNumber(account.getAccountNumber());
        UserDTO userDTO = new UserDTO();
        if(userRepository.existsById(registeredAccount.getCustomerId())) {
            User user = userRepository.getReferenceById(registeredAccount.getId());
            userDTO.setId(user.getId());
            userDTO.setEmail(user.getEmail());
            userDTO.setUsername(user.getUsername());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setPhone(user.getPhone());
        }
        accountDTO.setAccountHolder(userDTO);
        return ResponseEntity.ok(accountDTO);
    }

    private int generateUniqueAccountNumber() {
        Random random = new Random();
        int accountNumber;
        do{
            accountNumber = 10000000 + random.nextInt(10000000); // 8 digit account number
        } while (accountRepository.existsByAccountNumber(accountNumber));
        return accountNumber;
    }

    public ResponseEntity<?> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        if (accounts.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "No accounts found");
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(response);
        }
        List<AccountDTO> accountDTOs = new ArrayList<>();
        for (Account account : accounts) {
            AccountDTO accountDTO = new AccountDTO();
            accountDTO.setAccountId(account.getId());
            accountDTO.setBalance(account.getBalance());
            accountDTO.setAccountType(account.getAccountType());
            accountDTO.setAccountNumber(account.getAccountNumber());
            accountDTO.setCurrency(account.getCurrency());

            User user = userRepository.getReferenceById(account.getCustomerId());
            UserDTO userDTO = new UserDTO();
            if(userRepository.existsById(user.getId())) {
                 userDTO.setFirstName(user.getFirstName());
                 userDTO.setLastName(user.getLastName());
                 userDTO.setEmail(user.getEmail());
                 userDTO.setId(user.getId());
                 userDTO.setPhone(user.getPhone());
                 userDTO.setUsername(user.getUsername());
            }
            accountDTO.setAccountHolder(userDTO);
            accountDTOs.add(accountDTO);
        }
        return ResponseEntity.ok(accountDTOs);
    }
}
