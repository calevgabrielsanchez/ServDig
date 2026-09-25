/**
 * 
 */
package mx.gob.imss.digital.modelo.domicilio;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Localidad de un domicilio, es decir su municipio, entidad federativa etc..
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "localidad", namespace = "http://mx.gob.imss.digital.modelo.domicilio")
@XmlRootElement(name = "localidad", namespace = "http://mx.gob.imss.digital.modelo.domicilio")
public class Localidad implements Serializable {

    /**
     * Serial version uid
     */
    private static final long serialVersionUID = 1L;

    /**
     * Clave de la localidad
     */
    private String clave;
    /**
     * Nombre de la localidad
     */
    private String nombre;
    /**
     * Municipio al cual pertenece
     */
    private Municipio municipio;
    

    /**
     * 
     */
    public Localidad() {
        
    }
    /**
     * Constructor de un asentamiento con la localidad, municipio y entidad federativa cada uno con 
     * sus claves correspondientes. 
     */
    public Localidad (String clave, String nombre, String claveEntidad , String claveMunicipio) {
        
        //Datos del asentamiento
        this.clave = clave;
        this.nombre = nombre;
        this.setMunicipio(new Municipio());
        this.getMunicipio().setClave(claveMunicipio);
        this.getMunicipio().setEntidadFederativa(new EntidadFederativa());
        this.getMunicipio().getEntidadFederativa().setClave(claveEntidad);
        
    }


    /**
     * @return the clave
     */
    public String getClave() {
        return clave;
    }


    /**
     * @param clave the clave to set
     */
    public void setClave(String clave) {
        this.clave = clave;
    }


    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }


    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    /**
     * @return the municipio
     */
    public Municipio getMunicipio() {
        return municipio;
    }


    /**
     * @param municipio the municipio to set
     */
    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }
    
    
}
