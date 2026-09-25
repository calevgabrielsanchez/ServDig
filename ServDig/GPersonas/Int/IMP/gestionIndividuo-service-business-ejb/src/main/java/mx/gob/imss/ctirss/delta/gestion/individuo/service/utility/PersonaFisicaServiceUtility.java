package mx.gob.imss.ctirss.delta.gestion.individuo.service.utility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceUtility;
import mx.gob.imss.ctirss.delta.framework.exceptions.TransformacionException;
import mx.gob.imss.ctirss.delta.framework.util.Utilerias;
import mx.gob.imss.ctirss.delta.gestion.individuo.util.DateUtils;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Pais;
import mx.gob.imss.ctirss.delta.model.enums.DocumentosEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoIdentificadorEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.CURP;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Documento;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipo;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoPorTipoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.Nacimiento;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.TipoDocumentoProbatorioRenapoEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.EstadoCivil;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.EstadoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Identificador;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaEstado;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoIdentificador;
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
import mx.gob.imss.ctirss.delta.persistence.DicTipoIdentificador;
import mx.gob.imss.ctirss.delta.persistence.DitAsignacionNss;
import mx.gob.imss.ctirss.delta.persistence.DitAsignacionNssCL3;
import mx.gob.imss.ctirss.delta.persistence.DitDocumentoProbatorio;
import mx.gob.imss.ctirss.delta.persistence.DitFormaContacto;
import mx.gob.imss.ctirss.delta.persistence.DitHistEstadoPersona;
import mx.gob.imss.ctirss.delta.persistence.DitHistPersonaCalificacion;
import mx.gob.imss.ctirss.delta.persistence.DitHistPersonaCalificacionPK;
import mx.gob.imss.ctirss.delta.persistence.DitIdentificador;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaView;
import mx.gob.imss.ctirss.delta.persistence.DitPersonafContacto;
import mx.gob.imss.ctirss.delta.persistence.DitPersonafDom;
import mx.gob.imss.ctirss.delta.persistence.DitTipoContacto;

import org.apache.commons.lang.StringUtils;

@Stateless(name = "personaFisicaServiceUtility", mappedName = "personaFisicaServiceUtility")
public class PersonaFisicaServiceUtility extends AbstractServiceUtility implements PersonaFisicaServiceUtilityLocal {
	@EJB
	private CalificacionesPersonaUtilityServiceLocal calificacionesPersonaUtilityService;

	
	@Override
	public AsignacionNSS transformarNssToModel(DitAsignacionNss ditAsignacion) {
		AsignacionNSS asignacionNss = null;
		
		if(ditAsignacion != null) {
			asignacionNss = new AsignacionNSS();
			asignacionNss.setIdAsignacionNSS(ditAsignacion.getCveIdAsignacionNss());
			asignacionNss.setNss(ditAsignacion.getNumNss());
			asignacionNss.setNssStr(asignacionNss.getNss());
			
			DitPersona ditPersona = ditAsignacion.getDitPersona();
			asignacionNss.setIdPersona(ditPersona.getCveIdPersona());
			asignacionNss.setNombre(ditPersona.getNomNombre());
			asignacionNss.setPrimerApellido(ditPersona.getNomPrimerApellido());
			asignacionNss.setSegundoApellido(ditPersona.getNomSegundoApellido());
			asignacionNss.setSexo(new Sexo());
			asignacionNss.getSexo().setIdSexo(ditPersona.getDicSexo().getCveIdSexo().intValue());
			asignacionNss.getSexo().setDescripcion(ditPersona.getDicSexo().getDesSexo());
			asignacionNss.setLugarNacimiento(new EntidadFederativa());
			asignacionNss.getLugarNacimiento().setNombre(ditPersona.getDgCatEstado().getNomEnt());
			asignacionNss.setFechaNacimiento(ditPersona.getFecNacimiento());
			asignacionNss.setCurp(ditPersona.getCurp());
			asignacionNss.setRfc(ditPersona.getRfc());
		}
		
		return asignacionNss;
	}

