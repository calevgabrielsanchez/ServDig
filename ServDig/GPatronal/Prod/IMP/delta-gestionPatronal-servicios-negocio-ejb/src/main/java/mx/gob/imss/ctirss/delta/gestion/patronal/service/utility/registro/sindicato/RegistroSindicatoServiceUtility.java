package mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.registro.sindicato;

import java.util.Calendar;
import java.util.Date;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.ServiceUtility;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RegistroSindicato;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaMoral;
import mx.gob.imss.ctirss.delta.persistence.DitSindicato;

@Stateless
public class RegistroSindicatoServiceUtility extends ServiceUtility implements
		RegistroSindicatoServiceUtilityLocal {

	@Override
	public DitSindicato convertirModelToEntity(RegistroSindicato model)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RegistroSindicato convertirEntityToModel(DitSindicato entity)
			{
		RegistroSindicato model = new RegistroSindicato();
		model.setAutoridadLaboral(entity.getDesAutLab());
		model.setCveRegistroSindicato(entity.getCveIdSindicato());
		model.setFechaRegistro(entity.getFecDocRegistro());
		model.setNumReferenciadocRegistro( entity.getNumRefRegistro() );
		return model;
	}

	/**
	 * Utilizado para ajustar los valores de las variables del objeto a persistir
	 */
	@Override
	public DitSindicato asignarvaloresFaltantes(
			RegistroSindicato registroSindicato, DitSindicato ditSindicato)
			throws Exception {
		
		if (ditSindicato == null){
			ditSindicato = new DitSindicato();
		}
		Date fechaActualizacion=Calendar.getInstance().getTime();
		ditSindicato.setNumRefRegistro(registroSindicato.getNumReferenciadocRegistro() != null ? registroSindicato.getNumReferenciadocRegistro().toString() : "0");
		ditSindicato.setFecDocRegistro(registroSindicato.getFechaRegistro());
		ditSindicato.setDesAutLab(registroSindicato.getAutoridadLaboral());
		ditSindicato.setFecRegistroActualizado(fechaActualizacion);
		
		if(ditSindicato.getFecRegistroAlta()==null){
			ditSindicato.setFecRegistroAlta(fechaActualizacion);
		}
		
		DitPersonaMoral ditPersonaMoral = new DitPersonaMoral();
		ditPersonaMoral.setCveIdPersonaMoral(registroSindicato.getCveIdPersonaMoral());
		ditSindicato.setDitPersonaMoral(ditPersonaMoral);
		return ditSindicato;
	}

}
