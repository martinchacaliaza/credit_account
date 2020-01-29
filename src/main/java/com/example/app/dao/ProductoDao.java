package com.example.app.dao;


import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.app.models.CreditAccount;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ProductoDao extends ReactiveMongoRepository<CreditAccount, String> {

	
	/*@Query("{ 'numero_cuenta' : ?0 }")
	Flux<CreditAccount> viewNumTarjeta(String numero_cuenta);*/
	
	@Query("{ 'numero_cuenta' : ?0, 'codigo_bancario': ?1}")
	Mono<CreditAccount> viewNumTarjeta(String numero_cuenta, String codigo_bancario);
	

	@Query("{ 'dni' : ?0 }")
	Flux<CreditAccount> viewDniCliente(String dni);
	
	@Query("{ 'dni' : ?0 , $where : 'this.consumo > 0'}  }")
	Flux<CreditAccount> viewDniCliente2(String dni);
	
}
