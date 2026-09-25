package mx.gob.imss.ctirss.delta.framework.base.model;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class XmlMapAdapter extends XmlAdapter<MapType, Map<String, String>> {
	
	@Override
	public Map<String, String> unmarshal(MapType in) throws Exception {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		for (MapEntryType entryType : in.getEntry()) {
			hashMap.put(entryType.getKey(), entryType.getValue());
		}
		return hashMap;
	}
 
	@Override
	public MapType marshal(Map<String, String> map) throws Exception {
		MapType props = new MapType();
		for (Map.Entry<String, String> entry : map.entrySet()) {
            MapEntryType mapEntryType = new MapEntryType();
            System.err.println(entry.getKey()+"-"+entry.getValue());
            mapEntryType.setKey(entry.getKey());
            mapEntryType.setValue(entry.getValue());
            props.getEntry().add(mapEntryType);
        }

		return props;
	}
 

	
	
}
