package mx.gob.imss.ctirss.delta.gestion.individuo.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import mx.gob.imss.ctirss.delta.exception.domicilio.AsociarDomicilioException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.individuo.calificacion.PersonaSinCalificacionesException;
import mx.gob.imss.ctirss.delta.exception.medio.contacto.RegistrarMedioContactoException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.exceptions.DatosInsuficientesParaConsultaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.CalificacionesPersonaBusinessServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioFiscal;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Calificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.CalificacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteMoral;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaMoralBusinessRemote;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping(value = "/ubicar/persona")
public class UbicarPersonaController extends AbstractController {
	
	@Autowired PersonaFisicaServiceBusinessRemote localizarPersonaFisica;
    @Autowired PersonaBusinessRemote obtenerDatosPersona;
    @Autowired PersonaMoralBusinessRemote localizaPersonaMoral;
    
    @Autowired CalificacionesPersonaBusinessServiceRemote calificacionesPersonaBusinessService;
    @Autowired SolicitudBusinessRemote solicitudRemote;
    @Autowired DomicilioServiceBusinessRemote domicilios;
    @Autowired MediosContactoServiceBusinessRemote medios;

	@RequestMapping(value = "/test")
	public String unitTest(Model model) {
		
		model.addAttribute("fisicaForm", new Fisica());
		model.addAttribute("moralForm", new Moral());

		return "ubicar.persona.unit.test";
	}

	@RequestMapping(value = "/iniciar")
	public String mostrarFormulario(Model model) {

		model.addAttribute("fisicaForm", new Fisica());
		model.addAttribute("moralForm", new Moral());

		return "ubicar.persona.init";
	}
	
