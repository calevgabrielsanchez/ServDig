package mx.gob.imss.ctirss.gestionpersonas.servicios.business;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.documento.probatorio.DocumentoProbatorioException;
import mx.gob.imss.ctirss.delta.exception.domicilio.AsociarDomicilioException;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoValidoException;
import mx.gob.imss.ctirss.delta.exception.individuo.AfectacionDatosPersonaException;
import mx.gob.imss.ctirss.delta.exception.individuo.DatosPersonaSATNoValidosException;
import mx.gob.imss.ctirss.delta.exception.individuo.NotificacionNoValidaException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaMoralNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.RFCNoLocalizadoEnEntidadExternaException;
import mx.gob.imss.ctirss.delta.exception.individuo.situacionSAT.SituacionSATNoValidaException;
import mx.gob.imss.ctirss.delta.exception.medio.contacto.RegistrarMedioContactoException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoValidaException;
import mx.gob.imss.ctirss.delta.framework.exceptions.TransformacionException;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.service.interfaces.DocumentoProbatorioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.Movimiento06CorreccionBusinessRemote;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.DomicilioFiscal;
import mx.gob.imss.ctirss.delta.model.enums.EstadoAdministracionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.asegurado.integracion.sindo.MovCorreccionesDatosAseguradoType;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.AfectarDatosPersonaWrapper;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.DatosPersonaSAT;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ICADatosRespuesta;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.MDMDatosEntrada;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.SituacionSAT;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EscrituraConstitutiva;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modulo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RegistroSindicato;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCambioInformacionPersona;
import mx.gob.imss.ctirss.gestionpersonas.servicios.utility.AfectarDatosPersonaUtilityLocal;
import mx.gob.imss.distss.digital.jaxb.util.JaxbUtil;

import org.apache.commons.lang.StringUtils;

