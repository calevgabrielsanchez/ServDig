package mx.gob.imss.ctirss.correccion.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import mx.gob.imss.ctirss.correccion.base.model.AbstractCrtAnexosolcorrpat;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcEjercicio;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcTramiteMensajes;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrtEjertrabajador;
import mx.gob.imss.ctirss.domiciliosInegi.model.DgDomicilioGeografico;

@Entity
@Table(name="CRT_ANEXOSOLCORRPAT")
public class CrtAnexosolcorrpat extends AbstractCrtAnexosolcorrpat{
	
	@Transient
	public static String TIPO_REGISTRO_RP_FISCAL ="F";
	public static String TIPO_REGISTRO_RP_CENTRO_TRABAJO ="C";
	public static String TIPO_REGISTRO_RP_OBRA ="O";
	public static String TIPO_REGISTRO_RP_INSCRITO ="I";
	
	@Transient
	private CrtSolicitudcorr solicitudCorreccion;

	@Transient
	private SatPatron patron;

	@Transient
	private DgDomicilioGeografico domicilioGeografico;

	@Transient
	private String nuFolio;

	@Transient
	private String registroPatronal;

	@Transient
	private long estadoFolioCorr;
	
	@Transient
	private Date fecFechaLimite;

	@Transient
	private String digitoVerificador;
	
	@Transient
	private String lugar;
	
	@Transient
	private String motivo;
	
	@Transient
	private String calle;
	
	@Transient
	private Integer  numExterior;

	@Transient
	private String numExteriorAlfa;
	
	@Transient
	private Integer  numInterior;
	
	@Transient
	private String numInteriorAlfa;
	
	@Transient
	private String colonia;
	
	@Transient
	private String localidad;
	
	@Transient
	private String entidadFederativa;
	
	@Transient
	private String codigoPostal;
	
	@Transient
	private String municipio;
	
	@Transient
	private String tipoCorreccion;

	@Transient
	private String strDelegacion;
	
	@Transient
	private String strSubdelegacion;
	
	@Transient
	private CrcEjercicio crcEjercicio;
	
	@Transient
	private List<CrtEjertrabajador> trabajadores;
	
	@Transient
	private CrtCoppagada copPagadas;

	@Transient
	private List<CrcEjercicio> ejerciciosSolicitud;
	
	
	@Transient
	private String urlAcuseFirma;
	/**
	 * Indica si el RP inscrito cuenta con algan
	 * antecedente
	 */
	@Transient
	private String antecedenteRP;
	
	/**
	 * Indica si se ingresa por internet o subdelegacian
	 * 
	 */
	@Transient
	private String procedencia;
	
	
	@Transient
	private CrcTramiteMensajes mensaje;


	public SatPatron getPatron() {
		return patron;
	}

	public void setPatron(SatPatron patron) {
		this.patron = patron;
	}

	public String getDomicilioCompleto() {
		String dom = "";
		if(this.getCalle()!=null)
			dom = dom +this.getCalle();
		if(this.getNumExterior()!=null)
			dom = dom +" "+this.getNumExterior();
		if(this.getNumInterior()!=null)
			dom = dom +" "+this.getNumInterior();
		if(this.getColonia()!=null)
			dom = dom +" "+this.getColonia();
		if(this.getMunicipio()!=null)
			dom = dom +" "+this.getMunicipio();
		if(this.getLocalidad()!=null)
			dom = dom +" "+this.getLocalidad();
		if(this.getEntidadFederativa()!=null)
			dom = dom +" "+this.getEntidadFederativa();
		if(this.getCodigoPostal()!=null)
			dom = dom +" "+this.getCodigoPostal();
		return dom;
	}
	
	public String getRegistroPatronalSD()
	{
		if (this.getPatron()==null) {
			return "";
		}
			
		return this.getPatron().getRegistroPatronalSD();
	}

	public String getTrabajadoresN()
	{
		if (this.getPatron()==null) {
			return "";
		}
		return this.getPatron().getTrabajadores();
	}
		
	
	
