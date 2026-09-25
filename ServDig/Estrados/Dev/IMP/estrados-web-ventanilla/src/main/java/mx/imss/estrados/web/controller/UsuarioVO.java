package mx.imss.estrados.web.controller;



import java.io.Serializable;
import java.util.Date;

import org.apache.log4j.Logger;


public class UsuarioVO implements Serializable {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2405450200581900632L;


	/**
	 * Logger
	 */
	private final static Logger logger = Logger.getLogger(UsuarioVO.class);

	
	private String nombre;
	
	private String aPaterno;
	
	private String aMaterno;
	
	private String uid;
	
	private String correo;
	
	private String subDeleg;
	
	private String deleg;
	
	private String perfil;
	
	private String usuario;
	
	private Integer idCombo;
	
	private Date fechaEjecuta;
	
	private Integer numRegistros;
	
	private Integer numRegEjecutados;
	
	public Integer getNumRegEjecutados() {
		return numRegEjecutados;
	}

	public void setNumRegEjecutados(Integer numRegEjecutados) {
		this.numRegEjecutados = numRegEjecutados;
	}

	public Integer getNumRegistros() {
		return numRegistros;
	}

	public void setNumRegistros(Integer numRegistros) {
		this.numRegistros = numRegistros;
	}

	public Integer getIdCombo() {
		return idCombo;
	}

	public void setIdCombo(Integer idCombo) {
		this.idCombo = idCombo;
	}

	public Date getFechaEjecuta() {
		return fechaEjecuta;
	}

	public void setFechaEjecuta(Date fechaEjecuta) {
		this.fechaEjecuta = fechaEjecuta;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getaPaterno() {
		return aPaterno;
	}

	public void setaPaterno(String aPaterno) {
		this.aPaterno = aPaterno;
	}

	public String getaMaterno() {
		return aMaterno;
	}

	public void setaMaterno(String aMaterno) {
		this.aMaterno = aMaterno;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getSubDeleg() {
		return subDeleg;
	}

	public void setSubDeleg(String subDeleg) {
		this.subDeleg = subDeleg;
	}

	public String getDeleg() {
		return deleg;
	}

	public void setDeleg(String deleg) {
		this.deleg = deleg;
	}

	public static Logger getLogger() {
		return logger;
	}
}
