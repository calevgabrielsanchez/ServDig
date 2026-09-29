/**
 *
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.entity;

import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.NssRelacionadoVariasPersonasException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaConVariosNSSException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaSinNSSException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonasNoLocalizadasException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CompraServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.interfaces.CotizacionServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.motorCalculo.util.SUAConstants;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.exception.IvroException;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ConsultaBeneficiarioMigradoLocal;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ConsultaSeguroIvroLocal;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.EnviaCorreoLocal;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.SeguroIvroServiceLocal;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.model.DatosMovSeguro;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.model.MovimientoPeriodo;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util.BajaMoraUtil;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util.DetalleMoraInfo;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util.IvroConstants;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util.IvroDateUtils;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util.IvroFactory;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudServiciosExpuestosRemote;
import mx.gob.imss.ctirss.delta.model.enums.*;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Beneficiario;
import mx.gob.imss.ctirss.delta.persistence.BdtutUltimoTrabajo;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoSeguro;
import mx.gob.imss.ctirss.delta.persistence.DicModalidad;
import mx.gob.imss.ctirss.delta.persistence.DitFormaContacto;
import mx.gob.imss.ctirss.delta.persistence.DitSeguroIvro;
import mx.gob.imss.ctirss.delta.persistence.cobranza.DicEstadoCompra;
import mx.gob.imss.ctirss.delta.persistence.cobranza.DicEstadoPago;
import mx.gob.imss.ctirss.delta.persistence.cobranza.DitCompra;
import mx.gob.imss.ctirss.delta.persistence.cobranza.DitPago;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.digital.modelo.cobranza.*;
import mx.gob.imss.digital.modelo.derechohabiente.UnidadMedicoFamiliar;
import mx.gob.imss.digital.modelo.persona.Fisica;
import mx.gob.imss.digital.modelo.persona.Persona;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;
import mx.gob.imss.digital.modelo.sindo.MovimientoTrabajadorSindo;
import mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvro;
import weblogic.jdbc.wrapper.Array;
import mx.gob.imss.ctirss.delta.persistence.DitPersonafContacto;
import mx.gob.imss.ctirss.delta.persistence.DitFormaContacto;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.*;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import java.math.BigDecimal;
import java.util.*;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util.IvroSolicitudUtil;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util.PagoVencido;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;
// import java.nio.charset.StandardCharsets;  // No usar - usar "UTF-8" directamente para compatibilidad
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

// Excepciones
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.exception.IvroException;

// Entidades JPA del usuario (ya existen en su código)
import mx.gob.imss.ctirss.delta.persistence.bajas.LoteProcesamientoBaja;
import mx.gob.imss.ctirss.delta.persistence.bajas.StgReingresoRO;
import mx.gob.imss.ctirss.delta.persistence.bajas.DitBajaSeguro;
import mx.gob.imss.ctirss.delta.persistence.bajas.DitBajaReingresoDetalle;

// Entidades JPA del sistema existente
import mx.gob.imss.ctirss.delta.persistence.DitSeguroIvro;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoSeguro;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitPersonafContacto;
// DTOs y modelos
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;

// Enums
import mx.gob.imss.ctirss.delta.model.enums.EstadoSeguroIvroEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoOperacionNotificacionIVROEnum;

// Factory
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util.IvroFactory;

// Layout SINDO (para refactorización data-driven)
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util.SindoLayout;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.DetalleReingresoRODTO;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.HistorialUltimoSeguroCotizadoDTO;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util.SindoParser;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util.ValidacionMoraUtil;

import static mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util.SindoParser.SindoLineaDTO;

import mx.gob.imss.ctirss.delta.persistence.bajas.DitBajaMoraDetalle;
import mx.gob.imss.ctirss.delta.persistence.bajas.DitBajaMoraPagoVencido;
import mx.gob.imss.ctirss.delta.persistence.bajas.LoteProcesamientoBaja;
import mx.gob.imss.ctirss.delta.persistence.bajas.DitBajaSeguro;
import mx.gob.imss.ctirss.delta.persistence.cobranza.DitCompra;
import mx.gob.imss.ctirss.delta.persistence.cobranza.DitPago;
import mx.gob.imss.ctirss.delta.persistence.cobranza.DitCotizacion;
import mx.gob.imss.ctirss.delta.persistence.cobranza.DicEstadoCompra;
import mx.gob.imss.ctirss.delta.persistence.cobranza.DicEstadoPago;
import mx.gob.imss.ctirss.delta.model.enums.EstadoCompraEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoPagoEnum;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.DetalleMoraDTO;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import javax.persistence.Query;
import mx.gob.imss.ctirss.delta.persistence.bajas.DitSolicitudBajaWeb;
import mx.gob.imss.ctirss.delta.persistence.bajas.DitBajaExpresaDetalle;
import java.sql.Timestamp;

//import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.util.BajaMoraUtil;
//import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.util.ValidacionMoraUtil;

/**
 * Implementacion de los servicio para los seguros ivro
 *
 * @author NOVUTECK1
 *
 */
@Stateless(name = "seguroIvroServiceEntity", mappedName = "seguroIvroServiceEntity")
public class SeguroIvroServiceEntity implements SeguroIvroServiceLocal {

    /**
     * Logger de la clase
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SeguroIvroServiceEntity.class);
    /**
     * Servicios de consulta de seguros
     */
    @EJB
    private ConsultaSeguroIvroLocal consultaSeguroIvroLocal;

    @EJB(mappedName = "personaFisicaServiceBusiness")
    private PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;
    /**
     * Servicio para consultar cotizaciones
     */
    @EJB(mappedName = "cotizacionServiceBusiness")
    private CotizacionServiceRemote cotizacionServiceRemote;

    /**
     * Servicio para consultar las compras
     */
    @EJB(mappedName = "compraServiceBusiness")
    private CompraServiceRemote compraServiceRemote;

    /**
     * Consulta a los beneficiarios si no hay ramite implica que fueron migrados
     */
    @EJB
    private ConsultaBeneficiarioMigradoLocal consultaBeneficiarioMigradoEntity;

    /**
     * unidad de persistencia
     */
    @PersistenceContext(unitName = "deltaPersistenceUnit")
    private EntityManager entityManager;

    /**
     * Servicio para el envio de correo
     */
    @EJB
    private EnviaCorreoLocal enviaCorreoLocal;
    /**
     * Servicio de personas
     */
    @EJB(name = "personaBusiness", mappedName = "personaBusiness")
    private PersonaBusinessRemote personaBusinessRemote;

    @EJB(mappedName = "solicitudServiciosExpuestos")
    private SolicitudServiciosExpuestosRemote solicitudServiciosExpuestos;

    // Se agrega asi mismo para cuando un metodo llame a dentro de la misma clase
    // el framework ejecute las anotaciones.
    @EJB
    private SeguroIvroServiceLocal self;
    
	/**
	* Constantes de validación para archivos SINDO.
	*/
	// Valores especiales
	private static final String SALARIO_CERO = "00000000";
	private static final String MODALIDAD_ORIGEN_ESPERADA = "40";
	
	// Longitudes
	private static final int NSS_LONGITUD = 11;
	
	private final SindoParser sindoParser = new SindoParser();
	
	private static final String NOMBRE_LOTE_POST_WEB_FIJO = "LOTE_POST_WEB_FIJO";


    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * SeguroIvroServiceLocal
     * #activaSeguro(mx.gob.imss.digital.modelo.cobranza.ActualizacionCompra)
     */
    @Override
    public List<DatosMovSeguro> activaSeguro(ActualizacionCompra comprasPagadas) {
        List<DatosMovSeguro> movimientos = new ArrayList<DatosMovSeguro>();
        DicEstadoSeguro activo = IvroFactory.estadoSeguro(EstadoSeguroIvroEnum.ACTIVO);
        List<DicEstadoSeguro> activos = new ArrayList<DicEstadoSeguro>();
        activos.add(activo);
        if (comprasPagadas != null && comprasPagadas.getCompras() != null) {
            for (DatosCompra compra : comprasPagadas.getCompras()) {
                LOGGER.debug("validando compra activa");
                try {
                    List<Fisica> activas = new ArrayList<Fisica>();
                    DitSeguroIvro seguro = marcaSeguros(compra, activo);
                    long idModalidad = seguro.getDicModalidad().getCveIdModalidad();
                    SeguroIvro seg = IvroFactory.generaSeguro(seguro);
                    LOGGER.debug("Enviando Correo de activaci�n");
                    int codOperNotif = TipoOperacionNotificacionIVROEnum.RECEPCION_DE_PAGO.getCodigo();

                    if (idModalidad == ModalidadEnum.CUARENTA.getId()) {
                        codOperNotif = TipoOperacionNotificacionIVROEnum.RECEPCION_DE_PAGO_MOD40.getCodigo();
                    }

                    enviaCorreoLocal.enviaCorreo(seg, codOperNotif);
                    // si el seguro es nuevo y domestico se agregan todos los
                    // trabajadores en el alta

                    if (idModalidad == ModalidadEnum.TREINTAYCUATRO.getId()
                            || idModalidad == ModalidadEnum.TREINTAYTRES.getId()) {

                        LOGGER.debug("validando seguro modalidad "
                                + ModalidadEnum.fromId(idModalidad).getNumModalidad()
                                + " para ser activado");

                        TramiteSeguroIvro tramite;
                        if (seg.getTramite() != null) {
                            tramite = seg.getTramite();
                        } else if (seg.getTramiteSeguroFamiliar() != null) {
                            tramite = seg.getTramiteSeguroFamiliar();
                        } else if (seg.getTramiteContVoluntaria() != null) {
                            tramite = seg.getTramiteContVoluntaria();
                        } else {
                            tramite = null;
                        }

                        if (tramite != null
                                && (tramite.getIdSeguroAnterior() == null || tramite.getIdSeguroAnterior() == 0)
                                && tramite.getBeneficiarios() != null) {
                            activas.addAll(Arrays.asList(tramite.getBeneficiarios()));
                        } else {
                            activas.addAll(consultaBeneficiarioMigradoEntity
                                    .buscaBeneficiariosSeguro(seg));
                        }
                    } else {
                        LOGGER.debug("validando persona titular");
                        boolean nuevo = seguroNuevo(seg, activos);
                        if (nuevo && (!compra.getBimestral() || (compra.getBimestral() && compra
                                .getPrimerPago()))) {
                            LOGGER.debug("Agregando persona titular");
                            activas.add(seg.getTitular());
                        }
                    }

                    if (!activas.isEmpty()) {
                        LOGGER.debug("Agregando personas activas");
                        List<DatosMovSeguro> movimiento;

                        if (idModalidad == ModalidadEnum.CUARENTA.getId()) {
                            movimiento = generaDatosMovimientoCvro(seguro, activas,
                                    true, seg.getTramite(), compra.getPrimerPago());

                        } else {
                            movimiento = generaDatosMovimiento(seguro, activas,
                                    true, seg.getTramite());
                        }

                        movimientos.addAll(movimiento);
                    }
                } catch (IvroException e) {
                    LOGGER.error("No se eoncontro Seguro asociado a una compra, "
                            + "Esto no importa ", e);
                }
            }
        }
        return movimientos;
    }

    @Override
    public List<DatosMovSeguro> generaMovActivaSeguro(ActualizacionCompra comprasPagadas) {
        List<DatosMovSeguro> movimientos = new ArrayList<DatosMovSeguro>();
        DicEstadoSeguro activo = IvroFactory.estadoSeguro(EstadoSeguroIvroEnum.ACTIVO);
        List<DicEstadoSeguro> activos = new ArrayList<DicEstadoSeguro>();
        activos.add(activo);

        if (comprasPagadas != null && comprasPagadas.getCompras() != null) {
            for (DatosCompra compra : comprasPagadas.getCompras()) {
                LOGGER.info("validando compra activa");
                try {
                    List<Fisica> activas = new ArrayList<Fisica>();
                    DitSeguroIvro seguro = consultaSeguroIvroLocal.buscaSeguroCompra(compra.getIdCompra());
                    SeguroIvro seg = IvroFactory.generaSeguro(seguro);

                    long idModalidad = seguro.getDicModalidad().getCveIdModalidad();
                    if (idModalidad == ModalidadEnum.TREINTAYCUATRO.getId()
                            || idModalidad == ModalidadEnum.TREINTAYTRES.getId()) {

                        LOGGER.info("validando seguro modalidad "
                                + ModalidadEnum.fromId(idModalidad).getNumModalidad()
                                + " para ser activado");

                        TramiteSeguroIvro tramite = IvroFactory.obtenerTramitePorSeguro(seg);

                        if (tramite != null
                                && (tramite.getIdSeguroAnterior() == null 
                                || tramite.getIdSeguroAnterior() == 0)
                                && tramite.getBeneficiarios() != null) {
                            activas.addAll(Arrays.asList(tramite.getBeneficiarios()));
                            LOGGER.info("getBeneficiarios");
                        } else {
                            LOGGER.info("buscaBeneficiarios");
                            activas.addAll(consultaBeneficiarioMigradoEntity.buscaBeneficiariosSeguro(seg));
                        }
                    } else {
                        LOGGER.debug("validando persona titular");
                        boolean nuevo = seguroNuevo(seg, activos);
                        if (nuevo && (!compra.getBimestral() || (compra.getBimestral() && compra.getPrimerPago()))) {
                            LOGGER.info("Agregando persona titular");
                            activas.add(seg.getTitular());
                        }
                    }

                    if (!activas.isEmpty()) {
                        LOGGER.info("Agregando personas activas");
                        List<DatosMovSeguro> movimiento;

                        if (idModalidad == ModalidadEnum.CUARENTA.getId()) {
                            movimiento = generaDatosMovimientoCvro(seguro, activas,
                                    true, seg.getTramite(), compra.getPrimerPago());
                        } else {
                            movimiento = generaDatosMovimiento(seguro, activas,
                                    true, seg.getTramite());
                        }

                        movimientos.addAll(movimiento);
                    }
                } catch (IvroException e) {
                    LOGGER.error("No se eoncontro Seguro asociado a una compra, "
                            + "Esto no importa ", e);
                }
            }
        }

        return movimientos;
    }


    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * SeguroIvroServiceLocal
     * #venceSeguro(mx.gob.imss.digital.modelo.cobranza.ActualizacionCompra)
     */
    @Override
    public List<DatosMovSeguro> venceSeguro(ActualizacionCompra comprasVencidas) {
        List<DatosMovSeguro> movimientos = new ArrayList<DatosMovSeguro>();
        DicEstadoSeguro vencido = IvroFactory.estadoSeguro(EstadoSeguroIvroEnum.VENCIDO);
        if (comprasVencidas != null && comprasVencidas.getCompras() != null) {
            for (DatosCompra compra : comprasVencidas.getCompras()) {
                try {
                    DitSeguroIvro seguro = marcaSeguros(compra, vencido);
                    SeguroIvro seg = IvroFactory.generaSeguro(seguro);
                    enviaCorreoLocal.enviaCorreo(seg,
                            TipoOperacionNotificacionIVROEnum.SUSPENSION_POR_FALTA_PAGO.getCodigo());
                    DitCompra compraSeguro = entityManager.find(DitCompra.class, seguro
                            .getDitCompra().getCveIdCompra());
                    if (compraSeguro.getDicFormaPgo().getCveIdFormaPago() == FormaPagoEnum.BIMESTRAL.getId()
                            || compraSeguro.getDicFormaPgo().getCveIdFormaPago() == FormaPagoEnum.MENSUAL.getId()) {
                        boolean conPagados = false;
                        for (DitPago pago : compraSeguro.getDitPagos()) {
                            if (pago.getDicEstadoPago().getCveIdEstadoPago() == EstadoPagoEnum.PAGADO.getId()) {
                                conPagados = true;
                            }
                        }
                        if (conPagados) {
                            List<Fisica> causanBaja = new ArrayList<Fisica>();

                            if (seguro.getDicModalidad().getCveIdModalidad() == ModalidadEnum.TREINTAYCUATRO.getId()) {
                                SeguroIvro seguroDetallado = consultaSeguroIvroLocal
                                        .buscaSeguroPorId(seg.getCveIdSeguroIvro());
                                Fisica[] lstBeneficiario = seguroDetallado.getTramite().getBeneficiarios();

                                for (Fisica beneficiario : lstBeneficiario) {
                                    try {
                                        mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica trabajador = personaFisicaServiceBusiness
                                                .localizarPersonaFisicaPorNss(beneficiario.getNss());

                                        Fisica persona = new Fisica();
                                        persona.setIdPersona(trabajador.getIdPersona());
                                        persona.setNss(beneficiario.getNss());
                                        causanBaja.add(persona);
                                    } catch (PersonasNoLocalizadasException e) {
                                        Fisica persona = new Fisica();
                                        persona.setIdPersona(seguro.getDitPersona().getCveIdPersona());
                                        causanBaja.add(persona);
                                    } catch (NssRelacionadoVariasPersonasException e) {
                                        Fisica persona = new Fisica();
                                        persona.setIdPersona(seguro.getDitPersona().getCveIdPersona());
                                        causanBaja.add(persona);
                                    }
                                }
                            } else {
                                Fisica persona = new Fisica();
                                persona.setIdPersona(seguro.getDitPersona().getCveIdPersona());
                                causanBaja.add(persona);
                            }

                            SeguroIvro seguroIvro = IvroFactory.generaSeguro(seguro);
                            List<DatosMovSeguro> movimiento = generaDatosMovimiento(seguro,
                                    causanBaja, false, seguroIvro.getTramite());
                            movimientos.addAll(movimiento);
                        }
                    }

                } catch (IvroException e) {
                    LOGGER.error("No se eoncontro Seguro asociado a una compra, "
                            + "Esto no deberia de currir nunca", e);
                }
            }
        }
        return movimientos;
    }