	/**
	 * 
  	 Author: Gustavo U. Trejo A.
  	 Date: 30/10/2014 10:12:36
  	 Description: Servicio que busca personas físicas en IMSS, en caso de no existir y cuenta con la CURP, se consulta a RENAPO.
     @param fisica = Persona Física, model
	 */
	@RequestMapping(value="/buscar/fisica", method = RequestMethod.POST)
	public String mostrarPersonasEncontradasFisica( HttpServletRequest request, @ModelAttribute Fisica fisicaTemp, Model model) 
			throws DatosInsuficientesParaConsultaException {
		
		Fisica fisica = new Fisica();
		if(request.getParameter("valorBuscado")!=null){
			fisica.setCurp(request.getParameter("valorBuscado").toUpperCase());
			fisica.setRfc(request.getParameter("valorBuscado").toUpperCase());
			this.log.info(" -- :::: VALOR BUSCADO: "+request.getParameter("valorBuscado"));
		}else{
			fisica = fisicaTemp;
		}
		List<Fisica> listaPersonasFisicas = new ArrayList<Fisica>();
				try{
					//Datos para no buscar por Segundo Apellido (NULL)
					if(request.getParameter("sinApellido")!=null){
						if(request.getParameter("sinApellido").equals("SINAPELLIDO")){
							fisica.setSegundoApellido(null);
							this.log.info("--:::::ACTIVADO: " + request.getParameter("sinApellido").toString());
						}else
							this.log.info("--:::::NO ACTIVADO: " + request.getParameter("sinApellido").toString());
						
					}
					listaPersonasFisicas = this.localizarPersonaFisica.localizarPersonaFisicaPorDatosBasicosEnImss(fisica);
				}catch(Exception ex){
					this.log.warn(" -- Datos Insuficientes para la consulta: "+ex.getMessage());
				}
		String encontradoFisica = "";

		if(!listaPersonasFisicas.isEmpty()){
			encontradoFisica = "IMSS";
		}else{
			if(fisica.getCurp()!=null){
				//BUSCANDO CURP EN IMSS
				listaPersonasFisicas = 
						this.obtenerDatosPersona.buscarPersonaFisicaPorCurpEnImss(fisica.getCurp());
				if(!listaPersonasFisicas.isEmpty()){
					encontradoFisica = "IMSS";
				}else{
					//LOS DATOS NO FUERON ENCONTRADOS EN IMSS, SE PROCEDE A OBTENER LOS DATOS DE RENAPO
					try {
						Fisica pfisica = localizarPersonaFisica.getPersonaEnRenapo(fisica.getCurp());
						if(pfisica!=null){
							encontradoFisica = "RENAPO";
							listaPersonasFisicas.add(pfisica);
						}else{
							fisica.setCurp(null);
						}
					} catch (Exception e) {
						this.log.debug("  CURP no encontrada en RENAPO: -> "+fisica.getCurp());
						this.log.warn(" -- Curp no encontrada: "+e.getMessage());
						fisica.setCurp(null);
					}
					
				}
			}else if(fisica.getNss()!=null){
				//LOCALIZAR PERSONA POR NSS
				try{
					Fisica pfisica = this.localizarPersonaFisica.localizarPersonaFisicaPorNss(fisica.getNss());
					encontradoFisica = "IMSS";
					listaPersonasFisicas.add(pfisica);
				}catch(Exception e){
					this.log.debug("  NSS no encontrado en IMSS: -> "+fisica.getNss());
					this.log.warn(" -- NSS no encontrado: "+e.getMessage());
				}
				
			}
				if(fisica.getRfc()!=null && fisica.getCurp()==null){
					listaPersonasFisicas = this.obtenerDatosPersona.buscarPersonaFisicaPorRfcEnImss(fisica.getRfc());
					if(!listaPersonasFisicas.isEmpty()){
						encontradoFisica = "IMSS";
					}else{
						//BUSCANDO PERSONA FISICA EN SAT
						try{
							this.log.info(" -- :::::::::::::::::::::: Iniciando consulta al SAT ");
							Fisica fisicaSAT = this.obtenerDatosPersona.buscarPersonaFisicaPorRfcEnSat(fisica.getRfc());
							if(fisicaSAT!=null){
								this.log.info(" -- :::::::::::::::::::::: SAT "+fisicaSAT);
								listaPersonasFisicas.add(fisicaSAT);
								encontradoFisica = "SAT";
								model.addAttribute("vista", "personaFisica");
							}else{
								this.log.info(" -- LOS DATOS NO FUERON ENCONTRADOS EN SAT");
								encontradoFisica = "RFC_PF_NO_ENCONTRADO";
							}
						}catch(Exception ex){
							this.log.warn(" -- No se pudo realizar la consulta externa para buscar el RFC de persona fisica: "+fisica.getRfc()+" en SAT");
							this.log.info(" -- Error: "+ ex.getMessage());
						}
					}
			}
		}
		
		if(listaPersonasFisicas.isEmpty() && fisica.getCurp()==null && fisica.getRfc()!=null){
			encontradoFisica = "RFC_PF_RENAPO_NO_ENCONTRADO";
		}
		
		if(listaPersonasFisicas.isEmpty() && fisica.getCurp()==null && fisica.getRfc()==null){
			//NO SE ENCONTRO INFORMACION DE LA PERSONA A LOCALIZAR, SE PROCEDE A LOCALIZAR POR DATOS BASICOS A RENAPO
			try{
				Fisica fisicaDatosBasico = this.obtenerDatosPersona.buscarPersonaFisicaPorDatosBasicosEnRenapo(
						fisica.getNombre(), fisica.getPrimerApellido(), fisica.getSegundoApellido(), fisica.getSexo().getIdSexo(), fisica.getFechaNacimiento(), Long.valueOf(fisica.getLugarNacimiento().getClave()).intValue());
				if(fisicaDatosBasico!=null){
					encontradoFisica = "REGISTRO";
					model.addAttribute("vista", "altaPersonaFisica");
					model.addAttribute("fisicaForm", fisicaDatosBasico);
				}else{
					this.log.info(" -- LOS DATOS NO FUERON ENCONTRADOS EN RENAPO POR DATOS BASICOS");
					//model.addAttribute("curp", "");
					encontradoFisica = "REGISTRO_MANUAL";
					model.addAttribute("vista", "altaPersonaFisica");
					model.addAttribute("fisicaForm", fisica);
				}
			}catch(Exception ex){
				ex.printStackTrace();
				this.log.warn(" -- La persona no fue localizada en RENAPO por datos basicos: "+ex.getMessage());
				this.log.info(" -- LOS DATOS NO FUERON ENCONTRADOS EN RENAPO POR DATOS BASICOS");
				//model.addAttribute("curp", "");
				encontradoFisica = "REGISTRO_MANUAL";
				model.addAttribute("vista", "altaPersonaFisica");
				model.addAttribute("fisicaForm", fisica);
			}
		}else{
			
			if(fisica.getCurp()!=null)
				model.addAttribute("curp", fisica.getCurp().toUpperCase());
			else if(fisica.getRfc()!=null)
				model.addAttribute("curp", fisica.getRfc());
			else
				model.addAttribute("curp", "");
			model.addAttribute("vista", "personaFisica");
		}
		
		if(listaPersonasFisicas.isEmpty() && request.getParameter("valorBuscado")!=null){
			 if(request.getParameter("tipoBusqueda").equals("RFC")){
				 listaPersonasFisicas = this.obtenerDatosPersona.buscarPersonaFisicaPorRfcEnImss(request.getParameter("valorBuscado"));
				 if(!listaPersonasFisicas.isEmpty()){
						encontradoFisica = "IMSS";
				 }
			 }
			if(listaPersonasFisicas.isEmpty()){
				if(request.getParameter("tipoBusqueda").equals("RFC") && Long.parseLong(request.getParameter("_tipoPersona")) == TipoPersonaEnum.FISICA.getId()){
					encontradoFisica = "RFC_PF_NO_ENCONTRADO";
				}
			}
		}
		
		//OBTENIENDO LAS CALIFICACIONES DE PERSONAS FISICAS
		if(!listaPersonasFisicas.isEmpty() && listaPersonasFisicas!=null){
			int totalPersonaFisicas = listaPersonasFisicas.size();
			List<PersonaCalificacion> cals = null;
			for(int i=0; i<totalPersonaFisicas; i++){
				try{
					cals = this.calificacionesPersonaBusinessService.obtenerCalificacionesVigentes(listaPersonasFisicas.get(i));
				}catch(PersonaSinCalificacionesException ex){
					cals = null;
					this.log.error(" -- Error: "+ex.getMessage());
					this.log.warn(" -- Persona Fisica no cuenta con calificaciones: "+listaPersonasFisicas.get(i).getIdPersona());
				}
				listaPersonasFisicas.get(i).setPersonaCalificaciones(cals);
			}
			this.orderListFisicaByDateDesc(listaPersonasFisicas);
		}
		Map<Long,Boolean> fiels =  new HashMap<Long, Boolean>();
		if(listaPersonasFisicas.size()>0){
			for(Fisica f : listaPersonasFisicas){
				try{
				boolean statusFiel = this.obtenerDatosPersona.validarExistenciaFielPersona(f.getIdPersona());
				this.log.info(" -- Status fiel: "+statusFiel+" idPersona: "+f.getIdPersona());
				fiels.put(f.getIdPersona(), statusFiel);
				}catch(Exception ex){
					this.log.error(" -- No se puede obtener status de fiel: "+ex.getMessage());
				}
			}
		}
		
		model.addAttribute("fiels", fiels);
		model.addAttribute("listaPersonas", listaPersonasFisicas);
		model.addAttribute("totalPersonas", listaPersonasFisicas.size());
		model.addAttribute("encontradoFisica", encontradoFisica);
		
		return "ubicar.persona.resultados.fisica";
	}
	
