
package mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.bovedapersonalcommonschema.BaseResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.bovedapersonalcommonschema.Folder;


/**
 * <p>Java class for FolderDescendantsResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FolderDescendantsResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.bp.cit.imss.gob.mx/bovedaPersonalCommonSchema}BaseResponse">
 *       &lt;sequence>
 *         &lt;element name="Arbol" type="{http://ws.bp.cit.imss.gob.mx/bovedaPersonalCommonSchema}Folder" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FolderDescendantsResponse", propOrder = {
    "arbol"
})
public class FolderDescendantsResponse
    extends BaseResponse
{

    @XmlElement(name = "Arbol", required = true)
    protected List<Folder> arbol;

    /**
     * Gets the value of the arbol property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the arbol property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getArbol().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Folder }
     * 
     * 
     */
    public List<Folder> getArbol() {
        if (arbol == null) {
            arbol = new ArrayList<Folder>();
        }
        return this.arbol;
    }

}
