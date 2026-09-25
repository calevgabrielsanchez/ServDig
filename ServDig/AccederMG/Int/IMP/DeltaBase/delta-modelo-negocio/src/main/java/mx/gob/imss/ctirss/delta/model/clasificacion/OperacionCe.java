/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Leticia Torres
 *  @Proyecto: delta
 *  @Archivo: OperacionCe.java
 *  @Paquete: mx.gob.imss.ctirss.delta.model.clasificacion
 *  @Fecha: 21/10/2012
 */
package mx.gob.imss.ctirss.delta.model.clasificacion;

import java.io.Serializable;

public class OperacionCe implements Serializable {

	/** Serial version */
	private static final long serialVersionUID = -4772288111951805743L;

	private String nombreAtributo;
	private Integer indUsuario;
	private String cveIdUsuario;

	/**
	 * Constructor
	 */
	public OperacionCe() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor con campos
	 * 
	 * @param nombreAtributo
	 * @param indUsuario
	 * @param cveIdUsuario
	 */
	public OperacionCe(final String nombreAtributo, final Integer indUsuario,
			final String cveIdUsuario) {
		super();
		this.nombreAtributo = nombreAtributo;
		this.indUsuario = indUsuario;
		this.cveIdUsuario = cveIdUsuario;
	}

	/**
	 * @return the nombreAtributo
	 */
	public String getNombreAtributo() {
		return nombreAtributo;
	}

	/**
	 * @param nombreAtributo
	 *            the nombreAtributo to set
	 */
	public void setNombreAtributo(final String nombreAtributo) {
		this.nombreAtributo = nombreAtributo;
	}

	/**
	 * @return the indUsuario
	 */
	public Integer getIndUsuario() {
		return indUsuario;
	}

	/**
	 * @param indUsuario
	 *            the indUsuario to set
	 */
	public void setIndUsuario(final Integer indUsuario) {
		this.indUsuario = indUsuario;
	}

	/**
	 * @return the cveIdUsuario
	 */
	public String getCveIdUsuario() {
		return cveIdUsuario;
	}

	/**
	 * @param cveIdUsuario
	 *            the cveIdUsuario to set
	 */
	public void setCveIdUsuario(final String cveIdUsuario) {
		this.cveIdUsuario = cveIdUsuario;
	}

}