	/**
	 * 
  	 Author: Gustavo U. Trejo A.
  	 Date: 30/10/2014 10:14:54
  	 Description: Servicio que busca una persona moral en IMSS, en caso de no existir y cuenta con el RFC se consulta a SAT.
     @param moral =  Persona Moral, model
	 */
	@RequestMapping(value="/buscar/moral", method = RequestMethod.POST)
	public String mostrarPersonasEncontradasMoral(HttpServletRequest request, @ModelAttribute Moral moralTemp, Model model)  {
	
		Moral moral = new Moral();
		if(request.getParameter("valorBuscado")!=null){
			moral.setRfc(request.getParameter("valorBuscado"));
		}else{
			moral.setRfc(moralTemp.getRfc());
		}
		
		
		List<Moral> listaPersonasMorales = new ArrayList<Moral>();
		Moral moralSat = new Moral();
		String encontradoMoral = "";

			listaPersonasMorales = this.localizaPersonaMoral.buscarPersonaMoralPorRfcEnImss(moral.getRfc());
			if(!listaPersonasMorales.isEmpty()){
				encontradoMoral = "IMSS";
			}else{
				//PERSONA MORAL NO ENCONTRADA EN IMSS, BUSCANDO EN SAT
				try {
					moralSat = 
							this.obtenerDatosPersona.buscarPersonaMoralPorRfcEnSat(moral.getRfc());
					if(moralSat!=null){
						encontradoMoral = "SAT";
						listaPersonasMorales.add(moralSat);
					}
				} catch (Exception e) {
					this.log.debug("  Datos no encontrados en SAT: -> "+moral.getRfc());
					this.log.warn(" -- Datos no encontrados en SAT: "+e.getMessage());
				} 
			}
			
		// OBTENIENDO LAS CALIFICACIONES DE PERSONAS MORALES
		if (!listaPersonasMorales.isEmpty() && listaPersonasMorales != null) {
			int totalPersonaMorales = listaPersonasMorales.size();
			List<PersonaCalificacion> cals = null;
			for (int i = 0; i < totalPersonaMorales; i++) {
				try {
					cals = this.calificacionesPersonaBusinessService
							.obtenerCalificacionesVigentes(listaPersonasMorales
									.get(i));
				} catch (PersonaSinCalificacionesException ex) {
					cals = null;
					this.log.error(" -- Error: " + ex.getMessage());
					this.log.warn(" -- Persona Fisica no cuenta con calificaciones: "
							+ listaPersonasMorales.get(i).getCveMoral());
				}
				listaPersonasMorales.get(i).setPersonaCalificaciones(cals);
			}
		
			this.orderListMoralByDateDesc(listaPersonasMorales);
		}

		model.addAttribute("listaPersonas", listaPersonasMorales);
		model.addAttribute("totalPersonas", listaPersonasMorales.size());
		model.addAttribute("rfc", moral.getRfc().toUpperCase());
		model.addAttribute("encontradoMoral", encontradoMoral);
		model.addAttribute("vista", "personaMoral");
		
		return "ubicar.persona.resultados.moral";
	}
	
