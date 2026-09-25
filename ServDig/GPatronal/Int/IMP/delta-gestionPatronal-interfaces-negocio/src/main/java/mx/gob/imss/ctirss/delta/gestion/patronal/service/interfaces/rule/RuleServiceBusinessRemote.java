/**
 *
 */
package mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rule;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.exception.gestion.patronal.PatronExistenteMunicipioFraccionModalidadException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Regimen;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Fraccion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

/**
 *
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Hugo Armando Mart?nez Cham?nica
 *  @Proyecto: delta
 *  @Archivo: RuleBusinessServiceRemote.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.rule
 *  @Fecha: 11:31:20
 */
@Remote
public interface RuleServiceBusinessRemote {


	/**
	 * Valida la regla RPC, en adici�n eval�a si la regla ha fallado previamente,
	 * si esto se cumple marca la solicitud como un indicador de reintento con
	 * la finalidad de indicar al analista que ponga especial atenci�n a esta solicitud
	 *
	 *
	 * @param idSolicitud Identificador d ela solicitud con la cual se trabaja
	 * @param rfc RFC de la persona f�sica o moral due�a del RP
	 * @param clase Clase que desea asignarse al registro patronal a modificar
	 * @param numeroRegistroPatronal N�mero de registro patronal con el cual se esta trabajando
	 * @throws GestionPatronalBusinessException
	 */
	boolean validarRPCPorModificacionSRT(Long idSolicitud, String rfc, Long clase, String numeroRegistroPatronal);


	/**
	 * Valida si para el RFC proporcionado existe m�s un registro patronal marcado como RPC
	 * con la misma clase que se proporciona como par�metro sin tomar en cuenta el registro patronal
	 * que se intenta actualizar y que se proporciona como par�metro:
	 *
	 * Ejemplo:
	 *
	 * Si un patr�n tiene 4 regitros patronales, dos de ellos marcados como RPC:
	 *
	 * Y6812345 RPC Clase V
	 * Y6812346 Sin marca de RCP
	 * Y6812347 RPC Clase III
	 * Y6812348 Sin marca de RCP
	 *
	 * Si el patr�n solicita un cambio de clasificaci�n para el RP Y6812345 esta regla se aplicar� y
	 * validar� que la nueva clase proporcionada como par�metro sea distinta a III que es la clase
	 * asignada para el RP Y6812347 que tambi�n est� definido como un RPC.
	 *
	 * Si el patr�n intenta realizar un cambio a los RP Y6812346 o Y6812348 la regla no tendr� efecto
	 * pues solo contempla aquellos RP definidos como RPC.
	 *
	 * @param rfc
	 * @param clase
	 * @param numeroRegistroPatronal
	 * @throws GestionPatronalBusinessException
	 */
	void validarRPC_AP_MOD_MAC(String rfc, Long clase, String numeroRegistroPatronal)throws GestionPatronalBusinessException;

	void validarRPC_AP_MOD_MAC(String rfc, Long clase)throws GestionPatronalBusinessException;

	/**
	 * Valida que cuando se realize la baja de un representante legal asociado a cierto Patron S.O., si este ultimo
	 * es persona moral tenga obligatoriamente al menos un representante legal.
	 * @param representanteLegal
	 * @param tipoPersonaFiscal
	 * @throws GestionPatronalBusinessException
	 */
	void validarLimiteMinRepresentanteLegal(RepresentanteLegal representanteLegal, String tipoPersonaFiscal) throws GestionPatronalBusinessException;

	/**
	 * Valida el n�mero de registro patronal (composici�n de 10 u 11 caracteres), si el
	 * formato del registro patronal no existe se envia una excepci�n con el codigo: error.nrp.formato
	 * valida si el registro patronal existe, en caso de existir retorna la informaci�n del mismo
	 * Si el RP no existe se env�a una excepci�n con c�digo: error.nrp.inexistente
	 * @author Hugo Martinez
	 * @Date 22/02/2013
	 * @param numeroRegistroPatronal
	 * @return
	 */
	SujetoObligado validarNumeroDeRegistroPatronal(String numeroRegistroPatronal) throws GestionPatronalBusinessException;


