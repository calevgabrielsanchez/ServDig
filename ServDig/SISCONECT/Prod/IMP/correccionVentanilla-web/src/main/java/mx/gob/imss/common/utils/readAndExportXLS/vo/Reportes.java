/**
 * RBGSoftware setting java code convention measurements 
 * 2013.07.23
 */

package mx.gob.imss.common.utils.readAndExportXLS.vo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Reportes {
	private Location initialLocation;
	private List<ResourceDataXLS> elements;

	public Reportes(Integer rowIni, Integer columnIni) {
		this.initialLocation = new Location();
		this.initialLocation.setRow(rowIni);
		this.initialLocation.setColumn(columnIni);
		this.elements = new ArrayList<ResourceDataXLS>();
	}

	public Reportes(Integer rowIni, String columnIni) {
		initialLocation = new Location(rowIni, columnIni);
		elements = new ArrayList<ResourceDataXLS>();
	}

	public Location getCoordenadaInicial() {
		return initialLocation;
	}

	public void setCoordenadaInicial(Location coordenadaInicial) {
		initialLocation = coordenadaInicial;
	}

	public void setElemento(String data, String sheet) {
		ResourceDataXLS rsd = new ResourceDataXLS();
		Location cordenada = new Location(this.initialLocation.getRow(),
				initialLocation.getColumn());

		rsd.setData(data);
		rsd.setType("String");
		rsd.setLocation(cordenada);
		rsd.setSheetName(sheet);
		rsd.setLocked(Boolean.valueOf(true));
		rsd.setIsCatalogo(Boolean.valueOf(false));

		elements.add(rsd);
		nextCoordenada();
	}

	public List<ResourceDataXLS> getElementos() {
		return elements;
	}

	private void nextCoordenada() {
		this.initialLocation.setRow(Integer.valueOf(this.initialLocation
				.getRow().intValue() + 1));
	}

	public void agregarAResourceData(List<ResourceDataXLS> resourceData) {
		Iterator<ResourceDataXLS> iter = elements.iterator();

		while (iter.hasNext())
			resourceData.add((ResourceDataXLS) iter.next());
	}
}
