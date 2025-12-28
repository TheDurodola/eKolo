package com.thedurodola.ekolo.data.repositories;

import com.thedurodola.ekolo.data.models.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccounts extends JpaRepository<UserAccount, String> {
}
