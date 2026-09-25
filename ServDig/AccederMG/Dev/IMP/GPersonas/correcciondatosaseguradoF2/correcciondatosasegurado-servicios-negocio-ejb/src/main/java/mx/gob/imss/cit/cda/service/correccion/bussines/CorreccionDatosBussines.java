package mx.gob.imss.cit.cda.service.correccion.bussines;

import mx.gob.imss.cit.cda.service.autorizar.utility.AutorizarSolicitudUtilityLocal;
import mx.gob.imss.cit.cda.service.correccion.util.CoreccionDatosLocal;
import mx.gob.imss.cit.cda.service.cuentaindividual.entity.MovimientoAclaracionLocal;
import mx.gob.imss.cit.cda.service.entity.CorreccionDatosAseguradoLocal;
import mx.gob.imss.cit.cda.service.entity.MovimientoCuentaIndividualLocal;
import mx.gob.imss.cit.cda.service.interfaces.CorreccionDatosRemote;
import mx.gob.imss.cit.cda.service.utility.CorreccionDatosAseguradoUtilityLocal;
import mx.gob.imss.cit.cda.service.utility.SeparacionPersonasUtilityLocal;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.EstadoTareaUsuarioNoValidoException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.NoExisteTareaUsuarioException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.NoExisteTransicionParaTareaUsuarioException;
import mx.gob.imss.cit.gestion.solicitud.flujo.exception.TereaSinUsuarioAsignadoException;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.MensajeTarea;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.TareaBandeja;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.enums.TipoTransicionEnum;
import mx.gob.imss.cit.gestion.solicitud.flujo.service.interfaces.FlujoTrabajoRemote;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.NssRelacionadoVariasPersonasException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonasNoLocalizadasException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TramiteNoEncontradoException;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.ServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.SolicitudBusinessRemote;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.CorreccionDatos;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.EstadoNegocioEnum;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.ObservacionesSubdelegacion;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.OrigenInformacion;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.ResumenCorrecion;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.TipoAclaracion;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.TipoNss;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.TramiteNss;
import mx.gob.imss.ctirss.delta.model.enums.OrigenCapturaCDAEnum;
import mx.gob.imss.ctirss.delta.model.enums.OrigenConsultaNssEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoEstadoMovimientoEnviadoSindoEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoNSSAclaracionEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoNSSCorreccionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.nss.CorreccionNSS;
import mx.gob.imss.ctirss.delta.model.gestion.nss.MovAclaracionNss;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionCurp;
import mx.gob.imss.ctirss.delta.persistence.DicTipoNssAclaracion;
import mx.gob.imss.ctirss.delta.persistence.DicTipoTramCorreccionNss;
import mx.gob.imss.ctirss.delta.persistence.DitBitSeparacionPersonas;
import mx.gob.imss.ctirss.delta.persistence.DitCorreccionCtaIndCda;
import mx.gob.imss.ctirss.delta.persistence.DitDetalleNss;
import mx.gob.imss.ctirss.delta.persistence.DitMovAclaracionNssCda;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Stateless(name = "correccionDatosBusiness", mappedName = "correccionDatosBusiness")
public class CorreccionDatosBussines extends AbstractServiceUtility implements CorreccionDatosRemote {

	@EJB
	private CorreccionDatosAseguradoLocal correccionDatosAseguradoLocal;

	@EJB
	private CorreccionDatosAseguradoUtilityLocal correccionAseguradoUtilityLocal;

	@EJB
	private ServiceBusinessRemote serviceBusiness;

	@EJB
	private CoreccionDatosLocal correccionDatosUtil;

	@EJB
	private FlujoTrabajoRemote flujoTrabajoBusiness;

	@EJB
	private SolicitudBusinessRemote solicitudBusiness;

	@EJB
	private MovimientoAclaracionLocal movimientoAclaracionLocal;

	@EJB
	private SeparacionPersonasUtilityLocal utility;

	@EJB
	private MovimientoCuentaIndividualLocal movimientoCuentaIndividualEntity;

	@EJB
	private AutorizarSolicitudUtilityLocal autorizarSolicitudUtility;

    @EJB(name = "personaFisicaServiceBusiness", mappedName = "personaFisicaServiceBusiness")
    private PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;

	/**
	 * Obtener fuentes de informacion de los nss incolucrados en un solicitud
	 * 
	 * @param solicitud
	 * @return CorreccionDatos
	 */
	public CorreccionDatos obtenerFuentesDatos(Solicitud solicitud) {
		CorreccionDatos correccion = new CorreccionDatos();
		List<TramiteNss> listaNss = new ArrayList<TramiteNss>();
		TramiteNss tramiteNss = new TramiteNss();

		log.debug(" --CDA-- OBTENIENDO FUENTES DE INFORMACION PARA LA SOLIICTUD: "
				+ solicitud.getNoFolioSolicitud());

		if (solicitud.getTramites() != null) {

			TramiteCorreccionCurp tramite = (TramiteCorreccionCurp) solicitud
					.getTramites().get(0);

			for (CorreccionNSS nss : tramite.getListaNssCorreccion()) {
				log.debug(" --CDA-- CUBETA   NSS " + nss.getNss());
				tramiteNss = new TramiteNss();
				tramiteNss.setNss(nss.getNss());

				List<Fisica> personasFuenteNSS = serviceBusiness
						.getAseguradoByNSSLegadosyBDTU(nss.getNss(), true);
				tramiteNss.setCanase(obtenerInformacionCubetas(
						personasFuenteNSS, OrigenConsultaNssEnum.CANASE));
				tramiteNss.setCizUno(obtenerInformacionCubetas(
						personasFuenteNSS, OrigenConsultaNssEnum.SINDO_CIZ1));
				tramiteNss.setCizDos(obtenerInformacionCubetas(
						personasFuenteNSS, OrigenConsultaNssEnum.SINDO_CIZ2));
				tramiteNss.setCizTres(obtenerInformacionCubetas(
						personasFuenteNSS, OrigenConsultaNssEnum.SINDO_CIZ3));
				tramiteNss.setHistorico(obtenerInformacionCubetas(
						personasFuenteNSS,
						OrigenConsultaNssEnum.HISTORICO_CENTRAL));
				tramiteNss.setBdtu(obtenerInformacionCubetas(personasFuenteNSS,
						OrigenConsultaNssEnum.BDTU));

				/**
				 * Generar el tipo de NSS para el front
				 */
				tramiteNss.setIdTipoNss(nss.getIdTipoNss());
				tramiteNss.setTipoNss(generarTipoNss(nss.getIdTipoNss()));

				/**
				 * Obtener las aclaraciones y setearlas
				 * 
				 **/
				tramiteNss.setTipoAclaracion(generarTipoAclaracion(nss
						.getListaAclaraciones() != null ? nss
						.getListaAclaraciones()
						: new ArrayList<MovAclaracionNss>()));

				
				/**
				 * Obtener ORIGEN nss
				 * 
				 **/
				tramiteNss.setOrigen(OrigenCapturaCDAEnum.getOrigenbyClave(nss.getOrigen()).getDescripcion());
				
				listaNss.add(tramiteNss);
				correccion.setFolioSolicitud(solicitud.getNoFolioSolicitud());
			}

		}

		correccion.setListaNss(listaNss);

		return correccion;
	}
	
	
	
	

