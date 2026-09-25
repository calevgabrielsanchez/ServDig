/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.exception.IvroException;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.HistorialUltimoSeguroCotizadoDTO;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Beneficiario;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.digital.modelo.cobranza.ActualizacionCompra;
import mx.gob.imss.digital.modelo.cobranza.Pago;
import mx.gob.imss.digital.modelo.persona.Persona;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;
import mx.gob.imss.digital.modelo.sindo.MovimientoTrabajadorSindo;
import mx.gob.imss.digital.modelo.sindo.MovimientosTrabajadorSindo;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.DetalleReingresoRODTO;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.DetalleMoraDTO;
import java.util.Date;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.DetalleBajaExpresaDTO ;

/**
 * Servicios para el manejo de los seguros IVRO
 * @author NOVUTECK1
 *
 */
@Remote
public interface SeguroIvroServiceRemote {

    void guardarHistorialUltimoSeguro(HistorialUltimoSeguroCotizadoDTO historial)
            throws IvroException;
    
    /**
     * Dada una lista de compras se activa su seguro es decir se marca como valido y en caso de 
     * de ser nuevo se regresan las personas para darlas de alta despues en sindo
     * @param comprasPagadas el objeto que contiene los datos de las compras pagadas
     * @return la lista de personas a dar de alta
     */
    MovimientosTrabajadorSindo activaSeguro(ActualizacionCompra comprasPagadas) throws IvroException;
    
    MovimientosTrabajadorSindo generaMovimientosAlta(ActualizacionCompra comprasPagadas) throws IvroException;

    /**
     * Dada una lista de compras vencidas se cancelan los seguros asociados y se verifica si alguno 
     * de los aseguros aplica para baja generando la informacion necesaria para dar de baja las personas
     * @param comprasPagadas lal ista de compras pagadas
     * @return la lista depersonas a dar de baja
     */
    MovimientoTrabajadorSindo[] venceSeguro(ActualizacionCompra comprasVencidas) throws IvroException;

    MovimientoTrabajadorSindo[] generaMovimientosBaja(ActualizacionCompra comprasVencidas) throws IvroException;

    /**
     * Dado un seguro lo da de baja con el motivo de que fue reincorporado a alguna de las modalidades del
     * r�gimen obligatorio
     * @param SeguroIvro seguro a dar de baja por reingreso a r�gimen obligatorio
     * @return la lista de movimiemtos originados para sindo dada la baja
     */
    MovimientoTrabajadorSindo[] bajaDeSeguroPorReingresoRO(SeguroIvro seguroIvro) throws IvroException;
    
    /**
     * Verifica, si un seguro ya termino su periodo de vigencia y lo marca como terminado y verifica
     * si las personas asociadas a el ya cuentan con uno nuevo pagado, si no es asi regresa estas 
     * personas para dar las bajas a sindo
     * @return las personas que causan baja por no renovar seguro
     */
    MovimientoTrabajadorSindo[] vencimientoSeguroVigencia() throws IvroException;
    
    /**
     * Cancela el seguro de una persona por perdida de riss, si es que esta contaba con alguno
     * Ademas genera un nuevo seguro por el periodo restante del seguro con su nueva cotizaion
     * @param persona la persona a la cual se le cancelo el seguro
     * @return la persona a la cual se le cancelo el seguro
     */
    Persona cancelaSeguroRiss(Persona persona) throws IvroException;
    
    /**
     * Dada una lista de compras se activa su seguro es decir se marca como valido y en caso de 
     * de ser nuevo se regresan las personas para darlas de alta despues en sindo
     * @param comprasPagadas el objeto que contiene los datos de las compras pagadas
     * @return la lista de personas a dar de alta
     */
    SeguroIvro[] activaSeguros(ActualizacionCompra comprasPagadas);
    /**
     * Dada una lista de compras vencidas se cancelan los seguros asociados y se verifica si alguno 
     * de los aseguros aplica para baja generando la informacion necesaria para dar de baja las personas
     * @param comprasPagadas lal ista de compras pagadas
     * @return la lista depersonas a dar de baja
     */
    SeguroIvro[] venceSeguros(ActualizacionCompra comprasVencidas);
       
    
    /**
     * Verifica, si un seguro ya termino su periodo de vigencia y lo marca como terminado y verifica
     * si las personas asociadas a el ya cuentan con uno nuevo pagado, si no es asi regresa estas 
     * personas para dar las bajas a sindo
     * @return las personas que causan baja por no renovar seguro
     */
    SeguroIvro[] concluirSeguroVigencia();
    
