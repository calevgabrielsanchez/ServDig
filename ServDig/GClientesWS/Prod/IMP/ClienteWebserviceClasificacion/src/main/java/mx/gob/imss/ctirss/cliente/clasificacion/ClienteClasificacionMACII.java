package mx.gob.imss.ctirss.cliente.clasificacion;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.ctirss.cliente.clasificacion.dto.InfoConsultaMacII;
import mx.gob.imss.ctirss.cliente.clasificacion.dto.RequestMacII;
import mx.gob.imss.ctirss.cliente.clasificacion.dto.ResponseMacII;
import mx.gob.imss.macii.ws.EntradaMACII;
import mx.gob.imss.macii.ws.InfoMACIIVO;
import mx.gob.imss.macii.ws.RespuestaMACII;
import mx.gob.imss.macii.ws.WSClasificacionMACIIService;

public class ClienteClasificacionMACII {

	public ResponseMacII invocarServicioClasificacionMACII(
			RequestMacII requestMacII) throws MalformedURLException, RemoteException {

		System.out.println("Invocando Servicio WSClasificacionMACIIService...");
		
		ResponseMacII responseMacII;
		RespuestaMACII respuestaMACII = null;
		EntradaMACII entradaMACII = null;

		WSClasificacionMACIIService wSClasificacionMACIIService = new WSClasificacionMACIIService();
		System.out.println("URL del Servicio: "+ wSClasificacionMACIIService.getURLWS());
		entradaMACII = convertDtoToEntradaWs(requestMacII);

		respuestaMACII = wSClasificacionMACIIService.getWSClasificacionMACIIPort().getInfoMACII(entradaMACII);
		
		responseMacII = convertRespuestaWsToDto(respuestaMACII);
		
		System.out.println("Termina invocacion Servicio WSClasificacionMACIIService...");
		
		return responseMacII;
	}
	
	private EntradaMACII convertDtoToEntradaWs(RequestMacII requestMacII) {
		EntradaMACII entradaMACII = null;
		
		if (requestMacII != null) {
			
			System.out.println("Inicia seteo del request DTO al Objeto EntradaMACII del WSClasificacionMACIIService...");
			
			entradaMACII = new EntradaMACII();
			entradaMACII.setNRP(requestMacII.getRegistroPatronal());
			entradaMACII.setFecIniRegistro(requestMacII.getFecIniRegistro());
			entradaMACII.setFecFinRegistro(requestMacII.getFecFinRegistro());
			entradaMACII.setCveTipoPersona(requestMacII.getCveIdTipoPersona());
			entradaMACII.setClaseRectificada(requestMacII.getClaseRectificada());
			entradaMACII.setTipoRegistro(requestMacII.getTipoRegistro());
			entradaMACII.setCveEstatus(requestMacII.getCveIdEstatus());
			entradaMACII.setCveDelegacion(requestMacII.getCveIdDelegacion());
			entradaMACII.setCveSubdelegacion(requestMacII.getCveIdSubdelegacion());
			entradaMACII.setTipoMovimiento(requestMacII.getTipoMovimiento());
			entradaMACII.setTipoTramite(requestMacII.getCveIdTipoTramite());
		} else {
			System.out.println("El Objeto RequestMacII es NULL...");
			return entradaMACII;
		}
		
		System.out.println("Termina seteo del request DTO al Objeto EntradaMACII del WSClasificacionMACIIService...");
		
		return entradaMACII;
	}
	