	public void setDirreccionInegi(DgDomicilioGeografico dom){
		if(dom!=null)
		{
			this.setCalle(dom.getDgVialidadByCveViaPrin().getNomVia());
			this.setNumExterior(dom.getNumextnum()!=null?dom.getNumextnum():null);
			this.setNumInterior(dom.getNumintnum()!=null?dom.getNumintnum():null);
//			this.setColonia(dom.getDgAsentamiento().getNomAsen());
//			this.setMunicipio(dom.getDgAsentamiento().getNomAsen());
//			this.setLocalidad(dom.getDgAsentamiento().getNomAsen());
			
			
			this.setColonia(dom.getDgAsentamiento().getNomAsen());
			this.setMunicipio(dom.getDgCatLocalidad().getDgCatMunicipio().getNomMun());
			this.setLocalidad(dom.getDgCatLocalidad().getNomLoc());
			
			
			this.setEntidadFederativa(dom.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getNomEnt());
			this.setCodigoPostal(dom.getDgCodigosPostales().getId().getCodigo());
		}
	}

	
	public DgDomicilioGeografico getDomicilioGeografico() {
		return domicilioGeografico;
	}

	public void setDomicilioGeografico(DgDomicilioGeografico domicilioGeografico) {
		this.domicilioGeografico = domicilioGeografico;
		
	}

	public long getEstadoFolioCorr() {
		return estadoFolioCorr;
	}

	public void setEstadoFolioCorr(long estadoFolioCorr) {
		this.estadoFolioCorr = estadoFolioCorr;
	}

	public String getNuFolio() {
		return nuFolio;
	}

	public void setNuFolio(String nuFolio) {
		this.nuFolio = nuFolio;
	}
	
