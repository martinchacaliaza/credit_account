package com.example.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.app.dto.dtoCreditAccount;
import com.example.app.models.CreditAccount;
import com.example.app.service.ProductoService;


import io.swagger.annotations.ApiOperation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/api/ProductoCredito")
@RestController
public class ProductoControllers {

	@Autowired
	private ProductoService productoService;
	
	@GetMapping
	public Mono<ResponseEntity<Flux<CreditAccount>>> findAll() 
	{
		return Mono.just(
						ResponseEntity
						.ok()
						.body(productoService.findAllProducto())
						
						);
	}
	
	@ApiOperation(value = "LISTA TODOS CUENTAS CREDITO POR ID", notes="")
	@GetMapping("/{id}")
	public Mono<ResponseEntity<CreditAccount>> viewId(@PathVariable String id){
		return productoService.findByIdProducto(id).map(p-> ResponseEntity.ok()
				.body(p))
				.defaultIfEmpty(ResponseEntity.notFound().build());	
	}
	
	@ApiOperation(value = "ACTUALIZA CUENTAS DE CREDITO POR ID", notes="")
	@PutMapping
	public Mono<CreditAccount> updateProducto(@RequestBody CreditAccount producto)
	{
		System.out.println(producto.toString());
		return productoService.saveProducto(producto);
	}	
	
	@ApiOperation(value = "GUARDA CUENTAS DE CREDITO VALIDANDO SI EL [TIPO PROD] EXISTE", notes="")
	@PostMapping
	public Mono<CreditAccount> guardarProducto(@RequestBody CreditAccount pro){
			return productoService.saveProducto(pro);
	}
	
	
	
	@ApiOperation(value = "ELIMINA PRODUCTO POR ID", notes="")
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> deleteBanco(@PathVariable String id) {
		return productoService.findByIdProducto(id)
				.flatMap(s -> {
			return productoService.deleteProducto(s).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
		}).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NO_CONTENT));
	}

	
	@ApiOperation(value = "LISTA PRODUCTOS POR DNI DEL CLIENTE", notes="")
	@GetMapping("/dni/{dni}")
	public Flux<CreditAccount> listProductoByDicliente(@PathVariable String dni) {
			Flux<CreditAccount> producto = productoService.findByDni(dni);
			return producto;

	}
	
	@ApiOperation(value = "LISTA CREDITOS QUE NO TENGAN DEUDA - POR DNI DEL CLIENTE", notes="")
	@GetMapping("/dniDeuda/{dni}")
	public Flux<CreditAccount> listProductoByDicliente2(@PathVariable String dni) {
			Flux<CreditAccount> producto = productoService.clienteDeuda(dni);
			return producto;

	}
	
	@ApiOperation(value = "actualiza al momento de hacer la transaccion "
			+ "desde servicio operaciones(movimientos)", notes="")
	@PutMapping("/consumo/{numero_cuenta}/{monto}/{codigo_bancario}")
	public Mono<CreditAccount> retiroBancario(@PathVariable Double monto, @PathVariable String numero_cuenta, 
			@PathVariable String codigo_bancario) {
			return productoService.consumos(monto, numero_cuenta,codigo_bancario);
	}
	

	@ApiOperation(value = "actualiza al momento de hacer la transaccion desde "
			+ " servicio operaciones(movimientos)", notes="")
	@PutMapping("/pago/{numero_cuenta}/{monto}/{codigo_bancario}")
	public Mono<CreditAccount> despositoBancario(@PathVariable Double monto, @PathVariable String numero_cuenta,
			@PathVariable String codigo_bancario) {
		
			return productoService.pagos(monto, numero_cuenta,codigo_bancario);
	}
	
	
	@ApiOperation(value = "Muestra la cuenta bancaria por el numero de cuenta y codigo banco", notes="")
	@GetMapping("/numero_cuenta/{numero_cuenta}/{codigo_bancario}")
	public Mono<CreditAccount> consulta1(@PathVariable String numero_cuenta, @PathVariable String codigo_bancario) {
		Mono<CreditAccount> producto = productoService.listProdNumTarj(numero_cuenta, codigo_bancario);
		return producto;
	}
	
	@ApiOperation(value = " Muestra los saldos, deuda linea de credito de las cuentas de un cliente"
			+ " se consulta por el numero de cuenta", notes="")
	@GetMapping("/saldoDisponible/{numero_cuenta}/{codigo_bancario}")
	public Mono<dtoCreditAccount> SaldosBancarios(@PathVariable String numero_cuenta, @PathVariable String codigo_bancario) {
		Mono<CreditAccount> oper = productoService.listProdNumTarj(numero_cuenta, codigo_bancario);
		return oper.flatMap(c ->{
			dtoCreditAccount pp = new dtoCreditAccount();
			pp.setDni(c.getDni());
			pp.setNumeroCuenta(c.getNumeroCuenta());
			pp.setSaldo(c.getSaldo());
			pp.setConsumo(c.getConsumo());
			pp.setCredito(c.getCredito());		
			return Mono.just(pp);
		});

	}
	
}
