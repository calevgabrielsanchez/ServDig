package mx.gob.imss.ctirss.delta.gestion.patronal.service.business.afiliacion;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

@Local
public interface ServiciosExternosAfiliacionBusinessLocal {
	
	/**
	 * Metodo que consume un webservices para consultar si existe un patron con mismos nombre municipio modalidad
	 * y clasificación en la base de datos de REING
	 * @return
	 */
	boolean consultaPatronPorNombreModalidadPrimaMunicipioIMSSenReing(SujetoObligado sujetoObligado);


}
