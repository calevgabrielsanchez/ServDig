package mx.gob.imss.ctirss.gestionpersonas.servicios.entity;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import mx.gob.imss.ctirss.delta.gestion.individuo.util.Utilerias;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.CodigoPostal;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.domicilio.Pais;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoAmbito;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoVialidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Vialidad;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Nacimiento;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Calificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.EstadoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaEstado;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.CorreoElectronico;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoFijo;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TelefonoMovil;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.TipoMedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSociedad;

public class SolicitudConversor {

    public static Solicitud convertirXmlToModel(mx.gob.imss.ctirss.delta.model.xml.solicitud.Solicitud solicitudXml) {
		Solicitud solicitudModelo = null;
		
		//VERIFICA QUE LA SOLICITUD NO ESTE VACIA
		if (solicitudXml != null){
			
			//ASIGNAMOS SOLICITUD
			solicitudModelo = new Solicitud();
			solicitudModelo.setIdSolicitud( solicitudXml.getIdSolicitud() );
			solicitudModelo.setIdEstadoSolicitud( solicitudXml.getIdEstadoSolicitud() );
			solicitudModelo.setFechaRegistro( solicitudXml.getFechaRegistro() );
			solicitudModelo.setDesEstadoSolicitud( solicitudXml.getDesEstadoSolicitud() );
			
			//ASIGNAMOS TRAMITES
			if (solicitudXml.getTramite() != null) {
				for (mx.gob.imss.ctirss.delta.model.xml.solicitud.Tramite tramiteXml: solicitudXml.getTramite()) {
				    solicitudModelo.getTramite().add(convertirXmlToModel(tramiteXml));
				}
			}
				
		}
		return solicitudModelo;
	}

