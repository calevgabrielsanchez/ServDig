/**
*
*
**/
package mx.gob.imss.ctirss.delta.gestion.patronal.service.business.rule;

import java.util.Date;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.exception.gestion.patronal.PatronExistenteMunicipioFraccionModalidadException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RepresentanteLegal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;

/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Hugo Armando Martï¿½nez Chamï¿½nica
 *  @Proyecto: delta
 *  @Archivo: RuleServiceBusinessLocal.java
 *  @Paquete: mx.gob.imss.ctirss.delta.gestion.patronal.service.business.rule
 *  @Fecha: 11:41:37
 */
@Local
public interface RuleServiceBusinessLocal {
	
	/**
	 * Valida la regla RPC, en adiciï¿½n evalï¿½a si la regla ha fallado previamente,
	 * si esto se cumple marca la solicitud como un indicador de reintento con 
	 * la finalidad de indicar al analista que ponga especial atenciï¿½n a esta solicitud
	 * 
	 * 
	 * @param idSolicitud Identificador d ela solicitud con la cual se trabaja
	 * @param rfc RFC de la persona fï¿½sica o moral dueï¿½a del RP
	 * @param clase Clase que desea asignarse al registro patronal a modificar
	 * @param numeroRegistroPatronal Nï¿½mero de registro patronal con el cual se esta trabajando
	 * @throws GestionPatronalBusinessException
	 */
	boolean validarRPCPorModificacionSRT(Long idSolicitud, String rfc, Long clase, String numeroRegistroPatronal);
	
	
	void validarRPC_AP_MOD_MAC(String rfc, Long clase, String numeroRegistroPatronal) throws GestionPatronalBusinessException;
	
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
	 * Valida el nï¿½mero de registro patronal (composiciï¿½n de 10 u 11 caracteres), si el 
	 * formato del registro patronal no existe se envia una excepciï¿½n con el codigo: error.nrp.formato
	 * valida si el registro patronal existe, en caso de existir retorna la informaciï¿½n del mismo
	 * Si el RP no existe se envï¿½a una excepciï¿½n con cï¿½digo: error.nrp.inexistente
	 * @author Hugo Martinez
	 * @Date 22/02/2013
	 * @param numeroRegistroPatronal
	 * @return
	 */
	SujetoObligado validarNumeroDeRegistroPatronal(String numeroRegistroPatronal) throws GestionPatronalBusinessException;
	
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
	 * Esto se valida en base a la fecha de inicio de operación de servicios (de campo o urbano segíun
	 * corresponda) o bien, en caso de no existir fecha de inicio de operaciones se valida
	 * en base al identificador de convenio.
	 * 
	 * @param cveMunicipio
	 * @param numModalidad
	 * @param fechaDeMovimiento
	 */
	void validarTipoAmbitoPorMunicipio(String cveMunicipio, String numModalidad, Date fechaDeMovimiento)throws GestionPatronalBusinessException;
	
	boolean estaParametroRIFHabilitado();
}
