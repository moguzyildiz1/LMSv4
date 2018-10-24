/**
 * 
 */
package com.gcit.lms.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

/**
 * @author moguzyildiz
 */
@Entity
@Table(name="tbl_library_branch", catalog="library")
//@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="branchId", scope=Library.class)
public class Library {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer branchId;
	
	@NotEmpty
	@Length(max=45)
	private String branchName;
	
	@NotEmpty
	@Length(max=45)
	private String branchAddress;
	
//	private List<Loan> loans;
//	
//	private List<Inventory> inventories;
	
	public Library(){};
	
	public Library(Integer bId,String bName) {
		this.setBranchId(bId);
		this.setBranchName(bName);
	}
	
	public Library(Integer bId,String bName,String bAddress) {
		this.setBranchId(bId);
		this.setBranchName(bName);
		this.setBranchAddress(bAddress);
	}
	
	/**
	 * @return the branchId
	 */
	public Integer getBranchId() {
		return branchId;
	}
	/**
	 * @param branchId the branchId to set
	 */
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}
	/**
	 * @return the branchName
	 */
	public String getBranchName() {
		return branchName;
	}
	/**
	 * @param branchName the branchName to set
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	/**
	 * @return the branchAddress
	 */
	public String getBranchAddress() {
		return branchAddress;
	}
	/**
	 * @param branchAddress the branchAddress to set
	 */
	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}	
	
//	public List<Loan> getLoans() {
//		return loans;
//	}	
//	public void setLoans(List<Loan> loans) {
//		this.loans = loans;
//	}	
//	public List<Inventory> getInventories() {
//		return inventories;
//	}
//	public void setInventories(List<Inventory> inventories) {
//		this.inventories = inventories;
//	}
}
