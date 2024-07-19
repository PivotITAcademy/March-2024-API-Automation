package com.test.APIAutomation;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.startsWith;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class RestFulAPITest {

	
	
	@Test
	public void testGetBooking() {
		//baseURI = " https://restful-booker.herokuapp.com";
		
		given().baseUri("https://restful-booker.herokuapp.com/").basePath("booking").log().all().
		when().get().
		then().log().all().statusCode(200).body("[0].bookingid", equalTo(47));
		
	}
	
	@Test
	public void testGetBookingwithQueryParam() {
		given().baseUri("https://restful-booker.herokuapp.com/").basePath("booking").param("firstName", "John").param("lastname", "Smith").
		log().all().
		when().get().
		then().log().all().statusCode(200);
	}
	
	@Test
	public void testGetBookingDetailforBookingId() {
		given().baseUri("https://restful-booker.herokuapp.com/").basePath("booking/{bookingid}").pathParam("bookingid", 556).
		log().all().
		when().get().
		then().statusCode(200).body("lastname", equalTo("Smith")).body("firstname", startsWith("K")).body("totalprice", greaterThan(100))
		.body("bookingdates.checkin", equalTo("2018-01-01"));
	}
	
	@Test
	public void assertGetBookingIDDetails() {
		
		Response response = given().baseUri("https://restful-booker.herokuapp.com/").basePath("booking/{bookingid}").pathParam("bookingid", 556).
							log().all().when().get();
		
		System.out.println(response.asPrettyString());
		
		String fname = response.jsonPath().getString("firstname");
		
		Assert.assertEquals(fname, "John");
	}
	
	@Test
	public void testCreateBooking() {
		
		String request = "{\r\n"
				+ "        \"firstname\": \"Ketaki\",\r\n"
				+ "        \"lastname\": \"Pillay\",\r\n"
				+ "        \"totalprice\": 111,\r\n"
				+ "        \"depositpaid\": true,\r\n"
				+ "        \"bookingdates\": {\r\n"
				+ "            \"checkin\": \"2024-07-16\",\r\n"
				+ "            \"checkout\": \"2024-07-19\"\r\n"
				+ "        },\r\n"
				+ "        \"additionalneeds\": \"Breakfast\"\r\n"
				+ " }";
				
		given().baseUri("https://restful-booker.herokuapp.com/").basePath("booking").header("Content-Type","application/json").header("Accept","application/json").
		body(request).
		log().all().
		when().post().
		then().log().all().statusCode(200).body("booking.firstname",equalTo( "Ketaki"));
	}
}
