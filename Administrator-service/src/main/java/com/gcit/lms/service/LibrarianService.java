package com.gcit.lms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.gcit.lms.dao.InventoryDAO;
import com.gcit.lms.entity.Inventory;
import com.gcit.lms.entity.Library;
import com.gcit.lms.repositories.BookRepository;
import com.gcit.lms.repositories.LibraryRepository;
import com.gcit.lms.util.ResultServe;

@Service
public class LibrarianService {

	@Autowired
	LibraryRepository libraryRepo;

	@Autowired
	BookRepository bookRepo;

	@Autowired
	InventoryDAO idao;

	// ***************************************************************************************
	//
	public ResponseEntity<ResultServe<Library>> editLibraryParams(Integer id, String branchName, String branchAddress) {

		ResultServe<Library> result = new ResultServe<>();
		Library updatedLib = new Library();
		try {
			if (id == null) {
				result.setMessage("No such a record with id: " + id);
				return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
			} else {
				if ((updatedLib = libraryRepo.readLibraryById(id)) == null) {
					result.setMessage("No such a record with id: " + id);
					return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
				} else {
					if (!branchName.isEmpty()) {
						updatedLib.setBranchName(branchName);
					}
					if (!branchAddress.isEmpty()) {
						updatedLib.setBranchAddress(branchAddress);
					}
					libraryRepo.save(updatedLib);
					result.setData(updatedLib);
					result.setMessage("Library details successfully editted.");
				}
			}
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// ***************************************************************************************
	//
	public ResponseEntity<ResultServe<List<Inventory>>> readInventoryById(Integer libId) {

		ResultServe<List<Inventory>> result = new ResultServe<>();
		List<Inventory> inventoryList = new ArrayList<>();
		try {
			if (libraryRepo.readLibraryById(libId) == null) {
				result.setData(inventoryList);
				result.setMessage("No such a record in database");
				return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
			} else {
				inventoryList = idao.readInventoryById(libId);
				result.setData(inventoryList);
				result.setMessage("Inventory list successfully retrieved.");
			}
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// ***************************************************************************************
	//
	public ResponseEntity<ResultServe<Inventory>> editInventory(Integer libId, Integer bookId, Integer noCopy) {

		ResultServe<Inventory> result = new ResultServe<>();
		Inventory inventory = new Inventory();
		try {
			if (libraryRepo.readLibraryById(libId) == null) {
				result.setData(inventory);
				result.setMessage("No such a record in database");
				return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
			} else if (bookRepo.readBookById(bookId) == null) {
				result.setData(inventory);
				result.setMessage("No such a record in database");
				return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
			} else {
				idao.editInventoryByParams(libId, bookId, noCopy);
				result.setData(inventory);
				result.setMessage("Inventory successfully editted.");
			}
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// ***************************************************************************************
	//
	public ResponseEntity<ResultServe<Library>> editLibrary(Library library) {

		ResultServe<Library> result = new ResultServe<>();
		Library updatedLib = new Library();
		try {
			if (library == null) {
				result.setMessage("No such a library record");
				return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
			} else {
				if ((updatedLib = libraryRepo.readLibraryById(library.getBranchId())) == null) {
					result.setMessage("No such a library record");
					return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
				} else {
					if (!library.getBranchName().isEmpty()) {
						updatedLib.setBranchName(library.getBranchName());
					}
					if (!library.getBranchAddress().isEmpty()) {
						updatedLib.setBranchAddress(library.getBranchAddress());
					}
					libraryRepo.save(updatedLib);
					result.setData(updatedLib);
					result.setMessage("Library details successfully editted.");
				}
			}
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// ***************************************************************************************
	//
	public ResponseEntity<ResultServe<List<Library>>> readLibraries(@RequestParam String name) {

		ResultServe<List<Library>> result = new ResultServe<>();
		List<Library> libraries = new ArrayList<>();
		try {
			if (!name.isEmpty()) {
				libraries = libraryRepo.readLibraries(name);
				result.setData(libraries);
				result.setMessage("Library branch retrieved");
			} else {
				libraries = (List<Library>) libraryRepo.findAll();
				result.setData(libraries);
				result.setMessage("All branches retrieved");
			}
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// ***************************************************************************************
	//
	public ResponseEntity<ResultServe<Library>> readLibraryById(Integer id) {

		ResultServe<Library> result = new ResultServe<>();
		Library library = new Library();
		try {
			library = libraryRepo.readLibraryById(id);
			if (library.getBranchId() == null) {
				result.setMessage("No such a library record");
				return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
			} else {
				result.setData(library);
				result.setMessage("Library successfully retrieved");
			}
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// ***************************************************************************************
	//
	public ResponseEntity<ResultServe<Library>> saveLibrary(Library library) {

		ResultServe<Library> result = new ResultServe<>();
		Library returnLib = new Library();
		try {
			returnLib = libraryRepo.saveAndFlush(library);
			result.setData(returnLib);
			result.setMessage("Library successfully saved");
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
