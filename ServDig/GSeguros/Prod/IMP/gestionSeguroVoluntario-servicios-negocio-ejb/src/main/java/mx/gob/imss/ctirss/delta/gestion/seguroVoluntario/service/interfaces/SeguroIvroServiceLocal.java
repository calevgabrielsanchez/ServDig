/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces;

import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.model.DatosMovSeguro;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Beneficiario;
import mx.gob.imss.ctirss.delta.persistence.bajas.LoteProcesamientoBaja;
import mx.gob.imss.digital.modelo.cobranza.ActualizacionCompra;
import mx.gob.imss.digital.modelo.persona.Persona;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;

import javax.ejb.Local;
import java.util.List;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.exception.IvroException;

import java.util.ArrayList;
import java.util.Date;

/**
 * Interfaz para el manejo de los seguros ivr, altas y bajas
 * @author NOVUTECK1
 *
 */
@Local
public interface SeguroIvroServiceLocal {

    /**
     * Dada una lista de compras se activa su seguro es decir se marca como valido y en caso de 
     * de ser nuevo se regresan las personas para darlas de alta despues en sindo
     * @param comprasPagadas el objeto que contiene los datos de las compras pagadas
     * @return la lista de personas a dar de alta
     */
    List<DatosMovSeguro> activaSeguro(ActualizacionCompra comprasPagadas);
    
    List<DatosMovSeguro> generaMovActivaSeguro(ActualizacionCompra comprasPagadas);

    /**
     * Dada una lista de compras vencidas se cancelan los seguros asociados y se verifica si alguno 
     * de los aseguros aplica para baja generando la informacion necesaria para dar de baja las personas
     * @param comprasVencidas lal ista de compras vencidas
     * @return la lista depersonas a dar de baja
     */
    List<DatosMovSeguro> venceSeguro(ActualizacionCompra comprasVencidas);       
    
    List<DatosMovSeguro> generaMovVencSeguro(ActualizacionCompra comprasVencidas);

    /**
     * Verifica, si un seguro ya termino su periodo de vigencia y lo marca como terminado y verifica
     * si las personas asociadas a el ya cuentan con uno nuevo pagado, si no es asi regresa estas 
     * personas para dar las bajas a sindo
     * @return las personas que causan baja por no renovar seguro
     */
    List<DatosMovSeguro> concluirSegurosVigencia();
    
    /**
     * Cancela el seguro de una persona por perdida de riss, si es que esta contaba con alguno
     * Ademas genera un nuevo seguro por el periodo restante del seguro con su nueva cotizaion
     * @param persona la persona a la cual se le cancelo el seguro
     * @return la persona a la cual se le cancelo el seguro
     */
    Persona cancelaSeguroRiss(Persona persona);
    
    /**
     * Dada una lista de compras se activa su seguro es decir se marca como valido y en caso de 
     * de ser nuevo se regresan las personas para darlas de alta despues en sindo
     * @param comprasPagadas el objeto que contiene los datos de las compras pagadas
     * @return la lista de personas a dar de alta
     */
    List<SeguroIvro> activaSeguros(ActualizacionCompra comprasPagadas);
    /**
     * Dada una lista de compras vencidas se cancelan los seguros asociados y se verifica si alguno 
     * de los aseguros aplica para baja generando la informacion necesaria para dar de baja las personas
     * @param comprasVencidas lal ista de compras vencidas
     * @return la lista depersonas a dar de baja
     */
    List<SeguroIvro> venceSeguros(ActualizacionCompra comprasVencidas);
       
    
    /**
     * Verifica, si un seguro ya termino su periodo de vigencia y lo marca como terminado y verifica
     * si las personas asociadas a el ya cuentan con uno nuevo pagado, si no es asi regresa estas 
     * personas para dar las bajas a sindo
     * @return las personas que causan baja por no renovar seguro
     */
    List<SeguroIvro> concluirSeguroVigencia();
    
    /**
     * Se revisa si existe solicitud de rechazo para la persona que requiere comprar ivro individual
     * @param idPersona
     * @return boolean
     */
    boolean existeRechazo(Long idPersona, Long idModalidad);

    /**
     * @param datos Lista de beneficiarios a los cuales se les cancelara la solicitud de rechazo
     */
    void desactivarSegurosRechazados(List<DatosMovSeguro> datos);
    
	/**
	 * Devuelve la lista de seguros a CVRO a dar de baja en SINDO de forma
	 * mensual
	 * 
	 * @return
	 */
	List<DatosMovSeguro> obtenerSegurosCvroBajaMensual();

    /**
     * Genera los movimientos necesarios para la baja mensual cvro
     * @param seguroIvro El seguro al que se le generar� los movimientos de baja
     * @return
     */
    List<DatosMovSeguro> generaDatosMovimientosBajaMensualCvro(SeguroIvro seguroIvro);

