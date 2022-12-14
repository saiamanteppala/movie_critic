package com.javapoint.entities;

import java.util.Set;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@Entity(name = "actor")
@Table
@Data
public class Actor {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "actor_id_sequence")
	@Column(name = "actor_id", updatable = false)
	private int actor_id;
	
	private String actor_name;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "actor", fetch = FetchType.EAGER)
	private Set<Movie> movie;

	public Actor() {
		super();
	}

	public Actor(int actor_id, String actor_name, Set<Movie> movie) 
	{
		super();
		this.actor_id = actor_id;
		this.actor_name = actor_name;
		this.movie = movie;
	}

}
