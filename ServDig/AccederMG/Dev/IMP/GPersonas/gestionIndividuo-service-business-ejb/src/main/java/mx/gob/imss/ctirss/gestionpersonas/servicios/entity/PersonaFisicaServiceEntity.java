package mx.gob.imss.ctirss.gestionpersonas.servicios.entity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.Tuple;

import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.NssRelacionadoVariasPersonasException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonasNoLocalizadasException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.framework.util.Utilerias;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.utility.PersonaFisicaServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.AfectarDatosPersonaWrapper;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.persistence.DgCatEstado;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoCivil;
import mx.gob.imss.ctirss.delta.persistence.DicPai;
import mx.gob.imss.ctirss.delta.persistence.DicSexo;
import mx.gob.imss.ctirss.delta.persistence.DitAsignacionNss;
import mx.gob.imss.ctirss.delta.persistence.DitAsignacionNssCL3;
import mx.gob.imss.ctirss.delta.persistence.DitDoctosPersona;
import mx.gob.imss.ctirss.delta.persistence.DitDoctosPersonaPK;
import mx.gob.imss.ctirss.delta.persistence.DitDocumentoProbatorio;
import mx.gob.imss.ctirss.delta.persistence.DitHistEstadoPersona;
import mx.gob.imss.ctirss.delta.persistence.DitHistEstadoPersonaPK;
import mx.gob.imss.ctirss.delta.persistence.DitHistPersonaCalificacion;
import mx.gob.imss.ctirss.delta.persistence.DitHistPersonaCalificacionPK;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaAutorizada;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaFisica;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaView;
import mx.gob.imss.ctirss.delta.persistence.DitPersonafContacto;
import mx.gob.imss.ctirss.delta.persistence.DitPersonafDom;
import mx.gob.imss.ctirss.delta.persistence.DitSocio;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 * 121012
 * @author ICCSRG
 *
 */
@Stateless(name = "personaFisicaServiceEntity", mappedName = "personaFisicaServiceEntity")
public class PersonaFisicaServiceEntity extends AbstractServiceEntity implements PersonaFisicaServiceEntityLocal {
	
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yy");
	
	@EJB
	private PersonaFisicaServiceUtilityLocal personaFisicaServiceUtility;
	
	
	
	@Override
	public Boolean isSocio(Long idPersona) {
		Boolean isSocio = false;
		
		Criteria querySocio = this.getSession().createCriteria(DitSocio.class);
		querySocio.setProjection(Projections.rowCount());
		Criteria queryFisica = querySocio.createCriteria("ditPersonaFisica");
		queryFisica.createAlias("ditPersona", "persona");
		queryFisica.add(Restrictions.eq("persona.cveIdPersona", idPersona));
		
		Long numeroCoincidencias = (Long) querySocio.uniqueResult(); 
		
		log.debug("se encontraron " + numeroCoincidencias + " como socio");
		
		if(numeroCoincidencias.intValue() > 0) {
			isSocio = true;
		}

		
		return isSocio;
	}

	@Override
	public Boolean isPersonaAutorizada(Long idPersona) {
		Boolean isPersonaAutorizada = false;
		Criteria queryPersonaAutorizada = this.getSession().createCriteria(DitPersonaAutorizada.class);
		queryPersonaAutorizada.setProjection(Projections.rowCount());
		Criteria queryFisica = queryPersonaAutorizada.createCriteria("ditPersonaFisica");
		queryFisica.createAlias("ditPersona", "persona");
		queryFisica.add(Restrictions.eq("persona.cveIdPersona", idPersona));
		
		Long numeroCoincidencias = (Long) queryPersonaAutorizada.uniqueResult(); 
		
		log.debug("se encontraron " + numeroCoincidencias + " como persona autorizada");
		
		if(numeroCoincidencias.intValue() > 0) {
			isPersonaAutorizada = true;
		}
		
		return isPersonaAutorizada;
	}

