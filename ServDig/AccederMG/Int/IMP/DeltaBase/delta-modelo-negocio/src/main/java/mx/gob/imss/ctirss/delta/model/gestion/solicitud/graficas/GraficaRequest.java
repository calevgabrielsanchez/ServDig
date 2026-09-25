package mx.gob.imss.ctirss.delta.model.gestion.solicitud.graficas;

import java.util.Date;
import java.util.List;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class GraficaRequest extends AbstractModel {

	private static final long serialVersionUID = 1L;

	private String idGrafica;
	private int tipoGrafica;

	private Date fechaInicio;
	private Date fechaFin;

	private List<Integer> origenes;
	private List<Integer> tramites;
	private List<Integer> estados;

	private String tipoAgrupacion;

	private String llaveEjeX;
	private String condicionesEjeX;
	private String labelsEjeX;

	private String llaveEjeY;
	private String condicionesEjeY;
	private String labelsEjeY;

	// Atributos extras para dashboard
	private int periodo;
	private Date fechaInicioAnterior;
	private Date fechaFinAnterior;

	public String getIdGrafica() {
		return idGrafica;
	}

	public void setIdGrafica(String idGrafica) {
		this.idGrafica = idGrafica;
	}

	public int getTipoGrafica() {
		return tipoGrafica;
	}

	public void setTipoGrafica(int tipoGrafica) {
		this.tipoGrafica = tipoGrafica;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public List<Integer> getOrigenes() {
		return origenes;
	}

	public void setOrigenes(List<Integer> origenes) {
		this.origenes = origenes;
	}

	public List<Integer> getTramites() {
		return tramites;
	}

	public void setTramites(List<Integer> tramites) {
		this.tramites = tramites;
	}

	public List<Integer> getEstados() {
		return estados;
	}

	public void setEstados(List<Integer> estados) {
		this.estados = estados;
	}

	public String getTipoAgrupacion() {
		return tipoAgrupacion;
	}

	public void setTipoAgrupacion(String tipoAgrupacion) {
		this.tipoAgrupacion = tipoAgrupacion;
	}

	public String getLlaveEjeX() {
		return llaveEjeX;
	}

	public void setLlaveEjeX(String llaveEjeX) {
		this.llaveEjeX = llaveEjeX;
	}

	public String getCondicionesEjeX() {
		return condicionesEjeX;
	}

	public void setCondicionesEjeX(String condicionesEjeX) {
		this.condicionesEjeX = condicionesEjeX;
	}

	public String getLabelsEjeX() {
		return labelsEjeX;
	}

	public void setLabelsEjeX(String labelsEjeX) {
		this.labelsEjeX = labelsEjeX;
	}

	public String getLlaveEjeY() {
		return llaveEjeY;
	}

	public void setLlaveEjeY(String llaveEjeY) {
		this.llaveEjeY = llaveEjeY;
	}

	public String getCondicionesEjeY() {
		return condicionesEjeY;
	}

	public void setCondicionesEjeY(String condicionesEjeY) {
		this.condicionesEjeY = condicionesEjeY;
	}

	public String getLabelsEjeY() {
		return labelsEjeY;
	}

	public void setLabelsEjeY(String labelsEjeY) {
		this.labelsEjeY = labelsEjeY;
	}

	public int getPeriodo() {
		return periodo;
	}

	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}

	public Date getFechaInicioAnterior() {
		return fechaInicioAnterior;
	}

	public void setFechaInicioAnterior(Date fechaInicioAnterior) {
		this.fechaInicioAnterior = fechaInicioAnterior;
	}

	public Date getFechaFinAnterior() {
		return fechaFinAnterior;
	}

	public void setFechaFinAnterior(Date fechaFinAnterior) {
		this.fechaFinAnterior = fechaFinAnterior;
	}
}