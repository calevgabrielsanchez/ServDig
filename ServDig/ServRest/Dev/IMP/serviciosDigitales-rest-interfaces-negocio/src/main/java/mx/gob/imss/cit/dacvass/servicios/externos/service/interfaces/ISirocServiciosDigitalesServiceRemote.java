package mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.Page;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.ConsultaModel;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.ActualizarObraInputSiroc;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.AvisoUbicacionObra;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.AvisoUbicacionObraDetalle;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.ConsultaObraDetalle;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.DetalleRegistroObra;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.ObraGeneralExtPrto;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.ObraSirocInput;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.ObraSirocOutput;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.ObrasSimilaresInputSiroc;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.RegistroObra;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.RegistroObraDetalle;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.siroc.SirocOutput;

@Remote
public interface ISirocServiciosDigitalesServiceRemote {
	
	/**
	 * Metodo que consulta los avisos de ubicacion de obra por delegacion y subdelegacion
	 * @param cveDelegacion
	 * @param cveSubDelegacion
	 * @return
	 * @throws ServiciosRestException
	 */
	List<AvisoUbicacionObra> consultaAvisoRegistroObraByDelegSubDel(Long cveIdDelegacion, Long cvdIdSubDelegacion) throws  ServiciosRestException;
	/**
	 * COnustal el detalle de aviso de obra por numero de aviso
	 * @param numeroAvisoUbicacionObra
	 * @return
	 * @throws ServiciosRestException
	 */
	AvisoUbicacionObraDetalle getAvisoRegistroObra(String cveRegistroAvisoObra)throws  ServiciosRestException;
	
	/**
	 * Consulta las obras que se encuentras en una determinada zona por colonia y codigo postal
	 * @param colonia
	 * @param codigoPOstal
	 * @return
	 * @throws ServiciosRestException
	 */
	List<RegistroObra> consultaRegistroObraByCPColonia(String codigoPostal, String colonia) throws  ServiciosRestException;

	/**
	 * Netodo que recupera la informacion de una obra a partir de us id
	 * @param numRegistroObra
	 * @return DetalleRegistroObra
	 * @throws ServiciosRestException
	 */
	DetalleRegistroObra getRegistroObraByNumRegistro(String numRegistroObra) throws  ServiciosRestException;
	
	Page<ObraGeneralExtPrto> obrasRegistradasPRTO(
			ObrasSimilaresInputSiroc<ObraGeneralExtPrto> input)
			throws ServiciosRestException;

	ObraGeneralExtPrto actualizarObra(ActualizarObraInputSiroc input)
			throws ServiciosRestException;

	Page<ObraGeneralExtPrto> obrasSimilaresCP(
			ObrasSimilaresInputSiroc<ObraGeneralExtPrto> input)
			throws ServiciosRestException;

	Page<ObraGeneralExtPrto> obrasSimilaresCPColonia(
			ObrasSimilaresInputSiroc<ObraGeneralExtPrto> input)
			throws ServiciosRestException;

	ObraGeneralExtPrto detalleObraPRTO(String numObra)
			throws ServiciosRestException;
	
	List<ConsultaModel> tipoPatron() throws ServiciosRestException;
	
	List<ConsultaModel> estatusObra() throws ServiciosRestException;
	
	List<ConsultaModel> rocTipoIncidencia() throws ServiciosRestException;
	
	/**
	 * Consulta las obras mediante fecha del Ejercicio, la OOAD, Subdelegación y clase de obra 
	 * @Objet ObraSiroc
	 * 		@param fechaEjercicio
	 * 		@param oOAD
	 * 		@param subdelegacion
	 * 		@param claseObra
	 * @return
	 * @throws ServiciosRestException
	 */
	Page<ConsultaObraDetalle> getConsultaObra(ObrasSimilaresInputSiroc<ObraSirocInput> datObra) throws Exception;
	/**
	 * Consulta para AVISO DE OBRAS  Aviso de Ubicación de Obras 
	 * 
	 * 		@param ObraGeneralExtPrto
	 * @return
	 * @throws ServiciosRestException
	 */
	Page<RegistroObraDetalle> consultaUbicacionObra(ObrasSimilaresInputSiroc<ObraSirocInput> input) throws  Exception;
	
	SirocOutput rocDetalleObra(String numObra) throws ServiciosRestException;
	
	List<ObraSirocOutput> getObrasHijasByRegObra(List<String> input)throws  Exception;
	
	
}
