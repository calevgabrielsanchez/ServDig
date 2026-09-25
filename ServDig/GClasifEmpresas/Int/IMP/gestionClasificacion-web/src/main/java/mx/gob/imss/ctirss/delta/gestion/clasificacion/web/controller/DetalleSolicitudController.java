/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Eduardo Gonzales Alvarez
 *  @Proyecto: delta
 *  @Archivo:DetalleSolicitudController.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.web.controller
 *  @Fecha:15/05/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.web.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import mx.gob.imss.ctirss.delta.exception.clasificacion.AnalisisNoEncontradoException;
import mx.gob.imss.ctirss.delta.exception.clasificacion.ClasificacionException;
import mx.gob.imss.ctirss.delta.framework.base.controller.AbstractController;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.ClasificacionDTO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.model.dto.DictamenDTO;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.DictamenServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.AnalisisServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.analisis.TipoCausaAnalisisServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.clasificacion.ClasificacionServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.solicitud.SolicitudServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.tramite.TramiteServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.usuario.UsuarioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.Usuario;
import mx.gob.imss.ctirss.delta.model.clasificacion.AdjuntosClasificacion;
import mx.gob.imss.ctirss.delta.model.clasificacion.AnalisisClasificacionEmpresas;
import mx.gob.imss.ctirss.delta.model.clasificacion.ConfiguracionCe;
import mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisModel;
import mx.gob.imss.ctirss.delta.model.clasificacion.TipoCancelacionEnum;
import mx.gob.imss.ctirss.delta.model.clasificacion.TipoCausaAnalisis;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;

@Controller
@RequestMapping(value="/solicitud")
public class DetalleSolicitudController extends AbstractController{
	
	@Autowired
	SolicitudServiceBusinessRemote solicitudBusiness;
	
	@Autowired
	AnalisisServiceBusinessRemote analisisBusiness;
	
	@Autowired
	UsuarioServiceBusinessRemote usuarioBusiness;
	
	@Autowired
	TramiteServiceBusinessRemote tramiteBusiness;
	
	@Autowired
	ClasificacionServiceBusinessRemote clasificacionBusiness;
	
	@Autowired
	TipoCausaAnalisisServiceBusinessRemote tipoCausaBusiness;
	
	@Autowired
	DictamenServiceBusinessRemote dictamenServiceBusines;
	
	@Autowired
	private ClasificacionServiceBusinessRemote clasificacionService;
	
	@Autowired
	DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusinessRemote;
	
	@RequestMapping(value = "/{idSolicitud}/{regPatronal}/{tipoPersona}/detalle")
	public String detalleSolicitud(@PathVariable String idSolicitud,
			@PathVariable String regPatronal, @PathVariable String tipoPersona,
			HttpSession session, Model model) {

		return this.detalle(idSolicitud, regPatronal, tipoPersona, session, model, null, null, null);
	}
	
	@RequestMapping(value = "verDetalle")
	public String verDetalle(@ModelAttribute("clasificacionDTO") ClasificacionDTO clasificacionDTO,
			HttpSession session, Model model) {
		log.debug("Entre a buscar detalle - [DetalleSolicitudController.verDetalle] - ");
		log.debug(clasificacionDTO.toString());
		return this.detalle(clasificacionDTO.getCveIdSolicitud(), clasificacionDTO.getRegPatronal(),
				clasificacionDTO.getTipoPersona(), session, model, null, null,null);
	}	
	
