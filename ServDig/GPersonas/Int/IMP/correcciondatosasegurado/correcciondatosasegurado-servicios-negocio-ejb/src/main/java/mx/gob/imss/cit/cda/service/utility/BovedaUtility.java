package mx.gob.imss.cit.cda.service.utility;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;

import javax.ejb.Stateless;
import javax.xml.ws.BindingProvider;

import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.CreateDocumentRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.CreateDocumentResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.DeleteDocumentResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.DocumentRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.DocumentResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.bovedapersonalcommonschema.Actor;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.bovedapersonalcommonschema.BaseObject;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.bovedapersonalcommonschema.Document;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.bovedapersonalcommonschema.Tramite;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.documentows.DocumentoWSServiceImplService;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.documentows.IDocumentoWSService;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.v2.Atributo;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.v2.EntradaAlta;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.v2.EntradaBaja;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.v2.EntradaBajaLogica;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.v2.EntradaConsulta;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.v2.IESServicio;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.v2.IESServicioSoap;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.v2.SalidaAlta;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.v2.SalidaBaja;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.v2.SalidaBajaLogica;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.v2.SalidaConsulta;
import mx.gob.imss.cit.clienteServiciosComunes.model.CreateDocumentReq;
import mx.gob.imss.cit.clienteServiciosComunes.model.CreateDocumentRes;
import mx.gob.imss.cit.clienteServiciosComunes.model.DeleteDocumentReq;
import mx.gob.imss.cit.clienteServiciosComunes.model.DeleteDocumentRes;
import mx.gob.imss.cit.clienteServiciosComunes.model.DocumentReq;
import mx.gob.imss.cit.clienteServiciosComunes.model.DocumentRes;
import mx.gob.imss.cit.clienteServiciosComunes.model.Documento;
import mx.gob.imss.cit.clienteServiciosComunes.model.RespuestaBoveda;
import mx.gob.imss.cit.clienteServiciosComunes.ws.commonschema.SGBDS;
import mx.gob.imss.ctirss.delta.model.enums.MensajesBovedaCDAEnum;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Stateless(name="bovedaUtility", mappedName="bovedaUtility")
public class BovedaUtility implements BovedaUtilityLocal {
	
	private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");
	private final Logger log = LoggerFactory.getLogger(BovedaUtility.class);

	@Override
	public DocumentRes getDocument(DocumentReq documentReq) throws MalformedURLException {
		Tramite tramite = new Tramite();
		Actor actor = new Actor();
		DocumentRequest documentRequest = new DocumentRequest();
		tramite.setFolioTramite(documentReq.getTramite().getFolioTramite());
		actor.setId(documentReq.getUsuario().getIdUsr());
		actor.setTipoId(documentReq.getUsuario().getTipoIdUsr());
		actor.setIsOwner(String.valueOf(documentReq.getUsuario().isOwner()));
		BaseObject object = new BaseObject();
		object.setName(documentReq.getDocumento().getNombreArchivo());
		documentRequest.setObject(object);
		documentRequest.setTramite(tramite);
		documentRequest.setActor(actor);

		return parseRespuesta(getIDocumentoWSService().getDocument(documentRequest));
	}
	
	@Override
	public DocumentRes getDocumentV2(DocumentReq documentReq) throws MalformedURLException {
		EntradaConsulta entradaConsulta = new EntradaConsulta();
		
		Atributo atributo = new Atributo();
		log.info("---CDA--- Parametros de busqueda del archivo ");
		if(documentReq.getDocumento().getIdDocumento() != null){		
			atributo.setNombre("objectId");
			atributo.setValor(documentReq.getDocumento().getIdDocumento());		
			entradaConsulta.setTipoDocumental(TIPO_DOCUMENTAL_CDA);
		}else{
			atributo.setNombre("folioTramite");
			atributo.setValor(documentReq.getTramite().getFolioTramite());
			entradaConsulta.setTipoDocumental(TIPO_DOCUMENTAL_HISTORICO);
			Atributo atributo2 = new Atributo();
			atributo2.setNombre("name");
			atributo2.setValor(documentReq.getDocumento().getNombreArchivo());
			entradaConsulta.getAtributo().add(atributo2);		
		}
		entradaConsulta.getAtributo().add(atributo);
		
		log.info("---CDA--- TipoDocumental {} ",entradaConsulta.getTipoDocumental());
		
		for(Atributo atributoMandado : entradaConsulta.getAtributo()){
			log.info("---CDA--- Nombre del parametro {}",atributoMandado.getNombre());
			log.info("---CDA--- Valor del parametro {}",atributoMandado.getValor());			
		}
		return parseRespuesta(getIESServicioSoap().consultaDocumento(entradaConsulta));
	}

