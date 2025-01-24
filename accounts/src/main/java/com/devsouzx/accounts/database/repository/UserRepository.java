package com.devsouzx.accounts.database.repository;

import com.devsouzx.accounts.database.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
