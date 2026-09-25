                          
package mx.gob.imss.service.pagoscfdi.implementacion;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceComprobanteFiscalException;
import mx.gob.imss.ctirss.delta.model.gestion.cobranza.cfdi.Pago;
import mx.gob.imss.service.pagoscfdi.MessageWSConsPagosCFDIRegPatron;
import mx.gob.imss.service.pagoscfdi.PagosCFDIRegitroPatronal;
import mx.gob.imss.service.pagoscfdi.Response;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;

public class ClienteWebserviceComprobanteFiscal {
	Logger log = Logger.getLogger(ClienteWebserviceComprobanteFiscal.class);
	private static int serviceTimeOut = 30000;

	public List<Pago> obtenerComprobanteFiscal(String periodo, String numeroRegistroPatronal)
			throws ClienteWebserviceComprobanteFiscalException {
		List<Pago> listaPago = new ArrayList<Pago>();

		MessageWSConsPagosCFDIRegPatron parametros = new MessageWSConsPagosCFDIRegPatron();
		parametros.setPeriodo(Integer.valueOf(periodo));
		parametros.setRegistroPatronal(numeroRegistroPatronal);

		Response obtienePago = obtienePagoCFDI(parametros);
		if(obtienePago!=null && obtienePago.getListaInfoPagosCFDIRegPatronVO()!=null){
			
			List<PagosCFDIRegitroPatronal> listaCFDI =	obtienePago.getListaInfoPagosCFDIRegPatronVO().getInfoPagosCFDIRegPatronVO();
			
			if(!CollectionUtils.isEmpty(listaCFDI)){
				for(PagosCFDIRegitroPatronal comprobanteFiscal : listaCFDI){
					// Datos VO
					Pago pago = new Pago();
					pago.setRfc(comprobanteFiscal.getRfc().getValue());
					pago.setNumeroRegistroPatronal(comprobanteFiscal.getNrp().getValue());
					pago.setFolioSua(comprobanteFiscal.getFolSua().getValue().toString());
					pago.setPeriodo(comprobanteFiscal.getPeriodo().getValue().toString());
					BigDecimal subTotalIMSS = new BigDecimal("0.00");
					if(comprobanteFiscal.getSubTotIMSS()!=null){
						String valor = comprobanteFiscal.getSubTotIMSS().getValue();
						valor=valor.replaceAll(",", ".");
						subTotalIMSS = new BigDecimal(valor);
					}
					pago.setSubTotal(Double.valueOf(subTotalIMSS.doubleValue()));
					pago.setNombreRazonSocial(comprobanteFiscal.getNombre().getValue());

					String strFechaPago = comprobanteFiscal.getFechaPago().getValue();
					pago.setStrFechaPago(strFechaPago);

					if (StringUtils.isNotBlank(strFechaPago)) {
						try {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
							Date fechaPago = sdf.parse(strFechaPago);
							pago.setFechaPago(fechaPago);
						} catch (ParseException e) {
							log.error("Formato de fecha de pago invalida. Se mandara vacia");
						}
					}

					// Datos XML
					String xmlComprobante = comprobanteFiscal.getCfdiXml().getValue();
					pago.setXmlComprobante(xmlComprobante);
					pago.setEntidadRecaudadora(comprobanteFiscal.getEntidadRecaudadora().getValue().intValue());
					listaPago.add(pago);
				}
			}
		}
		return listaPago;
	}

	private Response obtienePagoCFDI(MessageWSConsPagosCFDIRegPatron request)
			throws ClienteWebserviceComprobanteFiscalException {
		Response response = null;

		try {
			this.log.debug("WebserviceComprobanteFiscal. Entrada -> Periodo:" + request.getPeriodo() + " y RP: " + request.getRegistroPatronal());
			this.log.debug("WebserviceComprobanteFiscal. Se ejecuta el thread del Cliente. " + new Date());

			DatosWebserviceComprobanteFiscal thread = new DatosWebserviceComprobanteFiscal();
			thread.setRequest(request);
			thread.start();
			
			this.log.debug("WebserviceComprobanteFiscal. Se establece el tiempo del timeout del thread = " + serviceTimeOut);
			thread.join(serviceTimeOut);
			this.log.debug("WebserviceComprobanteFiscal. Se recupera el control del proceso desde el thread. " + new Date());

			// VERIFICA SI SE GENERO UN ERROR EN EL THREAD
			if (thread.isExisteErrorServicio()) {
				throw new ClienteWebserviceComprobanteFiscalException();
			}

			// VERIFICAMOS EN QUE CONDICIONES SE HA FINALIZADO EL THREAD
			if (thread.isAlive()) {
				this.log.debug("WebserviceComprobanteFiscal. El thread sigue esperando la respuesa de RENAPO");
				thread.interrupt();
				this.log.debug("WebserviceComprobanteFiscal. El thread se ha interrumpido y se generara un ClienteWebserviceImssRissException");
				throw new ClienteWebserviceComprobanteFiscalException();
			} else {
				this.log.debug("WebserviceComprobanteFiscal. El thread termino satisfactoriamente las validaciones del RISS dentro del timeout especificado");
				response = thread.getResponse();

				if (response.getCodigoError() != null && response.getCodigoError() != 0) {
					throw new ClienteWebserviceComprobanteFiscalException(response.getMensajeError());
				} 
			}
		} catch (ClienteWebserviceComprobanteFiscalException e) {
			throw new ClienteWebserviceComprobanteFiscalException(e.getMessage());
		} catch (Exception e) {
			this.log.debug("WebserviceComprobanteFiscal. Se genero un error al accesar el webservice");
			throw new ClienteWebserviceComprobanteFiscalException();
		}

		this.log.debug("La respuesta de obtencion del CFDI es -> " + response);
		return response;
	}
}