    public static Tramite convertirXmlToModel(mx.gob.imss.ctirss.delta.model.xml.solicitud.Tramite tramiteXml) {
        //TRAMITE
        Tramite tramiteModelo = new Tramite();
        tramiteModelo.setIdTramite( tramiteXml.getIdTramite() );
        
        //TIPO TRAMITE
        if (tramiteXml.getTipoTramite() != null){
        	TipoTramite tipoTramiteModelo = new TipoTramite();
        	tipoTramiteModelo.setIdTipoTramite( tramiteXml.getTipoTramite().getIdTipoTramite() ); 
        	tipoTramiteModelo.setDesTipoTramite( tramiteXml.getTipoTramite().getDescripcion());
        	tramiteModelo.setTipoTramite(tipoTramiteModelo);						
        }
        
        //ESTADO TRAMITE
        if (tramiteXml.getEstadoTramite() != null){
        	tramiteModelo.setIdEstadoTramite(tramiteXml.getEstadoTramite().getIdEstadoTramitePersona().longValue());
        	tramiteModelo.setDesEstadoTramite(tramiteXml.getEstadoTramite().getDescripcion());
        }
        					
        //RAZON RESULTADO
        if ( tramiteXml.getRazonResultado() != null){
        	tramiteModelo.setIdRazonResultado(tramiteXml.getRazonResultado().getIdRazonResultado());
        	tramiteModelo.setDesRazonResultado(tramiteXml.getRazonResultado().getDescripcion());
        }		
        
        //ESTADO PERSONA
        List<PersonaEstado> listaEstados = null;
        if( tramiteXml.getPersona().getPersonaEstado() != null){
        	listaEstados = new LinkedList<PersonaEstado>();
        	
        	for(mx.gob.imss.ctirss.delta.model.xml.solicitud.PersonaEstado personaEstadoXml : tramiteXml.getPersona().getPersonaEstado()){
        		PersonaEstado personaEstadoModelo = new PersonaEstado();
        		EstadoPersona estadoPersonaModelo = new EstadoPersona();
        		estadoPersonaModelo.setIdEstadoPersona(Utilerias.convertir(personaEstadoXml.getEstadoPersona().getIdEstadoPersona()));
        		estadoPersonaModelo.setDescripcion(personaEstadoXml.getEstadoPersona().getDescripcion());
        		personaEstadoModelo.setEstadoPersona(estadoPersonaModelo);	
        		listaEstados.add(personaEstadoModelo);
        	}
        }//ESTADO PERSONA

        //CALIFICACION
        List<PersonaCalificacion> listaCalificaciones = null;
        if( tramiteXml.getPersona().getPersonaCalificacion() != null){
        	listaCalificaciones = new LinkedList<PersonaCalificacion>();
        	
        	for (mx.gob.imss.ctirss.delta.model.xml.solicitud.PersonaCalificacion personaCalificacionXml : tramiteXml.getPersona().getPersonaCalificacion()){
        		PersonaCalificacion personaCalificacionModelo = new PersonaCalificacion();
        		Calificacion calificacionModelo = new Calificacion();
        		calificacionModelo.setIdCalificacion(Utilerias.convertir(personaCalificacionXml.getCalificacion().getIdCalificacion()));
        		calificacionModelo.setDescripcion(personaCalificacionXml.getCalificacion().getDescripcion());
        		personaCalificacionModelo.setCalificacion(calificacionModelo);
        		listaCalificaciones.add(personaCalificacionModelo);
        	}
        }//CALIFICACION
        
        //DOCUMENTOS PROBATORIOS - ACTA NACIMIENTO
        List<DocumentoProbatorio> listaDocumentosProbatoriosModelo = null;
        Nacimiento nacimientoModelo = null;
        if (tramiteXml.getPersona().getDocumentoProbatorio() != null){
        	
        	for (mx.gob.imss.ctirss.delta.model.xml.solicitud.DocumentoProbatorio documentoProbatorioXml : tramiteXml.getPersona().getDocumentoProbatorio()){
        		if(documentoProbatorioXml.getActa()!= null){
        			
        			//VERIFICA SI EL DOCUMENTO PROBATORIO ES UN ACTA DE NACIMIENTO
        			if(documentoProbatorioXml.getActa().getNacimiento()!= null){
        				nacimientoModelo = new Nacimiento();
        				listaDocumentosProbatoriosModelo = new LinkedList<DocumentoProbatorio>();
        				listaDocumentosProbatoriosModelo.add(nacimientoModelo);
        				nacimientoModelo.setIdDocumentoProbatorio(documentoProbatorioXml.getIdDocumentoProbatorio());
        				nacimientoModelo.setAnio(documentoProbatorioXml.getActa().getNacimiento().getAnio());
        				nacimientoModelo.setCrip(documentoProbatorioXml.getActa().getNacimiento().getCrip());
        				nacimientoModelo.setTomo(documentoProbatorioXml.getActa().getNacimiento().getTomo());
        				nacimientoModelo.setNoFoja(documentoProbatorioXml.getActa().getFoja());
        				nacimientoModelo.setNoLibro(documentoProbatorioXml.getActa().getLibro());
        				nacimientoModelo.setNoActa(documentoProbatorioXml.getActa().getNumeroActa());
        				
        				//VERIFICA SI EXISTE EL MUNICIPIO
        				if(documentoProbatorioXml.getActa().getClaveMunicipioRegistro() != null && !documentoProbatorioXml.getActa().getClaveMunicipioRegistro().equals("")){
        					Municipio municipioModelo = new Municipio();
        					municipioModelo.setClave(documentoProbatorioXml.getActa().getClaveMunicipioRegistro());
        					municipioModelo.setNombre(documentoProbatorioXml.getActa().getDesMunicipioRegistro());
        					nacimientoModelo.setMunicipio(municipioModelo);
        					
        					//VERIFICA SI EXISTE LA ENTIDAD FEDERATIVA
        					if(documentoProbatorioXml.getActa().getClaveEntidadRegistro() != null && !documentoProbatorioXml.getActa().getClaveEntidadRegistro().equals("")){
        						EntidadFederativa entidadFederativaModelo = new EntidadFederativa();
        						entidadFederativaModelo.setClave(documentoProbatorioXml.getActa().getClaveEntidadRegistro());
        						entidadFederativaModelo.setNombre(documentoProbatorioXml.getActa().getDesEntidadRegistro());
        						municipioModelo.setEntidadFederativa(entidadFederativaModelo);
        					}
        					
        				}//VERIFICA SI EXISTE EL MUNICIPIO
        				
        			}//VERIFICA SI EL DOCUMENTO PROBATORIO ES UN ACTA DE NACIMIENTO
        		}
        	}
        }//DOCUMENTOS PROBATORIOS - ACTA NACIMIENTO
        
        //MEDIOS CONTACTOS
        List<MedioContacto> listaMedioContacto = null;
        TelefonoFijo telefonoFijoModelo = null;
        TelefonoMovil telefonoMovilModelo = null;
        CorreoElectronico correoElectronicoModelo = null;
        
        if(tramiteXml.getPersona().getMedioContacto() != null){
        	
        	for (mx.gob.imss.ctirss.delta.model.xml.solicitud.MedioContacto medioContactoXml : tramiteXml.getPersona().getMedioContacto()){
        		if (listaMedioContacto == null){
        			listaMedioContacto = new LinkedList<MedioContacto>();
        		}
        				
        		//MEDIOS CONTACTOS - TIPO MEDIO CONTACTO
        		TipoMedioContacto tipoMedioContactoModelo = null;
        		if (medioContactoXml.getTipoMedioContacto() != null){
        			tipoMedioContactoModelo = new TipoMedioContacto();
        			tipoMedioContactoModelo.setIdTipoMedioContacto(Utilerias.convertir(medioContactoXml.getTipoMedioContacto().getIdTipoMedioContacto()));
        			tipoMedioContactoModelo.setDescripcion(medioContactoXml.getTipoMedioContacto().getDescripcion());
        		}
        		
        		//MEDIOS CONTACTOS - TELEFONO FIJO
        		if (medioContactoXml.getTelefonoFijo() != null){
        			telefonoFijoModelo = new TelefonoFijo(
        					medioContactoXml.getTelefonoFijo().getNumeroTelefonico(),
        					medioContactoXml.getTelefonoFijo().getClaveLada(),
        					medioContactoXml.getTelefonoFijo().getExtension());
        			telefonoFijoModelo.setClave(Utilerias.convertir(medioContactoXml.getIdMedioContacto()));
        			telefonoFijoModelo.setTipoMedioContacto(tipoMedioContactoModelo);
        			listaMedioContacto.add(telefonoFijoModelo);
        		}

        		//MEDIOS CONTACTOS - TELEFONO MOVIL
        		if (medioContactoXml.getTelefonoMovil() != null){
        			telefonoMovilModelo = new TelefonoMovil();
        			telefonoMovilModelo.setClave(Utilerias.convertir(medioContactoXml.getIdMedioContacto()));
        			telefonoMovilModelo.setNumero(medioContactoXml.getTelefonoMovil().getNumeroTelefonico());
        			telefonoMovilModelo.setTipoMedioContacto(tipoMedioContactoModelo);
        			listaMedioContacto.add(telefonoMovilModelo);
        		}
        		
        		//MEDIOS CONTACTOS - CORREO ELECTRONICO
        		if (medioContactoXml.getCorreoElectronico() != null){
        			correoElectronicoModelo = new CorreoElectronico();
        			correoElectronicoModelo.setClave(Utilerias.convertir( medioContactoXml.getIdMedioContacto()));
        			correoElectronicoModelo.setCorreo(medioContactoXml.getCorreoElectronico().getCorreoElectronico());
        			correoElectronicoModelo.setTipoMedioContacto(tipoMedioContactoModelo);
        			listaMedioContacto.add(correoElectronicoModelo);
        		}						
        	}
        }//MEDIOS CONTACTOS
        
        //DOMICILIOS
        List<Domicilio> listaDomicilios = null;	
        if( tramiteXml.getPersona().getDomicilio() != null){
        	listaDomicilios = new LinkedList<Domicilio>();
        	
        	for (mx.gob.imss.ctirss.delta.model.xml.solicitud.Domicilio domicilioXml : tramiteXml.getPersona().getDomicilio()){
        		Domicilio domicilioModelo = new Domicilio();
        		domicilioModelo.setClave(domicilioXml.getClave());
        	//	domicilioModelo.setLatitud(domicilioXml.getLatitud());
        		//domicilioModelo.setLongitud(domicilioXml.getLongitud());
        		domicilioModelo.setNumExterior1(domicilioXml.getNumExterior1());
        		domicilioModelo.setNumExterior2(domicilioXml.getNumExterior2());
        		domicilioModelo.setNumExteriorAlf(domicilioXml.getNumExteriorAlf());
        		domicilioModelo.setNumInterior(domicilioXml.getNumInterior());
        		domicilioModelo.setNumInteriorAlf(domicilioXml.getNumInteriorAlf());
        		domicilioModelo.setDescripcion(domicilioXml.getDescripcion());
        		
        		//TIPO DOMICILIO
        		if (domicilioXml.getTipoDomicilio() != null){
        			TipoDomicilio tipoDomicilio = new TipoDomicilio();
        			tipoDomicilio.setClave(domicilioXml.getTipoDomicilio().getIdTipoDomicilio());
        			tipoDomicilio.setDescripcion(domicilioXml.getTipoDomicilio().getDescripcion());
        			domicilioModelo.setTipoDomicilio(tipoDomicilio);
        		}
        		
        		//AMBITO
        		if (domicilioXml.getTipoAmbito() != null){
        			TipoAmbito tipoAmbitoModelo = new TipoAmbito();
        			tipoAmbitoModelo.setClave( Utilerias.convertir(domicilioXml.getTipoAmbito().getClave()));
        			tipoAmbitoModelo.setDescripcion(domicilioXml.getTipoAmbito().getDescripcion());
        			domicilioModelo.setAmbito(tipoAmbitoModelo);
        		}
        		
        		//CODIGO POSTAL - NIVEL DOMICILIO
        		if (domicilioXml.getCodigoPostal() != null){
        			CodigoPostal codigoPostalModelo = new CodigoPostal();
        			codigoPostalModelo.setCodigoPostal(""+domicilioXml.getCodigoPostal().getCodigoPostal());
        			domicilioModelo.setCodigoPostal(codigoPostalModelo);
        		}

        		//ASENTAMIENTO
        		if (domicilioXml.getAsentamiento() != null){
        	        Asentamiento asentamientoModelo = new Asentamiento();
        	        asentamientoModelo.setClave(domicilioXml.getAsentamiento().getClave());
        	        asentamientoModelo.setNombre(domicilioXml.getAsentamiento().getNombre());
        	        domicilioModelo.setAsentamiento(asentamientoModelo);
        	        
        	        //CODIGO POSTAL - NIVEL ASENTAMIENTO
        	        if (domicilioXml.getAsentamiento().getCodigoPostal() != null){
        				CodigoPostal codigoPostalModelo = new CodigoPostal();
        				codigoPostalModelo.setCodigoPostal(""+domicilioXml.getAsentamiento().getCodigoPostal().getCodigoPostal());
        	        	asentamientoModelo.setCodigoPostal(codigoPostalModelo);	
        	        }
        			
        			//LOCALIDAD
        			if (domicilioXml.getAsentamiento().getLocalidad()!= null){
        		        Localidad localidadModelo = new Localidad();
        		        localidadModelo.setClave(domicilioXml.getAsentamiento().getLocalidad().getClave());
        		        localidadModelo.setNombre(domicilioXml.getAsentamiento().getLocalidad().getNombre());
        		        asentamientoModelo.setLocalidad(localidadModelo);

        				//MUNICIPIO
        				if (domicilioXml.getAsentamiento().getLocalidad().getMunicipio() != null){
        			        Municipio municipioModelo = new Municipio();
        			        municipioModelo.setClave(domicilioXml.getAsentamiento().getLocalidad().getMunicipio().getClave());
        					municipioModelo.setNombre(domicilioXml.getAsentamiento().getLocalidad().getMunicipio().getDescripcion());
        					localidadModelo.setMunicipio(municipioModelo);
        					
        					//ENTIDAD FEDERATIVA
        					if (domicilioXml.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa() != null){
        				        EntidadFederativa entidadFederativaModelo = new EntidadFederativa();
        				        if(domicilioXml.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getIdEntidadFederativa() != null && !domicilioXml.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getIdEntidadFederativa().equals("")){	
        				        	entidadFederativaModelo.setClave(String.valueOf(domicilioXml.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getIdEntidadFederativa()));
        						}
        				        entidadFederativaModelo.setNombre(domicilioXml.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getDescripcion());
        				        municipioModelo.setEntidadFederativa(entidadFederativaModelo);
        					}
        				}//MUNICIPIO
        			}//LOCALIDAD
        		}//ASENTAMIENTO
        		
        		//VIALIDAD PRIMARIA
        		if (domicilioXml.getVialidadPrimaria() != null){
        			Vialidad vialidadModelo = new Vialidad();
        			vialidadModelo.setClave(domicilioXml.getVialidadPrimaria().getVialidad().getClave());
        			vialidadModelo.setNombre(domicilioXml.getVialidadPrimaria().getVialidad().getNombre());
        			
        			if (domicilioXml.getVialidadPrimaria().getVialidad().getTipoVialidad() != null){
        				TipoVialidad tipoVialidadModelo = new TipoVialidad();
        				tipoVialidadModelo.setClave(domicilioXml.getVialidadPrimaria().getVialidad().getTipoVialidad().getClave());
        				tipoVialidadModelo.setDescripcion(domicilioXml.getVialidadPrimaria().getVialidad().getTipoVialidad().getDescripcion());
        				vialidadModelo.setTipoVialidad(tipoVialidadModelo);									
        			}
        			domicilioModelo.setVialidadPrimaria(vialidadModelo);
        		}							

        		//VIALIDAD REFERNCIA POSTERIOR
        		if (domicilioXml.getVialidadReferenciaPosterior() != null){
        			
        			Vialidad vialidadModelo = new Vialidad();
        			vialidadModelo.setClave(domicilioXml.getVialidadReferenciaPosterior().getVialidad().getClave());
        			vialidadModelo.setNombre(domicilioXml.getVialidadReferenciaPosterior().getVialidad().getNombre());

        			if (domicilioXml.getVialidadReferenciaPosterior().getVialidad().getTipoVialidad() != null){
        				TipoVialidad tipoVialidadModelo = new TipoVialidad();
        				tipoVialidadModelo.setClave(domicilioXml.getVialidadReferenciaPosterior().getVialidad().getTipoVialidad().getClave());
        				tipoVialidadModelo.setDescripcion(domicilioXml.getVialidadReferenciaPosterior().getVialidad().getTipoVialidad().getDescripcion());
        				vialidadModelo.setTipoVialidad(tipoVialidadModelo);									
        			}
        			
        			domicilioModelo.setVialidadReferenciaPosterior(vialidadModelo);
        		}							

        		//VIALIDAD REFERNCIA PRIMARIA
        		if (domicilioXml.getVialidadReferenciaPrimaria() != null){
        			Vialidad vialidadModelo = new Vialidad();
        			vialidadModelo.setClave(domicilioXml.getVialidadReferenciaPrimaria().getVialidad().getClave());
        			vialidadModelo.setNombre(domicilioXml.getVialidadReferenciaPrimaria().getVialidad().getNombre());
        			
        			if (domicilioXml.getVialidadReferenciaPrimaria().getVialidad().getTipoVialidad() != null){
        				TipoVialidad tipoVialidadModelo = new TipoVialidad();								
        				tipoVialidadModelo.setClave(domicilioXml.getVialidadReferenciaPrimaria().getVialidad().getTipoVialidad().getClave());
        				tipoVialidadModelo.setDescripcion(domicilioXml.getVialidadReferenciaPrimaria().getVialidad().getTipoVialidad().getDescripcion());
        				vialidadModelo.setTipoVialidad(tipoVialidadModelo);									
        			}
        			
        			domicilioModelo.setVialidadReferenciaPrimaria(vialidadModelo);
        		}							

        		//VIALIDAD REFERNCIA SECUNDARIA
        		if (domicilioXml.getVialidadReferenciaSecundaria() != null){
        			Vialidad vialidadModelo = new Vialidad();
        			vialidadModelo.setClave(domicilioXml.getVialidadReferenciaSecundaria().getVialidad().getClave());
        			vialidadModelo.setNombre(domicilioXml.getVialidadReferenciaSecundaria().getVialidad().getNombre());
        			
        			if (domicilioXml.getVialidadReferenciaSecundaria().getVialidad().getTipoVialidad() != null){
        				TipoVialidad tipoVialidadModelo = new TipoVialidad();
        				tipoVialidadModelo.setClave(domicilioXml.getVialidadReferenciaSecundaria().getVialidad().getTipoVialidad().getClave());
        				tipoVialidadModelo.setDescripcion(domicilioXml.getVialidadReferenciaSecundaria().getVialidad().getTipoVialidad().getDescripcion());
        				vialidadModelo.setTipoVialidad(tipoVialidadModelo);									
        			}
        			
        			domicilioModelo.setVialidadReferenciaSecundaria(vialidadModelo);
        		}							
        		
        		listaDomicilios.add(domicilioModelo);
        	}
        }
        
        //ASIGNA PERSONA FISICA
        if (tramiteXml.getPersona().getFisica() != null){
        	
        	//GENERA LA PERSONA FISICA Y LA ASIGNA AL TRAMITE
        	mx.gob.imss.ctirss.delta.model.xml.solicitud.Fisica fisicaXml = tramiteXml.getPersona().getFisica();
        	Fisica fisicaModelo = new Fisica();
        	tramiteModelo.setPersonaFisica(fisicaModelo);						
        	
        	//FISICA
        	fisicaModelo.setIdPersona(tramiteXml.getPersona().getIdPersona());
        	fisicaModelo.setNombre( fisicaXml.getNombres() );
        	fisicaModelo.setPrimerApellido( fisicaXml.getPrimerApellido() );
        	fisicaModelo.setSegundoApellido( fisicaXml.getSegundoApellido() );
        	fisicaModelo.setFechaNacimiento( fisicaXml.getFechaNacimiento() );
        	fisicaModelo.setCurp( fisicaXml.getCurp() );
        	fisicaModelo.setRfc( fisicaXml.getRfc() );
        	
        	//ENTIDAD NACIMIENTO
        	if (fisicaXml.getEntidadFederativa() !=null && fisicaXml.getEntidadFederativa().getIdEntidadFederativa() != null) {
        		fisicaModelo.getLugarNacimiento().setClave(fisicaXml.getEntidadFederativa().getIdEntidadFederativa().toString());
        		fisicaModelo.getLugarNacimiento().setNombre(fisicaXml.getEntidadFederativa().getDescripcion());							
        	}
        	
        	//SEXO
            if (fisicaXml.getSexo().getIdSexo() != null){
            	Sexo sexoModelo = new Sexo();
            	sexoModelo.setIdSexo( fisicaXml.getSexo().getIdSexo() );
            	sexoModelo.setDescripcion( fisicaXml.getSexo().getDescripcion() );
            	fisicaModelo.setSexo(sexoModelo);                         
            }
        	
        	//PAIS
        	if (fisicaXml.getPais() != null){
        		Pais paisModelo = new Pais();
        		paisModelo.setIdPais( fisicaXml.getPais().getIdPais() );
        		paisModelo.setDescripcion( fisicaXml.getPais().getDescripcion() );
        		fisicaModelo.setPais( paisModelo );
        	}					
        	
        	//ESTADO PERSONA
        	fisicaModelo.setPersonaEstados(listaEstados);
        	
        	//CALIFICACION
        	fisicaModelo.setPersonaCalificaciones(listaCalificaciones);
        
        	//MEDIOS CONTACTO
        	//fisicaModelo.setMediosContacto(listaMedioContacto); EL OBJETO PERSONA YA SETEA LA LISTA AUTOMATICAMENTE
        	if (telefonoFijoModelo != null){
        		fisicaModelo.setTelefonoFijo(telefonoFijoModelo);	
        	}
        	if (telefonoMovilModelo != null){
        		fisicaModelo.setTelefonoMovil(telefonoMovilModelo);	
        	}
        	if (correoElectronicoModelo != null){
        		fisicaModelo.setCorreoElectronico(correoElectronicoModelo);	
        	}
        	
        	//DOCUMENTOS PROBATORIOS - NACIMIENTO
        	fisicaModelo.setActaNacimiento(nacimientoModelo);
        	fisicaModelo.setDocumentosProbatorios(listaDocumentosProbatoriosModelo);
        	
        	//DOMICILIOS
        	fisicaModelo.setDomicilios(listaDomicilios);
        	
        }//ASIGNA PERSONA FISICA
        
        //ASIGNA PERSONA MORAL
        if (tramiteXml.getPersona().getMoral() != null){
        	
        	//GENERA LA PERSONA FISICA Y LA ASIGNA AL TRAMITE
        	mx.gob.imss.ctirss.delta.model.xml.solicitud.Moral moralXml = tramiteXml.getPersona().getMoral();
        	mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral moralModelo = new mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral();
        	tramiteModelo.setPersonaMoral(moralModelo);				
        	
        	//MORAL
        	moralModelo.setIdPersona(tramiteXml.getPersona().getIdPersona());
        	moralModelo.setRazonSocial( moralXml.getRazonSocial() );
        	moralModelo.setRfc( moralXml.getRfc() );
        	moralModelo.setActaConstitutiva( moralXml.getActaConstitutiva() );
        	moralModelo.setFechaCreacion(moralXml.getFechaCreacion());
        	
        	//TIPO DE SOCIEDAD
        	if (moralXml.getTipoSociedad() != null){
        	    moralModelo.setTipoSociedad(new TipoSociedad());
                moralModelo.getTipoSociedad().setIdTipoSociedad( Utilerias.convertir(moralXml.getTipoSociedad().getIdTipoSociedad()) );
        		moralModelo.getTipoSociedad().setDescripcion( moralXml.getTipoSociedad().getDescripcion() );
        		moralModelo.getTipoSociedad().setDescripcionAbreviada( moralXml.getTipoSociedad().getDescripcion() );
        	}
        	
        	//ESTADO PERSONA
        	moralModelo.setPersonaEstados(listaEstados);
        	
        	//CALIFICACION
        	moralModelo.setPersonaCalificaciones(listaCalificaciones);
        	
        	//MEDIOS CONTACTO
        	//moralModelo.setMediosContacto(listaMedioContacto); EL OBJETO PERSONA YA SETEA LA LISTA AUTOMATICAMENTE
        	if (telefonoFijoModelo != null){
        		moralModelo.setTelefonoFijo(telefonoFijoModelo);	
        	}
        	if (telefonoMovilModelo != null){
        		moralModelo.setTelefonoMovil(telefonoMovilModelo);	
        	}
        	if (correoElectronicoModelo != null){
        		moralModelo.setCorreoElectronico(correoElectronicoModelo);	
        	}
        	
        	//DOCUMENTOS PROBATORIOS - NACIMIENTO
        	moralModelo.setActaNacimiento(nacimientoModelo);
        	moralModelo.setDocumentosProbatorios(listaDocumentosProbatoriosModelo);
        	
        	//DOMICILIOS
        	moralModelo.setDomicilios(listaDomicilios);
        }//ASIGNA PERSONA MORAL
        return tramiteModelo;
    }
	
