/**
 * RBGSoftware clean service
 * 2013.07.23
 */
package mx.gob.imss.common.utils.readAndExportXLS.example;

import mx.gob.imss.common.utils.readAndExportXLS.vo.Location;

public class PrecargaAnexoI {
	private Location razonSocial;
	private Location calle;
	private Location numero;
	private Location colonia;
	private Location delegacion;
	private Location cp;
	private Location ciudad;
	private Location registroPatronal;
	private Location ejecicioDel;
	private Location ejecicioAl;
	private Location representanteLegal;

	public PrecargaAnexoI() {
		this.razonSocial = new Location(Integer.valueOf(8), "B");
		this.calle = new Location(Integer.valueOf(12), "B");
		this.numero = new Location(Integer.valueOf(12), "E");
		this.colonia = new Location(Integer.valueOf(12), "G");
		this.delegacion = new Location(Integer.valueOf(14), "A");
		this.cp = new Location(Integer.valueOf(14), "D");
		this.ciudad = new Location(Integer.valueOf(14), "G");
		this.registroPatronal = new Location(Integer.valueOf(10), "B");
		this.ejecicioDel = new Location(Integer.valueOf(10), "E");
		this.ejecicioAl = new Location(Integer.valueOf(10), "H");
		this.representanteLegal = new Location(Integer.valueOf(17), "B");
	}

	public Location getRazonSocial() {
		return this.razonSocial;
	}

	public void setRazonSocial(Location razonSocial) {
		this.razonSocial = razonSocial;
	}

	public Location getCalle() {
		return this.calle;
	}

	public void setCalle(Location calle) {
		this.calle = calle;
	}

	public Location getNumero() {
		return this.numero;
	}

	public void setNumero(Location numero) {
		this.numero = numero;
	}

	public Location getColonia() {
		return this.colonia;
	}

	public void setColonia(Location colonia) {
		this.colonia = colonia;
	}

	public Location getDelegacion() {
		return this.delegacion;
	}

	public void setDelegacion(Location delegacion) {
		this.delegacion = delegacion;
	}

	public Location getCp() {
		return this.cp;
	}

	public void setCp(Location cp) {
		this.cp = cp;
	}

	public Location getCiudad() {
		return this.ciudad;
	}

	public void setCiudad(Location ciudad) {
		this.ciudad = ciudad;
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

	public Location getRepresentanteLegal() {
		return this.representanteLegal;
	}

	public void setRepresentanteLegal(Location representanteLegal) {
		this.representanteLegal = representanteLegal;
	}
}
