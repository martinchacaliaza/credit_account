package com.example.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.app.models.TypeCreditAccount;
import com.example.app.service.TipoProductoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/api/TipoProducto")
@RestController
public class TipoProductoControllers {

	
	@Autowired
	private TipoProductoService  tipoProductosService;
	
	@GetMapping
	public Mono<ResponseEntity<Flux<TypeCreditAccount>>> findAll() 
	{
		return Mono.just(
				ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(tipoProductosService.findAllTipoproducto())
				);
	}
	
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<TypeCreditAccount>> viewId(@PathVariable String id){
		return tipoProductosService.findByIdTipoProducto(id).map(p-> ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(p))
				.defaultIfEmpty(ResponseEntity.notFound().build());	
	}
	
	@PutMapping
	public Mono<TypeCreditAccount> updateProducto(@RequestBody TypeCreditAccount tipoProducto)
	{
		//System.out.println(cliente.toString());
		return tipoProductosService.saveTipoProducto(tipoProducto);
	}	
	
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> deleteCourse(@PathVariable String id)
	{
		return tipoProductosService.findByIdTipoProducto(id).flatMap(t -> {
			
			return tipoProductosService.deleteTipo(t).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));		
		}).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NO_CONTENT));
	}
	
	@PostMapping
	public Mono<TypeCreditAccount> saveTipoProducto(@RequestBody TypeCreditAccount tipoProducto)
	{
		return tipoProductosService.saveTipoProducto(tipoProducto);
	}	


	
}
