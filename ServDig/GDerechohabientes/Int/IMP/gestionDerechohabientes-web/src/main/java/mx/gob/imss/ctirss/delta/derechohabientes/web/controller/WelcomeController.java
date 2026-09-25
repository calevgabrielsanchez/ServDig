/**
 * WelcomeController.java
 * @package mx.gob.imss.ctirss.delta.derechohabientes.web.controller
 * @project gestionDerechohabientes-web	
 */
package mx.gob.imss.ctirss.delta.derechohabientes.web.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.imageio.ImageIO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.web.bean.Busqueda;
import mx.gob.imss.ctirss.delta.derechohabientes.web.bean.Login;
import mx.gob.imss.ctirss.delta.derechohabientes.web.utils.DateUtils;
import mx.gob.imss.ctirss.delta.derechohabientes.web.utils.OpcionesProperties;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.AsignacionNSSNoLocalizadoException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.base.web.sso.UsuarioSSO;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.vigenciaderechos.ServiciosDTO;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.PatronServiceRemote;
import mx.gob.imss.ctirss.delta.model.enums.EstadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoInconsistenciaVigenciaEnum;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;
import mx.gob.imss.ctirss.delta.model.enums.ParentescoEnum;
import mx.gob.imss.ctirss.delta.model.enums.PerfilesEnum;
import mx.gob.imss.ctirss.delta.model.enums.SexoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.FileUploadVB;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RegistroPatronal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;
import mx.gob.imss.ctirss.delta.model.util.Constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @company IMSS (Instituto Mexicano del Seguro Social)
 * @date 28/08/2011
 */
@Controller
@RequestMapping(value = "/welcome/*")
public class WelcomeController extends AbstractController {

	@Autowired
	private OpcionesProperties opcionesProperties;
	
	private static final String KEY_PATRON_IMSS = "patronIMSS";
	private static final String KEY_GRUPO_FAMILIAR = "miGrupoFamiliar";
	private static final String KEY_PATRONES_ASEGURADO = "patrones";
	private static final String KEY_PATRON_ULTIMO_MOV = Constants.PATRON_SUJETO;
	private static final String KEY_ASEGURADO_FALLECIDO ="aseguradoFallecido";
	private static final String RP17_CON_SERVICIO = "M6610218175";
	private static final String[] RPS_17 = new String[] {"A7711544174","M6610218175","B3710738106"};
	
	private static final String PATRON_JCF = "Y5845183";
	private static final String DES_MODALIDAD_32_JCF = "PROGRAMA JOVENES CONSTRUYENDO EL FUTURO";
	private static final String DES_MODALIDAD_32_INST_EDUCATIVA = "SEGURO FACULTATIVO ESTUDIANTES";
	private static final String DES_MODALIDAD_32_CFE = "SEGURO FACULTATIVO IMSS / CFE";
	
	@EJB
	private PatronServiceRemote patronServices;

	@Autowired
	private GrupoFamiliarServiceRemote grupoFamiliarService;
	
	@Autowired 
	private SessionControler sessionControler;
	
	@RequestMapping("/ventanilla")
	public String welcome(HttpServletRequest request,HttpSession session) {
		return "inicial";
	}
	
