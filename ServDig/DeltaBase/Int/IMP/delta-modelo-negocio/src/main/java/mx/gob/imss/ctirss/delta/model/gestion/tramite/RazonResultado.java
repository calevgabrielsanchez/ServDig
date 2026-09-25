package mx.gob.imss.ctirss.delta.model.gestion.tramite;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "razon-resultado")
public class RazonResultado extends AbstractModel implements Serializable {

    private static final long serialVersionUID = -2958857898232204689L;

    @XmlAttribute(name = "id-razon-resultado", required = true)
    private Long idRazonResultado;
    @XmlAttribute(name = "descripcion", required = true)
    private String descripcion;

    public Long getIdRazonResultado() {
        return idRazonResultado;
    }

    public void setIdRazonResultado(Long idRazonResultado) {
        this.idRazonResultado = idRazonResultado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
