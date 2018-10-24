package com.gcit.lms.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gcit.lms.dao.LoanDAO;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Library;
import com.gcit.lms.entity.Loan;
import com.gcit.lms.repositories.BookRepository;
import com.gcit.lms.repositories.BorrowerRepository;
import com.gcit.lms.repositories.LibraryRepository;
import com.gcit.lms.util.ResultServe;

@Service
public class AdminLoanService {

	@Autowired
	LoanDAO ldao;

	@Autowired
	BookRepository bookRepo;

	@Autowired
	LibraryRepository libraryRepo;

	@Autowired
	BorrowerRepository borrowerRepo;

	// ****************************************************************************
	//
	public ResponseEntity<ResultServe<List<Loan>>> readLoans() {

		ResultServe<List<Loan>> result = new ResultServe<>();
		List<Loan> loans = new ArrayList<>();
		try {
			loans = ldao.readAllLoansUnreturned();
			result.setData(loans);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// ************************************************************************
	//
	public ResponseEntity<ResultServe<Loan>> editLoan(Integer cardNo, Integer branchId, Integer bookId, Date dueDate) {

		ResultServe<Loan> result = new ResultServe<>();
		Loan loan = new Loan();
		Borrower borrower = borrowerRepo.readBorrowerByNo(cardNo);
		Library library = libraryRepo.readLibraryById(branchId);
		Book book = bookRepo.readBookById(bookId);

		loan.setBorrower(borrower);
		loan.setBook(book);
		loan.setLibrary(library);
		Date newDueDate = addDays(dueDate, 7);

		try {
			ldao.editDueDate(loan, newDueDate);
			result.setMessage("Successfully editted.");
			result.setData(loan);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// ***************************************************************
	// Helper method to adding day to sql.Date objects
	public Date addDays(Date date, int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, days);
		return new Date(c.getTimeInMillis());
	}
}
