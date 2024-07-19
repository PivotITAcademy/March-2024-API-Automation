package com.test.APIAutomation;
import static io.restassured.RestAssured.*;

import java.util.Base64;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import utils.PropertyUtil;

public class SpotifyApiTest {

	String accessToken = null;
	
	@BeforeClass
	public  String generateToken() {
		
		
		Response response = given().baseUri("https://accounts.spotify.com/api/token").header("Content-Type","application/x-www-form-urlencoded").
		header("Authorization","Basic "+generateEncodedCredentials()).formParam("grant_type", "client_credentials").log().all().when().post();
		
		System.out.println(response.asPrettyString());
		
		accessToken = response.jsonPath().getString("access_token");
		
		return accessToken;
		
	}

	
	public static String generateEncodedCredentials() {
		
		String creds = PropertyUtil.getProperty("src/test/resources/config.properties", "client_id") +":"+PropertyUtil.getProperty("src/test/resources/config.properties", "client_secret");
		
		System.out.println("Non encoded creds : "+creds);
		
		creds = Base64.getEncoder().encodeToString(creds.getBytes());
		
		System.out.println("Encoded creds : "+creds);
		
		return creds;
	}
	
	@Test
	public void testSpotifyAPi() {
		
		
		
		given().baseUri("https://api.spotify.com").basePath("v1/albums/{id}").pathParam("id", "4aawyAB9vmqN3uQ7FjRGTy").
		header("Authorization","Bearer "+accessToken).log().all().when().get().then().log().all().statusCode(200);
	}
}