	/**
	 * Servicio que relaciona el pago generado autom�ticamente por el OSB al
	 * seguro CVRO correspondiente, tambi�n actualiza la cotizaci�n original del
	 * seguro para llevar un hist�rico de los periodos calculados
	 * 
	 * @param seguro
	 */
	void asociaPagoAutomaticoSeguro(SeguroIvro seguro);
 
	/**
	 * Servicio que genera los Movimientos 02 a enviar a SINDO para 
	 * la baja mensual de todos los seguros CVRO con su �ltimo pago pagado
	 * 
	 * @return
	 * @throws IvroException
	 */
	MovimientoTrabajadorSindo[] bajaMensualSindoSeguroCvro()
			throws IvroException;

	/**
	 * Servicio que vence los seguros asociados a las compras vencidas recibidas de la modalidad 40
	 * 
	 * @param comprasVencidas
	 */
	void venceSegurosPorMoraMod40(ActualizacionCompra comprasVencidas);
	
	/**
	 * Servicio que checa si el seguro recibido tiene ya un pago generado en el
	 * periodo a calcular por el proceso autom�tico de generaci�n de l�neas de
	 * captura para los seguros CVRO.
	 * 
	 * @param idSeguro
	 * @return
	 */
	Pago validarGeneracionNuevoPeriodoPagoCvro(long idSeguro);

	/**
	 * Servicio que evalua los pagos de un seguro para saber si se pondran disponibles para imprimirlos,
	 * dependiendo de si el asegurado puede continuar con esa modalidad
	 *
	 * @param seguro Seguro que se evaluara
	 * @return El seguro con los pagos actualizados de acuerdo a su disponibilidad a imprimir
	 */
	SeguroIvro confirmaPagosDeSeguro(SeguroIvro seguro);

	String obtenZonaSalarialOriginal(String cve, String mun);

	Fisica findPersonabyPago(Long cveIdPago) throws IvroException;

    boolean insertaCancelacionCuestionario(Beneficiario beneficiarioCancelar, String numSolicitud);

    Long recuperaIdAsignacionPorNSS(String nss);

    boolean verificaBeneficiarioConEnfermedad(Long idAsignacion);
	
