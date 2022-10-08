package com.rgrv.rcs.repos;

import java.util.Date;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rgrv.rcs.model.Transaction;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {

	public Iterable<Transaction> findAllByCustomerIdAndDateBetween(Integer customerId, Date startDate, Date endDate);
	
}
