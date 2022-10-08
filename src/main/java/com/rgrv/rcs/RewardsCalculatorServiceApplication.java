package com.rgrv.rcs;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rgrv.rcs.model.Customer;
import com.rgrv.rcs.model.Transaction;
import com.rgrv.rcs.repos.CustomerRepository;

@SpringBootApplication
public class RewardsCalculatorServiceApplication implements CommandLineRunner {

	@Autowired
	private CustomerRepository customerRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(RewardsCalculatorServiceApplication.class, args);
	}

	private void initializeDatabaseContent() {
		Random r = new Random();
		int transactionId = 1;
		List<Customer> customers = new ArrayList<Customer>();
		for(int customerId = 1; customerId <= 10; customerId++) {
			Customer customer = null;
			if(customerId == 4) {
				customer = new Customer();
				customer.setId(customerId);
				List<Transaction> transactions = new ArrayList<Transaction>();
				customer.setTransactions(transactions);
			} else if (customerId == 6) {
				customer = new Customer();
				customer.setId(customerId);
				List<Transaction> transactions = new ArrayList<Transaction>();
				customer.setTransactions(transactions);
				int upperLimitTransactionId = transactionId + 2;
				for(int currentTransactionId = transactionId; currentTransactionId < upperLimitTransactionId; currentTransactionId++) {
					Transaction transaction = new Transaction();
					transaction.setAmount(BigDecimal.valueOf((35 + (250 - 35) * r.nextDouble())).setScale(2, RoundingMode.HALF_EVEN).doubleValue());
					transaction.setDate(java.sql.Date.valueOf(LocalDate.now().withDayOfMonth(r.nextInt(30 - 1) + 1).minusMonths(r.nextInt(8 - 4) + 4)));
					transaction.setCustomer(customer);
					transaction.setId(currentTransactionId);
					transactions.add(transaction);
					transactionId = currentTransactionId;
				}
				transactionId = transactionId + 1;
				customer.setTransactions(transactions);
			} else if (customerId == 8) {
				customer = new Customer();
				customer.setId(customerId);
				List<Transaction> transactions = new ArrayList<Transaction>();
				customer.setTransactions(transactions);
				int upperLimitTransactionId = transactionId + 10;
				for(int currentTransactionId = transactionId; currentTransactionId < upperLimitTransactionId; currentTransactionId++) {
					Transaction transaction = new Transaction();
					transaction.setAmount(BigDecimal.valueOf((35 + (50 - 35) * r.nextDouble())).setScale(2, RoundingMode.HALF_EVEN).doubleValue());
					transaction.setDate(java.sql.Date.valueOf(LocalDate.now().withDayOfMonth(r.nextInt(30 - 1) + 1).minusMonths(r.nextInt(4 - 1) + 1)));
					transaction.setCustomer(customer);
					transaction.setId(currentTransactionId);
					transactions.add(transaction);
					transactionId = currentTransactionId;
				}
				transactionId = transactionId + 1;
				customer.setTransactions(transactions);
			} else if (customerId == 10) {
				customer = new Customer();
				customer.setId(customerId);
				List<Transaction> transactions = new ArrayList<Transaction>();
				customer.setTransactions(transactions);
				int upperLimitTransactionId = transactionId + 2;
				boolean firstRun = true;
				for(int currentTransactionId = transactionId; currentTransactionId < upperLimitTransactionId; currentTransactionId++) {
					Transaction transaction = null;
					if(firstRun) {
						transaction = new Transaction();
						transaction.setAmount(BigDecimal.valueOf((100 + (250 - 100) * r.nextDouble())).setScale(2, RoundingMode.HALF_EVEN).doubleValue());
						transaction.setDate(java.sql.Date.valueOf(LocalDate.now().minusMonths(5).withDayOfMonth(r.nextInt(30 - 1) + 1)));
						transaction.setCustomer(customer);
						transaction.setId(currentTransactionId);						
						firstRun = false;
					}else {
						transaction = new Transaction();
						transaction.setAmount(BigDecimal.valueOf((100 + (250 - 100) * r.nextDouble())).setScale(2, RoundingMode.HALF_EVEN).doubleValue());
						transaction.setDate(java.sql.Date.valueOf(LocalDate.now().withDayOfMonth(r.nextInt(30 - 1) + 1).minusMonths(r.nextInt(4 - 1) + 1)));
						transaction.setCustomer(customer);
						transaction.setId(currentTransactionId);						
					}
					transactions.add(transaction);
					transactionId = currentTransactionId;
				}
				transactionId = transactionId + 1;
				customer.setTransactions(transactions);
			} else {
				customer = new Customer();
				customer.setId(customerId);
				int upperLimitTransactionId = transactionId + 10;
				List<Transaction> transactions = new ArrayList<Transaction>();
				for(int currentTransactionId = transactionId; currentTransactionId < upperLimitTransactionId; currentTransactionId++) {
					Transaction transaction = new Transaction();
					transaction.setAmount(BigDecimal.valueOf((35 + (250 - 35) * r.nextDouble())).setScale(2, RoundingMode.HALF_EVEN).doubleValue());
					transaction.setDate(java.sql.Date.valueOf(LocalDate.now().withDayOfMonth(r.nextInt(30 - 1) + 1).minusMonths(r.nextInt(4 - 1) + 1)));
					transaction.setCustomer(customer);
					transaction.setId(currentTransactionId);
					transactions.add(transaction);
					transactionId = currentTransactionId;
				}
				transactionId = transactionId + 1;
				customer.setTransactions(transactions);
			}
			customers.add(customer);
		}
		customerRepository.saveAll(customers);
	}

	@Override
	public void run(String... args) throws Exception {
		initializeDatabaseContent();
	}

}
