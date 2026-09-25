/**
 *
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBException;

import mx.gob.imss.ctirss.delta.gestion.motorCalculo.service.exception.SUAException;
import mx.gob.imss.ctirss.delta.model.enums.EstadoSeguroIvroEnum;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoSeguro;
import mx.gob.imss.ctirss.delta.persistence.DicModalidad;
import mx.gob.imss.ctirss.delta.persistence.DitAsignacionNss;
import mx.gob.imss.ctirss.delta.persistence.DitDetalleTramite;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitSeguroIvro;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;

import mx.gob.imss.ctirss.delta.persistence.cobranza.DitCompra;
import mx.gob.imss.ctirss.delta.persistence.cobranza.DitPago;
import mx.gob.imss.digital.modelo.cobranza.Compra;
import mx.gob.imss.digital.modelo.cobranza.EstadoCompra;
import mx.gob.imss.digital.modelo.cobranza.Pago;
import mx.gob.imss.digital.modelo.comun.Modalidad;
import mx.gob.imss.digital.modelo.persona.Fisica;
import mx.gob.imss.digital.modelo.seguros.EstadoSeguroIvro;
import mx.gob.imss.digital.modelo.seguros.SeguroIvro;
import mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvro;
import mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvroMod33;
import mx.gob.imss.digital.modelo.tramite.TramiteSeguroIvroMod40;
import mx.gob.imss.distss.digital.jaxb.util.JaxbUtil;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase utilitaria para la cracion de seguros ivro xml a persistentes y
 * viceversa
 *
 * @author NOVUTECK1
 *
 */
public abstract class IvroFactory {

    /**
     * Modalidadaes de seguors individuales
     */
    public final static Long[] MOD_INDIVIDUAL = new Long[]{ModalidadEnum.TREINTAYCINCO.getId(),
        ModalidadEnum.CUARENTAYTRES.getId(), ModalidadEnum.CUARENTAYCUATRO.getId()};
    /**
     * Modalidaddes de seguros domesticos
     */
    public final static Long[] MOD_DOMESTICO = new Long[]{ModalidadEnum.TREINTAYCUATRO.getId()};
    public final static Long[] MOD_FAMILIAR = new Long[]{ModalidadEnum.TREINTAYTRES.getId()};
    public final static Long[] MOD_CVRO = new Long[]{ModalidadEnum.CUARENTA.getId()};
    /**
     * Logger de la clase
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(IvroFactory.class);

    /**
     * OBtiene la representacion de un seguro a patir de su entidad persitente
     *
     * @param seguros el seguro persitido
     * @return la representacion del seguro
     */
    public static final List<SeguroIvro> generaSeguros(List<DitSeguroIvro> seguros) {
        List<SeguroIvro> segurosIvro = new ArrayList<SeguroIvro>();
        for (DitSeguroIvro ditSeguro : seguros) {
            segurosIvro.add(generaSeguro(ditSeguro));
        }
        return segurosIvro;
    }

    /**
     * OBtiene la representacion de un seguro a patir de su entidad persitente
     * 
     * @param seguros
     *            el seguro persitido
     * @return la representacion del seguro
     */
    public static final List<SeguroIvro> generaSegurosConCompra(List<DitSeguroIvro> seguros) {
        List<SeguroIvro> segurosIvro = new ArrayList<SeguroIvro>();
        for (DitSeguroIvro ditSeguro : seguros) {
            segurosIvro.add(generaSeguroConCompra(ditSeguro));
        }
        return segurosIvro;
    }
    
