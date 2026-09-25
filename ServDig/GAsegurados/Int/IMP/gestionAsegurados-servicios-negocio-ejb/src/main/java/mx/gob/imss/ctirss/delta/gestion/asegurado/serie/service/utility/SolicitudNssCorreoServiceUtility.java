package mx.gob.imss.ctirss.delta.gestion.asegurado.serie.service.utility;

import java.util.Date;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.framework.exceptions.TransformacionException;
import mx.gob.imss.ctirss.delta.model.asegurado.SolicitudNssCorreo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.persistence.DitSolicitudNssCorreo;
import mx.gob.imss.ctirss.delta.persistence.DitSolicitudNssCorreoPK;

@Stateless(mappedName = "solicitudNssCorreoServiceUtility")
public class SolicitudNssCorreoServiceUtility extends AbstractServiceUtility
		implements SolicitudNssCorreoServiceUtilityLocal {

	@Override
	public SolicitudNssCorreo transformarFromEntity(DitSolicitudNssCorreo entity)
			throws TransformacionException {

		if (entity == null) {
			throw new TransformacionException();
		}

		CorreoElectronico correo = new CorreoElectronico();
		correo.setCorreo(entity.getPk().getRefCorreoElectronico());
		
		SolicitudNssCorreo model = new SolicitudNssCorreo();
		model.setCorreo(correo);
		model.setCurp(entity.getRefCurp());
		model.setNumConsultasPeriodo(entity.getNumConteoSolicitudPeriodo());
		model.setFechaConsulta(entity.getFecConsulta());
		model.setCveIdTipoSolicitud( entity.getPk().getCveIdTipoSolicitud() );

		return model;
	}

	@Override
	public DitSolicitudNssCorreo transformarFromModel(SolicitudNssCorreo model)
			throws TransformacionException {

		if (model == null) {
			throw new TransformacionException();
		}
		
		DitSolicitudNssCorreo entity = new DitSolicitudNssCorreo();
		DitSolicitudNssCorreoPK pk = new DitSolicitudNssCorreoPK();
		pk.setCveIdTipoSolicitud(model.getCveIdTipoSolicitud());
		pk.setRefCorreoElectronico( model.getCorreo().getCorreo() );
		
		entity.setRefCurp(model.getCurp());
		entity.setNumConteoSolicitudPeriodo(model.getNumConsultasPeriodo());
		entity.setPk(pk);
		
		if (model.getFechaConsulta() != null) {
			entity.setFecConsulta(model.getFechaConsulta());
		} else {
			entity.setFecConsulta(new Date());
		}
				
		return entity;
	}
}
