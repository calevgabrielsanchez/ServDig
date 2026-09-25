package mx.gob.imss.digital.modelo.medio.contacto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Cuenta de correo electronico asociada a auna persona
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "correoElectronico", namespace = "http://mx.gob.imss.digital.modelo.medio.contacto")
@XmlRootElement(name = "correoElectronico", namespace = "http://mx.gob.imss.digital.modelo.medio.contacto")
public class CorreoElectronico extends MedioContacto {
	
	/**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * constructor or defecto
     */
    public CorreoElectronico() {
	}
	/**
	 * Correo electronico
	 */
    private String correo;
    /**
     * 
     * @param correo
     */
	public CorreoElectronico(String correo) {
		this.setTipoMedioContacto(new  TipoMedioContacto());
		this.getTipoMedioContacto().setIdTipoMedioContacto(TipoMedioContacto.TIPO_CORREO_ELECTRONICO);
		this.correo=correo;
		this.setDesFormaContacto(correo);
	}
	
	/**
	 * 
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CorreoElectronico [correo=");
		builder.append(correo);
		builder.append("]");
		return builder.toString();
	}

	

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
