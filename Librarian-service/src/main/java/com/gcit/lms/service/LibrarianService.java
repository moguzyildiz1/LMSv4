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

@Service
public class LibrarianService {

	@Autowired
	LibraryRepository libraryRepo;

	@Autowired
	BookRepository bookRepo;

	@Autowired
	InventoryDAO idao;
	
	//***************************************************************************************
	//
	public String editLibraryParams(Integer id, String branchName, String branchAddress) {
		String resultString = "";
		Library updatedLib = new Library();
		try {
			if (id == null) {
				resultString = "Unvalid branch choise.";
			} else {
				if ((updatedLib = libraryRepo.readLibraryById(id)) == null) {
					resultString = "Unvalid branch choise.";
				} else {
					if (!branchName.isEmpty()) {
						updatedLib.setBranchName(branchName);
					}
					if (!branchAddress.isEmpty()) {
						updatedLib.setBranchAddress(branchAddress);
					}
					libraryRepo.save(updatedLib);
					resultString = "You successfully updated branch.";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultString;
	}
	//***************************************************************************************
	//
	public List<Inventory> readInventoryById(Integer libId) {		
		List<Inventory> inventoryList = new ArrayList<>();
		try {
			if (libraryRepo.readLibraryById(libId) == null) {
				return inventoryList;
			}else {
				inventoryList=idao.readInventoryById(libId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inventoryList;
	}
	//***************************************************************************************
	//
	public ResponseEntity<String> editInventory(Integer libId, Integer bookId, Integer noCopy) {
		
		//String resultString = "";	
		ResponseEntity<String> responseEntity = new ResponseEntity<>("Initial Message...",HttpStatus.OK);
		try {
			if (libraryRepo.readLibraryById(libId) == null) {
				//resultString = "Unvalid branch id choise.";
			} else if (bookRepo.readBookById(bookId) == null) {
				//resultString = "Unvalid book id choise.";
			} else {
				idao.editInventoryByParams(libId, bookId, noCopy);
				responseEntity = new ResponseEntity<>("You successfully editted copy number.",HttpStatus.OK);
				//resultString = "You successfully editted number of copies.";
			}
		} catch (Exception e) {
			responseEntity = new ResponseEntity<>("Exception occured. Operation failed.",HttpStatus.OK);
			e.printStackTrace();
		}
		return responseEntity;
	}
	//***************************************************************************************
	//
	public String editLibrary(Library library) {
		String resultString = "";
		Library updatedLib = new Library();
		try {
			if (library == null) {
				resultString = "Unvalid branch choise.";
			} else {
				if ((updatedLib = libraryRepo.readLibraryById(library.getBranchId())) == null) {
					resultString = "Unvalid branch choise.";
				} else {
					if (!library.getBranchName().isEmpty()) {
						updatedLib.setBranchName(library.getBranchName());
					}
					if (!library.getBranchAddress().isEmpty()) {
						updatedLib.setBranchAddress(library.getBranchAddress());
					}
					libraryRepo.save(updatedLib);
					resultString = "You successfully updated branch.";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultString;
	}
	//***************************************************************************************
	//
	public List<Library> readLibraries(@RequestParam String name) {
		List<Library> libraries = new ArrayList<>();
		try {
			if (!name.isEmpty()) {
				libraries = libraryRepo.readLibraries(name);
			} else {
				libraries = (List<Library>) libraryRepo.findAll();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return libraries;
	}
	//***************************************************************************************
	//
	public Library readLibraryById(Integer id) {
		Library library = new Library();
		try {
			library = libraryRepo.readLibraryById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return library;
	}
	
	//***************************************************************************************
	//
	public Library saveLibrary(Library library) {
		Library returnLib = new Library();
		try {
			returnLib=libraryRepo.saveAndFlush(library);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnLib;
	}
}
