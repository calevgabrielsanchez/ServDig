package mx.gob.imss.cit.cda.service.correccion.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.imss.cit.cda.service.entity.CorreccionDatosAseguradoLocal;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.TareaBandeja;
import mx.gob.imss.cit.gestion.solicitud.flujo.service.interfaces.FlujoTrabajoRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.CorreccionDatos;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.DetalleCorreccionNss;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.DetalleNss;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.EstadoNegocioEnum;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.ObservacionesSubdelegacion;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.OrigenInformacion;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.ResumenCorrecion;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.TipoAclaracion;
import mx.gob.imss.ctirss.delta.model.enums.OrigenConsultaNssEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoRegularizacionSolicitudCDAEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;

@Stateless
public class CorreccionDatosUtil implements CoreccionDatosLocal {

	private final Logger LOGGER = LoggerFactory
			.getLogger(CorreccionDatosUtil.class);
        
        private static final String ENTRO_CONST = "Entro ";
        private static final String CUBETA_INFO = "cubetaInfo";
        private static final String SALIO = "Salio ";

	public String CURP = "CURP";
	public String APELLIDO_PATERNO = "APELLIDO PATERNO";
	public String APELLIDO_MATERNO = "APELLIDO MATERNO";
	public String NOMBRE = "NOMBRE";
	public String SEXO = "SEXO";
	public String FECHA_NACIMIENTO = "FECHA NACIMIENTO";
	public String LUGAR_NACIMIENTO = "LUGAR NACIMIENTO";
	public String CURP_HISTORICA = "CURP HISTORICA";
	public String DATOS_DOC_PROB = "DATOS DOC. PROB.";
	public String NACIONALIDAD = "NACIONALIDAD";

	@EJB
	CorreccionDatosAseguradoLocal correccionDatosAseguradoLocal;
	
	@EJB
	private FlujoTrabajoRemote flujoTrabajoBusiness;

	/**
	 * Crear los datos necesarios para enviar 
	 * la solicitud al autorizador 
	 * 
	 * @param solicitud
	 * @param usuario
	 */
	public Solicitud crearSolicitudPorAutorizar(Solicitud solicitud, String usuario, String asignado) {

		EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
		estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION
				.getCodigo());
		solicitud.setEstadoSolicitud(estadoSolicitud);
		solicitud.setTramites(crearTramitesPorAutorizar(solicitud.getTramites(),
				usuario, asignado));

