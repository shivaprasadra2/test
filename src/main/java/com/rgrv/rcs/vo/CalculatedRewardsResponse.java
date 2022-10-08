package com.rgrv.rcs.vo;

import java.util.Date;

public class CalculatedRewardsResponse {

	private Integer customerId;
	private Date rewardsFrom;
	private Date rewardsTo;
	private Integer rewardPoints;
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Date getRewardsFrom() {
		return rewardsFrom;
	}
	public void setRewardsFrom(Date rewardsFrom) {
		this.rewardsFrom = rewardsFrom;
	}
	public Date getRewardsTo() {
		return rewardsTo;
	}
	public void setRewardsTo(Date rewardsTo) {
		this.rewardsTo = rewardsTo;
	}
	public Integer getRewardPoints() {
		return rewardPoints;
	}
	public void setRewardPoints(Integer rewardPoints) {
		this.rewardPoints = rewardPoints;
	}
	
	
}
