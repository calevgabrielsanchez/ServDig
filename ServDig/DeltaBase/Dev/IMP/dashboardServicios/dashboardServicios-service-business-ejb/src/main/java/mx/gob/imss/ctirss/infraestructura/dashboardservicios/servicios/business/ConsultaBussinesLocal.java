package mx.gob.imss.ctirss.infraestructura.dashboardservicios.servicios.business;

import java.util.List;

import mx.gob.imss.ctirss.infraestructura.dashboardservicios.modelo.negocio.BitacoraServicios;
import mx.gob.imss.ctirss.infraestructura.dashboardservicios.modelo.negocio.Operacion;
import mx.gob.imss.ctirss.infraestructura.dashboardservicios.modelo.negocio.Servicio;
import mx.gob.imss.ctirss.infraestructura.dashboardservicios.modelo.negocio.Sistema;

public interface ConsultaBussinesLocal {
	
	public static final int TIPO_ORDENAMIENTO_ID = 1;
	
	public static final int TIPO_ORDENAMIENTO_SERVICIO = 2;

	/**
	 * Obtiene la lista de los sistemas
	 * @return lista de Sistema
	 * @throws Exception En caso de error.
	 */
	public List<Sistema> getSistemas(Sistema filtroBusqueda);
	
	public boolean eliminaSistema(String sCveSistema);
	
	public boolean actualizaSistema(Sistema sistema);
	
	public boolean altaSistema(Sistema sistema);
	
	public Sistema getSistema(String sCveSistema);

	public List<Servicio> getServicios(String sCveSistema);
	
	public List<Operacion> getOperaciones(String sCveServicio);
	
	public List<BitacoraServicios> getBitacora(BitacoraServicios filtroBusqueda, int iTipoOrdenamiento);
	
	public String getBitacoraEntrada(Long iIdBitacora);
	
	public String getBitacoraSalida(Long iIdBitacora);	
	
	public boolean pruebaTransaccional();
}