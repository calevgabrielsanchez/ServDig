/**
 * 
 */
package mx.gob.imss.csdiss.sdroc.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mx.gob.imss.csdiss.sdroc.dto.SolicitudTramiteDTO;
import mx.gob.imss.csdiss.sdroc.util.GeneraReporte;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.FirmaDigitalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.enums.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.FirmaElectronica;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.TipoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;

/**
 * @author daniel.hernandez
 *
 */
@Service
public class SolicitudTramiteServiceImpl implements SolicitudTramiteService {

	
	
	@Autowired //Servicio para la creacion y consulta de solicitudes
	private SolicitudBusinessRemote solicitudBusinessRemote;
	@Autowired //Sericios para el registro de firma digital, ademas de guardado de documentos
	private FirmaDigitalBusinessRemote firmaDigitalBusinessRemote;
	//Ids de los tipos de solicitud y tramite utilizados en SDROC
	private final Long  ID_TIPO_SOLICITUD_OBRA = 48L;

	

	@Override
	public SolicitudTramiteDTO crearSolicitudTramite(Integer idTipoTramite, FirmaElectronica firmaElectronica) {

		Long idTramite = null;
		//creamos la solicitud
		SolicitudTramiteDTO solicitudTramiteDTO = new SolicitudTramiteDTO();
		
		Solicitud solicitud = new Solicitud();
		solicitud.setTipoSolicitud(new TipoSolicitud(ID_TIPO_SOLICITUD_OBRA));//Solicitud tipo de registro de obra
		solicitud.setFechaPresentacion(new Date());
		solicitud.setFechaConclusion(new Date());
		solicitud.setOrigenSolicitud(new OrigenSolicitud());
		solicitud.getOrigenSolicitud().setIdTipoSolicitud(OrigenSolicitudEnum.INTERNET.getId());
		solicitud.setEstadoSolicitud(new EstadoSolicitud(EstadoSolicitudEnum.ATENDIDA.getId().intValue()));
		//creamos la lista de tramites, solo va a lleva uno
		solicitud.setTramites(new ArrayList<Tramite>());
		Tramite tramiteRO = new Tramite();
		tramiteRO.setFechaPresentacion(new Date());
		tramiteRO.setFechaConclusion(new Date());
		tramiteRO.setTipoTramite(new TipoTramite(idTipoTramite));
		tramiteRO.setEstadoTramite(new EstadoTramite());
		tramiteRO.getEstadoTramite().setIdEstadoTramitePersona(EstadoTramiteEnum.CERRADO.getCodigo());
		//anadimos el tramite
		solicitud.getTramites().add(tramiteRO);
		
		//Creamos el tramite y la solicitud
		try {
			solicitud = solicitudBusinessRemote.crear(solicitud);
		} catch (SolicitudNoValidaException e) {
			e.printStackTrace();
			solicitud = null;
		}
		
		//si la solicitud se creo correctamente y se tienen los datos de firma electronica, se crea la relacion entre solicitud y firma
		if(solicitud != null && firmaElectronica != null) {
			firmaDigitalBusinessRemote.insertarSolicitudFirmaDigital(solicitud, firmaElectronica);
		}
		
		//Verificamos si la solicitud es diferente de null y si existen tramites en ella
		if(solicitud != null && solicitud.getTramites() != null && solicitud.getTramites().size() > 0) {
			//En caso de contener tramites obtenemos el primero, ya que para este caso solo deberia haber un solo tramite
			idTramite = solicitud.getTramites().get(0).getTramiteId();
		}
		
		solicitudTramiteDTO.setCveIdTramite(idTramite);
		solicitudTramiteDTO.setFolioSolicitud(solicitud.getNoFolioSolicitud());
		
		return solicitudTramiteDTO;

	}

	/**
	 * Funcion par aguardar los documentos en notaria
	 * @param secuenciaNotaria
	 * @param generadorReporte
	 * @param pathRegistroObraAcuse
	 * @param nombreReporte
	 * @param tipoRep
	 * @param paramAcuse
	 * @return
	 */
	@Override
	public byte[] guardarArchivoNotaria(String secuenciaNotaria,GeneraReporte generadorReporte, String pathRegistroObraAcuse,String nombreReporte, String tipoRep, HashMap<String, Object> paramAcuse) {
		Map<String, Object> doctoGenerado = null;
		byte[] bytesDoctoGenerado = null;
		//generamos el documento y llamamos esta funcion para que nos retorne el array de bytes
		doctoGenerado = generadorReporte.generaReportePDFBytes(pathRegistroObraAcuse, nombreReporte, tipoRep, paramAcuse);
		//obtenemos el array de bytes del documento generado
		bytesDoctoGenerado = (byte[])doctoGenerado.get("arrayDocto");
		
		//invocamos el servicio de guardado de documentos en notaria siempre y cuando tengamos la secuencia de notaria
		if(secuenciaNotaria != null) {
			try {
				firmaDigitalBusinessRemote.guardarArchivoFirmado(secuenciaNotaria, "registroObraAcuse.pdf", bytesDoctoGenerado);
			} catch(Exception e) {
				e.getCause();
				e.getMessage();
				e.printStackTrace();
			}
		}
		
		return bytesDoctoGenerado;
	}

	
}