	@Override
	public CreateDocumentRes createDocument(CreateDocumentReq createDocumentReq) {
		Tramite tramite = new Tramite();
		Actor actor = new Actor();
		Document document = new Document();
		tramite.setFolioTramite(createDocumentReq.getTramite().getFolioTramite());
		document.setName(createDocumentReq.getDocumento().getNombreArchivo());
		document.setContent(encodeByteArrayToBase64(createDocumentReq.getDocumento().getArchivo()));
		document.setExt(createDocumentReq.getDocumento().getExtencion());
		document.setMimeType(createDocumentReq.getDocumento().getMimeType());
		document.setIsFolder(String.valueOf(createDocumentReq.getDocumento().isFolder()));
		actor.setId(createDocumentReq.getUsuario().getIdUsr());
		actor.setTipoId(createDocumentReq.getUsuario().getTipoIdUsr());
		actor.setIsOwner(String.valueOf(createDocumentReq.getUsuario().isOwner()));
		CreateDocumentRequest createDocumentRequest = new CreateDocumentRequest();
		createDocumentRequest.setActor(actor);
		createDocumentRequest.setDocument(document);
		createDocumentRequest.setTramite(tramite);
		createDocumentRequest.setIsEncripted(String.valueOf(createDocumentReq.getDocumento().isEncriptado()));
		CreateDocumentRes createDocumentRes = new CreateDocumentRes();
		try {
			
			
			
			CreateDocumentResponse createDocumentResponse = 
					getIDocumentoWSService().createDocument(createDocumentRequest);
			createDocumentRes = parseRespuesta(createDocumentResponse);
		} catch (Exception e) {
			createDocumentRes.setRespuestaBoveda(new RespuestaBoveda());
			createDocumentRes.getRespuestaBoveda().setExito(false);
			createDocumentRes.getRespuestaBoveda().setDescripcionError(e.getMessage());
			log.debug("Ocurrio un error al adjuntar el documento ",e);
		}
		
		return createDocumentRes;
	}
	
	public IESServicioSoap getIESServicioSoap() throws MalformedURLException {
		PropertiesOpciones properties = new PropertiesOpciones();
        Map<String, String> opciones = properties.getOpciones();
		final IESServicio service = new IESServicio(
				new URL(opciones.get("boveda.documento.wsdl.v2")),
				new javax.xml.namespace.QName("http://www.openuri.org/", "IESServicio"));
		final IESServicioSoap port = service.getIESServicioSoap();
		((BindingProvider) port).getRequestContext().put("com.sun.xml.internal.ws.request.timeout", Integer.parseInt(opciones.get("common.request.timeout")));
    	((BindingProvider) port).getRequestContext().put("com.sun.xml.internal.ws.connect.timeout", Integer.parseInt(opciones.get("common.connect.timeout")));
    	((BindingProvider) port).getRequestContext().put("com.sun.xml.ws.request.timeout", Integer.parseInt(opciones.get("common.request.timeout")));
    	((BindingProvider) port).getRequestContext().put("com.sun.xml.ws.connect.timeout", Integer.parseInt(opciones.get("common.connect.timeout")));
		return port;
	}
	
	public IDocumentoWSService getIDocumentoWSService() throws MalformedURLException {
		PropertiesOpciones properties = new PropertiesOpciones();
        Map<String, String> opciones = properties.getOpciones();
		final DocumentoWSServiceImplService service = new DocumentoWSServiceImplService(
				new URL(opciones.get("boveda.documento.wsdl")),
				new javax.xml.namespace.QName(
						"http://documentows.ws.bp.cit.imss.gob.mx/",
						"DocumentoWSServiceImplService"));
		final IDocumentoWSService port = service.getDocumentoWSServiceImplPort();
		((BindingProvider) port).getRequestContext().put("com.sun.xml.internal.ws.request.timeout", Integer.parseInt(opciones.get("common.request.timeout")));
    	((BindingProvider) port).getRequestContext().put("com.sun.xml.internal.ws.connect.timeout", Integer.parseInt(opciones.get("common.connect.timeout")));
    	((BindingProvider) port).getRequestContext().put("com.sun.xml.ws.request.timeout", Integer.parseInt(opciones.get("common.request.timeout")));
    	((BindingProvider) port).getRequestContext().put("com.sun.xml.ws.connect.timeout", Integer.parseInt(opciones.get("common.connect.timeout")));
		return port;
	}	
	
