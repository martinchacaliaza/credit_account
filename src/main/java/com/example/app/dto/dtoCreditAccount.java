package com.example.app.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class dtoCreditAccount {

	private String dni;
	private String numeroCuenta;
	private Double credito;
	private Double saldo;
	private Double consumo;
}
