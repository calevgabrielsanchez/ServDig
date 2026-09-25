/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces;

import javax.ejb.Remote;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;

import mx.gob.imss.digital.modelo.persona.Persona;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;
import mx.gob.imss.digital.modelo.seguros.SegurosIvro;
import mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvro;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.digital.modelo.domicilio.Domicilio;

import java.util.List;

/**
 * Servicio para la consulta de seguros Ivro
 * @author NOVUTECK1
 *
 */
@Remote
public interface ConsultaSeguroIvroServiceRemote {

    /**
     * Busca el ultimo seguro individual asociado a una persona
     * o el que este activo en periodo de renovacion y el nuevo por pagar  
     * @param persona la persona a buscar sus seguros individuales
     * @return La lista de seguros encontrados
     */
    SegurosIvro buscaUltimosSegurosIndividual(Persona persona);
    /**
     * Busca todos los seguros activos asociados a un patron, o los que estene el mes de 
     * renovacion extemporania y que no esten renovados a´n
     * @param persona Los datos del patron asociado a los segurps
     * @return los seguros domesticos encontrados
     */
    SegurosIvro buscaSegurosDomesticoPatron(Persona persona);
    
    /**
     * Busca todos los seguros familiares asociados a un solicitante
     * 
     * @param persona - con el idPersona setteado
     * @return los seguros familiares encontrados
     */
    SegurosIvro buscaSegurosFamiliares(Persona persona);
    
    /**
     * Busca todos los seguros de Continuación Voluntaria asociados a un solicitante
     * 
     * @param persona - con el idPersona setteado
     * @return los seguros familiares encontrados
     */
    SegurosIvro buscaSegurosCVRO(Persona persona);
    
    /**
     * Busca el seguro por su identificador 
     * @param seguro el seguro a buscar
     * @return el seguro encontrado
     */
    SeguroIvro buscaSeguro(SeguroIvro seguro);

    /**
     * Busca el seguro por su identificador
     * @param seguro el seguro a buscar
     * @return el seguro encontrado
     */
    SeguroIvro buscaSeguroDetalle(SeguroIvro seguro);

    /**
     * Busca el seguro por id
     * @param seguro el seguro a buscar
     * @return el seguro encontrado
     */
    SeguroIvro buscaSeguroConEmailNotificable(SeguroIvro seguro);

    /**
     * Busca los seguros que se les debe generar un nuevo pago del mes (LC)
     * @return Lista de id's de los seguros para generarles la nueva LC
     */
    List<Long> buscaSegurosCvroLCAutomatica();

    /**
     * Busca los seguros que se les debe generar la baja mensual cvro, validando que su último pago haya sido cubierto
     * @return Lista de id's de los seguros para generarles la baja mensual
     */
    List<Long> buscaSegurosBajaMensualCvro();

    /**
     * Busca los seguros cvro que se darán de baja por mora debido a que ya aplican sus 2 últimos pagos como vencidos
     * @return Lista de id's de los seguros para actualizarlos a baja por mora
     */
    List<Long> buscaSegurosBajaPorMora();

    /**
	 * Busca todos los seguros CVRO candidatos a generarles su línea de captura
	 * automática y por cada seguro encontrado obtiene el correo a utilizar para
	 * notificar la generación de la nueva línea de captura
	 * 
	 * @return la lista de seguros por vencer
	 */
	SegurosIvro buscaSegurosCvroLineaCapturaAutomatica();

    /**
     * Busca todos los seguros de Continuación Voluntaria activos
     *
     * @return los seguros activos encontrados
     */
    SegurosIvro buscaSegurosActivosMod40 ();
    
    /**Obtiene el Tramite asociado al Seguro Individual
     * @param seguro
     * @param seguroIvro
     * @return
     */
    TramiteSeguroIvro buscaTramiteSeguroIndividual(SeguroIvro seguro);
    
    /**
     *
     * @param domicilio
     * @param idPersona
     * @throws DomicilioNoValidoException
     */
    void guardarYAsociarDomiciliosPersona(Domicilio domicilio,Long idPersona) throws DomicilioNoValidoException,DomicilioNoLocalizadoException;

    /**
     * Busca todos los seguros activos que ya paso su fecha de terminacion
     *
     * @return idSeguro_s que se encontraron
     */
    List<Long> buscaSegurosPorConcluir();

    /**
     * Se valida si el ultimo seguro anterior esta en estado concluido o vencido para quitar beneficio RISS
     * @param idPersona
     * @return
     */
    boolean validaSeguroAnteriorVencidoCancelado(Long idPersona);
}
