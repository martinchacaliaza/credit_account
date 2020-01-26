package com.example.app.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;

import com.example.app.models.DtoCreditAccount;
import com.example.app.models.CreditAccount;
import com.example.app.models.TypeCreditAccount;
import com.example.app.service.ProductoService;
import com.example.app.service.TipoProductoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/api/ProductoCredito")
@RestController
public class ProductoControllers {
	
	
	
	@Autowired
	private ProductoService productoService;
	
	@Autowired
	private TipoProductoService tipoProductoService;
	
	@GetMapping
	public Mono<ResponseEntity<Flux<CreditAccount>>> findAll() 
	{
		return Mono.just(
						ResponseEntity
						.ok()
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.body(productoService.findAllProducto())
						
						);
	}
		
	@GetMapping("/{id}")
	public Mono<ResponseEntity<CreditAccount>> viewId(@PathVariable String id){
		return productoService.findByIdProducto(id).map(p-> ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(p))
				.defaultIfEmpty(ResponseEntity.notFound().build());	
	}
	
	
	@PutMapping
	public Mono<CreditAccount> updateProducto(@RequestBody CreditAccount producto)
	{
		System.out.println(producto.toString());
		return productoService.saveProducto(producto);
	}	
		
	@PostMapping
	public Mono<CreditAccount> guardarProducto(@RequestBody CreditAccount pro){
	
		Mono<TypeCreditAccount> tipo= this.tipoProductoService.findByIdTipoProducto(pro.getTipoProducto().getIdTipo());
			return tipo
					.defaultIfEmpty(new TypeCreditAccount())
					.flatMap(c -> {
						if (c.getIdTipo() ==null) {
							return Mono.error(new InterruptedException("El tipo de Producto no existe"));
						}
							return Mono.just(c);
					})
					.flatMap(t->{
						pro.setTipoProducto(t);
						return productoService.saveProducto(pro);
					});
	}
	
	//actualiza al momento de hacer la transaccion desde servicio operaciones(movimientos)
	@PutMapping("/consumo/{numero_cuenta}/{monto}")
	public Mono<CreditAccount> retiroBancario(@PathVariable Double monto, @PathVariable String numero_cuenta) {
			return productoService.consumos(monto, numero_cuenta);
	}
	
	//actualiza al momento de hacer la transaccion desde servicio operaciones(movimientos)
	@PutMapping("/pago/{numero_cuenta}/{monto}")
	public Mono<CreditAccount> despositoBancario(@PathVariable Double monto, @PathVariable String numero_cuenta) {
		
			return productoService.pagos(monto, numero_cuenta);
	}
	
	//Muestra la cuenta bancaria por el numero de tarjeta
	@GetMapping("/numero_cuenta/{numero_cuenta}")
	public Mono<CreditAccount> consulta1(@PathVariable String numero_cuenta) {
		Mono<CreditAccount> producto = productoService.listProdNumTarj(numero_cuenta);
		return producto;
	}
	
	// Muestra los saldos, deuda linea de credito de las cuentas de un cliente
	// se consulta por el numero de cuenta
	@GetMapping("/saldoDisponible/{numero_cuenta}")
	public Mono<DtoCreditAccount> SaldosBancarios(@PathVariable String numero_cuenta) {

		Mono<CreditAccount> oper = productoService.listProdNumTarj(numero_cuenta);

		return oper.flatMap(c -> {

			DtoCreditAccount pp = new DtoCreditAccount();
			/*tipoProducto tp = new tipoProducto();
			
			tp.setIdTipo(c.getTipoProducto().getIdTipo());
			tp.setDescripcion(c.getTipoProducto().getDescripcion());*/
			
			pp.setDni(c.getDni());
			pp.setNumero_cuenta(c.getNumero_cuenta());
			pp.setSaldo(c.getSaldo());
	
			pp.setConsumo(c.getConsumo());
			pp.setCredito(c.getCredito());
			
			return Mono.just(pp);
		});

	}
	
}