    @Override
    public List<DatosMovSeguro> generaMovVencSeguro(ActualizacionCompra comprasVencidas) {
        List<DatosMovSeguro> movimientos = new ArrayList<DatosMovSeguro>();

        if (comprasVencidas != null && comprasVencidas.getCompras() != null) {
            for (DatosCompra compra : comprasVencidas.getCompras()) {
                try {
                    DitSeguroIvro seguro = consultaSeguroIvroLocal.buscaSeguroCompra(compra.getIdCompra());
                    SeguroIvro seg = IvroFactory.generaSeguro(seguro);
                    DitCompra compraSeguro = entityManager.find(DitCompra.class, seguro.getDitCompra().getCveIdCompra());

					if (compraSeguro.getDicFormaPgo().getCveIdFormaPago() == FormaPagoEnum.ANUAL.getId() || compraSeguro.getDicFormaPgo().getCveIdFormaPago() == FormaPagoEnum.BIMESTRAL.getId()
							|| compraSeguro.getDicFormaPgo().getCveIdFormaPago() == FormaPagoEnum.MENSUAL.getId() || compraSeguro.getDicFormaPgo().getCveIdFormaPago() == FormaPagoEnum.ANUAL_ANTICIPADO.getId()) {
						boolean conPagados = false;

						for (DitPago pago : compraSeguro.getDitPagos()) {
							if (pago.getDicEstadoPago().getCveIdEstadoPago() == EstadoPagoEnum.PAGADO.getId()) {
								conPagados = true;
							}
						}

						if (conPagados) {
                        List<Fisica> causanBaja = new ArrayList<Fisica>();

							if (seguro.getDicModalidad().getCveIdModalidad() == ModalidadEnum.TREINTAYCUATRO.getId() || seguro.getDicModalidad().getCveIdModalidad() == ModalidadEnum.TREINTAYTRES.getId()) {
                            SeguroIvro seguroDetallado = consultaSeguroIvroLocal.buscaSeguroPorId(seg.getCveIdSeguroIvro());
                            Fisica[] lstBeneficiario = seguroDetallado.getTramite().getBeneficiarios();

                            for (Fisica beneficiario : lstBeneficiario) {
                                try {
                                    mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica trabajador = personaFisicaServiceBusiness
                                            .localizarPersonaFisicaPorNss(beneficiario.getNss());

                                    Fisica persona = new Fisica();
                                    persona.setIdPersona(trabajador.getIdPersona());
                                    persona.setNss(beneficiario.getNss());
                                    causanBaja.add(persona);
                                } catch (PersonasNoLocalizadasException e) {
                                    Fisica persona = new Fisica();
                                    persona.setIdPersona(seguro.getDitPersona().getCveIdPersona());
                                    causanBaja.add(persona);
                                } catch (NssRelacionadoVariasPersonasException e) {
                                    Fisica persona = new Fisica();
                                    persona.setIdPersona(seguro.getDitPersona().getCveIdPersona());
                                    causanBaja.add(persona);
                                }
                            }
                        } else {
                            Fisica persona = new Fisica();
                            persona.setIdPersona(seguro.getDitPersona().getCveIdPersona());
                            causanBaja.add(persona);
                        }

                        SeguroIvro seguroIvro = IvroFactory.generaSeguro(seguro);
                        List<DatosMovSeguro> movimiento = generaDatosMovimiento(seguro, causanBaja, false,
                                seguroIvro.getTramite());
                        movimientos.addAll(movimiento);
                    }
					}
                } catch (IvroException e) {
                    LOGGER.error(
                            "No se eoncontro Seguro asociado a una compra, "
                            + "Esto no deberia de currir nunca", e);
                }
            }
        }

        return movimientos;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * SeguroIvroServiceLocal#vencimientoSeguroVigencia()
     */
    @Override
    public List<DatosMovSeguro> concluirSegurosVigencia() {
        List<DatosMovSeguro> movimientos = new ArrayList<DatosMovSeguro>();
        DicEstadoSeguro activo = IvroFactory.estadoSeguro(EstadoSeguroIvroEnum.ACTIVO);
        DicEstadoSeguro cancelacionRiss = IvroFactory
                .estadoSeguro(EstadoSeguroIvroEnum.CANCELADO_RISS);
        List<DicEstadoSeguro> activos = new ArrayList<DicEstadoSeguro>();
        activos.add(activo);
        activos.add(cancelacionRiss);

        List<DicModalidad> domesticos = new ArrayList<DicModalidad>();
        DicModalidad domestico = new DicModalidad();
        domestico.setCveIdModalidad(ModalidadEnum.TREINTAYCUATRO.getId());
        domesticos.add(domestico);
        List<DitSeguroIvro> seguros = consultaSeguroIvroLocal.buscaSegurosAConcluir();

        for (DitSeguroIvro seguro : seguros) {
            try {
                List<Fisica> concluidas = new ArrayList<Fisica>();
                // Si son domesticos se procesan de una forma
                if (seguro.getDicModalidad().getCveIdModalidad() == ModalidadEnum.TREINTAYCUATRO.getId()
                        || seguro.getDicModalidad().getCveIdModalidad() == ModalidadEnum.TREINTAYTRES.getId()) {
                    concluidas.addAll(personaNoRenovada(seguro, domesticos, activos));
                } else {
                    // para seguros individuales verificamos que ya tenga renovacion
                    // en caso contrario
                    // lo regresamos para dar de baja
                    boolean renovado = seguroRenovadoPersona(seguro, activos);
                    if (!renovado) {
                        Fisica fisica = new Fisica();
                        fisica.setIdPersona(seguro.getDitPersona().getCveIdPersona());
                        concluidas.add(fisica);
                    }
                }

                if (!concluidas.isEmpty()) {
                    SeguroIvro seguroIvro = IvroFactory.generaSeguro(seguro);
                    if (seguroIvro.getTramite() != null) {
                        List<DatosMovSeguro> movimiento = generaDatosMovimiento(seguro, concluidas, false, seguroIvro.getTramite());
                        movimientos.addAll(movimiento);
                    } else if (seguroIvro.getTramiteSeguroFamiliar() != null) {
                        List<DatosMovSeguro> movimiento = generaDatosMovimiento(seguro, concluidas, false,
                                seguroIvro.getTramiteSeguroFamiliar());
                        movimientos.addAll(movimiento);
                    } else if (seguroIvro.getTramiteContVoluntaria() != null) {
                        List<DatosMovSeguro> movimiento = generaDatosMovimiento(seguro, concluidas, false,
                                seguroIvro.getTramiteContVoluntaria());
                        movimientos.addAll(movimiento);
                    }
                }

                marcaSeguroConcluido(seguro);
            } catch (Exception e) {
                LOGGER.error("No fue posible procesar el seguro: " + seguro.getCveIdSeguroIvro(), e);
            }
        }
        return movimientos;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * SeguroIvroServiceLocal
     * #cancelaSeguroRiss(mx.gob.imss.digital.modelo.persona.Persona)
     */
    @Override
    public Persona cancelaSeguroRiss(Persona persona) {
        List<DicModalidad> individuales = IvroFactory.generaModalidades(IvroFactory.MOD_INDIVIDUAL);
        List<DicEstadoSeguro> estadosActivos = IvroFactory.estadosSeguro(true);
        persona.setIdPersona(0l);
        List<mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica> personas
                = personaBusinessRemote.buscarPersonaFisicaPorRfcEnImss(persona.getRfc());
        if (personas != null && !personas.isEmpty()) {
            persona.setIdPersona(personas.get(0).getIdPersona());
        }
        List<SeguroIvro> activos = consultaSeguroIvroLocal.buscaSegurosPersona(persona,
                individuales, estadosActivos);
        DicEstadoSeguro cancelado = IvroFactory.estadoSeguro(EstadoSeguroIvroEnum.CANCELADO_RISS);

        for (SeguroIvro seguro : activos) {
            enviaCorreoLocal.enviaCorreo(seguro,
                    TipoOperacionNotificacionIVROEnum.SUSPENSION_POR_FALTA_PAGO.getCodigo());
            // MArcamos como cancelado el seguro
            DitSeguroIvro seg = entityManager
                    .find(DitSeguroIvro.class, seguro.getCveIdSeguroIvro());
            seg.setDicEstadoSeguro(cancelado);
            // Recalculomos el tiempo restante del seguro bajo nuevas reglas
            try {
                Compra compra = compraServiceRemote.findCompraById(seg.getDitCompra()
                        .getCveIdCompra());
                Cotizacion cotizacion = cotizacionServiceRemote.findCotizacion(compra
                        .getIdCotizacion());
                Calendar fecha = cotizacion.getDetalle().getFechaFinCalculo();
                Calendar fechaFinal = cotizacion.getDetalle().getFechaFinCalculo();
                for (Pago pago : compra.getPagos()) {
                    if (pago.getEstadoPago().getIdEstadoPago() == EstadoPagoEnum.POR_PAGAR.getId()
                            && pago.getFechaInicioPeriodo().before(fecha.getTime())) {
                        fecha.setTime(pago.getFechaInicioPeriodo());
                    }
                }
                // Si la fecha y fecha final son iguales, indica que el seguro
                // fue pagado completo y
                // hay que calcular el a�o completo
                if (DateUtils.isSameDay(fecha, fechaFinal)) {
                    fecha.add(Calendar.DATE, 1);
                    fechaFinal.add(Calendar.YEAR, 1);
                }
                Calendar fechaFinSeguroCancelado = (Calendar) fecha.clone();
                fechaFinSeguroCancelado.add(Calendar.DATE, -1);
                seg.setFecFin(fechaFinSeguroCancelado.getTime());
                entityManager.merge(seg);
            } catch (SUAException e) {
                LOGGER.error("Error al buscar la compra de un seguro, esto no deberia suceder nunca");
            }

        }
        return persona;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * SeguroIvroServiceLocal
     * #activaSeguros(mx.gob.imss.digital.modelo.cobranza.ActualizacionCompra)
     */
    @Override
    public List<SeguroIvro> activaSeguros(ActualizacionCompra comprasPagadas) {
        List<SeguroIvro> activas = new ArrayList<SeguroIvro>();
        DicEstadoSeguro activo = IvroFactory.estadoSeguro(EstadoSeguroIvroEnum.ACTIVO);
        if (comprasPagadas != null && comprasPagadas.getCompras() != null) {
            for (DatosCompra compra : comprasPagadas.getCompras()) {
                try {
                    activas.add(IvroFactory.generaSeguro(marcaSeguros(compra, activo)));
                } catch (IvroException e) {
                    LOGGER.error("No se eoncontro Seguro asociado a una compra, "
                            + "Esto no importa ");
                }
            }
        }
        return activas;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * SeguroIvroServiceLocal
     * #venceSeguros(mx.gob.imss.digital.modelo.cobranza.ActualizacionCompra)
     */
    @Override
    public List<SeguroIvro> venceSeguros(ActualizacionCompra comprasVencidas) {
        List<SeguroIvro> vencidas = new ArrayList<SeguroIvro>();
        DicEstadoSeguro vencido = IvroFactory.estadoSeguro(EstadoSeguroIvroEnum.VENCIDO);
        if (comprasVencidas != null && comprasVencidas.getCompras() != null) {
            for (DatosCompra compra : comprasVencidas.getCompras()) {
                try {
                    vencidas.add(IvroFactory.generaSeguro(marcaSeguros(compra, vencido)));
                } catch (IvroException e) {
                    LOGGER.error("No se eoncontro Seguro asociado a una compra, "
                            + "Esto no deberia de currir nunca", e);
                }
            }
        }
        return vencidas;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * SeguroIvroServiceLocal#vencimientosSeguroVigencia()
     */
    @Override
    public List<SeguroIvro> concluirSeguroVigencia() {
        List<SeguroIvro> concluidas = new ArrayList<SeguroIvro>();
        List<DitSeguroIvro> seguros = consultaSeguroIvroLocal.buscaSegurosAConcluir();
        for (DitSeguroIvro seguro : seguros) {
            marcaSeguroConcluido(seguro);
            concluidas.add(IvroFactory.generaSeguro(seguro));
        }
        return concluidas;
    }

    /**
     * Indica si un seguro ya fue renovado para una persona
     *
     * @param seguro el seguro del cual se verifica su renovacion
     * @param activos los estados de los seguro validos a verificar renovacion
     * @return true si el seguro ya fue renovado, false en caso contrario
     */
    private boolean seguroRenovadoPersona(DitSeguroIvro seguro, List<DicEstadoSeguro> activos) {
        // Si es un seguro individual verificamos si ya cuenta con un seguro
        // nuevo y pagado
        Persona persona = new Persona();
        persona.setIdPersona(seguro.getDitPersona().getCveIdPersona());
        List<DicModalidad> modalidades = new ArrayList<DicModalidad>();
        modalidades.add(seguro.getDicModalidad());
        List<SeguroIvro> nuevos = consultaSeguroIvroLocal.buscaSegurosPersona(persona, modalidades,
                activos);
        boolean renovado = false;
        for (SeguroIvro segActivo : nuevos) {
            if (segActivo.getCveIdSeguroIvro() != seguro.getCveIdSeguroIvro()) {
                renovado = true;
            }
        }
        return renovado;
    }

    /**
     * Daod un seguro se busca si ya fue renovado y que personas han quedado en
     * la nueva renovacion, las personas que no son renovadas se regresan para
     * su baja
     *
     * @param seguro el seguro a buscar e identificar sus renovaciones
     * @param domesticos lista de modalidad ara un domestico
     * @param activos lista de estados activos para la consulta del seguro
     * @return la lista de personas no renovadas en el seguro para dar de baja
     */
    private List<Fisica> personaNoRenovada(DitSeguroIvro seguro, List<DicModalidad> domesticos,
            List<DicEstadoSeguro> activos) {
        List<Fisica> noRenovadas = new ArrayList<Fisica>();

        Persona persona = new Persona();
        persona.setIdPersona(seguro.getDitPersona().getCveIdPersona());
        List<SeguroIvro> segurosActivos = consultaSeguroIvroLocal.buscaSegurosPersona(persona,
                domesticos, activos);
        boolean renovado = false;
        for (SeguroIvro seguroActivo : segurosActivos) {
            if (seguroActivo.getTramite() != null
                    && seguroActivo.getTramite().getIdSeguroAnterior() != null
                    && seguroActivo.getTramite().getIdSeguroAnterior() == seguro.getCveIdSeguroIvro()) {
                renovado = true;
                noRenovadas.addAll(domesticosNoRenovados(seguro, seguroActivo));
                break;
            }
        }
        if (!renovado) {
            TramiteSeguroIvro tramite = IvroFactory.generaTramite(seguro.getDitTramites());
            if (tramite != null && tramite.getBeneficiarios() != null) {
                noRenovadas.addAll(Arrays.asList(tramite.getBeneficiarios()));
            } else {
                SeguroIvro segIvro = IvroFactory.generaSeguro(seguro);
                noRenovadas.addAll(consultaBeneficiarioMigradoEntity
                        .buscaBeneficiariosSeguro(segIvro));
            }
        }
        return noRenovadas;
    }

    /**
     * Dado un seguro anterior y su renovacion se verifican las personas que ya
     * no existen ene le nuevo seguro para daras de baja
     *
     * @param seguro el seguro anterior
     * @param seguroActivo el nuevo seguro
     * @return lal ista de personas que ya no se encuentran en e nuevo seguro
     */
    private List<Fisica> domesticosNoRenovados(DitSeguroIvro seguro, SeguroIvro seguroActivo) {
        List<Fisica> noRenovadas = new ArrayList<Fisica>();
        SeguroIvro seguroAnterior = IvroFactory.generaSeguro(seguro);
        Fisica[] benefAnterior;
        if (seguroAnterior.getTramite() != null
                && seguroAnterior.getTramite().getBeneficiarios() != null) {
            benefAnterior = seguroAnterior.getTramite().getBeneficiarios();
        } else {
            List<Fisica> fisicas = consultaBeneficiarioMigradoEntity
                    .buscaBeneficiariosSeguro(seguroAnterior);
            benefAnterior = fisicas.toArray(new Fisica[fisicas.size()]);
        }

        Fisica[] benefNuevos = seguroActivo.getTramite().getBeneficiarios();
        if (benefAnterior != null && benefNuevos != null) {
            for (Fisica anterior : benefAnterior) {
                boolean encontrada = false;
                for (Fisica nueva : benefNuevos) {
                    if (anterior.getNss().equals(nueva.getNss())) {
                        encontrada = true;
                        break;
                    }
                }
                if (!encontrada) {
                    noRenovadas.add(anterior);
                }
            }
        } else if (benefAnterior != null) {
            noRenovadas.addAll(Arrays.asList(benefAnterior));
        }
        return noRenovadas;
    }

    /**
     * Marca un seguro como concluido
     *
     * @param seguro el seguro a marcar
     */
    private void marcaSeguroConcluido(DitSeguroIvro seguro) {
        DicEstadoSeguro concluido = IvroFactory.estadoSeguro(EstadoSeguroIvroEnum.CONCLUIDO);
        Date hoy = new Date();
        seguro.setDicEstadoSeguro(concluido);
        seguro.setFecRegistroActualizado(hoy);
        entityManager.merge(seguro);
    }

    /**
     * Marca una seguro con el estado indicado
     *
     * @param compras la compra
     * @param estado el estado del seguro
     * @return el seguro encontrado y marcado
     * @throws IvroException errores al marcar del seguro
     */
    private DitSeguroIvro marcaSeguros(DatosCompra compras, DicEstadoSeguro estado)
            throws IvroException {
        DitSeguroIvro seguro = consultaSeguroIvroLocal.buscaSeguroCompra(compras.getIdCompra());
        seguro.setDicEstadoSeguro(estado);
        seguro.setFecRegistroActualizado(new Date());
        entityManager.merge(seguro);
        return seguro;
    }

    /**
     * Genera el objeto de movieimiento para un seguro y la lista de
     * trabajadores a asociar
     *
     * @param seguro el seguro a obtener informacion del movimiento
     * @param trabajadores la lista de trabajaores que aplican al movimiento
     * @param alta indica si el movimiento es de alta o baja
     * @param tramite el tramite del seguro ivro
     * @return los daos para generar movimientos
     */
    private List<DatosMovSeguro> generaDatosMovimiento(DitSeguroIvro seguro,
            List<Fisica> trabajadores, boolean alta, TramiteSeguroIvro tramite) {
        List<DatosMovSeguro> movimientos = new ArrayList<DatosMovSeguro>();
        try {
            String nrp = null;
            String nrp35 = null;
            UnidadMedicoFamiliar umf = null;
            boolean bimestral = true;
            boolean conBeneficio = false;
            Cotizacion cotizacion = null;
            LOGGER.info("Generando movimiento");
            if (seguro.getDitCompra() != null) {
                LOGGER.info("Generando movimiento con compra");
                cotizacion = cotizacionServiceRemote.findCotizacion(seguro.getDitCompra()
                        .getDitCotizacion().getCveIdCotizacion());

                Compra compraSeguro = compraServiceRemote.findCompraById(seguro.getDitCompra()
                        .getCveIdCompra());

                LOGGER.info("<->Forma de pago" + compraSeguro.getFormaPago());
                if (compraSeguro.getFormaPago() != null
                        && (compraSeguro.getFormaPago() == FormaPagoEnum.ANUAL.getId()
                        || compraSeguro.getFormaPago() == FormaPagoEnum.ANUAL_ANTICIPADO.getId()
                        || compraSeguro.getFormaPago() == FormaPagoEnum.MENSUAL.getId())) {
                    bimestral = false;
                }

                if (StringUtils.trimToNull(cotizacion.getDetalle().getNumeroRegistroPatronal()) != null) {
                    nrp = cotizacion.getDetalle().getNumeroRegistroPatronal();
                } else if (compraSeguro.getPagos()[0] != null
                        && compraSeguro.getPagos()[0].getSuaPago() != null
                        && compraSeguro.getPagos()[0].getSuaPago().getPatron() != null
                        && StringUtils.trimToNull(compraSeguro.getPagos()[0].getSuaPago()
                                .getPatron().getRegistroPatronalIMSS()) != null) {
                    nrp = compraSeguro.getPagos()[0].getSuaPago().getPatron()
                            .getRegistroPatronalIMSS();
                }
                conBeneficio = cotizacion.getDetalle().getConBeneficio() != null ? cotizacion
                        .getDetalle().getConBeneficio() : false;
            }
            EmpleadoCuota empleado = null;
            Integer idParentesco = null;
            if (cotizacion.getDetalle().getEmpleados() != null && cotizacion.getDetalle().getEmpleados().length > 0) {
                empleado = cotizacion.getDetalle().getEmpleados()[0];
            }
            SeguroIvro seguroIvro = IvroFactory.generaSeguro(seguro);
            if (nrp == null) {
                LOGGER.info("Buscando nrp migrado");
                nrp = consultaBeneficiarioMigradoEntity.obtenNrpPatron(seguroIvro);
            }
            if (seguro.getDicModalidad().getCveIdModalidad() == ModalidadEnum.TREINTAYCINCO.getId()
                    && tramite != null) {
                nrp35 = tramite.getNrpFisica();
            } else if (seguro.getDicModalidad().getCveIdModalidad() == ModalidadEnum.TREINTAYTRES.getId()
                    && tramite != null) {
                if (empleado != null) {
                    idParentesco = IvroSolicitudUtil.obtenTipoTrabajdor(empleado, new Long(seguro.getDicModalidad().getCveIdModalidad()).intValue());
                    LOGGER.info("idParentesco: "+idParentesco);
                }
                umf = tramite.getPersona().getUmfAsociado();
            }
            LOGGER.info("trabajadores: "+trabajadores.size());
            for (Fisica trabajador : trabajadores) {
                DatosMovSeguro movimiento = new DatosMovSeguro();

                trabajador.setUmfAsociado(umf);

                movimiento.setPersona(trabajador);
                movimiento.setNrp35(nrp35);
                movimiento.setNrp(nrp);
                movimiento.setConBeneficio(conBeneficio);
                movimiento.setPagoBimestral(bimestral);

                Fisica titular = tramite.getPersona();
                if (titular != null) {
                    movimiento.setDomicilioSeguro(titular.getDomicilioParticular());
                }

                Calendar fecMov = Calendar.getInstance();
                if (alta) {
                    fecMov.setTime(seguro.getFecInicio());
                    movimiento.setSalario(getSalario(trabajador, cotizacion != null ? cotizacion
                            .getDetalle().getEmpleados() : null));
                    Date hoy = Calendar.getInstance().getTime();
                    if (hoy.before(seguro.getFecInicio())) {
                        movimiento.setMovimientoFuturo(true);
                    }
                    if (hoy.after(seguro.getFecInicio())) {
                        if (seguro.getFecInicio().before(seguro.getFecRegistroAlta())) {
                            movimiento.setExtemporaneo(true);
                        } else {
                            movimiento.setExtemporaneo(false);
                        }
                        fecMov = Calendar.getInstance();
                    }
                }
                fecMov.add(Calendar.DAY_OF_MONTH, -1);
                movimiento.setFechaMovimiento(IvroDateUtils.getFechaHabil(fecMov,
                        new ArrayList<Date>()));
                movimiento.setFechaInicio(seguro.getFecInicio());
                if (idParentesco != null && empleado != null && trabajador.getNss().equals(empleado.getNumeroSeguridadSocial())) {
                    LOGGER.info("idParentesco true: "+idParentesco);
                    movimiento.setIdTipoTrabajador(idParentesco);
                } else {

                    if(idParentesco!=null && !idParentesco.equals(1)){
                        LOGGER.info("Se asigna el idParentesco que corresponde: "+idParentesco);
                        movimiento.setIdTipoTrabajador(idParentesco);
                    }else{
                        LOGGER.info("idParentesco: "+idParentesco + ", se transforma en: 1");
                        movimiento.setIdTipoTrabajador(1);
                    }
                }
                movimientos.add(movimiento);
            }

        } catch (SUAException e) {
            LOGGER.error(
                    "Aqui no se deben generar errores de consulta para compras o cotizaciones", e);
        }

        return movimientos;
    }

    private List<DatosMovSeguro> generaDatosMovimientoCvro(
            DitSeguroIvro seguro, List<Fisica> trabajadores, boolean alta,
            TramiteSeguroIvro tramite, boolean primerPago) {
        List<DatosMovSeguro> movimientos = new ArrayList<DatosMovSeguro>();

        try {
            DitCompra compra = seguro.getDitCompra();

            if (compra != null) {
                LOGGER.debug("Generando movimiento con compra");
                Cotizacion cotizacion = cotizacionServiceRemote.findCotizacion(
                        compra.getDitCotizacion().getCveIdCotizacion());

                Compra compraSeguro = compraServiceRemote.findCompraById(compra.getCveIdCompra());

                CalculoCuota detalleCotizacion = cotizacion.getDetalle();
                if (detalleCotizacion.getAplicaRecargoPorFechaBaja() != null
                        && detalleCotizacion.getAplicaRecargoPorFechaBaja()) {
                    EmpleadoCuota[] lstDetalleEmpleado = detalleCotizacion.getEmpleados();

                    if (lstDetalleEmpleado != null) {
                        for (EmpleadoCuota empleadoCuota : lstDetalleEmpleado) {
                            PeriodoCuota[] lstPeriodos = empleadoCuota.getPeriodos();

                            if (lstPeriodos != null) {
                                if (lstPeriodos.length > 1 && primerPago) {
                                    movimientos.addAll(generaDatosMovimientoPeriodo(lstPeriodos, seguro,
                                            trabajadores, compraSeguro, detalleCotizacion, tramite.getPersona()));
                                } else {
                                    movimientos.addAll(generaDatosMovimiento(seguro, trabajadores, alta, tramite));
                                }
                            }
                        }
                    }
                } else {
                    movimientos = generaDatosMovimiento(seguro, trabajadores, alta, tramite);
                }
            }
        } catch (SUAException e) {
            LOGGER.error("Aqui no se deben generar errores de consulta para compras o cotizaciones", e);
        }

        return movimientos;
    }

    private List<DatosMovSeguro> generaDatosMovimientoPeriodo(PeriodoCuota[] lstPeriodos, DitSeguroIvro seguro,
            List<Fisica> trabajadores, Compra compraSeguro, CalculoCuota detalleCotizacion, Fisica titular) {
        List<DatosMovSeguro> movimientos = new ArrayList<DatosMovSeguro>();
        List<MovimientoPeriodo> lstMovimientoPeriodo = new ArrayList<MovimientoPeriodo>();
        List<PeriodoCuota> listPeriodoCuota = ordenaListaPeriodoCuota(Arrays.asList(lstPeriodos));
        int orden = 1;

        MovimientoPeriodo movimientoPeriodo = new MovimientoPeriodo();
        PeriodoCuota primerPeriodo = listPeriodoCuota.get(0);
        movimientoPeriodo.setFechaInicio(primerPeriodo.getInicioPeriodo().getTime());
        movimientoPeriodo.setSalarioCotizacion(primerPeriodo.getSalarioPeriodo());
        movimientoPeriodo.setOrden(orden);

        // Se crea un movimiento periodo para cada mes retroactivo
        for (int index = 1; index < listPeriodoCuota.size(); index++) {
            PeriodoCuota periodoAnterior = listPeriodoCuota.get(index - 1);
            PeriodoCuota periodoActual = listPeriodoCuota.get(index);

            movimientoPeriodo.setFechaFin(periodoAnterior.getFinPeriodo().getTime());
            lstMovimientoPeriodo.add(movimientoPeriodo);

            orden++;
            movimientoPeriodo = new MovimientoPeriodo();
            movimientoPeriodo.setFechaInicio(periodoActual.getInicioPeriodo().getTime());
            movimientoPeriodo.setSalarioCotizacion(periodoActual.getSalarioPeriodo());
            movimientoPeriodo.setOrden(orden);

            if (index + 1 == listPeriodoCuota.size()) {
                movimientoPeriodo.setFechaFin(periodoActual.getFinPeriodo().getTime());
                lstMovimientoPeriodo.add(movimientoPeriodo);
            }
        }

        // Por cada periodo de mes retroactivo se genera un movimiento de alta y uno de baja
        lstMovimientoPeriodo = ordenaListaMovimientoPeriodo(lstMovimientoPeriodo);

        for (int index = 0; index < lstMovimientoPeriodo.size(); index++) {
            MovimientoPeriodo movPeriodo = lstMovimientoPeriodo.get(index);

            for (Fisica trabajador : trabajadores) {
                DatosMovSeguro movimientoAlta = generaMovimientoPeriodoCvro(trabajador, detalleCotizacion, seguro, compraSeguro,
                        titular, movPeriodo, true);
                movimientos.add(movimientoAlta);

                if (index + 1 != lstMovimientoPeriodo.size()) {
                    DatosMovSeguro movimientoBaja = generaMovimientoPeriodoCvro(trabajador, detalleCotizacion, seguro,
                            compraSeguro, titular, movPeriodo, false);
                    movimientos.add(movimientoBaja);
                }
            }
        }

        return movimientos;
    }

    private DatosMovSeguro generaMovimientoPeriodoCvro(Fisica trabajador,
            CalculoCuota detalleCotizacion,
            DitSeguroIvro seguro, Compra compraSeguro, Fisica titular,
            MovimientoPeriodo movPeriodo, boolean alta) {
        String nrp = null;
        boolean bimestral = true;

        Pago primerPago = compraSeguro.getPagos()[0];
        Long formaPago = compraSeguro.getFormaPago();

        // Obtencion de NRP
        if (StringUtils.isNotBlank(detalleCotizacion.getNumeroRegistroPatronal())) {
            nrp = detalleCotizacion.getNumeroRegistroPatronal();
        } else if (primerPago != null
                && primerPago.getSuaPago() != null
                && primerPago.getSuaPago().getPatron() != null
                && StringUtils.isNotBlank(primerPago.getSuaPago().getPatron().getRegistroPatronalIMSS())) {
            nrp = primerPago.getSuaPago().getPatron().getRegistroPatronalIMSS();
        }
        if (nrp == null) {
            SeguroIvro seguroIvro = new SeguroIvro();
            seguroIvro.setCveIdSeguroIvro(seguro.getCveIdSeguroIvro());
            nrp = consultaBeneficiarioMigradoEntity.obtenNrpPatron(seguroIvro);
        }

        // Forma de pago
        if (formaPago != null && (formaPago == FormaPagoEnum.ANUAL.getId()
                || formaPago == FormaPagoEnum.ANUAL_ANTICIPADO.getId()
                || formaPago == FormaPagoEnum.MENSUAL.getId())) {
            bimestral = false;
        }

        // Si aplica beneficio
        boolean conBeneficio = detalleCotizacion.getConBeneficio() != null
                ? detalleCotizacion.getConBeneficio() : false;

        DatosMovSeguro movimiento = new DatosMovSeguro();

        movimiento.setPersona(trabajador);
        movimiento.setNrp(nrp);
        movimiento.setConBeneficio(conBeneficio);
        movimiento.setPagoBimestral(bimestral);

        if (titular != null) {
            movimiento.setDomicilioSeguro(titular.getDomicilioParticular());
        }

        Calendar fecMov = Calendar.getInstance();
        if (alta) {
            fecMov.setTime(seguro.getFecInicio());
            movimiento.setSalario(movPeriodo.getSalarioCotizacion());
            Date hoy = Calendar.getInstance().getTime();
            if (hoy.before(seguro.getFecInicio())) {
                movimiento.setMovimientoFuturo(true);
            }
            if (hoy.after(seguro.getFecInicio())) {
                if (seguro.getFecInicio().before(seguro.getFecRegistroAlta())) {
                    movimiento.setExtemporaneo(true);
                } else {
                    movimiento.setExtemporaneo(false);
                }
                fecMov = Calendar.getInstance();
            }

            movimiento.setAplicaMovBajaIntegrado(false);
            movimiento.setFechaInicio(movPeriodo.getFechaInicio());
        } else {
            movimiento.setAplicaMovBajaIntegrado(true);
            movimiento.setFechaInicio(movPeriodo.getFechaFin());
        }
        fecMov.add(Calendar.DAY_OF_MONTH, -1);
        movimiento.setFechaMovimiento(IvroDateUtils.getFechaHabil(fecMov,
                new ArrayList<Date>()));
        movimiento.setIdTipoTrabajador(1);
        return movimiento;
    }

    private List<PeriodoCuota> ordenaListaPeriodoCuota(List<PeriodoCuota> listPeriodoCuota) {
        Collections.sort(listPeriodoCuota, new Comparator<PeriodoCuota>() {
            @Override
            public int compare(PeriodoCuota periodo1, PeriodoCuota periodo2) {
                return periodo1.getOrden().compareTo(periodo2.getOrden());
            }
        });

        return listPeriodoCuota;
    }

    private List<MovimientoPeriodo> ordenaListaMovimientoPeriodo(List<MovimientoPeriodo> listMovimientoPeriodo) {
        Collections.sort(listMovimientoPeriodo, new Comparator<MovimientoPeriodo>() {
            @Override
            public int compare(MovimientoPeriodo movimiento1, MovimientoPeriodo movimiento2) {
                return movimiento1.getOrden().compareTo(movimiento2.getOrden());
            }
        });

        return listMovimientoPeriodo;
    }

    /**
     * Obtiene el salario de un empleado
     *
     * @param persona la persona a buscar su salario
     * @param empleados lista de empleados a asociar el salario
     * @return el salario del empleado
     */
    private BigDecimal getSalario(Fisica persona, EmpleadoCuota[] empleados) {
        BigDecimal salario = BigDecimal.ZERO;
        if (empleados != null && empleados.length == 1) {
            return empleados[0].getSalario();
        } else if (empleados != null) {
            for (EmpleadoCuota empleado : empleados) {
                if (empleado.getNumeroSeguridadSocial().equals(persona.getNss())) {
                    return empleado.getSalario();
                }
            }
        }
        return salario;
    }

    /**
     * Dado un seguro verifica si es nuevo o se trata de una renovacion
     *
     * @param seg el seguro a verificar si es nuevo o renovacion
     * @param activos estados validos para verificar si tiene un seguro en
     * renovacion
     * @return true si se trata de un seguro nuevo
     */
    private boolean seguroNuevo(SeguroIvro seg, List<DicEstadoSeguro> activos) {
        Persona persona = seg.getTitular();

        DicModalidad dicModalidad = new DicModalidad();
        dicModalidad.setCveIdModalidad(seg.getModalidad().getIdModalidad());

        List<DicModalidad> modalidades = new ArrayList<DicModalidad>();
        modalidades.add(dicModalidad);

        List<SeguroIvro> segActivos = consultaSeguroIvroLocal.buscaSegurosPersona(persona, modalidades,
                activos);
        boolean nuevo = true;
        for (SeguroIvro segActivo : segActivos) {
            LOGGER.info("Validando seguro nuevo {}", segActivo.getCveIdSeguroIvro());
            LOGGER.info("Validando seguro nuevo {}", seg.getCveIdSeguroIvro());
            if (segActivo.getCveIdSeguroIvro().longValue() != seg.getCveIdSeguroIvro().longValue()) {
                LOGGER.info("No es  seguro nuevo");
                nuevo = false;
            }
//            if (segActivo.getCveIdSeguroIvro().longValue() == seg.getCveIdSeguroIvro().longValue()) {
//                LOGGER.info("Si  seguro nuevo");
//                nuevo = true;
//                break;
//            }
        }
        return nuevo;
    }

    @Override
    public boolean existeRechazo(Long idPersona, Long idModalidad) {


        if (idModalidad.equals(ModalidadEnum.TREINTAYTRES.getId())) {
            return existeRechazoModFamiliar(idPersona);
        } else {

            Object tipoSolicitud = null;

            if (idModalidad.equals(ModalidadEnum.TREINTAYCINCO.getId())
                    || idModalidad.equals(ModalidadEnum.TREINTAYCUATRO.getId())) {
                tipoSolicitud = TipoSolicitudEnum.COMPRA_SEGURO.getId();
            } else if (idModalidad.equals(ModalidadEnum.CUARENTA.getId())) {
                tipoSolicitud = TipoSolicitudEnum.CONTINUACION_VOLUNTARIA_REGIMEN_OBLIGATORIO.getId();
            }

            StringBuffer sb = new StringBuffer();
            sb.append("select count(*) from DitSolicitud s join s.ditTramites t join t.ditTramitePersonaFisica tpf ");
            sb.append("where tpf.id.cveIdPersona =:idPersona and s.dicTipoSolicitud.cveIdTipoSolicitud=:tipoSolicitud ");
            sb.append("and t.dicEstadoTramite.cveIdEstadoTramite=:estadotramite ");
            Query query = entityManager.createQuery(sb.toString());
            query.setParameter("idPersona", idPersona);
            query.setParameter("estadotramite", EstadoTramiteEnum.RECHAZADO.getId());

            if (tipoSolicitud != null) {
                query.setParameter("tipoSolicitud", tipoSolicitud);
            }
            Long numSolicitudes = (Long) query.getSingleResult();

            return (numSolicitudes != null && numSolicitudes > 0);
        }
    }


    private boolean existeRechazoModFamiliar(Long idPersona){

        StringBuffer sb = new StringBuffer();

        sb.append("SELECT count(*) ");
        sb.append("FROM DIT_LLAVE_ASEGURADO LLAV ");
        sb.append("INNER JOIN DIT_RECHAZOENFERMEDAD_MOD33 REC ");
        sb.append("ON LLAV.CVE_ID_ASIGNACION_NSS = REC.CVE_ID_ASIGNACION_NSS ");
        sb.append("WHERE LLAV.CVE_ID_PERSONA = :cveIdPersona ");
        sb.append("AND REC.FEC_REGISTRO_BAJA IS NULL");

        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("cveIdPersona", idPersona);

        Object result = query.getSingleResult();

        Integer numSolicitudes = null;

        if(result instanceof  BigDecimal){
            LOGGER.info("BigDecimal");
            BigDecimal num = (BigDecimal) result;
            numSolicitudes = num.intValue();
        }

        if(result instanceof Long){
            LOGGER.info("Long");
            Long num = (Long) result;
            numSolicitudes = num.intValue();
        }

        if(result instanceof String){
            LOGGER.info("String");
            String num = (String) result;
            numSolicitudes = Integer.parseInt(num);
        }

        return (numSolicitudes != null && numSolicitudes > 0);
    }

    @Override
    public void desactivarSegurosRechazados(List<DatosMovSeguro> datos) {
        for (DatosMovSeguro dato : datos) {
            try {
                Fisica asegurado = buscaTrabajador(dato.getPersona());
                Long idPersona = asegurado.getIdPersona();

                if (existeRechazo(idPersona, ModalidadEnum.TREINTAYCUATRO.getId())) {
                    List<Long> listTramitesRechazadas = obtenerListaTramitesRechazados(idPersona);
                    for (Long idTramite : listTramitesRechazadas) {
                        solicitudServiciosExpuestos.cancelarTramitePorId(idTramite);
                    }
                }
            } catch (Exception e) {
                LOGGER.error("No se puede cancelar el seguro rechazado");
            }
        }
    }

    @SuppressWarnings("unchecked")
    private List<Long> obtenerListaTramitesRechazados(Long idPersona) {
        StringBuffer sb = new StringBuffer();
        sb.append("select distinct t.cveIdTramite from DitSolicitud s join s.ditTramites t join t.ditTramitePersonaFisica tpf ");
        sb.append("where tpf.id.cveIdPersona =:idPersona and s.dicTipoSolicitud.cveIdTipoSolicitud=:tipoSolicitud ");
        sb.append("and t.dicEstadoTramite.cveIdEstadoTramite=:estadotramite ");
        Query query = entityManager.createQuery(sb.toString());
        query.setParameter("idPersona", idPersona);
        query.setParameter("tipoSolicitud", TipoSolicitudEnum.COMPRA_SEGURO.getId());
        query.setParameter("estadotramite", EstadoTramiteEnum.RECHAZADO.getId());
        List<Long> listTramitesRechazadas = (List<Long>) query.getResultList();

        return listTramitesRechazadas;
    }

    private Fisica buscaTrabajador(Fisica persona) throws IvroException {
        mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica trabajador = null;
        if (StringUtils.trimToNull(persona.getNss()) != null) {
            try {
                trabajador = personaFisicaServiceBusiness.localizarPersonaFisicaPorNss(persona
                        .getNss());
            } catch (PersonasNoLocalizadasException e) {
                LOGGER.error("No se ecuntra al trabajador por su nss ", e);
            } catch (NssRelacionadoVariasPersonasException e) {
                LOGGER.error("El NSS del trabajdor se encuetra varias veces", e);
            }
        } else if (persona.getIdPersona() != null) {
            mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica personaBusqueda
                    = new mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica();
            personaBusqueda.setIdPersona(persona.getIdPersona());
            try {
                trabajador = (mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica) personaBusinessRemote
                        .buscarPersnaPorID(persona.getIdPersona());
            } catch (PersonaNoEncontradaException e1) {
                LOGGER.warn(
                        "NO debe existir errores de consulta persona ya que se validaron antes", e1);
            }

            if (trabajador != null) {
                try {
                    String nss = personaBusinessRemote.obtenerNssPersona(trabajador.getIdPersona());
                    trabajador.setNss(nss);
                } catch (PersonaConVariosNSSException e) {
                    LOGGER.warn("NO debe existir errores de nss ya que se validaron antes", e);
                } catch (PersonaSinNSSException e) {
                    LOGGER.warn("NO debe existir errores de nss ya que se validaron antes", e);
                }
            }
        }
        Fisica encontrado;
        if (trabajador != null) {
            encontrado = copiaValores(trabajador);
        } else {
            throw new IvroException(IvroConstants.COD_NO_TRABAJADOR,
                    IvroConstants.MSG_NO_TRABAJADOR);
        }

        return encontrado;

    }

    private Fisica copiaValores(
            mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica modeloAnterior) {
        Fisica nuevo = new Fisica();
        nuevo.setIdPersona(modeloAnterior.getIdPersona());
        nuevo.setNss(modeloAnterior.getNss());
        nuevo.setNombre(modeloAnterior.getNombre());
        nuevo.setPrimerApellido(modeloAnterior.getPrimerApellido());
        nuevo.setSegundoApellido(modeloAnterior.getSegundoApellido());
        return nuevo;
    }

    @Override
    public List<DatosMovSeguro> obtenerSegurosCvroBajaMensual() {

        List<DatosMovSeguro> movimientos = new ArrayList<DatosMovSeguro>();

        List<DitSeguroIvro> seguros = consultaSeguroIvroLocal
                .buscaSegurosCvroUltimoPagoPagado();

        for (DitSeguroIvro seguro : seguros) {
            List<Fisica> concluidas = new ArrayList<Fisica>();

            Fisica fisica = new Fisica();
            fisica.setIdPersona(seguro.getDitPersona().getCveIdPersona());
            concluidas.add(fisica);

            if (!concluidas.isEmpty()) {
                SeguroIvro seguroIvro = IvroFactory.generaSeguro(seguro);
                List<DatosMovSeguro> movimiento = generaDatosMovimiento(seguro,
                        concluidas, false, seguroIvro.getTramite());
                movimientos.addAll(movimiento);
            }
        }

        return movimientos;
    }

    /**
     * Genera los movimientos necesarios para la baja mensual cvro
     *
     * @param seguroIvro El seguro al que se le generará los movimientos de
     * baja
     * @return
     */
    @Override
    public List<DatosMovSeguro> generaDatosMovimientosBajaMensualCvro(SeguroIvro seguroIvro) {

        DitSeguroIvro ditSeguroIvro = entityManager.find(DitSeguroIvro.class, seguroIvro.getCveIdSeguroIvro());

        List<Fisica> concluidas = new ArrayList<Fisica>();

        Fisica fisica = new Fisica();
        fisica.setIdPersona(ditSeguroIvro.getDitPersona().getCveIdPersona());
        concluidas.add(fisica);

        return generaDatosMovimiento(ditSeguroIvro, concluidas, false, seguroIvro.getTramite());
    }

    @Override
    public void venceSegurosPorMoraMod40(ActualizacionCompra comprasVencidas) {
        DicEstadoSeguro vencido = IvroFactory.estadoSeguro(EstadoSeguroIvroEnum.BAJA_POR_MORA);
        if (comprasVencidas != null && comprasVencidas.getCompras() != null) {
            for (DatosCompra compra : comprasVencidas.getCompras()) {
                try {
                    DitSeguroIvro seguro = marcaSeguros(compra, vencido);
                    SeguroIvro seg = IvroFactory.generaSeguro(seguro);

                    //Se enviara correo de notificacion de Baja por Mora
                    enviaCorreoLocal.enviaCorreo(seg, TipoOperacionNotificacionIVROEnum.BAJA_POR_MORA.getCodigo());

                } catch (IvroException e) {
                    LOGGER.error("No se eoncontro Seguro asociado a una compra, Esto no deberia de currir nunca", e);
                }
            }
        }
    }

    @Override
    public List<DatosMovSeguro> bajaDeSeguroPorReingresoRO(SeguroIvro seguroIvro) {
        List<DatosMovSeguro> movimientos = new ArrayList<DatosMovSeguro>();
        DicEstadoSeguro estado = IvroFactory.estadoSeguro(EstadoSeguroIvroEnum.BAJA_POR_REINGRESO_RO);

        DitSeguroIvro seguro = entityManager.find(DitSeguroIvro.class, seguroIvro.getCveIdSeguroIvro());
        seguro.setDicEstadoSeguro(estado);
        seguro.setFecRegistroActualizado(new Date());
        entityManager.merge(seguro);

        List<Fisica> causanBaja = new ArrayList<Fisica>();

        Fisica persona = new Fisica();
        persona.setIdPersona(seguro.getDitPersona().getCveIdPersona());
        causanBaja.add(persona);

        seguroIvro = IvroFactory.generaSeguro(seguro);
        enviaCorreoLocal.enviaCorreo(seguroIvro,
                TipoOperacionNotificacionIVROEnum.BAJA_POR_REINGRESO.getCodigo());
        List<DatosMovSeguro> movimiento = generaDatosMovimiento(seguro,
                causanBaja, false, seguroIvro.getTramite());
        movimientos.addAll(movimiento);

        return movimientos;
    }

    /**
     * Actualiza el seguro a estado de baja por mora, su compra a vencida y sus
     * pagos pendientes a vencidos
     *
     * @param seguroIvro El seguro con únicamente el id como obligatorio que se
     * actualizará
     */
    @Override
    public void bajaDeSeguroPorMora(SeguroIvro seguroIvro) {
        DicEstadoSeguro estado = IvroFactory.estadoSeguro(EstadoSeguroIvroEnum.BAJA_POR_MORA);

        DitSeguroIvro ditSeguroIvro = entityManager.find(DitSeguroIvro.class, seguroIvro.getCveIdSeguroIvro());
        ditSeguroIvro.setDicEstadoSeguro(estado);
        ditSeguroIvro.setFecRegistroActualizado(new Date());

        DitCompra ditCompra = ditSeguroIvro.getDitCompra();
        DicEstadoCompra dicEstadoCompra = new DicEstadoCompra();
        dicEstadoCompra.setCveIdEstadoCompra(EstadoCompraEnum.VENCIDO.getId());

        ditCompra.setDicEstadoCompra(dicEstadoCompra);
        ditCompra.setFecRegistroActualizado(new Date());

        List<DitPago> ditPagos = ditCompra.getDitPagos();

        DicEstadoPago dicEstadoPago = new DicEstadoPago();
        dicEstadoPago.setCveIdEstadoPago(EstadoPagoEnum.VENCIDO.getId());

        for (DitPago ditPago : ditPagos) {
            if (ditPago.getDicEstadoPago().getCveIdEstadoPago() == EstadoPagoEnum.POR_PAGAR.getId()) {
                ditPago.setDicEstadoPago(dicEstadoPago);
                ditPago.setFecRegistroActualizado(new Date());
            }
        }

        entityManager.merge(ditSeguroIvro);

        //Se enviara correo de notificacion de Baja por Mora
        enviaCorreoLocal.enviaCorreo(seguroIvro, TipoOperacionNotificacionIVROEnum.BAJA_POR_MORA.getCodigo());
    }

    @Override
    public String obtenZonaSalarialOriginal(String cveEnt, String cveMun) {

        StringBuffer q = new StringBuffer();
        q.append("SELECT DISTINCT DECODE(AG.CVE_ID_AREA_GEOGRAFICA,1,'A',2,'B',3,'C',4,'D','I')AREA_GEOGRAFICA ")
        .append("FROM DIT_MUNICIPIO_IMSS_INEGI   MII ")
        .append("INNER JOIN DIC_MUNICIPIO_IMSS   MI ON MII.CVE_ID_MUNICIPIO_IMSS = MI.CVE_ID_MUNICIPIO_IMSS ")
        .append("INNER JOIN DIC_AREA_GEOGRAFICA  AG ON MI.CVE_ID_AREA_GEOGRAFICA = AG.CVE_ID_AREA_GEOGRAFICA ")
        .append("WHERE MII.CVE_ENT = :cveEnt ")
        .append("AND MII.CVE_MUN = :cveMun ");
        try {

            Query query = entityManager.createNativeQuery(q.toString());
            query.setParameter("cveEnt", cveEnt);
            query.setParameter("cveMun", cveMun);
            List zonaSalList = query.getResultList();

            Object resultado  = (Object) zonaSalList.get(0);
            if(resultado instanceof String){
                LOGGER.info("El resultado es "+ resultado);
                return (String) resultado;
            }else{
                LOGGER.info("El resultado no es String");
            }
        }catch (NoResultException e) {
            LOGGER.error("No se encontro Zona Salarial para el cveEnt "+cveEnt+" cveMun "+cveMun);
        } catch (NonUniqueResultException n) {
            LOGGER.error("Se encontro mas de una Zona Salarial para el cveEnt "+cveEnt+" cveMun "+cveMun);
        } catch (Exception e) {
            LOGGER.error("ERROR al ejecutar el QUERY: ",e);
        }

        return null;
    }

    public boolean insertaCancelacionCuestionario(Beneficiario beneficiarioCancelar, String numSolicitud){

        StringBuffer sb = new StringBuffer();

        sb.append("Insert into DIT_RECHAZOENFERMEDAD_MOD33 ");
        sb.append("(CVE_ID_ASIGNACION_NSS, CVE_ID_TRAMITE, TIP_DERECHOHABIENTE) ");
        sb.append("Values ");
        sb.append("(:cveIdAsignacion, :cveIdTramite, :tipDerechohabiente) ");

        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter("cveIdAsignacion", beneficiarioCancelar.getCveIdAsignacionNSS());
        query.setParameter("cveIdTramite", numSolicitud);
        query.setParameter("tipDerechohabiente", beneficiarioCancelar.getTipoBeneficiario());

        int resultado = query.executeUpdate();

        if(resultado==1)
            return true;

        return false;
    }

    public Long recuperaIdAsignacionPorNSS(String nss){

        StringBuffer sb = new StringBuffer();

        sb.append("SELECT LLAV.CVE_ID_ASIGNACION_NSS ");
        sb.append("FROM DIT_LLAVE_ASEGURADO LLAV ");
        sb.append("INNER JOIN DIT_ASIGNACION_NSS NSS  ");
        sb.append("ON LLAV.CVE_ID_ASIGNACION_NSS = NSS.CVE_ID_ASIGNACION_NSS ");
        sb.append("WHERE NSS.NUM_NSS = :nss ");
        sb.append("AND NSS.FEC_REGISTRO_BAJA IS NULL ");
        sb.append("AND ROWNUM < 2 ");

        Long resultado = null;

        try {
            Query query = entityManager.createNativeQuery(sb.toString());
            query.setParameter("nss", nss);

            Object result = query.getSingleResult();


            if (result instanceof BigDecimal) {
                LOGGER.info("BigDecimal");
                BigDecimal num = (BigDecimal) result;
                resultado = num.longValue();
            }

            if (result instanceof Long) {
                LOGGER.info("Long");
                resultado = (Long) result;
            }

            if (result instanceof String) {
                LOGGER.info("String");
                String num = (String) result;
                resultado = Long.parseLong(num);
            }
        }catch(Exception e){
            LOGGER.error("ERROR: ",e);
        }

        return resultado;
    }


    public boolean verificaBeneficiarioConEnfermedad(Long idAsignacion){
        StringBuffer sb = new StringBuffer();

        sb.append("SELECT count(*) from DIT_RECHAZOENFERMEDAD_MOD33 ");
        sb.append("where cve_id_asignacion_nss = :idAsignacion ");

        boolean resultado = false;

        try {
            Query query = entityManager.createNativeQuery(sb.toString());
            query.setParameter("idAsignacion", idAsignacion);

            BigDecimal count = (BigDecimal) query.getSingleResult();

            if (count.intValue() > 0) {
                resultado = true;
            }

        }catch(Exception e){
            LOGGER.error("ERROR: ",e);
        }

        return resultado;
    }
	

// ================================================================
// MÉTODO 1: PROCESAMIENTO NORMAL (recibe byte[])
// ================================================================

/**
 * Procesa archivo SINDO de bajas por reingreso a RO.
 * Recibe el contenido del archivo como byte[] (Opción B).
 *
 * @param contenidoArchivo Contenido del archivo SINDO (174 chars/línea)
 * @param nombreArchivo Nombre del archivo original
 * @param usuario Usuario que ejecuta el procesamiento
 * @return ID del lote creado
 * @throws IvroException Si hay error en procesamiento
 */
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public Long procesarArchivoSindoBajas(byte[] contenidoArchivo,
                                      String nombreArchivo,
                                      String usuario) throws IvroException {

    if (contenidoArchivo == null || contenidoArchivo.length == 0) {
        throw new IvroException("", "El contenido del archivo SINDO está vacío");
    }

    File tempFile = null;

    try {
        // Crear archivo temporal en WebLogic
        tempFile = File.createTempFile("SINDO_BAJAS_", ".txt");

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(tempFile);
            out.write(contenidoArchivo);
            out.flush();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    LOGGER.warn("Error cerrando FileOutputStream", e);
                }
            }
        }

        LOGGER.info("Archivo temporal creado: " + tempFile.getAbsolutePath() + " (" + contenidoArchivo.length + " bytes)");

        // Procesar archivo completo
        Long loteId = procesarArchivoSindoBajasInterno(
            tempFile.getAbsolutePath(),
            nombreArchivo,
            usuario,
            false  // NO solo staging, procesar todo
        );
        LOGGER.info("*** Terminó el procesamiento de bajas sindo ");
        return loteId;

    } catch (IOException e) {
        throw new IvroException("",
                "Error creando archivo temporal para procesamiento SINDO", e);
    } finally {
        if (tempFile != null && tempFile.exists()) {
            if (!tempFile.delete()) {
                LOGGER.warn("No se pudo eliminar archivo temporal: " + tempFile.getAbsolutePath());
            }
        }
    }
}


// ================================================================
// MÉTODO 2: CARGAR SOLO A STAGING (sin ejecutar bajas)
// ================================================================

/**
 * Carga archivo SINDO a staging sin ejecutar bajas.
 * Útil para procesamiento por fases.
 *
 * @param contenidoArchivo Contenido del archivo SINDO
 * @param nombreArchivo Nombre del archivo original
 * @param usuario Usuario que ejecuta la carga
 * @return ID del lote creado
 * @throws IvroException Si hay error en procesamiento
 */
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public Long cargarArchivoSoloStaging(byte[] contenidoArchivo,
                                      String nombreArchivo,
                                      String usuario) throws IvroException {

    if (contenidoArchivo == null || contenidoArchivo.length == 0) {
        throw new IvroException("", "El contenido del archivo SINDO está vacío");
    }

    File tempFile = null;

    try {
        tempFile = File.createTempFile("SINDO_STAGING_", ".txt");

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(tempFile);
            out.write(contenidoArchivo);
            out.flush();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    LOGGER.warn("Error cerrando FileOutputStream", e);
                }
            }
        }

        LOGGER.info("Archivo temporal creado para staging: " + tempFile.getAbsolutePath() + " (" + contenidoArchivo.length + " bytes)");

        // Procesar solo staging (soloStaging = true)
        Long loteId = procesarArchivoSindoBajasInterno(
            tempFile.getAbsolutePath(),
            nombreArchivo,
            usuario,
            true  // SOLO staging, NO ejecutar bajas
        );

        return loteId;

    } catch (IOException e) {
        throw new IvroException("",
                "Error creando archivo temporal para carga a staging", e);
    } finally {
        if (tempFile != null && tempFile.exists()) {
            if (!tempFile.delete()) {
                LOGGER.warn("No se pudo eliminar archivo temporal: " + tempFile.getAbsolutePath());
            }
        }
    }
}


