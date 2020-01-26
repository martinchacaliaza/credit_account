package com.example.app.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.app.models.TypeCreditAccount;

public interface TipoProductoDao extends ReactiveMongoRepository<TypeCreditAccount, String> {

}
