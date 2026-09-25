package mx.gob.imss.ctirss.correccion.web.controller.promocion;

import java.util.List;

import mx.gob.imss.ctirss.correccion.bean.SelectBean;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.web.multipart.MultipartFile;

@JsonIgnoreProperties(ignoreUnknown=true)
public class PromocionCargaModel extends AbstractModel {
	private static final long serialVersionUID = 1L;
	private Long idOrigen;
	private Long idTipo;
	private Long idCriterio;
	private List<SelectBean> origenes;
	private List<SelectBean> tiposPromocion;
	private List<SelectBean> criterios;
	private MultipartFile archivo;
	private String msg;
	private Integer cveSubdelegacion;
	private Integer cveDelegacion;
	
	public Long getIdOrigen() {
		return idOrigen;
	}
	public void setIdOrigen(Long idOrigen) {
		this.idOrigen = idOrigen;
	}
	public Long getIdTipo() {
		return idTipo;
	}
	public void setIdTipo(Long idTipo) {
		this.idTipo = idTipo;
	}
	public Long getIdCriterio() {
		return idCriterio;
	}
	public void setIdCriterio(Long idCriterio) {
		this.idCriterio = idCriterio;
	}
	public MultipartFile getArchivo() {
		return archivo;
	}
	public void setArchivo(MultipartFile archivo) {
		this.archivo = archivo;
	}
	public List<SelectBean> getCriterios() {
		return criterios;
	}
	public void setCriterios(List<SelectBean> criterios) {
		this.criterios = criterios;
	}
	public List<SelectBean> getOrigenes() {
		return origenes;
	}
	public void setOrigenes(List<SelectBean> origenes) {
		this.origenes = origenes;
	}
	public List<SelectBean> getTiposPromocion() {
		return tiposPromocion;
	}
	public void setTiposPromocion(List<SelectBean> tiposPromocion) {
		this.tiposPromocion = tiposPromocion;
	}
	/**
	 * Metodo que obtiene el valor del atributo  msg
	 * @return  msg
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * Metodo que asigna un valor al atributo msg
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/**
	 * Retorna el valor cveSubdelegacion
	 * @return  cveSubdelegacion
	 */
	public Integer getCveSubdelegacion() {
		return cveSubdelegacion;
	}
	/**
	 * Asigna el valor del cveSubdelegacion al atributo cveSubdelegacion
	 * @param cveSubdelegacion 
	 */
	public void setCveSubdelegacion(Integer cveSubdelegacion) {
		this.cveSubdelegacion = cveSubdelegacion;
	}
	/**
	 * Retorna el valor cveDelegacion
	 * @return  cveDelegacion
	 */
	public Integer getCveDelegacion() {
		return cveDelegacion;
	}
	/**
	 * Asigna el valor del cveDelegacion al atributo cveDelegacion
	 * @param cveDelegacion 
	 */
	public void setCveDelegacion(Integer cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}
	
	
}