	/**
	 * 
  	 Author: Gustavo U. Trejo A.
  	 Date: 30/10/2014 10:16:57
  	 Description: Servicio que devuelve información de una persona física y/o moral a partir de un idPersona o RFC.
     @param id = Identificador de persona y/o RFC de persona moral.
	 */
	@RequestMapping(value="/buscar/{id}", method = RequestMethod.POST)
	@ResponseBody
	public String buscarPorId(@PathVariable("id") String id){
		
		Object object = null;
		String data="";
		
		Fisica fisica = null;
		Moral moral = null;
		TipoPersona tp = new TipoPersona();
		try {
			this.log.info(" -- Parseando Strint to Long: " + Long.parseLong(id));
			fisica = this.obtenerDatosPersona.getPersonaFisica(Long.parseLong(id));
			if (fisica != null) {
				this.log.info(" -- El identificador es un IdPersona: "+id);
				this.log.info(" -- Persona fisica encontrada ID: "
						+ fisica.getIdPersona() + " Nombre: "
						+ fisica.getNombre());
				tp.setIdTipoPersona(TipoPersonaEnum.FISICA.getId());
				tp.setDescripcion(TipoPersonaEnum.FISICA.toString());
				fisica.setTipoPersona(tp);
				
				//OBTENER EL CVE_FISICA SI TIENE
				try {
					Long cveFisica = this.localizarPersonaFisica
							.obtenerIDPersonaFisica(fisica.getIdPersona());
					if (cveFisica != null)
						fisica.setCveFisica(cveFisica);
				} catch (Exception ex) {
					this.log.info(" -- No aplica CVE FISICA");
				}
				
				object = fisica;
			}else {
				this.log.info(" -- Datos no encontrados para: "+id);
			}
		} catch (Exception e1) {
			this.log.info(" -- El identificador es un RFC: "+id);
			//COMO EL IDENTIFICADOR RECIBIDO NO ES UN IDPERSONA, ENTONCES ES DE UNA PERSONA MORAL, SE SEPARA EL RFC Y EL IDPERSONA MORAL.
			String ids[] = id.split("_");
			
			Moral perMoral = new Moral();
			perMoral.setRfc(ids[0]);
			perMoral.setIdPersona(Long.parseLong(ids[1]));
			try {
				moral = this.localizaPersonaMoral
						.getPersonaMoral(perMoral.getIdPersona());
				if (moral != null) {
					this.log.info(" -- Persona moral encontrada con idPersona: "
							+ moral.getIdPersona()
							+ " RazonSocial: "
							+ moral.getRazonSocial());
					tp.setIdTipoPersona(TipoPersonaEnum.MORAL.getId());
					tp.setDescripcion(TipoPersonaEnum.MORAL.toString());
					moral.setTipoPersona(tp);
					object = moral;
				}
			} catch (Exception e2) {
				this.log.info(" -- Datos no encontrados para: "+id);
			}
		}
		
		//OBJECT MAPPER PERMITE DES-SERIALIZAR EL OBJETO DE PERSONA Y LO CONVIERTE EN UN STRING EN FORMATO DE JSON.
		ObjectMapper json = new ObjectMapper();
		try {
			data = json.writeValueAsString(object).toString();
		} catch (Exception e3) {
			this.log.info(" -- No se puede parsear a JSON");
		}

		return data;
	}
	
