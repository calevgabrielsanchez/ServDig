package mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.ConsumidorServiciosMediosContactoRemote;
import mx.gob.imss.ctirss.delta.exception.medio.contacto.PersonaSinMedioDeContactoException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.enums.EstadoAdministracionEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.Facebook;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.Twitter;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.AfectarDatosPersonaBusinessRemote;

import org.apache.commons.lang.StringUtils;

@Stateless(name = "consumidorServiciosMediosContacto", mappedName = "consumidorServiciosMediosContacto")
public class ConsumidorServiciosMediosContacto extends AbstractServiceBusiness implements ConsumidorServiciosMediosContactoLocal, ConsumidorServiciosMediosContactoRemote{

	@EJB(name = "mediosContactoServiceBusiness", mappedName = "mediosContactoServiceBusiness")
	private MediosContactoServiceBusinessRemote mediosContactoServiceBusinessRemote;
	@EJB(name = "afectarDatosPersonaBusiness", mappedName = "afectarDatosPersonaBusiness")
	private AfectarDatosPersonaBusinessRemote afectarDatosPersonaBusinessRemote;
	
	/**
	 * Metodo para saber que medios actualizar y cuales eliminar
	 * @param fisica
	 */
	@Override
	public void procesaActualizacionEnMedios( Fisica fisica) {
		if(fisica != null && fisica.getIdPersona() != null) {
			this.procesarMedios(fisica);
		}
		
	}
	
	@Override
	public void procesarMediosContactoCorreccion(
			TramiteCorreccionDerechohabiente correccion) {
		if(correccion != null && correccion.getIdPersona() != null) {
			Fisica fisicaMedios = new Fisica();
			fisicaMedios.setIdPersona(correccion.getIdPersona());
			fisicaMedios.setTipoPersona(new TipoPersona());
			fisicaMedios.getTipoPersona().setIdTipoPersona(TipoPersonaEnum.FISICA.getId());
			
			fisicaMedios.setCorreoElectronico(correccion.getCorreoElectronico());
			fisicaMedios.setTelefonoFijo(correccion.getTelefonoFijo());
			fisicaMedios.setTelefonoMovil(correccion.getTelefonoMovil());
			fisicaMedios.setFacebook(correccion.getFacebook());
			fisicaMedios.setTwitter(correccion.getTwitter());
			
			procesarMedios(fisicaMedios);
		}
	}

