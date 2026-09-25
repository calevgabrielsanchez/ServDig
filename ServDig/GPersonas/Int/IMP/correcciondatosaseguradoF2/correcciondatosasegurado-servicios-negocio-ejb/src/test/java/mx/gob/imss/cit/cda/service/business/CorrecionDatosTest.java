package mx.gob.imss.cit.cda.service.business;

import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.base.Ambiente;
import mx.gob.imss.base.EjbLocator;
import mx.gob.imss.cit.cda.service.interfaces.CorreccionDatosRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.CorreccionDatos;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.ResumenCorrecion;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;

import org.junit.Test;

public class CorrecionDatosTest {

	@Test
	public void obtenerCorrecionSolicitud() {
/*
		CorreccionDatos correcion = EjbLocator.find(
				CorreccionDatosRemote.class, Ambiente.LOCAL)
				.obtenerTramitesCorreccion(74814535L);
		
		System.out.println("Ya acabe: "+correcion.getListaNss().size());
*/
	}
	
	public ResumenCorrecion obtenerResumenCoreccion(CorreccionDatos correccionDatos){
		ResumenCorrecion correcion = EjbLocator.find(
				CorreccionDatosRemote.class, Ambiente.LOCAL)
				.armarDetalleCoreccion(correccionDatos);
		
		return correcion;
		
	}
	
//	@Test
//	public void generarCorreciones() {
//
//		
//		List<String> aclaraciones = new ArrayList<String>();
//		aclaraciones.add("5");
//		List<String> aclaraciodup = new ArrayList<String>();
//		aclaraciodup.add("3");
//		CorreccionDatosRemote correccionBussines =EjbLocator.find(
//				CorreccionDatosRemote.class, Ambiente.LOCAL);
//					
//		correccionBussines.guardarCorrecionNssCDA("81502897", "16927357901", 1L, 2L, aclaraciones);
//		correccionBussines.guardarCorrecionNssCDA("81502896", "16947361693", 2L, 4L, aclaraciodup);
//		correccionBussines.guardarCorrecionNssCDA("81502898", "16947360232", 2L, 4L, aclaraciodup);
//		correccionBussines.guardarCorrecionNssCDA("81502895", "11947302789", 2L, 4L, aclaraciodup);
//		correccionBussines.guardarCorrecionNssCDA("81502894", "16927355673", 2L, 4L, aclaraciodup);
//		correccionBussines.guardarCorrecionNssCDA("81502899", "16937360267", 2L, 4L, aclaraciodup);
//		
//		System.out.println("Ya acabe.");
//
//	}
}
