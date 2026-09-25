package mx.gob.imss.digital.modelo.medio.contacto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * numero telefonico fijo
 * 
 * @author NOVUTECK1
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "telefonoFijo", namespace = "http://mx.gob.imss.digital.modelo.medio.contacto")
@XmlRootElement(name = "telefonoFijo", namespace = "http://mx.gob.imss.digital.modelo.medio.contacto")
public class TelefonoFijo extends MedioContacto {

    /**
     * Serial version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * El numero del telefono fijo
     */
    private String numero;

    /**
     * Clave de larga distancia
     */
    private String claveLada;

    /**
     * Extension del telefono.
     */
    private String extension;

    /**
	 *  
	 */
    public TelefonoFijo() {

    }

    /**
     * 
     * @param numero
     * @param claveLada
     * @param ext
     */
    public TelefonoFijo(String numero, String claveLada, String ext) {
        this.numero = numero;
        this.claveLada = claveLada;
        this.extension = ext;

        this.setTipoMedioContacto(new TipoMedioContacto());
        this.getTipoMedioContacto().setIdTipoMedioContacto(TipoMedioContacto.TIPO_TELEFONO_FIJO);

    }

    /**
     * @return the numero
     */
    public String getNumero() {
        return numero;
    }

    /**
     * @param numero
     *            the numero to set
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * @return the extension
     */
    public String getExtension() {
        return extension;
    }

    /**
     * @param extension
     *            the extension to set
     */
    public void setExtension(String extension) {
        this.extension = extension;
    }

    /**
     * @return the claveLada
     */
    public String getClaveLada() {
        return claveLada;
    }

    /**
     * @param claveLada
     *            the claveLada to set
     */
    public void setClaveLada(String claveLada) {
        this.claveLada = claveLada;
    }

    /**
     * 
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("TelefonoFijo [numero=");
        builder.append(numero);
        builder.append(", claveLada=");
        builder.append(claveLada);
        builder.append(", extension=");
        builder.append(extension);
        builder.append("]");
        return builder.toString();
    }

}
