package com.rgrv.rcs.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rgrv.rcs.service.impl.RewardsCalculatorServiceImpl;
import com.rgrv.rcs.vo.CalculatedRewardsResponse;
import com.rgrv.rcs.vo.ResponseWrapper;

@RestController
@RequestMapping(path = "/rewardTotal")
public class RewardsCalculatorController {

	@Autowired
	private RewardsCalculatorServiceImpl rewardsCalculatorService;
	
	@GetMapping("/customer/{custId}/last3Months")
	public ResponseEntity<ResponseWrapper> getLastThreeMonthsRewardsGiven(@PathVariable Integer custId) {
		ResponseEntity<ResponseWrapper> re = null;
		ResponseWrapper response = new ResponseWrapper();
		CalculatedRewardsResponse crr = rewardsCalculatorService.getLastThreeMonthsRewardsGiven(custId);
		if(crr.getCustomerId()==null) {
			response.setError(true);
			response.setErrorMessage("Customer id provided is not found!");
			re = ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}else {
			response.setResult(crr);
			re = ResponseEntity.status(HttpStatus.OK).body(response);
		}
		return re;
	}
}
