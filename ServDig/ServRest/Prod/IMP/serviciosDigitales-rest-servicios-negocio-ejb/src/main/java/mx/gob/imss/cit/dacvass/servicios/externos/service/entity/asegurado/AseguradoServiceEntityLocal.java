package mx.gob.imss.cit.dacvass.servicios.externos.service.entity.asegurado;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.cit.dacvass.servicios.externos.model.consultas.dto.ConsultaPersonaGfHistLab;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.catalogos.TipoPension;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.AsignacionNssDTO;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.DatosBasicosPersonaGfHistLab;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.DatosBasicosPersonaGrupoFamiliar;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.asegurado.DatosGeneralesAsegurado;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.asegurado.DatosGeneralesAseguradoCL;
import mx.gob.imss.cit.dacvass.servicios.externos.model.services.sisec.ResumenAseguradoTramiteCda;

@Local
public interface AseguradoServiceEntityLocal {
	
	/**
	 * Metodo que consulta el idAsingacion y idPersona de la tabla de asegurados sin considerar las bajas logicas o indicadores
	 * @param nss, filtroBajaLogica true si la fecha de baja debe ser nula, false no se aplica filtro 
	 * @return
	 * @throws Exception
	 */
	AsignacionNssDTO getDitAsignacionNSS(String nss, boolean filtroBajaLogica) throws Exception;
	
	
	
	/**Metodo que valida si el NSS se encuentra con una marca de paso al cambio al o marca de confirmar
	 * Regresa 0 en caso de que no tenga alguna marca regresa 1 en caso de que si 
	 * @param nss
	 * @return
	 * @throws ServiciosRestException
	 */
	String isAseguradoPasoAlCambioAlPendiente(String nss) throws Exception;
	
	
	/**
	 * Metodo que consulta la informaci�n de los tramites de CDA asociados a un asegurado
	 * haciendo la consulta directa de SQL
	 * @param refCurp
	 * @param nss
	 * @return
	 * @throws ServiciosRestException
	 */
	List<ResumenAseguradoTramiteCda> getResumenAseguradoTramiteCda(String refCurp, String nss)throws Exception;
	
	/**
	 * Metodo que consulta en llave Asegurado y cruza contra el tipo de pension si es que la tabla tiene un id
	 * devuelve nulo en caso de no tener pension
	 * @param nss
	 * @return
	 * @throws Exception
	 */
	TipoPension getTipoPensionAsegurado(String nss) throws Exception;
	
	/**
	 * Metodo que consulta los datos generales de un asegurado sin logica de bajas o indicadores recibe una lista de NSS
	 * @param nss
	 * @return
	 * @throws Exception
	 */
	List<DatosGeneralesAsegurado> getDatosGeneralesAseguradoByNss(List<String> lstNss) throws Exception;
	
	/**
	 * Metodo que consulta los datos generales de un asegurado sin logica de bajas o indicadores  en canase BDTU 
	 * recibe una lista de NSS
	 * @param nss
	 * @return
	 * @throws Exception
	 */
	List<DatosGeneralesAsegurado> getCanseDatosGeneralesAseguradoByNss(List<String> lstNss) throws Exception;
	
	
	DatosGeneralesAseguradoCL getCubetaUnoAseguradoByNss(String lstNss) throws Exception;
	
	DatosGeneralesAseguradoCL getCubetaDosAseguradoByNss(String lstNss) throws Exception;
	
	DatosGeneralesAseguradoCL getAseguradoBajaByNss(String numNSS) throws Exception;
	
	DatosGeneralesAsegurado getDatosGeneralesAseguradoByNssDitPersona(
			Long idPersona) throws Exception;
	
	 /**
	  * Metodo que devuele los datos generales de una persona y los grupos familiares en donde se localiza hace outer join
	  * para recuperar los datos de la persona y consulta asegurados y grupos familiares
	  * @param curp
	  * @return DatosBasicosPersonaGrupoFamiliar
	  * @throws ServiciosRestException
	  */
	 List<DatosBasicosPersonaGrupoFamiliar> getDatosBasicosPersonaGruposFamiliares(String curp) 
			 throws Exception;
	 
	 /**
	  * Metodo que devuele los datos generales de una persona y los grupos familiares en donde se localiza hace outer join
	  * por idee para recuperar los datos de la persona y consulta asegurados y grupos familiares
	  * @param curp
	  * @return DatosBasicosPersonaGrupoFamiliar
	  * @throws ServiciosRestException
	  */
	 List<DatosBasicosPersonaGrupoFamiliar> getDatosBasicosPersonaGruposFamiliaresByIdee(String idee) 
			 throws Exception;

	 /**
	  * Metodo que consulta la información basica de la persona (nombre apellidos)  por CURP, RFC y NSS
	  * los aotributos son opcionales pero minimo debe de tener alguno la consulta es con AND y 
	  * hace outer join con DitAsignacionNss y DitGrupoFamliar
	  * @param personaBusqueda
	  * @return
	  * @throws Exception
	  */
	 DatosBasicosPersonaGfHistLab getDatosBasicosPersonaAseguradoGF(ConsultaPersonaGfHistLab personaBusqueda) throws Exception; 
}