    /**
     * OBtiene la representacion de un seguro a patir de su entidad persitente
     * 
     * @param seguro
     *            el seguro persitido
     * @return la representacion del seguro
     */
    public static final SeguroIvro generaSeguro(DitSeguroIvro seguro) {
        SeguroIvro seguroIvro = new SeguroIvro();
        seguroIvro.setCveIdSeguroIvro(seguro.getCveIdSeguroIvro());
        seguroIvro.setEstadoSeguro(generaEstadoSeguro(seguro.getDicEstadoSeguro()));
        seguroIvro.setFechaInicio(seguro.getFecInicio());
        seguroIvro.setFechaFin(seguro.getFecFin());
        seguroIvro.setModalidad(getModalidad(seguro.getDicModalidad()));
        seguroIvro.setTitular(generaPersona(seguro.getDitPersona()));
        if (seguro.getDitCompra() != null) {
            seguroIvro.setCompra(new Compra());
            seguroIvro.getCompra().setIdCompra(seguro.getDitCompra().getCveIdCompra());
            if(seguro.getDitCompra().getDicFormaPgo() != null){
            	seguroIvro.getCompra().setFormaPago(seguro.getDitCompra().getDicFormaPgo().getCveIdFormaPago());
}
            if(seguro.getDitCompra().getDitCotizacion()!=null){
                seguroIvro.getCompra().setIdCotizacion(seguro.getDitCompra().getDitCotizacion().getCveIdCotizacion());
            }
        }
        seguroIvro.setTramite(generaTramite(seguro.getDitTramites()));
        seguroIvro.setTramiteSeguroFamiliar(generaTramiteSeguroFamiliar(seguro.getDitTramites()));
        seguroIvro.setTramiteContVoluntaria(generaTramiteContinuacionVoluntaria(seguro.getDitTramites()));
        return seguroIvro;
    }
    
    /**
     * OBtiene la representacion de un seguro a patir de su entidad persitente
     * 
     * @param seguro
     *            el seguro persitido
     * @return la representacion del seguro
     */
    public static final SeguroIvro generaSeguroConCompra(DitSeguroIvro seguro) {
        SeguroIvro seguroIvro = new SeguroIvro();
        seguroIvro.setCveIdSeguroIvro(seguro.getCveIdSeguroIvro());
        seguroIvro.setEstadoSeguro(generaEstadoSeguro(seguro.getDicEstadoSeguro()));
        seguroIvro.setFechaInicio(seguro.getFecInicio());
        seguroIvro.setFechaFin(seguro.getFecFin());
        seguroIvro.setModalidad(getModalidad(seguro.getDicModalidad()));
        seguroIvro.setTitular(generaPersona(seguro.getDitPersona()));
        if (seguro.getDitCompra() != null) {
            seguroIvro.setCompra(new Compra());
            seguroIvro.getCompra().setIdCompra(seguro.getDitCompra().getCveIdCompra());
            if(seguro.getDitCompra().getDicFormaPgo() != null){
            	seguroIvro.getCompra().setFormaPago(seguro.getDitCompra().getDicFormaPgo().getCveIdFormaPago());
            }
            if(seguro.getDitCompra().getDitPagos() != null){
            	Pago[] pagos = new Pago[seguro.getDitCompra().getDitPagos().size()];
            	int i = 0;
            	for(DitPago ditPago : seguro.getDitCompra().getDitPagos()){
            		Pago pago = new Pago();
            		pago.setIdPago(ditPago.getCveIdPago());
            		pago.setFechaInicioPeriodo(ditPago.getFecIniPeriodo());
            		pago.setFechaFinPeriodo(ditPago.getFecFinPeriodo());
            		pagos[i++] = pago;
            	}
            	seguroIvro.getCompra().setPagos(pagos);
            }
            
        }
        seguroIvro.setTramite(generaTramite(seguro.getDitTramites()));
        seguroIvro.setTramiteSeguroFamiliar(generaTramiteSeguroFamiliar(seguro.getDitTramites()));
        seguroIvro.setTramiteContVoluntaria(generaTramiteContinuacionVoluntaria(seguro.getDitTramites()));
        return seguroIvro;
    }

    /**
     * GEnera el modelo persistente de un seguro a parti de los datos del modelo
     * no persistente
     *
     * @param seguro el seguro a ser transformado en su parte persistencia
     * @return el seguro de la persistencia
     */
    public static final DitSeguroIvro generaDitSeguro(SeguroIvro seguro) {
        DitSeguroIvro ditSeguro = new DitSeguroIvro();
        ditSeguro.setDicEstadoSeguro(generaEstadoSeguro(seguro.getEstadoSeguro()));
        ditSeguro.setDicModalidad(getDicModalidad(seguro.getModalidad()));
        ditSeguro.setDitCompra(getDitCompra(seguro.getCompra()));
        ditSeguro.setDitPersona(generaPersona(seguro.getTitular()));
        ditSeguro.setDitTramites(generaTramite(seguro.getTramite()));
        ditSeguro.setFecFin(seguro.getFechaFin());
        ditSeguro.setFecInicio(seguro.getFechaInicio());
        ditSeguro.setFecRegistroAlta(new Date());
        ditSeguro.setFecRegistroActualizado(new Date());
        return ditSeguro;
    }

