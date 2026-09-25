package mx.gob.imss.cit.clienteServiciosComunes.services;

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
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.CreateFolderRequest;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.CreateFolderResponse;
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
import mx.gob.imss.cit.clienteServiciosComunes.model.CreateDocumentReq;
import mx.gob.imss.cit.clienteServiciosComunes.model.CreateDocumentRes;
import mx.gob.imss.cit.clienteServiciosComunes.model.DeleteDocumentReq;
import mx.gob.imss.cit.clienteServiciosComunes.model.DeleteDocumentRes;
import mx.gob.imss.cit.clienteServiciosComunes.model.DocumentReq;
import mx.gob.imss.cit.clienteServiciosComunes.model.DocumentRes;

public interface BovedaPersonalService {

	DocumentRes getDocument(DocumentReq documentReq);

	CreateDocumentRes createDocument(CreateDocumentReq createDocumentReq);

	DocumentsByMetadataResponse findDocumentsByMetadata(
			DocumentsByMetadataRequest body);

	DeleteDocumentRes deleteDocument(DeleteDocumentReq deleteDocumentReq);

	AllMetadataByMetadataResponse getAllMetadataByMetadata(
			AllMetadataByMetadataRequest body);

	MetadataByDocResponse getMetadataByDoc(MetadataByDocRequest body);

	AllDocumentVersionsByDocResponse getAllDocumentVersionsByDoc(
			AllDocumentVersionsByDocRequest body);

	AllDocumentVersionsMetadataByDocResponse getAllDocumentVersionsMetadataByDoc(
			AllDocumentVersionsMetadataByDocRequest body);

	AddDocumentActorResponse addDocumentActor(AddDocumentActorRequest body);

	CreateFolderResponse createFolder(CreateFolderRequest body);

	FolderDocumentsResponse getFolderDocuments(FolderDocumentsRequest body);

	FolderObjectsResponse getFolderObjects(FolderObjectsRequest body);

	FolderDescendantsResponse getFolderDescendants(FolderDescendantsRequest body);

	UserFolderResponse getUserFolder(UserFolderRequest body);

	AddFolderActorResponse addFolderActor(AddFolderActorRequest body);
}
