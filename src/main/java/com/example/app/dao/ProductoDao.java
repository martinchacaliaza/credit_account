package com.example.app.dao;


import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.app.models.CreditAccount;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ProductoDao extends ReactiveMongoRepository<CreditAccount, String> {

	
	/*@Query("{ 'numero_cuenta' : ?0 }")
	Flux<Producto> viewNumTarjeta(String numero_cuenta);*/
	
	@Query("{ 'numero_cuenta' : ?0 }")
	Mono<CreditAccount> viewNumTarjeta(String numero_cuenta);
	
}
