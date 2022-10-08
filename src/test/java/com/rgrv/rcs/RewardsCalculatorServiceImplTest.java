package com.rgrv.rcs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.rgrv.rcs.model.Customer;
import com.rgrv.rcs.model.Transaction;
import com.rgrv.rcs.repos.CustomerRepository;
import com.rgrv.rcs.repos.TransactionRepository;
import com.rgrv.rcs.service.impl.RewardsCalculatorServiceImpl;
import com.rgrv.rcs.vo.CalculatedRewardsResponse;

@ExtendWith(MockitoExtension.class)
class RewardsCalculatorServiceImplTest {

	@InjectMocks
	RewardsCalculatorServiceImpl rewardsCalculatorService;
	
	@Mock
	CustomerRepository customerRepository;
	@Mock
	TransactionRepository transactionRepository;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}
	
	@DisplayName("Get customer reward total with positive rewards points.")
	@Test
	void getCustomerWithPositiveRewardPointsTest() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
		Customer cust = new Customer();
		cust.setId(1);
		
		when(customerRepository.existsById(1)).thenReturn(true);
		
		List<Transaction> list = new ArrayList<Transaction>();
		Transaction tOne = new Transaction(cust, 1, sdf.parse("01/06/2022"), 55.25);
		Transaction tTwo = new Transaction(cust, 1, sdf.parse("01/05/2022"), 55.25);

		list.add(tOne);
		list.add(tTwo);

		when(transactionRepository.findAllByCustomerIdAndDateBetween(1, sdf.parse("01/03/2022"), sdf.parse("30/06/2022"))).thenReturn(list);

		CalculatedRewardsResponse response = rewardsCalculatorService.getLastThreeMonthsRewardsGiven(1);

		assertEquals(10, response.getRewardPoints());
	}
	
	@DisplayName("Get customer reward total with no transactions returns zero rewards points.")
	@Test
	void getCustomerWithNoTransactionsTest() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		when(customerRepository.existsById(4)).thenReturn(true);
		
		List<Transaction> list = new ArrayList<Transaction>();
		
		when(transactionRepository.findAllByCustomerIdAndDateBetween(4, sdf.parse("01/03/2022"), sdf.parse("30/06/2022"))).thenReturn(list);

		CalculatedRewardsResponse response = rewardsCalculatorService.getLastThreeMonthsRewardsGiven(4);

		assertEquals(0, response.getRewardPoints());
	}
	
	@DisplayName("Get customer reward total with all transactions above $101.")
	@Test
	void getCustomerWithAllTransactionAbove101Test() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
		Customer cust = new Customer();
		cust.setId(2);
		
		when(customerRepository.existsById(2)).thenReturn(true);
		
		List<Transaction> list = new ArrayList<Transaction>();
		Transaction tOne = new Transaction(cust, 2, sdf.parse("01/06/2022"), 101.25);
		Transaction tTwo = new Transaction(cust, 2, sdf.parse("01/05/2022"), 101.55);

		list.add(tOne);
		list.add(tTwo);
		
		when(transactionRepository.findAllByCustomerIdAndDateBetween(2, sdf.parse("01/03/2022"), sdf.parse("30/06/2022"))).thenReturn(list);

		CalculatedRewardsResponse response = rewardsCalculatorService.getLastThreeMonthsRewardsGiven(2);

		assertEquals(105, response.getRewardPoints());
	}
	
	@DisplayName("Get customer reward total with mixed transactions amounts.")
	@Test
	void getCustomerWithMixedTransactionAmountsTest() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); 
		Customer cust = new Customer();
		cust.setId(3);
		
		when(customerRepository.existsById(3)).thenReturn(true);
		
		List<Transaction> list = new ArrayList<Transaction>();
		Transaction tOne = new Transaction(cust, 3, sdf.parse("01/06/2022"), 51.25);
		Transaction tTwo = new Transaction(cust, 3, sdf.parse("01/05/2022"), 101.55);
		Transaction tThree = new Transaction(cust, 3, sdf.parse("01/05/2022"), 33.55);

		list.add(tOne);
		list.add(tTwo);
		list.add(tThree);
		
		when(transactionRepository.findAllByCustomerIdAndDateBetween(3, sdf.parse("01/03/2022"), sdf.parse("30/06/2022"))).thenReturn(list);

		CalculatedRewardsResponse response = rewardsCalculatorService.getLastThreeMonthsRewardsGiven(3);

		assertEquals(54, response.getRewardPoints());
	}

}