	public DeleteDocumentRes deleteDocumentV2(DeleteDocumentReq deleteDocumentReq){
		EntradaBaja entradaBaja = new EntradaBaja();

		if(deleteDocumentReq.getDocumento().getIdDocumento() != null){		
				entradaBaja.setIdDocumento(deleteDocumentReq.getDocumento().getIdDocumento());
		}
		
		log.info("---CDA--- Eliminar documento de boveda con idDocumento: {} ",entradaBaja.getIdDocumento());
		
		DeleteDocumentRes createDocumentRes = new DeleteDocumentRes();		
		try {
			createDocumentRes = parseRespuesta(getIESServicioSoap().bajaDocumento(entradaBaja));
		} catch (Exception e) {
			createDocumentRes.setRespuestaBoveda(new RespuestaBoveda());
			createDocumentRes.getRespuestaBoveda().setExito(false);
			createDocumentRes.getRespuestaBoveda().setCodigoError(MensajesBovedaCDAEnum.MSJ_EX003.getCodigo());
			createDocumentRes.getRespuestaBoveda().setDescripcionError(MensajesBovedaCDAEnum.obtenerMensajeErrorPorCodigo(MensajesBovedaCDAEnum.MSJ_EX003.getCodigo()));
			log.debug("Ocurrio un error al eliminar el documento {}" +  entradaBaja.getIdDocumento() ,e);
		}
		
		return createDocumentRes;
		
	}
	
	public DeleteDocumentRes logicDeleteDocumentV2(DeleteDocumentReq deleteDocumentReq){
		EntradaBajaLogica entradaBaja = new EntradaBajaLogica();

		if(deleteDocumentReq.getDocumento().getIdDocumento() != null){		
				entradaBaja.setIdDocumento(deleteDocumentReq.getDocumento().getIdDocumento());
		}
		
		log.info("---CDA--- Eliminar documento de boveda con idDocumento: {} ",entradaBaja.getIdDocumento());
		
		DeleteDocumentRes createDocumentRes = new DeleteDocumentRes();		
		try {
			createDocumentRes = parseRespuesta(getIESServicioSoap().bajaLogicaDocumento(entradaBaja));
		} catch (Exception e) {
			createDocumentRes.setRespuestaBoveda(new RespuestaBoveda());
			createDocumentRes.getRespuestaBoveda().setExito(false);
			createDocumentRes.getRespuestaBoveda().setDescripcionError(e.toString());
			log.debug("Ocurrio un error al eliminar el documento " +  entradaBaja.getIdDocumento() ,e);
		}
		
		return createDocumentRes;
		
	}
	
	private RespuestaBoveda parseRespuestaCommon(SGBDS sgbds) {
		RespuestaBoveda respuestaBoveda = new RespuestaBoveda();
		respuestaBoveda.setExito(sgbds.isSuccessful());
		respuestaBoveda.setCodigoError(sgbds.getErrorCode() != null ? sgbds
				.getErrorCode().toString() : "0");
		respuestaBoveda.setDescripcionError(sgbds.getErrorDescription());
		return respuestaBoveda;
	}

	private Documento parseDocumento(Document document) {
		Documento documento = new Documento();
		documento.setArchivo(decodeFromBase64AsByteArray(document
				.getContent()));
		documento.setNombreArchivo(document.getName());
		return documento;
	}

	private CreateDocumentRes parseRespuesta(
			CreateDocumentResponse createDocumentResponse) {
		CreateDocumentRes createDocumentRes = new CreateDocumentRes();
		createDocumentRes
				.setRespuestaBoveda(parseRespuestaCommon(createDocumentResponse
						.getGovernanceHeaderResponse().getSgbds()));
		return createDocumentRes;
	}
	
