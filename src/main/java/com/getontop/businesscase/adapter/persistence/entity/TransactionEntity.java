package com.getontop.businesscase.adapter.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_seq")
	@SequenceGenerator(name = "transaction_seq", sequenceName = "transaction_seq", allocationSize = 1)
	private Long id;

	@Column
	private LocalDateTime timestamp;

	@Column
	private long ownerAccountId;

	@Column
	private long sourceAccountId;

	@Column
	private long targetAccountId;

	@Column
	private double amount;

	@Column
	private String status;

}