	/**
	 * 191807 161012
	 * Metodo encargado de registrar una persona fisica en BD 
	 * @param fisica
	 */
    @Override
    public Fisica registrar(DitPersona ditPersona) {
       	Fisica personaFisica = null;
	    if (ditPersona != null) {
	    	
	        //EJECUTA VALIDACIONES DE CATAlogOS
	        validarEntidadesDependientes(ditPersona);
	
	        //ASIGNA LA FECHA DE ALTA A LA PERSONA FISICA Y SUS OBJETOS ASOCIADOS
	        Date fechaAlta = new Date();
	        ditPersona.setFecRegistroAlta(fechaAlta);
//	        ditPersona.setCveIdPersona(1L); // <-- quitar esta mamada ya que provoca un fallo a proposito
	        
	        em.persist(ditPersona);
		
	        // DOMICILIOS (RELACION)
	        for (DitPersonafDom ditPersonafDom : ditPersona.getDitPersonafDoms()) {
	            if (ditPersonafDom.getDgDomicilioGeografico() != null && Utilerias.isNotBlank(ditPersonafDom.getDgDomicilioGeografico().getDomicilioId())) {
	                ditPersonafDom.setFecRegistroAlta(fechaAlta);
	                ditPersonafDom.setFecRegistroActualizado(fechaAlta);
	                em.persist(ditPersonafDom);
	            }
	        }
	
	        // MEDIOS DE CONTACTO
	        for (DitPersonafContacto ditPersonafContacto : ditPersona.getDitPersonafContactos()) {
	            if (ditPersonafContacto.getDitFormaContacto() != null && ditPersonafContacto.getDitFormaContacto().getDitTipoContacto() != null) {
	                ditPersonafContacto.setFecRegistroActualizado(fechaAlta);
	                ditPersonafContacto.setFecRegistroAlta(fechaAlta);
	                em.persist(ditPersonafContacto);
	            }
	        }
	
	        // DOCUMENTOS PROBATORIOS (ACTA NAC, ETC)
	        List<DitDocumentoProbatorio> trans = new ArrayList<DitDocumentoProbatorio>();
	        trans.addAll(ditPersona.getDitDocumentoProbatorios());
	        ditPersona.getDitDocumentoProbatorios().clear();
	        for (DitDocumentoProbatorio ditDocumentoProbatorio : trans) {
	            DitDoctosPersonaPK pkPersona = new DitDoctosPersonaPK();
	
	            pkPersona.setCveIdDocumentoProbatorio(ditDocumentoProbatorio.getCveIdDocumentoProbatorio());
	            pkPersona.setCveIdPersona(ditPersona.getCveIdPersona());
	
	            DitDoctosPersona ditDoctoPersona = new DitDoctosPersona();
	            ditDoctoPersona.setId(pkPersona);
	            ditDoctoPersona.setDitDocumentoProbatorio(ditDocumentoProbatorio);
	
	            this.em.persist(ditDoctoPersona);
	
	        }
	        
	        // Se setea el contenido de la tabla DIT_PERSONA_FISICA
	        DitPersonaFisica ditPersonaFisica = new DitPersonaFisica();
	        ditPersonaFisica.setDitPersona(ditPersona);
	        ditPersonaFisica.setRfc(ditPersona.getRfc());
	        ditPersonaFisica.setFecRegistroActualizado(fechaAlta);
	        ditPersonaFisica.setFecRegistroAlta(fechaAlta);
	        em.persist(ditPersonaFisica);

	        personaFisica = new Fisica();
	        personaFisica.setCveFisica(ditPersonaFisica.getCveIdPersonaFisica());
	        // Finalmente se sincroniza el contexto de persistencia con la BD
	        em.flush();
	        
	    }
	    return personaFisica;
    }
    
    /**
     * 191807 161012
     * Metodo encargado de actualizar una persona fisica en BD (checar PersonaEntity.actualizarPersonaFisica(final Fisica fisica))
     * @param fisica
     * @return
     */
    public void actualizar(DitPersona ditPersona) {
    	
        log.debug("PersonaEntity.actualizarPersonaFisica. Iniciando actualizacion de persona fisica en BDU... ");
        log.debug("El objeto a guardar en la BDU es: *** " + ditPersona.getCveIdPersona() + " *** ");

//        Fisica fisicaResultado = null;

//        if(fisica != null){
        	
        	// Si no se hace este find, entonces hibernate entiende que se tiene que hacer un INSERT y no un UPDATE
//        	DitPersona ditPersona = em.find(DitPersona.class, fisica.getCveFisica());
        			   ditPersona = em.find(DitPersona.class, ditPersona.getCveIdPersona());
        	
//        	PersonaConversor.actualizarPersonaFisicaToEntity(ditPersona, fisica);
       	
//        	ditPersona.setNomNombre(fisica.getNombre());
//        	ditPersona.setNomPrimerApellido(fisica.getPrimerApellido());
//        	ditPersona.setNomSegundoApellido(fisica.getSegundoApellido());

        	if(ditPersona != null){
            	
                // Se validan los campor provenientes de catalogos
        		validarEntidadesDependientes(ditPersona);

                // Los campos alfanumericos se convierten a mayusculas
                PersonaConversor.convertirMayusculas(ditPersona);
                
                Date fechaAlta = new Date();
                ditPersona.setFecRegistroAlta(fechaAlta);
                
                em.persist(ditPersona);

                // Calificaciones (subestados validados)
                if(ditPersona.getDitHistPersonaCalificacions() != null){
                    for(DitHistPersonaCalificacion ditHistPersonaCalificacion : ditPersona.getDitHistPersonaCalificacions()){
                        ditHistPersonaCalificacion.setFecRegistroActualizado(fechaAlta);
                        ditHistPersonaCalificacion.setFecRegistroAlta(fechaAlta);
                        DitHistPersonaCalificacionPK idCalif = new DitHistPersonaCalificacionPK();
                        idCalif.setCveIdCalificacion(ditHistPersonaCalificacion.getDicPersonaCalificacion().getCveIdCalificacion());
                        idCalif.setCveIdPersona(ditHistPersonaCalificacion.getDitPersona().getCveIdPersona());
                        ditHistPersonaCalificacion.setId(idCalif);
                        
                        em.persist(ditHistPersonaCalificacion);
                    }
                }

                // Estados de la persona (HIST)
                if(ditPersona.getDitHistEstadoPersonas() != null){
                    for(DitHistEstadoPersona ditHistEstadoPersona : ditPersona.getDitHistEstadoPersonas()) {
                        ditHistEstadoPersona.setFecRegistroActualizado(fechaAlta);
                        ditHistEstadoPersona.setFecRegistroAlta(fechaAlta);
                        DitHistEstadoPersonaPK idEstado = new DitHistEstadoPersonaPK();
                        idEstado.setCveEstadoPersona(ditHistEstadoPersona.getDicEstadoPersona().getCveEstadoPersona());
                        idEstado.setCveIdPersona(ditHistEstadoPersona.getDitPersona().getCveIdPersona());
                        ditHistEstadoPersona.setId(idEstado);
                        
                        em.persist(ditHistEstadoPersona);
                    }
                }

                // Domicilios (RELACION)
                for(DitPersonafDom ditPersonafDom : ditPersona.getDitPersonafDoms()){
                    if(ditPersonafDom.getDgDomicilioGeografico() != null && Utilerias.isNotBlank(ditPersonafDom.getDgDomicilioGeografico().getDomicilioId())){
                        ditPersonafDom.setFecRegistroAlta(fechaAlta);
                        ditPersonafDom.setFecRegistroActualizado(fechaAlta);
                        
                        em.persist(ditPersonafDom);
                    }
                }

                // Medios de contacto
                for(DitPersonafContacto ditPersonafContacto : ditPersona.getDitPersonafContactos()){
                    if(ditPersonafContacto.getDitFormaContacto() != null && ditPersonafContacto.getDitFormaContacto().getDitTipoContacto() != null){
                        ditPersonafContacto.setFecRegistroActualizado(fechaAlta);
                        ditPersonafContacto.setFecRegistroAlta(fechaAlta);
                        
                        em.persist(ditPersonafContacto);
                    }
                }

                // documentos probatorios
                List<DitDocumentoProbatorio> documentosProbatorios = new ArrayList<DitDocumentoProbatorio>();
                documentosProbatorios.addAll(ditPersona.getDitDocumentoProbatorios());
                ditPersona.getDitDocumentoProbatorios().clear();
                for(DitDocumentoProbatorio ditDocumentoProbatorio : documentosProbatorios){
                    DitDoctosPersonaPK pkPersona = new DitDoctosPersonaPK();

                    pkPersona.setCveIdDocumentoProbatorio(ditDocumentoProbatorio.getCveIdDocumentoProbatorio());
                    pkPersona.setCveIdPersona(ditPersona.getCveIdPersona());

                    DitDoctosPersona ditDoctoPersona = new DitDoctosPersona();
                    ditDoctoPersona.setId(pkPersona);
                    ditDoctoPersona.setDitDocumentoProbatorio(ditDocumentoProbatorio);

                    this.em.persist(ditDoctoPersona);

                }

//                fisicaResultado = personaFisicaServiceUtility.transformarAModelo(ditPersona);

                log.debug("PersonaEntity.actualizarPersonaFisica. Finalizando actualizacion de persona fisica en BDU... ");
                log.debug("La persona fisica fue actualizada con exito ");
                
            }
//        }
        
    }
    
