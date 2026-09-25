/**
 * 
 */
package mx.gob.imss.digital.modelo.persona;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Fiel de una persona (Al final del dia es un certificado)
 * @author NOVUTECK1
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "fiel", namespace = "http://mx.gob.imss.digital.modelo.persona")
@XmlRootElement(name = "fiel", namespace = "http://mx.gob.imss.digital.modelo.persona")
public class Fiel extends Certificado {

    /**
     * Serial version uid
     */
    private static final long serialVersionUID = 1L;
    

}