	private DocumentRes parseRespuesta(SalidaConsulta salidaConsulta) {
		DocumentRes documentRes = new DocumentRes();
		RespuestaBoveda respuestaBoveda = new RespuestaBoveda();
		respuestaBoveda.setExito(salidaConsulta.isExito());
		respuestaBoveda.setCodigoError(salidaConsulta.getClave() != null ? salidaConsulta.getClave() : "0");
		respuestaBoveda.setDescripcionError(salidaConsulta.getDescripcion());
		log.info("---CDA--- peticion Consulta codigo respuesta {} , mensaje {}  ", 
				new Object[]{salidaConsulta.getClave(),salidaConsulta.getDescripcion()});
		documentRes.setRespuestaBoveda(respuestaBoveda);
		if (documentRes.getRespuestaBoveda().isExito()) {
			Documento documento = new Documento();
			documento.setArchivo(salidaConsulta.getDocumento().get(0).getContenido());
			documento.setNombreArchivo(salidaConsulta.getDocumento().get(0).getNombre());			
			documentRes.setDocumento(documento);
		}
		return documentRes;
	}

	private DocumentRes parseRespuesta(DocumentResponse documentResponse) {
		DocumentRes documentRes = new DocumentRes();
		documentRes.setRespuestaBoveda(parseRespuestaCommon(documentResponse
				.getGovernanceHeaderResponse().getSgbds()));
		if (documentRes.getRespuestaBoveda().isExito()) {
			documentRes.setDocumento(parseDocumento(documentResponse
					.getDocument()));
		}
		return documentRes;
	}

	private DeleteDocumentRes parseRespuesta(
			DeleteDocumentResponse deleteDocumentResponse) {
		DeleteDocumentRes deleteDocumentRes = new DeleteDocumentRes();
		deleteDocumentRes
				.setRespuestaBoveda(parseRespuestaCommon(deleteDocumentResponse
						.getGovernanceHeaderResponse().getSgbds()));
		return deleteDocumentRes;
	}

	private String encodeToBase64(final String toEncode) {
		Base64 base64Codec = new Base64();
		return new String(base64Codec.encode(toEncode == null ? new byte[] {}
				: toEncode.getBytes()), UTF8_CHARSET);
	}

	private String decodeFromBase64(final String toDecode) {
		return new String(decodeFromBase64AsByteArray(toDecode), UTF8_CHARSET);
	}
	
	private byte[] decodeFromBase64AsByteArray(final byte[] toDecode) {
		Base64 base64Codec = new Base64();
		return base64Codec.decode(toDecode == null ? new byte[] {} : toDecode);
	}

	private byte[] decodeFromBase64AsByteArray(final String toDecode) {
		Base64 base64Codec = new Base64();
		return base64Codec.decode(toDecode == null ? new byte[] {} : toDecode
				.getBytes());
	}

	private String encodeByteArrayToBase64(final byte[] data) {
		Base64 base64Codec = new Base64();
		return null == data ? "" : base64Codec.encodeToString(data);
	}

	@Override
	public CreateDocumentRes createDocumentV2(
			CreateDocumentReq createDocumentReq) {
		EntradaAlta entradaAlta = new EntradaAlta();
		
		entradaAlta.setDocumento(createDocumentReq.getDocumento().getArchivo());
		entradaAlta.setTipoDocumental(TIPO_DOCUMENTAL_CDA);
		entradaAlta.setRuta(RUTA_CDA);
		entradaAlta.setTipo(createDocumentReq.getDocumento().getExtencion());
		
		Atributo atributo = new Atributo();
		atributo.setNombre("name");
		atributo.setValor(createDocumentReq.getDocumento().getNombreArchivo());
		Atributo atributo1 = new Atributo();
		atributo1.setNombre("folioTramite");
		atributo1.setValor(createDocumentReq.getTramite().getFolioTramite());
		Atributo atributo2 = new Atributo();
		atributo2.setNombre("idSolicitud");
		atributo2.setValor(createDocumentReq.getUsuario().getIdUsr());
		Atributo atributo3 = new Atributo();
		atributo3.setNombre("tipoDocumento");
		atributo3.setValor(createDocumentReq.getUsuario().getTipoIdUsr());
		
		entradaAlta.getAtributo().add(atributo);
		entradaAlta.getAtributo().add(atributo1);
		entradaAlta.getAtributo().add(atributo2);
		entradaAlta.getAtributo().add(atributo3);
		
		log.info("---CDA--- TipoDocumental {} ",entradaAlta.getTipoDocumental());
		log.info("---CDA--- Tipo {} ",entradaAlta.getTipo());
		log.info("---CDA--- Ruta {} ",entradaAlta.getRuta());
		log.info("---CDA--- Archivo size {} ",entradaAlta.getDocumento().length);
		
		StringBuilder builder = new StringBuilder()
		.append(" Nombre de archivo:")
		.append(createDocumentReq.getDocumento().getNombreArchivo())
		.append(" Folio:")
		.append(createDocumentReq.getTramite().getFolioTramite())
		.append(" Id Solicitud:")
		.append(createDocumentReq.getUsuario().getIdUsr());
		
		for(Atributo atributoMandado : entradaAlta.getAtributo()){
			log.info("---CDA--- Nombre del parametro {}",atributoMandado.getNombre());
			log.info("---CDA--- Valor del parametro {}",atributoMandado.getValor());			
		}
		
		
		
		CreateDocumentRes createDocumentRes = new CreateDocumentRes();		
		try {
			createDocumentRes = parseRespuesta(getIESServicioSoap().altaDocumento(entradaAlta));
		} catch (Exception e) {
			createDocumentRes.setRespuestaBoveda(new RespuestaBoveda());
			createDocumentRes.getRespuestaBoveda().setExito(false);
			createDocumentRes.getRespuestaBoveda().setDescripcionError(MensajesBovedaCDAEnum.MSJ_EX001.getDescripcion());
			createDocumentRes.getRespuestaBoveda().setCodigoError(MensajesBovedaCDAEnum.MSJ_EX001.getCodigo());
			log.debug("Ocurrio un error al adjuntar el documento {} {}",builder.toString() ,e);
		}
		
		return createDocumentRes;
	}
	