	private ResponseMacII convertRespuestaWsToDto(RespuestaMACII respuestaMACII) {
		ResponseMacII responseMacII = null;
		List<InfoMACIIVO> listInfoMACIIVOs = null;
		InfoConsultaMacII infoConsultaMacII = null;
		ArrayList<InfoConsultaMacII> listInfoConsultaMacIIs = new ArrayList<InfoConsultaMacII>();
		
		if (respuestaMACII != null) {
			if (respuestaMACII.getInfoMACIIVO() != null && respuestaMACII.getInfoMACIIVO().size() > 0) {
				listInfoMACIIVOs = respuestaMACII.getInfoMACIIVO();
				
				System.out.println("Tamaño del arreglo: "+respuestaMACII.getInfoMACIIVO().size());
				
				System.out.println("Inicia seteo de la respuesta del WS WSClasificacionMACIIService al DTO...");
				
				for (InfoMACIIVO infoMACIIVO : listInfoMACIIVOs) {
					infoConsultaMacII = new InfoConsultaMacII();
					infoConsultaMacII.setRegistroPatronal(infoMACIIVO.getNRP());
					infoConsultaMacII.setNombreRazonSocial(infoMACIIVO.getNombreRs());
					infoConsultaMacII.setCveIdDelegacion(infoMACIIVO.getCveDelegacion());
					infoConsultaMacII.setCveIdSubdelegacion(infoMACIIVO.getCveSubdelegacion());
					infoConsultaMacII.setCveIdMunicipio(infoMACIIVO.getCveMunicipio());
					infoConsultaMacII.setFecRegistro(infoMACIIVO.getFecRegistro());
					infoConsultaMacII.setFecRevision(infoMACIIVO.getFecRevision());
					infoConsultaMacII.setFecMovimiento(infoMACIIVO.getFecMovimiento());
					infoConsultaMacII.setCveIdTipoPersona(infoMACIIVO.getCveTipoPersona());
					infoConsultaMacII.setDesTipoPersona(infoMACIIVO.getTipoPersona());
					infoConsultaMacII.setDesTipoRegistro(infoMACIIVO.getTipoRegistro());
					infoConsultaMacII.setCveIdEstatus(infoMACIIVO.getCveEstatus());
					infoConsultaMacII.setDesEstatus(infoMACIIVO.getEstatus());
					infoConsultaMacII.setClaseDeclarada(infoMACIIVO.getClaseDeclarada());
					infoConsultaMacII.setFraccionDeclarada(infoMACIIVO.getFraccionDeclarada());
					infoConsultaMacII.setPrimaDeclarada(infoMACIIVO.getPrimaDeclarada());
					infoConsultaMacII.setClaseRectificada(infoMACIIVO.getClaseRectificada());
					infoConsultaMacII.setFraccionRectificada(infoMACIIVO.getFraccionRectificada());
					infoConsultaMacII.setPrimaRectificada(infoMACIIVO.getPrimaRectificada());
					infoConsultaMacII.setFolioResolucion(infoMACIIVO.getFolioResolucion());
					infoConsultaMacII.setCveIdCiz(infoMACIIVO.getCveCiz());
					infoConsultaMacII.setSistemaOrigen(infoMACIIVO.getSistemaOrigen());
					infoConsultaMacII.setFecExtraccion(infoMACIIVO.getFecExtraccion());
					infoConsultaMacII.setTipoMovimiento(infoMACIIVO.getTipoMovimiento());
					infoConsultaMacII.setCveIdTipoTramite(infoMACIIVO.getTipoTramite());
					infoConsultaMacII.setDesTipoTramite(infoMACIIVO.getDescTipoTramite());
					
					listInfoConsultaMacIIs.add(infoConsultaMacII);
				}
			} else if (respuestaMACII.getCODIGOERROR() != null && respuestaMACII.getMENSAJEERROR() != null) {
				System.out.println("El Servicio WSClasificacionMACIIService regreso CODIGO DE ERROR...");
				responseMacII = new ResponseMacII();
				responseMacII.setCodigoError(respuestaMACII.getCODIGOERROR().getValue());
				responseMacII.setMensajeError(respuestaMACII.getMENSAJEERROR().getValue());
				responseMacII.setInfoConsultaMacII(listInfoConsultaMacIIs);
				return responseMacII;
			} else {
				System.out.println("El Servicio WSClasificacionMACIIService no regreso CODIGO DE ERROR o MENSAJE DE ERROR...");
				responseMacII = new ResponseMacII();
				responseMacII.setCodigoError(00000);
				responseMacII.setMensajeError("Sin mensaje de error");
				responseMacII.setInfoConsultaMacII(listInfoConsultaMacIIs);
				return responseMacII;
			}
		} else {
			System.out.println("El Servicio WSClasificacionMACIIService regreso el Objeto RespuestaMACII NULL...");
			return responseMacII;
		}
		
		System.out.println("Termina seteo de la respuesta del WS WSClasificacionMACIIService al DTO...");
		
		responseMacII = new ResponseMacII();
		responseMacII.setInfoConsultaMacII(listInfoConsultaMacIIs);
		
		return responseMacII;
	}
}
