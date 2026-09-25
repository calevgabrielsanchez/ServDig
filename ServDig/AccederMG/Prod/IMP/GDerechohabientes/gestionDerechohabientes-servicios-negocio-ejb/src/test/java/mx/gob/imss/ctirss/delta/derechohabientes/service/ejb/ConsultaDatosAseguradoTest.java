package mx.gob.imss.ctirss.delta.derechohabientes.service.ejb;

import org.junit.Test;

//import mx.gob.imss.ctirss.delta.derechohabientes.service.test.EjbLocator;
//import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.ConsultaDatosAseguradoServiceRemote;
//import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.DatosAseguradoDTO;

import mx.gob.imss.ctirss.delta.derechohabientes.service.test.EjbLocator;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.ConsultaDatosAseguradoServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.ConsultarVigenciaServiceRemote;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.DatosAseguradoDTO;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.Respuesta3;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.vigenciaderechos.BeneficiarioDTO;

public class ConsultaDatosAseguradoTest {
	
	@Test
	public void consultaDatosByNSS(){
		
		System.out.println("Test");
		ConsultaDatosAseguradoServiceRemote consultaDatosAseguradoServiceRemote = EjbLocator.getConsultaDatosAseguradoServiceRemote();
		
		try{
			DatosAseguradoDTO datosAseg = new DatosAseguradoDTO();
			//54785600534
			//94008201579
			//01008309880 - Sin datos Asegurado
			//01038000848 
			//04068609488
			//32887048612
			//01003700026
			//01018416618
			datosAseg = consultaDatosAseguradoServiceRemote.consultaDatosAseguradoPorNSS("06715337686");
			
			System.out.println("Nombre-"+datosAseg.getAseguradoDTO().getNombreAsegurado());
			System.out.println("NSS-"+datosAseg.getAseguradoDTO().getNss());
			System.out.println("Sexo-"+datosAseg.getAseguradoDTO().getCveSexoAsegurado());
			System.out.println("Delegacion-"+datosAseg.getDatosAfiliacionDTO().getDelegacion());
			System.out.println("cveIdPersonaAsegurado-"+datosAseg.getAseguradoDTO().getIdPersona());
			System.out.println("codigo -- "+datosAseg.getAseguradoDTO().getCodigo());
			System.out.println("ESTADO CIVIL -- "+datosAseg.getAseguradoDTO().getDesEstadoCivil());
			//System.out.println("Calle-"+datosAseg.getDomicilioAsegurado().getCalle());
			
			for(BeneficiarioDTO bene: datosAseg.getLstBeneficiariosDTO()){
				System.out.println("cveIdPersonsaIntegrante[" +bene.getIdPersona()  +"]");
				System.out.println("prorroga[" +bene.getDatosAfiliacionBeneficiarioDTO().getProrroga()+"]");
				System.out.println("fecha vigencia[" +bene.getDatosAfiliacionBeneficiarioDTO().getFecFinVigencia()+"]");
				System.out.println("codigo integrante[" +bene.getCodigo());
				
			}
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	/*
	//@Test
	public void Consulta(){
		
		System.out.println("Test");
		ConsultarVigenciaServiceRemote consultarVigenciaServiceRemote = EjbLocator.getConsultarVigenciaServiceRemote();
		
		try{
			
			Respuesta3 resp3  = consultarVigenciaServiceRemote.consultaVigencia3("01008309880");
			resp3.getReturn().getNombre();
//			DatosAseguradoDTO datosAseg = new DatosAseguradoDTO();
//			
//			datosAseg = consultaDatosAseguradoServiceRemote.consultaDatosAseguradoPorNSS("01008309880");
//			
//			System.out.println("Nombre-"+datosAseg.getAseguradoDTO().getNombreAsegurado());
//			System.out.println("NSS-"+datosAseg.getAseguradoDTO().getNss());
//			System.out.println("Sexo-"+datosAseg.getAseguradoDTO().getCveSexoAsegurado());
//			System.out.println("Delegacion-"+datosAseg.getDatosAfiliacionDTO().getDelegacion());
//			System.out.println("Calle-"+datosAseg.getDomicilioAsegurado().getCalle());
		}catch(Exception e){
			System.out.println(e);
		}
	}
	*/

}