	private CreateDocumentRes parseRespuesta(
			SalidaAlta salidaAlta) {
		CreateDocumentRes createDocumentRes = new CreateDocumentRes();
		
		log.info("---CDA--- peticion Alta codigo respuesta {} , mensaje {} , para el id de documento {} ", 
				new Object[]{salidaAlta.getClave(),salidaAlta.getDescripcion(),salidaAlta.getIdDocumento()});
		RespuestaBoveda respuestaBoveda = new RespuestaBoveda();
		respuestaBoveda.setExito(salidaAlta.isExito());
		respuestaBoveda.setCodigoError(salidaAlta.getClave() != null ? salidaAlta.getClave() : "0");
		respuestaBoveda.setDescripcionError(salidaAlta.getDescripcion());
		
		respuestaBoveda.setIdDocumento(salidaAlta.getIdDocumento());
		
		createDocumentRes
				.setRespuestaBoveda(respuestaBoveda);
		return createDocumentRes;
	}
	
	private DeleteDocumentRes parseRespuesta(
			SalidaBaja salidaBaja) {
		DeleteDocumentRes deleteDocumentRes = new DeleteDocumentRes();
		
		RespuestaBoveda respuestaBoveda = new RespuestaBoveda();
		respuestaBoveda.setExito(salidaBaja.isExito());
		log.info("---CDA--- peticion eliminar codigo respuesta {} , mensaje {} , para el id de documento {} ", 
				new Object[]{salidaBaja.getClave(),salidaBaja.getDescripcion(),salidaBaja.getIdDocumento()});
		respuestaBoveda.setCodigoError(salidaBaja.getClave() != null ? salidaBaja.getClave() : "0");		
		respuestaBoveda.setDescripcionError(salidaBaja.getDescripcion());		
		respuestaBoveda.setIdDocumento(salidaBaja.getIdDocumento());
		
		deleteDocumentRes
				.setRespuestaBoveda(respuestaBoveda);
		return deleteDocumentRes;
	}
	
	private DeleteDocumentRes parseRespuesta(
			SalidaBajaLogica salidaBaja) {
		DeleteDocumentRes deleteDocumentRes = new DeleteDocumentRes();
		
		RespuestaBoveda respuestaBoveda = new RespuestaBoveda();
		respuestaBoveda.setExito(salidaBaja.isExito());
		respuestaBoveda.setCodigoError(salidaBaja.getClave() != null ? salidaBaja.getClave() : "0");
		respuestaBoveda.setDescripcionError(salidaBaja.getDescripcion());
		
		respuestaBoveda.setIdDocumento(salidaBaja.getIdDocumento());
		
		deleteDocumentRes
				.setRespuestaBoveda(respuestaBoveda);
		return deleteDocumentRes;
	}

}
