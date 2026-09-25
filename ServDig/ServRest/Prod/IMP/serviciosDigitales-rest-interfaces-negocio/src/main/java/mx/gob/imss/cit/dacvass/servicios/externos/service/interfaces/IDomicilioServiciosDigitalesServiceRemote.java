package mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.catalogos.MunicipioInegi;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.digital.modelo.domicilio.Domicilio;


@Remote
public interface IDomicilioServiciosDigitalesServiceRemote {
	
	Domicilio registraDomicilio(Domicilio domicilioRecortado) throws ServiciosRestException;
	
	Domicilio consultaDomicilio(Long cveDomicilioGeografico) throws ServiciosRestException;
	
	void actualizaDomicilio(Domicilio domicilioRecortado) throws ServiciosRestException;
	
	List<mx.gob.imss.digital.modelo.domicilio.Asentamiento> getAsentamientoPorCodigoPostal(String codigoPostal) throws ServiciosRestException;

	/**
	 * Metodo que consulta las lcoalidades  por codigo postal haciendo primero la consulta por asentmiento y de ahi saca entidad federativa y municpio 
	 * @param codigoPostal
	 * @return
	 * @throws ServiciosRestException
	 */
	List<mx.gob.imss.digital.modelo.domicilio.Localidad> getLocalidadPorCodigoPostal (String codigoPostal) throws ServiciosRestException;
	
	/**
	 * Metodo que consulta las lcoalidades  por municipio 
	 * @param codigoPostal
	 * @return
	 * @throws ServiciosRestException
	 */
	List<mx.gob.imss.digital.modelo.domicilio.Localidad> getLocalidadPorMunicipio(MunicipioInegi municipio) throws ServiciosRestException;
	
	/**
	 * Metodo que guarda un domicilio Delta con la info de la norma ingegi
	 * @param domicilioDelta
	 * @return
	 * @throws ServiciosRestException
	 */
	mx.gob.imss.ctirss.delta.model.domicilio.Domicilio registraDomicilioDeltaInegi(mx.gob.imss.ctirss.delta.model.domicilio.Domicilio domicilioDelta)
				throws ServiciosRestException;
	mx.gob.imss.ctirss.delta.model.domicilio.Domicilio consultaDomicilioNormaInegi(Long cveDomicilioGeografico) 
			throws ServiciosRestException;
}
