package com.getontop.businesscase.adapter.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.getontop.businesscase.adapter.persistence.entity.AccountEntity;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
}