// ================================================================
// MÉTODO 3: PROCESAMIENTO INTERNO (núcleo)
// ================================================================

/**
 * Procesamiento interno del archivo SINDO.
 * Flujo completo: Crear lote → Cargar staging → Procesar líneas → Cerrar lote.
 *
 * @param rutaArchivo Ruta del archivo temporal
 * @param nombreArchivo Nombre original del archivo
 * @param usuario Usuario que ejecuta
 * @param soloStaging true = solo cargar a staging, false = procesar todo
 * @return ID del lote creado
 * @throws IvroException Si hay error
 */
 @TransactionAttribute(TransactionAttributeType.REQUIRED)
private Long procesarArchivoSindoBajasInterno(String rutaArchivo,
                                                String nombreArchivo,
                                                String usuario,
                                                boolean soloStaging) throws IvroException {

    // ========================================
    // FASE 1: Crear lote
    // ========================================
    LoteProcesamientoBaja lote = new LoteProcesamientoBaja();
    lote.setTpOrigen("ARCHIVO_SINDO");
    lote.setTxtNombreArchivo(nombreArchivo);
    lote.setFecInicio(new Date());
    lote.setTpEstado("EN_PROCESO");
    lote.setNumRegistrosTotal(0);
    lote.setNumExitosos(0);
    lote.setNumErrores(0);
    lote.setCveUsuarioCreacion(usuario);
    lote.setStpCreacion(new Date());
    lote.setStpActualizacion(new Date());

    entityManager.persist(lote);
    entityManager.flush();

    LOGGER.info("Lote creado: ID=" + lote.getCveIdLote() + ", archivo=" + nombreArchivo + ", modo=" + (soloStaging ? "SOLO_STAGING" : "COMPLETO"));

    // ========================================
    // FASE 2: Cargar archivo a staging
    // ========================================
    List<StgReingresoRO> lineasStaging = cargarArchivoAStaging(rutaArchivo, lote);

    lote.setNumRegistrosTotal(lineasStaging.size());
    entityManager.merge(lote);


    LOGGER.info("Lote " + lote.getCveIdLote() + ": " + lineasStaging.size() + " líneas cargadas a staging");

    // ========================================
    // FASE 3: Procesar líneas (si NO es solo staging)
    // ========================================
    if (!soloStaging) {
        for (StgReingresoRO stg : lineasStaging) {
            try {
                // Procesar cada línea en transacción independiente
                procesarLineaSindoConEstados(stg, lote, usuario);

            } catch (Exception ex) {
                LOGGER.error("Error procesando línea STG ID=" + stg.getCveIdStaging() + ": " + ex.getMessage());
                // Continuar con siguiente línea
            }
        }
    }

    // ========================================
    // FASE 4: Cerrar lote
    // ========================================
    // Recalcular contadores
    Long exitosos = (Long) entityManager.createQuery(
        "SELECT COUNT(s) FROM StgReingresoRO s " +
        "WHERE s.loteProcesamientoBaja.cveIdLote = :idLote " +
        "AND s.tpEstado = 'PROCESADO'"
    ).setParameter("idLote", lote.getCveIdLote())
     .getSingleResult();

    Long errores = (Long) entityManager.createQuery(
        "SELECT COUNT(s) FROM StgReingresoRO s " +
        "WHERE s.loteProcesamientoBaja.cveIdLote = :idLote " +
        "AND s.tpEstado = 'ERROR'"
    ).setParameter("idLote", lote.getCveIdLote())
     .getSingleResult();

    lote.setNumExitosos(exitosos.intValue());
    lote.setNumErrores(errores.intValue());
    lote.setTpEstado(soloStaging ? "STAGING_COMPLETO" : "PROCESADO");
    lote.setFecFin(new Date());
    lote.setStpActualizacion(new Date());
    LOGGER.info("*** procede a generar merge con objeto lote: {} ", lote);
    entityManager.merge(lote);


    LOGGER.info("Lote " + lote.getCveIdLote() + " finalizado: estado=" + lote.getTpEstado() + ", exitosos=" + exitosos + ", errores=" + errores);

    return lote.getCveIdLote();
}


