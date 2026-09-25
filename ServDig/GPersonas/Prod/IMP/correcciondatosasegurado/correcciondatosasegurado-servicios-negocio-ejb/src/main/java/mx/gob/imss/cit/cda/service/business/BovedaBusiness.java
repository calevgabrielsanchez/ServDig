package mx.gob.imss.cit.cda.service.business;

import java.net.SocketTimeoutException;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.cit.cda.service.interfaces.BovedaRemote;
import mx.gob.imss.cit.cda.service.utility.BovedaUtilityLocal;
import mx.gob.imss.cit.clienteServiciosComunes.model.CreateDocumentReq;
import mx.gob.imss.cit.clienteServiciosComunes.model.CreateDocumentRes;
import mx.gob.imss.cit.clienteServiciosComunes.model.DeleteDocumentReq;
import mx.gob.imss.cit.clienteServiciosComunes.model.DeleteDocumentRes;
import mx.gob.imss.cit.clienteServiciosComunes.model.DocumentReq;
import mx.gob.imss.cit.clienteServiciosComunes.model.DocumentRes;
import mx.gob.imss.cit.clienteServiciosComunes.model.Documento;
import mx.gob.imss.cit.clienteServiciosComunes.model.Tramite;
import mx.gob.imss.cit.clienteServiciosComunes.model.Usuario;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.BovedaCDAException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoCDAEnum;
import mx.gob.imss.ctirss.delta.model.enums.MensajesBovedaCDAEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(name = "bovedaBusiness", mappedName = "bovedaBusiness")
public class BovedaBusiness extends AbstractServiceUtility implements BovedaRemote{

	@EJB
	private BovedaUtilityLocal bovedaUtility;
	
	private static final String PDF_EXTENSION = "pdf";
	private static final String PDF_MIME_TYPE = "application/pdf";
	private static final String TIPO_ID_USR = "IDPERSONA";
	private static final String SEPARADOR_NOMBRE = "_";
	private final Logger log = LoggerFactory.getLogger(BovedaBusiness.class);
	
	@Override
	public String subirDocumento(byte[] documentoCertificado,
			Solicitud solicitud, TipoDocumentoCDAEnum tipoDocumentoCDAEnum) throws BovedaCDAException {
		if (documentoCertificado != null) {
			Tramite tramite = new Tramite();
			log.debug("---CDA--- Folio Tramite {}",
						solicitud.getNoFolioSolicitud());
			tramite.setFolioTramite(solicitud.getNoFolioSolicitud());

			
			Documento documento = new Documento();
			documento.setNombreArchivo(crearNombreArchivo(tipoDocumentoCDAEnum.getPrefijo()+SEPARADOR_NOMBRE+solicitud.getNoFolioSolicitud()
					+ "."+ PDF_EXTENSION));
			documento.setExtencion(PDF_EXTENSION);
			documento.setMimeType(PDF_MIME_TYPE);
			documento.setArchivo(documentoCertificado);
			Usuario usuario = new Usuario();

			usuario.setIdUsr(solicitud.getSolicitudId().toString());
			usuario.setOwner(false);
			usuario.setTipoIdUsr(TIPO_ID_USR);

			CreateDocumentReq createDocumentReq = new CreateDocumentReq();

			createDocumentReq.setDocumento(documento);
			createDocumentReq.setTramite(tramite);
			createDocumentReq.setUsuario(usuario);

			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("DOCUMENTO:[Nombre Archivo: ");
			sBuilder.append(documento.getNombreArchivo());
			sBuilder.append("extension: ");
			sBuilder.append(documento.getExtencion());
			sBuilder.append("MimeType: ");
			sBuilder.append(documento.getMimeType());
			sBuilder.append("bytes: ");
			sBuilder.append(documento.getArchivo() != null);
			sBuilder.append("]");
			sBuilder.append("TRAMITE:[Folio Tramite: ");
			sBuilder.append(tramite.getFolioTramite());
			sBuilder.append("]");
			sBuilder.append("USUARIO:[IdUsr: ");
			sBuilder.append(usuario.getIdUsr());
			sBuilder.append("Owner: ");
			sBuilder.append(usuario.isOwner());
			sBuilder.append("TipoIdUsr: ");
			sBuilder.append(usuario.getTipoIdUsr());
			sBuilder.append("]");
			log.debug("---CDA--- Parametros upload documento folio:{} {}",
					solicitud.getNoFolioSolicitud(), sBuilder.toString());
			
			return enviarDocumentoBoveda(createDocumentReq);
			
		}
		return null;
		
	}
	
