package com.example.app.dao;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;


import com.example.app.models.TypeCreditAccount;

import reactor.core.publisher.Mono;

public interface TipoProductoDao extends ReactiveMongoRepository<TypeCreditAccount, String> {



	Mono<TypeCreditAccount> findByIdTipo(String id);
	

	
}
