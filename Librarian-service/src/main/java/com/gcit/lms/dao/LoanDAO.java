package com.gcit.lms.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.gcit.lms.entity.Loan;
import com.gcit.lms.repositories.BookRepository;
import com.gcit.lms.repositories.BorrowerRepository;
import com.gcit.lms.repositories.LibraryRepository;

@Component
public class LoanDAO extends BaseDAO<Loan> implements ResultSetExtractor<List<Loan>> {

	@Autowired
	BookRepository bookRepo;

	@Autowired
	LibraryRepository libraryRepo;
	
	@Autowired
	BorrowerRepository borrowerRepo;

	@Autowired
	InventoryDAO indao;

	/**
	 * 
	 * @param loan
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void addLoan(Loan loan)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update(
				"insert into tbl_book_loans (bookId,branchId,cardNo,dateOut,dueDate,dateIn) values(?,?,?,?,?,?)",
				new Object[] { loan.getBook().getBookId(), loan.getLibrary().getBranchId(),
						loan.getBorrower().getCardNo(), loan.getDateOut(), loan.getDueDate(), null });
		// set new number of copy of this branch
		indao.deleteOneBookWithParams(loan.getBook().getTitle(), loan.getLibrary().getBranchName());
	}

	/**
	 * 
	 * @param loan
	 * @param dateIn
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void returnLoan(Loan loan, Date dateIn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update("update tbl_book_loans set dateIn = ? where cardNo = ? and bookId=? and branchId=?",
				new Object[] { dateIn, loan.getBorrower().getCardNo(), loan.getBook().getBookId(),
						loan.getLibrary().getBranchId() });
		// set new number of copy of this branch
		indao.addOneBookWithParams(loan.getBook().getTitle(), loan.getLibrary().getBranchName());
	}
	
	//**************************************************************************************************************
	//
	public void editDueDate(Loan loan, Date newDueDate)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update("update tbl_book_loans set dueDate = ? where cardNo = ? and bookId=? and branchId=?",
				new Object[] { newDueDate, loan.getBorrower().getCardNo(), loan.getBook().getBookId(),
						loan.getLibrary().getBranchId() });
	}

	//**************************************************************************************************************
	//
	public List<Loan> readAllLoans()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return libraryTemplate.query("SELECT * FROM library.tbl_book_loans",this);
	}	
	
	//**************************************************************************************************************
	//
	public List<Loan> readAllLoansUnreturned()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return libraryTemplate.query("SELECT * FROM library.tbl_book_loans where dateIn is null",this);
	}
	
	//**************************************************************************************************************
	//
	public List<Loan> readLoan(Integer cardNo, Integer branchId, Integer bookId)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return libraryTemplate.query("SELECT * FROM library.tbl_book_loans where cardNo=? and branchId=? and bookId=? and dateIn is null",
				new Object[] {cardNo,branchId,bookId},this);
	}
	
	//********************************************************************************************************************************
	public List<Loan> readLoans(Integer cardNo)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return libraryTemplate.query("SELECT * FROM library.tbl_book_loans where cardNo=? and dateIn is null",
				new Object[] {cardNo},this);
	}

	@Override
	public List<Loan> extractData(ResultSet rs) throws SQLException {
		List<Loan> loans = new ArrayList<>();
		while (rs.next()) {
			Loan loan = new Loan();
			loan.setDateOut(rs.getDate("dateOut"));
			loan.setDueDate(rs.getDate("dueDate"));
			loan.setDateIn(rs.getDate("dateIn"));
			loan.setBook(bookRepo.readBookById(rs.getInt("bookId")));
			loan.setLibrary(libraryRepo.readLibraryById(rs.getInt("branchId")));
			loan.setBorrower(borrowerRepo.readBorrowerByNo(rs.getInt("cardNo")));
			loans.add(loan);
		}
		return loans;
	}
}
