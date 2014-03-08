package edu.sjsu.cmpe.library.dto;

import edu.sjsu.cmpe.library.domain.Author;

public class AuthorDto extends LinksDto {
	private Author author;

	public AuthorDto(Author author) {
		super();
		this.author = author;
	    }

	public AuthorDto(){

    }


	/**
	 * @return the author
	 */
	public Author getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(Author author) {
		this.author = author;
	}

}