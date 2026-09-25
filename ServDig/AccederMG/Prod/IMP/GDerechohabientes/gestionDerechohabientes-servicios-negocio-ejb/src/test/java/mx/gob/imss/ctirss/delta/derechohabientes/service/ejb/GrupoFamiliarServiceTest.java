package mx.gob.imss.ctirss.delta.derechohabientes.service.ejb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import mx.gob.imss.ctirss.delta.derechohabientes.service.test.EjbLocator;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GrupoFamiliarServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.util.DeltaUtils;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

import org.junit.Test;

public class GrupoFamiliarServiceTest {
	/*
	@Test
	public void buscarNSSByCurp() {
		PersonaBusinessRemote personaBusinessRemote = EjbLocator.getPersonaBusiness();
		
		List<AsignacionNSS> nsss = personaBusinessRemote.obtenerNsssByCurp("TEBM871029HPLRLR08");
		for(AsignacionNSS nss: nsss) {
			System.out.println("Se localizo el nss " + nss.getNss());
		}
	}*/

	@Test
	public void crearArchivoCurps() {
		GrupoFamiliarServiceRemote grupoFamiliarServiceRemote = EjbLocator.getGrupoFamiliarService();
		
		Integer totalActual = 0;
		Integer numeroFilas = 2000;
		Long idMinimo = 102601530L;
		
		do {
			idMinimo = grupoFamiliarServiceRemote.generarArchivoConCurps(numeroFilas,idMinimo);
			totalActual += numeroFilas;
		} while(totalActual < 10000);
		
	}
	
	@Test
	public void consultaNSSCabeza(){
		GrupoFamiliarServiceRemote grupoFamiliarServiceRemote = EjbLocator.getGrupoFamiliarService();
		try{
		grupoFamiliarServiceRemote.getGrupoFamiliar("11967000552" , true);
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	@Test
	public void actualizarIDEESS() {
		GrupoFamiliarServiceRemote grupoFamiliarServiceRemote = EjbLocator.getGrupoFamiliarService();
		Boolean seguirConsultando = true;
		do{
			seguirConsultando = grupoFamiliarServiceRemote.actualizarIDEESIncorrectos(2000);
		} while(seguirConsultando);
		
		
	}

	@Test
	public void insertarIDEESEstudiantes() {
		GrupoFamiliarServiceRemote grupoFamiliarServiceRemote = EjbLocator.getGrupoFamiliarService();
		Boolean seguirConsultando = true;
		int numeroRegistros = 0;
		
		do{
			seguirConsultando = grupoFamiliarServiceRemote.actualizarIDEESCL3Incorrectos(10000);
			//numeroRegistros += 10000;
		} while(seguirConsultando);// && numeroRegistros <= 100000);
		
	}
	
	@Test
	public void actualizarIDEESTablaAux() {
		GrupoFamiliarServiceRemote grupoFamiliarServiceRemote = EjbLocator.getGrupoFamiliarService();
		Boolean seguirConsultando = true;
		int numeroRegistros = 0;
		
		do{
			seguirConsultando = grupoFamiliarServiceRemote.actualizarIDEESAUX(10000);
			numeroRegistros += 100;
		} while(seguirConsultando);// && numeroRegistros <= 1000);
		
	}
	
	
	@Test
	public void testAgregadoMedico() {
		GrupoFamiliarServiceRemote grupoFamiliarServiceRemote = EjbLocator.getGrupoFamiliarService();
		Calendar cal = Calendar.getInstance();
		cal.set(1957,6, 18);
		
		/*
		List<Modalidad> modalidades = new ArrayList<Modalidad>();
		Modalidad modalidad = new Modalidad();
		modalidad.setIdModalidad(15L);
		modalidad.setNumModalidad("33");
		modalidad.setSiglaAgregadoMedico("SF");
		modalidades.add(modalidad);
		
		Modalidad modalidad40 = new Modalidad();
		modalidad40.setIdModalidad(20L);
		modalidad40.setNumModalidad("40");
		modalidad40.setSiglaAgregadoMedico("PE");
		modalidades.add(modalidad40);
		*/
		AsignacionNSS nss = new AsignacionNSS(14985291L);
		
		List<SujetoObligado> patrones = null;
		try {
			patrones = grupoFamiliarServiceRemote.getPatronesAsegurado(nss);
		} catch (DerechohabientesBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String agregadoMedico = DeltaUtils.getAgregadoMedico(5L, 1, 2, cal.getTime(), 57, patrones);
		
		System.out.println("El agregado medico generado es: " + agregadoMedico);
	}
	
@Test
	public void testCabeza() {
		GrupoFamiliarServiceRemote grupoFamiliarServiceRemote = EjbLocator.getGrupoFamiliarService();
		
		try {
			CabezaGrupoFamiliar cabeza = grupoFamiliarServiceRemote.getCabezaWS(50700507L);
			System.out.println("registro patronal " + cabeza.getPatronSujetoObligado().getNumeroRegistroPatronal());
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	@Test
	public void testisSocioisPersonaAutorizada() {
		Long idPersona = 84499092L;
		Boolean isPersonaAut = false;
		Boolean isSocio = false;
		
		isPersonaAut = EjbLocator.getPersonaFisicaBusiness().isPersonaAutorizada(idPersona);
		
		System.out.println("La persona " + idPersona + " es persona autorizada ? " + isPersonaAut);
		
		isSocio = EjbLocator.getPersonaFisicaBusiness().isSocio(idPersona);
		
		System.out.println("La persona " + idPersona + " es socio ? " + isSocio);
		
		idPersona = 64089202L;
		
		isPersonaAut = EjbLocator.getPersonaFisicaBusiness().isPersonaAutorizada(idPersona);
		
		System.out.println("La persona " + idPersona + " es persona autorizada ? " + isPersonaAut);
		
		isSocio = EjbLocator.getPersonaFisicaBusiness().isSocio(idPersona);
		
		System.out.println("La persona " + idPersona + " es socio ? " + isSocio);
		
		idPersona = 49338413L;
		
		isPersonaAut = EjbLocator.getPersonaFisicaBusiness().isPersonaAutorizada(idPersona);
		
		System.out.println("La persona " + idPersona + " es persona autorizada ? " + isPersonaAut);
		
		isSocio = EjbLocator.getPersonaFisicaBusiness().isSocio(idPersona);
		
		System.out.println("La persona " + idPersona + " es socio ? " + isSocio);
	}
	@Test
	public void testRP() {
		
		SujetoObligadoServiceBusinessRemote sujetoService = EjbLocator.getSujetoObligatoService();
		Long idPatron = sujetoService.getCvePatronSujetoObligadoPorRP("E6051425");
		
		System.out.println("Se encontro el id " + idPatron);
		
		Modalidad modalidad = sujetoService.getModalidad("10");
		
		System.out.println("La modalidad encontrada es: " + modalidad.getDescripcion());
		
	}
	
	public static void main(String[] args) {
		List<Long> lista = new ArrayList<Long>();
		lista.add(1L);
		lista.add(null);
		
		for(Long menor: lista) {
			if(menor.intValue() < 2) {
				System.out.println("menor");
			}
		}
	}
	
	@Test
	public void crearArchivoNSSS() {
		GrupoFamiliarServiceRemote grupoFamiliarServiceRemote = EjbLocator.getGrupoFamiliarService();
		
		grupoFamiliarServiceRemote.generarArchivoNSSVigentesDomicilio();
		
	}
}
