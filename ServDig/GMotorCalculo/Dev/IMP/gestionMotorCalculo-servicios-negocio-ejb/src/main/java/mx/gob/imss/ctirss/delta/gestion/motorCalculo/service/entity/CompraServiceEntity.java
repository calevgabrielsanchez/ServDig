/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.entity;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CompraServiceLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.ParametrosEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.PublicaCompraVencida;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.util.CompraFactoryUtil;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.util.CotizadorFactoryUtil;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.EstadoCompraEnum;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.EstadoPagoEnum;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.FormaPagoEnum;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.SUAConstants;
import mx.gob.imss.ctirss.delta.model.enums.EstadoSeguroIvroEnum;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoSeguro;
import mx.gob.imss.ctirss.delta.persistence.DicModalidad;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitSeguroIvro;
import mx.gob.imss.ctirss.delta.persistence.cobranza.*;
import mx.gob.imss.digital.modelo.cobranza.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.*;

/**
 * @author NOVUTECK1
 * 
 */
@Stateless(name = "compraServiceEntity", mappedName = "compraServiceEntity")
public class CompraServiceEntity implements CompraServiceLocal {

    /**
     * Logger de la clase
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CompraServiceEntity.class);

    /**
     * Query para obtener pagos vencidos
     */
	private static final String PAGOS_VENCIDOS = "SELECT distinct pago FROM DitSeguroIvro seguro "
			+ " JOIN seguro.ditCompra compra JOIN compra.ditPagos pago "
			+ " WHERE pago.fecLimitePago < :fechaLimite "
			+ " AND pago.dicEstadoPago = :estadoPorPagar "
			+ " AND seguro.dicModalidad != :modalidad " ;
	
	/**
     * Query para obtener Seguros con pagos Modalidad 40
     */
	private static final String SEGUROS_PAGOS_POR_PAGAR_MOD_40 = "SELECT distinct seguro FROM DitSeguroIvro seguro "
                                                                + " JOIN seguro.ditCompra compra JOIN compra.ditPagos pago "
                                                                + " WHERE seguro.dicModalidad = :modalidad " +
                                                                " AND seguro.dicEstadoSeguro IN (:estadosSeg) " +
                                                                " AND pago.dicEstadoPago = :estadoPago";

	/**
     * Query para obtener Seguros con pagos Modalidad 40
     */
	private static final String PAGOS_POR_COMPRA_POR_FECHA_MOD_40 = "SELECT distinct pago FROM DitSeguroIvro seguro "
                                                                    + " JOIN seguro.ditCompra compra JOIN compra.ditPagos pago "
                                                                    + " WHERE seguro.dicModalidad = :modalidad "
                                                                    + " AND pago.ditCompra = :compra"
                                                                    + " AND pago.fecLimitePago = :fechaLimitePago";
	
    /**
     * Query para obtener compras vencidas
     */
  //Se agrega para considerar vencimiento por caducidad de segundo pago o posteriores en pagos bimestrales
    private static final String COMPRAS_VENCIDAS = "SELECT distinct compra FROM DitSeguroIvro seguro "
            + " JOIN  seguro.ditCompra compra  JOIN compra.ditPagos pago "
            + " WHERE (compra.dicEstadoCompra = :compraPorPagar or compra.dicEstadoCompra = :compraActiva ) " 
            + " AND pago.dicEstadoPago = :estadoVencido "
            + " AND seguro.dicModalidad != :modalidad " ;

    /**
     * Query para obtener las compras y pagos bimestrales a ser marcados como
     * pagados
     */
    private static final String GET_PAGO_LC_BIMESTRAL = "SELECT compra, pago FROM DitPago pago "
            + " JOIN pago.ditCompra compra WHERE compra.dicFormaPgo = :bimestral AND pago.desLineaCaptura in (:lc)";

    /**
     * Query para obtener las compras y pagos bimestrales a ser marcados como
     * pagados
     */
    private static final String GET_PAGO_LC_ANUAL = "SELECT pago FROM DitPago pago JOIN pago.ditCompra compra "
            + "WHERE compra.dicFormaPgo = :anual AND pago.desLineaCaptura in (:lc)";
    
    /**
     * Query para obtener las compras y pagos bimestrales a ser marcados como
     * pagados
     */
    private static final String GET_PAGO_LC_MENSUAL_CVRO = "SELECT compra, pago FROM DitSeguroIvro seguro "
            + " JOIN seguro.ditCompra compra JOIN compra.ditPagos pago "
    		+ " WHERE compra.dicFormaPgo = :mensual "
            + " AND pago.desLineaCaptura in (:lc)"
            + " AND seguro.dicModalidad = :modalidadCvro " ;

    /**
     * Query para obtener las compras y pagos bimestrales a ser marcados como
     * pagados
     */
    private static final String GET_COMPRA_LC_ANUAL = "SELECT Distinct compra FROM DitPago pago "
            + " JOIN pago.ditCompra compra WHERE compra.dicFormaPgo = :anual AND pago.desLineaCaptura in (:lc)";
    /**
     * Numero maximo de elementos que puede tener una lista en una consulta
     */
    private static final Integer MAX_VALUE_LIST = 1000;

