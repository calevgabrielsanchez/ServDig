package mx.gob.imss.ctirss.sso.admonusuarios.dto;

import java.io.Serializable;


/**
 * Clase para crear objetos de transferencia que representan un perfil dentro del sistema Delta
 * @author Horacio Oswaldo Ferro Díaz
 * @version 1.0
 */
public class PerfilDTO implements Serializable {
	/** UID serial de versión */
	private static final long serialVersionUID = -8397978586150561116L;
	
	private long cveSsoperfilessol;
	
	private PuestoDTO puestoDTO;
	
	private AreaNormativaDTO areaNormDTO;
	
	private DepartamentoDTO deptoDTO;
	
	private SolicitudDTO solicitudDTO;
	
	private String desDefault;
	
	public PerfilDTO(){
		puestoDTO = new PuestoDTO();
		areaNormDTO = new AreaNormativaDTO();
		deptoDTO = new DepartamentoDTO();
		solicitudDTO = new SolicitudDTO();
	}
	
	public String datosBitacora(){
		String cadena = "idperfilsol:"+ this.cveSsoperfilessol + "|" +
				"puesto:" + this.puestoDTO.getNombrePuesto() + "area:" + this.areaNormDTO.getDesAreanorma() + "|" +
				"departamento:" + this.getDeptoDTO().getDesDepartamento();
		return cadena;
	}
	
	public long getCveSsoperfilessol() {
		return cveSsoperfilessol;
	}

	public void setCveSsoperfilessol(long cveSsoperfilessol) {
		this.cveSsoperfilessol = cveSsoperfilessol;
	}

	public PuestoDTO getPuestoDTO() {
		return puestoDTO;
	}

	public void setPuestoDTO(PuestoDTO ssoCatpuesto) {
		this.puestoDTO = ssoCatpuesto;
	}

	public AreaNormativaDTO getAreaNormDTO() {
		return areaNormDTO;
	}

	public void setAreaNormDTO(AreaNormativaDTO areaNormDTO) {
		this.areaNormDTO = areaNormDTO;
	}

	public DepartamentoDTO getDeptoDTO() {
		return deptoDTO;
	}

	public void setDeptoDTO(DepartamentoDTO deptoDTO) {
		this.deptoDTO = deptoDTO;
	}

	public SolicitudDTO getSolicitudDTO() {
		return solicitudDTO;
	}

	public void setSolicitudDTO(SolicitudDTO solicitudDTO) {
		this.solicitudDTO = solicitudDTO;
	}

	public String getDesDefault() {
		return desDefault;
	}

	public void setDesDefault(String desDefault) {
		this.desDefault = desDefault;
	}	

}
