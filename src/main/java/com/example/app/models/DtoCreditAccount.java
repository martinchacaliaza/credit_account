package com.example.app.models;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class DtoCreditAccount {

	private String dni;
	private String numero_cuenta;
	private Double credito;
	private Double saldo;
	private Double consumo;
}
