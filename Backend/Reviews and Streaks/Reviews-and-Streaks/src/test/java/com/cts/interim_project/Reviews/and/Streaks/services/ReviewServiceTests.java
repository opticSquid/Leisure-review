package com.cts.interim_project.Reviews.and.Streaks.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.cts.interim_project.Reviews.and.Streaks.Exceptions.ProviderNotFoundException;
import com.cts.interim_project.Reviews.and.Streaks.Exceptions.UserNotValidException;
import com.cts.interim_project.Reviews.and.Streaks.config.Role;
import com.cts.interim_project.Reviews.and.Streaks.config.ValidateRequest;
import com.cts.interim_project.Reviews.and.Streaks.config.ValidateResponse;
import com.cts.interim_project.Reviews.and.Streaks.entities.Review;
import com.cts.interim_project.Reviews.and.Streaks.entities.ReviewPOJO;
import com.cts.interim_project.Reviews.and.Streaks.repository.ReviewRepo;

@SpringBootTest
public class ReviewServiceTests {
	@Mock
	private ReviewRepo reviewRepo;
	@Mock
	private RestTemplate restTemplate;
	@InjectMocks
	private ReviewService reviewService;

	@Test
	public void addReviewTest_providing_valid_response() {
		// Configure
		String token = "Bearer jjvjnvov-jsssjvsv-svvjvkjnv";
		ReviewPOJO review = new ReviewPOJO("jvjvjnvej-jsvv-chcbkjc", "very good hotel", 5);
		ResponseEntity<ValidateResponse> response = new ResponseEntity<>(
				new ValidateResponse("fwbvbv-nss-jsssv", Role.USER), HttpStatus.OK);
		Review newReview = new Review(review.getServiceProviderId(), "fwbvbv-nss-jsssv", review.getReview(),
				review.getRating());
		Review savedReview = new Review("jbvjvovjv-jsjvjsvsjnv-jsv", review.getServiceProviderId(), "fwbvbv-nss-jsssv",
				review.getReview(), review.getRating());
		// Mocking external calls
		when(restTemplate.postForEntity("http://USERS/users/auth/validate", new ValidateRequest(token.substring(7)),
				ValidateResponse.class)).thenReturn(response);
		when(restTemplate.getForObject("http://VENDORS/vendors/check/" + review.getServiceProviderId(), Boolean.class))
				.thenReturn(true);
		when(reviewRepo.save(newReview)).thenReturn(savedReview);
		// Testing
		assertEquals(reviewService.addReview(token, review), savedReview.getReviewId());
	}

	@Test
	public void addReviewTest_providing_invalid_user() {
		// Configure
		String token = "Bearer jjvjnvov-jsssjvsv-svvjvkjnv";
		ReviewPOJO review = new ReviewPOJO("jvjvjnvej-jsvv-chcbkjc", "very good hotel", 5);
		ResponseEntity<ValidateResponse> response = new ResponseEntity<>(new ValidateResponse(null, null),
				HttpStatus.FORBIDDEN);
		Review newReview = new Review(review.getServiceProviderId(), "fwbvbv-nss-jsssv", review.getReview(),
				review.getRating());
		Review savedReview = new Review("jbvjvovjv-jsjvjsvsjnv-jsv", review.getServiceProviderId(), "fwbvbv-nss-jsssv",
				review.getReview(), review.getRating());
		// Mocking external calls
		when(restTemplate.postForEntity("http://USERS/users/auth/validate", new ValidateRequest(token.substring(7)),
				ValidateResponse.class)).thenReturn(response);
		when(restTemplate.getForObject("http://VENDORS/vendors/check/" + review.getServiceProviderId(), Boolean.class))
				.thenReturn(true);
		when(reviewRepo.save(newReview)).thenReturn(savedReview);
		// Testing
		Exception exception = assertThrows(UserNotValidException.class, () -> reviewService.addReview(token, review));
		String excepMsg = "user is not valid";
		String actualMsg = exception.getMessage();
		assertTrue(actualMsg.equals(excepMsg));
	}

