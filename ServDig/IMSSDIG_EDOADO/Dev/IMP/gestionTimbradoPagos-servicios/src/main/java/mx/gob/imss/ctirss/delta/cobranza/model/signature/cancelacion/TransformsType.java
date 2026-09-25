package mx.gob.imss.ctirss.delta.cobranza.model.signature.cancelacion;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransformsType", propOrder = {
    "transform"
})
public class TransformsType {

    @XmlElement(name = "Transform", required = true)
    protected TransformType transform;

    /**
     * Gets the value of the transform property.
     * 
     * @return
     *     possible object is
     *     {@link TransformType }
     *     
     */
    public TransformType getTransform() {
        return transform;
    }

    /**
     * Sets the value of the transform property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransformType }
     *     
     */
    public void setTransform(TransformType value) {
        this.transform = value;
    }

}
