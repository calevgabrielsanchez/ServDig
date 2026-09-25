package mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.ctirss.correccion.catalogos.model.CrcEjercicio;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcPercepciones;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
@JsonIgnoreProperties(ignoreUnknown = true)
public class CedulaRevisionAudVO extends AbstractModel implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Long> ejercicios;
	private List<String> registrosPatronales;
	private List<TotalRpEjercVO> totalRpEjer;
	
	private String cveAnexoSolCorrPat;
	private String cvePresentaCorr;
	private List<RubroVO> rubros;
	private List<CrcPercepciones> percepciones;
	private String baseCotPagaImsPatron;
	private String baseCotPagaImsIMSS;
	private boolean autorizaBaseCotPaga;
	private boolean autorizaDifBaseCot;
	private boolean razonable;
	private boolean presuntivo;
	private boolean accesoDetalle;
	private String  razonAcceso;
	private String observaciones;
	private boolean solicitudConstruccion;
	private String fechaAplicacionCedula;
	private boolean recepcionAutorizada;
	private String difBaseCotiPatron;
	private String difBaseCotiIMSS;
	
	

	private boolean autorizacionCompleta;
	
	private Integer indAutorizaRevision;

	public List<Long> getEjercicios() {
		return ejercicios;
	}

	public void setEjercicios(List<Long> ejercicios) {
		this.ejercicios = ejercicios;
	}

	public List<String> getRegistrosPatronales() {
		return registrosPatronales;
	}

	public void setRegistrosPatronales(List<String> registrosPatronales) {
		this.registrosPatronales = registrosPatronales;
	}

	/**
	 * @return the baseCotPagaImsPatron
	 */
	public String getBaseCotPagaImsPatron() {
		return baseCotPagaImsPatron;
	}

	/**
	 * @param baseCotPagaImsPatron the baseCotPagaImsPatron to set
	 */
	public void setBaseCotPagaImsPatron(String baseCotPagaImsPatron) {
		this.baseCotPagaImsPatron = baseCotPagaImsPatron;
	}

	/**
	 * @return the baseCotPagaImsIMSS
	 */
	public String getBaseCotPagaImsIMSS() {
		return baseCotPagaImsIMSS;
	}

	/**
	 * @param baseCotPagaImsIMSS the baseCotPagaImsIMSS to set
	 */
	public void setBaseCotPagaImsIMSS(String baseCotPagaImsIMSS) {
		this.baseCotPagaImsIMSS = baseCotPagaImsIMSS;
	}

	/**
	 * @return the difBaseCotiPatron
	 */
	public String getDifBaseCotiPatron() {
		return difBaseCotiPatron;
	}

	/**
	 * @param difBaseCotiPatron the difBaseCotiPatron to set
	 */
	public void setDifBaseCotiPatron(String difBaseCotiPatron) {
		this.difBaseCotiPatron = difBaseCotiPatron;
	}

	/**
	 * @return the difBaseCotiIMSS
	 */
	public String getDifBaseCotiIMSS() {
		return difBaseCotiIMSS;
	}

	/**
	 * @param difBaseCotiIMSS the difBaseCotiIMSS to set
	 */
	public void setDifBaseCotiIMSS(String difBaseCotiIMSS) {
		this.difBaseCotiIMSS = difBaseCotiIMSS;
	}

	public List<RubroVO> getRubros() {
		return rubros;
	}

	public void setRubros(List<RubroVO> rubros) {
		this.rubros = rubros;
	}

	public List<CrcPercepciones> getPercepciones() {
		return percepciones;
	}

	public void setPercepciones(List<CrcPercepciones> percepciones) {
		this.percepciones = percepciones;
	}

	public String getCveAnexoSolCorrPat() {
		return cveAnexoSolCorrPat;
	}

	public void setCveAnexoSolCorrPat(String cveAnexoSolCorrPat) {
		this.cveAnexoSolCorrPat = cveAnexoSolCorrPat;
	}

	public String getCvePresentaCorr() {
		return cvePresentaCorr;
	}

	public void setCvePresentaCorr(String cvePresentaCorr) {
		this.cvePresentaCorr = cvePresentaCorr;
	}

	public List<TotalRpEjercVO> getTotalRpEjer() {
		return totalRpEjer;
	}

	public void setTotalRpEjer(List<TotalRpEjercVO> totalRpEjer) {
		this.totalRpEjer = totalRpEjer;
	}

	public boolean isAutorizaBaseCotPaga() {
		return autorizaBaseCotPaga;
	}

	public void setAutorizaBaseCotPaga(boolean autorizaBaseCotPaga) {
		this.autorizaBaseCotPaga = autorizaBaseCotPaga;
	}

	public boolean isAutorizaDifBaseCot() {
		return autorizaDifBaseCot;
	}

	public void setAutorizaDifBaseCot(boolean autorizaDifBaseCot) {
		this.autorizaDifBaseCot = autorizaDifBaseCot;
	}

	public boolean isPresuntivo() {
		return presuntivo;
	}

	public void setPresuntivo(boolean presuntivo) {
		this.presuntivo = presuntivo;
	}

	public String getFechaAplicacionCedula() {
		return fechaAplicacionCedula;
	}

	public void setFechaAplicacionCedula(String fechaAplicacionCedula) {
		this.fechaAplicacionCedula = fechaAplicacionCedula;
	}

	public boolean isSolicitudConstruccion() {
		return solicitudConstruccion;
	}

	public void setSolicitudConstruccion(boolean solicitudConstruccion) {
		this.solicitudConstruccion = solicitudConstruccion;
	}

	public boolean isAccesoDetalle() {
		return accesoDetalle;
	}

	public void setAccesoDetalle(boolean accesoDetalle) {
		this.accesoDetalle = accesoDetalle;
	}

	public String getRazonAcceso() {
		return razonAcceso;
	}

	public void setRazonAcceso(String razonAcceso) {
		this.razonAcceso = razonAcceso;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public boolean isAutorizacionCompleta() {
		return autorizacionCompleta;
	}

	public void setAutorizacionCompleta(boolean autorizacionCompleta) {
		this.autorizacionCompleta = autorizacionCompleta;
	}

	public boolean isRazonable() {
		return razonable;
	}

	public void setRazonable(boolean razonable) {
		this.razonable = razonable;
	}

	public Integer getIndAutorizaRevision() {
		return indAutorizaRevision;
	}

	public void setIndAutorizaRevision(Integer indAutorizaRevision) {
		this.indAutorizaRevision = indAutorizaRevision;
	}

	public boolean isRecepcionAutorizada() {
		return recepcionAutorizada;
	}

	public void setRecepcionAutorizada(boolean recepcionAutorizada) {
		this.recepcionAutorizada = recepcionAutorizada;
	}

	

	
	
	
}
