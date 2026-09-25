
import gob.imss.webservice.sat.rfc.cliente.Identificacion;
import gob.imss.webservice.sat.rfc.cliente.MensajeControl;
import gob.imss.webservice.sat.rfc.cliente.SalidaSAT;
import gob.imss.webservice.sat.rfc.implementacion.ClienteWebserviceRfc;

import java.util.Date;
import java.util.List;

import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;

import org.junit.Test;

public class ConsultaRfcTest {
	
	@Test
	public void consultaRfcThread(){
		System.out.println("consultaRfc. Inicio. " + new Date());
		
		try{
			ClienteWebserviceRfc clienteWebserviceRfc = new ClienteWebserviceRfc();
			SalidaSAT respuesta = clienteWebserviceRfc.getDatosRfc("AA&120726PN1");
			
	        if (respuesta!= null){
	        	
	        	 System.out.println(" RFC Original :::: {" +   respuesta.getRFCOriginal()+"}");
	        	 
	        	 System.out.println(" RFC Original :::: {" +   respuesta.getRFCSolicitado()+"}");
	        	 
	        	 System.out.println(" RFC Original :::: {" +   respuesta.getRFCVigente()+"}");
	        	
	        	
	        	
	        	
	        	
	            final MensajeControl mensajeControl = respuesta.getMensajeControl();
	            if (mensajeControl.getDescripcion().equals("")) {
	                final List<Identificacion> identificacion = respuesta.getIdentificacion();
	                if (identificacion != null) {
	                    Identificacion persona = identificacion.get(0);
	                    System.out.println("respuesta. nombre: " + persona.getNombre());
	                    System.out.println("respuesta. getApPaterno: " + persona.getApPaterno());
	                }
	            }
	        }			
		}
		catch(ClienteWebserviceSatRfcException ws){
			System.out.println("Se generó un error en el webservice");
		}
		catch(Exception e){
			System.out.println("\n\n Se generó un errorno identificado en cliente\n\n");			
			e.printStackTrace();
		}
		
        System.out.println("consultaRfc. Final. " + new Date());
	}
	
	@Test
	public void getDatosRfcPersonaFisica(){
		System.out.println("getDatosRfcPersonaFisica. Inicio. " + new Date());
		
		try{
			ClienteWebserviceRfc clienteWebserviceRfc = new ClienteWebserviceRfc();
			Fisica fisica = clienteWebserviceRfc.buscarPersonaFisicaPorRfcEnSat("AAJJ440908RH3");			
			
	        if (fisica!= null){
                System.out.println("respuesta. nombre: " + fisica.getNombre());
                System.out.println("respuesta. getApPaterno: " + fisica.getPrimerApellido());
                System.err.println("respuesta. RIF: " + fisica.getIndRIF());
	        }			
		}
		catch(ClienteWebserviceSatRfcException ws){
			System.out.println("Se generó un error en el webservice");
		}
		catch(Exception e){
			System.out.println("\n\n Se generó un errorno identificado en cliente\n\n");			
			e.printStackTrace();
		}
		
        System.out.println("getDatosRfcPersonaFisica. Final. " + new Date());
	}
	
	@Test
	public void getDatosRfcPersonaMoral(){
		System.out.println("getDatosRfcPersonaMoral. Inicio. " + new Date());
		
		try{
			ClienteWebserviceRfc clienteWebserviceRfc = new ClienteWebserviceRfc();
			//Moral moral = clienteWebserviceRfc.buscarPersonaMoralPorRfcEnSat("ADE0501173H6");
			Moral moral = clienteWebserviceRfc.buscarPersonaMoralPorRfcEnSat("AA&120726PN1");
			
	        if (moral!= null){
                System.out.println("respuesta. getRazonSocial: " + moral.getRazonSocial());
//                System.out.println("respuesta. getNombreComercial: " + moral.getNombreComercial());
	        }			
		}
		catch(ClienteWebserviceSatRfcException ws){
			System.out.println("Se generó un error en el webservice");
		}
		catch(Exception e){
			System.out.println("\n\n Se generó un error no identificado en cliente\n\n");			
			e.printStackTrace();
		}
		
        System.out.println("getDatosRfcPersonaMoral. Final. " + new Date());
	}	

	@Test
	public void obtenerDatosFiscalesMoralPorRfcTest() {
		System.out.println("obtenerDatosFiscalesMoralPorRfc. Inicio. " + new Date());

		try {
			ClienteWebserviceRfc clienteWebserviceRfc = new ClienteWebserviceRfc();
			Moral moral = clienteWebserviceRfc.obtenerDatosFiscalesMoralPorRfc("AA&120726PN1");

			if (moral != null) {
				System.out.println("respuesta. getRazonSocial: " + moral.getRazonSocial());
			}
		} catch (ClienteWebserviceSatRfcException ws) {
			System.out.println("Se genero un error en el webservice");
		} catch (Exception e) {
			System.out.println("\n\n Se genero un error no identificado en cliente\n\n");
			e.printStackTrace();
		}

		System.out.println("getDatosRfcPersonaMoral. Final. " + new Date());
	}

	@Test
	public void obtenerDatosFiscalesFisicaPorRfcTest() {
		System.out.println("obtenerDatosFiscalesMoralPorRfc. Inicio. " + new Date());

		try {
			ClienteWebserviceRfc clienteWebserviceRfc = new ClienteWebserviceRfc();
			Fisica fisica = clienteWebserviceRfc.obtenerDatosFiscalesFisicaPorRfc("AA&120726PN1");

			if (fisica != null) {
				System.out.println("respuesta. nombre: " + fisica.getNombre());
                System.out.println("respuesta. getApPaterno: " + fisica.getPrimerApellido());
			}
		} catch (ClienteWebserviceSatRfcException ws) {
			System.out.println("Se genero un error en el webservice");
		} catch (Exception e) {
			System.out.println("\n\n Se genero un error no identificado en cliente\n\n");
			e.printStackTrace();
		}

		System.out.println("getDatosRfcPersonaMoral. Final. " + new Date());
	}
}
