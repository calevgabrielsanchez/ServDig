package mx.gob.imss.ctirss.gestionpersonas.servicios.entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.framework.exceptions.TransformacionException;
import mx.gob.imss.ctirss.delta.framework.util.Utilerias;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.ActaConstitutiva;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Calificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.EstadoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaEstado;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EscrituraConstitutiva;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RegistroSindicato;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSociedad;
import mx.gob.imss.ctirss.delta.persistence.DgDomicilioGeografico;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoPersona;
import mx.gob.imss.ctirss.delta.persistence.DicPersonaCalificacion;
import mx.gob.imss.ctirss.delta.persistence.DicTipoDomicilio;
import mx.gob.imss.ctirss.delta.persistence.DicTipoSociedad;
import mx.gob.imss.ctirss.delta.persistence.DitActaConstitutiva;
import mx.gob.imss.ctirss.delta.persistence.DitDatosPersonaSat;
import mx.gob.imss.ctirss.delta.persistence.DitFormaContacto;
import mx.gob.imss.ctirss.delta.persistence.DitHistEstadoPersonaMoral;
import mx.gob.imss.ctirss.delta.persistence.DitHistPersonaMoralCalific;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaMoral;
import mx.gob.imss.ctirss.delta.persistence.DitPersonamContacto;
import mx.gob.imss.ctirss.delta.persistence.DitPersonamDom;
import mx.gob.imss.ctirss.delta.persistence.DitSindicato;
import mx.gob.imss.ctirss.delta.persistence.DitTipoContacto;
import mx.gob.imss.ctirss.gestionpersonas.servicios.utility.DatosPersonaSATServiceUtility;
import mx.gob.imss.ctirss.gestionpersonas.servicios.utility.DatosPersonaSATServiceUtilityLocal;

import org.apache.commons.lang.StringUtils;

public final class PersonaMoralConversor extends AbstractServiceUtility {
	
    private PersonaMoralConversor() {
        super();
    }