	@RequestMapping(value = "/detalle")
	public String detalleSolicitudDictamen(@ModelAttribute("dictamen") DictamenDTO dictamen,
			HttpSession session, Model model) throws Exception {
		
		log.debug("DICTAMEN DTO = "+ dictamen.toString());
		Long idSolicitud = null;
		String tipoPersona = null;
		SujetoObligado consulta = new SujetoObligado();
		AnalisisClasificacionEmpresas analisis = new AnalisisClasificacionEmpresas();

		try {	
			String rfc = dictamen.getRfc();
			tipoPersona = rfc != null ? (rfc.length() == 13 ? "1" : "2") : "1";
			idSolicitud = dictamen.getIdSolicitud();
			//Se recupera el usuario logeado
		 	Usuario usuario = (Usuario)session.getAttribute(KEY_USUARIO);
		 	dictamen.setUsuario(usuario.getUsuario());
			if(idSolicitud == null) {
				consulta.setNumeroRegistroPatronal(dictamen.getRegistroPatronal());
				consulta.setTipoPersonaFiscal(tipoPersona.equals("2") ? TipoPersonaFiscal.MORAL : TipoPersonaFiscal.FISICA);			
//				consulta = solicitudBusiness.obtenerDetalleSolicitud(consulta);
				consulta = solicitudBusiness.obtenerDetalleSolicitudDictamen(consulta);
				log.debug("Este es el resultado de consulta" + consulta.toString());
				idSolicitud = solicitudBusiness.crearVistaDictamen(consulta, dictamen);
				
				dictamen.setIdSolicitud(idSolicitud);
			}
		} catch (Exception e) {
			log.debug("ERROR - [detalleSolicitudDictamen] - " + e.getMessage());
			 if(idSolicitud == null) {
				 idSolicitud = solicitudBusiness.crearVistaDictamen(null, dictamen);
			}
			analisis = analisisBusiness.obtenerIdAnalisis(new BigDecimal(idSolicitud));
			solicitudBusiness.actualizaEstatusInconsistencia(analisis.getCveIdAnalisis());
			e.printStackTrace();
			session.setAttribute("errorInconsistencia",
					"Informaci\u00F3n inconsistente, revisi\u00F3n normativa con m\u00F3dulo de dictamen electr\u00F3nico");
			return "errorInconsistencia";			
		}

		return this.detalle(""+idSolicitud, dictamen.getRegistroPatronal(), tipoPersona, session, model,dictamen.getCveIdPatronDictamen(), consulta, dictamen);
	}
	