	@RequestMapping(value = "uno/busqueda")
	public String home3(Login login, HttpServletRequest request, Model model,
			HttpSession session) {
		
		sessionControler.limpiarSession(session);
		
		String forward = null;
		Busqueda busqueda = new Busqueda();
		Usuario miUsuario = null;


		try {

			UsuarioSSO usr = this.procesarUsuarioSSO(request);
			forward = Constants.BUSQUEDA_PRINCIPAL_FORDWARD;
			miUsuario = sessionControler.validarSesionUsuario(session, usr);
			busqueda.setUsuario(miUsuario.getUsuario());
			busqueda.setFechaSistema(new Date());
			busqueda.setPerfil(miUsuario.getPerfilUsuario().getIdPerfilUsuario());
			busqueda.setDelegacion(miUsuario.getUsuarioFuncionario().getDelegacion().getDescripcion());
			if(miUsuario.getUsuarioFuncionario() != null && miUsuario.getUsuarioFuncionario().getUnidadMedicaFamiliar() != null) {
				busqueda.setUmf(miUsuario.getUsuarioFuncionario().getUnidadMedicaFamiliar().getNombreCorto());
			}	
		} catch (Exception e){
			log.error("Error al validar el usuario", e);
			forward = "internalError";
			request.setAttribute("exception", e.getMessage());
			request.setAttribute("error", e);
			return forward;

		}
		//opciones de menus
		request.setAttribute("opciones", opcionesProperties.getOpciones());
		model.addAttribute(Busqueda.REQ_NAME, busqueda);
		return forward;
	}

/** metodo que no se utiliza actualmente
	@RequestMapping(value = "busquedaTramitador")
	public String forwardBusquedaTramitador(HttpSession session, Model model) {
		Usuario miUsuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		
		
		Busqueda busqueda = new Busqueda();
		busqueda.setFechaSistema(new Date());
		busqueda.setUsuario(miUsuario.getFisica().getNombre() + " "
				+ miUsuario.getFisica().getPrimerApellido()+ " "+ miUsuario.getFisica().getSegundoApellido());
		
		busqueda.setDelegacion(miUsuario.getUsuarioFuncionario().getDelegacion().getDescripcion());
		busqueda.setUmf(miUsuario.getUsuarioFuncionario().getUnidadMedicaFamiliar().getNombreCorto());
		busqueda.setPerfil(miUsuario.getPerfilUsuario().getIdPerfilUsuario());
		
		model.addAttribute(Busqueda.REQ_NAME, busqueda);
		return Constants.BUSQUEDA_PRINCIPAL_FORDWARD;
	}
**/
	
	
	
	@RequestMapping(value = "uno/valida")
	public String home2(Busqueda busqueda, HttpServletRequest request,
			Model model, RegistroPatronal registroPatronal, HttpSession session) {
		session.removeAttribute("solicitudActiva");
		
		sessionControler.limpiarSession(session);
		
		AsignacionNSS asig = null;
		String numNss = null;
		
		busqueda.setError(null);
		Usuario usuario = (Usuario) session.getAttribute(Usuario.SES_NAME);
		
		if(busqueda.getNss() == null && busqueda.getFolio() == null) {
			asig = (AsignacionNSS) session.getAttribute(Constants.ASIGNACION_NSS_SESSION_NAME);
			if(asig != null ){
				numNss = asig.getNssStr();
			}
		} else {
			if (busqueda.getBusca().equals("nss")) {
				numNss = busqueda.getNss().trim();
				if(numNss.length() == 10){										
					numNss = numNss+generaDigitoVerificador(numNss);								
				}
			} else {
				numNss = null;
			}

		}
		
		model.addAttribute("persona",usuario.getFisica());
		return this.busquedaNss(asig, numNss, busqueda, request, model, session);
		
	}
	
	

	private Integer generaDigitoVerificador(String nss){  
		int suma = 0;
		int resultado = 0;
		for(int i = 1 ; i <= nss.length() ; i++){
			if(i%2==0){
				int multiplicacion = (Integer.parseInt(nss.charAt(i-1)+"")) * 2;
				if(multiplicacion > 9 ){
					suma = suma + ((multiplicacion-10)+1);
				}else{
					suma = suma + multiplicacion;
				}
			}else{
				suma = suma + (Integer.parseInt(nss.charAt(i-1)+""));
			}	
		}
		int modulo = suma%10;
		if(modulo == 0 ){
			resultado = 0;
		}else if( modulo < 10){
			resultado = 10-modulo;
		}
		return resultado;
	}
	
	private String functionErrorBusqueda(Busqueda busqueda,HttpServletRequest request, Model model, String mensajeError) {
		String fordward = Constants.BUSQUEDA_PRINCIPAL_FORDWARD;
		busqueda.setError(mensajeError);
		model.addAttribute(Busqueda.REQ_NAME, busqueda);// formulario
		return fordward;
	}
	
