package mx.gob.imss.ctirss.gestionpersonas.servicios.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.exception.individuo.PersonaMoralNoEncontradaException;
import mx.gob.imss.ctirss.delta.exception.individuo.calificacion.PersonaSinCalificacionesException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.util.Utilerias;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.CalificacionesPersonaBusinessServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.utility.bean.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.gestion.individuo.utility.bean.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.AfectarDatosPersonaWrapper;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoServicioModificacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSociedad;
import mx.gob.imss.ctirss.delta.persistence.DicTipoSociedad;
import mx.gob.imss.ctirss.delta.persistence.DitActaConstitutiva;
import mx.gob.imss.ctirss.delta.persistence.DitHistEstadoPersonaMoral;
import mx.gob.imss.ctirss.delta.persistence.DitHistEstadoPersonaMoralPK;
import mx.gob.imss.ctirss.delta.persistence.DitHistPersonaMoralCalific;
import mx.gob.imss.ctirss.delta.persistence.DitHistPersonaMoralCalificPK;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaMoral;
import mx.gob.imss.ctirss.delta.persistence.DitPersonamContacto;
import mx.gob.imss.ctirss.delta.persistence.DitPersonamDom;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

@Stateless
public class PersonaMoralEntity extends AbstractServiceEntity implements PersonaMoralEntityLocal {

	@EJB
	private CalificacionesPersonaBusinessServiceRemote calificacionesPersonaBusinessService;
	
	@Override
    public List<Domicilio> buscarDomiciliosPersonaMoral(Long idPersona) {
        DitPersonaMoral ditPersona = em.find(DitPersonaMoral.class, idPersona);
        List<Domicilio> domicilios = new ArrayList<Domicilio>();

        for (DitPersonamDom ditPersonaDom : ditPersona.getDitPersonamDoms()) {
			Domicilio domicilio = new Domicilio();
			domicilio.setClave(new Long(ditPersonaDom.getDgDomicilioGeografico().getDomicilioId()).intValue());
			domicilios.add(domicilio);
        }
        return domicilios;
    }	
	
    @Override
    public DitPersonaMoral altaPersonaMoral(final DitPersonaMoral ditPersonaMoral) {
        if (ditPersonaMoral != null) {
            //EJECUTA VALIDACIONES DE CATALOGOS
            validarTipoSociedad(ditPersonaMoral);
            convertirMayusculas(ditPersonaMoral);
            persistDitActa(ditPersonaMoral);

            //ASIGNA LA FECHA DE ALTA A LA PERSONA MORAL Y SUS OBJETOS ASOCIADOS
            final Date fechaAlta = new Date();
            ditPersonaMoral.setFecRegistroAlta(fechaAlta);

            em.persist(ditPersonaMoral);

            // CALIFICACION  (SUBESTADOVALIDADO)
            if (ditPersonaMoral.getDitHistPersonaMoralCalifics() != null) {
                for (DitHistPersonaMoralCalific ditHistPersonaCalificacion : ditPersonaMoral.getDitHistPersonaMoralCalifics()) {
                    ditHistPersonaCalificacion.setFecRegistroActualizado(fechaAlta);
                    ditHistPersonaCalificacion.setFecRegistroAlta(fechaAlta);
                    DitHistPersonaMoralCalificPK pkHist = new DitHistPersonaMoralCalificPK();
                    pkHist.setCveIdCalificacion(ditHistPersonaCalificacion.getDicPersonaCalificacion().getCveIdCalificacion());
                    pkHist.setCveIdPersonaMoral(ditPersonaMoral.getCveIdPersonaMoral());
                    ditHistPersonaCalificacion.setId(pkHist);
                    em.persist(ditHistPersonaCalificacion);
                }
            }

            // ESTADOS PERSONA (HIST)
            if (ditPersonaMoral.getDitHistEstadoPersonaMorals() != null) {
                for (DitHistEstadoPersonaMoral ditHistEstadoPersona : ditPersonaMoral.getDitHistEstadoPersonaMorals()) {
                    ditHistEstadoPersona.setFecRegistroActualizado(fechaAlta);
                    ditHistEstadoPersona.setFecRegistroAlta(fechaAlta);
                    DitHistEstadoPersonaMoralPK histId = new DitHistEstadoPersonaMoralPK();
                    histId.setCveEstadoPersona(ditHistEstadoPersona.getDicEstadoPersona().getCveEstadoPersona());
                    histId.setCveIdPersonaMoral(ditPersonaMoral.getCveIdPersonaMoral());
                    ditHistEstadoPersona.setId(histId);
                    em.persist(ditHistEstadoPersona);
                }
            }

            // DOMICILIOS (RELACION)
            for (DitPersonamDom ditPersonafDom : ditPersonaMoral.getDitPersonamDoms()) {
                if (ditPersonafDom.getDgDomicilioGeografico() != null && Utilerias.isNotBlank(ditPersonafDom.getDgDomicilioGeografico().getDomicilioId())) {
                    ditPersonafDom.setFecRegistroActualizado(fechaAlta);
                    ditPersonafDom.setFecRegistroAlta(fechaAlta);
                    em.persist(ditPersonafDom);
                }
            }

            // MEDIOS DE CONTACTO
            for (DitPersonamContacto ditPersonaContacto : ditPersonaMoral.getDitPersonamContactos()) {
                if (ditPersonaContacto.getDitFormaContacto() != null && ditPersonaContacto.getDitFormaContacto().getDitTipoContacto() != null) {
                    ditPersonaContacto.setFecRegistroActualizado(fechaAlta);
                    ditPersonaContacto.setFecRegistroAlta(fechaAlta);
                    em.persist(ditPersonaContacto);
                }
            }

            em.flush();
        }
        return ditPersonaMoral;
    }

