/**
 * 
 */
package com.gcit.lms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

/**
 * @author moguzyildiz
 *
 */

@Entity
@Table(name = "tbl_borrower", catalog = "library")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "cardNo", scope = Borrower.class)
public class Borrower {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cardNo;

	@Column(name = "name")
	@NotEmpty
	@Length(max = 45)
	private String borrowerName;

	@NotEmpty
	@Length(max = 45)
	private String address;

	@NotEmpty
	@Length(max = 20)
	private String phone;

	//private List<Loan> loans;

	
//	public List<Loan> getLoans() {
//		return loans;
//	}
//
//	public void setLoans(List<Loan> loans) {
//		this.loans = loans;
//	}

	/**
	 * @return the cardNo
	 */
	public Integer getCardNo() {
		return cardNo;
	}

	/**
	 * @param cardNo
	 *            the cardNo to set
	 */
	public void setCardNo(Integer cardNo) {
		this.cardNo = cardNo;
	}

	/**
	 * @return the borrowerName
	 */
	public String getBorrowerName() {
		return borrowerName;
	}

	/**
	 * @param borrowerName
	 *            the borrowerName to set
	 */
	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone
	 *            the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