	private String busquedaNss(AsignacionNSS asignacionNss, String numNss, Busqueda busqueda,HttpServletRequest request, Model model,
			HttpSession session) {
		
		log.debug("Se empiezan las consultas");
		
		
		//Elimina el bean de documentos probatorios en caso de que existe
		session.removeAttribute(FileUploadVB.SES_NAME);
		
		String fordward = Constants.GRUPO_FAMILIAR_FORDWARD;
		GrupoFamiliar asegurado = null;
		CabezaGrupoFamiliar cabezaGrupoFamiliar = null;
		String mensajeError = null;
		Boolean patronIMSS = false;
		String conAsegurado = Constants.CON_ASEGURADO_EXTERNO;
		List<SujetoObligado> patronesAsegurado = new ArrayList<SujetoObligado>();
		List<ServiciosDTO> servicios = new ArrayList<ServiciosDTO>();
		
		String elemento = Constants.EXTERNO;
		String conDetalleS = Constants.SIN_DETALLE_SITUACION;
		
		// --------------------------------------------------------------
		// Parentescos que se buscaran por default en GrupoFamiliar.
		// En BDTU se busca adem�s Pensionado
		// --------------------------------------------------------------
		List<Long> parentescosAsegurados = new ArrayList<Long>();
		parentescosAsegurados.add(ParentescoEnum.ASEGURADO.getId());
		
		
		try {
			
			//String mensajeError = null;
			if(asignacionNss == null) {
				try{
					
					// -------------------------------------------------------------------------------
					// Buscamos el asegurado en BDTU y en CL3(Estudiantes).
					// Como el segundo parametro es true, se obtendr� el estado de inconsistencia.
					// -------------------------------------------------------------------------------
					asegurado = grupoFamiliarService.getGrupoFamiliar(numNss, true);
				
					asignacionNss = asegurado.getAsignacionNSS();
					elemento = Constants.INTERNO;
					
				}catch(DerechohabientesBusinessException e){
					log.error("ERROR AL CONSULTAR EL NSS ", e);
					mensajeError = e.getMessage();
				}catch(Exception e) {
					log.error("ERROR GENERAL AL CONSULTAR EL NSS E ", e);
					if(e instanceof AsignacionNSSNoLocalizadoException) {
						log.error("AsignacionNSSNoLocalizadoException ", e);
						return this.functionErrorBusqueda(busqueda, request, model, mensajeError == null ? ExceptionMessages.ERROR_ASEGURADO_EN_BAJA: mensajeError);
					}else {
						mensajeError = e.getMessage();
					}
				}
			}
			
		
			if(asignacionNss == null ){
				return this.functionErrorBusqueda(busqueda, request, model, mensajeError == null ? ExceptionMessages.NSS_NO_ENCONTRADO : mensajeError);
			}
			
			
			
			// -----------------------------------------------------------------------------------
			// A los inconsistentes solamente se los mostrara el reporte de vigencia.
			// -----------------------------------------------------------------------------------
			if( asignacionNss.getEstadoInconsistencia().intValue() == EstadoInconsistenciaVigenciaEnum.ASEGURADO.getId()  
				|| asignacionNss.getEstadoInconsistencia().intValue() == EstadoInconsistenciaVigenciaEnum.BENEFICIARIOS.getId()){
				
				session.setAttribute(KEY_GRUPO_FAMILIAR, asegurado);
				session.setAttribute(Constants.CABEZA_GRUPO_FAM_SESSION, cabezaGrupoFamiliar);
				session.setAttribute(Constants.ASIGNACION_NSS_SESSION_NAME,asignacionNss);			
				
				model.addAttribute(KEY_GRUPO_FAMILIAR, asegurado);
				model.addAttribute(Constants.CABEZA_GRUPO_FAM_SESSION, cabezaGrupoFamiliar);
				
				return Constants.GRUPO_FAMILIAR_INCONSISTENTES;
			}
				
			
			
		
			// ---------------------------------------------------------------
			if(asignacionNss.getIdAsignacionNSS() == null ){
				return this.functionErrorBusqueda(busqueda, request, model, ExceptionMessages.NSS_NO_ENCONTRADO);
			}
			
			
			
			try{
				cabezaGrupoFamiliar = grupoFamiliarService.cabezaGrupoFamiliar(asignacionNss.getIdAsignacionNSS());
			} catch(DerechohabientesBusinessException e){
				log.error("ocurrio  un error al obtener la cabeza del grupo familiar ", e);
				mensajeError = "No fue posible localizar la cabeza de grupo familiar: " + e.getMessage();
			}catch(Exception e){
				log.error("ocurrio un error al obtener la cabeza del grupo familiar", e);
				mensajeError = "Ocurri&oacute; un error al consultar la cabeza de grupo familiar "+asignacionNss.getIdAsignacionNSS();
			}
			
			if(mensajeError != null) {
				return this.functionErrorBusqueda(busqueda, request, model, mensajeError);
			}
			
			if(cabezaGrupoFamiliar == null) {
				return this.functionErrorBusqueda(busqueda, request, model, "No se encontr&oacute; la cabeza de grupo familiar");
			}
			
			if( cabezaGrupoFamiliar.getEsEstudiante()  ){
				fordward = Constants.GRUPO_FAMILIAR_ESTUDIANTES;
			}
			
			
			//Se checa si la cabeza de grupo familiar tiene patronIMSS
			patronIMSS = cabezaGrupoFamiliar.getPatronImss() == null ? false : cabezaGrupoFamiliar.getPatronImss().equals(1);
			
			
			
			if(cabezaGrupoFamiliar.getFechaFinVigencia() != null) {
				String fechaS = DateUtils.dateToStringConFormato(cabezaGrupoFamiliar.getFechaFinVigencia(), "dd/MM/yyyy");
				if(fechaS.substring(6).equals("3000")){
					conDetalleS = Constants.CON_DETALLE_SITUACION;
				}else{
					conDetalleS = Constants.SIN_DETALLE_SITUACION;
				}
			}else{
				conDetalleS = Constants.SIN_DETALLE_SITUACION;
			}
			
			
			
			
			//log.debug("el servicio medico es [" +asegurado.getConDerechoSm()+"]");
			String strServicioMedicoSiNo =asegurado.getConDerechoSm();
			asegurado = grupoFamiliarService.getCabezaGrupaFamilarRegistrada(asignacionNss,cabezaGrupoFamiliar);
			asegurado.setConDerechoSm(strServicioMedicoSiNo);
			
			
			if( cabezaGrupoFamiliar.getEsEstudiante() )
				asegurado.setCalidad(new BigDecimal(ParentescoEnum.ASEGURADO.getId()));
			
			
			if( asegurado.getIndRegistrado() == 1 ){
				conAsegurado = Constants.CON_ASEGURADO_INTERNO;
			}else{
				
				if(cabezaGrupoFamiliar.getCalidadParentesco().getIdParentesco().equals(ParentescoEnum.ASEGURADO.getId())) {
					conAsegurado = Constants.SIN_ASEGURADO_INTERNO;
				}else {
					conAsegurado = Constants.SIN_PENSIONADO_INTERNO;
				}
				
				
				
				// ---------------------------------------------------------
				// Si el estudiante no esta registrado en GrupoFamiliar
				// se env�a al cambio de cl�nica
				// ---------------------------------------------------------
				fordward = Constants.GRUPO_FAMILIAR_FORDWARD2;
				if( cabezaGrupoFamiliar.getEsEstudiante() ){
					
					TramiteCorreccionDerechohabiente correccion = this.convetirGrupoCorreccion(asegurado);
					model.addAttribute("tramiteCorreccion", correccion);
					
					fordward = Constants.CAMBIO_CLINICA_ESTUDIANTES;
				}
			}
			//Si la calidad es pensionado(6) y 
			//Si el asegurado tiene Preafiliacion (Modalidad=00) no se buscan patrones
			if(cabezaGrupoFamiliar.getPatronSujetoObligado() != null){
				if(!(cabezaGrupoFamiliar.getCalidadParentesco().getIdParentesco().equals(ParentescoEnum.PENSIONADO.getId()) && cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad().equals("00"))){
			
					if(!cabezaGrupoFamiliar.getEsEstudiante()){
			
						try{
							patronesAsegurado = grupoFamiliarService.getPatronesAsegurado(asignacionNss);	
						}catch(Exception e){
							log.error("Ocurrio un error al obtener a los patrones del asegurado", e);
							return this.functionErrorBusqueda(busqueda, request, model, 
							"Ocurri&oacute; un error al obtener a los patrones activos del asegurado / pensionado");
						}
				
					}
				}
			}

			//Si la calidad es pensionado(6), con último movimiento afiliatorio modalidad 17 y el patron 
			//tiene convenio, se toma el valor <ConDerechoSm> que regresa el WS de vigencia.
			Boolean isPensionadoMod17Convenio = false;
			if(cabezaGrupoFamiliar.getPatronSujetoObligado() != null){
				if((cabezaGrupoFamiliar.getCalidadParentesco().getIdParentesco().equals(ParentescoEnum.PENSIONADO.getId())))  {
					if (cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad().equals("17")){
						for(String rpMod17: RPS_17) {
							if((cabezaGrupoFamiliar.getPatronSujetoObligado().getNumeroRegistroPatronal()+cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad()+cabezaGrupoFamiliar.getPatronSujetoObligado().getDigVerificador()).equals(rpMod17)){
								log.error("Si es patron con convenio " + rpMod17);
								isPensionadoMod17Convenio = true;
							}
						}
					} else {
						isPensionadoMod17Convenio = true;
					}
				}
			}

			Map<String, Boolean> validaciones17 = this.validarModalidad17(cabezaGrupoFamiliar, patronesAsegurado); 
			Boolean isModalidad17 = validaciones17.get("isModalidad17");
			Boolean isPatron17ConServicios = validaciones17.get("isPatron17ConServicios");
			
			if(cabezaGrupoFamiliar.getPatronSujetoObligado() != null){
				String idMod = cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad();			
				if(idMod.equals(ModalidadEnum.TREINTAYDOS.getNumModalidad())){
					cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().setDescripcion(DES_MODALIDAD_32_CFE);
				
					String regPatronal = cabezaGrupoFamiliar.getPatronSujetoObligado().getNumeroRegistroPatronal();
					if(regPatronal.equals(PATRON_JCF)){
						cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().setDescripcion(DES_MODALIDAD_32_JCF);
					}
				
					if (patronServices.isRegistroPatronalnstitucionEducativa(regPatronal + idMod)) {
						cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().setDescripcion(DES_MODALIDAD_32_INST_EDUCATIVA);
					}				
				}
			}

			session.setAttribute("ISMODALIDAD17", isModalidad17);
			session.setAttribute("ISRP17CONSERVICIOS", isPatron17ConServicios);
			session.setAttribute("isPensionadoMod17Convenio", isPensionadoMod17Convenio);
			session.setAttribute(KEY_PATRON_IMSS, patronIMSS);
			session.setAttribute(KEY_PATRON_ULTIMO_MOV,cabezaGrupoFamiliar.getPatronSujetoObligado());
			session.setAttribute(KEY_PATRONES_ASEGURADO, patronesAsegurado);
			session.setAttribute(KEY_GRUPO_FAMILIAR, asegurado);
			session.setAttribute(Constants.ASIGNACION_NSS_SESSION_NAME,asignacionNss);			
			session.setAttribute("conAsegurado", conAsegurado);
			session.setAttribute("elemento", elemento);
			session.setAttribute("conDetalleS", conDetalleS);
			session.setAttribute(Constants.CABEZA_GRUPO_FAM_SESSION, cabezaGrupoFamiliar);
			session.setAttribute("perfilUsuario", PerfilesEnum.TRAMITADOR.getId());
			session.setAttribute(KEY_ASEGURADO_FALLECIDO,
					(cabezaGrupoFamiliar.getSubEstadoDerechohabiente().getIdSubEstadoDerechohabiente().intValue()==2)?1:0);
			
			model.addAttribute("cambioClinica", false);
			model.addAttribute("miGrupoFamiliar", asegurado);
			model.addAttribute(Constants.CABEZA_GRUPO_FAM_SESSION, cabezaGrupoFamiliar);
			model.addAttribute("servicios", servicios);
			//opciones de menus
			request.setAttribute("opciones", opcionesProperties.getOpciones());
			
			if( asegurado.getIndRegistrado() == 1 ){
				// ----------------------------------------------------------
				// Existe en BDTU pero puede tener o no cl�nica y domicilio
				// ----------------------------------------------------------s
				String forw =  this.asignacionDeDomicilioYOClinica(asegurado, session, model,patronIMSS, cabezaGrupoFamiliar);
				
				 if (forw != null) 
					 return forw;
				 else 
					 log.debug("El asegurado si tiene domicilio");
			}
			
			
			
			
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
			mensajeError = e.getMessage();
		}catch (Exception e){
			e.printStackTrace();
			mensajeError = "Se encontraron inconsistencias en los datos";
		}
		
		if(mensajeError != null) {
			return this.functionErrorBusqueda(busqueda, request, model, mensajeError);
		}
		
		return fordward;
	}
	
	
	private Map<String, Boolean> validarModalidad17(CabezaGrupoFamiliar cabezaGrupoFamiliar, List<SujetoObligado> patronesAsegurado) {
		Map<String, Boolean> result = new HashMap<String, Boolean>();
		Boolean isModalidad17 = false;
		Boolean isPatron17ConServicios = false;
		Long idEstadoCabeza = cabezaGrupoFamiliar.getEstadoDerechohabiente().getIdEstadoDerechohabiente();
		
		if(!idEstadoCabeza.equals(EstadoDerechohabienteEnum.BAJA.getId())) {
			List<String> rpsAsegurado = new ArrayList<String>();
			if(!idEstadoCabeza.equals(EstadoDerechohabienteEnum.VIGENTE.getId())
					&&cabezaGrupoFamiliar.getPatronSujetoObligado() != null && cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad() != null) {
				
					String rpValidar = cabezaGrupoFamiliar.getPatronSujetoObligado().getNumeroRegistroPatronal() +
							cabezaGrupoFamiliar.getPatronSujetoObligado().getModalidad().getNumModalidad()
							+cabezaGrupoFamiliar.getPatronSujetoObligado().getDigVerificador();
					rpsAsegurado.add(rpValidar);
			} 
			
			if(idEstadoCabeza.equals(EstadoDerechohabienteEnum.VIGENTE.getId()) && patronesAsegurado != null && !patronesAsegurado.isEmpty()) {
				
				for(SujetoObligado pat: patronesAsegurado) {
					rpsAsegurado.add(pat.getNumeroRegistroPatronal()+pat.getModalidad().getNumModalidad()+pat.getDigVerificador());
				}
				
			}
			
			if(!rpsAsegurado.isEmpty()) {
				for(String rpMod17: RPS_17) {
					for(String rpAsegurado: rpsAsegurado) {
						log.error("El patron modalidad 17 del asegurado es " + rpAsegurado);
						if(rpAsegurado.equals(rpMod17)){
							log.error("Si es patron con convenio " + rpAsegurado);
							isModalidad17 = true;
							if(rpAsegurado.equals(RP17_CON_SERVICIO)) {
								log.error("El patron tiene servicios" + rpAsegurado);
								isPatron17ConServicios = true;
							}
						}
					}
				}
			}
		}
		
		result.put("isModalidad17", isModalidad17);
		result.put("isPatron17ConServicios", isPatron17ConServicios);
		
		return result;
	}
	
	