    private void convertirMayusculas(final DitPersonaMoral ditPersonaMoral) {
    	if (ditPersonaMoral != null){
            ditPersonaMoral.setDitActaConstitutivas(ditPersonaMoral.getDitActaConstitutivas());
            if (ditPersonaMoral.getDenominacionRazonSocial() != null){
            	ditPersonaMoral.setDenominacionRazonSocial(ditPersonaMoral.getDenominacionRazonSocial().toUpperCase());	
            }
            if (ditPersonaMoral.getDesCamaraOrganizacion() != null){
            	ditPersonaMoral.setDesCamaraOrganizacion(ditPersonaMoral.getDesCamaraOrganizacion().toUpperCase());	
            }
//            if (ditPersonaMoral.getNombreComercial() != null){
//            	ditPersonaMoral.setNombreComercial(ditPersonaMoral.getNombreComercial().toUpperCase());	
//            }
            if (ditPersonaMoral.getRfc() != null){
            	ditPersonaMoral.setRfc(ditPersonaMoral.getRfc().toUpperCase());            	
            }
    	}
    }

    public TipoSociedad getTipoSociedad(Long idTipoSociedad){
    	TipoSociedad respuesta = null;
    	
    	if (idTipoSociedad != null){
			try {
				final DicTipoSociedad dicTipoSociedad = em.find(DicTipoSociedad.class, idTipoSociedad.intValue());
				if (dicTipoSociedad != null){
					respuesta = new TipoSociedad();
					respuesta.setIdTipoSociedad(idTipoSociedad);
					respuesta.setDescripcion(dicTipoSociedad.getDesTipoSociedad());
					respuesta.setDescripcionAbreviada(dicTipoSociedad.getDesTipoSociedadAbrev());			
				}
			} catch (Exception e) {
				e.printStackTrace();
			}    		
    	}
    	return respuesta;
    }
    
    private void validarTipoSociedad(final DitPersonaMoral ditPersonaMoral) {
        final DicTipoSociedad tipoSocFromPM = ditPersonaMoral.getDicTipoSociedad();
        if (tipoSocFromPM != null && Utilerias.isNotBlank(tipoSocFromPM.getCveIdTipoSociedad())) {
            final DicTipoSociedad dicTipoSociedad = em.find(DicTipoSociedad.class, ditPersonaMoral.getDicTipoSociedad().getCveIdTipoSociedad());
            ditPersonaMoral.setDicTipoSociedad(dicTipoSociedad);
        }
    }

