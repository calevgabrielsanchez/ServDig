
package mx.gob.imss.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para respuestaWSConsInfoCabGpoFam complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
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
     * Obtiene el valor de la propiedad infoCabezaGrupoFamiliarVO.
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
     * Define el valor de la propiedad infoCabezaGrupoFamiliarVO.
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
