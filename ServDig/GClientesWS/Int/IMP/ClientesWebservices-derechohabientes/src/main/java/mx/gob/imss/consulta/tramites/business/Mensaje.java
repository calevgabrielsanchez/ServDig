package mx.gob.imss.consulta.tramites.business;

import java.io.Serializable;

public class Mensaje implements Serializable {

	private static final long serialVersionUID = 1L;

	private String identificadorFuente;
	private String registroAInsertar;
	private String identificadorOperacion;

	public String getIdentificadorFuente() {
		return identificadorFuente;
	}

	public void setIdentificadorFuente(String identificadorFuente) {
		this.identificadorFuente = identificadorFuente;
	}

	public String getRegistroAInsertar() {
		return registroAInsertar;
	}

	public void setRegistroAInsertar(String registroAInsertar) {
		this.registroAInsertar = registroAInsertar;
	}

	public String getIdentificadorOperacion() {
		return identificadorOperacion;
	}

	public void setIdentificadorOperacion(String identificadorOperacion) {
		this.identificadorOperacion = identificadorOperacion;
	}

}