	@Test
	public void addReviewTest_providing_invalid_vendor() {
		// Configure
		String token = "Bearer jjvjnvov-jsssjvsv-svvjvkjnv";
		ReviewPOJO review = new ReviewPOJO("jvjvjnvej-jsvv-chcbkjc", "very good hotel", 5);
		ResponseEntity<ValidateResponse> response = new ResponseEntity<>(
				new ValidateResponse("fwbvbv-nss-jsssv", Role.USER), HttpStatus.OK);
		Review newReview = new Review(review.getServiceProviderId(), "fwbvbv-nss-jsssv", review.getReview(),
				review.getRating());
		Review savedReview = new Review("jbvjvovjv-jsjvjsvsjnv-jsv", review.getServiceProviderId(), "fwbvbv-nss-jsssv",
				review.getReview(), review.getRating());
		// Mocking external calls
		when(restTemplate.postForEntity("http://USERS/users/auth/validate", new ValidateRequest(token.substring(7)),
				ValidateResponse.class)).thenReturn(response);
		when(restTemplate.getForObject("http://VENDORS/vendors/check/" + review.getServiceProviderId(), Boolean.class))
				.thenReturn(false);
		when(reviewRepo.save(newReview)).thenReturn(savedReview);
		// Testing
		Exception exception = assertThrows(ProviderNotFoundException.class,
				() -> reviewService.addReview(token, review));
		String excepMsg = "No vendor with given id is found";
		String actualMsg = exception.getMessage();
		assertTrue(actualMsg.equals(excepMsg));
	}

	@Test
	public void getAllReviewsOfServiceProviderTest_valid_providerID() {
		// Configure test
		String providerID = "ijvwjnvw-jvjvjnv-jnsvjsnv";
		List<Review> allReviews = new LinkedList<>();
		allReviews.add(new Review(providerID, "snsnvss-najaj-abcjac", "very bad hotel", 2));
		allReviews.add(new Review(providerID, "wbfiwb-caccbc-habcac", "average hotel", 3));
		allReviews.add(new Review(providerID, "nnwjnw-kshb-bbcjwefjwn", "good hotel", 4));
		// mock call
		when(reviewRepo.findAllReviewsOfServiceProvider(providerID))
				.thenReturn(allReviews.stream().filter(review -> review.getServiceProviderId() == providerID).toList());
		// test call
		assertEquals(reviewService.getAllReviewsOfServiceProvider(providerID), allReviews);
	}

	@Test
	public void getAllReviewsOfServiceProviderTest_invalid_providerID() {
		// Configure test
		String providerID = "ijvwjnvw-jvjvjnv-jnsvjsnv";
		List<Review> allReviews = new LinkedList<>();
		allReviews.add(new Review("bvsjvnsjvn-scjsn", "snsnvss-najaj-abcjac", "very bad hotel", 2));
		allReviews.add(new Review("anjnjsnvsvsv", "wbfiwb-caccbc-habcac", "average hotel", 3));
		allReviews.add(new Review("ajsjnsjc-jjjnvv", "nnwjnw-kshb-bbcjwefjwn", "good hotel", 4));
		// mock call
		when(reviewRepo.findAllReviewsOfServiceProvider(providerID))
				.thenReturn(allReviews.stream().filter(review -> review.getServiceProviderId() == providerID).toList());
		// test call
		assertTrue(reviewService.getAllReviewsOfServiceProvider(providerID).isEmpty());
	}

