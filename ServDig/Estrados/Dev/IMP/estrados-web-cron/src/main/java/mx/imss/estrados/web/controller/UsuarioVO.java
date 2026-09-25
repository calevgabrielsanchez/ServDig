package mx.imss.estrados.web.controller;



import org.apache.log4j.Logger;


public class UsuarioVO {
	
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
