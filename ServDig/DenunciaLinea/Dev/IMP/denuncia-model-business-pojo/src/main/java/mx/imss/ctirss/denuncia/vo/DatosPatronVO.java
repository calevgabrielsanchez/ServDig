package mx.imss.ctirss.denuncia.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DatosPatronVO implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int denunciaExistente;
	private String razonSocial;
	private String nombreRepresentanteLegal;
	private String giroPatron;
	private String sectorPatron;
	private String sector;
	private String rfc;
	private String regPat;
	private String numTrabajadores;
	private String telefonoEmpresa;
	private int recibeTotalSueldoDeUnPatron;
	private String observaciones;
	private Long idDomicilio;
	private String desDomicilio;
	private List<PatronSecundarioVO> patronesSecundarios;
	
	
	public DatosPatronVO(){
		this.patronesSecundarios=new ArrayList<PatronSecundarioVO>();
	}
	
	public int getDenunciaExistente() {
		return denunciaExistente;
	}
	public void setDenunciaExistente(int denunciaExistente) {
		this.denunciaExistente = denunciaExistente;
	}
	public String getRazonSocial() {
		return razonSocial!=null ?razonSocial.toUpperCase():razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getNombreRepresentanteLegal() {
		return nombreRepresentanteLegal!=null ? nombreRepresentanteLegal.toUpperCase():nombreRepresentanteLegal;
	}
	public void setNombreRepresentanteLegal(String nombreRepresentanteLegal) {
		this.nombreRepresentanteLegal = nombreRepresentanteLegal;
	}
	public String getGiroPatron() {
		return giroPatron!=null ? giroPatron.toUpperCase():giroPatron;
	}
	public void setGiroPatron(String giroPatron) {
		this.giroPatron = giroPatron;
	}
	public String getSector() {
		return sector!=null ? sector.toUpperCase():sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	public String getRfc() {
		return rfc!=null ? rfc.toUpperCase():rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public String getRegPat() {
		return regPat!=null? regPat.toUpperCase():regPat;
	}
	public void setRegPat(String regPat) {
		this.regPat = regPat;
	}

	public String getNumTrabajadores() {
		return numTrabajadores;
	}
	public void setNumTrabajadores(String numTrabajadores) {
		this.numTrabajadores = numTrabajadores;
	}
	public String getTelefonoEmpresa() {
		return telefonoEmpresa;
	}
	public void setTelefonoEmpresa(String telefonoEmpresa) {
		this.telefonoEmpresa = telefonoEmpresa;
	}
	public int getRecibeTotalSueldoDeUnPatron() {
		return recibeTotalSueldoDeUnPatron;
	}
	public void setRecibeTotalSueldoDeUnPatron(int recibeTotalSueldoDeUnPatron) {
		this.recibeTotalSueldoDeUnPatron = recibeTotalSueldoDeUnPatron;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DatosPatronVO [denunciaExistente=");
		builder.append(denunciaExistente);
		builder.append(", razonSocial=");
		builder.append(razonSocial);
		builder.append(", nombreRepresentanteLegal=");
		builder.append(nombreRepresentanteLegal);
		builder.append(", giroPatron=");
		builder.append(giroPatron);
		builder.append(", sector=");
		builder.append(sector);
		builder.append(", rfc=");
		builder.append(rfc);
		builder.append(", regPat=");
		builder.append(regPat);
		builder.append(", numTrabajadores=");
		builder.append(numTrabajadores);
		builder.append(", telefonoEmpresa=");
		builder.append(telefonoEmpresa);
		builder.append(", recibeTotalSueldoDeUnPatron=");
		builder.append(recibeTotalSueldoDeUnPatron);
		builder.append(", observaciones=");
		builder.append(observaciones);
		builder.append("]");
		return builder.toString();
	}
	public List<PatronSecundarioVO> getPatronesSecundarios() {
		return patronesSecundarios;
	}
	public void setPatronesSecundarios(List<PatronSecundarioVO> patronesSecundarios) {
		this.patronesSecundarios = patronesSecundarios;
	}

	public Long getIdDomicilio() {
		return idDomicilio;
	}

	public void setIdDomicilio(Long idDomicilio) {
		this.idDomicilio = idDomicilio;
	}

	public String getDesDomicilio() {
		return desDomicilio!=null ? desDomicilio.toUpperCase():desDomicilio;
	}

	public void setDesDomicilio(String desDomicilio) {
		this.desDomicilio = desDomicilio;
	}
	
}
