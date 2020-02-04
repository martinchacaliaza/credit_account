package com.example.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.app.exception.RequestException;
import com.example.app.dto.dtoClient;
import com.example.app.models.CreditAccount;
import com.example.app.models.TypeCreditAccount;
import com.example.app.repository.ProductoDao;
import com.example.app.repository.TipoProductoDao;
import com.example.app.service.ProductoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductoServiceImpl implements ProductoService {

	@Value("${com.bootcamp.gateway.url}")
	String valor;

	@Autowired
	public ProductoDao productoDao;

	@Autowired
	public TipoProductoDao tipoProductoDao;

	@Override
	public Flux<CreditAccount> findAllProducto() {
		return productoDao.findAll();
	}

	@Override
	public Mono<CreditAccount> findByIdProducto(String id) {
		return productoDao.findById(id);
	}

	@Override
	public Mono<CreditAccount> saveProducto(CreditAccount cli) {
		Mono<TypeCreditAccount> tipo = this.tipoProductoDao.findByIdTipo(cli.getTipoProducto().getIdTipo());
			return tipo.defaultIfEmpty(new TypeCreditAccount()).flatMap(c -> {
				if (c.getIdTipo() == null) {
					throw new RequestException("el tipo cliente no existe");

				}
				return Mono.just(c);
			}).flatMap(t -> {
				cli.setTipoProducto(t);
				Mono<dtoClient> cl = WebClient.builder().baseUrl("http://" + valor + "/clientes/api/Clientes/").build().get()
						.uri("/dni/" + cli.getDni()).retrieve().bodyToMono(dtoClient.class);
				return cl.flatMap(a -> {
					
					if (a.getDni().equalsIgnoreCase("")) {
						return Mono.empty();
						
					}else if(!a.getCodigo_bancario().equalsIgnoreCase(cli.getCodigo_bancario())) {		
						throw new RequestException("Cliente no pertenece al banco(codigo bancario)");
					}	
				return productoDao.save(cli);
			});

			});
	}

	@Override
	public Mono<CreditAccount> consumos(Double monto, String numTarjeta, String codigo_bancario) {
		return productoDao.consultaNumCuentaByCodBanc(numTarjeta, codigo_bancario).flatMap(c -> {
			if (monto <= c.getSaldo()) {
				c.setSaldo((c.getSaldo() - monto));
				c.setConsumo(c.getConsumo() + monto);
				return productoDao.save(c);

			} else {
				return Mono.error(new InterruptedException("No tiene el saldo suficiente para realizar"
						+ " el consumo, tiene un saldo de: " + c.getSaldo()));
			}
		});
	}

	@Override
	public Mono<CreditAccount> pagos(Double monto, String numTarjeta, String codigo_bancario) {
		return productoDao.consultaNumCuentaByCodBanc(numTarjeta, codigo_bancario).flatMap(c -> {

			if (c.getConsumo() == 0.0) {
				return Mono.error(new InterruptedException("No tiene deuda"));

			} else {
				c.setSaldo((c.getSaldo() + monto));
				c.setConsumo(c.getConsumo() - monto);
				return productoDao.save(c);
			}
		});
	}

	@Override
	public Mono<CreditAccount> listProdNumTarj(String num, String codigo_bancario) {
		return productoDao.consultaNumCuentaByCodBanc(num, codigo_bancario);
	}

	@Override
	public Flux<CreditAccount> findByDni(String dni) {
		return productoDao.findByDni(dni);
	}

	@Override
	public Mono<Void> deleteProducto(CreditAccount prod) {
		// TODO Auto-generated method stub
		return productoDao.delete(prod);
	}

}
