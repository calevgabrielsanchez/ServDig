package mx.gob.imss.ctirss.delta.model.gestion.patronal.fecha;

import java.io.Serializable;

public class FechaDto implements Serializable{
	private static final long serialVersionUID = 1L;
	private int dia;
	private int mes;
	private  int year;
	
	public int getDia() {
		return dia;
	}
	public void setDia(int dia) {
		this.dia = dia;
	}
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
}
