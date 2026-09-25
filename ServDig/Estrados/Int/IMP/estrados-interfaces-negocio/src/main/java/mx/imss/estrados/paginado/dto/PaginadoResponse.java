package mx.imss.estrados.paginado.dto;

import java.io.Serializable;
import java.util.List;

import mx.imss.estrados.dto.NotificacionesDTO;

public class PaginadoResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4912184992635887073L;
	/**
	 * 
	 */



	private int sEcho;
	private int iTotalRecords;
	private int iTotalDisplayRecords;
	private List<NotificacionesDTO> aaData;

	public List<NotificacionesDTO> getAaData() {
		return aaData;
	}

	public void setAaData(List<NotificacionesDTO> aaData) {
		this.aaData = aaData;
	}

	public int getsEcho() {
		return sEcho;
	}

	public void setsEcho(int sEcho) {
		this.sEcho = sEcho;
	}

	public int getiTotalRecords() {
		return iTotalRecords;
	}

	public void setiTotalRecords(int iTotalRecords) {
		this.iTotalRecords = iTotalRecords;
	}

	public int getiTotalDisplayRecords() {
		return iTotalDisplayRecords;
	}

	public void setiTotalDisplayRecords(int iTotalDisplayRecords) {
		this.iTotalDisplayRecords = iTotalDisplayRecords;
	}

}
