/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Héctor Lara Andrés
 *  @Proyecto: delta
 *  @Archivo:SolicitudDocumentoBusiness.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.solicitud
 *  @Fecha:25/10/2012
 */
package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.business.solicitud;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.solicitud.SolicitudDocumentoEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.clasificacion.service.interfaces.solicitud.SolicitudDocumentoBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rule.RuleServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.clasificacion.DocumentosAnalisis;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;

@Stateless(name="solicitudDocumentoBusiness", mappedName="solicitudDocumentoBusiness")
public class SolicitudDocumentoBusiness extends AbstractServiceBusiness implements SolicitudDocumentoBusinessRemote{
	
	@EJB
	private SolicitudDocumentoEntityLocal solicitudDocumentoEntity;
	
	@EJB
	RuleServiceBusinessRemote ruleServiceBusinessRemote;
	
	@Override
	public DocumentosAnalisis obtenerDocumento(Long idSolicitud){
		return solicitudDocumentoEntity.obtenerDocumento(idSolicitud);
	}

	@Override
	public void pruebaInsertarDocumentoProbatorio() {
		solicitudDocumentoEntity.pruebaaInsertarDocumentoProbatorio();
	}

	@Override
	public DocumentoProbatorio pruebaaObtenerDocumentoProbatorio(){
		return solicitudDocumentoEntity.pruebaaObtenerDocumentoProbatorio();
	}

	// Se comenta ya que se esta utilizando las validaciones agregadas en el Controller
	// con el servicio validaClasificacionServiceBusiness.validaClasificacionPropuesta
	// el cual utiliza los servicios utilizados en gestion patronal para validar
	// la clasificacion  JSM
	
//	@Override
//	public String pruebaReglaRPC(String rfc, Long clase, String registroPatronal){
//		String msg="";
//		try {
//			ruleServiceBusinessRemote.validarRPC_AP_MOD_MAC(rfc, clase, registroPatronal);
//			msg="El RFC indicado no es afectado por la validación RPC";
//		} catch (GestionPatronalBusinessException gpbe) {
//			msg="El RFC indicado ya cuenta con una Clase " + clase.toString() + "\n" + gpbe.getMessage();
//		}catch(NullPointerException npe){
//			msg="Datos nulos:: "+ "\n" +npe.getMessage();
//		}catch(Exception e){
//			msg="Error Distinto a GestionPatronalBusinessException:: "+ "\n" +e.getMessage();
//		}
//		return msg;
//	}
//	
}