
package mx.gob.imss.cit.clienteServiciosComunes.mediosContacto.ws;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import mx.gob.imss.cit.clienteServiciosComunes.mediosContacto.model.MedContactoRepreLegalDTO;


/**
 * <p>Java class for ArrayOfMedContactoRepreLegalDTO_literal complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfMedContactoRepreLegalDTO_literal">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MedContactoRepreLegalDTO" type="{java:mx.gob.imss.distss.pensiones.bdtu.modelo}MedContactoRepreLegalDTO" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfMedContactoRepreLegalDTO_literal", propOrder = {
    "medContactoRepreLegalDTO"
})
public class ArrayOfMedContactoRepreLegalDTOLiteral {

    @XmlElement(name = "MedContactoRepreLegalDTO", nillable = true)
    protected List<MedContactoRepreLegalDTO> medContactoRepreLegalDTO;

    /**
     * Gets the value of the medContactoRepreLegalDTO property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the medContactoRepreLegalDTO property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMedContactoRepreLegalDTO().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MedContactoRepreLegalDTO }
     * 
     * 
     */
    public List<MedContactoRepreLegalDTO> getMedContactoRepreLegalDTO() {
        if (medContactoRepreLegalDTO == null) {
            medContactoRepreLegalDTO = new ArrayList<MedContactoRepreLegalDTO>();
        }
        return this.medContactoRepreLegalDTO;
    }

}
