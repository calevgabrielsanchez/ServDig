package mx.gob.imss.ctirss.delta.framework.base.model;

import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class MapEntryType {
	
	private String key;
	 
	
	private String value;
	
	MapEntryType() {
	}
	 
	public MapEntryType(Map.Entry<String, String> e) {
		key = e.getKey();
		value = e.getValue();
	}

	 

	@XmlElement
	public String getKey() {
		return key;
	}

	public void setKey(String name) {
		this.key = name;
	}
	
	@XmlElement
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	
	
}
