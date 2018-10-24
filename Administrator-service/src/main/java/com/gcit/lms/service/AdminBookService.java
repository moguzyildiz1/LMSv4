package com.gcit.lms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gcit.lms.entity.Book;
import com.gcit.lms.repositories.BookRepository;
import com.gcit.lms.util.ResultServe;

@Service
public class AdminBookService {

	@Autowired
	BookRepository bookRepo;

	// ****************************************************************************
	// Returns all books or specified book by name with HttpStatus code OK
	//
	public ResponseEntity<ResultServe<List<Book>>> readBooks(String title) {

		ResultServe<List<Book>> result = new ResultServe<>();
		List<Book> books = new ArrayList<>();
		try {
			if (!title.isEmpty()) {
				books = bookRepo.readBooks(title);
				result.setData(books);
				result.setMessage("Book: " + books.get(0).getTitle() + " returned successfully.");
			} else {
				books = bookRepo.findAll();
				result.setData(books);
				result.setMessage("All books returned successfully.");
			}
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// ************************************************************************
	// Deletes book by its Id and returns deleted book with HttpStatus code OK
	//
	public ResponseEntity<ResultServe<Book>> deleteBook(Integer bId) {

		ResultServe<Book> result = new ResultServe<>();
		Book book = bookRepo.readBookById(bId);
		try {
			bookRepo.deleteById(bId);
			result.setMessage("Book succesfully deleted from library.");
			result.setData(book);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// ****************************************************************************
	// Reads book by id returns corresponding book with HttpStatus code OK
	//
	public ResponseEntity<ResultServe<Book>> readBookById(Integer id) {

		ResultServe<Book> result = new ResultServe<>();
		Book book = new Book();
		try {
			if (id == null || id == 0) {
				result.setMessage("Cannot found such a record with id: " + id);
				result.setData(book);
				return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
			} else {
				book = bookRepo.readBookById(id);
				result.setData(book);
			}
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// ************************************************************************
	// Edits book and returns editted version of book with HttpStatus OK
	//
	public ResponseEntity<ResultServe<Book>> editBook(String bTitle, Integer bId) {
		
		ResultServe<Book> result = new ResultServe<>();
		Book book = new Book();
		try {
			bookRepo.editBook(bTitle, bId);
			book = bookRepo.readBookById(bId);
			result.setMessage("Book succesfully editted.");
			result.setData(book);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// ****************************************************************************
	// Saves book to database and returns saved book with HttpStatus code OK
	//
	public ResponseEntity<ResultServe<Book>> saveBook(Book book) {
		
		ResultServe<Book> result = new ResultServe<>();
		Book returnedBook = new Book();
		try {
			returnedBook = bookRepo.saveAndFlush(book);
			result.setMessage("Book succesfully saved.");
			result.setData(returnedBook);
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
