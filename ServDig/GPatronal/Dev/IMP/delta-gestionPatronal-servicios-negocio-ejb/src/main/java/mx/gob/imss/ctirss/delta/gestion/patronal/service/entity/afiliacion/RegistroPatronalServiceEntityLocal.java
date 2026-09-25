package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.afiliacion;

import java.util.List;
import java.util.Date;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RegistroPatronal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

/**
 * @author Hugo Martinez
 * @Projecto: delta-gestionPatronal-servicios-negocio-ejb
 * @Package: mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.afiliacion
 * @Archivo: RegistroPatronalServiceEntityLocal.java
 * @Fecha: 10/01/2013 09:43:47
 */
@Local
public interface RegistroPatronalServiceEntityLocal {
	
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 07/09/2012
	 * @param input
	 * @return List<SujetoObligado>
	 */
	DatosSalidaPaginador<SujetoObligado> consultarRegistrosPatronalesPersonaFisica(DatosEntradaPaginador<SujetoObligado> input);
	
	/**
	 * 
	 * @author Hugo Martinez
	 * @Date 07/09/2012
	 * @param input
	 * @return List<SujetoObligado>
	 */
	DatosSalidaPaginador<SujetoObligado> consultarRegistrosPatronalesPersonaMoral(DatosEntradaPaginador<SujetoObligado> input);
	
	/**
	 * Utilizado para dar de baja un conjunto de registros patronales proporcionados (actualiza la fecha de baja en la BD)
	 * @param registrosPatronales
	 * @throws Exception
	 */
	void darDeBajaRPs(List<String> registrosPatronales) throws Exception;
	
	/**
	 * Utilizado para registrar un nuevo registro patronal asociado al RFC en cuestión
	 * @param RegistroPatronal
	 * @throws Exception
	 */
	RegistroPatronal registrarRegistroPatronal(RegistroPatronal RegistroPatronal) throws Exception;
	
    /**
     * @returns Lista de SujetoObligado correspondiente a los RP relacionados
     * @param rp registro patronal a 8 o a 10 posiciones
     */
    List<SujetoObligado> listaRPRelacionadosPersonaFisica(String rp);

    /**
     * @returns Lista de SujetoObligado correspondiente a los RP relacionados
     * @param rp registro patronal a 8 o a 10 posiciones
     */
    List<SujetoObligado> listaRPRelacionadosPersonaMoral(String rp);
    
    /**
     * Consulta la información de la tabla DicModalidad
     * en base al numModalidad y devuelve la información
     * @param cveModalidad numModalidad
     * @return Modalidad
     */
    Modalidad obtenerModalidadPorClave(String cveModalidad);
    
    /**
     * Obtiene datos del registro patronal
     * @param cveIdSujetoObligado
     * @return RegistroPatronal
     */
    RegistroPatronal obtenerDatosGenerales(Long cveIdSujetoObligado);
    
    /**
     * Retorna el correo asociado al centro de trabajo
     * @param id id de la tabla dit_patron_sujeto_obligado
     * @return Correo
     */
    String obtenerCorreoPorIdentificador(Long id);
    
    /**
     * Retorna el correo asociado al centro de trabajo
     * @param nrp Número de registro patronal
     * @return Correo
     */
    String obtenerCorreoPorNumeroDeRegistroPatronal(String nrp, String modalidad, String digitoVerificador);

    /**
     * Obtiene el correo fiscal
     * @param idPersona id persona física o cve_Id_persona_moral
     * @return Correo
     */
    String obtenerCorreoFiscal(Long idPersona, Long idTipoPersona);
    
    /**
     *  Obtiene el registro patronal de la persona fisica en base al RFC
     * 
     * @param  rfc
     * @return Nrp
     */
    SujetoObligado obtenerNrpModalidad34PersonaFisica(String rfc, String cveMunicipioImss);
    
    /**
     * Obtiene el registro patronal convencional 
     * activo registrado en la tabla DIT_REG_PAT_CONVENCIONAL
     * @param cveIdMunicipioImss
     * @return Número de Registro Patronal
     */
    String obtenerNrpConvencionalPorMunicipio(String cveMunImss, String numModalidad) throws GestionPatronalBusinessException;
    
    /**
     * Obtiene el registro patronal convencional 
     * activo registrado en la tabla DIC_REG_PAT_CONVENCIONAL por subdelegacion y delegacion
     * y modalidad
     * @param idDelegacion
     * @param idSubdelegacion
     * @param numModalidad
     * @return
     * @throws GestionPatronalBusinessException
     */
    String obtenerNrpConvencionalPorSubdelegacionDelegacion(Long idDelegacion, Long idSubdelegacion,
			String numModalidad) throws GestionPatronalBusinessException;
			
	Date obtenerEstadoHuelga(String regPatronal);
	
	List<RegistroPatronal> obtenerPatronesConMunicipiosImss(List<String> registrosPatronales);
}
