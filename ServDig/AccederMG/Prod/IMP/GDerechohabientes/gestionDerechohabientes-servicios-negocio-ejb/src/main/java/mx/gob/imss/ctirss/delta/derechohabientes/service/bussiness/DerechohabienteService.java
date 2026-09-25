package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.DerechohabienteDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.GrupoFamiliarDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.dao.TramitePersonaFisicaDaoLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.DerechohabienteServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.medio.contacto.PersonaSinMedioDeContactoException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Derechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.Facebook;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.Twitter;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCircunscripcionForanea;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteProrroga;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRegistroDerechohabiente;

/**
 * @author Mario Teran Blanco
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 11/04/2012
 */
@Stateless( name = "derechohabienteService", mappedName = "derechohabienteService")
public class DerechohabienteService extends AbstractServiceBusiness implements DerechohabienteServiceRemote{

	
	@EJB DerechohabienteDaoLocal derechohabienteDao;
	@EJB GrupoFamiliarDaoLocal grupoFamiliarDaoLocal;
	@EJB TramitePersonaFisicaDaoLocal tramiteDao;
	@EJB ProrrogaServiceLocal prorrogaServiceLocal;
	@EJB(name = "mediosContactoServiceBusiness", mappedName = "mediosContactoServiceBusiness") MediosContactoServiceBusinessRemote mediosContactoServiceBusinessRemote;
	@EJB(name = "solicitudBusiness", mappedName = "solicitudBusiness") SolicitudBusinessRemote solicitudBusinessRemote;
	
	@Override 
	public TramiteRegistroDerechohabiente getRegistroDerechohabiente(Long idTramite) throws Exception{
		
		TramiteRegistroDerechohabiente dato =derechohabienteDao.getRegistroDerechohabiente(idTramite);
		
		return dato;
	}

	@Override
	public void updateResultadoCuestionario(TramiteRegistroDerechohabiente registro) throws Exception {

		Date fechaActualizacion = new Date();
		log.debug("El id del registro que se busca es el : " + registro.getTramiteId());
		TramiteRegistroDerechohabiente registroDerechohabiente = derechohabienteDao.getRegistroDerechohabiente(registro.getTramiteId());
		Solicitud solicitud = solicitudBusinessRemote.consultarPorIdTramite(registro.getTramiteId());
		solicitud.setFechaActualizacion(fechaActualizacion);
		for(Tramite tramite: solicitud.getTramites()) {
			tramite.setEstadoTramite(registro.getEstadoTramite());
			tramite.setFechaRegistroActualizacion(fechaActualizacion);
		}

		registroDerechohabiente.setEstadoTramite(registro.getEstadoTramite());
		registroDerechohabiente.setFechaRegistroActualizacion(fechaActualizacion);
		derechohabienteDao.updateRegistroDerechohabiente(registroDerechohabiente);
		solicitudBusinessRemote.actualizarEstados(solicitud);
		
	}

