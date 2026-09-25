package mx.gob.imss.cit.dacvass.servicios.externos.service.business;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.ResolucionPension;

@Local
public interface IConsultaServiciosExternosServiceLocal {
	/**
	 * Metodo encargado de consultar el servicio REST de pensión envia CURP y recibe un List de resoluciones asociadas a la CURP
	 * @param curp
	 * @return ResolucionPension
	 * @throws ServiciosRestException
	 */
	List<ResolucionPension> getConsultaInfoPension(String curp) throws ServiciosRestException;
}