	/**
	 * 
  	 Author: Gustavo U. Trejo A.
  	 Date: 30/10/2014 10:23:46
  	 Description: Servicio que busca una persona moral en SAT a partir de un RFC proporcionado, y lo registra en IMSS.
     @param rfc = RFC de una persona moral
	 */
	@RequestMapping(value="/buscar/moral/{rfc}", method = RequestMethod.POST)
	@ResponseBody
	public String buscarPorRFC(@PathVariable("rfc") String rfc){
		String data="";
		String carSeparador=";";
		String folio="";
		Moral moral = new Moral();
		moral.setRfc(rfc);
		try {
			//CONSULTA EXTERNA A SAT
			moral = 
					this.obtenerDatosPersona.buscarPersonaMoralPorRfcEnSat(moral.getRfc());
			if(moral!=null){
				//GUARDAR DATOS DE PERSONA MORAL EN IMSS
				this.log.info(" RFC SAT: "+moral.getRfc());
				this.log.info(" RAZON SOCIAL: "+moral.getRazonSocial());
								
				moral.setPersonaCalificaciones(this.crearCalificacionPersona(null, moral, CalificacionEnum.VALIDADO_SAT.getCodigo().longValue()));
								
				Moral moralBD = this.localizaPersonaMoral.altaPersonaMoral(moral);
				
				try{
				DomicilioFiscal domicilioFiscalMoral = this.domicilios.registrarDomicilioFiscal(moral.getDomicilioFiscal());
				this.domicilios.asociarDomicilioFiscalPersonaMoral(domicilioFiscalMoral.getClave(), moralBD.getIdPersona());
				}catch(DomicilioNoValidoException ex){
					this.log.warn(" -- Error: domicilio no valido::::: "+ex.getMessage());
				}catch(AsociarDomicilioException ex){
					this.log.warn(" -- Error: no se puede asociar el domicilio a la persona moral::::: "+ex.getMessage());
				}
				
				List<MedioContacto> listaDomFiscalMoral = moral.getMediosContactoFiscales();
				if(listaDomFiscalMoral!=null){
					if(listaDomFiscalMoral.size()>0){
						for(MedioContacto mcf: listaDomFiscalMoral){
							try{
							this.medios.registrarAsociarMedioContactoFiscalPersonaMoral(mcf,moralBD.getIdPersona());
							}catch(RegistrarMedioContactoException ex){
								this.log.warn(" -- No se puede registrar medio de contacto fiscal:::: "+ex.getMessage());
							}
						}
					}
				}
				
				
				data = moralBD.getRfc()+"_"+moralBD.getIdPersona();
				this.log.info(" ID GENERADO DE LA PERSONA MORAL: "+moralBD.getIdPersona());
				
				Solicitud sol = this.generarSolicitud(moralBD, TipoPersona.TIPO_PERSONA_MORAL);
				folio = sol.getNoFolioSolicitud();
			}
		} catch (Exception e) {
			this.log.debug("  Datos no encontrados en SAT: -> "
					+ moral.getRfc());
			this.log.warn(" -- Datos no encontrados en SAT: "+e.getMessage());
		}
		return data+carSeparador+folio;
	}
	
