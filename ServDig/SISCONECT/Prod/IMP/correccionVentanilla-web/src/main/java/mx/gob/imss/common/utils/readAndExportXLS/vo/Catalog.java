/**
 * RBGSoftware clean service
 * 2013.07.23
 */
package mx.gob.imss.common.utils.readAndExportXLS.vo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Catalog {
	private Location initialLocation;
	private List<ResourceDataXLS> elements;

	public Catalog(Integer rowIni, Integer columnIni) {
		initialLocation = new Location();
		initialLocation.setRow(rowIni);
		initialLocation.setColumn(columnIni);
		elements = new ArrayList<ResourceDataXLS>();
	}

	public Catalog(Integer rowIni, String columnIni) {
		this.initialLocation = new Location(rowIni, columnIni);
		this.elements = new ArrayList<ResourceDataXLS>();
	}

	public Location getCoordenadaInicial() {
		return this.initialLocation;
	}

	public void setCoordenadaInicial(Location coordenadaInicial) {
		this.initialLocation = coordenadaInicial;
	}

	public void setElemento(String data, String sheet) {
		ResourceDataXLS rsd = new ResourceDataXLS();
		Location cordenada = new Location(this.initialLocation.getRow(),
				this.initialLocation.getColumn());

		rsd.setData(data);
		rsd.setType("String");
		rsd.setLocation(cordenada);
		rsd.setSheetName(sheet);
		rsd.setLocked(Boolean.valueOf(true));
		rsd.setIsCatalogo(Boolean.valueOf(true));

		this.elements.add(rsd);
		nextCoordenada();
	}

	public List<ResourceDataXLS> getElementos() {
		return this.elements;
	}

	private void nextCoordenada() {
		this.initialLocation.setRow(Integer.valueOf(this.initialLocation
				.getRow().intValue() + 1));
	}

	public void agregarAResourceData(List<ResourceDataXLS> resourceData) {
		Iterator<ResourceDataXLS> iter = this.elements.iterator();

		while (iter.hasNext())
			resourceData.add((ResourceDataXLS) iter.next());
	}
}