    private void persistDitActa(final DitPersonaMoral ditPersonaMoral) {
        if (ditPersonaMoral.getDitActaConstitutivas() != null) {
            DitActaConstitutiva ditActa = new DitActaConstitutiva(); // NOPMD
            if (ditPersonaMoral.getDitActaConstitutivas().size() == 1) {
                ditActa = ditPersonaMoral.getDitActaConstitutivas().get(0);
            } else if (ditPersonaMoral.getDitActaConstitutivas().size() > 1) {
                ditActa = ditPersonaMoral.getDitActaConstitutivas().get(0);
            }
            if ((StringUtils.isNotEmpty(ditActa.getNumEscritura()) 
            		&& StringUtils.isNotBlank(ditActa.getNumEscritura()))
            	|| (StringUtils.isNotEmpty(ditActa.getNumNotaria()) 
            		&& StringUtils.isNotBlank(ditActa.getNumNotaria()))
        		|| (StringUtils.isNotEmpty(ditActa.getNumFolioMercantil()) 
        			&& StringUtils.isNotBlank(ditActa.getNumFolioMercantil())) ) {
            	
                ditActa.setFecRegistroAlta(new Date());
                ditActa.setDitPersonaMoral(ditPersonaMoral);
                em.persist(ditActa);
            }else{
            	ditPersonaMoral.setDitActaConstitutivas(null);
            }
            
        }
    }

    @Override
    public List<Moral> buscarPersonaMoral(final Moral personaMoral) {
    	log.debug("::: Buscando la persona Moral en BDTU, PersonaMoralEntity.buscarPersonaMoral");
        final Criteria criteria = getSession().createCriteria(DitPersonaMoral.class);
        log.error("Personas morales por criteria...." + criteria);
        addRestrictionsBusquedaPersonaMoral(criteria, personaMoral);
        @SuppressWarnings("unchecked")
        final List<DitPersonaMoral> personaLst = criteria.setMaxResults(1000).list();
        if (personaLst != null && personaLst.size() < 20) {
            log.trace("DitPersonaMoralLst: \n" + personaLst);
        }
        List<Moral> personaMoralLst = new ArrayList<Moral>();
        for (DitPersonaMoral ditPersonaMoral : personaLst) {
            personaMoralLst.add(PersonaMoralConversor.convertirEntityToPersonaMoral(ditPersonaMoral));
            log.error("Personas morales de criteria despues de pasar por el metodo convertirentitytopersonamoral...." + personaMoralLst);
        }
        return personaMoralLst;
    }

    @Override
    public List<Moral> buscarPersonaMoral_AP(final Moral personaMoral) {
    	log.debug("::: Buscando la persona Moral en BDTU, PersonaMoralEntity.buscarPersonaMoral_AP");
    	Query query = em
    			.createQuery("select pm from DitPersonaMoral pm "
    					+ "where pm.cveIdPersonaMoral = "
    					+ personaMoral.getIdPersona());
    	@SuppressWarnings("unchecked")
		List<DitPersonaMoral> personaLst =  query.getResultList();
//        if (personaLst != null && personaLst.size() < 20) {
//            log.trace("DitPersonaMoralLst: \n" + personaLst);
//        }
        List<Moral> personaMoralLst = new ArrayList<Moral>();
        for (DitPersonaMoral ditPersonaMoral : personaLst) {
            personaMoralLst.add(PersonaMoralConversor.convertirEntityToPersonaMoral(ditPersonaMoral));
            log.error("Personas morales de criteria despues de pasar por el metodo convertirentitytopersonamoral...." + personaMoralLst);
        }
        return personaMoralLst;
    }
    
    @Override
    public List<Moral> buscarPersonaMoral_RFC_AP(final Moral personaMoral) {
    	log.debug("::: Buscando la persona Moral en BDTU, PersonaMoralEntity.buscarPersonaMoral_RFC_AP");
    	Query query = em
    			.createQuery("select pm from DitPersonaMoral pm "
    					+ "where pm.rfc = '"
    					+ personaMoral.getRfc() + "'");
    	@SuppressWarnings("unchecked")
		List<DitPersonaMoral> personaLst =  query.getResultList();
//        if (personaLst != null && personaLst.size() < 20) {
//            log.trace("DitPersonaMoralLst: \n" + personaLst);
//        }
        List<Moral> personaMoralLst = new ArrayList<Moral>();
        for (DitPersonaMoral ditPersonaMoral : personaLst) {
            personaMoralLst.add(PersonaMoralConversor.convertirEntityToPersonaMoral(ditPersonaMoral));
            log.error("Personas morales de criteria despues de pasar por el metodo convertirentitytopersonamoral...." + personaMoralLst);
        }
        return personaMoralLst;
    }    
        
