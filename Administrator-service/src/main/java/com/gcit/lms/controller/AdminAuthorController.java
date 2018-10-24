package com.gcit.lms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.entity.Author;
import com.gcit.lms.service.AdminAuthorService;
import com.gcit.lms.util.ResultServe;

@RestController
@RequestMapping("/authors/*")
public class AdminAuthorController {

	@Autowired
	AdminAuthorService adminService;

	// ***************************************************************************
	@RequestMapping(value = "/all", produces = { "application/json", "application/xml" })
	public ResponseEntity<ResultServe<List<Author>>> readAllAuthors() {
		return adminService.readAuthors();
	}

	// ***************************************************************************
	@RequestMapping(value = "/author/{id}", produces = { "application/json", "application/xml" })
	public ResponseEntity<ResultServe<Author>> readAuthor(@PathVariable("id") Integer id) {
		return adminService.readAuthorById(id);
	}

	// ***************************************************************************
	@RequestMapping(value = "/{name}", produces = { "application/json", "application/xml" })
	public ResponseEntity<ResultServe<Author>> readAuthor(@PathVariable("name") String name) {
		return adminService.readAuthor(name);
	}
	// ***************************************************************************
	//
	@RequestMapping(value = "/add/{name}", produces = { "application/json", "application/xml" })
	public Integer saveAuthorWithId(@PathVariable("name") String name) {
		return adminService.saveAuthorWithId(name);
	}
	// ***************************************************************************
	//
	@RequestMapping("/initAuthor")
	public ResponseEntity<ResultServe<Author>> initAuthor() {
		return new ResponseEntity<ResultServe<Author>>(new ResultServe<>(new Author()), HttpStatus.OK);
	}

	// ***************************************************************************
	//
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET, produces = { "application/json",
			"application/xml" })
	public ResponseEntity<ResultServe<Author>> deleteAuthor(@PathVariable Integer id) {
		return adminService.deleteAuthor(id);
	}

	// ***************************************************************************
	//
	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = { "application/json",
			"application/xml" })
	public ResponseEntity<ResultServe<Author>> saveAuthor(@RequestBody Author author) {
		return adminService.saveAuthor(author);
	}
}