	public String subirDocumento(byte[] archivo, Solicitud solicitud, String nombreDoc, String extension , String mimeType) throws BovedaCDAException{
		if(archivo != null){
			Documento documento = new Documento();
			documento.setNombreArchivo(crearNombreArchivo(nombreDoc));
			documento.setExtencion(extension);
			documento.setMimeType(mimeType);
			documento.setArchivo(archivo);
			
			Tramite tramite = new Tramite();
			tramite.setFolioTramite(solicitud.getNoFolioSolicitud());
			
			Usuario usuario = new Usuario();
			usuario.setIdUsr(solicitud.getSolicitudId().toString());
			usuario.setTipoIdUsr("89");
			
			CreateDocumentReq createDocumentReq = new CreateDocumentReq();
			createDocumentReq.setDocumento(documento);
			createDocumentReq.setTramite(tramite);
			createDocumentReq.setUsuario(usuario);
			
			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("DOCUMENTO:[Nombre Archivo: ");
			sBuilder.append(documento.getNombreArchivo());
			sBuilder.append("extension: ");
			sBuilder.append(documento.getExtencion());
			sBuilder.append("MimeType: ");
			sBuilder.append(documento.getMimeType());
			sBuilder.append("bytes: ");
			sBuilder.append(documento.getArchivo() != null);
			sBuilder.append("]");
			sBuilder.append("TRAMITE:[Folio Tramite: ");
			sBuilder.append(tramite.getFolioTramite());
			sBuilder.append("]");
			sBuilder.append("USUARIO:[IdUsr: ");
			sBuilder.append(usuario.getIdUsr());
			sBuilder.append("]");
			log.debug("---CDA--- Parametros upload documento folio:{} {}",
					solicitud.getNoFolioSolicitud(), sBuilder.toString());
			
			return enviarDocumentoBoveda(createDocumentReq);
		}
		return null;
	}
	
	@Override
	public byte[] recuperarDocumento(Solicitud solicitud,
			TipoDocumentoCDAEnum tipoDocumentoCDAEnum, String objectId) throws BovedaCDAException {
		log.debug("init obtener documento");
		byte[] documento = null;
		mx.gob.imss.cit.clienteServiciosComunes.model.Documento doc = new mx.gob.imss.cit.clienteServiciosComunes.model.Documento();
		doc.setNombreArchivo(tipoDocumentoCDAEnum.getPrefijo()+SEPARADOR_NOMBRE+solicitud.getNoFolioSolicitud()
				+ "."+ PDF_EXTENSION);
		doc.setExtencion(PDF_EXTENSION);
		log.debug("IdDocumento {}",objectId);
		doc.setIdDocumento(objectId);
		log.debug("Nombre {}", doc.getNombreArchivo());
		log.debug("Extencion {}", doc.getExtencion());
		DocumentReq documentReq = new DocumentReq();
		Tramite tramite = new Tramite();
		tramite.setFolioTramite(solicitud.getNoFolioSolicitud());
		log.debug("Folio Tramite {}", tramite.getFolioTramite());
		Usuario usuario = new Usuario();
		usuario.setIdUsr(solicitud.getSolicitudId().toString());
		log.debug("Usuario {}", usuario.getIdUsr());
		usuario.setOwner(false);
		usuario.setTipoIdUsr(TIPO_ID_USR);
		documentReq.setDocumento(doc);
		documentReq.setTramite(tramite);		
		
		documentReq.setUsuario(usuario);
		long tiempoInicio = 0 ,tiempoTermino = 0 ;
		try{
			log.info("---CDA--- Iniciando consulta {}", new Date());
		tiempoInicio = System.nanoTime();
		DocumentRes documentRes = bovedaUtility
				.getDocumentV2(documentReq);
		tiempoTermino = System.nanoTime();
		log.info("---CDA--- Finalizando consulta {}", new Date());
		log.info("---CDA--- La Consulta se tardo  "+ ( tiempoTermino - tiempoInicio ) +" nanoSegundos");
		if (documentRes.getRespuestaBoveda().isExito()) {
			log.info("---CDA--- Nombre del documento {}",documentRes.getDocumento().getNombreArchivo());
			log.info("---CDA--- Tamanio del archivo {}",documentRes.getDocumento().getArchivo().length);
			documento = documentRes.getDocumento().getArchivo();
		} else {
			
			String error = MensajesBovedaCDAEnum.obtenerMensajeErrorPorCodigo(documentRes.getRespuestaBoveda().getCodigoError());
			log.debug("Ocurrio un error al adjuntar el documento {}",error);
			
			StringBuilder sb = new StringBuilder("Error al Descargar el Archivo con ObjectID ");
			sb.append(objectId).append(" con el siguiente error ").append(error);
			
			throw new BovedaCDAException(sb.toString(), error);
		}
		}catch(Exception e){
			tiempoTermino = System.nanoTime();
			log.info("---CDA--- La Consulta se tardo  "+ ( tiempoTermino - tiempoInicio ) +" nanoSegundos");
			log.info("---CDA---Ocurrio un error Inesperado {}",e);
			manejoErroresBoveda(e,objectId,tiempoTermino-tiempoInicio);
		}
	
		return documento;
	}
	
