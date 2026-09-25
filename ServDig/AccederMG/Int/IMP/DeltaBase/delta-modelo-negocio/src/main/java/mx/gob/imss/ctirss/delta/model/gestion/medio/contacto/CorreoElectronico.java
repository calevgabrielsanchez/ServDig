/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:CorreoElectronico.java
 *  @Paquete:mx.gob.imss.ctirss.delta.model.gestion.medio.contacto
 *  @Fecha:03/05/2012
 */
package mx.gob.imss.ctirss.delta.model.gestion.medio.contacto;

/**
 * @author Lucio Duran Silva
 *
 */
public class CorreoElectronico extends MedioContacto {
	
	public CorreoElectronico() {
		// TODO Auto-generated constructor stub
	}
	
	public CorreoElectronico(String correo) {
		this.setTipoMedioContacto(new  TipoMedioContacto());
		this.getTipoMedioContacto().setIdTipoMedioContacto(TipoMedioContacto.TIPO_CORREO_ELECTRONICO);
		this.correo=correo;
		this.setDesFormaContacto(correo);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CorreoElectronico [correo=");
		builder.append(correo);
		builder.append("]");
		return builder.toString();
	}

	private String correo;

	/**
	 * @return the correo
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * @param correo the correo to set
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	

}
