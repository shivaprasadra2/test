package com.rgrv.rcs.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rgrv.rcs.model.Transaction;
import com.rgrv.rcs.model.TransactionMetadata;
import com.rgrv.rcs.repos.CustomerRepository;
import com.rgrv.rcs.repos.TransactionRepository;
import com.rgrv.rcs.vo.CalculatedRewardsResponse;

@Service
public class RewardsCalculatorServiceImpl {
	
	Logger logger = LoggerFactory.getLogger(RewardsCalculatorServiceImpl.class);

	@Autowired
	TransactionRepository transactionRepository;
	
	@Autowired
	CustomerRepository customerRepository;
	
	public CalculatedRewardsResponse getLastThreeMonthsRewardsGiven(Integer custId) {
		CalculatedRewardsResponse crr = new CalculatedRewardsResponse();
		if(customerRepository.existsById(custId)) {
			logger.debug("++++++++++++++request processing begins here+++++++++++++++");
			logger.debug("Customer Id provided: "+custId);
			crr.setCustomerId(custId);
			Date startDate = getFirstDayDateAfterMinus4Months();
			crr.setRewardsFrom(startDate);
			Date endDate = getLastDayDateAfterMinus1Month();
			crr.setRewardsTo(endDate);
			Iterable<Transaction> transactions = transactionRepository.findAllByCustomerIdAndDateBetween(custId, startDate, endDate);
			List<TransactionMetadata> tmds = new ArrayList<TransactionMetadata>();
			transactions.forEach((transaction) -> {
				TransactionMetadata tmd = new TransactionMetadata();
				tmd.setTransactionId(transaction.getId());
				tmd.setCountFrom50To100(getCountInBetween50And100(transaction));
				tmd.setCountGreaterThan100(getCountGreaterThan100(transaction));
				tmd.setTotalCount(roundTheDouble(tmd.getCountFrom50To100()).intValue() + roundTheDouble(tmd.getCountGreaterThan100() * 2).intValue());
				logger.debug("Tx Id: "+tmd.getTransactionId() + "; Points for $ >50 and <=100: "+tmd.getCountFrom50To100()+"; Points for $ > 100: "+tmd.getCountGreaterThan100()+"; Total points for this TX: "+tmd.getTotalCount());
				tmds.add(tmd);
			});
			crr.setRewardPoints(calculateTotalPoints(tmds));
			logger.debug("Grand total of rewards points for customer id "+ custId + ": " + crr.getRewardPoints());
		}
		return crr;
	}
	
	private Integer roundTheDouble(Double toBeRounded) {
		return BigDecimal.valueOf(toBeRounded).setScale(0, RoundingMode.HALF_EVEN).intValue();
	}

	private Integer calculateTotalPoints(List<TransactionMetadata> tmds) {
		Integer grandTotal = 0;
		for(TransactionMetadata tmd : tmds) {
			grandTotal = grandTotal + tmd.getTotalCount();
		}
		return grandTotal;
	}

	private Double getCountGreaterThan100(Transaction transaction) {
		Double count = 0.00;
		if(transaction.getAmount()>100) {
			count = transaction.getAmount() - 100;
		}
		return count;
	}

	private Double getCountInBetween50And100(Transaction transaction) {
		Double count = 0.00;
		if(transaction.getAmount()>50) {
			if(transaction.getAmount()>=100) {
				count = 50.00;
			}else {
				count = transaction.getAmount() - 50;
			}
		}
		return count;
	}

	private Date getLastDayDateAfterMinus1Month() {
		return java.sql.Date.valueOf(LocalDate.now().withDayOfMonth(1).atStartOfDay().minusSeconds(1).toLocalDate());
	}

	private Date getFirstDayDateAfterMinus4Months() {
		return java.sql.Date.valueOf(LocalDate.now().withDayOfMonth(1).minusMonths(4).atStartOfDay().toLocalDate());
	}

}
