package mx.gob.imss.ctirss.infraestructura.dashboardservicios.servicios.entity;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.infraestructura.dashboardservicios.modelo.negocio.BitacoraServicios;
import mx.gob.imss.ctirss.infraestructura.dashboardservicios.modelo.negocio.Operacion;
import mx.gob.imss.ctirss.infraestructura.dashboardservicios.modelo.negocio.Servicio;

/**
 * @author Joaquin Esteban Ponte Díaz
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 20/01/2012
 */
@Local
public interface ConsultaEntityLocal{

	public static final int TIPO_BITACORA_ENTRADA = 1;
	public static final int TIPO_BITACORA_SALIDA = 2;
	public static final int TIPO_ORDENAMIENTO_ID = 1;
	public static final int TIPO_ORDENAMIENTO_SERVICIO = 2;
		
	public List<Servicio> getServicios(String sCveSistema);
	
	public List<Operacion> getOperaciones(String sCveServicio);
	
	public List<BitacoraServicios> getBitacora(BitacoraServicios filtroBusqueda, int iTipoOrdenamiento);
	
	public String getBitacoraEntrada(Long iIdBitacora);
	
	public String getBitacoraSalida(Long iIdBitacora);	
}
