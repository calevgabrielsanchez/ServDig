package mx.gob.imss.ctirss.sso.admonusuarios.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase define un objeto de transferencia de datos, este objeto contendrá la información de un usuario,
 * así como, los perfiles y roles asociados.
 * @author Alan René García Rico
 * @version 1.0
 */
public class UsuarioDTO implements Serializable{
	/** Identificador único de la versión de la clase */
	private static   long serialVersionUID = 3515802327994150187L;
	private String nombres;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String uid;
	private String password;
	private String newPassword;
	private boolean activo;
	private String correoElectronico;
	private Integer claveDelegacion;
	private Integer claveSubDelegacion;
	private Integer claveUMF;
	private String curp;
	private String idBdtu;
	private String matricula;
	private Long claveModulo;
	private Long idSolicitud;
	private String confirmarPassword;
	private String nss;
	private Integer claveAreaNormativa;
	private Integer claveDepartamento;
	private Integer clavePuesto;
	private String telefono;
	private List<PuestoDTO> rolesData;
	private List<ModuloDTO> modulosData;
	private String descripcionArea ="";
	private String descripcionDelegacion ="";
	private String descripcionSubDelegacion ="";
	private String descripcionDepartamento ="";
	private String descripcionPuesto ="";
	private String descripcionCargo ="";
	private String descripcionEstatus ="";
	private int numPerfiles = 0;
	private int numModulos = 0;
	private String serial;
	private String modulos;
	private String perfiles;
	
	private String nssNom;
	private String puestoDescNom;
	private String departamentoDescNom;
	private String cveDelegacionNom;
	private String cveSubdelegacionNom;
	private String cveUmfNom;
	private long cveEstatusNom;
	
	
	
	/*
	 * Constructor vacio
	 */
	public UsuarioDTO(){
	}
	/**JLBC PERFILES*/
	public UsuarioDTO(PuestoDTO rol, String nombres, String apellidoPaterno,
			String apellidoMaterno, String uid, String password,
			String newPassword, boolean activo, String correoElectronico,
			Integer claveDelegacion, Integer claveSubDelegacion,
			Integer claveUMF, String curp, String idBdtu,String serial) {
		super();
		this.nombres = nombres;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.uid = uid;
		this.password = password;
		this.newPassword = newPassword;
		this.activo = activo;
		this.correoElectronico = correoElectronico;
		this.claveDelegacion = claveDelegacion;
		this.claveSubDelegacion = claveSubDelegacion;
		this.claveUMF = claveUMF;
		this.curp = curp;
		this.idBdtu = idBdtu;
		this.serial = serial;
	}
	
	public UsuarioDTO(PerfilDTO rol, String nombres, String apellidoPaterno,
			String apellidoMaterno, String uid, String password,
			String newPassword, boolean activo, String correoElectronico,
			Integer claveDelegacion, Integer claveSubDelegacion,
			Integer claveUMF, String curp, String idBdtu,String serial) {
		super();
		this.nombres = nombres;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.uid = uid;
		this.password = password;
		this.newPassword = newPassword;
		this.activo = activo;
		this.correoElectronico = correoElectronico;
		this.claveDelegacion = claveDelegacion;
		this.claveSubDelegacion = claveSubDelegacion;
		this.claveUMF = claveUMF;
		this.curp = curp;
		this.idBdtu = idBdtu;
		this.serial = serial;
	}
	
	public UsuarioDTO(String nombres, String apellidoPaterno,
			String apellidoMaterno, String uid, String password,
			String newPassword, boolean activo, String correoElectronico,
			Integer claveDelegacion, Integer claveSubDelegacion,
			Integer claveUMF, String curp, String idBdtu,String serial) {
		super();
		this.nombres = nombres;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.uid = uid;
		this.password = password;
		this.newPassword = newPassword;
		this.activo = activo;
		this.correoElectronico = correoElectronico;
		this.claveDelegacion = claveDelegacion;
		this.claveSubDelegacion = claveSubDelegacion;
		this.claveUMF = claveUMF;
		this.curp = curp;
		this.idBdtu = idBdtu;
		this.serial = serial;
	}
	
