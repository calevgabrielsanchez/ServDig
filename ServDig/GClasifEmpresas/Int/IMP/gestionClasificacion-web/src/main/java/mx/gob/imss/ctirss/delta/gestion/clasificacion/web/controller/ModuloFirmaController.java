/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Archivo:ModuloFirmaController.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.web.controller
 */

package mx.gob.imss.ctirss.delta.gestion.clasificacion.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.DefaultMessageContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import mx.gob.imss.ctirss.delta.exception.clasificacion.DatosClemException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.FiltrosConsultaFirmaClemDataTable;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.FirmaClemDTO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.ResolucionVO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clem.DatosClemServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clem.FirmaClemServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.web.utils.Constantes;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.web.utils.Utiles;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.clasificacion.CodigoRolClasificacion;
import mx.gob.imss.ctirss.delta.model.clasificacion.ReporteClemBean;
import mx.gob.imss.ctirss.delta.web.validator.FiltrosClemFirmadaValidator;
import mx.gob.imss.ctirss.delta.web.validator.FiltrosFirmaValidator;

@Controller
@RequestMapping(value = "/modulo/firma")
public class ModuloFirmaController extends AbstractController {

	@Autowired
	FirmaClemServiceBusinessRemote firmaClemBusiness;
	
	@Autowired
	DatosClemServiceBusinessRemote datosClemBusiness;
	
	
	@RequestMapping(method = RequestMethod.GET)
	public String consultarSolicitudesSinFirma(Model model, HttpSession session) {
		this.log.debug("[ModuloFirmaController] - " + "consultarSolicitudesSinFirma ");
		
		/* Obtenemos el objeto de usuario de la sesion */
		Usuario usuario = (Usuario) session.getAttribute(KEY_USUARIO);
		int iRol = usuario.getPerfilUsuario().getIdPerfilUsuario().intValue();

		session.setAttribute("menuDecoration", "1");
		session.setAttribute("grupoTramite", "0");
		
		String vista = "firmaClem";
		
		if (!perfilUsuarioValido(iRol)) { // si no es un perfil valido para firmar la clem, solo podra ver clem firmadas
			vista = "verClemFirmada";
		}
		
		return vista;
	}	
	
	@RequestMapping(value = "/paginar/clem", method = RequestMethod.POST)
	public @ResponseBody
	DatosSalidaPaginador<FirmaClemDTO> paginarClem(@RequestBody FiltrosConsultaFirmaClemDataTable aoData,
			HttpSession session, HttpServletResponse response) {

		this.log.debug("[ModuloFirmaController] - " + "paginarClem ");
		DatosSalidaPaginador<FirmaClemDTO> clemList = null;
		/* Obtenemos el objeto de usuario de la sesion */
		Usuario usuario = (Usuario) session.getAttribute(KEY_USUARIO);
		int iRol = usuario.getPerfilUsuario().getIdPerfilUsuario().intValue();
		//Se valida usuario
		if (!perfilUsuarioValido(iRol)) {
			log.error("::: El usuario "+usuario.getUsuario()+" no tiene un perfil valido para ejecutar esta accion, rol: " + iRol);
			return null;
		}
		
		this.log.debug("::: Parametros recibidos ::::");
		this.log.debug(aoData.getoForm().toString());
		
		if (perfilDelegacionClemSinFirma(iRol)) {
			aoData.getoForm().setIdDelegacion(usuario.getUsuarioFuncionario().getDelegacion().getId());
			aoData.getoForm().setIdSubDelegacion(new Long("0"));
		} else if (perfilSubDelegacionClemSinFirma(iRol)) {
			aoData.getoForm().setIdDelegacion(usuario.getUsuarioFuncionario().getDelegacion().getId());
			aoData.getoForm().setIdSubDelegacion(usuario.getUsuarioFuncionario().getSubdelegacion().getId());
		}

		DatosEntradaPaginador<FirmaClemDTO> send = new DatosEntradaPaginador<FirmaClemDTO>();
		send.parserArray(aoData.getAoData());
		send.setModelo(aoData.getoForm());

		/* Codigo para el manejo de las validaciones de los campos requeridos */
		Map result = new HashMap<String, Object>();
		Errors errors = new BindException(aoData.getoForm(), "model");
		new FiltrosFirmaValidator().validate(aoData.getoForm(), errors);

		if (errors.hasErrors()) {
			clemList = new DatosSalidaPaginador<FirmaClemDTO>();
			this.procesaErroresDeCaptura(errors, result, response);
			clemList.setErroresCaptura((List) result.get(KEY_CODE_ERROR_FIELDS));
			return clemList;
		}
		
		this.log.debug("[ Invocando al servicio EJB solicitudService ] - " + "consultarSolicitudesConcluidas");
		this.log.debug(":::: Filtros :::::");
		this.log.debug("Delegacion: " + send.getModelo().getIdDelegacion() + ", Subdelegacion: " + send.getModelo().getIdSubDelegacion()
				+ ", fechaIni: " + Constantes.FORMATO_FECHA_YYYY_MM_DD.format(send.getModelo().getStrPeriodoInicio()) 
				 + ", fechaFin: " + Constantes.FORMATO_FECHA_YYYY_MM_DD.format(send.getModelo().getStrPeriodoFin())
				+ ", tipoClem: " + send.getModelo().getTipoClem());
		clemList = firmaClemBusiness.buscaClemSinFirmaPaginado(send);
		clemList.setsEcho(send.getsEcho());
		
		session.setAttribute("clemList", clemList.getAaData());
		session.setAttribute("tipo_clem", send.getModelo().getTipoClem());
		this.log.debug(" [Termino la invocacion..] solicitudes [" + clemList + "]");
		return clemList;
	}

