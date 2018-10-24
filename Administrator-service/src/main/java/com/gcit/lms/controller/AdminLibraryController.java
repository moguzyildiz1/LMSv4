package com.gcit.lms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.entity.Library;
import com.gcit.lms.service.LibrarianService;
import com.gcit.lms.util.ResultServe;

@RestController
@RequestMapping("/libraries/*")
public class AdminLibraryController {

	@Autowired
	LibrarianService libService;

	// ******************************************************************************************************
	//
	@RequestMapping(value = "/all", produces = { "application/json", "application/xml" })
	public ResponseEntity<ResultServe<List<Library>>> readLibraries() {
		String name = "";
		return libService.readLibraries(name);
	}

	// ******************************************************************************************************
	//
	@RequestMapping(value = "/{name}", produces = { "application/json", "application/xml" })
	public ResponseEntity<ResultServe<List<Library>>> readLibrary(@PathVariable("name") String name) {
		return libService.readLibraries(name);
	}

	// ******************************************************************************************************
	//
	@RequestMapping(value = "/library/{id}", produces = { "application/json", "application/xml" })
	public ResponseEntity<ResultServe<Library>> readLibraryById(@PathVariable("id") Integer id) {
		return libService.readLibraryById(id);
	}

	// ******************************************************************************************************
	//
	@RequestMapping(name = "/save", method = RequestMethod.POST, produces = { "application/json", "application/xml" })
	public ResponseEntity<ResultServe<Library>> saveLibrary(@RequestBody Library library) {
		return libService.saveLibrary(library);
	}
}
