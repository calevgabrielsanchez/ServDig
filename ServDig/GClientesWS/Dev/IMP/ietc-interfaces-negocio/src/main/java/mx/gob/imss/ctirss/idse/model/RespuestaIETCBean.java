package mx.gob.imss.ctirss.idse.model;

public class RespuestaIETCBean implements java.io.Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = -7162021157242969053L;

	private int codigoRespuesta;

	private java.lang.String descripcionRespuesta;

	public RespuestaIETCBean() {
	}

	public RespuestaIETCBean(final int codigoRespuesta, final java.lang.String descripcionRespuesta) {
		this.codigoRespuesta = codigoRespuesta;
		this.descripcionRespuesta = descripcionRespuesta;
	}

	/**
	 * Gets the codigoRespuesta value for this RespuestaIETCBean.
	 * 
	 * @return codigoRespuesta
	 */
	public int getCodigoRespuesta() {
		return codigoRespuesta;
	}

	/**
	 * Sets the codigoRespuesta value for this RespuestaIETCBean.
	 * 
	 * @param codigoRespuesta
	 */
	public void setCodigoRespuesta(final int codigoRespuesta) {
		this.codigoRespuesta = codigoRespuesta;
	}

	/**
	 * Gets the descripcionRespuesta value for this RespuestaIETCBean.
	 * 
	 * @return descripcionRespuesta
	 */
	public java.lang.String getDescripcionRespuesta() {
		return descripcionRespuesta;
	}

	/**
	 * Sets the descripcionRespuesta value for this RespuestaIETCBean.
	 * 
	 * @param descripcionRespuesta
	 */
	public void setDescripcionRespuesta(final java.lang.String descripcionRespuesta) {
		this.descripcionRespuesta = descripcionRespuesta;
	}

}
