package mx.gob.imss.ctirss.delta.model.gestion.patronal;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

@XmlRootElement
public class Fraccion extends AbstractModel {

	/**
	 *
	 */
	private static final long serialVersionUID = 4810671069135046043L;

	private Long id;
	private String descripcion;
	private String descripcionDetallada;
	private Grupo grupo;
	private Clase clase;
	// TODO definir si debe estar en Fraccion o en Clase
	private BigDecimal indRPC;
	private BigDecimal primaSRT;
	private String numFraccion;

	/**
	 * @return the primaSRT
	 */
	public BigDecimal getPrimaSRT() {
		return primaSRT;
	}

	/**
	 * @param primaSRT
	 *            the primaSRT to set
	 */
	public void setPrimaSRT(BigDecimal primaSRT) {
		this.primaSRT = primaSRT;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion
	 *            the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the descripcionDetallada
	 */
	public String getDescripcionDetallada() {
		return descripcionDetallada;
	}

	/**
	 * @param descripcionDetallada
	 *            the descripcionDetallada to set
	 */
	public void setDescripcionDetallada(String descripcionDetallada) {
		this.descripcionDetallada = descripcionDetallada;
	}

	/**
	 * @return the grupo
	 */
	public Grupo getGrupo() {
		return grupo;
	}

	/**
	 * @param grupo
	 *            the grupo to set
	 */
	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	/**
	 * @return the clase
	 */
	public Clase getClase() {
		return clase;
	}

	/**
	 * @param clase
	 *            the clase to set
	 */
	public void setClase(Clase clase) {
		this.clase = clase;
	}

	public String getNumFraccion() {
		return numFraccion;
	}

	public void setNumFraccion(String numFraccion) {
		this.numFraccion = numFraccion;
	}

	public BigDecimal getIndRPC() {
		return indRPC;
	}

	public void setIndRPC(BigDecimal indRPC) {
		this.indRPC = indRPC;
	}
}
