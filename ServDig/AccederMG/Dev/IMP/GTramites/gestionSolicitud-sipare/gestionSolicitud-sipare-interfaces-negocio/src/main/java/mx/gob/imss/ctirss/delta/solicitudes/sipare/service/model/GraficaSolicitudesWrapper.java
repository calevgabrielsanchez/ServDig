package mx.gob.imss.ctirss.delta.solicitudes.sipare.service.model;

import java.util.List;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class GraficaSolicitudesWrapper extends AbstractModel {

	private static final long serialVersionUID = 1L;

	private String identificador;
	private int tipo;
	private String nombre;
	private String descripcion;
	private String url;
	private List<? extends GraficaSolicitudes> data;
	private String xKey;
	private List<String> yKeys;
	private List<String> labels;

	private boolean graficaDiaActual;
	private int tipoFormatoEjeX;
	
	private boolean mostrarDetalleDia;

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<? extends GraficaSolicitudes> getData() {
		return data;
	}

	public void setData(List<? extends GraficaSolicitudes> data) {
		this.data = data;
	}

	public String getxKey() {
		return xKey;
	}

	public void setxKey(String xKey) {
		this.xKey = xKey;
	}

	public List<String> getyKeys() {
		return yKeys;
	}

	public void setyKeys(List<String> yKeys) {
		this.yKeys = yKeys;
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public boolean isGraficaDiaActual() {
		return graficaDiaActual;
	}

	public void setGraficaDiaActual(boolean graficaDiaActual) {
		this.graficaDiaActual = graficaDiaActual;
	}

	public int getTipoFormatoEjeX() {
		return tipoFormatoEjeX;
	}

	public void setTipoFormatoEjeX(int tipoFormatoEjeX) {
		this.tipoFormatoEjeX = tipoFormatoEjeX;
	}

	public boolean isMostrarDetalleDia() {
		return mostrarDetalleDia;
	}

	public void setMostrarDetalleDia(boolean mostrarDetalleDia) {
		this.mostrarDetalleDia = mostrarDetalleDia;
	}

}