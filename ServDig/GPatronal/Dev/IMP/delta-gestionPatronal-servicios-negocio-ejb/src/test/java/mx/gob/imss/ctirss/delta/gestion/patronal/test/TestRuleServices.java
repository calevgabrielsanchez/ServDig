package mx.gob.imss.ctirss.delta.gestion.patronal.test;

import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.EjbLocator;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.business.SujetoObligadoServiceBusinessTestIt;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rule.RuleServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.domicilio.MunicipioIMSS;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Division;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Grupo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;

public class TestRuleServices {
	 private static final Logger log = LoggerFactory.getLogger(SujetoObligadoServiceBusinessTestIt.class);

	 
//	 @Test
	public void validarFecha() {
	
			Calendar calendarioPivote = Calendar.getInstance();
			calendarioPivote.set(1, 1, 1, 0 , 0, 0);
			Date fechaPivote = calendarioPivote.getTime();

			Calendar calendario = Calendar.getInstance();
			//calendario.set(2024, 9, 31, 0 , 0, 0);
			calendario.set(1997, 6, 1, 0 , 0, 0);
			Date inicioOperacionesServiciosUrbanos = calendario.getTime();
			
			Date fechaDeMovimiento = new Date();

			System.out.println("fechaPivote: " + fechaPivote);
			System.out.println("inicioOperacionesServiciosUrbanos: " + inicioOperacionesServiciosUrbanos);
			
			if(inicioOperacionesServiciosUrbanos!=null && inicioOperacionesServiciosUrbanos.compareTo(fechaPivote)>0){
				log.error("::::Cuenta con fecha de Inicio de operaciones de servicios urbanos::::");
				if(fechaDeMovimiento.compareTo(inicioOperacionesServiciosUrbanos)>=0) {
					System.out.println("return");
					return;
				}else
					System.out.println("error.ambito.urbano.ejercicio.invalido");
			}

		 
		 
	}
	 
	 
	 
	 
//		@Test
		public void validarRPPreviosPF() {

			try {

				log.debug("::Iniciando");

				SujetoObligado soRp = new SujetoObligado();

				soRp.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);
				Fisica fisica = new Fisica();
				fisica.setRfc("AIMF870530568");

				Fraccion frac = new Fraccion();
				Grupo gr = new Grupo();
				Division div = new Division();
				div.setNumDivision("6");
				gr.setNumGrupo("2");
				gr.setDivision(div);
				frac.setNumFraccion("1");
				frac.setGrupo(gr);
				Clasificacion clas = new Clasificacion();
				clas.setFraccion(frac);

				MunicipioIMSS m = new MunicipioIMSS();
				m.setIdMunicipio("104");

				soRp.setFisica(fisica);
				soRp.setClasificacion(clas);
				soRp.setMunicipioIMSS(m);
				soRp.setCveIdSujetoObligado(null);

				log.debug("Ejecutando validarRPPrevios");
				EjbLocator.getRulesService().validarRPPrevios(soRp);

			} catch (GestionPatronalBusinessException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			log.debug("::Termine");
		}
		