    public static DitPersonaMoral convertirPersonaMoralToEntity(final Moral personaMoral) {
        DitPersonaMoral ditPersonaMoral = null; // NOPMD
        if (personaMoral != null) {
            ditPersonaMoral = new DitPersonaMoral();
            
            // DATOS BASICOS PERSONA MORAL
            ditPersonaMoral.setCveIdPersonaMoral(personaMoral.getCveMoral());
            ditPersonaMoral.setDenominacionRazonSocial(personaMoral.getRazonSocial());
            if (personaMoral.getTipoSociedad() != null && personaMoral.getTipoSociedad().getIdTipoSociedad() != null) {
                final DicTipoSociedad dicTipoSociedad = new DicTipoSociedad();
                dicTipoSociedad.setCveIdTipoSociedad(Utilerias.convertir(personaMoral.getTipoSociedad().getIdTipoSociedad()));
                ditPersonaMoral.setDicTipoSociedad(dicTipoSociedad);
            }
            ditPersonaMoral.setFecRegistroActualizado(personaMoral.getFechaModificacion());
            
            // ACTA(S) CONSTITUTIVA(S)
            List<DitActaConstitutiva> ditActaConstitutiva = ditPersonaMoral.getDitActaConstitutivas();
            if (ditActaConstitutiva == null) {
                ditActaConstitutiva = new ArrayList<DitActaConstitutiva>();
            }
            final DitActaConstitutiva actaConstitutiva = new DitActaConstitutiva();
            actaConstitutiva.setFecExpedicionActa(personaMoral.getFechaCreacion());
            ditActaConstitutiva.add(actaConstitutiva);
            ditPersonaMoral.setDitActaConstitutivas(ditActaConstitutiva);
            ditPersonaMoral.setFecRegistroBaja(personaMoral.getFechaBaja());
            ditPersonaMoral.setRfc(personaMoral.getRfc());
            final List<DitActaConstitutiva> actas = new ArrayList<DitActaConstitutiva>();
            final DitActaConstitutiva ditActaConst = new DitActaConstitutiva(personaMoral.getIdActaConstitutiva() == null ? null : personaMoral.getIdActaConstitutiva().intValue());
            ditActaConst.setNumActa(personaMoral.getActaConstitutiva());
            ditActaConst.setFecExpedicionActa(personaMoral.getFechaCreacion());
            actas.add(ditActaConst);
            ditPersonaMoral.setDitActaConstitutivas(actas);

            // ESTADO ->
            if (personaMoral.getPersonaEstados() != null && !personaMoral.getPersonaEstados().isEmpty()) {
                final List<DitHistEstadoPersonaMoral> dicEstadoPersona = new ArrayList<DitHistEstadoPersonaMoral>();
                for (PersonaEstado personaEstado : personaMoral.getPersonaEstados()) {
                    if (personaEstado.getEstadoPersona() != null && personaEstado.getEstadoPersona().getIdEstadoPersona() != null) {
                        DicEstadoPersona estado = new DicEstadoPersona();
                        estado.setCveEstadoPersona(Utilerias.convertir(personaEstado.getEstadoPersona().getIdEstadoPersona()));
                        DitHistEstadoPersonaMoral histEstado = new DitHistEstadoPersonaMoral();
                        histEstado.setDicEstadoPersona(estado);
                        histEstado.setDitPersonaMoral(ditPersonaMoral);

                        dicEstadoPersona.add(histEstado);
                    }
                }
                ditPersonaMoral.setDitHistEstadoPersonaMorals(dicEstadoPersona);
            }

            // CALIFICACION  (SUBESTADOVALIDADO)
            if (personaMoral.getPersonaCalificaciones() != null && !personaMoral.getPersonaCalificaciones().isEmpty()) {
                for (PersonaCalificacion personaCalificacion : personaMoral.getPersonaCalificaciones()) {
                    if (personaCalificacion.getCalificacion() != null && Utilerias.isNotBlank(personaCalificacion.getCalificacion().getIdCalificacion())) {
                        DicPersonaCalificacion dicPersonaCalificacion = new DicPersonaCalificacion();
                        dicPersonaCalificacion.setCveIdCalificacion(personaCalificacion.getCalificacion().getIdCalificacion());
                        DitHistPersonaMoralCalific ditHistCalificacion = new DitHistPersonaMoralCalific();
                        ditHistCalificacion.setDicPersonaCalificacion(dicPersonaCalificacion);
                        ditHistCalificacion.setDitPersonaMoral(ditPersonaMoral);
                        ditPersonaMoral.getDitHistPersonaMoralCalifics().add(ditHistCalificacion);
                    }
                }
            }
            
            // DOMICILIO
            if (personaMoral.getDomicilios() != null && !personaMoral.getDomicilios().isEmpty()) {
                for (Domicilio domicilio : personaMoral.getDomicilios()) {
                    if (domicilio.getClave() != null) {
                        DitPersonamDom ditPersonaDomicilio = new DitPersonamDom();
                        ditPersonaDomicilio.setDitPersonaMoral(ditPersonaMoral);
                        DgDomicilioGeografico dgDomicilio = new DgDomicilioGeografico();
                        dgDomicilio.setDomicilioId(domicilio.getClave().longValue());
                        ditPersonaDomicilio.setDgDomicilioGeografico(dgDomicilio);
                        DicTipoDomicilio dicTipoDomicilio = new DicTipoDomicilio();
                        dicTipoDomicilio.setCveIdTipoDomicilio(Utilerias.convertir(domicilio.getTipoDomicilio() == null ? null : domicilio.getTipoDomicilio().getClave()));
                        ditPersonaDomicilio.setDicTipoDomicilio(dicTipoDomicilio); 
                        ditPersonaMoral.getDitPersonamDoms().add(ditPersonaDomicilio);
                    }
                }
            }

            // MEDIOS DE CONTACTO
            if (personaMoral.getMediosContacto() != null && !personaMoral.getMediosContacto().isEmpty()) {
                for (MedioContacto medioContacto : personaMoral.getMediosContacto()) {
                    DitFormaContacto ditFormaContacto = new DitFormaContacto();
                    DitTipoContacto ditTipoContacto = new DitTipoContacto();
                    ditTipoContacto.setCveIdTipoContacto(medioContacto.getTipoMedioContacto().getIdTipoMedioContacto());
                    ditFormaContacto.setDitTipoContacto(ditTipoContacto);
                    ditFormaContacto.setCveIdFormaContacto(medioContacto.getClave());
                    DitPersonamContacto contacto = new DitPersonamContacto();
                    contacto.setDitPersonaMoral(ditPersonaMoral);
                    contacto.setDitFormaContacto(ditFormaContacto);
                    ditPersonaMoral.getDitPersonamContactos().add(contacto);
                }
            }
        }
        return ditPersonaMoral;
    }

