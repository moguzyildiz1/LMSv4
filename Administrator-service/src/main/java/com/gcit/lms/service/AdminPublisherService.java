package com.gcit.lms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.gcit.lms.entity.Publisher;
import com.gcit.lms.repositories.PublisherRepository;
import com.gcit.lms.util.ResultServe;

@Service
public class AdminPublisherService {

	@Autowired
	PublisherRepository publisherRepo;

	// ****************************************************************************************
	// Returns publishers with HttpStatus OK
	//
	public ResponseEntity<ResultServe<List<Publisher>>> readPublishers(@RequestParam String name) {

		ResultServe<List<Publisher>> result = new ResultServe<>();
		List<Publisher> publishers = new ArrayList<>();
		try {
			if (!name.isEmpty()) {
				publishers = publisherRepo.readPublishers(name);
				result.setData(publishers);
			} else {
				publishers = (List<Publisher>) publisherRepo.findAll();
				result.setData(publishers);
				result.setMessage("All publishers returned successfully.");
			}
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// ****************************************************************************************
	// Returns publisher by id with HttpStatus OK
	//
	public ResponseEntity<ResultServe<Publisher>> readPublisherById(Integer id) {

		ResultServe<Publisher> result = new ResultServe<>();
		Publisher publisher = new Publisher();
		try {
			publisher = publisherRepo.readPublisherById(id);
			if (publisher.getPublisherId() == null) {
				result.setData(publisher);
				result.setMessage("No such a record with id: " + id);
				return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
			} else {
				result.setData(publisher);
			}
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// ************************************************************************
	// Edits publisher and returns updates form with Http OK
	//
	public ResponseEntity<ResultServe<Publisher>> editPublisher(String pName, Integer pId) {

		ResultServe<Publisher> result = new ResultServe<>();
		Publisher publisher = new Publisher();
		try {
			publisherRepo.editPublisher(pName, pId);
			publisher = publisherRepo.readPublisherById(pId);
			result.setData(publisher);
			result.setMessage("Publisher editted successfully.");
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// ************************************************************************
	// Deletes publisher and returns deleted publisher with Http OK
	//
	public ResponseEntity<ResultServe<Publisher>> deletePublisher(Integer pId) {

		ResultServe<Publisher> result = new ResultServe<>();
		Publisher publisher = new Publisher();
		try {
			publisher = publisherRepo.readPublisherById(pId);

			if (publisher.getPublisherId() == null) {
				result.setData(publisher);
				result.setMessage("No such a record with id: " + pId);
				return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
			}
			publisherRepo.deleteById(pId);
			result.setData(publisher);
			result.setMessage("Publisher deleted successfully.");
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// ************************************************************************
	// Saves publisher and returns with Http OK
	//
	public ResponseEntity<ResultServe<Publisher>> savePublisherByParam(String pName, String pAddress, String pPhone) {

		ResultServe<Publisher> result = new ResultServe<>();
		Publisher publisher = new Publisher();
		publisher.setPubName(pName);
		publisher.setPubAddress(pAddress);
		publisher.setPubPhone(pPhone);
		try {
			publisher = (publisherRepo.saveAndFlush(publisher));
			result.setData(publisher);
			result.setMessage("Publisher saved successfully.");
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// ************************************************************************
	//
	public Integer savePublisherWithId(String pName, String pAddress, String pPhone) {

		Integer newId = 0;
		Publisher publisher = new Publisher();
		publisher.setPubName(pName);
		publisher.setPubAddress(pAddress);
		publisher.setPubPhone(pPhone);
		try {
			newId = (publisherRepo.saveAndFlush(publisher)).getPublisherId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newId;
	}

	// ****************************************************************************************
	//
	public ResponseEntity<ResultServe<Publisher>> savePublisher(Publisher publisher) {

		ResultServe<Publisher> result = new ResultServe<>();
		Publisher returnedPub = new Publisher();
		try {
			returnedPub = publisherRepo.saveAndFlush(publisher);
			result.setData(returnedPub);
			result.setMessage("Publisher saved successfully.");
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