	/**
	 * 
  	 Author: Gustavo U. Trejo A.
  	 Date: 30/10/2014 10:25:31
  	 Description: Servicio que busca a una persona física a partir de una CURP en RENAPO, si es encontrado se registra en IMSS.
     @param curp = CURP de una persona física.
	 */
	@RequestMapping(value="/buscar/fisica/{curp}", method = RequestMethod.POST)
	@ResponseBody
	public String buscarPorCURP(@PathVariable("curp") String curp){
		String data="";
		String carSeparador=";";
		String folio="";
		Fisica fisica = new Fisica();
		try {
			//CONSULTA EXTERNA A RENAPO
			fisica = localizarPersonaFisica.getPersonaEnRenapo(curp);
			if(fisica!=null){
				//GUARDAR DATOS DE PERSONA FISICA EN IMSS
				this.log.info(" NOMBRE: "+fisica.getNombre());
				this.log.info(" PRIMER APELLIDO: "+fisica.getPrimerApellido());
				this.log.info(" SEGUNDO APELLIDO: "+fisica.getSegundoApellido());
				this.log.info(" CURP: "+fisica.getCurp());
				
				fisica.setFechaModificacion(new Date());
				
				fisica.setPersonaCalificaciones(this.crearCalificacionPersona(fisica, null, CalificacionEnum.VALIDADO_RENAPO.getCodigo().longValue()));
				
				Fisica fisicaBD = this.obtenerDatosPersona.altaPersonaFisica(fisica);
				data =fisicaBD.getIdPersona().toString();
				this.log.info(" ID GENERADO DE LA PERSONA FISICA: "+fisicaBD.getIdPersona());
				
				Solicitud sol = this.generarSolicitud(fisicaBD, TipoPersona.TIPO_PERSONA_FISICA);
				folio = sol.getNoFolioSolicitud();
			}
		} catch (Exception e) {
			this.log.debug("  CURP no encontrada en RENAPO: -> "+ curp);
			this.log.warn(" -- Curp no encontrada en RENAPO: "+e.getMessage());
			//BUSCANDO EN SAT
			try{
				String rfc = curp;
				Fisica fisicaSAT = this.obtenerDatosPersona.buscarPersonaFisicaPorRfcEnSat(rfc);
				if(fisicaSAT!=null){
					Persona personaFisica = fisicaSAT;
					TipoPersona tp = new TipoPersona();
					tp.setIdTipoPersona(TipoPersonaFiscal.FISICA.getCodigo().longValue());
					personaFisica.setTipoPersona(tp);
					Persona personFisicaBD = this.obtenerDatosPersona.registrarPersonaConDatosSat(personaFisica, false);
					Fisica tempFis = this.obtenerDatosPersona.getDatosComplementariosPersonaFisica(personFisicaBD.getIdPersona());
					this.log.info(" -- Cve Fisica encontrado::: "+tempFis.getCveFisica());
					try{
						DomicilioFiscal domicilioFiscalFisica = this.domicilios.registrarDomicilioFiscal(fisicaSAT.getDomicilioFiscal());
						this.domicilios.asociarDomicilioFiscalPersonaFisica(domicilioFiscalFisica.getClave(), tempFis.getCveFisica());
						}catch(DomicilioNoValidoException ex){
							this.log.warn(" -- Error: domicilio no valido::::: "+ex.getMessage());
						}catch(AsociarDomicilioException ex){
							this.log.warn(" -- Error: no se puede asociar el domicilio a la persona moral::::: "+ex.getMessage());
						}
						
						List<MedioContacto> listaDomFiscalFisica = fisicaSAT.getMediosContactoFiscales();
						if(listaDomFiscalFisica!=null){
							if(listaDomFiscalFisica.size()>0){
								for(MedioContacto mcf: listaDomFiscalFisica){
									try{
									this.medios.registrarAsociarMedioContactoFiscalPersonaFisica(mcf,tempFis.getCveFisica());
									}catch(RegistrarMedioContactoException ex){
										this.log.warn(" -- No se puede registrar medio de contacto fiscal:::: "+ex.getMessage());
									}
								}
							}
						}
					tempFis.setFechaModificacion(new Date());
					tempFis.setPersonaCalificaciones(this.crearCalificacionPersona(tempFis, null, CalificacionEnum.VALIDADO_SAT.getCodigo().longValue()));
				    this.obtenerDatosPersona.actualizarPersona(tempFis);
				    data =personFisicaBD.getIdPersona().toString();
					this.log.info(" ID GENERADO DE LA PERSONA FISICA: "+personFisicaBD.getIdPersona());
					Solicitud sol = this.generarSolicitud(personFisicaBD, TipoPersona.TIPO_PERSONA_FISICA);
					folio = sol.getNoFolioSolicitud();
				}
			}catch(Exception ex){
				this.log.warn(" -- No se pudo realizar la consulta externa para buscar el RFC de persona fisica: "+fisica.getRfc()+" en SAT");
				this.log.info(" -- Error: "+ ex.getMessage());
			}

		}
		
		return data+carSeparador+folio;
	}
	
	/**
	 * 
  	 Author: Gustavo U. Trejo A.
  	 Date: 30/10/2014 10:27:25
  	 Description: Servicio que actualiza datos básicos de una persona Física como: nombre, apellidos, curp, rfc, lugar y fecha de nacimiento.
     @param fisica = Persona Física
	 */
	@RequestMapping(value="/actualizar/fisica/", method = RequestMethod.POST)
	@ResponseBody
	public String actualizarPersonaFisica(@ModelAttribute Fisica fisica){
		//DATOS RECIBIDOS PARA LA ACTUALIZACION
		this.log.info(" -- IdPersona: "+fisica.getIdPersona());
		this.log.info(" -- Nombre(s): "+fisica.getNombre());
		this.log.info(" -- Primer Apellido: "+fisica.getPrimerApellido());
		this.log.info(" -- Segundo Apellido: "+fisica.getSegundoApellido());
		this.log.info(" -- SexoId: "+fisica.getSexo().getIdSexo());
		this.log.info(" -- Fecha de nacimiento: "+fisica.getFechaNacimiento());
		this.log.info(" -- Lugar de Nacimiento: "+fisica.getLugarNacimiento());
		this.log.info(" -- CURP: "+fisica.getCurp());
		this.log.info(" -- RFC: "+fisica.getRfc());
		this.log.info(" -- NSS: "+fisica.getNss());
		
		String data = "";
		try{ 
			//ACTUALIZAR DATOS DE LA PERSONA FISICA
			this.obtenerDatosPersona.actualizarPersona(fisica);
			
			//OBTENIENDO DATOS DE LA PERSONA FISICA
			Fisica fis = this.obtenerDatosPersona.getDatosComplementariosPersonaFisica(fisica.getIdPersona());
			
			ObjectMapper json = new ObjectMapper();
			data = json.writeValueAsString(fis).toString();
		} catch (Exception e3) {
			data="500";
			this.log.info(" -- No se pudo actualizar datos");
		}
		
		return data;
	}
	
