package com.gcit.lms.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.entity.Loan;
import com.gcit.lms.service.AdminLoanService;
import com.gcit.lms.util.ResultServe;

@RestController
@RequestMapping("/loans/*")
public class AdminLoanController {

	@Autowired
	AdminLoanService adminService;

	// *************************************************************************
	//
	@RequestMapping(value = "/all", produces = { "application/json", "application/xml" })
	public ResponseEntity<ResultServe<List<Loan>>> readLoans() {
		return adminService.readLoans();
	}

	// *************************************************************************
	//
	@RequestMapping(value = "/edit", produces = { "application/json", "application/xml" })
	public ResponseEntity<ResultServe<Loan>> editLoan(@RequestParam("cardNo") Integer cardNo,
			@RequestParam("branchId") Integer branchId, @RequestParam("bookId") Integer bookId,
			@RequestParam("dueDate") Date dueDate) {
		return adminService.editLoan(cardNo, branchId, bookId, dueDate);
	}
}
