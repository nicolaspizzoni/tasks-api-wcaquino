package br.ce.nicopizzoni.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8001/tasks-backend";
	}
	
	@Test
	public void test() {
		RestAssured.given()
			.log().all()
		.when()
			.get("/todo")
		.then()
			.log().all()
		;
	}
	
	@Test
	public void deveAdicionarTodo() {
		RestAssured.given()
			.body("{\"task\": \"Teste de insercao api\", \"dueDate\": \"2040-10-12\"}")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.statusCode(201)
		;
	}
	
	@Test
	public void naoDeveAdicionarComParametroInvalido() {
		RestAssured.given()
			.body("{\"task\": \"Teste de insercao api\", \"dueDate\": \"2010-10-12\"}")
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.log().all()
			.statusCode(400)
			.body("message", CoreMatchers.is("Due date must not be in past"))
		;
	}
}
