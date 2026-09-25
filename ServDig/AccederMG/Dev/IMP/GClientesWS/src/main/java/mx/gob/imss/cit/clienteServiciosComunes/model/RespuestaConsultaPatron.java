package mx.gob.imss.cit.clienteServiciosComunes.model;

import java.util.List;

public class RespuestaConsultaPatron {

	private int codigo;
	private String descripcion;
	private int exito;
	private List<InformacionPatron> lstInformacionPatron;

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getExito() {
		return exito;
	}

	public void setExito(int exito) {
		this.exito = exito;
	}

	public List<InformacionPatron> getLstInformacionPatron() {
		return lstInformacionPatron;
	}

	public void setLstInformacionPatron(List<InformacionPatron> lstInformacionPatron) {
		this.lstInformacionPatron = lstInformacionPatron;
	}

}
