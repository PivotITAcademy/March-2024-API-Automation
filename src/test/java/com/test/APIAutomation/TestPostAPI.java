package com.test.APIAutomation;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import utils.JsonUtils;

public class TestPostAPI {

	@Test
	public void testPostRequestWithhashMap() {
		
		Map<String,Object> requestPayload = new HashMap<>();
		requestPayload.put("firstname", "Ketaki");
		requestPayload.put("lastname", "Pillay");
		requestPayload.put("totalprice", 150);
		requestPayload.put("depositpaid", true);
		requestPayload.put("additionalneeds", "Breakfast");
		
		Map<String,Object> bookingdates = new HashMap<>();
		bookingdates.put("checkin", "2024-01-01");
		bookingdates.put("checkout", "2024-01-10");
		
		requestPayload.put("bookingdates", bookingdates);
		
		Response response = given().baseUri("https://restful-booker.herokuapp.com/").basePath("booking").header("Content-Type","application/json").header("Accept","application/json").
		body(requestPayload).
		log().all().
		when().post();
		
		int bookingId = response.jsonPath().getInt("bookingid");
		System.out.println("booking id : "+bookingId);
		
		String firstName = response.jsonPath().getString("booking.firstname");
		
		Assert.assertEquals(firstName, requestPayload.get("firstname"));
	}
	
	@Test
	public void testPostRequestWithExternalFile() {
		
		String filepath = "src/test/resources/data/bookingRequest.json";
		Map<String,Object> requestPayload=null;
		
		try {
			requestPayload = JsonUtils.getJsonDataFromFile(filepath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		requestPayload.put("firstname", "Shasha");
		
		Response response = given().baseUri("https://restful-booker.herokuapp.com/").basePath("booking").header("Content-Type","application/json").header("Accept","application/json").
		body(requestPayload).
		log().all().
		when().post();
		
		int bookingId = response.jsonPath().getInt("bookingid");
		System.out.println("booking id : "+bookingId);
		
		String firstName = response.jsonPath().getString("booking.firstname");
		
		Assert.assertEquals(firstName, requestPayload.get("firstname"));
	}
	
	@Test
	public void validateResponseSchema() {
		
		Map<String,Object> requestPayload = new HashMap<>();
		requestPayload.put("firstname", "Ketaki");
		requestPayload.put("lastname", "Pillay");
		requestPayload.put("totalprice", 150);
		requestPayload.put("depositpaid", true);
		requestPayload.put("additionalneeds", "Breakfast");
		
		Map<String,Object> bookingdates = new HashMap<>();
		bookingdates.put("checkin", "2024-01-01");
		bookingdates.put("checkout", "2024-01-10");
		
		requestPayload.put("bookingdates", bookingdates);
		
		File file = new File("src/test/resources/schemas/createBookingResponseSchema.json");
		
		given().baseUri("https://restful-booker.herokuapp.com/").basePath("booking").header("Content-Type","application/json").header("Accept","application/json").
		body(requestPayload).
		log().all().
		when().post().then().log().all().body(JsonSchemaValidator.matchesJsonSchema(file));
	}
}