    private static final String GET_PERSONA_LC_PAGO =
            "SELECT PERSONA.CVE_ID_PERSONA, PERSONA.CVE_ID_PAIS, PERSONA.CVE_ID_SEXO, PERSONA.CVE_ID_ESTADO_CIVIL, "+
                    "PERSONA.NOM_NOMBRE, PERSONA.NOM_PRIMER_APELLIDO, PERSONA.NOM_SEGUNDO_APELLIDO, PERSONA.CURP, PERSONA.RFC, "+
                    "PERSONA.FEC_NACIMIENTO,PERSONA.OBSERVACIONES, PERSONA.IND_PER_AUTORIZADA,PERSONA.FEC_DEFUNCION, "+
                    "PERSONA.FEC_REGISTRO_ALTA, PERSONA.FEC_REGISTRO_BAJA, PERSONA.FEC_REGISTRO_ACTUALIZADO, "+
                    "PERSONA.CVE_ENT, PERSONA.NUM_MES_NAC_REG, PERSONA.NUM_ANIO_NAC_REG "+
                    "FROM DIT_PERSONA persona  "+
                    "INNER JOIN DIT_SEGURO_IVRO seguro  ON (persona.CVE_ID_PERSONA = seguro.CVE_ID_PERSONA)  "+
                    "INNER JOIN DIT_COMPRA compra  ON (seguro.CVE_ID_COMPRA = compra.CVE_ID_COMPRA)  "+
                    "INNER JOIN DIT_PAGO pago  ON (compra.CVE_ID_COMPRA = pago.CVE_ID_COMPRA)  "+
                    "WHERE pago.CVE_ID_PAGO = :cveIdPago";

    /**
     * unidad de persistencia
     */
    @PersistenceContext(unitName = "deltaPersistenceUnit")
    private EntityManager entityManager;

    /**
     * Servicio para obtener parametros
     */
    @EJB
    private ParametrosEntityLocal parametrosEntity;
    
    /**
     * Servicio para publicar las compras vencidas
     */
    @EJB
    private PublicaCompraVencida publicaCompraVencida;

    /**
     * Busca una compra a partir de su id
     * 
     * @param cveIdompra
     *            el id de la compra
     * @return la compra encontrada
     * @throws SUAException
     *             Errores al buscar la compra
     */
    public Compra findCompraById(long cveIdompra) throws SUAException {
        DitCompra ditCompra = entityManager.find(DitCompra.class, new Long(cveIdompra));
        if (ditCompra == null) {
            throw new SUAException(SUAConstants.COD_COMPRA_NOT_FOUND,
                    SUAConstants.MSG_COMPRA_NOT_FOUND);
        }
        return CompraFactoryUtil.generaCompra(ditCompra);
    }

    /**
     * GUarda una compra en la base de datos, es tos a partir de los datos del
     * modelo xml, guarda la informacion en el modelo de datos
     * 
     * @param compra
     *            la compra a guardar
     * @return la compra con su id de persistencia y sus pagos persistidos
     * @throws SUAException
     *             errore al generar la persistencia de la compra
     */
    public Compra guardaCompra(Compra compra) throws SUAException {
        LOGGER.debug("Se generara y guardara la compra");
        DitCompra ditCompra = CompraFactoryUtil.generaDitCompra(compra);
        entityManager.persist(ditCompra);
        List<DitPago> ditPagos = CompraFactoryUtil.generaDitPagos(Arrays.asList(compra.getPagos()), ditCompra);
        for (DitPago ditPago : ditPagos) {
            DitDetallePago ditDetallePago = ditPago.getDitDetallePago();
            ditPago.setDitDetallePago(null);
            ditPago.setDitCompra(ditCompra);
            entityManager.persist(ditPago);
            ditDetallePago.setDitPago(ditPago);
            entityManager.persist(ditDetallePago);
            ditPago.setDitDetallePago(ditDetallePago);
        }
        ditCompra.setDitPagos(ditPagos);
        LOGGER.debug("SE GUARDO LA COMPRA");
        return CompraFactoryUtil.generaCompra(ditCompra);
    }

    public Fisica findPersonabyPago(long cveIdPago)throws SUAException {

        try{

            Pago pago = findPagoById(cveIdPago);

            Query query = entityManager.createNativeQuery(GET_PERSONA_LC_PAGO,DitPersona.class);
            query.setParameter("cveIdPago", cveIdPago);
            List personaPago = query.getResultList();

            Object resultado = (Object) personaPago.get(0);
            LOGGER.info("El resultado es " + resultado);

            if (resultado instanceof DitPersona) {
                DitPersona persona = new DitPersona();
                LOGGER.info("El resultado es " + resultado);
                persona = (DitPersona) resultado;
                Fisica personaReturn = new Fisica();
                personaReturn.setIdPersona(persona.getCveIdPersona());
                personaReturn.setRfc(persona.getRfc());
                personaReturn.setCurp(persona.getCurp());
                personaReturn.setCveIdAsignacionNSS(persona.getDitAsignacionNsses().get(0).getCveIdAsignacionNss());
                personaReturn.setNss(persona.getDitAsignacionNsses().get(0).getNumNss());
                return personaReturn;
            } else {
                LOGGER.info("El resultado no es PERSONA FISICA");
            }

        }catch(Exception e){
            LOGGER.error("Ocurrio un error en el query: ",e);
            throw new SUAException(e.getMessage());
        }

        return null;
    }


    /**
     * Busca un pago a partir de su id
     * 
     * @param cveIdPago
     *            el id del pago peristido
     * @return el pago encontrado en la base de datos
     * @throws SUAException
     *             errore al realizar la consulta
     */
    public Pago findPagoById(long cveIdPago) throws SUAException {
        DitPago ditPago = findDitPagoById(cveIdPago);        
        Pago pago = CompraFactoryUtil.generaPago(ditPago, true, false, false);
        if(ditPago.getDitCompra() != null && ditPago.getDitCompra().getDitCotizacion() != null) {
            DitCotizacion ditCotizacion = entityManager.find(DitCotizacion.class, 
                    ditPago.getDitCompra().getDitCotizacion().getCveIdCotizacion());
            Cotizacion cotizacion = CotizadorFactoryUtil.generaDeModeloPersitente(ditCotizacion);            
            pago.setConBeneficio(cotizacion.getDetalle().getConBeneficio() != null 
                    ? cotizacion.getDetalle().getConBeneficio().booleanValue() : false);
        }        
        return pago;
    }

