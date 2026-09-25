package mx.gob.imss.ctirss.delta.gestion.beneficio.web.formModel;

import java.util.List;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

public class DatosEntradaRiss extends AbstractModel {

	private static final long serialVersionUID = 1L;

	public static final String opcRissRfc = "rissRfc";
	public static final String opcRissNss = "rissNss";

	private String opcRISS;
	private String rfc;
	private String nss;
	private String accion;

	private List<SujetoObligado> patrones;

	public String getOpcRISS() {
		return opcRISS;
	}

	public void setOpcRISS(String opcRISS) {
		this.opcRISS = opcRISS;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getNss() {
		return nss;
	}

	public void setNss(String nss) {
		this.nss = nss;
	}

	public List<SujetoObligado> getPatrones() {
		return patrones;
	}

	public void setPatrones(List<SujetoObligado> patrones) {
		this.patrones = patrones;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

}
