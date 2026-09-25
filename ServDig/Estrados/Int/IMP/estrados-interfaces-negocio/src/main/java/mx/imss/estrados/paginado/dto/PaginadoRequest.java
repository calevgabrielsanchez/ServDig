package mx.imss.estrados.paginado.dto;

import java.io.Serializable;

import mx.imss.estrados.dto.SsoVwUsuarioDTO;

public class PaginadoRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7521964302526120568L;
	/**
	 * 
	 */
	

	private int echo;
	private String search;
	private int displayStart;
	private int displayLength;
	private FiltroColumna filtroColumna;
	private String searchColumnaDocumento;
	private String searchColumnaStatus;
	private SsoVwUsuarioDTO filtroUsuarioSession;

	public int getEcho() {
		return echo;
	}

	public void setEcho(int echo) {
		this.echo = echo;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public int getDisplayStart() {
		return displayStart;
	}

	public void setDisplayStart(int displayStart) {
		this.displayStart = displayStart;
	}

	public int getDisplayLength() {
		return displayLength;
	}

	public void setDisplayLength(int displayLength) {
		this.displayLength = displayLength;
	}

	public FiltroColumna getFiltroColumna() {
		return filtroColumna;
	}

	public void setFiltroColumna(FiltroColumna filtroColumna) {
		this.filtroColumna = filtroColumna;
	}

	public String getSearchColumnaDocumento() {
		return searchColumnaDocumento;
	}

	public void setSearchColumnaDocumento(String searchColumnaDocumento) {
		this.searchColumnaDocumento = searchColumnaDocumento;
	}

	public String getSearchColumnaStatus() {
		return searchColumnaStatus;
	}

	public void setSearchColumnaStatus(String searchColumnaStatus) {
		this.searchColumnaStatus = searchColumnaStatus;
	}

	public SsoVwUsuarioDTO getFiltroUsuarioSession() {
		return filtroUsuarioSession;
	}

	public void setFiltroUsuarioSession(SsoVwUsuarioDTO filtroUsuarioSession) {
		this.filtroUsuarioSession = filtroUsuarioSession;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
