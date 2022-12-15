package com.javapoint.entities;

import java.time.LocalTime;
import java.util.*;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

@Entity(name = "movie")
@Data
public class Movie {

	@Id

	@GeneratedValue(strategy = GenerationType.AUTO)
	private int movie_id;

	private String movie_name;

	private String movie_categories;

	private String movie_language;

	private String movie_genres;

	private Date release_date;

	private LocalTime movie_duration;

	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	private String movie_poster;

	@JsonManagedReference
	@OneToMany(mappedBy = "movie", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	private List<Review> review;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "movie_actor", joinColumns = { @JoinColumn(name = "movie_id") }, inverseJoinColumns = {
			@JoinColumn(name = "actor_id") })
	private Set<Actor> actor;

	public Movie() {
		super();
	}

	public Movie(int movie_id, String movie_name, String movie_categories, String movie_language, String movie_genres,
			Date release_date, LocalTime movie_duration, String movie_poster, List<Review> review, Set<Actor> actor) {
		super();
		this.movie_id = movie_id;
		this.movie_name = movie_name;
		this.movie_categories = movie_categories;
		this.movie_language = movie_language;
		this.movie_genres = movie_genres;
		this.release_date = release_date;
		this.movie_duration = movie_duration;
		this.movie_poster = movie_poster;
		this.review = review;
		this.actor = actor;
	}

	public Movie(int i, String string, String string2, String string3, String string4, int j, String string5,
			String string6) {
	}

}
