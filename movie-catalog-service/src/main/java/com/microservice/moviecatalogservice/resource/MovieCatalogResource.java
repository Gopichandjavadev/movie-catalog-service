package com.microservice.moviecatalogservice.resource;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.microservice.moviecatalogservice.models.CatalogItem;
import com.microservice.moviecatalogservice.models.UserRating;
import com.microservice.moviecatalogservice.services.MovieInfoService;
import com.microservice.moviecatalogservice.services.UserRatingService;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {
	
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	WebClient.Builder builder;
	
	@Autowired
	MovieInfoService movieInfoService;
	
	@Autowired
	UserRatingService userRatingService;
	
	@RequestMapping("/{userId}")
	public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {
		
		
//		List<Rating> ratings = Arrays.asList(
//				new Rating("1234", 4),
//				new Rating("5678", 5));
		//Movie movie = builder.build().get().uri("http://localhost:8082/movies/test").retrieve().bodyToMono(Movie.class).block();
		
		UserRating userRatings = userRatingService.getUserRating(userId);
		
		return userRatings.getUserRating().stream().map(rating -> {
			return movieInfoService.getMovieInfo(rating);
		}).collect(Collectors.toList());
	}
}
