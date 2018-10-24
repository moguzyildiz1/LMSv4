package com.gcit.lms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.entity.Book;
import com.gcit.lms.service.AdminBookService;
import com.gcit.lms.util.ResultServe;

@RestController
@RequestMapping("/books/*")
public class AdminBookController {

	@Autowired
	AdminBookService adminService;

	// ****************************************************************************
	//
	@RequestMapping(value = "/{title}", produces = { "application/json", "application/xml" })
	public ResponseEntity<ResultServe<List<Book>>> readBooks(@PathVariable("title") String title) {
		return adminService.readBooks(title);
	}

	// ****************************************************************************
	//
	@RequestMapping(value = "/all", produces = { "application/json", "application/xml" })
	public ResponseEntity<ResultServe<List<Book>>> readBook() {
		String title = "";
		return adminService.readBooks(title);
	}

	// ****************************************************************************
	//
	@RequestMapping(value = "/book/{id}", produces = { "application/json", "application/xml" })
	public ResponseEntity<ResultServe<Book>> readBookById(@RequestParam("id") Integer id) {
		return adminService.readBookById(id);
	}

	// ************************************************************************
	//
	@RequestMapping(value = "/edit", produces = { "application/json", "application/xml" })
	public ResponseEntity<ResultServe<Book>> editBook(@RequestParam("title") String bTitle,
			@RequestParam("id") Integer bId) {
		return adminService.editBook(bTitle, bId);
	}

	// **********************************************************************************************
	//
	@RequestMapping(value = "/initBook", method = RequestMethod.GET, produces = { "application/json",
			"application/xml" })
	public ResponseEntity<ResultServe<Book>> initBook() {
		return new ResponseEntity<ResultServe<Book>>(new ResultServe<>(new Book()), HttpStatus.OK);
	}

	// ************************************************************************
	//
	@RequestMapping(value = "/delete/{id}", produces = { "application/json", "application/xml" })
	public ResponseEntity<ResultServe<Book>> deleteBook(@PathVariable("id") Integer bId) {
		return adminService.deleteBook(bId);
	}

	// ****************************************************************************
	//
	@RequestMapping(name = "/save", method = RequestMethod.POST, produces = { "application/json",
			"application/xml" })
	public ResponseEntity<ResultServe<Book>> saveBook(@RequestBody Book book) {
		return adminService.saveBook(book);
	}
}
