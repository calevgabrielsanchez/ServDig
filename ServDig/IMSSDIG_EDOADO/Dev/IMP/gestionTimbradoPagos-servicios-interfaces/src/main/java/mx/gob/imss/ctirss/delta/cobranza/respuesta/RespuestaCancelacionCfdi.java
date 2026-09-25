package mx.gob.imss.ctirss.delta.cobranza.respuesta;

import java.io.Serializable;

public class RespuestaCancelacionCfdi implements Serializable {

	private static final long serialVersionUID = -9080183257696422529L;

	private TipoCfdi[] listTipoCfdi;
	private String codEstatus;
	private String fecha;
	private String rfcEmisor;
	private String codigoOperacion;
	private String resultadoOperacion;

	public TipoCfdi[] getListTipoCfdi() {
		return listTipoCfdi;
	}

	public void setListTipoCfdi(TipoCfdi[] listTipoCfdi) {
		this.listTipoCfdi = listTipoCfdi;
	}

	public String getCodEstatus() {
		return codEstatus;
	}

	public void setCodEstatus(String codEstatus) {
		this.codEstatus = codEstatus;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getRfcEmisor() {
		return rfcEmisor;
	}

	public void setRfcEmisor(String rfcEmisor) {
		this.rfcEmisor = rfcEmisor;
	}

	public String getCodigoOperacion() {
		return codigoOperacion;
	}

	public void setCodigoOperacion(String codigoOperacion) {
		this.codigoOperacion = codigoOperacion;
	}

	public String getResultadoOperacion() {
		return resultadoOperacion;
	}

	public void setResultadoOperacion(String resultadoOperacion) {
		this.resultadoOperacion = resultadoOperacion;
	}

}
