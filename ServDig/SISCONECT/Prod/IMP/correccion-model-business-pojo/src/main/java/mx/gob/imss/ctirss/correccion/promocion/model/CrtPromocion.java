package mx.gob.imss.ctirss.correccion.promocion.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import mx.gob.imss.ctirss.correccion.catalogos.model.CrcTipoCorr;
import mx.gob.imss.ctirss.correccion.promocion.base.model.AbstractCrtPromocion;

@Entity
@Table(name = "CRT_PROMOCION")
public class CrtPromocion extends AbstractCrtPromocion {

	@Transient
	public Integer idOrigen;

	@Transient
	public Date fechaDe;

	@Transient
	public Date fechaA;

	@Transient
	public String fechaInicio;

	@Transient
	public String fechaFin;

	@Transient
	public String fechaOficio;

	@Transient
	public String fechaNotificacion;

	@Transient
	public String fechaAtencion;
	
	@Transient
	public String fechaEmision;
	
	@Transient
	public String fechaPAI;	
	@Transient
	public String fechaCancelacion;
	@Transient
	public String descCriterioseleccion;

	@Transient
	public String fechaRegularizacion;

	@Transient
	public String fechaIncial;

	@Transient
	public String fechaFinal;

	@Transient
	public String domicilio;

	@Transient
	public String domCalle;

	@Transient
	public String refColonia;

	@Transient
	public String numNroint;

	@Transient
	public String numNroext;

	@Transient
	public String numCodigopostal;

	@Transient
	private String estado;
	
	@Transient
	private String municipio;
	
	@Transient
	public String tipoPromocion;

	@Transient
	public String regPatron;

	@Transient
	public String razonSocial;
	
	@Transient
	public String fecSolCorr;
	
	@Transient
	public String fecOficioInvitacion;
	
	@Transient
	public String bandera;
	
	@Transient
	public String numRegObra;
	
	@Transient
	private String folioTemp;
	
	@Transient
	private String claseObra;
	
	@Transient
	private String tipoObra;
	
	@Transient
	private String faseObra;
	@Transient
	public String fechaAvisoDictamen;
	
	@Transient
	private String cveTemp;
	
	@Transient
	private Long cveSolicitudCorr;
	
	@Transient
	private Long cveInvitacion;
	
	@Transient
	private String auditor;
	
	@Transient
	private String seguimiento;
	
	@Transient
	private String rolUsuario;
	
	@Transient
	private String afil15;
	
	@Transient
	private String tipoPrograma;
	
	@Transient
	private Integer cveSubdel;
	
	@Transient
	private Integer cveDel;
	
	@Transient
	public String getCveTemp() {
		return cveTemp;
	}
	
	
	@Transient
	public void setCveTemp(String cveTemp) {
		this.cveTemp = cveTemp;
	}
	@Transient
	public String getClaseObra() {
		return claseObra;
	}
	@Transient
	public void setClaseObra(String claseObra) {
		this.claseObra = claseObra;
	}
	@Transient
	public String getTipoObra() {
		return tipoObra;
	}
	@Transient
	public void setTipoObra(String tipoObra) {
		this.tipoObra = tipoObra;
	}
	@Transient
	public String getFaseObra() {
		return faseObra;
	}
	@Transient
	public void setFaseObra(String faseObra) {
		this.faseObra = faseObra;
	}
	@Transient
	public String getFolioTemp() {
		return folioTemp;
	}
	@Transient
	public void setFolioTemp(String folioTemp) {
		this.folioTemp = folioTemp;
	}	
	@Transient
	public String getBandera() {
		return bandera;
	}
	
	public void setBandera(String bandera) {
		this.bandera = bandera;
	}

	public String getNumRegObra() {
		return numRegObra;
	}

	public void setNumRegObra(String numRegObra) {
		this.numRegObra = numRegObra;
	}

	public String getFecOficioInvitacion() {
		return fecOficioInvitacion;
	}

