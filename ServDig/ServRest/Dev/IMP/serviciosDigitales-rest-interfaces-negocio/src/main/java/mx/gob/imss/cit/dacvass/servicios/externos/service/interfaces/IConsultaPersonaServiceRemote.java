package mx.gob.imss.cit.dacvass.servicios.externos.service.interfaces;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.cit.dacvass.servicios.externos.model.consultas.dto.ConsultaPersonaGfHistLab;
import mx.gob.imss.cit.dacvass.servicios.externos.model.general.exception.ServiciosRestException;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.DerechohabienteDTO;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.PersonaFisicaMoral;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.PersonaSua;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.DatosBasicosPersonaGfHistLab;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.DatosBasicosPersonaGrupoFamiliar;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.DatosPersona;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.Persona;
import mx.gob.imss.cit.dacvass.servicios.externos.model.identidad.dto.PersonaGruposFamilares;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaAutorizada;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;

@Remote
public interface IConsultaPersonaServiceRemote {
	
	/**
	 * Metodo encargado de buscar la informaci�n asociada a la persona,buscando en RENAPO, SAT, servicios en almacenes y BDTU
	 * Encapusla las excpcioens de negocio para solo mandar un Excepicon y la descripci�n del error
	 * @param curp
	 * @return
	 * @throws Exception
	 */
	DatosPersona getInfoPersonaServiciosDigitales(String curp) throws Exception;
	
	/**�
	 * Metodo encargado de buscar a la peronsa en BDTU se busca contra RENAPO y compara sus datos estadisticos para ver que haya consistencia
	 * @param curp
	 * @return Persona
	 * @throws ServiciosRestException
	 */
	Persona getPersonaServiciosDigitalesNSSRenapoCurp(String curp, String nss) 
			throws ServiciosRestException;
	
	
		
	/**
	 * Metodo que busca la informaci�n de un beneficiario incluyendo su vigencia y datos generales
	 * @param cveIdAsignacionNSS
	 * @param cveIdPersona
	 * @return GrupoFamiliar
	 * @throws ServiciosRestException
	 */
	DerechohabienteDTO getIntegranteGrupoFamiliar(Long cveIdAsignacionNSS, Long cveIdPersona) 
			throws ServiciosRestException;
	
	
	/**
	 * Metodo que consula los datos de una persona en RENAPO y complementa la informaci�n del BDTU
	 * @param curp
	 * @param indConsultaRegistroProtalFiel
	 * @param indConsultaCorreoElectronico
	 * @return
	 * @throws ServiciosRestException
	 */
	PersonaSua getPersonaSuaRenapoComplementoSD(String curp, boolean indConsultaRegistroProtalFiel, boolean indConsultaCorreoElectronico) 
			throws ServiciosRestException;

	/**
	 * Metodo que valida que el CURP de la persona exista en renapo busca la informaci�n en BDTU por CURP y con
	 * el registro devuelve si tiene grupos familares
	 * @param curp
	 * @return
	 * @throws ServiciosRestException
	 */
	PersonaGruposFamilares getPersonaGruposFamilares(String curp)throws ServiciosRestException;
	
	/**
	 * Metodo que consulta la informaci�n general de una persona fisica o moral por el RFC
	 * @param rfc
	 * @return
	 * * @throws ServiciosRestException
	 */
	PersonaFisicaMoral getPersonaFisicaMoralByRfc(String rfc)throws ServiciosRestException;
	/**
	 * Metodo que consulta los representantes legales que puede tener una persona fisica o moral
	 * @param cveIdPersona
	 * @param cveIdTipoPersona
	 * @return List<RepresentanteLegal>
	 * @throws ServiciosRestException
	 */
	List<RepresentanteLegal> getRepresentanteLegal(Long cveIdPersona, Long cveIdTipoPersona) throws ServiciosRestException;
	/**
	 * Metodo que devuele las personas fisicas o morales a las que representa una persona
	 * @param cveIdPersona
	 * @return
	 * @throws ServiciosRestException
	 */
	List<mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona> getPersonaRepresentada(Long cveIdPersona) throws ServiciosRestException;
	
	/**
	 * Metodo que recupera las persona autorizadas de una persona fisica o moral
	 * @param cveIdPersona
	 * @param cveIdTipoPersona
	 * @return
	 * @throws ServiciosRestException
	 */
	 List<PersonaAutorizada>  getPersonaAutorizada(Long cveIdPersona, Long cveIdTipoPersona)throws ServiciosRestException;
	 
	 /**
	  * Metodo que devuelve el listado de RFC que representa una persona por rfc
	  * @param rfc
	  * @return
	  * @throws ServiciosRestException
	  */
	 List<String> getRfcPersonaRepresentadaByRfc(String rfc) throws ServiciosRestException;
	
	 /**
	  * Metodo que devuele los datos generales de una persona y los grupos familiares en donde se localiza, 
	  * el segundo parametro define si se consulta la vigencia o no de las personas encontradas
	  * @param curp
	  * @return DatosBasicosPersonaGrupoFamiliar
	  * @throws ServiciosRestException
	  */
	 List<DatosBasicosPersonaGrupoFamiliar> getDatosBasicosPersonaGruposFamiliares(String curp, boolean conVigencia) 
			 throws ServiciosRestException;
	 
	 /**
	  * Metodo que devuele los datos generales de una persona y los grupos familiares en donde se localiza,
	  * por IDEE
	  * el segundo parametro define si se consulta la vigencia o no de las personas encontradas
	  * @param curp
	  * @return DatosBasicosPersonaGrupoFamiliar
	  * @throws ServiciosRestException
	  */
	 List<DatosBasicosPersonaGrupoFamiliar> getDatosBasicosPersonaGruposFamiliaresByIdee(String idee, boolean conVigencia) 
			 throws ServiciosRestException;
	 
	 /**
	  * Metodo que consulta la información basica de la persona (nombre apellidos)  por CURP, RFC y NSS
	  * los aotributos son opcionales pero minimo debe de tener alguno la consulta es con AND y 
	  * hace outer join con DitAsignacionNss y DitGrupoFamliar
	  * @param personaBusqueda
	  * @return
	  * @throws Exception
	  */
	 DatosBasicosPersonaGfHistLab getDatosBasicosPersonaAseguradoGFHistLab(ConsultaPersonaGfHistLab personaBusqueda) throws ServiciosRestException; 
}


