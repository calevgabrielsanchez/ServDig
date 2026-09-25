package mx.gob.imss.cit.clienteServiciosComunes.service;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.v2.Atributo;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.v2.EntradaAlta;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.v2.EntradaConsulta;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.v2.IESServicio;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.v2.IESServicioSoap;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.v2.SalidaAlta;
import mx.gob.imss.cit.clienteServiciosComunes.bovedaPersonal.ws.v2.SalidaConsulta;
import mx.gob.imss.cit.clienteServiciosComunes.services.BovedaPersonalV2Service;
import mx.gob.imss.cit.test.BaseTest;

public class BovedaPersonalV2ServiceTest extends BaseTest {

	@Autowired
	private BovedaPersonalV2Service bovedaPersonalV2Service;
	
	@Test
	public void consultaDocumento(){
		EntradaConsulta entradaConsulta = new EntradaConsulta();
		entradaConsulta.setTipoDocumental("D:cda:imss");
		Atributo atributo = new Atributo();
		atributo.setNombre("objectId");
		atributo.setValor("5ee3ac2f-65a6-433a-abf3-186ca9f12e4a;1.0");
		entradaConsulta.getAtributo().add(atributo);
		atributo = new Atributo();
		atributo.setNombre("name");
		atributo.setValor("test.txt");
		entradaConsulta.getAtributo().add(atributo);
		
		SalidaConsulta salidaConsulta = bovedaPersonalV2Service.consultaDocumento(entradaConsulta);
		System.out.println(salidaConsulta.getClave());
		System.out.println(salidaConsulta.getDescripcion());
		if (salidaConsulta.getDocumento() != null) {
			System.out.println(salidaConsulta.getDocumento().get(0).getNombre());
			System.out.println(new String(salidaConsulta.getDocumento().get(0).getContenido()));
		}		
	}
	
	@Test
	public void altaDocumento(){
		EntradaAlta entradaAlta = new EntradaAlta();
		entradaAlta.setTipoDocumental("D:cda:imss");
		entradaAlta.setTipo("txt");
		entradaAlta.setRuta("/cda");
		Atributo atributo = new Atributo();
		atributo.setNombre("folioTramite");
		atributo.setValor("3242534575778");
		entradaAlta.getAtributo().add(atributo);
		atributo = new Atributo();
		atributo.setNombre("name");
		atributo.setValor("test.txt");
		String txt = buildStringWithRandomCharacters(15);
		entradaAlta.setDocumento(txt.getBytes());
		SalidaAlta salidaAlta = bovedaPersonalV2Service.altaDocumento(entradaAlta);
		System.out.println(salidaAlta.getClave());
		System.out.println(salidaAlta.getDescripcion());
		System.out.println(salidaAlta.getIdDocumento());
	
	}
	
	@Test
	public void altaDocumento2(){
		URL wsdl_ies = null;
		try {
			wsdl_ies = new URL("http://172.16.162.6/CDA/Proxy/CDAProxy?wsdl");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		QName name_ies = new QName("http://www.openuri.org/", "IESServicio");
		IESServicioSoap iesServicio = new IESServicio(wsdl_ies, name_ies).getIESServicioSoap();
		
		
		
		EntradaAlta entradaAlta = new EntradaAlta();
		entradaAlta.setTipoDocumental("D:cda:imss");
		entradaAlta.setTipo("txt");
		entradaAlta.setRuta("/cda");
		Atributo atributo = new Atributo();
		atributo.setNombre("folioTramite");
		atributo.setValor("3242534575778");
		entradaAlta.getAtributo().add(atributo);
		atributo = new Atributo();
		atributo.setNombre("name");
		atributo.setValor("test.txt");
		String txt = buildStringWithRandomCharacters(15);
		entradaAlta.setDocumento(txt.getBytes());
		SalidaAlta salidaAlta = iesServicio.altaDocumento(entradaAlta);
		System.out.println(salidaAlta.getClave());
		System.out.println(salidaAlta.getDescripcion());
		System.out.println(salidaAlta.getIdDocumento());
	
	}
	
}