	public void setFecOficioInvitacion(String fecOficioInvitacion) {
		this.fecOficioInvitacion = fecOficioInvitacion;
	}

	@Transient 
	private Map<Long, String> tiposCorreccion = new HashMap<Long,String>();
	
	public String getFecSolCorr() {
		return fecSolCorr;
	}

	public void setFecSolCorr(String fecSolCorr) {
		this.fecSolCorr = fecSolCorr;
	}

	public String getDomCalle() {
		return domCalle;
	}

	public void setDomCalle(String domCalle) {
		this.domCalle = domCalle;
	}

	public String getRefColonia() {
		return refColonia;
	}

	public void setRefColonia(String refColonia) {
		this.refColonia = refColonia;
	}

	public String getNumNroint() {
		return numNroint;
	}

	public void setNumNroint(String numNroint) {
		this.numNroint = numNroint;
	}

	public String getNumNroext() {
		return numNroext;
	}

	public void setNumNroext(String numNroext) {
		this.numNroext = numNroext;
	}

	public String getNumCodigopostal() {
		return numCodigopostal;
	}

	public void setNumCodigopostal(String numCodigopostal) {
		this.numCodigopostal = numCodigopostal;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getTipoPromocion() {
		return tipoPromocion;
	}

	public void setTipoPromocion(String tipoPromocion) {
		this.tipoPromocion = tipoPromocion;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getFechaIncial() {
		return fechaIncial;
	}

	public void setFechaIncial(String fechaIncial) {
		this.fechaIncial = fechaIncial;
	}

	public String getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(String fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	public Integer getIdOrigen() {
		return idOrigen;
	}

	public void setIdOrigen(Integer idOrigen) {
		this.idOrigen = idOrigen;
	}

	public Date getFechaDe() {
		return fechaDe;
	}

	public void setFechaDe(Date fechaDe) {
		this.fechaDe = fechaDe;
	}

	public Date getFechaA() {
		return fechaA;
	}

	public void setFechaA(Date fechaA) {
		this.fechaA = fechaA;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getFechaOficio() {
		return fechaOficio;
	}

	public void setFechaOficio(String fechaOficio) {
		this.fechaOficio = fechaOficio;
	}

	public String getFechaNotificacion() {
		return fechaNotificacion;
	}

	public void setFechaNotificacion(String fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}

	public String getRegPatron() {
		return regPatron;
	}

	public void setRegPatron(String regPatron) {
		this.regPatron = regPatron;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String imprimeObjeto() {
		return new StringBuffer().append("CrtPromocion{")
				.append("cvePromocion:").append(this.getCvePromocion())
				.append(";\n").append("cveNroregobraSatic:")
				.append(this.getCveNroregobraSatic()).append(";\n")
				.append("cveTipocorr:").append(this.getCveTipocorr())
				.append(";\n").append("cveUsuario:")
				.append(this.getCveUsuario()).append(";\n")
				.append("fecFechaemisionpro:")
				.append(this.getFecFechaemisionpro()).append(";\n")
				.append("fecFechanotif:").append(this.getFecFechanotif())
				.append(";\n").append("fecFechaoficiopro:")
				.append(this.getFecFechaoficiopro()).append(";\n")
				.append("fecFecharegulariza:")
				.append(this.getFecFecharegulariza()).append(";\n")
				.append("fecFechareg:").append(this.getFecFechareg())
				.append(";\n").append("fecFechapai:")
				.append(this.getFecFechapai()).append(";\n")
				.append("fecFechaCancela:").append(this.getFecFechaCancela())
				.append(";\n").append("fecFechaAtencion:")
				.append(this.getFecFechaAtencion()).append(";\n")
				.append("nuFoliopromocion:").append(this.getNuFoliopromocion())
				.append(";\n").append("nuOficiopro:")
				.append(this.getNuOficiopro()).append(";\n")
				.append("sdelegOrig:").append(this.getSdelegOrig())
				.append(";\n").append("idCriterioSeleccion:")
				.append(this.getIdCriterioSeleccion()).append(";\n")
				.append("cveDeteccion:").append(this.getCveDeteccion())
				.append(";\n").append("idMotivoCancelacion:")
				.append(this.getCveDeteccion()).append(";\n")
				.append("cveFkPatron:").append(this.getCveDeteccion())
				.append(";\n").append("}").toString();
	}

	public CrtPromocion(long cvePromocion, String nuFoliopromocion,
			String nuOficiopro, Date fecFechaemisionpro, Date fecFechanotif,
			Long cveFkPatron, String cveUsuario, BigDecimal cveDeteccion,
			String txObservaciones, Long cveSelector, Long idCriterioSeleccion,String cveAuditor) {
		super(cvePromocion, nuFoliopromocion, nuOficiopro, fecFechaemisionpro,
				fecFechanotif, cveFkPatron, cveUsuario, cveDeteccion,
				txObservaciones, cveSelector, idCriterioSeleccion,cveAuditor);
		// TODO Auto-generated constructor stub
	}
	
	public CrtPromocion(long cvePromocion, String nuFoliopromocion,
			String nuOficiopro, Date fecFechaemisionpro, Date fecFechanotif,
			Long cveFkPatron,String cveUsuario, BigDecimal cveDeteccion) {
	super(cvePromocion, nuFoliopromocion, nuOficiopro, fecFechaemisionpro, fecFechanotif,
			cveFkPatron, cveUsuario, cveDeteccion);
	// TODO Auto-generated constructor stub
	}


	public CrtPromocion() {
		super();
	}
	
	/**
	 * Constructor para Auditor
	 * @author Enrique Duran Jimenez
	 * @since 25/05/2012
	 */
	public CrtPromocion(Long cvePromocion, String nuFoliopromocion,
			Long cveFkPatron, Date fecInicialDictamen, Date fecFinalDictamen,
			Date fecFechaoficiopro) {
		super(cvePromocion, nuFoliopromocion,cveFkPatron, fecInicialDictamen, fecFinalDictamen, fecFechaoficiopro);	
	}

	/**
	 * @return the fechaAtencion
	 */
	public String getFechaAtencion() {
		return fechaAtencion;
	}

	/**
	 * @param fechaAtencion the fechaAtencion to set
	 */
	public void setFechaAtencion(String fechaAtencion) {
		this.fechaAtencion = fechaAtencion;
	}

	/**
	 * @return the fechaPAI
	 */
	public String getFechaPAI() {
		return fechaPAI;
	}

	/**
	 * @param fechaPAI the fechaPAI to set
	 */
	public void setFechaPAI(String fechaPAI) {
		this.fechaPAI = fechaPAI;
	}
	
	/**
	 * @return the fechaCancelacion
	 */
	public String getFechaCancelacion() {
		return fechaCancelacion;
	}

	/**
	 * @param fechaCancelacion the fechaCancelacion to set
	 */
	public void setFechaCancelacion(String fechaCancelacion) {
		this.fechaCancelacion = fechaCancelacion;
	}

	/**
	 * @return the descCriterioseleccion
	 */
	public String getDescCriterioseleccion() {
		return descCriterioseleccion;
	}

	/**
	 * @param descCriterioseleccion the descCriterioseleccion to set
	 */
	public void setDescCriterioseleccion(String descCriterioseleccion) {
		this.descCriterioseleccion = descCriterioseleccion;
	}

	/**
	 * @return the fechaRegularizacion
	 */
	public String getFechaRegularizacion() {
		return fechaRegularizacion;
	}

	/**
	 * @param fechaRegularizacion the fechaRegularizacion to set
	 */
	public void setFechaRegularizacion(String fechaRegularizacion) {
		this.fechaRegularizacion = fechaRegularizacion;
	}

	/**
	 * @return the tiposCorreccion
	 */
	public Map<Long, String> getTiposCorreccion() {
		return tiposCorreccion;
	}

	/**
	 * @param tiposCorreccion the tiposCorreccion to set
	 */
	public void setTiposCorreccion(Map<Long, String> tiposCorreccion) {
		this.tiposCorreccion = tiposCorreccion;
	}
	/**
	 * Metodo que obtiene el valor del atributo  fechaAvisoDictamen
	 * @return  fechaAvisoDictamen
	 */
	public String getFechaAvisoDictamen() {
		return fechaAvisoDictamen;
	}
	/**
	 * Metodo que asigna un valor al atributo fechaAvisoDictamen
	 * @param fechaAvisoDictamen the fechaAvisoDictamen to set
	 */
	public void setFechaAvisoDictamen(String fechaAvisoDictamen) {
		this.fechaAvisoDictamen = fechaAvisoDictamen;
	}
	/**
	 * @return the cveSolicitudCorr
	 */
	public Long getCveSolicitudCorr() {
		return cveSolicitudCorr;
	}
	/**
	 * @param cveSolicitudCorr the cveSolicitudCorr to set
	 */
	public void setCveSolicitudCorr(Long cveSolicitudCorr) {
		this.cveSolicitudCorr = cveSolicitudCorr;
	}
	/**
	 * @return the cveInvitacion
	 */
	public Long getCveInvitacion() {
		return cveInvitacion;
	}
	/**
	 * @param cveInvitacion the cveInvitacion to set
	 */
	public void setCveInvitacion(Long cveInvitacion) {
		this.cveInvitacion = cveInvitacion;
	}
	/**
	 * @return the auditor
	 */
	public String getAuditor() {
		return auditor;
	}
	/**
	 * @param auditor the auditor to set
	 */
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	/**
	 * @return the seguimiento
	 */
	public String getSeguimiento() {
		return seguimiento;
	}
	/**
	 * @param seguimiento the seguimiento to set
	 */
	public void setSeguimiento(String seguimiento) {
		this.seguimiento = seguimiento;
	}
	/**
	 * @return the fechaEmision
	 */
	@Transient
	public String getFechaEmision() {
		return fechaEmision;
	}
	/**
	 * @param fechaEmision the fechaEmision to set
	 */
	@Transient
	public void setFechaEmision(String fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	/**
	 * @return the rolUsuario
	 */
	@Transient
	public String getRolUsuario() {
		return rolUsuario;
	}
	/**
	 * @param rolUsuario the rolUsuario to set
	 */
	@Transient
	public void setRolUsuario(String rolUsuario) {
		this.rolUsuario = rolUsuario;
	}
	/**
	 * @return the afil15
	 */
	@Transient
	public String getAfil15() {
		return afil15;
	}
	/**
	 * @param afil15 the afil15 to set
	 */
	@Transient
	public void setAfil15(String afil15) {
		this.afil15 = afil15;
	}
	/**
	 * @return the tipoPrograma
	 */
	public String getTipoPrograma() {
		return tipoPrograma;
	}
	/**
	 * @param tipoPrograma the tipoPrograma to set
	 */
	public void setTipoPrograma(String tipoPrograma) {
		this.tipoPrograma = tipoPrograma;
	}
	/**
	 * Retorna el valor cveSubdel
	 * @return  cveSubdel
	 */
	@Transient
	public Integer getCveSubdel() {
		return cveSubdel;
	}
	/**
	 * Asigna el valor del cveSubdel al atributo cveSubdel
	 * @param cveSubdel 
	 */
	@Transient
	public void setCveSubdel(Integer cveSubdel) {
		this.cveSubdel = cveSubdel;
	}
	/**
	 * Retorna el valor cveDel
	 * @return  cveDel
	 */
	@Transient
	public Integer getCveDel() {
		return cveDel;
	}
	/**
	 * Asigna el valor del cveDel al atributo cveDel
	 * @param cveDel 
	 */
	@Transient
	public void setCveDel(Integer cveDel) {
		this.cveDel = cveDel;
	}

	

}
