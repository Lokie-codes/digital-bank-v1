package com.digitalbank.accounts_service.repositories;

import com.digitalbank.accounts_service.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
