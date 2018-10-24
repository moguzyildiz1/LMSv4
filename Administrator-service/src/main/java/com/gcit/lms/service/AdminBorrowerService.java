package com.gcit.lms.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gcit.lms.entity.Borrower;
import com.gcit.lms.repositories.BookRepository;
import com.gcit.lms.repositories.BorrowerRepository;
import com.gcit.lms.repositories.LibraryRepository;
import com.gcit.lms.util.ResultServe;

@Service
public class AdminBorrowerService {

	@Autowired
	BorrowerRepository borrowerRepo;

	@Autowired
	BookRepository bookRepo;

	@Autowired
	LibraryRepository libraryRepo;

	// ***************************************************************
	// Borrower/s returns with HttpStatus Code OK
	//
	public ResponseEntity<ResultServe<List<Borrower>>> readBorrowers() {

		ResultServe<List<Borrower>> result = new ResultServe<>();
		List<Borrower> borrowers = new ArrayList<>();
		try {
			borrowers = (List<Borrower>) borrowerRepo.findAll();
			result.setData(borrowers);
			result.setMessage("All borrowers returned successfully.");
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// ***************************************************************
	// Borrower/s returns with HttpStatus Code OK
	//
	public ResponseEntity<ResultServe<Borrower>> readBorrower(String name) {

		ResultServe<Borrower> result = new ResultServe<>();
		Borrower borrower = new Borrower();
		try {
			if (!name.isEmpty()) {
				if(borrowerRepo.readBorrowers(name)!=null) {
					borrower =borrowerRepo.readBorrowers(name).get(0);
					result.setData(borrower);
					result.setMessage("Borrower: " + borrower.getBorrowerName() + " returned successfully.");
				}else {
					result.setMessage("No such a record with name :"+name);
					result.setMessage("Unvalid name entry.");
					return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
				}				
			} else {
				result.setData(borrower);
				result.setMessage("Unvalid name entry.");
				return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			result.setMessage("No such a record with name :"+name);
			return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// ************************************************************************
	// Borrower edited and updates borrower returns with HttpStatus code OK
	//
	public ResponseEntity<ResultServe<Borrower>> editBorrower(String bName, Integer bCardNo) {

		ResultServe<Borrower> result = new ResultServe<>();
		Borrower borrower = new Borrower();
		try {
			borrowerRepo.editBorrower(bName, bCardNo);
			borrower = borrowerRepo.readBorrowerByNo(bCardNo);
			result.setData(borrower);
			result.setMessage("Borrower editted successfully.");
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// ************************************************************************
	// Returns borrower by card no and returns with HttpStatus code OK
	//
	public ResponseEntity<ResultServe<Borrower>> readBorrowerByNo(Integer cardNo) {

		ResultServe<Borrower> result = new ResultServe<>();
		Borrower borrower = new Borrower();
		try {
			if (cardNo == null || cardNo == 0) {
				result.setMessage("Cannot found such a record with card no: " + cardNo);
				result.setData(borrower);
				return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
			} else {
				borrower = borrowerRepo.readBorrowerByNo(cardNo);
				result.setData(borrower);
			}
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// ************************************************************************
	// Saves borrower by given inputs and returns it with HttpStatus code OK
	//
	public ResponseEntity<ResultServe<Borrower>> saveBorrowerByParam(String bName, String bAddress, String bPhone) {

		ResultServe<Borrower> result = new ResultServe<>();
		Integer newCardNo;
		Borrower borrower = new Borrower();
		borrower.setBorrowerName(bName);
		borrower.setAddress(bAddress);
		borrower.setPhone(bPhone);
		try {
			newCardNo = (borrowerRepo.saveAndFlush(borrower)).getCardNo();
			borrower = borrowerRepo.readBorrowerByNo(newCardNo);
			result.setData(borrower);
			result.setMessage("Borrower succesfully saved.");
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// ************************************************************************
	// Deletes borrower by card no and returns deleted borrower with Http OK
	//
	public ResponseEntity<ResultServe<Borrower>> deleteBorrower(Integer bCardNo) {

		ResultServe<Borrower> result = new ResultServe<>();
		Borrower borrower = new Borrower();
		try {
			borrowerRepo.deleteById(bCardNo);
			result.setData(borrower);
			result.setMessage("Borrower succesfully deleted.");
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// ************************************************************************
	// Saves borrower entity and returns saved one with HttpStatus code OK
	//
	public ResponseEntity<ResultServe<Borrower>> saveBorrower(Borrower borrower) {

		ResultServe<Borrower> result = new ResultServe<>();
		Borrower returnB = new Borrower();
		try {
			returnB = borrowerRepo.saveAndFlush(borrower);
			result.setData(returnB);
			result.setMessage("Borrower succesfully saved.");
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// ************************************************************************
	// Helper method to adding day to sql.Date objects
	//
	public Date addDays(Date date, int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, days);
		return new Date(c.getTimeInMillis());
	}
}