    /**
     * BUsca un pago persistido por su id
     * 
     * @param id
     *            el id del pago
     * @return el pago encontrado
     * @throws SUAException
     *             si no existe el pago persistido
     */
    private DitPago findDitPagoById(long id) throws SUAException {
        DitPago ditPago = entityManager.find(DitPago.class, new Long(id));
        if (ditPago == null) {
            throw new SUAException(SUAConstants.COD_PAGO_NOT_FOUND, SUAConstants.MSG_PAGO_NOT_FOUND);
        }
        return ditPago;
    }

    /**
     * Actualiza los valores de linea de captura en un pago en BD
     * 
     * @param pago
     *            al pago a ser actualizado
     * @return el pago actualizadp
     * @throws SUAException
     *             errore sna la actualizacion
     */
    public Pago actualizaPagoLC(Pago pago) throws SUAException {
        if (pago.getIdPago() == null || pago.getIdPago().longValue() == 0) {
            throw new SUAException(SUAConstants.COD_PAGO_NO_UPDATE, SUAConstants.MSG_PAGO_NO_UPDATE);
        }
        DitPago ditPago = findDitPagoById(pago.getIdPago());
        
        
        if (pago.getPdf() != null || pago.getLineaCaptura() != null) {
            LOGGER.info("************************** PDF SIPARE", pago.getPdf());
            LOGGER.info("************************** LC SIPARE", pago.getLineaCaptura());
        	if (ditPago.getRefPdfLc() == null||ditPago.getDesLineaCaptura() == null) {
        		LOGGER.info("************************** Se setea PDF y LC");
        		ditPago.setRefPdfLc(pago.getPdf());
        		ditPago.setDesLineaCaptura(pago.getLineaCaptura());
        	}

        } else {
        	LOGGER.info("**************************El pdf SIPARE o la LC es nulo");
        }
        
        ditPago.setFecRegistroActualizado(new Date());
        entityManager.merge(ditPago);
        ditPago.setDitDetallePago(null);
              
        return CompraFactoryUtil.generaPago(ditPago, true, false, false);
    }

    /**
     * Actualiaz una lista de pagos en la BD su linea de captura
     * 
     * @param pagos
     *            la lista de pagos a ser actualizada
     * @return la lista de pago actualizada
     * @throws SUAException
     *             errores en la actualizacion
     */
    public List<Pago> actualizaPagosLC(List<Pago> pagos) throws SUAException {
        List<Pago> pagosActualizados = new ArrayList<Pago>();
        for (Pago pago : pagos) {
            pagosActualizados.add(actualizaPagoLC(pago));
        }
        return pagosActualizados;
    }

    /**
     * Dada una ista de lineas de captura las marca como pagadas, tambien marca
     * la compra si todos sus pagos ya fueron realizados y es anual o si es
     * bimestral y el pago realizado es sobre el bimestre actual
     * 
     * @param lineasCaptura
     *            las referencias sobre las cuales se buscan los pagos a marcar
     * @return la lista de compras afectadas que ya son validas para vigencia de
     *         compra y la lista de lineas de captura que tubieron un error en
     *         la actualizacion (Si aplica)
     * 
     */
    public ActualizacionCompra pagosPagados(List<String> lineasCaptura) {

        Calendar ini = Calendar.getInstance();
        DicEstadoPago pagado = CompraFactoryUtil.getEstadoPago(EstadoPagoEnum.PAGADO.getId());
        DicEstadoCompra compraPagada = CompraFactoryUtil.getEstadoCompra(EstadoCompraEnum.PAGADO
                .getId());
        
        ActualizacionCompra resultado = new ActualizacionCompra();
        List<DatosCompra> compras = new ArrayList<DatosCompra>();
        List<String> lineasError = new ArrayList<String>();
        int tamano = lineasCaptura.size();
        int inicio = 0;
        int max = MAX_VALUE_LIST;
        int residuo = tamano % max == 0 ? 0 : 1;
        int iteraciones  = (int) (tamano / max) + residuo;
        for (int i = 0; i < iteraciones; i++) {
            int fin = max > tamano ? tamano : max;
            List<String> lineas = lineasCaptura.subList(inicio, fin);
            
            ActualizacionCompra comprasBimestrales = pagaPagosBimestrales(lineas, pagado, compraPagada);
            compras.addAll(Arrays.asList(comprasBimestrales.getCompras()));
            lineasError.addAll(Arrays.asList(comprasBimestrales.getLineasEnError()));
            
            ActualizacionCompra comprasMensuales = pagaPagosMensualesCvro(lineas, pagado, compraPagada);
            compras.addAll(Arrays.asList(comprasMensuales.getCompras()));
            lineasError.addAll(Arrays.asList(comprasMensuales.getLineasEnError()));
            
            ActualizacionCompra comprasAnuales = pagaPagosAnuales(lineas, pagado, compraPagada);
            compras.addAll(Arrays.asList(comprasAnuales.getCompras()));
            lineasError.addAll(Arrays.asList(comprasAnuales.getLineasEnError()));
            
            ActualizacionCompra comprasAnualesAnticipados = pagaPagosAnualesAnticipados(lineas, pagado, compraPagada);
            compras.addAll(Arrays.asList(comprasAnualesAnticipados.getCompras()));
            lineasError.addAll(Arrays.asList(comprasAnualesAnticipados.getLineasEnError()));
            
            inicio = max;
            max = max + MAX_VALUE_LIST;
        }
        Set<DatosCompra> hs= new HashSet<DatosCompra>();
        hs.addAll(compras);
        compras.clear();
        compras.addAll(hs);
       
        Set<String> hsError= new HashSet<String>();
        hsError.addAll(lineasError);
        lineasError.clear();
        lineasError.addAll(hsError);
        
        resultado.setCompras(compras.toArray(new DatosCompra[compras.size()]));
        resultado.setLineasEnError(lineasError.toArray(new String[lineasError.size()]));
        
        Calendar fin = Calendar.getInstance();
        LOGGER.debug("Tiempo de ejecucion {} milisegundos", fin.getTimeInMillis() - ini.getTimeInMillis());
        return resultado;
    }

