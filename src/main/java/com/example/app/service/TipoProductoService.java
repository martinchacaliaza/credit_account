package com.example.app.service;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.app.models.TypeCreditAccount;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TipoProductoService {
	
	Flux<TypeCreditAccount> findAllTipoproducto();
	Mono<TypeCreditAccount> findByIdTipoProducto(String id);
	Mono<TypeCreditAccount> saveTipoProducto(TypeCreditAccount tipoProducto);
	Mono<Void> deleteTipo(TypeCreditAccount tipoProducto);
	
}
