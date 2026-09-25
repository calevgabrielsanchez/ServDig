/**
 * RBGSoftware setting java code convention measurements 
 * 2013.07.23
 */
package mx.gob.imss.common.utils.readAndExportXLS.vo;

public class ResourceDataXLS {
	private String data;
	private String type;
	private Boolean locked;
	private Location location;
	private String sheetName;
	private Boolean isCatalogo;

	public ResourceDataXLS() {
		setIsCatalogo(Boolean.valueOf(false));
		this.location = new Location();
	}

	public String getData() {
		return this.data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getLocked() {
		return this.locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Location getLocation() {
		return this.location;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String getSheetName() {
		return this.sheetName;
	}

	public void setIsCatalogo(Boolean isCatalogo) {
		this.isCatalogo = isCatalogo;
	}

	public Boolean getIsCatalogo() {
		return this.isCatalogo;
	}
}