	private String asignacionDeDomicilioYOClinica(GrupoFamiliar grupoFamiliar, HttpSession session,
			Model model, Boolean isPatronImss, CabezaGrupoFamiliar cabezaGrupoFamiliar) {
		/*
		
		Boolean tieneConcubinaPadres = false;
		Boolean domicilioOtro = false;
		Domicilio defaultDomicilio = null;
		*/
		if( grupoFamiliar.getDomicilio() == null ||  grupoFamiliar.getMedicoEnTurno() == null){

			if( cabezaGrupoFamiliar.getEsEstudiante() ){
				TramiteCorreccionDerechohabiente correccion = this.convetirGrupoCorreccion(grupoFamiliar);
				model.addAttribute("tramiteCorreccion", correccion);
				return Constants.CAMBIO_CLINICA_ESTUDIANTES;
			} /*else{
				if(grupoFamiliar.getDomicilio() == null) {
					try {
						List<GrupoFamiliar> concubinas = grupoFamiliarService.findGrupoFamiliarParentescoEstado(
								grupoFamiliar.getAsignacionNSS().getIdAsignacionNSS(), ParentescoEnum.CONCUBINARIO.getId(), EstadoDerechohabienteEnum.VIGENTE.getId());
					
						if(concubinas != null && !concubinas.isEmpty()) {
							tieneConcubinaPadres = true;
							
							for(GrupoFamiliar con : concubinas) {
								if(con.getDomicilio() != null) {
									defaultDomicilio = con.getDomicilio();
									domicilioOtro = true;
									break;
								}
							}
							
						} else {
							//Solo si no es patron imss buscaremos el domicilio de los padres
							if(!isPatronImss) {
								concubinas = grupoFamiliarService.findGrupoFamiliarParentescoEstado(
										grupoFamiliar.getAsignacionNSS().getIdAsignacionNSS(), ParentescoEnum.PADRES.getId(), EstadoDerechohabienteEnum.VIGENTE.getId());
								
								if(concubinas != null && !concubinas.isEmpty()) {
									tieneConcubinaPadres = true;
									
									for(GrupoFamiliar con : concubinas) {
										if(con.getDomicilio() != null) {
											defaultDomicilio = con.getDomicilio();
											domicilioOtro = true;
											break;
										}
									}
								}
							}
						}
					} catch (Exception e) {
						log.warn("No fue posible ebcontrar a los padres o concubinas", e);
					}
				}
				
				try {
					TramiteCorreccionDerechohabiente datosActuales = this.convetirGrupoCorreccion(grupoFamiliar);
					session.setAttribute(Constants.ASIGNACION_NSS_SESSION_NAME,grupoFamiliar.getAsignacionNSS());
					
					if(defaultDomicilio != null) {
						grupoFamiliar.setDomicilio(defaultDomicilio);
					}
					TramiteCorreccionDerechohabiente correccion = this.convetirGrupoCorreccion(grupoFamiliar);
					
					model.addAttribute("tieneConcubinaPadres", tieneConcubinaPadres ? 1 : 0);
					model.addAttribute("domicilioOtro", domicilioOtro ? 1 : 0);
					model.addAttribute("datosActuales", datosActuales);
					model.addAttribute("derechohabiente", correccion);
					model.addAttribute("miGrupoFamiliar",grupoFamiliar);
					model.addAttribute("hijo", grupoFamiliar);
					model.addAttribute("idUmfPersona",grupoFamiliar.getMedicoEnTurno() != null ?  grupoFamiliar.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF() : 0);
					
				} catch (Exception e) {
					
					log.debug(grupoFamiliar);
					e.printStackTrace();
					
				}
			
				return Constants.ASIGNACION_DOMICILIO_FORWARD;
			}*/
			
			
		} 
		
		return null;
	}
	
