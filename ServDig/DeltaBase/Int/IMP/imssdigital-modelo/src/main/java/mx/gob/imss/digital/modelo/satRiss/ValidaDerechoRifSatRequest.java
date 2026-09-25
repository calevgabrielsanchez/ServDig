package mx.gob.imss.digital.modelo.satRiss;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "validaDerechoRifSatRequest", namespace = "http://mx.gob.imss.delta.global.service/" ) 
@XmlRootElement(name = "validaDerechoRifSatRequest", namespace = "http://mx.gob.imss.delta.global.service/")
public class ValidaDerechoRifSatRequest implements Serializable {

	private static final long serialVersionUID = -7555674203891995749L;

	@XmlElement(required = true)
	protected String rfc;
	
	/**
	 * Gets the value of the rfc property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getRfc() {
		return rfc;
	}

	/**
	 * Sets the value of the rfc property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 * 
	 */
	public void setRfc(String value) {
		this.rfc = value;
	}
	
	public String toString() {
		return "\n"	+ ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE) + "\n";
	}
	
}
