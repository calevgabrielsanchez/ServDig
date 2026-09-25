package mx.gob.imss.ctirss.infraestructura.dashboardservicios.servicios.business;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.infraestructura.dashboardservicios.modelo.negocio.BitacoraServicios;
import mx.gob.imss.ctirss.infraestructura.dashboardservicios.modelo.negocio.Sistema;
import mx.gob.imss.ctirss.infraestructura.dashboardservicios.modelo.negocio.Servicio;
import mx.gob.imss.ctirss.infraestructura.dashboardservicios.modelo.negocio.Operacion;
//import mx.gob.imss.ctirss.delta.gestion.patron.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.infraestructura.framework.servicios.AbstractServiceBusiness;
import mx.gob.imss.ctirss.infraestructura.dashboardservicios.servicios.business.interfaces.ConsultaBusinessRemote;
import mx.gob.imss.ctirss.infraestructura.dashboardservicios.servicios.entity.ConsultaEntityLocal;
import mx.gob.imss.ctirss.infraestructura.dashboardservicios.servicios.entity.SistemaEntityLocal;


/**
 * @author Joaquin Esteban Ponte Díaz
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 20/01/2012
 */
@Stateless(mappedName = "ejb/ConsultaBusiness")
public class ConsultaBusiness extends AbstractServiceBusiness implements ConsultaBusinessRemote, ConsultaBussinesLocal {
	
	@EJB
	private ConsultaEntityLocal consultaEntity;

	@EJB
	private SistemaEntityLocal sistemaEntity;
	
	//OPERACIONES DE SISTEMA
	@Override
	public List<Sistema> getSistemas(Sistema filtroBusqueda){
		return sistemaEntity.getSistemas(filtroBusqueda);
	}
		
	public boolean eliminaSistema(String sCveSistema){
		return sistemaEntity.eliminaSistema(sCveSistema);
	}
	
	public boolean actualizaSistema(Sistema sistema){
		boolean bExito = false;
		try{
			bExito = sistemaEntity.actualizaSistema(sistema);	
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return bExito;
	}
	
	public boolean altaSistema(Sistema sistema){
		return sistemaEntity.altaSistema(sistema);
	}
	
	public Sistema getSistema(String sCveSistema){
		return sistemaEntity.getSistema(sCveSistema);
	}
	
	
	//OPERACIONES DE CONSULTA
	@Override
	public List<Servicio> getServicios(String sCveSistema){
		return consultaEntity.getServicios(sCveSistema);
	}
	
	@Override
	public List<Operacion> getOperaciones(String sCveServicio){
		return consultaEntity.getOperaciones(sCveServicio);
	}
	
	@Override
	public List<BitacoraServicios> getBitacora(BitacoraServicios filtroBusqueda, int iTipoOrdenamiento){
		return consultaEntity.getBitacora(filtroBusqueda, iTipoOrdenamiento);
	}
	
	@Override
	public String getBitacoraSalida(Long iIdBitacora){
		return consultaEntity.getBitacoraSalida(iIdBitacora);	
	}

	@Override
	public String getBitacoraEntrada(Long iIdBitacora){
		return consultaEntity.getBitacoraEntrada(iIdBitacora);
		
	}
	
	@Override
	public boolean pruebaTransaccional(){
		Sistema sistema = new Sistema();
		sistema.setCveSistema("Nomina");
		sistema.setDesSistema("Nomina de empleados 5");
		
		sistemaEntity.actualizaSistema(sistema);

		sistema = new Sistema();
		sistema.setCveSistema("Nomina");
		sistema.setDesSistema("Nomina de empleados 6");
		sistemaEntity.actualizaSistema(sistema);
		
		sistema = new Sistema();
		sistema.setCveSistema("Nomina");
		sistema.setDesSistema("'");
		
		sistemaEntity.actualizaSistema(sistema);
		
		return true;
	}
}
