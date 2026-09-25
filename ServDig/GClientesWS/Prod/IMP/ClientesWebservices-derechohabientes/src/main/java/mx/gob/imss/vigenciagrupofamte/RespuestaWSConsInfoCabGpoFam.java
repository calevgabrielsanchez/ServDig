
package mx.gob.imss.vigenciagrupofamte;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for respuestaWSConsInfoCabGpoFam complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="respuestaWSConsInfoCabGpoFam">
 *   &lt;complexContent>
 *     &lt;extension base="{http://service.imss.gob.mx/}respuestaWS">
 *       &lt;sequence>
 *         &lt;element name="infoCabezaGrupoFamiliarVO" type="{http://service.imss.gob.mx/}InfoCabezaGrupoFamiliarVO" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "respuestaWSConsInfoCabGpoFam", propOrder = {
    "infoCabezaGrupoFamiliarVO"
})
public class RespuestaWSConsInfoCabGpoFam
    extends RespuestaWS
{

    protected InfoCabezaGrupoFamiliarVO infoCabezaGrupoFamiliarVO;

    /**
     * Gets the value of the infoCabezaGrupoFamiliarVO property.
     * 
     * @return
     *     possible object is
     *     {@link InfoCabezaGrupoFamiliarVO }
     *     
     */
    public InfoCabezaGrupoFamiliarVO getInfoCabezaGrupoFamiliarVO() {
        return infoCabezaGrupoFamiliarVO;
    }

    /**
     * Sets the value of the infoCabezaGrupoFamiliarVO property.
     * 
     * @param value
     *     allowed object is
     *     {@link InfoCabezaGrupoFamiliarVO }
     *     
     */
    public void setInfoCabezaGrupoFamiliarVO(InfoCabezaGrupoFamiliarVO value) {
        this.infoCabezaGrupoFamiliarVO = value;
    }

}