    public DatosSalidaPaginador<Moral> paginar(
			DatosEntradaPaginador<Moral> params
			) throws Exception {
    	log.debug("::: Buscando en PersonaMoralEntity.paginar");
    	DatosSalidaPaginador<Moral> response = new DatosSalidaPaginador<Moral>();
        List<DitPersonaMoral> result = null;
        
    	/*Objeto con los filtros seleccionados en la vista*/
    	Moral filtro = params.getModelo();
    	
    	final Criteria criteria = getSession().createCriteria(DitPersonaMoral.class);
    	criteria.add(Restrictions.isNotNull("cveIdPersonaMoral"));
    	
    	/**
		 * Total records, before filtering (i.e. the total number of records in
		 * the database)
		 */
		int iTotalRecords = 0;
		/*Se debe de obtener el numero total de registros en la base de datos*/
		criteria.setProjection(Projections.rowCount());
		
		iTotalRecords = ((Long) criteria.setProjection(Projections.rowCount())
				.list().get(0)).intValue();
		
		criteria.setProjection(null);
		
		//Aplicamos los filtros de la forma de la vista
		addRestrictionsBusquedaPersonaMoral(criteria, filtro);
		/**
		 * Total records, after filtering (i.e. the total number of records
		 * after filtering has been applied - not just the number of records
		 * being returned in this result set)
		 */
		int iTotalDisplayRecords = 0;
		iTotalDisplayRecords = ((Long)criteria.setProjection(Projections.rowCount()).list().get(0)).intValue();
		criteria.setProjection(null);
		
		criteria.addOrder(Order.asc("denominacionRazonSocial"));
		criteria.setResultTransformer(Criteria.ROOT_ENTITY);
		
		result = criteria.setFirstResult(params.getiDisplayStart()).setMaxResults(params.getiDisplayLength()).list();
    	
		List<Moral> personaMoralLst = new ArrayList<Moral>();
        for (DitPersonaMoral ditPersonaMoral : result) {
            personaMoralLst.add(PersonaMoralConversor.convertirEntityToPersonaMoral(ditPersonaMoral));
        }
    	
        response.setAaData(personaMoralLst);
        response.setiTotalDisplayRecords(iTotalDisplayRecords);
        response.setiTotalRecords(iTotalRecords);
        
    	return response;
    }
    
    private void addRestrictionsBusquedaPersonaMoral(final Criteria criteria, final Moral filtro) {

        if (filtro != null) {
            
        	if ( filtro.getIdPersona() != null) {
                criteria.add(Restrictions.eq("cveIdPersonaMoral", filtro.getIdPersona()));
            }
            
        	// El -1 es equivalente en todos los combos a null.
            if (filtro.getTipoSociedad() != null && Utilerias.isNotBlank(filtro.getTipoSociedad().getIdTipoSociedad())) {
                criteria.createAlias("dicTipoSociedad", "tipoSociedad", Criteria.LEFT_JOIN).add(Restrictions.eq("tipoSociedad.cveIdTipoSociedad", filtro.getTipoSociedad().getIdTipoSociedad().intValue()));
            }
            
            criteria.createAlias("ditActaConstitutivas", "actas",Criteria.LEFT_JOIN);
            if (StringUtils.isNotBlank(filtro.getActaConstitutiva())) {
                if (Boolean.parseBoolean(filtro.getBusqAprox())) {
                    criteria.add(Restrictions.like("actas.numActa", filtro.getActaConstitutiva(), MatchMode.START));
                } else {
                    criteria.add(Restrictions.eq("actas.numActa", filtro.getActaConstitutiva()));
                }
            }
            
            if (filtro.getFechaCreacion() != null) {
                criteria.add(Restrictions.eq("actas.fecExpedicionActa", filtro.getFechaCreacion()));
            }

            log.error("Datos que pasan por el filtro de criteria...." + filtro);
            final DitPersonaMoral ditPersonaMoral = PersonaMoralConversor.convertirPersonaMoralToEntity(filtro);
            log.error("Datos que pasaron por convertir persona moral con los datos de criteria...." + ditPersonaMoral);
            resetBlankFields(ditPersonaMoral);
            log.trace("ditPersonaMoral:  " + ditPersonaMoral);
            
            final Example example = Example.create(ditPersonaMoral);
            example.enableLike();
            
            if (Boolean.parseBoolean(filtro.getBusqAprox())) {
                example.enableLike(MatchMode.ANYWHERE);
            }
            
            
            criteria.add(example).addOrder(Order.desc("fecRegistroActualizado"));
            
            
        }
    }

