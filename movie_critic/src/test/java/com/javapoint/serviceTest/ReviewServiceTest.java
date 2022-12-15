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
import com.javapoint.entities.Review;
import com.javapoint.repository.ReviewRepo;
import com.javapoint.service.ReviewService;

@SpringBootTest 
class ReviewServiceTest {
	
	@Autowired 
  	private ReviewService reviewService;
	
	@MockBean 
  	private ReviewRepo reviewRepo;

	@Test
	void getReviewTest() {
		when(reviewRepo.findAll()).thenReturn((List<Review>) Stream.of(new Review(1, "good movie", 5.0, "superb movie"))
				.collect(Collectors.toList()));
		assertEquals(1,reviewService.getAllReviewDetails().size());		
	}
	
	@Test
	public void saveReviewTest() {
		Review review = new Review(1, "good movie", 5.0, "superb movie");
		when(reviewRepo.save(review)).thenReturn(review);
		assertEquals(review, reviewService.saveReviewDetails(review));
	}
	
	@Test
	public void getReviewByIdTest() {
		int id = 1;
		Review review = new Review(1, "good movie", 5.0, "superb movie");
		when(reviewRepo.findById(1)).thenReturn((review));
		assertEquals(review, reviewService.getReviewDetailsById(id));
	}
	
	
	@Test
	public void deleteReviewTest() {
		Review review = new Review(1, "good movie", 5.0, "superb movie");
		reviewService.deleteReviewById(1);
		verify(reviewRepo, times(0)).delete(review);
	}


}