@Stateless(name = "afectarDatosPersonaBusiness", mappedName = "afectarDatosPersonaBusiness")
public class AfectarDatosPersonaBusiness extends AbstractServiceBusiness
		implements AfectarDatosPersonaBusinessRemote {

	@EJB
	private transient PersonaBusinessLocal personaBusiness;
	@EJB
	private PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;
	@EJB
	private PersonaMoralBusinessLocal personaMoralBusiness;
	@EJB
	private DomicilioServiceBusinessRemote domicilioServiceBusiness;    
    @EJB
    private MediosContactoServiceBusinessRemote mediosContactoServiceBusiness;
    @EJB
    private DocumentoProbatorioServiceBusinessRemote documentoProbatorioServiceBusiness;
    @EJB
	private SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusiness;
    @EJB
    private AfectarDatosPersonaUtilityLocal afectarDatosPersonaUtility;
    @EJB
    private SituacionSATServiceBusinessLocal situacionSATServiceBusiness;
    @EJB
    private DatosPersonaSATServiceBusinessLocal datosPersonaSATServiceBusiness;
    @EJB
    private NotificacionServiceBusinessLocal notificacionServiceBusiness;
    @EJB(mappedName="movimiento06CorreccionAsegurado", name="movimiento06CorreccionAsegurado")
    private Movimiento06CorreccionBusinessRemote movimiento06CorreccionAsegurado;
    
    
    
	
	
    
	@Override
	public void afectarDatos(TramiteCambioInformacionPersona tramite, Modulo moduloOrigen)
			throws AfectacionDatosPersonaException, PersonaNoEncontradaException{

		this.log.debug("Entrando a la afectacion de datos de una persona");
		
		//Se convierte el tramite al wrapper
		AfectarDatosPersonaWrapper datosPersona = convertirTramiteAWrapper(tramite);
		
		try{
		
		//Se ejecuta la sincronización previo a afectar para tener el dato de razón social original
		ejecutarSincronizacionSINDO(datosPersona);
		}catch(RFCNoLocalizadoEnEntidadExternaException e){
			this.log.error("No se encontro la persona en el SAT");
			throw new PersonaNoEncontradaException(1l);
		}
			
		
		
		ejecutarMov06Asegurado(datosPersona);
		
		// Se checa si existen cambios a afectar
		if (datosPersona != null) {
			if (datosPersona.getFisica() != null) {
				afectarDatosPersonaFisica(datosPersona);
			} else if (datosPersona.getMoral() != null) {
				afectarDatosPersonaMoral(datosPersona);
			} else {
				throw new AfectacionDatosPersonaException();
			}
			
			if (tramite.getTramiteId() != null) {
				//Se crea(n) la(s) notificacion(es)
				try {
					this.notificacionServiceBusiness.crearNotificacion(tramite, moduloOrigen);
				} catch (NotificacionNoValidaException e) {
					this.log.error(e);
				}
			} else {
				this.log.warn("No se recibió id de trámite para crear notificaciones");
			}
			
		} else {
			String mensaje = "No existen cambios a afectar en la persona";
			this.log.error(mensaje);
			throw new AfectacionDatosPersonaException(mensaje);
		}
	}
	
	@Override
	public void modificarMediosContactoPersona(Fisica fisica) {
		
		this.log.debug("Se van a modificar los medios de contacto particulares de la persona " + fisica.getIdPersona());
		
		if (fisica.getMediosContacto() != null && fisica.getIdPersona() != null) {
			ListIterator<MedioContacto> itMedios = fisica.getMediosContacto().listIterator();
			
			MedioContacto medio = null;
			while(itMedios.hasNext()){
				medio = itMedios.next();
				
				if (medio.getEstadoAdministracionMedioContacto() != null){
					if (medio.getEstadoAdministracionMedioContacto().getClave() == EstadoAdministracionEnum.NUEVO
							.getClave()) {
						// Se guarda el medio de contacto nuevo
						try {
							medio = this.mediosContactoServiceBusiness
									.registrarAsociarMedioContactoPersona(medio, fisica.getIdPersona());
						} catch (RegistrarMedioContactoException e) {
							this.log.error(e);
						}
					} else if (medio.getEstadoAdministracionMedioContacto().getClave() == EstadoAdministracionEnum.MODIFICADO
							.getClave()) {
						/*
						 * Cuando es modificación se valida si el medio de 
						 * contacto trae el ID, si lo trae significa que
						 * ya existe en base de datos y sólo se modifica, si
						 * no lo trae significa que es uno nuevo que se
						 * tiene que dar de alta desde cero
						 */
						if(medio.getClave() != null){
							try {
								this.mediosContactoServiceBusiness.actualizarMedioDeContacto(medio);
							} catch (RegistrarMedioContactoException e) {
								this.log.error(e);
							}
						}else{
							try {
								medio = this.mediosContactoServiceBusiness
										.registrarAsociarMedioContactoPersona(medio, fisica.getIdPersona());
							} catch (RegistrarMedioContactoException e) {
								this.log.error(e);
							}
						}
					} else if (medio.getEstadoAdministracionMedioContacto().getClave() == EstadoAdministracionEnum.ELIMINADO
							.getClave() && medio.getClave() != null) {
						/*
						 * Si el medio de contacto a eliminar tiene un id
						 * asignado, significa que está en la base de datos
						 * y realmente se tiene que eliminar, en caso
						 * contrario sólo está en sesión y no es necesario
						 * invoncar al servicio para eliminarlo
						 */
						try {
							this.mediosContactoServiceBusiness.eliminarMedioDeContacto(medio.getClave());
						} catch (RegistrarMedioContactoException e) {
							this.log.error(e);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Ejecuta el proceso para encolar la información del movimiento 05  SINDO siempre y cuando
	 * existan cambio en alguno de los siguientes datos:
	 * Nombre
	 * RFC
	 * CURP
	 * Razon Social
	 * 
	 * @param datosPersona Wrapper con la información de los datos que se modificaron
	 * @throws AfectacionDatosPersonaException
	 */
	private void ejecutarSincronizacionSINDO(AfectarDatosPersonaWrapper datosPersona) throws AfectacionDatosPersonaException, RFCNoLocalizadoEnEntidadExternaException{
		Persona personaAfectada = null;
		if (datosPersona != null) {
			if (datosPersona.getFisica() != null) {
				personaAfectada = datosPersona.getFisica();
			} else if (datosPersona.getMoral() != null) {
				personaAfectada = datosPersona.getMoral();
			} else {
				throw new AfectacionDatosPersonaException();
			}
		}
		//Se realiza el envio a SINDO
		if(datosPersona.getModificarCURP() || datosPersona.getModificarNombre() 
				|| datosPersona.getModificarRazonSocial() || datosPersona.getModificarRFC())
			try {
				log.debug("Enviando movimiento 05 a sindo");
				sujetoObligadoServiceBusiness.enviarMovimientosDeActualizacionDatosGeneralesASindo(personaAfectada, true);
			} catch (GestionPatronalBusinessException e) {
				log.error("Ocurrio un error al notificar a SINDO");
				e.printStackTrace();
				throw new AfectacionDatosPersonaException("Se presentó un problema al notificar el cambio a SINDO");
			}
	}
	
	/**
	 * Ejecuta el proceso para encolar la información del movimiento 06 SINDO
	 * ASEGURADOS siempre y cuando la persona tenga NSS y existan cambio en
	 * alguno de los siguientes datos: <br>
	 * Nombre, sexo, mes o lugar de nacimiento
	 * 
	 * @param datosPersona
	 *            Wrapper con la información de los datos que se modificaron
	 * @throws AfectacionDatosPersonaException
	 */
	private void ejecutarMov06Asegurado(AfectarDatosPersonaWrapper datosPersona)
			throws AfectacionDatosPersonaException {

		Fisica personaAfectada = null;

		if (datosPersona != null && datosPersona.getFisica() != null) {

			personaAfectada = datosPersona.getFisica();
			
			// Se realiza el envio a SINDO
			if (StringUtils.isNotBlank(personaAfectada.getNss())
					&& (datosPersona.getModificarNombre()
							|| datosPersona.getModificarSexo()
							|| datosPersona.getModificarFechaNacimiento()
							|| datosPersona.getModificarLugarNacimiento())) {
				
				this.log.debug("NSS de la persona a afectar datos con el movimiento 06 -> "
						+ personaAfectada.getNss());
				this.log.debug("Enviando movimiento 06-asegurado a SINDO");

				MovCorreccionesDatosAseguradoType mov06Type = this.afectarDatosPersonaUtility
						.generarMovimientoActualizacionAseguradoSINDO(personaAfectada, null, null);

				this.log.debug("Movimiento 06 generado -> " + mov06Type);

				this.movimiento06CorreccionAsegurado
						.encolarMovimiento06CorrecconAsegurado(mov06Type);

				this.log.debug("Movimiento 06-asegurado enviado correctamente a SINDO");
			} else {
				this.log.debug("No se envia movimiento 06-asegurado");
			}
		}
	}
	
	@Override
	public void afectarDatosPersonaFisica(
			AfectarDatosPersonaWrapper datosPersona) throws PersonaNoEncontradaException {
		
		Fisica fisica = datosPersona.getFisica();
		
		/*
		 * Estos datos aunque son de RENAPO, puede darse el caso de
		 * que sólo se compare contra SAT y que el nombre tenga diferencias,
		 * es por eso que las validaciones están independientes.
		 */
		if (datosPersona.getModificarNombre()
				|| datosPersona.getModificarCURP()
				|| datosPersona.getModificarSexo()
				|| datosPersona.getModificarFechaNacimiento()
				|| datosPersona.getModificarLugarNacimiento()) {
			
			this.log.debug("Se van a modificar los datos RENAPO de la persona " + fisica.getIdPersona());
			
			// Llamada al método para modificar datos RENAPO
			this.personaBusiness.afectarDatosPersona(datosPersona);
			
		}
		
		if(datosPersona.getModificarDatosRENAPO()){
						
			if(datosPersona.getModificarDocumentoProbatorio()){
				
				this.log.debug("Se van a modificar los documentos probatorios de la persona " + fisica.getIdPersona());
				
				/*
				 * Entrando a esta validación, significa que hubo cambios en los
				 * documentos probatorios, por lo tanto, los documentos probatorios
				 * pueden estar dentro de la lista o en los atributos particulares,
				 * entonces se checa si la lista viene vacias, si es así, se agregan
				 * los atributos a la lista para poderla procesar.
				 */
				if (fisica.getDocumentosProbatorios() == null) {
					fisica.setDocumentosProbatorios(new ArrayList<DocumentoProbatorio>());
				}
				
				if (fisica.getDocumentosProbatorios().isEmpty()) {
					if (fisica.getActaNacimiento() != null) {
						fisica.getDocumentosProbatorios().add(fisica.getActaNacimiento());
					} else if (fisica.getDocumentoMigratorio() != null) {
						fisica.getDocumentosProbatorios().add(fisica.getDocumentoMigratorio());
					} else if (fisica.getCartaNaturalizacion() != null) {
						fisica.getDocumentosProbatorios().add(fisica.getCartaNaturalizacion());
					} else if (fisica.getNumeroUnicoExtranjero() != null) {
						fisica.getDocumentosProbatorios().add(fisica.getNumeroUnicoExtranjero());
					} else if (fisica.getCertificadoNacionalidadMexicana() != null) {
						fisica.getDocumentosProbatorios().add(fisica.getCertificadoNacionalidadMexicana());
					} else if (fisica.getOficioSolicitanteRefugiado() != null) {
						fisica.getDocumentosProbatorios().add(fisica.getOficioSolicitanteRefugiado());
					} else if (fisica.getFormaMigratoriaTurista() != null) {
						fisica.getDocumentosProbatorios().add(fisica.getFormaMigratoriaTurista());
					}
				}
				
				ListIterator<DocumentoProbatorio> itDocs = fisica
						.getDocumentosProbatorios().listIterator();
				
				DocumentoProbatorio docProbatorio = null;
				while(itDocs.hasNext()){
					docProbatorio = itDocs.next();
					
					if(docProbatorio.getEstadoAdministracionDocto() != null){
						if (docProbatorio.getEstadoAdministracionDocto()
								.getClave() == EstadoAdministracionEnum.NUEVO.getClave()) {
							try {
								// Llamada al método para guardar un documento probatorio nuevo
								this.documentoProbatorioServiceBusiness.registrarAsociarDocumentoProbatorioPersona(
										docProbatorio,fisica.getIdPersona());
							} catch (DocumentoProbatorioException e) {
								this.log.error(e);
							} catch (Exception e) {
								this.log.error(e);
							}
						} else if (docProbatorio.getEstadoAdministracionDocto()
								.getClave() == EstadoAdministracionEnum.MODIFICADO.getClave()) {
							/*
							 * Cuando es modificación se valida si el documento
							 * probatorio trae el ID, si lo trae significa que
							 * ya existe en base de datos y sólo se modifica, si
							 * no lo trae significa que es uno nuevo que se
							 * tiene que dar de alta desde cero
							 */
							if(docProbatorio.getIdDocumentoProbatorio() != null) {
								// Llamada al método para actualizar un documento probatorio
								try {
									this.documentoProbatorioServiceBusiness.modificarDocumentoProbatorio(docProbatorio);
								} catch (DocumentoProbatorioException e) {
									this.log.error(e);
								}
							}else{
								try {
									// Llamada al método para guardar un documento probatorio nuevo
									this.documentoProbatorioServiceBusiness.registrarAsociarDocumentoProbatorioPersona(
											docProbatorio,fisica.getIdPersona());
								} catch (DocumentoProbatorioException e) {
									this.log.error(e);
								} catch (Exception e) {
									this.log.error(e);
								}
							}
						} else if (docProbatorio.getEstadoAdministracionDocto()
								.getClave() == EstadoAdministracionEnum.ELIMINADO
								.getClave() && docProbatorio.getIdDocumentoProbatorio() != null) {
							/*
							 * Si el doc probatorio a eliminar tiene un id
							 * asignado, significa que está en la base de datos
							 * y realmente se tiene que eliminar, en caso
							 * contrario sólo está en sesión y no es necesario
							 * invoncar al servicio para eliminarlo
							 */
							this.documentoProbatorioServiceBusiness.eliminarDesasociarDocumentoProbatorioPersona(
											docProbatorio, fisica.getIdPersona());
						}
					}
				}
			}
		}
		
		if(datosPersona.getModificarDatosSAT()){
			
			this.log.debug("Se van a modif icar los datos SAT de la persona " + fisica.getIdPersona());
						
			/*
			 * Se checa si la persona ya es una persona fisica (fiscalmente), si
			 * el campo cveFisica viene nulo, significa que aún no es
			 * fiscalmente una persona física y, por lo tanto, se tiene que
			 * crear la persona física asociada a la persona para poder asociarle
			 * tanto el domicilio fiscal, como los medios de contacto fiscales.
			 */
			if(fisica.getCveFisica() == null){
				Fisica fisicaAux = new Fisica();
				fisicaAux.setIdPersona(fisica.getIdPersona());
				fisicaAux.setRfc(fisica.getRfc());
				
				fisicaAux = this.personaFisicaServiceBusiness.guardarPersonaFisica(fisicaAux);
				fisica.setCveFisica(fisicaAux.getCveFisica());
			}
			
			if(datosPersona.getModificarRFC()){
				// Modificar datos fiscales
				try {
					this.personaFisicaServiceBusiness.afectarDatosPersonaFisica(datosPersona);
				} catch (PersonaFisicaNoEncontradaException e) {
					this.log.error(e);
				}
			}
						
			if(datosPersona.getModificarDomicilioFiscal()){
				DomicilioFiscal domFiscal = fisica.getDomicilioFiscal();
				
				if(domFiscal.getClave() != null && domFiscal.getClave() > 0){
					// Se debe actualizar el domicilio fiscal
					try {
						this.domicilioServiceBusiness.modificarDomicilioFiscal(domFiscal);
					} catch (DomicilioNoValidoException e) {
						this.log.error(e);
					}
				} else {
					// Se dio de alta domicilio fiscal
					try {
						domFiscal = this.domicilioServiceBusiness.registrarDomicilioFiscal(domFiscal);
						// Se crea la asociación entre la persona y el domicilio fiscal
						this.domicilioServiceBusiness.asociarDomicilioFiscalPersonaFisica(domFiscal.getClave(),
								fisica.getCveFisica());
					} catch (DomicilioNoValidoException e) {
						this.log.error(e);
					} catch (AsociarDomicilioException e) {
						this.log.error(e);
					} 
				}
			}
			
			if(datosPersona.getModificarMediosContactoFiscales()){
				ListIterator<MedioContacto> itMediosFiscales = fisica.getMediosContactoFiscales().listIterator();
				
				MedioContacto medioFiscal = null;
				while(itMediosFiscales.hasNext()){
					medioFiscal = itMediosFiscales.next();
					
					if (medioFiscal.getEstadoAdministracionMedioContacto() != null){
						if (medioFiscal.getEstadoAdministracionMedioContacto().getClave() == EstadoAdministracionEnum.NUEVO
								.getClave()) {
							// Se guarda el medio de contacto nuevo
							try {
								medioFiscal = this.mediosContactoServiceBusiness
										.registrarAsociarMedioContactoFiscalPersonaFisica(medioFiscal, fisica.getCveFisica());
							} catch (RegistrarMedioContactoException e) {
								this.log.error(e);
							}
						} else if (medioFiscal.getEstadoAdministracionMedioContacto().getClave() == EstadoAdministracionEnum.MODIFICADO
								.getClave()) {
							/*
							 * Cuando es modificación se valida si el medio de 
							 * contacto trae el ID, si lo trae significa que
							 * ya existe en base de datos y sólo se modifica, si
							 * no lo trae significa que es uno nuevo que se
							 * tiene que dar de alta desde cero
							 */
							if(medioFiscal.getClave() != null){
								try {
									this.mediosContactoServiceBusiness.actualizarMedioDeContacto(medioFiscal);
								} catch (RegistrarMedioContactoException e) {
									this.log.error(e);
								}
							}else{
								try {
									medioFiscal = this.mediosContactoServiceBusiness
											.registrarAsociarMedioContactoFiscalPersonaFisica(medioFiscal, fisica.getCveFisica());
								} catch (RegistrarMedioContactoException e) {
									this.log.error(e);
								}
							}
						} else if (medioFiscal.getEstadoAdministracionMedioContacto().getClave() == EstadoAdministracionEnum.ELIMINADO
								.getClave() && medioFiscal.getClave() != null) {
							/*
							 * Si el medio de contacto a eliminar tiene un id
							 * asignado, significa que está en la base de datos
							 * y realmente se tiene que eliminar, en caso
							 * contrario sólo está en sesión y no es necesario
							 * invoncar al servicio para eliminarlo
							 */
							try {
								this.mediosContactoServiceBusiness.eliminarMedioDeContacto(medioFiscal.getClave());
							} catch (RegistrarMedioContactoException e) {
								this.log.error(e);
							}
						}
					}
				}
			}
			
			if(datosPersona.getModificarSituacion()){
				try {
					/*
					 * Se busca la situacion actual, para poderla expirar. Se
					 * obtiene la posicion 0, ya que una persona sólo puede
					 * tener una situación activa
					 */
					SituacionSAT situacionActual = null; 
					List<SituacionSAT> situaciones = this.situacionSATServiceBusiness
							.obtenerSituacionesPersona(fisica, true);
					
					if(situaciones != null && !situaciones.isEmpty()){
						situacionActual = situaciones.get(0);
					}
					
					if(situacionActual != null){
						// Se expira la situación SAT actual
						this.situacionSATServiceBusiness.expirar(situacionActual);
					}
					
					if (fisica.getSituacionesSAT() != null
							&& !fisica.getSituacionesSAT().isEmpty()) {
						SituacionSAT situacionSATNueva = fisica
								.getSituacionesSAT().get(0);
						situacionSATNueva.setPersona(fisica);
						
						this.situacionSATServiceBusiness.guardar(situacionSATNueva);
					} else {
						this.log.warn("La persona no cuenta con situacion SAT para dar de alta");
					}
				} catch (SituacionSATNoValidaException e) {
					this.log.warn(e);
				}
			}
			
			if(datosPersona.getModificarFechaCreacion()){
				DatosPersonaSAT datosPersonaSAT = fisica.getDatosPersonaSAT();
				datosPersonaSAT.setPersona(fisica);
				try {
					this.datosPersonaSATServiceBusiness.guardar(datosPersonaSAT);
				} catch (DatosPersonaSATNoValidosException e) {
					this.log.warn(e);
				}
			}
		}
		
		if(datosPersona.getModificarDatosComplementarios()){
			
			this.log.debug("Se van a modificar los datos complementarios de la persona " + fisica.getIdPersona());
	
			if (datosPersona.getModificarDomicilioParticular()) {
				
				this.log.debug("Se van a modificar los domicilios particulares de la persona " + fisica.getIdPersona());
				
				ListIterator<Domicilio> itDomicilios = fisica.getDomicilios().listIterator();
				
				Domicilio domicilio = null;
				while(itDomicilios.hasNext()){
					domicilio = itDomicilios.next();
					
					if(domicilio.getEstadoAdministracionDomicilio() != null){
						if (domicilio.getEstadoAdministracionDomicilio().getClave() == EstadoAdministracionEnum.NUEVO
								.getClave()) {
							try {
								// Se guarda el domicilio nuevo
																
								domicilio = this.domicilioServiceBusiness.registrarDomicilio(domicilio);
								// Se asocia el domicilio a la persona
								this.domicilioServiceBusiness.asociarDomicilioPersona(domicilio, fisica.getIdPersona());
							} catch (DomicilioNoValidoException e) {
								this.log.error(e);
							}
						} else if (domicilio.getEstadoAdministracionDomicilio().getClave() == EstadoAdministracionEnum.MODIFICADO
								.getClave()) {
					
							/*
							 * Cuando es modificación se valida si el medio de 
							 * contacto trae el ID, si lo trae significa que
							 * ya existe en base de datos y sólo se modifica, si
							 * no lo trae significa que es uno nuevo que se
							 * tiene que dar de alta desde cero
							 */
							if(domicilio.getClave() != null){
								try {
									// Se modifica el domicilio
									this.domicilioServiceBusiness.modificarDomicilio(domicilio);
								} catch (TransformacionException e) {
									this.log.error(e);
								}
							}else{
								try {
									// Se guarda el domicilio nuevo
									domicilio = this.domicilioServiceBusiness.registrarDomicilio(domicilio);
									// Se asocia el domicilio a la persona
									this.domicilioServiceBusiness.asociarDomicilioPersona(domicilio, fisica.getIdPersona());
								} catch (DomicilioNoValidoException e) {
									this.log.error(e);
								}
							}
						} else if (domicilio.getEstadoAdministracionDomicilio().getClave() == EstadoAdministracionEnum.ELIMINADO
								.getClave() && domicilio.getClave() != null) {
							/*
							 * Si el domicilio a eliminar tiene un id
							 * asignado, significa que está en la base de datos
							 * y realmente se tiene que eliminar, en caso
							 * contrario sólo estó en sesión y no es necesario
							 * invoncar al servicio para eliminarlo
							 */
							try {
								// Se elimina el domicilio
								this.domicilioServiceBusiness.desasociarEliminarDomicilioPersona(
										domicilio.getClave().longValue(), fisica.getIdPersona());
							} catch (AsociarDomicilioException e) {
								this.log.error(e);
							}
						}
					}
				}
			}

			if (datosPersona.getModificarMediosContactoParticular()) {
				
				modificarMediosContactoPersona(fisica);
				
			}
		}
		
		// Se modifican las calificaciones
		this.personaBusiness.afectarCalificacionesPersona(datosPersona);
		
		// Se modifican los identificadores
		this.personaBusiness.afectarIdentificadoresPersona(datosPersona);
		
	}

	@Override
	public void afectarDatosPersonaMoral(
			AfectarDatosPersonaWrapper datosPersona) {
		
		Moral moral = datosPersona.getMoral();
		
		if(datosPersona.getModificarDatosSAT()){
			
			this.log.debug("Se van a modificar los datos SAT de la persona " + moral.getCveMoral());
			
			if (datosPersona.getModificarRazonSocial()
					|| datosPersona.getModificarTipoSociedad()
					|| datosPersona.getModificarRFC()) {
				// Se modifican los datos SAT
				try {
					this.personaMoralBusiness.afectarDatosPersonaMoral(datosPersona);
				} catch (PersonaMoralNoEncontradaException e) {
					this.log.error(e);
				}
			}			
			
			if(datosPersona.getModificarFechaCreacion()){
				DatosPersonaSAT datosPersonaSAT = moral.getDatosPersonaSAT();
				datosPersonaSAT.setPersona(moral);
				try {
					this.datosPersonaSATServiceBusiness.guardar(datosPersonaSAT);
				} catch (DatosPersonaSATNoValidosException e) {
					this.log.warn(e);
				}
			}
			
			if(datosPersona.getModificarSituacion()){
				try {
					/*
					 * Se busca la situacion actual, para poderla expirar. Se
					 * obtiene la posicion 0, ya que una persona sólo puede
					 * tener una situación activa
					 */
					SituacionSAT situacionActual = null; 
					List<SituacionSAT> situaciones = this.situacionSATServiceBusiness
							.obtenerSituacionesPersona(moral, true);
					
					if(situaciones != null && !situaciones.isEmpty()){
						situacionActual = situaciones.get(0);
					}
					
					if(situacionActual != null){
						// Se expira la situación SAT actual
						this.situacionSATServiceBusiness.expirar(situacionActual);
					}
					
					if (moral.getSituacionesSAT() != null
							&& !moral.getSituacionesSAT().isEmpty()) {
						SituacionSAT situacionSATNueva = moral
								.getSituacionesSAT().get(0);
						situacionSATNueva.setPersona(moral);
						
						this.situacionSATServiceBusiness.guardar(situacionSATNueva);
					} else {
						this.log.warn("La persona no cuenta con situacion SAT para dar de alta");
					}
				} catch (SituacionSATNoValidaException e) {
					this.log.warn(e);
				}
			}
			
			if(datosPersona.getModificarDomicilioFiscal()){
				DomicilioFiscal domFiscal = moral.getDomicilioFiscal();
				
				if(domFiscal.getClave() != null && domFiscal.getClave() > 0){
					// Se debe actualizar el domicilio fiscal
					try {
						this.domicilioServiceBusiness.modificarDomicilioFiscal(domFiscal);
					} catch (DomicilioNoValidoException e) {
						this.log.error(e);
					}
				} else {
					// Se dio de alta domicilio fiscal
					try {
						domFiscal = this.domicilioServiceBusiness.registrarDomicilioFiscal(domFiscal);
						this.domicilioServiceBusiness.asociarDomicilioFiscalPersonaMoral(domFiscal.getClave(), 
								moral.getCveMoral());
					} catch (DomicilioNoValidoException e) {
						this.log.error(e);
					} catch (AsociarDomicilioException e) {
						this.log.error(e);
					}
				}
			}
			
			if(datosPersona.getModificarMediosContactoFiscales()){
				ListIterator<MedioContacto> itMediosFiscales = moral.getMediosContactoFiscales().listIterator();
				
				MedioContacto medioFiscal = null;
				while(itMediosFiscales.hasNext()){
					medioFiscal = itMediosFiscales.next();
					
					if (medioFiscal.getEstadoAdministracionMedioContacto() != null){
						if (medioFiscal.getEstadoAdministracionMedioContacto().getClave() == EstadoAdministracionEnum.NUEVO
								.getClave()) {
							// Se guarda el medio de contacto nuevo
							try {
								medioFiscal = this.mediosContactoServiceBusiness
										.registrarAsociarMedioContactoFiscalPersonaMoral(medioFiscal, moral.getCveMoral());
							} catch (RegistrarMedioContactoException e) {
								this.log.error(e);
							}
						} else if (medioFiscal.getEstadoAdministracionMedioContacto().getClave() == EstadoAdministracionEnum.MODIFICADO
								.getClave()) {
							/*
							 * Cuando es modificación se valida si el medio de 
							 * contacto trae el ID, si lo trae significa que
							 * ya existe en base de datos y sólo se modifica, si
							 * no lo trae significa que es uno nuevo que se
							 * tiene que dar de alta desde cero
							 */
							if(medioFiscal.getClave() != null){
								try {
									this.mediosContactoServiceBusiness.actualizarMedioDeContacto(medioFiscal);
								} catch (RegistrarMedioContactoException e) {
									this.log.error(e);
								}
							}else{
								try {
									medioFiscal = this.mediosContactoServiceBusiness
											.registrarAsociarMedioContactoFiscalPersonaMoral(medioFiscal, moral.getCveMoral());
								} catch (RegistrarMedioContactoException e) {
									this.log.error(e);
								}
							}
						} else if (medioFiscal.getEstadoAdministracionMedioContacto().getClave() == EstadoAdministracionEnum.ELIMINADO
								.getClave() && medioFiscal.getClave() != null) {
							/*
							 * Si el medio de contacto a eliminar tiene un id
							 * asignado, significa que está en la base de datos
							 * y realmente se tiene que eliminar, en caso
							 * contrario sólo está en sesión y no es necesario
							 * invoncar al servicio para eliminarlo
							 */
							try {
								this.mediosContactoServiceBusiness.eliminarMedioDeContacto(medioFiscal.getClave());
							} catch (RegistrarMedioContactoException e) {
								this.log.error(e);
							}
						}
					}
				}
			}
			
		}
		
		if(datosPersona.getModificarDatosComplementarios()){
			
			this.log.debug("Se van a modificar los datos complementarios de la persona " + moral.getCveMoral());
	
			if (datosPersona.getModificarActaConstitutiva()) {
				
				this.log.debug("Se van a modificar la escritura constitutiva de la persona " + moral.getCveMoral());
				
				// Guardar/modificar la escritura constitutiva
				try {
					
					EscrituraConstitutiva escritura = moral.getEscrituraConstitutiva();
					escritura.setCveIdPersonaMoral(moral.getCveMoral());
					
					this.sujetoObligadoServiceBusiness.actualizarEscrituraConstitutiva(escritura);
				} catch (GestionPatronalBusinessException e) {
					this.log.error(e);
				}
			}
			
			if (datosPersona.getModificarRegistroSindicato()) {
				
				this.log.debug("Se van a modificar el registro sindical de la persona " + moral.getCveMoral());
				// Guardar/modificar el registro sindical
				try {
					RegistroSindicato registroSindicato = moral.getRegistroSindicato();
					registroSindicato.setCveIdPersonaMoral(moral.getCveMoral());
					
					this.sujetoObligadoServiceBusiness.actualizarRegistroSindicato(registroSindicato);
				} catch (GestionPatronalBusinessException e) {
					this.log.error(e);
				}
				
			}
		}
		
		// Se modifican las calificaciones
		this.personaMoralBusiness.afectarCalificacionesPersona(datosPersona);
		
		// Se modifican los identificadores
		this.personaMoralBusiness.afectarIdentificadoresPersona(datosPersona);
	}
	
	private AfectarDatosPersonaWrapper convertirTramiteAWrapper(
			TramiteCambioInformacionPersona tramite)
			throws AfectacionDatosPersonaException {

		AfectarDatosPersonaWrapper datosPersona = null;
		
		if (tramite.getDatosICA() != null) {
			// La modificación viene del ICA
			datosPersona = this.afectarDatosPersonaUtility.crearWrapperDesdeICA(tramite);
		} else if (tramite.getDatosModifManual() != null) {
			// La modificación viene de la MDM
			datosPersona = this.afectarDatosPersonaUtility.crearWrapperDesdeModificacionManual(tramite);
		} else {
			throw new AfectacionDatosPersonaException(
					"No se puede generar el wrapper, ya que no se cuenta con la informacion necesaria");
		}
				
		return datosPersona;
	}
	
	/**
	 * Método de prueba
	 * 
	 */
	@Override
	public void crearSolicitudICA(ICADatosRespuesta datosRespuesta)
			throws SolicitudNoValidaException {
		
		TramiteCambioInformacionPersona tramite = new TramiteCambioInformacionPersona();
		tramite.setDatosICA(datosRespuesta);
		
		String xml = JaxbUtil.objectToXml(tramite);
		
		this.log.info("XML DEL TRAMITE:");
		this.log.info(xml);
		
		tramite = null;
		tramite = (TramiteCambioInformacionPersona) JaxbUtil.xmlToObject(xml);
		
		this.log.info("Tramite desde el XML: ");
		this.log.info(tramite);		

	}
	
	/**
	 * Método de prueba
	 * 
	 */
	@Override
	public void crearSolicitudMDM(MDMDatosEntrada datosEntrada)
			throws SolicitudNoValidaException {
		
		TramiteCambioInformacionPersona tramite = new TramiteCambioInformacionPersona();
		tramite.setDatosModifManual(datosEntrada);
		
		String xml = JaxbUtil.objectToXml(tramite);
		
		this.log.info("XML DEL TRAMITE:");
		this.log.info(xml);
		
		tramite = null;
		tramite = (TramiteCambioInformacionPersona) JaxbUtil.xmlToObject(xml);
		
		this.log.info("Tramite desde el XML: ");
		this.log.info(tramite);		

	}
}
