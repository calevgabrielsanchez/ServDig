package mx.gob.imss.ctirss.delta.gestion.patronal.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mx.gob.imss.ctirss.delta.gestion.patronal.service.EjbLocator;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fiel;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaAutorizada;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TramiteSolicitud {

	private static final Logger log;

	static {
		log = LoggerFactory.getLogger(TramiteSolicitud.class);
	}
	
	
	//@Test
	public void getTramite(){
		Long idP = new Long("457982520");
		Solicitud solicitudAlta = EjbLocator.getSolicitudServiceBusiness().consultarSolicitudPorId(idP);
		SujetoObligado so = getSO(solicitudAlta);
		log.debug("Termine");
	}
	
	@Test
	public void getSOporNrp(){

		String rp = "L4316246104";
		int tipoPersona = TipoPersona.TIPO_PERSONA_MORAL.intValue();

		try {

			SujetoObligado soRp = new SujetoObligado();
			soRp.setNumeroRegistroPatronal(rp);
			TipoPersonaFiscal tipoPersonaFiscal = tipoPersona == TipoPersona.TIPO_PERSONA_FISICA.intValue() ? TipoPersonaFiscal.FISICA : TipoPersonaFiscal.MORAL;			
			soRp.setTipoPersonaFiscal(tipoPersonaFiscal);
			log.debug(":::: Voy a consultar el SO, rp: " + soRp.getNumeroRegistroPatronal());
			soRp = EjbLocator.getSujetoObligadoServiceBusiness().obtenerDetalleSujetoObligadoActividadEconomica(soRp);
			//log.debug("Obtuve el SO del RP: " + soRp.toString());
			
			if(tipoPersona == TipoPersona.TIPO_PERSONA_FISICA.intValue()){
				log.debug("Razon social obtenida: " + soRp.getFisica().getNombre()
						+ " " + soRp.getFisica().getPrimerApellido() + " "
						+ soRp.getFisica().getSegundoApellido() + ", CURP: " + soRp.getFisica().getCurp() + ", RFC: " + soRp.getFisica().getRfc());
			}else{
				log.debug("Razon social obtenida: " + soRp.getMoral().getRazonSocial());
			}
			
			log.debug("NRP: " + soRp.getNumeroRegistroPatronal());
			
			if(soRp.getClasificacion() != null){
				log.debug("******************** CLASIFICACION ***********************");
				log.debug(soRp.getClasificacion().toString());
				log.debug("**********************************************");
			}
			
			
			if(soRp.getCntroTrabajo() != null){
				log.debug("******************** CT ***********************");
				log.debug(soRp.getCntroTrabajo().toString());
				log.debug("**********************************************");

			}
			if(soRp.getRepresentantesLegales() != null){
				log.debug("******************** RL ***********************");
				for (Iterator<RepresentanteLegal> iterator = soRp.getRepresentantesLegales().iterator(); iterator.hasNext();) {
					RepresentanteLegal rl = iterator.next();
					log.debug(rl.toString());
				}
				log.debug("**********************************************");			
			}
			if(soRp.getPersonasAutorizadas() != null){
				log.debug("******************** PA ***********************");
				for (Iterator<PersonaAutorizada> iterator = soRp.getPersonasAutorizadas().iterator(); iterator.hasNext();) {
					PersonaAutorizada rl = iterator.next();
					log.debug(rl.toString());
				}
				log.debug("**********************************************");			
			}

		}catch(Exception e) {
			e.printStackTrace();		}
		
		log.debug("Termine");
	}
	
	//@Test 
	public void getSolicitud(){
		log.debug("Buscando solicitud");
		Solicitud solicitud = EjbLocator.getSolicitudServiceBusiness().consultarDetalleSolicitudPorIdentificador(Long.parseLong("371739524"));
		log.debug(solicitud.toString());	
		log.debug("Termine");
	}
	

	//@Test
	public void consultaSO(){

		String[] arr = {		
				"G0650032107|"+TipoPersona.TIPO_PERSONA_MORAL.intValue()				
		};
		
		log.debug("::: Inicia recorrido ");
		
		List<String> col = new ArrayList<String>();
		
		for (int i = 0; i < arr.length; i++) {

			String[] val = arr[i].split("\\|");
			log.debug("::: Posicion: " + (i+1) + "de " + arr.length);
			log.debug("Inicia busqueda : val[0]: " + val[0] + " - val[1]: " + val[1]);
			TipoPersonaFiscal tipoPersonaFiscal = null;
			if(TipoPersona.TIPO_PERSONA_MORAL.intValue() == new Integer(val[1]).intValue()){
				tipoPersonaFiscal = TipoPersonaFiscal.MORAL;
			}else{
				tipoPersonaFiscal = TipoPersonaFiscal.FISICA;
			}
			
			SujetoObligado so = new SujetoObligado();
			so.setNumeroRegistroPatronal(val[0] );
			so.setTipoPersonaFiscal(tipoPersonaFiscal);

			log.debug(":::: Voy a consultar el SO, rp: " + so.getNumeroRegistroPatronal());
			so = EjbLocator.getSujetoObligadoServiceBusiness().obtenerDetalleSujetoObligadoActividadEconomica(so);
			System.err.println(val[0] + "|" + so.getCveIdSujetoObligado());
			col.add(so.getNumeroRegistroPatronal() + "|" + so.getCveIdSujetoObligado());

		}
		log.debug(":::Recorriendo resultados");
		for (Iterator<String> iterator = col.iterator(); iterator.hasNext();) {
			log.debug(iterator.next());			
		}
		
		log.debug(":::: Ya termine ");
	}
	
	
	//@Test
	public void consultaSolicitud(){
		
		String folio = "1575044729311371739524";
		String idSol = "371739524";
		String NRP = "H6725276107";
		try {
			log.debug("Consulta solicitud por idSol: " + idSol);
			Solicitud solicitudAlta = EjbLocator.getSolicitudServiceBusiness().consultarSolicitudPorId(new Long(idSol));

			System.err.println(solicitudAlta);

			Fiel fiel = new Fiel();
			fiel.setClaveSerial(solicitudAlta.getCertificado().getClaveSerial());
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		//Solicitud consultarSolicitudPorFolio(String folio);
		//Solicitud consultarDetalleSolicitudPorFolio(String folio)
		//Solicitud consultarSolicitudPorId(Long idSolicitud);
		//Solicitud consultarDetalleSolicitudPorIdentificador(Long idSolicitud);
		//Solicitud obtenerSolicitudAltaPatronalPorNRP(Long idPatron);
		log.debug(":::: Ya consulte solicitudAlta ");

		
	}	
	
	private SujetoObligado getSO(Solicitud sol){
		Tramite tramite = null;
		
		for(Tramite t : sol.getTramites()){
			if(t.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.ALTA_SRT.getCodigo().intValue() ||
					t.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.ALTA_SRT_PM.getCodigo().intValue()){
				tramite = t;
				log.debug("Id-Tramite - "
						+ sol.getSolicitudId()
						+ "|"
						+ tramite.getTramiteId()
						+ " - "
						+ sol.getEstadoSolicitud().getIdEstadoSolicitud()
						+ " - "
						+ sol.getEstadoSolicitud().getDescripcion()
						+ " - "
						+ tramite.getEstadoTramite()
								.getIdEstadoTramitePersona() + "-"
						+ tramite.getEstadoTramite().getDescripcion());
				break;
			}
		}
		
		SujetoObligado so = ((TramiteSujetoObligado)tramite).getSujetoObligado();
		
		return ((TramiteSujetoObligado)tramite).getSujetoObligado();
	}	
	
	
}
