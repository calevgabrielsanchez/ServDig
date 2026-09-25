package mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Persona implements Serializable {
	
	
	private static final long serialVersionUID = 1060112370829299206L;
	
	private String nombre;
	private String primerApellido;
	private String segundoApellido;
	private Date fechaNacimiento;
	private Date fechaDefuncion;
	private String curp;
    private String nss;
    private String rfc;
    private Long cveIdPersona;
    private Sexo sexo;
    private EntidadFederativa lugarNacimiento;
	private EstadoCivil estadoCivil;
    private DatosPersonaRenapo datosPersonaRenapo;
    private Pais pais;
	
	public Pais getPais() {
		return pais;
	}
	public void setPais(Pais pais) {
		this.pais = pais;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getPrimerApellido() {
		return primerApellido;
	}
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}
	public String getSegundoApellido() {
		return segundoApellido;
	}
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public Date getFechaDefuncion() {
		return fechaDefuncion;
	}
	public void setFechaDefuncion(Date fechaDefuncion) {
		this.fechaDefuncion = fechaDefuncion;
	}
	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
	}
	public String getNss() {
		return nss;
	}
	public void setNss(String nss) {
		this.nss = nss;
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public Long getCveIdPersona() {
		return cveIdPersona;
	}
	public void setCveIdPersona(Long cveIdPersona) {
		this.cveIdPersona = cveIdPersona;
	}
	public Sexo getSexo() {
		return sexo;
	}
	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}
	public EntidadFederativa getLugarNacimiento() {
		return lugarNacimiento;
	}
	public void setLugarNacimiento(EntidadFederativa lugarNacimiento) {
		this.lugarNacimiento = lugarNacimiento;
	}
	public EstadoCivil getEstadoCivil() {
		return estadoCivil;
	}
	public void setEstadoCivil(EstadoCivil estadoCivil) {
		this.estadoCivil = estadoCivil;
	}
	public DatosPersonaRenapo getDatosPersonaRenapo() {
		return datosPersonaRenapo;
	}
	public void setDatosPersonaRenapo(DatosPersonaRenapo datosPersonaRenapo) {
		this.datosPersonaRenapo = datosPersonaRenapo;
	}

   
   


}
