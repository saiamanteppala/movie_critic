package com.javapoint.controller;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;
import java.util.Base64;
import java.io.IOException;
import java.text.ParseException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

class UserControllerTest {
	private String responseBody;
	public String responseBodyPOST;
	final static Logger logger = Logger.getLogger(UserControllerTest.class);
	// RESTTemplate Object
	private RestTemplate restTemplate;
	// User ID
	private long userId;
	// Create Response Entity - Stores HTTPStatus Code, Response Body, etc
	private ResponseEntity<String> response;

	@BeforeTest
	public void beforeTest() throws IOException, ParseException {
		logger.info("Setting up prerequisite for test execution");
		logger.info("Creating RestTemplate object before tests");
		this.restTemplate = new RestTemplate();
	}

	@Test
	public void addUser() throws IOException, ParseException {
		String addURI = "http://localhost:8080/api/save/UserDetails";
		String name = "Amansai";
		String password = "Amansai10@";
		String authString = name + ":" + password;
		String authStringEnc = Base64.getEncoder().encodeToString(authString.getBytes());
		System.out.println("Base64 encoded auth string: " + authStringEnc);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", "application/json");
		headers.add("Content-Type", "application/json");
		headers.add("Authorization", "Basic " + authStringEnc);
		logger.info("Add URL :" + addURI);
		String jsonBody = "{ \"first_name\":\"Aman\",\n" + "\"last_name\":\"Sai\",\n" + "\"gender\":\"male\",\n"
				+ "\"age\":\"23\",\n" + "\"contact_number\":\"12345678\",\n" + "\"email\": \"amansaileo@gmail.com\",\n"
				+ "\"user_name\":\"Amansaileo\",\n" + "\"password\": \"123456789\"}";
		System.out.println("\n\n" + jsonBody);
		HttpEntity<String> entity = new HttpEntity<String>(jsonBody, headers);
		// POST Method to Add New user
		response = this.restTemplate.postForEntity(addURI, entity, String.class);
		responseBodyPOST = response.getBody();
		// Write response to file
		responseBody = response.getBody();
		System.out.println("responseBody;" + responseBody);
		// Get ID from the Response object
		userId = getUserIdFromResponse(responseBody);
		System.out.println("userId is :" + userId);
		// Check if the added User is present in the response body.
		Assert.assertTrue(userId > 0);
		// Check if the status code is 201
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		logger.info("user is Added successfully userId:" + userId);
	}

	public static long getUserIdFromResponse(String json) {
		JSONParser parser = new JSONParser();
		JSONObject jsonResponseObject = new JSONObject();
		try {
			jsonResponseObject = (JSONObject) parser.parse(json);
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
		long id = (Long) jsonResponseObject.get("user_id");
		logger.debug("User id: " + id);
		return id;
	}

	@Test(dependsOnMethods = "addUser", enabled = true)
	public void updateUser() throws IOException, ParseException {
		String updateURI = "http://localhost:8080/api/updateUserDetails";
		String name = "Amansai";
		String password = "Amansai10@";
		String authString = name + ":" + password;
		String authStringEnc = Base64.getEncoder().encodeToString(authString.getBytes());
		System.out.println("Base64 encoded auth string: " + authStringEnc);
		logger.info("Update URL :" + updateURI);
		responseBodyPOST = response.getBody();
		JSONParser parser = new JSONParser();
		JSONObject jsonResponseObject = new JSONObject();
		try {
			jsonResponseObject = (JSONObject) parser.parse(responseBodyPOST);
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
		jsonResponseObject.put("first_name", "AMAN");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", "application/json");
		headers.add("Content-Type", "application/json");
		headers.add("Authorization", "Basic " + authStringEnc);
		HttpEntity<String> entity = new HttpEntity<String>(jsonResponseObject.toJSONString(), headers);
		System.out.printf("entity", entity);
		// PUT Method to Update the existing user
		// NOTE that I have Not used restTemplate.put as it's void and we need response
		// for verification
		response = restTemplate.exchange(updateURI, HttpMethod.PUT, entity, String.class);
		logger.info(response);
		responseBody = response.getBody().toString();
		System.out.println("Update Response Body :" + responseBody);
		// Check if the updated user is present in the response body.
		// Check if the status code is 200
		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
		logger.info("User Name is Updated successfully userId:" + userId);
	}

	@Test(dependsOnMethods = "updateUser", enabled = true)
	void getUser() throws IOException, ParseException {
		String getURI = "http://localhost:8080/api/getUserDetailsById/" + this.userId;
		String name = "Amansai";
		String password = "Amansai10@";
		String authString = name + ":" + password;
		String authStringEnc = Base64.getEncoder().encodeToString(authString.getBytes());
		System.out.println("Base64 encoded auth string: " + authStringEnc);
		logger.info("Get URL :" + getURI);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", "application/json");
		headers.add("Content-Type", "application/json");
		headers.add("Authorization", "Basic " + authStringEnc);
		HttpEntity<String> request = new HttpEntity<String>(headers);
		ResponseEntity<String> response = restTemplate.exchange(getURI, HttpMethod.GET, request, String.class);
		responseBodyPOST = response.getBody();
		// GET Method to Get existing user
		// Write response to file
		responseBody = response.getBody().toString();
		System.out.println("GET Response Body :" + responseBody);
		// Check if the added User ID is present in the response body.
		// Check if the status code is 200
		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
		logger.info("User is retrieved successfully userId:" + userId);
	}

	@Test(dependsOnMethods = "getUser", enabled = true)
	public void deleteUser() throws IOException, ParseException {
		String delURI = "http://localhost:8080/api/deleteUserById/" + this.userId;

		String name = "Amansai";
		String password = "Amansai10@";
		String authString = name + ":" + password;
		String authStringEnc = Base64.getEncoder().encodeToString(authString.getBytes());
		System.out.println("Base64 encoded auth string: " + authStringEnc);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", "application/json");
		headers.add("Content-Type", "application/json");
		headers.add("Authorization", "Basic " + authStringEnc);
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		// DELETE Method to Delete existing user
		response = restTemplate.exchange(delURI, HttpMethod.DELETE, entity, String.class);
		// Check if the status code is 204
		System.out.println("response code-------" + response.getStatusCode());
		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	public static String getMessageFromResponse(String json) {
		String successMessageText = null;
		try {
			JSONParser parser = new JSONParser();
			JSONObject jsonResponseObject = new JSONObject();
			jsonResponseObject = (JSONObject) (parser.parse(json));
			String successMessage = jsonResponseObject.get("success").toString();

			jsonResponseObject = (JSONObject) (parser.parse(successMessage));
			successMessageText = jsonResponseObject.get("text").toString();
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
		return successMessageText;
	}

	@AfterTest
	public void afterTest() {
		logger.info("Clean up after test execution");
		logger.info("Creating RestTemplate object as Null");
		this.restTemplate = new RestTemplate();
	}

}