	@Test
	public void getAllReviewsOfUserTest_validToken() {
		// Configure test
		String token = "Bearer jjvjnvov-jsssjvsv-svvjvkjnv";
		String userID = "ijvwjnvw-jvjvjnv-jnsvjsnv";
		List<Review> allReviews = new LinkedList<>();
		allReviews.add(new Review("snnvsv-cjjjs-jsn", "snsnvss-najaj-abcjac", userID, "very bad hotel", 2));
		allReviews.add(new Review("cjnck-jjksln-jnj", "wbfiwb-caccbc-habcac", userID, "average hotel", 3));
		allReviews.add(new Review("jnjanknc-jvsnjc", "nnwjnw-kshb-bbcjwefjwn", userID, "good hotel", 4));
		ResponseEntity<ValidateResponse> response = new ResponseEntity<>(new ValidateResponse(userID, Role.USER),
				HttpStatus.OK);
		// mock call
		when(restTemplate.postForEntity("http://USERS/users/auth/validate", new ValidateRequest(token.substring(7)),
				ValidateResponse.class)).thenReturn(response);
		when(reviewRepo.findAllReviewsOfUser(userID))
				.thenReturn(allReviews.stream().filter(review -> review.getUserId() == userID).toList());
		// test call
		assertEquals(reviewService.getAllReviewsOfUser(token), allReviews);
	}

	@Test
	public void getAllReviewsOfUserTest_userDidNotReviewAnyting() {
		// Configure test
		String token = "Bearer jjvjnvov-jsssjvsv-svvjvkjnv";
		String userID = "ijvwjnvw-jvjvjnv-jnsvjsnv";
		List<Review> allReviews = new LinkedList<>();
		allReviews.add(new Review("snsnvss-najaj-abcjac", "fjnvovna-abavavv", "very bad hotel", 2));
		allReviews.add(new Review("wbfiwb-caccbc-habcac", "cahcbajaj-aaakv-ajv", "average hotel", 3));
		allReviews.add(new Review("nnwjnw-kshb-bbcjwefjwn", "sjvjanvjanvav-cjjv-back", "good hotel", 4));
		ResponseEntity<ValidateResponse> response = new ResponseEntity<>(new ValidateResponse(userID, Role.USER),
				HttpStatus.OK);
		// mock call
		when(restTemplate.postForEntity("http://USERS/users/auth/validate", new ValidateRequest(token.substring(7)),
				ValidateResponse.class)).thenReturn(response);
		when(reviewRepo.findAllReviewsOfUser(userID))
				.thenReturn(allReviews.stream().filter(review -> review.getUserId() == userID).toList());
		// test call
		assertTrue(reviewService.getAllReviewsOfUser(token).isEmpty());
	}

	@Test
	public void getAllReviewsOfUserTest_invalidUser() {
		// Configure test
		String token = "Bearer jjvjnvov-jsssjvsv-svvjvkjnv";
		String userID = "ijvwjnvw-jvjvjnv-jnsvjsnv";
		List<Review> allReviews = new LinkedList<>();
		allReviews.add(new Review("snsnvss-najaj-abcjac", "fjnvovna-abavavv", "very bad hotel", 2));
		allReviews.add(new Review("wbfiwb-caccbc-habcac", "cahcbajaj-aaakv-ajv", "average hotel", 3));
		allReviews.add(new Review("nnwjnw-kshb-bbcjwefjwn", "sjvjanvjanvav-cjjv-back", "good hotel", 4));
		ResponseEntity<ValidateResponse> response = new ResponseEntity<>(new ValidateResponse(null, Role.SERVICE_OWNER),
				HttpStatus.UNAUTHORIZED);
		// mock call
		when(restTemplate.postForEntity("http://USERS/users/auth/validate", new ValidateRequest(token.substring(7)),
				ValidateResponse.class)).thenReturn(response);
		when(reviewRepo.findAllReviewsOfUser(userID))
				.thenReturn(allReviews.stream().filter(review -> review.getUserId() == userID).toList());
		// test call
		Exception excep = assertThrows(UserNotValidException.class, () -> reviewService.getAllReviewsOfUser(token));
		assertTrue(excep.getMessage().equals("user is not valid"));
	}