    private void resetBlankFields(final DitPersonaMoral ditPersonaMoral) {
        /*
         * Debido a que el controller pasa como cadenas vacias a los parametros
         * en caso de que no se envien datos de consulta, se requiere que, si la
         * cadena viene vacia se regrese a su equivalente null:
         */
        if (StringUtils.isBlank(ditPersonaMoral.getDenominacionRazonSocial())) {
            ditPersonaMoral.setDenominacionRazonSocial(null);
        }
        if (StringUtils.isBlank(ditPersonaMoral.getRfc())) {
            ditPersonaMoral.setRfc(null);
        }
//        if (StringUtils.isBlank(ditPersonaMoral.getNombreComercial())) {
//            ditPersonaMoral.setNombreComercial(null);
//        }
    }

    @Override
    public TipoSociedad getTipoSociedadByDescripcion(final String descTipoSociedad) {
    	TipoSociedad tipoSociedad = null;
        
    	List<DicTipoSociedad> lista = null;
		
    	if (descTipoSociedad != null){
        	String sSql = "SELECT s FROM DicTipoSociedad s WHERE s.desTipoSociedadAbrev = '" + descTipoSociedad + "'";
        	Query querySerie = em.createQuery(sSql);
        	lista = (List<DicTipoSociedad>)querySerie.getResultList();
    	}
    	
    	if (lista != null){
    		for (DicTipoSociedad dicTipoSociedad : lista){
    			tipoSociedad = new TipoSociedad();
    			tipoSociedad.setIdTipoSociedad(Long.valueOf(dicTipoSociedad.getCveIdTipoSociedad()));
    			tipoSociedad.setDescripcion(dicTipoSociedad.getDesTipoSociedad());
    			tipoSociedad.setDescripcionAbreviada(dicTipoSociedad.getDesTipoSociedadAbrev());			
    			break;
    		}
        }
        return tipoSociedad;
    }

    @Override
    public Long contarNumPersonasTotal() {
        return (Long) getSession().createCriteria(DitPersonaMoral.class).setProjection(Projections.max("cveIdPersonaMoral")).list().get(0);
    }

    
    @Override
    public void actualizarPersonaMoral(Moral moral) throws PersonaNoEncontradaException{
    	
    	this.log.debug("Actualizando los datos de la persona moral....");
    	
    	DitPersonaMoral ditPersonaMoral = this.em.find(DitPersonaMoral.class, moral.getIdPersona());
    	
    	if(ditPersonaMoral != null){
    		
    		
    		/*
    		 * Razon Social
    		 */
    		if(moral.getRazonSocial() != null && !moral.getRazonSocial().isEmpty()){
    			ditPersonaMoral.setDenominacionRazonSocial(moral.getRazonSocial());
    		}
    		
    		/*
    		 * RFC
    		 */
    		if(moral.getRfc() != null && !moral.getRfc().isEmpty()){
    			ditPersonaMoral.setRfc(moral.getRfc());
    		}
    		
    		/*
    		 * Tipo de Sociedad
    		 */
    		if(moral.getTipoSociedad() != null && moral.getTipoSociedad().getIdTipoSociedad() != null){
    			DicTipoSociedad dicTipoSociedad = new DicTipoSociedad();
    			dicTipoSociedad.setCveIdTipoSociedad(moral.getTipoSociedad().getIdTipoSociedad().intValue());
    			ditPersonaMoral.setDicTipoSociedad(dicTipoSociedad);
    		}
    		
    		
    		/*
    		 * Calificaciones
    		 */
    		if(moral.getPersonaCalificaciones() != null && !moral.getPersonaCalificaciones().isEmpty()){
    			
    			
    			Iterator<PersonaCalificacion> it = moral.getPersonaCalificaciones().iterator();
    			
    			while(it.hasNext()){
    				PersonaCalificacion calificacion = it.next();
    				
    				DitHistPersonaMoralCalificPK pk = new DitHistPersonaMoralCalificPK();
    				pk.setCveIdCalificacion(calificacion.getCalificacion().getIdCalificacion());
    				pk.setCveIdPersonaMoral(moral.getIdPersona());
    				
    				DitHistPersonaMoralCalific ditCalificacion = this.em.find(DitHistPersonaMoralCalific.class, pk);
    				if(ditCalificacion != null){
    					/*
    					 * La calificacion ya existe, actualizamos solo la
    					 * fecha de modificacion.
    					 */
    					ditCalificacion.setFecRegistroActualizado(new Date());
    					
    				}else{
    					/*
    					 * La calificacion no existe, se debe de crear 
    					 */
    					ditCalificacion = new DitHistPersonaMoralCalific();
    					ditCalificacion.setId(pk);
    					ditCalificacion.setFecRegistroAlta(new Date());
    					ditCalificacion.setFecRegistroActualizado(new Date());
    					this.em.persist(ditCalificacion);
    					
    				}
    				
    			}
    			
    			
    		}
    		
    	}else{
    		this.log.error("No existe la persona moral solicitada");
    		throw new PersonaNoEncontradaException(moral.getIdPersona());
    		
    	}
    	
    	
    }
    