	/**
	 * Valida si existe un registro patronal asociado a la persona proporcionada con la misma fracci�n
	 * deltro del municipio IMSS.
	 * @param cveIdPersona
	 * @param tipoPersona
	 * @param idMunicipioIMSS
	 * @throws GestionPatronalBusinessException
	 */
	void validarClasificacionPorPatronYMunicipio(Long cveIdPersona, Long tipoPersona, Long idMunicipioIMSS, Long idFraccion, Long idRegistroPatronalActual)throws GestionPatronalBusinessException;

	/**
	 * Valida si existe un registro patronal asociado a la persona proporcionada con la misma fracci�n
	 * dentro del municipio IMSS por medio del RFC proporcionado.
	 * @param rfc
	 * @param tipoPersona
	 * @param idMunicipioIMSS
	 * @throws GestionPatronalBusinessException
	 */
	void validarClasificacionPorPatronYMunicipio(String rfc, Long tipoPersona, Long idMunicipioIMSS, Long idFraccion, Long idRegistroPatronalActual)throws GestionPatronalBusinessException;

	/**
	 * Metodo que valida que no exista un patron imss con los mismos datos de nombre, fraccion prima modalidad en el mismo municipio,
	 * si encuentra un registros con estas caracteristicas arroja excepcion de negocio
	 * @param SujetoObligado
	 * @throws PatronExistenteMunicipioFraccionModalidadException
	 */
	void validaPatronExistentePorMunicipioFraccionModalidad(SujetoObligado objSujetoObligado) throws PatronExistenteMunicipioFraccionModalidadException;

	/**
	 * Valida si el municipio asignado cuenta con el tipo de servicio (servicios urbanos o de campo) asociado a la modalidad en base a la
	 * siguiente tabla:
	 *
	 * Modalidad	Servicios urbanos	Servicios campo
	 * 10					X
	 * 13										X
	 * 14										X
	 * 17					X
	 * 30										X
	 * 32					X
	 * 33					X
	 * 35					X					X
	 * 36					X
	 * 38					X
	 * 40					X
	 * 42					X
	 * 43										X
	 * 44					X
	 * 46					X					X
	 *
	 * Esto se valida en base a la fecha de inicio de operaci�n de servicios (de campo o urbano seg�un
	 * corresponda) o bien, en caso de no existir fecha de inicio de operaciones se valida
	 * en base al identificador de convenio.
	 *
	 * @param cveMunicipio
	 * @param numModalidad
	 * @param fechaDeMovimiento
	 */
	void validarTipoAmbitoPorMunicipio(String cveMunicipio, String numModalidad, Date fechaDeMovimiento)throws GestionPatronalBusinessException;

	/**
	 * Valida si el cambio a una fracci�n es v�lido verificando si la modalidad correspondiente a la
	 * fracci�n es la misma que la de la fracci�n que tiene actualmente.
	 *
	 * @param cveIdPatronSujetoObligado Identificador delta del registro patronal
	 * @param nuevaClasificacion Clasificacion(Divisi�n Grupo y fracci�n a la cual se desea cambiar)
	 */
	void validarModalidad(Long cveIdPatronSujetoObligado, Clasificacion nuevaClasificacion) throws GestionPatronalBusinessException;

	/**
	 * Valida si el cambio a una fraccion es valido verificando si la modalidad correspondiente a la
	 * fraccion es la misma que la de la fraccion que tiene actualmente.
	 *
	 * @param cveIdPatronSujetoObligado Identificador delta del registro patronal
	 * @param nuevaClasificacion Clasificacion(Division Grupo y fraccion a la cual se desea cambiar)
	 */
	void validarModalidadClasificacion(Long cveIdPatronSujetoObligado, Clasificacion nuevaClasificacion) throws GestionPatronalBusinessException;

