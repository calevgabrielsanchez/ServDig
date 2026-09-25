
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
 * <p>Java class for FolderObjectsResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FolderObjectsResponse">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ws.bp.cit.imss.gob.mx/bovedaPersonalCommonSchema}BaseResponse">
 *       &lt;sequence>
 *         &lt;element name="ObjectList" type="{http://ws.bp.cit.imss.gob.mx/bovedaPersonalCommonSchema}Folder" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FolderObjectsResponse", propOrder = {
    "objectList"
})
public class FolderObjectsResponse
    extends BaseResponse
{

    @XmlElement(name = "ObjectList", required = true)
    protected List<Folder> objectList;

    /**
     * Gets the value of the objectList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the objectList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getObjectList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Folder }
     * 
     * 
     */
    public List<Folder> getObjectList() {
        if (objectList == null) {
            objectList = new ArrayList<Folder>();
        }
        return this.objectList;
    }

}