	@Override
	public void afectarDatosPersonaMoral(AfectarDatosPersonaWrapper datosPersona)
			throws PersonaMoralNoEncontradaException {
    
		Moral moral = datosPersona.getMoral();
		
		DitPersonaMoral ditPersonaMoral = this.em.find(DitPersonaMoral.class, moral.getCveMoral());
		
		if (ditPersonaMoral == null) {
			throw new PersonaMoralNoEncontradaException(moral.getCveMoral());
		} else {
			
			this.log.debug("Se van a afectar los datos SAT de la persona moral con clave [" + moral.getCveMoral() + "]");
			
			this.log.debug("VER SI TRAE GETMODIFICARTIPOSOCIEDAD [" + datosPersona.getModificarTipoSociedad() + "]");
			
			this.log.debug("VER SI TRAE getModificarRazonSocial [" + datosPersona.getModificarRazonSocial() + "]");
			
			
			if(datosPersona.getModificarRazonSocial()){
				ditPersonaMoral.setDenominacionRazonSocial(moral.getRazonSocial());
			}
						
			if(datosPersona.getModificarTipoSociedad()){
				
				DicTipoSociedad dicTipoSociedad = new DicTipoSociedad();
				dicTipoSociedad.setCveIdTipoSociedad(moral.getTipoSociedad().getIdTipoSociedad().intValue());
				
				ditPersonaMoral.setDicTipoSociedad(dicTipoSociedad);
								
			this.log.debug("VER SI SE CAMBIO ID TIPO SOCIEDAD [" + dicTipoSociedad.getCveIdTipoSociedad() + "]");
			
			}
			
			if(datosPersona.getModificarRFC()){
				ditPersonaMoral.setRfc(moral.getRfc());
			}
						
			ditPersonaMoral.setFecRegistroActualizado(new Date());
		}
    }
	
	@Override
	public void afectarCalificacionesPersona(
			AfectarDatosPersonaWrapper datosPersona) {
		
		Moral moral = datosPersona.getMoral();
		
		/*
		 * Se actualizan la calificaciones, si se viene del ICA, las
		 * calificaci�n SAT se agrega/actualiza y la calificaci�n
		 * IMSS se da de baja; si se viene de la Modificaci�n manual se deben
		 * dar de baja las calificaciones existentes y s�lo dar de alta la
		 * calificaci�n "validado por IMSS"
		 */
		if (datosPersona.getTipoServicio() != null) { 
			if (datosPersona.getTipoServicio().getIdServicio()
					.equals(TipoServicioModificacionEnum.ICA.getIdServicio())) {
				try {
					this.calificacionesPersonaBusinessService.calificarSAT(moral);
				} catch (PersonaSinCalificacionesException e) {
					this.log.error(e);
				}
			} else if (datosPersona.getTipoServicio().getIdServicio().equals(
					TipoServicioModificacionEnum.MDM.getIdServicio())) {
				try {
					this.calificacionesPersonaBusinessService.calificarIMSS(moral);
				} catch (PersonaSinCalificacionesException e) {
					this.log.error(e);
				}
			}
		} else {
			this.log.warn("No se afectar�n calificaciones ya que no se cuenta con el tipo de servicio (ICA o MDM)");
		}
	}

