package com.example.app.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.dao.ProductoDao;
import com.example.app.models.CreditAccount;
import com.example.app.service.ProductoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductoServiceImpl implements ProductoService {

	@Autowired
	public ProductoDao productoDao;

	@Override
	public Flux<CreditAccount> findAllProducto() {
		return productoDao.findAll();
	}

	@Override
	public Mono<CreditAccount> findByIdProducto(String id) {
		return productoDao.findById(id);
	}

	@Override
	public Mono<CreditAccount> saveProducto(CreditAccount clientePersonal) {
		return productoDao.save(clientePersonal);
	}

	@Override
	public Mono<CreditAccount> consumos(Double monto, String numTarjeta, String codigo_bancario) {
		return productoDao.viewNumTarjeta(numTarjeta, codigo_bancario).flatMap(c -> {
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
		return productoDao.viewNumTarjeta(numTarjeta, codigo_bancario).flatMap(c -> {

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
		return productoDao.viewNumTarjeta(num, codigo_bancario);
	}

	/*@Override
	public Flux<CreditAccount> listProdNumTarj(String num) {
		return productoDao.viewNumTarjeta(num);
	}*/

	@Override
	public Flux<CreditAccount> findAllProductoByDniCliente(String dni) {
		return productoDao.viewDniCliente(dni);
	}

	@Override
	public Mono<Void> deleteProducto(CreditAccount prod) {
		// TODO Auto-generated method stub
		return productoDao.delete(prod);
	}

	@Override
	public Flux<CreditAccount> findAllProductoByDniCliente2(String dni) {
		
		return productoDao.viewDniCliente2(dni);
	}


}
