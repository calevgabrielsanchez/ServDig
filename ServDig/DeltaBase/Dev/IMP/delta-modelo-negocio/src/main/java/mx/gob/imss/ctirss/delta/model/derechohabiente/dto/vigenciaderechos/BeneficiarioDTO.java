package mx.gob.imss.ctirss.delta.model.derechohabiente.dto.vigenciaderechos;

import java.io.Serializable;
import java.util.Date;

import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.DatosAfiliacionBeneficiarioDTO;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.DatosAfiliacionBeneficiarioEstDTO;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;

public class BeneficiarioDTO extends AseguradoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6942955560860266731L;
	private String nombreBen;
	private String primerApellidoBen;
	private String segundoApellidoBen;
	private String curpBen;
	private String parentescoBen;
	private Date fechaNacimientoBen;
	private Long edadBen;
	private String sexoBen;
	private String delegacionBen;
	private String umfBen;
	private Date vencimientoVigenciaBen;
	private String situacionBen;
	private String detalleSituacionBen;
	private String calidad;
	private Integer numeroRegistro;
	private String mesNacimiento;
	private Integer anioNacimiento;
	private String observaciones;
	
	private String desEstadoCivil;
	private Integer cveIdEstadoCivil;
	
	
	public String getDesEstadoCivil() {
		return desEstadoCivil;
	}

	public void setDesEstadoCivil(String desEstadoCivil) {
		this.desEstadoCivil = desEstadoCivil;
	}

	public Integer getCveIdEstadoCivil() {
		return cveIdEstadoCivil;
	}

	public void setCveIdEstadoCivil(Integer cveIdEstadoCivil) {
		this.cveIdEstadoCivil = cveIdEstadoCivil;
	}

	private Domicilio domicilio;
	
	private DatosAfiliacionBeneficiarioDTO datosAfiliacionBeneficiarioDTO; 
	
	private DatosAfiliacionBeneficiarioEstDTO datosAfiliacionBeneficiarioEstDTO;

	public String getNombreBen() {
		return nombreBen;
	}

	public void setNombreBen(String nombreBen) {
		this.nombreBen = nombreBen;
	}

	public String getPrimerApellidoBen() {
		return primerApellidoBen;
	}

	public void setPrimerApellidoBen(String primerApellidoBen) {
		this.primerApellidoBen = primerApellidoBen;
	}

	public String getSegundoApellidoBen() {
		return segundoApellidoBen;
	}

	public void setSegundoApellidoBen(String segundoApellidoBen) {
		this.segundoApellidoBen = segundoApellidoBen;
	}

	public String getCurpBen() {
		return curpBen;
	}

	public void setCurpBen(String curpBen) {
		this.curpBen = curpBen;
	}

	public String getParentescoBen() {
		return parentescoBen;
	}

	public void setParentescoBen(String parentescoBen) {
		this.parentescoBen = parentescoBen;
	}

	public Date getFechaNacimientoBen() {
		return fechaNacimientoBen;
	}

	public void setFechaNacimientoBen(Date fechaNacimientoBen) {
		this.fechaNacimientoBen = fechaNacimientoBen;
	}

	public Long getEdadBen() {
		return edadBen;
	}

	public void setEdadBen(Long edadBen) {
		this.edadBen = edadBen;
	}

	public String getSexoBen() {
		return sexoBen;
	}

	public void setSexoBen(String sexoBen) {
		this.sexoBen = sexoBen;
	}

	public String getDelegacionBen() {
		return delegacionBen;
	}

	public void setDelegacionBen(String delegacionBen) {
		this.delegacionBen = delegacionBen;
	}

	public String getUmfBen() {
		return umfBen;
	}

	public void setUmfBen(String umfBen) {
		this.umfBen = umfBen;
	}

	public Date getVencimientoVigenciaBen() {
		return vencimientoVigenciaBen;
	}

	public void setVencimientoVigenciaBen(Date vencimientoVigenciaBen) {
		this.vencimientoVigenciaBen = vencimientoVigenciaBen;
	}

	public String getSituacionBen() {
		return situacionBen;
	}

	public void setSituacionBen(String situacionBen) {
		this.situacionBen = situacionBen;
	}

	public String getDetalleSituacionBen() {
		return detalleSituacionBen;
	}

	public void setDetalleSituacionBen(String detalleSituacionBen) {
		this.detalleSituacionBen = detalleSituacionBen;
	}

	public String getCalidad() {
		return calidad;
	}

	public void setCalidad(String calidad) {
		this.calidad = calidad;
	}

	public String getMesNacimiento() {
		return mesNacimiento;
	}

	public void setMesNacimiento(String mesNacimiento) {
		this.mesNacimiento = mesNacimiento;
	}

	public Integer getAnioNacimiento() {
		return anioNacimiento;
	}

	public void setAnioNacimiento(Integer anioNacimiento) {
		this.anioNacimiento = anioNacimiento;
	}

	public Integer getNumeroRegistro() {
		return numeroRegistro;
	}

	public void setNumeroRegistro(Integer numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Domicilio getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
	}

	public DatosAfiliacionBeneficiarioDTO getDatosAfiliacionBeneficiarioDTO() {
		return datosAfiliacionBeneficiarioDTO;
	}

	public void setDatosAfiliacionBeneficiarioDTO(DatosAfiliacionBeneficiarioDTO datosAfiliacionBeneficiarioDTO) {
		this.datosAfiliacionBeneficiarioDTO = datosAfiliacionBeneficiarioDTO;
	}

	public DatosAfiliacionBeneficiarioEstDTO getDatosAfiliacionBeneficiarioEstDTO() {
		return datosAfiliacionBeneficiarioEstDTO;
	}

	public void setDatosAfiliacionBeneficiarioEstDTO(DatosAfiliacionBeneficiarioEstDTO datosAfiliacionBeneficiarioEstDTO) {
		this.datosAfiliacionBeneficiarioEstDTO = datosAfiliacionBeneficiarioEstDTO;
	}

}