	/**
	 * 
  	 Author: Gustavo U. Trejo A.
  	 Date: 30/10/2014 10:33:13
  	 Description: Servicio que actualiza datos generales de una persona moral como: RFC, Razón social y Tipo de Sociedad.
     @param moral = Persona Moral
	 */
	@RequestMapping(value="/actualizar/moral/", method = RequestMethod.POST)
	@ResponseBody
	public String actualizarPersonaMoral(@ModelAttribute Moral moral){
		
		//DATOS RECIBIDOS PARA LA ACTUALIZACION
		this.log.info(" -- Id Persona Moral: "+moral.getIdPersona());
		this.log.info(" -- RFC: "+moral.getRfc());
		this.log.info(" -- Razon Social(s): "+moral.getRazonSocial());
		this.log.info(" -- Id Tipo de Sociedad: "+moral.getTipoSociedad().getIdTipoSociedad());

		
		String data = "";
		try{ 
			//ACTUALIZAR DATOS DE LA PERSONA MORAL
			this.localizaPersonaMoral.actualizarPersonaMoral(moral);
			
			//OBTENIENDO DATOS DE LA PERSONA MORAL
			Moral perMoral = new Moral();
			perMoral.setRfc(moral.getRfc());
			perMoral.setIdPersona(moral.getIdPersona());
			
			Moral personaMoral= this.localizaPersonaMoral.getPersonaMoral(perMoral).get(0);
			
			ObjectMapper json = new ObjectMapper();
			data = json.writeValueAsString(personaMoral).toString();
		} catch (Exception e3) {
			data="500";
			this.log.info(" -- No se pudo actualizar datos");
		}
		
		return data;
	}
	
	
	private List<PersonaCalificacion> crearCalificacionPersona(Fisica fisica, Moral moral, long idCalificacion){
		List<PersonaCalificacion> calificaciones = new ArrayList<PersonaCalificacion>();
		
		Calificacion calificacion = new Calificacion();
			if(CalificacionEnum.VALIDADO_RENAPO.getCodigo().longValue() == idCalificacion){
				calificacion.setIdCalificacion(CalificacionEnum.VALIDADO_RENAPO.getCodigo().longValue());
				calificacion.setDescripcion(CalificacionEnum.VALIDADO_RENAPO.getDescripcion());
			}else if(CalificacionEnum.VALIDADO_IMSS.getCodigo().longValue() == idCalificacion){
				calificacion.setIdCalificacion(CalificacionEnum.VALIDADO_IMSS.getCodigo().longValue());
				calificacion.setDescripcion(CalificacionEnum.VALIDADO_IMSS.getDescripcion());
			}else if(CalificacionEnum.VALIDADO_SAT.getCodigo().longValue() == idCalificacion){
				calificacion.setIdCalificacion(CalificacionEnum.VALIDADO_SAT.getCodigo().longValue());
				calificacion.setDescripcion(CalificacionEnum.VALIDADO_SAT.getDescripcion());
			}
			
		PersonaCalificacion personaCalificacion = new PersonaCalificacion();
			personaCalificacion.setCalificacion(calificacion);
			personaCalificacion.setFechaCalificacion(new Date());
			
		if(fisica!=null){
			fisica.getPersonaCalificaciones().add(personaCalificacion);
		    calificaciones = fisica.getPersonaCalificaciones();
		}
		else if(moral!=null){
			moral.getPersonaCalificaciones().add(personaCalificacion);
		    calificaciones = moral.getPersonaCalificaciones();
		}

		this.log.info(" -- :::::::::::::::::::::::::::::::Se agrega las calificaciones validadas por: "+calificacion.getDescripcion());
		
		return calificaciones;
	}
	
