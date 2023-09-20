package com.getontop.businesscase.adapter.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.getontop.businesscase.adapter.persistence.entity.TransactionEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {

	@Query("""
			select a from TransactionEntity a
			where a.ownerAccountId = :ownerAccountId
			and a.timestamp >= :since
			""")
	List<TransactionEntity> findByOwnerSince(@Param("ownerAccountId") long ownerAccountId,
			@Param("since") LocalDateTime since);

	@Query("""
			select sum(a.amount) from TransactionEntity a
			where a.targetAccountId = :accountId
			and a.ownerAccountId = :accountId
			and a.timestamp < :until
			""")
	Optional<Double> getDepositBalanceUntil(@Param("accountId") long accountId, @Param("until") LocalDateTime until);

	@Query("""
			select sum(a.amount) from TransactionEntity a
			where a.sourceAccountId = :accountId
			and a.ownerAccountId = :accountId
			and a.timestamp < :until
			""")
	Optional<Double> getWithdrawalBalanceUntil(@Param("accountId") long accountId, @Param("until") LocalDateTime until);

}