// ================================================================
// MÉTODO 4: CARGAR ARCHIVO A STAGING
// ================================================================

/**
 * Lee archivo SINDO línea por línea y carga a STG_REINGRESO_RO.
 *
 * @param rutaArchivo Ruta del archivo
 * @param lote Lote de procesamiento
 * @return Lista de registros staging creados
 * @throws IvroException Si hay error leyendo archivo
 */
private List<StgReingresoRO> cargarArchivoAStaging(String rutaArchivo,
                                                     LoteProcesamientoBaja lote)
        throws IvroException {

    List<StgReingresoRO> lineasStaging = new ArrayList<StgReingresoRO>();
    BufferedReader reader = null;

    try {
reader = new BufferedReader(
    new InputStreamReader(
        new FileInputStream(rutaArchivo),
        "UTF-8"
    )
);

        String linea;
        int numLinea = 0;

        while ((linea = reader.readLine()) != null) {
            numLinea++;

            // Validar longitud de línea
            if (linea.length() < 174) {
                LOGGER.warn("Lote " + lote.getCveIdLote() + ": Línea " + numLinea + " tiene longitud inválida (" + linea.length() + "), se omite");
                continue;
            }

            // Crear registro staging
            StgReingresoRO stg = new StgReingresoRO();
            stg.setLoteProcesamientoBaja(lote);
            stg.setNumLineaArchivo(numLinea);
            stg.setTxtLineaOriginal(linea);
            stg.setTpEstado("PENDIENTE");
            stg.setNumIntentos(0);
            stg.setStpCarga(new Date());

            entityManager.persist(stg);
            lineasStaging.add(stg);

            // Flush cada 100 líneas
            if (numLinea % 100 == 0) {

                LOGGER.debug("Lote " + lote.getCveIdLote() + ": " + numLinea + " líneas cargadas a staging");
            }
        }



        LOGGER.info("Lote " + lote.getCveIdLote() + ": Total " + lineasStaging.size() + " líneas cargadas a staging");

        return lineasStaging;

    } catch (IOException e) {
        throw new IvroException("", "Error leyendo archivo SINDO: " + e.getMessage(), e);
    } finally {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                LOGGER.warn("Error cerrando BufferedReader", e);
            }
        }
    }
}


// ================================================================
// MÉTODO 5: PROCESAR LÍNEA SINDO CON ESTADOS
// ================================================================

/**
 * Procesa una línea SINDO en transacción independiente.
 * Flujo: Parsear → Validar → Buscar seguro → Ejecutar baja → Auditoría → Actualizar estado.
 *
 * @param stg Registro staging a procesar
 * @param lote Lote de procesamiento
 * @param usuario Usuario que ejecuta
 */
@TransactionAttribute(TransactionAttributeType.REQUIRED)
private void procesarLineaSindoConEstados(StgReingresoRO stg,
                                           LoteProcesamientoBaja lote,
                                           String usuario) {

    stg.setNumIntentos(stg.getNumIntentos() + 1);

    try {
        // ========================================
        // PASO 1: Parsear línea SINDO
        // ========================================
		SindoLineaDTO dto = sindoParser.parseLinea(stg.getTxtLineaOriginal());
        LOGGER.debug("STG " + stg.getCveIdStaging() + ": Parseado - NSS=" + dto.nss + ", fechaBaja=" + dto.fechaBajaModalidad40 + ", RP=" + dto.rpDestino);
 
        // ========================================
        // PASO 2: Validar datos mínimos
        // ========================================
        if (dto.nss == null || dto.nss.trim().isEmpty()) {
            throw new IvroException("", "NSS vacío en línea SINDO");
        }

        if (dto.fechaBajaModalidad40 == null) {
            throw new IvroException("", "Fecha de baja inválida en línea SINDO");
        }

        // ========================================
        // PASO 3: Buscar seguro activo por NSS
        // ========================================
        DitSeguroIvro seguroActivo = buscarSeguroActivoPorNss(dto.nss);

        /*if (seguroActivo == null) {
            throw new IvroException("",
                    "No se encontró seguro ACTIVO para NSS: " + dto.nss);
        }*/

        LOGGER.info("STG " + stg.getCveIdStaging() + ": Seguro activo encontrado ID=" + seguroActivo.getCveIdSeguroIvro() + " para NSS=" + dto.nss);

        // ========================================
        // PASO 4: Ejecutar baja del seguro o lanza excepcion
        // ========================================
        ejecutarBajaSeguro(seguroActivo.getCveIdSeguroIvro(), dto, usuario);

        // ========================================
        // PASO 5: Registrar auditoría
        // ========================================
        registrarAuditoriaBajaReingresoRO(seguroActivo.getCveIdSeguroIvro(), dto, stg, usuario);
 
        // ========================================
        // PASO 6: Marcar línea como PROCESADO
        // ========================================
        stg.setTpEstado("PROCESADO");

        stg.setTxtError("Baja ejecutada correctamente");

        entityManager.merge(stg);
        entityManager.flush();

        LOGGER.info("STG " + stg.getCveIdStaging() + ": Procesado exitosamente, seguro ID=" + seguroActivo.getCveIdSeguroIvro() + " dado de baja");

    } catch (Exception ex) {
        // ========================================
        // ERROR: Marcar línea como ERROR
        // ========================================
        stg.setTpEstado("ERROR");

        stg.setTxtError(
            "Error: " + ex.getMessage() +
            " (Intento " + stg.getNumIntentos() + ")"
        );

        entityManager.merge(stg);


        LOGGER.error("STG " + stg.getCveIdStaging() + ": Error en procesamiento - " + ex.getMessage());
    }
}


// ================================================================
// MÉTODO 6: BUSCAR SEGURO ACTIVO POR NSS
// ================================================================

/**
* Busca el seguro ACTIVO más reciente para un NSS.
* Compatible con Oracle 11g (usa ROWNUM en lugar de FETCH FIRST).
*
* @param nss NSS del asegurado (11 dígitos)
* @return DitSeguroIvro activo, o null si no existe
*/
private DitSeguroIvro buscarSeguroActivoPorNss(String nss) {
    try {
        Query query = entityManager.createNativeQuery(
            "SELECT * FROM ( " +
            "  SELECT s.* " +
            "  FROM MGPBDTU9X.DIT_SEGURO_IVRO s " +
            "  INNER JOIN MGPBDTU9X.DIT_LLAVE_ASEGURADO la " +
            "    ON s.CVE_ID_PERSONA = la.CVE_ID_PERSONA " +
            "  INNER JOIN MGPBDTU9X.DIT_ASIGNACION_NSS ans " +
            "    ON la.CVE_ID_ASIGNACION_NSS = ans.CVE_ID_ASIGNACION_NSS " +
            "  WHERE ans.NUM_NSS = :nss " +
            "  AND (s.CVE_ID_ESTADO_SEGURO = :estadoActivo OR s.CVE_ID_ESTADO_SEGURO = :estadoActivo2 )  " +
            "  AND ans.FEC_REGISTRO_BAJA IS NULL " +
            "  ORDER BY s.FEC_REGISTRO_ALTA DESC " +
            ") WHERE ROWNUM <= 1",
            DitSeguroIvro.class
        );

        query.setParameter("nss", nss);

        query.setParameter("estadoActivo", EstadoSeguroIvroEnum.ACTIVO.getId());
        query.setParameter("estadoActivo2", EstadoSeguroIvroEnum.NUEVO.getId());
        List<DitSeguroIvro> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);

    } catch (Exception ex) {
        LOGGER.error("Error buscando seguro activo para NSS=" + nss + ": " + ex.getMessage());
        return null;
    }
}


// ================================================================
// MÉTODO 7: EJECUTAR BAJA DEL SEGURO
// ================================================================

/**
 * Ejecuta la baja del seguro IVRO por reingreso a RO.
 * COPIA EXACTA del método existente bajaDeSeguroPorReingresoRO() (línea 1382).
 *
 * @param cveIdSeguroIvro ID del seguro IVRO a dar de baja
 * @param dto DTO con información de la línea SINDO
 * @param usuario Usuario que ejecuta la baja
 * @throws IvroException Si hay error en la baja
 */
private void ejecutarBajaSeguro(Long cveIdSeguroIvro,
                                 SindoLineaDTO dto,
                                 String usuario) throws IvroException {

    // ========================================
    // PASO 1: Crear estado BAJA_POR_REINGRESO_RO
    // (COPIADO de bajaDeSeguroPorReingresoRO línea 1384)
    // ========================================
    DicEstadoSeguro estado = IvroFactory.estadoSeguro(
        EstadoSeguroIvroEnum.BAJA_POR_REINGRESO_RO  // ID = 7
    );

    // ========================================
    // PASO 2: Obtener entidad JPA DitSeguroIvro
    // (COPIADO de bajaDeSeguroPorReingresoRO línea 1386)
    // ========================================
	LOGGER.info("Va a consultar toda la info");
    DitSeguroIvro seguro = entityManager.find(DitSeguroIvro.class, cveIdSeguroIvro);
    LOGGER.info("Obtuvo toda la info");
    if (seguro == null) { 
    	LOGGER.error("Seguro IVRO no encontrado");
        throw new IvroException("", "Seguro IVRO no encontrado: " + cveIdSeguroIvro);
    }

    LOGGER.debug("Ejecutando baja del seguro " + cveIdSeguroIvro + ", persona=" + seguro.getDitPersona().getCveIdPersona());

    // ========================================
    // PASO 3: Cambiar estado a BAJA
    // (COPIADO de bajaDeSeguroPorReingresoRO líneas 1387-1389)
    // ========================================
    seguro.setDicEstadoSeguro(estado);
    seguro.setFecRegistroActualizado(new Date());
    LOGGER.debug("{}", seguro);
    LOGGER.debug("{}", seguro.toString());
    LOGGER.debug("{}", seguro.getDicEstadoSeguro().toString());
    LOGGER.debug("{}", seguro.getDicModalidad().toString());
    LOGGER.debug("{}", seguro.getDitCompra().toString());
    LOGGER.debug("{}", seguro.getDitPersona().toString());
    LOGGER.debug("{}", seguro.getDitTramites().toString());
    LOGGER.debug("{}", seguro.getDitPersona().toString());
    entityManager.merge(seguro);


    LOGGER.info("Seguro " + cveIdSeguroIvro + " cambiado a estado BAJA_POR_REINGRESO_RO (ID=7)");

    // ========================================
    // PASO 4: Preparar objeto mínimo para correo
    // ========================================
	SeguroIvro seguroIvro = new SeguroIvro();
    seguroIvro.setCveIdSeguroIvro(cveIdSeguroIvro);

	    // ========================================
    // PASO 5: Enviar correo electrónico
    // ========================================
    try {
    	/*
        enviaCorreoLocal.enviaCorreo(
            seguroIvro,
            TipoOperacionNotificacionIVROEnum.BAJA_POR_REINGRESO.getCodigo()
        );*/
        
        enviaCorreoLocal.enviaCorreo(
                cveIdSeguroIvro,
                dto.fechaBajaModalidad40,
                null,
                TipoOperacionNotificacionIVROEnum.BAJA_POR_REINGRESO_V2.getCodigo()
            );

        LOGGER.info("Correo de baja enviado para seguro " + cveIdSeguroIvro);

    } catch (Exception ex) {
        // Si el correo falla, loggear pero NO fallar la baja
        LOGGER.error("Error enviando correo de baja para seguro " + cveIdSeguroIvro +
                     ": " + ex.getMessage() +
                     ". La baja se ejecutó correctamente en BD.");
    }

}


