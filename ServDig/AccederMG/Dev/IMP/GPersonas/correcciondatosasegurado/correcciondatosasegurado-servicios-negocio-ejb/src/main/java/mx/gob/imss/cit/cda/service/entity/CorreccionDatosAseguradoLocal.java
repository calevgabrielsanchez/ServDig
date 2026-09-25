package mx.gob.imss.cit.cda.service.entity;

import java.util.List;

import javax.ejb.Local;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.DatosLaborales;

import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoTramite;
import mx.gob.imss.ctirss.delta.persistence.DitDetalleNss;

@Local
public interface CorreccionDatosAseguradoLocal {
	
	Integer DIAS_MAXIMOS_ESPERA= 40;

	TramiteCorreccionCurp almacenarTramiteCDA(Long idTramite, String curp);
        
        void almacenaDatosLaborales(Long idTramiteCorreccion, DatosLaborales datosLaborales);
	
	Long getIdTramiteActivo(List<String> curps,List<Integer> estadosValidos);
	
	DicEstadoTramite consultarEstadoTramiteById(Long idTramite);
	
	DitDetalleNss bloquearNSS(String nss, Long idCorreccionDatosAseg, Long idOrigen);
	
	DitDetalleNss consultarBloqueoNSS(String nss);
	
	int insertarResponableAutorizadorCorreccion (Long idTramite, int tipoUsr);
	
	String obtenerResponsableTramiteCDA(Long idSolicitud);
	
	String obtenerResponsableTramiteCDA(String folio);
	
	void actualizarSubdelegacionSolicitud (Solicitud sol);
	
}
