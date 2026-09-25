package mx.gob.imss.ctirss.delta.model.derechohabiente.dto.externo;

import java.io.Serializable;



public class ConsultorioExternoDto  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long idUmfConsultorioTurnoMedico;
	private Long idConsultorio;
	private String descripcion;
	private Long poblacion;
	private Integer consultorioVirtual;

	public ConsultorioExternoDto() {
		
	}
	
	public ConsultorioExternoDto(Long idConsultorio) {
		this.idConsultorio = idConsultorio;
	}
	
	public ConsultorioExternoDto(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public ConsultorioExternoDto(Long idConsultorio, String descripcion) {
		this.idConsultorio = idConsultorio;
		this.descripcion = descripcion;
	}
	
	public ConsultorioExternoDto(Long idConsultorio, String descripcion, Long poblacion) {
		this.idConsultorio = idConsultorio;
		this.descripcion = descripcion;
		this.poblacion = poblacion;
	}
	
	public ConsultorioExternoDto(Long idConsultorio, String descripcion, Long poblacion, Long idUmfConsultorioTurnoMedico) {
		this.idConsultorio = idConsultorio;
		this.descripcion = descripcion;
		this.poblacion = poblacion;
		this.idUmfConsultorioTurnoMedico = idUmfConsultorioTurnoMedico;
	}
	
	/**
	 * Gets the value of the idConsultorio property.
	 * 
	 * @return possible object is {@link Long }
	 * 
	 */
	public Long getIdConsultorio() {
		return idConsultorio;
	}

	/**
	 * Sets the value of the idConsultorio property.
	 * 
	 * @param value
	 *            allowed object is {@link Long }
	 * 
	 */
	public void setIdConsultorio(Long value) {
		this.idConsultorio = value;
	}

	/**
	 * Gets the value of the descripcion property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Sets the value of the descripcion property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setDescripcion(String value) {
		this.descripcion = value;
	}
	
	public Long getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(Long poblacion) {
		this.poblacion = poblacion;
	}

	public Long getIdUmfConsultorioTurnoMedico() {
		return idUmfConsultorioTurnoMedico;
	}

	public void setIdUmfConsultorioTurnoMedico(Long idUmfConsultorioTurnoMedico) {
		this.idUmfConsultorioTurnoMedico = idUmfConsultorioTurnoMedico;
	}
	
	
	public Integer getConsultorioVirtual() {
		return consultorioVirtual;
	}

	public void setConsultorioVirtual(Integer consultorioVirtual) {
		this.consultorioVirtual = consultorioVirtual;
	}

	@Override
	public String toString() {
		String consultorio = "";
		
		consultorio += "El consultorio es; idConsultorio: " + this.idConsultorio + ", descripcion: " + this.descripcion + ", poblacion: " + this.poblacion
				+ " y id de la relacion UMF-Consultorio-Turno-Medico: " + this.idUmfConsultorioTurnoMedico;
		
		return consultorio;
	}
	
}