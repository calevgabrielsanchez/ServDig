package mx.imss.ctirss.deteccion.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import mx.imss.ctirss.deteccion.base.model.AbstractCrtDeteccion;
import mx.imss.ctirss.utils.Functions;
import mx.gob.imss.ctirss.domiciliosInegi.model.DgDomicilioGeografico;

@Entity
@Table(name="CRT_DETECCION")
public class CrtDeteccion extends AbstractCrtDeteccion{
	
	@Transient
	public DgDomicilioGeografico domicilioInegi;
	
	@Transient
	public Integer idOrigen;
	
	@Transient
	public String fechaDeteccion;
	
	@Transient
	public String fechaRegistro;
	
	@Transient
	public String fechaIncial;
	
	@Transient
	public String fechaFinal;
	
	@Transient
	public String fechaEstimIncio;
	
	@Transient
	public String fechaEstTerm;
	
	@Transient
	public String fechaEstimIncio2;
	
	@Transient
	public String fechaEstTerm2;
	
	@Transient
	public String fechaIncialAviso;
	
	@Transient
	public String fechaFinalAviso;
	
	@Transient
	public String tipoIncidencia;
	
	@Transient
	public String actividad;
	
	@Transient
	private String domCalle;
	
	@Transient
	private String numCodigopostal;

	@Transient
	private String numNroext;

	@Transient
	private String numNroint;
	
	@Transient
	private String refColonia;
	
	@Transient
	private String estado;
	
	@Transient
	private String municipio;
	
	@Transient
	private String nombreCensor;
	
	@Transient
	private String estatus;
	
	@Transient
	private String nss;
	
	@Transient
	private String matricula;			
	
	@Transient
	private String incidencia;
	
	@Transient
	private String fechaAtencion;
	
	@Transient
	private String fechaNotificacion;
	
	@Transient
	private String fechaEmision;
	
	@Transient
	private Long cvePromocion;
	
	@Transient
	private String fechaDet;
	
	@Transient
	private String periodo;
	
	public String getFechaDeteccion() {
		return fechaDeteccion;
	}

	public void setFechaDeteccion(String fechaDeteccion) {
		this.fechaDeteccion = fechaDeteccion;
	}

