package com.example.app.repository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;


import com.example.app.models.TypeCreditAccount;

import reactor.core.publisher.Mono;

public interface TipoProductoDao extends ReactiveMongoRepository<TypeCreditAccount, String> {



	Mono<TypeCreditAccount> findByIdTipo(String id);
	

	
}
