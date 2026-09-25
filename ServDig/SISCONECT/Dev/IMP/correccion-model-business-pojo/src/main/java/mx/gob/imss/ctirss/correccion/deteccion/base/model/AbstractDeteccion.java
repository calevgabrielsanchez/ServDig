package mx.gob.imss.ctirss.correccion.deteccion.base.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

@MappedSuperclass
public abstract class AbstractDeteccion extends AbstractModel{

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="CVE_DETECCION_GENERATOR", sequenceName="SEQ_CVE_DETECCION")
	@GeneratedValue(generator="CVE_DETECCION_GENERATOR")
	@Column(name="CVE_DETECCION")
	private Integer cveDeteccion;
	
	@Column(name="NOM_RAZONSOCIAL")
	private String razonSocial;
	
	@Column(name="NUM_REGISTROPATRONAL")
	private String numRegPat;
	
	@Column(name="CAN_SUPERFICIE")
	private Integer canSuperficie;
	
	@Column(name="IMP_IMPORTE")
	private Integer importe;
	
	@Column(name="CVE_NROCONTRATO")
	private String cveContrato;
	
	@Column(name="CVE_NROLICITACION")
	private String cveLicitacion;
	
	@Column(name="TIP_CLASEOBRA")
	private String tipoClase;
	
	@Column(name="IND_CRUZADO")
	private Integer cruzado;

	@Column(name="TIP_ORIGEN")
	private String tipoOrigen;
		
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHAINICIO_FC")
	private Date fecInicio;
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHATERMINO_FC")
	private Date fecTermino;
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHAEXPEDICION_FC")
	private Date fecExpedicion;
	
	@Column(name="IMP_MANOOBRA")
	private Integer impManoObra;
	
	@Column(name="IMP_MONTOCONTRATADO")
	private Integer impMontoContratado;
	
	@Column(name="DES_DEPCONTRATANTE")
	private String desDepContratante;
	
	@Column(name="POR_AVANCEOBRA")
	private Integer avanceObra;
	
	@Column(name="DES_SECTOR")
	private Integer desSector;
	
	@Column(name="DES_SUBSECTOR")
	private Integer desSubSector;
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHARECORRIDO_FC")
	private Date fecRecorrido;
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_FECHADETECCION_FC")
	private Date fecDeteccion;
	
	@Column(name="DOM_CALLE")
	private String domCalle;
	
	@Column(name="NUM_CODIGOPOSTAL")
	private String codigoPostal;
	
	@Column(name="REF_COLONIA")
	private String refColonia;
	
	@Column(name="REF_EMAIL")
	private String refEmail;
	
	@Column(name="NUM_NROEXT")
	private String numExt;
	
	@Column(name="NUM_NROINT")
	private String numInt;
	
	@Column(name="NUM_TELEFONO")
	private String numTelefono;
	
	@Column(name="ID_MUNICIPIO")
	private String idMunicipio;
	
	@Column(name="ENT_FED")
	private BigDecimal entidad;
	
	@Column(name="SDELEG_ORIG")
	private BigDecimal sDelegOrig;
	
	@Column(name="CVE_DELEG_ORIG")
	private BigDecimal delegOrig;
	
	@Column(name="CVE_PERSONA")
	private BigDecimal cvePersona;
	
	@Column(name="FOLIO_DETECCION")
	private String folioDeteccion;
	
	@Column(name="TIPO_OBRA")
	private Integer tpObra;
	
	@Column(name="FASE_OBRA")
	private Integer faseObra;
	
	@Column(name="NUM_CURP")
	private String curp;
	
	@Column(name="RFC")
	private String rfc;
	
	@Column(name="NUM_TRABAJADORES")
	private Integer trabajadores;
	
	@Column(name="ZONA_SALARIAL")
	private Integer zona;
	
	public AbstractDeteccion(){		
	}

	public Integer getCveDeteccion() {
		return cveDeteccion;
	}

	public void setCveDeteccion(Integer cveDeteccion) {
		this.cveDeteccion = cveDeteccion;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getNumRegPat() {
		return numRegPat;
	}

	public void setNumRegPat(String numRegPat) {
		this.numRegPat = numRegPat;
	}

	public Integer getCanSuperficie() {
		return canSuperficie;
	}

	public void setCanSuperficie(Integer canSuperficie) {
		this.canSuperficie = canSuperficie;
	}

	public Integer getImporte() {
		return importe;
	}

	public void setImporte(Integer importe) {
		this.importe = importe;
	}

	public String getCveContrato() {
		return cveContrato;
	}

	public void setCveContrato(String cveContrato) {
		this.cveContrato = cveContrato;
	}

	public String getCveLicitacion() {
		return cveLicitacion;
	}

	public void setCveLicitacion(String cveLicitacion) {
		this.cveLicitacion = cveLicitacion;
	}

	public String getTipoClase() {
		return tipoClase;
	}

	public void setTipoClase(String tipoClase) {
		this.tipoClase = tipoClase;
	}

	public Integer getCruzado() {
		return cruzado;
	}

	public void setCruzado(Integer cruzado) {
		this.cruzado = cruzado;
	}

	public String getTipoOrigen() {
		return tipoOrigen;
	}

	public void setTipoOrigen(String tipoOrigen) {
		this.tipoOrigen = tipoOrigen;
	}

	public Date getFecInicio() {
		return fecInicio;
	}

	public void setFecInicio(Date fecInicio) {
		this.fecInicio = fecInicio;
	}

	public Date getFecTermino() {
		return fecTermino;
	}

	public void setFecTermino(Date fecTermino) {
		this.fecTermino = fecTermino;
	}

	public Date getFecExpedicion() {
		return fecExpedicion;
	}

	public void setFecExpedicion(Date fecExpedicion) {
		this.fecExpedicion = fecExpedicion;
	}

	public Integer getImpManoObra() {
		return impManoObra;
	}

	public void setImpManoObra(Integer impManoObra) {
		this.impManoObra = impManoObra;
	}

	public Integer getImpMontoContratado() {
		return impMontoContratado;
	}

	public void setImpMontoContratado(Integer impMontoContratado) {
		this.impMontoContratado = impMontoContratado;
	}

	public String getDesDepContratante() {
		return desDepContratante;
	}

	public void setDesDepContratante(String desDepContratante) {
		this.desDepContratante = desDepContratante;
	}

	public Integer getAvanceObra() {
		return avanceObra;
	}

	public void setAvanceObra(Integer avanceObra) {
		this.avanceObra = avanceObra;
	}

	public Integer getDesSector() {
		return desSector;
	}

	public void setDesSector(Integer desSector) {
		this.desSector = desSector;
	}

	public Integer getDesSubSector() {
		return desSubSector;
	}

	public void setDesSubSector(Integer desSubSector) {
		this.desSubSector = desSubSector;
	}

	public Date getFecRecorrido() {
		return fecRecorrido;
	}

	public void setFecRecorrido(Date fecRecorrido) {
		this.fecRecorrido = fecRecorrido;
	}

	public Date getFecDeteccion() {
		return fecDeteccion;
	}

	public void setFecDeteccion(Date fecDeteccion) {
		this.fecDeteccion = fecDeteccion;
	}

	public String getDomCalle() {
		return domCalle;
	}

	public void setDomCalle(String domCalle) {
		this.domCalle = domCalle;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getRefColonia() {
		return refColonia;
	}

	public void setRefColonia(String refColonia) {
		this.refColonia = refColonia;
	}

	public String getRefEmail() {
		return refEmail;
	}

	public void setRefEmail(String refEmail) {
		this.refEmail = refEmail;
	}

	public String getNumExt() {
		return numExt;
	}

	public void setNumExt(String numExt) {
		this.numExt = numExt;
	}

	public String getNumInt() {
		return numInt;
	}

	public void setNumInt(String numInt) {
		this.numInt = numInt;
	}

	public String getNumTelefono() {
		return numTelefono;
	}

	public void setNumTelefono(String numTelefono) {
		this.numTelefono = numTelefono;
	}

	public String getIdMunicipio() {
		return idMunicipio;
	}

	public void setIdMunicipio(String idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	public BigDecimal getEntidad() {
		return entidad;
	}

	public void setEntidad(BigDecimal entidad) {
		this.entidad = entidad;
	}

	public BigDecimal getsDelegOrig() {
		return sDelegOrig;
	}

	public void setsDelegOrig(BigDecimal sDelegOrig) {
		this.sDelegOrig = sDelegOrig;
	}

	public BigDecimal getDelegOrig() {
		return delegOrig;
	}

	public void setDelegOrig(BigDecimal delegOrig) {
		this.delegOrig = delegOrig;
	}

	public BigDecimal getCvePersona() {
		return cvePersona;
	}

	public void setCvePersona(BigDecimal cvePersona) {
		this.cvePersona = cvePersona;
	}

	public String getFolioDeteccion() {
		return folioDeteccion;
	}

	public void setFolioDeteccion(String folioDeteccion) {
		this.folioDeteccion = folioDeteccion;
	}

	public Integer getTpObra() {
		return tpObra;
	}

	public void setTpObra(Integer tpObra) {
		this.tpObra = tpObra;
	}

	public Integer getFaseObra() {
		return faseObra;
	}

	public void setFaseObra(Integer faseObra) {
		this.faseObra = faseObra;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public Integer getTrabajadores() {
		return trabajadores;
	}

	public void setTrabajadores(Integer trabajadores) {
		this.trabajadores = trabajadores;
	}

	public Integer getZona() {
		return zona;
	}

	public void setZona(Integer zona) {
		this.zona = zona;
	}	
	
}
