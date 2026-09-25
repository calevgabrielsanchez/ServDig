/**
 * 
 */
package mx.gob.imss.ctirss.delta.cobranza.service.business;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import mx.gob.imss.ctirss.delta.cobranza.dto.ComprobanteDTO;
import mx.gob.imss.ctirss.delta.cobranza.dto.PagoReferenciadoDTO;
import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorCancelarTimbradoException;
import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorEnCancelacionFolioTimbradoException;
import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorEnGeneracionComprobanteException;
import mx.gob.imss.ctirss.delta.cobranza.exception.ErrorEnGeneracionXMLPagoException;
import mx.gob.imss.ctirss.delta.cobranza.model.LoteCFDI;
import mx.gob.imss.ctirss.delta.cobranza.model.RegistroCFDI;
//import mx.gob.imss.ctirss.delta.cobranza.model.RegistroCFDI;
import mx.gob.imss.ctirss.delta.cobranza.respuesta.RespuestaServicioTimbradoCFDI;
import mx.gob.imss.ctirss.delta.cobranza.services.business.GenerarTimbradoCFDIServiceBusinessRemote;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.xml.ws.transport.http.client.HttpTransportPipe;

/**
 * @author vanderluk
 *
 */
public class GeneracionPagosServiceUtilityTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}
	

	private static final Logger LOG;
	
	static {
	   LOG = LoggerFactory.getLogger(GeneracionPagosServiceUtilityTest.class);
	}

	/**
	 * Test method for {@link mx.gob.imss.ctirss.delta.cobranza.service.utility.GeneracionPagoServiceUtility#getXMLPago(mx.gob.imss.ctirss.delta.cobranza.model.Pago)}.
	 */
	/*@Test
	public void testGetXMLPago() {
		
		
		Pago pago = new Pago();
		
		
		pago.setNrp("ABC55555101");
		pago.setRfc("ABCD999999AA1");
		
		GeneracionPagoServiceUtility utility = new GeneracionPagoServiceUtility();
		try {
			String xml = utility.getXMLPago(pago);
			
			System.out.println("XML generado del pago : " + xml);
			
			
		} catch (ErrorEnGeneracionXMLPagoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace()
		}
		
		
	}*/
	
//	@Test
//	public void testCiclo() {
//		for (int i = 1; i < 11; i++) {
//			System.out.println();
//			System.out.println("################################################################################################ " + i + " ################################################################################################");
//			System.out.println();
//			testGetXMLPago();
//		}
//	}
	
