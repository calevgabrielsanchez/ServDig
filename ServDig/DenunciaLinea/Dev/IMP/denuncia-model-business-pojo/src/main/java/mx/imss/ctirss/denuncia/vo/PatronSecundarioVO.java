package mx.imss.ctirss.denuncia.vo;

import java.io.Serializable;

public class PatronSecundarioVO  implements Serializable{

	private int idRow;
	private int cveDenuncia;
	private String rfc;
	private String nombreRazonSocial;
	private String cveDomicilio;
	private String descDomicilio;
	
	
	public int getCveDenuncia() {
		return cveDenuncia;
	}
	public void setCveDenuncia(int cveDenuncia) {
		this.cveDenuncia = cveDenuncia;
	}
	public String getRfc() {
		return rfc!=null ? rfc.toUpperCase() :rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public String getNombreRazonSocial() {
		return nombreRazonSocial!=null ? nombreRazonSocial.toUpperCase() :nombreRazonSocial;
	}
	public void setNombreRazonSocial(String nombreRazonSocial) {
		this.nombreRazonSocial = nombreRazonSocial;
	}

	public int getIdRow() {
		return idRow;
	}
	public void setIdRow(int idRow) {
		this.idRow = idRow;
	}
	public String getCveDomicilio() {
		return cveDomicilio;
	}
	public void setCveDomicilio(String cveDomicilio) {
		this.cveDomicilio = cveDomicilio;
	}
	public String getDescDomicilio() {
		return descDomicilio;
	}
	public void setDescDomicilio(String descDomicilio) {
		this.descDomicilio = descDomicilio;
	}
	
}
