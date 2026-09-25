package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.clasificacion;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import mx.gob.imss.ctirss.delta.persistence.DicDiasFestivo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.fecha.FechaDto;

@Stateless
public class FechaServiceEntity 	extends AbstractServiceEntity 
								implements FechaServiceEntityLocal{

	@Override
	@SuppressWarnings("unchecked")
    public List<FechaDto> consultarFestivos(String mes, String year) {
	
		// Desde hoy a las 00:00:00
		Calendar inicioCal = Calendar.getInstance();
		inicioCal.set(Calendar.HOUR_OF_DAY, 0);
		inicioCal.set(Calendar.MINUTE, 0);
		inicioCal.set(Calendar.SECOND, 0);
		inicioCal.set(Calendar.MILLISECOND, 0);
		Date fechaInicio = inicioCal.getTime();

		// 60 días después
		Calendar finCal = (Calendar) inicioCal.clone();
		finCal.add(Calendar.DAY_OF_MONTH, 60);
		Date fechaFin = finCal.getTime();

		Criteria criteria = this.getSession().createCriteria(DicDiasFestivo.class)
		    .add(Restrictions.ge("fecDiaFestivo", fechaInicio))
		    .add(Restrictions.lt("fecDiaFestivo", fechaFin));

		List<DicDiasFestivo> resultados = criteria.list();
		List<FechaDto> diasFestivos = new ArrayList<FechaDto>();

		for (DicDiasFestivo entity : resultados) {
		    FechaDto nuevaFecha = new FechaDto();
		    
		    Calendar cal = Calendar.getInstance();
		    cal.setTime(entity.getFecDiaFestivo());
		    
		    nuevaFecha.setDia(cal.get(Calendar.DAY_OF_MONTH));
		    nuevaFecha.setMes(cal.get(Calendar.MONTH) + 1); // +1 para mes real (1-12)
		    nuevaFecha.setYear(cal.get(Calendar.YEAR));
		    
		    diasFestivos.add(nuevaFecha);
		}

		return diasFestivos;
    }
	
}