// ================================================================
// MÉTODO 8: REGISTRAR AUDITORÍA DE BAJA
// ================================================================

/**
 * Registra la auditoría completa de la baja por reingreso a RO.
 *
 * @param cveIdSeguroIvro ID del seguro dado de baja
 * @param dto DTO con información de la línea SINDO
 * @param stg Registro staging
 * @param usuario Usuario que ejecuta
 */
private void registrarAuditoriaBajaReingresoRO(Long cveIdSeguroIvro,
                                     SindoLineaDTO dto,
                                     StgReingresoRO stg,
                                     String usuario) {

    // ========================================
    // Crear registro en DIT_BAJA_SEGURO
    // ========================================
    // Obtener entidad completa DitSeguroIvro (requerido por FK)
    DitSeguroIvro seguro = entityManager.find(DitSeguroIvro.class, cveIdSeguroIvro);

    DitBajaSeguro baja = new DitBajaSeguro();
    // Se asigna como "PROCESADO" porque la baja ya paso, si algo mal pasa con la baja por reingreso RO, no llega a este punto
    baja.setCveNss(dto.nss);
    baja.setCveUsuarioCreacion(usuario);
    baja.setDitSeguroIvro(seguro);
    baja.setFecEfectivaBaja(dto.fechaBajaModalidad40);
    baja.setFecSolicitud(new Date());
    baja.setLoteProcesamientoBaja(stg.getLoteProcesamientoBaja());  // FK a lote
    baja.setStpCreacion(new Date());
    baja.setStpActualizacion(new Date());
    baja.setTpBaja(""+EstadoSeguroIvroEnum.BAJA_POR_REINGRESO_RO.getId() ); //EstadoSeguroIvroEnum.BAJA_A_SOLICITUD_ASEGURADO.getId()).equals(tipoBajaActual)
    baja.setTpEstado("PROCESADO");
    
    List<String> correos = obtieneListaCorreos(seguro );
    StringBuilder  sb= new StringBuilder("");
    for(int i=0; i<correos.size() ;i++) {
		
		if( i== (correos.size()-1) ) {
			LOGGER.info("es el último");
			sb.append(correos.get(i));
		}else
			sb.append(correos.get(i)).append(",");
	}
 
    baja.setTxtCorreos(sb.toString() );
    baja.setTxtCurp(seguro.getDitPersona().getCurp()); //Nuevo Campo
    baja.setTxtNombreCompleto(seguro.getDitPersona().getNomNombre()+" "+
    		seguro.getDitPersona().getNomPrimerApellido()+" "+
    		seguro.getDitPersona().getNomSegundoApellido());
    baja.setTxtObservaciones("Baja automática por reingreso al Régimen Obligatorio");                    // FK requiere entidad completa
    
    LOGGER.info("{}", baja);
    
    entityManager.persist(baja);
    entityManager.flush();

    LOGGER.debug("Auditoria de baja creada: ID=" + baja.getCveIdBaja() + ", seguro=" + cveIdSeguroIvro);
    stg.setCveIdBaja(baja.getCveIdBaja());
    
    LOGGER.info(dto.toString());
    // ========================================
    // Crear detalle del reingreso
    // ========================================
    DitBajaReingresoDetalle detalle = new DitBajaReingresoDetalle();
    detalle.setCveCiz(dto.ciz);
    detalle.setCveModalidadDestino(dto.modalidadDestino);
    detalle.setCveModalidadOrigen(dto.modalidadOrigen);
    detalle.setCvePatronDestino(dto.rpDestino);
    detalle.setCvePatronOrigen(dto.rpOrigen);
    detalle.setDitBajaSeguro(baja);
    detalle.setFecBajaModalidad40(dto.fechaBajaModalidad40);
    detalle.setFecMovimientoPatron(dto.fechaMovimientoPatron);
    detalle.setNumSalarioDiarioDestino(dto.salarioDiarioDestino);
    detalle.setNumSalarioDiarioOrigen(dto.salarioBaseModalidad40);
    detalle.setRpDestSub(dto.rpDestinoSubdelegacion);
    detalle.setRpOrigSub(dto.rpOrigenSubdelegacion);
    detalle.setStpCreacion(new Date());                  // Timestamp creaciÃ³n
    detalle.setTpMovimientoDestino(dto.tpMovimientoDestino); //no se tiene 
    detalle.setTpMovimientoOrigen(dto.claveMovimiento);  // Tipo movimiento origen
    detalle.setTxtNombrePatronDestino(dto.nombrePatronDestino);
    detalle.setTxtNombrePatronOrigen(dto.nombrePatronOrigen);
    
    

    entityManager.persist(detalle);
    entityManager.flush();

    LOGGER.debug("Detalle de reingreso creado: ID=" + detalle.getCveIdDetalle() + ", RP destino=" + dto.rpDestino);
}

/**
 * Obtiene una lista de correo de la usuario reingreso a RO.
 *
 * @param Seguro persona usuario ID del seguro dado de baja
 */
private List<String> obtieneListaCorreos(DitSeguroIvro seguro) {
	List<String> correos = new ArrayList<String>();
	if (seguro.getDitPersona().getDitPersonafContactos() != null) {
		for (DitPersonafContacto contactoPersona : seguro.getDitPersona().getDitPersonafContactos()) {

	        if (contactoPersona.getFecRegistroBaja() == null) {
	            DitFormaContacto formaContacto = contactoPersona.getDitFormaContacto();
	            if (formaContacto != null && formaContacto.getDitTipoContacto() != null) {
	                Long idTipoContacto = formaContacto.getDitTipoContacto().getCveIdTipoContacto();
	                if (idTipoContacto != null && idTipoContacto.equals(1L)) {
	
	                	correos.add(formaContacto.getDesFormaContacto()); 
	                   
	                }
	            }
	        }
		}
	}
	return correos;
}






// ================================================================
// MÉTODO 10: REPROCESAR LOTE
// ================================================================

/**
 * Reprocesa un lote completo o solo sus errores.
 *
 * @param idLote ID del lote a reprocesar
 * @param soloErrores true = solo errores, false = todo
 * @param usuario Usuario que ejecuta
 * @return Número de líneas reprocesadas
 * @throws IvroException Si hay error
 */
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public int reprocesarLote(Long idLote, boolean soloErrores, String usuario)
        throws IvroException {

    // Buscar lote
    LoteProcesamientoBaja lote = entityManager.find(LoteProcesamientoBaja.class, idLote);

    if (lote == null) {
        throw new IvroException("", "Lote no encontrado: " + idLote);
    }

    // Buscar líneas a reprocesar
    String jpql = "SELECT s FROM StgReingresoRO s " +
                  "WHERE s.loteProcesamientoBaja.cveIdLote = :idLote " +
                  (soloErrores ? "AND s.tpEstado = 'ERROR' " : "") +
                  "ORDER BY s.numLinea";

    List<StgReingresoRO> lineas = entityManager.createQuery(jpql, StgReingresoRO.class)
            .setParameter("idLote", idLote)
            .getResultList();

    LOGGER.info("Reprocesando lote " + idLote + ": " + lineas.size() + " líneas (soloErrores=" + soloErrores + ")");

    // Reprocesar cada línea
    for (StgReingresoRO stg : lineas) {
        try {
            // Marcar como PENDIENTE
            stg.setTpEstado("PENDIENTE");
            stg.setTxtError(null);
            entityManager.merge(stg);


            // Procesar
            procesarLineaSindoConEstados(stg, lote, usuario);

        } catch (Exception ex) {
            LOGGER.error("Error reprocesando STG " + stg.getCveIdStaging() + ": " + ex.getMessage());
        }
    }

    return lineas.size();
}


// ================================================================
// MÉTODO 11: PROCESAR POR RANGO DE LÍNEAS
// ================================================================

/**
 * Procesa un rango específico de líneas de un lote.
 * Útil para procesamiento por fases.
 *
 * @param idLote ID del lote
 * @param lineaInicio Línea inicial (inclusive)
 * @param lineaFin Línea final (inclusive)
 * @param usuario Usuario que ejecuta
 * @return Número de líneas procesadas
 * @throws IvroException Si hay error
 */
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public int procesarPorRangoLineas(Long idLote, int lineaInicio, int lineaFin, String usuario)
        throws IvroException {

    LoteProcesamientoBaja lote = entityManager.find(LoteProcesamientoBaja.class, idLote);

    if (lote == null) {
        throw new IvroException("", "Lote no encontrado: " + idLote);
    }

    List<StgReingresoRO> lineas = entityManager.createQuery(
        "SELECT s FROM StgReingresoRO s " +
        "WHERE s.loteProcesamientoBaja.cveIdLote = :idLote " +
        "AND s.numLinea >= :inicio " +
        "AND s.numLinea <= :fin " +
        "ORDER BY s.numLinea",
        StgReingresoRO.class
    )
    .setParameter("idLote", idLote)
    .setParameter("inicio", (long) lineaInicio)
    .setParameter("fin", (long) lineaFin)
    .getResultList();

    LOGGER.info("Procesando lote " + idLote + ": rango líneas " + lineaInicio + "-" + lineaFin + " (" + lineas.size() + " líneas)");

    for (StgReingresoRO stg : lineas) {
        try {
            procesarLineaSindoConEstados(stg, lote, usuario);
        } catch (Exception ex) {
            LOGGER.error("Error procesando línea " + stg.getNumLineaArchivo() + ": " + ex.getMessage());
        }
    }

    return lineas.size();
}


// ================================================================
// MÉTODO 12: OBTENER DETALLE REINGRESO PARA JSP (Requerimiento 6)
// ================================================================

/**
 * Obtiene detalle de baja por reingreso a RO para mostrar en JSP.
 *
 * @param idSeguroIvro ID del seguro IVRO
 * @return DTO con información del reingreso, o NULL si no aplica
 */

public DetalleReingresoRODTO obtenerDetalleReingresoParaJSP(Long idSeguroIvro) {

    if (idSeguroIvro == null) {
        return null;
    }

    String jpql = "SELECT d FROM DitBajaReingresoDetalle d " +
                  "INNER JOIN d.ditBajaSeguro b " +
                  "WHERE b.ditSeguroIvro.cveIdSeguroIvro = :idSeguroIvro " +
                  "AND b.tpBaja = " + EstadoSeguroIvroEnum.BAJA_POR_REINGRESO_RO.getId() +
                  " ORDER BY d.fecMovimientoPatron DESC";

    try {
        List<DitBajaReingresoDetalle> resultados = entityManager
                .createQuery(jpql, DitBajaReingresoDetalle.class)
                .setParameter("idSeguroIvro", idSeguroIvro)
                .setMaxResults(1)
                .getResultList();

        if (resultados.isEmpty()) {
            return null;
        }

        DitBajaReingresoDetalle detalle = resultados.get(0);

        // Construir DTO (NO exponer entidad JPA)
        DetalleReingresoRODTO dto = new DetalleReingresoRODTO();
        dto.setFechaBajaModalidad40(detalle.getFecBajaModalidad40());
        dto.setFechaReingresoRO(detalle.getFecMovimientoPatron());
        dto.setRegistroPatronal(detalle.getCvePatronDestino());
        dto.setNombrePatron(detalle.getTxtNombrePatronDestino());
        dto.setSalarioDiario(detalle.getNumSalarioDiarioDestino() != null ? detalle.getNumSalarioDiarioDestino().doubleValue() : null);

        LOGGER.debug("Detalle reingreso RO encontrado para seguro " + idSeguroIvro + ": RP=" + dto.getRegistroPatronal());

        return dto;

    } catch (Exception ex) {
        LOGGER.error("Error obteniendo detalle reingreso RO para seguro " + idSeguroIvro + ": " + ex.getMessage());
        return null;
    }
}

/**
* Ejecuta proceso automático de baja por mora (masivo, 3 fases en secuencia).
* Método de conveniencia que ejecuta el flujo completo para todos los seguros.
*
* FASE 1: Marca pagos POR_PAGAR como VENCIDOS (masivo)
* FASE 2: Da de baja seguros que califican (masivo)
* FASE 3: Completa bitácoras faltantes (masivo)
*
* @param usuarioOperador Usuario que ejecuta
* @return ID del último lote creado
*/
@Override
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public Long ejecutarProcesoMoraAutomatico(String usuarioOperador) {
 
	LOGGER.info("=================================================================");
	LOGGER.info("Iniciando proceso AUTOMÁTICO de baja por mora - Usuario: " + usuarioOperador);
	LOGGER.info("=================================================================");
 
	try {
		// =====================================================================
		// FASE 1: Marcar pagos vencidos (todos los seguros activos)
		// =====================================================================
		LOGGER.info("FASE 1: Marcando pagos como VENCIDOS...");
		Integer pagosMarcados = marcarPagosVencidosPorFecha(null, usuarioOperador);
		LOGGER.info("FASE 1 completada - Total pagos marcados como VENCIDOS: " + pagosMarcados);
 
		// =====================================================================
		// FASE 2: Dar de baja seguros que califican
		// =====================================================================
		LOGGER.info("FASE 2: Dando de baja seguros por mora...");
		Long loteIdBajas = darBajaPorMoraHastaFecha(null, usuarioOperador);
		LOGGER.info("FASE 2 completada - Lote de bajas: " + loteIdBajas);
 
		// =====================================================================
		// FASE 3: Completar bitácoras faltantes
		// =====================================================================
		LOGGER.info("FASE 3: Completando bitácoras...");
		Long loteIdBitacoras = completarBitacorasBajaMoraDesde(null, null, usuarioOperador);
		LOGGER.info("FASE 3 completada - Lote de bitácoras: " + loteIdBitacoras);
 
		LOGGER.info("=================================================================");
		LOGGER.info("Proceso AUTOMÁTICO de baja por mora completado exitosamente");
		LOGGER.info("=================================================================");
 
		return loteIdBitacoras;
 
	} catch (Exception e) {
		LOGGER.error("Error en proceso automático de baja por mora: " + e.getMessage(), e);
		throw new RuntimeException("Error en proceso automático de baja por mora", e);
	}
}

/**
* Ejecuta baja por mora para UN seguro (3 fases en secuencia).
* Método de conveniencia que ejecuta el flujo completo.
*
* FASE 1: Marca pagos POR_PAGAR como VENCIDOS
* FASE 2: Cambia estado del seguro a BAJA_POR_MORA
* FASE 3: Registra bitácora completa
*
* @param cveIdSeguroIvro ID del seguro
* @param cveIdLote ID del lote de procesamiento
* @param usuarioOperador Usuario que ejecuta
* @return true si se completó correctamente, false si falló
*/
@Override
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public boolean ejecutarBajaPorMora(Long cveIdSeguroIvro, Long cveIdLote, String usuarioOperador) {
 
	LOGGER.info("Iniciando baja por mora (3 fases) - Seguro: " + cveIdSeguroIvro + ", Lote: " + cveIdLote);
 
	try {
		// =====================================================================
		// FASE 1: Marcar pagos como VENCIDOS
		// =====================================================================
		Integer pagosMarcados = marcarPagosVencidosSeguro(cveIdSeguroIvro, null, usuarioOperador);
		LOGGER.info("FASE 1 completada - Pagos marcados como VENCIDOS: " + pagosMarcados);
 
		// =====================================================================
		// FASE 2: Dar de baja el seguro (cambiar estado)
		// =====================================================================
		Boolean seguroDadoDeBaja = darBajaPorMoraSeguro(cveIdSeguroIvro, usuarioOperador);
 
		if (!seguroDadoDeBaja) {
			LOGGER.warn("FASE 2 falló - Seguro " + cveIdSeguroIvro + " no califica para baja por mora");
			return false;
		}
 
		LOGGER.info("FASE 2 completada - Seguro dado de baja por MORA");
 
		// =====================================================================
		// FASE 3: Registrar bitácora completa
		// =====================================================================
		Long idBaja = registrarBitacoraBajaMora(cveIdSeguroIvro, cveIdLote, usuarioOperador);
 
		if (idBaja == null) {
			LOGGER.error("FASE 3 falló - No se pudo registrar bitácora para seguro " + cveIdSeguroIvro);
			return false;
		}
 
		LOGGER.info("FASE 3 completada - Bitácora registrada con ID: " + idBaja);
 
		// =====================================================================
		// FASE 4: Enviar correo de notificación
		// =====================================================================
		try {
			// Buscar la baja registrada para obtener fecha efectiva
			DitBajaSeguro baja = entityManager.find(DitBajaSeguro.class, idBaja);
 
			if (baja != null) {
				enviaCorreoLocal.enviaCorreo(
					cveIdSeguroIvro,
					baja.getFecEfectivaBaja(),
					null,
					TipoOperacionNotificacionIVROEnum.BAJA_POR_MORA_V2.getCodigo()
				);
 
				LOGGER.info("Correo de baja mora enviado para seguro " + cveIdSeguroIvro);
			}
 
		} catch (Exception ex) {
			LOGGER.error("Error enviando correo de baja mora para seguro " + cveIdSeguroIvro +
			            ": " + ex.getMessage() + ". La baja se ejecutó correctamente en BD.");
		}
 
		LOGGER.info("Baja por mora completada exitosamente para seguro: " + cveIdSeguroIvro);
		return true;
 
	} catch (Exception e) {
		LOGGER.error("Error ejecutando baja por mora para seguro " + cveIdSeguroIvro +
		            ": " + e.getMessage(), e);
		return false;
	}
}

private DetalleMoraInfo calcularDetalleMora(DitSeguroIvro seguro) {
	return calcularDetalleMora(seguro, true);
}

/**
* Marca pagos POR_PAGAR como VENCIDOS para un seguro.
* Método privado usado internamente.
*
* @param seguro Seguro a procesar
* @param fechaCorte Fecha límite para marcar pagos
* @return Cantidad de pagos marcados
*/
private int marcarPagosComoVencidos(DitSeguroIvro seguro, Date fechaCorte) {
 
    if (seguro == null || seguro.getDitCompra() == null) {
        return 0;
    }
 
    DitCompra ditCompra = seguro.getDitCompra();
    List ditPagos = ditCompra.getDitPagos();
 
    if (ditPagos == null || ditPagos.isEmpty()) {
        return 0;
    }
 
    // Filtrar pagos que deben marcarse como vencidos
    List<DitPago> pagosAMarcar = BajaMoraUtil.filtrarPagosVencidos(ditPagos, fechaCorte);
 
    if (pagosAMarcar.isEmpty()) {
        return 0;
    }
 
    // Cambiar estado a VENCIDO
    DicEstadoPago estadoVencido = new DicEstadoPago();
    estadoVencido.setCveIdEstadoPago(EstadoPagoEnum.VENCIDO.getId());
 
    int marcados = 0;
    for (DitPago pago : pagosAMarcar) {
        pago.setDicEstadoPago(estadoVencido);
        pago.setFecRegistroActualizado(new Date());
        marcados++;
    }
 
    LOGGER.info("Seguro " + seguro.getCveIdSeguroIvro() + ": " + marcados + " pagos marcados como VENCIDOS");
 
    return marcados;
}

/**
* Cambia el estado del seguro a BAJA_POR_MORA.
* Método privado usado internamente.
*
* @param seguro Seguro a dar de baja
*/
private void cambiarEstadoABajaPorMora(DitSeguroIvro seguro) {
 
    if (seguro == null) {
        return;
    }
 
    // Cambiar estado del seguro
    DicEstadoSeguro estado = IvroFactory.estadoSeguro(EstadoSeguroIvroEnum.BAJA_POR_MORA);
    seguro.setDicEstadoSeguro(estado);
    seguro.setFecRegistroActualizado(new Date());
 
    // Cambiar estado de la compra
    if (seguro.getDitCompra() != null) {
        DicEstadoCompra estadoCompra = new DicEstadoCompra();
        estadoCompra.setCveIdEstadoCompra(EstadoCompraEnum.VENCIDO.getId());
        seguro.getDitCompra().setDicEstadoCompra(estadoCompra);
        seguro.getDitCompra().setFecRegistroActualizado(new Date());
    }
 
    entityManager.merge(seguro);
 
    LOGGER.info("Seguro " + seguro.getCveIdSeguroIvro() + " dado de baja por MORA");
}

/**
* Marca pagos POR_PAGAR como VENCIDOS hasta cierta fecha (proceso masivo).
*
* @param fechaCorte Fecha límite (null = día 17 mes actual)
* @param usuarioOperador Usuario que ejecuta
* @return Cantidad de pagos marcados
*/
@Override
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public Integer marcarPagosVencidosPorFecha(Date fechaCorte, String usuarioOperador) {
 
    LOGGER.info("Iniciando marcado masivo de pagos vencidos - Usuario: " + usuarioOperador);
 
    // Obtener fecha de corte
    Date fecha = BajaMoraUtil.obtenerFechaCorte17DelMes(fechaCorte);
    LOGGER.info("Fecha de corte: " + fecha);
 
    // Buscar seguros ACTIVOS
    String jpql = "SELECT s FROM DitSeguroIvro s WHERE s.dicEstadoSeguro.cveIdEstadoSeguro = 2";
    List<DitSeguroIvro> segurosActivos = entityManager.createQuery(jpql).getResultList();
 
    LOGGER.info("Seguros activos encontrados: " + segurosActivos.size());
 
    int totalPagosMarcados = 0;
 
    for (DitSeguroIvro seguro : segurosActivos) {
        try {
            int marcados = marcarPagosComoVencidos(seguro, fecha);
            totalPagosMarcados += marcados;
        } catch (Exception ex) {
            LOGGER.error("Error marcando pagos de seguro " + seguro.getCveIdSeguroIvro() + ": " + ex.getMessage());
        }
    }
    return totalPagosMarcados;
}