	@RequestMapping(value = "/ver/firmadas", method = RequestMethod.GET)
	public String verClemFirmadas(Model model, HttpSession session) {
		this.log.debug("[ModuloFirmaController] - " + "verClemFirmadas ");
		
		/* Obtenemos el objeto de usuario de la sesion */
		Usuario usuario = (Usuario) session.getAttribute(KEY_USUARIO);
		int iRol = usuario.getPerfilUsuario().getIdPerfilUsuario().intValue();
		
		log.info("----------------------------Seguimiento 2 /ver/firmadas ----------------------");
		if(usuario == null || usuario.getUsuario().isEmpty()){
			log.info("----------- Usuario nulo o vacio --------");
		}else {
			log.info("----------- Usuario: " + usuario.getUsuario() + " --- Rol: " + iRol);
		}
		

		//!(14 DELEGADO_DEL, 17 SUBDELEGADO_SUBDEL, 19 JEFE_OFICINA_COBROS_SUBDEL)
		if (!perfilUsuarioValido(iRol)) { // si no es un perfil valido para firmar la clem, solo podra ver clem firmadas
			log.debug("----------- Entra a IF de seg2. Usuario Rol invalido");
			session.setAttribute("menuDecoration", "1");
		}else{
			log.debug("----------- Entra a Else de seg2. Usuario Rol valido");
			//resalta/subraya la opcion "Ver Clem con firma" (para ciertos roles).
			session.setAttribute("menuDecoration", "3");
		}

		//grupoTramite = 0 -> muestra el menu del modulo de firma.
		session.setAttribute("grupoTramite", "0");
		String vista = "verClemFirmada";
		
		return vista;
	}		
	
