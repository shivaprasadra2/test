package com.rgrv.rcs.model;

public class TransactionMetadata {

	private Integer transactionId;
	private Double countFrom50To100;
	private Double countGreaterThan100;
	private Integer totalCount;
	public Integer getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}
	public Double getCountFrom50To100() {
		return countFrom50To100;
	}
	public void setCountFrom50To100(Double countFrom50To100) {
		this.countFrom50To100 = countFrom50To100;
	}
	public Double getCountGreaterThan100() {
		return countGreaterThan100;
	}
	public void setCountGreaterThan100(Double countGreaterThan100) {
		this.countGreaterThan100 = countGreaterThan100;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	
	
}