	public static mx.gob.imss.ctirss.delta.model.xml.solicitud.Solicitud convertirModelToXml(Solicitud solicitudModelo) {
		mx.gob.imss.ctirss.delta.model.xml.solicitud.Solicitud solicitudXml = null;
		
		//VERIFICA QUE LA SOLICITUD NO ESTE VACIA
		if (solicitudModelo!= null){
			
			//ASIGNAMOS SOLICITUD
			solicitudXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.Solicitud();
			solicitudXml.setIdSolicitud( solicitudModelo.getIdSolicitud() );
			solicitudXml.setIdEstadoSolicitud( solicitudModelo.getIdEstadoSolicitud() );
			solicitudXml.setFechaRegistro( solicitudModelo.getFechaRegistro() );
			solicitudXml.setDesEstadoSolicitud( solicitudModelo.getDesEstadoSolicitud() );
			
			//ASIGNAMOS TRAMITES
			if (solicitudModelo.getTramite() != null){
				
				for (Tramite tramiteModelo: solicitudModelo.getTramite()) {
					solicitudXml.getTramite().add(convertirModelToXml(tramiteModelo));
				}
			}//ASIGNAMOS TRAMITES
		} //VERIFICA QUE LA SOLICITUD NO ESTE VACIA

		return solicitudXml;
	}

