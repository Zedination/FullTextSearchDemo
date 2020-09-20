package com.example.demo.dao;

import com.example.demo.entities.Book;
import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

//import org.hibernate.search.jpa.Search;


@Repository
@Transactional
public class FullTextSearchDemo {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public void insertBook(Book book) {
		entityManager.persist(book);
	}
	
	public List<Book> searchBook(String keyWord){
		Session session = entityManager.unwrap(Session.class);
		FullTextSession fullTextSession = Search.getFullTextSession(session);
		QueryBuilder qb = fullTextSession.getSearchFactory()
				.buildQueryBuilder().forEntity(Book.class).get();
		org.apache.lucene.search.Query query = qb
				.keyword().onFields("title","description","author") // Chỉ định tìm theo cột nào
				.matching(keyWord)
				.createQuery();
		org.hibernate.search.FullTextQuery hibQuery = fullTextSession.createFullTextQuery(query, Book.class);
		List<Book> results = hibQuery.getResultList();
		return results;
	}
//	public List<Book> searchBookJPA(String keyWord){
//		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
//		QueryBuilder qb = fullTextEntityManager.getSearchFactory()
//				.buildQueryBuilder().forEntity(Book.class).get();
//		org.apache.lucene.search.Query query = qb
//				.keyword().onFields("title", "description", "author") // Chỉ định tìm theo cột nào
//				.matching(keyWord)
//				.createQuery();
//		org.hibernate.search.jpa.FullTextQuery hibQuery = fullTextEntityManager.createFullTextQuery(query, Book.class);
//		List<Book> results = hibQuery.getResultList();
//		return results;
//	}
	public void indexBooks() throws Exception {
		try {
			Session session = entityManager.unwrap(Session.class);
			FullTextSession fullTextSession = Search.getFullTextSession(session);
			fullTextSession.createIndexer().startAndWait();
		} catch(Exception e) {
			throw e;
		}
	}
}
