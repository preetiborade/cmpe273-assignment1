package edu.sjsu.cmpe.library.domain;


import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "isbn", "title", "publication-date", "language", "num-pages", "status", "authors", "reviews"})
public class Book {
	@JsonProperty
	private long isbn;
	@JsonProperty
	@NotEmpty
    private String title;
	@JsonProperty("publication-date")
	private String publication_date;
	@JsonProperty
    private String language;
	@JsonProperty("num-pages")
    private int num_pages;
    @JsonProperty
    private String status;
    @NotEmpty
	@Valid
	@JsonProperty
    private Author[] authors;

    /**
	 * @return the reviews
	 */
	public List<Review> getReviews() {
		return reviews;
	}


	/**
	 * @param reviews the reviews to set
	 */
	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	@JsonProperty
    private List<Review> reviews = new ArrayList<Review>();
    
	/**
	 * @return the authors
	 */
	public Author[] getAuthors() {
		return authors;
	}


	/**
	 * @param authors the authors to set
	 */
	public void setAuthors(Author[] authors) {
		this.authors = authors;
	} 								

    /**
     * @return the isbn
     */
    public long getIsbn() {
	return isbn;
    }

	/**
     * @param isbn
     *            the isbn to set
     */
       
    public void setIsbn(long isbn) {
	this.isbn = isbn;
    }

    /**
     * @return the title
     */
    public String getTitle() {
	return title;
    }

    /**
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
	this.title = title;
    }


	/**
	 * @return the publication_date
	 */
	public String getPublication_date() {
		return publication_date;
	}


	/**
	 * @param publication_date the publication_date to set
	 */
	public void setPublication_date(String publication_date) {
		this.publication_date = publication_date;
	}


	/**
	 * @return the num_pages
	 */
	public int getNum_pages() {
		return num_pages;
	}


	/**
	 * @param num_pages the num_pages to set
	 */
	public void setNum_pages(int num_pages) {
		this.num_pages = num_pages;
	}


	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
     * @return a review
     */
	public Review getoneReview(int id) {
		return this.reviews.get(id);
	}


}
