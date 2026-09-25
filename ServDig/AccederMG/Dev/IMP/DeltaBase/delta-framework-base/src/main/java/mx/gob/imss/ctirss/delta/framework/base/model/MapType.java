package mx.gob.imss.ctirss.delta.framework.base.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapType {
	private List<MapEntryType> entries = new ArrayList<MapEntryType>();
	
	public MapType() {

	}

	public MapType(Map<String, String> map) {
        for (Map.Entry<String, String> e : map.entrySet()) {
            entries.add(new MapEntryType(e));
        }
    }
 
    public List<MapEntryType> getEntry() {
        return entries;
    }
 
    public void setEntry(List<MapEntryType> entry) {
        this.entries = entry;
    }


}
