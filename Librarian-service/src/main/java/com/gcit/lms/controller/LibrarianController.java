package com.gcit.lms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.entity.Inventory;
import com.gcit.lms.entity.Library;
import com.gcit.lms.service.LibrarianService;

@RestController
@RequestMapping("/*")
public class LibrarianController {

	@Autowired
	LibrarianService librarianService;

	// ***************************************************************************************
	//
	@RequestMapping(value = "/branches/all", produces = { "application/json", "application/xml" })
	public List<Library> readAllLibraries() {
		String name="";
		return librarianService.readLibraries(name);
	}

	// ***************************************************************************************
	//
	@RequestMapping(value = "/branches/{name}", produces = { "application/json", "application/xml" })
	public List<Library> readAllLibraries(@PathVariable("name") String name) {
		return librarianService.readLibraries(name);
	}

	// ***************************************************************************************
	//
	@RequestMapping(value = "/branch/{id}", produces = { "application/json", "application/xml" })
	public Library readLibraryById(@PathVariable("id") Integer id) {
		return librarianService.readLibraryById(id);
	}

	// ***************************************************************************************
	//
	@RequestMapping(value = "/branch/edit", produces = { "application/json", "application/xml" })
	public String editLibrary(@RequestParam("id") Integer id, @RequestParam("bName") String bName,
			@RequestParam("bAddress") String bAddress) {
		return librarianService.editLibraryParams(id, bName, bAddress);
	}

	// ***************************************************************************************
	//
	@RequestMapping(value = "/editInventory", produces = { "application/json", "application/xml" })
	public ResponseEntity<String> editInventory(@RequestParam("branchId") Integer branchId,
			@RequestParam("bookId") Integer bookId, @RequestParam("noCopy") Integer noCopy) {
		return librarianService.editInventory(branchId, bookId, noCopy);
	}

	// ***************************************************************************************
	//
	@RequestMapping(value = "/readInventoryById", produces = { "application/json", "application/xml" })
	public List<Inventory> showInventory(@RequestParam("branchId") Integer branchId) {
		return librarianService.readInventoryById(branchId);
	}

	// ***************************************************************************************
	//
	@RequestMapping(name = "/edit", method = RequestMethod.POST, produces = { "application/json",
			"application/xml" })
	public String editLibrary(@RequestBody Library Library) {
		return librarianService.editLibrary(Library);
	}
}