    /**
     * Genera el tramite de seguro ivro a partir de los dats de los tramites de
     * un seguro ya persistido
     *
     * @param tramites los tramites para generar el tramite de seguro ivro
     * @return el tramite generado para sociar al modelo xml
     */
    public static final TramiteSeguroIvro generaTramite(Set<DitTramite> tramites) {
        TramiteSeguroIvro tramiteSeguro = null;
        if (tramites != null && !tramites.isEmpty()) {
            for (DitTramite tramite : tramites) {
                Integer tipoTramite = Integer.valueOf(tramite.getDicTipoTramite()
                        .getCveIdTipoTramite().intValue());
                if ((tipoTramite.equals(TipoTramiteEnum.COMPRA_SEGURO_INDIVIDUAL.getCodigo())
                        || tipoTramite.equals(TipoTramiteEnum.RENOVACION_SEGURO_INDIVIDUAL
                                .getCodigo())
                        || tipoTramite.equals(TipoTramiteEnum.COMPRA_SEGURO_DOMESTICO.getCodigo()) || tipoTramite
                        .equals(TipoTramiteEnum.RENOVACION_SEGURO_DOMESTICO.getCodigo()))
                        && tramite.getDitDetalleTramite() != null) {
                    try {
                        tramiteSeguro = JaxbUtil.unmarshaller(tramite.getDitDetalleTramite()
                                .getRefDatosTramiteXml(), TramiteSeguroIvro.class);
                        tramiteSeguro.setTramiteId(tramite.getCveIdTramite());
                    } catch (JAXBException e) {
                        LOGGER.error("Error al parsear el tramite del seguro", e);
                    }
                } else if (tipoTramite.equals(TipoTramiteEnum.COMPRA_SEGURO_FAMILIAR.getCodigo())
                        || tipoTramite.equals(TipoTramiteEnum.RENOVACION_SEGURO_FAMILIAR.getCodigo())) {
                    try {
                        tramiteSeguro = JaxbUtil.unmarshaller(tramite.getDitDetalleTramite()
                                .getRefDatosTramiteXml(), TramiteSeguroIvroMod33.class);
                        tramiteSeguro.setTramiteId(tramite.getCveIdTramite());
                    } catch (JAXBException e) {
                        LOGGER.error("Error al parsear el tramite del seguro", e);
                    }
                } else if (tipoTramite.equals(TipoTramiteEnum.COMPRA_CONTINUACION_VOLUNTARIA.getCodigo())
                        || tipoTramite.equals(TipoTramiteEnum.RENOVACION_CONTINUACION_VOLUNTARIA.getCodigo())) {
                    try {
                        tramiteSeguro = JaxbUtil.unmarshaller(tramite.getDitDetalleTramite()
                                .getRefDatosTramiteXml(), TramiteSeguroIvroMod40.class);
                        tramiteSeguro.setTramiteId(tramite.getCveIdTramite());
                    } catch (JAXBException e) {
                        LOGGER.error("Error al parsear el tramite del seguro", e);
                    }
                }
            }
        }
        if (tramiteSeguro != null) {
            corrigeNombreBeneficiarios(tramiteSeguro.getBeneficiarios());
        }
        return tramiteSeguro;
    }

    /**
     * Genera el tramite de seguro ivro a partir de los dats de los tramites de
     * un seguro ya persistido
     *
     * @param tramites los tramites para generar el tramite de seguro ivro
     * @return el tramite generado para sociar al modelo xml
     */
    public static final TramiteSeguroIvroMod33 generaTramiteSeguroFamiliar(Set<DitTramite> tramites) {
        TramiteSeguroIvroMod33 tramiteSeguro = null;

        if (tramites != null && !tramites.isEmpty()) {
            for (DitTramite tramite : tramites) {
                Integer tipoTramite = Integer.valueOf(tramite.getDicTipoTramite()
                        .getCveIdTipoTramite().intValue());
                if ((tipoTramite.equals(TipoTramiteEnum.COMPRA_SEGURO_FAMILIAR.getCodigo())
                        || tipoTramite.equals(TipoTramiteEnum.RENOVACION_SEGURO_FAMILIAR.getCodigo()))
                        && tramite.getDitDetalleTramite() != null) {
                    try {
                        tramiteSeguro = JaxbUtil.unmarshaller(tramite.getDitDetalleTramite()
                                .getRefDatosTramiteXml(), TramiteSeguroIvroMod33.class);
                        tramiteSeguro.setTramiteId(tramite.getCveIdTramite());
                        corrigeNombreBeneficiarios(tramiteSeguro.getBeneficiarios());
                    } catch (JAXBException e) {
                        LOGGER.error("Error al parsear el tramite de un seguro familiar: " + e.getMessage());
                    } catch (Exception e) {
                        LOGGER.warn("Error al transformar el tramite de un seguro familiar: " + e.getMessage());
                    }
                }
            }
        }

        return tramiteSeguro;
    }

