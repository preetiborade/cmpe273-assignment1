package edu.sjsu.cmpe.library.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import edu.sjsu.cmpe.library.domain.Review;
@JsonPropertyOrder(alphabetic = true)
public class ReviewDto extends LinksDto {

	private Review review;

	/**
	 * @return the review
	 */
	public Review getReview() {
		return review;
	}

	public ReviewDto(Review review) {
		super();
		this.review = review;
		// TODO Auto-generated constructor stub
	}
	public ReviewDto(){
		super();
	}

	/**
	 * @param review the review to set
	 */
	public void setReview(Review review) {
		this.review = review;
	}
}