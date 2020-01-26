package com.example.app.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.app.dao.TipoProductoDao;
import com.example.app.models.TypeCreditAccount;
import com.example.app.service.TipoProductoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class tipoProductoServiceImpl implements TipoProductoService{

	
	@Autowired
	public TipoProductoDao  tipoProductoDao;
	
	@Override
	public Flux<TypeCreditAccount> findAllTipoproducto()
	{
	return tipoProductoDao.findAll();
	
	}
	@Override
	public Mono<TypeCreditAccount> findByIdTipoProducto(String id)
	{
	return tipoProductoDao.findById(id);
	
	}
	
	@Override
	public Mono<TypeCreditAccount> saveTipoProducto(TypeCreditAccount tipoCliente)
	{
	return tipoProductoDao.save(tipoCliente);
	}
	
	@Override
	public Mono<Void> deleteTipo(TypeCreditAccount tipoProducto) {
		return tipoProductoDao.delete(tipoProducto);
	}
	
}
