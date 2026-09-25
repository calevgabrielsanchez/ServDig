package mx.imss.ctirss.web.controller.promocion;

import java.util.List;

import mx.imss.ctirss.bean.SelectBean;
import mx.imss.ctirss.framework.base.model.AbstractModel;

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
}
