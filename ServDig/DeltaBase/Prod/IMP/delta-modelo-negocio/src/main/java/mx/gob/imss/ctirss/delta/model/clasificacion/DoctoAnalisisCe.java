/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Leticia Torres
 *  @Proyecto: delta
 *  @Archivo: DoctoAnalisisCe.java
 *  @Paquete: mx.gob.imss.ctirss.delta.model.clasificacion
 *  @Fecha: 01/11/2012
 */
package mx.gob.imss.ctirss.delta.model.clasificacion;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class DoctoAnalisisCe extends AbstractModel {

	/** Serial version */
	private static final long serialVersionUID = 5071471647336181268L;

	private String cveIdUsuario;
	private Integer cveIdRol;
	private Long cveIdDoctoAnalisisCe;
	private Long cveIdAnalisis;
	private Long cveIdSolicitud;
	private Long cveTipoDoctoAnalisisCe;
	private byte[] refDocumento;

	/**
	 * Constructor
	 */
	public DoctoAnalisisCe() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor con campos
	 * 
	 * @param cveIdDoctoAnalisisCe
	 * @param cveIdAnalisis
	 * @param cveTipoDoctoAnalisisCe
	 * @param refDocumento
	 */
	public DoctoAnalisisCe(final Long cveIdDoctoAnalisisCe,
			final Long cveIdAnalisis, final Long cveTipoDoctoAnalisisCe,
			final byte[] refDocumento) {
		super();
		this.cveIdDoctoAnalisisCe = cveIdDoctoAnalisisCe;
		this.cveIdAnalisis = cveIdAnalisis;
		this.cveTipoDoctoAnalisisCe = cveTipoDoctoAnalisisCe;
		this.refDocumento = refDocumento;
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

	/**
	 * @return the cveIdRol
	 */
	public Integer getCveIdRol() {
		return cveIdRol;
	}

	/**
	 * @param cveIdRol
	 *            the cveIdRol to set
	 */
	public void setCveIdRol(final Integer cveIdRol) {
		this.cveIdRol = cveIdRol;
	}

	/**
	 * @return the cveIdDoctoAnalisisCe
	 */
	public Long getCveIdDoctoAnalisisCe() {
		return cveIdDoctoAnalisisCe;
	}

	/**
	 * @param cveIdDoctoAnalisisCe
	 *            the cveIdDoctoAnalisisCe to set
	 */
	public void setCveIdDoctoAnalisisCe(final Long cveIdDoctoAnalisisCe) {
		this.cveIdDoctoAnalisisCe = cveIdDoctoAnalisisCe;
	}

	/**
	 * @return the cveIdAnalisis
	 */
	public Long getCveIdAnalisis() {
		return cveIdAnalisis;
	}

	/**
	 * @param cveIdAnalisis
	 *            the cveIdAnalisis to set
	 */
	public void setCveIdAnalisis(final Long cveIdAnalisis) {
		this.cveIdAnalisis = cveIdAnalisis;
	}

	/**
	 * @return the cveIdSolicitud
	 */
	public Long getCveIdSolicitud() {
		return cveIdSolicitud;
	}

	/**
	 * @param cveIdSolicitud
	 *            the cveIdSolicitud to set
	 */
	public void setCveIdSolicitud(final Long cveIdSolicitud) {
		this.cveIdSolicitud = cveIdSolicitud;
	}

	/**
	 * @return the cveTipoDoctoAnalisisCe
	 */
	public Long getCveTipoDoctoAnalisisCe() {
		return cveTipoDoctoAnalisisCe;
	}

	/**
	 * @param cveTipoDoctoAnalisisCe
	 *            the cveTipoDoctoAnalisisCe to set
	 */
	public void setCveTipoDoctoAnalisisCe(final Long cveTipoDoctoAnalisisCe) {
		this.cveTipoDoctoAnalisisCe = cveTipoDoctoAnalisisCe;
	}

	/**
	 * @return the refDocumento
	 */
	public byte[] getRefDocumento() {
		return refDocumento;
	}

	/**
	 * @param refDocumento
	 *            the refDocumento to set
	 */
	public void setRefDocumento(final byte[] refDocumento) {
		this.refDocumento = refDocumento;
	}

}
