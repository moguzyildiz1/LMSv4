package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.gcit.lms.entity.Inventory;
import com.gcit.lms.repositories.BookRepository;
import com.gcit.lms.repositories.LibraryRepository;

@Component
public class InventoryDAO extends BaseDAO<Inventory> implements ResultSetExtractor<List<Inventory>> {

	@Autowired
	BookRepository bookRepo;

	@Autowired
	LibraryRepository libraryRepo;

	// *******************************************************************************************************************
	//
	public void addInventory(Inventory inventory)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update("insert into tbl_book_copies (bookId,branchId,noOfCopies) values(?,?,?)", new Object[] {
				inventory.getBook().getBookId(), inventory.getLibrary().getBranchId(), inventory.getCopies() });
	}

	// *******************************************************************************************************************
	//
	public void editInventory(Inventory inventory)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update("update tbl_book_copies set noOfCopies=? where bookId = ? and branchId=?", new Object[] {
				inventory.getCopies(), inventory.getBook().getBookId(), inventory.getLibrary().getBranchId() });
	}
	// *******************************************************************************************************************
	//
	public void editInventoryByParams(Integer libId, Integer bookId, Integer number)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update(
				"UPDATE tbl_book_copies NATURAL JOIN tbl_book NATURAL JOIN tbl_library_branch SET noOfCopies=? where tbl_book.bookId=?"
						+ " and tbl_library_branch.branchId=?",
				new Object[] { number, bookId, libId });
	}
	// *******************************************************************************************************************
	//
	public void addOneBookWithParams(String title, String branchName)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update(
				"UPDATE tbl_book_copies NATURAL JOIN tbl_book NATURAL JOIN tbl_library_branch SET noOfCopies=noOfCopies+1 where tbl_book.title=?"
						+ " and tbl_library_branch.branchName=?",
				new Object[] { title, branchName });
	}
	// *******************************************************************************************************************
	//
	public void deleteOneBookWithParams(String title, String branchName)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update(
				"UPDATE tbl_book_copies NATURAL JOIN tbl_book NATURAL JOIN tbl_library_branch SET noOfCopies=noOfCopies-1 where tbl_book.title=?"
						+ " and tbl_library_branch.branchName=?",
				new Object[] { title, branchName });
	}
	// ********************************************************************************************************
	//
	public List<Inventory> readAvailableBooks(Integer branchId)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return libraryTemplate.query("Select * from tbl_book_copies where branchId=? and noOfCopies>0",
				new Object[] { branchId }, this);
	}
	// ********************************************************************************************************
	//
	public List<Inventory> readInventoryById(Integer branchId)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return libraryTemplate.query("Select * from tbl_book_copies where branchId=? and noOfCopies>=0",
				new Object[] { branchId }, this);
	}
	// ********************************************************************************************************
	//
	public List<Inventory> readAllInventories()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return libraryTemplate.query("Select * from tbl_book_copies", this);
	}
	// *******************************************************************************************************************
	//
	@Override
	public List<Inventory> extractData(ResultSet rs) throws SQLException {
		List<Inventory> inventories = new ArrayList<>();
		while (rs.next()) {
			Inventory inventory = new Inventory();
			inventory.setCopies((rs.getInt("noOfCopies")));
			inventory.setBook(bookRepo.readBookById(rs.getInt("bookId")));
			inventory.setLibrary(libraryRepo.readLibraryById(rs.getInt("branchId")));
			inventories.add(inventory);
		}
		return inventories;
	}
}