	@RequestMapping(value = "/paginar/clem/firmada", method = RequestMethod.POST)
	public @ResponseBody
	DatosSalidaPaginador<FirmaClemDTO> paginarClemFirmada(@RequestBody FiltrosConsultaFirmaClemDataTable aoData,
			HttpSession session, HttpServletResponse response) {

		this.log.debug("[ModuloFirmaController] - " + "paginarClemFirmada ");
		DatosSalidaPaginador<FirmaClemDTO> clemList = null;
		/* Obtenemos el objeto de usuario de la sesion */
		Usuario usuario = (Usuario) session.getAttribute(KEY_USUARIO);
		int iRol = usuario.getPerfilUsuario().getIdPerfilUsuario().intValue();
		
		this.log.debug("::: Parametros recibidos ::::");
		this.log.debug(aoData.getoForm().toString());
		
		if (perfilDelegacionClemFirmada(iRol)) {
			aoData.getoForm().setIdDelegacion(usuario.getUsuarioFuncionario().getDelegacion().getId());
			aoData.getoForm().setIdSubDelegacion(new Long("0"));
		} else if (perfilSubDelegacionClemFirmada(iRol)) {
			aoData.getoForm().setIdDelegacion(usuario.getUsuarioFuncionario().getDelegacion().getId());
			aoData.getoForm().setIdSubDelegacion(usuario.getUsuarioFuncionario().getSubdelegacion().getId());
		}
		
		DatosEntradaPaginador<FirmaClemDTO> send = new DatosEntradaPaginador<FirmaClemDTO>();
		send.parserArray(aoData.getAoData());
		send.setModelo(aoData.getoForm());

		/* Codigo para el manejo de las validaciones de los campos requeridos */
		Map result = new HashMap<String, Object>();
		Errors errors = new BindException(aoData.getoForm(), "model");
		new FiltrosClemFirmadaValidator().validate(aoData.getoForm(), errors);

		if (errors.hasErrors()) {
			clemList = new DatosSalidaPaginador<FirmaClemDTO>();
			this.procesaErroresDeCaptura(errors, result, response);
			clemList.setErroresCaptura((List) result.get(KEY_CODE_ERROR_FIELDS));
			return clemList;
		}
		
		this.log.debug("[ Invocando al servicio EJB solicitudService ] - " + "consultarSolicitudesConcluidas");
		this.log.debug(":::: Filtros :::::");
		this.log.debug("Delegacion: " + send.getModelo().getIdDelegacion() + ", Subdelegacion: " + send.getModelo().getIdSubDelegacion());
		if(send.getModelo().getStrPeriodoInicio() != null ){
			this.log.debug("Periodo Inicio: " + Constantes.FORMATO_FECHA_YYYY_MM_DD.format(send.getModelo().getStrPeriodoInicio()));
		}
		if(send.getModelo().getStrPeriodoFin() != null ){
			this.log.debug("Periodo Fin: " + Constantes.FORMATO_FECHA_YYYY_MM_DD.format(send.getModelo().getStrPeriodoFin()));
			
		}
		if(send.getModelo().getStrPerIniF() != null ){
			this.log.debug("Periodo Inicio: " + Constantes.FORMATO_FECHA_YYYY_MM_DD.format(send.getModelo().getStrPerIniF()));
		}
		if(send.getModelo().getStrPerFinF() != null ){
			this.log.debug("Periodo Fin: " + Constantes.FORMATO_FECHA_YYYY_MM_DD.format(send.getModelo().getStrPerFinF()));
			
		}		
		if(send.getModelo().getRegistroPatronal() != null ){
			this.log.debug("registroPatronal: " + send.getModelo().getRegistroPatronal());
		}

		clemList = firmaClemBusiness.buscaClemConFirmaPaginado(send);
		clemList.setsEcho(send.getsEcho());
		
		session.setAttribute("clemList", clemList.getAaData());
		

		this.log.debug(" [Termino la invocacion..] solicitudes [" + clemList + "]");
		return clemList;
	}		
	
	@RequestMapping(value = "/firma/clem", method = RequestMethod.POST)
	public String firmarClem(Model model, HttpSession session) {
		String vista = "firmaClem";
		List<FirmaClemDTO> clemList = new ArrayList<FirmaClemDTO>();
		
		/* Obtenemos el objeto de usuario de la sesion */
		Usuario usuario = (Usuario) session.getAttribute(KEY_USUARIO);
		int iRol = usuario.getPerfilUsuario().getIdPerfilUsuario().intValue();
		//Se valida usuario
		if (!perfilUsuarioValido(iRol)) {
			log.error("::: El usuario "+usuario.getUsuario()+" no tiene un perfil valido para ejecutar esta accion, rol: " + iRol);
			session.setAttribute("internalError", "::: El usuario " + usuario.getUsuario()
							+ " no tiene un poerfil valido para ejecutar esta accion, rol: " + iRol);
			return "internalError";
		}

		if(session.getAttribute("clemList") != null){
			clemList = (List<FirmaClemDTO>) session.getAttribute("clemList");
			
			this.log.debug(":::::: Recorriendo solicitudes por firmar");
			
			for (Iterator<FirmaClemDTO> iterator = clemList.iterator(); iterator.hasNext();) {
				FirmaClemDTO firmaClemDTO = iterator.next();
				this.log.debug(firmaClemDTO.toString());
				this.log.debug("------------------------------------------------------------------------------");
			}
			
			this.log.debug(":::::: Termine de recorrer solicitudes");
		}

		if(clemList == null || clemList.size() == 0){
			this.log.debug(":::::: No se tienen solicitudes por firmar");
			model.addAttribute("mensaje", "No se tienen solicitudes por firmar");			
		}
		
		return vista;
	}
	
