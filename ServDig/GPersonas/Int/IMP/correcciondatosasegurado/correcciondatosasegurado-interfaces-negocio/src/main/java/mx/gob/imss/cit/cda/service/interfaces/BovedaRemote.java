package mx.gob.imss.cit.cda.service.interfaces;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.BovedaCDAException;
import mx.gob.imss.ctirss.delta.model.enums.TipoDocumentoCDAEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;

@Remote
public interface BovedaRemote {
	
	String subirDocumento(byte[] documentoCertificado, Solicitud solicitud, TipoDocumentoCDAEnum tipoDocumentoCDAEnum) throws BovedaCDAException;
	String subirDocumento(byte[] archivo, Solicitud solicitud, String nombreDoc, String extension , String mimeType) throws BovedaCDAException;
	byte[] recuperarDocumento(Solicitud solicitud,TipoDocumentoCDAEnum tipoDocumentoCDAEnum,String objectId) throws BovedaCDAException;
	byte[] recuperarDocumento(Solicitud solicitud, String idDocBoveda, String nombreDoc) throws BovedaCDAException;
	String eliminarDocumento(String idDocBoveda) throws BovedaCDAException;
	String eliminarDocumentoLogica(String idDocBoveda) throws BovedaCDAException;
}
