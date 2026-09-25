package mx.gob.imss.ctirss.delta.cobranza.service.business;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.ctirss.autopac.cancelacion.cfdi.ClienteAutoPacCancelacionCfdi;
import mx.gob.imss.ctirss.autopac.modelo.PeticionConsultaCfdiDto;
import mx.gob.imss.ctirss.autopac.modelo.RespuestaConsultaCfdiDto;
import mx.gob.imss.ctirss.delta.cobranza.dto.PagoReferenciadoDTO;
import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorEnGeneracionComprobanteException;
import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorEnGeneracionXMLPagoException;
import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorEnServicioTimbradoException;
import mx.gob.imss.ctirss.delta.cobranza.respuesta.RespuestaCancelacionCfdi;
import mx.gob.imss.ctirss.delta.cobranza.respuesta.RespuestaServicioTimbradoCFDI;
import mx.gob.imss.ctirss.delta.cobranza.services.business.GenerarTimbradoCFDIServiceBusinessRemote;

public class GenerarTimbradoCFDIServiceBusinessTest {
	
	private static final Logger log = LoggerFactory.getLogger(GenerarTimbradoCFDIServiceBusinessTest.class);
	
	private GenerarTimbradoCFDIServiceBusinessRemote generarTimbradoCFDIServiceBusinessRemote;
	
	private void iniciarServicioGenerarTimbradoCFDIServiceBusinessRemoteLocal() {
		Object object = EJBLocator.getContextoLocal("generarTimbradoCFDIServiceBusiness#mx.gob.imss.ctirss.delta.cobranza.services.business.GenerarTimbradoCFDIServiceBusinessRemote");
		generarTimbradoCFDIServiceBusinessRemote = (GenerarTimbradoCFDIServiceBusinessRemote) object;
	}
	
	private void iniciarServicioGenerarTimbradoCFDIServiceBusinessRemoteStage() {
		Object object = EJBLocator.getContextoStage("generarTimbradoCFDIServiceBusiness#mx.gob.imss.ctirss.delta.cobranza.services.business.GenerarTimbradoCFDIServiceBusinessRemote");
		generarTimbradoCFDIServiceBusinessRemote = (GenerarTimbradoCFDIServiceBusinessRemote) object;
	}
	
//	@Test
	public void generaComprobantePagoTestLocal() {
		iniciarServicioGenerarTimbradoCFDIServiceBusinessRemoteLocal();
		
		log.info("########## Inicia el test de generaComprobantePagoTest ##########");
        
        PagoReferenciadoDTO pagoReferenciadoDTO = new PagoReferenciadoDTO();
        pagoReferenciadoDTO.setRfc("BAJF541014RB3");
        pagoReferenciadoDTO.setRazonSocial("BERTHA MARIA MATA GUZMAN");
        pagoReferenciadoDTO.setPeriodo("201709");
        pagoReferenciadoDTO.setFolioSUA(new BigDecimal("31125"));
        pagoReferenciadoDTO.setSubTotalIMSS(new BigDecimal("1867.91"));
        pagoReferenciadoDTO.setRecIMMS(new BigDecimal("10"));
        pagoReferenciadoDTO.setActIMSS(new BigDecimal("20"));
        pagoReferenciadoDTO.setSubTotRCV(new BigDecimal("8100.05"));
        pagoReferenciadoDTO.setRecRCV(new BigDecimal("30"));
        pagoReferenciadoDTO.setActRCV(new BigDecimal("40"));
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    	Date date = null;
		try {
			date = formatter.parse("16/01/2016");
		} catch (ParseException e) {
			e.printStackTrace();
		}
        
        pagoReferenciadoDTO.setFecPago(date);
        pagoReferenciadoDTO.setEntidadRecaudadora("02");
        pagoReferenciadoDTO.setRegistroPatronal("A8078000107");
        
        pagoReferenciadoDTO.setFecIngreso("20160428");
        pagoReferenciadoDTO.setEstatusRegistro("I");
        pagoReferenciadoDTO.setFormaPago("01");
        pagoReferenciadoDTO.setTipoRelacion("04");
        pagoReferenciadoDTO.setUuid("2fd826af-a975-4b24-9b3d-d752c0d90b5d");
        
        try {
        	RespuestaServicioTimbradoCFDI respuestaServicioTimbradoCFDI = generarTimbradoCFDIServiceBusinessRemote.generaComprobantePago(pagoReferenciadoDTO);
			
			if (respuestaServicioTimbradoCFDI != null) {
				log.info("########## XML TIMBRADO ##########\n\n"+respuestaServicioTimbradoCFDI.getXmlTimbrado());
				log.info("########## CODIGO ESTATUS:" + respuestaServicioTimbradoCFDI.getAcuseRespuestaTimbrado().getCodeStatus() +" ##########");
			}
		} catch (ErrorEnServicioTimbradoException ex) {
			ex.printStackTrace();
		} catch (ErrorEnGeneracionXMLPagoException ex) {
			ex.printStackTrace();
		} catch (ErrorEnGeneracionComprobanteException ex) {
			ex.printStackTrace();
		}
        log.info("########## Termina el test de generaComprobantePagoTest ##########");
	}
	
//	@Test
	public void generaComprobantePagoTestStage() {
		iniciarServicioGenerarTimbradoCFDIServiceBusinessRemoteStage();
		
		log.info("########## Inicia el test de generaComprobantePagoTest ##########");
        
        PagoReferenciadoDTO pagoReferenciadoDTO = new PagoReferenciadoDTO();
        pagoReferenciadoDTO.setRfc("BAJF541014RB3");
        pagoReferenciadoDTO.setRazonSocial("BERTHA MARIA MATA GUZMAN");
        pagoReferenciadoDTO.setPeriodo("201709");
        pagoReferenciadoDTO.setFolioSUA(new BigDecimal("31125"));
        pagoReferenciadoDTO.setSubTotalIMSS(new BigDecimal("1867.91"));
        pagoReferenciadoDTO.setRecIMMS(new BigDecimal("10"));
        pagoReferenciadoDTO.setActIMSS(new BigDecimal("20"));
        pagoReferenciadoDTO.setSubTotRCV(new BigDecimal("8100.05"));
        pagoReferenciadoDTO.setRecRCV(new BigDecimal("30"));
        pagoReferenciadoDTO.setActRCV(new BigDecimal("40"));
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    	Date date = null;
		try {
			date = formatter.parse("16/01/2016");
		} catch (ParseException e) {
			e.printStackTrace();
		}
        
        pagoReferenciadoDTO.setFecPago(date);
        pagoReferenciadoDTO.setEntidadRecaudadora("02");
        pagoReferenciadoDTO.setRegistroPatronal("A8078000107");
        
        pagoReferenciadoDTO.setFecIngreso("20160428");
        pagoReferenciadoDTO.setEstatusRegistro("I");
        
        try {
        	RespuestaServicioTimbradoCFDI respuestaServicioTimbradoCFDI = generarTimbradoCFDIServiceBusinessRemote.generaComprobantePago(pagoReferenciadoDTO);
			
			if (respuestaServicioTimbradoCFDI != null) {
				log.info("########## XML TIMBRADO ##########\n\n"+respuestaServicioTimbradoCFDI.getXmlTimbrado());
				log.info("########## CODIGO ESTATUS:" + respuestaServicioTimbradoCFDI.getAcuseRespuestaTimbrado().getCodeStatus() +" ##########");
			}
		} catch (ErrorEnServicioTimbradoException ex) {
			ex.printStackTrace();
		} catch (ErrorEnGeneracionXMLPagoException ex) {
			ex.printStackTrace();
		} catch (ErrorEnGeneracionComprobanteException ex) {
			ex.printStackTrace();
		}
        log.info("########## Termina el test de generaComprobantePagoTest ##########");
	}
	