	public String detalle(String idSolicitud, String regPatronal, String tipoPersona,
			HttpSession session, Model model,Long idPatronDictamen, SujetoObligado sujetoObligado , DictamenDTO dictamen ) {

		AnalisisClasificacionEmpresas analisis = new AnalisisClasificacionEmpresas();
		EstatusAnalisisModel estatusAnalisisModel = new EstatusAnalisisModel();
		ConfiguracionCe configuracionCe = new ConfiguracionCe();
		boolean bReclasificado = false;
		TipoCausaAnalisis defaultTipoCausa = new TipoCausaAnalisis();
		List<TipoCausaAnalisis> lstHistTipoCausa = new ArrayList<TipoCausaAnalisis>();
		String tipoTramite = "1";//MODIFICACION		 	
	 	String mensaje = null; 
	 	
		try {
			log.debug("********** BUSCANDO DATOS DE LA SOLICITUD " + idSolicitud 
					 + " CON REGISTRO PATRONAL " + regPatronal + " Y TIPO DE PERSONA " + tipoPersona + "***************");

			//Se recupera el usuario logeado
		 	Usuario usuario = (Usuario)session.getAttribute(KEY_USUARIO);
		 	Integer rol = usuario.getPerfilUsuario().getIdPerfilUsuario().intValue();
		 	
		 	if(sujetoObligado == null || sujetoObligado.getCveIdSujetoObligado() == null) {
		 		sujetoObligado = new SujetoObligado();
				sujetoObligado.setNumeroRegistroPatronal(regPatronal);
				sujetoObligado.setTipoPersonaFiscal(tipoPersona.equals("2") ? TipoPersonaFiscal.MORAL : TipoPersonaFiscal.FISICA);			
				
				if(idPatronDictamen!=null) {
					sujetoObligado = solicitudBusiness.obtenerDetalleSolicitudDictamen(sujetoObligado);
				} else {
					sujetoObligado = solicitudBusiness.obtenerDetalleSolicitud(sujetoObligado);
				}
				
				if(sujetoObligado == null || sujetoObligado.getCveIdSujetoObligado() == null){
					throw new ClasificacionException("El ID de Sujeto Obligado es NULL", 555);
				}
		 	}
		 		 	
			analisis = analisisBusiness.obtenerDetalleAnalisis(new BigDecimal(idSolicitud));
			if (analisis == null) {
				if (idPatronDictamen!=null) {
					Long solicitudId = null;
					solicitudId = solicitudBusiness.crearVistaDictamen(sujetoObligado, dictamen);
					analisis = analisisBusiness.obtenerDetalleAnalisis(new BigDecimal(solicitudId));
				}
				
			}
			
			log.debug("::: Situacion del patron en baja: " + sujetoObligado.getDescSituacionBaja());
		 	if(sujetoObligado.getDescSituacionBaja() != null && sujetoObligado.getDescSituacionBaja().trim().equals("BAJA")) {
		 		analisis.setCveIdSubdelegacion(sujetoObligado.getSubdelegacion().getId());
		 		analisis.setClaveUsuarioAsignado(usuario.getCveIdUsuario());
		 		analisisBusiness.cancelaAnalisisPorBajaNRP(analisis);
		 		throw new ClasificacionException("El registro patronal no puede ser revisado se encuentra en estado de baja", 555);
		 	}	
			
			Tramite tramite = analisis.getTramite();
		 	bReclasificado = solicitudBusiness.consultaReintentoRPC(new Long(idSolicitud)); //Esta linea es para obtener si es reclasificaci�n o no
			estatusAnalisisModel = clasificacionBusiness.buscaClasificacionInicial(analisis.getCveIdAnalisis());//Para obtener las clasificaciones actual y anterior originales (foto).
			SujetoObligado sujetoObligadoAnterior = null;
			String regPatronAnterior ="";
			if(tramite.getTipoTramite().getIdTipoTramite() == TipoTramiteEnum.CAMBIO_DE_DOMICILIO_DIFERENTE_MUNICIPIO.getCodigo().intValue()) {
				log.debug("::: Tramite de aviso CD diferente municipio");
				SujetoObligado sujeto = analisis.getTramite().getSujetoObligado();
				List<SujetoObligado> sujetos = sujeto.getSujetosObligados();
				for (Iterator<SujetoObligado> iterator = sujetos.iterator(); iterator.hasNext();) {
					sujetoObligadoAnterior = iterator.next();
					log.debug("::: SO con domicilio anterior: " + sujetoObligadoAnterior.getNumeroRegistroPatronal());
					log.debug("::: Esatus de la baja" + sujetoObligadoAnterior.getClasificacion().getIndBaja());
					log.debug("::: Domicilio anterior:");
					log.debug(sujetoObligadoAnterior.getCntroTrabajo());
					regPatronAnterior = sujetoObligadoAnterior.getNumeroRegistroPatronal()+sujetoObligadoAnterior.getModalidad().getNumModalidad()+sujetoObligadoAnterior.getDigVerificador();
				}				
			}									
			
			model.addAttribute("soAnterior", sujetoObligadoAnterior);
			model.addAttribute("regPatronAnterior", regPatronAnterior);
			
			if(tramite.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.ALTA_SRT.getCodigo().intValue()
					|| tramite.getTipoTramite().getIdTipoTramite().intValue() == TipoTramiteEnum.ALTA_SRT_PM.getCodigo().intValue())
				tipoTramite = "0";//INSCRIPCION
			
		 	// valida el tipo de usuario en sesión con el estatus actual 
		 	// del análisis para saber que botones mostrar 
		 	mensaje = analisis.getDescEstatus();
		 	
		 	if(idPatronDictamen != null) {
		 		mensaje = mensaje.contains("RECTIFICADO") ? mensaje.replaceAll("RECTIFICADO", "ENVIAR A REVISION") : mensaje;
		 		Clasificacion clasificacionDictamen = dictamenServiceBusines.getClasificacionDictamen(idPatronDictamen, regPatronal);
		 		
		 		if(clasificacionDictamen != null) {		 			
		 			//se obtiene la descripcion de la fraccion encontrada
		 			estatusAnalisisModel.setPrimaDec(clasificacionDictamen.getPrimaSRTActual());
		 			Clasificacion clasEq = clasificacionService.consultarFracEqPorNumero(clasificacionDictamen);
		 			//estatusAnalisisModel.setFraccionActual(clasificacionDictamen.getFraccion());		 		
		 			estatusAnalisisModel.setFraccionActual(clasEq.getFraccion());
		 		} else {
		 			estatusAnalisisModel.setFraccionActual(null);
		 			estatusAnalisisModel.setPrimaDec(null);
		 		}		 		
		 	}
		 	
			//Para obtener la relacion entre los roles y sus operaciones.
			configuracionCe = solicitudBusiness.obtenerConfiguracionCe(analisis.getCveIdAnalisis(), 
													     ((Usuario)session.getAttribute(KEY_USUARIO)).getCveIdUsuario(), 
													     rol);
			//Setea el tipo de Proceso, si es Inscripción o Modificación
			lstHistTipoCausa = tipoCausaBusiness.consultaHistoricoTipoCausa(analisis.getCveIdAnalisis(), tramite);
			
			//Para obtener el tipo de causa default de acuerdo al tipo de tramite.
			//defaultTipoCausa = tipoCausaBusiness.consultaTipoCausa(new Long(tramite.getTipoTramite().getIdTipoTramite()));
			defaultTipoCausa = lstHistTipoCausa.get(0);
			
			//busca documentos adjuntos
			try {
				log.debug("::: Consultando lista de archivos para la solicitud: " + idSolicitud);
				// buscamos los archivos que se han adjuntado en caso de retomar el tramite
				List<AdjuntosClasificacion> adjList = analisisBusiness.consultarArchivoAdjunto(idSolicitud);
				List<AdjuntosClasificacion> adjListAux = new ArrayList<AdjuntosClasificacion>();
				if(adjList != null && adjList.size() > 0){
					log.debug("::: Se encontraron "+adjList.size()+" archivos adjuntos al folio " + idSolicitud);	
					for (Iterator<AdjuntosClasificacion> iterator = adjList.iterator(); iterator.hasNext();) {
						AdjuntosClasificacion adjuntosClasificacion = iterator.next();
						adjuntosClasificacion.setRutaArchivo(adjuntosClasificacion.getRutaArchivo()+adjuntosClasificacion.getNombreArchivo());
						adjListAux.add(adjuntosClasificacion);
					}
					
					model.addAttribute("adjList", adjListAux);
					model.addAttribute("existAdj", true);
				}else{
					log.debug("::: NO se encontraron archivos adjuntos a la solicitud " + idSolicitud);
					model.addAttribute("existAdj", false);
				}
			} catch (Exception e) {
				model.addAttribute("existAdj", false);
				log.error("::: Ocurrio un error al consultar archivos adjuntos: " + e.getMessage());
				e.printStackTrace();
			}				

			//variables para regresar al detalle
		 	session.setAttribute("idSolicitud", idSolicitud);
		 	session.setAttribute("regPatronal", regPatronal);
		 	if(sujetoObligado != null && sujetoObligado.getTipoPersonaFiscal() != null) {
		 		session.setAttribute("tipoPersona", sujetoObligado.getTipoPersonaFiscal().getCodigo().toString());
		 	} else {
		 		session.setAttribute("tipoPersona", tipoPersona);
		 	}
			
			
			
			//sube a sesion el sujeto obligado para no volver a obtener
			session.setAttribute("sujetoObligado", sujetoObligado);
			
			session.setAttribute("indModAut", analisis.getIndModAut());
			session.setAttribute("cveUsuarioAsignado", usuario.getCveIdUsuario());
			
		 	if(tramite.getTipoTramite().getIdTipoTramite() == TipoTramiteEnum.COMPRA_DE_ACTIVOS.getCodigo().intValue() //Compra de Activos
		 			|| tramite.getTipoTramite().getIdTipoTramite() == TipoTramiteEnum.COMODATO.getCodigo().intValue() //Comodato
		 			|| tramite.getTipoTramite().getIdTipoTramite() == TipoTramiteEnum.ENAJENACION.getCodigo().intValue() //Enajenacion
		 			|| tramite.getTipoTramite().getIdTipoTramite() == TipoTramiteEnum.ARRENDAMIENTO.getCodigo().intValue() //Arrendamiento
		 			|| tramite.getTipoTramite().getIdTipoTramite() == TipoTramiteEnum.FIDEICOMISO_TRASLATIVO.getCodigo().intValue() /*Fideicomiso Traslativo*/){
		 		model.addAttribute("muestraBienes", 1);
		 	}else{
		 		model.addAttribute("muestraBienes", 0);
		 	}
		 	
		 	
		 	model.addAttribute("analizandoDictamen", idPatronDictamen != null);
		 	model.addAttribute("cveIdPatronDictamen", idPatronDictamen);
			model.addAttribute("tipoTramite", tramite);
			model.addAttribute("tTramite", tipoTramite);
		 	model.addAttribute("sujetoObligado", sujetoObligado);
		 	model.addAttribute("idSolicitud", idSolicitud);
		 	model.addAttribute("regPatronal", regPatronal);
		 	
		 	List<Long> tiposTramite = new ArrayList<Long>();
		 	List<Map<String, Object>> documentos = null;
			Long documentosRequeridos = 0L;
			
			tiposTramite.add((tramite.getTipoTramite().getIdTipoTramite().longValue()));
			
			log.debug("El tipo de tramite es "+ tiposTramite);
			
						
			documentos = documentoProbatorioServiceBusinessRemote.getDocumentosClasificadosPorTipoTramite(tiposTramite);
			documentosRequeridos = documentos != null ? documentos.size() : 0L;
			model.addAttribute("documentos", documentos);
			model.addAttribute("documentosRequeridos", documentosRequeridos);
		 	
		 	if(sujetoObligado.getTipoPersonaFiscal() == TipoPersonaFiscal.FISICA){
		 		model.addAttribute("persona", sujetoObligado.getFisica());
		 		log.debug("Modelo de persona Fisica= "+ sujetoObligado.getFisica());
		 		session.setAttribute("rfc", sujetoObligado.getFisica().getRfc());
		 	}else{
		 		model.addAttribute("persona", sujetoObligado.getMoral());
		 		log.debug("Modelo de persona Moral= "+ sujetoObligado.getMoral());
		 		session.setAttribute("rfc", sujetoObligado.getMoral().getRfc());
		 	}
		 	model.addAttribute("personaFisica", TipoPersonaFiscal.FISICA.getCodigo());
		 	model.addAttribute("personaMoral", TipoPersonaFiscal.MORAL.getCodigo());
			model.addAttribute("analisis", analisis);
			
			if(sujetoObligado != null && sujetoObligado.getTipoPersonaFiscal() != null){
				model.addAttribute("tipoPersona", sujetoObligado.getTipoPersonaFiscal().getCodigo().toString());
			} else {
				model.addAttribute("tipoPersona", tipoPersona);
			}
			
		 	model.addAttribute("mensaje", mensaje);
		 	model.addAttribute("estatusAnalisisModel", estatusAnalisisModel);
		 	model.addAttribute("configuracionCE", configuracionCe);
		 	if(analisis.getCveIdEstatus() == null)
		 		model.addAttribute("bCancelado", false);
		 	else
		 		model.addAttribute("bCancelado", esAnalisisCancelado(analisis.getCveIdEstatus().intValue()));
		 	model.addAttribute("bReclasificado", bReclasificado);
		 	model.addAttribute("defaultTipoCausa", defaultTipoCausa);
		 	
		 	lstHistTipoCausa.remove(0);
		 	model.addAttribute("lstHistTipoCausa", lstHistTipoCausa);
		 	model.addAttribute("secuenciaDeNotaria", analisis.getSolicitud().getSecuenciaDeNotaria());
		 	
		 					 	
			if (dictamen != null ) {
				model.addAttribute("IdEjercicio", dictamen.getIdEjercicio());
		 		model.addAttribute("ejercicio", dictamenServiceBusines.getEjercicioFiscal(dictamen.getIdEjercicio()));
		 	}	
			log.debug("***************************************************************************************************************************************");
			log.debug("***************************************************************************************************************************************");
			log.debug("***************************************************************************************************************************************");
			
			log.debug("Parametros enviados : getCveIdAnalisis: " +  analisis.getCveIdAnalisis() + ", getCveIdUsuario: " + ((Usuario)session.getAttribute(KEY_USUARIO)).getCveIdUsuario()
					+ ", rol: " + usuario.getPerfilUsuario().getIdPerfilUsuario().intValue());
			
			log.debug("isBoRatificar: " + configuracionCe.isBoRatificar());
			log.debug("isBoRectificar: " + configuracionCe.isBoRectificar());
			log.debug("isBoAutoRatificarN1: " + configuracionCe.isBoAutoRatificarN1());
			log.debug("isBoAutoRectificarN1: " + configuracionCe.isBoAutoRectificarN1());
			log.debug("isBoRechRatificarN1: " + configuracionCe.isBoRechRatificarN1());
			log.debug("isBoRechRectificarN1: " + configuracionCe.isBoRechRectificarN1());
			log.debug("isBoVerClem: " + configuracionCe.isBoVerClem());
			log.debug("isBoModificarClem: " + configuracionCe.isBoModificarClem());
			log.debug("isBoModificarAuto: " + configuracionCe.isBoModificarAuto());
			log.debug("isBoIndFirma: " + configuracionCe.isBoIndFirma());
			log.debug("urlClemFirma: " + configuracionCe.getUrlClemFirma());
			log.debug("***************************************************************************************************************************************");
			
		} catch (ClasificacionException ce) {
			log.debug("ERROR - [DetalleSolicitudController] - " + ce.getMessage());
			ce.printStackTrace();
			session.setAttribute("internalError", "Se origin\u00F3 un problema al obtener el detalle, "
							+ ce.getMessage() + " solicitud " + idSolicitud);
			return "internalError";
			
		} catch (AnalisisNoEncontradoException ane) {
			log.debug("ERROR - [DetalleSolicitudController] - " + ane.getMessage());
			ane.printStackTrace();
			session.setAttribute("internalError", "Se origin\u00F3 un problema al obtener el detalle, "
							+ ane.getMessage() + " solicitud " + idSolicitud);
			return "internalError";
		} catch (Exception e) {
			log.debug("ERROR - [DetalleSolicitudController] - " + e.getMessage());
			e.printStackTrace();
			session.setAttribute("internalError", "Se origin\u00F3 un problema al obtener el detalle, "
							+ e.getMessage() + " solicitud " + idSolicitud);
			return "internalError";
		}
				
		//Dictamen
		if(idPatronDictamen!=null) {
			//log.debug("Este es el modelo que se genera:" + model);
			return "detalleSolicitudDictamen";
		}	
		
		//Vista MAC II
		return "detalleSolicitud";
	}

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public @ResponseBody void download(HttpServletResponse sresponse) {
		byte[] response = null;
		try {
			ByteArrayOutputStream bos = null;
			InputStream file = null;
			file = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("documentos/Ofi_Desecha_tramite_Generico.docx");
			// Lee el documento
			bos = new ByteArrayOutputStream();
			int c;
			// Copia el documento en bytes
			while ((c = file.read()) != -1)
				bos.write(c);

			response = bos.toByteArray();

			if (response != null) {
				log.debug("Se obtuvo el archivo doc Ofi_Desecha_tramite_Generico.docx");
				sresponse.setContentType("application/vnd.ms-word");
				sresponse.addHeader("content-disposition", "attachment; filename=Ofi_Desecha_tramite_Generico.docx");
				sresponse.addHeader("X-Download-Options", "open");
				try {
					log.debug("bytes pdf:" + response.length);
					sresponse.setContentLength(response.length);
					sresponse.getOutputStream().write(response);
					sresponse.getOutputStream().flush();
					sresponse.getOutputStream().close();
				} catch (IOException e) {
					log.error("Error en el metodo init previo : " + e);
					e.printStackTrace();
				}
			} else {
				log.debug("No se obtuvo el archivo Ofi_Desecha_tramite_Generico.docx");
			}
		} catch (Exception e) {
			log.error("Error al obtener el archivo doc.");
			e.printStackTrace();
		}

	}
	
	
	@RequestMapping(value = "/downloadAvisos", method = RequestMethod.GET)
	public @ResponseBody void downloadAvisos(HttpServletResponse sresponse) {
		byte[] response = null;
		try {
			ByteArrayOutputStream bos = null;
			InputStream file = null;
			file = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("documentos/Avisos.pdf");
			// Lee el documento
			bos = new ByteArrayOutputStream();
			int c;
			// Copia el documento en bytes
			while ((c = file.read()) != -1)
				bos.write(c);

			response = bos.toByteArray();

			if (response != null) {
				log.debug("Se obtuvo el archivo doc Avisos.pdf");
				sresponse.setContentType("application/pdf");
				sresponse.addHeader("content-disposition", "attachment; filename=Avisos.pdf");
				sresponse.addHeader("X-Download-Options", "open");
				try {
					log.debug("bytes pdf:" + response.length);
					sresponse.setContentLength(response.length);
					sresponse.getOutputStream().write(response);
					sresponse.getOutputStream().flush();
					sresponse.getOutputStream().close();
				} catch (IOException e) {
					log.error("Error en el metodo init previo : " + e);
					e.printStackTrace();
				}
			} else {
				log.debug("No se obtuvo el archivo Avisos.pdf");
			}
		} catch (Exception e) {
			log.error("Error al obtener el archivo doc-Avisos.");
			e.printStackTrace();
		}

	}
	
	/**
	 * Determina si el estado del análisis es cancelado
	 * 
	 * @param estadoAnalisis
	 * @return respuesta
	 */
	private boolean esAnalisisCancelado(final int estadoAnalisis) {
		boolean respuesta = Boolean.FALSE;
		final TipoCancelacionEnum[] tipoCancelacionEnums = TipoCancelacionEnum
				.values();
		for (final TipoCancelacionEnum tipoCancelacionEnum : tipoCancelacionEnums) {
			if (estadoAnalisis == tipoCancelacionEnum.getClave()) {
				respuesta = Boolean.TRUE;
				break;
			}
		}
		return respuesta;
	}
}