/**
 * 
 */
package mx.gob.imss.ctirss.delta.cobranza.service.business;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.autopac.modelo.RespuestaCancelacionCfdiDto;
import mx.gob.imss.ctirss.delta.cobranza.dto.PagoReferenciadoDTO;
import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorCancelarTimbradoException;
import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorEnCancelacionFolioTimbradoException;
import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorEnGeneracionComprobanteException;
import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorEnGeneracionXMLPagoException;
import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorEnServicioTimbradoException;
import mx.gob.imss.ctirss.delta.cobranza.model.LoteCFDI;
import mx.gob.imss.ctirss.delta.cobranza.model.RegistroCFDI;
import mx.gob.imss.ctirss.delta.cobranza.respuesta.RespuestaCancelacionCfdi;
import mx.gob.imss.ctirss.delta.cobranza.respuesta.RespuestaServicioTimbradoCFDI;
import mx.gob.imss.ctirss.delta.cobranza.service.utility.Utilerias;
import mx.gob.imss.ctirss.delta.cobranza.services.business.GenerarTimbradoCFDIServiceBusinessRemote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author vanderluk
 *
 */

@Stateless(name = "generarTimbradoCFDIServiceBusiness", mappedName = "generarTimbradoCFDIServiceBusiness")
public class GenerarTimbradoCFDIServiceBusiness implements
		GenerarTimbradoCFDIServiceBusinessRemote 
		{
	 private static final Logger LOG;
	
	
	static {
		LOG = LoggerFactory.getLogger(GenerarTimbradoCFDIServiceBusiness.class);
	}

	/* (non-Javadoc)
	 * @see mx.gob.imss.ctirss.delta.cobranza.services.business.GenerarTimbradoCFDIServiceBusinessRemote#generaComprobantePago(mx.gob.imss.ctirss.delta.cobranza.dto.PagoReferenciadoDTO)
	 */
	@Override 
	public RespuestaServicioTimbradoCFDI generaComprobantePago(PagoReferenciadoDTO pago) 
			throws ErrorEnGeneracionXMLPagoException, ErrorEnGeneracionComprobanteException, ErrorEnServicioTimbradoException {
		
		LOG.info("########## PETICION AL METODO DEL BACKEND GENERACOMPROBANTE CON RFC ########## "+pago.getRfc());
		
		RespuestaServicioTimbradoCFDI respuestaServicioTimbrado = GeneracionXMLPagoServiceBusiness.procesaComprobanteFiscalXMLTimbrado(pago);
		
		if (respuestaServicioTimbrado.getXmlTimbrado() == null) {
			System.out.println("ERROR: Ocurrio un error y no se logro timbrar.");
			LOG.error("ERROR: Ocurrio un error y no se logro timbrar.");
		}
		return respuestaServicioTimbrado;
	}

	@Override
	public RegistroCFDI[] cancelarPagosTimbrados(RegistroCFDI[] listaFolios) throws ErrorCancelarTimbradoException {
	   //TODO Auto-generated method stub
		
		RegistroCFDI[] listaFoliosCancelados = null;
		ProcesaCancelacionTimbrado cancelaTimbrado = new  ProcesaCancelacionTimbrado();		
        try {
        	listaFoliosCancelados = cancelaTimbrado.generaXMLCancelacionDeFolios(listaFolios);
		} catch (ErrorEnCancelacionFolioTimbradoException e) {
			e.printStackTrace();
			throw new ErrorCancelarTimbradoException("Error al en cancelar pagos timbrados [" + e.getMessage() + "]");
		}
	
        return listaFoliosCancelados;
	}
	
	@Override
	public LoteCFDI[] cancelarPagosTimbradosPorLote(LoteCFDI[] lotesFolios) throws ErrorCancelarTimbradoException {
		LOG.info("########## INICIA EL PROCESO DE CANCELACION POR LOTES DE TIMBRADO ##########");
		LOG.info("########## TAMAÑO DE LA LISTA DE LOTES A CANCELAR ########## "+lotesFolios.length);
		RegistroCFDI[] listaFoliosCancelados = null;
		List<RegistroCFDI[]> listLotes =  new ArrayList<RegistroCFDI[]>();
		LoteCFDI[] lotes = null;
		ProcesaCancelacionTimbrado cancelaTimbrado = new  ProcesaCancelacionTimbrado();		
        try {
        	for(int i = 0; i < lotesFolios.length; i++ ) {
        		LOG.info("########## NUMERO DE LOTE: " + i + " TAMAÑO DE FOLIOS: " +lotesFolios[i].getRegistros().length +" ##########");
//        		listaFoliosCancelados = cancelaTimbrado.generaXMLCancelacionDeFolios(lotesFolios[i].getRegistros());
        		listaFoliosCancelados = cancelaTimbrado.generaXMLCancelacionDeFoliosNuevaImplementacion(lotesFolios[i].getRegistros());
        		listLotes.add(listaFoliosCancelados);
        	}
        	lotes =Utilerias.convierteListEnArreglo(listLotes);
        	LOG.info("########## FINALIZA PROCESO DE CANCELACION POR LOTES DE TIMBRADO ##########");
		} catch (ErrorEnCancelacionFolioTimbradoException e) {
			e.printStackTrace();
			throw new ErrorCancelarTimbradoException("########## ERROR AL CANCELAR LOS PAGOS TIMBRADOS ########## [" + e.getMessage() + "]");
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
        return lotes;
	}

	@Override
	public RespuestaCancelacionCfdi solicitarCancelacionCfdi(String uuid) {
		LOG.info("########## INICIA SOLICITUD DE CANCELACION DE CFDI PARA EL UUID: " + uuid + " ##########");
		
		RespuestaCancelacionCfdi respuestaCancelacionCfdi = null;
		ProcesaCancelacionTimbrado cancelaTimbrado = new  ProcesaCancelacionTimbrado();
		
		respuestaCancelacionCfdi = cancelaTimbrado.solicitarCancelacionCfdiPorUuid(uuid);
		LOG.info("########## FINALIZA SOLICITUD DE CANCELACION DE CFDI PARA EL UUID: " + uuid + " ##########");
		return respuestaCancelacionCfdi;
	}

}
