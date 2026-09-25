package mx.gob.imss.cit.clienteServiciosComunes.services.impl;

import org.springframework.beans.factory.annotation.Autowired;

import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.AddDocumentActorRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.AddDocumentActorResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.AddFolderActorRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.AddFolderActorResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.AllDocumentVersionsByDocRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.AllDocumentVersionsByDocResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.AllDocumentVersionsMetadataByDocRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.AllDocumentVersionsMetadataByDocResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.AllMetadataByMetadataRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.AllMetadataByMetadataResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.CreateDocumentRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.CreateDocumentResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.CreateFolderRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.CreateFolderResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.DeleteDocumentRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.DocumentRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.DocumentsByMetadataRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.DocumentsByMetadataResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.FolderDescendantsRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.FolderDescendantsResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.FolderDocumentsRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.FolderDocumentsResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.FolderObjectsRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.FolderObjectsResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.MetadataByDocRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.MetadataByDocResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.UserFolderRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.UserFolderResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.bovedapersonalcommonschema.Actor;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.bovedapersonalcommonschema.BaseObject;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.bovedapersonalcommonschema.Document;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.bovedapersonalcommonschema.Tramite;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.carpetaws.ICarpetaWSService;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.documentows.IDocumentoWSService;
import mx.gob.imss.cit.clienteServiciosComunes.helper.BovedaPersonalHelper;
import mx.gob.imss.cit.clienteServiciosComunes.helper.CodecHelper;
import mx.gob.imss.cit.clienteServiciosComunes.model.CreateDocumentReq;
import mx.gob.imss.cit.clienteServiciosComunes.model.CreateDocumentRes;
import mx.gob.imss.cit.clienteServiciosComunes.model.DeleteDocumentReq;
import mx.gob.imss.cit.clienteServiciosComunes.model.DeleteDocumentRes;
import mx.gob.imss.cit.clienteServiciosComunes.model.DocumentReq;
import mx.gob.imss.cit.clienteServiciosComunes.model.DocumentRes;
import mx.gob.imss.cit.clienteServiciosComunes.model.RespuestaBoveda;
import mx.gob.imss.cit.clienteServiciosComunes.services.BovedaPersonalService;

public class BovedaPersonalServiceImpl implements BovedaPersonalService {

	private IDocumentoWSService iDocumentoWSService;

	private ICarpetaWSService iCarpetaWSService;

	@Autowired
	private CodecHelper codecHelper;

	@Autowired
	private BovedaPersonalHelper bovedaPersonalHelper;

	public DocumentRes getDocument(DocumentReq documentReq) {
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
		return bovedaPersonalHelper.parseRespuesta(iDocumentoWSService.getDocument(documentRequest));
	}

	public CreateDocumentRes createDocument(CreateDocumentReq createDocumentReq) {
		Tramite tramite = new Tramite();
		Actor actor = new Actor();
		Document document = new Document();
		tramite.setFolioTramite(createDocumentReq.getTramite().getFolioTramite());
		document.setName(createDocumentReq.getDocumento().getNombreArchivo());
		document.setContent(codecHelper.encodeByteArrayToBase64(createDocumentReq.getDocumento().getArchivo()));
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
			CreateDocumentResponse createDocumentResponse = iDocumentoWSService.createDocument(createDocumentRequest);
			createDocumentRes = bovedaPersonalHelper.parseRespuesta(createDocumentResponse);
		} catch (Exception e) {
			createDocumentRes.setRespuestaBoveda(new RespuestaBoveda());
			createDocumentRes.getRespuestaBoveda().setExito(false);
			createDocumentRes.getRespuestaBoveda().setDescripcionError(e.getMessage());
			e.printStackTrace();
		}
		
		return createDocumentRes;
	}

	public DocumentsByMetadataResponse findDocumentsByMetadata(DocumentsByMetadataRequest body) {
		return iDocumentoWSService.findDocumentsByMetadata(body);
	}

	public DeleteDocumentRes deleteDocument(DeleteDocumentReq deleteDocumentReq) {
		Tramite tramite = new Tramite();
		Actor actor = new Actor();
		DeleteDocumentRequest deleteDocumentRequest = new DeleteDocumentRequest();
		tramite.setFolioTramite(deleteDocumentReq.getTramite().getFolioTramite());
		actor.setId(deleteDocumentReq.getUsuario().getIdUsr());
		actor.setTipoId(deleteDocumentReq.getUsuario().getTipoIdUsr());
		actor.setIsOwner(String.valueOf(deleteDocumentReq.getUsuario().isOwner()));
		BaseObject object = new BaseObject();
		object.setName(deleteDocumentReq.getDocumento().getNombreArchivo());
		deleteDocumentRequest.setObject(object);
		deleteDocumentRequest.setTramite(tramite);
		deleteDocumentRequest.setActor(actor);
		return bovedaPersonalHelper.parseRespuesta(iDocumentoWSService.deleteDocument(deleteDocumentRequest));
	}

	public AllMetadataByMetadataResponse getAllMetadataByMetadata(AllMetadataByMetadataRequest body) {
		return iDocumentoWSService.getAllMetadataByMetadata(body);
	}

	public MetadataByDocResponse getMetadataByDoc(MetadataByDocRequest body) {
		return iDocumentoWSService.getMetadataByDoc(body);
	}

	public AllDocumentVersionsByDocResponse getAllDocumentVersionsByDoc(AllDocumentVersionsByDocRequest body) {
		return iDocumentoWSService.getAllDocumentVersionsByDoc(body);
	}

	public AllDocumentVersionsMetadataByDocResponse getAllDocumentVersionsMetadataByDoc(
			AllDocumentVersionsMetadataByDocRequest body) {
		return iDocumentoWSService.getAllDocumentVersionsMetadataByDoc(body);
	}

	public AddDocumentActorResponse addDocumentActor(AddDocumentActorRequest body) {
		return iDocumentoWSService.addDocumentActor(body);
	}

	public CreateFolderResponse createFolder(CreateFolderRequest body) {
		return iCarpetaWSService.createFolder(body);
	}

	public FolderDocumentsResponse getFolderDocuments(FolderDocumentsRequest body) {
		return iCarpetaWSService.getFolderDocuments(body);
	}

	public FolderObjectsResponse getFolderObjects(FolderObjectsRequest body) {
		return iCarpetaWSService.getFolderObjects(body);
	}

	public FolderDescendantsResponse getFolderDescendants(FolderDescendantsRequest body) {
		return iCarpetaWSService.getFolderDescendants(body);
	}

	public UserFolderResponse getUserFolder(UserFolderRequest body) {
		return iCarpetaWSService.getUserFolder(body);
	}

	public AddFolderActorResponse addFolderActor(AddFolderActorRequest body) {
		return iCarpetaWSService.addFolderActor(body);
	}

	public IDocumentoWSService getiDocumentoWSService() {
		return iDocumentoWSService;
	}

	public void setiDocumentoWSService(IDocumentoWSService iDocumentoWSService) {
		this.iDocumentoWSService = iDocumentoWSService;
	}

	public ICarpetaWSService getiCarpetaWSService() {
		return iCarpetaWSService;
	}

	public void setiCarpetaWSService(ICarpetaWSService iCarpetaWSService) {
		this.iCarpetaWSService = iCarpetaWSService;
	}

}
