package mx.gob.imss.ctirss.gestionpersonas.servicios.entity;

import java.util.ArrayList;
import java.util.List;

import mx.gob.imss.ctirss.delta.framework.util.Utilerias;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CURP;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Nacimiento;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaEstado;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.persistence.DgCatEstado;
import mx.gob.imss.ctirss.delta.persistence.DgDomicilioGeografico;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoCivil;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoPersona;
import mx.gob.imss.ctirss.delta.persistence.DicPai;
import mx.gob.imss.ctirss.delta.persistence.DicPersonaCalificacion;
import mx.gob.imss.ctirss.delta.persistence.DicSexo;
import mx.gob.imss.ctirss.delta.persistence.DicTipoDocumentoProbatorio;
import mx.gob.imss.ctirss.delta.persistence.DicTipoDomicilio;
import mx.gob.imss.ctirss.delta.persistence.DitDocumentoProbatorio;
import mx.gob.imss.ctirss.delta.persistence.DitFormaContacto;
import mx.gob.imss.ctirss.delta.persistence.DitHistEstadoPersona;
import mx.gob.imss.ctirss.delta.persistence.DitHistPersonaCalificacion;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaView;
import mx.gob.imss.ctirss.delta.persistence.DitPersonafContacto;
import mx.gob.imss.ctirss.delta.persistence.DitPersonafDom;
import mx.gob.imss.ctirss.delta.persistence.DitTipoContacto;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersonaConversor {

    @SuppressWarnings("unused")
    private static final Logger LOG;

    static {
        LOG = LoggerFactory.getLogger(PersonaConversor.class);
    }

    public static void convertirMayusculas(final DitPersonaView ditPersona) {
        ditPersona.setNomNombre(ditPersona.getNomNombre() == null ? null : ditPersona.getNomNombre().toUpperCase().trim());
        ditPersona.setNomPrimerApellido(ditPersona.getNomPrimerApellido() == null ? null : ditPersona.getNomPrimerApellido().toUpperCase().trim());
        ditPersona.setNomSegundoApellido(ditPersona.getNomSegundoApellido() == null ? null : ditPersona.getNomSegundoApellido().toUpperCase().trim());
        ditPersona.setCurp(ditPersona.getCurp() == null ? null : ditPersona.getCurp().toUpperCase().trim());
        ditPersona.setRfc(ditPersona.getRfc() == null ? null : ditPersona.getRfc().toUpperCase().trim());
    }

    public static void convertirMayusculas(final DitPersona ditPersona) {
        ditPersona.setNomNombre(ditPersona.getNomNombre() == null ? null : ditPersona.getNomNombre().toUpperCase().trim());
        ditPersona.setNomPrimerApellido(ditPersona.getNomPrimerApellido() == null ? null : ditPersona.getNomPrimerApellido().toUpperCase().trim());
        ditPersona.setNomSegundoApellido(ditPersona.getNomSegundoApellido() == null ? null : ditPersona.getNomSegundoApellido().toUpperCase().trim());
        ditPersona.setCurp(ditPersona.getCurp() == null ? null : ditPersona.getCurp().toUpperCase().trim());
        ditPersona.setRfc(ditPersona.getRfc() == null ? null : ditPersona.getRfc().toUpperCase().trim());
    }
    
    /**
     * 191807 091012
     * Este metodo hace algo muy similar a convertirPersonaFisicaToEntity, pero con la particularidad que el objeto ditPersona no es nuevo, sino que proviene
     * de una llamada previa al metodo em.find(...) para que pueda hacerse un update a la base de datos
     * @param personaFisica
     * @return
     */
    public static void actualizarPersonaFisicaToEntity(DitPersona ditPersona, Fisica fisica){

        if (fisica != null) {
//            ditPersona.setCveIdPersona(fisica.getIdPersona());
            ditPersona.setCurp(fisica.getCurp());
            ditPersona.setRfc(fisica.getRfc());
            ditPersona.setNomNombre(fisica.getNombre());
            ditPersona.setNomPrimerApellido(fisica.getPrimerApellido());
            ditPersona.setNomSegundoApellido(fisica.getSegundoApellido());
            ditPersona.setFecNacimiento(fisica.getFechaNacimiento());

            // FECHAS DE BITACORA
            ditPersona.setFecRegistroActualizado(fisica.getFechaModificacion());
            ditPersona.setFecRegistroAlta(fisica.getFechaRegistro());
            ditPersona.setFecRegistroBaja(fisica.getFechaBaja());

            // ENTIDAD NACIMIENTO
            if (StringUtils.isNotBlank(fisica.getLugarNacimiento().getClave())) {
                final DgCatEstado entidadNacimiento = new DgCatEstado();
                entidadNacimiento.setCveEnt(fisica.getLugarNacimiento().getClave());
                entidadNacimiento.setNomEnt(fisica.getLugarNacimiento().getNombre());
                ditPersona.setDgCatEstado(entidadNacimiento);
            }

            // PAIS
            if (fisica.getPais() != null && fisica.getPais().getIdPais() != null) {
                final DicPai dicPais = new DicPai();
                dicPais.setCveIdPais(fisica.getPais().getIdPais());
                ditPersona.setDicPai(dicPais);
            }

            // SEXO
            if (fisica.getSexo() != null && Utilerias.isNotBlank(fisica.getSexo().getIdSexo())) {
                final DicSexo dicSexo = new DicSexo();
                dicSexo.setCveIdSexo(fisica.getSexo().getIdSexo().longValue());
                ditPersona.setDicSexo(dicSexo);
            }

            // CALIFICACION (SUBESTADOVALIDADO)
            if (fisica.getPersonaCalificaciones() != null && !fisica.getPersonaCalificaciones().isEmpty()) {
                for (PersonaCalificacion personaCalificacion : fisica.getPersonaCalificaciones()) {
                    if (personaCalificacion.getCalificacion() != null && Utilerias.isNotBlank(personaCalificacion.getCalificacion().getIdCalificacion())) {
                        DicPersonaCalificacion dicPersonaCalificacion = new DicPersonaCalificacion();
                        dicPersonaCalificacion.setCveIdCalificacion(personaCalificacion.getCalificacion().getIdCalificacion());
                        DitHistPersonaCalificacion ditHistcalif = new DitHistPersonaCalificacion();
                        ditHistcalif.setDicPersonaCalificacion(dicPersonaCalificacion);
                        ditHistcalif.setDitPersona(ditPersona);
                        ditPersona.getDitHistPersonaCalificacions().add(ditHistcalif);
                    }
                }
            }

            // ESTADO
            if (fisica.getPersonaEstados() != null && !fisica.getPersonaEstados().isEmpty()) {
                final List<DitHistEstadoPersona> dicEstadoPersonaColl = new ArrayList<DitHistEstadoPersona>();
                for (PersonaEstado personaEstado : fisica.getPersonaEstados()) {
                    if (personaEstado.getEstadoPersona() != null && Utilerias.isNotBlank(personaEstado.getEstadoPersona().getIdEstadoPersona())) {
                        DicEstadoPersona dicEstadoPersona = new DicEstadoPersona();
                        dicEstadoPersona.setCveEstadoPersona(Utilerias.convertir(personaEstado.getEstadoPersona().getIdEstadoPersona()));
                        DitHistEstadoPersona ditHistEstadoPersona = new DitHistEstadoPersona();
                        ditHistEstadoPersona.setDicEstadoPersona(dicEstadoPersona);
                        ditHistEstadoPersona.setDitPersona(ditPersona);
                        dicEstadoPersonaColl.add(ditHistEstadoPersona);
                    }
                }
                ditPersona.setDitHistEstadoPersonas(dicEstadoPersonaColl);
            }

            // DOMICILIO
            if (fisica.getDomicilios() != null && !fisica.getDomicilios().isEmpty()) {
                for (Domicilio domicilio : fisica.getDomicilios()) {
                    if (domicilio.getClave() != null) {
                        DitPersonafDom ditPersonaDomicilio = new DitPersonafDom();
                        ditPersonaDomicilio.setDitPersona(ditPersona);
                        DgDomicilioGeografico dgDomicilio = new DgDomicilioGeografico();
                        dgDomicilio.setDomicilioId(domicilio.getClave().longValue());
                        ditPersonaDomicilio.setDgDomicilioGeografico(dgDomicilio);
                        DicTipoDomicilio dicTipoDomicilio = new DicTipoDomicilio();
                        dicTipoDomicilio.setCveIdTipoDomicilio(Utilerias.convertir(domicilio.getTipoDomicilio() == null ? null : domicilio.getTipoDomicilio().getClave()));
                        ditPersonaDomicilio.setDicTipoDomicilio(dicTipoDomicilio);
                        ditPersona.getDitPersonafDoms().add(ditPersonaDomicilio);
                    }
                }
            }

            // MEDIOS DE CONTACTO
            if (fisica.getMediosContacto() != null && !fisica.getMediosContacto().isEmpty()) {
                for (MedioContacto medioContacto : fisica.getMediosContacto()) {
                    DitFormaContacto ditFormaContacto = new DitFormaContacto();
                    DitTipoContacto ditTipoContacto = new DitTipoContacto();
                    System.out.println("Tipo Medio: " + medioContacto.getTipoMedioContacto().getIdTipoMedioContacto());
                    ditTipoContacto.setCveIdTipoContacto(medioContacto.getTipoMedioContacto().getIdTipoMedioContacto());
                    ditFormaContacto.setDitTipoContacto(ditTipoContacto);
                    ditFormaContacto.setCveIdFormaContacto(medioContacto.getClave());
                    DitPersonafContacto contacto = new DitPersonafContacto();
                    contacto.setDitPersona(ditPersona);
                    contacto.setDitFormaContacto(ditFormaContacto);
                    ditPersona.getDitPersonafContactos().add(contacto);
                }
            }

            // 191807 050912
            // Se agrega esta conversion para el modulo de derechohabientes
            // Estado civil
            if(fisica.getEstadoCivil() != null && Utilerias.isNotBlank(fisica.getEstadoCivil().getIdEstadoCivil())) {
                final DicEstadoCivil dicEstadoCivil = new DicEstadoCivil();
                dicEstadoCivil.setCveIdEstadoCivil(fisica.getEstadoCivil().getIdEstadoCivil().longValue());
                ditPersona.setDicEstadoCivil(dicEstadoCivil);
            }            
            
            // 191807 150812
            // Si es extranjero, entonces no guardara documentos probatorios... esto es una solicion parcial en lo que se implementa correctamente la
            // funcionalidad para almacenar los documentos probatorios, no solo el acta
            if(fisica.getCurp() != null && !fisica.getCurp().substring(11, 13).equals("NE")){
	            // DOCUMENTOS PROBATORIOS
	            if (fisica.getDocumentosProbatorios() != null && !fisica.getDocumentosProbatorios().isEmpty()) {
	                for (DocumentoProbatorio documentoProbatorio : fisica.getDocumentosProbatorios()) {
	                	
	                	//VERIFICA SI ES ACTA DE NACIMIENTO
	                    if (documentoProbatorio instanceof Nacimiento) {
	
	                        Nacimiento nacimiento = (Nacimiento) documentoProbatorio;
	                        // VALIDA QUE LLEGUEN LOS CAMPOS REQUERIDOS:
	                        if (nacimiento.getNoJuzgado() != null && nacimiento.getAnio() != null && nacimiento.getNoLibro() != null) {
	                            DitDocumentoProbatorio ditDocumentoProbatorio = new DitDocumentoProbatorio();
	                            ditDocumentoProbatorio.setCveIdDocumentoProbatorio(documentoProbatorio.getIdDocumentoProbatorio().longValue());
	                            DicTipoDocumentoProbatorio dicTipoDocumentoProbatorio = new DicTipoDocumentoProbatorio();
	                            
	                            /**
	                             * Se modifico la forma de obtener el tipo del documento probatorio
	                             * ahora debe de obtenerse de la entidad DocumentoPorTipo.
	                             */
	                            
	                            if (documentoProbatorio.getDocumentoPorTipo() != null 
	                            		&& documentoProbatorio.getDocumentoPorTipo().getTipoDocumentoProbatorio() != null) {
	                            	
	                                dicTipoDocumentoProbatorio.setCveIdTipoDocumentoProbator(
	                                		documentoProbatorio.getDocumentoPorTipo().getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio());
	                            }
	
	                            ditDocumentoProbatorio.setFecExpedicion(documentoProbatorio.getFechaExpedicion());
	                            ditPersona.getDitDocumentoProbatorios().add(ditDocumentoProbatorio);
	                        }
	                    }//VERIFICA SI ES ACTA DE NACIMIENTO
	                    
	                	//VERIFICA SI ES CURP
	                    if (documentoProbatorio instanceof CURP) {
	                        
	                        DitDocumentoProbatorio ditDocumentoProbatorio = new DitDocumentoProbatorio();
	                        ditDocumentoProbatorio.setCveIdDocumentoProbatorio(documentoProbatorio.getIdDocumentoProbatorio().longValue());
	                        DicTipoDocumentoProbatorio dicTipoDocumentoProbatorio = new DicTipoDocumentoProbatorio();
	                            
	                        if (documentoProbatorio.getDocumentoPorTipo() != null 
	                        		&& documentoProbatorio.getDocumentoPorTipo().getTipoDocumentoProbatorio() != null) {
	                        	
	                            dicTipoDocumentoProbatorio.setCveIdTipoDocumentoProbator(
	                            		documentoProbatorio.getDocumentoPorTipo().getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio());
	                        }
	
	                        ditDocumentoProbatorio.setFecExpedicion(documentoProbatorio.getFechaExpedicion());
	                        ditPersona.getDitDocumentoProbatorios().add(ditDocumentoProbatorio);
	
	                    }//VERIFICA SI ES CURP                    
	                }
	            }
	            /**
	             * 
	             */
            }
            
        }
        
    }
    
	/**
	 * 191807 060812
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
        	cadenaSinComaFinal = "Sin calificaci\u00f3n a causa de una excepci\u00f3n: " + e.getMessage();
        }
        
        return cadenaSinComaFinal;
    }
}
