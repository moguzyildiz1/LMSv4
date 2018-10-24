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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gcit.lms.entity.Genre;
import com.gcit.lms.service.AdminGenreService;
import com.gcit.lms.util.ForbiddenException;
import com.gcit.lms.util.ResultServe;

@RestController
@RequestMapping("/genres/*")
public class AdminGenreController {

	@Autowired
	AdminGenreService adminService;

	// ************************************************************************
	//
	@RequestMapping(value = "", produces = { "application/json", "application/xml" })
	@ResponseBody
	public ResponseEntity<?> sendViaResponseEntity() {
		throw new ForbiddenException();
	}

	// ************************************************************************
	//
	@RequestMapping(value = "/all", produces = { "application/json", "application/xml" })
	public ResponseEntity<ResultServe<List<Genre>>> readGenres() {
		String name="";
		return adminService.readGenres(name);
	}

	// ************************************************************************
	//
	@RequestMapping(value = "/{name}", produces = { "application/json", "application/xml" })
	public ResponseEntity<ResultServe<List<Genre>>> readGenre(@PathVariable("name") String name) {
		return adminService.readGenres(name);
	}

	// ************************************************************************
	//
	@RequestMapping(value = "/genre/{id}", produces = { "application/json", "application/xml" })
	public ResponseEntity<ResultServe<Genre>> readGenreById(@PathVariable("id") Integer id) {
		return adminService.readGenreById(id);
	}

	// ************************************************************************
	//
	@RequestMapping(value = "/initGenre", produces = { "application/json", "application/xml" })
	public ResponseEntity<ResultServe<Genre>> initGenre() {
		return new ResponseEntity<ResultServe<Genre>>(new ResultServe<>(new Genre()), HttpStatus.OK);
	}

	// ************************************************************************
	//
	@RequestMapping(value = "/delete/{id}", produces = { "application/json", "application/xml" })
	public ResponseEntity<ResultServe<Genre>> deleteGenre(@PathVariable("id") Integer id) {
		return adminService.deleteGenre(id);
	}

	// ************************************************************************
	//
	@RequestMapping(value = "/edit", produces = { "application/json", "application/xml" })
	public ResponseEntity<ResultServe<Genre>> editGenre(@RequestParam("name") String gName,
			@RequestParam("id") Integer gId) {
		return adminService.editGenre(gName, gId);
	}

	// ************************************************************************
	//
	@RequestMapping(value = "/add", produces = { "application/json", "application/xml" })
	public ResponseEntity<ResultServe<Genre>> saveGenreByName(@RequestParam("name") String gName) {
		return adminService.saveGenreByName(gName);
	}

	// ************************************************************************
	//
//	@RequestMapping(value = "/saveGenreWithId", produces = { "application/json", "application/xml" })
//	public Integer saveGenreWithId(@RequestParam("name") String gName) {
//		return adminService.saveGenreWithId(gName);
//	}

	// ************************************************************************
	// Returns custom http response message
	//
	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = { "application/json",
			"application/xml" })
	public ResponseEntity<ResultServe<Genre>> saveGenre(@RequestBody Genre genre) {
		return adminService.saveGenre(genre);
	}
}
