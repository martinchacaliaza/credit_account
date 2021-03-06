package com.example.app.models;


import java.util.Date;

import javax.validation.constraints.NotEmpty;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Document(collection ="cuentas_creditos")
public class CreditAccount {
	@Id
	@NotEmpty
	private String id;
	@NotEmpty
	private String numeroCuenta;
	@NotEmpty
	private String dni;
	@NotEmpty
	private TypeCreditAccount tipoProducto;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha_afiliacion;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fecha_caducidad;
	@NotEmpty
	private Double credito;
	@NotEmpty
	private Double saldo;
	@NotEmpty
	private Double consumo;
	@NotEmpty
	private String usuario;
	@NotEmpty
	private String clave;
	@NotEmpty
	private String codigoBancario;
	

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
	public Date fecha_afiliacion() {
		return fecha_afiliacion;
	}
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
	public Date fecha_caducidad() {
		return fecha_caducidad;
	}

}