    private static void corrigeNombreBeneficiarios(Fisica[] beneficiarios) {
        if (beneficiarios != null && beneficiarios.length > 0) {
            for (Fisica beneficiario : beneficiarios) {
                beneficiario.setNombre(corrigeCadena(beneficiario.getNombre()));
            }
        }
    }

    /**
     * Genera el tramite de seguro ivro a partir de los dats de los tramites de
     * un seguro ya persistido
     *
     * @param tramites los tramites para generar el tramite de seguro ivro
     * @return el tramite generado para sociar al modelo xml
     */
    public static final TramiteSeguroIvroMod40 generaTramiteContinuacionVoluntaria(Set<DitTramite> tramites) {
        TramiteSeguroIvroMod40 tramiteSeguro = null;

        if (tramites != null && !tramites.isEmpty()) {
            for (DitTramite tramite : tramites) {
                Integer tipoTramite = Integer.valueOf(tramite.getDicTipoTramite()
                        .getCveIdTipoTramite().intValue());
                if ((tipoTramite.equals(TipoTramiteEnum.COMPRA_CONTINUACION_VOLUNTARIA.getCodigo())
                        || tipoTramite.equals(TipoTramiteEnum.RENOVACION_CONTINUACION_VOLUNTARIA.getCodigo()))
                        && tramite.getDitDetalleTramite() != null) {
                    try {
                    	
                  	
                        tramiteSeguro = JaxbUtil.unmarshaller(tramite.getDitDetalleTramite()
                                .getRefDatosTramiteXml(), TramiteSeguroIvroMod40.class);
                        tramiteSeguro.setTramiteId(tramite.getCveIdTramite());
                        corrigeNombreBeneficiarios(tramiteSeguro.getBeneficiarios());
                    } catch (ClassCastException e) {
                    	LOGGER.error("ClassCastException no es del tipo : "+ e.getMessage());
                    	
                    	try {
                    		TramiteSeguroIvro tramiteSeguroIvro = JaxbUtil.unmarshaller(tramite.getDitDetalleTramite()
                                    .getRefDatosTramiteXml(), TramiteSeguroIvro.class);
                    		
                            
                            LOGGER.info("Se realiza el tramite de otro tipo, se debe convertir.");
                        	
                            tramiteSeguro = convertirATramiteSeguroIvroMod40(tramiteSeguroIvro);
                            tramiteSeguro.setTramiteId(tramite.getCveIdTramite());
                            corrigeNombreBeneficiarios(tramiteSeguro.getBeneficiarios());
                            
                    	}catch (Exception e2) {
                            LOGGER.warn("Error al transformar el tramite de un seguro CVRO: ", e2);
                            LOGGER.error(
                            	       "Error inesperado procesando trámite CVRO. tramiteId={}, tipoTramite={}, excepcion={}",
                            	       tramite.getCveIdTramite(),
                            	       tipoTramite
                            	   );
                            e2.printStackTrace();
                        }
                    	
                    	
                    } catch (JAXBException e) {
                        LOGGER.error("Error al parsear el tramite de un seguro CVRO: ", e);
                        LOGGER.error(
                        	       "Error JAXB al convertir XML. tramiteId={}, tipoTramite={}",
                        	       tramite.getCveIdTramite(),
                        	       tipoTramite
                        	   );
                        
                    } catch (Exception e) {
                        LOGGER.error("Error al transformar el tramite de un seguro CVRO: ", e);
                        LOGGER.error(
                        	       "Error inesperado procesando trámite CVRO. tramiteId={}, tipoTramite={}, excepcion={}",
                        	       tramite.getCveIdTramite(),
                        	       tipoTramite
                        	   );
                        
                    }
                }
            }
        }

        return tramiteSeguro;
    }

