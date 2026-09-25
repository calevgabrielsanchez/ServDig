package mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DatosAsegurado implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4914621936223311106L;
	
	private String nss;
	private Long cveIdAsignacionNss;
	private ArrayList<DatosHistoriaLaboral> datosHistoriaLaboral;
	private DatosGrupoFamiliar datosGrupoFamiliar;
	private ArrayList <DatosGrupoFamiliar> datosGrupoFamiliarBeneficiarios;
	
	
	
	
	public DatosGrupoFamiliar getDatosGrupoFamiliar() {
		return datosGrupoFamiliar;
	}
	public void setDatosGrupoFamiliar(DatosGrupoFamiliar datosGrupoFamiliar) {
		this.datosGrupoFamiliar = datosGrupoFamiliar;
	}

	
	
	public ArrayList<DatosGrupoFamiliar> getDatosGrupoFamiliarBeneficiarios() {
		return datosGrupoFamiliarBeneficiarios;
	}
	public void setDatosGrupoFamiliarBeneficiarios(ArrayList<DatosGrupoFamiliar> datosGrupoFamiliarBeneficiarios) {
		this.datosGrupoFamiliarBeneficiarios = datosGrupoFamiliarBeneficiarios;
	}
	public String getNss() {
		return nss;
	}
	public void setNss(String nss) {
		this.nss = nss;
	}
	public Long getCveIdAsignacionNss() {
		return cveIdAsignacionNss;
	}
	public void setCveIdAsignacionNss(Long cveIdAsignacionNss) {
		this.cveIdAsignacionNss = cveIdAsignacionNss;
	}
	public List<DatosHistoriaLaboral> getDatosHistoriaLaboral() {
		return datosHistoriaLaboral;
	}
	public void setDatosHistoriaLaboral(ArrayList<DatosHistoriaLaboral> datosHistoriaLaboral) {
		this.datosHistoriaLaboral = datosHistoriaLaboral;
	}
	
	
	
	
}
