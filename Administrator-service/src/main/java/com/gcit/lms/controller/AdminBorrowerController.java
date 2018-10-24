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

import com.gcit.lms.entity.Borrower;
import com.gcit.lms.service.AdminBorrowerService;
import com.gcit.lms.util.ResultServe;

@RestController
@RequestMapping("/borrowers/*")
public class AdminBorrowerController {

	@Autowired
	AdminBorrowerService adminService;

	// ************************************************************************
	//
	@RequestMapping(value = "/all", produces = { "application/json", "application/xml" })
	public ResponseEntity<ResultServe<List<Borrower>>> readBorrowers() {
		return adminService.readBorrowers();
	}

	// ************************************************************************
	//
	@RequestMapping(value = "/borrower/{cardNo}", produces = { "application/json", "application/xml" })
	public ResponseEntity<ResultServe<Borrower>> readBorrowerById(@PathVariable("cardNo") Integer cardNo) {
		return adminService.readBorrowerByNo(cardNo);
	}

	// ************************************************************************
	//
	@RequestMapping(value = "/{name}", produces = { "application/json", "application/xml" })
	public ResponseEntity<ResultServe<Borrower>> readBorrower(@PathVariable("name") String name) {
		return adminService.readBorrower(name);
	}

	// ************************************************************************
	//
	@RequestMapping(value = "/edit", produces = { "application/json", "application/xml" })
	public ResponseEntity<ResultServe<Borrower>> editBorrower(@RequestParam("name") String bName,
			@RequestParam("cardNo") Integer bCardNo) {
		return adminService.editBorrower(bName, bCardNo);
	}

	// ************************************************************************
	//
	@RequestMapping(value = "/delete", produces = { "application/json", "application/xml" })
	public ResponseEntity<ResultServe<Borrower>> deleteBorrower(@RequestParam("cardNo") Integer bCardNo) {
		return adminService.deleteBorrower(bCardNo);
	}

	// ************************************************************************
	//
	@RequestMapping(value = "/add", produces = { "application/json", "application/xml" })
	public ResponseEntity<ResultServe<Borrower>> saveBorrowerByParam(@RequestParam("name") String bName,
			@RequestParam("address") String bAddress, @RequestParam("phone") String bPhone) {
		return adminService.saveBorrowerByParam(bName, bAddress, bPhone);
	}

	// ************************************************************************
	//
	@RequestMapping(name = "/save", method = RequestMethod.POST, produces = { "application/json",
			"application/xml" })
	public ResponseEntity<ResultServe<Borrower>> saveBorrower(@RequestBody Borrower borrower) {
		return adminService.saveBorrower(borrower);
	}
}