	@Test
	public void solictarCancelacionCfdiTestLocal() {
		iniciarServicioGenerarTimbradoCFDIServiceBusinessRemoteLocal();
		
		log.info("########## Inicia el test de solicitarCancelacionCfdi ##########");
		
		String uuid = "CE7E97DA-5485-4464-B7D4-215FEE458981";
		
		RespuestaCancelacionCfdi respuestaCancelacionCfdi;
		
		respuestaCancelacionCfdi = generarTimbradoCFDIServiceBusinessRemote.solicitarCancelacionCfdi(uuid);
		
		if (respuestaCancelacionCfdi != null) {
			log.info("########## LA RESPUESTA DE LA CANCELACION DE CFDI ES CORRECTA ##########");
		} else {
			log.info("########## LA RESPUESTA DE LA CANCELACION DE CFDI ES NULA ##########");
		}
		
	}
	
	@Test
	public void solictarEstatusCancelacionCfdiTestLocal() throws MalformedURLException, RemoteException {
		
		
		log.info("########## Inicia el test de solictarEstatusCancelacionCfdiTestLocal ##########");
		
		
		ClienteAutoPacCancelacionCfdi clienteAutoPacCancelacionCfdi = new ClienteAutoPacCancelacionCfdi();
		PeticionConsultaCfdiDto peticionConsultaCfdiDto =  new PeticionConsultaCfdiDto();
		peticionConsultaCfdiDto.setMonto("10");
		peticionConsultaCfdiDto.setUuid("CE7E97DA-5485-4464-B7D4-215FEE458981");
		peticionConsultaCfdiDto.setRfcEmisor("WAL99092955A");
		peticionConsultaCfdiDto.setRfcReceptor("HSE580109DW8");
		
		
		RespuestaConsultaCfdiDto invocarServicioAutoPacConsultaEstatusCfdi = clienteAutoPacCancelacionCfdi.invocarServicioAutoPacConsultaEstatusCfdi(peticionConsultaCfdiDto);
		
		if (invocarServicioAutoPacConsultaEstatusCfdi != null) {
			log.info("########## LA RESPUESTA DE LA CANCELACION DE CFDI ES CORRECTA ##########");			
		} else {
			log.info("########## LA RESPUESTA DE LA CANCELACION DE CFDI ES NULA ##########");
		}
		
	}

}
