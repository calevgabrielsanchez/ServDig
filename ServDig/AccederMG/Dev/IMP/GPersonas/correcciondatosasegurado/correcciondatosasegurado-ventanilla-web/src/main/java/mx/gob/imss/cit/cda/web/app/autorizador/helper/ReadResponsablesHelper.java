package mx.gob.imss.cit.cda.web.app.autorizador.helper;

import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.cit.cda.core.events.ReadEvent;
import mx.gob.imss.cit.cda.core.events.RequestReadEvent;
import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.service.interfaces.ResponsablesDelegacionRemote;
import mx.gob.imss.cit.cda.web.app.autorizador.model.FiltroResponsables;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.responsable.model.UsuarioVentanilla;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.ClienteWebserviceResponsablesSubdelegacionException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component(BeansConstants.READ_RESPONSABLES_HELPER)
public class ReadResponsablesHelper implements ReadHelper<FiltroResponsables, List<UsuarioVentanilla>> {

	private final Logger log = LoggerFactory.getLogger(ReadResponsablesHelper.class);
	
	@Autowired
	@Qualifier("responsablesDelegacionBusiness")
	private ResponsablesDelegacionRemote responsablesDelegacionRemote;

	@SuppressWarnings("unchecked")
	@Override
	public ReadEvent<List<UsuarioVentanilla>> requestEvent(RequestReadEvent<FiltroResponsables> requestReadEvent) {
		log.debug("init responsables [{}]", requestReadEvent.getData().getSubDelegacion());
		
		List<UsuarioVentanilla> combos = new ArrayList<UsuarioVentanilla>();
		try {
			log.debug("Subdelegacion {} Delegacion {} ", requestReadEvent.getUserProfile().getIdSubdelegacion(),requestReadEvent.getUserProfile().getIdDelegacion());
			List<Fisica> responsables = responsablesDelegacionRemote.consultarResponsablesDelegacion(requestReadEvent.getUserProfile().getIdDelegacion().intValue(),requestReadEvent.getUserProfile().getIdSubdelegacion().intValue());
			Integer index = 1;
			for(Fisica fisica : responsables){
				if(!fisica.getCurp().equalsIgnoreCase(requestReadEvent.getData().getCurpResponsable())){
				UsuarioVentanilla combo = new UsuarioVentanilla();
				combo.setKey(index.toString());
				StringBuilder nombre = new StringBuilder();
				nombre.append(fisica.getNombre());
				nombre.append(" ");
				nombre.append(fisica.getPrimerApellido());
				nombre.append(" ");
				nombre.append(fisica.getSegundoApellido());
				combo.setValue(nombre.toString());
				combo.setCorreoElectronico(fisica.getCorreoElectronico().getCorreo());
				combo.setCurp(fisica.getCurp());
				combos.add(combo);
				index++;
				}
			}
			
		} catch (ClienteWebserviceResponsablesSubdelegacionException e1) {
			log.error("Error al consultarLosResponsablesSubdelegacion {}",e1);
		}		
		
		try {
			return new ReadEvent<List<UsuarioVentanilla>>(requestReadEvent.getKey(), combos);
		} catch (Exception e) {
			return ReadEvent.notFound(requestReadEvent.getKey());
		}
	}

}
