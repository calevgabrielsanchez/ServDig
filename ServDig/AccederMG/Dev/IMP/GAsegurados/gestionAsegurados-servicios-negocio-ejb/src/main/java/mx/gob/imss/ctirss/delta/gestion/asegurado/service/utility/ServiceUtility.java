/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:ServiceUtility.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.asegurado.service.utility
 *  @Fecha:20/02/2012
 */
package mx.gob.imss.ctirss.delta.gestion.asegurado.service.utility;

import java.util.Date;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.framework.util.DateUtils;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.gestion.asegurado.integracion.AseguradoWrapper;
import mx.gob.imss.ctirss.delta.model.gestion.asegurado.integracion.UMFSimple;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;

/**
 * @author Lucio Duran Silva
 *
 */
public class ServiceUtility extends AbstractServiceUtility {

	public static AseguradoWrapper crearAseguradoWrapper (Fisica fisica) {
		AseguradoWrapper wrapper = new AseguradoWrapper();
		wrapper.setNombre(fisica.getNombre());
		wrapper.setPrimerApellido(fisica.getPrimerApellido());
		wrapper.setSegundoApellido(fisica.getSegundoApellido());
		wrapper.setCurp(fisica.getCurp());
		wrapper.setNss(fisica.getNss());
		
		UnidadMedicaFamiliar umf = fisica.getUmf();
		
		if (umf != null) {
			UMFSimple umfSimple = new UMFSimple();
			umfSimple.setIdUMF(umf.getIdUMF());
			umfSimple.setNoEconomico(umf.getNoEconomico());
			umfSimple.setSubdelegacion(umf.getSubdelegacion());
			wrapper.setUmf(umfSimple);
		}
		
		wrapper.setFechaOperacion(DateUtils.dateToStringConFormato(new Date(), "ddMMyyyy"));
		
		return wrapper;
	}
	
	public static AseguradoWrapper procesarErrorAsignacionEstudiantes(Fisica fisica,
			String msgError) {
		AseguradoWrapper wrapper = crearAseguradoWrapper(fisica);
		wrapper.setEstatusRegistro(AseguradoWrapper.ERROR);
		wrapper.setMsgProceso(msgError);
		
		wrapper.setFechaOperacion(DateUtils.dateToStringConFormato(new Date(), "ddMMyyyy"));
		
		return wrapper;
	}
	
	
}
