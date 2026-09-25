package mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ObrasSimilaresInputSiroc<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public T getModel() {
		return model;
	}

	public void setModel(T model) {
		this.model = model;
	}
	
	public String getCampoAproximacion() {
		return campoAproximacion;
	}

	public void setCampoAproximacion(String campoAproximacion) {
		this.campoAproximacion = campoAproximacion;
	}

	private Integer page;

	private Integer pageSize;

	private String order;

	private String desc;

	private T model;
	
	private String campoAproximacion;

}