	@Test
	public void editReview_validValues() {
		// configure test
		String token = "Bearer jjvjnvov-jsssjvsv-svvjvkjnv";
		String userID = "ijvwjnvw-jvjvjnv-jnsvjsnv";
		Review savedReview = new Review("jbvjvovjv-jsjvjsvsjnv-jsv", "jvjvjnvej-jsvv-chcbkjc", userID,
				"very good hotel", 5);
		Review updatedReview = new Review("jbvjvovjv-jsjvjsvsjnv-jsv", "jvjvjnvej-jsvv-chcbkjc", userID,
				"average hotel", 3);
		ResponseEntity<ValidateResponse> response = new ResponseEntity<>(new ValidateResponse(userID, Role.USER),
				HttpStatus.OK);
		// mock call
		when(restTemplate.postForEntity("http://USERS/users/auth/validate", new ValidateRequest(token.substring(7)),
				ValidateResponse.class)).thenReturn(response);
		when(restTemplate.getForObject("http://VENDORS/vendors/check/" + savedReview.getServiceProviderId(),
				Boolean.class)).thenReturn(true);
		when(reviewRepo.findById(savedReview.getReviewId())).thenReturn(Optional.of(savedReview));
		when(reviewRepo.save(updatedReview)).thenReturn(updatedReview);
		// test call
		assertEquals(reviewService.editReview(token, savedReview), updatedReview.getReviewId());
	}

	@Test
	public void editReview_invalidUser() {
		// configure test
		String token = "Bearer jjvjnvov-jsssjvsv-svvjvkjnv";
		String userID = "ijvwjnvw-jvjvjnv-jnsvjsnv";
		Review savedReview = new Review("jbvjvovjv-jsjvjsvsjnv-jsv", "jvjvjnvej-jsvv-chcbkjc", userID,
				"very good hotel", 5);
		Review updatedReview = new Review("jbvjvovjv-jsjvjsvsjnv-jsv", "jvjvjnvej-jsvv-chcbkjc", userID,
				"average hotel", 3);
		ResponseEntity<ValidateResponse> response = new ResponseEntity<>(
				new ValidateResponse("bcbsdnj-dsshabcac", Role.USER), HttpStatus.OK);
		// mock call
		when(restTemplate.postForEntity("http://USERS/users/auth/validate", new ValidateRequest(token.substring(7)),
				ValidateResponse.class)).thenReturn(response);
		when(restTemplate.getForObject("http://VENDORS/vendors/check/" + savedReview.getServiceProviderId(),
				Boolean.class)).thenReturn(true);
		when(reviewRepo.findById(savedReview.getReviewId())).thenReturn(Optional.of(savedReview));
		when(reviewRepo.save(updatedReview)).thenReturn(updatedReview);
		// test call
		Exception ex = assertThrows(ProviderNotFoundException.class,
				() -> reviewService.editReview(token, updatedReview));
		assertTrue(ex.getMessage().equals("service provider with gived id is not found or user is invalid"));
	}

	@Test
	public void deleteReviewTest_InvalidUser() {
		// Test configuration
		String token = "Bearer jjvjnvov-jsssjvsv-svvjvkjnv";
		String userID = "ijvwjnvw-jvjvjnv-jnsvjsnv";
		String reviewID = "jbvjvovjv-jsjvjsvsjnv-jsv";
		Review savedReview = new Review(reviewID, "jvjvjnvej-jsvv-chcbkjc", "qdyqdduuvcu", "very good hotel", 5);
		ResponseEntity<ValidateResponse> response = new ResponseEntity<>(new ValidateResponse(userID, Role.USER),
				HttpStatus.OK);
		// mock call
		when(restTemplate.postForEntity("http://USERS/users/auth/validate", new ValidateRequest(token.substring(7)),
				ValidateResponse.class)).thenReturn(response);
		when(reviewRepo.findById(savedReview.getReviewId())).thenReturn(Optional.of(savedReview));
		// test call
		Exception ex = assertThrows(UserNotValidException.class, () -> reviewService.deleteReview(token, reviewID));
	}
}