	private Solicitud generarSolicitud(Object persona, Long tipo){
		//###PROCESAR PARA GENERAR UN FOLIO DE SOLICITUD Y UN REGISTRO DEL TRAMITE PARA PERSONA.###//
		Date fechaActual = new Date();
		Solicitud solicitud = new Solicitud();

		try {
			mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite tramite =
					new mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite();
			if (TipoPersona.TIPO_PERSONA_MORAL == tipo) {
				TramiteMoral tramiteRegistro = new TramiteMoral();
				tramiteRegistro.setMoral((Moral) persona);

				tramite = tramiteRegistro;
			} else if (TipoPersona.TIPO_PERSONA_FISICA == tipo) {
				TramiteFisica tramiteRegistro = new TramiteFisica();
				tramiteRegistro.setFisica((Fisica) persona);

				tramite = tramiteRegistro;
			}

			solicitud = solicitudRemote.crearSolicitudInicialPorEnum(EstadoSolicitudEnum.ATENDIDA,
					mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum.REGISTRO_PERSONA,
					OrigenSolicitudEnum.VENTANILLA, null);
			solicitud.setFechaPresentacion(fechaActual);

			solicitud = solicitudRemote.asociarTramiteSolicitudPorEnum(solicitud, tramite,
					TipoTramiteEnum.REGISTRO_DE_PERSONA, EstadoTramiteEnum.CERRADO);

			solicitud = solicitudRemote.crear(solicitud);

			log.info(" -- Folio de la Solicitud creada correctamente para tipo persona:::::::::::: "
					+ tipo + "::::::::::::::::::: CON NUMERO DE FOLIO: " + solicitud.getNoFolioSolicitud());
		} catch (SolicitudNoValidaException ex) {
			log.error(" -- No se puede crear la solicitud: " + ex.getMessage());
		}

		return solicitud;
	}

	//ORDENAMIENTO POR FECHA DE MODIFICACION DESCENDENTE FISICA
	private void orderListFisicaByDateDesc(List<Fisica> lista){
			Comparator<Fisica> comparator = new Comparator<Fisica>(){
				 public int compare(Fisica f1, Fisica f2) {
				    	try{
				    		return f2.getFechaModificacion().compareTo(f1.getFechaModificacion());
				    	}catch(Exception ex){
				    		f1.setFechaModificacion(f1.getFechaRegistro());
				    		f2.setFechaModificacion(f2.getFechaRegistro());
				    		return f2.getFechaModificacion().compareTo(f1.getFechaModificacion());
				    	}
				    }
			};
			Collections.sort(lista,comparator);
	}
	
	//ORDENAMIENTO POR FECHA DE REGISTRO DESCENDENTE MORAL
	private void orderListMoralByDateDesc(List<Moral> lista){
		Comparator<Moral> comparator = new Comparator<Moral>(){
			 public int compare(Moral m1, Moral m2) {
			    	try{
			    		return m2.getFechaRegistro().compareTo(m1.getFechaRegistro());
			    	}catch(Exception ex){
			    		return 0;
			    	}
			    }
		};
		Collections.sort(lista,comparator);
}
	
	@RequestMapping(value="/mostrar/folio", method = RequestMethod.POST)
	public String mostrarVistaFolio(HttpServletRequest request, Model model)  {
		String folio="";
		if(request.getParameter("folioRecibido")!=null){
			this.log.info(":::: FOLIO RECIBIDO: "+request.getParameter("folioRecibido"));
			folio = request.getParameter("folioRecibido");
		}
		model.addAttribute("folio", folio);
		return "mostrar.vista.folio";
	}
	
	/**
	 * 
  	 Author: Gustavo U. Trejo A.
  	 Date: 30/10/2014 10:33:13
  	 Description: Servicio para dar de alta a una persona de forma manual validado por IMSS.
     @param moral = Persona Moral
	 */
	@RequestMapping(value="/agregar", method = RequestMethod.POST)
	@ResponseBody
	public String agregarPeronsaManual(@ModelAttribute Fisica fisica)  {
		String data="";
		String carSeparador=";";
		String folio="";
		
		this.log.info(" -- Nombre(s): "+fisica.getNombre());
		this.log.info(" -- Primer Apellido: "+fisica.getPrimerApellido());
		this.log.info(" -- Segundo Apellido: "+fisica.getSegundoApellido());
		this.log.info(" -- SexoId: "+fisica.getSexo().getIdSexo());
		this.log.info(" -- Fecha de nacimiento: "+fisica.getFechaNacimiento());
		this.log.info(" -- Lugar de Nacimiento: "+fisica.getLugarNacimiento());
		
		try{
			fisica.setFechaModificacion(new Date());
			fisica.setPersonaCalificaciones(this.crearCalificacionPersona(fisica, null, CalificacionEnum.VALIDADO_IMSS.getCodigo().longValue()));
			Fisica fisicaDB = this.obtenerDatosPersona.altaPersonaFisica(fisica);
			
			data =fisicaDB.getIdPersona().toString();
			this.log.info(" ID GENERADO DE LA PERSONA FISICA: "+fisicaDB.getIdPersona());
			Solicitud sol = this.generarSolicitud(fisicaDB, TipoPersona.TIPO_PERSONA_FISICA);
			folio = sol.getNoFolioSolicitud();
		}catch(Exception ex){
			this.log.warn(" -- No se pudo agregar, la persona: "+ex.getMessage());
		}
		return data+carSeparador+folio;
	}

}
