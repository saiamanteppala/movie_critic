package com.javapoint.serviceTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.javapoint.entities.Movie;
import com.javapoint.repository.MovieRepo;
import com.javapoint.service.MovieService;

@SpringBootTest
class MovieServiceTest {

	@Autowired
	private MovieService movieService;

	@MockBean
	private MovieRepo movieRepo;

	@Test
	public void getMovieTest() {
		when(movieRepo.findAll()).thenReturn((List<Movie>) 
				Stream.of(new Movie(1, "kanthara", "devotional", "Kannada,Telugu", "Thriller", 22 - 05 - 22, "02:45", "poster"))
				.collect(Collectors.toList()));
		assertEquals(1, movieService.getAllMovieDetails().size());

	}

	@Test
	public void saveMovieTest() {
		Movie movie = new Movie(1, "kanthara", "devotional", "Kannada,Telugu", "Thriller", 22 - 05 - 22, "02:45", "poster");
		when(movieRepo.save(movie)).thenReturn(movie);
		assertEquals(movie, movieService.saveMovieDetails(movie));
	}
	
	@Test
	public void getMovieByIdTest() {
		int id = 1;
		Movie movie = new Movie(1, "kanthara", "devotional", "Kannada,Telugu", "Thriller", 22 - 05 - 22, "02:45", "poster");
		when(movieRepo.findById(1)).thenReturn((movie));
		assertEquals(movie, movieService.getMovieDetailsById(id));
	}

	
	@Test
	public void deleteMovieTest() {
		Movie movie = new Movie(1, "kanthara", "devotional", "Kannada,Telugu", "Thriller", 22 - 05 - 22, "02:45", "poster");
		movieService.deleteMovieById(1);
		verify(movieRepo, times(0)).delete(movie);
	}


}