	/**
	 * Metodo para pasar los datos de un objeto de tipo GrupoFamiliar aun CorreccionDatoDerechohabiente
	 * @param integrante
	 * @return
	 */
	private TramiteCorreccionDerechohabiente convetirGrupoCorreccion(GrupoFamiliar integrante) {
		TramiteCorreccionDerechohabiente correccion = new TramiteCorreccionDerechohabiente();
		
		correccion.setIdPersona(integrante.getDerechohabiente().getIdPersona());
		correccion.setNombre(integrante.getDerechohabiente().getNombre());
		correccion.setPrimerApellido(integrante.getDerechohabiente().getPrimerApellido());
		correccion.setSegundoApellido(integrante.getDerechohabiente().getSegundoApellido());
		correccion.setCurpCap(integrante.getDerechohabiente().getCurp());
		if(integrante.getDerechohabiente().getSexo().getIdSexo().equals(SexoEnum.MUJER.getId()) && integrante.getParentesco().getIdParentesco().equals(ParentescoEnum.PADRES.getId())){
			correccion.setSexo(new Sexo());
			correccion.getSexo().setIdSexo(Integer.parseInt(""+ParentescoEnum.MADRE.getId()));
		} else {
			correccion.setSexo(integrante.getDerechohabiente().getSexo());
		}
		correccion.setFechaNacimiento(integrante.getDerechohabiente().getFechaNacimiento());
		correccion.setLugarNacimiento(integrante.getDerechohabiente().getLugarNacimiento());
		correccion.setParentesco(integrante.getParentesco());
		correccion.setCalidad(integrante.getCalidad().toString());
		correccion.setIdAsignacionNss(integrante.getAsignacionNSS().getIdAsignacionNSS());
		correccion.setNss(integrante.getAsignacionNSS().getNssStr());
		correccion.setDomicilio(integrante.getDomicilio());
		correccion.setMedicoEnTurno(integrante.getMedicoEnTurno());
		correccion.setEstadoCivil(integrante.getDerechohabiente().getEstadoCivil());
		correccion.setCorreoElectronico(integrante.getDerechohabiente().getCorreoElectronico());
		correccion.setFacebook(integrante.getDerechohabiente().getFacebook());
		correccion.setTwitter(integrante.getDerechohabiente().getTwitter());
		correccion.setTelefonoFijo(integrante.getDerechohabiente().getTelefonoFijo());
		correccion.setTelefonoMovil(integrante.getDerechohabiente().getTelefonoMovil());
		correccion.setMesRegistroNac(integrante.getDerechohabiente().getMesRegistroNac());
		correccion.setAnioRegistroNac(integrante.getDerechohabiente().getAnioRegistroNac());
		
		if(integrante.getMedicoEnTurno() != null && integrante.getMedicoEnTurno().getUnidadMedicaFamiliar() != null) {
			correccion.setIdUmfOrigen(integrante.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF());
		}
		
		return correccion;
	}
	
	
	
	
}