	/**
	 * Metodo encargado de realizar la transformacion de un objeto de entidad a uno de modelo
	 * @param fisica
	 * @return
	 */
	public DitPersona transformarAEntidad(Fisica fisica){
		DitPersona ditPersona = null;
		try {
			if (fisica != null) {
        	
            ditPersona = new DitPersona();
            ditPersona.setCveIdPersona(fisica.getIdPersona());
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
                        
                        DitHistPersonaCalificacionPK idHistcalif = new DitHistPersonaCalificacionPK();
                        idHistcalif.setCveIdCalificacion(personaCalificacion.getCalificacion().getIdCalificacion());
                        idHistcalif.setCveIdPersona(fisica.getIdPersona());
						ditHistcalif.setId(idHistcalif);
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
//                    System.out.println("Tipo Medio: " + medioContacto.getTipoMedioContacto().getIdTipoMedioContacto());
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
                DicEstadoCivil dicEstadoCivil = new DicEstadoCivil();
                dicEstadoCivil.setCveIdEstadoCivil(fisica.getEstadoCivil().getIdEstadoCivil().longValue());
                ditPersona.setDicEstadoCivil(dicEstadoCivil);
            }            
            
            /**
             * 150812
             * Si es extranjero, entonces no guardara documentos probatorios... esto es una solicion parcial en lo que se implementa correctamente la
             * funcionalidad para almacenar los documentos probatorios, no solo el acta
             */
            
            /**
             * LUDS: 13/02/2013
             * Se identifico un problema con el CURP, cuando no es NULO y viene vacio o no tiene la 
             * longitud especifica ( 18 ) , al momento de realizar el substring ( 11, 13) 
             * manda un error.
             * 
             * Se agrego la validacion de la longitud de caracteres asi como validar que 
             * sea diferente de "vacio".
             */

			if (StringUtils.isNotBlank(fisica.getCurp())
					&& fisica.getCurp().length() > 13) {
				this.log.debug("La CURP es : " + fisica.getCurp());
				this.log.debug("Longitud del curp :::"
						+ fisica.getCurp().length());

					// DOCUMENTOS PROBATORIOS
					if (fisica.getDocumentosProbatorios() != null
							&& !fisica.getDocumentosProbatorios().isEmpty()) {

	                for (DocumentoProbatorio documentoProbatorio : fisica.getDocumentosProbatorios()) {
	                	
	                	//VERIFICA SI ES ACTA DE NACIMIENTO
	                    if (documentoProbatorio instanceof Nacimiento) {
	
	                        Nacimiento nacimiento = (Nacimiento) documentoProbatorio;
	                        // VALIDA QUE LLEGUEN LOS CAMPOS REQUERIDOS:
	                        if (nacimiento.getNoJuzgado() != null && nacimiento.getAnio() != null && nacimiento.getNoLibro() != null) {
	                            DitDocumentoProbatorio ditDocumentoProbatorio = new DitDocumentoProbatorio();
	                            
	                            if(documentoProbatorio.getIdDocumentoProbatorio() != null){
		                            ditDocumentoProbatorio.setCveIdDocumentoProbatorio(documentoProbatorio.getIdDocumentoProbatorio().longValue());
		                            DicTipoDocumentoProbatorio dicTipoDocumentoProbatorio = new DicTipoDocumentoProbatorio();
		                            
		                            
		                            /**
		                             * Se modifico la forma de obtener el tipo del documento probatorio
		                             * ahora debe de obtenerse de la entidad DocumentoPorTipo.
		                             */
		                            
		                            if (documentoProbatorio.getDocumentoPorTipo() != null && documentoProbatorio.getDocumentoPorTipo().getTipoDocumentoProbatorio() != null) {
		                            	
		                                dicTipoDocumentoProbatorio.setCveIdTipoDocumentoProbator(documentoProbatorio.getDocumentoPorTipo().getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio());
		                            }
		
		                            ditDocumentoProbatorio.setFecExpedicion(documentoProbatorio.getFechaExpedicion());
		                            ditPersona.getDitDocumentoProbatorios().add(ditDocumentoProbatorio);
	                            }
	                        }
	                    }//VERIFICA SI ES ACTA DE NACIMIENTO
	                    
	                	//VERIFICA SI ES CURP
	                    if (documentoProbatorio instanceof CURP) {
	                        
	                        DitDocumentoProbatorio ditDocumentoProbatorio = new DitDocumentoProbatorio();
	                        ditDocumentoProbatorio.setCveIdDocumentoProbatorio(documentoProbatorio.getIdDocumentoProbatorio().longValue());
	                        DicTipoDocumentoProbatorio dicTipoDocumentoProbatorio = new DicTipoDocumentoProbatorio();
	                            
	                        if (documentoProbatorio.getDocumentoPorTipo() != null && documentoProbatorio.getDocumentoPorTipo().getTipoDocumentoProbatorio() != null) {
	                        	
	                            dicTipoDocumentoProbatorio.setCveIdTipoDocumentoProbator(documentoProbatorio.getDocumentoPorTipo().getTipoDocumentoProbatorio().getIdTipoDocumentoProbatorio());
	                        }
	
	                        ditDocumentoProbatorio.setFecExpedicion(documentoProbatorio.getFechaExpedicion());
	                        ditPersona.getDitDocumentoProbatorios().add(ditDocumentoProbatorio);
	
	                    }//VERIFICA SI ES CURP                    
	                }
	            }
	            
            } /** Extranjeros **/
            
            // 191807 311012 Identificadores
            if(fisica.getIdentificadores() != null && !fisica.getIdentificadores().isEmpty()){
            	       		
            	DitIdentificador ditIdentificador = null;
            	DicTipoIdentificador dicTipoIdentificador = null;
            	for(Identificador identificador : fisica.getIdentificadores()){
            		
        			ditIdentificador = new DitIdentificador();
        			
            		String cveIidentificadora;
            		if(identificador.getTipoIdentificador().getIdTipoIdentificador() == TipoIdentificadorEnum.CURP.getCodigo()){
            			cveIidentificadora = fisica.getCurp();
            		}else if(identificador.getTipoIdentificador().getIdTipoIdentificador() == TipoIdentificadorEnum.RFC.getCodigo()){
            			cveIidentificadora = fisica.getRfc();
            		}else{
            			cveIidentificadora = "";
            		}
            		
            		ditIdentificador.setCveIdentificadora(cveIidentificadora);
            		ditIdentificador.setIndVigente(new BigDecimal(identificador.getVigente()));
        			ditIdentificador.setDitPersona(ditPersona);
        			
        			dicTipoIdentificador = new DicTipoIdentificador();
        			dicTipoIdentificador.setCveIdTipoIdentificador(identificador.getTipoIdentificador().getIdTipoIdentificador());
        			dicTipoIdentificador.setDesIdentificador(identificador.getTipoIdentificador().getDesIdentificador());
        			
        			ditIdentificador.setDicTipoIdentificador(dicTipoIdentificador);
        			
        			ditPersona.getDitIdentificadores().add(ditIdentificador);
            		
            	}
            	            	
            }
            
        }
        }catch (Exception e) {
			this.log.error(e.getMessage(), e);
		}
        