    /**
     * Obtiene la lista de tramites a asoiar a un seguro ivro
     *
     * @param tramite el tramite a ser trnaformado en los datos necesarios apra
     * su correcta relacion de objeto
     * @return la lista de tramites a asociar al seguro
     */
    private static Set<DitTramite> generaTramite(TramiteSeguroIvro tramite) {
        Set<DitTramite> tramites = new HashSet<DitTramite>();
        if (tramite != null) {
            DitTramite ditTramite = new DitTramite();
            ditTramite.setCveIdTramite(tramite.getTramiteId());
            tramites.add(ditTramite);
        }
        return tramites;
    }

    /**
     * Genera los datos de una persona a partir de la informacion del objeto
     * persistido
     *
     * @param ditPersona la persona persitida
     * @return el objeto de transferencia con los datos de la persona
     */
    private static Fisica generaPersona(DitPersona ditPersona) {
        Fisica titular = new Fisica();
        titular.setIdPersona(ditPersona.getCveIdPersona());
        titular.setNombre(generaNombre(ditPersona.getNomNombre(),
                ditPersona.getNomPrimerApellido(), ditPersona.getNomSegundoApellido()));
        titular.setCurp(ditPersona.getCurp());
        titular.setRfc(ditPersona.getRfc());
        if (ditPersona.getDitAsignacionNsses() != null
                && ditPersona.getDitAsignacionNsses().size() == 1) {
            DitAsignacionNss asignacion = ditPersona.getDitAsignacionNsses().get(0);
            titular.setNss(asignacion.getNumNss());
        }

        return titular;
    }

    /**
     * Genera el objeto de persona con su id de persistencia
     *
     * @param persona la persona en modelo no persistente
     * @return la persona persistida
     */
    private static DitPersona generaPersona(Fisica persona) {
        DitPersona titular = new DitPersona();
        titular.setCveIdPersona(persona.getIdPersona());
        return titular;
    }

    /**
     * Concatena los nombres de una persona,
     *
     * @param nombre el nombre de la persona
     * @param primApellido el primer apellido de la persona
     * @param segApellido el segundo apellido de la persona
     * @return el nombre concatenado (Nombre PrimerApellido SegundoApellido)
     */
    private static String generaNombre(String nombre, String primApellido, String segApellido) {
        StringBuilder nombreCompleto = new StringBuilder(corrigeCadena(nombre));
        nombreCompleto.append(" ").append(corrigeCadena(primApellido)).append(" ")
                .append(corrigeCadena(segApellido));
        return nombreCompleto.toString();
    }

    /**
     * Regresa el valor de una cadena recibida, si es nula regresa vacio
     *
     * @param valor el valor a validar si es vacio
     * @return el contenido de la cadena, si es nula regresa vacio
     */
    public static String corrigeCadena(String valor) {
        return StringUtils.trimToEmpty(valor).replace("#", "Ñ");
    }

    /**
     * Convierte de un estado de seguro persistente a un modelo no persistente
     *
     * @param estado el estado del seguro
     * @return el estado no persistente
     */
    private static EstadoSeguroIvro generaEstadoSeguro(DicEstadoSeguro estado) {
        EstadoSeguroIvro estadoSeguro = new EstadoSeguroIvro();
        estadoSeguro.setDescripcion(estado.getDesEstadoSeguro());
        estadoSeguro.setIdEstadoSeguro(estado.getCveIdEstadoSeguro());
        return estadoSeguro;
    }

    /**
     * Convierte de un estado de seguro persistente a un modelo no persistente
     *
     * @param estado el estado del seguro
     * @return el estado no persistente
     */
    private static DicEstadoSeguro generaEstadoSeguro(EstadoSeguroIvro estado) {
        DicEstadoSeguro estadoSeguro = new DicEstadoSeguro();
        estadoSeguro.setCveIdEstadoSeguro(estado.getIdEstadoSeguro());
        return estadoSeguro;
    }

    /**
     * Genera una modalidad a patir de su representacion e en el modelo
     * persistente
     *
     * @param dicModalidad la modalidadad en el modelo persistente
     * @return la modalidad convertida
     */
    public static final Modalidad getModalidad(DicModalidad dicModalidad) {
        Modalidad modalidad = new Modalidad();
        modalidad.setIdModalidad(dicModalidad.getCveIdModalidad());
        modalidad.setNumModalidad(dicModalidad.getNumModalidad());
        modalidad.setDescripcion(dicModalidad.getDesNomModalidadCorto());
        return modalidad;
    }

