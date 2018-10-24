package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.gcit.lms.entity.Publisher;

@Component
public class PublisherDAO extends BaseDAO<Publisher> implements ResultSetExtractor<List<Publisher>> {

	@Autowired
	BookDAO bdao;

	// **********************************************************************************************************
	// Auto-generated primary key save method
	public Integer addPublisher(Publisher publisher)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Integer ID = 0;
		ID = libraryTemplate.update(
				"insert into tbl_publisher (publisherName,publisherAddress,publisherPhone) values(?,?,?)",
				new Object[] { publisher.getPubName(), publisher.getPubAddress(), publisher.getPubPhone() });
		return ID;
	}

	// **********************************************************************************************************
	//
	public void editPublisher(Publisher publisher)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update(
				"update tbl_publisher set publisherName = ? , publisherAddress=? , publisherPhone=? where publisherId = ?",
				new Object[] { publisher.getPubName(), publisher.getPubAddress(), publisher.getPubPhone(),
						publisher.getPublisherId() });
	}

	// **********************************************************************************************************
	//
	public void deletePublisher(Publisher publisher)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		libraryTemplate.update("delete from tbl_publisher where publisherId = ?",
				new Object[] { publisher.getPublisherId() });
	}

	// **********************************************************************************************************
	//
	public List<Publisher> readAllPublishers()
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return libraryTemplate.query("Select * from tbl_publisher", this);
	}

	// **********************************************************************************************************
	//
	public List<Publisher> readAllPublishersByName(String searchString)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		return libraryTemplate.query("Select * from tbl_publisher where publisherName like ?",
				new Object[] { searchString }, this);
	}

	// **********************************************************************************************************
	//
	public Publisher readPublisherByPK(Integer pk)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		List<Publisher> publishers = libraryTemplate.query("Select * from tbl_publisher where publisherId = ?",
				new Object[] { pk }, this);
		if (publishers != null) {
			return publishers.get(0);
		} else {
			return null;
		}
	}
	// **********************************************************************************************************
	//
	@Override
	public List<Publisher> extractData(ResultSet rs)
			throws SQLException {
		List<Publisher> publishers = new ArrayList<>();
		while (rs.next()) {
			Publisher publisher = new Publisher();
			publisher.setPublisherId(rs.getInt("publisherId"));
			publisher.setPubName(rs.getString("publisherName"));
			publisher.setPubPhone(rs.getString("publisherPhone"));
			publisher.setPubAddress(rs.getString("publisherAddress"));
			publisher.setBooks(bdao.readBookByPubId(rs.getInt("publisherId")));
			publishers.add(publisher);
		}
		return publishers;
	}
}
