package com.gcit.lms.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.gcit.lms.dao.InventoryDAO;
import com.gcit.lms.dao.LoanDAO;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Inventory;
import com.gcit.lms.entity.Library;
import com.gcit.lms.entity.Loan;
import com.gcit.lms.repositories.BookRepository;
import com.gcit.lms.repositories.BorrowerRepository;
import com.gcit.lms.repositories.LibraryRepository;

@Service
public class BorrowerService {

	@Autowired
	LibraryRepository libraryRepo;

	@Autowired
	BookRepository bookRepo;

	@Autowired
	BorrowerRepository borrowerRepo;

	@Autowired
	LoanDAO ldao;

	@Autowired
	InventoryDAO idao;

	// ***************************************************************
	//
	public boolean checkById(Integer cardNo) {
		boolean result = false;
		try {
			if (borrowerRepo.readBorrowerByNo(cardNo) != null)
				result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// ***************************************************************
	//
	public ResponseEntity<String> checkOutBook(Integer cardNo, Integer branchId, Integer bookId) {

		ResponseEntity<String> responseEntity=new ResponseEntity<String>("Initial message",HttpStatus.OK);
		@SuppressWarnings("unused")
		String resultString = "";
		Loan loan = new Loan();
		try {
			if (borrowerRepo.readBorrowerByNo(cardNo) == null) {
				resultString = "Unauthorized access attempt";
			} else if (libraryRepo.readLibraryById(branchId) == null) {
				resultString = "Unvalid branch id choise.";
			} else if (bookRepo.readBookById(bookId) == null) {
				resultString = "Unvalid book id choise.";
			} else {
				loan.setBook(bookRepo.readBookById(bookId));
				loan.setLibrary(libraryRepo.readLibraryById(branchId));
				loan.setBorrower(borrowerRepo.readBorrowerByNo(cardNo));
				java.sql.Date dateOut = new java.sql.Date(Calendar.getInstance().getTime().getTime());
				java.sql.Date dueDate = addDays(dateOut, 7);
				loan.setDateOut(dateOut);
				loan.setDueDate(dueDate);
				loan.setDateIn(null);
				ldao.addLoan(loan);
				responseEntity = new ResponseEntity<>("You successfully check out book.",HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseEntity;
	}

	// ***************************************************************
	//
	public String checkInBook(Integer cardNo, Integer branchId, Integer bookId) {

		String resultString = "";
		Loan loan = new Loan();
//		String[] dates = dateOut.split("-");
//		Integer[] intDates = { Integer.getInteger(dates[0]), Integer.getInteger(dates[1]),
//				Integer.getInteger(dates[2]) };
		try {
			loan = ldao.readLoan(cardNo, branchId, bookId).get(0);
			if (borrowerRepo.readBorrowerByNo(cardNo) == null) {
				resultString = "Unauthorized access attempt";
			} else if (libraryRepo.readLibraryById(branchId) == null) {
				resultString = "Unvalid branch id choise.";
			} else if (bookRepo.readBookById(bookId) == null) {
				resultString = "Unvalid book id choise.";
			} else if (loan == null) {
				resultString = "You have no checked out book.";
			} else {
				loan.setDateIn(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
				ldao.returnLoan(loan, loan.getDateIn());
				resultString = "You successfully check in book.";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultString;
	}

	// ***************************************************************
	// Helper method to adding day to sql.Date objects
	public Date addDays(Date date, int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, days);
		return new Date(c.getTimeInMillis());
	}

	// ***************************************************************
	//
	public List<Book> readAvailableBooks(Integer branchId) {

		List<Book> bookList = new ArrayList<>();
		List<Inventory> invList = new ArrayList<>();
		try {
			if (libraryRepo.readLibraryById(branchId) == null) {
				return null;
			} else {
				invList = idao.readAvailableBooks(branchId);
				for (Inventory i : invList) {
					bookList.add(i.getBook());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bookList;
	}
	// ***************************************************************
	//
	public List<Loan> readLoans(Integer cardNo) {
		List<Loan> loanList = new ArrayList<>();
		try {
			loanList = ldao.readLoans(cardNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loanList;
	}

	// ***************************************************************
	//
	public List<Library> readLibraries(@RequestParam String name) {
		List<Library> libraries = new ArrayList<>();
		try {
			if (!name.isEmpty()) {
				libraries = libraryRepo.readLibraries(name);
			} else {
				libraries = (List<Library>) libraryRepo.findAll();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return libraries;
	}
}
