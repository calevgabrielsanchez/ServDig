/**
 * RBGSoftware clean service
 * 2013.07.23
 * 
 */

package mx.gob.imss.common.utils.readAndExportXLS.example;

import mx.gob.imss.common.utils.readAndExportXLS.vo.Location;

public class PrecargaAnexoII {
	private Location razonSocial;
	private Location registroPatronal;
	private Location ejecicioDel;
	private Location ejecicioAl;

	public PrecargaAnexoII() {
		this.razonSocial = new Location(Integer.valueOf(6), Integer.valueOf(4));
		this.registroPatronal = new Location(Integer.valueOf(8),
				Integer.valueOf(5));
		this.ejecicioDel = new Location(Integer.valueOf(9), Integer.valueOf(10));
		this.ejecicioAl = new Location(Integer.valueOf(9), Integer.valueOf(12));
	}

	public Location getRazonSocial() {
		return this.razonSocial;
	}

	public void setRazonSocial(Location razonSocial) {
		this.razonSocial = razonSocial;
	}

	public Location getRegistroPatronal() {
		return this.registroPatronal;
	}

	public void setRegistroPatronal(Location registroPatronal) {
		this.registroPatronal = registroPatronal;
	}

	public Location getEjecicioDel() {
		return this.ejecicioDel;
	}

	public void setEjecicioDel(Location ejecicioDel) {
		this.ejecicioDel = ejecicioDel;
	}

	public Location getEjecicioAl() {
		return this.ejecicioAl;
	}

	public void setEjecicioAl(Location ejecicioAl) {
		this.ejecicioAl = ejecicioAl;
	}
}
