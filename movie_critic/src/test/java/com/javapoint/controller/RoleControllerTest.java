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

public class RoleControllerTest {

	private String responseBody;
	public String responseBodyPOST;
	final static Logger logger = Logger.getLogger(RoleControllerTest.class);
	// RESTTemplate Object
	private RestTemplate restTemplate;
	// Role ID
	private long roleId;
	// Create Response Entity - Stores HTTPStatus Code, Response Body, etc
	private ResponseEntity<String> response;
	@BeforeTest
	public void beforeTest() throws IOException, ParseException {
		logger.info("Setting up prerequisite for test execution");
		logger.info("Creating RestTemplate object before tests");
		this.restTemplate = new RestTemplate();
	}

	@Test
	public void addRole() throws IOException, ParseException {
		String addURI = "http://localhost:8080/api/save/RoleDetails";
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
		String jsonBody = "{ \"role_name\":\"ADMIN\" }";
		System.out.println("\n\n" + jsonBody);
		HttpEntity<String> entity = new HttpEntity<String>(jsonBody, headers);
		// POST Method to Add New Role
		response = this.restTemplate.postForEntity(addURI, entity, String.class);
		responseBodyPOST = response.getBody();
		// Write response to file
		responseBody = response.getBody().toString();
		System.out.println("responseBody;" + responseBody);
		// Get ID from the Response object
		roleId = getRoleIdFromResponse(responseBody);
		System.out.println("userId is :" + roleId);
		// Check if the added Role is present in the response body.
		Assert.assertTrue(roleId > 0);
		// Check if the status code is 201
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		logger.info("user is Added successfully userId:" + roleId);
	}

	public static long getRoleIdFromResponse(String json) 
	{
		JSONParser parser = new JSONParser();
		JSONObject jsonResponseObject = new JSONObject();
		try {
			jsonResponseObject = (JSONObject) parser.parse(json);
		} catch (org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
		long id = (Long) jsonResponseObject.get("role_id");
		logger.debug("Role id: " + id);
		return id;
	}

	@Test(dependsOnMethods = "addRole", enabled = true)
	public void updateRole() throws IOException, ParseException {
		String updateURI = "http://localhost:8080/api/updateRoleDetails";

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
		jsonResponseObject.put("role_name", "USER");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Accept", "application/json");
		headers.add("Content-Type", "application/json");
		headers.add("Authorization", "Basic " + authStringEnc);
		HttpEntity<String> entity = new HttpEntity<String>(jsonResponseObject.toJSONString(), headers);
		System.out.printf("entity", entity);
		// PUT Method to Update the existing role
		// NOTE that I have Not used restTemplate.put as it's void and we need response
		// for verification
		response = restTemplate.exchange(updateURI, HttpMethod.PUT, entity, String.class);
		logger.info(response);
		responseBody = response.getBody().toString();
		System.out.println("Update Response Body :" + responseBody);
		// Check if the updated Role is present in the response body.
		// Check if the status code is 200
		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
		logger.info("Role Name is Updated successfully roleId:" + roleId);
	}

	@Test(dependsOnMethods = "updateRole", enabled = true)
	void getRole() throws IOException, ParseException {
		String getURI = "http://localhost:8080/api/getRoleDetailsById/" + this.roleId;
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
		// GET Method to Get existing role
		// Write response to file
		responseBody = response.getBody().toString();
		System.out.println("GET Response Body :" + responseBody);
		// Check if the added Role ID is present in the response body.
		// Check if the status code is 200
		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
		logger.info("Role is retrieved successfully userId:" + roleId);
	}

	@Test(dependsOnMethods = "getRole", enabled = true)
	public void deleteRole() throws IOException, ParseException {
		String delURI = "http://localhost:8080/api/deleteRoleById/" + this.roleId;
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
		// DELETE Method to Delete existing role
		response = restTemplate.exchange(delURI, HttpMethod.DELETE, entity, String.class);
		// Check if the status code is 204
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