	/**
 * Procesa archivo SINDO de bajas por reingreso a RO.
 *
 * @param contenidoArchivo Contenido del archivo (174 chars por l�nea)
 * @param nombreArchivo Nombre del archivo original
 * @param usuario Usuario que ejecuta
 * @return ID del lote creado
 * @throws IvroException Si hay error en procesamiento
 */
Long procesarArchivoSindoBajas(byte[] contenidoArchivo,
                                String nombreArchivo,
                                String usuario) throws IvroException;

/**
 * Carga archivo SINDO a staging SIN procesar (para procesamiento por fases).
 *
 * @param contenidoArchivo Contenido del archivo
 * @param nombreArchivo Nombre del archivo original
 * @param usuario Usuario que ejecuta
 * @return ID del lote creado
 * @throws IvroException Si hay error
 */
Long cargarArchivoSoloStaging(byte[] contenidoArchivo,
                               String nombreArchivo,
                               String usuario) throws IvroException;

/**
 * Reprocesa l�neas de un lote.
 *
 * @param idLote ID del lote a reprocesar
 * @param soloErrores TRUE = solo l�neas en ERROR, FALSE = todas pendientes
 * @param usuario Usuario que ejecuta
 * @return N�mero de l�neas procesadas exitosamente
 * @throws IvroException Si hay error
 */
int reprocesarLote(Long idLote,
                   boolean soloErrores,
                   String usuario) throws IvroException;

/**
 * Procesa rango de l�neas de un lote (para procesamiento por fases).
 *
 * @param idLote ID del lote
 * @param lineaInicio L�nea inicial (inclusive)
 * @param lineaFin L�nea final (inclusive)
 * @param usuario Usuario que ejecuta
 * @return N�mero de l�neas procesadas exitosamente
 * @throws IvroException Si hay error
 */
int procesarPorRangoLineas(Long idLote,
                            int lineaInicio,
                            int lineaFin,
                            String usuario) throws IvroException;

/**
 * Obtiene detalle de baja por reingreso a RO para mostrar en JSP (Requerimiento 6).
 *
 * @param idSeguroIvro ID del seguro IVRO
 * @return DTO con informaci�n del reingreso, o NULL si no aplica
 */
DetalleReingresoRODTO obtenerDetalleReingresoParaJSP(Long idSeguroIvro);

/**
 * Ejecuta proceso automático de baja por mora.
 */
Long ejecutarProcesoMoraAutomatico(String usuarioOperador) throws Exception;

/**
 * Ejecuta baja por mora para un seguro específico.
 */
boolean ejecutarBajaPorMora(Long cveIdSeguroIvro, Long cveIdLote, String usuarioOperador);

/**
 * Obtiene detalle de baja por mora para mostrar en JSP.
 */
DetalleMoraDTO obtenerDetalleMoraParaJSP(Long idSeguroIvro);

/**
 * Obtiene detalle de baja expresa para JSP.
 */
DetalleBajaExpresaDTO obtenerDetalleBajaExpresaParaJSP(Long idSeguroIvro);


/**
 * Obtiene el tipo de baja actual del seguro (MORA, REINGRESO_RO, EXPRESA) Solo
 * retorna valor si el seguro está actualmente en estado de baja.
 *
 * @param idSeguroIvro ID del seguro a consultar
 * @return "MORA", "REINGRESO_RO", "EXPRESA" o NULL si no está en baja
 */
String obtenerTipoBajaActual(Long idSeguroIvro);

boolean actualizarEstadoSeguroMora(Long cveIdSeguroIvro);

Long registrarBitacoraBajaMora(Long cveIdSeguroIvro, Long cveIdLote, String usuarioOperador);

Long completarBitacoraBajasMoraExistentes(Date fechaInicio, Date fechaFin, String usuarioOperador);

/**
 * Solicitar baja expresa con generación de token
 *
 * @param cveIdSeguroIvro ID del seguro a dar de baja
 * @param motivo          Motivo de baja (opcional, capturado del usuario)
 * @param usuario         Usuario autenticado que solicita
 * @param ipSolicitud     IP del cliente que solicita
 * @return ID de la solicitud creada
 * @throws Exception Si el seguro no está activo o ya tiene solicitud pendiente
 */
Long solicitarBajaExpresa(Long cveIdSeguroIvro, String motivo, String usuario, String ipSolicitud) throws Exception;

/**
 *
 * @param token          Token UUID recibido del link del correo
 * @param ipConfirmacion IP del cliente que confirma
 * @return true si la baja se ejecutó correctamente
 * @throws Exception Si el token es inválido, expirado o ya usado
 */
boolean confirmarBajaExpresa(String token, String ipConfirmacion) throws Exception;

/**
 * Ejecuta baja por MORA para un grupo específico de seguros
 *
 * Permite control granular sobre qué seguros dar de baja (staging/producción)
 *
 * @param idsSeguro       Lista de IDs de seguros a dar de baja
 * @param usuarioOperador Usuario que ejecuta la operación
 * @return ID del lote creado para esta ejecución
 * @throws Exception Si ocurre error en el proceso
 */
Long ejecutarBajaPorMoraLista(List<Long> idsSeguro, String usuarioOperador) throws Exception;

/**
* Marca pagos POR_PAGAR como VENCIDOS hasta cierta fecha (masivo).
*
* @param fechaCorte Fecha límite (null = día 17 mes actual)
* @param usuarioOperador Usuario que ejecuta
* @return Cantidad de pagos marcados
*/
Integer marcarPagosVencidosPorFecha(Date fechaCorte, String usuarioOperador);
 
/**
* Marca pagos POR_PAGAR como VENCIDOS para UN seguro.
*
* @param cveIdSeguroIvro ID del seguro
* @param fechaCorte Fecha límite (null = día 17 mes actual)
* @param usuarioOperador Usuario que ejecuta
* @return Cantidad de pagos marcados
*/
Integer marcarPagosVencidosSeguro(Long cveIdSeguroIvro, Date fechaCorte, String usuarioOperador);
 
/**
* Da de baja seguros con 2+ pagos VENCIDOS consecutivos (masivo).
*
* @param fechaCorte Fecha límite (null = procesa todos)
* @param usuarioOperador Usuario que ejecuta
* @return ID del lote creado
*/
Long darBajaPorMoraHastaFecha(Date fechaCorte, String usuarioOperador);
 
/**
* Da de baja UN seguro por mora (si califica).
*
* @param cveIdSeguroIvro ID del seguro
* @param usuarioOperador Usuario que ejecuta
* @return true si se dio de baja, false si no califica
*/
Boolean darBajaPorMoraSeguro(Long cveIdSeguroIvro, String usuarioOperador);
 
/**
* Completa bitácoras faltantes de seguros en BAJA_POR_MORA (masivo).
*
* @param fechaDesde Solo seguros >= fecha (null = todos)
* @param dummy No usado (compatibilidad)
* @param usuarioOperador Usuario que ejecuta
* @return ID del lote creado
*/
Long completarBitacorasBajaMoraDesde(Date fechaDesde, Date dummy, String usuarioOperador);

}
