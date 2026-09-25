package mx.gob.imss.cit.clienteServiciosComunes.helper;

import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.CreateDocumentResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.DeleteDocumentResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.DocumentResponse;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.bovedapersonalcommonschema.Document;
import mx.gob.imss.cit.clienteServiciosComunes.model.CreateDocumentRes;
import mx.gob.imss.cit.clienteServiciosComunes.model.DeleteDocumentRes;
import mx.gob.imss.cit.clienteServiciosComunes.model.DocumentRes;
import mx.gob.imss.cit.clienteServiciosComunes.model.Documento;
import mx.gob.imss.cit.clienteServiciosComunes.model.RespuestaBoveda;
import mx.gob.imss.cit.clienteServiciosComunes.ws.commonschema.SGBDS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BovedaPersonalHelper {

	@Autowired
	private CodecHelper codecHelper;

	public RespuestaBoveda parseRespuestaCommon(SGBDS sgbds) {
		RespuestaBoveda respuestaBoveda = new RespuestaBoveda();
		respuestaBoveda.setExito(sgbds.isSuccessful());
		respuestaBoveda.setCodigoError(sgbds.getErrorCode() != null ? sgbds
				.getErrorCode().toString() : "0");
		respuestaBoveda.setDescripcionError(sgbds.getErrorDescription());
		return respuestaBoveda;
	}

	public Documento parseDocumento(Document document) {
		Documento documento = new Documento();
		documento.setArchivo(codecHelper.decodeFromBase64AsByteArray(document
				.getContent()));
		documento.setNombreArchivo(document.getName());
		return documento;
	}

	public CreateDocumentRes parseRespuesta(
			CreateDocumentResponse createDocumentResponse) {
		CreateDocumentRes createDocumentRes = new CreateDocumentRes();
		createDocumentRes
				.setRespuestaBoveda(parseRespuestaCommon(createDocumentResponse
						.getGovernanceHeaderResponse().getSgbds()));
		return createDocumentRes;
	}

	public DocumentRes parseRespuesta(DocumentResponse documentResponse) {
		DocumentRes documentRes = new DocumentRes();
		documentRes.setRespuestaBoveda(parseRespuestaCommon(documentResponse
				.getGovernanceHeaderResponse().getSgbds()));
		if (documentRes.getRespuestaBoveda().isExito()) {
			documentRes.setDocumento(parseDocumento(documentResponse
					.getDocument()));
		}
		return documentRes;
	}

	public DeleteDocumentRes parseRespuesta(
			DeleteDocumentResponse deleteDocumentResponse) {
		DeleteDocumentRes deleteDocumentRes = new DeleteDocumentRes();
		deleteDocumentRes
				.setRespuestaBoveda(parseRespuestaCommon(deleteDocumentResponse
						.getGovernanceHeaderResponse().getSgbds()));
		return deleteDocumentRes;
	}

}