		return solicitud;
	}
	
	/**
	 * Crear los datos necesarios para enviar 
	 * la solicitud al autorizador 
	 * 
	 * @param solicitud
	 * @param usuario
	 */
	public Solicitud crearSolicitudInformacionAdicional(Solicitud solicitud, String usuario, String asignado) {

				
		solicitud.setTramites(crearObservacionInformacionAdicional(solicitud.getTramites(),
				usuario, asignado));

		return solicitud;
	}
	
	public List<Tramite>  crearObservacionInformacionAdicional(List<Tramite> tramites,String usuario,String asignado){
		 List<Tramite> lstTramite = new ArrayList<Tramite>();		
		  for (Tramite tramitecda : tramites) {
	            TramiteCorreccionCurp tramite = (TramiteCorreccionCurp) tramitecda;
	            
	            EstadoTramite estadoTramite = new EstadoTramite();
	            estadoTramite.setDescripcion(EstadoNegocioEnum.obtenerDescripcionNegocio(EstadoTramiteEnum.EN_ESPERA_DERECHOHABIENTE_ATENDIDA.getCodigo()));
	            estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.EN_ESPERA_DERECHOHABIENTE_ATENDIDA.getCodigo());
	            tramite.setEstadoTramite(estadoTramite);
	            
	            LOGGER.info("Buscando tarea del tramite:        "+tramite.getTramiteId());
	            TareaBandeja tarea = flujoTrabajoBusiness
	    				.getTareaActivaPorIdTramite(tramite.getTramiteId());
	            if (tramite.getObservacionesSubdelegacion() == null) {
	                tramite.setObservacionesSubdelegacion(new ArrayList<ObservacionesSubdelegacion>());
	            }
                    
                    
                    String responsable = "";
                    if(tarea!=null){
                        
                        responsable = tarea.getInicioTramite().getParticipantes().get("Responsable");
//                    }else{
//                        responsable = tarea.getMensajeTarea().getAsignado();
                    }
                    
                    if(usuario != null && usuario.equals("")){
                        usuario = responsable;
                    }         
	            
	           LOGGER.info("responsable:        "+responsable);
                   LOGGER.info("usuario:        "+usuario);
	            tramite.getObservacionesSubdelegacion().add(armarObservacionPorAutorizar(usuario, responsable));
	            lstTramite.add(tramite);

	        }
		  return lstTramite;
	}
	
	
	 private ObservacionesSubdelegacion armarObservacionPorAutorizar(String usuario, String responsable){
	        ObservacionesSubdelegacion obSubdelegacion = new ObservacionesSubdelegacion();
	        obSubdelegacion.setDetalle(EstadoTramiteEnum.EN_ESPERA_DERECHOHABIENTE_ATENDIDA.getDescripcion());
	        obSubdelegacion.setResumen(EstadoTramiteEnum.EN_ESPERA_DERECHOHABIENTE_ATENDIDA.getDescripcion());
	        obSubdelegacion.setFechaActualizacion(new Date());
	        obSubdelegacion.setUsuario(usuario);
	        obSubdelegacion.setAsignado(responsable);
	        obSubdelegacion.setCveEstado(EstadoTramiteEnum.EN_ESPERA_DERECHOHABIENTE_ATENDIDA.getCodigo());
	        return obSubdelegacion;
	    }
	
	
    /**
     * Metodo para asignar el estado cancelado a los tramites
     */
    private List<Tramite> crearTramitesPorAutorizar (List<Tramite> tramites, String usuario, String asignado ){
        List<Tramite> lstTramite = new ArrayList<Tramite>();
        
        LOGGER.info("AGREGANDO OBSERVACIONES.......................: ");
        
        for (Tramite tramitecda : tramites) {
            TramiteCorreccionCurp tramite = (TramiteCorreccionCurp) tramitecda;
            
            EstadoTramite estadoTramite = new EstadoTramite();
            estadoTramite.setDescripcion(EstadoNegocioEnum.obtenerDescripcionNegocio(EstadoTramiteEnum.EN_ESPERA_AUTORIZACION.getCodigo()));
            estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.EN_ESPERA_AUTORIZACION.getCodigo());
            tramite.setEstadoTramite(estadoTramite);
            
            LOGGER.info("Buscando tarea del tramite:        "+tramite.getTramiteId());
            TareaBandeja tarea = flujoTrabajoBusiness
    				.getTareaActivaPorIdTramite(tramite.getTramiteId());
            if (tramite.getObservacionesSubdelegacion() == null) {
                tramite.setObservacionesSubdelegacion(new ArrayList<ObservacionesSubdelegacion>());
            }
            
           
            tramite.getObservacionesSubdelegacion().add(crearObservacionPorAutorizar(usuario,  tarea!=null?tarea.getMensajeTarea().getAsignado()
            		:"RESPONSABLE"));
            lstTramite.add(tramite);

        }
        
        return lstTramite;
        
    }
    
    /**
     * Crear Bitacora de estados para avanzar
     * a la autorizacion
     * 
     * @param usuario
     * @param responsable
     * @return obSubdelegacion
     */
    private ObservacionesSubdelegacion crearObservacionPorAutorizar(String usuario, String responsable){
        ObservacionesSubdelegacion obSubdelegacion = new ObservacionesSubdelegacion();
        obSubdelegacion.setDetalle("PENDIENTE POR AUTORIZAR");
        obSubdelegacion.setResumen("PENDIENTE POR AUTORIZAR");
        obSubdelegacion.setFechaActualizacion(new Date());
        obSubdelegacion.setUsuario(usuario);
        obSubdelegacion.setAsignado(responsable);
        obSubdelegacion.setCveEstado(EstadoTramiteEnum.EN_ESPERA_AUTORIZACION.getCodigo());
        return obSubdelegacion;
    }

	public ResumenCorrecion obtenerResumenorreccion(OrigenInformacion renapo,
			CorreccionDatos correccionDatos) {
		List<DetalleNss> detalleCertificador = new ArrayList<DetalleNss>();
		List<DetalleNss> detalleNoCorresponde = new ArrayList<DetalleNss>();
		Map<String, DetalleCorreccionNss> detalleAsociado = new HashMap<String, DetalleCorreccionNss>();
		ResumenCorrecion resumenCorrecion = new ResumenCorrecion();

		LOGGER.debug("Entro obtenerResumenorreccion");

		for (int index = 0; index <= correccionDatos.getListaNss().size(); index++) {
			LOGGER.debug("Entro obtenerResumenorreccion linea 56");

			if (correccionDatos.getListaNss().get(index).getTipoNss()
					.isCertificador()) {
				LOGGER.debug("Entro certificador");
				detalleCertificador = validaInfoOrigen(renapo, correccionDatos,
						index);
				LOGGER.debug("Salio certificador");
			}

			else if (correccionDatos.getListaNss().get(index).getTipoNss()
					.isAsociado()) {
				LOGGER.debug("Entro asociado");
				if (detalleCorreccion(renapo,
						correccionDatos.getListaNss().get(index).getBdtu())
						.size() > 0) {
					LOGGER.debug(ENTRO_CONST
							+ OrigenConsultaNssEnum.BDTU.getDescripcion());
					detalleAsociado
							.put(OrigenConsultaNssEnum.BDTU.getDescripcion(),
									setDetalleOrigenDatos(
											OrigenConsultaNssEnum.BDTU
													.getDescripcion(),
											correccionDatos.getListaNss()
													.get(index).getBdtu()));
					LOGGER.debug(SALIO
							+ OrigenConsultaNssEnum.BDTU.getDescripcion());

				}

				if (detalleCorreccion(renapo,
						correccionDatos.getListaNss().get(index).getCanase())
						.size() > 0) {
					LOGGER.debug(ENTRO_CONST
							+ OrigenConsultaNssEnum.CANASE.getDescripcion());
					detalleAsociado
							.put(OrigenConsultaNssEnum.CANASE.getDescripcion(),
									setDetalleOrigenDatos(
											OrigenConsultaNssEnum.BDTU
													.getDescripcion(),
											correccionDatos.getListaNss()
													.get(index).getBdtu()));
					LOGGER.debug(SALIO
							+ OrigenConsultaNssEnum.CANASE.getDescripcion());

				}
				if (detalleCorreccion(renapo,
						correccionDatos.getListaNss().get(index).getCizUno())
						.size() > 0) {
					LOGGER.debug(ENTRO_CONST
									+ OrigenConsultaNssEnum.SINDO_CIZ1
											.getDescripcion());
					detalleAsociado.put(
							OrigenConsultaNssEnum.SINDO_CIZ1.getDescripcion(),
							setDetalleOrigenDatos(
									OrigenConsultaNssEnum.SINDO_CIZ1
											.getDescripcion(), correccionDatos
											.getListaNss().get(index)
											.getCizUno()));
					LOGGER.debug(SALIO
									+ OrigenConsultaNssEnum.SINDO_CIZ1
											.getDescripcion());

				}

				if (detalleCorreccion(renapo,
						correccionDatos.getListaNss().get(index).getCizDos())
						.size() > 0) {
					LOGGER.debug(ENTRO_CONST
									+ OrigenConsultaNssEnum.SINDO_CIZ2
											.getDescripcion());
					detalleAsociado.put(
							OrigenConsultaNssEnum.SINDO_CIZ2.getDescripcion(),
							setDetalleOrigenDatos(
									OrigenConsultaNssEnum.SINDO_CIZ2
											.getDescripcion(), correccionDatos
											.getListaNss().get(index)
											.getCizDos()));
					LOGGER.debug(SALIO
									+ OrigenConsultaNssEnum.SINDO_CIZ2
											.getDescripcion());

				}
				if (detalleCorreccion(renapo,
						correccionDatos.getListaNss().get(index).getCizTres())
						.size() > 0) {
					LOGGER.debug(ENTRO_CONST
									+ OrigenConsultaNssEnum.SINDO_CIZ3
											.getDescripcion());
					detalleAsociado.put(
							OrigenConsultaNssEnum.SINDO_CIZ3.getDescripcion(),
							setDetalleOrigenDatos(
									OrigenConsultaNssEnum.SINDO_CIZ3
											.getDescripcion(), correccionDatos
											.getListaNss().get(index)
											.getCizTres()));
					LOGGER.debug(SALIO
									+ OrigenConsultaNssEnum.SINDO_CIZ1
											.getDescripcion());

				}
				if (detalleCorreccion(renapo,
						correccionDatos.getListaNss().get(index).getHistorico())
						.size() > 0) {
					LOGGER.debug(ENTRO_CONST
							+ OrigenConsultaNssEnum.HISTORICO_CENTRAL
									.getDescripcion());
					detalleAsociado.put(
							OrigenConsultaNssEnum.HISTORICO_CENTRAL
									.getDescripcion(),
							setDetalleOrigenDatos(
									OrigenConsultaNssEnum.HISTORICO_CENTRAL
											.getDescripcion(), correccionDatos
											.getListaNss().get(index)
											.getHistorico()));
					LOGGER.debug(SALIO
							+ OrigenConsultaNssEnum.HISTORICO_CENTRAL
									.getDescripcion());

				}

				LOGGER.debug("salio asociado");
			}

			else {
				LOGGER.debug("Entro no pertenece");
				DetalleNss noCorresponde = new DetalleNss();
				noCorresponde.setNss(correccionDatos.getListaNss().get(index)
						.getNss());
				noCorresponde
						.setCorreccionesNss(obtenerRegulaciones(correccionDatos
								.getListaNss().get(index).getTipoAclaracion()));
				detalleNoCorresponde.add(noCorresponde);
				LOGGER.debug("Salio no pertenece");

			}
		}
		resumenCorrecion.setCertificador(detalleCertificador);
		resumenCorrecion.setAsociados(detalleAsociado);
		LOGGER.debug("Salio obtenerResumenorreccion");
		return null;
	}

	public List<DetalleNss> validaInfoOrigen(OrigenInformacion renapo,
			CorreccionDatos correccionDatos, Integer index) {
		List<DetalleNss> detalleNss = new ArrayList<DetalleNss>();
		DetalleNss detalle = new DetalleNss();
		LOGGER.debug("Entro validaInfoOrigen");

		detalle.setNss(correccionDatos.getListaNss().get(index).getNss());
		detalle.setCorreccionesNss(obtenerRegulaciones(correccionDatos
				.getListaNss().get(index).getTipoAclaracion()));

		if (detalleCorreccion(renapo,
				correccionDatos.getListaNss().get(index).getBdtu()).size() > 0) {
			LOGGER.debug("Entro Line 143");
			detalle.getDetalleCoreeccion().addAll(
					detalleCorreccion(renapo, correccionDatos.getListaNss()
							.get(index).getBdtu()));
		}
		if (detalleCorreccion(renapo,
				correccionDatos.getListaNss().get(index).getCanase()).size() > 0) {
			LOGGER.debug("Entro Line 147");
			detalle.getDetalleCoreeccion().addAll(
					detalleCorreccion(renapo, correccionDatos.getListaNss()
							.get(index).getCanase()));
		}
		if (detalleCorreccion(renapo,
				correccionDatos.getListaNss().get(index).getCizUno()).size() > 0) {
			LOGGER.debug("Entro Line 151");
			detalle.getDetalleCoreeccion().addAll(
					detalleCorreccion(renapo, correccionDatos.getListaNss()
							.get(index).getCizUno()));
		}
		if (detalleCorreccion(renapo,
				correccionDatos.getListaNss().get(index).getCizDos()).size() > 0) {
			LOGGER.debug("Entro Line 155");
			detalle.getDetalleCoreeccion().addAll(
					detalleCorreccion(renapo, correccionDatos.getListaNss()
							.get(index).getCizDos()));
		}
		if (detalleCorreccion(renapo,
				correccionDatos.getListaNss().get(index).getCizTres()).size() > 0) {
			LOGGER.debug("Entro Line 159");
			detalle.getDetalleCoreeccion().addAll(
					detalleCorreccion(renapo, correccionDatos.getListaNss()
							.get(index).getCizTres()));
		}
		if (detalleCorreccion(renapo,
				correccionDatos.getListaNss().get(index).getHistorico()).size() > 0) {
			LOGGER.debug("Entro Line 163");
			detalle.getDetalleCoreeccion().addAll(
					detalleCorreccion(renapo, correccionDatos.getListaNss()
							.get(index).getHistorico()));
		}
		detalleNss.add(detalle);
		LOGGER.debug("salio asociado con tamaño" + detalleNss.size());
		return detalleNss;
	}

	// @SuppressWarnings("unused")
	public List<DetalleNss> setCubetaInformacion(OrigenInformacion renapo,
			CorreccionDatos correccionDatos, DetalleNss detalle) {
		// LOGGER.debug("Entro setCubetaInformacion");
		// List<DetalleNss>tipoDetalle =new ArrayList<DetalleNss>();
		// for(int index=0;index<=correccionDatos.getListaNss().size();index++){
		// LOGGER.debug(index + " # # # # # # " +
		// correccionDatos.getListaNss().get(index).getNss());
		// detalle.setNss(correccionDatos.getListaNss().get(index).getNss());
		// detalle.setCorreccionesNss(obtenerRegulaciones(correccionDatos.getListaNss().get(index).getTipoAclaracion()));
		// if(correccionDatos.getListaNss().get(index).getTipoNss().isCertificador()){
		// if(detalleCorreccion(renapo,
		// correccionDatos.getListaNss().get(index).getBdtu())!=null)
		// detalle.getDetalleCoreeccion().addAll(detalleCorreccion(renapo,
		// correccionDatos.getListaNss().get(index).getBdtu()));
		// if(detalleCorreccion(renapo,
		// correccionDatos.getListaNss().get(index).getCanase())!=null)
		// detalle.getDetalleCoreeccion().addAll(detalleCorreccion(renapo,
		// correccionDatos.getListaNss().get(index).getCanase()));
		// if(detalleCorreccion(renapo,
		// correccionDatos.getListaNss().get(index).getCizUno())!=null)
		// detalle.getDetalleCoreeccion().addAll(detalleCorreccion(renapo,
		// correccionDatos.getListaNss().get(index).getCizUno()));
		// if(detalleCorreccion(renapo,
		// correccionDatos.getListaNss().get(index).getCizDos())!=null)
		// detalle.getDetalleCoreeccion().addAll(detalleCorreccion(renapo,
		// correccionDatos.getListaNss().get(index).getCizDos()));
		// if(detalleCorreccion(renapo,
		// correccionDatos.getListaNss().get(index).getCizTres())!=null)
		// detalle.getDetalleCoreeccion().addAll(detalleCorreccion(renapo,
		// correccionDatos.getListaNss().get(index).getCizTres()));
		// if(detalleCorreccion(renapo,
		// correccionDatos.getListaNss().get(index).getHistorico())!=null)
		// detalle.getDetalleCoreeccion().addAll(detalleCorreccion(renapo,
		// correccionDatos.getListaNss().get(index).getHistorico()));
		//
		// tipoDetalle.add(detalle);
		// LOGGER.debug("tamaño tipoDetalle "+ tipoDetalle.size());
		// LOGGER.debug("salio certificador");
		//
		// }
		// else
		// if(correccionDatos.getListaNss().get(index).getTipoNss().isAsociado()){
		//
		//
		//
		// }
		// else
		// if(correccionDatos.getListaNss().get(index).getTipoNss().isCorresOtraPersona()){
		//
		// }else{
		//
		// }
		//
		// }
		// LOGGER.debug("salio detalleCorreccion");
		return null;
	}

	public List<DetalleCorreccionNss> detalleCorreccion(
			OrigenInformacion renapo, OrigenInformacion cubetaInformacion) {
		LOGGER.debug("Entro detalleCorreccion");

		List<DetalleCorreccionNss> listDetalle = new ArrayList<DetalleCorreccionNss>();

		if (compararDato(renapo.getCurp(), cubetaInformacion.getCurp(),
				cubetaInformacion.getTipoFuente(), CURP) != null) {
			listDetalle.add(compararDato(renapo.getCurp(),
					cubetaInformacion.getCurp(),
					cubetaInformacion.getTipoFuente(), CURP));
		}
		if (compararDato(renapo.getApellidoPaterno(),
				cubetaInformacion.getApellidoPaterno(),
				cubetaInformacion.getTipoFuente(), APELLIDO_PATERNO) != null)
			listDetalle.add(compararDato(renapo.getApellidoPaterno(),
					cubetaInformacion.getApellidoPaterno(),
					cubetaInformacion.getTipoFuente(), APELLIDO_PATERNO));
		if (compararDato(renapo.getApellidoMaterno(),
				cubetaInformacion.getApellidoMaterno(),
				cubetaInformacion.getTipoFuente(), APELLIDO_MATERNO) != null)
			listDetalle.add(compararDato(renapo.getApellidoMaterno(),
					cubetaInformacion.getApellidoMaterno(),
					cubetaInformacion.getTipoFuente(), APELLIDO_MATERNO));
		if (compararDato(renapo.getNombre(), cubetaInformacion.getNombre(),
				cubetaInformacion.getTipoFuente(), NOMBRE) != null)
			listDetalle.add(compararDato(renapo.getNombre(),
					cubetaInformacion.getNombre(),
					cubetaInformacion.getTipoFuente(), NOMBRE));
		if (compararDato(renapo.getSexo(), cubetaInformacion.getSexo(),
				cubetaInformacion.getTipoFuente(), SEXO) != null)
			listDetalle.add(compararDato(renapo.getSexo(),
					cubetaInformacion.getSexo(),
					cubetaInformacion.getTipoFuente(), SEXO));
		if (compararDato(renapo.getFechaNacimiento(),
				cubetaInformacion.getFechaNacimiento(),
				cubetaInformacion.getTipoFuente(), FECHA_NACIMIENTO) != null)
			listDetalle.add(compararDato(renapo.getFechaNacimiento(),
					cubetaInformacion.getFechaNacimiento(),
					cubetaInformacion.getTipoFuente(), FECHA_NACIMIENTO));
		if (compararDato(renapo.getLugarNacimiento(),
				cubetaInformacion.getLugarNacimiento(),
				cubetaInformacion.getTipoFuente(), LUGAR_NACIMIENTO) != null)
			listDetalle.add(compararDato(renapo.getLugarNacimiento(),
					cubetaInformacion.getLugarNacimiento(),
					cubetaInformacion.getTipoFuente(), LUGAR_NACIMIENTO));
		// if(compararDato(renapo.getCurp(),cubetaInformacion.getCurp(),
		// cubetaInformacion.getTipoFuente(), CURP_HISTORICA)!=null)
		// listDetalle.add(compararDato(renapo.getCurpsHistoricas(),cubetaInformacion.getCurpsHistoricas(),
		// cubetaInformacion.getTipoFuente(), CURP_HISTORICA));
		if (compararDato(renapo.getDatosDocumentoProbatorio(),
				cubetaInformacion.getDatosDocumentoProbatorio(),
				cubetaInformacion.getTipoFuente(), DATOS_DOC_PROB) != null)
			listDetalle.add(compararDato(renapo.getDatosDocumentoProbatorio(),
					cubetaInformacion.getDatosDocumentoProbatorio(),
					cubetaInformacion.getTipoFuente(), DATOS_DOC_PROB));
		// if(compararDato(renapo.getCurp(),cubetaInformacion.getCurp(),
		// cubetaInformacion.getTipoFuente(), NACIONALIDAD)!=null)
		// listDetalle.add(compararDato(renapo.getNacionalidad(),cubetaInformacion.getNacionalidad(),
		// cubetaInformacion.getTipoFuente(), NACIONALIDAD));
		LOGGER.debug("tamaño lista" + listDetalle.size());
		return listDetalle;

	}

	public DetalleCorreccionNss compararDato(String renapoInfo,
			String cubetaInfo, String tipoFuente, String tipoDato) {
		LOGGER.debug("Entro compararDato");
		if (cubetaInfo == null) {
			return setDatosDetalle(tipoFuente, tipoDato, null, renapoInfo);
		} else if (!renapoInfo.equals(cubetaInfo)) {
			return setDatosDetalle(tipoFuente, tipoDato, cubetaInfo, renapoInfo);
		}
		return null;
	}

	public DetalleCorreccionNss setDatosDetalle(String fuente, String tipoDato,
			String cubetaInfo, String renapoInfo) {
		LOGGER.debug("Entro setDatosDetalle");
		DetalleCorreccionNss detalle = new DetalleCorreccionNss();
		// LOGGER.debug("fuente");
		detalle.setOrigenDato("fuente");

		// LOGGER.debug("tipoDato");
		detalle.setDatos("tipoDato");
		if (cubetaInfo != null) {
			if (CURP.equals(tipoDato)) {
				detalle.setCurp(CUBETA_INFO);
			}
			if (NOMBRE.equals(tipoDato)) {
				detalle.setNombre(CUBETA_INFO);
			}
			if (APELLIDO_PATERNO.equals(tipoDato)) {
				detalle.setApellidoPaterno(CUBETA_INFO);
			}
			if (APELLIDO_MATERNO.equals(tipoDato)) {
				detalle.setApellidoMaterno(CUBETA_INFO);
			}
			if (FECHA_NACIMIENTO.equals(tipoDato)) {
				detalle.setFechaNacimiento(CUBETA_INFO);
			}
			if (SEXO.equals(tipoDato)) {
				detalle.setSexo(CUBETA_INFO);
			}
		}

		return detalle;

	}

	public DetalleCorreccionNss setDetalleOrigenDatos(String fuente,
			OrigenInformacion dato) {
		LOGGER.debug("Entro setDetalleOrigenDatos");
		DetalleCorreccionNss detalle = new DetalleCorreccionNss();
		detalle.setOrigenDato(fuente);

		detalle.setDatos("");

		detalle.setCurp(dato.getCurp());
		detalle.setNombre(dato.getNombre());
		detalle.setApellidoPaterno(dato.getApellidoPaterno());
		detalle.setApellidoMaterno(dato.getApellidoMaterno());
		detalle.setFechaNacimiento(dato.getFechaNacimiento());
		detalle.setSexo(dato.getSexo());

		return detalle;

	}

	public String obtenerRegulaciones(TipoAclaracion tipoAclaracion) {
		LOGGER.debug("Entro obtenerRegulaciones");
		                
		StringBuffer regulacion = new StringBuffer();
		if (tipoAclaracion.isCanceladoDup()) {
			if (regulacion != null && !regulacion.equals("")) {
				regulacion.append(", ");
			}
			regulacion.append(TipoRegularizacionSolicitudCDAEnum.DUPLICIDAD
					.getDescripcion());
		}
		if (tipoAclaracion.isCorreccionEstadis()) {
			if (regulacion != null && !regulacion.equals("")) {
				regulacion.append(", ");
			}
			regulacion
					.append(TipoRegularizacionSolicitudCDAEnum.CORRECION_DATOS_BASICOS
							.getDescripcion());
		}
		if (tipoAclaracion.isCorreccionNombre()) {
			if (regulacion != null && !regulacion.equals("")) {
				regulacion.append(", ");
			}
			regulacion
					.append(TipoRegularizacionSolicitudCDAEnum.CORRECION_DATOS_BASICOS
							.getDescripcion());
		}
		if (tipoAclaracion.isHomonimio()) {
			if (regulacion != null && !regulacion.equals("")) {
				regulacion.append(", ");
			}
			regulacion.append(TipoRegularizacionSolicitudCDAEnum.HOMONIMIA
					.getDescripcion());
		}
		if (tipoAclaracion.isNoExisteCanase()) {
			if (regulacion != null && !regulacion.equals("")) {
				regulacion.append(", ");
			}
			regulacion.append(TipoRegularizacionSolicitudCDAEnum.CUENTA_ILOGICA
					.getDescripcion());
		}
		if (tipoAclaracion.isOtroAsegurado()) {
			if (regulacion != null && !regulacion.equals("")) {
				regulacion.append(", ");
			}
			regulacion.append(TipoRegularizacionSolicitudCDAEnum.DUPLICIDAD
					.getDescripcion());
		}

		return regulacion.toString();
	}

}