	public String getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(String fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public DgDomicilioGeografico getDomicilioInegi() {
		return domicilioInegi;
	}

	public void setDomicilioInegi(DgDomicilioGeografico domicilioInegi) {
		this.domicilioInegi = domicilioInegi;
	}

	public String getDomCalle() {
		return domCalle;
	}

	public void setDomCalle(String domCalle) {
		this.domCalle = domCalle;
	}

	public String getNumCodigopostal() {
		return numCodigopostal;
	}

	public void setNumCodigopostal(String numCodigopostal) {
		this.numCodigopostal = numCodigopostal;
	}

	public String getNumNroext() {
		return numNroext;
	}

	public void setNumNroext(String numNroext) {
		this.numNroext = numNroext;
	}

	public String getNumNroint() {
		return numNroint;
	}

	public void setNumNroint(String numNroint) {
		this.numNroint = numNroint;
	}

	public String getRefColonia() {
		return refColonia;
	}

	public void setRefColonia(String refColonia) {
		this.refColonia = refColonia;
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

	public String getFechaIncialAviso() {
		return fechaIncialAviso;
	}

	public void setFechaIncialAviso(String fechaIncialAviso) {
		this.fechaIncialAviso = fechaIncialAviso;
	}

	public String getFechaFinalAviso() {
		return fechaFinalAviso;
	}

	public void setFechaFinalAviso(String fechaFinalAviso) {
		this.fechaFinalAviso = fechaFinalAviso;
	}

	public String getTipoIncidencia() {
		return tipoIncidencia;
	}

	public void setTipoIncidencia(String tipoIncidencia) {
		this.tipoIncidencia = tipoIncidencia;
	}

	public String getFechaEstimIncio() {
		return fechaEstimIncio;
	}

	public void setFechaEstimIncio(String fechaEstimIncio) {
		this.fechaEstimIncio = fechaEstimIncio;
	}

	public String getFechaEstTerm() {
		return fechaEstTerm;
	}

	public void setFechaEstTerm(String fechaEstTerm) {
		this.fechaEstTerm = fechaEstTerm;
	}

	public String getFechaIncial() {
		if(this.getFecFechainicioEst()!=null)
			return Functions.dateToString(this.getFecFechainicioEst());
		else
			return this.fechaIncial;
	}

	public void setFechaIncial(String fechaIncial) {
		this.fechaIncial = fechaIncial;
	}

	public String getFechaFinal() {
		if(this.getFecFechaterminoEst()!=null)
			return Functions.dateToString(this.getFecFechaterminoEst());
		else
			return this.fechaFinal;
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
	
	public String getActividad() {
		return actividad;
	}

	public void setActividad(String actividad) {
		this.actividad = actividad;
	}

	public String getNombreCensor() {
		return nombreCensor;
	}

	public void setNombreCensor(String nombreCensor) {
		this.nombreCensor = nombreCensor;
	}

	
	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getNss() {
		return nss;
	}

	public void setNss(String nss) {
		this.nss = nss;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getFechaEstimIncio2() {
		return fechaEstimIncio2;
	}

	public void setFechaEstimIncio2(String fechaEstimIncio2) {
		this.fechaEstimIncio2 = fechaEstimIncio2;
	}

	public String getFechaEstTerm2() {
		return fechaEstTerm2;
	}

	public void setFechaEstTerm2(String fechaEstTerm2) {
		this.fechaEstTerm2 = fechaEstTerm2;
	}

	public String getIncidencia() {
		return incidencia;
	}

	public void setIncidencia(String incidencia) {
		this.incidencia = incidencia;
	}

	public String getFechaAtencion() {
		return fechaAtencion;
	}

	public void setFechaAtencion(String fechaAtencion) {
		this.fechaAtencion = fechaAtencion;
	}

	public String getFechaNotificacion() {
		return fechaNotificacion;
	}

	public void setFechaNotificacion(String fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}

	public String getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(String fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public Long getCvePromocion() {
		return cvePromocion;
	}

	public void setCvePromocion(Long cvePromocion) {
		this.cvePromocion = cvePromocion;
	}

	public String getFechaDet() {
		if(this.getFecFechadeteccionFc()!=null)
			return Functions.dateToString(this.getFecFechadeteccionFc());
		else
			return this.fechaDet;
	}

	public void setFechaDet(String fechaDet) {
		this.fechaDet = fechaDet;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public CrtDeteccion() {
		
	}
	
	public CrtDeteccion(Long cveDeteccion) {
		super(cveDeteccion);
	}

	public CrtDeteccion(Long cveDeteccion, Integer cveTipocorr,
			String nuFoliodeteccion, Date fecFechadeteccionFc,
			String nuReportectrlobra, 
			String nomRazonsocial, String txCurppatron,
			String txRfcpatron, BigDecimal sdelegOrig,
			String tipClaseobra, Integer cvePkTipObra,
			Integer cvePkFaseConst, String desDependenciapub,
			String desDepcontratante, BigDecimal canSuperficie,
			BigDecimal impCostoobra, Date fecFechainicioEst,
			Date fecFechaterminoEst, BigDecimal porAvanceobraEst,
			String txTelefono, String txEmail, BigDecimal idPromovido,
			Date fecFechareg ,String cveUsuario, Integer domicilioId, Long cveFkPatron,Integer idMotivo) {
		super(cveDeteccion, cveTipocorr, nuFoliodeteccion, fecFechadeteccionFc,
				nuReportectrlobra, nomRazonsocial, txCurppatron, txRfcpatron, "", 
				"", "", "", "", sdelegOrig, tipClaseobra,
				cvePkTipObra, cvePkFaseConst, desDependenciapub,
				desDepcontratante, canSuperficie, impCostoobra, fecFechainicioEst,
				fecFechaterminoEst, porAvanceobraEst, txTelefono, txEmail, idPromovido,
				fecFechareg, cveUsuario, domicilioId, cveFkPatron, idMotivo);
		// TODO Auto-generated constructor stub
	}
	
	public CrtDeteccion(String calle,String colonia,
			String numInt, Integer numExt, String cp, 
			Long cveDeteccion, Integer cveTipocorr,
			String nuFoliodeteccion, Date fecFechadeteccionFc,
			String nuReportectrlobra, 
			String nomRazonsocial, String txCurppatron,
			String txRfcpatron, BigDecimal sdelegOrig,
			String tipClaseobra, Integer cvePkTipObra,
			Integer cvePkFaseConst, String desDependenciapub,
			String desDepcontratante, BigDecimal canSuperficie,
			BigDecimal impCostoobra, Date fecFechainicioEst,
			Date fecFechaterminoEst, BigDecimal porAvanceobraEst,
			String txTelefono, String txEmail, BigDecimal idPromovido,
			Date fecFechareg ,String cveUsuario, Integer domicilioId,
			Long cveFkPatron, Integer idMotivo) {
		super(cveDeteccion, cveTipocorr, nuFoliodeteccion, fecFechadeteccionFc,
				nuReportectrlobra, nomRazonsocial, txCurppatron, txRfcpatron, "", 
				"", "", "", "", sdelegOrig, tipClaseobra,
				cvePkTipObra, cvePkFaseConst, desDependenciapub,
				desDepcontratante, canSuperficie, impCostoobra, fecFechainicioEst,
				fecFechaterminoEst, porAvanceobraEst, txTelefono, txEmail, idPromovido,
				fecFechareg, cveUsuario, domicilioId, cveFkPatron, idMotivo);
		this.domCalle = calle;
		this.refColonia = colonia;
		this.numNroint = numInt;
		this.numNroext = numExt.toString();
		this.numCodigopostal = cp;
		// TODO Auto-generated constructor stub
	}
	
	public CrtDeteccion(Long cveDeteccion, Integer cveTipocorr,
			String nuFoliodeteccion, Date fecFechadeteccionFc,
			String nuReportectrlobra, 
			String nomRazonsocial, String txCurppatron,
			String txRfcpatron, String domCalle, String numNroext,
			String numNroint, String refColonia, String numCodigopostal,
			BigDecimal sdelegOrig,
			String tipClaseobra, Integer cvePkTipObra,
			Integer cvePkFaseConst, String desDependenciapub,
			String desDepcontratante, BigDecimal canSuperficie,
			BigDecimal impCostoobra, Date fecFechainicioEst,
			Date fecFechaterminoEst, BigDecimal porAvanceobraEst,
			String txTelefono, String txEmail, BigDecimal idPromovido,
			Date fecFechareg ,String cveUsuario, Integer domicilioId,
			Date fecFechaInicioAviso, Date fecFechaTerminoAviso,
			String tipoIncidencia,Integer idMotivo) {
		super(cveDeteccion, cveTipocorr, nuFoliodeteccion, fecFechadeteccionFc,
				nuReportectrlobra, nomRazonsocial, txCurppatron, txRfcpatron, domCalle, 
				numNroext, numNroint, refColonia, numCodigopostal, sdelegOrig, tipClaseobra,
				cvePkTipObra, cvePkFaseConst, desDependenciapub,
				desDepcontratante, canSuperficie, impCostoobra, fecFechainicioEst,
				fecFechaterminoEst, porAvanceobraEst, txTelefono, txEmail, idPromovido,
				fecFechareg, cveUsuario, domicilioId, null, idMotivo);
		this.fechaIncialAviso = Functions.dateToString(fecFechaInicioAviso); 
		this.fechaFinalAviso = Functions.dateToString(fecFechaTerminoAviso);
		this.tipoIncidencia = tipoIncidencia;
	}
	
	public CrtDeteccion(String regPatron, String nomRazonsocial,
			long numRegObra, String domCalle, String numNroint,
			String numNroext, String refColonia, String numCodigopostal,
			String tipClaseobra, Long cvePK,
			BigDecimal canSuperficie, BigDecimal impCostoobra,
			Date fecFechainicioEst, Date fecFechaterminoEst,
			Date fecFechaInicioAviso, Date fecFechaTerminoAviso,
			String tipoIncidencia) {
		super(regPatron, nomRazonsocial, numRegObra, domCalle, numNroint, numNroext,
				refColonia, numCodigopostal, tipClaseobra, cvePK,
				canSuperficie, impCostoobra, fecFechainicioEst, fecFechaterminoEst);
		this.fechaIncialAviso = Functions.dateToString(fecFechaInicioAviso); 
		this.fechaFinalAviso = Functions.dateToString(fecFechaTerminoAviso);
		this.tipoIncidencia = tipoIncidencia;
		// TODO Auto-generated constructor stub
	}
	
	

	public CrtDeteccion(String regPatron, String nomRazonsocial,
			long numRegObra, String domCalle, String numNroint,
			String numNroext, String refColonia, String numCodigopostal,
			String tipClaseobra, Long cvePK,
			BigDecimal canSuperficie, BigDecimal impCostoobra,
			Date fecFechainicioEst, Date fecFechaterminoEst) {
		super(regPatron, nomRazonsocial, numRegObra, domCalle, numNroint, numNroext,
				refColonia, numCodigopostal, tipClaseobra, cvePK,
				canSuperficie, impCostoobra, fecFechainicioEst, fecFechaterminoEst);
		// TODO Auto-generated constructor stub
	}

	public String imprimeObjeto(){
		return new StringBuffer().append("CrtDeteccion{")
								 .append("cveDeteccion:").append(this.getCveDeteccion()).append(";\n")
								 .append("cveTipocorr:").append(this.getCveTipocorr()).append(";\n")
								 .append("nuFoliodeteccion:").append(this.getNuFoliodeteccion()).append(";\n")
								 .append("fecFechadeteccionFc:").append(this.getFecFechadeteccionFc()).append(";\n")
//								 .append("cvePersona:").append(this.getCvePersona()).append(";\n")
								 .append("nuReportectrlobra:").append(this.getNuReportectrlobra()).append(";\n")
								 .append("regPatron:").append(this.getRegPatron()).append(";\n")
//								 .append("cveModal:").append(this.getCveModal()).append(";\n")
								 .append("nomRazonsocial:").append(this.getNomRazonsocial()).append(";\n")
								 .append("txCurppatron:").append(this.getTxCurppatron()).append(";\n")
								 .append("txRfcpatron:").append(this.getTxRfcpatron()).append(";\n")
								 .append("domCalle:").append(this.getDomCalle()).append(";\n")
								 .append("numNroext:").append(this.getNumNroext()).append(";\n")
								 .append("numNroint:").append(this.getNumNroint()).append(";\n")
								 .append("refColonia:").append(this.getRefColonia()).append(";\n")
								 .append("numCodigopostal:").append(this.getNumCodigopostal()).append(";\n")
//								 .append("idMunicipio:").append(this.getIdMunicipio()).append(";\n")
//								 .append("entFed:").append(this.getEntFed()).append(";\n")
								 .append("sdelegOrig:").append(this.getSdelegOrig()).append(";\n")
//								 .append("cveDelegOrig:").append(this.getCveDelegOrig()).append(";\n")
								 .append("tipClaseobra:").append(this.getTipClaseobra()).append(";\n")
								 .append("cvePkTipObra:").append(this.getCvePkTipObra()).append(";\n")
								 .append("cvePkFaseConst:").append(this.getCvePkFaseConst()).append(";\n")
								 .append("desDependenciapub:").append(this.getDesDependenciapub()).append(";\n")
								 .append("desDepcontratante:").append(this.getDesDepcontratante()).append(";\n")
								 .append("canSuperficie:").append(this.getCanSuperficie()).append(";\n")
								 .append("impCostoobra:").append(this.getImpCostoobra()).append(";\n")
								 .append("fecFechainicioEst:").append(this.getFecFechainicioEst()).append(";\n")								 
								 .append("fecFechaterminoEst:").append(this.getFecFechaterminoEst()).append(";\n")
								 .append("porAvanceobraEst:").append(this.getPorAvanceobraEst()).append(";\n")								 
								 .append("txTelefono:").append(this.getTxTelefono()).append(";\n")
								 .append("txEmail:").append(this.getTxEmail()).append(";\n")
								 .append("idPromovido:").append(this.getIdPromovido()).append(";\n")
								 .append("fecFechareg:").append(this.getFecFechareg()).append(";\n")
								 .append("cveUsuario:").append(this.getCveUsuario()).append(";\n")
								 .append("cveSelector:").append(this.getCveSelector()).append(";\n")
								 .append("idObligado:").append(this.getIdObligado()).append(";\n")
								 .append("}")
								 .toString();
	}
	
	public CrtDeteccion(Long cveDeteccion, Integer cveTipocorr,
			String nuFoliodeteccion, Date fecFechadeteccionFc, String nomRazonsocial,
			BigDecimal sdelegOrig,String desDependenciapub, String desDepcontratante, Date fecFechainicioEst,
			Date fecFechaterminoEst, BigDecimal idPromovido, Date fecFechareg, String cveUsuario, Integer domicilioId,
			Long cveFkPatron) {
	super(cveDeteccion, cveTipocorr, nuFoliodeteccion, fecFechadeteccionFc, nomRazonsocial,
			sdelegOrig, desDependenciapub, desDepcontratante, fecFechainicioEst,
			fecFechaterminoEst, idPromovido, fecFechareg, cveUsuario, domicilioId,
			cveFkPatron);
	}

	
}