	/****
	 * Metodo que prepara los datos a firmar
	 * 
	 * @throws Exception
	 ****/
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/autenticacion/clem", method = RequestMethod.POST)
	public String firmarClem(@ModelAttribute("firmaClemDTO") FirmaClemDTO firmaClemDTO,
			HttpSession session, Model model,
			DefaultMessageContext messageContext) throws Exception {
		
		this.log.debug("[ModuloFirmaController] - " + "firmarClem " + "parametro recibido rfc: " + firmaClemDTO.getCveSolClems());
		this.log.debug("Solicitudes a firmar: " + firmaClemDTO.getSolicitudesFirma());
		session.setAttribute("menuDecoration", "1");
		session.setAttribute("grupoTramite", "0");
		
		Usuario usuario = (Usuario) session.getAttribute(KEY_USUARIO);
		int iRol = usuario.getPerfilUsuario().getIdPerfilUsuario().intValue();
		String tipoClemCheck = (String) session.getAttribute("tipo_clem");
		List<FirmaClemDTO> firmar = (List<FirmaClemDTO>) session.getAttribute("clemList");
		//Se valida usuario
		if (!perfilUsuarioValido(iRol)) {
			log.error("::: El usuario "+usuario.getUsuario()+" no tiene un perfil valido para ejecutar esta accion, rol: " + iRol);
			return "internalError";
		}				
		
		//Buscamos solo los tramites seleccionados
		List<FirmaClemDTO> clemSelect = new ArrayList<FirmaClemDTO>();
		String[] solFirma = firmaClemDTO.getSolicitudesFirma().split(",");
		for (int i = 0; i < solFirma.length; i++) {
			for (Iterator<FirmaClemDTO> iterator = firmar.iterator(); iterator.hasNext();) {
				FirmaClemDTO clemDTO = iterator.next();
				if(solFirma[i].equals(clemDTO.getIdSolicitud().toString())) {
					clemSelect.add(clemDTO);
				}				
			}		
		}
		log.debug("::: Clems encontradas en la busqueda "+firmar.size()+", Clems seleccionadas a firmar: " + clemSelect.size());
		session.setAttribute("clemList", clemSelect);
		
		List<ResolucionVO> requestFirma = firmaClemBusiness.buscarDatosFirmaMasivaCMS(clemSelect);
		System.out.println("Encontre " + requestFirma.size() + ", clem para firmar");
		log.debug("::Recorriendo Clems a firmar");
		for(ResolucionVO vo: requestFirma){			
			System.out.println("parametros a enviar a firmar :" + vo.getClemVO().toString());
			//Se valida contenido de campo de motivos en la Clem
			ReporteClemBean reporteClemBean = new ReporteClemBean();
			reporteClemBean.setMotivos(vo.getClemVO().getMotivos());
			reporteClemBean.setTitular(vo.getClemVO().getTitular());
			reporteClemBean.setPuesto(vo.getClemVO().getPuesto());
			reporteClemBean.setLugarFechaExpedicion(vo.getClemVO().getLugarFechaExpedicion());
			reporteClemBean.setSuplente(vo.getClemVO().getSuplente());			
			if(!datosClemBusiness.validaCaracteresPermitidosClem(reporteClemBean)) {
				log.debug(":: ERROR: Caracter especial invalido en la CLEM");
				messageContext.setMessageSource(messageSource);
		 		messageContext.addMessage(Utiles.construirMensaje(Boolean.TRUE, "label.error.caracter.clem"));
		 		model.addAttribute("messageContext", messageContext);
				return this.consultarSolicitudesSinFirma(model, session);
			}else {
				log.debug("::Caracteres en la Clem validos");
			}
		}
		
		for(ResolucionVO vo : requestFirma){
			System.out.println("parametro idddddd:P :" + vo.getIdAnalisis());
			String cadOriginal = "|Identificador_" + vo.getIdAnalisis() +
					"|NoFolio_" + vo.getClemVO().getFolioClem() + 
					"|Patron_" + vo.getClemVO().getRazonSocial() +  
					"|Registro patronal_" + vo.getClemVO().getRegPatronal() + 
					"|Delegacion_" + vo.getClemVO().getDelegacion() + 
					"|Subdelegacion_ " + vo.getClemVO().getSubdelegacion() + 
					"|Titular_" + vo.getClemVO().getTitular() + 
					"|" + Utiles.getDateHHMM() + "|RFC_" + firmaClemDTO.getCveSolClems() +
					"|CE_" + usuario.getUsuario();
					
			vo.setCadOriginal(cadOriginal);			
		}
		
		System.out.println("tipo de documento a enviar:  "  + tipoClemCheck );
		String tippoAcuse = Utiles.tipoAcuse(tipoClemCheck);
		System.out.println("tipo de documento a generar:  "  + tippoAcuse );
		session.setAttribute("parametrosFirma", requestFirma);
		session.setAttribute("tipoClemFirma", tippoAcuse);
		session.setAttribute("paramRFC", firmaClemDTO.getCveSolClems().trim());
		String vista = "autenticacionFirma";

		log.info("::::::::::::::::::: Seguimiento 1 /autenticacion/clem ::::::::::::::::::::::::::::::::");
		log.info(":::::::::: Usuario: " + usuario.getUsuario() + " ::: Rol: " + iRol);

		return vista;
	}
	
