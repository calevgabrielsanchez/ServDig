package mx.gob.imss.cit.gestion.solicitud.flujo.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public class DataPage implements Serializable {
	
	private static final long serialVersionUID = -3004314912200250893L;

	private long totalOfRecords;
	
	private long totalOfPages;
	
	private long currentPage;
	
	private int pageSize;
	
	private List<Long> procesos;
	
	private Collection<? extends Serializable> data;

	public long getTotalOfRecords() {
		return totalOfRecords;
	}

	public void setTotalOfRecords(long totalOfRecords) {
		this.totalOfRecords = totalOfRecords;
	}

	public long getTotalOfPages() {
		return totalOfPages;
	}

	public void setTotalOfPages(long totalOfPages) {
		this.totalOfPages = totalOfPages;
	}

	public long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Collection<? extends Serializable> getData() {
		return data;
	}

	public void setData(Collection<? extends Serializable> data) {
		this.data = data;
	}
	
	public List<Long> getProcesos() {
		return procesos;
	}

	public void setProcesos(List<Long> procesos) {
		this.procesos = procesos;
	}

}
