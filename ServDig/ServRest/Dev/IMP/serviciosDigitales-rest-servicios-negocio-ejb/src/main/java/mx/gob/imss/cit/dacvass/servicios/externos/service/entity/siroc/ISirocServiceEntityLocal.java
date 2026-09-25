package mx.gob.imss.cit.dacvass.servicios.externos.service.entity.siroc;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.cit.dacvass.servicios.externos.model.general.Page;
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

@Local
public interface ISirocServiceEntityLocal {
	
	/**
	 * metodo que consulta los avisos de obra por delegacion y subdelegación
	 * @param cveIdDelegacion
	 * @param cvdIdSubDelegacion
	 * @return
	 * @throws Exception
	 */
	List<AvisoUbicacionObra> consultaAvisoRegistroObraByDelegSubDel(Long cveIdDelegacion, Long cvdIdSubDelegacion)throws Exception;
	
	/**
	 * Metodo que consulta un aviso de registro de obra por ID
	 * @param cveRegistroAvisoObra
	 * @return
	 * @throws Exception
	 */
	AvisoUbicacionObraDetalle getAvisoRegistroObra(String cveRegistroAvisoObra)throws Exception;
	
	/**
	 * Consulta los registros de obra por codigo postal y colonia
	 * @param codigoPostal
	 * @param colonia
	 * @return
	 * @throws Exception
	 */
	List<RegistroObra> consultaRegistroObraByCPColonia(String codigoPostal, String colonia) throws Exception;
	
	/**
	 * Metodo que consulta la información del detlla de un registro de obra 
	 * @param cveRegistroObra registro de obra
	 * @return DetalleRegistroObra con los datos de la obra , aptron ,domicilio
	 * @throws Exception
	 */
	DetalleRegistroObra getRegistroObraByNumRegistro(String cveRegistroObra) throws Exception;	
	
	Page<ObraGeneralExtPrto> obrasRegistradasPRTO(
			ObrasSimilaresInputSiroc<ObraGeneralExtPrto> input, Date fecha)
			throws Exception;

	ObraGeneralExtPrto actualizarObra(ActualizarObraInputSiroc input)
			throws Exception;

	ObraGeneralExtPrto detalleObraPRTO(String numObra) throws Exception;

	Page<ObraGeneralExtPrto> obrasSimilaresCP(
			ObrasSimilaresInputSiroc<ObraGeneralExtPrto> input)
			throws Exception;

	Page<ObraGeneralExtPrto> obrasSimilaresCPColonia(
			ObrasSimilaresInputSiroc<ObraGeneralExtPrto> input)
			throws Exception;
	
	boolean existeMarcaPRTO(String numObra) throws Exception;

	ObraGeneralExtPrto actualizaMarcaPRTO(String numObra, String marcaPRTO)
			throws Exception;

	ObraGeneralExtPrto insertaMarcaPRTO(String numObra, String marcaPRTO)
			throws Exception;

	Page<ObraGeneralExtPrto> obrasRegistradasPRTOAproximacion(
			ObrasSimilaresInputSiroc<ObraGeneralExtPrto> input, Date fecha)
			throws Exception;
	
	/**
	 * Consulta los registros de obra 
	 * @return
	 * @throws Exception
	 */
	Page<ConsultaObraDetalle> getConsultaObra(ObrasSimilaresInputSiroc<ObraSirocInput>datObra) throws Exception;
	
	/**
	 * Consulta los registros de obra 
	 * @return
	 * @throws Exception
	 */
	Page<RegistroObraDetalle> consultaUbicacionObra(ObrasSimilaresInputSiroc<ObraSirocInput> input)throws Exception;
	/**
	 * Consulta los registros de obra 
	 * */
	List<ConsultaModel> tipoPatron()  throws Exception;
	
	/**
	 * Consulta los estatus de la Obra
	 */
	List<ConsultaModel> estatusObra()  throws Exception;
	
	/**
	 * Consulta el tipo de la incidencia
	 */
	List<ConsultaModel> rocTipoIncidencia()  throws Exception;
	
	/**
	 * Consulta el catalago de detalle de la obra
	 */
	SirocOutput rocDetalleObra(String numObra)  throws Exception;
	
	/**
	 * Consulta las obras hijas que tiene una obra
	 */
	List<ObraSirocOutput> getObrasHijasByRegObra(List<String> input) throws Exception;
	

}
