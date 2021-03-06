package com.example.app.service;



import com.example.app.models.CreditAccount;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductoService {

	Flux<CreditAccount> findAllProducto();
	
	Mono<CreditAccount> findByIdProducto(String id);
	
	Mono<CreditAccount> saveProducto(CreditAccount clientePersonal);

	Mono<CreditAccount> consumos(Double monto, String numero_cuenta, String codigo_bancario);
	
	Mono<CreditAccount> pagos(Double monto, String numero_cuenta, String codigo_bancario);

	Mono<CreditAccount> listProdNumTarj(String numero_cuenta, String codigo_bancario);

	Flux<CreditAccount> findByDni(String dni);
	
	Mono<Void> deleteProducto(CreditAccount prod);

	Flux<CreditAccount> clienteDeuda(String dni);

}
