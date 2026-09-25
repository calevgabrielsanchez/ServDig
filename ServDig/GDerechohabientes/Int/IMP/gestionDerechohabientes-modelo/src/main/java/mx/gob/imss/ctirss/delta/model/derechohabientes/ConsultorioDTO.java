package mx.gob.imss.ctirss.delta.model.derechohabientes;

import java.io.Serializable;
import java.math.BigDecimal;



public class ConsultorioDTO  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected BigDecimal idConsultorio;
	protected String descripcion;
	protected BigDecimal poblacion;
	protected BigDecimal idUmfConsultorioTurnoMed;

	
	public ConsultorioDTO() {
		
	}
	
	public ConsultorioDTO(BigDecimal idConsultorio) {
		this.idConsultorio = idConsultorio;
	}
	
	public ConsultorioDTO(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public ConsultorioDTO(BigDecimal idConsultorio, String descripcion) {
		this.idConsultorio = idConsultorio;
		this.descripcion = descripcion;
	}
	
	public ConsultorioDTO(BigDecimal idConsultorio, String descripcion, BigDecimal poblacion) {
		this.idConsultorio = idConsultorio;
		this.descripcion = descripcion;
		this.poblacion = poblacion;
	}
	
	public ConsultorioDTO(BigDecimal idConsultorio, String descripcion, BigDecimal poblacion, BigDecimal idUmfConsultorioTurnoMed) {
		this.idConsultorio = idConsultorio;
		this.descripcion = descripcion;
		this.poblacion = poblacion;
		this.idUmfConsultorioTurnoMed = idUmfConsultorioTurnoMed;
	}
	
	/**
	 * Gets the value of the idConsultorio property.
	 * 
	 * @return possible object is {@link Long }
	 * 
	 */
	public BigDecimal getIdConsultorio() {
		return idConsultorio;
	}

	/**
	 * Sets the value of the idConsultorio property.
	 * 
	 * @param value
	 *            allowed object is {@link Long }
	 * 
	 */
	public void setIdConsultorio(BigDecimal value) {
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
	

	public BigDecimal getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(BigDecimal poblacion) {
		this.poblacion = poblacion;
	}

	public BigDecimal getIdUmfConsultorioTurnoMed() {
		return idUmfConsultorioTurnoMed;
	}

	public void setIdUmfConsultorioTurnoMed(BigDecimal idUmfConsultorioTurnoMed) {
		this.idUmfConsultorioTurnoMed = idUmfConsultorioTurnoMed;
	}
	
}