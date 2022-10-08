package com.rgrv.rcs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.rgrv.rcs.endpoints.RewardsCalculatorController;
import com.rgrv.rcs.model.Transaction;
import com.rgrv.rcs.repos.TransactionRepository;
import com.rgrv.rcs.vo.CalculatedRewardsResponse;

@SpringBootTest
class RewardsCalculatorServiceApplicationTests {

	@Autowired
	private RewardsCalculatorController rewardsCalculatorController;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@DisplayName("RewardsCalculatorController is loaded into context.")
	@Test
	void contextLoads() {
		assertThat(rewardsCalculatorController).isNotNull();
	}
	
	@DisplayName("when provided with non existing customer id, API must reply with error indicator as true message.")
	@Test
	void shouldReturnErrorAsThereAreOnly20CustomersLoaded() {
		assertEquals(rewardsCalculatorController.getLastThreeMonthsRewardsGiven(21).getBody().getError(), true);
	}
	
	@DisplayName("when provided with an existing customer id, API must reply with result.")
	@Test
	void shouldReturnResponseAsTheCustomerExists() {
		CalculatedRewardsResponse crr = (CalculatedRewardsResponse) rewardsCalculatorController.getLastThreeMonthsRewardsGiven(1).getBody().getResult();
		assertEquals(crr.getCustomerId(), 1);
	}
	
	@DisplayName("when provided with an customer id 4, API must reply with zero points result.")
	@Test
	void shouldReturnZeroResultResponseForTheCustomer4() {
		CalculatedRewardsResponse crr = (CalculatedRewardsResponse) rewardsCalculatorController.getLastThreeMonthsRewardsGiven(4).getBody().getResult();
		assertEquals(crr.getCustomerId(), 4);
		assertEquals(crr.getRewardPoints(), 0);
	}
	
	@DisplayName("when provided with an customer id 8, API must reply with zero points result as none of the transactions surpass the threshold.")
	@Test
	void shouldReturnZeroResultResponseForTheCustomer8() {
		CalculatedRewardsResponse crr = (CalculatedRewardsResponse) rewardsCalculatorController.getLastThreeMonthsRewardsGiven(8).getBody().getResult();
		assertEquals(crr.getCustomerId(), 8);
		assertEquals(crr.getRewardPoints(), 0);
	}
	
	@DisplayName("when provided with an customer id 10, API must reply with non-zero points result by only considering the last 3 months transactions.")
	@Test
	void shouldReturnResultResponseForTheCustomer10() {
		CalculatedRewardsResponse crr = (CalculatedRewardsResponse) rewardsCalculatorController.getLastThreeMonthsRewardsGiven(10).getBody().getResult();
		assertEquals(crr.getCustomerId(), 10);
		Iterable<Transaction> trans = transactionRepository.findAllByCustomerIdAndDateBetween(10, java.sql.Date.valueOf(LocalDate.now().withDayOfMonth(1).minusMonths(4).atStartOfDay().toLocalDate()), java.sql.Date.valueOf(LocalDate.now().withDayOfMonth(1).atStartOfDay().minusSeconds(1).toLocalDate()));
		int counter = 0;
		for (@SuppressWarnings("unused") Transaction i : trans) {
		    counter++;
		}
		assertEquals(counter, 1);
	}

}
