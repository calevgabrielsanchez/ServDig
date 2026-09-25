package mx.gob.imss.cit.cda.web.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DatosContacto implements Serializable {

    private static final long serialVersionUID = -1396008021139392166L;
    private String telefonoFijo;
    private String telefonoCelular;
    private String correoElectronico;

    public DatosContacto() {

    }

    public DatosContacto(String telefonoFijo, String telefonoCelular) {
        super();
        this.telefonoFijo = telefonoFijo;
        this.telefonoCelular = telefonoCelular;
    }

    public String getTelefonoFijo() {
        return telefonoFijo;
    }

    public void setTelefonoFijo(String telefonoFijo) {
        this.telefonoFijo = telefonoFijo;
    }

    public String getTelefonoCelular() {
        return telefonoCelular;
    }

    public void setTelefonoCelular(String telefonoCelular) {
        this.telefonoCelular = telefonoCelular;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    @Override
    public String toString() {
        return "DatosContacto [telefonoFijo=" + telefonoFijo
                + ", telefonoCelular=" + telefonoCelular
                + ", correoElectronico=" + correoElectronico + "]";
    }

}