	/**
	 * Buscar el origen de datos indicado apartir de una lista de personas
	 * fisicas
	 * 
	 * @param personasFuenteNSS
	 * @param origen
	 * @return OrigenInformacion
	 */
	private OrigenInformacion obtenerInformacionCubetas(List<Fisica> personasFuenteNSS, OrigenConsultaNssEnum origen) {

		String entidadFederativa = "";
		OrigenInformacion cubeta = new OrigenInformacion();
		log.info("BUSCANDO CUBETA------" + origen.getDescripcion());
		cubeta.setTipoFuente(origen.getDescripcion());
		for (Fisica fisica : personasFuenteNSS) {
			log.info("size Identificadores()" + fisica.getIdentificadores().size());
			if (fisica.getIdentificadores() != null) {
				log.info("fisica.getIdentificadores()" + fisica.getIdentificadores().get(0).getIdentificadora());
				if (origen.getClave() == fisica.getIdentificadores().get(0).getIdIdentificador()) {
					cubeta = new OrigenInformacion();
					cubeta.setNombre(fisica.getNombre() != null ? fisica.getNombre().trim() : "");
					cubeta.setApellidoPaterno(fisica.getPrimerApellido() != null ? fisica.getPrimerApellido().trim() : "");
					cubeta.setApellidoMaterno(fisica.getSegundoApellido() != null ? fisica.getSegundoApellido().trim() : "");
					cubeta.setCurp(fisica.getCurp() != null ? fisica.getCurp().trim() : "");
					cubeta.setFechaNacimiento(fisica.getFechaNacimientoFormateada());
					cubeta.setCurpsHistoricas(fisica.getCurpsHistoricas() != null ? fisica.getCurpsHistoricas().toString() : "");
					cubeta.setSexo((fisica.getSexo() != null && fisica.getSexo().getDescripcion() != null) ? fisica.getSexo().getDescripcion().trim() : "");
					entidadFederativa = (fisica.getLugarNacimiento() != null && fisica.getLugarNacimiento().getNombre() != null) ? fisica.getLugarNacimiento().getNombre().trim() : "";
					entidadFederativa = Normalizer.normalize(entidadFederativa, Normalizer.Form.NFD);
					entidadFederativa = entidadFederativa.replaceAll("[^\\p{ASCII}]", "");
					log.info("EntidadFederativa------" + entidadFederativa);
					if (entidadFederativa.equals("CIUDAD DE MEXICO")) {
						entidadFederativa = "DISTRITO FEDERAL";
					}
					log.info("EntidadFederativa Actualizada------" + entidadFederativa);
					cubeta.setLugarNacimiento(entidadFederativa);
					cubeta.setIdlugarNacimiento(fisica.getLugarNacimiento().getClave());
					cubeta.setNacionalidad((fisica.getPais() != null) ? fisica.getPais().getNacionalidad().trim() : "");
					break;
				}
			}
		}
		return cubeta;
	}

	/**
	 * Metodo que genera el objeto que identifica el tipo de nss
	 * 
	 * @param cveIdTipoNss
	 * @return tiponss
	 */
	private TipoNss generarTipoNss(Long cveIdTipoNss) {
		TipoNss tipoNss = new TipoNss();
		log.debug("generando tipo NSS :" + cveIdTipoNss);
		if (cveIdTipoNss != null) {
			if (TipoNSSCorreccionEnum.CERTIFICADOR.getId().equals(cveIdTipoNss)) {
				tipoNss.setCertificador(true);
				tipoNss.setTipo("certificador");
			} else if (TipoNSSCorreccionEnum.ASOCIADO_AL_CERTIFICADOR.getId()
					.equals(cveIdTipoNss)) {
				tipoNss.setAsociado(true);
				tipoNss.setTipo("asociado");
			} else if (TipoNSSCorreccionEnum.CORRESPONDE_A_OTRA_PERSONA.getId()
					.equals(cveIdTipoNss)) {
				tipoNss.setCorresOtraPersona(true);
				tipoNss.setTipo("corresOtraPersona");
			} else if (TipoNSSCorreccionEnum.NO_EXISTE_EN_CANASE.getId()
					.equals(cveIdTipoNss)) {
				tipoNss.setNoExisteCanase(true);
				tipoNss.setTipo("noExisteCanase");
			}
		}

		return tipoNss;
	}

  
  private void generarTipoAclaracionStep01(MovAclaracionNss aclaracion, TipoAclaracion tipoAc ){
    if (TipoNSSAclaracionEnum.CORRESPONDE_A_OTRO_ASEGURADO.getId().equals(aclaracion.getIdTipoNssAclaracion())) {
				tipoAc.setOtroAsegurado(true);
			} else if (TipoNSSAclaracionEnum.CORRESPONDE_A_UN_HOMONIMO.getId().equals(aclaracion.getIdTipoNssAclaracion())) {
				tipoAc.setHomonimio(true);
			} else if (TipoNSSAclaracionEnum.NO_EXISTE_EN_CANASE.getId().equals(aclaracion.getIdTipoNssAclaracion())) {
				tipoAc.setNoExisteCanase(true);
			}
			else if (TipoNSSAclaracionEnum.CUENTA_ILOGICA.getId().equals(aclaracion.getIdTipoNssAclaracion())) {
				tipoAc.setCuentaIlogica(true);
			}
			else if (TipoNSSAclaracionEnum.BLANQUEAMIENTO_CURP.getId().equals(aclaracion.getIdTipoNssAclaracion())) {
				tipoAc.setBlanqueoCurp(true);
			}
  }
  
	/**
	 * Obtiene las aclaraciones para un nss objeto que se presenta en vista
	 * 
	 * @param aclaraciones
	 * 
	 * @return TipoAclaracion
	 */
	private TipoAclaracion generarTipoAclaracion(
			List<MovAclaracionNss> aclaraciones) {
		TipoAclaracion tipoAc = new TipoAclaracion();

		for (MovAclaracionNss aclaracion : aclaraciones) {

			if (TipoNSSAclaracionEnum.CORRECCION_DE_NOMBRE.getId().equals(aclaracion.getIdTipoNssAclaracion())) {
				tipoAc.setCorreccionNombre(true);
			} else if (TipoNSSAclaracionEnum.CORRECCION_DE_DATOS_ESTADISTICOS.getId().equals(aclaracion.getIdTipoNssAclaracion())) {
				tipoAc.setCorreccionEstadis(true);
			} else if (TipoNSSAclaracionEnum.CANCELADO_POR_DUPLICIDAD.getId().equals(aclaracion.getIdTipoNssAclaracion())) {
				tipoAc.setCanceladoDup(true);
			} else {
        generarTipoAclaracionStep01(aclaracion, tipoAc);
      }
		}

		return tipoAc;
	}