        return ditPersona;
    }

	
	
	@Override
	public DitPersonaView convertirPersonaFisicaToEntityView(final Fisica personaFisica) {

        DitPersonaView ditPersona = null;
        if (personaFisica != null) {
            ditPersona = new DitPersonaView();
            ditPersona.setCveIdPersona(Utilerias.convertir(personaFisica.getIdPersona()));
            ditPersona.setCurp(personaFisica.getCurp());
            ditPersona.setRfc(personaFisica.getRfc());
            ditPersona.setNomNombre(personaFisica.getNombre());
            ditPersona.setNomPrimerApellido(personaFisica.getPrimerApellido());
            ditPersona.setNomSegundoApellido(personaFisica.getSegundoApellido());
            ditPersona.setFecNacimiento(personaFisica.getFechaNacimiento());

            // FECHAS DE BITACORA
            ditPersona.setFecRegistroActualizado(personaFisica.getFechaModificacion());
            ditPersona.setFecRegistroAlta(personaFisica.getFechaRegistro());
            ditPersona.setFecRegistroBaja(personaFisica.getFechaBaja());

            // ENTIDAD NACIMIENTO
            if (personaFisica.getLugarNacimiento() != null 
            		&& StringUtils.isNotBlank(personaFisica.getLugarNacimiento().getClave())) {
                final DgCatEstado entidadNacimiento = new DgCatEstado();
                entidadNacimiento.setCveEnt(personaFisica.getLugarNacimiento().getClave());
                entidadNacimiento.setNomEnt(personaFisica.getLugarNacimiento().getNombre());
                ditPersona.setEntidadNacimiento(entidadNacimiento);
            }

            // PAIS
            if (personaFisica.getPais() != null && personaFisica.getPais().getIdPais() != null) {
                final DicPai dicPais = new DicPai();
                dicPais.setCveIdPais(personaFisica.getPais().getIdPais());
                ditPersona.setDicPais(dicPais);
            }

            // SEXO
            if (personaFisica.getSexo() != null && Utilerias.isNotBlank(personaFisica.getSexo().getIdSexo())) {
                final DicSexo dicSexo = new DicSexo();
                dicSexo.setCveIdSexo(personaFisica.getSexo().getIdSexo().longValue());
                ditPersona.setDicSexo(dicSexo);
            }

            // CALIFICACION (SUBESTADOVALIDADO)
            if (personaFisica.getPersonaCalificaciones() != null && !personaFisica.getPersonaCalificaciones().isEmpty()) {
                for (PersonaCalificacion personaCalificacion : personaFisica.getPersonaCalificaciones()) {
                    if (personaCalificacion.getCalificacion() != null && Utilerias.isNotBlank(personaCalificacion.getCalificacion().getIdCalificacion())) {
                        DicPersonaCalificacion dicPersonaCalificacion = new DicPersonaCalificacion();
                        dicPersonaCalificacion.setCveIdCalificacion(personaCalificacion.getCalificacion().getIdCalificacion());
                        DitHistPersonaCalificacion ditHistcalif = new DitHistPersonaCalificacion();
                        ditHistcalif.setDicPersonaCalificacion(dicPersonaCalificacion);
                        ditHistcalif.setDitPersonaView(ditPersona);
                        ditPersona.getDitHistPersonaCalificacions().add(ditHistcalif);
                    }
                }
            }

            // ESTADO
            if (personaFisica.getPersonaEstados() != null && !personaFisica.getPersonaEstados().isEmpty()) {
                for (PersonaEstado personaEstado : personaFisica.getPersonaEstados()) {
                    if (personaEstado.getEstadoPersona() != null && Utilerias.isNotBlank(personaEstado.getEstadoPersona().getIdEstadoPersona())) {
                        DicEstadoPersona dicEstadoPersona = new DicEstadoPersona();
                        dicEstadoPersona.setCveEstadoPersona(Utilerias.convertir(personaEstado.getEstadoPersona().getIdEstadoPersona()));
                        ditPersona.getDicEstadoPersona().add(dicEstadoPersona);
                    }
                }
            }

            // DOMICILIO
            if (personaFisica.getDomicilios() != null && !personaFisica.getDomicilios().isEmpty()) {
                for (Domicilio domicilio : personaFisica.getDomicilios()) {
                    if (domicilio.getClave() != null) {
                        DitPersonafDom ditPersonaDomicilio = new DitPersonafDom();
                        ditPersonaDomicilio.setDitPersonaView(ditPersona);
                        DgDomicilioGeografico dgDomicilio = new DgDomicilioGeografico();
                        dgDomicilio.setDomicilioId(domicilio.getClave().longValue());
                        ditPersonaDomicilio.setDgDomicilioGeografico(dgDomicilio);
                        DicTipoDomicilio dicTipoDomicilio = new DicTipoDomicilio();
                        dicTipoDomicilio.setCveIdTipoDomicilio(Utilerias.convertir(domicilio.getTipoDomicilio().getClave()));
                        ditPersonaDomicilio.setDicTipoDomicilio(dicTipoDomicilio);
                        ditPersona.getDitPersonafDoms().add(ditPersonaDomicilio);

                        System.out.println("PersonaConversor. convertirPersonaFisicaToEntity. Se setea DitFPersonaFDom con lo siguientes datos: \n" + "idDomicilio: " + ditPersonaDomicilio.getDgDomicilioGeografico().getDomicilioId() + "\n " + "idPersona: "
                                + ditPersonaDomicilio.getDitPersona().getCveIdPersona() + "\n " + "idTipoDomicilio: " + ditPersonaDomicilio.getDicTipoDomicilio().getCveIdTipoDomicilio() + "\n ");
                    }
                }
            }

            // MEDIOS DE CONTACTO
            if (personaFisica.getMediosContacto() != null && !personaFisica.getMediosContacto().isEmpty()) {
                for (MedioContacto medioContacto : personaFisica.getMediosContacto()) {
                    DitFormaContacto ditFormaContacto = new DitFormaContacto();
                    DitTipoContacto ditTipoContacto = new DitTipoContacto();
                    ditTipoContacto.setCveIdTipoContacto(medioContacto.getTipoMedioContacto() == null ? null : medioContacto.getTipoMedioContacto().getIdTipoMedioContacto());
                    ditFormaContacto.setDitTipoContacto(ditTipoContacto);
                    ditFormaContacto.setCveIdFormaContacto(medioContacto.getClave() == null ? null : new Long(medioContacto.getClave()));
                    DitPersonafContacto contacto = new DitPersonafContacto();
                    contacto.setDitPersonaView(ditPersona);
                    contacto.setDitFormaContacto(ditFormaContacto);
                    ditPersona.getDitPersonafContactos().add(contacto);
                }
            }

            // DOCUMENTOS PROBATORIOS
            if (personaFisica.getDocumentosProbatorios() != null && !personaFisica.getDocumentosProbatorios().isEmpty()) {
                for (DocumentoProbatorio documentoProbatorio : personaFisica.getDocumentosProbatorios()) {
                    DitDocumentoProbatorio ditDocumentoProbatorio = new DitDocumentoProbatorio();
                    
                    if (documentoProbatorio.getIdDocumentoProbatorio() != null) {
                    	ditDocumentoProbatorio.setCveIdDocumentoProbatorio(documentoProbatorio.getIdDocumentoProbatorio().longValue());
                    }
                    
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
                    
                    
                    
                    //Se comento por que ya no existe este atributo
                    //ditDocumentoProbatorio.setDicTipoDocumentoProbatorio(dicTipoDocumentoProbatorio);
                     ditDocumentoProbatorio.setFecExpedicion(documentoProbatorio.getFechaExpedicion());
                     ditPersona.getDitDocumentoProbatorios().add(ditDocumentoProbatorio);
                }
            }

        }
        return ditPersona;
    }



	/**
	 * Metodo encargado de realizar la transformacion de un objeto de modelo a uno de entidad
	 * @param fisica
	 * @return
	 */
	@Override
    public Fisica transformarAModelo(DitPersona ditPersona) {
    	
        Fisica personaFisica = null;
        if (ditPersona != null) {
        	
            personaFisica = new Fisica();
            personaFisica.setIdPersona(ditPersona.getCveIdPersona());
            personaFisica.setCurp(ditPersona.getCurp());
            personaFisica.setRfc(ditPersona.getRfc());
            personaFisica.setNombre(ditPersona.getNomNombre());
            personaFisica.setPrimerApellido(ditPersona.getNomPrimerApellido());
            personaFisica.setSegundoApellido(ditPersona.getNomSegundoApellido());

            // FECHAS DE BITACORA
            personaFisica.setFechaBaja(ditPersona.getFecRegistroBaja());
            personaFisica.setFechaModificacion(ditPersona.getFecRegistroActualizado());
            personaFisica.setFechaRegistro(ditPersona.getFecRegistroAlta());
            personaFisica.setMesRegistroNac(ditPersona.getNumMesNacReg());
            personaFisica.setAnioRegistroNac(ditPersona.getNumAnioNacReg());

            // FECHA NACIMIENTO
            Date fechaNacimiento = ditPersona.getFecNacimiento();
            personaFisica.setFechaNacimiento(fechaNacimiento);
            if (fechaNacimiento != null) {
                personaFisica.setFechaNacimientoFormateada(DateUtils.dateToStringConFormato(fechaNacimiento, "dd/MM/yyyy"));
            }

            // ENTIDAD NACIMIENTO
            if (ditPersona.getDgCatEstado() != null) {
                personaFisica.getLugarNacimiento().setNombre(ditPersona.getDgCatEstado().getNomEnt());
                personaFisica.getLugarNacimiento().setClave(ditPersona.getDgCatEstado().getCveEnt());
            }

            // PAIS
            if (ditPersona.getDicPai() != null) {
                final Pais pais = new Pais();
                pais.setIdPais(new Long(ditPersona.getDicPai().getCveIdPais()).intValue());
                pais.setDescripcion(ditPersona.getDicPai().getDesPais());
                pais.setNacionalidad(ditPersona.getDicPai().getDesNacionalidad());
                personaFisica.setPais(pais);
            }

            // SEXO
            if (ditPersona.getDicSexo() != null) {
                Sexo sexo = new Sexo();
                sexo.setDescripcion(ditPersona.getDicSexo().getDesSexo());
                sexo.setIdSexo(Utilerias.convertir(ditPersona.getDicSexo().getCveIdSexo()));
                personaFisica.setSexo(sexo);
            }

            // ESTADO CIVIL
            if (ditPersona.getDicEstadoCivil() != null) {
                EstadoCivil estadoCivil = new EstadoCivil();
                estadoCivil.setDescripcion(ditPersona.getDicEstadoCivil().getDesEstadoCivil());
                estadoCivil.setIdEstadoCivil(ditPersona.getDicEstadoCivil().getCveIdEstadoCivil().intValue());
                personaFisica.setEstadoCivil(estadoCivil);
            }

            // CALIFICACION (SUB_ESTADO_VALIDADO)
			try {
				List<PersonaCalificacion> listCalificacionesVigentes = calificacionesPersonaUtilityService
						.filtrarCalificacionesVigentesPersonaFisica(ditPersona.getDitHistPersonaCalificacions());
				personaFisica.setPersonaCalificaciones(listCalificacionesVigentes);
				personaFisica.setSubEstadosFormateados(calificacionesPersonaUtilityService
								.getCalificacionesVigentesAsString(listCalificacionesVigentes));
			} catch (TransformacionException e) {
				log.error(e);
			}

            // ESTADO
            if (ditPersona.getDitHistEstadoPersonas() != null && !ditPersona.getDitHistEstadoPersonas().isEmpty()) {
                for (DitHistEstadoPersona ditHistEstadoPersona : ditPersona.getDitHistEstadoPersonas()) {
                    DicEstadoPersona dicEstadoPersona = ditHistEstadoPersona.getDicEstadoPersona();
                    if (dicEstadoPersona.getCveEstadoPersona() != null) {

                        EstadoPersona estadoPersonaAsignado = new EstadoPersona();
                        estadoPersonaAsignado.setIdEstadoPersona(Utilerias.convertir(dicEstadoPersona.getCveEstadoPersona()));
                        estadoPersonaAsignado.setDescripcion(dicEstadoPersona.getDesEstadoPersona());
                        PersonaEstado personaEstado = new PersonaEstado();
                        personaEstado.setEstadoPersona(estadoPersonaAsignado);
                        personaFisica.getPersonaEstados().add(personaEstado);
                    }
                }
                personaFisica.setEstadosFormateados(quitarComaFinal(ditPersona.getDicEstadoPersonaAsString()));
            }

            // DOMICILIO
            if (ditPersona.getDitPersonafDoms() != null && !ditPersona.getDitPersonafDoms().isEmpty()) {
                for (DitPersonafDom ditPersonafDom : ditPersona.getDitPersonafDoms()) {
                    DgDomicilioGeografico dgDomicilio = ditPersonafDom.getDgDomicilioGeografico();
                    if (dgDomicilio.getDomicilioId() != null) {
                        Domicilio domicilio = new Domicilio();
                        domicilio.setClave(Utilerias.convertir(dgDomicilio.getDomicilioId()));
                        personaFisica.getDomicilios().add(domicilio);
                    }
                }
            }
            
            //NSS - RECUPERA EL PRIMER NSS ENCONTRADO EN LA LISTA
            List<DitAsignacionNss> listaAsignacionNss = ditPersona.getDitAsignacionNsses();
            if (listaAsignacionNss != null){
            	for (DitAsignacionNss ditAsignacionNss : listaAsignacionNss){
            		personaFisica.setNss(ditAsignacionNss.getNumNss());
            		personaFisica.setCveIdAsignacionNSS(ditAsignacionNss.getCveIdAsignacionNss());
            		break;            		
            	}
            }
        }
        
        // 191807 311012 Identificadores
        if(ditPersona.getDitIdentificadores() != null && !ditPersona.getDitIdentificadores().isEmpty()){
        	Fisica personaId = new Fisica();
        	personaId.setCveFisica(personaFisica.getCveFisica());
        	Identificador identificador = null;
        	TipoIdentificador tipoIdentificador = null;
        	for(DitIdentificador ditIdentificador : ditPersona.getDitIdentificadores()){

    			identificador = new Identificador();
    			
        		String identificadora;
        		if(ditIdentificador.getDicTipoIdentificador().getCveIdTipoIdentificador() == TipoIdentificadorEnum.CURP.getCodigo()){
        			identificadora = ditPersona.getCurp();
        		}else if(ditIdentificador.getDicTipoIdentificador().getCveIdTipoIdentificador() == TipoIdentificadorEnum.RFC.getCodigo()){
        			identificadora = ditPersona.getRfc();
        		}else{
        			identificadora = "";
        		}
        		
        		identificador.setIdentificadora(identificadora);
        		identificador.setVigente(identificador.getVigente());
        		
        		personaId.setIdPersona(ditPersona.getCveIdPersona());
        		
        		identificador.setPersona(personaId);
        		
        		tipoIdentificador = new TipoIdentificador();
        		tipoIdentificador.setIdTipoIdentificador(ditIdentificador.getDicTipoIdentificador().getCveIdTipoIdentificador());
        		tipoIdentificador.setDesIdentificador(ditIdentificador.getDicTipoIdentificador().getDesIdentificador());
        		
        		identificador.setTipoIdentificador(tipoIdentificador);
        		
        		personaFisica.getIdentificadores().add(identificador);
    			
	        }
        	
        }

        return personaFisica;
    }
    
    
	@Override
	public Fisica transformarAModelSoloDatosPersonales(DitPersona ditPersona) {
		 Fisica personaFisica = null;
	        if (ditPersona != null) {
	        	
	            personaFisica = new Fisica();
	            personaFisica.setIdPersona(ditPersona.getCveIdPersona());
	            personaFisica.setCurp(ditPersona.getCurp());
	            personaFisica.setRfc(ditPersona.getRfc());
	            personaFisica.setNombre(ditPersona.getNomNombre());
	            personaFisica.setPrimerApellido(ditPersona.getNomPrimerApellido());
	            personaFisica.setSegundoApellido(ditPersona.getNomSegundoApellido());

	            // FECHAS DE BITACORA
	            personaFisica.setFechaBaja(ditPersona.getFecRegistroBaja());
	            personaFisica.setFechaModificacion(ditPersona.getFecRegistroActualizado());
	            personaFisica.setFechaRegistro(ditPersona.getFecRegistroAlta());

	            // FECHA NACIMIENTO
	            Date fechaNacimiento = ditPersona.getFecNacimiento();
	            personaFisica.setFechaNacimiento(fechaNacimiento);
	        
	            if (fechaNacimiento != null) {
	                personaFisica.setFechaNacimientoFormateada(DateUtils.dateToStringConFormato(fechaNacimiento, "dd/MM/yyyy"));
	            }
	            
	            Date fechaDefuncion=ditPersona.getFecDefuncion();
	            if(fechaDefuncion!=null){
	            	personaFisica.setFechaDefuncion(fechaDefuncion);	
	            }
	            

	            // ENTIDAD NACIMIENTO
	            if (ditPersona.getDgCatEstado() != null) {
	                personaFisica.getLugarNacimiento().setNombre(ditPersona.getDgCatEstado().getNomEnt());
	                personaFisica.getLugarNacimiento().setClave(ditPersona.getDgCatEstado().getCveEnt());
	            }

	            // PAIS
	            if (ditPersona.getDicPai() != null) {
	                final Pais pais = new Pais();
	                pais.setIdPais(new Long(ditPersona.getDicPai().getCveIdPais()).intValue());
	                pais.setDescripcion(ditPersona.getDicPai().getDesPais());
	                pais.setNacionalidad(ditPersona.getDicPai().getDesNacionalidad());
	                personaFisica.setPais(pais);
	            }

	            // SEXO
	            if (ditPersona.getDicSexo() != null) {
	                Sexo sexo = new Sexo();
	                sexo.setDescripcion(ditPersona.getDicSexo().getDesSexo());
	                sexo.setIdSexo(Utilerias.convertir(ditPersona.getDicSexo().getCveIdSexo()));
	                personaFisica.setSexo(sexo);
	            }
	        }
	        
	        return personaFisica;
	}

	@Override
	public Fisica convertirEntityViewToPersonaFisica(DitPersonaView ditPersonaView) {
        Fisica personaFisica = null;
        if (ditPersonaView != null) {
            personaFisica = new Fisica();
            personaFisica.setIdPersona(Utilerias.convertir(ditPersonaView.getCveIdPersona()));
            personaFisica.setCurp(ditPersonaView.getCurp());
            personaFisica.setRfc(ditPersonaView.getRfc());
            personaFisica.setNombre(ditPersonaView.getNomNombre());
            personaFisica.setPrimerApellido(ditPersonaView.getNomPrimerApellido());
            personaFisica.setSegundoApellido(ditPersonaView.getNomSegundoApellido());

            // FECHAS DE BITACORA
            personaFisica.setFechaBaja(ditPersonaView.getFecRegistroBaja());
            personaFisica.setFechaModificacion(ditPersonaView.getFecRegistroActualizado());
            personaFisica.setFechaDefuncion(ditPersonaView.getFecDefuncion());
            personaFisica.setFechaRegistro(ditPersonaView.getFecRegistroAlta());
            personaFisica.setMesRegistroNac(ditPersonaView.getNumMesNacReg());
            personaFisica.setAnioRegistroNac(ditPersonaView.getNumAnioNacReg());

            // FECHA NACIMIENTO
            Date fechaNacimiento = ditPersonaView.getFecNacimiento();
            personaFisica.setFechaNacimiento(fechaNacimiento);
            if (fechaNacimiento != null) {
                personaFisica.setFechaNacimientoFormateada(DateUtils.dateToStringConFormato(fechaNacimiento, "dd/MM/yyyy"));
            }

            // ENTIDAD NACIMIENTO
            if (ditPersonaView.getEntidadNacimiento() != null) {
                personaFisica.getLugarNacimiento().setNombre(ditPersonaView.getEntidadNacimiento().getNomEnt());
                personaFisica.getLugarNacimiento().setClave(ditPersonaView.getEntidadNacimiento().getCveEnt());
            }

            // PAIS
            if (ditPersonaView.getDicPais() != null) {
                final Pais pais = new Pais();
                pais.setIdPais(new Long(ditPersonaView.getDicPais().getCveIdPais()).intValue());
                pais.setDescripcion(ditPersonaView.getDicPais().getDesPais());
                pais.setNacionalidad(ditPersonaView.getDicPais().getDesNacionalidad());
                personaFisica.setPais(pais);
            }

            // SEXO
            if (ditPersonaView.getDicSexo() != null) {
                Sexo sexo = new Sexo();
                sexo.setDescripcion(ditPersonaView.getDicSexo().getDesSexo());
                sexo.setIdSexo(Utilerias.convertir(ditPersonaView.getDicSexo().getCveIdSexo()));
                personaFisica.setSexo(sexo);
            }

            // CALIFICACION (SUBESTADOVALIDADO)
            try {
				List<PersonaCalificacion> listCalificacionesVigentes = calificacionesPersonaUtilityService.
						filtrarCalificacionesVigentesPersonaFisica(ditPersonaView.getDitHistPersonaCalificacions());
				personaFisica.setPersonaCalificaciones(listCalificacionesVigentes);
				personaFisica.setSubEstadosFormateados(calificacionesPersonaUtilityService.
						getCalificacionesVigentesAsString(listCalificacionesVigentes));
			} catch (TransformacionException e) {
				log.error(e);
			}

            //ESTADO
            if (ditPersonaView.getDicEstadoPersona() != null && !ditPersonaView.getDicEstadoPersona().isEmpty()) {
                for (DicEstadoPersona dicEstadoPersona : ditPersonaView.getDicEstadoPersona()) {
                    if (Utilerias.isNotBlank(dicEstadoPersona.getCveEstadoPersona())) {

                        EstadoPersona estadoPersonaAsignado = new EstadoPersona();
                        estadoPersonaAsignado.setIdEstadoPersona(Utilerias.convertir(dicEstadoPersona.getCveEstadoPersona()));
                        estadoPersonaAsignado.setDescripcion(dicEstadoPersona.getDesEstadoPersona());
                        PersonaEstado personaEstado = new PersonaEstado();
                        personaEstado.setEstadoPersona(estadoPersonaAsignado);
                        personaFisica.getPersonaEstados().add(personaEstado);
                    }
                }
                personaFisica.setEstadosFormateados(quitarComaFinal(ditPersonaView.getDicEstadoPersonaAsString()));
            }

            //NSS - RECUPERA EL PRIMER NSS ENCONTRADO EN LA LISTA
            List<DitAsignacionNss> listaAsignacionNss = ditPersonaView.getDitAsignacionNss();
            if (listaAsignacionNss != null){
            	for (DitAsignacionNss ditAsignacionNss : listaAsignacionNss){
            		personaFisica.setNss(ditAsignacionNss.getNumNss());
            		break;
            	}
            }
            
            
            
        }
        return personaFisica;
    }

	@Override
	public List<Fisica> covertirListaEntityViewToPersonaFisica(List<DitPersonaView> listaDitPersonaView) {
        LinkedList<Fisica> listaPersonaFisica = null;

        if (listaDitPersonaView != null) {

            listaPersonaFisica = new LinkedList<Fisica>();
            for (DitPersonaView ditPersonaView : listaDitPersonaView) {
                listaPersonaFisica.add(convertirEntityViewToPersonaFisica(ditPersonaView));
            }
        }
        return listaPersonaFisica;
    }
	
	
	
	/**
	 *  Se creo este transformado para poder recuperar los NSS ligados a una misma persona.
	 * @param listaAsignaciones
	 * @return
	 */
	public List<Fisica> covertirListaAsignacionEntityToPersonaFisica(List<DitAsignacionNss> listaAsignaciones) {
        LinkedList<Fisica> listaPersonaFisica = null;

        if (listaAsignaciones != null) {

            listaPersonaFisica = new LinkedList<Fisica>();
            for (DitAsignacionNss ditAsignacionNss : listaAsignaciones) {
            	
            	Fisica f = transformarAModelo(ditAsignacionNss.getDitPersona());
            	f.setNss(ditAsignacionNss.getNumNss());
                listaPersonaFisica.add(f);
            }
        }
        return listaPersonaFisica;
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
    
    /**
     * 161012
     * Este metodo convierte a mayusculas el contenido String del objeto ditPersona
     * @param ditPersona
     */
    public void convertirMayusculas(final DitPersona ditPersona) {
        ditPersona.setNomNombre(ditPersona.getNomNombre() == null ? null : ditPersona.getNomNombre().toUpperCase().trim());
        ditPersona.setNomPrimerApellido(ditPersona.getNomPrimerApellido() == null ? null : ditPersona.getNomPrimerApellido().toUpperCase().trim());
        ditPersona.setNomSegundoApellido(ditPersona.getNomSegundoApellido() == null ? null : ditPersona.getNomSegundoApellido().toUpperCase().trim());
        ditPersona.setCurp(ditPersona.getCurp() == null ? null : ditPersona.getCurp().toUpperCase().trim());
        ditPersona.setRfc(ditPersona.getRfc() == null ? null : ditPersona.getRfc().toUpperCase().trim());
    }
    
    /**
	 * Método que asigna al atributo correspondiente el documento probatorio
	 * dependiendo de su tipo, utilizando los métodos auxiliares para no
	 * agregar cada documento a la lista de documentos probatorios
	 * 
	 * @param fisica: objeto al que se le settean los documentos probatorios
	 * @param documentosProbatorios: lista de los documentos probatorios
	 */
    @Override
	public void asignarDocumentosProbatorios(Fisica fisica,
			List<DocumentoProbatorio> documentosProbatorios) {
    	
		if (documentosProbatorios != null && !documentosProbatorios.isEmpty()) {

			Nacimiento actaNacimiento = null;
			CURP doctoAux = null;

			for (DocumentoProbatorio docProbatorio : documentosProbatorios) {
				if (docProbatorio instanceof Nacimiento) {
					actaNacimiento = (Nacimiento) docProbatorio;
					fisica.setActaNacimientoAux(actaNacimiento);
				} else if (docProbatorio instanceof CURP) {
					doctoAux = (CURP) docProbatorio;
					
					if ((doctoAux.getNumTipoDocumento() != null && 
							doctoAux.getNumTipoDocumento().intValue() == TipoDocumentoProbatorioRenapoEnum.ACTA_NACIMIENTO.getValor().intValue())
							|| (doctoAux.getDocumentoPorTipo() != null && 
									doctoAux.getDocumentoPorTipo().getIdDocumentoPorTipo().equals(DocumentoPorTipoEnum.ACTA_NACIMIENTO.getId()))) {
						
						actaNacimiento = new Nacimiento();
						actaNacimiento.setAnio(doctoAux.getAnioRegistro().intValue());
		                actaNacimiento.setTomo(doctoAux.getNoTomo());
		                actaNacimiento.setCrip(doctoAux.getCrip());
		                actaNacimiento.setNoFoja(doctoAux.getNoFoja());
		                actaNacimiento.setNoLibro(doctoAux.getNoLibro());
		                actaNacimiento.setNoActa(doctoAux.getNoActa());
		                actaNacimiento.setMunicipio(doctoAux.getMunicipio());
		                
		                Documento documento = new Documento();
		                documento.setCveIdDocumento(DocumentosEnum.ACTA_NACIMIENTO.getId());
		                documento.setDesDocumento(DocumentosEnum.ACTA_NACIMIENTO.getDescripcion());
		                
		                DocumentoPorTipo documentoPorTipo = new DocumentoPorTipo();
		                documentoPorTipo.setIdDocumentoPorTipo(DocumentoPorTipoEnum.ACTA_NACIMIENTO.getId());
		                documentoPorTipo.setDocumento(documento);
		                
		                actaNacimiento.setDocumentoPorTipo(documentoPorTipo);
		                
		                fisica.setActaNacimientoAux(actaNacimiento);
					} else if ((doctoAux.getNumTipoDocumento() != null && 
							doctoAux.getNumTipoDocumento().intValue() == TipoDocumentoProbatorioRenapoEnum.DOCUMENTO_MIGRATORIO.getValor().intValue())
							|| (doctoAux.getDocumentoPorTipo() != null && 
									doctoAux.getDocumentoPorTipo().getIdDocumentoPorTipo().equals(DocumentoPorTipoEnum.DOCUMENTO_MIGRATORIO.getId()))) {
						fisica.setDocumentoMigratorioAux(doctoAux);
					} else if ((doctoAux.getNumTipoDocumento() != null && 
							doctoAux.getNumTipoDocumento().intValue() == TipoDocumentoProbatorioRenapoEnum.CARTA_NATURALIZACION.getValor().intValue())
							|| (doctoAux.getDocumentoPorTipo() != null && 
									doctoAux.getDocumentoPorTipo().getIdDocumentoPorTipo().equals(DocumentoPorTipoEnum.CARTA_NATURALIZACION.getId()))) {
						fisica.setCartaNaturalizacionAux(doctoAux);
					} else if ((doctoAux.getNumTipoDocumento() != null && 
							doctoAux.getNumTipoDocumento().intValue() == TipoDocumentoProbatorioRenapoEnum.NUMERO_UNICO_DE_EXTRANJERO.getValor().intValue())
							|| (doctoAux.getDocumentoPorTipo() != null && 
									doctoAux.getDocumentoPorTipo().getIdDocumentoPorTipo().equals(DocumentoPorTipoEnum.NUMERO_UNICO_EXTRANJERO.getId()))) {
						fisica.setNumeroUnicoExtranjeroAux(doctoAux);
					} else if ((doctoAux.getNumTipoDocumento() != null && 
							doctoAux.getNumTipoDocumento().intValue() == TipoDocumentoProbatorioRenapoEnum.CERTIFICADO_DE_NACIONALIDAD_MEXICANA.getValor().intValue())
							|| (doctoAux.getDocumentoPorTipo() != null && 
									doctoAux.getDocumentoPorTipo().getIdDocumentoPorTipo().equals(DocumentoPorTipoEnum.CERTIFICADO_NACIONALIDAD_MEXICANA.getId()))) {
						fisica.setCertificadoNacionalidadMexicanaAux(doctoAux);
					} else if ((doctoAux.getNumTipoDocumento() != null && 
							doctoAux.getNumTipoDocumento().intValue() == TipoDocumentoProbatorioRenapoEnum.OFICIO_SOLICITANTE_DE_REFUGIADO.getValor().intValue())
							|| (doctoAux.getDocumentoPorTipo() != null && 
									doctoAux.getDocumentoPorTipo().getIdDocumentoPorTipo().equals(DocumentoPorTipoEnum.OFICIO_SOLICITANTE_REFUGIADO.getId()))) {
						fisica.setOficioSolicitanteRefugiadoAux(doctoAux);
					} else if ((doctoAux.getNumTipoDocumento() != null && 
							doctoAux.getNumTipoDocumento().intValue() == TipoDocumentoProbatorioRenapoEnum.FORMA_MIGRATORIA_TURISTA.getValor().intValue())
							|| (doctoAux.getDocumentoPorTipo() != null && 
									doctoAux.getDocumentoPorTipo().getIdDocumentoPorTipo().equals(DocumentoPorTipoEnum.FORMA_MIGRATORIA_TURISTA.getId()))) {
						fisica.setFormaMigratoriaTuristaAux(doctoAux);
					}
				}
			}
		}
	}
    
    
    @Override
	public AsignacionNSS transformarNssCL3ToModel(DitAsignacionNssCL3 ditAsignacion) {
		AsignacionNSS asignacionNss = null;
		
		if(ditAsignacion != null) {
			asignacionNss = new AsignacionNSS();
			asignacionNss.setIdAsignacionNSS(ditAsignacion.getCveIdAsignacionNss());
			asignacionNss.setNss(ditAsignacion.getNumNss());
			asignacionNss.setNssStr(asignacionNss.getNss());
			
			DitPersona ditPersona = ditAsignacion.getDitPersona();
			asignacionNss.setIdPersona(ditPersona.getCveIdPersona());
			asignacionNss.setNombre(ditPersona.getNomNombre());
			asignacionNss.setPrimerApellido(ditPersona.getNomPrimerApellido());
			asignacionNss.setSegundoApellido(ditPersona.getNomSegundoApellido());
			asignacionNss.setSexo(new Sexo());
			asignacionNss.getSexo().setIdSexo(ditPersona.getDicSexo().getCveIdSexo().intValue());
			asignacionNss.getSexo().setDescripcion(ditPersona.getDicSexo().getDesSexo());
			asignacionNss.setLugarNacimiento(new EntidadFederativa());
			asignacionNss.getLugarNacimiento().setNombre(ditPersona.getDgCatEstado().getNomEnt());
			asignacionNss.getLugarNacimiento().setClave(ditPersona.getDgCatEstado().getCveEnt());
			asignacionNss.setFechaNacimiento(ditPersona.getFecNacimiento());
			asignacionNss.setCurp(ditPersona.getCurp());
			asignacionNss.setRfc(ditPersona.getRfc());
		}
		
		return asignacionNss;
	}
}