	private void procesarMedios(Fisica fisica) {
		
		
		if(fisica !=null && fisica.getIdPersona() != null) {
			try {
				List<MedioContacto> listaMedios = new ArrayList<MedioContacto>();
				fisica.setTipoPersona(new TipoPersona());
				fisica.getTipoPersona().setIdTipoPersona(TipoPersonaEnum.FISICA.getId());


				List<MedioContacto> listaMediosActuales = null;
				
				try {
				listaMediosActuales = mediosContactoServiceBusinessRemote.consultarMedioDeContactoPersona(fisica);
				} catch(PersonaSinMedioDeContactoException e) {
					log.error("La persona no cuenta con medios de contacto");
				}
				
				Map<Long, MedioContacto> mapMedios = new HashMap<Long, MedioContacto>();

				if(listaMediosActuales != null && !listaMediosActuales.isEmpty()) {
					for(MedioContacto medioActual: listaMediosActuales) {
						mapMedios.put(medioActual.getClave(), medioActual);
					}
				}

				if (fisica.getCorreoElectronico() != null) {
					CorreoElectronico corr = (CorreoElectronico) this.setBanderaMedioContacto(fisica.getCorreoElectronico());
					if (corr != null) {
						if(corr.getClave() != null){
							try {
								CorreoElectronico anterior = (CorreoElectronico) mapMedios.get(corr.getClave());
								if(anterior != null) {
									if(agregar(corr.getCorreo(),anterior.getCorreo(), corr.getEstadoAdministracionMedioContacto())) {
										listaMedios.add(corr);
									}
								}else {
									listaMedios.add(corr);
								}
							} catch(Exception e) {
								log.error("No se encontro el correo con id dentro de la lista de medios");
							}
						}else {
							listaMedios.add(corr);
						}
					}
				}
				if (fisica.getFacebook() != null) {
					Facebook face = (Facebook) this.setBanderaMedioContacto(fisica.getFacebook());
					if (face != null){
						if(face.getClave() != null){
							try {
								Facebook anterior = (Facebook) mapMedios.get(face.getClave());
								if(anterior != null) {
									if(agregar(face.getCuenta(),anterior.getCuenta(), face.getEstadoAdministracionMedioContacto())) {
										listaMedios.add(face);
									}
								}else {
									listaMedios.add(face);
								}
							} catch(Exception e) {
								log.error("No se encontro el facebook con id dentro de la lista de medios");
							}
						}else {
							listaMedios.add(face);
						}
					}
				}
				if (fisica.getTelefonoFijo() != null) {
					TelefonoFijo telf = (TelefonoFijo) this.setBanderaMedioContacto(fisica.getTelefonoFijo());
					if (telf != null) {
						if(telf.getClave() != null){
							try {
								TelefonoFijo anterior = (TelefonoFijo) mapMedios.get(telf.getClave());
								if(anterior != null) {
									if(agregar(telf.getClaveLada(),anterior.getClaveLada(), telf.getEstadoAdministracionMedioContacto())) {
										listaMedios.add(telf);
									}
								}else {
									listaMedios.add(telf);
								}
							} catch(Exception e) {
								log.error("No se encontro el telefono con id dentro de la lista de medios");
							}
						}else {
							listaMedios.add(telf);
						}
					}
				}
				if (fisica.getTelefonoMovil() != null) {
					TelefonoMovil telm = (TelefonoMovil) this.setBanderaMedioContacto(fisica.getTelefonoMovil());
					if (telm != null) {
						if(telm.getClave() != null){
							try {
								TelefonoMovil anterior = (TelefonoMovil) mapMedios.get(telm.getClave());
								if(anterior != null) {
									if(agregar(telm.getNumero(),anterior.getNumero(), telm.getEstadoAdministracionMedioContacto())) {
										listaMedios.add(telm);
									}
								}else {
									listaMedios.add(telm);
								}
							} catch(Exception e) {
								log.error("No se encontro el movil con id dentro de la lista de medios");
							}
						}else {
							listaMedios.add(telm);
						}
					}
				}
				if (fisica.getTwitter() != null) {
					Twitter twit = (Twitter) this.setBanderaMedioContacto(fisica.getTwitter());
					if (twit != null) {
						if(twit.getClave() != null){
							try {
								Twitter anterior = (Twitter) mapMedios.get(twit.getClave());
								if(anterior != null) {
									if(agregar(twit.getCuenta(),anterior.getCuenta(), twit.getEstadoAdministracionMedioContacto())) {
										listaMedios.add(twit);
									}
								}else {
									listaMedios.add(twit);
								}
							} catch(Exception e) {
								log.error("No se encontro el telefono con id dentro de la lista de medios");
							}
						}else {
							listaMedios.add(twit);
						}
					}
				}

				if (!listaMedios.isEmpty()) {
					fisica.setMediosContacto(listaMedios);
					log.debug("Se actualizaran " + listaMedios.size() + " medios para la persona " + fisica.getIdPersona()); 
					afectarDatosPersonaBusinessRemote.modificarMediosContactoPersona(fisica);
				} 

			} catch(Exception e) {
				log.error("Ocurrio un error al guardar los medios de contacto", e);
			}
		}
		
	}
	