	/**
	 * Este metodo verifica que para el objeto fisica existan aquellas entidades de tipo catalogo para que posteriormente se pueda registrar a la persona
	 * @param ditPersona
	 * @throws PersistenceException
	 */
    public void validarEntidadesDependientes(DitPersona ditPersona) throws PersistenceException{
        // VALIDACION DE SEXO ---->
        if (ditPersona != null && ditPersona.getDicSexo() != null && Utilerias.isNotBlank(ditPersona.getDicSexo().getCveIdSexo())) {
            final DicSexo dicSexoEnt = em.find(DicSexo.class, ditPersona.getDicSexo().getCveIdSexo());
            if (dicSexoEnt == null) {
                throw new PersistenceException("No existe el sexo de id " + ditPersona.getDicSexo().getCveIdSexo() + " en el catalogo en la base de datos. No fue posible guardar a la persona.");
            } else {
                ditPersona.setDicSexo(dicSexoEnt);
            }
        }
        // VALIDACION DE EstadoCivil ---->
        if (ditPersona != null && ditPersona.getDicEstadoCivil() != null && Utilerias.isNotBlank(ditPersona.getDicEstadoCivil().getCveIdEstadoCivil())) {
            final DicEstadoCivil dicEdoCivilEnt = em.find(DicEstadoCivil.class, ditPersona.getDicEstadoCivil().getCveIdEstadoCivil());
            if (dicEdoCivilEnt == null) {
                throw new PersistenceException("No existe el estado civil de id " + ditPersona.getDicEstadoCivil().getCveIdEstadoCivil() + " en el catalogo en la base de datos. No fue posible guardar a la persona.");
            } else {
                ditPersona.setDicEstadoCivil(dicEdoCivilEnt);
            }
        }

        // VALIDACION DE PAIS ---->
        if (ditPersona != null && ditPersona.getDicPai() != null && Utilerias.isNotBlank(ditPersona.getDicPai().getCveIdPais())) {
            final DicPai dicPaisEnt = em.find(DicPai.class, ditPersona.getDicPai().getCveIdPais());
            if (dicPaisEnt == null) {
                throw new PersistenceException("No existe el pais de id " + ditPersona.getDicPai().getCveIdPais() + " en el catalogo en la base de datos. No fue posible guardar a la persona.");
            } else {
                ditPersona.setDicPai(dicPaisEnt);
            }
        }

        // VALIDACION DE ENTIDAD FEDERATIVA (DgCatEstado)
        if (ditPersona.getDgCatEstado() != null) {
            final String cveEntFedNac = ditPersona.getDgCatEstado().getCveEnt();
            if (cveEntFedNac != null) { // && cveEntFedNac != -1) {
                String desEntFedNac = null; // NOPMD
                final DgCatEstado entFedNac = em.find(DgCatEstado.class, cveEntFedNac);
                ditPersona.setDgCatEstado(entFedNac);
                if (entFedNac == null) {   
                    throw new PersistenceException("No existe la entidad federativa de id " + cveEntFedNac + " en el catÃ¡logo en la base de datos. No fue posible guardar a la persona.");
                } else {
                    desEntFedNac = entFedNac.getNomEnt();
                }
                log.trace("La entidad de nacimiento es: " + desEntFedNac);
            }
        }

	}