	/**
	 * Valida que el registro patronal proporcionado no se encuentra en la base delta, de lo contrario env�a una excepci�n.
	 * @param numeroRegistroPatronal N�mero de Registro Patronal a 10 posiciones
	 * @throws GestionPatronalBusinessException
	 */
	void validarNuevoRegistroPatronal(String numeroRegistroPatronal) throws GestionPatronalBusinessException;

	/**
	 * Calcula la prima en base a las reglas de negocio definidas
	 * @param cveCausa
	 * @param idPatron
	 * @param clasificacionNueva
	 * @return
	 */
	BigDecimal calcularPrima(Integer cveCausa, Long idPatron, Clasificacion clasificacionNueva);

	/**
	 * Se obtiene la fracci�n equivalente en base a los datos proporcionados por el clasificador.
	 * Se validan todas las reglas de negocio necesarias as� como el c�lculo de prima para retornar una fracci�n v�lida
	 * y con la prima correspondiente.
	 * @param inputObject
	 * @param idTipoTramite
	 */
	Clasificacion obtenerClasificacionEquivalenteValida(Clasificacion inputObject, Integer idTipoTramite) throws GestionPatronalBusinessException;

	/**
	 * Determina si el parametro para evaluar el regimen RIF esta activo acorde con la fecha actual y el parametro FECHA_INICIO_RIF
	 * @return BOOLEAN
	 */
	boolean estaParametroRIFHabilitado();

	/**
	 * Validar si dentro de los regimenes del patron esta el indicado para RIF
	 * @param regimenes
	 * @return
	 */
	boolean isRegimenRIF(List<Regimen> regimenes);

	/**
	 * Validar si dentro de los regimenes del patron esta el indicado para RIF
	 * @param regimenes
	 * @return
	 */
	boolean personaConRegimenRIF(Fisica persona);

	/**
	 * Valida la siguiente regla:
	 * Para una alta patronal solo de debera de permitir agregar la misma fraccion que tenga su primer ARP,
	 * adem�s, de que si su primer RP tienen una fraccion 411 podra registrar un RP con un fraccion diferente siendo esa su primer fraccion
	 * y como consecuencia se tomar� esta como la fracci�n fija a evaluar para sus siguientes altas
	 * @param clasif
	 * @throws GestionPatronalBusinessException
	 */
	void validarFraccionObligatoria(Clasificacion clasif)throws GestionPatronalBusinessException;

	/**
	 * Se valida que se tenga solo una fraccion distinta a 411 dentro de todos los RP buscados por rfc
	 * Solo aplica para Persona Moral
	 * @param rfc
	 * @throws GestionPatronalBusinessException
	 */
	void validarFraccionesConsistentesPorPatron(String rfc)throws GestionPatronalBusinessException;

	/**
	 * Se valida que se tenga solo una fraccion distinta a 411 dentro de todos los RP buscados por rfc dentro del mismo Municipio IMSS
	 * Solo aplica para Persona Moral
	 * @param rfc
	 * @param fraccion
	 * @throws GestionPatronalBusinessException
	 */
	void validarFraccionesConsistentesPorPatronV2(String rfc, String cvecMunicipioSINDO, Fraccion fraccion)throws GestionPatronalBusinessException;


	/**
	 *
	 * @param cveIdPersonaMoral
	 * @throws GestionPatronalBusinessException
	 */
	void validarSociosRequeridos(Long cveIdPersonaMoral)throws GestionPatronalBusinessException;

	/**
     *  Obtiene el registro patronal de la persona fisica con la mayor prima de riesgo en base al RFC
     *
     * @param  rfc
     * @return Nrp
     */
    String obtenerNrpPrimaRiesgoMayorPersonaFisica(String rfc);

    // Se sustituye por INC398780
    //void validarRPPrevios(String rfc, String cvecMunicipioSINDO, Fraccion fraccion, Integer tipoPersona)throws GestionPatronalBusinessException;

    void validarRPPrevios(SujetoObligado sujetoTramite)throws GestionPatronalBusinessException;
    
    void validarRPPreviosFisica(String rfc, String cvecMunicipioSINDO)throws GestionPatronalBusinessException;
}