	@Override
	public byte[] recuperarDocumento(Solicitud solicitud, String idDocBoveda, String nombreDoc) throws BovedaCDAException {
		log.debug("init obtener documento");
		byte[] documento = null;
		mx.gob.imss.cit.clienteServiciosComunes.model.Documento doc = new mx.gob.imss.cit.clienteServiciosComunes.model.Documento();
		doc.setNombreArchivo(nombreDoc);
		log.debug("Nombre {}", doc.getNombreArchivo());
		log.debug("Extencion {}", doc.getExtencion());
		DocumentReq documentReq = new DocumentReq();
		Tramite tramite = new Tramite();
		tramite.setFolioTramite(solicitud.getNoFolioSolicitud());
		log.debug("Folio Tramite {}", tramite.getFolioTramite());
		Usuario usuario = new Usuario();
		usuario.setIdUsr(solicitud.getSolicitudId().toString());
		log.debug("Usuario {}", usuario.getIdUsr());
		doc.setIdDocumento(idDocBoveda);
		documentReq.setDocumento(doc);
		documentReq.setTramite(tramite);
		documentReq.setUsuario(usuario);
		long tiempoInicio = 0 ,tiempoTermino = 0 ;
		try{			
			log.info("---CDA--- Iniciando consulta {}", new Date());
			tiempoInicio = System.nanoTime();			
		DocumentRes documentRes = bovedaUtility
				.getDocumentV2(documentReq);
		
		log.info("---CDA--- Finalizando consulta {}", new Date());
		tiempoTermino = System.nanoTime();
		log.info("---CDA--- La Consulta se tardo  "+ ( tiempoTermino - tiempoInicio ) +" nanoSegundos");
		if (documentRes.getRespuestaBoveda().isExito()) {
			documento = documentRes.getDocumento().getArchivo();
			log.info("---CDA--- Nombre del documento {}",documentRes.getDocumento().getNombreArchivo());
			log.info("---CDA--- Tamanio del archivo {}",documentRes.getDocumento().getArchivo().length);
		} else {
			String error = MensajesBovedaCDAEnum.obtenerMensajeErrorPorCodigo(documentRes.getRespuestaBoveda().getCodigoError());
			log.debug("Ocurrio un error al adjuntar el documento {}",error);
			
			StringBuilder sb = new StringBuilder("Error al Descargar el Archivo con ObjectID ");
			sb.append(idDocBoveda).append(" con el siguiente error ").append(error);
			
			throw new BovedaCDAException(sb.toString(), error);
		}
		}catch(Exception e){
			tiempoTermino = System.nanoTime();
			log.error("---CDA--- La Consulta se tardo  "+ ( tiempoTermino - tiempoInicio ) +" nanoSegundos");
			log.error("---CDA---Ocurrio un error Inesperado {}",e);
			manejoErroresBoveda(e,idDocBoveda,tiempoTermino-tiempoInicio);
		}
	
		return documento;
	}
	
