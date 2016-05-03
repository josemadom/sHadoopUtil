package es.unex.shadoopMongo;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class Datos {

	@NotEmpty
	private String coleccion;
	
	@NotNull
	private Integer annoDia;
	
	@NotEmpty
	private String datoAParsear;

	@NotNull
	private Integer totalDatosDias;
	
	private Datos() {
		
	}
	
	public Datos(String coleccion, Integer annoDia, String datoAParsear, Integer totalDatosDias) {
		this.coleccion = coleccion;
		this.annoDia = annoDia;
		this.datoAParsear = datoAParsear;
		this.totalDatosDias = totalDatosDias;
	}

	public String getColeccion() {
		return coleccion;
	}

	public Integer getAnnoDia() {
		return annoDia;
	}

	public String getDatoAParsear() {
		return datoAParsear;
	}
	
	public Integer getTotalDatosDias() {
		return totalDatosDias;
	}	

}
