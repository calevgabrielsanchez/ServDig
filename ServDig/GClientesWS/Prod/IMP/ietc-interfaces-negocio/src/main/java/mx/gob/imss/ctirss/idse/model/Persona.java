package mx.gob.imss.ctirss.idse.model;



public class Persona implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	private String rfc;
	private String nombreRazonSocial;
	private String nombreUsuario;
	private String curp;
	private String domicilioFiscal;
	private String correoElectronico;
	private Certificado certificado;	
	private int tipoPersona;
	
	//Atributo de apoyo para procesar movimiento.
	private Long cvePersona;
	


	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getNombreRazonSocial() {
		return nombreRazonSocial;
	}
	public void setNombreRazonSocial(String nombreRazonSocial) {
		this.nombreRazonSocial = nombreRazonSocial;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
	}
	public String getDomicilioFiscal() {
		return domicilioFiscal;
	}
	public void setDomicilioFiscal(String domicilioFiscal) {
		this.domicilioFiscal = domicilioFiscal;
	}
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	public Certificado getCertificado() {
		return certificado;
	}
	public void setCertificado(Certificado certificado) {
		this.certificado = certificado;
	}
	public int getTipoPersona() {
		return tipoPersona;
	}
	public void setTipoPersona(int tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	
	public Long getCvePersona() {
		return cvePersona;
	}
	public void setCvePersona(Long cvePersona) {
		this.cvePersona = cvePersona;
	}
}