	public static mx.gob.imss.ctirss.delta.model.xml.solicitud.Tramite convertirModelToXml(Tramite tramiteModelo) {
        //TRAMITE
        mx.gob.imss.ctirss.delta.model.xml.solicitud.Tramite tramiteXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.Tramite();
        
        if (tramiteModelo.getIdTramite() != null){
            tramiteXml.setIdTramite( tramiteModelo.getIdTramite() );        	
        }

        //TIPO TRAMITE
        if (tramiteModelo.getTipoTramite() != null){
        	mx.gob.imss.ctirss.delta.model.xml.solicitud.TipoTramite tipoTramiteXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.TipoTramite();
        	tipoTramiteXml.setIdTipoTramite( tramiteModelo.getTipoTramite().getIdTipoTramite().longValue() ); 
        	tipoTramiteXml.setDescripcion( tramiteModelo.getTipoTramite().getDesTipoTramite());
        	tramiteXml.setTipoTramite(tipoTramiteXml);						
        }
        
        //ESTADO TRAMITE
        mx.gob.imss.ctirss.delta.model.xml.solicitud.EstadoTramite estadoTramiteXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.EstadoTramite();
        estadoTramiteXml.setIdEstadoTramitePersona( Utilerias.convertir(tramiteModelo.getIdEstadoTramite())  ); 
        estadoTramiteXml.setDescripcion( tramiteModelo.getDesEstadoTramite());
        tramiteXml.setEstadoTramite(estadoTramiteXml);
        
        //RAZON RESULTADO
        if ( tramiteModelo.getIdRazonResultado() != null){
        	mx.gob.imss.ctirss.delta.model.xml.solicitud.RazonResultado razonResultadoXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.RazonResultado();
        	razonResultadoXml.setIdRazonResultado( tramiteModelo.getIdRazonResultado() ); 
        	razonResultadoXml.setDescripcion( tramiteModelo.getDesRazonResultado());
        	tramiteXml.setRazonResultado(razonResultadoXml);						
        }
        				
        //GENERA LA PERSONA Y LA ASIGNA AL TRAMITE
        mx.gob.imss.ctirss.delta.model.xml.solicitud.Persona personaXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.Persona();
        tramiteXml.setPersona(personaXml);
        
        List<PersonaEstado> listaEstados = null;
        List<PersonaCalificacion> listaCalificaciones = null;
        List<Domicilio> listaDomicilios = null;
        
        //DOCUMENTOS PROBATORIOS
//					List<DocumentoProbatorio> listaDocumentosProbatoriosModelo = null;
        Nacimiento nacimientoModelo = null;
        
        //MEDIOS CONTACTO
        TelefonoFijo telefonoFijoModelo = null;
        TelefonoMovil telefonoMovilModelo = null;
        CorreoElectronico correoElectronicoModelo = null;
        					
        //VERIFICA SI EL TRAMITE TIENE UNA PERSONA FISICA
        if (tramiteModelo.getPersonaFisica() != null){
        							
        	//PERSONA
        	Fisica fisicaModelo = tramiteModelo.getPersonaFisica();
        	mx.gob.imss.ctirss.delta.model.xml.solicitud.Fisica fisicaXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.Fisica();						
        	personaXml.setIdPersona( fisicaModelo.getIdPersona() );
        	personaXml.setFisica(fisicaXml);
        	
        	//FISICA
        	fisicaXml.setNombres( fisicaModelo.getNombre() );
        	fisicaXml.setPrimerApellido( fisicaModelo.getPrimerApellido() );
        	fisicaXml.setSegundoApellido( fisicaModelo.getSegundoApellido() );
        	fisicaXml.setFechaNacimiento( fisicaModelo.getFechaNacimiento() );
        	fisicaXml.setFechaNacimiento( fisicaModelo.getFechaNacimiento() );
        	fisicaXml.setCurp( fisicaModelo.getCurp() );
        	fisicaXml.setRfc( fisicaModelo.getRfc() );
        	
        	//ENTIDAD NACIMIENTO
        	if (StringUtils.isNotBlank(fisicaModelo.getLugarNacimiento().getClave())) {
        		mx.gob.imss.ctirss.delta.model.xml.solicitud.EntidadFederativa entidadFederativaXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.EntidadFederativa();
        		entidadFederativaXml.setDescripcion(fisicaModelo.getLugarNacimiento().getNombre());
        		entidadFederativaXml.setIdEntidadFederativa(Integer.parseInt(fisicaModelo.getLugarNacimiento().getClave()));
        		fisicaXml.setEntidadFederativa(entidadFederativaXml); 							
        	}
        	
        	//SEXO
        	if (fisicaModelo.getSexo().getIdSexo() != null){
        		mx.gob.imss.ctirss.delta.model.xml.solicitud.Sexo sexoXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.Sexo();
        		sexoXml.setIdSexo( fisicaModelo.getSexo().getIdSexo() );
        		sexoXml.setDescripcion( fisicaModelo.getSexo().getDescripcion() );
        		fisicaXml.setSexo(sexoXml);							
        	}
        	
        	//PAIS
        	if (fisicaModelo.getPais() != null){
        		mx.gob.imss.ctirss.delta.model.xml.solicitud.Pais paisXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.Pais();
        		paisXml.setIdPais( fisicaModelo.getPais().getIdPais() );
        		paisXml.setDescripcion( fisicaModelo.getPais().getDescripcion() );
        		fisicaXml.setPais( paisXml );
        	}

        	//ESTADO PERSONA
        	listaEstados = fisicaModelo.getPersonaEstados();
        	
        	//CALIFICACION
        	listaCalificaciones = fisicaModelo.getPersonaCalificaciones();

        	//DOMICILIOS
        	listaDomicilios = fisicaModelo.getDomicilios();
        	
        	//DOCUMENTOS PROBATORIOS
//						listaDocumentosProbatoriosModelo = fisicaModelo.getDocumentosProbatorios();
        	nacimientoModelo = fisicaModelo.getActaNacimiento();
        	
        	//TIPO DE PERSONA - FISICA
        	mx.gob.imss.ctirss.delta.model.xml.solicitud.TipoPersona tipoPersonaXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.TipoPersona();
        	tipoPersonaXml.setIdTipoPersona(mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.TipoPersona.FISICA); 
        	tipoPersonaXml.setDescripcion(mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.TipoPersona.TIPO_PERSONA_1_FISICA);
        	personaXml.setTipoPersona(tipoPersonaXml);

        	//MEDIOS CONTACTO
        	telefonoFijoModelo = fisicaModelo.getTelefonoFijo();
        	telefonoMovilModelo = fisicaModelo.getTelefonoMovil();
        	correoElectronicoModelo = fisicaModelo.getCorreoElectronico();						
        }//VERIFICA SI EL TRAMITE TIENE UNA PERSONA FISICA

        //VERIFICA SI EL TRAMITE TIENE UNA PERSONA MORAL
        if (tramiteModelo.getPersonaMoral() != null){
        	
        	//PERSONA
            mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral moralModelo = tramiteModelo.getPersonaMoral();
        	mx.gob.imss.ctirss.delta.model.xml.solicitud.Moral moralXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.Moral();						
        	personaXml.setIdPersona( moralModelo.getIdPersona() );
        	personaXml.setMoral(moralXml);
        	
        	//MORAL
        	moralXml.setRazonSocial( moralModelo.getRazonSocial() );
        	moralXml.setRfc( moralModelo.getRfc() );
        	moralXml.setFechaCreacion( moralModelo.getFechaCreacion());
        	moralXml.setActaConstitutiva( moralModelo.getActaConstitutiva() );
        	
        	//TIPO DE SOCIEDAD
        	if (moralModelo.getTipoSociedad().getIdTipoSociedad() != null){
        		mx.gob.imss.ctirss.delta.model.xml.solicitud.TipoSociedad tipoSociedadXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.TipoSociedad();
        		tipoSociedadXml.setIdTipoSociedad( Utilerias.convertir(moralModelo.getTipoSociedad().getIdTipoSociedad()) );
        		tipoSociedadXml.setDescripcion( moralModelo.getTipoSociedad().getDescripcionAbreviada() );
        		moralXml.setTipoSociedad(tipoSociedadXml);
        	}

        	//ESTADO PERSONA
        	listaEstados = moralModelo.getPersonaEstados();
        	
        	//CALIFICACION
        	listaCalificaciones = moralModelo.getPersonaCalificaciones();
        	
        	//DOMICILIOS
        	listaDomicilios = moralModelo.getDomicilios();						
        	
        	//DOCUMENTOS PROBATORIOS
//						listaDocumentosProbatoriosModelo = moralModelo.getDocumentosProbatorios();
        	nacimientoModelo = moralModelo.getActaNacimiento();
        	
        	//TIPO DE PERSONA - MORAL
        	mx.gob.imss.ctirss.delta.model.xml.solicitud.TipoPersona tipoPersonaXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.TipoPersona();
        	tipoPersonaXml.setIdTipoPersona(mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.TipoPersona.MORAL); 
        	tipoPersonaXml.setDescripcion(mx.gob.imss.ctirss.delta.model.gestion.individuo.vo.TipoPersona.TIPO_PERSONA_2_MORAL);
        	personaXml.setTipoPersona(tipoPersonaXml);

        	//MEDIOS CONTACTO
        	telefonoFijoModelo = moralModelo.getTelefonoFijo();
        	telefonoMovilModelo = moralModelo.getTelefonoMovil();
        	correoElectronicoModelo = moralModelo.getCorreoElectronico();
        }//VERIFICA SI EL TRAMITE TIENE UNA PERSONA MORAL
        
        //ESTADO PERSONA
        if (listaEstados != null){
        	for (PersonaEstado personaEstadoModelo : listaEstados){
        		mx.gob.imss.ctirss.delta.model.xml.solicitud.PersonaEstado personaEstadoXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.PersonaEstado();
        		mx.gob.imss.ctirss.delta.model.xml.solicitud.EstadoPersona estadoPersonaXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.EstadoPersona();
        		estadoPersonaXml.setIdEstadoPersona(Utilerias.convertir(personaEstadoModelo.getEstadoPersona().getIdEstadoPersona()));
        		estadoPersonaXml.setDescripcion(personaEstadoModelo.getEstadoPersona().getDescripcion());
        		personaEstadoXml.setEstadoPersona(estadoPersonaXml);
        		personaXml.getPersonaEstado().add(personaEstadoXml);
        	}
        }//ESTADO PERSONA
        
        //CALIFICACION
        if (listaCalificaciones != null){
        	for (PersonaCalificacion personaCalificacionModelo : listaCalificaciones){
        		mx.gob.imss.ctirss.delta.model.xml.solicitud.PersonaCalificacion personaCalificacionXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.PersonaCalificacion();
        		mx.gob.imss.ctirss.delta.model.xml.solicitud.Calificacion calificacionXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.Calificacion();
        		calificacionXml.setIdCalificacion(Utilerias.convertir(personaCalificacionModelo.getCalificacion().getIdCalificacion()));
        		calificacionXml.setDescripcion(personaCalificacionModelo.getCalificacion().getDescripcion());
        		personaCalificacionXml.setCalificacion(calificacionXml);
        		personaXml.getPersonaCalificacion().add(personaCalificacionXml);
        	}
        }//CALIFICACION

        //DOCUMENTOS PROBATORIOS - ACTA NACIMIENTO
        if (nacimientoModelo != null){
        	mx.gob.imss.ctirss.delta.model.xml.solicitud.DocumentoProbatorio documentoProbatorioXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.DocumentoProbatorio();
        	personaXml.getDocumentoProbatorio().add(documentoProbatorioXml);
        	documentoProbatorioXml.setIdDocumentoProbatorio(nacimientoModelo.getIdDocumentoProbatorio());
        	
        	mx.gob.imss.ctirss.delta.model.xml.solicitud.Acta actaXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.Acta();
        	mx.gob.imss.ctirss.delta.model.xml.solicitud.Nacimiento nacimientoXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.Nacimiento();
        	actaXml.setNacimiento(nacimientoXml);
        	documentoProbatorioXml.setActa(actaXml);
        	
        	nacimientoXml.setAnio(nacimientoModelo.getAnio());
        	nacimientoXml.setCrip(nacimientoModelo.getCrip());
        	nacimientoXml.setTomo(nacimientoModelo.getTomo());
        	
        	if (nacimientoModelo.getMunicipio() != null){
        		actaXml.setClaveMunicipioRegistro(nacimientoModelo.getMunicipio().getClave());						
        		actaXml.setDesMunicipioRegistro(nacimientoModelo.getMunicipio().getNombre());
        		
        		if (nacimientoModelo.getMunicipio().getEntidadFederativa() != null){
        			actaXml.setClaveEntidadRegistro(nacimientoModelo.getMunicipio().getEntidadFederativa().getClave());
        			actaXml.setDesEntidadRegistro(nacimientoModelo.getMunicipio().getEntidadFederativa().getNombre());								
        		}
        	}
        	
        	actaXml.setFoja(nacimientoModelo.getNoFoja());
        	actaXml.setLibro(nacimientoModelo.getNoLibro());
        	actaXml.setNumeroActa(nacimientoModelo.getNoActa());
        	
        }
        
        //MEDIOS CONTACTO
        if (telefonoFijoModelo != null || telefonoMovilModelo != null || correoElectronicoModelo != null){
        	
        	//MEDIOS CONTACTO - TELEFONO FIJO
        	if(telefonoFijoModelo != null){
        		mx.gob.imss.ctirss.delta.model.xml.solicitud.MedioContacto medioContactoXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.MedioContacto();
        		personaXml.getMedioContacto().add(medioContactoXml);
        		medioContactoXml.setIdMedioContacto(Utilerias.convertir(telefonoFijoModelo.getClave()));
        		
        		if (telefonoFijoModelo.getTipoMedioContacto() != null){
        			mx.gob.imss.ctirss.delta.model.xml.solicitud.TipoMedioContacto tipoMedioContactoXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.TipoMedioContacto();
        			tipoMedioContactoXml.setIdTipoMedioContacto(Utilerias.convertir(telefonoFijoModelo.getTipoMedioContacto().getIdTipoMedioContacto()));
        			tipoMedioContactoXml.setDescripcion(telefonoFijoModelo.getTipoMedioContacto().getDescripcion());
        			medioContactoXml.setTipoMedioContacto(tipoMedioContactoXml);
        		}
        		
        		mx.gob.imss.ctirss.delta.model.xml.solicitud.TelefonoFijo telefonoFijoXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.TelefonoFijo();
        		telefonoFijoXml.setClaveLada(telefonoFijoModelo.getClaveLada());
        		telefonoFijoXml.setExtension(telefonoFijoModelo.getExtension());
        		telefonoFijoXml.setNumeroTelefonico(telefonoFijoModelo.getNumero());
        		medioContactoXml.setTelefonoFijo(telefonoFijoXml);
        	}
        	
        	//MEDIO CONTACTO - TELEFONO MOVIL
        	if(telefonoMovilModelo != null){
        		mx.gob.imss.ctirss.delta.model.xml.solicitud.MedioContacto medioContactoXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.MedioContacto();
        		personaXml.getMedioContacto().add(medioContactoXml);
        		medioContactoXml.setIdMedioContacto(Utilerias.convertir(telefonoMovilModelo.getClave()));
        		
        		if (telefonoMovilModelo.getTipoMedioContacto() != null){
        			mx.gob.imss.ctirss.delta.model.xml.solicitud.TipoMedioContacto tipoMedioContactoXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.TipoMedioContacto();
        			tipoMedioContactoXml.setIdTipoMedioContacto(Utilerias.convertir(telefonoMovilModelo.getTipoMedioContacto().getIdTipoMedioContacto()));
        			tipoMedioContactoXml.setDescripcion(telefonoMovilModelo.getTipoMedioContacto().getDescripcion());
        			medioContactoXml.setTipoMedioContacto(tipoMedioContactoXml);
        		}
        		
        		mx.gob.imss.ctirss.delta.model.xml.solicitud.TelefonoMovil telefonoMovilXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.TelefonoMovil();
        		telefonoMovilXml.setNumeroTelefonico(telefonoMovilModelo.getNumero());
        		medioContactoXml.setTelefonoMovil(telefonoMovilXml);							
        	}

        	//MEDIO CONTACTO - CORREO ELECTRONICO
        	if(correoElectronicoModelo != null){
        		mx.gob.imss.ctirss.delta.model.xml.solicitud.MedioContacto medioContactoXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.MedioContacto();
        		personaXml.getMedioContacto().add(medioContactoXml);
        		medioContactoXml.setIdMedioContacto(Utilerias.convertir(correoElectronicoModelo.getClave()));
        		
        		if (correoElectronicoModelo.getTipoMedioContacto() != null){
        			mx.gob.imss.ctirss.delta.model.xml.solicitud.TipoMedioContacto tipoMedioContactoXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.TipoMedioContacto();
        			tipoMedioContactoXml.setIdTipoMedioContacto(Utilerias.convertir(correoElectronicoModelo.getTipoMedioContacto().getIdTipoMedioContacto()));
        			tipoMedioContactoXml.setDescripcion(correoElectronicoModelo.getTipoMedioContacto().getDescripcion());
        			medioContactoXml.setTipoMedioContacto(tipoMedioContactoXml);
        		}
        		
        		mx.gob.imss.ctirss.delta.model.xml.solicitud.CorreoElectronico correoElectronicoXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.CorreoElectronico();
        		correoElectronicoXml.setCorreoElectronico(correoElectronicoModelo.getCorreo());
        		medioContactoXml.setCorreoElectronico(correoElectronicoXml);							
        	}
        	
        }

        //DOMICILIOS
        if (listaDomicilios != null){
        	for (Domicilio domicilioModelo : listaDomicilios){
        		mx.gob.imss.ctirss.delta.model.xml.solicitud.Domicilio domicilioXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.Domicilio();
        		domicilioXml.setClave( domicilioModelo.getClave() );
        		//domicilioXml.setLatitud( domicilioModelo.getLatitud() );
        		//domicilioXml.setLongitud( domicilioModelo.getLongitud() );
        		domicilioXml.setNumExterior1( domicilioModelo.getNumExterior1() );
        		domicilioXml.setNumExterior2( domicilioModelo.getNumExterior2() );
        		domicilioXml.setNumExteriorAlf( domicilioModelo.getNumExteriorAlf() );
        		domicilioXml.setNumInterior( domicilioModelo.getNumInterior() );
        		domicilioXml.setNumInteriorAlf( domicilioModelo.getNumInteriorAlf() );
        		domicilioXml.setDescripcion(domicilioModelo.getDescripcion());
        		//AMBITO
        		if (domicilioModelo.getAmbito() != null){
        			mx.gob.imss.ctirss.delta.model.xml.solicitud.TipoAmbito tipoAmbitoXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.TipoAmbito();
        			tipoAmbitoXml.setClave(Utilerias.convertir(domicilioModelo.getAmbito().getClave()));
        			tipoAmbitoXml.setDescripcion(domicilioModelo.getAmbito().getDescripcion());
        			domicilioXml.setTipoAmbito(tipoAmbitoXml);
        		}
        		
        		//TIPO DOMICILIO
        		if (domicilioModelo.getTipoDomicilio() != null){
        			mx.gob.imss.ctirss.delta.model.xml.solicitud.TipoDomicilio tipoDomicilioXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.TipoDomicilio();
        			tipoDomicilioXml.setIdTipoDomicilio(domicilioModelo.getTipoDomicilio().getClave());
        			tipoDomicilioXml.setDescripcion(domicilioModelo.getTipoDomicilio().getDescripcion());
        			domicilioXml.setTipoDomicilio(tipoDomicilioXml);
        		}							
        		
        		//CODIGO POSTAL - NIVEL DOMICILIO
        		if (domicilioModelo.getCodigoPostal() != null){
        			mx.gob.imss.ctirss.delta.model.xml.solicitud.CodigoPostal codigoPostalXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.CodigoPostal();
        			codigoPostalXml.setCodigoPostal(domicilioModelo.getCodigoPostal().getCodigoPostal());
        			domicilioXml.setCodigoPostal(codigoPostalXml);
        		}

        		//ASENTAMIENTO
        		if (domicilioModelo.getAsentamiento() != null){
        			mx.gob.imss.ctirss.delta.model.xml.solicitud.Asentamiento asentamientoXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.Asentamiento();
        			asentamientoXml.setClave(domicilioModelo.getAsentamiento().getClave());
        			asentamientoXml.setNombre(domicilioModelo.getAsentamiento().getNombre());
        			domicilioXml.setAsentamiento(asentamientoXml);
        	        
        	        //CODIGO POSTAL - NIVEL ASENTAMIENTO
        	        if (domicilioModelo.getAsentamiento().getCodigoPostal() != null){
        	        	mx.gob.imss.ctirss.delta.model.xml.solicitud.CodigoPostal codigoPostalXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.CodigoPostal();
        	        	codigoPostalXml.setCodigoPostal(domicilioModelo.getAsentamiento().getCodigoPostal().getCodigoPostal());
        				asentamientoXml.setCodigoPostal(codigoPostalXml);	
        	        }
        			
        			//LOCALIDAD
        			if (domicilioModelo.getAsentamiento().getLocalidad()!= null){
        				mx.gob.imss.ctirss.delta.model.xml.solicitud.Localidad localidadXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.Localidad();
        				localidadXml.setClave(domicilioModelo.getAsentamiento().getLocalidad().getClave());
        				localidadXml.setNombre(domicilioModelo.getAsentamiento().getLocalidad().getNombre());
        				asentamientoXml.setLocalidad(localidadXml);

        				//MUNICIPIO
        				if (domicilioModelo.getAsentamiento().getLocalidad().getMunicipio() != null){
        					mx.gob.imss.ctirss.delta.model.xml.solicitud.Municipio municipioXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.Municipio();
        					municipioXml.setClave(domicilioModelo.getAsentamiento().getLocalidad().getMunicipio().getClave());
        					municipioXml.setDescripcion(domicilioModelo.getAsentamiento().getLocalidad().getMunicipio().getNombre());
        					localidadXml.setMunicipio(municipioXml);
        					
        					//ENTIDAD FEDERATIVA
        					if (domicilioModelo.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa() != null){
        						mx.gob.imss.ctirss.delta.model.xml.solicitud.EntidadFederativa entidadFederativaXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.EntidadFederativa();
        						if(domicilioModelo.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getClave() != null && !domicilioModelo.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getClave().equals("")){
        							entidadFederativaXml.setIdEntidadFederativa(Integer.valueOf(domicilioModelo.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getClave()));
        						}
        						entidadFederativaXml.setDescripcion(domicilioModelo.getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getNombre());
        						municipioXml.setEntidadFederativa(entidadFederativaXml);
        					}
        				}//MUNICIPIO
        			}//LOCALIDAD
        		}//ASENTAMIENTO
        		
        		//VIALIDAD PRIMARIA
        		if (domicilioModelo.getVialidadPrimaria() != null){
        			mx.gob.imss.ctirss.delta.model.xml.solicitud.VialidadPrimaria vialidadPrimariaXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.VialidadPrimaria();
        			mx.gob.imss.ctirss.delta.model.xml.solicitud.Vialidad vialidadXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.Vialidad();
        			vialidadXml.setClave(domicilioModelo.getVialidadPrimaria().getClave());
        			vialidadXml.setNombre(domicilioModelo.getVialidadPrimaria().getNombre());								
        			
        			if (domicilioModelo.getVialidadPrimaria().getTipoVialidad() != null){
        				mx.gob.imss.ctirss.delta.model.xml.solicitud.TipoVialidad tipoVialidadXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.TipoVialidad();
        				tipoVialidadXml.setClave(domicilioModelo.getVialidadPrimaria().getTipoVialidad().getClave());
        				tipoVialidadXml.setDescripcion(domicilioModelo.getVialidadPrimaria().getTipoVialidad().getDescripcion());								
        				vialidadXml.setTipoVialidad(tipoVialidadXml);									
        			}

        			vialidadPrimariaXml.setVialidad(vialidadXml);
        			domicilioXml.setVialidadPrimaria(vialidadPrimariaXml);
        		}
        		
        		//VIALIDAD REFERENCIA POSTERIOR
        		if (domicilioModelo.getVialidadReferenciaPosterior() != null){
        			mx.gob.imss.ctirss.delta.model.xml.solicitud.VialidadReferenciaPosterior vialidadReferenciaPosteriorXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.VialidadReferenciaPosterior();
        			mx.gob.imss.ctirss.delta.model.xml.solicitud.Vialidad vialidadXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.Vialidad();
        			vialidadXml.setClave(domicilioModelo.getVialidadReferenciaPosterior().getClave());
        			vialidadXml.setNombre(domicilioModelo.getVialidadReferenciaPosterior().getNombre());
        			
        			if (domicilioModelo.getVialidadReferenciaPosterior().getTipoVialidad() != null){
        				mx.gob.imss.ctirss.delta.model.xml.solicitud.TipoVialidad tipoVialidadXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.TipoVialidad();
        				tipoVialidadXml.setClave(domicilioModelo.getVialidadReferenciaPosterior().getTipoVialidad().getClave());
        				tipoVialidadXml.setDescripcion(domicilioModelo.getVialidadReferenciaPosterior().getTipoVialidad().getDescripcion());								
        				vialidadXml.setTipoVialidad(tipoVialidadXml);									
        			}

        			vialidadReferenciaPosteriorXml.setVialidad(vialidadXml);
        			domicilioXml.setVialidadReferenciaPosterior(vialidadReferenciaPosteriorXml);
        		}

        		//VIALIDAD REFERENCIA PRIMARIA
        		if (domicilioModelo.getVialidadReferenciaPrimaria() != null){
        			mx.gob.imss.ctirss.delta.model.xml.solicitud.VialidadReferenciaPrimaria vialidadReferenciaPrimariaXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.VialidadReferenciaPrimaria();
        			mx.gob.imss.ctirss.delta.model.xml.solicitud.Vialidad vialidadXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.Vialidad();
        			vialidadXml.setClave(domicilioModelo.getVialidadReferenciaPrimaria().getClave());
        			vialidadXml.setNombre(domicilioModelo.getVialidadReferenciaPrimaria().getNombre());

        			if (domicilioModelo.getVialidadReferenciaPrimaria().getTipoVialidad() != null){
        				mx.gob.imss.ctirss.delta.model.xml.solicitud.TipoVialidad tipoVialidadXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.TipoVialidad();
        				tipoVialidadXml.setClave(domicilioModelo.getVialidadReferenciaPrimaria().getTipoVialidad().getClave());
        				tipoVialidadXml.setDescripcion(domicilioModelo.getVialidadReferenciaPrimaria().getTipoVialidad().getDescripcion());								
        				vialidadXml.setTipoVialidad(tipoVialidadXml);									
        			}
        			
        			vialidadReferenciaPrimariaXml.setVialidad(vialidadXml);
        			domicilioXml.setVialidadReferenciaPrimaria(vialidadReferenciaPrimariaXml);
        		}

        		//VIALIDAD REFERENCIA PRIMARIA
        		if (domicilioModelo.getVialidadReferenciaSecundaria() != null){
        			mx.gob.imss.ctirss.delta.model.xml.solicitud.VialidadReferenciaSecundaria vialidadReferenciaSecundariaXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.VialidadReferenciaSecundaria();
        			mx.gob.imss.ctirss.delta.model.xml.solicitud.Vialidad vialidadXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.Vialidad();
        			vialidadXml.setClave(domicilioModelo.getVialidadReferenciaSecundaria().getClave());
        			vialidadXml.setNombre(domicilioModelo.getVialidadReferenciaSecundaria().getNombre());
        			
        			if (domicilioModelo.getVialidadReferenciaSecundaria().getTipoVialidad() != null){
        				mx.gob.imss.ctirss.delta.model.xml.solicitud.TipoVialidad tipoVialidadXml = new mx.gob.imss.ctirss.delta.model.xml.solicitud.TipoVialidad();									
        				tipoVialidadXml.setClave(domicilioModelo.getVialidadReferenciaSecundaria().getTipoVialidad().getClave());
        				tipoVialidadXml.setDescripcion(domicilioModelo.getVialidadReferenciaSecundaria().getTipoVialidad().getDescripcion());								
        				vialidadXml.setTipoVialidad(tipoVialidadXml);
        			}

        			vialidadReferenciaSecundariaXml.setVialidad(vialidadXml);
        			domicilioXml.setVialidadReferenciaSecundaria(vialidadReferenciaSecundariaXml);
        		}
        		
        		personaXml.getDomicilio().add(domicilioXml);
        	}
        }//DOMICILIOS
        return tramiteXml;
    }
	
}
