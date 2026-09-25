package mx.gob.imss.ctirss.delta.cobranza.service.business;

import java.math.BigDecimal;
import java.util.Date;

import mx.gob.imss.ctirss.delta.cobranza.dto.PagoReferenciadoDTO;
import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorEnGeneracionComprobanteException;
import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorEnGeneracionXMLPagoException;
import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorEnServicioTimbradoException;
import mx.gob.imss.ctirss.delta.cobranza.respuesta.RespuestaServicioTimbradoCFDI;

public class TimbrarService {

	public static void main(String[] args) {
		
		System.out.println("***************** Iniciando Generacion XML  ********************");
        PagoReferenciadoDTO pagoReferenciadoDTO = new PagoReferenciadoDTO();
        pagoReferenciadoDTO.setRegistroPatronal("01047525108");
        pagoReferenciadoDTO.setRfc("HSE580109DW8");      
        pagoReferenciadoDTO.setPeriodo("201406");
        pagoReferenciadoDTO.setFolioSUA(BigDecimal.ZERO);
        pagoReferenciadoDTO.setSubTotalIMSS(BigDecimal.TEN);
        pagoReferenciadoDTO.setRecIMMS(BigDecimal.ZERO);
        pagoReferenciadoDTO.setActIMSS(BigDecimal.ZERO);
        pagoReferenciadoDTO.setSubTotRCV(BigDecimal.ZERO);
        pagoReferenciadoDTO.setRecRCV(BigDecimal.ZERO);
        pagoReferenciadoDTO.setActRCV(BigDecimal.ZERO);
        pagoReferenciadoDTO.setFecPago(new Date());
        pagoReferenciadoDTO.setEntidadRecaudadora("02");
        pagoReferenciadoDTO.setFecIngreso("20140616");
        pagoReferenciadoDTO.setEstatusRegistro("I");
        pagoReferenciadoDTO.setTipoRelacion("0");;
        
             
        try {
//        	ComprobanteDTO comprobante = GeneracionXMLPagoServiceBusiness.generaComprobantePago(pagoReferenciadoDTO);
			String xml="";
			try {
				RespuestaServicioTimbradoCFDI respuestaServicioTimbrado  = GeneracionXMLPagoServiceBusiness.procesaComprobanteFiscalXMLTimbrado(pagoReferenciadoDTO);
				System.out.println("XML :::." + xml);
			} catch (ErrorEnServicioTimbradoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (ErrorEnGeneracionXMLPagoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ErrorEnGeneracionComprobanteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
