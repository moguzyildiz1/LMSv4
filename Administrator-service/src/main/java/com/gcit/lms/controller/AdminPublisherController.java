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

import com.gcit.lms.entity.Publisher;
import com.gcit.lms.service.AdminPublisherService;
import com.gcit.lms.util.ResultServe;

@RestController
@RequestMapping("/publishers/*")
public class AdminPublisherController {

	@Autowired
	AdminPublisherService adminService;

	// ****************************************************************************************
	//
	@RequestMapping(value = "/all", produces = { "application/json", "application/xml" })
	public ResponseEntity<ResultServe<List<Publisher>>> readPublishers() {
		String name = "";
		return adminService.readPublishers(name);
	}

	// ****************************************************************************************
	//
	@RequestMapping(value = "/{name}", produces = { "application/json", "application/xml" })
	public ResponseEntity<ResultServe<List<Publisher>>> readPublishers(@PathVariable("name") String name) {
		return adminService.readPublishers(name);
	}

	// ****************************************************************************************
	//
	@RequestMapping(value = "/publisher/{id}", produces = { "application/json", "application/xml" })
	public ResponseEntity<ResultServe<Publisher>> readPublisherById(@PathVariable("id") Integer id) {
		return adminService.readPublisherById(id);
	}

	// ************************************************************************
	//
	@RequestMapping(value = "/edit", produces = { "application/json", "application/xml" })
	public ResponseEntity<ResultServe<Publisher>> editPublisher(@RequestParam("name") String pName,
			@RequestParam("id") Integer pId) {
		return adminService.editPublisher(pName, pId);
	}

	// ************************************************************************
	//
	@RequestMapping(value = "/add", produces = { "application/json", "application/xml" })
	public ResponseEntity<ResultServe<Publisher>> savePublisherByParam(@RequestParam("name") String pName,
			@RequestParam("address") String pAddress, @RequestParam("phone") String pPhone) {
		return adminService.savePublisherByParam(pName, pAddress, pPhone);
	}

	// ************************************************************************
	//
	@RequestMapping(value = "/savePublisherWithId", produces = { "application/json", "application/xml" })
	public Integer savePublisherWithId(@RequestParam("name") String pName, @RequestParam("address") String pAddress,
			@RequestParam("phone") String pPhone) {
		return adminService.savePublisherWithId(pName, pAddress, pPhone);
	}

	// ************************************************************************
	//
	@RequestMapping(value = "/initPublisher", produces = { "application/json", "application/xml" })
	public ResponseEntity<ResultServe<Publisher>> initPublisher() {
		return new ResponseEntity<ResultServe<Publisher>>(new ResultServe<>(new Publisher()), HttpStatus.OK);
	}

	// ************************************************************************
	//
	@RequestMapping(value = "/delete", produces = { "application/json", "application/xml" })
	public ResponseEntity<ResultServe<Publisher>> deletePublisher(@RequestParam("id") Integer pId) {
		return adminService.deletePublisher(pId);
	}

	// ****************************************************************************************
	//
	@RequestMapping(name = "/save", method = RequestMethod.POST, produces = { "application/json", "application/xml" })
	public ResponseEntity<ResultServe<Publisher>> savePublisher(@RequestBody Publisher publisher) {
		return adminService.savePublisher(publisher);
	}
}