//	/**
//	* Metodo de prueba para iniciar el proceso para la generacion del XML
//	* @param
//	* @return
//	* @throws 
//	*/
	
	@Test
	public void testGetXMLPago(){
		HttpTransportPipe.dump = true;
        System.out.println("***************** Iniciando Generacion XML  ********************");
        LOG.info("***************** Iniciando Generacion XML  ********************");
        
        PagoReferenciadoDTO pagoReferenciadoDTO = new PagoReferenciadoDTO();
        pagoReferenciadoDTO.setRfc("BAJF541014RB3");
        pagoReferenciadoDTO.setRazonSocial("BERTHA MARIA MATA GUZMAN");
        pagoReferenciadoDTO.setPeriodo("201709");
        pagoReferenciadoDTO.setFolioSUA(new BigDecimal("31125"));
        pagoReferenciadoDTO.setSubTotalIMSS(new BigDecimal("1000"));
        pagoReferenciadoDTO.setRecIMMS(new BigDecimal("1000"));
        pagoReferenciadoDTO.setActIMSS(new BigDecimal("999"));
        pagoReferenciadoDTO.setSubTotRCV(new BigDecimal("2000"));
        pagoReferenciadoDTO.setRecRCV(new BigDecimal("500"));
        pagoReferenciadoDTO.setActRCV(new BigDecimal("8333"));
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    	Date date = null;
		try {
			date = formatter.parse("23/03/2016");
		} catch (ParseException e) {
			e.printStackTrace();
		}
        
        pagoReferenciadoDTO.setFecPago(date);
        pagoReferenciadoDTO.setEntidadRecaudadora("02");
        pagoReferenciadoDTO.setRegistroPatronal("A8078000107");
        
        pagoReferenciadoDTO.setFecIngreso("20160428");
        pagoReferenciadoDTO.setEstatusRegistro("I");
        
        try {
        	ComprobanteDTO comprobante = GeneracionXMLPagoServiceBusiness.generaComprobantePago(pagoReferenciadoDTO);
			String xml = GeneracionXMLPagoServiceBusiness.generaComprobantePagoXML(comprobante);
			System.out.println("XML :::.");
			System.out.println(xml);
			try {
				RespuestaServicioTimbradoCFDI respuestaServicioTimbrado = GeneracionXMLPagoServiceBusiness.timbraComprobanteFiscal(xml);
				if (respuestaServicioTimbrado != null) {
					System.out.println("XML TIMBRADO::");
					System.out.println(respuestaServicioTimbrado.getXmlTimbrado());
					System.out.println("CODIGO ESTATUS ::" + respuestaServicioTimbrado.getAcuseRespuestaTimbrado().getCodeStatus());
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} catch (ErrorEnGeneracionXMLPagoException ex) {
			ex.printStackTrace();
		} catch (ErrorEnGeneracionComprobanteException ex) {
			ex.printStackTrace();
		}
    }
	
//	@Test
//	public void testXMLProcesaCancelacion() {
//		try {
//			/* Folios de prueba
//			   folio.setUUID("3B9D3F1A-1B53-4D2B-A21C-B148166E8D43");
//	           folio1.setUUID("3B9D3F1A-1B53-4D2B-A21C-B148166E8D43");*/
//			
//			RegistroCFDI[] listaFolios = new RegistroCFDI[1];
//	        RegistroCFDI regCFDI = new RegistroCFDI();
////	        regCFDI.setUuid("85455f8d-26e3-4a63-9f19-542dedde1481");
//	        regCFDI.setUuid("4ac350a3-6a55-467e-a633-6fca68462bff");
////	        RegistroCFDI regCFDI1 = new RegistroCFDI();
////	        regCFDI1.setUuid("3B9D3F1A-1B53-4D2B-A21C-B148166E8D43");
//	        listaFolios[0] = regCFDI;
////	        listaFolios[1] = regCFDI1;
//	        
//	        ProcesaCancelacionTimbrado cancelaTimbrado = new  ProcesaCancelacionTimbrado();
//        
//			cancelaTimbrado.generaXMLCancelacionDeFolios(listaFolios);
//		} catch (ErrorEnCancelacionFolioTimbradoException ex) {
//			ex.printStackTrace();
//		}
//	}
	
//	@Test
//	public void testCancelacionPorLotes() {
//		try {
//		/* Folios de prueba
//		   folio.setUUID("3B9D3F1A-1B53-4D2B-A21C-B148166E8D43");
//           folio1.setUUID("3B9D3F1A-1B53-4D2B-A21C-B148166E8D43");*/
//		
//		RegistroCFDI[] listaFolios = new RegistroCFDI[39];
//        RegistroCFDI regCFDI_1 = new RegistroCFDI();
//        regCFDI_1.setUuid("3de0ce6b-4413-4940-a1a2-47917129b406");
//        
//        RegistroCFDI regCFDI_2 = new RegistroCFDI();
//        regCFDI_2.setUuid("b4b1a976-ea03-4e7b-83ea-7a10362f1b76");
//        
//        RegistroCFDI regCFDI_3 = new RegistroCFDI();
//        regCFDI_3.setUuid("88b15259-89f3-4d0c-9e5d-0d5469b86dd5");
//        
//        RegistroCFDI regCFDI_4 = new RegistroCFDI();
//        regCFDI_4.setUuid("7da2a580-e7f5-49bc-a5d8-dc3dbe6c942e");
//        
//        RegistroCFDI regCFDI_5 = new RegistroCFDI();
//        regCFDI_5.setUuid("2cc3a2b7-91b8-4e81-a1f5-e2b5c405ad81");
//        
//        RegistroCFDI regCFDI_6 = new RegistroCFDI();
//        regCFDI_6.setUuid("3e3e0580-2e2a-4acd-b4c5-c986cc16055c");
//        
//        RegistroCFDI regCFDI_7 = new RegistroCFDI();
//        regCFDI_7.setUuid("68ac4054-a284-4f09-a90a-8f1a933e7f3c");
//        
//        RegistroCFDI regCFDI_8 = new RegistroCFDI();
//        regCFDI_8.setUuid("ef312b1c-2e87-4dd4-a88e-71e653e9ab3d");
//        
//        RegistroCFDI regCFDI_9 = new RegistroCFDI();
//        regCFDI_9.setUuid("9ca30c35-63c7-4c27-9a74-33598932d8be");
//        
//        RegistroCFDI regCFDI_10 = new RegistroCFDI();
//        regCFDI_10.setUuid("a2575c69-a87f-475d-afc2-99528c0438fb");
//        
//        RegistroCFDI regCFDI_11 = new RegistroCFDI();
//        regCFDI_11.setUuid("2fc8b91e-6660-4d28-b5d1-5e8bce0613c3");
//        
//        RegistroCFDI regCFDI_12 = new RegistroCFDI();
//        regCFDI_12.setUuid("c4c1f9ed-b48d-4a15-9c58-e88ec4e69e38");
//        
//        RegistroCFDI regCFDI_13 = new RegistroCFDI();
//        regCFDI_13.setUuid("91f1e007-9a42-4075-bcbd-eb8bd4562623");
//        
//        RegistroCFDI regCFDI_14 = new RegistroCFDI();
//        regCFDI_14.setUuid("64e5afcb-1557-4b68-a74b-b830235529c4");
//        
//        RegistroCFDI regCFDI_15 = new RegistroCFDI();
//        regCFDI_15.setUuid("dceb7644-1b14-4827-976f-dc77185b33fe");
//        
//        RegistroCFDI regCFDI_16 = new RegistroCFDI();
//        regCFDI_16.setUuid("77722890-8b6d-431d-87b7-fd05b422fb89");
//        
//        RegistroCFDI regCFDI_17 = new RegistroCFDI();
//        regCFDI_17.setUuid("9cded4a9-3924-4033-845e-715296d2d32f");
//        
//        RegistroCFDI regCFDI_18 = new RegistroCFDI();
//        regCFDI_18.setUuid("62c9de64-0c3e-41f3-871e-91a14c16db01");
//        
//        RegistroCFDI regCFDI_19 = new RegistroCFDI();
//        regCFDI_19.setUuid("081e52bb-3e07-42f2-82bf-f13fb28a8900");
//        
//        RegistroCFDI regCFDI_20 = new RegistroCFDI();
//        regCFDI_20.setUuid("28dd72f1-965a-473a-a51f-24754fb75c91");
//        
//        RegistroCFDI regCFDI_21 = new RegistroCFDI();
//        regCFDI_21.setUuid("2ea81f61-4053-4af0-9970-293830306bd0");
//        
//        RegistroCFDI regCFDI_22 = new RegistroCFDI();
//        regCFDI_22.setUuid("2ff4d034-2a3b-4ef8-950c-d3fa5026ae20");
//        
//        RegistroCFDI regCFDI_23 = new RegistroCFDI();
//        regCFDI_23.setUuid("f08f002d-8458-48f8-8039-36d2903dbcc3");
//        
//        RegistroCFDI regCFDI_24 = new RegistroCFDI();
//        regCFDI_24.setUuid("6f1439b8-596b-4bcf-8864-a096ecfc02d8");
//        
//        RegistroCFDI regCFDI_25 = new RegistroCFDI();
//        regCFDI_25.setUuid("a8876a98-3327-4395-9896-7a5af1236c50");
//        
//        RegistroCFDI regCFDI_26 = new RegistroCFDI();
//        regCFDI_26.setUuid("a37717fc-ed47-47f4-be08-ebe01aa2ad08");
//        
//        RegistroCFDI regCFDI_27 = new RegistroCFDI();
//        regCFDI_27.setUuid("d2998ae4-5441-4f83-abd7-04bb23c922c2");
//        
//        RegistroCFDI regCFDI_28 = new RegistroCFDI();
//        regCFDI_28.setUuid("d1202726-c853-419d-afb0-bc709bee3c2d");
//        
//        RegistroCFDI regCFDI_29 = new RegistroCFDI();
//        regCFDI_29.setUuid("bd79db6f-181b-4675-b3b7-da96e5d014c2");
//        
//        RegistroCFDI regCFDI_30 = new RegistroCFDI();
//        regCFDI_30.setUuid("9e979927-c5e6-4f5c-9b8c-3c603f2bfb2c");
//        
//        RegistroCFDI regCFDI_31 = new RegistroCFDI();
//        regCFDI_31.setUuid("22e88f57-3b74-4569-b1a4-84d331c7bec0");
//        
//        RegistroCFDI regCFDI_32 = new RegistroCFDI();
//        regCFDI_32.setUuid("aeb46abc-abf8-45f6-84d3-0fa41d09a1b9");
//        
//        RegistroCFDI regCFDI_33 = new RegistroCFDI();
//        regCFDI_33.setUuid("0f219933-e2de-4f6a-a059-bdb49a4e506b");
//        
//        RegistroCFDI regCFDI_34 = new RegistroCFDI();
//        regCFDI_34.setUuid("303420f0-6e86-4b76-81aa-00c48a1e2d75");
//        
//        RegistroCFDI regCFDI_35 = new RegistroCFDI();
//        regCFDI_35.setUuid("ac18bd77-08dc-4bc3-920b-aa8c69c92f0b");
//        
//        RegistroCFDI regCFDI_36 = new RegistroCFDI();
//        regCFDI_36.setUuid("8b729928-8dd3-49fb-a948-a4a3862a3604");
//        
//        RegistroCFDI regCFDI_37 = new RegistroCFDI();
//        regCFDI_37.setUuid("d69bce5a-6cd7-4a96-8ad9-be19c2baca68");
//        
//        RegistroCFDI regCFDI_38 = new RegistroCFDI();
//        regCFDI_38.setUuid("c5c3cb90-38b0-410f-a5cb-89633fadc85c");
//        
//        RegistroCFDI regCFDI_39 = new RegistroCFDI();
//        regCFDI_39.setUuid("c4abe8c3-61b3-40a6-a38c-2e4bba73616a");
//        
//        listaFolios[0] = regCFDI_1;
//        listaFolios[1] = regCFDI_2;
//        listaFolios[2] = regCFDI_3;
//        listaFolios[3] = regCFDI_4;
//        listaFolios[4] = regCFDI_5;
//        listaFolios[5] = regCFDI_6;
//        listaFolios[6] = regCFDI_7;
//        listaFolios[7] = regCFDI_8;
//        listaFolios[8] = regCFDI_9;
//        listaFolios[9] = regCFDI_10;
//        listaFolios[10] = regCFDI_11;
//        listaFolios[11] = regCFDI_12;
//        listaFolios[12] = regCFDI_13;
//        listaFolios[13] = regCFDI_14;
//        listaFolios[14] = regCFDI_15;
//        listaFolios[15] = regCFDI_16;
//        listaFolios[16] = regCFDI_17;
//        listaFolios[17] = regCFDI_18;
//        listaFolios[18] = regCFDI_19;
//        listaFolios[19] = regCFDI_20;
//        listaFolios[20] = regCFDI_21;
//        listaFolios[21] = regCFDI_22;
//        listaFolios[22] = regCFDI_23;
//        listaFolios[23] = regCFDI_24;
//        listaFolios[24] = regCFDI_25;
//        listaFolios[25] = regCFDI_26;
//        listaFolios[26] = regCFDI_27;
//        listaFolios[27] = regCFDI_28;
//        listaFolios[28] = regCFDI_29;
//        listaFolios[29] = regCFDI_30;
//        listaFolios[30] = regCFDI_31;
//        listaFolios[31] = regCFDI_32;
//        listaFolios[32] = regCFDI_33;
//        listaFolios[33] = regCFDI_34;
//        listaFolios[34] = regCFDI_35;
//        listaFolios[35] = regCFDI_36;
//        listaFolios[36] = regCFDI_37;
//        listaFolios[37] = regCFDI_38;
//        listaFolios[38] = regCFDI_39;
//        
//        
//        GenerarTimbradoCFDIServiceBusiness generarTimbradoCFDIServiceBusiness = new GenerarTimbradoCFDIServiceBusiness();
//        
//        LoteCFDI[] listLoteCFDIs = new LoteCFDI[1];
//        LoteCFDI loteCFDI = new LoteCFDI();
//        loteCFDI.setFechaLoteProceso(Calendar.getInstance().getTime());
//        loteCFDI.setRegistros(listaFolios);
//        listLoteCFDIs[0] = loteCFDI;
//        
//        generarTimbradoCFDIServiceBusiness.cancelarPagosTimbradosPorLote(listLoteCFDIs);
//        
//		} catch (ErrorCancelarTimbradoException ex) {
//			ex.printStackTrace();
//		}
//	}
	
//	@Test
//	public void testCancelacion() {
//		try {
//			ClienteAutopacCancelacion clienteAutopacCancelacion = new ClienteAutopacCancelacion();
//			RespuestaCancelacionDto respuestaCancelacionDto = null;
//			
//			String peticionCancelacionTimbrado = "<Cancelacion xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""+
//				"xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" Fecha=\"2016-01-20T13:16:50\""+
//				"RfcEmisor=\"ACA010531133\" xmlns=\"http://cancelacfd.sat.gob.mx\">"+
//				"<Folios>"+
//					"<UUID>C728C984-B871-46CE-BBD3-6267CE09F42D</UUID>"+
//				"</Folios>"+
//				"<Folios>"+
//					"<UUID>3B9D3F1A-1B53-4D2B-A21C-B148166E8D43</UUID>"+
//				"</Folios>"+
//				"<Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\">"+
//					"<SignedInfo xmlns=\"http://www.w3.org/2000/09/xmldsig#\""+
//						"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">"+
//						"<CanonicalizationMethod"+
//							"Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\" />"+
//						"<SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\" />"+
//						"<Reference URI=\"\">"+
//							"<Transforms>"+
//								"<Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\" />"+
//							"</Transforms>"+
//							"<DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\" />"+
//							"<DigestValue>8dimPLrvLIBL46v3PGu6S1Cai0c="+
//							"</DigestValue>"+
//						"</Reference>"+
//					"</SignedInfo>"+
//					"<SignatureValue>YeEuV/p6zue97UvY+7ZFqPk3+QC+OleRZSmubGuTJaLgACXaERq2J+JNSMyyTpiFejugHVc1Dqig"+
//						"9+caiprPCTLzbzA171uFaUeWzoU6R5+AJYb3kG2j9ov3ktAVIDQ4k1PGwOIVZ6mIsR64kozHoZAi"+
//						"k8YsTnBu+PyEgwi3hqU="+
//					"</SignatureValue>"+
//					"<KeyInfo>"+
//						"<X509Data>"+
//							"<X509IssuerSerial>"+
//								"<X509IssuerName>OID.1.2.840.113549.1.9.2=Responsable: Claudia"+
//									"Covarrubias Ochoa, OID.2.5.4.45=SAT970701NN3, L=Cuauhtémoc,"+
//									"ST=Distrito Federal, C=MX, OID.2.5.4.17=06300, STREET=\"Av. Hidalgo"+
//									"77, Col. Guerrero\", EMAILADDRESS=acods@sat.gob.mx,"+
//									"OU=Administración de Seguridad de la Información, O=Servicio de"+
//									"Administración Tributaria, CN=A.C. del Servicio de Administración"+
//									"Tributaria</X509IssuerName>"+
//								"<X509SerialNumber>275106190557734483187066766792486680278364205618</X509SerialNumber>"+
//							"</X509IssuerSerial>"+
//							"<X509Certificate>MIIErDCCA5SgAwIBAgIUMDAwMDEwMDAwMDAzMDQ1NDE2MjIwDQYJKoZIhvcNAQEFBQAwggGKMTgwNgYDVQQDDC9BLkMuIGRlbCBTZXJ2aWNpbyBkZSBBZG1pbmlzdHJhY2nDs24gVHJpYnV0YXJpYTEvMC0GA1UECgwmU2VydmljaW8gZGUgQWRtaW5pc3RyYWNpw7NuIFRyaWJ1dGFyaWExODA2BgNVBAsML0FkbWluaXN0cmFjacOzbiBkZSBTZWd1cmlkYWQgZGUgbGEgSW5mb3JtYWNpw7NuMR8wHQYJKoZIhvcNAQkBFhBhY29kc0BzYXQuZ29iLm14MSYwJAYDVQQJDB1Bdi4gSGlkYWxnbyA3NywgQ29sLiBHdWVycmVybzEOMAwGA1UEEQwFMDYzMDAxCzAJBgNVBAYTAk1YMRkwFwYDVQQIDBBEaXN0cml0byBGZWRlcmFsMRQwEgYDVQQHDAtDdWF1aHTDqW1vYzEVMBMGA1UELRMMU0FUOTcwNzAxTk4zMTUwMwYJKoZIhvcNAQkCDCZSZXNwb25zYWJsZTogQ2xhdWRpYSBDb3ZhcnJ1YmlhcyBPY2hvYTAeFw0xNDA2MjcwMDA5NDBaFw0xODA2MjcwMDA5NDBaMIH4MS4wLAYDVQQDEyVJTlNUSVRVVE8gIE1FWElDQU5PIERFTCBTRUdVUk8gU09DSUFMMS4wLAYDVQQpEyVJTlNUSVRVVE8gIE1FWElDQU5PIERFTCBTRUdVUk8gU09DSUFMMS4wLAYDVQQKEyVJTlNUSVRVVE8gIE1FWElDQU5PIERFTCBTRUdVUk8gU09DSUFMMSUwIwYDVQQtExxJTVM0MjEyMzFJNDUgLyBTQUZBNTExMDA2OTc0MR4wHAYDVQQFExUgLyBTQUZBNTExMDA2TURGTFJEMDYxHzAdBgNVBAsTFkNVT1RBUyBPQlJFUk8gUEFUUk9OQUwwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAI1hRQM0DUrYwV7OZaah2ylM+idJ0X3wkA3CuZB4gyACAD616ORbl14zFjd6l8ErNfsbmeFMUL912zLAYNqcxFqexzEOd28YVewIRRDYRLFOxuGzoTtZhQ2vyND51XvV+6ns8/q4wBVRu3dpOO5bUaUC3k962wTJmeVrN75wHSDzAgMBAAGjHTAbMAwGA1UdEwEB/wQCMAAwCwYDVR0PBAQDAgbAMA0GCSqGSIb3DQEBBQUAA4IBAQBP5xNA11NlWxb1t+moCOBBpXCx55Auas/e/jYvi0fFx0U6av7oS2CzNSDD3lBHpXyPWLpQj94w4sRn1wqUx/xLEhU0kOyrTjsW6+Wm6YjrO1RdOiYUSfN1FBusb+pZRCLXhySqTysymDwon5kPA0B9+bz/geCa7X0QDimKf5k378b0k1kXullnPzpiajIfC9bGPE9s91FJHamXloJXm1elAqKxssiWCPR6CebWwDUvVWxeGVhW7uaxNfHoTsjCHBMDTAuNynKEmy5w/wmpuMZkQw5sMq8dhHVevDYV+WbJpSD+vETARHjhISiRBk9AlN0h297/hOpT1ODgqI4s7W6H</X509Certificate>"+
//						"</X509Data>"+
//					"</KeyInfo>"+
//				"</Signature>"+
//			"</Cancelacion>";
//			
//			String strRFC = "ACA010531133";
//		
//			respuestaCancelacionDto = clienteAutopacCancelacion.invocarServicioAutoPacCancelacion(peticionCancelacionTimbrado, strRFC);
//		
//			if (respuestaCancelacionDto != null && 
//					respuestaCancelacionDto.getRespuestaCancelacion() != null && 
//					respuestaCancelacionDto.getRespuestaCancelacion().getAcuseCancelacion() != null) {
//				System.out.println("Respuesta: "+respuestaCancelacionDto.getRespuestaCancelacion().getAcuseCancelacion());
//			}
//		} catch (MalformedURLException ex) {
//			ex.printStackTrace();
//			System.out.println("ERROR MalformedURLException: "+ex.getMessage());
//		} catch (RemoteException ex) {
//			ex.printStackTrace();
//			System.out.println("ERROR RemoteException: "+ex.getMessage());
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			System.out.println("ERROR Exception: "+ex.getMessage());
//		}
//	}

}
