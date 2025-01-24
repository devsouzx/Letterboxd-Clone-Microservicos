package com.devsouzx.accounts.database.repository;

import com.devsouzx.accounts.database.model.UsersFollows;
import com.devsouzx.accounts.database.model.UsersFollowsId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersFollowsRepository extends JpaRepository<UsersFollows, UsersFollowsId> {
}
