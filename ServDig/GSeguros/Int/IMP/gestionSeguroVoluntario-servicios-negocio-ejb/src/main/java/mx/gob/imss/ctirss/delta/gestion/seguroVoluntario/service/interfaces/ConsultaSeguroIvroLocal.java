/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.exception.IvroException;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoSeguro;
import mx.gob.imss.ctirss.delta.persistence.DicModalidad;
import mx.gob.imss.ctirss.delta.persistence.DitSeguroIvro;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;
import mx.gob.imss.digital.modelo.cobranza.Pago;
import mx.gob.imss.digital.modelo.persona.Fisica;
import mx.gob.imss.digital.modelo.persona.Persona;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;
import mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvro;

import javax.ejb.Local;
import java.util.List;

/**
 * Servicio para a consuta de seguros IVRO
 * @author NOVUTECK1
 *
 */
@Local  
public interface ConsultaSeguroIvroLocal {

    /**
     * Obtiene el ultimo seguro asociado a una persona, null si no hay seguros
     * @param persona la persona a buscar su seguro
     * @return el seguro encontrado
     */
    SeguroIvro getUltimoSeguro(Persona persona);
    
    /**
     * Obtiene el seguro asociado su id
     * @param id identificador del seguro
     * @return el seguro encontrado
     * 
     */
    SeguroIvro buscaSeguroPorId(long id);

    /**
     * Obtiene el seguro asociado su id
     * validando que no tenga una LC del mismo mes
     * para evitar duplicados
     * @param id identificador del seguro
     * @return el seguro encontrado
     *
     */
    SeguroIvro buscaSeguroPorIdValidaPago(long id);
    
    /**
     * Busca los seguros asociados a una person, si se recibe la modalidad solo los que sean de esa modalidad,
     * ademas si el parametro vigente es true busca todos los activos o que se encuentren en periodo de renovacion
     * si el parametro es false busca los cancelados o que ya terminaron su vigencia, si no se recibe el parametro
     * no se toma en cuenta las vigencias
     * @param persona la persona asociada al seguro
     * @param modalidades la modalidades del seguro
     * @param estados indicador de ls estados del seguro
     * @return los seguros encontrados.
     */
    List<SeguroIvro> buscaSegurosPersona(Persona persona, List<DicModalidad> modalidades, 
            List<DicEstadoSeguro> estados);
    
    /**
     * Busca un sguro asociado a una compra
     * @param idCompra el identificador de la comrpa
     * @return el seguro asociado
     * @throws IvroException Seguro no asociado a ninguna compra
     */
    DitSeguroIvro buscaSeguroCompra(Long idCompra) throws IvroException;
    
        
    /**
     * Busca el ultimo seguro individual asociado a una persona
     * o el que este activo en periodo de renovacion y el nuevo por pagar  
     * @param persona la persona a buscar sus seguros individuales
     * @return La lista de seguros encontrados
     */
    List<SeguroIvro> buscaUltimosSegurosIndividual(Persona persona);
    /**
     * Busca todos los seguros activos asociados a un patron, o los que estene el mes de 
     * renovacion extemporania y que no esten renovados a´n
     * @param persona Los datos del patron asociado a los segurps
     * @return los seguros domesticos encontrados
     */
    List<SeguroIvro> buscaSegurosDomesticoPatron(Persona persona);
    
    /**
     * Busca todos los seguros familiares asociados a un solicitante
     * 
     * @param persona - con el idPersona setteado
     * @return los seguros familiares encontrados
     */
    List<SeguroIvro> buscaSegurosFamiliares(Persona persona);
    
    /**
     * Busca todos los seguros de Continuación Voluntaria asociados a un solicitante
     * 
     * @param persona - con el idPersona setteado
     * @return los seguros familiares encontrados
     */
    List<SeguroIvro> buscaSegurosCVRO(Persona persona);
    
    /**
     * Busca todos los seguros activos que ya paso su fecha de terminacion 
     * @return la lista de seguros encontrados
     */
    List<DitSeguroIvro> buscaSegurosAConcluir();
    
    /**
     * Busca todos los seguros en periodo de renovacio
     * @return los seguros en periodo de renovacion
     */
    List<SeguroIvro> buscaSegurosPorRenovar();
    
    /**
     * Busca todos los seguros que esten por vencer su fecha de pago
     * @return la lista de seguros por vencer
     */
    List<SeguroIvro> buscaSegurosPorVencer();

	/**
	 * Busca todos los seguros CVRO candidatos a generarles su línea de captura
	 * automática
	 * 
	 * @return la lista de seguros por vencer
	 */
    List<SeguroIvro> buscaSegurosCvroLineaCapturaAutomatica();
    
    /**
     * Busca los seguros CVRO con su último pago pagado
     * 
     * @return
     */
    List<DitSeguroIvro> buscaSegurosCvroUltimoPagoPagado();

    /**
     * Busca todos los seguros activos de la modalidad 40
     * @return la lista de seguros activos
     */
    List<SeguroIvro> buscaSegurosActivosMod40();
    
    /**
	 * Obtiene el último pago asociado al idSeguro recibido
	 * 
	 * @param idSeguro
	 * @return
     * @throws SUAException 
	 */
    Pago buscaUltimoPagoSeguro(long idSeguro) throws SUAException;
    
    /**Obtiene el Tramite asociado al Seguro Individual
     * @param idSeguro
     * @return
     */
    TramiteSeguroIvro buscaTramiteSeguroIndividual(long idSeguro);

    /**
     * Obtiene los idSeguros´s que se les generará las nuevas LC´s automáticas del mes
     * @return idSeguros´s que se les generará las nuevas LC´s automáticas del mes
     */
    List<Long> buscaSegurosCvroLCAutomatica();

    /**
     * Obtiene los idSeguros´s que se les dará de baja mensual CVRO
     * @return idSeguros´s que se les dará de baja mensual CVRO
     */
    List<Long> buscaSegurosBajaMensualCvro();

    /**
     * Obtiene los idSeguros´s que se les dará de baja por mora
     * @return idSeguros´s que se les dará de baja por mora
     */
    List<Long> buscaSegurosBajaPorMora();

    /**
     * Busca todos los seguros activos que ya paso su fecha de terminacion
     *
     * @return idSeguro_s que se encontraron
     */
    List<Long> buscaSegurosPorConcluir();

    /**
     *
     * @param titular
     * @return
     */
    List<SeguroIvro> buscaNuevosSegurosFamiliares(Fisica titular);

    /**
     * Guarda la compra nueva que se genero con la nueva cotizacion, en cambio de año cambia el salario
     * @param cveIdCompraAnt
     * @param cveIdCompraNva
     * @param cveIdSeguroIvro
     * @return
     */
    Boolean guardaHistSeguroCompra(Long cveIdCompraAnt, Long cveIdCompraNva, Long cveIdSeguroIvro);

    /**
     * Obtiene los ultimos seguros aptos para realizar una renovacion
     * @param persona
     * @return
     */
    SeguroIvro buscaUltimoSeguroIVRO(Persona persona);

    
    /**Valida si el XML del Tramite asociado al idSeguro es correcto, en caso contrario, lo actualiza
     * @param idSeguro
     * @return
     */
    void corrigeDetalleTramitePorSeguroIndividual(long idSeguro);
}
