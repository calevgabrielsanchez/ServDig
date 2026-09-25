/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.asegurado.serie.service.business;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.AseguradoConRPAsignado;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.NSSYaExistenteException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.SerieNssAgotadaException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.SujetoObligadoInexistente;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.ErrorAlActivarSerieException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.ErrorAsignarSerieException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.ErrorCrearFoliosDeSerieException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.ErrorGuardarSerieException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.NivelDeAsignacionSerieIndefinidoException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.NumeroDeSeriePorAnioRegistroExisteException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.SerieNoExisteException;
import mx.gob.imss.ctirss.delta.exception.gestion.serie.SeriesNoLocalizadasException;
import mx.gob.imss.ctirss.delta.exception.individuo.AfectacionDatosPersonaException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.ArgumentosInvalidosException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceRenapoCurpException;
import mx.gob.imss.ctirss.delta.framework.exceptions.ClienteWebserviceSatRfcException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.asegurado.serie.service.entity.AseguradoEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.asegurado.serie.service.entity.SerieServiceEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.asegurado.serie.service.utility.SerieServiceUtility;
import mx.gob.imss.ctirss.delta.gestion.asegurado.serie.service.utility.SerieServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.asegurado.service.interfaces.SerieServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.AsignacionPatronalBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.ReingresoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.ModuloEnum;
import mx.gob.imss.ctirss.delta.model.asegurado.Asegurado;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.enums.SexoEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoSerieEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.integracion.asignacion.MovimientoAsignacionType;
import mx.gob.imss.ctirss.delta.model.gestion.integracion.asignacion.util.MovimientoAsignacionTypeBuilder;
import mx.gob.imss.ctirss.delta.model.gestion.nss.AsignacionSerieNSS;
import mx.gob.imss.ctirss.delta.model.gestion.nss.Serie;
import mx.gob.imss.ctirss.delta.model.gestion.nss.TipoSerie;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modulo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteAsegurado;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCambioInformacionPersona;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.reingreso.MovimientoReingresoType;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.reingreso.util.MovimientoReingresoTypeBuilder;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.AfectarDatosPersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.ComponentesExternosBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.publicos.ServiciosPersonaBusinessRemote;

import org.apache.commons.lang.StringUtils;

/**
 * @author vanderluk
 * 
 */
