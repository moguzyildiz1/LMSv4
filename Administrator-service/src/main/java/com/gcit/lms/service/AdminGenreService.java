package com.gcit.lms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.gcit.lms.entity.Genre;
import com.gcit.lms.repositories.GenreRepository;
import com.gcit.lms.util.GenreAlreadyExistsException;
import com.gcit.lms.util.ResultServe;

@Service
public class AdminGenreService {

	@Autowired
	GenreRepository genreRepo;

	// ************************************************************************
	// Returns genre by name or all genres with HttpStatus code OK
	//
	public ResponseEntity<ResultServe<List<Genre>>> readGenres(@RequestParam String name) {

		ResultServe<List<Genre>> result = new ResultServe<>();
		List<Genre> genres = new ArrayList<>();
		try {
			if (!name.isEmpty()) {
				genres = genreRepo.readGenres(name);
				result.setData(genres);
				result.setMessage("Genre: " + genres.get(0).getGenreName() + " returned successfully.");
			} else {
				genres = (List<Genre>) genreRepo.findAll();
				result.setData(genres);
				result.setMessage("All genres returned successfully.");
			}
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// ************************************************************************
	// Returns genre by Id with HttpStatus code Ok
	//
	public ResponseEntity<ResultServe<Genre>> readGenreById(Integer gId) {

		ResultServe<Genre> result = new ResultServe<>();
		Genre genre = new Genre();
		try {
			if (gId == null || gId == 0) {
				result.setMessage("Cannot found such a record with id: " + gId);
				result.setData(genre);
				return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
			}
			genre = genreRepo.readGenreById(gId);
			if (genre == null) {
				return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
			} else {
				result.setData(genre);
			}
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// ************************************************************************
	// Edit genre and returns updates genre with Http OK
	//
	public ResponseEntity<ResultServe<Genre>> editGenre(String genreName, Integer genreId) {

		ResultServe<Genre> result = new ResultServe<>();
		Genre genre = new Genre();
		try {
			genreRepo.editGenre(genreName, genreId);
			genre = genreRepo.readGenreById(genreId);
			result.setData(genre);
			result.setMessage("Genre editted successfully.");
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// ************************************************************************
	// Deletes genre by id and returns deleted genre with HttpStatus code OK
	//
	public ResponseEntity<ResultServe<Genre>> deleteGenre(Integer id) {

		ResultServe<Genre> result = new ResultServe<>();
		Genre genre = new Genre();
		try {
			genre = genreRepo.readGenreById(id);

			if (genre.getGenreName() == null) {
				result.setData(genre);
				result.setMessage("No such a record with id: " + id);
				return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
			}
			genreRepo.deleteById(id);
			result.setData(genre);
			result.setMessage("Genre deleted successfully.");
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// ************************************************************************
	// Saves genre by name and returns it with Http OK
	//
	public ResponseEntity<ResultServe<Genre>> saveGenreByName(String gName) {

		ResultServe<Genre> result = new ResultServe<>();
		Integer newId;
		Genre genre = new Genre();
		genre.setGenreName(gName);
		try {
			newId = (genreRepo.saveAndFlush(genre)).getGenreId();
			genre = genreRepo.readGenreById(newId);
			result.setData(genre);
			result.setMessage("Genre saved successfully.");
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// ************************************************************************
	//
	public Integer saveGenreWithId(String gName) {

		Integer newId = 0;
		Genre genre = new Genre();
		genre.setGenreName(gName);
		try {
			newId = (genreRepo.saveAndFlush(genre)).getGenreId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newId;
	}

	// ************************************************************************
	// Saves genre by name and returns it with Http OK
	//
	public ResponseEntity<ResultServe<Genre>> saveGenre(Genre genre) {

		ResultServe<Genre> result = new ResultServe<>();
		try {
			if (genreRepo.findByName(genre.getGenreName())!= null) {
				throw new GenreAlreadyExistsException();
			}
			genre = genreRepo.saveAndFlush(genre);
			result.setData(genre);
			result.setMessage("Genre saved successfully.");
		} catch (Exception e) {
			result.setMessage(e.getMessage());
			return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