	@Override
	public Long obtenerIDPersonaFisica(Long idPersona)
			throws PersonaFisicaNoEncontradaException {

		this.log.debug("Se busca el id de la persona fisica");

		Long idPersonaFisica = null;
		DitPersonaFisica ditPersonaFisica = null;

		StringBuilder jpaQuery = new StringBuilder();
		jpaQuery.append("from DitPersonaFisica fisica ");
		jpaQuery.append("where fisica.ditPersona.cveIdPersona = :idPersona");

		Query query = this.em.createQuery(jpaQuery.toString());
		query.setParameter("idPersona", idPersona);

		try {
			ditPersonaFisica = (DitPersonaFisica) query.getSingleResult();
		} catch (NoResultException e) {
			this.log.info("La persona con id [" + idPersona
					+ "] no tiene asociada una persona fisica");
			throw new PersonaFisicaNoEncontradaException(idPersona);
		} catch (NonUniqueResultException e) {
			this.log.error(e);
			throw new PersonaFisicaNoEncontradaException(idPersona,
					" Que existen mas de una persona fisica asociadas al individuo");
		}

		if (ditPersonaFisica != null) {
			idPersonaFisica = ditPersonaFisica.getCveIdPersonaFisica();
			this.log.debug("El idPersonaFisica de la persona [" + idPersona
					+ "] es: " + idPersonaFisica);
		}

		return idPersonaFisica;
	}

    @Override
    public Long obtenerIDPersonaFisicaEscVirtual(Long idPersona)
            throws PersonaFisicaNoEncontradaException {

        this.log.debug("Se busca el id de la persona fisica");

        Long idPersonaFisica = null;

        StringBuffer getTrueFalseFiel = new StringBuffer();
        getTrueFalseFiel.append("WITH ANALISIS_PROPIEDADES AS" +
                " (" +
                " SELECT IDT.CVE_ID_PERSONA_FISICA " +
                "   FROM (" +
                "         SELECT CVE_ID_PERSONA_FISICA, CVE_ID_PERSONA, RFC," +
                "                CASE WHEN ( SELECT COUNT(0) REGS FROM DIT_LLAVE_PATRON PTR    WHERE PTR.CVE_ID_PERSONA = PER.CVE_ID_PERSONA ) > 0 THEN 1 ELSE 0 END PATRON," +
                "                CASE WHEN ( SELECT MAX( CVE_ID_PERSONA_FISICA ) CAL FROM DIT_PERSONA_FISICA PF WHERE PF.CVE_ID_PERSONA_FISICA = PER.CVE_ID_PERSONA_FISICA ) > 0 THEN 1 ELSE 0 END FISICA," +
                "                CASE WHEN ( SELECT COUNT(0) REGS FROM DIT_TRAMITE_PERSONA_FISICA PF INNER JOIN DIT_TRAMITE TRM ON PF.CVE_ID_TRAMITE = TRM.CVE_ID_TRAMITE WHERE PF.CVE_ID_PERSONA = PER.CVE_ID_PERSONA AND TRM.CVE_ID_TIPO_TRAMITE = 76 ) > 0 THEN 1 ELSE 0 END ESCRITORIO_VIRTUAL" +
                "           FROM DIT_PERSONA_FISICA PER" +
                "          WHERE PER.CVE_ID_PERSONA IN (:idPersona)" +
                "       ) IDT" +
                " ORDER BY " +
                "      CASE WHEN ESCRITORIO_VIRTUAL = 1 THEN 1" +
                "       ELSE" +
                "          CASE WHEN PATRON = 1 THEN 2" +
                "          ELSE" +
                "            99" +
                "          END" +
                "       END, CVE_ID_PERSONA_FISICA DESC" +
                " )" +
                " SELECT AP.*" +
                "  FROM ANALISIS_PROPIEDADES AP" +
                " WHERE ROWNUM = 1");

        this.log.info(" -- Class PersonaEntity: "+idPersona);
        this.log.info(" -- Implements...return");
        try{
            javax.persistence.Query query = em.createNativeQuery(getTrueFalseFiel.toString());
            query.setParameter("idPersona", idPersona);

			List<Object> cveIdPersonas =  query.getResultList();

			for(Object cveIdPersonaObject:cveIdPersonas) {
				BigDecimal objtc= (BigDecimal) cveIdPersonaObject;
				Long cveIdPersona = objtc.longValue();

				if (cveIdPersona != null && cveIdPersona.intValue() > 0) {
					idPersonaFisica = cveIdPersona;
				}

			}

        }catch(Exception nre){
            System.err.println("No existen datos de fiel para la persona con id: "+idPersona);
            nre.printStackTrace();
        }


        return idPersonaFisica;
    }

	@Override
	public Fisica guardarPersonaFisica(Fisica fisica){
	
		this.log.debug("Guardando la persona fisica de la persona con id [" + fisica.getIdPersona() + "]");
		
		DitPersona ditPersona = new DitPersona();
		ditPersona.setCveIdPersona(fisica.getIdPersona());
		
		DitPersonaFisica ditPersonaFisica = new DitPersonaFisica();
		ditPersonaFisica.setDitPersona(ditPersona);
		ditPersonaFisica.setRfc(fisica.getRfc());
		
		ditPersonaFisica.setFecRegistroAlta(new Date());
		
		this.em.persist(ditPersonaFisica);
		
		fisica.setCveFisica(ditPersonaFisica.getCveIdPersonaFisica());
		
		return fisica;
		
	}
	
