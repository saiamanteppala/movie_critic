package com.javapoint.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.text.ParseException;
import java.util.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

public class ActorControllerTest {
	private String responseBody;
	public String responseBodyPOST;
	final static Logger logger = Logger.getLogger(ActorControllerTest.class);
	// RESTTemplate Object
	private RestTemplate restTemplate;
	// Actor ID
	private long actorId;
	// Create Response Entity - Stores HTTPStatus Code, Response Body, etc
	private ResponseEntity<String> response;

	@BeforeTest
	public void beforeTest() throws IOException, ParseException {
		logger.info("Setting up prerequisite for test execution");
		logger.info("Creating RestTemplate object before tests");
		this.restTemplate = new RestTemplate();
	}

	@Test
	public void addActor() throws IOException, ParseException {
		String addURI = "http://localhost:8080/api/save/ActorDetails";
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
		String jsonBody = "{ \"actor_name\":\"Balayya\" }";
		System.out.println("\n\n" + jsonBody);
		HttpEntity<String> entity = new HttpEntity<String>(jsonBody, headers);
		// POST Method to Add New Actor
		response = this.restTemplate.postForEntity(addURI, entity, String.class);
		responseBodyPOST = response.getBody();
		// Write response to file
		responseBody = response.getBody().toString();
		System.out.println("responseBody;" + responseBody);
		// Get ID from the Response object
		actorId = getActorIdFromResponse(responseBody);
		System.out.println("actorId is :" + actorId);
		// Check if the added Actor is present in the response body.
		Assert.assertTrue(actorId > 0);
		// Check if the status code is 201
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		logger.info("Actor is Added successfully userId:" + actorId);
	}
	
	public static long getActorIdFromResponse(String json) {
		JSONParser parser = new JSONParser();
		JSONObject jsonResponseObject = new JSONObject();
		try {
			jsonResponseObject = (JSONObject) parser.parse(json);
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
		long id = (Long) jsonResponseObject.get("actor_id");
		logger.debug("Actor id: " + id);
		return id;
	}

	@Test(dependsOnMethods = "addActor", enabled = true)
	public void updateActor() throws IOException, ParseException {
		String updateURI = "http://localhost:8080/api/updateActorDetails";
		String name = "Amansai";
		String password = "Amansai10@";
		String authString = name + ":" + password;
		String authStringEnc = Base64.getEncoder().encodeToString(authString.getBytes());
		System.out.println("Base64 encoded auth string: " + authStringEnc);
		logger.info("Update URL :" + updateURI);

		responseBodyPOST = response.getBody();

		JSONParser parser = new JSONParser();
		JSONObject jsonResponseObject = new JSONObject();
		// Object obj = new Object();
		try {
			jsonResponseObject = (JSONObject) parser.parse(responseBodyPOST);
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
		// jsonResponseObject = (JSONObject) obj;
		jsonResponseObject.put("actor_name", "Balayya");
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", "application/json");
		headers.add("Content-Type", "application/json");
		headers.add("Authorization", "Basic " + authStringEnc);
		HttpEntity<String> entity = new HttpEntity<String>(jsonResponseObject.toJSONString(), headers);
		System.out.printf("entity", entity);
		// PUT Method to Update the existing Actor
		// NOTE that I have Not used restTemplate.put as it's void and we need response
		// for verification
		response = restTemplate.exchange(updateURI, HttpMethod.PUT, entity, String.class);
		logger.info(response);
		responseBody = response.getBody().toString();
		System.out.println("Update Response Body :" + responseBody);
		// Check if the updated actor is present in the response body.	
		// Check if the status code is 200
		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
		logger.info("User Name is Updated successfully actorId:" + actorId);
	}

	@Test(dependsOnMethods = "updateActor", enabled = true)
	void getActor() throws IOException, ParseException 
	{
		String getURI = "http://localhost:8080/api/getActorDetailsById/" + this.actorId;
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
		// GET Method to Get existing Actor
		// Write response to file
		responseBody = response.getBody().toString();
		System.out.println("GET Response Body :" + responseBody);
		// Check if the added Actor ID is present in the response body.
		// Check if the status code is 200
		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);

		logger.info("Actor is retrieved successfully actorId:" + actorId);
	}
	
	@Test(dependsOnMethods = "getActor", enabled = true)
	public void deleteActor() throws IOException, ParseException 
	{
		String delURI = "http://localhost:8080/api/deleteActorById/" + this.actorId;
		String name = "Amansai";
		String password = "Amansai10@";
		String authString = name + ":" + password;
		String authStringEnc = Base64.getEncoder().encodeToString(authString.getBytes());
		System.out.println("Base64 encoded auth string: " + authStringEnc);
		System.out.printf("deleteURI----", delURI);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", "application/json");
		headers.add("Content-Type", "application/json");
		headers.add("Authorization", "Basic " + authStringEnc);
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		//DELETE Method to Delete existing Actor
		response = restTemplate.exchange(delURI, HttpMethod.DELETE, entity, String.class);
		//Check if the status code is 204
		System.out.println("response code-------" + response.getStatusCode());
		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	public static String getMessageFromResponse(String json)
	{
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
