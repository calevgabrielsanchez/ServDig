package mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoAsentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoVialidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Vialidad;

@Remote
public interface DomicilioParserServiceRemote {
	/**
	 * Metodo para convertir un domicilio del paquete de iumss digital
	 * a un domicilio del parquete que se usa en todos los proyectos
	 * @param domicilio
	 * @return
	 */
	Domicilio convertirDomicilioDigtoDomicilioRecortado(mx.gob.imss.digital.modelo.domicilio.Domicilio domicilio);
	/**
	 * 
	 * @param asent
	 * @return
	 */
	Asentamiento convertirAsentamientoDigtoAsentamiento(mx.gob.imss.digital.modelo.domicilio.Asentamiento asent);
	/**
	 * Metodo para convertir una Localidad del paquete de umss digital
	 * a una localidad del parquete que se usa en todos los proyectos
	 * @param localidad
	 * @return
	 */
	Localidad convertirLocalidadDigtoLocalidad(mx.gob.imss.digital.modelo.domicilio.Localidad localidad);
	/**
	 * Metodo para convertir una Entidad Federativa del paquete de iumss digital
	 * a una Entidad Federativa del parquete que se usa en todos los proyectos
	 * @param entidad
	 * @return
	 */
	EntidadFederativa convertirEntidadFederativaDigToEntidad(mx.gob.imss.digital.modelo.domicilio.EntidadFederativa entidad);
	/**
	 * Metodo para convertir un Municipio del paquete de iumss digital
	 * a un municipio del parquete que se usa en todos los proyectos
	 * @param municipio
	 * @return
	 */
	Municipio convertirMunicipioDigToMunicipio(mx.gob.imss.digital.modelo.domicilio.Municipio municipio);
	/**
	 * Metodo para convertir una vialidad del paquete de iumss digital
	 * a una vialidad del parquete que se usa en todos los proyectos
	 * @param vialidad
	 * @return
	 */
	Vialidad convertirVialidadDigToVialidad(mx.gob.imss.digital.modelo.domicilio.Vialidad vialidad);
	/**
	 * Metodo para convertir un Tipo Vialidad del paquete de iumss digital
	 * a un TipoVialidad del parquete que se usa en todos los proyectos
	 * @param tipoVialiad
	 * @return
	 */
	TipoVialidad convertirTipovialiadDigToTipo(mx.gob.imss.digital.modelo.domicilio.TipoVialidad tipoVialiad);
	/**
	 * Metodo para convertir un tipo Asentamiento del paquete de iumss digital
	 * a un tipo asentamiento del parquete que se usa en todos los proyectos
	 * @param tipoAse
	 * @return
	 */
	TipoAsentamiento convertirTipoAsenDigToTipoAsen(mx.gob.imss.digital.modelo.domicilio.TipoAsentamiento tipoAse);
	
}
