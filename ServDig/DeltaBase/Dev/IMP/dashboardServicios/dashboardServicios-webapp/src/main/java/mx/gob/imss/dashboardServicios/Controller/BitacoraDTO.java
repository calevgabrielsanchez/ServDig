/**
 * @ autor Josue Hernandez Ramirez
 * @ version 1.0
 * 06/01/2012
 */
package mx.gob.imss.dashboardServicios.Controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import mx.gob.imss.ctirss.infraestructura.dashboardservicios.modelo.negocio.Operacion;
import mx.gob.imss.ctirss.infraestructura.dashboardservicios.modelo.negocio.Servicio;
import mx.gob.imss.ctirss.infraestructura.dashboardservicios.modelo.negocio.Sistema;
import mx.gob.imss.ctirss.infraestructura.dashboardservicios.modelo.negocio.BitacoraServicios;
import mx.gob.imss.infraestructura.convertirdores.ConverterGregorianToDate;


public class BitacoraDTO implements Serializable{
	private static final long serialVersionUID = 5321040618876929316L;
	private String id;
	private Date fechaInico;
	private Date fechaFin;
	private String nombre;
	private String filtro;
	private String horaInicio;
	private String horaFin;
	private String cveSistema;
	private String cveServicio;
	private String cveOperacion;
	private List<Sistema> lstAplicaciones;
	private List<Servicio> lstServicios;
	private List<Operacion> lstOperaciones;
	private List<BitacoraServicios> lstBitacora;
	private ConverterGregorianToDate converter= new ConverterGregorianToDate();
	
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the fechaInico
	 */
	public Date getFechaInico() {
		if(this.fechaInico==null){
		return converter.generaFechasIniciales(true);	
		}
		return fechaInico;
		
	}
	/**
	 * @param fechaInico the fechaInico to set
	 */
	public void setFechaInico(Date fechaInico) {
		this.fechaInico = fechaInico;
	}
	/**
	 * @return the fechaFin
	 */
	public Date getFechaFin() {
		if(this.fechaFin==null){
			return	converter.generaFechasIniciales(false);	
		}
			return fechaFin;
	}
	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the filtro
	 */
	public String getFiltro() {
		return filtro;
	}
	/**
	 * @param filtro the filtro to set
	 */
	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}
	/**
	 * @return the horaInicio
	 */
	public String getHoraInicio() {
		return horaInicio;
	}
	/**
	 * @param horaInicio the horaInicio to set
	 */
	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}
	/**
	 * @return the horaFin
	 */
	public String getHoraFin() {
		return horaFin;
	}
	/**
	 * @param horaFin the horaFin to set
	 */
	public void setHoraFin(String horaFin) {
		this.horaFin = horaFin;
	}
	/**
	 * @return the idAplicacion
	 */
	public String getCveSistema() {
		return cveSistema;
	}
	/**
	 * @param idAplicacion the idAplicacion to set
	 */
	public void setCveSistema(String sCveSistema) {
		this.cveSistema = sCveSistema;
	}
	/**
	 * @return the idServicio
	 */
	public String getCveServicio() {
		return cveServicio;
	}
	/**
	 * @param idServicio the idServicio to set
	 */
	public void setCveServicio(String cveServicio) {
		this.cveServicio = cveServicio;
	}
	/**
	 * @return the idOperacion
	 */
	public String getCveOperacion() {
		return cveOperacion;
	}
	/**
	 * @param idOperacion the idOperacion to set
	 */
	public void setCveOperacion(String cveOperacion) {
		this.cveOperacion = cveOperacion;
	}
	/**
	 * @return the lstAplicaciones
	 */
	public List<Sistema> getLstAplicaciones() {
		return lstAplicaciones;
	}
	/**
	 * @param lstAplicaciones the lstAplicaciones to set
	 */
	public void setLstAplicaciones(List<Sistema> lstAplicaciones) {
		this.lstAplicaciones = lstAplicaciones;
	}
	/**
	 * @return the lstServicios
	 */
	public List<Servicio> getLstServicios() {
		return lstServicios;
	}
	/**
	 * @param lstServicios the lstServicios to set
	 */
	public void setLstServicios(List<Servicio> lstServicios) {
		this.lstServicios = lstServicios;
	}
	/**
	 * @return the lstOperaciones
	 */
	public List<Operacion> getLstOperaciones() {
		return lstOperaciones;
	}
	/**
	 * @param lstOperaciones the lstOperaciones to set
	 */
	public void setLstOperaciones(List<Operacion> lstOperaciones) {
		this.lstOperaciones = lstOperaciones;
	}
	/**
	 * @return the lstResultados
	 */
	public List<BitacoraServicios> getLstBitacora() {
		return lstBitacora;
	}
	/**
	 * @param lstResultados the lstResultados to set
	 */
	public void setLstBitacora(List<BitacoraServicios> lstBitacora) {
		this.lstBitacora = lstBitacora;
	}
}