	public UsuarioDTO(String nombres, String apellidoPaterno,
			String apellidoMaterno, String uid, String password,
			String newPassword, boolean activo, String correoElectronico,
			Integer claveDelegacion, Integer claveSubDelegacion,
			Integer claveUMF, String curp, String idBdtu) {
		super();
		this.nombres = nombres;
		this.apellidoPaterno = apellidoPaterno;
		this.apellidoMaterno = apellidoMaterno;
		this.uid = uid;
		this.password = password;
		this.newPassword = newPassword;
		this.activo = activo;
		this.correoElectronico = correoElectronico;
		this.claveDelegacion = claveDelegacion;
		this.claveSubDelegacion = claveSubDelegacion;
		this.claveUMF = claveUMF;
		this.curp = curp;
		this.idBdtu = idBdtu;
	}
	@Override
	public int hashCode() {
		  int prime = 31;
		int result = 1;
		result = prime * result + (activo ? 1231 : 1237);
		result = prime * result
				+ ((apellidoMaterno == null) ? 0 : apellidoMaterno.hashCode());
		result = prime * result
				+ ((apellidoPaterno == null) ? 0 : apellidoPaterno.hashCode());
		result = prime * result
				+ ((claveDelegacion == null) ? 0 : claveDelegacion.hashCode());
		result = prime
				* result
				+ ((claveSubDelegacion == null) ? 0 : claveSubDelegacion
						.hashCode());
		result = prime * result
				+ ((claveUMF == null) ? 0 : claveUMF.hashCode());
		result = prime
				* result
				+ ((correoElectronico == null) ? 0 : correoElectronico
						.hashCode());
		result = prime * result + ((curp == null) ? 0 : curp.hashCode());
		result = prime * result + ((idBdtu == null) ? 0 : idBdtu.hashCode());
		result = prime * result
				+ ((newPassword == null) ? 0 : newPassword.hashCode());
		result = prime * result + ((nombres == null) ? 0 : nombres.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((uid == null) ? 0 : uid.hashCode());
		result = prime * result
				+ ((claveAreaNormativa == null) ? 0 : claveAreaNormativa.hashCode());
		result = prime * result
				+ ((claveDepartamento == null) ? 0 : claveDepartamento.hashCode());
		result = prime * result
				+ ((clavePuesto == null) ? 0 : clavePuesto.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioDTO other = (UsuarioDTO) obj;
		if (activo != other.activo)
			return false;
		if (apellidoMaterno == null) {
			if (other.apellidoMaterno != null)
				return false;
		} else if (!apellidoMaterno.equals(other.apellidoMaterno))
			return false;
		if (apellidoPaterno == null) {
			if (other.apellidoPaterno != null)
				return false;
		} else if (!apellidoPaterno.equals(other.apellidoPaterno))
			return false;
		if (claveDelegacion == null) {
			if (other.claveDelegacion != null)
				return false;
		} else if (!claveDelegacion.equals(other.claveDelegacion))
			return false;
		if (claveSubDelegacion == null) {
			if (other.claveSubDelegacion != null)
				return false;
		} else if (!claveSubDelegacion.equals(other.claveSubDelegacion))
			return false;
		if (claveUMF == null) {
			if (other.claveUMF != null)
				return false;
		} else if (!claveUMF.equals(other.claveUMF))
			return false;
		if (correoElectronico == null) {
			if (other.correoElectronico != null)
				return false;
		} else if (!correoElectronico.equals(other.correoElectronico))
			return false;
		if (curp == null) {
			if (other.curp != null)
				return false;
		} else if (!curp.equals(other.curp))
			return false;
		if (idBdtu == null) {
			if (other.idBdtu != null)
				return false;
		} else if (!idBdtu.equals(other.idBdtu))
			return false;
		if (newPassword == null) {
			if (other.newPassword != null)
				return false;
		} else if (!newPassword.equals(other.newPassword))
			return false;
		if (nombres == null) {
			if (other.nombres != null)
				return false;
		} else if (!nombres.equals(other.nombres))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (uid == null) {
			if (other.uid != null)
				return false;
		} else if (!uid.equals(other.uid))
			return false;
		
		
		if (claveAreaNormativa == null) {
			if (other.claveAreaNormativa != null)
				return false;
		} else if (!claveAreaNormativa.equals(other.claveAreaNormativa))
			return false;
		
		
		if (claveDepartamento == null) {
			if (other.claveDepartamento != null)
				return false;
		} else if (!claveDepartamento.equals(other.claveDepartamento))
			return false;
		
		
		if (clavePuesto == null) {
			if (other.clavePuesto != null)
				return false;
		} else if (!clavePuesto.equals(other.clavePuesto))
			return false;
		
		
		return true;
	}
	public String getNombres() {
		return nombres;
	}
	public void setNombres(String nombres) {
		this.nombres = nombres;
	}
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	public String getCorreoElectronico() {
		return correoElectronico;
	}
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}
	public Integer getClaveDelegacion() {
		return claveDelegacion;
	}
	public void setClaveDelegacion(Integer claveDelegacion) {
		this.claveDelegacion = claveDelegacion;
	}
	public Integer getClaveSubDelegacion() {
		return claveSubDelegacion;
	}
	public void setClaveSubDelegacion(Integer claveSubDelegacion) {
		this.claveSubDelegacion = claveSubDelegacion;
	}
	public Integer getClaveUMF() {
		return claveUMF;
	}
	public void setClaveUMF(Integer claveUMF) {
		this.claveUMF = claveUMF;
	}
	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
	}
	public String getIdBdtu() {
		return idBdtu;
	}
	public void setIdBdtu(String idBdtu) {
		this.idBdtu = idBdtu;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public Long getClaveModulo() {
		return claveModulo;
	}
	public void setClaveModulo(Long claveModulo) {
		this.claveModulo = claveModulo;
	}
	public Long getIdSolicitud() {
		return idSolicitud;
	}
	public void setIdSolicitud(Long idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
	public String getConfirmarPassword() {
        return confirmarPassword;
    }
    public void setConfirmarPassword(String confirmarPassword) {
        this.confirmarPassword = confirmarPassword;
    }
    public String getNss() {
        return nss;
    }
    public void setNss(String nss) {
        this.nss = nss;
    } 
	public Integer getClaveAreaNormativa() {
		return claveAreaNormativa;
	}
	public void setClaveAreaNormativa(Integer claveAreaNormativa) {
		this.claveAreaNormativa = claveAreaNormativa;
	}
	public Integer getClaveDepartamento() {
		return claveDepartamento;
	}
	public void setClaveDepartamento(Integer claveDepartamento) {
		this.claveDepartamento = claveDepartamento;
	}
	public Integer getClavePuesto() {
		return clavePuesto;
	}
	public void setClavePuesto(Integer clavePuesto) {
		this.clavePuesto = clavePuesto;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public List<PuestoDTO> getRolesData() {
		return rolesData;
	}
	public void setRolesData(List<PuestoDTO> rolesData) {
		this.rolesData = rolesData;
	}
	public List<ModuloDTO> getModulosData() {
		return modulosData;
	}
	public void setModulosData(List<ModuloDTO> modulosData) {
		this.modulosData = modulosData;
	}
	public String getDescripcionArea() {
		return descripcionArea;
	}
	public void setDescripcionArea(String descripcionArea) {
		this.descripcionArea = descripcionArea;
	}
	public String getDescripcionDelegacion() {
		return descripcionDelegacion;
	}
	public void setDescripcionDelegacion(String descripcionDelegacion) {
		this.descripcionDelegacion = descripcionDelegacion;
	}
	public String getDescripcionSubDelegacion() {
		return descripcionSubDelegacion;
	}
	public void setDescripcionSubDelegacion(String descripcionSubDelegacion) {
		this.descripcionSubDelegacion = descripcionSubDelegacion;
	}
	public String getDescripcionDepartamento() {
		return descripcionDepartamento;
	}
	public void setDescripcionDepartamento(String descripcionDepartamento) {
		this.descripcionDepartamento = descripcionDepartamento;
	}
	public String getDescripcionPuesto() {
		return descripcionPuesto;
	}
	public void setDescripcionPuesto(String descripcionPuesto) {
		this.descripcionPuesto = descripcionPuesto;
	}
	public String getDescripcionEstatus() {
		return descripcionEstatus;
	}
	public void setDescripcionEstatus(String descripcionEstatus) {
		this.descripcionEstatus = descripcionEstatus;
	}
	public int getNumPerfiles() {
		return numPerfiles;
	}
	public void setNumPerfiles(int numPerfiles) {
		this.numPerfiles = numPerfiles;
	}
	public int getNumModulos() {
		return numModulos;
	}
	public void setNumModulos(int numModulos) {
		this.numModulos = numModulos;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public String getModulos() {
		return modulos;
	}
	public void setModulos(String modulos) {
		this.modulos = modulos;
	}
	public String getPerfiles() {
		return perfiles;
	}
	public void setPerfiles(String perfiles) {
		this.perfiles = perfiles;
	}
	public String getNssNom() {
		return nssNom;
	}
	public void setNssNom(String nssNom) {
		this.nssNom = nssNom;
	}
	public String getPuestoDescNom() {
		return puestoDescNom;
	}
	public void setPuestoDescNom(String puestoDescNom) {
		this.puestoDescNom = puestoDescNom;
	}
	public String getDepartamentoDescNom() {
		return departamentoDescNom;
	}
	public void setDepartamentoDescNom(String departamentoDescNom) {
		this.departamentoDescNom = departamentoDescNom;
	}
	public String getCveDelegacionNom() {
		return cveDelegacionNom;
	}
	public void setCveDelegacionNom(String cveDelegacionNom) {
		this.cveDelegacionNom = cveDelegacionNom;
	}
	public String getCveSubdelegacionNom() {
		return cveSubdelegacionNom;
	}
	public void setCveSubdelegacionNom(String cveSubdelegacionNom) {
		this.cveSubdelegacionNom = cveSubdelegacionNom;
	}
	public String getCveUmfNom() {
		return cveUmfNom;
	}
	public void setCveUmfNom(String cveUmfNom) {
		this.cveUmfNom = cveUmfNom;
	}
	public long getCveEstatusNom() {
		return cveEstatusNom;
	}
	public void setCveEstatusNom(long cveEstatusNom) {
		this.cveEstatusNom = cveEstatusNom;
	}
	public String getDescripcionCargo() {
		return descripcionCargo;
	}
	public void setDescripcionCargo(String descripcionCargo) {
		this.descripcionCargo = descripcionCargo;
	}
	
	
}