    public static Moral convertirEntityToPersonaMoral(final DitPersonaMoral entity) {
        Moral personaMoral = null; // NOPMD
        if (entity != null) {
            personaMoral = new Moral();
            if (entity.getDicTipoSociedad() != null) {
                personaMoral.setTipoSociedad(new TipoSociedad());
                personaMoral.getTipoSociedad().setIdTipoSociedad(Utilerias.convertir(entity.getDicTipoSociedad().getCveIdTipoSociedad()));
                personaMoral.getTipoSociedad().setDescripcion((entity.getDicTipoSociedad().getDesTipoSociedad()));
                personaMoral.getTipoSociedad().setDescripcionAbreviada((entity.getDicTipoSociedad().getDesTipoSociedadAbrev()));
            }
            personaMoral.setFechaBaja(entity.getFecRegistroBaja());
            personaMoral.setFechaRegistro(entity.getFecRegistroAlta());
            DitActaConstitutiva ditActaConst = null; // NOPMD
            if (entity.getDitActaConstitutivas() != null) {
                if (entity.getDitActaConstitutivas().size() > 0) {
                    ditActaConst = entity.getDitActaConstitutivas().get(0);
                }
   
                final ActaConstitutiva actaConstitutiva = convertirEntityToModel(ditActaConst);
                if (actaConstitutiva != null) {
                    personaMoral.setIdActaConstitutiva(actaConstitutiva.getIdActaConstitutiva());

                    personaMoral.setActaConstitutiva(actaConstitutiva.getNumActa());
                    
                    // Se crea el objeto Escritura constitutiva
            		EscrituraConstitutiva escrituraConstitutiva = new EscrituraConstitutiva();
        			
        			escrituraConstitutiva.setCveEscrituraConstitutiva(ditActaConst.getCveIdActConstitutiva().longValue());
        			if(!StringUtils.isBlank(ditActaConst.getNumEscritura())){
        				escrituraConstitutiva.setNumEscritura(ditActaConst.getNumEscritura());
        			}
        			escrituraConstitutiva.setNumNotaria(ditActaConst.getNumNotaria());
        			
        			if(!StringUtils.isBlank(ditActaConst.getCveEnt()) && 
        					!StringUtils.isBlank(ditActaConst.getCveMun())){
	        			EntidadFederativa entidadFederativa = new EntidadFederativa();
	        			entidadFederativa.setClave(ditActaConst.getCveEnt());
	        			if(ditActaConst.getDgCatMunicipio()!=null && ditActaConst.getDgCatMunicipio().getDgCatEstado()!=null)
	        				entidadFederativa.setNombre(ditActaConst.getDgCatMunicipio().getDgCatEstado().getNomEnt());
	        			Municipio municipio = new Municipio();
	        			municipio.setClave(ditActaConst.getCveMun());
	        			if(ditActaConst.getDgCatMunicipio()!=null )
	        				municipio.setNombre(ditActaConst.getDgCatMunicipio().getNomMun());
	        			municipio.setEntidadFederativa(entidadFederativa);
	        			escrituraConstitutiva.setLugarExpedicion(municipio);
        			}
        			
        			escrituraConstitutiva.setFechaExpedicion(ditActaConst.getFecExpedicionActa());
        			escrituraConstitutiva.setFolioMercantil(ditActaConst.getNumFolioMercantil());
        			escrituraConstitutiva.setCveIdPersonaMoral(ditActaConst.getDitPersonaMoral().getCveIdPersonaMoral());

        			escrituraConstitutiva.setSeccion(ditActaConst.getNumSeccion());
        			escrituraConstitutiva.setPartida(ditActaConst.getNumPartida());
        			escrituraConstitutiva.setVolumen(ditActaConst.getNumVolumen());
        			escrituraConstitutiva.setFoja(ditActaConst.getNumFoja());
        			
        			personaMoral.setEscrituraConstitutiva(escrituraConstitutiva);
                }
            }
            personaMoral.setFechaModificacion(entity.getFecRegistroActualizado() != null ? entity.getFecRegistroActualizado() : entity.getFecRegistroAlta());
            personaMoral.setIdPersona(entity.getCveIdPersonaMoral());
            personaMoral.setCveMoral(entity.getCveIdPersonaMoral());
            personaMoral.setRazonSocial(entity.getDenominacionRazonSocial());
            personaMoral.setRfc(entity.getRfc());
            personaMoral.setRfcSat(entity.getRfc()); // TODO validar esto
            
            if (entity.getDicTipoSociedad() != null) {
                personaMoral.getTipoSociedad().setIdTipoSociedad(Utilerias.convertir(entity.getDicTipoSociedad().getCveIdTipoSociedad()));
                personaMoral.getTipoSociedad().setDescripcion(entity.getDicTipoSociedad().getDesTipoSociedadAbrev());
            }else{
            	personaMoral.setTipoSociedad(new TipoSociedad());
            }

            // ESTADO
            if (entity.getDitHistEstadoPersonaMorals() != null && !entity.getDitHistEstadoPersonaMorals().isEmpty()) {
                for (DitHistEstadoPersonaMoral ditHistEstadoPersona : entity.getDitHistEstadoPersonaMorals()) {
                    DicEstadoPersona dicEstadoPersona = ditHistEstadoPersona.getDicEstadoPersona();
                    if (dicEstadoPersona.getCveEstadoPersona() != null) {
                        EstadoPersona edoPersona = new EstadoPersona();
                        edoPersona.setIdEstadoPersona(Utilerias.convertir(dicEstadoPersona.getCveEstadoPersona()));
                        edoPersona.setDescripcion(dicEstadoPersona.getDesEstadoPersona());
                        PersonaEstado personaEstado = new PersonaEstado();
                        personaEstado.setEstadoPersona(edoPersona);
                        personaMoral.getPersonaEstados().add(personaEstado);
                    }
                }
                personaMoral.setEstadosFormateados(quitarComaFinal(entity.getDicEstadoPersonaAsString()));
            }

            // SUBESTADO
            if (entity.getDitHistPersonaMoralCalifics() != null && !entity.getDitHistPersonaMoralCalifics().isEmpty()) {
                for (DitHistPersonaMoralCalific dicHistPersonaCalificacion : entity.getDitHistPersonaMoralCalifics()) {
                    DicPersonaCalificacion dicPersonaCalificacion = dicHistPersonaCalificacion.getDicPersonaCalificacion();
                    if (dicPersonaCalificacion.getCveIdCalificacion() != null) {
                        PersonaCalificacion personaEstado = new PersonaCalificacion();
                        Calificacion subestado = new Calificacion();
                        subestado.setIdCalificacion(dicPersonaCalificacion.getCveIdCalificacion());
                        subestado.setDescripcion(dicPersonaCalificacion.getDesCalificacion());
                        personaEstado.setCalificacion(subestado);
                        personaMoral.getPersonaCalificaciones().add(personaEstado);
                    }
                }
                personaMoral.setSubEstadosFormateados(quitarComaFinal(entity.getDicPersonaCalificacionAsString()));
            }
            
            //Registro Sindicato
            if(entity.getDitSindicatos() != null && !entity.getDitSindicatos().isEmpty()){
            	DitSindicato ditSindicato = entity.getDitSindicatos().get(0);
            	RegistroSindicato registroSindicato = new RegistroSindicato();
            	
            	registroSindicato.setCveRegistroSindicato(ditSindicato.getCveIdSindicato());
            	
            	if(!StringUtils.isBlank(ditSindicato.getNumRefRegistro())){
            		registroSindicato.setNumReferenciadocRegistro(ditSindicato.getNumRefRegistro());
            	}
            	
            	registroSindicato.setFechaRegistro(ditSindicato.getFecDocRegistro());
            	registroSindicato.setAutoridadLaboral(ditSindicato.getDesAutLab());
            	
            	if(ditSindicato.getDitPersonaMoral() != null){
            		registroSindicato.setCveIdPersonaMoral(ditSindicato.getDitPersonaMoral().getCveIdPersonaMoral());
            	}else{
            		registroSindicato.setCveIdPersonaMoral(entity.getCveIdPersonaMoral());
            	}
            	
            	personaMoral.setRegistroSindicato(registroSindicato);
            	
            }
            
            if(entity.getDitDatosPersonaSat() != null && !entity.getDitDatosPersonaSat().isEmpty()){
            	
            	/* Se obtiene la primera posición ya que una persona
            	 * sólo debe tener un grupo de datos SAT. 
            	 */
            	DitDatosPersonaSat datosPersonaSat = entity.getDitDatosPersonaSat().get(0);
            	
            	personaMoral.setFechaCreacion(datosPersonaSat.getFecConstitucion());

                /*
                 * Ahora hay que agregar el campo fechaCreacionFormateada
                 * para la vista en formato dd/MM/yyyy
                 */
                final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String fechaFormateada;
                if (datosPersonaSat.getFecConstitucion() == null) {
                    fechaFormateada = "";
                } else {
                    fechaFormateada = sdf.format(datosPersonaSat.getFecConstitucion());
                }
                personaMoral.setFechaCreacionFormateada(fechaFormateada);
                
                DatosPersonaSATServiceUtilityLocal datosPersonaSATServiceUtility = new DatosPersonaSATServiceUtility();
                
				try {
					personaMoral.setDatosPersonaSAT(datosPersonaSATServiceUtility
							.transformarDatosPersonaSAT(datosPersonaSat));
				} catch (TransformacionException e) {
					e.printStackTrace();
				}
            }

        }
        return personaMoral;
    }