		@Test
		public void validarRPPreviosPM() {

			try {

				log.debug("::Iniciando");

				SujetoObligado soRp = new SujetoObligado();

				soRp.setTipoPersonaFiscal(TipoPersonaFiscal.MORAL);
				Moral moral = new Moral();
				moral.setRfc("MCI120518FH4"); //MCI120518FH4 prod  --FNS230601TH9 stage

				Fraccion frac = new Fraccion();
				Grupo gr = new Grupo();
				Division div = new Division();
				div.setNumDivision("6");
				gr.setNumGrupo("8");
				gr.setDivision(div);
				frac.setNumFraccion("3");
				frac.setGrupo(gr);
				frac.setId(new Long(212));
				Clasificacion clas = new Clasificacion();
				clas.setFraccion(frac);

				MunicipioIMSS m = new MunicipioIMSS();
				m.setIdMunicipio("208");

				soRp.setMoral(moral);
				soRp.setClasificacion(clas);
				soRp.setMunicipioIMSS(m);
				soRp.setCveIdSujetoObligado(null);

				log.debug("Ejecutando validarRPPrevios");
				EjbLocator.getRulesService().validarRPPrevios(soRp);

			} catch (GestionPatronalBusinessException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			log.debug("::Termine");
		}
	 	 
	 
	 	@Test
		public void obtenerRegistroPatronalEnMunicipioIMSSPorFraccion() {
			
			try {

				String rfc = "MCI120518FH4";
				Long idTipoPersona = TipoPersona.TIPO_PERSONA_MORAL.longValue();
				Long idMunicipioIMSS = new Long(208);
				Long idFraccion = new Long(211);		
				
				SujetoObligado registroPatronal = EjbLocator.getSujetoObligadoServiceBusiness()
				.obtenerRegistroPatronalEnMunicipioIMSSPorFraccion(rfc, idTipoPersona, idMunicipioIMSS, idFraccion, null);
				if(registroPatronal != null) {
					log.debug("SO:");
					log.debug(registroPatronal.toString());
				}else {
					log.debug("::: El SO es NULL");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			log.debug("::Termine");
		}
	 	 
	 
//	 	@Test
		public void validaPatronExistentePorMunicipioFraccionModalidad() {
			
	 		String rp = "A6540760100";
	 		//String rp = "A6540760100";
			int tipoPersona = TipoPersona.TIPO_PERSONA_FISICA.intValue();

			try {

				SujetoObligado soRp = new SujetoObligado();
				soRp.setNumeroRegistroPatronal(rp);
				TipoPersonaFiscal tipoPersonaFiscal = tipoPersona == TipoPersona.TIPO_PERSONA_FISICA.intValue() ? TipoPersonaFiscal.FISICA : TipoPersonaFiscal.MORAL;			
				soRp.setTipoPersonaFiscal(tipoPersonaFiscal);
				log.debug(":::: Voy a consultar el SO, rp: " + soRp.getNumeroRegistroPatronal());
				soRp = EjbLocator.getSujetoObligadoServiceBusiness().obtenerDetalleSujetoObligadoActividadEconomica(soRp);
				log.debug("Obtuve el SO del RP");
//				log.debug(soRp.toString());
				
				if(soRp.getMunicipioIMSS().getIdMunicipio() != null) {
					log.debug("Mun: " + soRp.getMunicipioIMSS().getIdMunicipio());
				}else {
					log.debug("El Mpio esta vacio");
				}
				
				soRp.getMunicipioIMSS().setIdMunicipio("733");
				
				log.debug("Fraccion: " + soRp.getClasificacion().getFraccion().getId());
				log.debug("RFC: " + soRp.getFisica().getRfc());
				
				//validamos si se puede generar el alta
				log.debug("Validando si se puede generar el alta");
				log.debug("Clave SO: " + soRp.getCveIdSujetoObligado());
				soRp.setCveIdSujetoObligado(null);
				
				//EjbLocator.getRulesService().validaPatronExistentePorMunicipioFraccionModalidad(soRp);
				
				
				String rfc = soRp.getFisica().getRfc();
				Long idTipoPersona = TipoPersona.TIPO_PERSONA_FISICA.longValue();
				Long idMunicipioIMSS = new Long(soRp.getMunicipioIMSS().getIdMunicipio());
				Long idFraccion = soRp.getClasificacion().getFraccion().getId();		
				
				log.debug("Ejecutando validarClasificacionPorPatronYMunicipio");
					EjbLocator.getRulesService().validarClasificacionPorPatronYMunicipio(rfc, idTipoPersona, idMunicipioIMSS, idFraccion, null);
																
//				SujetoObligado registroPatronal = EjbLocator.getSujetoObligadoServiceBusiness()
//				.obtenerRegistroPatronalEnMunicipioIMSSPorFraccion(rfc, idTipoPersona, idMunicipioIMSS, idFraccion, null);
//				if(registroPatronal != null) {
//					log.debug("SO:");
////					log.debug(registroPatronal.toString());
//				}else {
//					log.debug("::: El SO es NULL");
//				}
				
			} catch (GestionPatronalBusinessException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
				
			
			log.debug("::Termine");
		}
	 
	 
	 
		//@Test
		public void validarFraccionesConsistentesPorPatron() {
			

			String rfc = "HSE9701164Z8";
			log.debug("Inicia");
				
			FileOutputStream fos = null;
			
			try {
				log.debug("Obteniendo ejb");
				RuleServiceBusinessRemote ejb = EJBLocator.getRuleServiceBusiness();
				if(ejb != null){
					log.debug("Recupere ejb");
				}else{
					log.debug("Ejb null");
				}
				
				ejb.validarFraccionesConsistentesPorPatron(rfc);
				
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	 
		//@Test
		public void validarFraccionObligatoria() {
			

			String rfc = "HSE9701164Z8";
			log.debug("Inicia");
				
			FileOutputStream fos = null;
			
			try {
				log.debug("Obteniendo ejb");
				RuleServiceBusinessRemote ejb = EJBLocator.getRuleServiceBusiness();
				if(ejb != null){
					log.debug("Recupere ejb");
				}else{
					log.debug("Ejb null");
				}
				
			//	ejb.validarFraccionObligatoria();
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}		
		

		//@Test
		public void validarRPCPorModificacionSRT() {
			
			log.debug("Inicia");
				
			try {
				log.debug("Obteniendo ejb");
				RuleServiceBusinessRemote ejb = EJBLocator.getRuleServiceBusiness();
				if(ejb != null){
					log.debug("Recupere ejb");
				}else{
					log.debug("Ejb null");
				}
				
				boolean res = ejb.validarRPCPorModificacionSRT(new Long("574234919"), "ESU001009LX0", new Long("3"), "Z0662875102");
				
				System.out.println("Resultado: " + res);
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
			System.out.println("Fin");
		}		
		
		
}
