package mx.gob.imss.ctirss.delta.cobranza.web.dto;

import java.io.Serializable;
import java.util.List;

public class ComprobantesFiscalesDto implements Serializable{

	private static final long serialVersionUID = 2128909742206812092L;

	private List<String> comprobantes;
	private String periodo;
	private String rfc;
	private String numeroRegistroPatronal;

	public List<String> getComprobantes() {
		return comprobantes;
	}

	public void setComprobantes(List<String> comprobantes) {
		this.comprobantes = comprobantes;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getNumeroRegistroPatronal() {
		return numeroRegistroPatronal;
	}

	public void setNumeroRegistroPatronal(String numeroRegistroPatronal) {
		this.numeroRegistroPatronal = numeroRegistroPatronal;
	}
	
	
}