	private boolean agregar(String medioAnterior, String medioActual,EstadoAdministracionEnum estadoAdm) {
		
		if(estadoAdm.getClave() == EstadoAdministracionEnum.MODIFICADO.getClave()){
			if(medioAnterior == null) {
				if(medioActual == null) {
					return false;
				}
			} else {
				if(medioActual != null) {
					if(StringUtils.isBlank(medioActual.trim())) {
						return false;
					} else {
						if(medioActual.trim().equals(medioAnterior.trim())) {
							return false;
						}
					}
				}
			}
		} else if(estadoAdm.getClave() == EstadoAdministracionEnum.NUEVO.getClave()) {
			if(medioActual != null) {
				if(StringUtils.isBlank(medioActual.trim())) {
					return false;
				}
			} else {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Metodo para setear la bandera de medios de contacto
	 * @param medio
	 * @return
	 */
	private MedioContacto setBanderaMedioContacto(MedioContacto medio) {
		if(medio != null) {
			if (medio.getClave() != null) {
				if (medio instanceof CorreoElectronico) {
					CorreoElectronico correo = (CorreoElectronico) medio;
					if (StringUtils.isNotBlank(correo.getCorreo())) {
						correo.setEstadoAdministracionMedioContacto(EstadoAdministracionEnum.MODIFICADO);
						return correo;
					} else {
						correo.setEstadoAdministracionMedioContacto(EstadoAdministracionEnum.ELIMINADO);
						return correo;
					}
				} else if (medio instanceof Facebook) {
					Facebook facebook = (Facebook) medio;
					if (StringUtils.isNotBlank(facebook.getCuenta())) {
						facebook.setEstadoAdministracionMedioContacto(EstadoAdministracionEnum.MODIFICADO);
						return facebook;
					} else {
						facebook.setEstadoAdministracionMedioContacto(EstadoAdministracionEnum.ELIMINADO);
						return facebook;
					}
				} else if (medio instanceof TelefonoFijo) {
					TelefonoFijo telefonoFijo = (TelefonoFijo) medio;
					if (StringUtils.isNotBlank(telefonoFijo.getClaveLada())) {
						telefonoFijo.setEstadoAdministracionMedioContacto(EstadoAdministracionEnum.MODIFICADO);
						return telefonoFijo;
					} else {
						telefonoFijo.setEstadoAdministracionMedioContacto(EstadoAdministracionEnum.ELIMINADO);
						return telefonoFijo;
					}
				} else if (medio instanceof TelefonoMovil) {
					TelefonoMovil telefonoMovil = (TelefonoMovil) medio;
					if (StringUtils.isNotBlank(telefonoMovil.getNumero())) {
						telefonoMovil.setEstadoAdministracionMedioContacto(EstadoAdministracionEnum.MODIFICADO);
						return telefonoMovil;
					} else {
						telefonoMovil.setEstadoAdministracionMedioContacto(EstadoAdministracionEnum.ELIMINADO);
						return telefonoMovil;
					}
				} else if (medio instanceof Twitter) {
					Twitter twitter = (Twitter) medio;
					if (StringUtils.isNotBlank(twitter.getCuenta())) {
						twitter.setEstadoAdministracionMedioContacto(EstadoAdministracionEnum.MODIFICADO);
						return twitter;
					} else {
						twitter.setEstadoAdministracionMedioContacto(EstadoAdministracionEnum.ELIMINADO);
						return twitter;
					}
				}
			} else {
				if (medio instanceof CorreoElectronico) {
					CorreoElectronico correo = (CorreoElectronico) medio;
					if (StringUtils.isNotBlank(correo.getCorreo())) {
						correo.setEstadoAdministracionMedioContacto(EstadoAdministracionEnum.NUEVO);
						return correo;
					} else {
						return null;
					}
				} else if (medio instanceof Facebook) {
					Facebook facebook = (Facebook) medio;
					if (StringUtils.isNotBlank(facebook.getCuenta())) {
						facebook.setEstadoAdministracionMedioContacto(EstadoAdministracionEnum.NUEVO);
						return facebook;
					} else {
						return null;
					}
				} else if (medio instanceof TelefonoFijo) {
					TelefonoFijo telefonoFijo = (TelefonoFijo) medio;
					if (StringUtils.isNotBlank(telefonoFijo.getClaveLada())) {
						telefonoFijo.setEstadoAdministracionMedioContacto(EstadoAdministracionEnum.NUEVO);
						return telefonoFijo;
					} else {
						return null;
					}
				} else if (medio instanceof TelefonoMovil) {
					TelefonoMovil telefonoMovil = (TelefonoMovil) medio;
					if (StringUtils.isNotBlank(telefonoMovil.getNumero())) {
						telefonoMovil.setEstadoAdministracionMedioContacto(EstadoAdministracionEnum.NUEVO);
						return telefonoMovil;
					} else {
						return null;
					}
				} else if (medio instanceof Twitter) {
					Twitter twitter = (Twitter) medio;
					if (StringUtils.isNotBlank(twitter.getCuenta())) {
						twitter.setEstadoAdministracionMedioContacto(EstadoAdministracionEnum.NUEVO);
						return twitter;
					} else {
						return null;
					}
				}
			}
		}

		return null;
	}
}
