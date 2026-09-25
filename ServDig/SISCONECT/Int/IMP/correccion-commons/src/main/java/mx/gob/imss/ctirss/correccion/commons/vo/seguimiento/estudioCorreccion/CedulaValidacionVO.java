package mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.estudioCorreccion;

import java.io.Serializable;
import java.util.List;

import mx.gob.imss.ctirss.correccion.commons.vo.seguimiento.promocion.generico.SegRegularizarObraGenericoTabVO;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class CedulaValidacionVO extends AbstractModel implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Long> ejercicios;
	private List<String> registrosPatronales;

	private String cveAnexoSolCorrPat;
	private Integer cvePresentaCorr;
	private Integer cveEjercicio;
	private Integer cveRecepcion;

	private Integer numTrabRegularizados;
	private Integer seccionAutoriza;
	private String indValPrimera;
	private String indValSegunda;
	
	List<CedulaValidacionConsolidadoVO> listaConsolidados;
	List<CedulaValidacionConsolidadoVO> listaPercepciones;
	
	//para la seccion de consolidacion importes x aclarar
	private SegRegularizarObraGenericoTabVO consolidaImporteVo;
	private String fechaCapturaTxt;
	private boolean comprobanteConvenio;
	//clave de crt_revecepcion
	private Integer cveConsolidaImporte;
	private String observacionesConsolImpte;

	private String resultado;
	private String numFolio;
	
	/**
	 * Retorna el valor fechaCapturaTxt
	 * @return  fechaCapturaTxt
	 */
	public String getFechaCapturaTxt() {
		return fechaCapturaTxt;
	}

	/**
	 * Asigna el valor del fechaCapturaTxt al atributo fechaCapturaTxt
	 * @param fechaCapturaTxt 
	 */
	public void setFechaCapturaTxt(String fechaCapturaTxt) {
		this.fechaCapturaTxt = fechaCapturaTxt;
	}

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
	 * @return cveAnexoSolCorrPat
	 */
	public String getCveAnexoSolCorrPat() {
		return cveAnexoSolCorrPat;
	}

	/**
	 * @param cveAnexoSolCorrPat Clave del anexo de la solicitud de correccion
	 */
	public void setCveAnexoSolCorrPat(String cveAnexoSolCorrPat) {
		this.cveAnexoSolCorrPat = cveAnexoSolCorrPat;
	}

	/**
	 * @return cvePresentaCorr
	 */
	public Integer getCvePresentaCorr() {
		return cvePresentaCorr;
	}

	/**
	 * @param cvePresentaCorr Clave de presentacion de la correccion
	 */
	public void setCvePresentaCorr(Integer cvePresentaCorr) {
		this.cvePresentaCorr = cvePresentaCorr;
	}

	/**
	 * Retorna el valor consolidaImporteVo
	 * @return  consolidaImporteVo
	 */
	public SegRegularizarObraGenericoTabVO getConsolidaImporteVo() {
		return consolidaImporteVo;
	}

	/**
	 * Asigna el valor del consolidaImporteVo al atributo consolidaImporteVo
	 * @param consolidaImporteVo 
	 */
	public void setConsolidaImporteVo(
			SegRegularizarObraGenericoTabVO consolidaImporteVo) {
		this.consolidaImporteVo = consolidaImporteVo;
	}

	/**
	 * Retorna el valor comprobanteConvenio
	 * @return  comprobanteConvenio
	 */
	public boolean isComprobanteConvenio() {
		return comprobanteConvenio;
	}

	/**
	 * Asigna el valor del comprobanteConvenio al atributo comprobanteConvenio
	 * @param comprobanteConvenio 
	 */
	public void setComprobanteConvenio(boolean comprobanteConvenio) {
		this.comprobanteConvenio = comprobanteConvenio;
	}

	/**
	 * Retorna el valor cveConsolidaImporte
	 * @return  cveConsolidaImporte
	 */
	public Integer getCveConsolidaImporte() {
		return cveConsolidaImporte;
	}

	/**
	 * Asigna el valor del cveConsolidaImporte al atributo cveConsolidaImporte
	 * @param cveConsolidaImporte 
	 */
	public void setCveConsolidaImporte(Integer cveConsolidaImporte) {
		this.cveConsolidaImporte = cveConsolidaImporte;
	}

	/**
	 * Retorna el valor observacionesConsolImpte
	 * @return  observacionesConsolImpte
	 */
	public String getObservacionesConsolImpte() {
		return observacionesConsolImpte;
	}

	/**
	 * Asigna el valor del observacionesConsolImpte al atributo observacionesConsolImpte
	 * @param observacionesConsolImpte 
	 */
	public void setObservacionesConsolImpte(String observacionesConsolImpte) {
		this.observacionesConsolImpte = observacionesConsolImpte;
	}

	/**
	 * Retorna el valor listaConsolidados
	 * @return  listaConsolidados
	 */
	public List<CedulaValidacionConsolidadoVO> getListaConsolidados() {
		return listaConsolidados;
	}

	/**
	 * Asigna el valor del listaConsolidados al atributo listaConsolidados
	 * @param listaConsolidados 
	 */
	public void setListaConsolidados(
			List<CedulaValidacionConsolidadoVO> listaConsolidados) {
		this.listaConsolidados = listaConsolidados;
	}

	public List<CedulaValidacionConsolidadoVO> getListaPercepciones() {
		return listaPercepciones;
	}

	public void setListaPercepciones(
			List<CedulaValidacionConsolidadoVO> listaPercepciones) {
		this.listaPercepciones = listaPercepciones;
	}

	public Integer getCveEjercicio() {
		return cveEjercicio;
	}

	public void setCveEjercicio(Integer cveEjercicio) {
		this.cveEjercicio = cveEjercicio;
	}

	public Integer getCveRecepcion() {
		return cveRecepcion;
	}

	public void setCveRecepcion(Integer cveRecepcion) {
		this.cveRecepcion = cveRecepcion;
	}

	public String getIndValPrimera() {
		return indValPrimera;
	}

	public void setIndValPrimera(String indValPrimera) {
		this.indValPrimera = indValPrimera;
	}

	public String getIndValSegunda() {
		return indValSegunda;
	}

	public void setIndValSegunda(String indValSegunda) {
		this.indValSegunda = indValSegunda;
	}

	public Integer getSeccionAutoriza() {
		return seccionAutoriza;
	}

	public void setSeccionAutoriza(Integer seccionAutoriza) {
		this.seccionAutoriza = seccionAutoriza;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public Integer getNumTrabRegularizados() {
		return numTrabRegularizados;
	}

	public void setNumTrabRegularizados(Integer numTrabRegularizados) {
		this.numTrabRegularizados = numTrabRegularizados;
	}

	/**
	 * Retorna el valor numFolio
	 * @return  numFolio
	 */
	public String getNumFolio() {
		return numFolio;
	}

	/**
	 * Asigna el valor del numFolio al atributo numFolio
	 * @param numFolio 
	 */
	public void setNumFolio(String numFolio) {
		this.numFolio = numFolio;
	}
	
	

	
	
}