    /**
     * Dada una ista de lineas de captura las marca como pagadas, tambien marca
     * la compra si es bimestral y el pago realizado es sobre el bimestre actual
     * 
     * @param lineasCaptura
     *            las referencias sobre las cuales se buscan los pagos a marcar
     * @param pagado
     *            Estado pagado para un pago
     * @param compraPagada
     *            Estado de la compra pagada
     * @return la lista de compras afectadas que ya son validas para vigencia de
     *         compra y la lista de lineas de captura que tubieron un error en
     *         la actualizacion (Si aplica)
     * 
     */
    private ActualizacionCompra pagaPagosAnuales(List<String> lineasCaptura, DicEstadoPago pagado,
            DicEstadoCompra compraPagada) {
        Calendar hoy = Calendar.getInstance();
        List<String> lineasError = new ArrayList<String>();
        List<DatosCompra> datos = new ArrayList<DatosCompra>();
        DicFormaPago anual = new DicFormaPago();
        anual.setCveIdFormaPago(FormaPagoEnum.ANUAL.getId());

        TypedQuery<DitPago> queryPago = entityManager.createQuery(GET_PAGO_LC_ANUAL, DitPago.class);
                queryPago.setParameter("lc", lineasCaptura).setParameter("anual", anual);
        List<DitPago> pagos = queryPago.getResultList();
        for (DitPago pago : pagos) {
            try {
                marcaPago(pago, hoy, pagado);
            } catch (Exception e) {
                LOGGER.error("Error al actualizar linea de captura anual " + pago.getDesLineaCaptura() , e);
                lineasError.add(pago.getDesLineaCaptura());
            }
        }

        TypedQuery<DitCompra> queryCompra = entityManager
                .createQuery(GET_COMPRA_LC_ANUAL, DitCompra.class)
                .setParameter("lc", lineasCaptura).setParameter("anual", anual);
        List<DitCompra> compras = queryCompra.getResultList();
        for (DitCompra compra : compras) {
            boolean todoPagado = true;
            for (DitPago pago : compra.getDitPagos()) {
                todoPagado = todoPagado
                        && pago.getDicEstadoPago().getCveIdEstadoPago() == EstadoPagoEnum.PAGADO
                                .getId();
            }
            if (todoPagado) {
                compra.setDicEstadoCompra(compraPagada);
                entityManager.merge(compra);
                datos.add(armaDatosCompra(compra, false));
            }
        }
        ActualizacionCompra resultado = new ActualizacionCompra();
        resultado.setLineasEnError(lineasError.toArray(new String[lineasError.size()]));
        resultado.setCompras(datos.toArray(new DatosCompra[datos.size()]));
        return resultado;
    }

    /**
     * Dada una ista de lineas de captura las marca como pagadas, tambien marca
     * la compra si es bimestral y el pago realizado es sobre el bimestre actual
     * 
     * @param lineasCaptura
     *            las referencias sobre las cuales se buscan los pagos a marcar
     * @param pagado
     *            Estado pagado para un pago
     * @param compraPagada
     *            Estado de la compra pagada
     * @return la lista de compras afectadas que ya son validas para vigencia de
     *         compra y la lista de lineas de captura que tubieron un error en
     *         la actualizacion (Si aplica)
     * 
     */
    @SuppressWarnings("unchecked")
	private ActualizacionCompra pagaPagosBimestrales(List<String> lineasCaptura,
            DicEstadoPago pagado, DicEstadoCompra compraPagada) {
        Calendar hoy = Calendar.getInstance();

        List<String> lineasError = new ArrayList<String>();
        List<DatosCompra> datos = new ArrayList<DatosCompra>();

        DicFormaPago bimestral = new DicFormaPago();
        bimestral.setCveIdFormaPago(FormaPagoEnum.BIMESTRAL.getId());
        Query query = entityManager.createQuery(GET_PAGO_LC_BIMESTRAL)
                .setParameter("lc", lineasCaptura).setParameter("bimestral", bimestral);
        List<Object[]> resultados = query.getResultList();

        for (Object[] resultado : resultados) {
            DitCompra compra = (DitCompra) resultado[0];
            DitPago pago = (DitPago) resultado[1];
            Date nuevaFechaLimite = compra.getFecLimitePago();
            try {
                marcaPago(pago, hoy, pagado);
                DatosCompra datoCompra = armaDatosCompra(pago.getDitCompra(), true);
                boolean todoPagado = false;
                
                String queryPagos = "select pagos from DitPago pagos where pagos.ditCompra.cveIdCompra = "+compra.getCveIdCompra()
                		+" order by pagos.fecIniPeriodo asc";
                
                Query queryPago = entityManager.createQuery(queryPagos);

				List<DitPago> pagosDeCompra = queryPago.getResultList();
                DitPago ditPrimerPago = pagosDeCompra!=null ? pagosDeCompra.get(0) : null;
                
                if(ditPrimerPago!=null 
                		&& ditPrimerPago.getCveIdPago()== pago.getCveIdPago()) {
                	todoPagado = true;
				} else {
					datoCompra.setPrimerPago(false);
				}
                
                for (DitPago pagoCompra : pagosDeCompra) {
                    
                	
                	
//                	if (pagoCompra.getDicEstadoPago().getCveIdEstadoPago() == EstadoPagoEnum.PAGADO
//                            .getId() && pagoCompra.getCveIdPago() != pago.getCveIdPago()) {
//                        datoCompra.setPrimerPago(false);
//                    }                    
                    if (pagoCompra.getDicEstadoPago().getCveIdEstadoPago() != EstadoPagoEnum.PAGADO.getId()) {

//                        if (pagoCompra.getCveIdPago() != pago.getCveIdPago()) {
//                            todoPagado = false;
//                        }
                        if (nuevaFechaLimite == null) {
                            nuevaFechaLimite = pagoCompra.getFecLimitePago();
                        }
                        nuevaFechaLimite = nuevaFechaLimite.before(pagoCompra.getFecLimitePago()) 
                                    ? nuevaFechaLimite : pagoCompra.getFecLimitePago();
                                              
                    }                    
                }
                compra.setFecLimitePago(nuevaFechaLimite);
                if (todoPagado) {
                	datos.add(datoCompra);
                    compra.setDicEstadoCompra(compraPagada);                    
                }
                entityManager.merge(compra);
//                datos.add(datoCompra);
            } catch (Exception e) {
                LOGGER.error("Error al actualizar linea de captura bimestral " + pago.getDesLineaCaptura() , e);
                lineasError.add(pago.getDesLineaCaptura());
            }
        }
        ActualizacionCompra resultado = new ActualizacionCompra();
        resultado.setLineasEnError(lineasError.toArray(new String[lineasError.size()]));
        resultado.setCompras(datos.toArray(new DatosCompra[datos.size()]));
        return resultado;
    }
    
