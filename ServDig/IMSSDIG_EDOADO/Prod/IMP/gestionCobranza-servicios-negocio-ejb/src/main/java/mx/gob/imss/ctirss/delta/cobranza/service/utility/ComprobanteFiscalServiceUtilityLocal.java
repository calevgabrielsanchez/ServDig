package mx.gob.imss.ctirss.delta.cobranza.service.utility;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceComprobanteFiscalException;
import mx.gob.imss.ctirss.delta.model.gestion.cobranza.cfdi.Pago;

@Local
public interface ComprobanteFiscalServiceUtilityLocal {
	
	List<Pago> obtenerPagos(Pago pago) throws ClienteWebserviceComprobanteFiscalException;
	
	Map<String, Object> descargarFacturaElectronica(Pago pago, BufferedImage imagenCodeQR);
	
	String generarCadenaCodigoQR(Pago pago);
}