	/**
	 * Actualiza el xml de las aclaraciones de una solicitud
	 * 
	 * @param correccion
	 * @return
	 * @throws IllegalArgumentException
	 * @throws TramiteNoEncontradoException
	 */
	public boolean guardarXmlCorreccionDatos(CorreccionDatos correccion)
			throws SolicitudNoEncontradaException,
			TramiteNoEncontradoException {

		int contador=0;
		List<CorreccionNSS> asociadosCert = new ArrayList<CorreccionNSS>();
		log.debug("GUARDANDO ACLARACIONES EN  XML DE LA SOLICITUD....."
				+ correccion.getFolioSolicitud());
		Solicitud solicitud = solicitudBusiness
				.consultarPorFolioSolicitud(correccion.getFolioSolicitud());

		TramiteCorreccionCurp tramite = (TramiteCorreccionCurp) solicitud
				.getTramites().get(0);
		boolean ci= false;
		
		
		// Se obtienen movimientos existentes
//		 List<DitMovAclaracionNssCda> movExistentes =  movimientoAclaracionLocal.buscarMovExistentes(correccion.getFolioSolicitud());
		
		for (CorreccionNSS nss : tramite.getListaNssCorreccion()) {		
			
			
			log.debug("GUARDANDO ACLARACIONES XML NSS: " + nss.getNss());
			nss.setListaAclaraciones(obtenerAclaraciones(nss.getNss(),
					correccion.getListaNss()));
			log.debug("setListaAclaraciones: " + nss.getNss());
			if(tramite.getListaNssCorreccion().size()> 1)
			{
				if(nss.getListaAclaraciones().size() != 0 )
				if(nss.getListaAclaraciones().get(0).getAclaracion().equals("CUENTA ILOGICA"))
					{
						contador = contador + 1;
					}
			}
			else if (tramite.getListaNssCorreccion().size() == 1)
			{
				if(nss.getListaAclaraciones().size() != 0 )
				if(nss.getListaAclaraciones().get(0).getAclaracion().equals("CUENTA ILOGICA"))
				{
					contador = contador + 1;
				}
			}
			log.debug("NUMERO DE ACLARACIONES: "
					+ nss.getListaAclaraciones().size());

			log.debug("GUARDANDO TIPO  NSS XML: ");
			nss.setIdTipoNss(obtenerTipoNss(nss.getNss(),
					correccion.getListaNss()));
		
			log.info("ACTUALIZANDO DETALLE");
			DitDetalleNss detalle = correccionDatosAseguradoLocal
					.obtenerDetalleNss(tramite.getTramiteId(), nss.getNss());
			
			log.debug("----Eliminando correcciones anteriores por sistema");
			int cc = correccionDatosAseguradoLocal.eliminarCorrrecionesNssPorSistema(detalle
					.getCveDetalleNss());
			log.debug("----Elimine " + cc + " aclaraciones por sistema para el NSS: " + nss.getNss());
			
			log.info("CDA----Eliminando correcciones anteriores ");
			correccionDatosAseguradoLocal.eliminarCorrrecionesNss(detalle
					.getCveDetalleNss());

			List<DitMovAclaracionNssCda> movExistentes =  movimientoAclaracionLocal.buscarMovExistentes(detalle
					.getCveDetalleNss());
			correccionDatosAseguradoLocal.actualizarTipoDetalleNss(
					detalle.getCveDetalleNss(), nss.getIdTipoNss());
			

			log.debug("GUARDANDO ACLARACIONES  NSS EN BD: ");
			guardarAclaraciones(nss.getListaAclaraciones(), detalle, movExistentes);
			
			
			
			log.debug("tramite:" + tramite.toString());
			log.debug("Lista NSS size:" + tramite.getListaNssCorreccion().size() );
			log.debug("Lista NSS size:" + contador );
			if(contador == (tramite.getListaNssCorreccion().size()))
			{
				ci = true;
			}

			log.info("aclaracion nss: " + nss.getNss() + "tipo aclaracion: " + nss.getIdTipoNss());
			if(nss.getIdTipoNss() == (TipoNSSCorreccionEnum.ASOCIADO_AL_CERTIFICADOR).getId()){

				asociadosCert.add(nss);
			}
		}

        if (asociadosCert.size() >= 1) {

            log.info("Se encontro mas de un nss asociado al certificador");
            boolean actualizar = true;
            boolean errorSindo = false;
            DitMovAclaracionNssCda mov = null;
            List<DitMovAclaracionNssCda> envCda = movimientoAclaracionLocal.obtenerMovimientosAclaracion(correccion.getFolioSolicitud());
            for (DitMovAclaracionNssCda ditMovAclaracionNssCda : envCda) {
                if (ditMovAclaracionNssCda.getCveIdTipoNssAclaracion() != null) {
                    if (ditMovAclaracionNssCda.getCveIdTipoNssAclaracion().getCveTipoNssAclaracion() == TipoNSSAclaracionEnum.CANCELADO_POR_DUPLICIDAD
                            .getId()) {

                        if (ditMovAclaracionNssCda.getIndEnvSindo() == null && !errorSindo) {
                            mov = ditMovAclaracionNssCda;
                        } else if (ditMovAclaracionNssCda.getIndEnvSindo() != null && ditMovAclaracionNssCda.getIndEnvSindo() == '1') {
                            actualizar = false;
                        } else if (ditMovAclaracionNssCda.getIndEnvSindo() != null && ditMovAclaracionNssCda.getIndEnvSindo() == '0' && ditMovAclaracionNssCda.getCveIdEstadoMovSindo() == 2) {
                        	 mov = ditMovAclaracionNssCda;
                        	 errorSindo = true;
                        }
                    }
                }
            }

			if (mov != null && actualizar) {
				actualizarEnvioSindo(mov,'1');
			}
		}

		tramite.setsoloCuentaIndividual(ci);
		log.debug("ACTUALIZANDO XML TRAMITE");
		solicitudBusiness.actualizarXmlTramite(tramite);

		log.debug(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
		log.debug(":::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
		log.debug("::: REVISANDO LISTA DE ACLARACIONES :::");
		//para tramites de duplicidad y unificacion si el NSS asociado no trae marca se envia Mov 06
		for (CorreccionNSS nss : tramite.getListaNssCorreccion()) {	
			log.debug("-- NSS: " + nss.getNss() + ", tipo: " + nss.getIdTipoNss());
			boolean acl = false;
            if ((TipoNSSCorreccionEnum.ASOCIADO_AL_CERTIFICADOR).getId().equals(nss.getIdTipoNss())) {
                for (MovAclaracionNss aclaracion : nss.getListaAclaraciones()) {
                    log.debug("--- Tipo aclaracion: " + aclaracion.getIdTipoNssAclaracion() + ", aclaracion: " + aclaracion.getAclaracion());
                    if (TipoNSSAclaracionEnum.CORRECCION_DE_NOMBRE.getId().equals(aclaracion.getIdTipoNssAclaracion())
                            || TipoNSSAclaracionEnum.CORRECCION_DE_DATOS_ESTADISTICOS.getId().equals(aclaracion.getIdTipoNssAclaracion())) {
                        //si tiene una de estas aclaraciones ya trae MOV06
                        log.debug("--- La aclaracion es de tipo correccion de datos basicos o estadisticos, no se agrega Mov06");
                        acl = true;
                        break;
                    }
                }
				// si el NSS no trae aclaracion de correccion de nombre o de datos estadisticos
				if(!acl){
					log.debug("-- Se guarda Mov06 por sistema para el NSS " + nss.getNss());
					DitDetalleNss detalle = correccionDatosAseguradoLocal.obtenerDetalleNss(tramite.getTramiteId(), nss.getNss());
					guardarAclaracion(detalle, true);
				}
			}			
		}
		return true;
	}
	
	/**
	 * 
	 * Guarda aclaracion de un nss
	 * 
	 * @param sistema
	 * @param detalle
	 */
	private void guardarAclaracion(DitDetalleNss detalle, boolean sistema) {
		log.info("--CDA -- ALMACENANDO ACLARACION EN BD.....");
		MovAclaracionNss aclaracion;
		aclaracion = new MovAclaracionNss();
		aclaracion.setAclaracion(TipoNSSAclaracionEnum.CORRECCION_DE_NOMBRE.getDescripcion());
		aclaracion.setIdTipoNssAclaracion(TipoNSSAclaracionEnum.CORRECCION_DE_NOMBRE.getId());

		DitMovAclaracionNssCda movAclaracion = new DitMovAclaracionNssCda();
		movAclaracion.setCveIdDetalleNssCda(detalle);
		DicTipoNssAclaracion dicAclaracion = new DicTipoNssAclaracion();
		dicAclaracion.setCveTipoNssAclaracion(aclaracion.getIdTipoNssAclaracion().intValue());
		movAclaracion.setCveIdTipoTramCorrecNss(correccionAseguradoUtilityLocal.obtenerTipoTramite(aclaracion.getIdTipoNssAclaracion()));
		movAclaracion.setCveIdTipoNssAclaracion(dicAclaracion);
		movAclaracion.setFecRegistroAlta(new Date());
		movAclaracion.setFecRegistroBaja(null);
		movAclaracion.setFecRegistroActualizado(new Date());
		if(sistema){
			movAclaracion.setIndMovSistema('1');	
		}
		log.info("-------------se hace persist-------------");
		movimientoAclaracionLocal.guardarMovimiento(movAclaracion);

	}
		
	/**
	 * 
	 * Guarda aclaraciones de un nss
	 * 
	 * @param aclaraciones
	 * @param detalle
	 */
	private void guardarAclaraciones(List<MovAclaracionNss> aclaraciones, DitDetalleNss detalle, List<DitMovAclaracionNssCda> movExistentes) {

		log.info("--CDA -- ALMACENANDO ACLARACIONES EN BD.....");
		boolean guardado = true;

		if (aclaraciones != null) {
			for (MovAclaracionNss aclaracion : aclaraciones) {
				DitMovAclaracionNssCda movAclaracion = new DitMovAclaracionNssCda();
				movAclaracion.setCveIdDetalleNssCda(detalle);
				DicTipoNssAclaracion ditAclaracion = new DicTipoNssAclaracion();
				ditAclaracion.setCveTipoNssAclaracion(aclaracion.getIdTipoNssAclaracion().intValue());

				movAclaracion.setCveIdTipoTramCorrecNss(correccionAseguradoUtilityLocal.obtenerTipoTramite(aclaracion.getIdTipoNssAclaracion()));
				//movAclaracion.setCveIdTipoTramCorrecNss(ditAclaracion.getCveTipoNssAclaracion() == 5 ? dictt.setCveIdTipoTramCorrecNss((long) 5) : movAclaracion.getCveIdTipoTramCorrecNss());
				ditAclaracion.setCveTipoNssAclaracion(ditAclaracion.getCveTipoNssAclaracion() == 8 ? 5 : ditAclaracion.getCveTipoNssAclaracion());
				movAclaracion.setCveIdTipoNssAclaracion(ditAclaracion);
				movAclaracion.setFecRegistroAlta(new Date());
				movAclaracion.setFecRegistroBaja(null);
				movAclaracion.setFecRegistroActualizado(new Date());

				log.info("--CDA -- Evalua mov ....." + movExistentes.size());

				for (int i = 0; i < movExistentes.size(); i++) {
					if (movExistentes.get(i).getCveIdTipoNssAclaracion() != null) {
						if (movExistentes.get(i).getCveIdDetalleNssCda().getCveDetalleNss() == movAclaracion.getCveIdDetalleNssCda()
								.getCveDetalleNss() && movExistentes.get(i).getCveIdTipoNssAclaracion().getCveTipoNssAclaracion() == movAclaracion
								.getCveIdTipoNssAclaracion().getCveTipoNssAclaracion()) {
							log.info("-------------se hace merge-------------");
							guardado = false;
							//movimientoAclaracionLocal.actualizarMovimiento(movAclaracion);
							movExistentes.remove(i);

						}
					} else {
						guardado = false;
					}

				}
				if (guardado) {

					log.info("-------------se hace persist-------------");
					movimientoAclaracionLocal.guardarMovimiento(movAclaracion);
				}
				guardado = true;

			}
			for (DitMovAclaracionNssCda movExistente : movExistentes) {
				log.info("-------------se elimina-------------");
				movimientoAclaracionLocal.elimianrMovimiento(movExistente);
			}

		}

	}
				
		
	
	
	
	
	
	

	/**
	 * Obtiene las aclaraciones de un nss
	 * 
	 * @param nss
	 * @param listaNss
	 * @return
	 */
	private List<MovAclaracionNss> obtenerAclaraciones(String nss,
			List<TramiteNss> listaNss) {
		List<MovAclaracionNss> aclaraciones = new ArrayList<MovAclaracionNss>();
		for (TramiteNss tramiteNss : listaNss) {
			if (nss.equals(tramiteNss.getNss())) {
				aclaraciones = obtenerListaAclaraciones(tramiteNss
						.getTipoAclaracion());
			}
		}
		return aclaraciones;
	}

	/**
	 * Obtiene las aclaraciones a aplicar para un nss dentro del xml
	 * 
	 * @param correc
	 * @return lista de correciones
	 */
	private List<MovAclaracionNss> obtenerListaAclaraciones(
			TipoAclaracion correc) {
		List<MovAclaracionNss> aclaraciones = new ArrayList<MovAclaracionNss>();
		MovAclaracionNss aclaracion = null;

		if (correc.isCorreccionNombre()) {
			aclaracion = new MovAclaracionNss();
			aclaracion.setAclaracion(TipoNSSAclaracionEnum.CORRECCION_DE_NOMBRE
					.getDescripcion());
			aclaracion
					.setIdTipoNssAclaracion(TipoNSSAclaracionEnum.CORRECCION_DE_NOMBRE
							.getId());
			aclaraciones.add(aclaracion);
		}
		if (correc.isCorreccionEstadis()) {
			aclaracion = new MovAclaracionNss();
			aclaracion
					.setAclaracion(TipoNSSAclaracionEnum.CORRECCION_DE_DATOS_ESTADISTICOS
							.getDescripcion());
			aclaracion
					.setIdTipoNssAclaracion(TipoNSSAclaracionEnum.CORRECCION_DE_DATOS_ESTADISTICOS
							.getId());
			aclaraciones.add(aclaracion);
		}
		if (correc.isCanceladoDup()) {
			aclaracion = new MovAclaracionNss();
			aclaracion
					.setAclaracion(TipoNSSAclaracionEnum.CANCELADO_POR_DUPLICIDAD
							.getDescripcion());
			aclaracion
					.setIdTipoNssAclaracion(TipoNSSAclaracionEnum.CANCELADO_POR_DUPLICIDAD
							.getId());
			aclaraciones.add(aclaracion);
		}
		if (correc.isHomonimio()) {
			aclaracion = new MovAclaracionNss();
			aclaracion
					.setAclaracion(TipoNSSAclaracionEnum.CORRESPONDE_A_UN_HOMONIMO
							.getDescripcion());
			aclaracion
					.setIdTipoNssAclaracion(TipoNSSAclaracionEnum.CORRESPONDE_A_UN_HOMONIMO
							.getId());
			aclaraciones.add(aclaracion);

		}
		if (correc.isNoExisteCanase()) {
			aclaracion = new MovAclaracionNss();
			aclaracion.setAclaracion(TipoNSSAclaracionEnum.NO_EXISTE_EN_CANASE
					.getDescripcion());
			aclaracion
					.setIdTipoNssAclaracion(TipoNSSAclaracionEnum.NO_EXISTE_EN_CANASE
							.getId());
			aclaraciones.add(aclaracion);

		}
		if (correc.isOtroAsegurado()) {

			aclaracion = new MovAclaracionNss();
			aclaracion
					.setAclaracion(TipoNSSAclaracionEnum.CORRESPONDE_A_OTRO_ASEGURADO
							.getDescripcion());
			aclaracion
					.setIdTipoNssAclaracion(TipoNSSAclaracionEnum.CORRESPONDE_A_OTRO_ASEGURADO
							.getId());
			aclaraciones.add(aclaracion);
		}
		if (correc.isCuentaIlogica()) {
			aclaracion = new MovAclaracionNss();
			aclaracion
					.setAclaracion(TipoNSSAclaracionEnum.CUENTA_ILOGICA
							.getDescripcion());
			aclaracion
					.setIdTipoNssAclaracion(TipoNSSAclaracionEnum.CUENTA_ILOGICA
							.getId());
			aclaraciones.add(aclaracion);
		}
		if (correc.isBlanqueoCurp()) {
			aclaracion = new MovAclaracionNss();
			aclaracion
					.setAclaracion(TipoNSSAclaracionEnum.BLANQUEAMIENTO_CURP
							.getDescripcion());
			aclaracion
					.setIdTipoNssAclaracion(TipoNSSAclaracionEnum.BLANQUEAMIENTO_CURP
							.getId());
			aclaraciones.add(aclaracion);
		}

		return aclaraciones;
	}

	/**
	 * Obtiene el TipoNss
	 * 
	 * @param nss
	 * @param listaNss
	 * @return id del Tipo Nss
	 */
	private Long obtenerTipoNss(String nss, List<TramiteNss> listaNss) {

		Long idTipoNss = null;

		for (TramiteNss tramite : listaNss) {

			if (tramite.getNss().equals(nss)) {
				if (tramite.getTipoNss().isCertificador()) {
					idTipoNss = TipoNSSCorreccionEnum.CERTIFICADOR.getId();
					break;
				} else if (tramite.getTipoNss().isAsociado()) {
					idTipoNss = TipoNSSCorreccionEnum.ASOCIADO_AL_CERTIFICADOR
							.getId();
					break;
				} else if (tramite.getTipoNss().isCorresOtraPersona()) {
					idTipoNss = TipoNSSCorreccionEnum.CORRESPONDE_A_OTRA_PERSONA
							.getId();
					break;
				} else if (tramite.getTipoNss().isNoExisteCanase()) {
					idTipoNss = TipoNSSCorreccionEnum.NO_EXISTE_EN_CANASE
							.getId();
					break;
				} else {
					idTipoNss = TipoNSSCorreccionEnum.CERTIFICADOR.getId();
					break;
				}
			}

		}

		log.info("TIPO NSS....................: " + idTipoNss);
		return idTipoNss;

	}
	
	

	

	/**
	 * Guardar correcciones de XML a tablas de una solicitud
	 * 
	 * @param folioSolicitud
	 * @param usuario
	 * @return
	 * @throws TereaSinUsuarioAsignadoException
	 * @throws NoExisteTransicionParaTareaUsuarioException
	 * @throws EstadoTareaUsuarioNoValidoException
	 * @throws NoExisteTareaUsuarioException
	 * @throws TramiteNoEncontradoException 
	 */
	public boolean guardarCorreccionDatos(String folioSolicitud, String usuario)
			throws SolicitudNoEncontradaException,
			NoExisteTareaUsuarioException, EstadoTareaUsuarioNoValidoException,
			NoExisteTransicionParaTareaUsuarioException,
			TereaSinUsuarioAsignadoException, TramiteNoEncontradoException {
		log.debug("GUARDANDO ACLARACIONES EN BD....." + folioSolicitud);
		Solicitud solicitud;

		solicitud = solicitudBusiness
				.consultarPorFolioSolicitud(folioSolicitud);

		TramiteCorreccionCurp tramite = (TramiteCorreccionCurp) solicitud
				.getTramites().get(0);

		DitDetalleNss detalle = null;

		DitMovAclaracionNssCda movAclaracion = null;
		for (CorreccionNSS nss : tramite.getListaNssCorreccion()) {

			log.debug("Aclaraciones del nss: " + nss.getNss());

			detalle = correccionDatosAseguradoLocal.obtenerDetalleNss(
					tramite.getTramiteId(), nss.getNss());

			log.info("CDA----Eliminando correcciones anteriores ");
			correccionDatosAseguradoLocal.eliminarCorrrecionesNss(detalle
					.getCveDetalleNss());

			for (MovAclaracionNss aclaracion : nss.getListaAclaraciones()) {
				movAclaracion = new DitMovAclaracionNssCda();
				movAclaracion.setCveIdDetalleNssCda(detalle);
				DicTipoNssAclaracion ditAclaracion = new DicTipoNssAclaracion();
				ditAclaracion.setCveTipoNssAclaracion(aclaracion
						.getIdTipoNssAclaracion().intValue());
				movAclaracion.setCveIdTipoNssAclaracion(ditAclaracion);
				movAclaracion.setFecRegistroAlta(new Date());
				movAclaracion.setFecRegistroActualizado(new Date());

				movimientoAclaracionLocal.guardarMovimiento(movAclaracion);
			}

		}

		
		/**
		 * ACTUALIZANDO TRAMITE
		 */
		solicitud = correccionDatosUtil.crearSolicitudPorAutorizar(
				solicitud, usuario, "RESPONSABLE");

		solicitudBusiness.actualizarTramites(solicitud);
		/**
		 * AVANZANDO TAREA
		 */
		avanzarTareaTramite(tramite.getTramiteId());

		return true;
	}
	
	@TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
	private void avanzarTareaTramite(Long idTramite)
			throws NoExisteTareaUsuarioException,
			EstadoTareaUsuarioNoValidoException,
			NoExisteTransicionParaTareaUsuarioException,
			TereaSinUsuarioAsignadoException {

		log.info("AVANZADO TAREA DEL TRAMITE" + idTramite);
		TareaBandeja tareaBandeja = flujoTrabajoBusiness
				.getTareaActivaPorIdTramite(idTramite);

		log.info("CDA----tareaBandeja.getIdTarea():"
				+ tareaBandeja.getIdTareaUsuario());

		MensajeTarea mensjeTarea = tareaBandeja.getMensajeTarea();
		mensjeTarea.setEstado(EstadoNegocioEnum.POR_AUTORIZAR.getDescripcion());
		mensjeTarea.setTipoTransicion(TipoTransicionEnum.PRINCIPAL.getId());
		flujoTrabajoBusiness.completarTarea(tareaBandeja.getIdTareaUsuario(),
				mensjeTarea);

	}
	
	/**
	 * Metodo que avanza la tarea de una solicitud y cambia el estado del tramite
	 * 
	 * @param folio
	 * @throws NoExisteTareaUsuarioException
	 * @throws EstadoTareaUsuarioNoValidoException
	 * @throws NoExisteTransicionParaTareaUsuarioException
	 * @throws TereaSinUsuarioAsignadoException
	 * @throws SolicitudNoEncontradaException
	 * @throws TramiteNoEncontradoException 
	 */
	public void avanzarTareaTramite(String folio, String usuario )
			throws NoExisteTareaUsuarioException,
			EstadoTareaUsuarioNoValidoException,
			NoExisteTransicionParaTareaUsuarioException,
			TereaSinUsuarioAsignadoException, SolicitudNoEncontradaException, 
			TramiteNoEncontradoException {

		

		Solicitud solicitud = solicitudBusiness
				.consultarPorFolioSolicitud(folio);
		
		TramiteCorreccionCurp tramite = (TramiteCorreccionCurp) solicitud
				.getTramites().get(0);
		
		
		TareaBandeja tareaBandeja= flujoTrabajoBusiness.getTareaActivaPorIdTramite(tramite.getTramiteId());
		String responsable= "RESPONSABLE";
		
		if(tareaBandeja!=null){
			responsable=tareaBandeja.getInicioTramite().getParticipantes().get("Responsable");
			
		}
		
		
		solicitud = correccionDatosUtil.crearSolicitudPorAutorizar(
				solicitud, usuario, responsable);

		solicitudBusiness.actualizarTramites(solicitud);
		
		

		
		
		
		log.info("AVANZADO TAREA DEL TRAMITE" + tramite.getTramiteId());
		

		log.info("CDA----tareaBandeja.getIdTarea():"
				+ tareaBandeja.getIdTareaUsuario());

		MensajeTarea mensjeTarea = tareaBandeja.getMensajeTarea();
		mensjeTarea.setEstado(EstadoNegocioEnum.POR_AUTORIZAR.getDescripcion());
		mensjeTarea.setTipoTransicion(TipoTransicionEnum.PRINCIPAL.getId());
		flujoTrabajoBusiness.completarTarea(tareaBandeja.getIdTareaUsuario(),
				mensjeTarea);

	}
	
	
	
	/**
	 * Metodo que obtiene el detalle con las diferencias, para cada correcion
	 * por nss clasificado
	 * 
	 * @param correccionDatos
	 * @return ResumenCorrecion
	 */
	public ResumenCorrecion armarDetalleCoreccion(
			CorreccionDatos correccionDatos) {

		ResumenCorrecion resumenCorrecion = new ResumenCorrecion();

		resumenCorrecion = correccionDatosUtil.obtenerResumenorreccion(
				correccionDatos.getRenapo(), correccionDatos);

		return resumenCorrecion;
	}

	

	public boolean vlidaNssConvencional(String nss) {

		String nss1y2 = nss.substring(0, 2);
		if (nss1y2.equals("89")) {
			String nss3y4 = nss.substring(2, 4);
			if (nss3y4.equals("97") || nss3y4.equals("98")
					|| nss3y4.equals("99") || nss3y4.equals("00")
					|| nss3y4.equals("01") || nss3y4.equals("02")) {
				return true;
			}

		}
		if (nss1y2.equals("36") || nss1y2.equals("77") || nss1y2.equals("79")
				|| nss1y2.equals("80") || nss1y2.equals("97")) {
			return true;
		}
		return false;
	}

	public boolean validarBlanqueamientoCURP(Solicitud solicitud) throws PersonasNoLocalizadasException, NssRelacionadoVariasPersonasException {

		boolean esBlanqueamiento = false;
		TramiteCorreccionCurp tramite = (TramiteCorreccionCurp) solicitud.getTramites().get(0);
		if (tramite.getListaNssCorreccion() != null) {
			for (CorreccionNSS nss : tramite.getListaNssCorreccion()) {
				if (nss.getListaAclaraciones() != null) {
					for (MovAclaracionNss aclaracion : nss.getListaAclaraciones()) {
						DitDetalleNss detalle = correccionDatosAseguradoLocal.obtenerDetalleNss(tramite.getTramiteId(), nss.getNss());
						correccionDatosAseguradoLocal
								.eliminarMovimientoPorDetalleNss(detalle.getCveDetalleNss(), TipoNSSAclaracionEnum.BLANQUEAMIENTO_CURP.getId());
						if (aclaracion.getIdTipoNssAclaracion().equals(TipoNSSAclaracionEnum.CORRESPONDE_A_UN_HOMONIMO.getId()) || aclaracion
								.getIdTipoNssAclaracion().equals(TipoNSSAclaracionEnum.CORRESPONDE_A_OTRO_ASEGURADO.getId())) {
							try {
								/*Fisica fisicaNss = personaFisicaServiceBusiness.localizarPersonaFisicaPorNss(nss.getNss());*/
								Fisica fisicaNss = null;
								List<Fisica> personasFuenteNSS = serviceBusiness.getAseguradoByNSSLegadosyBDTU(nss.getNss(), true);
								if (personasFuenteNSS != null) {
									for (Fisica f : personasFuenteNSS) {
										if (((int) f.getIdentificadores().get(0).getIdIdentificador()) == OrigenConsultaNssEnum.CANASE.getClave()) {
											fisicaNss = f;
											break;
										}
									}
								}
								if (fisicaNss != null) {
									if (fisicaNss.getCurp() != null && (fisicaNss.getCurp().equals(tramite.getPersonaRENAPO().getCurp()) || (
											tramite.getPersonaRENAPO().getCurpsHistoricas() != null && tramite.getPersonaRENAPO().getCurpsHistoricas()
													.contains(fisicaNss.getCurp())))) {
										DitMovAclaracionNssCda movAclaracion = new DitMovAclaracionNssCda();
										movAclaracion.setCveIdDetalleNssCda(detalle);
										DicTipoNssAclaracion ditAclaracion = new DicTipoNssAclaracion();
										ditAclaracion.setCveTipoNssAclaracion(aclaracion.getIdTipoNssAclaracion().intValue());
										movAclaracion.setCveIdTipoNssAclaracion(ditAclaracion);
										movAclaracion.setFecRegistroAlta(new Date());
										movAclaracion.setFecRegistroActualizado(new Date());
										DicTipoTramCorreccionNss dicTipoTramCorreccionNss = new DicTipoTramCorreccionNss();
										dicTipoTramCorreccionNss.setCveIdTipoTramCorrecNss(8L);
										movAclaracion.setCveIdTipoTramCorrecNss(dicTipoTramCorreccionNss);
										movimientoAclaracionLocal.guardarMovimiento(movAclaracion);
										esBlanqueamiento = true;
									}
								}
							/*} catch (PersonasNoLocalizadasException e) {
								log.error("Error en el blanquemiento de CURP " + e.getMessage(), e); 
								throw new PersonasNoLocalizadasException(e.getMessage());
							} catch (NssRelacionadoVariasPersonasException e) {
								log.error("Error en el blanquemiento de CURP " + e.getMessage(), e);
								throw new NssRelacionadoVariasPersonasException(
										e.getMessage()); */
							} catch (Exception e) {
								log.error("Error en el blanquemiento de CURP " + e.getMessage(), e);
								throw new NssRelacionadoVariasPersonasException(e.getMessage());
							}
						}
					}
				}
			}
		}
		return esBlanqueamiento;
	}

    private void actualizarEnvioSindo(DitMovAclaracionNssCda movAclaracion, Character indicador) {

        log.info("Actualizando envio movimiento 05" + movAclaracion.getCveIdMovAclaracionNss());
        movAclaracion.setIndEnvSindo(indicador);

        movimientoAclaracionLocal.actualizarMovimiento(movAclaracion);
    }

	@Override
	public boolean validarTipoMovimientos(String folio) {

		log.debug("---CDA AUTORIZAR--- se consultan los movimientos para validar si tiene tipos");
		List<DitMovAclaracionNssCda> movs = movimientoAclaracionLocal.obtenerMovimientosAclaracion(folio);
		for (DitMovAclaracionNssCda mov : movs) {
			if (mov.getCveIdTipoTramCorrecNss() != null) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void validarSeparacionPersonas(String usuario, Solicitud solicitud) {

		String nssCertificador = null;
		List<Fisica> involucrados = new ArrayList<Fisica>();
		TramiteCorreccionCurp tramite = (TramiteCorreccionCurp) solicitud.getTramites().get(0);
		try {
			if (tramite.getListaNssCorreccion() != null) {
				for (CorreccionNSS nss : tramite.getListaNssCorreccion()) {
					log.info("Separacion NSS: " + nss.getNss() + " Tipo NSS: " + TipoNSSCorreccionEnum.fromId(nss.getIdTipoNss()));
					if (TipoNSSCorreccionEnum.CERTIFICADOR.getId().equals(nss.getIdTipoNss())) {
						log.info("Se guarda el NSS de la persona certificadora: " + nss.getNss());
						nssCertificador = nss.getNss();
					} else {
						if (nss.getListaAclaraciones() != null) {
							for (MovAclaracionNss aclaracion : nss.getListaAclaraciones()) {
								log.info("Se valida que los NSS involucrados traigan un tipo de aclaracion de homonimia o invasion");
								if (aclaracion.getIdTipoNssAclaracion().equals(TipoNSSAclaracionEnum.CORRESPONDE_A_UN_HOMONIMO.getId()) || aclaracion
										.getIdTipoNssAclaracion().equals(TipoNSSAclaracionEnum.CORRESPONDE_A_OTRO_ASEGURADO.getId())) {
									log.info("El NSS {" + nss.getNss() + "} si contiene una correccion de invasion u homonimia se guarda la persona involucrada");
									involucrados.add(personaFisicaServiceBusiness.localizarPersonaFisicaPorNss(nss.getNss()));
								}
							}
						}
					}
				}
			}

			if (!involucrados.isEmpty()) {
				List<DitBitSeparacionPersonas> listBitacora = correccionDatosAseguradoLocal.obtenerBitacoraSeparacionPersonas(tramite.getTramiteId(), false);
				for (Fisica fisica : involucrados) {
					Fisica certificador = personaFisicaServiceBusiness.localizarPersonaFisicaPorNss(nssCertificador);
					if (certificador != null && fisica.getIdPersona().equals(certificador.getIdPersona())) {
						if (listBitacora != null) {
							for (DitBitSeparacionPersonas bit : listBitacora) {
								if (!fisica.getCveIdAsignacionNSS().equals(bit.getCveIdAsignacionNss())) {
									this.guardarEnBitacora(fisica, tramite.getTramiteId(), usuario);
								} else {
									log.info("El registro ya existe, no se guarda nuevamente en la bitacora idAsignacion " + fisica.getCveIdAsignacionNSS());
								}
							}
						} else {
							this.guardarEnBitacora(fisica, tramite.getTramiteId(), usuario);
						}
					} else {
						log.info("El id es diferente continua flujo idPersona: " + fisica.getIdPersona());
					}
				}
			}
		} catch (Exception e) {
			log.error("Ocurrio un error al realizar la separacion de personas - excepcion: " + e);
		}
	}

	private void guardarEnBitacora(Fisica fisica, Long idTramite, String usuario) {

		log.info("EL ID ES EL MISMO {Se trata de la misma persona} NSS: " + fisica.getNss() + " idPersona: " + fisica.getIdPersona());
		DitDetalleNss detalle = correccionDatosAseguradoLocal.obtenerDetalleNss(idTramite, fisica.getNss());
		DitBitSeparacionPersonas bitacora = utility.convertModelToEntity(fisica);
		bitacora.setDetalleNssCda(detalle);
		bitacora.setCveIdTramite(idTramite);
		bitacora.setCveUsuario(usuario);
		correccionDatosAseguradoLocal.guardarActualizarBitacoraSeparacionPersonas(bitacora);
	}

	@Override
	public void registrarNuevaPersona(Long idTramite) {

		try {
			List<DitBitSeparacionPersonas> bitacora = correccionDatosAseguradoLocal.obtenerBitacoraSeparacionPersonas(idTramite, true);
			if (bitacora != null) {
				for (DitBitSeparacionPersonas bit : bitacora) {
					bit.setFecRegistroActualizado(new Date());
					bit.setCveIdPersonaNuevo(personaFisicaServiceBusiness.registrarNuevaPersonaCDA(utility.convertEntityToModel(bit), bit.getCveIdPersonaAnterior(), bit.getCveIdAsignacionNss()));
					correccionDatosAseguradoLocal.guardarActualizarBitacoraSeparacionPersonas(bit);
				}
			}
		} catch (Exception e) {
			log.info("No fue posible generar la nueva persona para realizar la separacion de personas");
			e.printStackTrace();
		}
	}

	@Override
	public boolean validarCambioAOperada(Solicitud solicitud, String usuario, Map<String, String> tramitesTareas) {

		String nssCertificador = null;
		boolean hayHomonimiaOInvasion = false;
		boolean hayDuplicidad = false;
		TramiteCorreccionCurp tramite = (TramiteCorreccionCurp) solicitud.getTramites().get(0);
		// Validacion de los NSSs involucrados y el tipo de correccion que posee cada uno
		try {
			if (tramite.getListaNssCorreccion() != null) {
				for (CorreccionNSS nss : tramite.getListaNssCorreccion()) {
					log.info("Tipo NSS: " + TipoNSSCorreccionEnum.fromId(nss.getIdTipoNss()));
					if (TipoNSSCorreccionEnum.CERTIFICADOR.getId().equals(nss.getIdTipoNss())) {
						log.info("Se guarda el NSS certificador {" + nss.getNss() + "}");
						nssCertificador = nss.getNss();
						if (nss.getListaAclaraciones() != null && !nss.getListaAclaraciones().isEmpty()) {
							log.info("El certificador si tiene tipo de correccion continua flujo normal");
							return false;
						}
					} else {
						if (nss.getListaAclaraciones() != null) {
							for (MovAclaracionNss aclaracion : nss.getListaAclaraciones()) {
								log.info("Se valida que los NSS involucrados traigan un tipo de aclaracion de homonimia o invasion");
								if (aclaracion.getIdTipoNssAclaracion().equals(TipoNSSAclaracionEnum.CORRESPONDE_A_UN_HOMONIMO.getId()) || aclaracion
										.getIdTipoNssAclaracion().equals(TipoNSSAclaracionEnum.CORRESPONDE_A_OTRO_ASEGURADO.getId())) {
									log.info("El NSS {" + nss.getNss() + "} si contiene una correccion de invasion u homonimia");
									hayHomonimiaOInvasion = true;
								} else if (aclaracion.getIdTipoNssAclaracion().equals(TipoNSSAclaracionEnum.CANCELADO_POR_DUPLICIDAD.getId())) {
									hayDuplicidad = true;
								}
							}
						}
					}
				}
			}

			if (hayHomonimiaOInvasion) {
				// Se valida si cumple con los criterios para cambiar a operada consultando si existen movimientos marcados para envio a SINDO
				log.info("Consultando movimientos de cuenta individual marcados");
				List<DitCorreccionCtaIndCda> listaMovs = movimientoCuentaIndividualEntity.obtenerMovimientosByFolio(solicitud.getNoFolioSolicitud());
				if (listaMovs == null || listaMovs.isEmpty()) {
					if (hayDuplicidad) {
						// Hay duplicidad pero el NSS certificador no posee ningun tipo de correccion, se valida si existe el NSS
						log.info("Existe una correccion de duplicidad para envio a SINDO");
						actualizaEstatus(nssCertificador, solicitud, usuario, tramitesTareas, false);
					} else {
						// No hay tramite de duplicidad se valida si existe el NSS para permitir el cambio a Operada
						return actualizaEstatus(nssCertificador, solicitud, usuario, tramitesTareas, true);
					}
				} else {
					// Si existen movimientos marcados pero el NSS certificador no posee ningun tipo de correccion, se valdia si existe el NSS
					actualizaEstatus(nssCertificador, solicitud, usuario, tramitesTareas, false);
				}
			}
		} catch (Exception e) {
			log.error("Ocurrio un error al validar el cambio de estado de la solicitud { " + solicitud.getNoFolioSolicitud() + " } a operada");
			e.printStackTrace();
		}
		log.info("Fin de la validacion");
		return false;
	}

	private List<Tramite> crearTramitesOperados(List<Tramite> tramites, String usuario) {

		List<Tramite> lstTramite = new ArrayList<Tramite>();

		for (Tramite tramitecda : tramites) {
			TramiteCorreccionCurp tramite = (TramiteCorreccionCurp) tramitecda;

			EstadoTramite estadoTramite = new EstadoTramite();
			estadoTramite.setDescripcion(EstadoNegocioEnum.obtenerDescripcionNegocio(EstadoTramiteEnum.PROCESADO_SINDO.getCodigo()));
			estadoTramite.setIdEstadoTramitePersona(EstadoTramiteEnum.PROCESADO_SINDO.getCodigo());
			tramite.setEstadoTramite(estadoTramite);

			if (tramite.getObservacionesSubdelegacion() == null) {
				tramite.setObservacionesSubdelegacion(new ArrayList<ObservacionesSubdelegacion>());
			}
			tramite.getObservacionesSubdelegacion().add(crearObservacionTramite(estadoTramite, usuario));
			lstTramite.add(tramite);
		}

		return lstTramite;
	}

	private ObservacionesSubdelegacion crearObservacionTramite(EstadoTramite estadoTramite, String usuario ){
		ObservacionesSubdelegacion obSubdelegacion = new ObservacionesSubdelegacion();
		obSubdelegacion.setFechaActualizacion(new Date());
		obSubdelegacion.setDetalle("OPERADA POR EL SISTEMA");
		obSubdelegacion.setResumen("OPERADA POR EL SISTEMA");
		obSubdelegacion.setUsuario(usuario);
		obSubdelegacion.setAsignado(usuario);
		obSubdelegacion.setCveEstado(estadoTramite.getIdEstadoTramitePersona());
		return obSubdelegacion;
	}

	private boolean actualizaEstatus(String nssCertificador, Solicitud solicitud, String usuario, Map<String, String> tramitesTareas, boolean isCambioAOperada)
			throws TramiteNoEncontradoException, SolicitudNoEncontradaException, NoExisteTareaUsuarioException, EstadoTareaUsuarioNoValidoException {

		// Para permitir el envio del mov05, movCI o el cambio a operada se valida que el certificador se encuentre en BDTU
		try {
			// Se realiza la consulta del NSS
			log.info("NSS certificador {" + nssCertificador + "}");
			personaFisicaServiceBusiness.localizarPersonaFisicaPorNss(nssCertificador);
		} catch (PersonasNoLocalizadasException e) {
			// El NSS no existe en BDTU, se genera mov06 para el certificador y continua el flujo normal
			log.debug("Se genera correccion de datos basicos por sistema para el NSS " + nssCertificador);
			guardarAclaracion(correccionDatosAseguradoLocal.obtenerDetalleNss(solicitud.getTramites().get(0).getTramiteId(), nssCertificador), true);
			return false;
		} catch (NssRelacionadoVariasPersonasException e) {
			log.info("El NSS si existe pero requiere separacion de personas, puede continuar con el flujo normal");
		}

		if (isCambioAOperada) {
			// Sí se encuentra en BDTU se actualiza el estado de la solicitud a OPERADA
			log.info("Se actualiza el estado de la solicitud {" + solicitud.getNoFolioSolicitud() + "} a OPERADA");
			solicitud.getSolicitante().setCveIdUsuario(usuario);
			EstadoSolicitud estadoSolicitud = new EstadoSolicitud();
			estadoSolicitud.setIdEstadoSolicitud(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo());
			solicitud.setEstadoSolicitud(estadoSolicitud);
			solicitud.setTramites(crearTramitesOperados(solicitud.getTramites(), usuario));
			solicitudBusiness.actualizarEstados(solicitud);

			TramiteCorreccionCurp tramite = (TramiteCorreccionCurp) solicitud.getTramites().get(0);
			solicitudBusiness.actualizarXmlTramite(tramite);

			Iterator<Map.Entry<String, String>> entries = tramitesTareas.entrySet().iterator();
			while (entries.hasNext()) {
				Map.Entry<String, String> entry = entries.next();
				if (tramite.getTramiteId().equals(Long.parseLong(entry.getKey()))) {
					flujoTrabajoBusiness.actualizarBDocInstancia(Long.parseLong(entry.getValue()), autorizarSolicitudUtility.crearMensajeTarea(usuario), usuario);
				}
			}
			return true;
		} else {
			// Sí se encuentra en BDTU se actualiza el estado del mov06 a 3 (Procesado) para permitir el envio del movCI o mov05
			List<DitMovAclaracionNssCda> movs = movimientoAclaracionLocal.obtenerMovimientosAclaracionByFolioNss(solicitud.getNoFolioSolicitud(), nssCertificador);
			for (DitMovAclaracionNssCda mov : movs) {
				log.info("Se actualiza el mov06 {" + mov.getCveIdMovAclaracionNss() + "} a procesado para permitir el envio del mov05");
				mov.setCveIdEstadoMovSindo(TipoEstadoMovimientoEnviadoSindoEnum.PROCESADO.getId());
				movimientoAclaracionLocal.actualizarMovimiento(mov);
			}
		}
		return false;
	}
}
