package mx.gob.imss.ctirss.delta.derechohabientes.service.ejb;

import java.util.List;

import mx.gob.imss.ctirss.delta.derechohabientes.service.test.EjbLocator;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.CatalogosDerechohabientesExternoRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.UmfServiceRemote;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.global.model.UnidadMedicaFamiliarTO;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Consultorio;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.externo.AsentamientoExternoDto;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.externo.ConsultorioExternoDto;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.externo.TurnoConsultorioExternoDto;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.externo.TurnoExternoDto;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.externo.response.derechohabientes.ConsultorioResponse;
import mx.gob.imss.ctirss.delta.model.externo.response.derechohabientes.TurnoConsultorioResponse;
import mx.gob.imss.ctirss.delta.model.externo.response.derechohabientes.TurnoResponse;
import mx.gob.imss.ctirss.delta.model.externo.response.derechohabientes.UmfResponse;

import org.junit.Test;

public class UmfServiceTest {
	
	@Test
	public void testConsultorioMenorPoblacion() {
		UmfServiceRemote umfservice = EjbLocator.getUmfService();
		
		try {
			Consultorio consultorio = umfservice.getConsultorioMenorPoblacion(674L, 2L, true);
			System.out.println(consultorio);
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
		}
		
		
	}
	
	@Test
	public void testAntecedentesMovil() {
		CatalogosDerechohabientesExternoRemote ejb = EjbLocator.getCatalogosDere();
		
		TurnoConsultorioResponse respuesta = ejb.findAntecedentesEnUmf(45843884L, 796L, 5L);
		if(respuesta.getCodigo().equals("000")) {
			System.out.print("Codigo: " + respuesta.getCodigo() + ", descripcion: " + respuesta.getMensaje());
			TurnoConsultorioExternoDto tur = respuesta.getTurnoConsultorioExternoDto();
			System.out.print("idTurno: " +tur.getIdTurno() + ", descripcion: " + tur.getDesTurno() + ", Consultorio: " + tur.getNumeroConsultorio() + ", idRelacion: " + tur.getIdRelacionConsTurno());
		} else {
			System.out.print("Error al conseguir antecedentes Codigo: " + respuesta.getCodigo() + ", descripcion: " + respuesta.getMensaje());
		}
	}
	
	@Test
	public void testUnidadPorAsentamientoMovil() {
		CatalogosDerechohabientesExternoRemote ejb = EjbLocator.getCatalogosDere();
		
		AsentamientoExternoDto asentamiento = new AsentamientoExternoDto();
		asentamiento.setCodigoPostal("07700");
		asentamiento.setCveAsentamiento("011858");
		asentamiento.setCveEntidad("09");
		asentamiento.setCveMunicipio("005");
		UmfResponse respuesta = ejb.consultarUMFAcentamiento(asentamiento);
		if(respuesta.getCodigo().equals("000")) {
			System.out.print("Codigo: " + respuesta.getCodigo() + ", descripcion: " + respuesta.getMensaje());
			UnidadMedicaFamiliarTO[] umfs = respuesta.getUnidadMedicaFamiliarTO();
			
			for(UnidadMedicaFamiliarTO umf: umfs) {
				System.out.print("idUmf: " + umf.getIdUMF() + ", descripcion: " + umf.getDescripcion() + ", direccion: " + umf.getDesDireccion());
			}
			
		} else {
			System.out.print("Error al conseguir las umfs  Codigo: " + respuesta.getCodigo() + ", descripcion: " + respuesta.getMensaje());
		}
	}
	
	@Test
	public void testConsultorioMenorPoblacionMovil() {
		CatalogosDerechohabientesExternoRemote ejb = EjbLocator.getCatalogosDere();
		
		ConsultorioResponse respuesta = ejb.getConsultorioMenorPoblacion(440L, 1L);
		if(respuesta.getCodigo().equals("000")) {
			System.out.print("Codigo: " + respuesta.getCodigo() + ", descripcion: " + respuesta.getMensaje());
			ConsultorioExternoDto tur = respuesta.getTurnoConsultorioExternoDto();
			System.out.print("el consultorio es: " + tur.getIdConsultorio() + ", descripcion: " + tur.getDescripcion() + ", poblacion: " + tur.getPoblacion() +
					", idRelacion: " + tur.getIdUmfConsultorioTurnoMedico());
		} else {
			System.out.print("Error al conseguir el turno con menor poblacion Codigo: " + respuesta.getCodigo() + ", descripcion: " + respuesta.getMensaje());
		}
	}
	
	@Test
	public void testTurnosMovil() {
		CatalogosDerechohabientesExternoRemote ejb = EjbLocator.getCatalogosDere();
		
			TurnoResponse respuesta= ejb.findTurnosByUmf(100000L);
			
			if(respuesta.getCodigo().equals("000")) {
				System.out.print("Codigo: " + respuesta.getCodigo() + ", descripcion: " + respuesta.getMensaje());
				TurnoExternoDto[] turnos = respuesta.getTurnoExternoDto();
				System.out.print("Los turnos son");
				for(TurnoExternoDto turnod: turnos) {
					System.out.print("IdTurno: " + turnod.getIdTurno() + ", descripcion: " + turnod.getDescripcion());
				}
			} else {
				System.out.print("Error al conseguir los turnos Codigo: " + respuesta.getCodigo() + ", descripcion: " + respuesta.getMensaje());
			}
	}
	
	@Test
	public void testAsentamientosUmf() {
		UmfServiceRemote umfservice = EjbLocator.getUmfService();
		Asentamiento asentamiento = new Asentamiento();
		asentamiento.setCodigoPostal(new CodigoPostal());
		asentamiento.getCodigoPostal().setCodigoPostal("07700");;
		asentamiento.setClave("011858");
		asentamiento.setLocalidad(new Localidad("", "", "09", "005"));
		
		try {
			List<UnidadMedicaFamiliar> umfs = umfservice.findUmfsByAsentamiento(asentamiento, false);
			System.out.println(umfs);
		} catch (DerechohabientesBusinessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