	@SuppressWarnings("unchecked")
	private ActualizacionCompra pagaPagosMensualesCvro(List<String> lineasCaptura, DicEstadoPago pagado,
			DicEstadoCompra compraPagada) {
		Calendar hoy = Calendar.getInstance();

		List<String> lineasError = new ArrayList<String>();
		List<DatosCompra> datos = new ArrayList<DatosCompra>();

		DicModalidad modalidadContinuacion = new DicModalidad();
		modalidadContinuacion.setCveIdModalidad(ModalidadEnum.CUARENTA.getId());

		DicFormaPago mensual = new DicFormaPago();
		mensual.setCveIdFormaPago(FormaPagoEnum.MENSUAL.getId());

		Query query = entityManager.createQuery(GET_PAGO_LC_MENSUAL_CVRO)
				.setParameter("lc", lineasCaptura)
				.setParameter("mensual", mensual)
				.setParameter("modalidadCvro", modalidadContinuacion);
		List<Object[]> resultados = query.getResultList();

		for (Object[] resultado : resultados) {
			DitCompra compra = (DitCompra) resultado[0];
			DitPago pago = (DitPago) resultado[1];
			DitPago pagoReferencia = pago;
			Date nuevaFechaLimite = compra.getFecLimitePago();

			try {
				marcaPago(pago, hoy, pagado);
				DatosCompra datoCompra = armaDatosCompra(pago.getDitCompra(), false);
				boolean todoPagado = false;

				String queryPagos = "select pagos from DitPago pagos where pagos.ditCompra.cveIdCompra = "
						+ compra.getCveIdCompra() + " order by pagos.fecIniPeriodo asc";

				Query queryPago = entityManager.createQuery(queryPagos);
				
				List<DitPago> pagosDeCompra = queryPago.getResultList();
				
				List<DitPago> bloquePrimerPago = obtenerBloquePrimerPago(pagosDeCompra);
				DitPago ditPrimerPago = bloquePrimerPago.get(0);
				
				if (pagoContenidoBloquePrimerPago(bloquePrimerPago, pago)) {
					pagoReferencia = ditPrimerPago;

					if (primerPagoPagado(bloquePrimerPago)) {
						todoPagado = true;
						datoCompra = armaDatosCompra(ditPrimerPago.getDitCompra(), false);
					}
				} else {
					todoPagado = true;
				}

				compra.setFecLimitePago(nuevaFechaLimite);

				if (todoPagado) {
					datos.add(datoCompra);
					compra.setDicEstadoCompra(compraPagada);
				}

				entityManager.merge(compra);
				
				String strQuerySeguro = "select seguro from DitSeguroIvro seguro where seguro.ditCompra.cveIdCompra = "
						+ compra.getCveIdCompra();
				Query querySeguro = entityManager.createQuery(strQuerySeguro);
				DitSeguroIvro seguro = (DitSeguroIvro) querySeguro.getSingleResult();
				
				if (!DateUtils.isSameDay(seguro.getFecInicio(),
						pagoReferencia.getFecIniPeriodo())) {
					modificaVigenciaSeguro(pagoReferencia, seguro);
				}
			} catch (Exception e) {
				LOGGER.error("Error al actualizar linea de captura mensual "
						+ pago.getDesLineaCaptura(), e);
				lineasError.add(pago.getDesLineaCaptura());
			}
		}

		ActualizacionCompra resultado = new ActualizacionCompra();
		resultado.setLineasEnError(lineasError.toArray(new String[lineasError.size()]));
		resultado.setCompras(datos.toArray(new DatosCompra[datos.size()]));

		return resultado;
	}

	private void modificaVigenciaSeguro(DitPago pagoReferencia, DitSeguroIvro seguro) {
		DitSeguroIvro ditSeguroIvro = entityManager.find(DitSeguroIvro.class,
				seguro.getCveIdSeguroIvro());

		ditSeguroIvro.setFecInicio(pagoReferencia.getFecIniPeriodo());
		ditSeguroIvro.setFecFin(pagoReferencia.getFecFinPeriodo());

		entityManager.merge(ditSeguroIvro);
	}