	/**
	 * Metodo para obtener el detalle de un derechohabiente pasandole los siguientes parametros
	 * @param nss String con el nss de la cabeza del grupo familiar
	 * @param idPErsona el id de la persona integrante dle grupo familiar del que se quiere obtener informacion
	 * return derechohabiente de tipo GrupoFamiliar con la informacion relacionada del derechohabiente
	 */
	@Override
	public GrupoFamiliar detalleDerechohabienteGrupoFamiliar(Long idAsignacionNss,
			Long idPersona) throws DerechohabientesBusinessException {
		GrupoFamiliar derechohabiente = null;
		
		Persona miPersona = new Persona();
		TipoPersona unTipoPersona = new TipoPersona();
		unTipoPersona.setIdTipoPersona(TipoPersonaEnum.FISICA.getId());
		miPersona.setIdPersona(idPersona);
		miPersona.setTipoPersona(unTipoPersona);
		
		Boolean existeIntegrante = grupoFamiliarDaoLocal.existeIntegranteRegistrado(idAsignacionNss, idPersona);
		
		if(!existeIntegrante) {
			throw new DerechohabientesBusinessException("La persona no se encuentra registrada a&uacute;n como derechohabiente", "La persona no se encuentra registrada a&uacute;n como derechohabiente");
		}
		
		
		try{
			//obtenemos al derechohabiente con los datos proporcionados
			derechohabiente = grupoFamiliarDaoLocal.getIntegranteGrupoFamiliar(idAsignacionNss, idPersona);
		} catch(DerechohabientesBusinessException e) {
			throw e;
		} catch(Exception e) {
			//Si ocurre alguna excepcion es que no se encontro informacion
			e.printStackTrace();
			throw new DerechohabientesBusinessException("Ocurri&oacute; un error al consultar el integrante del grupo familiar", "Ocurri&oacute; un error al consultar el integrante del grupo familiar");
		}
		
		//si el derechohabiente es igual a nulo y mandamos la misma excepcion que no se encontro informacion
		if(derechohabiente == null) {
			throw new DerechohabientesBusinessException("La persona no se encuentra registrada a&uacute;n como derechohabiente", "La persona no se encuentra registrada a&uacute;n como derechohabiente");
		}
		
		try{
			List<MedioContacto> mediosContacto = mediosContactoServiceBusinessRemote.consultarMedioDeContactoPersona(miPersona);
			  if(mediosContacto != null){ 
				  for(MedioContacto medio: mediosContacto) {
					  if(medio instanceof TelefonoFijo){
	                      TelefonoFijo telefonoFijo = (TelefonoFijo)medio;
	                      telefonoFijo.setClaveLada(telefonoFijo.getClaveLada() == null ? "" : telefonoFijo.getClaveLada().trim());
	                      telefonoFijo.setNumero(telefonoFijo.getNumero() == null ? "" : telefonoFijo.getNumero().trim());
	                      telefonoFijo.setExtension(telefonoFijo.getExtension() == null ? "": telefonoFijo.getExtension().trim());
	                      derechohabiente.getDerechohabiente().setTelefonoFijo(telefonoFijo);
	                  }else if ( medio instanceof TelefonoMovil){
	                          TelefonoMovil telefonoMovil = (TelefonoMovil)medio;
	                          derechohabiente.getDerechohabiente().setTelefonoMovil(telefonoMovil);
	                  }else if (medio instanceof CorreoElectronico){
	                          CorreoElectronico correoElectronico = (CorreoElectronico)medio;
	                          derechohabiente.getDerechohabiente().setCorreoElectronico(correoElectronico);
	                  }else if (medio instanceof Facebook){
	                          Facebook facebook = (Facebook)medio;
	                          derechohabiente.getDerechohabiente().setFacebook(facebook);
	                  }else if ( medio instanceof Twitter){
	                          Twitter twitter = (Twitter)medio;
	                          derechohabiente.getDerechohabiente().setTwitter(twitter);
	                  } 
				  }
			  }
		}catch(PersonaSinMedioDeContactoException e){
			log.debug("La persona no tiene medios de contacto");
		}
			
		//Verificamos si el derechohabiente tieneprorroga
		try {
			TramiteProrroga prorroga = prorrogaServiceLocal.getProrrogaActiva(idAsignacionNss,idPersona);
			if(prorroga != null) {
				derechohabiente.setProrrogaActiva(true);
				
				if(prorroga.getTramite().getTipoTramite().getIdTipoTramite() != null && prorroga.getTramite().getTipoTramite().getIdTipoTramite().equals(TipoTramiteEnum.PRORROGA_VIGENCIA_PERMANENTE.getCodigo().longValue())){
						derechohabiente.setProrrogaPermanente(true);
				}
				
				derechohabiente.setTipoProrroga(prorroga.getTramite().getTipoTramite());
			} else
			{
				derechohabiente.setProrrogaActiva(false);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		TramiteCircunscripcionForanea c = null;

		try{

			c = tramiteDao.getCircunscripcionForanea(
					derechohabiente.getDerechohabiente().getIdPersona(), 
					derechohabiente.getAsignacionNSS(), 
					true);

			if (c != null){
				derechohabiente.setCircunscripcionForaneaActiva(true);
			}else{
				derechohabiente.setCircunscripcionForaneaActiva(false);
			}
		}catch(Exception e){
			derechohabiente.setCircunscripcionForaneaActiva(false);
			log.error("Error al consultar la circunscripcion", e);
		}

		return derechohabiente;
	}

	@Override
	public Derechohabiente getDerechohabiente(Long idPersona)
			throws DerechohabientesBusinessException, Exception {
		Derechohabiente miDerechohabiente = derechohabienteDao.getDerechohabiente(idPersona);		
		return miDerechohabiente;
	}

	@Override
	public void actualizaDerechoabiente(Derechohabiente derechohabiente)
			throws Exception {
		derechohabienteDao.updateDerechohabiente(derechohabiente);
		
	}
	
	@Override
	public boolean tieneCircunscripcionForeanea(Derechohabiente derechohabiente){
		
		try{

			TramiteCircunscripcionForanea c = tramiteDao.getCircunscripcionForanea(
					derechohabiente.getIdPersona(), 
					derechohabiente.getAsignacionNSS(), 
					true);

			if (c != null){
				return true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return false;
		
	}

}