/**
* Marca pagos POR_PAGAR como VENCIDOS para UN seguro específico.
*
* @param cveIdSeguroIvro ID del seguro
* @param fechaCorte Fecha límite (null = día 17 mes actual)
* @param usuarioOperador Usuario que ejecuta
* @return Cantidad de pagos marcados
*/
@Override
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public Integer marcarPagosVencidosSeguro(Long cveIdSeguroIvro, Date fechaCorte, String usuarioOperador) {
 
    LOGGER.info("Marcando pagos vencidos - Seguro: " + cveIdSeguroIvro);
 
    DitSeguroIvro seguro = entityManager.find(DitSeguroIvro.class, cveIdSeguroIvro);
    if (seguro == null) {
        LOGGER.error("Seguro no encontrado: " + cveIdSeguroIvro);
        return 0;
    }
 
    Date fecha = BajaMoraUtil.obtenerFechaCorte17DelMes(fechaCorte);
    int marcados = marcarPagosComoVencidos(seguro, fecha);
 
    LOGGER.info("Pagos marcados: " + marcados);
 
    return marcados;
}

/**
* Da de baja seguros con 2+ pagos VENCIDOS consecutivos (proceso masivo).
* NO crea bitácora, solo cambia estados.
*
* @param fechaCorte Fecha límite (null = procesa todos)
* @param usuarioOperador Usuario que ejecuta
* @return ID del lote creado
*/
@Override
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public Long darBajaPorMoraHastaFecha(Date fechaCorte, String usuarioOperador) {
 
    LOGGER.info("Iniciando proceso de baja por mora - Usuario: " + usuarioOperador);
 
    // Crear lote
    LoteProcesamientoBaja lote = new LoteProcesamientoBaja();
    lote.setTpOrigen("MARCAR_BAJA_MORA");
    lote.setFecInicio(new Date());
    lote.setTpEstado("PROCESANDO");
    lote.setNumRegistrosTotal(0);
    lote.setNumExitosos(0);
    lote.setNumErrores(0);
    lote.setCveUsuarioCreacion(usuarioOperador);
    lote.setStpCreacion(new Date());
    lote.setStpActualizacion(new Date());
 
    entityManager.persist(lote);
    entityManager.flush();
 
    Long cveIdLote = lote.getCveIdLote();
    LOGGER.info("Lote creado: " + cveIdLote);
 
    // Buscar seguros ACTIVOS
    String jpql = "SELECT s FROM DitSeguroIvro s WHERE s.dicEstadoSeguro.cveIdEstadoSeguro = 2";
    List<DitSeguroIvro> segurosActivos = entityManager.createQuery(jpql).getResultList();
 
    LOGGER.info("Seguros activos encontrados: " + segurosActivos.size());
 
    int totalExitosos = 0;
    int totalErrores = 0;
 
    for (DitSeguroIvro seguro : segurosActivos) {
        try {
            // Obtener pagos VENCIDOS
            List<DitPago> pagosVencidos = BajaMoraUtil.obtenerPagosYaVencidos(
                seguro.getDitCompra().getDitPagos(),
                fechaCorte
            );
 
            // Validar si califica (2+ consecutivos)
            if (ValidacionMoraUtil.calificaParaBajaMora(pagosVencidos)) {
                cambiarEstadoABajaPorMora(seguro);
                totalExitosos++;
            }
 
        } catch (Exception ex) {
            totalErrores++;
            LOGGER.error("Error procesando seguro " + seguro.getCveIdSeguroIvro() + ": " + ex.getMessage());
        }
    }
 
    // Cerrar lote
    lote.setTpEstado("COMPLETADO");
    lote.setFecFin(new Date());
    lote.setNumRegistrosTotal(segurosActivos.size());
    lote.setNumExitosos(totalExitosos);
    lote.setNumErrores(totalErrores);
    lote.setStpActualizacion(new Date());
 
    entityManager.merge(lote);
 
    LOGGER.info("Proceso completado - Exitosos: " + totalExitosos + ", Errores: " + totalErrores);
 
    return cveIdLote;
}

/**
* Da de baja UN seguro específico por mora (si califica).
* NO crea bitácora, solo cambia estado.
*
* @param cveIdSeguroIvro ID del seguro
* @param usuarioOperador Usuario que ejecuta
* @return true si se dio de baja, false si no califica
*/
@Override
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public Boolean darBajaPorMoraSeguro(Long cveIdSeguroIvro, String usuarioOperador) {
 
    LOGGER.info("Dando de baja seguro por mora - ID: " + cveIdSeguroIvro);
 
    DitSeguroIvro seguro = entityManager.find(DitSeguroIvro.class, cveIdSeguroIvro);
    if (seguro == null) {
        LOGGER.error("Seguro no encontrado: " + cveIdSeguroIvro);
        return false;
    }
 
    // Obtener pagos VENCIDOS
    List<DitPago> pagosVencidos = BajaMoraUtil.obtenerPagosYaVencidos(
        seguro.getDitCompra().getDitPagos(),
        null
    );
 
    // Validar si califica
    if (!ValidacionMoraUtil.calificaParaBajaMora(pagosVencidos)) {
        LOGGER.warn("Seguro " + cveIdSeguroIvro + " no califica para baja por mora");
        return false;
    }
 
    // Dar de baja
    cambiarEstadoABajaPorMora(seguro);
 
    LOGGER.info("Seguro " + cveIdSeguroIvro + " dado de baja por mora");
 
    return true;
}

/**
* Completa bitácoras faltantes de seguros en estado BAJA_POR_MORA.
* Crea LOTE + DIT_BAJA_SEGURO + DIT_BAJA_MORA_DETALLE.
*
* @param fechaDesde Solo seguros dados de baja >= fecha (null = todos)
* @param dummy Parámetro no usado (compatibilidad)
* @param usuarioOperador Usuario que ejecuta
* @return ID del lote creado
*/
@Override
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public Long completarBitacorasBajaMoraDesde(Date fechaDesde, Date dummy, String usuarioOperador) {
 
    LOGGER.info("Completando bitácoras mora faltantes - Usuario: " + usuarioOperador);
 
    // Crear lote
    LoteProcesamientoBaja lote = self.guardarLoteParaBitacorasDeBajaMora(usuarioOperador);
 
    Long cveIdLote = lote.getCveIdLote();
    LOGGER.info("Lote creado: " + cveIdLote);
 
    // Buscar seguros en BAJA_POR_MORA sin bitácora
    String jpql = "SELECT s FROM DitSeguroIvro s " +
                  "WHERE s.dicEstadoSeguro.cveIdEstadoSeguro = 6 " + // BAJA_POR_MORA
                  "AND NOT EXISTS (" +
                  "  SELECT b FROM DitBajaSeguro b " +
                  "  WHERE b.ditSeguroIvro.cveIdSeguroIvro = s.cveIdSeguroIvro " +
                  "    AND b.tpBaja = 'MORA'" +
                  ")";
 
    if (fechaDesde != null) {
        jpql += " AND s.fecRegistroActualizado >= :fechaDesde";
    }
 
    javax.persistence.Query query = entityManager.createQuery(jpql);
    if (fechaDesde != null) {
        query.setParameter("fechaDesde", fechaDesde);
    }
 
    List<DitSeguroIvro> seguros = query.getResultList();
 
    LOGGER.info("Seguros pendientes de bitácora: " + seguros.size());
 
    ArrayList<Long> bajasExitosas = new ArrayList<Long>();
    int totalErrores = 0;

    for (DitSeguroIvro seguro : seguros) {
        try {
            Long idBaja = self.registrarBitacoraBajaMora(seguro.getCveIdSeguroIvro(), cveIdLote, usuarioOperador);
            if (idBaja != null) {
                bajasExitosas.add(idBaja);
            } else {
                totalErrores++;
            }
        } catch (Exception ex) {
            totalErrores++;
            LOGGER.error("Error completando bitácora seguro " + seguro.getCveIdSeguroIvro() + ": " + ex.getMessage());
        }
    }
    
    // Cerrar lote
    self.cerrarLoteDeBitacorasDeBajaMora(lote, seguros.size(), bajasExitosas.size(), totalErrores);
    
    self.enviarCorreoConValidacion(bajasExitosas);
 
    LOGGER.info("BitÃ¡coras completadas - Exitosos: " + bajasExitosas.size() + ", Errores: " + totalErrores);
 
    return cveIdLote;
}


/**
 * Crear lote de bitacoras de baja por mora
 *
 * @param usuarioOperador Usuario que ejecuta
 * @return Lote guardado
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public LoteProcesamientoBaja guardarLoteParaBitacorasDeBajaMora(String usuarioOperador) {
	LoteProcesamientoBaja lote = new LoteProcesamientoBaja();
	lote.setTpOrigen("COMPLETAR_BITACORA_MORA");
	lote.setFecInicio(new Date());
	lote.setTpEstado("PROCESANDO");
	lote.setNumRegistrosTotal(0);
	lote.setNumExitosos(0);
	lote.setNumErrores(0);
	lote.setCveUsuarioCreacion(usuarioOperador);
	lote.setStpCreacion(new Date());
	lote.setStpActualizacion(new Date());
	
	entityManager.persist(lote);
	entityManager.flush();
	
	return lote;
}


/**
 * Cerrar lote de bitacoras de baja por mora, para registro de resultado.
 *
 * @param lote: Lote a actualizar
 * @param lote: registrosTotal a bajas totales, ya sean exitosas o con error
 * @param totalExitosos, total de bitacoras exitosamente realizadas
 * @param totalErrores, total de bitacoras que hubo un error
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public void cerrarLoteDeBitacorasDeBajaMora(LoteProcesamientoBaja lote, int numRegistrosTotal, int totalExitosos, int totalErrores) {
	lote.setTpEstado("COMPLETADO");
    lote.setFecFin(new Date());
    lote.setNumRegistrosTotal(numRegistrosTotal);
    lote.setNumExitosos(totalExitosos);
    lote.setNumErrores(totalErrores);
    lote.setStpActualizacion(new Date());
    entityManager.merge(lote);
}


/**
 * Antes de enviar el correo hace una consulta, si es que las bitacora de baja
 * Se guardo exitosamente.
 *
 * @param idBajasExitosas: Ids de DIT_BAJA_SEGURO.CVE_ID_BAJA que en pasos anteriores
 * resultaron como exitosas, pero por separacion de resposabilidades y transaccion,
 * se pone por separado.
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public void enviarCorreoConValidacion(ArrayList<Long> idBajasExitosas) {
	for (Long idBitacoraBaja : idBajasExitosas) {
		try {
		    DitBajaSeguro baja = entityManager.find(DitBajaSeguro.class, idBitacoraBaja);
		    DitSeguroIvro seguro = baja.getDitSeguroIvro();
		    if (baja != null) {
		        enviaCorreoLocal.enviaCorreo(
		            seguro.getCveIdSeguroIvro(),
		            baja.getFecEfectivaBaja(),
		            null,
		            TipoOperacionNotificacionIVROEnum.BAJA_POR_MORA_V2.getCodigo()
		        );
		        LOGGER.info("Correo de baja mora enviado para seguro " + seguro.getCveIdSeguroIvro());
		    }
		  } catch (Exception exCorreo) {
		      LOGGER.error("Error enviando correo para la bitacora DIT_BAJA_SEGURO.CVE_ID_BAJA: " + idBitacoraBaja +
		                  ": " + exCorreo.getMessage() + ". La baja se ejecutÃ³ correctamente.");
		  }	
	}
}



/**
 * @param seguro Seguro a analizar
 * @param validarSeguroActivo Si true, valida que el seguro esté en estado ACTIVO o NUEVO
 * @return DetalleMoraInfo con datos calculados o null si no califica
 */
private DetalleMoraInfo calcularDetalleMora(DitSeguroIvro seguro, boolean validarSeguroActivo) {

	DitCompra compra = seguro.getDitCompra();
	if (compra == null || compra.getDitPagos() == null) {
		LOGGER.warn("Seguro " + seguro.getCveIdSeguroIvro() + " no tiene compra o pagos asociados");
		return null;
	}

	// Validar modalidad 40
	if (seguro.getDicModalidad().getCveIdModalidad() != ModalidadEnum.CUARENTA.getId()) {
		LOGGER.warn("Seguro " + seguro.getCveIdSeguroIvro() + " no es modalidad 40");
		return null;
	}

	// Validar estado NUEVO o ACTIVO (solo si se requiere)
	if (validarSeguroActivo) {
		long estado = seguro.getDicEstadoSeguro().getCveIdEstadoSeguro();
		if (estado != EstadoSeguroIvroEnum.NUEVO.getId() &&
		    estado != EstadoSeguroIvroEnum.ACTIVO.getId()) {
			LOGGER.warn("Seguro " + seguro.getCveIdSeguroIvro() + " no está en estado ACTIVO/NUEVO");
			return null;
		}
	}

	List<DitPago> pagosVencidos = BajaMoraUtil.obtenerPagosYaVencidos(
		compra.getDitPagos(),
		null  // null = obtener TODOS los pagos ya marcados como VENCIDOS
	);

	// Validar que existan pagos vencidos
	if (pagosVencidos == null || pagosVencidos.isEmpty()) {
		LOGGER.warn("Seguro " + seguro.getCveIdSeguroIvro() + " no tiene pagos VENCIDOS");
		return null;
	}

	LOGGER.info("Seguro " + seguro.getCveIdSeguroIvro() + " tiene " +
	           pagosVencidos.size() + " pagos VENCIDOS");

	DetalleMoraInfo info = new DetalleMoraInfo();
	BigDecimal montoTotal = BigDecimal.ZERO;
	
	try {
		// Obtener compra completa con montos del motor de c�lculo
		Compra compraCompleta = compraServiceRemote.findCompraById(compra.getCveIdCompra());

		if (compraCompleta != null && compraCompleta.getPagos() != null) {
			// SUMAR montos reales de cada pago vencido
			for (int i = 0; i < pagosVencidos.size(); i++) {
				DitPago ditPago = pagosVencidos.get(i);

				// Buscar el pago en la compra completa (que tiene montos del motor)
				for (Pago pago : compraCompleta.getPagos()) {
					boolean mismoPago = pago.getIdPago() != null && pago.getIdPago().equals(ditPago.getCveIdPago());
					if (mismoPago) {
						PagoVencido pv = new PagoVencido();
						pv.setIdPago(pago.getIdPago());
						pv.setLineaCaptura(pago.getLineaCaptura());
						pv.setFecIniPeriodo(pago.getFechaInicioPeriodo());
						pv.setFecFinPeriodo(pago.getFechaFinPeriodo());
						boolean siMontoEsPositivo = pago.getMonto() != null && pago.getMonto().compareTo(BigDecimal.ZERO) > 0;
						if (siMontoEsPositivo) {
							montoTotal = montoTotal.add(pago.getMonto());
							pv.setMonto(pago.getMonto());
							LOGGER.debug("Pago ID " + ditPago.getCveIdPago() + " - Monto: " + pago.getMonto());
						}
						info.getPagosVencidos().add(pv);
						break;
					}
				}
			}

			LOGGER.info("Seguro " + seguro.getCveIdSeguroIvro() +
			           " - Monto total adeudado: " + montoTotal +
			           " (suma de " + pagosVencidos.size() + " pagos vencidos)");
		} else {
			LOGGER.warn("No se pudo obtener compra completa, monto quedará en 0");
		}

	} catch (Exception e) {
		LOGGER.error("Error calculando monto de mora para seguro " +
		            seguro.getCveIdSeguroIvro() + ": " + e.getMessage(), e);
		// Continuar aunque falle el cálculo de monto (se guardará en 0)
	}

	// =========================================================================
	// Calcular recargos y actualizaciones (por implementar si se requiere)
	// =========================================================================
	BigDecimal recargos = BigDecimal.ZERO;
	BigDecimal actualizaciones = BigDecimal.ZERO;

	// =========================================================================
	// Obtener fechas relevantes
	// =========================================================================
	DitPago primerPago = pagosVencidos.get(0);
	DitPago ultimoPago = pagosVencidos.get(pagosVencidos.size() - 1);
	Date fecPrimerVencimiento = primerPago.getFecLimitePago();
	Date fecUltimoVencimiento = ultimoPago.getFecLimitePago();

	// Buscar último pago recibido (estado PAGADO)
	Date fecUltimoPago = null;
	List ditPagos = compra.getDitPagos();
	for (int i = 0; i < ditPagos.size(); i++) {
		DitPago pago = (DitPago) ditPagos.get(i);
		if (pago.getDicEstadoPago().getCveIdEstadoPago() == EstadoPagoEnum.PAGADO.getId()) {
			if (pago.getFecPago() != null) {
				if (fecUltimoPago == null || pago.getFecPago().after(fecUltimoPago)) {
					fecUltimoPago = pago.getFecPago();
				}
			}
		}
	}

	String periodosCsv = BajaMoraUtil.construirPeriodosCsv(pagosVencidos);

	// =========================================================================
	// Construir DTO con resultados
	// =========================================================================
	
	info.setMesesMora(pagosVencidos.size());
	info.setMontoTotal(montoTotal);
	info.setFecUltimoPago(fecUltimoPago);
	info.setFecPrimerVencimiento(fecPrimerVencimiento);
	info.setFecUltimoVencimiento(fecUltimoVencimiento);
	info.setPeriodosCsv(periodosCsv);
	info.setRecargos(recargos);
	info.setActualizaciones(actualizaciones);

	LOGGER.info("Detalle mora calculado para seguro " + seguro.getCveIdSeguroIvro() +
	           " - Meses: " + info.getMesesMora() +
	           ", Monto: " + info.getMontoTotal() +
	           ", Períodos: " + info.getPeriodosCsv());

	return info;
}


/**
 * Obtiene detalle de baja expresa para mostrar en JSP.
 */
@Override
public mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.DetalleBajaExpresaDTO obtenerDetalleBajaExpresaParaJSP(
		Long idSeguroIvro) {

	if (idSeguroIvro == null) {
		return null;
	}

	  String jpql = "SELECT e FROM DitBajaExpresaDetalle e " + "INNER JOIN e.ditBajaSeguro b "
		  		+ "WHERE b.ditSeguroIvro.cveIdSeguroIvro = :idSeguroIvro " + "AND b.tpBaja = '9' "
		  		+ "ORDER BY b.fecEfectivaBaja DESC";

	try {
		List resultados = entityManager.createQuery(jpql).setParameter("idSeguroIvro", idSeguroIvro).setMaxResults(1)
				.getResultList();

		if (resultados.isEmpty()) {
			return null;
		}

		DitBajaExpresaDetalle detalle = (DitBajaExpresaDetalle) resultados.get(0);
		DitBajaSeguro baja = detalle.getDitBajaSeguro();

		// Construir DTO
		mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.DetalleBajaExpresaDTO dto = new mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.dto.DetalleBajaExpresaDTO();
		dto.setFechaBajaExpresa(baja != null ? baja.getFecEfectivaBaja() : null);
		
		LOGGER.debug("Detalle mora encontrado para seguro " + idSeguroIvro + ": Baja Expresa");

		return dto;

	} catch (Exception ex) {
		LOGGER.error("Error obteniendo detalle baja expresa para seguro " + idSeguroIvro + ": " + ex.getMessage());
		return null;
	}
}


/**
 * Obtiene detalle de baja por mora para mostrar en JSP.
 */
@Override
public DetalleMoraDTO obtenerDetalleMoraParaJSP(
		Long idSeguroIvro) {

	if (idSeguroIvro == null) {
		return null;
	}

	 String jpql = "SELECT m FROM DitBajaMoraDetalle m "
			 	+ "INNER JOIN m.ditBajaSeguro b "
		  		+ "WHERE b.ditSeguroIvro.cveIdSeguroIvro = :idSeguroIvro " + "AND b.tpBaja = 'MORA' "
		  		+ "ORDER BY b.fecEfectivaBaja DESC";

	try {
		List resultados = entityManager.createQuery(jpql).setParameter("idSeguroIvro", idSeguroIvro).setMaxResults(1)
				.getResultList();

		if (resultados.isEmpty()) {
			return null;
		}

		DitBajaMoraDetalle detalle = (DitBajaMoraDetalle) resultados.get(0);
		DitBajaSeguro baja = detalle.getDitBajaSeguro();

		// Construir DTO
		DetalleMoraDTO dto = new DetalleMoraDTO();
		dto.setFechaEfectivaBaja(baja != null ? baja.getFecEfectivaBaja() : null);
		dto.setFechaUltimoPago(detalle.getFecUltimoPago());
		
		// Obtener pagos vencidos de bitacora
		try {
			Long cveIdDetalle = detalle.getCveIdDetalle();
			List<DitBajaMoraPagoVencido> pagosVencidosBitacora = getPagosVencidosBitacora(cveIdDetalle);
			dto = agregarDatosDePagosVencidosADTO(dto, pagosVencidosBitacora);
		} catch (Exception ex) {
			LOGGER.error("Error detalles de pagos vencidos de DitBajaMoraPagoVencido, para el seguro: " + idSeguroIvro);
			LOGGER.error(ex.getMessage());
			return null;
		} // FIN Obtener pagos vencidos de bitacora
		
		LOGGER.debug("Detalle mora encontrado para seguro " + idSeguroIvro + ": " + dto.getMesesMora() + " meses");
		return dto;

	} catch (Exception ex) {
		LOGGER.error("Error obteniendo detalle mora para seguro " + idSeguroIvro + ": " + ex.getMessage());
		return null;
	}
}

