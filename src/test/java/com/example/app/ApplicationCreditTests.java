package com.example.app;

import java.util.Collections;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.example.app.models.CreditAccount;
import com.example.app.service.ProductoService;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationCreditTests {

	@Autowired
	private WebTestClient credit; 
	
	@Autowired
	ProductoService creditService;
	
	
	@Test
	void contextLoads() {
	}
	@Test
	public void listCredit() {
		credit.get().uri("/api/ProductoCredito/")
		.accept(MediaType.APPLICATION_JSON_UTF8)
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
		.expectBodyList(CreditAccount.class).consumeWith(response -> {
			List<CreditAccount> credit = response.getResponseBody();
			
			credit.forEach(p -> {
				System.out.println(p.getDni());
			});
			
			Assertions.assertThat(credit.size()>0).isTrue();
		});;
	}
	
	@Test
	public void findByIdCredit() {
		CreditAccount cred = creditService.findByIdProducto("5e304f943baa0732debbda85").block();
		credit.get().uri("/api/ProductoCredito/{id}", Collections.singletonMap("id", cred.getId()))
		.accept(MediaType.APPLICATION_JSON_UTF8)
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8);
	}
	
	@Test
	public void findAllProductoByDniCliente() {
		CreditAccount cred = creditService.findAllProductoByDniCliente("123456").blockFirst();
		credit.get().uri("/api/ProductoCredito/dni/{dni}", Collections.singletonMap("dni", cred.getDni()))
		.accept(MediaType.APPLICATION_JSON_UTF8)
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8);
	}
}
