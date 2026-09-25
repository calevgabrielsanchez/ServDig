package mx.gob.imss.ctirss.delta.gestion.patronal.test.idse;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.exception.medio.contacto.PersonaSinMedioDeContactoException;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.EjbLocator;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rep.legal.RepresentanteLegalServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.test.EJBLocator;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioFiscal;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fiel;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CentroTrabajo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Division;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Grupo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteSujetoObligado;
import mx.gob.imss.ctirss.idse.service.interfaces.RegistroPatronalIdseServiceBusinessRemote;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.SystemPropertyUtils;


public class IDSETest {
	
	
	private static final Logger log;

	static {
		log = LoggerFactory.getLogger(IDSETest.class);
	}
	
	@Test
	public void altaNRP_IDSE(){
		
		System.out.println(":: Comenzando a obtener servicio de IDSE");
		try {
			RegistroPatronalIdseServiceBusinessRemote soEJB = EjbLocator.getRegistroPatronalIdseServiceBusiness();
			System.out.println(":: Obtuve servicio");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		System.out.println(":: Fin");
	}
	
	
	//@Test
	public void obtieneRPxRFC(){
		
		SujetoObligadoServiceBusinessRemote soEJB = EjbLocator.getSujetoObligadoServiceBusiness();
		
		try {
			String rfc = "CAVL8112142F7";			
			Persona persona = new Persona();
			TipoPersona tp = new TipoPersona();
			int tpI = 0;
			String rp = null;
			tp.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
			tpI = TipoPersona.TIPO_PERSONA_FISICA.intValue();
			persona.setRfc(rfc);
			persona.setTipoPersona(tp);
			SujetoObligado soP = soEJB.obtenerDetallePrimerSujetoObligado(persona);
			System.out.println(soP);
			System.out.println("RP: " + soP.getNumeroRegistroPatronal()+soP.getModalidad()+soP.getDigVerificador());
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
//	@Test
	public void prepararDatosIDSE(){

		
		String[] arr = {		
				
				"B9850176106|457661520|"+TipoPersona.TIPO_PERSONA_MORAL.intValue()                    				
				
		};
		
		log.debug("::: Inicia recorrido ");
		
		for (int i = 0; i < arr.length; i++) {
			String[] val = arr[i].split("\\|");
			log.debug("::: Posicion: " + (i+1) + " de " + arr.length);
			log.debug("Inicia busqueda : val[0]: " + val[0] + " - val[1]: " + val[1] + " - " + val[2]);
			obtieneDatos(val[0], val[1] , new Integer(val[2]).intValue());
		}

		log.debug("::: Termino recorrido ");
		
	}
	
	
	
	public void obtieneDatos(String rp, String idSol, int tipoPersona){
		try {
			
			log.debug(":::: Voy a consultar solicitud idSol: " + idSol);			
			Solicitud solicitudAlta = EjbLocator.getSolicitudServiceBusiness().consultarSolicitudPorId(new Long(idSol));
			SujetoObligado soTr = getSO(solicitudAlta);

			SujetoObligado soRp = new SujetoObligado();
			soRp.setNumeroRegistroPatronal(rp);
			TipoPersonaFiscal tipoPersonaFiscal = tipoPersona == TipoPersona.TIPO_PERSONA_FISICA.intValue() ? TipoPersonaFiscal.FISICA : TipoPersonaFiscal.MORAL;			
			soRp.setTipoPersonaFiscal(tipoPersonaFiscal);
			log.debug(":::: Voy a consultar el SO, rp: " + soRp.getNumeroRegistroPatronal());
			soRp = EjbLocator.getSujetoObligadoServiceBusiness().obtenerDetalleSujetoObligadoActividadEconomica(soRp);
			log.debug("Obtuve el SO del RP: " + soRp.getCveIdSujetoObligado());
			
			String rfcRL = "";
			String curpRL = "";
/*VERIFICAR*/			String serialRL = "";
			String rfcPM = "";
/*VERIFICAR*/			String serialPM = "";

			//Datos Patr�n
			String nombreCompleto = "";
			String refDomFis = "";
			String refCorreoElec = "";
/*VERIFICAR*/			Integer estatusFiel = 2;
			String cveDel = "";
			String cveSub = "";
			String rpF = "";
			String regPatDig = "";
			String tipoPatron = "";
			String nombrePatron = "";
			String municipio = "";
			String sector = "";
			String domicilio = "";
			String localidad = "";
			String actividad = "";
			String fraccion = "";
			String claseRT = "";
			String folio = "";

			if(tipoPersona == TipoPersona.TIPO_PERSONA_FISICA.intValue()){
				log.debug(":::: Voy a consultar datos de PF");	


				rfcRL = StringUtils.isNotBlank(soTr.getFisica().getRfc()) ? soTr.getFisica().getRfc().toUpperCase() : soTr.getFisica().getRfc();
				curpRL = soTr.getFisica().getCurp() !=null ? soTr.getFisica().getCurp(): "----";
				
				if(rfcRL == null || rfcRL.trim().length() == 0){
					rfcRL = StringUtils.isNotBlank(soRp.getFisica().getRfc()) ? soRp.getFisica().getRfc().toUpperCase() : soRp.getFisica().getRfc();
				}
				if(curpRL == null || curpRL.trim().length() == 0){
					curpRL = soRp.getFisica().getCurp() !=null ? soRp.getFisica().getCurp(): "----";
				}
				
serialRL = "-----";
				rfcPM = "-----";
				serialPM = "-----";
				
			}else{
				log.debug(":::: Voy a consultar datos de PM");
				log.debug(":::: Voy a consultar RL ");
				
				List<RepresentanteLegal> representanteLegalList = soTr.getRepresentantesLegales();
				RepresentanteLegal representanteLegal = null;
				if(representanteLegalList != null && representanteLegalList.size() > 0){
					log.debug("--- El SO trae RL");
					representanteLegal = representanteLegalList.get(0);
				}else{
					log.debug("::: Se busca RL en SO de RP");
					representanteLegalList = soRp.getRepresentantesLegales();
					if(representanteLegalList != null && representanteLegalList.size() > 0){
						representanteLegal = representanteLegalList.get(0);
					}else{
						log.debug("--- El SO NOOOO trae RL se va buscar a BDTU");
						RepresentanteLegalServiceBusinessRemote repService = EJBLocator.getRepresetanteLegalBusinessRemote();
						representanteLegalList = repService.obtenerRepresentanteLegalPorSujetoObligado(soTr.getCveIdSujetoObligado());
						if(representanteLegalList != null && representanteLegalList.size() > 0){
							log.debug("--- Encontre RL " + representanteLegalList.size() + ", RL");
							representanteLegal = representanteLegalList.get(0);
						}else{
							log.debug("--- No encontre de nuevo RL");
						}						
					}
				}
				
				if(representanteLegal != null ){
					rfcRL = representanteLegal.getPersonaFisica().getRfc();
					curpRL = representanteLegal.getPersonaFisica().getCurp();
serialRL = "-----";
					if(soTr.getMoral() != null){
						rfcPM = StringUtils.isNotBlank(soTr.getMoral().getRfc()) ? soTr.getMoral().getRfc().toUpperCase() : soTr.getMoral().getRfc();
					}
					if(rfcPM == null || rfcPM.trim().length() == 0){
						if(soRp.getMoral() != null){
							rfcPM = StringUtils.isNotBlank(soRp.getMoral().getRfc()) ? soRp.getMoral().getRfc().toUpperCase() : soRp.getMoral().getRfc();
						}
					}
										
serialPM = "-----";

				}else{
					log.debug("********** No se encontro representante legal");
				}

				log.debug(":::: Termine con RL ");
			}
			
			//////////////////////
			//Datos del patr�n
			//////////////////////

			nombreCompleto = tipoPersona == TipoPersona.TIPO_PERSONA_FISICA.intValue() ? obtenerNombreCompletoPersonaFisica(soRp.getFisica()) : soRp.getMoral().getRazonSocial();
			if(nombreCompleto == null || nombreCompleto.trim().length() == 0){
				nombreCompleto = tipoPersona == TipoPersona.TIPO_PERSONA_FISICA.intValue() ? obtenerNombreCompletoPersonaFisica(soTr.getFisica()) : soTr.getMoral().getRazonSocial();
			}
			
			refDomFis = getDom_Fiscal_CT(soTr.getDomicilioFiscal());
			if(refDomFis == null || refDomFis.trim().length() == 0){
				refDomFis = getDom_Fiscal_CT(soRp.getDomicilioFiscal());				
			}
			if(refDomFis == null || refDomFis.trim().length() == 0){
				DomicilioFiscal domFis = null;
				
				try {
					// Se ejecuta el servicio para obtener el domicilio fiscal
					log.debug("Obteniendo domicilio fiscal");
					if(tipoPersona == TipoPersona.TIPO_PERSONA_FISICA.intValue()){
						domFis = EjbLocator.getDomicilioService().consultarDomicilioFiscalPersona(soTr.getFisica());
					}else{
						domFis = EjbLocator.getDomicilioService().consultarDomicilioFiscalPersona(soTr.getMoral());
					}
				} catch (DomicilioNoLocalizadoException e) {
					refDomFis = "";
					e.printStackTrace();
				}				
				if(domFis != null){
					refDomFis = getDom_Fiscal_CT(domFis);
				}else{
					log.debug("::: De plano no se pudo obtener el domicilio fiscal");
				}				
			}
			
			refCorreoElec = obtenerCorreoCentroTrabajo(soTr.getCntroTrabajo());
			if(refCorreoElec == null || refCorreoElec.trim().length() == 0){
				refCorreoElec = obtenerCorreoCentroTrabajo(soRp.getCntroTrabajo());
			}
			if(refCorreoElec == null || refCorreoElec.trim().length() == 0){
				log.debug("::: Buscando correo PF");
				MediosContactoServiceBusinessRemote mediosContactoService = EJBLocator.getMediosContactoService();
				if(tipoPersona == TipoPersona.TIPO_PERSONA_FISICA.intValue()){
					refCorreoElec = obtenerMediosContacto(soTr.getFisica(), soTr.getCveIdSujetoObligado(), mediosContactoService);
				}else{
					refCorreoElec = obtenerMediosContacto(soTr.getMoral(), soTr.getCveIdSujetoObligado(), mediosContactoService);
				}
			}
			
			if(soTr.getSubdelegacion() != null && soTr.getSubdelegacion().getDelegacion() != null){
				cveDel = soTr.getSubdelegacion().getDelegacion().getClave();				
			}else{
				cveDel = soRp.getSubdelegacion().getDelegacion().getClave();
			}
			if(soTr.getSubdelegacion() != null){
				cveSub = soTr.getSubdelegacion().getClave();
			}else{
				cveSub = soRp.getSubdelegacion().getClave();
			}

			if(soTr.getNumeroRegistroPatronal() != null){
				rpF = obtenerNRP(soTr);
			}else{
				rpF = obtenerNRP(soRp);
			}
			if(soTr.getDigVerificador() != null){
				regPatDig = soTr.getDigVerificador();
			}else{
				regPatDig = soRp.getDigVerificador();
			}
			
			tipoPatron = tipoPersona+"";

			nombrePatron = tipoPersona == TipoPersona.TIPO_PERSONA_FISICA.intValue() ? obtenerNombreCompletoPersonaFisica(soRp.getFisica()) : soRp.getMoral().getRazonSocial();
			if(nombrePatron == null || nombrePatron.trim().length() == 0){
				nombrePatron = tipoPersona == TipoPersona.TIPO_PERSONA_FISICA.intValue() ? obtenerNombreCompletoPersonaFisica(soTr.getFisica()) : soTr.getMoral().getRazonSocial();
			}

			if(soTr.getMunicipioIMSS()!= null && soTr.getMunicipioIMSS().getCvecMunicipioSINDO() != null){
				municipio = soTr.getMunicipioIMSS().getCvecMunicipioSINDO();
			}else{
				municipio = soRp.getMunicipioIMSS().getCvecMunicipioSINDO();
			}
			
			sector = "90";
			
			domicilio = getDom_Fiscal_CT(soRp.getCntroTrabajo());
			if(domicilio == null || domicilio.trim().length() == 0){
				domicilio = getDom_Fiscal_CT(soTr.getCntroTrabajo());
			}
			if(domicilio != null){
				domicilio = domicilio.toUpperCase();
			}			
			localidad = obtenerLocalidadSINDO(soTr.getCntroTrabajo());
			if(localidad == null || localidad.trim().length() == 0){
				localidad = obtenerLocalidadSINDO(soRp.getCntroTrabajo());
			}			
			localidad = StringUtils.isNotBlank(localidad) ? localidad.length() > 40 ? localidad.substring(0, 40) : localidad : localidad;
			if(localidad == null || localidad.trim().length() == 0){
				String[] dom = domicilio.split(",");
				localidad = dom[1].trim();
			}
			if(soTr.getClasificacion()!= null && soTr.getClasificacion().getFraccion()!=null){
				actividad = soTr.getClasificacion().getFraccion().getDescripcion();
				claseRT = soTr.getClasificacion().getFraccion().getClase().getClave().toString();
			}

			if(actividad == null || actividad.trim().length() == 0){
				if(soRp.getClasificacion()!= null && soRp.getClasificacion().getFraccion()!=null){
					actividad = soRp.getClasificacion().getFraccion().getDescripcion();
				}
			}
			if(claseRT == null || claseRT.trim().length() == 0){
				if(soRp.getClasificacion()!= null && soRp.getClasificacion().getFraccion()!=null){
					claseRT = soRp.getClasificacion().getFraccion().getClase().getClave().toString();
				}
			}
			if( soTr.getClasificacion()!=null &&  soTr.getClasificacion().getFraccion()!=null){
				Fraccion frac = soTr.getClasificacion().getFraccion();
				Division division = frac.getGrupo().getDivision();
				Grupo grupo = frac.getGrupo();
				fraccion = division.getNumDivision() + grupo.getNumGrupo() + String.format("%02d", Integer.parseInt(frac.getNumFraccion()));
			}
			if(fraccion == null || fraccion.trim().length() == 0){
				Fraccion frac = soRp.getClasificacion().getFraccion();
				Division division = frac.getGrupo().getDivision();
				Grupo grupo = frac.getGrupo();
				fraccion = division.getNumDivision() + grupo.getNumGrupo() + String.format("%02d", Integer.parseInt(frac.getNumFraccion()));
			}
			
			folio = solicitudAlta.getNoFolioSolicitud();
					
			StringBuffer msgIdse = new StringBuffer();
			
			//Datos PF o RL
			msgIdse.append(rfcRL).append("|");			
			msgIdse.append(curpRL).append("|");
			msgIdse.append(serialRL).append("|");
			msgIdse.append(rfcPM).append("|");
			msgIdse.append(serialPM).append("|");
			
			//Datos Patron
			msgIdse.append(nombreCompleto).append("|");
			msgIdse.append(refDomFis).append("|");
			msgIdse.append(refCorreoElec).append("|");
			msgIdse.append(estatusFiel).append("|");
			msgIdse.append(cveDel).append("|");
			msgIdse.append(cveSub).append("|");
			msgIdse.append(rpF).append("|");
			msgIdse.append(rpF).append("|");
			msgIdse.append(regPatDig).append("|");
			msgIdse.append(tipoPatron).append("|");
			msgIdse.append(nombrePatron).append("|");
			msgIdse.append(municipio).append("|");
			msgIdse.append(sector).append("|");
			msgIdse.append(domicilio).append("|");
			msgIdse.append(localidad).append("|");
			msgIdse.append(actividad).append("|");
			msgIdse.append(fraccion).append("|");
			msgIdse.append(claseRT).append("|");
			msgIdse.append(folio);
			
			msgIdse.append("|");
			//msgIdse.append(solicitudAlta.getSolicitudId()).append("|");
			
			writeResult("C:\\salidasLogs\\sincronizaIDSE.txt", msgIdse.toString());		
			log.debug("::: Escribi rp: " + rp + " en archivo");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private String obtenerMediosContacto(Persona persona, Long idPatronSujetoObligado, MediosContactoServiceBusinessRemote mediosContactoService){
		List<MedioContacto> mediosContactoP = Collections.emptyList();
		List<MedioContacto> mediosContactoCT = Collections.emptyList();
		List<MedioContacto> mediosContactoFis = Collections.emptyList();
		String ce = "-----";
		
		try {
			if(persona.getIdPersona()!=null)
				mediosContactoP = mediosContactoService.consultarMedioDeContactoPersona(persona);
			
		} catch (PersonaSinMedioDeContactoException e) {
			e.getMessage();
		}
		if(mediosContactoP == null || mediosContactoP.size() == 0){
			CentroTrabajo centro = new CentroTrabajo();
			centro.setCveIdPatronSujetoObligado(idPatronSujetoObligado.longValue());				
			mediosContactoCT = mediosContactoService.consultarMedioContactoDeCentroTrabajo(centro);
		}
		if(mediosContactoCT == null || mediosContactoCT.size() == 0){
			try {
				mediosContactoFis = mediosContactoService.consultarMediosFiscalesPersona(persona);
			} catch (PersonaSinMedioDeContactoException e) {
				e.getMessage();
			}
		}

		CorreoElectronico correoElectronico = new CorreoElectronico();

		if(mediosContactoP != null && !mediosContactoP.isEmpty()){
			log.debug("******** Encontre " + mediosContactoP.size() + " medios de contacto P");
			for(Object mCon : mediosContactoP){
				MedioContacto auxMedio = (MedioContacto)mCon;
				if(TipoMedioContacto.TIPO_CORREO_ELECTRONICO.equals(auxMedio.getTipoMedioContacto().getIdTipoMedioContacto())){
					correoElectronico = (CorreoElectronico)mCon;
				}			
			}
		}
		if(mediosContactoCT != null && !mediosContactoCT.isEmpty()){
			log.debug("******** Encontre " + mediosContactoCT.size() + " medios de contacto CT");
			for(Object mCon : mediosContactoCT){
				MedioContacto auxMedio = (MedioContacto)mCon;
				if(TipoMedioContacto.TIPO_CORREO_ELECTRONICO.equals(auxMedio.getTipoMedioContacto().getIdTipoMedioContacto())){
					correoElectronico = (CorreoElectronico)mCon;
				}			
			}
		}
		if(mediosContactoFis != null && !mediosContactoFis.isEmpty()){
			log.debug("******** Encontre " + mediosContactoP.size() + " medios de contacto Fis");
			for(Object mCon : mediosContactoFis){
				MedioContacto auxMedio = (MedioContacto)mCon;
				if(TipoMedioContacto.TIPO_CORREO_ELECTRONICO.equals(auxMedio.getTipoMedioContacto().getIdTipoMedioContacto())){
					correoElectronico = (CorreoElectronico)mCon;
				}			
			}
		}

		if(correoElectronico.getCorreo() != null && correoElectronico.getCorreo().trim().length() > 0){
			log.debug("Agregando correoElectronico: " + correoElectronico.getCorreo());
			ce = correoElectronico.getCorreo();
		}else{
			log.debug("No encontre correo");
		}

		return ce;
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

	public void writeResult(String writeFileName, String text) {
		try {
			FileWriter fileWriter = new FileWriter(writeFileName, true);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			bufferedWriter.newLine();
			bufferedWriter.write(text);
			// Always close files.
			bufferedWriter.close();

		} catch (IOException ex) {
			System.out.println("Error writing to file '" + writeFileName + "'");
		}
	}
	
	
	private String obtenerNRP(SujetoObligado registroPatronal){
		StringBuffer nrp = new StringBuffer();
		if(registroPatronal.getNumeroRegistroPatronal().length()==10){
			nrp.append(registroPatronal.getNumeroRegistroPatronal());
		}else if(registroPatronal.getNumeroRegistroPatronal().length()==8){
			nrp.append(registroPatronal.getNumeroRegistroPatronal());
			nrp.append(registroPatronal.getModalidad().getNumModalidad());
		}else{// mayor a 10
			nrp.append(registroPatronal.getNumeroRegistroPatronal().substring(0, 10));
		}
		return nrp.toString();
	}

	
	private String obtenerLocalidadSINDO(Domicilio domicilio){
		if(domicilio==null)
			return"";
				
		Asentamiento asentamiento = domicilio.getAsentamiento();
        Localidad localidad = asentamiento!=null ? asentamiento.getLocalidad() : null;
        if(localidad==null)
        	return "";
        
        String localidadSindo = new StringBuffer().append(localidad.getMunicipio().getNombre()).
        		append(" ").append(localidad.getMunicipio().getEntidadFederativa().getNombre()).toString().toUpperCase();
        return localidadSindo;
	}
	
	
	private String obtenerCorreoCentroTrabajo(CentroTrabajo centroTrabajo) {
		for (MedioContacto medio : centroTrabajo.getMediosContacto()) {
			if (medio.getTipoMedioContacto().getIdTipoMedioContacto()
					.equals(TipoMedioContacto.TIPO_CORREO_ELECTRONICO)) {
				return medio.getDesFormaContacto();
			}
		}
		return "";
	}

	private String getDom_Fiscal_CT(Domicilio domicilio){
        if(domicilio==null)
        	return "";

        String calle = StringUtils.isNotBlank(domicilio.getCalle()) ? domicilio.getCalle() :
        	domicilio.getVialidadPrimaria()!=null ? domicilio.getVialidadPrimaria().getNombre().trim() : "" ; 
        
        StringBuffer domicilioStr = new StringBuffer(calle)
        .append(" ").append(safeNull(domicilio.getNumExterior1()));
        if(domicilio.getNumExteriorAlf()!=null)
        	domicilioStr.append(" ").append(safeNull(domicilio.getNumExteriorAlf()));
        if(domicilio.getNumInterior()!=null)
        	domicilioStr.append(" ").append(safeNull(domicilio.getNumInterior()));
        if(domicilio.getNumInteriorAlf()!=null)
        	domicilioStr.append(" ").append(safeNull(domicilio.getNumInteriorAlf()));
        if(domicilio.getAsentamiento()!=null && domicilio.getAsentamiento().getNombre()!=null)
        	domicilioStr.append(" ").append(safeNull(","+domicilio.getAsentamiento().getNombre().trim()+","));
        	
		if(domicilio.getAsentamiento() != null){
			log.debug("Asentamiento NO es NULL");
			if(domicilio.getAsentamiento().getLocalidad() != null && domicilio.getAsentamiento().getLocalidad().getNombre()!=null){
				domicilioStr.append(" ").append(domicilio.getAsentamiento().getLocalidad().getNombre().trim());
			}
			if(domicilio.getAsentamiento().getLocalidad() != null && domicilio.getAsentamiento().getLocalidad().getMunicipio() != null
					&& domicilio.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa() != null
					&& domicilio.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getNombre() != null){
				domicilioStr.append(" ").append(domicilio.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getNombre().trim());
			}
			if(domicilio.getAsentamiento().getCodigoPostal() != null && domicilio.getAsentamiento().getCodigoPostal().getCodigoPostal()!= null){
				domicilioStr.append(" ").append(", C.P. " + domicilio.getAsentamiento().getCodigoPostal().getCodigoPostal());
			}
		}else{
			log.debug("Asentamiento es NULL");
			if(domicilio.getLocalidad() != null && domicilio.getLocalidad().getNombre() != null){
				domicilioStr.append(" ").append(domicilio.getLocalidad().getNombre().trim());
			}
			if(domicilio.getLocalidad() != null && domicilio.getLocalidad().getMunicipio() != null
					&& domicilio.getLocalidad().getMunicipio().getEntidadFederativa() != null
					&& domicilio.getLocalidad().getMunicipio().getEntidadFederativa().getNombre()!= null){
				domicilioStr.append(" ").append(domicilio.getLocalidad().getMunicipio().getEntidadFederativa().getNombre().trim());
			}
		}
		
       if(domicilioStr.toString().trim().equals("")){
    	   domicilioStr.append(domicilio.getDescripcion());
       }
                
       return domicilioStr.toString().replaceAll("[\u00F1\u00D1]", "#");
	}
	
//	private String obtenerDomicilioCompleto(Domicilio domicilio){
//        if(domicilio==null)
//        	return "";
//
//        String calle = StringUtils.isNotBlank(domicilio.getCalle()) ? domicilio.getCalle() :
//        	domicilio.getVialidadPrimaria()!=null ? domicilio.getVialidadPrimaria().getNombre() : "" ; 
//
//        StringBuffer domicilioStr = new StringBuffer(calle)
//                .append(" ").append(safeNull(domicilio.getNumExterior1()));
//                if(domicilio.getNumExteriorAlf()!=null)
//                	domicilioStr.append(" ").append(safeNull(domicilio.getNumExteriorAlf()));
//                if(domicilio.getNumInterior()!=null)
//                	domicilioStr.append(" ").append(safeNull(domicilio.getNumInterior()));
//                if(domicilio.getNumInteriorAlf()!=null)
//                	domicilioStr.append(" ").append(safeNull(domicilio.getNumInteriorAlf()));
//                if(domicilio.getAsentamiento()!=null && domicilio.getAsentamiento().getNombre()!=null)
//                	domicilioStr.append(" ").append(safeNull(domicilio.getAsentamiento().getNombre()));
//
//       if(domicilioStr.toString().trim().equals("")){
//    	   domicilioStr.append(domicilio.getDescripcion());
//       }
//       
//       return domicilioStr.toString().replaceAll("[\u00F1\u00D1]", "#");
//
//	}

	private String safeNull(String nullablestring) {
        if (nullablestring == null) {
            return "";
        }else if (nullablestring.equalsIgnoreCase("NULL")) {
            return "";
        }
        nullablestring=nullablestring.trim();
        return nullablestring;
    }

	private String safeNull(Number nullable) {
        if (nullable == null) {
            return "";
        }
        
        if(nullable !=null && nullable.equals(0))
        	return "";
        return "" + nullable;
    }

	private String obtenerNombreCompletoPersonaFisica(Fisica persona){
		StringBuffer nombre = new StringBuffer(); 
		
		if(StringUtils.isNotEmpty(persona.getNombre()) )
			nombre.append(persona.getNombre());
		if(StringUtils.isNotEmpty(persona.getPrimerApellido()) )
			nombre.append(" "+persona.getPrimerApellido());
		if(StringUtils.isNotEmpty(persona.getSegundoApellido()) )
			nombre.append(" "+persona.getSegundoApellido());
		
		return nombre.toString();
	}

	

}
