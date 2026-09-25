package mx.gob.imss.cit.dacvass.servicios.externos.model.general;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Page<T> implements Serializable {

	private static final long serialVersionUID = -3443479268786538129L;

	public Page() {
		this.number = 0;
		this.numberOfElements = 0;
		this.size = 0;
		this.totalElements = 0;
		this.totalPages = 0;
		this.content = new ArrayList<T>();
	}


	public Integer getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(Integer totalElements) {
		this.totalElements = totalElements;
	}

	public Integer getNumberOfElements() {
		return numberOfElements;
	}

	public void setNumberOfElements(Integer numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	private List<T> content;

	private Integer totalElements;

	private Integer numberOfElements;

	private Integer number;

	private Integer size;

	private Integer totalPages;

}