	public String eliminarDocumento(String idDocBoveda) throws BovedaCDAException {
		
	    if(idDocBoveda != null){
			
			Documento doc = new Documento();
			doc.setIdDocumento(idDocBoveda);
			
			DeleteDocumentReq deleteDocumentReq = new DeleteDocumentReq();
			deleteDocumentReq.setDocumento(doc);
			
			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("DOCUMENTO a eliminar :[idDocumento: ");
			sBuilder.append(doc.getIdDocumento());
			sBuilder.append("]");
			
			DeleteDocumentRes documentRes = null;
			log.debug("---CDA--- Parametros eliminar documento: {}", sBuilder.toString());
			try{
				documentRes = bovedaUtility.deleteDocumentV2(deleteDocumentReq);			
                
				if (documentRes.getRespuestaBoveda().isExito()) {
					log.info("---CDA--- Se elimino exitosamente el documento con Id: {}",documentRes.getRespuestaBoveda().getIdDocumento());
				}
				
			}catch(Exception e){
				log.error("---CDA---Ocurrio un error Inesperado al eliminar documento {}",e);
				manejoErroresEliminacion(e,idDocBoveda);
			}
			
			return MensajesBovedaCDAEnum.obtenerMensajeErrorPorCodigo(documentRes.getRespuestaBoveda().getCodigoError());
		}
				
		return null;
	}
	
	public String eliminarDocumentoLogica(String idDocBoveda) throws BovedaCDAException{
		if(idDocBoveda != null){
			
			Documento doc = new Documento();
			doc.setIdDocumento(idDocBoveda);
			
			DeleteDocumentReq deleteDocumentReq = new DeleteDocumentReq();
			deleteDocumentReq.setDocumento(doc);
			
			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("DOCUMENTO a eliminar :[idDocumento: ");
			sBuilder.append(doc.getIdDocumento());
			sBuilder.append("]");
			
			DeleteDocumentRes documentRes;
			
			log.debug("---CDA--- Parametros eliminar documento: {}", sBuilder.toString());
			try{
				documentRes = bovedaUtility.logicDeleteDocumentV2(deleteDocumentReq);
				
				if (documentRes.getRespuestaBoveda().isExito()) {
					log.info("---CDA--- Se elimino exitosamente el documento con Id: {}",documentRes.getRespuestaBoveda().getIdDocumento());
					StringBuilder builder = new StringBuilder()
					.append("El documento fue eliminado con \u00e9xito.<br>")
					.append(documentRes.getRespuestaBoveda().getCodigoError())
					.append(" - ")
					.append(MensajesBovedaCDAEnum.obtenerDescripcionPorCodigo(documentRes.getRespuestaBoveda().getCodigoError()))
					.append(".");
					
					return builder.toString();
				} else {
					log.debug("---CDA--- Codigo: {}", documentRes.getRespuestaBoveda()
							.getCodigoError());
					log.debug("---CDA--- Mensaje: {}", documentRes.getRespuestaBoveda()
							.getDescripcionError());
				}
			}catch(Exception e){
				log.error("---CDA---Ocurrio un error Inesperado al eliminar documento {}",e);
				manejoErroresEliminacion(e,idDocBoveda);
			}
		}
		return null;
	}
	
	private String crearNombreArchivo(String nombre){
		StringBuilder stb = new StringBuilder(nombre);
		stb.insert(0, System.nanoTime());
		return stb.toString();
	}

	private String crearCadenaParametrosAdjuntado(CreateDocumentReq createDocumentReq){
		StringBuilder stb = new StringBuilder();
		stb.append("Nombre Archivo ");
		stb.append(createDocumentReq.getDocumento().getNombreArchivo());
		stb.append(" Tipo ");
		stb.append(createDocumentReq.getDocumento().getExtencion());
		stb.append(" Folio Tramite ");
		stb.append(createDocumentReq.getTramite().getFolioTramite());
		stb.append(" idSolicitud ");
		stb.append(createDocumentReq.getUsuario().getIdUsr());
		stb.append(" tipoDocumento ");
		stb.append(createDocumentReq.getUsuario().getTipoIdUsr());
		return stb.toString();
	}
	
