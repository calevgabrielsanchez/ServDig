package mx.gob.imss.ctirss.delta.cobranza.service.interfaces;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.cobranza.exception.EstadoAdeudoException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceComprobanteFiscalException;
import mx.gob.imss.ctirss.delta.model.gestion.cobranza.cfdi.Pago;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.enums.TipoDescargaArchivo;

@Remote
public interface ComprobanteFiscalServiceRemote {
	
	List<Pago> obtenerPagosporPeriodo(Pago pagoFiscal) throws ClienteWebserviceComprobanteFiscalException;
	
	Solicitud crearSolicitudDescargaCompFiscal(Pago pagoFiscal, Date fechaActual, TipoDescargaArchivo tipoDescarga) throws EstadoAdeudoException;

	String getCadenaComprobanteFiscal(Pago pagoFiscal);

	Map<String, Object> descargarFacturaElectronica(Pago pagoFiscal);
}