    /**
	 * Servicio que vence los seguros asociados a las compras vencidas recibidas de la modalidad 40
	 * 
	 * @param comprasVencidas
	 */
	void venceSegurosPorMoraMod40(ActualizacionCompra comprasVencidas);

    /**
     * Servicio que pone en baja el seguro que ha sido reemplazada por alguna modalidad de r�gimen obligatorio
     *
     * @param SeguroIvro seguroIvro
     */
    List<DatosMovSeguro> bajaDeSeguroPorReingresoRO(SeguroIvro seguroIvro);

    /**
     * Actualiza el seguro a estado de baja por mora, su compra a vencida y sus pagos pendientes a vencidos
     * @param seguroIvro El seguro con �nicamente el id como obligatorio que se actualizar�
     */
    void bajaDeSeguroPorMora(SeguroIvro seguroIvro);

    String obtenZonaSalarialOriginal(String cveEnt,String cveMun);

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
    mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.DetalleReingresoRODTO obtenerDetalleReingresoParaJSP(Long idSeguroIvro);
    
	/**
	 * Ejecuta proceso automático completo de baja por MORA. Busca seguros con pagos
	 * vencidos y ejecuta baja masiva.
	 */
	Long ejecutarProcesoMoraAutomatico(String usuarioOperador) throws Exception;

	/**
	 * Ejecuta baja por MORA para un seguro específico. Crea registros completos de
	 * auditoría y actualiza estados.
	 */
	boolean ejecutarBajaPorMora(Long cveIdSeguroIvro, Long cveIdLote, String usuarioOperador);

	/**
	 * Obtiene detalle de baja por MORA para mostrar en JSP. Retorna DTO con
	 * información de mora o null si no existe.
	 */
	mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.DetalleMoraDTO obtenerDetalleMoraParaJSP(Long idSeguroIvro);
	
	/**
	 * Obtiene detalle de baja por MORA para mostrar en JSP. Retorna DTO con
	 * información de mora o null si no existe.
	 */
	mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.DetalleBajaExpresaDTO obtenerDetalleBajaExpresaParaJSP(Long idSeguroIvro);

	/**
	 * Actualiza solo el estado de DIT_SEGURO_IVRO a BAJA_POR_MORA. No crea bitácora
	 * de auditoría.
	 */
	boolean actualizarEstadoSeguroMora(Long cveIdSeguroIvro);

	/**
	 * Crea solo registros de bitácora para seguros ya dados de baja. Útil para
	 * completar auditoría de bajas ejecutadas por procesos legados.
	 */
	Long registrarBitacoraBajaMora(Long cveIdSeguroIvro, Long cveIdLote, String usuarioOperador);

	/**
	 * Busca seguros con estado BAJA_POR_MORA sin bitácora y crea registros
	 * faltantes. Proceso de migración para bajas ejecutadas por sistema legado.
	 */
	Long completarBitacoraBajasMoraExistentes(Date fechaInicio, Date fechaFin, String usuarioOperador);

	/**
	 * Obtiene el tipo de baja actual del seguro. Retorna MORA, REINGRESO_RO,
	 * EXPRESA o null si no está en baja.
	 */
	String obtenerTipoBajaActual(Long idSeguroIvro);

	Long solicitarBajaExpresa(Long cveIdSeguroIvro, String motivo, String usuario, String ipSolicitud) throws Exception;

	boolean confirmarBajaExpresa(String token, String ipConfirmacion) throws Exception;
	
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

	  
	  /**
	   * Crear lote de bitacoras de baja por mora
	   *
	   * @param usuarioOperador Usuario que ejecuta
	   * @return Lote guardado
	   */
	  LoteProcesamientoBaja guardarLoteParaBitacorasDeBajaMora(String usuarioOperador);
	  
	  /**
	   * Cerrar lote de bitacoras de baja por mora, para registro de resultado.
	   *
	   * @param lote: Lote a actualizar
	   * @param lote: registrosTotal a bajas totales, ya sean exitosas o con error
	   * @param totalExitosos, total de bitacoras exitosamente realizadas
	   * @param totalErrores, total de bitacoras que hubo un error
	   */
	  void cerrarLoteDeBitacorasDeBajaMora(LoteProcesamientoBaja lote, int size, int size2, int totalErrores);

	  
	  /**
	   * Antes de enviar el correo hace una consulta, si es que las bitacora de baja
	   * Se guardo exitosamente.
	   *
	   * @param idBajasExitosas: Ids de DIT_BAJA_SEGURO.CVE_ID_BAJA que en pasos anteriores
	   * resultaron como exitosas, pero por separacion de resposabilidades y transaccion,
	   * se pone por separado.
	   */
	  void enviarCorreoConValidacion(ArrayList<Long> bajasExitosas);

}