	private List<DitPago> obtenerBloquePrimerPago(List<DitPago> pagosDeCompra) {
		List<DitPago> bloquePrimerPago = new ArrayList<DitPago>();
		Date fechaReferencia = pagosDeCompra.get(0).getFecLimitePago();

		for (DitPago pagoCompra : pagosDeCompra) {
			if (DateUtils.isSameDay(fechaReferencia, pagoCompra.getFecLimitePago())) {
				bloquePrimerPago.add(pagoCompra);
			}
		}

		return bloquePrimerPago;
	}

	private boolean pagoContenidoBloquePrimerPago(
			List<DitPago> bloquePrimerPago, DitPago pago) {
		boolean isContenidoBloque = false;
		
		for (DitPago pagoBloque : bloquePrimerPago) {
			if (StringUtils.isNotBlank(pago.getDesLineaCaptura())
					&& StringUtils.isNotBlank(pagoBloque.getDesLineaCaptura())
					&& StringUtils.equals(pago.getDesLineaCaptura(), pagoBloque.getDesLineaCaptura())) {
				isContenidoBloque = true;
			}
		}
		
		return isContenidoBloque;
	}


	private boolean primerPagoPagado(List<DitPago> bloquePrimerPago) {
		boolean bloquePagado = true;

		for (DitPago pago : bloquePrimerPago) {
			bloquePagado = bloquePagado
					&& pago.getDicEstadoPago().getCveIdEstadoPago() == EstadoPagoEnum.PAGADO.getId();
		}

		return bloquePagado;
	}

	/**
     * Dada una ista de lineas de captura las marca como pagadas, tambien marca
     * la compra si es bimestral y el pago realizado es sobre el bimestre actual
     * 
     * @param lineasCaptura
     *            las referencias sobre las cuales se buscan los pagos a marcar
     * @param pagado
     *            Estado pagado para un pago
     * @param compraPagada
     *            Estado de la compra pagada
     * @return la lista de compras afectadas que ya son validas para vigencia de
     *         compra y la lista de lineas de captura que tubieron un error en
     *         la actualizacion (Si aplica)
     * 
     */
    private ActualizacionCompra pagaPagosAnualesAnticipados(List<String> lineasCaptura, DicEstadoPago pagado,
            DicEstadoCompra compraPagada) {
        Calendar hoy = Calendar.getInstance();
        List<String> lineasError = new ArrayList<String>();
        List<DatosCompra> datos = new ArrayList<DatosCompra>();
        DicFormaPago anual = new DicFormaPago();
        anual.setCveIdFormaPago(FormaPagoEnum.ANUAL_ANTICIPADO.getId());

        TypedQuery<DitPago> queryPago = entityManager.createQuery(GET_PAGO_LC_ANUAL, DitPago.class);
                queryPago.setParameter("lc", lineasCaptura).setParameter("anual", anual);
        List<DitPago> pagos = queryPago.getResultList();
        for (DitPago pago : pagos) {
            try {
                marcaPago(pago, hoy, pagado);
            } catch (Exception e) {
                LOGGER.error("Error al actualizar linea de captura anual " + pago.getDesLineaCaptura() , e);
                lineasError.add(pago.getDesLineaCaptura());
            }
        }

        TypedQuery<DitCompra> queryCompra = entityManager
                .createQuery(GET_COMPRA_LC_ANUAL, DitCompra.class)
                .setParameter("lc", lineasCaptura).setParameter("anual", anual);
        List<DitCompra> compras = queryCompra.getResultList();
        for (DitCompra compra : compras) {
            boolean todoPagado = true;
            for (DitPago pago : compra.getDitPagos()) {
                todoPagado = todoPagado
                        && pago.getDicEstadoPago().getCveIdEstadoPago() == EstadoPagoEnum.PAGADO
                                .getId();
            }
            if (todoPagado) {
                compra.setDicEstadoCompra(compraPagada);
                entityManager.merge(compra);
                datos.add(armaDatosCompra(compra, false));
            }
        }
        ActualizacionCompra resultado = new ActualizacionCompra();
        resultado.setLineasEnError(lineasError.toArray(new String[lineasError.size()]));
        resultado.setCompras(datos.toArray(new DatosCompra[datos.size()]));
        return resultado;
    }
    
    /**
     * COnstruye los datos de una compra a partir de la compra persistide y el
     * indicador de forma de pago
     * 
     * @param compra
     *            la compra a obtener su info
     * @param bimestral
     *            indicador de compra bimestral o anual
     * @return los datos de compra armados
     */
    private DatosCompra armaDatosCompra(DitCompra compra, boolean bimestral) {
        DatosCompra datoCompra = new DatosCompra();
        datoCompra.setBimestral(bimestral);
        datoCompra.setIdCompra(compra.getCveIdCompra());
        datoCompra.setPrimerPago(true);
        return datoCompra;
    }

    /**
     * Agrega la marca de pago y actualiza
     * 
     * @param pago
     *            el pago a actualizar
     * @param hoy
     *            la fecha actual
     * @param pagado
     *            el estado de pago
     */
    private void marcaPago(DitPago pago, Calendar hoy, DicEstadoPago pagado) {
        pago.setFecPago(hoy.getTime());
        pago.setDicEstadoPago(pagado);
        entityManager.merge(pago);
    }

