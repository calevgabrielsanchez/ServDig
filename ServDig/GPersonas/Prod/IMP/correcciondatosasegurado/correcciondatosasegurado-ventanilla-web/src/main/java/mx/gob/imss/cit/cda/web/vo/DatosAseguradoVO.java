package mx.gob.imss.cit.cda.web.vo;

import java.io.Serializable;
import java.util.Date;

public class DatosAseguradoVO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String curp;
	private String primerApellido;
	private String segundoApellido;
	private String sexo;
	private Date fechaNacimiento;
	private String lugarNacimiento;
	private String nacionalidad;
	private String nombre;
	private String entidad;
	private String municipio;
	private int anio;
	private int libro;
	private int tomo;
	private String foja;
	private int acta;
	private String crip;
	
	
	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
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
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public String getLugarNacimiento() {
		return lugarNacimiento;
	}
	public void setLugarNacimiento(String lugarNacimiento) {
		this.lugarNacimiento = lugarNacimiento;
	}
	public String getNacionalidad() {
		return nacionalidad;
	}
	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getEntidad() {
		return entidad;
	}
	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}
	public int getAnio() {
		return anio;
	}
	public void setAnio(int anio) {
		this.anio = anio;
	}
	public int getLibro() {
		return libro;
	}
	public void setLibro(int libro) {
		this.libro = libro;
	}
	public int getTomo() {
		return tomo;
	}
	public void setTomo(int tomo) {
		this.tomo = tomo;
	}
	public String getFoja() {
		return foja;
	}
	public void setFoja(String foja) {
		this.foja = foja;
	}
	public int getActa() {
		return acta;
	}
	public void setActa(int acta) {
		this.acta = acta;
	}
	public String getCrip() {
		return crip;
	}
	public void setCrip(String crip) {
		this.crip = crip;
	}
	public String getMunicipio() {
		return municipio;
	}
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}
	
	
}