	@RequestMapping(value = "/procesarDatosFirma", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, ? extends Object> procesarDatosFirma(@RequestBody FirmaClemDTO firmaClemDTO,
			HttpServletResponse response, HttpSession session){
		this.log.debug("[ModuloFirmaController] - " + "procesarDatosFirma ");
		this.log.debug("Se almacenan los datos de la firma digital: ");		
		try {
			this.log.debug("Parametros recibidos");
			this.log.debug(firmaClemDTO.toString());
			datosClemBusiness.actualizaDatosFirmaClem(firmaClemDTO);
		} catch (DatosClemException e) {
			e.printStackTrace();
			this.log.debug("::: Ourrio un error al actualizar los datos de la firma en la clem: "
					+ firmaClemDTO.getCveAnalisis().toString());
		}		
		return null;		
    }		

	private boolean perfilUsuarioValido(int iRol){
		if (iRol == CodigoRolClasificacion.DELEGADO_DEL.getCodigo().intValue()
				|| iRol == CodigoRolClasificacion.SUBDELEGADO_SUBDEL.getCodigo().intValue()
				|| iRol == CodigoRolClasificacion.JEFE_OFICINA_COBROS_SUBDEL.getCodigo().intValue()) {
			return true;
		}
		return false;
	}
	
	private boolean perfilDelegacionClemSinFirma(int iRol){
		if (iRol == CodigoRolClasificacion.DELEGADO_DEL.getCodigo().intValue()) {
			return true;
		}
		return false;
	}
	
	private boolean perfilSubDelegacionClemSinFirma(int iRol){
		if (iRol == CodigoRolClasificacion.SUBDELEGADO_SUBDEL.getCodigo().intValue()
				|| iRol == CodigoRolClasificacion.JEFE_OFICINA_COBROS_SUBDEL.getCodigo().intValue()) {
			return true;
		}
		return false;
	}
	
	private boolean perfilDelegacionClemFirmada(int iRol){
		if (iRol == CodigoRolClasificacion.DELEGADO_DEL.getCodigo().intValue()
				|| iRol == CodigoRolClasificacion.JEFE_DEPTO_DEL.getCodigo().intValue()
				|| iRol == CodigoRolClasificacion.VENTANILLA_CLASIF_DEL.getCodigo().intValue()
				|| iRol == CodigoRolClasificacion.NORMATIVO_DEL.getCodigo().intValue()
				|| iRol == CodigoRolClasificacion.JEFE_OFICINA_DEL.getCodigo().intValue()) {
			return true;
		}
		return false;
	}
	
	private boolean perfilSubDelegacionClemFirmada(int iRol){
		if (iRol == CodigoRolClasificacion.JEFE_DEPTO_SUBDEL.getCodigo().intValue()
				|| iRol == CodigoRolClasificacion.JEFE_OFICINA_SUBDEL.getCodigo().intValue()
				|| iRol == CodigoRolClasificacion.VENTANILLA_CLASIF_SUBDEL.getCodigo().intValue()
				|| iRol == CodigoRolClasificacion.NORMATIVO_SUBDEL.getCodigo().intValue()
				|| iRol == CodigoRolClasificacion.SUBDELEGADO_SUBDEL.getCodigo().intValue()
				|| iRol == CodigoRolClasificacion.JEFE_OFICINA_COBROS_SUBDEL.getCodigo().intValue()) {
			return true;
		}
		return false;
	}
	
}