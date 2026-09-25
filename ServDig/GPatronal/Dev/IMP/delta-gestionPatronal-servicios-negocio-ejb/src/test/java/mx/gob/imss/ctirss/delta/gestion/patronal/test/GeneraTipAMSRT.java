package mx.gob.imss.ctirss.delta.gestion.patronal.test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import mx.gob.imss.ctirss.delta.gestion.patronal.service.EjbLocator;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.personas.autorizadas.PersonasAutorizadasServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rep.legal.RepresentanteLegalServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaAutorizada;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeneraTipAMSRT {

	private static final Logger log;

	static {
		log = LoggerFactory.getLogger(GeneraTipAMSRT.class);
	}

	@Test
	public void generaDocumentoResultante() {
		
		Long idSol = new Long("1029499990");
		Long idTramite = new Long("1051086127");
		Integer tipoDoc = new Integer("57"); // 57-AVISO_DE_MODIFICACION-AMSRT -- 55-TIP
		
		log.debug("Inicia generacion de docto");
			
		FileOutputStream fos = null;
		
		try {
			log.debug("Obteniendo ejb");
			SolicitudBusinessRemote ejb = EJBLocator.getSolicitudBusinessRemote();
			if(ejb != null){
				log.debug("Recupere ejb");
			}else{
				log.debug("Ejb null");
			}
			
			byte[] byteArray = ejb.obtenerDocumentoResultante(idSol, idTramite, tipoDoc);

			if(byteArray == null){
				log.debug("::::: El documento viene vacio");
			}
					
			fos = new FileOutputStream("c:\\salidasLogs\\AMSRT-"+idSol+".pdf");
			fos.write(byteArray);
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			fos = null;
		}
		
		log.debug("::::::: Termino proceso");
	}
	
//	@Test
	public void obtenerPersonasAutorizadas() {
		
		try {
			log.debug("Obteniendo ejb");
			PersonasAutorizadasServiceRemote ejb = EJBLocator.getPersonasAutorizadasService();
			if(ejb != null){
				log.debug("Recupere ejb");
			}else{
				log.debug("Ejb null");
			}
			
			Persona persona = new Persona();
			
			persona.setRfc("IMS421231I45");
			persona.setTipoPersona(new TipoPersona());
			persona.getTipoPersona().setIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);
			
			
			
			List<PersonaAutorizada> rlL = ejb.getPersonasAutorizadasByPersona(persona);
			
			if(rlL != null && rlL.size() > 0){
				log.debug("Encontre " + rlL.size() + " PA");
				for (Iterator<PersonaAutorizada> iterator = rlL.iterator(); iterator.hasNext();) {
					PersonaAutorizada personaAutorizada = iterator.next();

					if(personaAutorizada != null){
						Fisica pf = personaAutorizada.getFisica();
						if(pf==null || (
								pf!=null && StringUtils.isBlank(pf.getRfc())
								)){
							continue;
						}
						log.debug(pf.getNombre() + " "  + (pf.getPrimerApellido() != null ? pf.getPrimerApellido() : ""));
						log.debug(pf.getNombre() + " " + pf.getPrimerApellido()!=null ? pf.getPrimerApellido() : "" );
						log.debug(pf.getNombre() + " " + pf.getPrimerApellido()!=null ? pf.getPrimerApellido() : "" + " " + pf.getSegundoApellido()!=null ? pf.getSegundoApellido() : "");
						log.debug("Nombre: " + pf.getNombre() + " , Primer apellido: " +  pf.getPrimerApellido() + " , Segundo Apellido: " + pf.getSegundoApellido());			
					}

				}
				
			}else{
				log.debug("No encontre PA");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		log.debug("::::::: Termino proceso");
	}
		
	
//	@Test
	public void obtenerDetalleSujetoObligadoActividadEconomica() {
		SujetoObligado soRp = new SujetoObligado();
		soRp.setNumeroRegistroPatronal("H7112701");
		TipoPersonaFiscal tipoPersonaFiscal =  TipoPersonaFiscal.MORAL;			
		soRp.setTipoPersonaFiscal(tipoPersonaFiscal);
		log.debug(":::: Voy a consultar el SO, rp: " + soRp.getNumeroRegistroPatronal());
		SujetoObligadoServiceBusinessRemote  ejb = EjbLocator.getSujetoObligadoServiceBusiness();
		log.debug(":::: Obtuve EJB");
		soRp = ejb.obtenerDetalleSujetoObligadoActividadEconomica(soRp);
		log.debug("Obtuve el SO del RP: " + soRp.getCveIdSujetoObligado());
		log.debug(soRp.toString());
		log.debug("Termine");		
		
		if(soRp.getMunicipioIMSS().getDescEntidad() != null){
			log.debug("Entidad1: " + soRp.getMunicipioIMSS().getDescEntidad());	
		}
		if(soRp.getMunicipioIMSS().getDescMunicipio() != null){
			log.debug("Entidad2: " + soRp.getMunicipioIMSS().getDescMunicipio());	
		}
		
		
		if(soRp.getSubdelegacion().getDescripcion() != null){
			log.debug("Subdelegacion: " + soRp.getSubdelegacion().getDescripcion());	
		}
		
		
	}
		
	
//	@Test
	public void obtenerRepresentanteLegalPorSujetoObligado() {
		
		try {
			log.debug("Obteniendo ejb");
			RepresentanteLegalServiceBusinessRemote ejb = EJBLocator.getRepresetanteLegalBusinessRemote();
			if(ejb != null){
				log.debug("Recupere ejb");
			}else{
				log.debug("Ejb null");
			}
			
			List<RepresentanteLegal> rl = ejb.obtenerRepresentanteLegalPorSujetoObligado(new Long(8532312));
			
			if(rl != null && rl.size() > 0){
				log.debug("Encontre " + rl.size() + " RL");
				
			}else{
				log.debug("No encontre RL");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		log.debug("::::::: Termino proceso");
	}
	
//	@Test
	public void obtenerRepresentantesLegalesPorPersona() {
		
		try {
			log.debug("Obteniendo ejb");
			RepresentanteLegalServiceBusinessRemote ejb = EJBLocator.getRepresetanteLegalBusinessRemote();
			if(ejb != null){
				log.debug("Recupere ejb");
			}else{
				log.debug("Ejb null");
			}
			
			TipoPersonaEnum tipoPersona = TipoPersonaEnum.MORAL;

			List<RepresentanteLegal> rlL = ejb.obtenerRepresentantesLegalesPorPersona(new Long(6382), tipoPersona);
			
			if(rlL != null && rlL.size() > 0){
				log.debug("Encontre " + rlL.size() + " RL");
				for (Iterator<RepresentanteLegal> iterator = rlL.iterator(); iterator.hasNext();) {
					RepresentanteLegal rl = iterator.next();
					Fisica pRl = rl.getPersonaFisica();
					log.debug("RL: " + pRl.getNombre() + " " + pRl.getPrimerApellido() + " " + pRl.getSegundoApellido());
				}
				
			}else{
				log.debug("No encontre RL");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		log.debug("::::::: Termino proceso");
	}
	
//	@Test
	public void obtenerRepresentantesPorRFCMoral() {
		
		try {
			log.debug("Obteniendo ejb");
			RepresentanteLegalServiceBusinessRemote ejb = EJBLocator.getRepresetanteLegalBusinessRemote();
			if(ejb != null){
				log.debug("Recupere ejb");
			}else{
				log.debug("Ejb null");
			}
			
			TipoPersonaEnum tipoPersona = TipoPersonaEnum.MORAL;

			List<RepresentanteLegal> rlL = ejb.obtenerRepresentantesPorRFCMoral("EAD021205J94");
			
			if(rlL != null && rlL.size() > 0){
				log.debug("Encontre " + rlL.size() + " RL");
				for (Iterator<RepresentanteLegal> iterator = rlL.iterator(); iterator.hasNext();) {
					RepresentanteLegal rl = iterator.next();
					Fisica pRl = rl.getPersonaFisica();
					log.debug("RL: " + pRl.getNombre() + " " + pRl.getPrimerApellido() + " " + pRl.getSegundoApellido());
				}
				
			}else{
				log.debug("No encontre RL");
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		log.debug("::::::: Termino proceso");
	}	
	
}
