package mx.gob.imss.ctirss.delta.gestion.asegurado.serie.service.business;

import java.util.Calendar;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.asegurado.serie.service.entity.AseguradoEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ValidaNssBusinessRemote;
import mx.gob.imss.ctirss.delta.persistence.DitAsignacionNss;

@Stateless(name = "validaNssBusiness", mappedName = "validaNssBusiness")
public class ValidaNssBusiness extends AbstractServiceBusiness
	implements ValidaNssBusinessRemote{
	
	@EJB
	private AseguradoEntityLocal aseguradoEntity;
	
	@Override
	public boolean aplicaValidacionNssAlmacen(String nss){
		DitAsignacionNss asignacionNSS = aseguradoEntity.consultarDitAsignacionNss(nss);
		
		//Si Fecha Alta Asignacion NSS es HOY(actual), almacen aun no lo tiene hasta la noche.
		if(asignacionNSS != null && asignacionNSS.getFecRegistroAlta() != null ){
			Date fechaControl = getFechaInicializada(Calendar.getInstance());
			Calendar fecha = Calendar.getInstance();
			fecha.setTime(asignacionNSS.getFecRegistroAlta());
			Date fechaAlta = getFechaInicializada(fecha);
			if(fechaControl.equals(fechaAlta)){
				return false;
			}
		}		
		return true;
	}
	
	private Date getFechaInicializada(Calendar fecha){
		fecha.set(Calendar.HOUR_OF_DAY, 0);
		fecha.set(Calendar.MINUTE, 0);
		fecha.set(Calendar.SECOND, 0);
		fecha.set(Calendar.MILLISECOND, 0);
		return fecha.getTime();
	}
	
	
}