	public String getRegistroPatronal() {
		return registroPatronal;
	}

	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}
	
	
	public String getDigitoVerificador() {
		return digitoVerificador;
	}

	public void setDigitoVerificador(String digitoVerificador) {
		this.digitoVerificador = digitoVerificador;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public Integer  getNumExterior() {
		return numExterior;
	}

	public void setNumExterior(Integer  numExterior) {
		this.numExterior = numExterior;
	}

	public Integer  getNumInterior() {
		return numInterior;
	}

	public void setNumInterior(Integer  numInterior) {
		this.numInterior = numInterior;
	}

	public String getColonia() {
		return colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getEntidadFederativa() {
		return entidadFederativa;
	}

	public void setEntidadFederativa(String entidadFederativa) {
		this.entidadFederativa = entidadFederativa;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	
	public Date getFecFechaLimite() {
		return fecFechaLimite;
	}

	public void setFecFechaLimite(Date fecFechaLimite) {
		this.fecFechaLimite = fecFechaLimite;
	}
	
	public CrtSolicitudcorr getSolicitudCorreccion() {
		return solicitudCorreccion;
	}

	public void setSolicitudCorreccion(CrtSolicitudcorr solicitudCorreccion) {
		this.solicitudCorreccion = solicitudCorreccion;
	}
	
	
	public String getTipoCorreccion() {
		return tipoCorreccion;
	}

	public void setTipoCorreccion(String tipoCorreccion) {
		this.tipoCorreccion = tipoCorreccion;
	}
	
	
	public String getNumExteriorAlfa() {
		return numExteriorAlfa;
	}

	public void setNumExteriorAlfa(String numExteriorAlfa) {
		this.numExteriorAlfa = numExteriorAlfa;
	}

	public String getNumInteriorAlfa() {
		return numInteriorAlfa;
	}

	public void setNumInteriorAlfa(String numInteriorAlfa) {
		this.numInteriorAlfa = numInteriorAlfa;
	}

	
	public String getStrDelegacion() {
		return strDelegacion;
	}

	public void setStrDelegacion(String strDelegacion) {
		this.strDelegacion = strDelegacion;
	}

	public String getStrSubdelegacion() {
		return strSubdelegacion;
	}

	public void setStrSubdelegacion(String strSubdelegacion) {
		this.strSubdelegacion = strSubdelegacion;
	}

	public CrcEjercicio getCrcEjercicio() {
		return crcEjercicio;
	}

	public void setCrcEjercicio(CrcEjercicio crcEjercicio) {
		this.crcEjercicio = crcEjercicio;
	}


	public List<CrtEjertrabajador> getTrabajadores() {
		return trabajadores;
	}

	public void setTrabajadores(List<CrtEjertrabajador> trabajadores) {
		this.trabajadores = trabajadores;
	}

	
	public CrtAnexosolcorrpat() {
		super();
	}


	public CrtAnexosolcorrpat(String nuFolio, String registroPatronal,
			Date fecFechaLimite) {
		super();
		this.nuFolio = nuFolio;
		this.registroPatronal = registroPatronal;
		this.fecFechaLimite = fecFechaLimite;
	}
	
	public CrtAnexosolcorrpat(String registroPatronal,  String strSubdelegacion, String strDelegacion, Long cvePatronPr,
			String txRazonSocial, CrcEjercicio crcEjercicio, Integer cveAnexoSolicitudCorrPat) {
		super(cvePatronPr, txRazonSocial, cveAnexoSolicitudCorrPat);
		this.registroPatronal = registroPatronal;
		this.strSubdelegacion = strSubdelegacion;
		this.strDelegacion = strDelegacion;
		this.crcEjercicio = crcEjercicio;		
	}


	public CrtAnexosolcorrpat(String calle, Integer  numExterior,
			String numExteriorAlfa, Integer  numInterior, String numInteriorAlfa,
			String colonia, String localidad, String entidadFederativa,
			String codigoPostal, String municipio) {
		super();
		this.calle = calle;
		this.numExterior = numExterior;
		this.numExteriorAlfa = numExteriorAlfa;
		this.numInterior = numInterior;
		this.numInteriorAlfa = numInteriorAlfa;
		this.colonia = colonia;
		this.localidad = localidad;
		this.entidadFederativa = entidadFederativa;
		this.codigoPostal = codigoPostal;
		this.municipio = municipio;
	}

	public CrtCoppagada getCopPagadas() {
		return copPagadas;
	}

	public void setCopPagadas(CrtCoppagada copPagadas) {
		this.copPagadas = copPagadas;
	}
	

	public List<CrcEjercicio> getEjerciciosSolicitud() {
		return ejerciciosSolicitud;
	}

	public void setEjerciciosSolicitud(List<CrcEjercicio> ejerciciosSolicitud) {
		this.ejerciciosSolicitud = ejerciciosSolicitud;
	}

	
	public CrtAnexosolcorrpat(Integer cveAnexoSolicitudCorrPat,
			Long cvePatron, Long cvePatronPr, String txRazonSocial,String registroPatronal) {
		super(cveAnexoSolicitudCorrPat, cvePatron, cvePatronPr, txRazonSocial);
		this.registroPatronal = registroPatronal;
	}

	public String getAntecedenteRP() {
		return antecedenteRP;
	}

	public void setAntecedenteRP(String antecedenteRP) {
		this.antecedenteRP = antecedenteRP;
	}
	
	public CrtAnexosolcorrpat(Integer cveAnexoSolicitudCorrPat,
			Long cvePatron, Long cvePatronPr, String txRazonSocial,String registroPatronal, String tipoPatron,Integer cveDomicilio,
			Integer numTrabajadores, String txActividad, String txClase, String txFraccion, String txPrima) {
		super(cveAnexoSolicitudCorrPat, cvePatron, cvePatronPr, txRazonSocial, registroPatronal, tipoPatron,
			  cveDomicilio, numTrabajadores,  txActividad,  txClase,  txFraccion,  txPrima);
		this.registroPatronal = registroPatronal;
	}
	
	public CrtAnexosolcorrpat(Integer cveAnexoSolicitudCorrPat,
			Long cvePatron, Long cvePatronPr, String txRazonSocial,String registroPatronal, String tipoPatron,Integer cveDomicilio,
			Integer numTrabajadores, String txActividad, String txClase, String txFraccion, String txPrima, String txTelefono) {
		super(cveAnexoSolicitudCorrPat, cvePatron, cvePatronPr, txRazonSocial, registroPatronal, tipoPatron,
			  cveDomicilio, numTrabajadores,  txActividad,  txClase,  txFraccion,  txPrima, txTelefono);
		this.registroPatronal = registroPatronal;
	}

	public String getProcedencia() {
		return procedencia;
	}

	public void setProcedencia(String procedencia) {
		this.procedencia = procedencia;
	}

	
	public CrcTramiteMensajes getMensaje() {
		return mensaje;
	}

	public void setMensaje(CrcTramiteMensajes mensaje) {
		this.mensaje = mensaje;
	}

	public String getUrlAcuseFirma() {
		return urlAcuseFirma;
	}

	public void setUrlAcuseFirma(String urlAcuseFirma) {
		this.urlAcuseFirma = urlAcuseFirma;
	}
	
	


}