    /**
     * Actualiza la lista de pagos a vencidas cuando aplica
     * 
     * @return lal ista de compras que fueron vencidas
     */
    public List<DatosCompra> pagosVencidos() {
        LOGGER.debug("Iniciando el vencimieno de pagos");
        DicEstadoPago porPagar = CompraFactoryUtil.getEstadoPago(EstadoPagoEnum.POR_PAGAR.getId());
        DicEstadoPago vencida = CompraFactoryUtil.getEstadoPago(EstadoPagoEnum.VENCIDO.getId());
        Calendar hoy = Calendar.getInstance();

        Calendar fechaLimite = Calendar.getInstance();
        fechaLimite.add(Calendar.DATE, parametrosEntity.getDiasVEncimiento());
        fechaLimite = DateUtils.truncate(fechaLimite, Calendar.DATE);
        
        DicModalidad modalidadContinuacion = new DicModalidad();
        modalidadContinuacion.setCveIdModalidad(ModalidadEnum.CUARENTA.getId());

        TypedQuery<DitPago> query = entityManager.createQuery(PAGOS_VENCIDOS, DitPago.class);
        query.setParameter("estadoPorPagar", porPagar).setParameter("fechaLimite",
                fechaLimite.getTime(), TemporalType.DATE).setParameter("modalidad", modalidadContinuacion);
        List<DitPago> pagos = query.getResultList();
        for (DitPago pago : pagos) {
            pago.setDicEstadoPago(vencida);
            pago.setFecRegistroActualizado(hoy.getTime());
            pago.setFecRegistroBaja(hoy.getTime());
            entityManager.merge(pago);
        }

        DicEstadoCompra compraPorPagar = CompraFactoryUtil
                .getEstadoCompra(EstadoCompraEnum.POR_PAGAR.getId());
        DicEstadoCompra compraVencida = CompraFactoryUtil.getEstadoCompra(EstadoCompraEnum.VENCIDO
                .getId());
        DicEstadoCompra compraActiva = CompraFactoryUtil.getEstadoCompra(EstadoCompraEnum.PAGADO
                .getId());

        TypedQuery<DitCompra> queryCompra = entityManager.createQuery(COMPRAS_VENCIDAS,
                DitCompra.class);
        queryCompra.setParameter("compraPorPagar", compraPorPagar).setParameter("estadoVencido",
                vencida).setParameter("compraActiva", compraActiva).setParameter("modalidad", modalidadContinuacion);
        List<DitCompra> compras = queryCompra.getResultList();

        List<DatosCompra> comprasVencidas = new ArrayList<DatosCompra>();
        for (DitCompra compra : compras) {
        	compra.setDicEstadoCompra(compraVencida);
            compra.setFecRegistroBaja(hoy.getTime());
            compra.setFecRegistroActualizado(hoy.getTime());
            entityManager.merge(compra);
            DatosCompra datosCompra = new DatosCompra();
            datosCompra.setIdCompra(compra.getCveIdCompra());
            comprasVencidas.add(datosCompra);
        }
        
        return comprasVencidas;
    }
    
    /**
     * Publica la lista de pagos a vencidas cuando aplica y publica el resultado 
     * en un queue (jms/cobranzaConnectionFactory jms/vencimientoComprasQueue)
     * @return La lista de compras vencidas
     */
    public List<DatosCompra> pagosVencidosQueue() {
        List<DatosCompra> vencidas = pagosVencidos();
        if (vencidas != null && !vencidas.isEmpty()) {
            ActualizacionCompra datos = new ActualizacionCompra();
            datos.setCompras(vencidas.toArray(new DatosCompra[vencidas.size()]));
            publicaCompraVencida.publicaCompraVencida(datos);
        }
        return vencidas;
    }
    
	@Override
	public void cambiarPagosDeCompra(Compra compraDestino, Compra compraOrigen) {
		DitCompra origen = this.entityManager.find(DitCompra.class,
				compraOrigen.getIdCompra());
		DitCompra destino = this.entityManager.find(DitCompra.class,
				compraDestino.getIdCompra());

		for (DitPago pago : origen.getDitPagos()) {
			pago.setDitCompra(destino);
		}
	}
	
	/**
     * Actualiza la lista de pagos a vencidas cuando aplica, para la modalidad 40
     * 
     * @return La lista de compras que fueron vencidas
     */
	@Override
	public List<DatosCompra> pagosVencidosMod40() {
		LOGGER.debug("Iniciando el vencimiento de pagos para la modalidad 40");
        DicEstadoPago porPagar = CompraFactoryUtil.getEstadoPago(EstadoPagoEnum.POR_PAGAR.getId());
        
        DicModalidad modalidadContinuacion = new DicModalidad();
        modalidadContinuacion.setCveIdModalidad(ModalidadEnum.CUARENTA.getId());

        DicEstadoSeguro edoSeguroNuevo = new DicEstadoSeguro();
        edoSeguroNuevo.setCveIdEstadoSeguro(EstadoSeguroIvroEnum.NUEVO.getId());

        DicEstadoSeguro edoSeguroActivo = new DicEstadoSeguro();
        edoSeguroActivo.setCveIdEstadoSeguro(EstadoSeguroIvroEnum.ACTIVO.getId());
        
		TypedQuery<DitSeguroIvro> queryPagosPorPagarMod40 = entityManager.createQuery(SEGUROS_PAGOS_POR_PAGAR_MOD_40, DitSeguroIvro.class);
		queryPagosPorPagarMod40.setParameter("modalidad", modalidadContinuacion);
        queryPagosPorPagarMod40.setParameter("estadosSeg", Arrays.asList(new DicEstadoSeguro[]{edoSeguroNuevo, edoSeguroActivo}));
        queryPagosPorPagarMod40.setParameter("estadoPago", porPagar);
		
		List<DitSeguroIvro> segurosConPagosPorPagarMod40 = queryPagosPorPagarMod40.getResultList();
		List<DitCompra> comprasVencerMod40 = new ArrayList<DitCompra>();
		
		for(DitSeguroIvro seguroIvro: segurosConPagosPorPagarMod40){
			//Antes de ordenar descendentemente por fecha limite de pago
			seguroIvro.getDitCompra().setDitPagos(ordernaListaPagos(seguroIvro.getDitCompra().getDitPagos()));
			//Despues de ordenar descendentemente por fecha limite de pago

			List<DitPago> pagosSeguroIvro = seguroIvro.getDitCompra().getDitPagos();

			Integer pagosSize = seguroIvro.getDitCompra().getDitPagos().size();
			Integer posicion = 0, numFechaLimitePago = 0;
			Integer cantidadPagosPorPagar = 0, fechasLimitePasados = 0;

			//Se revisa que sea menor de 2 porque es el número máximo de fechas límites de pago a revisar
			while(posicion < pagosSize && numFechaLimitePago < 2){
				TypedQuery<DitPago> queryPagosCompraFecha = entityManager.createQuery(PAGOS_POR_COMPRA_POR_FECHA_MOD_40, DitPago.class);
				queryPagosCompraFecha.setParameter("modalidad", modalidadContinuacion)
									 .setParameter("compra", pagosSeguroIvro.get(posicion).getDitCompra())
									 .setParameter("fechaLimitePago", pagosSeguroIvro.get(posicion).getFecLimitePago());

				List<DitPago> pagosPorCompraFecha = queryPagosCompraFecha.getResultList();

				if(existePagoPorPagar(pagosPorCompraFecha)){
					cantidadPagosPorPagar++;
					if(fechaLimitePasado(pagosPorCompraFecha)){
						fechasLimitePasados++;
					}
				}
				numFechaLimitePago += 1;
				posicion += pagosPorCompraFecha.size();
			}
			//Si son dos o más por pagar y son más de una fechaLimitePasada, entonces vencer pagos
			LOGGER.debug("Cantidad de pagos por pagar es "+cantidadPagosPorPagar);
			LOGGER.debug("Cantidad fechas limite pasados es "+fechasLimitePasados);
			if(cantidadPagosPorPagar > 1 && fechasLimitePasados > 1){
				vencerPagosPendientesMod40(pagosSeguroIvro);
				comprasVencerMod40.add(seguroIvro.getDitCompra());
			}
		}//Termina recorrido de segurosIvro
		//Se vencen las compras que tuvieron pagos pendientes
		LOGGER.debug("Cantidad de compras a vencer de modalidad 40: "+comprasVencerMod40.size());
		return vencerComprasPendientesMod40(comprasVencerMod40);
	}
	
