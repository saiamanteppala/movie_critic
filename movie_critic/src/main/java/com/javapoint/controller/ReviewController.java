package com.javapoint.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.javapoint.entities.Review;
import com.javapoint.repository.UserRepo;
import com.javapoint.service.MovieService;
import com.javapoint.service.ReviewService;
@CrossOrigin(origins ="*", maxAge = 3600)
@RestController
@RequestMapping("/api")

public class ReviewController {
	@Autowired
	ReviewService reviewService;
	
	 @Autowired
     private MovieService movieService;

     @Autowired
     private UserRepo userRepo;
		
	@PostMapping("/save/ReviewDetails/{movie_id}/{user_id}")
	 public Review addNewReview(@PathVariable(value = "movie_id") int movie_id,
             @PathVariable(value = "user_id") int user_id, 
             @RequestBody Review review) {
     try {
             review.setMovie(movieService.getMovieDetailsById(movie_id));
             review.setUser(userRepo.findById(user_id));
             reviewService.saveReviewDetails(review);
     } catch (Exception e) {
             e.printStackTrace();
     }
     return review ;
}
	
	@GetMapping("/getAllReviewDetails")
	public List<Review>getAllReviewDetails()
	{
		List<Review> revDetails=reviewService.getAllReviewDetails();
		return revDetails;
	}
	@GetMapping("/getReviewDetailsById/{id}")
	public Review getReviewDetailsById(@PathVariable int id)
	{
		Review revDetails=reviewService.getReviewDetailsById(id);
		return revDetails;
	}
	@DeleteMapping("/deleteReviewById/{id}")
	public void deleteReviewById(@PathVariable int id)
	{
		reviewService.deleteReviewById(id);
	}
	@PutMapping("/updateReviewDetails")
	public Review updateReviewDetails(@RequestBody Review review)
	{
		return reviewService.updateReviewDetails(review);
	}
}