    public static List<Moral> convertirListOfPersonaMoralEntitiesToModel(final List<DitPersonaMoral> ditPersonaMoralLst) { // NOPMD
        final List<Moral> personaMoralList = new ArrayList<Moral>();
        for (DitPersonaMoral ditPersonaMoral : ditPersonaMoralLst) {
            personaMoralList.add(convertirEntityToPersonaMoral(ditPersonaMoral));
        }
        return personaMoralList;
    }

    public static ActaConstitutiva convertirEntityToModel(final DitActaConstitutiva entity) {
        ActaConstitutiva actaConstitutiva = null; // NOPMD
        if (entity != null) {
            actaConstitutiva = new ActaConstitutiva();
            actaConstitutiva.setIdActaConstitutiva(entity.getCveIdActConstitutiva() == null ? null : new Long(entity.getCveIdActConstitutiva()));
            if (entity.getDitPersonaMoral() != null) {
                actaConstitutiva.setIdPersonaMoral(entity.getDitPersonaMoral().getCveIdPersonaMoral());
            }
            actaConstitutiva.setFechaExpedicionActa(entity.getFecExpedicionActa());
            actaConstitutiva.setNumActa(entity.getNumActa());
        }
        return actaConstitutiva;
    }
    
	/**
	 * 191807
	 * 060812
	 * Este metodo quita la coma final de la cadena formada por las calificaciones
	 * @param cadena
	 * @return
	 */
    public static String quitarComaFinal(String cadena) {

		String cadenaSinComaFinal = "";
		
        try{
        	int fin = cadena.lastIndexOf(",");
        	cadenaSinComaFinal = cadena.substring(0, fin);
        }catch(Exception e){
        	cadenaSinComaFinal = "Sin calificaci\u00f3";
        }
        
        return cadenaSinComaFinal;
    }   

}