@Stateless(name = "serieServiceBusiness", mappedName = "serieServiceBusiness")
public class SerieServiceBusiness extends AbstractServiceBusiness implements
SerieServiceBusinessRemote {
	@EJB(name = "serviciosPersonaBusiness", mappedName = "serviciosPersonaBusiness")
	private ServiciosPersonaBusinessRemote servicioPersonaBusiness;
	@EJB(mappedName = "personaBusiness")
	private PersonaBusinessRemote personaBusiness;
	@EJB
	private SerieServiceEntityLocal entity;
	@EJB
	private SerieServiceUtilityLocal utility;
	@EJB
	private AseguradoEntityLocal aseguradoEntity;
	@EJB
	private AsignacionPatronalBusinessRemote asignacionPatronalBusiness;
	@EJB
	private ReingresoServiceBusinessRemote reingresoServiceBusiness;
	@EJB
	private ComponentesExternosBusinessRemote componentesExternosBusiness;
	@EJB
	private AfectarDatosPersonaBusinessRemote afectarDatosPersonaBusiness;

	private static final Long MAXIMO_DE_FOLIO_POR_ANIO = new Long(9999);
	private static final Integer TIPO_SERIE_ORIDNARIA = 1;
	private static final int ANIO_MINIMO_SERIE = 14;
	private static final int ANIO_CERO_CERO= 00;
	private static final int ANIO_NOVENTAYNUEVE = 99;

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.imss.ctirss.delta.gestion.asegurado.serie.service.business.
	 * SerieServiceBusinessLocal#obtenerSeriesActivas(java.lang.Long,
	 * java.lang.Long)
	 */
	@Override
	public List<AsignacionSerieNSS> obtenerSeriesActivas(Long delegacion,
			Long subdelegacion) throws SeriesNoLocalizadasException {
		this.log.debug("Obteniendo las series activas delegacion ["
				+ delegacion + "] y [" + subdelegacion + "]");

		/*
		 * Regla de negocio: Se debe de obtener las Series Activas del anio en
		 * curso.
		 */
		Long anioRegistro = utility.obtenerDosDigitosDeAnio(new Date());
		List<AsignacionSerieNSS> series = this.entity.consultarSeriesActivas(
				delegacion, subdelegacion, anioRegistro, null);

		if (series == null || series.isEmpty()) {
			throw new SeriesNoLocalizadasException();
		}

		return series;
	}

	@Override
	public List<AsignacionSerieNSS> obtenerSeriesActivas(Long delegacion,
			Long subdelegacion, Long anioRegistro, Integer idTipoSerie) {
		return this.entity.consultarSeriesActivas(delegacion, subdelegacion,
				anioRegistro, idTipoSerie);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.imss.ctirss.delta.gestion.asegurado.serie.service.business.
	 * SerieServiceBusinessLocal
	 * #obtenerDetalleDeSerie(mx.gob.imss.ctirss.delta.model.gestion.nss.Serie)
	 */
	@Override
	public AsignacionSerieNSS obtenerDetalleDeSerie(Serie serie)
			throws SerieNoExisteException {
		this.log.debug("Consultando la Serie ...." + serie);

		AsignacionSerieNSS asignacionSerieNSS = this.entity
				.consultarDetalleSerie(serie);

		if (serie == null) {
			throw new SerieNoExisteException();
		}

		return asignacionSerieNSS;
	}

	/**
	 * 
	 * @param asignacion
	 * @param asegurado
	 * @return
	 * @throws NivelDeAsignacionSerieIndefinidoException
	 * @throws SeriesNoLocalizadasException
	 * @throws ErrorAlActivarSerieException
	 */
	private Serie ubicarSerieDisponible(AsignacionSerieNSS asignacion,
			Fisica asegurado) throws NivelDeAsignacionSerieIndefinidoException,
	SeriesNoLocalizadasException, ErrorAlActivarSerieException {

		Serie folio = new Serie();

		Long anioNacimiento = this.utility.obtenerDosDigitosDeAnio(asegurado
				.getFechaNacimiento());

		Integer nivelAsignacion = this.utility.getNivelAsignacion(asignacion);

		/*
		 * En este caso no se encontr� una Serie/Folio del mismo tipo y del
		 * mismo nivel, se debera ubicar una Serie / Folio de un nivel m�s Alto.
		 */
		if (nivelAsignacion.intValue() == SerieServiceUtility.ASIGNACION_SERIE_NIVEL_CENTRAL) {
			/*
			 * En este caso si el nivel de asignaci�n es Nivel Central / General
			 * y no se encontr� Serie/Folio Inactiva, esto quiere decir que es
			 * un error, ya que no hay nivel m�s alto.
			 */
			throw new SeriesNoLocalizadasException(
					"No se existen Series Activas para el Tipo de Serie y Nivel de Asignaci\u00F3n  solicitado.");

		} else {
			/*
			 * Si es de otro tipo de nivel se debera ubicar una Serie/Folio de
			 * otro nivel m�s alto.
			 */
			switch (nivelAsignacion.intValue()) {
			case 2: {
				/*
				 * Nivel delegacional, se debe entonces realizar la b�squeda por
				 * nivel general, esto es, volviendo nulos la delegaci�n y
				 * subdelegaci�n
				 */
				asignacion.setDelegacion(null);
				asignacion.setSubdelegacion(null);
				break;
			}
			case 1: {
				/*
				 * Nivel subdelegacional, se debera realizar la b�squeda por
				 * nivel delegacional.
				 */
				asignacion.setSubdelegacion(null);
				break;
			}

			default: {
				break;
			}
			}

			/*
			 * Realizamos la b�squeda de series activas, ya con el nivel
			 * modificado.
			 */
			this.log.debug("Se busca serie activa para el nivel superior");

			folio = this.entity.getFolioDeAnioNacimientoSerie(asignacion,
					anioNacimiento);

			if (folio == null) {
				/*
				 * Realizamos la b�squeda de series inactivas, ya con el nivel
				 * modificado.
				 */
				this.log.debug("No se encontro serie activa para el nivel superior, se procede a buscar una inactiva");

				folio = this.entity.getSerieInactivaDeFolio(asignacion,
						anioNacimiento);

				if (folio == null) {
					if (nivelAsignacion.intValue() == SerieServiceUtility.ASIGNACION_SERIE_NIVEL_SUBDELEGACION) {
						/*
						 * Se realiza otra busqueda de serie activa, pero ahora a nivel general.
						 */
						asignacion.setDelegacion(null);
						asignacion.setSubdelegacion(null);

						this.log.debug("Se busca serie activa de nivel general");

						folio = this.entity.getFolioDeAnioNacimientoSerie(asignacion,
								anioNacimiento);

						if (folio == null) {
							this.log.debug("No se encontro serie activa de nivel general, se procede a buscar serie inactiva de nivel general");

							folio = this.entity.getSerieInactivaDeFolio(asignacion,
									anioNacimiento);

							if (folio == null) {
								throw new SeriesNoLocalizadasException(
										"No se existen Series Activas para el Tipo de Serie y Nivel de Asignaci\u00F3n  solicitado.");
							}

							this.log.debug("Se encontro serie inactiva de nivel general");

							/*
							 * Activamos la serie localizada...
							 */
							this.entity.activarFolioDeSerie(folio);
						} else {
							this.log.debug("Se encontro serie activa de nivel general");
						}
					} else {
						/*
						 * En este caso si no se localizo una Serie/Folio activa
						 * para el nivel delegacional ( que se hizo para nivel
						 * general) entonces es error.
						 */
						throw new SeriesNoLocalizadasException(
								"No se existen Series Activas para el Tipo de Serie y Nivel de Asignaci\u00F3n  solicitado.");
					}
				} else {
					this.log.debug("Se encontro serie inactiva para el nivel superior");
					/*
					 * Activamos la serie localizada...
					 */
					this.entity.activarFolioDeSerie(folio);

				}
			} else {
				this.log.debug("Se encontro serie activa para el nivel superior");
			}
		}

		return folio;
	}

	@Override
	public AsignacionNSS asignarNSS(AsignacionSerieNSS asignacion,
			Fisica asegurado) throws NivelDeAsignacionSerieIndefinidoException,
	SeriesNoLocalizadasException, ErrorAlActivarSerieException,
	NSSYaExistenteException, SerieNssAgotadaException {

		Serie serieEntrada = asignacion.getSerie();

		this.log.debug("Iniciando la asignacion del NSS ...");

		// 1. Verificamos que se tengan todos los datos de la persona.
		if (asegurado == null || serieEntrada == null) {
			this.log.error("No se recibieron todos los parametros necesarios para la asignacion.");
		} else {
			// Validacion de la fecha de nacimiento
			if (asegurado.getFechaNacimiento() == null) {
				this.log.error("Fecha de nacimiento de asegurado es nula");
			}

			// Validacion del tipo de serie
			if (serieEntrada.getTipoSerie() == null) {
				this.log.error("El Tipo de Serie es nulo.");
			} else if (serieEntrada.getTipoSerie().getIdTipoSerie() == null) {
				this.log.error("El Id del Tipo de Serie es nulo.");
			}
		}

		this.log.debug("Persona a asignar el NSS :->"
				+ asegurado.getIdPersona() + " - "
				+ asegurado.getFechaNacimiento() + "<- ");

		this.log.debug("Serie a asignar : -->" + serieEntrada.getIdSerie() + "--"
				+ serieEntrada.getTipoSerie().getDescripcion() + "<--");

		// 2. Obtenemos el Folio de la Serie.
		Long anioNacimiento = this.utility.obtenerDosDigitosDeAnio(asegurado
				.getFechaNacimiento());

		/**TODO la asignación del año de serie se hace en un nivel superior para controlar el ciclo
		 * y la iteración de recursividad 

		// 2.1 Obtenemos el anio de registro, que debe ser el anio actual
		Long anioRegistro = this.utility.obtenerDosDigitosDeAnio(new Date());
		this.log.debug("Anio de registro: " + anioRegistro);
		serieEntrada.setAnioRegistro(anioRegistro.intValue());
		 **/

		/*
		 * Validamos que exista una serie con el tipo de serie que se solicita.
		 * Estos es, puede ser que no exista serie para el tipo de serie
		 * solicitado, en este caso se debe de enviar una exception.
		 */
		Serie serie = this.entity.getFolioDeAnioNacimientoSerie(asignacion,
				anioNacimiento);


		if (serie == null) {
			this.log.error(" No se encontro folio para el anio de nacimiento de la serie.");
			/*
			 * Si no se encontro un folio disponible para la serie y tipo serie
			 * se debe de localizar una Serie/Folio Inactiva del mismo tipo y
			 * del mismo nivel de asignacion.
			 */
			serie = this.entity.getSerieInactivaDeFolio(asignacion,
					anioNacimiento);
			if (serie == null) {
				serie = this.ubicarSerieDisponible(asignacion, asegurado);
			} else {
				// Se ubico un folio Inactivo, se debe de activar.
				this.entity.activarFolioDeSerie(serie);
			}

		}
		
		Long folio = null;
		try{
			folio = this.entity.incrementaFolioDeSerie(serie, anioNacimiento);
		}catch(SerieNssAgotadaException ex){
			this.log.warn("El folio ya alcanzo el maximo permitido, se debera de inactivar la serie ...");
			this.entity.desactivarFolioDeSerie(serie);
			return null;
		}

		this.log.info("Folio obtenido para la secuencia " + serie.getSecuenciaNss() + " -> " + folio);

		// 4. - Validamos que el folio obtenido no supere el maximo permitido
		if (folio >= MAXIMO_DE_FOLIO_POR_ANIO) {
			this.log.warn("El folio ya alcanzo el maximo permitido, se debera de inactivar la serie ...");
			this.entity.desactivarFolioDeSerie(serie);
			this.log.debug("ya sali de actualizar la serie a baja y el foliio es[" +folio+"]");
			if(folio > MAXIMO_DE_FOLIO_POR_ANIO){
				this.log.error("el folio es superior al maximo regreso nulo para que itere de nuevo el folio es[" +folio+"]");
				return null;
			}
		}

		// 4. - Validamos que el folio obtenido no supere el maximo permitido
		if (folio >= MAXIMO_DE_FOLIO_POR_ANIO) {
			this.log.warn("El folio ya alcanzo el maximo permitido, se debera de inactivar la serie ...");
			this.entity.desactivarFolioDeSerie(serie);
		}

		serie.setFolio(folio);

		// 5. Generamos el nss
		String nss = this.utility.generaNSS(serie);
		this.log.debug("NSS : " + nss);
		System.out.println("NSS: " + nss);

		/*
		 * 5.1. Se checa que el NSS reci�n generado no exista ya en la base de
		 * datos, esto se hace para prevenir NSS duplicados a ra�z de los NSS
		 * cargados desde CANASE
		 */
		boolean existeNSS = this.entity.existeNSS(nss);

		if (existeNSS) {
			this.log.warn("El NSS (" + nss + ") reci�n calculado ya existe, se vuelve a calcular uno nuevo");

			throw new NSSYaExistenteException(nss);			
		}
		
		// 6. Asignamos el NSS a la persona.
		AsignacionNSS asignacionNSS = new AsignacionNSS();
		asignacionNSS.setNssStr(nss);
		asignacionNSS.setNss(nss);
		asignacionNSS.setIdPersona(asegurado.getIdPersona());

		asegurado.setNss(nss);

		AsignacionNSS asignacionNSSOtorgada = this.entity.asignarNSSAsegurado(asignacionNSS);
		this.log.debug("Se asigno correctamente el NSS a la persona ...");
		// 7. Se encola el asegurado recien creado al archivo para SINDO
		try {
			MovimientoAsignacionType type = generarMovimientoAsignacionSINDO(asegurado, serie); 
			this.log.debug("Asegurado para SINDO -> " + nss +   type);
			this.asignacionPatronalBusiness.encolarMovimientoAsignacion(type);
		}catch (Exception e) {
			log.error("error al encolar el asegurado para el movimeinto a SINDO nss " + nss , e);
		}
		this.log.debug("Se encolo asegurado para SINDO" + nss);

		return asignacionNSSOtorgada;
	}

	@Override
	public AsignacionNSS registrarAsegurado(Fisica asegurado,
			AsignacionSerieNSS asignacionSerieNSS, TramiteAsegurado tramite)
					throws ArgumentosInvalidosException, DomicilioNoValidoException,
					ClienteWebserviceSatRfcException,
					ClienteWebserviceRenapoCurpException,
					NivelDeAsignacionSerieIndefinidoException,
					SeriesNoLocalizadasException, ErrorAlActivarSerieException,
					PersonaNoEncontradaException {
		Fisica personaRegistrada;
		AsignacionNSS asignacionNSS = null;

		// 1. Verificamos que la persona exista, si no existe se crea una nueva
		if(asegurado.getIdPersona() != null){
			personaRegistrada = asegurado;

			/*
			 * Guardamos el domicilio y lo asociamos, el servicio utilizado ya
			 * valida si es nuevo o no
			 */
			if (personaRegistrada.getDomicilios() != null && !personaRegistrada.getDomicilios().isEmpty()) {
				this.componentesExternosBusiness.guardarYAsociarDomiciliosPersona(personaRegistrada);
			}
			if (personaRegistrada.getMediosContacto() != null && !personaRegistrada.getMediosContacto().isEmpty()) {
				this.componentesExternosBusiness.guardarYAsociarMediosContactoPersona(personaRegistrada);
			}

			// Se checa si el tramite trae el objeto del ICA
			if (tramite.getIcaDatosRespuesta() != null) {

				TramiteCambioInformacionPersona tramiteCambioInformacionPersona = new TramiteCambioInformacionPersona();
				tramiteCambioInformacionPersona.setDatosICA(tramite.getIcaDatosRespuesta());

				Modulo moduloOrigen = new Modulo();
				moduloOrigen.setIdModulo(ModuloEnum.ASIGNACION_NSS.getCodigo().longValue());

				try {
					this.afectarDatosPersonaBusiness.afectarDatos(tramiteCambioInformacionPersona, moduloOrigen);
				} catch (AfectacionDatosPersonaException e) {
					this.log.error(e);
				}
			}
		} else {
			List<Fisica> listPersonasEncontradas = null;

			listPersonasEncontradas = servicioPersonaBusiness
					.localizarPersonaFisica(asegurado.getCurp(),
							asegurado.getRfc(), asegurado.getNombre(),
							asegurado.getPrimerApellido(),
							asegurado.getSegundoApellido(),
							asegurado.getFechaNacimiento(),
							asegurado.getLugarNacimiento(), asegurado.getSexo());

			if (listPersonasEncontradas != null
					&& listPersonasEncontradas.size() > 0) {
				if (listPersonasEncontradas.get(0).getIdPersona() != null) {
					personaRegistrada = listPersonasEncontradas.get(0);
				} else {
					// 1. Verificamos qsi los datos de la persona provienene de
					// la pantalla de Concluir Tramite o proviene del WS
					if (asignacionSerieNSS == null) {
						asegurado = listPersonasEncontradas.get(0);
					}

					personaRegistrada = personaBusiness.altaPersonaFisica(asegurado);
				}
			} else {
				throw new PersonaNoEncontradaException(0L);
			}
		}

		// 2. Verificamos si la persona ya tiene asignado un NSS. Si no es asi
		// Asignamos el NSS a la persona.
		if (StringUtils.isBlank(personaRegistrada.getNss())) {
			if (asignacionSerieNSS == null) {
				asignacionSerieNSS = new AsignacionSerieNSS();

				Serie serie = new Serie();
				TipoSerie tipoSerie = new TipoSerie();

				// TODO: Colocar el tipo de Serie correspondiente a Estudiantes (se
				// utiliza por el momento la ordinaria)
				tipoSerie.setIdTipoSerie(TIPO_SERIE_ORIDNARIA);

				serie.setTipoSerie(tipoSerie);
				asignacionSerieNSS.setSerie(serie);
			} 

			/**
			 * se cambia la sección para que desde aqui se caclule el año de seria
			 * para que pueda ser recursiva 
			 */
			Long anioRegistro = this.utility.obtenerDosDigitosDeAnio(new Date());
			asignacionSerieNSS.getSerie().setAnioRegistro(anioRegistro.intValue());

			boolean nssCorrecto = false;

			do {
				try {
					asignacionNSS = asignarNSS(asignacionSerieNSS, personaRegistrada);
					nssCorrecto = true;
				} catch (NSSYaExistenteException e) {
					this.log.warn(e);
				} catch (SerieNssAgotadaException e) {
					log.warn("LA SERIE ACTUAL YA LLEGO AL LIMITE SE USA LA DEL ANIO ANTERIOR", e );
					if(asignacionSerieNSS.getSerie().getAnioRegistro().intValue() == ANIO_MINIMO_SERIE)
						throw new SeriesNoLocalizadasException();
					asignacionSerieNSS.getSerie().setAnioRegistro(
							asignacionSerieNSS.getSerie().getAnioRegistro().intValue() == ANIO_CERO_CERO?ANIO_NOVENTAYNUEVE:
								asignacionSerieNSS.getSerie().getAnioRegistro().intValue()-1);
				}catch(SeriesNoLocalizadasException ex){
					log.warn("NO SE ENCONTRO NINGUNA SERIE ACTIVA SE UTILIZARA LA DEL ANIO ANTERIOR", ex );
					if(asignacionSerieNSS.getSerie().getAnioRegistro().intValue() == ANIO_MINIMO_SERIE)
						throw ex;
					asignacionSerieNSS.getSerie().setAnioRegistro(
							asignacionSerieNSS.getSerie().getAnioRegistro().intValue() == ANIO_CERO_CERO?ANIO_NOVENTAYNUEVE:
								asignacionSerieNSS.getSerie().getAnioRegistro().intValue()-1);
				}
			} while (!nssCorrecto);

			personaRegistrada.setNss(asignacionNSS.getNss());
		} else {
			asignacionNSS = aseguradoEntity
					.consultarAsignacionNSS(personaRegistrada.getNss());
		}

		asegurado = personaRegistrada;

		return asignacionNSS;
	}

	@Override
	public Asegurado generarAsegurado(Fisica asegurado,
			SujetoObligado sujetoObligado) throws Exception {
		Date fechaActual = new Date();
		AsignacionNSS asignacionNSS = registrarAsegurado(asegurado, null, null);

		// 3. Se asocia un RP al NSS asignado (o encontrado)
		Asegurado aseguradoAsignado = new Asegurado();
		aseguradoAsignado.setFechaAlta(fechaActual);
		aseguradoAsignado.setAsignacionNSS(asignacionNSS);

		if (sujetoObligado == null || sujetoObligado.getCveIdSujetoObligado() != null) {
			aseguradoAsignado.setSujetoObligado(sujetoObligado);
		} else {
			throw new SujetoObligadoInexistente();
		}

		Asegurado aseguradoNuevo = new Asegurado();
		Asegurado aseguradoVigente = aseguradoEntity.getAseguradoByAsegurado(aseguradoAsignado);
		if (aseguradoVigente == null || aseguradoVigente.getIdAsegurado() == null) {
			aseguradoNuevo = aseguradoEntity.altaAsegurado(aseguradoAsignado);
			aseguradoNuevo.setAsignacionNSS(asignacionNSS);
		} else {
			throw new AseguradoConRPAsignado();
		}

		// Se encola para el movimiento de reingresos
		MovimientoReingresoType reingreso = generarMovimientoReingresoSINDO(
				asegurado, sujetoObligado);
		this.log.debug("Asegurado reingreso -> " + reingreso);
		this.reingresoServiceBusiness.encolarMovimientoReingreso(reingreso);

		return aseguradoNuevo;
	}

	@Override
	public Serie crearSerie(AsignacionSerieNSS asignacionSerie)
			throws NumeroDeSeriePorAnioRegistroExisteException,
			ErrorGuardarSerieException, ErrorCrearFoliosDeSerieException,
			ErrorAsignarSerieException {
		this.log.debug("Iniciando el servicio de creacion de Serie ...");

		Serie serie = asignacionSerie.getSerie();
		
		/*
		 * Paso 1: Validamos que el Numero de Serie no exista ya para el Anio de
		 * registro.
		 */
		this.entity.consultarNumSeriePorAnioRegistro(serie);

		/*
		 * Paso 2. Guardamos la serie.
		 */
		this.entity.guardarSerie(serie);

		/*
		 * Paso 3. Creamos los folios correspondientes a la Serie
		 */
		this.entity.crearFoliosDeSerie(serie);

		/*
		 * Paso 4. Asignamos la Serie.
		 */
		this.entity.asignarSerie(asignacionSerie);

		return serie;
	}

	private MovimientoAsignacionType generarMovimientoAsignacionSINDO(
			Fisica asegurado, Serie serie) {

		// Para SINDO HOMBRE = 1, MUJER = 2, NO BINARIO = 3
		int sexo = asegurado.getSexo().getIdSexo().longValue() == SexoEnum.HOMBRE
				.getId() ? 1 : asegurado.getSexo().getIdSexo().longValue() == SexoEnum.MUJER
				.getId() ? 2 : 3;

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(asegurado.getFechaNacimiento());

		int mesNacimiento = calendar.get(Calendar.MONTH) + 1;

		UnidadMedicaFamiliar umf = null;
		int cizOrigen = 0;
		int delOrigen = 0;
		int subDelOrigen = 0;
		int cveUMF = 0;

		if (asegurado.getUmf() != null) {
			AsignacionSerieNSS detalleSerie = entity.consultarDetalleSerie(serie);
			if(detalleSerie != null && detalleSerie.getSerie().getTipoSerie().getIdTipoSerie() == 
					TipoSerieEnum.MEXICANOS_EXTRANJERO.getClave()){
				umf = asegurado.getUmf();
				cizOrigen = detalleSerie.getDelegacion().getCiz();
				delOrigen = Integer.valueOf(detalleSerie.getSubdelegacion().getDelegacion().getClave());
				subDelOrigen = Integer.valueOf(detalleSerie.getSubdelegacion().getClave());
				cveUMF = umf.getNoEconomico().intValue();

			}else{
				umf = asegurado.getUmf();
				cizOrigen = umf.getSubdelegacion().getDelegacion().getCiz();
				delOrigen = Integer.valueOf(umf.getSubdelegacion().getDelegacion().getClave());
				subDelOrigen = Integer.valueOf(umf.getSubdelegacion().getClave());
				cveUMF = umf.getNoEconomico().intValue();
			}
		} else {
			this.log.warn("El asegurado a enviar a SINDO no cuenta con UMF, se settean valores por default.");
		}

		MovimientoAsignacionType movimientoSINDO = new MovimientoAsignacionTypeBuilder()
				.withCizOrigen(cizOrigen)
				.withDelOrigen(delOrigen)
				.withSubdelOrigen(subDelOrigen)
				.withCodEnvio(1)
				//.withCodRetorno(3)
				//.withCondicion(4)
				.withTpMovto(1)
				//.withOpcionMovto44(0)
				.withNss(asegurado.getNss().substring(0, 10))
				.withDigver(Integer.valueOf(asegurado.getNss().substring(10, asegurado.getNss().length())))
				.withNombre(asegurado.getNombre() != null ? asegurado.getNombre().trim() : "")
				.withPrimerApellido(asegurado.getPrimerApellido() != null ? asegurado.getPrimerApellido().trim() : "")
				.withSegundoApellido(asegurado.getSegundoApellido() != null ? asegurado.getSegundoApellido().trim() : "")
				.withSexo(sexo)
				.withMesNac(mesNacimiento)
				.withLugarNac(Integer.valueOf(asegurado.getLugarNacimiento().getClave()))
				.withIdUsuario("DEAS091") // Se pone este usuario que es de los gen�ricos para DELTA
				.withUmf(cveUMF)
				.withOrigen(1)
				.withFechaMovto(new Date())
				.withCurp(asegurado.getCurp())
				.build();

		return movimientoSINDO;

	}

	private MovimientoReingresoType generarMovimientoReingresoSINDO(
			Fisica asegurado, SujetoObligado sujetoObligado) {

		// Para SINDO HOMBRE = 1, MUJER = 2, NO BINARIO = 3
		int sexo = asegurado.getSexo().getIdSexo().longValue() == SexoEnum.HOMBRE
				.getId() ? 1 : asegurado.getSexo().getIdSexo().longValue() == SexoEnum.MUJER
				.getId() ? 2 : 3;

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(asegurado.getFechaNacimiento());

		int mesNacimiento = calendar.get(Calendar.MONTH) + 1;

		UnidadMedicaFamiliar umf = null;
		int delOrigen = 0;
		int subDelOrigen = 0;
		int cveUMF = 0;

		if (asegurado.getUmf() != null) {
			umf = asegurado.getUmf();
			delOrigen = Integer.valueOf(umf.getSubdelegacion().getDelegacion().getClave());
			subDelOrigen = Integer.valueOf(umf.getSubdelegacion().getClave());
			cveUMF = umf.getNoEconomico().intValue();
		} else {
			this.log.warn("El asegurado para REINGRESO no cuenta con UMF, se settean valores por default.");
		}

		StringBuffer nombreCompleto = new StringBuffer();
		nombreCompleto.append(asegurado.getNombre()).append(" ");
		nombreCompleto.append(asegurado.getPrimerApellido()).append(" ");
		nombreCompleto.append(asegurado.getSegundoApellido());

		MovimientoReingresoType movReingreso = new MovimientoReingresoTypeBuilder()
				.withFMovto(new Date())
				.withDelOrig(delOrigen)
				.withSubOrig(subDelOrigen)
				.withCveAplic(2)
				.withTpMovto(1)
				.withOrigenMov(1)
				.withNumFolio(1)
				.withArgumento(2)
				.withRegPatron(sujetoObligado.getNumeroRegistroPatronal())
				.withDigVrPat(Integer.valueOf(sujetoObligado.getDigVerificador()))
				.withFMovto(new Date())
				.withFRecepMovi(new Date())
				.withCveUnica(asegurado.getCurp())
				.withIdSubrServ(0)
				.withIdEventual(0)
				.withNumSegSoc(asegurado.getNss().substring(0, 10))
				.withDigVrNss(Integer.valueOf(asegurado.getNss().substring(10, asegurado.getNss().length())))
				.withNombre(asegurado.getNombre())                  //Parte de nomAseg
				.withPrimerApellido(asegurado.getPrimerApellido())           //Parte de nomAseg
				.withSegundoApellido(asegurado.getSegundoApellido())        //Parte de nomAseg
				.withIdExtemp(0)
				.withReducPago(0)
				.withExtODel(0)
				.withSalBase(BigDecimal.ZERO)
				.withSalInfonavit(BigDecimal.ZERO)
				.withTpSalario(1)
				.withSexo(sexo)
				.withMesNac(mesNacimiento)
				.withLugarNac(Integer.valueOf(asegurado.getLugarNacimiento().getClave()))
				.withUmf(cveUMF)
				.withAutPerm(0)
				.withDelDest(0)
				.withSubDest(0)
				.withTpDerech(0)
				.withAaNac(0)
				.withSituacion(0)
				.withTsalODel("A")
				.withNombreDh("EL REGIS")
				.withMesNacAp(1)
				.withNssCorr(1)
				.withDigVrNssCorr(1)
				.withTpPens(1)
				.withAlfGuar("A")
				.withNumGuar(1)
				.withCondicion(1)
				.withLocMpio("CENTRO HISTORICO")
				.withTpProrroga(1)
				.withFecTerProrr(1)
				.withIdPd(1)
				.build();

		return movReingreso;
	}

	@Override
	public String generarXmlMovAsignacionNSS(TramiteAsegurado tramiteAsegurado, boolean encolarMovimiento){

		String xml = null;
		Fisica fisica = null;
		AsignacionSerieNSS asignacionSerieNSS = null;
		MovimientoAsignacionType movAsignacion = null;

		if (tramiteAsegurado != null) {
			fisica = tramiteAsegurado.getFisica();
			asignacionSerieNSS = tramiteAsegurado
					.getAsignacionSerieNss();

			movAsignacion = this.generarMovimientoAsignacionSINDO(
					fisica, asignacionSerieNSS.getSerie());

			xml = this.asignacionPatronalBusiness.generarXmlMovimientoAsignacion(movAsignacion);

			this.log.debug("=============Movimiento=============");
			this.log.debug(xml);

			if (encolarMovimiento) {
				this.asignacionPatronalBusiness.encolarMovimientoAsignacion(movAsignacion);
			}

		} else {
			this.log.error("No se cuenta con la informaci�n necesaria para generar la trama");
		}

		return xml;
	}


	@Override
	public String  calculaNSS(AsignacionSerieNSS asignacionSerie,
			Fisica asegurado) throws NivelDeAsignacionSerieIndefinidoException,
	SeriesNoLocalizadasException, ErrorAlActivarSerieException,
	NSSYaExistenteException, SerieNssAgotadaException {
		String asignacionNSS = null;
		boolean nssCorrecto = false;
		/**
		 * se cambia la sección para que desde aqui se caclule el año de seria
		 * para que pueda ser recursiva 
		 */
		Long anioRegistro = this.utility.obtenerDosDigitosDeAnio(new Date());
		asignacionSerie.getSerie().setAnioRegistro(anioRegistro.intValue());
		do {
			try {
				asignacionNSS =  this.calculaNSSLocal(asignacionSerie, asegurado);
				if(asignacionNSS != null &&  !asignacionNSS.isEmpty()){
					log.debug("ya regreso un nss no nulo [" +asignacionNSS+ "]");
					nssCorrecto = true;
				}
			} catch (NSSYaExistenteException e) {
				this.log.warn(e);
			} catch (SerieNssAgotadaException e) {
				log.warn("LA SERIE ACTUAL YA LLEGO AL LIMITE SE USA LA DEL ANIO ANTERIOR", e );
				if(asignacionSerie.getSerie().getAnioRegistro().intValue() == ANIO_MINIMO_SERIE)
					throw new SeriesNoLocalizadasException();
				asignacionSerie.getSerie().setAnioRegistro(
						asignacionSerie.getSerie().getAnioRegistro().intValue() == ANIO_CERO_CERO?ANIO_NOVENTAYNUEVE:
							asignacionSerie.getSerie().getAnioRegistro().intValue()-1);
			}catch(SeriesNoLocalizadasException ex){
				log.warn("NO SE ENCONTRO NINGUNA SERIE ACTIVA SE UTILIZARA LA DEL ANIO ANTERIOR", ex );
				if(asignacionSerie.getSerie().getAnioRegistro().intValue() == ANIO_MINIMO_SERIE)
					throw ex;
				asignacionSerie.getSerie().setAnioRegistro(
						asignacionSerie.getSerie().getAnioRegistro().intValue() == ANIO_CERO_CERO?ANIO_NOVENTAYNUEVE:
							asignacionSerie.getSerie().getAnioRegistro().intValue()-1);
			}
		} while (!nssCorrecto);

		return asignacionNSS;
	}




	private String  calculaNSSLocal(AsignacionSerieNSS asignacion,
			Fisica asegurado) throws NivelDeAsignacionSerieIndefinidoException,
	SeriesNoLocalizadasException, ErrorAlActivarSerieException,
	NSSYaExistenteException, SerieNssAgotadaException {

		Serie serieEntrada = asignacion.getSerie();

		this.log.debug("Iniciando la asignacion del NSS ...");

		// 1. Verificamos que se tengan todos los datos de la persona.
		if (asegurado == null || serieEntrada == null) {
			this.log.error("No se recibieron todos los parametros necesarios para la asignacion.");
		} else {
			// Validacion de la fecha de nacimiento
			if (asegurado.getFechaNacimiento() == null) {
				this.log.error("Fecha de nacimiento de asegurado es nula");
			}

			// Validacion del tipo de serie
			if (serieEntrada.getTipoSerie() == null) {
				this.log.error("El Tipo de Serie es nulo.");
			} else if (serieEntrada.getTipoSerie().getIdTipoSerie() == null) {
				this.log.error("El Id del Tipo de Serie es nulo.");
			}
		}

		this.log.debug("Persona a asignar el NSS :->"
				+ asegurado.getIdPersona() + " - "
				+ asegurado.getFechaNacimiento() + "<- ");

		this.log.debug("Serie a asignar : -->" + serieEntrada.getIdSerie() + "--"
				+ serieEntrada.getTipoSerie().getDescripcion() + "<--");

		// 2. Obtenemos el Folio de la Serie.
		Long anioNacimiento = this.utility.obtenerDosDigitosDeAnio(asegurado
				.getFechaNacimiento());

		/** Se cambia el metodo para qu eel seteo se haga dede quien invoca el servicio
		 * para poder manejar la recusividad para las serires
		// 2.1 Obtenemos el anio de registro, que debe ser el anio actual
		Long anioRegistro = this.utility.obtenerDosDigitosDeAnio(new Date());
		this.log.debug("Anio de registro: " + anioRegistro);
		serieEntrada.setAnioRegistro(anioRegistro.intValue());
		asignacion.setSerie(serieEntrada);
		 **/

		/*
		 * Validamos que exista una serie con el tipo de serie que se solicita.
		 * Estos es, puede ser que no exista serie para el tipo de serie
		 * solicitado, en este caso se debe de enviar una exception.
		 */
		Serie serie = this.entity.getFolioDeAnioNacimientoSerie(asignacion,
				anioNacimiento);

		if (serie == null) {
			this.log.error(" No se encontro folio para el anio de nacimiento de la serie.");

			/*
			 * Si no se encontro un folio disponible para la serie y tipo serie
			 * se debe de localizar una Serie/Folio Inactiva del mismo tipo y
			 * del mismo nivel de asignacion.
			 */
			serie = this.entity.getSerieInactivaDeFolio(asignacion,
					anioNacimiento);
			if (serie == null) {
				serie = this.ubicarSerieDisponible(asignacion, asegurado);
			} else {
				// Se ubico un folio Inactivo, se debe de activar.
				this.entity.activarFolioDeSerie(serie);
			}

		}

		// 3. Incrementamos el folio.
		Long folio = null;
		try{
			folio = this.entity.incrementaFolioDeSerie(serie, anioNacimiento);
		}catch(SerieNssAgotadaException ex){
			this.log.warn("El folio ya alcanzo el maximo permitido, se debera de inactivar la serie ...");
			this.entity.desactivarFolioDeSerie(serie);
			return null;
		}

		this.log.info("Folio obtenido para la secuencia " + serie.getSecuenciaNss() + " -> " + folio);

		// 4. - Validamos que el folio obtenido no supere el maximo permitido
		if (folio >= MAXIMO_DE_FOLIO_POR_ANIO) {
			this.log.warn("El folio ya alcanzo el maximo permitido, se debera de inactivar la serie ...");
			this.entity.desactivarFolioDeSerie(serie);
			this.log.debug("ya sali de actualizar la serie a baja y el foliio es[" +folio+"]");
			if(folio > MAXIMO_DE_FOLIO_POR_ANIO){
				this.log.error("el folio es superior al maximo regreso nulo para que itere de nuevo el folio es[" +folio+"]");
				return null;
			}
		}

		serie.setFolio(folio);

		// 5. Generamos el nss
		String nss = this.utility.generaNSS(serie);
		this.log.debug("El NSS generado : " + nss);
		System.out.println("NSS: " + nss);

		/*
		 * 5.1. Se checa que el NSS reci�n generado no exista ya en la base de
		 * datos, esto se hace para prevenir NSS duplicados a ra�z de los NSS
		 * cargados desde CANASE
		 */
		boolean existeNSS = this.entity.existeNSS(nss);

		if (existeNSS) {
			this.log.warn("El NSS (" + nss + ") reci�n calculado ya existe, se vuelve a calcular uno nuevo");

			throw new NSSYaExistenteException(nss);			
		}

		return nss;
	}

	@Override
	public AsignacionNSS guardaAseguradoConNSS(Fisica asegurado, Serie serie)
			throws Exception{

		AsignacionNSS asignacionNSS = new AsignacionNSS();
		asignacionNSS.setNssStr(asegurado.getNss());
		asignacionNSS.setNss(asegurado.getNss());
		asignacionNSS.setIdPersona(asegurado.getIdPersona());

		AsignacionNSS asignacionNSSOtorgada = this.entity.asignarNSSAsegurado(asignacionNSS);
		this.log.debug("Se asigno correctamente el NSS a la persona ...");
		
		try {
		// 7. Se encola el asegurado reci�n creado al archivo para SINDO
		MovimientoAsignacionType type = generarMovimientoAsignacionSINDO(asegurado, serie); 
		this.log.debug("Asegurado para SINDO -> nss " + asegurado.getNss() + type);
		this.asignacionPatronalBusiness.encolarMovimientoAsignacion(type);
		this.log.debug("Se encolo asegurado para SINDO nss" + asegurado.getNss());
		}catch (Exception e) {
			log.error("error al encolar al asegurado a SINDO nss " + asegurado.getNss() , e);
		}
		return asignacionNSSOtorgada;


	}


}
