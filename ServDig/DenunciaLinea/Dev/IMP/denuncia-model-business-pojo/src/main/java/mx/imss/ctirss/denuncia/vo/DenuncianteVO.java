package mx.imss.ctirss.denuncia.vo;

import java.io.Serializable;




public class DenuncianteVO implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private boolean tieneNss;
	private String nss;
	private String curp;
	private String rfc;
	private String email;
	private String telefonoContacto;
	private String telefonoCelular;
	private int docOficial;
	private String nombreDocumento;
	private String numeroDocumento;
	private Long idDomicilio;
	private String desDomicilio;
	private Integer sexoTrabajador;

	public Integer getSexoTrabajador() {
		return sexoTrabajador;
	}

	public void setSexoTrabajador(Integer sexoTrabajador) {
		this.sexoTrabajador = sexoTrabajador;
	} 
	
		
	public String getNombre() {
		return nombre!=null ? nombre.toUpperCase():nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidoPaterno() {
		return apellidoPaterno!=null ? apellidoPaterno.toUpperCase():apellidoPaterno;
	}
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	public String getApellidoMaterno() {
		return apellidoMaterno!=null ? apellidoMaterno.toUpperCase():apellidoMaterno;
	}
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	public boolean isTieneNss() {
		return tieneNss;
	}
	public void setTieneNss(boolean tieneNss) {
		this.tieneNss = tieneNss;
	}
	public String getNss() {
		return nss!=null ?nss.toUpperCase():nss;
	}
	public void setNss(String nss) {
		this.nss = nss;
	}
	public String getCurp() {
		return curp!=null? curp.toUpperCase():curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
	}
	public String getRfc() {
		return rfc!=null ?rfc.toUpperCase():rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefonoContacto() {
		return telefonoContacto!=null ? telefonoContacto.toUpperCase():telefonoContacto;
	}
	public void setTelefonoContacto(String telefonoContacto) {
		this.telefonoContacto = telefonoContacto;
	}
	public String getTelefonoCelular() {
		return telefonoCelular!=null ? telefonoCelular.toUpperCase():telefonoCelular;
	}
	public void setTelefonoCelular(String telefonoCelular) {
		this.telefonoCelular = telefonoCelular;
	}
	public int getDocOficial() {
		return docOficial;
	}
	public void setDocOficial(int docOficial) {
		this.docOficial = docOficial;
	}
	public String getNumeroDocumento() {
		return numeroDocumento!=null? numeroDocumento.toUpperCase():numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
		
	public Long getIdDomicilio() {
		return idDomicilio;
	}
	public void setIdDomicilio(Long idDomicilio) {
		this.idDomicilio = idDomicilio;
	}
		
	public String getNombreDocumento() {
		return nombreDocumento!=null ?nombreDocumento.toUpperCase():nombreDocumento;
	}
	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}
		
	public String getDesDomicilio() {
		return desDomicilio!=null ? desDomicilio.toUpperCase():desDomicilio;
	}
	public void setDesDomicilio(String desDomicilio) {
		this.desDomicilio = desDomicilio;
	}
	

	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DenuncianteVO [nombre=");
		builder.append(nombre);
		builder.append(", apellidoPaterno=");
		builder.append(apellidoPaterno);
		builder.append(", apellidoMaterno=");
		builder.append(apellidoMaterno);
		builder.append(", tieneNss=");
		builder.append(tieneNss);
		builder.append(", nss=");
		builder.append(nss);
		builder.append(", curp=");
		builder.append(curp);
		builder.append(", rfc=");
		builder.append(rfc);
		builder.append(", email=");
		builder.append(email);
		builder.append(", telefonoContacto=");
		builder.append(telefonoContacto);
		builder.append(", telefonoCelular=");
		builder.append(telefonoCelular);
		builder.append(", docOficial=");
		builder.append(docOficial);
		builder.append(", numeroDocumento=");
		builder.append(numeroDocumento);
		//builder.append(", sexoTrabajador=");
		//builder.append(sexoTrabajador);
		builder.append("]");
		return builder.toString();
	}
	
	
}
