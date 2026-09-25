package mx.gob.imss.ctirss.delta.model.clasificacion;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class FiltrosConcentrado extends AbstractModel {

	private static final long serialVersionUID = 1L;

	private String strPeriodoInicio;
	
	private String strPeriodoFin;
	
	private String cveIdGrupoAnalisisCe;
	
	@Override
	public String toString() {
		return "FiltrosConcentrado [strPeriodoInicio=" + strPeriodoInicio
				+ ", strPeriodoFin=" + strPeriodoFin
				+ ", cveIdGrupoAnalisisCe=" + cveIdGrupoAnalisisCe + "]";
	}

	public String getStrPeriodoInicio() {
		return strPeriodoInicio;
	}

	public void setStrPeriodoInicio(String strPeriodoInicio) {
		this.strPeriodoInicio = strPeriodoInicio;
	}

	public String getStrPeriodoFin() {
		return strPeriodoFin;
	}

	public void setStrPeriodoFin(String strPeriodoFin) {
		this.strPeriodoFin = strPeriodoFin;
	}

	public String getCveIdGrupoAnalisisCe() {
		return cveIdGrupoAnalisisCe;
	}

	public void setCveIdGrupoAnalisisCe(String cveIdGrupoAnalisisCe) {
		this.cveIdGrupoAnalisisCe = cveIdGrupoAnalisisCe;
	}
	
}