	/**
	 * Ordena los pagos por medio de la fecha limite de pago en orden descendente
	 * 
	 * @param pagosSeguroIvro
	 * @return la lista ordenada
	 */
	private List<DitPago> ordernaListaPagos(List<DitPago> pagosSeguroIvro){
		Collections.sort(pagosSeguroIvro, new Comparator<DitPago>() {
			@Override
			public int compare(DitPago pago1, DitPago pago2) {
				return pago2.getFecLimitePago().compareTo(pago1.getFecLimitePago());
			}
		}
		);
		return pagosSeguroIvro;
	}
	
	/**
	 * Evalua si existe pago por pagar en una lista de pagos
	 * 
	 * @param pagos
	 * @return Boolean
	 */
	private Boolean existePagoPorPagar(List<DitPago> pagos){
		Boolean existePagoPorPagar = false;
		for(DitPago pago : pagos){
			if(EstadoPagoEnum.POR_PAGAR.getId() == pago.getDicEstadoPago().getCveIdEstadoPago()){
				existePagoPorPagar = true;
				break;
			}
		}
		return existePagoPorPagar;
	}
	
	/**
	 * Evalua si existe una fecha limite de pago rebasada
	 * 
	 * @param pagos
	 * @return Boolean
	 */
	private Boolean fechaLimitePasado(List<DitPago> pagos){
		Boolean fechaLimitePasado = false;
		Calendar fechaLimite = Calendar.getInstance();
        fechaLimite.add(Calendar.DATE, parametrosEntity.getDiasVEncimiento());
        fechaLimite = DateUtils.truncate(fechaLimite, Calendar.DATE);
        
        Date fechaLimitePago;
        for(DitPago pago : pagos){
        	fechaLimitePago = pago.getFecLimitePago();
        	if(fechaLimite.getTime().after(fechaLimitePago)){
    			fechaLimitePasado = true;
    			break;
    		}
        }
		
		return fechaLimitePasado;
	}
	
	/**
	 * Realiza el vencimiento de pagos pendientes de modalidad 40
	 * 
	 * @param pagos
	 */
	private void vencerPagosPendientesMod40(List<DitPago> pagos){
		DicEstadoPago vencida = CompraFactoryUtil.getEstadoPago(EstadoPagoEnum.VENCIDO.getId());
		Calendar hoy = Calendar.getInstance();
		
		for(DitPago pago : pagos){
			if(EstadoPagoEnum.POR_PAGAR.getId() == pago.getDicEstadoPago().getCveIdEstadoPago()){
				pago.setDicEstadoPago(vencida);
				pago.setFecRegistroActualizado(hoy.getTime());
				pago.setFecRegistroBaja(hoy.getTime());
				entityManager.merge(pago);
			}
		}
	}
	
	/**
	 * Realiza el vencimiento de compras pendientes de modalidad 40
	 * 
	 * @param compras
	 * @return List<DatosCompra>
	 */
	private List<DatosCompra> vencerComprasPendientesMod40(List<DitCompra> compras){
		 DicEstadoCompra compraVencida = CompraFactoryUtil.getEstadoCompra(EstadoCompraEnum.VENCIDO.getId());
		 Calendar hoy = Calendar.getInstance();
		 
		 List<DatosCompra> comprasVencidas = new ArrayList<DatosCompra>();
		 for (DitCompra compra : compras) {
			 compra.setDicEstadoCompra(compraVencida);
			 compra.setFecRegistroBaja(hoy.getTime());
			 compra.setFecRegistroActualizado(hoy.getTime());
			 entityManager.merge(compra);
			 DatosCompra datosCompra = new DatosCompra();
			 datosCompra.setIdCompra(compra.getCveIdCompra());
			 comprasVencidas.add(datosCompra);
		 }
		 return comprasVencidas;
	}

}