private DetalleMoraDTO agregarDatosDePagosVencidosADTO(DetalleMoraDTO dto, List<DitBajaMoraPagoVencido> pagosVencidosBitacora) {
	String periodosVencidos = "";
	String montosAdeudados = "";
	SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/yyyy");
	NumberFormat numberFormater = NumberFormat.getInstance(Locale.US);
	for(int i =0; i < pagosVencidosBitacora.size(); i++) {
		DitBajaMoraPagoVencido pagoVencido = (DitBajaMoraPagoVencido) pagosVencidosBitacora.get(i);
		String formattedDate = dateFormatter.format(pagoVencido.getFecInicioPeriodo());
		BigDecimal montoRounded = pagoVencido.getNumMonto().setScale(2, RoundingMode.HALF_UP);
		String montoAMostrar = numberFormater.format(montoRounded);
		boolean noEsUltimoItem = (i+1) < pagosVencidosBitacora.size();
		boolean esPrimerItem = i == 0;
		
		if(esPrimerItem) {
			dto.setFechaPrimerVencimiento(pagoVencido.getFecInicioPeriodo());
		}
		if(noEsUltimoItem) {
			periodosVencidos = periodosVencidos + formattedDate + ", ";
			montosAdeudados = montosAdeudados + montoAMostrar + ", "; 
		} else { // es ultimo item
			periodosVencidos = periodosVencidos + formattedDate;
			montosAdeudados = montosAdeudados + montoAMostrar;
			dto.setFechaUltimoVencimiento(pagoVencido.getFecFinPeriodo());
		}
	}
	dto.setPeriodosVencidos(periodosVencidos);
	dto.setMesesMora(pagosVencidosBitacora.size());
	dto.setMontosAdeudos(montosAdeudados);
	
	/* NO ENTIENDO ESTOS DATOS
	dto.setRecargos(detalle.getNumRecargos());
	dto.setActualizaciones(detalle.getNumActualizaciones());
	dto.setRegistroPatronal(detalle.getCveRegistroPatronal());
	*/
	return dto;
}

private List<DitBajaMoraPagoVencido> getPagosVencidosBitacora(Long cveIdDetalle) throws Exception {
	List<DitBajaMoraPagoVencido> pagosVencidos =
		    entityManager.createQuery(
		        "SELECT p FROM DitBajaMoraPagoVencido p WHERE p.cveIdDetalle = :cveIdDetalle",
		        DitBajaMoraPagoVencido.class
		    )
		    .setParameter("cveIdDetalle", cveIdDetalle)
		    .getResultList();
	return pagosVencidos;
}

/**
 * Actualiza solo el estado de DIT_SEGURO_IVRO a BAJA_POR_MORA. No crea bitácora
 * de auditoría.
 */
@Override
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public boolean actualizarEstadoSeguroMora(Long cveIdSeguroIvro) {

	LOGGER.info("Actualizando estado a BAJA_POR_MORA para seguro: " + cveIdSeguroIvro);

	try {
		DitSeguroIvro seguro = entityManager.find(DitSeguroIvro.class, cveIdSeguroIvro);
		if (seguro == null) {
			LOGGER.error("Seguro no encontrado: " + cveIdSeguroIvro);
			return false;
		}

		// Actualizar DIT_SEGURO_IVRO
		DicEstadoSeguro estado = IvroFactory.estadoSeguro(EstadoSeguroIvroEnum.BAJA_POR_MORA);
		seguro.setDicEstadoSeguro(estado);
		seguro.setFecRegistroActualizado(new Date());

		// Actualizar DIT_COMPRA
		DitCompra ditCompra = seguro.getDitCompra();
		if (ditCompra != null) {
			DicEstadoCompra dicEstadoCompra = new DicEstadoCompra();
			dicEstadoCompra.setCveIdEstadoCompra(EstadoCompraEnum.VENCIDO.getId());
			ditCompra.setDicEstadoCompra(dicEstadoCompra);
			ditCompra.setFecRegistroActualizado(new Date());

			// Actualizar DIT_PAGO
			List ditPagos = ditCompra.getDitPagos();
			if (ditPagos != null) {
				DicEstadoPago dicEstadoPago = new DicEstadoPago();
				dicEstadoPago.setCveIdEstadoPago(EstadoPagoEnum.VENCIDO.getId());

				for (int i = 0; i < ditPagos.size(); i++) {
					DitPago ditPago = (DitPago) ditPagos.get(i);
					if (ditPago.getDicEstadoPago().getCveIdEstadoPago() == EstadoPagoEnum.POR_PAGAR.getId()) {
						ditPago.setDicEstadoPago(dicEstadoPago);
						ditPago.setFecRegistroActualizado(new Date());
					}
				}
			}
		}

		entityManager.merge(seguro);

		LOGGER.info("Estado actualizado a BAJA_POR_MORA para seguro: " + cveIdSeguroIvro);
		return true;

	} catch (Exception e) {
		LOGGER.error("Error actualizando estado mora para seguro " + cveIdSeguroIvro + ": " + e.getMessage(), e);
		return false;
	}
}

/**
 * Crea solo registros de bitácora para seguros ya dados de baja.
 */
@Override
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public Long registrarBitacoraBajaMora(Long cveIdSeguroIvro, Long cveIdLote, String usuarioOperador) {

	LOGGER.info("Registrando bitácora mora para seguro: " + cveIdSeguroIvro + ", lote: " + cveIdLote);

	try {
		DitSeguroIvro seguro = entityManager.find(DitSeguroIvro.class, cveIdSeguroIvro);
		if (seguro == null) {
			LOGGER.error("Seguro no encontrado: " + cveIdSeguroIvro);
			return null;
		}

		// Verificar que no existe ya bitácora
		Long countBajas = (Long) entityManager
				.createQuery("SELECT COUNT(b) FROM DitBajaSeguro b "
						+ "WHERE b.ditSeguroIvro.cveIdSeguroIvro = :idSeguro AND b.tpBaja = 'MORA'")
				.setParameter("idSeguro", cveIdSeguroIvro).getSingleResult();

		if (countBajas > 0) {
			LOGGER.warn("Ya existe bitácora de baja mora para seguro: " + cveIdSeguroIvro);
			return null;
		}

		// Calcular detalle de mora
		DetalleMoraInfo detalleMora = calcularDetalleMora(seguro, false);
		if (detalleMora == null) {
			LOGGER.error("No se pudo calcular detalle de mora para seguro: " + cveIdSeguroIvro);
			return null;
		}

		// Buscar lote
		LoteProcesamientoBaja lote = entityManager.find(LoteProcesamientoBaja.class, cveIdLote);
		if (lote == null) {
			LOGGER.error("Lote no encontrado: " + cveIdLote);
			return null;
		}

		// Crear DIT_BAJA_SEGURO
		DitBajaSeguro baja = new DitBajaSeguro();
		//baja.setTpBaja("MORA"); anterior 
		baja.setTpBaja("6");
		baja.setLoteProcesamientoBaja(lote);
		baja.setDitSeguroIvro(seguro);
		baja.setCveIdStaging(null);
		String nssAsignado = obtenerNssDelSeguro(seguro);
		baja.setCveNss(nssAsignado);
		
		// correo curp y nombre
		if (seguro.getDitPersona() != null) {
			baja.setTxtCurp(seguro.getDitPersona().getCurp()); 
			
			String nombre = seguro.getDitPersona().getNomNombre() != null ? seguro.getDitPersona().getNomNombre() : "";
			String apellidoPat = seguro.getDitPersona().getNomPrimerApellido() != null ? seguro.getDitPersona().getNomPrimerApellido() : "";
			String apellidoMat = seguro.getDitPersona().getNomSegundoApellido() != null ? seguro.getDitPersona().getNomSegundoApellido() : "";
			
			baja.setTxtNombreCompleto((nombre + " " + apellidoPat + " " + apellidoMat).trim()); 
		}
		baja.setTxtCorreos(obtenerCorreosBitacora(seguro)); 
		
		// ------------------------------------------------------------------		
		baja.setFecSolicitud(new Date());
		baja.setFecEfectivaBaja(new Date());
		baja.setTpEstado("PROCESADO");
		baja.setCveUsuarioCreacion(usuarioOperador);
		baja.setStpCreacion(new Date());
		baja.setStpActualizacion(new Date());

		entityManager.persist(baja);
		entityManager.flush();
		detalleMora.setCveIdBaja(baja.getCveIdBaja());

		LOGGER.info("DIT_BAJA_SEGURO creado con ID: " + baja.getCveIdBaja());

		// Crear DIT_BAJA_MORA_DETALLE
		DitBajaMoraDetalle detalle = new DitBajaMoraDetalle();
		detalle.setDitBajaSeguro(baja);
		detalle.setFecUltimoPago(detalleMora.getFecUltimoPago());
		entityManager.persist(detalle);
		entityManager.flush();
		detalleMora.setCveIdDetalle(detalle.getCveIdDetalle());
		
		
		// CREAR DIT_BAJA_MORA_PAGOS_VENCIDOS
		List<DitBajaMoraPagoVencido> pagosVencidosBitacora 
			= BajaMoraUtil.crearListMoraPagoVencidoBitacora(detalleMora);
		for (DitBajaMoraPagoVencido pV : pagosVencidosBitacora) {
			entityManager.persist(pV);
		}
		entityManager.flush();
		//detalle.setPagosVencidos(pagosVencidosBitacora);
		
		
		
		LOGGER.info("DIT_BAJA_MORA_DETALLE creado - bit�cora completa para seguro: " + cveIdSeguroIvro);
		
		return baja.getCveIdBaja();

	} catch (Exception e) {
		boolean errorPorConstrain = e.getCause() instanceof ConstraintViolationException;
		if(errorPorConstrain) {
			ConstraintViolationException exConst = (ConstraintViolationException) e.getCause();
			String constrainName = exConst.getConstraintName();
			boolean constrainUnicoNSSPorLote = constrainName.contains("NSS_LOTE");
			if(constrainUnicoNSSPorLote) {
				String msg = "Error: El mismo NSS existe en el mismo Lote, En un lote no se permite un NSS repetido.";
				LOGGER.error(msg);
				return null;
			}
			
		}
		LOGGER.error("Error registrando bitácora mora para seguro " + cveIdSeguroIvro + ": " + e.getMessage(), e);
		return null;
	}
}

/**
 * Busca seguros con estado BAJA_POR_MORA sin bitácora y crea registros
 * faltantes.
 */
@Override
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public Long completarBitacoraBajasMoraExistentes(Date fechaFiltro, Date dummy, String usuarioOperador) {

    LOGGER.info("Completando bitácora bajas mora existentes - Usuario: " + usuarioOperador);

    LoteProcesamientoBaja lote = new LoteProcesamientoBaja();
    lote.setTpOrigen("COMPLETAR_MORA_LEGADO");
    lote.setFecInicio(new Date());
    lote.setTpEstado("PROCESANDO");
    lote.setNumRegistrosTotal(0);
    lote.setNumExitosos(0);
    lote.setNumErrores(0);
    lote.setCveUsuarioCreacion(usuarioOperador);
    lote.setStpCreacion(new Date());
    lote.setStpActualizacion(new Date());

    entityManager.persist(lote);
    entityManager.flush();

    Long cveIdLote = lote.getCveIdLote();

    try {

        String sql =
        "SELECT s.* " +
        "FROM DIT_SEGURO_IVRO s " +
        "WHERE s.CVE_ID_ESTADO_SEGURO = :estadoMora " +
        "  AND NOT EXISTS ( " +
        "      SELECT 1 FROM DIT_BAJA_SEGURO b " +
        "      WHERE b.CVE_ID_SEGURO_IVRO = s.CVE_ID_SEGURO_IVRO " +
        "      AND b.TP_BAJA = 'MORA'" +
        "  ) " +
        "  AND ( " +
        "      TRUNC(s.FEC_REGISTRO_ACTUALIZADO) = TRUNC(:fechaFiltro) " +
        "      OR TRUNC(s.FEC_REGISTRO_ACTUALIZADO) = TRUNC(SYSDATE) " +
        "  ) " +
        "ORDER BY s.FEC_REGISTRO_ACTUALIZADO DESC";

        Query query = entityManager.createNativeQuery(sql, DitSeguroIvro.class);
        query.setParameter("estadoMora", EstadoSeguroIvroEnum.BAJA_POR_MORA.getId());
        query.setParameter("fechaFiltro", fechaFiltro);

        @SuppressWarnings("unchecked")
        List<DitSeguroIvro> segurosEncontrados = query.getResultList();

        for (DitSeguroIvro seguro : segurosEncontrados) {

            try {
                registrarBitacoraBajaMora(
                     seguro.getCveIdSeguroIvro(),
                     cveIdLote,
                     usuarioOperador
                );

                lote.setNumExitosos(lote.getNumExitosos() + 1);

            } catch (Exception ex) {
                lote.setNumErrores(lote.getNumErrores() + 1);
            }
        }

        lote.setTpEstado("COMPLETADO");
        lote.setFecFin(new Date());
        lote.setStpActualizacion(new Date());
        entityManager.merge(lote);

        return cveIdLote;

    } catch (Exception e) {

        lote.setTpEstado("ERROR");
        lote.setFecFin(new Date());
        lote.setStpActualizacion(new Date());
        entityManager.merge(lote);

        throw new RuntimeException(e);
    }
}

/**
 * Obtiene el NSS activo de un seguro.
 * 
 * @param seguro Entidad del seguro
 * @return NSS (11 dígitos) o null si no existe
 */
private String obtenerNssDelSeguro(DitSeguroIvro seguro) {
	if (seguro == null || seguro.getDitPersona() == null) {
		return null;
	}
	try {
		/*String nss = (String) entityManager
				.createQuery("SELECT ans.numNss FROM DitAsignacionNss ans " + "INNER JOIN ans.ditLlaveAsegurado la "
						+ "WHERE la.cveIdPersona = :idPersona " + "AND ans.fecRegistroBaja IS NULL")
				.setParameter("idPersona", seguro.getDitPersona().getCveIdPersona()).setMaxResults(1).getSingleResult();
		return nss;*/
		
		String sql = "SELECT ans.num_Nss " +
	             "FROM Dit_Asignacion_Nss ans " +
	             "INNER JOIN dit_Llave_Asegurado la ON la.CVE_ID_ASIGNACION_NSS = ans.CVE_ID_ASIGNACION_NSS " +
	             "WHERE la.cve_Id_Persona = ? " +
	             "AND ans.fec_Registro_Baja IS NULL " +
	             "AND ROWNUM = 1";
	 
	List<String> resultados = entityManager.createNativeQuery(sql)
	        .setParameter(1, seguro.getDitPersona().getCveIdPersona())
	        .getResultList();
	 
	return resultados.isEmpty() ? null : resultados.get(0);
	 
	} catch (Exception e) {
		LOGGER.warn("No se pudo obtener NSS para seguro " + seguro.getCveIdSeguroIvro() + ": " + e.getMessage());
		return null;
	}
}

/**
 * Obtiene el tipo de baja actual del seguro. Retorna MORA, REINGRESO_RO,
 * EXPRESA o null si no está en baja.
 */
@Override
public String obtenerTipoBajaActual(Long idSeguroIvro) {
	if (idSeguroIvro == null) {
		return null;
	}

	try {
		// Verificar estado actual del seguro
		DitSeguroIvro seguro = entityManager.find(DitSeguroIvro.class, idSeguroIvro);

		if (seguro == null) {
			LOGGER.warn("Seguro no encontrado: " + idSeguroIvro);
			return null;
		}

		DicEstadoSeguro dicEstado = seguro.getDicEstadoSeguro();
		if (dicEstado == null) {
			return null;
		}

		Long idEstado = dicEstado.getCveIdEstadoSeguro();

		// Verificar si está en estado de baja
		boolean esEstadoBaja = (idEstado != null && (idEstado.longValue() == EstadoSeguroIvroEnum.BAJA_POR_MORA.getId()
				|| idEstado.longValue() == EstadoSeguroIvroEnum.BAJA_POR_REINGRESO_RO.getId() 
				|| idEstado.longValue() == EstadoSeguroIvroEnum.BAJA_A_SOLICITUD_ASEGURADO.getId()));

		if (!esEstadoBaja) {
			return null;
		}

		// Obtener registro de baja más reciente
		String jpql = "SELECT b FROM DitBajaSeguro b " + "WHERE b.ditSeguroIvro.cveIdSeguroIvro = :idSeguro "
				+ "ORDER BY b.stpCreacion DESC";

		List resultados = entityManager.createQuery(jpql).setParameter("idSeguro", idSeguroIvro).setMaxResults(1)
				.getResultList();

		if (resultados.isEmpty()) {
			LOGGER.warn("Seguro " + idSeguroIvro + " está en estado baja pero sin registro en DIT_BAJA_SEGURO");
			return null;
		}

		DitBajaSeguro bajaActual = (DitBajaSeguro) resultados.get(0);
		String tipoBaja = bajaActual.getTpBaja();

		LOGGER.debug("Seguro " + idSeguroIvro + " - Tipo baja actual: " + tipoBaja);
		return tipoBaja;

	} catch (Exception e) {
		LOGGER.error("Error obteniendo tipo de baja actual para seguro " + idSeguroIvro + ": " + e.getMessage(), e);
		return null;
	}
}