	public Integer consultaActaConstitutivaPersonaMoral(Long cveIdPersona) {
		StringBuffer query = new StringBuffer();
		Integer response = 0;
		
		try {
			query.append("SELECT ac.cve_id_act_constitutiva FROM DIT_PERSONA_MORAL pm ");
			query.append("JOIN DIT_ACTA_CONSTITUTIVA ac ON ac.cve_id_persona_moral = pm.cve_id_persona_moral ");
			query.append("WHERE pm.cve_id_persona_moral = " + cveIdPersona);

			Query emQuery = em.createNativeQuery(query.toString());
			@SuppressWarnings("unchecked")
			List<Integer> lstActasCons = emQuery.getResultList();
			
			if (lstActasCons !=null && lstActasCons.size() > 0) {
				response = 1;
			}
			return  response;

		} catch (NoResultException e) {
			// TODO: handle exception
			return response;
		}
	}
	
	public Integer consultaSindicatoPersonaMoral(Long cveIdPersona) {
		StringBuffer query = new StringBuffer();
		Integer response = 0;
				
		try {
			query.append("SELECT si.cve_id_sindicato FROM DIT_PERSONA_MORAL pm ");
			query.append("JOIN DIT_SINDICATO si ON si.cve_id_persona_moral = pm.cve_id_persona_moral ");
			query.append("WHERE pm.cve_id_persona_moral = " + cveIdPersona);

			Query emQuery = em.createNativeQuery(query.toString());
			@SuppressWarnings("unchecked")
			List<Integer> lstIdSindicato = emQuery.getResultList();
			
			if (lstIdSindicato !=null && lstIdSindicato.size() > 0) {
				response = 1;
			}
			return  response;

		} catch (NoResultException e) {
			// TODO: handle exception
			return response;
		}
	}
	
	public Integer validaIndAcreditado(Long cveIdPersona) {
		StringBuffer query = new StringBuffer();
		Integer response = 0;
				
		try {
			query.append("SELECT pm.ind_acreditado FROM DIT_PERSONA_MORAL pm ");
			query.append("WHERE pm.cve_id_persona_moral = " + cveIdPersona);

			Query emQuery = em.createNativeQuery(query.toString());
			BigDecimal acreditado = (BigDecimal) emQuery.getSingleResult();
			
			if (acreditado !=null && Utilerias.isNotBlank(acreditado)) {
				response = acreditado.intValue();
			}
			return  response;

		} catch (NoResultException e) {
			// TODO: handle exception
			return response;
		}
	}
	
	//Se agrega cambio para version de produccion
	@Override
	public void actualizaRazonSocialTipoSociedad(Long cveIdPersona, String nombreRazonSocial, TipoSociedad tipoSociedad) {		
		if(cveIdPersona != null && nombreRazonSocial != null && tipoSociedad != null && tipoSociedad.getIdTipoSociedad() != null) {
	    	this.log.debug("::: Se actualizara la persona moral: " + cveIdPersona);
			String query = "UPDATE DIT_PERSONA_MORAL SET ";
			query += "DENOMINACION_RAZON_SOCIAL = '" + nombreRazonSocial + "', ";
			query += "CVE_ID_TIPO_SOCIEDAD = " + tipoSociedad.getIdTipoSociedad() + " ";
			query += "WHERE CVE_ID_PERSONA_MORAL = " + cveIdPersona;
			SQLQuery queryActualizar = this.getSession().createSQLQuery(query);
			queryActualizar.executeUpdate();
		}else{
			this.log.error("::: Los parametros para actualizar la persona moral no son correctos");
		}
	}	
	
}