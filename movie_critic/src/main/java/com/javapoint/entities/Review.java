package com.javapoint.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.*;

@Entity(name = "review")
@Data
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int review_id;
	
	private String movie_reviews;

	private double movie_ratings;

	private String content;


	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER )
	private Movie movie;

	@OneToOne( fetch = FetchType.EAGER)
	private User user;

	public Review() {
		super();
	}
	public Review(int review_id, String movie_reviews, double movie_ratings, String content, Movie movie, User user) {
		super();
		this.review_id = review_id;
		this.movie_reviews = movie_reviews;
		this.movie_ratings = movie_ratings;
		this.content = content;
		this.movie = movie;
		this.user = user;
	}
}