    /**
     * Genera una modalidad a patir de su representacion e en el modelo
     * persistente
     *
     * @param modalidad la modalidadad en el modelo persistente
     * @return la modalidad convertida
     */
    public static final DicModalidad getDicModalidad(Modalidad modalidad) {
        DicModalidad dicModalidad = new DicModalidad();
        dicModalidad.setCveIdModalidad(modalidad.getIdModalidad());
        return dicModalidad;
    }

    /**
     * Genera una compa persistida a partir de su modelo xml
     *
     * @param compra la compra xml (con el id de la compra persistida)
     * @return el ibjeto de persistencia de una compra parala actualizacion
     */
    private static DitCompra getDitCompra(Compra compra) {
        DitCompra ditCompra = new DitCompra();
        ditCompra.setCveIdCompra(compra.getIdCompra());
        return ditCompra;
    }

    /**
     * Regresa una lista de modalidades dada un areglo con sus identificadores
     *
     * @param ids el arreglo con los identificadores de las modalidades
     * @return la lista de modalidades
     */
    public static final List<DicModalidad> generaModalidades(Long[] ids) {
        List<DicModalidad> modalidades = new ArrayList<DicModalidad>();
        for (Long id : ids) {
            DicModalidad modalidad = new DicModalidad();
            modalidad.setCveIdModalidad(id);
            modalidades.add(modalidad);
        }
        return modalidades;
    }

    /**
     * Obtiene la lista de estados vigentes o no vigentes
     *
     * @param vigentes indicador del tipo de estados que qeuremos
     * @return la lista de estados generados
     */
    public static final List<DicEstadoSeguro> estadosSeguro(boolean vigentes) {
        List<DicEstadoSeguro> estados = new ArrayList<DicEstadoSeguro>();
        if (vigentes) {
            estados.add(estadoSeguro(EstadoSeguroIvroEnum.ACTIVO));
            estados.add(estadoSeguro(EstadoSeguroIvroEnum.NUEVO));
        } else {
            estados.add(estadoSeguro(EstadoSeguroIvroEnum.CANCELADO_RISS));
            estados.add(estadoSeguro(EstadoSeguroIvroEnum.CONCLUIDO));
            estados.add(estadoSeguro(EstadoSeguroIvroEnum.VENCIDO));
            estados.add(estadoSeguro(EstadoSeguroIvroEnum.BAJA_POR_MORA));
            estados.add(estadoSeguro(EstadoSeguroIvroEnum.BAJA_A_SOLICITUD_ASEGURADO));
            estados.add(estadoSeguro(EstadoSeguroIvroEnum.BAJA_POR_REINGRESO_RO));
        }
        return estados;
    }

    /**
     * Obtiene un objeto de estado seguro por su enum
     *
     * @param estadoEnum enum que indica el id el estado
     * @return el objeto con su id de estado
     */
    public static final DicEstadoSeguro estadoSeguro(EstadoSeguroIvroEnum estadoEnum) {
        DicEstadoSeguro estado = new DicEstadoSeguro();
        estado.setCveIdEstadoSeguro(estadoEnum.getId());
        return estado;
    }

    /**
     * Genera una compra a partir de su modelo persisente
     *
     * @param ditCompra la compra a ser transformada
     * @return la compra en su modelo xml
     * @throws SUAException Errore s en la transformacion del objeto
     */
    public static final Compra generaCompra(DitCompra ditCompra) {
        LOGGER.debug("Transformando una compra persistida en su modelo xml");
        Compra compra = new Compra();
        compra.setFechaCompra(ditCompra.getFecRegistroAlta());
        compra.setFechaLimite(ditCompra.getFecLimitePago());
        compra.setIdCompra(ditCompra.getCveIdCompra());
        compra.setMonto(ditCompra.getNumMonto());
        compra.setIdCotizacion(ditCompra.getDitCotizacion() != null
                ? ditCompra.getDitCotizacion().getCveIdCotizacion() : null);
        compra.setEstadoCompra(new EstadoCompra());
        if (ditCompra.getDicFormaPgo() != null) {
            compra.setFormaPago(ditCompra.getDicFormaPgo().getCveIdFormaPago());

        }
        if (ditCompra.getDicEstadoCompra() != null) {
            long idEstado = ditCompra.getDicEstadoCompra().getCveIdEstadoCompra();
            compra.getEstadoCompra().setIdEstadoCompra(idEstado);
            compra.getEstadoCompra().setDescripcion(ditCompra.getDicEstadoCompra().getDesEstadoCompra());
        }
        return compra;
    }

