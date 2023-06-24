package br.edu.univas.si7.topicos.tests.accounting;

import static io.restassured.RestAssured.given;

import org.apache.http.HttpStatus;
import org.junit.Test;

import br.edu.univas.si7.topicos.tests.accounting.dto.AccountingDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class AccountingTest {

	private final String baseURL = "http://localhost:8080/accountings";

	@Test
	public void testCreateAccounting_withSuccess() {
		AccountingDTO accounting = new AccountingDTO();
		accounting.setType("Venda");
		accounting.setDescription("Venda de produtos de limpeza");
		accounting.setAccValue(800.0f);
		accounting.setProfit(200.0f);
		accounting.setDateTime("2023-04-14 08:00:00");

		given().body(accounting).contentType(ContentType.JSON).post(baseURL).then().statusCode(HttpStatus.SC_CREATED);
	}

	@Test
	public void testCreateAccounting_withInvalidData() {
		AccountingDTO accounting = new AccountingDTO();

		given().body(accounting).contentType(ContentType.JSON).post(baseURL).then()
				.statusCode(HttpStatus.SC_BAD_REQUEST);
	}

	@Test
	public void testCreateAccounting_withDuplicateItemCode() {
		AccountingDTO accounting = new AccountingDTO();
		accounting.setItemCode(1L);
		accounting.setType("Venda");
		accounting.setDescription("Venda de produtos de limpeza");
		accounting.setAccValue(800.0f);
		accounting.setProfit(200.0f);
		accounting.setDateTime("2023-04-14 08:00:00");

		given().body(accounting).contentType(ContentType.JSON).post(baseURL).then().statusCode(HttpStatus.SC_CREATED);

		given().body(accounting).contentType(ContentType.JSON).post(baseURL).then()
				.statusCode(HttpStatus.SC_BAD_REQUEST);
	}

	
	@Test
	public void testGetAccountingById_nonExistingAccounting() {
		Long nonExisting = 999L;
		Response resp = RestAssured.get(baseURL + "/" + nonExisting);
		resp.then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
	}
	
	@Test
	public void testGetAccountingById_existingAccounting() {
		Long nonExisting = 1L;
		Response resp = RestAssured.get(baseURL + "/" + nonExisting);
		resp.then().assertThat().statusCode(HttpStatus.SC_OK);
	}
	
	@Test
	public void testActivateAccounting_success() {
	    Long accountingId = 1L;

	    // Activate the accounting
	    Response activateResponse = RestAssured.patch(baseURL + "/active/" + accountingId);
	    activateResponse.then().assertThat().statusCode(HttpStatus.SC_NO_CONTENT);
	}

	@Test
	public void testActivateAccounting_nonExistingAccounting() {
	    Long nonExisting = 999L;
	    Response resp = RestAssured.patch(baseURL + "/active/" + nonExisting);
	    resp.then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
	}

}

//public class AccountingTest {
//
//	private ObjectMapper mapper = new ObjectMapper();
//	private final String orderURL = "http://localhost:8080/accountings";
//
//	@Test
//	public void testCreateOrder_withSuccess() {
//		Long orderNumber = 1001L;
//		Response resp = createOrderWithId(orderNumber);
//		resp.then().assertThat().statusCode(HttpStatus.SC_CREATED);
//		
//		deleteOrderWithNumber(orderNumber);
//	}
//
//	@Test
//	public void testCreateOrder_withEmptyBody() {
//		RestAssured.given().contentType(ContentType.JSON)
//			.post(orderURL).then()
//			.assertThat().statusCode(HttpStatus.SC_BAD_REQUEST);
//	}
//	
//	@Test
//	public void testCreateOrder_withMissingItems() {
//		Long orderNumber = 1001L;
//		OrderNewDTO order = new OrderNewDTO(orderNumber, "S1", "s1@email.com", "1", null);
//		RestAssured.given().body(order).contentType(ContentType.JSON)
//			.post(orderURL).then()
//			.assertThat().statusCode(HttpStatus.SC_BAD_REQUEST);
//	}
//	
//	@Test
//	public void testCreateOrder_withMissingNumber() {
//		OrderNewDTO order = new OrderNewDTO(null, "S1", "s1@email.com", "1", Arrays.asList(new ItemNewDTO(2, 5)));
//		RestAssured.given().body(order).contentType(ContentType.JSON)
//			.post(orderURL).then()
//			.assertThat().statusCode(HttpStatus.SC_BAD_REQUEST);
//	}
//	
//	@Test
//	public void testGetOrderById_withSuccess() throws JsonMappingException, JsonProcessingException {
//		Long orderNumber = 1001L;
//		createOrderWithId(orderNumber);
//		Response resp = RestAssured.get(orderURL + "/" + orderNumber);
//		resp.then().assertThat().statusCode(HttpStatus.SC_OK);
//		String jsonBody = resp.getBody().asPrettyString();
//		
//		OrderDTO order = mapper.readValue(jsonBody, OrderDTO.class);
//		assertEquals(orderNumber, order.getNumber());
//		assertNotNull(order.getItems());
//		assertEquals(1, order.getItems().size());
//		
//		deleteOrderWithNumber(orderNumber);
//	}
//
//	@Test
//	public void testGetOrderById_nonExistingOrder() {
//		Long nonExistingOrderNumber = 999L;
//		Response resp = RestAssured.get(orderURL + "/" + nonExistingOrderNumber);
//		resp.then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
//	}
//
//	@Test
//	public void testDelete_nonExistingOrder() {
//		Long nonExistingOrderNumber = 999L;
//		RestAssured.delete(orderURL + "/" + nonExistingOrderNumber)
//			.then().assertThat().statusCode(HttpStatus.SC_NOT_FOUND);
//	}
//
//	private void deleteOrderWithNumber(Long orderNumber) {
//		RestAssured.delete(orderURL + "/" + orderNumber)
//			.then().assertThat().statusCode(HttpStatus.SC_NO_CONTENT);
//	}
//	
//	private Response createOrderWithId(Long orderNumber) {
//		List<ItemNewDTO> items = new ArrayList<ItemNewDTO>();
//		items.add(new ItemNewDTO(2, 5));
//		OrderNewDTO order = new OrderNewDTO(orderNumber, "S1", "s1@email.com", "1", items);
//
//		Response resp = RestAssured.given().body(order).contentType(ContentType.JSON)
//			.post(orderURL);
//		return resp;
//	}
//}
