package mx.gob.imss.cit.dacvass.servicios.externos.service.business;

import javax.ejb.Local;

import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.DatosPersona;

@Local
public interface IConsultaPersonaServiceLocal {
	
	DatosPersona getInfoPersonaServiciosDigitales(String curp)throws Exception;
	

}