    /**
     * Obtiene un objeto de modalidad seguro por su enum
     *
     * @param modalidadEnum enum que indica el id de la modalidad
     * @return el objeto con su id de modalidad
     */
    public static DicModalidad modalidadSeguro(ModalidadEnum modalidadEnum) {
        DicModalidad dicModalidad = new DicModalidad();
        dicModalidad.setCveIdModalidad(modalidadEnum.getId());
        return dicModalidad;
    }

    
    /**
     * Convierte un objeto DitDetalleTramite a TramiteSeguroIvro
     *
     * @param detalle
     * @return
     */
    public static TramiteSeguroIvro crearTramiteSeguroIvro(DitDetalleTramite detalle) {
        TramiteSeguroIvro tramiteSeguro = new TramiteSeguroIvro();
        try {
            tramiteSeguro = JaxbUtil.unmarshaller(detalle
                    .getRefDatosTramiteXml(), TramiteSeguroIvro.class);
        } catch (JAXBException e) {
            tramiteSeguro = null;
        }
        return tramiteSeguro;
    }
    
    /**
     * Convierte un objeto DitDetalleTramite a TramiteSeguroIvro
     *
     * @param detalle
     * @return
     */
    public static TramiteSeguroIvro validaTramiteSeguroIvro(DitDetalleTramite detalle) {
        TramiteSeguroIvro tramiteSeguro = new TramiteSeguroIvro();
        try {
            tramiteSeguro = JaxbUtil.unmarshaller(detalle
                    .getRefDatosTramiteXml(), TramiteSeguroIvro.class);
        } catch (JAXBException e) {
            tramiteSeguro = null;
        } catch (ClassCastException e) {
        	LOGGER.error("Error al parsear el tramite del seguro, tramite incorrecto1"+ e.getMessage());
        	
        	try {
				Tramite tramiteActual = JaxbUtil.unmarshaller(detalle
				        .getRefDatosTramiteXml(), Tramite.class);
				LOGGER.info("Si pudo recuperar el XML del detalle tramite incorrecto, es DitTramite y debe ser TramiteSeguroIvro");
				tramiteSeguro = new TramiteSeguroIvro();
				//regresa un resultado pero es incorrecto, para cacharlo
				tramiteSeguro.setTramiteId(-1L);
				
			} catch (JAXBException e1) {
				LOGGER.error("Error al parsear el tramite del seguro, tramite incorrecto2 "+ e.getMessage());
				tramiteSeguro = null;
			}                    	
        	
		}
        return tramiteSeguro;
    }

    public static TramiteSeguroIvro obtenerTramitePorSeguro(SeguroIvro seg) {
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
        return tramite;
    }

    private static TramiteSeguroIvroMod40 convertirATramiteSeguroIvroMod40(TramiteSeguroIvro origen) {

		if (origen == null) {
			return null;
		}

		TramiteSeguroIvroMod40 destino = new TramiteSeguroIvroMod40();

		destino.setCompra(origen.getCompra());
		destino.setCotizacion(origen.getCotizacion());
		destino.setFechaInicio(origen.getFechaInicio());
		destino.setFechaFin(origen.getFechaFin());
		destino.setModalidad(origen.getModalidad());
		destino.setBeneficiarios(origen.getBeneficiarios());
		destino.setRenovacion(origen.getRenovacion());
		destino.setIdSeguroAnterior(origen.getIdSeguroAnterior());
		destino.setCuetionarios(origen.getCuetionarios());
		destino.setAplicaCuestionario(origen.getAplicaCuestionario());
		destino.setNrpFisica(origen.getNrpFisica());
		destino.setSolicitante(origen.getSolicitante());
		destino.setDomicilioSeguro(origen.getDomicilioSeguro());
		destino.setDesdeExtranjero(origen.isDesdeExtranjero());
		destino.setSoloSolicitante(origen.isSoloSolicitante());
		destino.setRegistroPatronal(origen.getRegistroPatronal());
		
		return destino;
	}
}