/**
 *
 * FLUJO: 1. Validar que el seguro esté activo 2. Verificar que no tenga
 * solicitud pendiente 3. Generar token UUID único 4. Insertar
 * DIT_SOLICITUD_BAJA_WEB 5. Enviar correo con link de confirmación
 *
 * @param cveIdSeguroIvro ID del seguro
 * @param motivo          Motivo de baja (opcional, capturado del usuario)
 * @param usuario         Usuario autenticado
 * @param ipSolicitud     IP del cliente
 * @return ID de la solicitud creada
 * @throws Exception Si el seguro no está activo o ya tiene solicitud pendiente
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public Long solicitarBajaExpresa(Long cveIdSeguroIvro, String motivo, String usuario, String ipSolicitud)
		throws Exception {

	LOGGER.info("========================================");
	LOGGER.info("SOLICITAR BAJA EXPRESA");
	LOGGER.info("Seguro: " + cveIdSeguroIvro);
	LOGGER.info("Usuario: " + usuario);
	LOGGER.info("IP: " + ipSolicitud);
	LOGGER.info("========================================");

	// ========================================
	// FASE 1: Validaciones de negocio
	// ========================================
	DitSeguroIvro seguro = entityManager.find(DitSeguroIvro.class, cveIdSeguroIvro);

	if (seguro == null) {
		throw new Exception("Seguro no encontrado: " + cveIdSeguroIvro);
	}

	// Validar que el seguro esté ACTIVO
	if (seguro.getDicEstadoSeguro() == null
			|| seguro.getDicEstadoSeguro().getCveIdEstadoSeguro() != EstadoSeguroIvroEnum.ACTIVO.getId()) {
		throw new Exception("El seguro no está ACTIVO. Estado actual: "
				+ (seguro.getDicEstadoSeguro() != null ? seguro.getDicEstadoSeguro().getCveIdEstadoSeguro() : "NULL"));
	}

	// Verificar que no tenga solicitud PENDIENTE previa
	String jpql = "SELECT s FROM DitSolicitudBajaWeb s " + "WHERE s.cveIdSeguroIvro = :seguroId "
			+ "AND s.tpEstado = 'PENDIENTE'";

	List<DitSolicitudBajaWeb> solicitudesPendientes = entityManager.createQuery(jpql)
			.setParameter("seguroId", cveIdSeguroIvro).getResultList();

	if (!solicitudesPendientes.isEmpty()) {
		DitSolicitudBajaWeb solicitudPrevia = solicitudesPendientes.get(0);
		LOGGER.warn("Ya existe solicitud PENDIENTE para seguro " + cveIdSeguroIvro + ". Token: "
				+ solicitudPrevia.getTxtToken());

		// Opción A: Reenviar el mismo token (no crear nuevo)
		// return solicitudPrevia.getCveIdSolicitud();

		// Opción B: Cancelar el anterior y crear nuevo
		solicitudPrevia.setTpEstado("CANCELADA");
		solicitudPrevia.setStpActualizacion(new Timestamp(System.currentTimeMillis()));
		entityManager.merge(solicitudPrevia);
		LOGGER.info("Solicitud previa cancelada. Creando nueva...");
	}

	// ========================================
	// FASE 2: Generar token UUID único
	// ========================================
	String token = UUID.randomUUID().toString();

	// Calcular expiración: SYSDATE + 3 días (72 horas)
	Calendar cal = Calendar.getInstance();
	cal.add(Calendar.DAY_OF_MONTH, 3);
	Date fecExpiracion = new Date(cal.getTimeInMillis());

	LOGGER.info("Token generado: " + token);
	LOGGER.info("Expiración: " + fecExpiracion);

	// ========================================
	// FASE 3: Insertar DIT_SOLICITUD_BAJA_WEB
	// ========================================
	DitSolicitudBajaWeb solicitud = new DitSolicitudBajaWeb();
	solicitud.setCveIdSeguroIvro(cveIdSeguroIvro);
	solicitud.setTxtToken(token);
	solicitud.setFecExpiracion(fecExpiracion);
	solicitud.setTpEstado("PENDIENTE");
	solicitud.setFecSolicitud(new Timestamp(System.currentTimeMillis())); // REQ-19
	solicitud.setFecConfirmacion(null); // NULL hasta confirmar (REQ-20)
	solicitud.setTxtIpSolicitud(ipSolicitud);
	solicitud.setTxtMotivo(motivo);
	solicitud.setCveUsuarioSolicitud(usuario);
	solicitud.setStpCreacion(new Timestamp(System.currentTimeMillis()));
	solicitud.setStpActualizacion(new Timestamp(System.currentTimeMillis()));

	entityManager.persist(solicitud);
	entityManager.flush();

	Long cveIdSolicitud = solicitud.getCveIdSolicitud();
	LOGGER.info("Solicitud creada: " + cveIdSolicitud);

	String urlBajaConfirmacion = obtenerUrlBaseConfirmacion(token);
	// ========================================
	// FASE 4: Enviar correo con link de confirmación (REQ-16)
	// ========================================
	
	   enviaCorreoLocal.enviaCorreo(
			   seguro.getCveIdSeguroIvro(),
		        new Date(),
		        urlBajaConfirmacion,
		        TipoOperacionNotificacionIVROEnum.SOLICITUD_BAJA_EXPRESA.getCodigo()
		    );


	LOGGER.info("Solicitud de baja expresa completada. ID: " + cveIdSolicitud);
	return cveIdSolicitud;
}

/**
 *
 * FLUJO: 1. Buscar solicitud por token 2. Validar token (existe, PENDIENTE, no
 * expirado) 3. Buscar o crear lote diario (POST_WEB) 4. Insertar
 * DIT_BAJA_SEGURO 5. Insertar DIT_BAJA_EXPRESA_DETALLE 6. Actualizar
 * DIT_SOLICITUD_BAJA_WEB (CONFIRMADA) 7. Actualizar DIT_SEGURO_IVRO (estado
 * BAJA_A_SOLICITUD_ASEGURADO) 8. Enviar correo confirmación
 *
 * @param token          Token UUID recibido del link del correo
 * @param ipConfirmacion IP del cliente que confirma
 * @return true si la baja se ejecutó correctamente
 * @throws Exception Si el token es inválido, expirado o ya usado
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public boolean confirmarBajaExpresa(String token, String ipConfirmacion) throws Exception {

	LOGGER.info("========================================");
	LOGGER.info("CONFIRMAR BAJA EXPRESA");
	LOGGER.info("Token: " + token);
	LOGGER.info("IP: " + ipConfirmacion);
	LOGGER.info("========================================");

	// ========================================
	// FASE 1: Buscar y validar token
	// ========================================
	String jpql = "SELECT s FROM DitSolicitudBajaWeb s WHERE s.txtToken = :token";
	List<DitSolicitudBajaWeb> resultados = entityManager.createQuery(jpql).setParameter("token", token).getResultList();

	if (resultados.isEmpty()) {
		throw new Exception("Token inválido: " + token);
	}

	DitSolicitudBajaWeb solicitud = resultados.get(0);

	// Validación A: ¿Estado PENDIENTE?
	if (!"PENDIENTE".equals(solicitud.getTpEstado())) {
		String mensaje = "CONFIRMADA".equals(solicitud.getTpEstado()) ? "Esta solicitud ya fue procesada anteriormente"
				: "Token inválido. Estado: " + solicitud.getTpEstado();
		throw new Exception(mensaje);
	}

	// Validación B: ¿Token expirado?
	Date ahora = new Date(System.currentTimeMillis());
	if (solicitud.getFecExpiracion().before(ahora)) {
		// Marcar como expirado
		solicitud.setTpEstado("EXPIRADA");
		solicitud.setStpActualizacion(new Timestamp(System.currentTimeMillis()));
		entityManager.merge(solicitud);

		throw new Exception("El token ha expirado. Solicite una nueva baja.");
	}

	LOGGER.info("Token válido. Solicitud: " + solicitud.getCveIdSolicitud());

	// ========================================
	// FASE 2: Buscar o crear lote diario (POST_WEB)
	// ========================================
	//LoteProcesamientoBaja lote = buscarOCrearLoteDiarioWeb();
	LoteProcesamientoBaja lote = obtenerLoteFijoPostWeb();
	LOGGER.info("Lote diario web: " + lote.getCveIdLote());

	// ========================================
	// FASE 3: Obtener seguro y validar estado actual
	// ========================================
	DitSeguroIvro seguro = entityManager.find(DitSeguroIvro.class, solicitud.getCveIdSeguroIvro());
	
	
	String correosFinal= new String();
	String curpPersonaBitacora = new String();
	String nombreCompletoPersonaBitacora = new String();
	
	if (seguro.getDitPersona() != null) {
		
		DitPersona personaAsegurada = seguro.getDitPersona();
		
		curpPersonaBitacora = personaAsegurada.getCurp();
		nombreCompletoPersonaBitacora = personaAsegurada.getNomNombre()+" "+ personaAsegurada.getNomPrimerApellido()+" "+personaAsegurada.getNomSegundoApellido();
		
		correosFinal = obtenerCorreosBitacora(seguro);
    //LOGGER.info(correosFinal);
    // persistir
}

	if (seguro == null) {
		throw new Exception("Seguro no encontrado: " + solicitud.getCveIdSeguroIvro());
	}

	// Validar que siga ACTIVO (no se haya dado de baja mientras tanto)
	if (seguro.getDicEstadoSeguro() == null
			|| seguro.getDicEstadoSeguro().getCveIdEstadoSeguro() != EstadoSeguroIvroEnum.ACTIVO.getId()) {
		throw new Exception("El seguro ya no está ACTIVO. No se puede procesar la baja.");
	}

	// ========================================
	// FASE 4: Insertar DIT_BAJA_SEGURO
	// ========================================
	LOGGER.info("Homologacion en tipo de baja" + String.valueOf(seguro.getDicEstadoSeguro().getCveIdEstadoSeguro()));
	DitBajaSeguro baja = new DitBajaSeguro();
	//baja.setTpBaja("EXPRESA");
	baja.setTpBaja(String.valueOf(EstadoSeguroIvroEnum.BAJA_A_SOLICITUD_ASEGURADO.getId()));
	baja.setCveIdStaging(null); // Baja expresa NO tiene staging
	baja.setLoteProcesamientoBaja(lote);
	baja.setDitSeguroIvro(seguro);
	baja.setCveNss(obtenerNssDelSeguro(seguro));
	baja.setFecSolicitud(new Date(solicitud.getFecSolicitud().getTime()));
	baja.setFecEfectivaBaja(new Date(System.currentTimeMillis()));
	baja.setTpEstado("PROCESADO"); // Directo a PROCESADO (ya confirmado)
	baja.setCveUsuarioCreacion(solicitud.getCveUsuarioSolicitud());
	baja.setStpCreacion(new Timestamp(System.currentTimeMillis()));
	baja.setStpActualizacion(new Timestamp(System.currentTimeMillis()));
	baja.setTxtCorreos(correosFinal);
	baja.setTxtCurp(curpPersonaBitacora);
	baja.setTxtNombreCompleto(nombreCompletoPersonaBitacora);
	

	entityManager.persist(baja);
	entityManager.flush();

	Long cveIdBaja = baja.getCveIdBaja();
	LOGGER.info("Baja creada: " + cveIdBaja);

	// ========================================
	// FASE 5: Insertar DIT_BAJA_EXPRESA_DETALLE
	// ========================================
	DitBajaExpresaDetalle detalle = new DitBajaExpresaDetalle();
	detalle.setDitBajaSeguro(baja);
	detalle.setFecSolicitudBaja(solicitud.getFecSolicitud()); // REQ-19
	detalle.setTxtIpSolicitud(solicitud.getTxtIpSolicitud());
	detalle.setCveUsuarioSolicitud(solicitud.getCveUsuarioSolicitud());
	detalle.setFecConfirmacionBaja(new Timestamp(System.currentTimeMillis())); // REQ-20
	detalle.setTxtIpConfirmacion(ipConfirmacion);
	detalle.setTxtMotivo(solicitud.getTxtMotivo());
	detalle.setCveIdSolicitud(solicitud.getCveIdSolicitud());
	detalle.setTxtTokenUsado(token);
	LOGGER.info("token : " + token);
	detalle.setStpCreacion(new Timestamp(System.currentTimeMillis()));

	entityManager.persist(detalle);
	LOGGER.info("Detalle baja expresa creado");

	// ========================================
	// FASE 6: Actualizar DIT_SOLICITUD_BAJA_WEB
	// ========================================
	solicitud.setTpEstado("CONFIRMADA");
	solicitud.setFecConfirmacion(new Timestamp(System.currentTimeMillis())); // REQ-20
	solicitud.setTxtIpConfirmacion(ipConfirmacion);
	solicitud.setStpActualizacion(new Timestamp(System.currentTimeMillis()));

	entityManager.merge(solicitud);
	LOGGER.info("Solicitud actualizada a CONFIRMADA");

	// ========================================
	// FASE 7: Actualizar DIT_SEGURO_IVRO (REQ-15)
	// ========================================
	seguro.setDicEstadoSeguro(IvroFactory.estadoSeguro(EstadoSeguroIvroEnum.BAJA_A_SOLICITUD_ASEGURADO // ID = 9
	));
	seguro.setFecRegistroActualizado(new Date(System.currentTimeMillis()));

	entityManager.merge(seguro);
	LOGGER.info("Seguro actualizado a estado BAJA_A_SOLICITUD_ASEGURADO");

	// ========================================
	// FASE 8: Actualizar contadores del lote
	// ========================================	
	lote.setNumExitosos(lote.getNumExitosos() + 1);
	lote.setNumRegistrosTotal(lote.getNumRegistrosTotal() + 1);
	lote.setStpActualizacion(new Date(System.currentTimeMillis()));

	entityManager.merge(lote);

	// ========================================
	// FASE 9: Enviar correo de confirmación
	// ========================================
	
	try {
        enviaCorreoLocal.enviaCorreo(
        	seguro.getCveIdSeguroIvro(),
            baja.getFecEfectivaBaja(),
            null,
            TipoOperacionNotificacionIVROEnum.CONFIRMACION_BAJA_EXPRESA.getCodigo()
        );
        LOGGER.info("Correo enviado");
    } catch (Exception ex) {
        LOGGER.error("Error enviando correo: " + ex.getMessage());
    }

	LOGGER.info("Baja expresa confirmada exitosamente. ID Baja: " + cveIdBaja);
	return true;
}

//========================================================================
//MÉTODOS PRIVADOS DE SOPORTE
//========================================================================

/**
* Obtiene el lote fijo de POST_WEB
*
* Este lote debe haber sido creado previamente por script de datos.
* Se usa un lote único permanente para todas las bajas expresa,
* en lugar de crear lotes diarios.
*
* @return El lote fijo de POST_WEB
* @throws IllegalStateException Si no se encuentra el lote fijo
*/
private LoteProcesamientoBaja obtenerLoteFijoPostWeb() {
 
    String jpql = "SELECT l " +
                  "FROM LoteProcesamientoBaja l " +
                  "WHERE l.tpOrigen = :origen " +
                  "  AND l.txtNombreArchivo = :nombre " +
                  "  AND l.tpEstado = 'PROCESANDO'";
 
    List<LoteProcesamientoBaja> resultados = entityManager
        .createQuery(jpql, LoteProcesamientoBaja.class)
        .setParameter("origen", "POST_WEB")
        .setParameter("nombre", NOMBRE_LOTE_POST_WEB_FIJO)
        .getResultList();
 
    if (resultados.isEmpty()) {
        throw new IllegalStateException(
            "No se encontró el lote fijo POST_WEB con txtNombreArchivo = '" +
            NOMBRE_LOTE_POST_WEB_FIJO + "'. Verificar script de datos."
        );
    }
 
    return resultados.get(0);
}

/**
 * Obtener URL base para confirmación de baja expresa
 *
 * Configurar en properties o hardcodear según ambiente
 *
 * @return URL base (ej: https://digital.imss.gob.mx/baja-expresa/confirmar)
 */
private String obtenerUrlBaseConfirmacion(String token) {
	// TODO: Leer de properties o configuración
	// return PropertiesUtil.getProperty("url.baja.expresa.confirmacion");

	// Por ahora hardcodeado (ajustar según ambiente)
	// https://serviciosdigitales.imss.gob.mx/gestionSeguroVoluntario-web-ciudadano/wizard/detalle/seguro/748811
	// https://serviciosdigitales-stage.imss.gob.mx/portal-ciudadano-web-externo/home/validar
	return "https://serviciosdigitales-stage.imss.gob.mx/gestionSeguroVoluntario-web-ciudadano/baja-expresa/confirmar?t=" + token;
}

	/**
	 * Ejecuta baja por mora para una LISTA de seguros (3 fases en secuencia).
	 * Método de conveniencia que ejecuta el flujo completo para múltiples seguros.
	 *
	 * @param idsSeguro       Lista de IDs de seguros a procesar
	 * @param usuarioOperador Usuario que ejecuta
	 * @return ID del lote creado
	 * @throws Exception Si ocurre un error en el procesamiento
	 */
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Long ejecutarBajaPorMoraLista(List<Long> idsSeguro, String usuarioOperador) throws Exception {
	
		final String METHOD = "ejecutarBajaPorMoraLista";
		LOGGER.info(METHOD + " - Iniciando proceso para " + idsSeguro.size() + " seguros");
	
		// Crear lote para registrar el procesamiento
		LoteProcesamientoBaja lote = new LoteProcesamientoBaja();
		lote.setTpOrigen("BAJA_MORA_LISTA");
		lote.setFecInicio(new Date());
		lote.setTpEstado("PROCESANDO");
		lote.setNumRegistrosTotal(idsSeguro.size());
		lote.setNumExitosos(0);
		lote.setNumErrores(0);
		lote.setCveUsuarioCreacion(usuarioOperador);
		lote.setStpCreacion(new Date());
		lote.setStpActualizacion(new Date());
	
		entityManager.persist(lote);
		entityManager.flush();
	
		Long cveIdLote = lote.getCveIdLote();
		LOGGER.info(METHOD + " - Lote creado: " + cveIdLote);
	
		int totalExitosos = 0;
		int totalErrores = 0;
	
		// Procesar cada seguro
		for (Long idSeguro : idsSeguro) {
			try {
				LOGGER.info(METHOD + " - Procesando seguro: " + idSeguro);
	
				// Ejecutar flujo completo (3 fases)
				boolean resultado = ejecutarBajaPorMora(idSeguro, cveIdLote, usuarioOperador);
	
				if (resultado) {
					totalExitosos++;
					LOGGER.info(METHOD + " - Seguro " + idSeguro + " procesado exitosamente");
				} else {
					totalErrores++;
					LOGGER.warn(METHOD + " - Seguro " + idSeguro + " no pudo procesarse");
				}
	
			} catch (Exception ex) {
				totalErrores++;
				LOGGER.error(METHOD + " - Error procesando seguro " + idSeguro + ": " + ex.getMessage(), ex);
			} 
		}
	
		// Cerrar lote
		lote.setTpEstado("COMPLETADO");
		lote.setFecFin(new Date());
		lote.setNumExitosos(totalExitosos);
		lote.setNumErrores(totalErrores);
		lote.setStpActualizacion(new Date());
	
		entityManager.merge(lote);
	
		LOGGER.info(METHOD + " - Proceso completado - Exitosos: " + totalExitosos + ", Errores: " + totalErrores);
	
		return cveIdLote;
	}
	/**
	 * Obtener un listado de correos para persistir en bitacora
	 * @param seguroIncome
	 * @return
	 */
	private String obtenerCorreosBitacora(DitSeguroIvro seguroIncome) {
		String correosInt = new String();
		StringBuffer correosNotificados = new StringBuffer();
	    if (seguroIncome.getDitPersona().getDitPersonafContactos() != null) {
	        for (DitPersonafContacto contactoPersona : seguroIncome.getDitPersona().getDitPersonafContactos()) {

	            if (contactoPersona.getFecRegistroBaja() == null) {
	                DitFormaContacto formaContacto = contactoPersona.getDitFormaContacto();
	                if (formaContacto != null && formaContacto.getDitTipoContacto() != null) {
	                    Long idTipoContacto = formaContacto.getDitTipoContacto().getCveIdTipoContacto();
	                    if (idTipoContacto != null && idTipoContacto.equals(1L)) { 
	                    	correosNotificados.append(formaContacto.getDesFormaContacto()+",");
	                        //break;
	                    	//LOGGER.info(correosNotificados.toString());
	                    }
	                }
	            }
	        }
	        correosInt= correosNotificados.toString();
	    }
	    
	    if(correosInt.lastIndexOf(",",correosInt.length()) >0){
	    	correosInt = correosInt.substring(0, correosInt.length() -1);
	    }
		
		return correosInt;
	}

	
	@Override
    public void guardarHistorialUltimoSeguro(HistorialUltimoSeguroCotizadoDTO historial)
            throws IvroException {
        if (historial == null) {
            throw new IvroException("El historial del ultimo seguro es obligatorio");
        }

        if (historial.getCveNss() == null || !historial.getCveNss().trim().matches("[0-9]{11}")
                || historial.getFecConsulta() == null) {
            throw new IvroException("NSS y fecha de consulta son obligatorios");
        }
        java.util.Calendar inicio = java.util.Calendar.getInstance();
        inicio.setTime(historial.getFecConsulta());
        inicio.set(java.util.Calendar.HOUR_OF_DAY, 0);
        inicio.set(java.util.Calendar.MINUTE, 0);
        inicio.set(java.util.Calendar.SECOND, 0);
        inicio.set(java.util.Calendar.MILLISECOND, 0);
        java.util.Date desde = inicio.getTime();
        inicio.add(java.util.Calendar.DAY_OF_MONTH, 1);
        java.util.Date hasta = inicio.getTime();
        if (!entityManager.createQuery("select u.idUltimoTrabajo from BdtutUltimoTrabajo u "
                + "where trim(u.cveNss) = :nss and u.fecConsulta >= :desde and u.fecConsulta < :hasta")
                .setParameter("nss", historial.getCveNss().trim())
                .setParameter("desde", desde)
                .setParameter("hasta", hasta)
                .setMaxResults(1).getResultList().isEmpty()) {
        	
        	LOGGER.info("***Se detecta que el asegurado ya tiene un registro el dia de hoy, no se vuelve a guardar.");
        	
            return;
        }

        BdtutUltimoTrabajo entity = new BdtutUltimoTrabajo();
        entity.setCveNss(historial.getCveNss());
        entity.setRefRegistroPatronal(historial.getRefRegistroPatronal());
        entity.setCveEntInegi(historial.getCveEntInegi());
        entity.setCveMunInegi(historial.getCveMunInegi());
        entity.setCveIdMunicipioImss(historial.getCveIdMunicipioImss());
        entity.setFecConsulta(historial.getFecConsulta());
        entity.setCveMunicipioImss(historial.getCveMunicipioImss());
        entity.setNumAnioUltimoTrabajo(historial.getNumAnioUltimoTrabajo());
        entity.setNumMesUltimoTrabajo(historial.getNumMesUltimoTrabajo());
        entity.setCveModalidad(historial.getCveModalidad());
        entity.setNumSalarioUltimoTrabajo(historial.getSalarioUltimoTrabajo());
        entity.setNumSemanasRoUlt5anios(historial.getNumSemanasRoUltSanios());
        entity.setIndPension(historial.getIndPension());
        entity.setIndTrabajadorImss(historial.getIndTrabajadorImss());
        entity.setStpAlta(historial.getStpAlta());
        entity.setCveUsuarioAlta(historial.getCveUsuarioAlta());
        entity.setStpModifica(historial.getStpModifica());
        entity.setCveUsuarioModifica(historial.getCveUsuarioModifica());
        entity.setStpBaja(historial.getStpBaja());
        entity.setCveUsuarioBaja(historial.getCveUsuarioBaja());
        entity.setCveCurp(historial.getCveCurp());
        entity.setCveRfcAsegurado(historial.getCveRfcAsegurado());
        entity.setNomAsegurado(historial.getNomAsegurado());
        entity.setFecBajaUltimoTrabajo(historial.getFecBajaUltimoTrabajo());
        entityManager.persist(entity);
    }

    @Override
    public boolean actualizarHistorialUltimoSeguroModalidad40(String cveNss, String cveEntInegi,
            String cveMunInegi) throws IvroException {
        if (cveNss == null || !cveNss.trim().matches("[0-9]{11}")) {
            return false;
        }
        if (cveEntInegi == null || cveEntInegi.trim().isEmpty()
                || cveMunInegi == null || cveMunInegi.trim().isEmpty()) {
            return false;
        }

        java.util.Calendar inicio = java.util.Calendar.getInstance();
        inicio.set(java.util.Calendar.HOUR_OF_DAY, 0);
        inicio.set(java.util.Calendar.MINUTE, 0);
        inicio.set(java.util.Calendar.SECOND, 0);
        inicio.set(java.util.Calendar.MILLISECOND, 0);
        java.util.Date desde = inicio.getTime();
        inicio.add(java.util.Calendar.DAY_OF_MONTH, 1);

        java.util.List<BdtutUltimoTrabajo> registros = entityManager.createQuery(
                "select u from BdtutUltimoTrabajo u "
                + "where trim(u.cveNss) = :nss and u.fecConsulta >= :desde "
                + "and u.fecConsulta < :hasta "
                + "order by u.fecConsulta desc, u.idUltimoTrabajo desc", BdtutUltimoTrabajo.class)
                .setParameter("nss", cveNss.trim())
                .setParameter("desde", desde)
                .setParameter("hasta", inicio.getTime())
                .setMaxResults(1).getResultList();

        if (registros.isEmpty()) {
            return false;
        }

        BdtutUltimoTrabajo ultimoTrabajo = registros.get(0);
        ultimoTrabajo.setCveEntInegi(cveEntInegi.trim());
        ultimoTrabajo.setCveMunInegi(cveMunInegi.trim());
        ultimoTrabajo.setStpModifica(new java.util.Date());
        ultimoTrabajo.setCveUsuarioModifica("MODALIDAD40");
        entityManager.flush();
        return true;
    }
    
    
    @Override
    public BdtutUltimoTrabajo getUltimoTrabajoPorNss(String cveNss) throws IvroException {
        if (cveNss == null || !cveNss.trim().matches("[0-9]{11}")) {
            throw new IvroException("El NSS debe tener 11 digitos");
        }
 
        java.util.Calendar inicio = java.util.Calendar.getInstance();
        inicio.set(java.util.Calendar.HOUR_OF_DAY, 0);
        inicio.set(java.util.Calendar.MINUTE, 0);
        inicio.set(java.util.Calendar.SECOND, 0);
        inicio.set(java.util.Calendar.MILLISECOND, 0);
        java.util.Date desde = inicio.getTime();
        inicio.add(java.util.Calendar.DAY_OF_MONTH, 1);
 
        java.util.List<BdtutUltimoTrabajo> registros = entityManager.createQuery(
                "select u from BdtutUltimoTrabajo u where trim(u.cveNss) = :nss "
                + "and u.fecConsulta >= :desde and u.fecConsulta < :hasta "
                + "order by u.fecConsulta desc, u.idUltimoTrabajo desc", BdtutUltimoTrabajo.class)
                .setParameter("nss", cveNss.trim())
                .setParameter("desde", desde)
                .setParameter("hasta", inicio.getTime())
                .setMaxResults(1).getResultList();
 
        return registros.isEmpty() ? null : registros.get(0);
    }

}