	@Override
	public void afectarDatosPersonaFisica(AfectarDatosPersonaWrapper datosPersona)
			throws PersonaFisicaNoEncontradaException {

		Fisica fisica = datosPersona.getFisica();
		DitPersonaFisica ditPersonaFisica = this.em.find(
				DitPersonaFisica.class, fisica.getCveFisica());

		if (ditPersonaFisica == null) {
			throw new PersonaFisicaNoEncontradaException(fisica.getCveFisica());
		} else {
			
			this.log.debug("Se van a afectar los datos SAT de la persona fisica con clave [" +  fisica.getCveFisica() + "]");
			
			if (datosPersona.getModificarRFC()) {
				Date fechaActual = new Date();
				ditPersonaFisica.setRfc(fisica.getRfc());
				ditPersonaFisica.setFecRegistroActualizado(fechaActual);
				
				DitPersona ditPersona = this.em.find(DitPersona.class, fisica.getIdPersona());
				ditPersona.setRfc(fisica.getRfc());
				ditPersona.setFecRegistroActualizado(fechaActual);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Fisica> localizarPersonaFisicaPorDatosBasicosEnImss(Fisica fisica) {
		
		List<DitPersonaView> personasEntity = null;
		List<Fisica> personas = null;
		
		StringBuffer jpaQueryBase = new StringBuffer();
		jpaQueryBase.append("from DitPersonaView p ");
		jpaQueryBase.append("where p.nomNombre = :nombre ");
		jpaQueryBase.append("and p.nomPrimerApellido = :primerApellido ");
		//Apellido no venga NULL
		if (StringUtils.isNotBlank(fisica.getSegundoApellido()) && fisica.getSegundoApellido()!=null) {
			jpaQueryBase.append("and p.nomSegundoApellido = :segundoApellido ");
		}else if(fisica.getSegundoApellido()==null){
			jpaQueryBase.append("and p.nomSegundoApellido is null ");
		}
		jpaQueryBase.append("and p.entidadNacimiento.cveEnt = :entidadFederativa ");
		jpaQueryBase.append("and p.dicSexo.cveIdSexo = :sexo ");
		
		// Primero se busca con la fecha de nacimiento exacta
		StringBuffer jpaQueryFechaExacta = new StringBuffer(jpaQueryBase.toString());
		jpaQueryFechaExacta.append("and p.fecNacimiento = :fechaNacimiento ");
		
		Query query = this.em.createQuery(jpaQueryFechaExacta.toString());
		query.setParameter("nombre", fisica.getNombre());
		query.setParameter("primerApellido", fisica.getPrimerApellido());
		if (StringUtils.isNotBlank(fisica.getSegundoApellido())) {
			query.setParameter("segundoApellido", fisica.getSegundoApellido());
		}
		query.setParameter("entidadFederativa", fisica.getLugarNacimiento().getClave());
		query.setParameter("sexo", fisica.getSexo().getIdSexo().intValue());
		query.setParameter("fechaNacimiento", fisica.getFechaNacimiento());
		personasEntity = query.getResultList();
			this.log.debug("Se encontaron " + personasEntity.size()
					+ " personas con la fecha de nacimiento exacta");
			personas = personaFisicaServiceUtility.covertirListaEntityViewToPersonaFisica(personasEntity);
			
				
		return personas;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Fisica> localizarPersonaFisicaPorDatosBasicosConFechaYSinFechaNacimiento(
			Fisica fisica) {
		List<DitPersonaView> personasEntity = null;
		List<Fisica> personas = null;
		
		StringBuffer jpaQueryBase = new StringBuffer();
		jpaQueryBase.append(" from DitPersonaView p");
		jpaQueryBase.append(" where p.nomNombre = :nombre ");
		jpaQueryBase.append("and p.nomPrimerApellido = :primerApellido ");
		if (StringUtils.isNotBlank(fisica.getSegundoApellido())) {
			jpaQueryBase.append("and p.nomSegundoApellido = :segundoApellido ");
		}else{
			jpaQueryBase.append("and p.nomSegundoApellido is null ");
		}
		jpaQueryBase.append("and p.entidadNacimiento.cveEnt = :entidadFederativa ");
		jpaQueryBase.append("and p.dicSexo.cveIdSexo = :sexo ");
		
		// Primero se busca con la fecha de nacimiento exacta
		StringBuffer jpaQueryFechaExacta = new StringBuffer(jpaQueryBase.toString());
		jpaQueryFechaExacta.append("and (p.fecNacimiento = :fechaNacimiento or " );
		jpaQueryFechaExacta.append("(p.fecNacimiento is null and p.numAnioNacReg = :anioNac and p.numMesNacReg = :mesNac ) ) " );
		
		Query query = this.em.createQuery(jpaQueryFechaExacta.toString());
		query.setParameter("nombre", fisica.getNombre());
		query.setParameter("primerApellido", fisica.getPrimerApellido());
		if (StringUtils.isNotBlank(fisica.getSegundoApellido())) {
			query.setParameter("segundoApellido", fisica.getSegundoApellido());
		}
		query.setParameter("entidadFederativa", fisica.getLugarNacimiento().getClave());
		query.setParameter("sexo", fisica.getSexo().getIdSexo().intValue());
		query.setParameter("fechaNacimiento", fisica.getFechaNacimiento());
		
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fisica.getFechaNacimiento());
		
		
		
		int year = Integer.parseInt(sdf.format(fisica.getFechaNacimiento()));
		int month = calendar.get(Calendar.MONTH)+1;
		
		
		this.log.debug("el anio JC y el mes son  :" + year +"|" + month);
		query.setParameter("anioNac", year);		
		query.setParameter("mesNac", month);
		
		
		long init = System.currentTimeMillis();
		personasEntity = query.getResultList();
		long end = System.currentTimeMillis();
		
			this.log.debug("Se encontaron " + personasEntity.size()
					+ " personas con la fecha de nacimiento exacta" + " ..tiempo:" +  (end-init)/100);
			personas = personaFisicaServiceUtility.covertirListaEntityViewToPersonaFisica(personasEntity);
			
			
			return personas;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Fisica> localizarPersonaFisicaPorDatosBasicosEnImssConNSS(Fisica fisica) {
		
		List<DitAsignacionNss> asignaciones = null;
		List<Fisica> personas = null;
		
		StringBuffer jpaQueryBase = new StringBuffer();
		jpaQueryBase.append("select a ");
		jpaQueryBase.append(" from DitPersonaView as p join p.ditAsignacionNss as a ");
		jpaQueryBase.append(" where p.nomNombre = :nombre ");
		jpaQueryBase.append("and p.nomPrimerApellido = :primerApellido ");
		if (StringUtils.isNotBlank(fisica.getSegundoApellido())) {
			jpaQueryBase.append("and p.nomSegundoApellido = :segundoApellido ");
		}else{
			jpaQueryBase.append("and p.nomSegundoApellido is null ");
		}
		jpaQueryBase.append("and p.entidadNacimiento.cveEnt = :entidadFederativa ");
		jpaQueryBase.append("and p.dicSexo.cveIdSexo = :sexo ");
		
		// Primero se busca con la fecha de nacimiento exacta
		StringBuffer jpaQueryFechaExacta = new StringBuffer(jpaQueryBase.toString());
		jpaQueryFechaExacta.append("and (p.fecNacimiento = :fechaNacimiento or " );
		jpaQueryFechaExacta.append("(p.fecNacimiento is null and p.numAnioNacReg = :anioNac and p.numMesNacReg = :mesNac ) ) " );
		jpaQueryFechaExacta.append("and a.numNss is not null ");
		jpaQueryFechaExacta.append("and a.fecRegistroBaja is null ");
		jpaQueryFechaExacta.append("and (a.indActivo = :indActivo or a.indActivo is null)");
		
		Query query = this.em.createQuery(jpaQueryFechaExacta.toString());
		query.setParameter("nombre", fisica.getNombre());
		query.setParameter("primerApellido", fisica.getPrimerApellido());
		if (StringUtils.isNotBlank(fisica.getSegundoApellido())) {
			query.setParameter("segundoApellido", fisica.getSegundoApellido());
		}
		query.setParameter("entidadFederativa", fisica.getLugarNacimiento().getClave());
		query.setParameter("sexo", fisica.getSexo().getIdSexo().intValue());
		query.setParameter("fechaNacimiento", fisica.getFechaNacimiento());
		query.setParameter("indActivo", BigDecimal.ONE);
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fisica.getFechaNacimiento());
		
		
		
		int year = Integer.parseInt(sdf.format(fisica.getFechaNacimiento()));
		int month = calendar.get(Calendar.MONTH)+1;
		
		
		this.log.debug("el anio JC y el mes son  :" + year +"|" + month);
		query.setParameter("anioNac", year);		
		query.setParameter("mesNac", month);
		
		
		long init = System.currentTimeMillis();
		asignaciones = query.getResultList();
		long end = System.currentTimeMillis();
		
			this.log.debug("Se encontaron " + asignaciones.size()
					+ " personas con la fecha de nacimiento exacta" + " ..tiempo:" +  (end-init)/100);
			personas = personaFisicaServiceUtility.covertirListaAsignacionEntityToPersonaFisica(asignaciones);
			
				
		return personas;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AsignacionNSS> localizarNssPorDatosBasicos(Fisica fisica) {
		
		List<DitAsignacionNss> asignaciones = null;
		List<AsignacionNSS> personas = null;
		
		StringBuffer jpaQueryBase = new StringBuffer();
		jpaQueryBase.append("select a ");
		jpaQueryBase.append(" from DitPersonaView as p join p.ditAsignacionNss as a ");
		jpaQueryBase.append(" where p.nomNombre = :nombre ");
		jpaQueryBase.append("and p.nomPrimerApellido = :primerApellido ");
		jpaQueryBase.append("and a.fecRegistroBaja is null ");
		jpaQueryBase.append("and (a.indActivo = :indActivo or a.indActivo is null)");
		if (StringUtils.isNotBlank(fisica.getSegundoApellido())) {
			jpaQueryBase.append("and p.nomSegundoApellido = :segundoApellido ");
		}
		jpaQueryBase.append("and p.entidadNacimiento.cveEnt = :entidadFederativa ");
		jpaQueryBase.append("and p.dicSexo.cveIdSexo = :sexo ");
		
		// Primero se busca con la fecha de nacimiento exacta
		StringBuffer jpaQueryFechaExacta = new StringBuffer(jpaQueryBase.toString());
		jpaQueryFechaExacta.append("and (p.fecNacimiento = :fechaNacimiento or " );
		jpaQueryFechaExacta.append("(p.fecNacimiento is null and p.numAnioNacReg = :anioNac and p.numMesNacReg = :mesNac ) ) " );
		jpaQueryFechaExacta.append("and a.numNss is not null ");
		
		Query query = this.em.createQuery(jpaQueryFechaExacta.toString());
		query.setParameter("indActivo", BigDecimal.ONE);
		
		query.setParameter("nombre", fisica.getNombre());
		query.setParameter("primerApellido", fisica.getPrimerApellido());
		if (StringUtils.isNotBlank(fisica.getSegundoApellido())) {
			query.setParameter("segundoApellido", fisica.getSegundoApellido());
		}
		query.setParameter("entidadFederativa", fisica.getLugarNacimiento().getClave());
		query.setParameter("sexo", fisica.getSexo().getIdSexo().intValue());
		query.setParameter("fechaNacimiento", fisica.getFechaNacimiento());
		
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fisica.getFechaNacimiento());
		
		
		
		int year = Integer.parseInt(sdf.format(fisica.getFechaNacimiento()));
		int month = calendar.get(Calendar.MONTH)+1;
		
		
		this.log.debug("el anio JC y el mes son  :" + year +"|" + month);
		query.setParameter("anioNac", year);		
		query.setParameter("mesNac", month);
		
		
		long init = System.currentTimeMillis();
		asignaciones = query.getResultList();
		long end = System.currentTimeMillis();
		
		if(!asignaciones.isEmpty()) {
			personas = new ArrayList<AsignacionNSS>();
			
			for(DitAsignacionNss ditNss: asignaciones) {
				AsignacionNSS nss = this.personaFisicaServiceUtility.transformarNssToModel(ditNss);
				personas.add(nss);
			}
		}
				
		return personas;
	}
	@Override
	public Fisica localizarPersonaPorNss(String nss)
			throws PersonasNoLocalizadasException,
			NssRelacionadoVariasPersonasException {
		
		Fisica fisicaLocalizada = null;
		
		StringBuffer jpaQuery = new StringBuffer();
		jpaQuery.append("select a from DitAsignacionNss a ");
		jpaQuery.append("where a.numNss=:numNss ");
		jpaQuery.append("and a.fecRegistroBaja is null ");
		jpaQuery.append("and (a.indActivo = :indActivo or a.indActivo = '2' or a.indActivo is null)");

		Query query = this.em.createQuery(jpaQuery.toString());
		query.setParameter("numNss", nss);
		query.setParameter("indActivo", BigDecimal.ONE);
		
		try {
			DitAsignacionNss nssObj = (DitAsignacionNss) query.getSingleResult();
	
			fisicaLocalizada = personaFisicaServiceUtility
					.transformarAModelo(nssObj.getDitPersona());
			fisicaLocalizada.setNss(nssObj.getNumNss());
			fisicaLocalizada.setCveIdAsignacionNSS(nssObj.getCveIdAsignacionNss());
			
			
		} catch (NoResultException e){ 
			this.log.warn(e);
			throw new PersonasNoLocalizadasException(
					"No se localizó ninguna persona con el NSS " + nss);
		} catch(NonUniqueResultException e) {
			this.log.warn(e);
			throw new NssRelacionadoVariasPersonasException(nss);
		}
			
		return fisicaLocalizada;
	}
	
		@Override
	public Fisica localizarPersonaPorNssCertificacion(String nss)
			throws PersonasNoLocalizadasException,
			NssRelacionadoVariasPersonasException {
		
		Fisica fisicaLocalizada = null;
		
		StringBuffer jpaQuery = new StringBuffer();
		jpaQuery.append("select a from DitAsignacionNss a ");
		jpaQuery.append("where a.numNss=:numNss ");
		jpaQuery.append("and a.fecRegistroBaja is null ");
		

		Query query = this.em.createQuery(jpaQuery.toString());
		query.setParameter("numNss", nss);
		
		
		try {
			DitAsignacionNss nssObj = (DitAsignacionNss) query.getSingleResult();
	
			fisicaLocalizada = personaFisicaServiceUtility
					.transformarAModelo(nssObj.getDitPersona());
			fisicaLocalizada.setNss(nssObj.getNumNss());
			fisicaLocalizada.setCveIdAsignacionNSS(nssObj.getCveIdAsignacionNss());
			
			
		} catch (NoResultException e){ 
			this.log.warn(e);
			throw new PersonasNoLocalizadasException(
					"No se localiz? ninguna persona con el NSS " + nss);
		} catch(NonUniqueResultException e) {
			this.log.warn(e);
			throw new NssRelacionadoVariasPersonasException(nss);
		}
			
		return fisicaLocalizada;
	}
	
	@Override
	public Fisica getPersonaFisica(Long idPersonaFisica){
		if(idPersonaFisica!=null){
			Fisica fisica=null;
			DitPersonaFisica ditPersonaFisica = this.em
				.find(DitPersonaFisica.class, idPersonaFisica);
			if (ditPersonaFisica != null) {
				fisica = personaFisicaServiceUtility.transformarAModelSoloDatosPersonales(
					ditPersonaFisica.getDitPersona());
				if(fisica!=null)
					fisica.setCveFisica(idPersonaFisica);
				
			}
			return fisica;
		}
		return null;
	}

	
	@Override
	public DitPersona getDitPersona(Long idPersona){
		DitPersona ditPersona = em.find(DitPersona.class, idPersona);
		return ditPersona;
	}
	
	@Override
	public DitPersonaFisica getDitPersonaFisica(Long idPersona)throws PersonaFisicaNoEncontradaException{
		DitPersonaFisica ditPersonaFisica = null;
		StringBuilder jpaQuery = new StringBuilder();
		jpaQuery.append("from DitPersonaFisica fisica ");
		jpaQuery.append("where fisica.ditPersona.cveIdPersona = :idPersona");

		Query query = this.em.createQuery(jpaQuery.toString());
		query.setParameter("idPersona", idPersona);

		try {
			ditPersonaFisica = (DitPersonaFisica) query.getSingleResult();
			return ditPersonaFisica;
		} catch (NoResultException e) {
			return null;
		} catch (NonUniqueResultException e) {
			this.log.error(e);
			throw new PersonaFisicaNoEncontradaException(idPersona,
					" Existe mas de una persona fisica asociadas al individuo");
		}
	}
	
	@Override
	public void actualizarDitPersona(DitPersona ditPersona){
		if(ditPersona!=null && ditPersona.getCveIdPersona()!=null){
			em.merge(ditPersona);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AsignacionNSS> localizarNssCl3PorDatosBasicosSinFechaNac(Fisica fisica) {
		
		List<DitAsignacionNssCL3> asignaciones = null;
		List<AsignacionNSS> personas = null;
		
		StringBuffer jpaQueryBase = new StringBuffer();
		jpaQueryBase.append("select a ");
		jpaQueryBase.append(" from DitAsignacionNssCL3 as a join a.ditPersonaView as p ");
		jpaQueryBase.append(" where p.nomNombre = :nombre ");
		jpaQueryBase.append("and p.nomPrimerApellido = :primerApellido ");
		jpaQueryBase.append("and a.fecRegistroBaja is null ");
		jpaQueryBase.append("and (a.indActivo = :indActivo or a.indActivo is null)");
		if (StringUtils.isNotBlank(fisica.getSegundoApellido())) {
			jpaQueryBase.append("and p.nomSegundoApellido = :segundoApellido ");
		}
		jpaQueryBase.append("and p.entidadNacimiento.cveEnt = :entidadFederativa ");
		jpaQueryBase.append("and p.dicSexo.cveIdSexo = :sexo ");
		
		jpaQueryBase.append("and a.numNss is not null ");
		
		Query query = this.em.createQuery(jpaQueryBase.toString());
		query.setParameter("indActivo", BigDecimal.ONE);
		query.setParameter("nombre", fisica.getNombre());
		query.setParameter("primerApellido", fisica.getPrimerApellido());
		if (StringUtils.isNotBlank(fisica.getSegundoApellido())) {
			query.setParameter("segundoApellido", fisica.getSegundoApellido());
		}
		query.setParameter("entidadFederativa", fisica.getLugarNacimiento().getClave());
		query.setParameter("sexo", fisica.getSexo().getIdSexo().intValue());
			
		//persisCL3ToModel
		asignaciones = query.getResultList();
		
		if(!asignaciones.isEmpty()) {
			personas = new ArrayList<AsignacionNSS>();
			
			for(DitAsignacionNssCL3 ditNss: asignaciones) {
				AsignacionNSS nss = this.personaFisicaServiceUtility.transformarNssCL3ToModel(ditNss);
				personas.add(nss);
			}
		}
				
		return personas;
	}

	// Metodo para registrar una nueva persona derivado de la separacion de personas en CDA
	@Override
	public Long registrarNuevaPersonaCDA(DitPersona ditPersona, Long idPersonaAnterior, Long idAsignacionNSS) throws Exception {

		log.info("Entrando a generar una nueva persona para separacion de personas CDA ID_PERSONA_ANTERIOR: " + idPersonaAnterior
				+ " ID_ASIGNACION_NSS: " + idAsignacionNSS);

		if (ditPersona != null) {
			validarEntidadesDependientes(ditPersona);
			em.persist(ditPersona);
			em.flush();

			Long idPersonaNuevo = ditPersona.getCveIdPersona();

			log.info("Se genero la nueva persona NUEVO_ID_PERSONA: " + idPersonaNuevo
					+ " continua actualizacion en DIT_LLAVE_ASEGURADO y DIT_ASIGNACION_NSS para asignar el nuevo id");

			String updateAsigancionNss =
					"UPDATE DIT_ASIGNACION_NSS SET CVE_ID_PERSONA = " + idPersonaNuevo + " WHERE CVE_ID_PERSONA = " + idPersonaAnterior
							+ " AND CVE_ID_ASIGNACION_NSS = " + idAsignacionNSS;

			this.getSession().createSQLQuery(updateAsigancionNss).executeUpdate();

			String updateLlaveAsegurado =
					"UPDATE DIT_LLAVE_ASEGURADO SET CVE_ID_PERSONA = " + idPersonaNuevo + " WHERE CVE_ID_PERSONA = " + idPersonaAnterior
							+ " AND CVE_ID_ASIGNACION_NSS = " + idAsignacionNSS;

			this.getSession().createSQLQuery(updateLlaveAsegurado).executeUpdate();

			return idPersonaNuevo;
		} else {
			throw new Exception("Los datos de la persona estan vacios");
		}
	}
}