	private String enviarDocumentoBoveda(CreateDocumentReq createDocumentReq) throws BovedaCDAException{
		CreateDocumentRes createDocumentRes = null;
		long tiempoInicio = 0 ,tiempoTermino = 0 ;
		try {
			tiempoInicio = System.nanoTime();
			log.info("---CDA--- Iniciando consulta {}", new Date());
			createDocumentRes = bovedaUtility
					.createDocumentV2(createDocumentReq);
			tiempoTermino = System.nanoTime();
			log.info("---CDA--- Finalizando consulta {}", new Date());
			log.info("---CDA--- La Consulta se tardo  "+ ( tiempoTermino - tiempoInicio ) +" nanosegundos");
		} catch (Exception e) {
			tiempoTermino = System.nanoTime();
			log.error("---CDA--- La Consulta se tardo  "+ ( tiempoTermino - tiempoInicio ) +" nanosegundos");			
			log.error("---CDA--- Error al subir el documento {}", e);
			manejoErroresAdjuntado(e, tiempoTermino-tiempoInicio, createDocumentReq);
			return null;
		}
	
		if (createDocumentRes.getRespuestaBoveda().isExito()) {
			log.debug("---CDA--- documento insertado en boveda");
			log.debug("---CDA--- EL DOCUMENTO SUBIDO ES : {}",
					createDocumentReq.getDocumento().getNombreArchivo());
			log.debug("---CDA--- EL Documento ID  regresado de la boveda es  : {}",
					createDocumentRes.getRespuestaBoveda().getIdDocumento());
		} else {
			String mensaje = MensajesBovedaCDAEnum.obtenerMensajeErrorPorCodigo(createDocumentRes.getRespuestaBoveda().getCodigoError());
			log.debug("---CDA--- Error en createDocument ::: {}",mensaje);
			StringBuilder build = new StringBuilder("Error al Adjuntar el Archivo con los siguientes parametros  ");
			build.append(crearCadenaParametrosAdjuntado(createDocumentReq)).append(mensaje);			
			throw new BovedaCDAException(build.toString(),mensaje);
		}
		return createDocumentRes.getRespuestaBoveda().getIdDocumento();
	}	

	private void manejoErroresAdjuntado(Exception e,long time, CreateDocumentReq createDocumentReq) throws BovedaCDAException{
		if(e instanceof SocketTimeoutException){
			throw new BovedaCDAException(" SocketTimeoutException al adjuntar  el archivo con los siguientes parametros "+crearCadenaParametrosAdjuntado(createDocumentReq)+ "Tiempo " +time);
		}else{
			throw new BovedaCDAException(" Error al Adjuntar el Archivo con los siguientes parametros  "+crearCadenaParametrosAdjuntado(createDocumentReq)
					+ "Exception "+e.getMessage()+ " Causa " + e.getCause());
		}
	}
	
	private void manejoErroresBoveda(Exception e, String documentID, long time) throws BovedaCDAException {
		String mensajeError = MensajesBovedaCDAEnum.obtenerMensajeErrorPorCodigo(MensajesBovedaCDAEnum.MSJ_EX002.getCodigo());
		if(e instanceof SocketTimeoutException){
			throw new BovedaCDAException("SocketTimeoutException al recuperar el archivo con ObjectID "+documentID+ " Tiempo " +time+" nanosegundos",mensajeError);
		}else if (e instanceof BovedaCDAException){
			throw (BovedaCDAException)e;
		}else{
			throw new BovedaCDAException("Error al Descargar el Archivo con ObjectID "+documentID +e.getMessage(),mensajeError);
		}
		
	}
	
	private void manejoErroresEliminacion(Exception e, String documentID) throws BovedaCDAException {
		String mensajeError = MensajesBovedaCDAEnum.obtenerMensajeErrorPorCodigo(MensajesBovedaCDAEnum.MSJ_EX003.getCodigo());
		if(e instanceof SocketTimeoutException){
			throw new BovedaCDAException(" SocketTimeoutException al recuperar el archivo con ObjectID "+documentID,mensajeError);
		}else{
			throw new BovedaCDAException("Error al Descargar el Archivo con ObjectID "+documentID +e.getMessage(), mensajeError);
		}
		
	}
	
}
