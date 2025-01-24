package com.devsouzx.accounts.database.repository;

import com.devsouzx.accounts.database.model.BlockedUsers;
import com.devsouzx.accounts.database.model.BlockedUsersId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockedUsersRepository extends JpaRepository<BlockedUsers, BlockedUsersId> {
}
