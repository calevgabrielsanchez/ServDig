package mx.gob.imss.ctirss.gestionpersonas.servicios.entity;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.exception.individuo.PersonaFisicaNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;

import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaConVariosNSSException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaSinNSSException;
import mx.gob.imss.ctirss.delta.exception.individuo.calificacion.PersonaSinCalificacionesException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.framework.util.Utilerias;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.CalificacionesPersonaBusinessServiceRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.utility.PersonaFisicaServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.AfectarDatosPersonaWrapper;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.CalificacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fiel;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaCalificacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.PersonaEstado;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoServicioModificacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.nss.Serie;
import mx.gob.imss.ctirss.delta.model.gestion.nss.TipoSerie;
import mx.gob.imss.ctirss.delta.persistence.DgCatEstado;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoCivil;
import mx.gob.imss.ctirss.delta.persistence.DicPai;
import mx.gob.imss.ctirss.delta.persistence.DicSexo;
import mx.gob.imss.ctirss.delta.persistence.DicTipoIdentificador;
import mx.gob.imss.ctirss.delta.persistence.DitAsignacionNss;
import mx.gob.imss.ctirss.delta.persistence.DitAsignacionSerie;
import mx.gob.imss.ctirss.delta.persistence.DitDatosCertificadoFiel;
import mx.gob.imss.ctirss.delta.persistence.DitDoctosPersona;
import mx.gob.imss.ctirss.delta.persistence.DitDoctosPersonaPK;
import mx.gob.imss.ctirss.delta.persistence.DitDocumentoProbatorio;
import mx.gob.imss.ctirss.delta.persistence.DitDomicilioSat;
import mx.gob.imss.ctirss.delta.persistence.DitFormaContacto;
import mx.gob.imss.ctirss.delta.persistence.DitGrupoFamiliar;
import mx.gob.imss.ctirss.delta.persistence.DitHistEstadoPersona;
import mx.gob.imss.ctirss.delta.persistence.DitHistEstadoPersonaPK;
import mx.gob.imss.ctirss.delta.persistence.DitHistPersonaCalificacion;
import mx.gob.imss.ctirss.delta.persistence.DitHistPersonaCalificacionPK;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaFDomFiscal;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaFisica;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaMDomFiscal;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaMoral;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaView;
import mx.gob.imss.ctirss.delta.persistence.DitPersonafContacto;
import mx.gob.imss.ctirss.delta.persistence.DitPersonafDom;
import mx.gob.imss.ctirss.delta.persistence.DitPersonamContacto;

/**
 * @author Cesar Garcia Mauricio 09 Feb 2012
 */
@Stateless(mappedName = "personaEntity")
public class PersonaEntity extends AbstractServiceEntity implements PersonaEntityLocal {

	@EJB
	private CalificacionesPersonaBusinessServiceRemote calificacionesPersonaBusinessService;
	@EJB
	private PersonaFisicaServiceUtilityLocal personaFisicaServiceUtility;

    @EJB
    private PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;
	
    private int iTotalRegistrosConsulta = 0;

    @Override
    public int getTotalRegistrosBusquedaPersonaFisica() {
        return iTotalRegistrosConsulta;
    }
    
    @Override
    public Fisica altaPersonaFisica(final Fisica personaFisica) {
        log.trace("PersonaEntity.altaPersonaFisica. Inicio. " + new Date());
        log.trace("PersonaEntity.altaPersonaFisica. El objeto a guardar en la base de datos es: \n\n " + personaFisica);

        Fisica personaFisicaResultado = null; // NOPMD

        if (personaFisica != null) {
            DitPersona ditPersona =  personaFisicaServiceUtility.transformarAEntidad(personaFisica);

            if (ditPersona != null) {
                //EJECUTA VALIDACIONES DE CATAlogOS
                realizarValidaciones(ditPersona);

                //ASIGNA LA FECHA DE ALTA A LA PERSONA FISICA Y SUS OBJETOS ASOCIADOS
                Date fechaAlta = new Date();
                ditPersona.setFecRegistroAlta(fechaAlta);
                PersonaConversor.convertirMayusculas(ditPersona);

                log.debug("getNomNombre: " + ditPersona.getNomNombre());
                em.persist(ditPersona);

                // CALIFICACION  (SUBESTADOVALIDADO)
                if (ditPersona.getDitHistPersonaCalificacions() != null) {
                    for (DitHistPersonaCalificacion ditHistPersonaCalificacion : ditPersona.getDitHistPersonaCalificacions()) {
                        ditHistPersonaCalificacion.setFecRegistroActualizado(fechaAlta);
                        ditHistPersonaCalificacion.setFecRegistroAlta(fechaAlta);
                        DitHistPersonaCalificacionPK idCalif = new DitHistPersonaCalificacionPK();
                        idCalif.setCveIdCalificacion(ditHistPersonaCalificacion.getDicPersonaCalificacion().getCveIdCalificacion());
                        idCalif.setCveIdPersona(ditHistPersonaCalificacion.getDitPersona().getCveIdPersona());
                        ditHistPersonaCalificacion.setId(idCalif);
                        em.persist(ditHistPersonaCalificacion);
                    }
                }

                // ESTADOS PERSONA (HIST)
                if (ditPersona.getDitHistEstadoPersonas() != null) {
                    for (DitHistEstadoPersona ditHistEstadoPersona : ditPersona.getDitHistEstadoPersonas()) {
                        ditHistEstadoPersona.setFecRegistroActualizado(fechaAlta);
                        ditHistEstadoPersona.setFecRegistroAlta(fechaAlta);
                        DitHistEstadoPersonaPK idEstado = new DitHistEstadoPersonaPK();
                        idEstado.setCveEstadoPersona(ditHistEstadoPersona.getDicEstadoPersona().getCveEstadoPersona());
                        idEstado.setCveIdPersona(ditHistEstadoPersona.getDitPersona().getCveIdPersona());
                        ditHistEstadoPersona.setId(idEstado);
                        em.persist(ditHistEstadoPersona);
                    }
                }

                // DOMICILIOS (RELACION)
                for (DitPersonafDom ditPersonafDom : ditPersona.getDitPersonafDoms()) {
                    if (ditPersonafDom.getDgDomicilioGeografico() != null && Utilerias.isNotBlank(ditPersonafDom.getDgDomicilioGeografico().getDomicilioId())) {
                        ditPersonafDom.setFecRegistroAlta(fechaAlta);
                        ditPersonafDom.setFecRegistroActualizado(fechaAlta);
                        em.persist(ditPersonafDom);
                        log.debug("Cve ditPersonafDom " + ditPersonafDom.getCveIdPersonafDom());
                    }
                }

                // MEDIOS DE CONTACTO
                for (DitPersonafContacto ditPersonafContacto : ditPersona.getDitPersonafContactos()) {
                    if (ditPersonafContacto.getDitFormaContacto() != null && ditPersonafContacto.getDitFormaContacto().getDitTipoContacto() != null) {
                        ditPersonafContacto.setFecRegistroActualizado(fechaAlta);
                        ditPersonafContacto.setFecRegistroAlta(fechaAlta);
                        em.persist(ditPersonafContacto);
                        log.debug("Cve ditPersonafContacto " + ditPersonafContacto.getCveIdPersonafContacto());
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

                personaFisicaResultado = personaFisicaServiceUtility.transformarAModelo(ditPersona);

                em.flush();
                
                System.out.println("PersonaEntity.altaPersonaFisica. La nueva persona se gener? con clave: " + ditPersona.getCveIdPersona() + "  y el siguiente objeto de negocio:\n\n " + personaFisicaResultado + "\n\n");
                log.debug("PersonaEntity.altaPersonaFisica. La nueva persona se gener? con clave: " + ditPersona.getCveIdPersona() + "  y el siguiente objeto de negocio:\n\n " + personaFisicaResultado + "\n\n");
            }
        }
        return personaFisicaResultado;
    }
    
    @Override
    public void agregarDatosPersonaFisica(Fisica personaFisica){
    	DitPersonaFisica ditFisica = new DitPersonaFisica();
    	DitPersona ditPersona = this.em.find(DitPersona.class, personaFisica.getIdPersona());
    	ditFisica.setRfc(personaFisica.getRfc());
    	ditFisica.setFecRegistroAlta(Calendar.getInstance().getTime());
    	ditFisica.setDitPersona(ditPersona);
    	this.em.persist(ditFisica);
    	personaFisica.setCveFisica(ditFisica.getCveIdPersonaFisica());
    }
    
    private void realizarValidaciones(final DitPersona ditPersona) {
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
            String cveEntFedNac = ditPersona.getDgCatEstado().getCveEnt();
            if (cveEntFedNac != null) { // && cveEntFedNac != -1) {
            	cveEntFedNac=String.format("%02d", Integer.parseInt(cveEntFedNac));
            	String desEntFedNac = null; // NOPMD
                final DgCatEstado entFedNac = em.find(DgCatEstado.class, cveEntFedNac);
                ditPersona.setDgCatEstado(entFedNac);
                if (entFedNac == null) {
                    throw new PersistenceException("No existe la entidad federativa de id " + cveEntFedNac + " en el cat?logo en la base de datos. No fue posible guardar a la persona.");
                } else {
                    desEntFedNac = entFedNac.getNomEnt();
                }
                log.trace("La entidad de nacimiento es: " + desEntFedNac);
            }
        }
    }

    @Override
    public Long contarNumPersonasTotal() {
        return (Long) getSession().createCriteria(DitPersona.class).setProjection(Projections.max("cveIdPersona")).list().get(0);
    }

    /*
     * Debido a que el controller pasa como cadenas vacias a los parametros en
     * caso de que no se envien datos de consulta, se requiere que, si la cadena
     * viene vacia se regrese a su equivalente null:
     */
    private void resetBlankFields(final DitPersonaView personaFiltro) {
        if (StringUtils.isBlank(personaFiltro.getNomNombre())) {
            personaFiltro.setNomNombre(null);
        }
        if (StringUtils.isBlank(personaFiltro.getNomPrimerApellido())) {
            personaFiltro.setNomPrimerApellido(null);
        }
        if (StringUtils.isBlank(personaFiltro.getNomSegundoApellido())) {
            personaFiltro.setNomSegundoApellido(null);
        }
        if (StringUtils.isBlank(personaFiltro.getCurp())) {
            personaFiltro.setCurp(null);
        }
        if (StringUtils.isBlank(personaFiltro.getRfc())) {
            personaFiltro.setRfc(null);
        }
        if (StringUtils.isBlank(personaFiltro.getObservaciones())) {
            personaFiltro.setObservaciones(null);
        }
    }

    public List<Fisica> buscarPersonaFisica(Fisica personaFisica, int iRegistroInicial, int iRegistrosPorPagina) {
        log.trace("PersonaEntity.buscarPersonaFisica. Inicio. " + new Date());
        log.trace("PersonaEntity.buscarPersonaFisica. Datos para paginacion. iRegistroInicial= " + iRegistroInicial + ",  iRegistrosPorPagina= " + iRegistrosPorPagina);
        log.trace("PersonaEntity.buscarPersonaFisica. Se realiza la busqueda de personas fisicas con el siguiente filtro:\n" + personaFisica);
        Fisica personaCaracteres = null;
       
        personaCaracteres = this.getPersonaSinCaracteresEspeciales(personaFisica);
        Criteria criteria = getSession().createCriteria(DitPersonaView.class);
        addRestrictionsBusquedaPersonaFisicaView(criteria, personaFisica);
        @SuppressWarnings("unchecked")
        List<DitPersonaView> listaPersonasFisicas = criteria.setMaxResults(1000).list();
        if(personaCaracteres != null) {
        	criteria = getSession().createCriteria(DitPersonaView.class);
        	addRestrictionsBusquedaPersonaFisicaView(criteria, personaCaracteres);
        	 @SuppressWarnings("unchecked")
             List<DitPersonaView> listaPersonasFisicasCaracteres = criteria.setMaxResults(1000).list();
        	 
        	 if(!listaPersonasFisicasCaracteres.isEmpty()) {
        		 listaPersonasFisicas.addAll(listaPersonasFisicasCaracteres);
        	 }
        }
        
        log.trace("PersonaEntity.buscarPersonaFisica. Total de registros encontrados " + listaPersonasFisicas.size());
        iTotalRegistrosConsulta = listaPersonasFisicas.size();

        LinkedList<DitPersonaView> listaSubconjunto = null;
        //VERIFICAMOS SI DEBEMOS REALIZAR LA PAGINACION DE REGISTROS
        if (iRegistrosPorPagina > 0) {

            //VERIFICAMOS QUE NO SE QUIERA OBTENER UNA PAGINA QUE TENGA REGISTROS MAYORES AL 1000
            if (iRegistroInicial + iRegistrosPorPagina < 1000) {

                //ESTABLECE EL LIMITE SUPERIOR E INFERIOR DE LA PAGINACION
                int iInicio = 0;
                int iFinal = 0;

                //VERIFICA QUE EL LIMITE INFERIOR DE LA PAGINACION NO SEA MENOR AL TOTAL DE REGISTROS ENCONTRADOS EN LA CONSULTA
                if (listaPersonasFisicas.size() - 1 >= iRegistroInicial) {
                    iInicio = iRegistroInicial;
                    if (listaPersonasFisicas.size() > iRegistroInicial + iRegistrosPorPagina) {
                        iFinal = iInicio + iRegistrosPorPagina - 1;
                    } else {
                        iFinal = listaPersonasFisicas.size() - 1;
                    }

                    listaSubconjunto = new LinkedList<DitPersonaView>();
                    for (int iRegistro = iInicio; iRegistro <= iFinal; iRegistro++) {
                        DitPersonaView persona = null;
                        try {
                            persona = (DitPersonaView) BeanUtils.cloneBean(listaPersonasFisicas.get(iRegistro));
                        } catch (IllegalAccessException e) {
                            log.error("Al convertir de ditPersona a ditPersonaView", e);
                        } catch (InstantiationException e) {
                            log.error("Al convertir de ditPersona a ditPersonaView", e);
                        } catch (InvocationTargetException e) {
                            log.error("Al convertir de ditPersona a ditPersonaView", e);
                        } catch (NoSuchMethodException e) {
                            log.error("Al convertir de ditPersona a ditPersonaView", e);
                        } catch (Exception e) {
                            log.error("Al convertir de ditPersona a ditPersonaView", e);
                        }
                        if (persona != null && persona.getDicPais() == null) {
                            persona.setDicPais(new DicPai()); // Evita NPE a la JSON
                        }
                        listaSubconjunto.add(persona);
                    }

                    log.trace("PersonaEntity.buscarPersonaFisica. Se ejecuto paginaci?n. Registros encontrados en el subconjunto: " + listaSubconjunto.size());
                }
            }
        } else {
            //REGRESA TODAS LOS REGISTROS ENCONTRADOS EN LA CONSULTA
            listaSubconjunto = new LinkedList<DitPersonaView>(listaPersonasFisicas);
        }
        return personaFisicaServiceUtility.covertirListaEntityViewToPersonaFisica(listaSubconjunto);
    }


    public String getRazonSocial(Long idPersona, Long tipoPersona){
        String nombreRazonSocial = null;
        String query ="SELECT MGPBDTU9X.FN_GETNOMBREPATRON_XTIPPERSONA('"+tipoPersona+"','"+idPersona+"') NOMBRE_PROCESADO FROM DUAL";
        this.log.info(" -- Implements...return");
        try{
            nombreRazonSocial = (String) this.em.createNativeQuery(query).getSingleResult();
            if(nombreRazonSocial!=null && !nombreRazonSocial.isEmpty()){
                this.log.info(" -- Nombre o Razon Social actualizado: "+nombreRazonSocial);
            }
        }catch(Exception nre){
            System.err.println("No existe nombre o razon social para el idPersona: "+idPersona);
            nre.printStackTrace();
        }
        return nombreRazonSocial;
    }
    
    public String getRazonSocial(String rfc,String razonSocial){
        String nombreRazonSocial = null;
        
        rfc = rfc.replace("'", "''");
        razonSocial = razonSocial.replace("'", "''");
        
        String query ="SELECT MGPBDTU9X.FN_CO32_GETRAZONSOCIAL('"+rfc+"','"+razonSocial+"') NOMBRE_PROCESADO FROM DUAL";
        this.log.info(" -- Implements FN_CO32_GETRAZONSOCIAL...return");
        try{
            nombreRazonSocial = (String) this.em.createNativeQuery(query).getSingleResult();
            if(nombreRazonSocial!=null && !nombreRazonSocial.isEmpty()){
                this.log.info(" -- Nombre o Razon Social actualizado: "+nombreRazonSocial);
            }
        }catch(Exception nre){
            System.err.println("No existe nombre o razon social para el RFC: "+rfc);
            nre.printStackTrace();
        }
        return nombreRazonSocial;
    }
    

    private void addRestrictionsBusquedaPersonaFisicaView(final Criteria criteria, final Fisica filtro) {

        if (filtro != null) {
            if (filtro.getIdPersona() != null) {
                criteria.add(Restrictions.eq("cveIdPersona", filtro.getIdPersona().intValue()));
            }
            // El -1 es equivalente en todos los combos a null.
            if (filtro.getSexo() != null && Utilerias.isNotBlank(filtro.getSexo().getIdSexo())) {
                criteria.createAlias("dicSexo", "sexo").add(Restrictions.eq("sexo.cveIdSexo", filtro.getSexo().getIdSexo().longValue()));
            }
            if (filtro.getLugarNacimiento() != null && filtro.getLugarNacimiento().getClave() != null && Utilerias.isNotBlank(Integer.parseInt(filtro.getLugarNacimiento().getClave()))) {
                criteria.add(Restrictions.eq("entidadNacimiento.cveEnt", filtro.getLugarNacimiento().getClave()));
            }
            if (filtro.getPersonaEstados() != null && !filtro.getPersonaEstados().isEmpty()) {
                Collection<Long> idEstados = null;
                for (PersonaEstado personaEstado : filtro.getPersonaEstados()) {
                	if (personaEstado.getEstadoPersona() != null){
                    	if (personaEstado.getEstadoPersona().getIdEstadoPersona() != null){
                    		if (idEstados == null){
                    			idEstados = new ArrayList<Long>();
                    		}
                    		idEstados.add(personaEstado.getEstadoPersona().getIdEstadoPersona());
                    	}                	                		
                	}
                }
                if (idEstados != null){
                	criteria.createAlias("dicEstadoPersona", "estadoPersona").add(Restrictions.in("estadoPersona.cveEstadoPersona", idEstados));
                }
            }
            if (filtro.getPersonaCalificaciones() != null && !filtro.getPersonaCalificaciones().isEmpty()) {
                Collection<Long> idSubEstados = null;
                for (PersonaCalificacion personaCalificacion : filtro.getPersonaCalificaciones()) {
                	if (personaCalificacion.getCalificacion() != null){
                    	if (personaCalificacion.getCalificacion().getIdCalificacion() != null){
                    		if (idSubEstados == null){
                    			idSubEstados = new ArrayList<Long>();
                    		}
                    		idSubEstados.add(personaCalificacion.getCalificacion().getIdCalificacion().longValue());
                    	}                		
                	}
                }
                if (idSubEstados!= null){
                	criteria.createAlias("dicPersonaCalificacion", "calificacion").add(Restrictions.in("calificacion.cveIdCalificacion", idSubEstados));	
                }
            }

            final DitPersonaView ditPersona = personaFisicaServiceUtility.convertirPersonaFisicaToEntityView(filtro);

            resetBlankFields(ditPersona);
            PersonaConversor.convertirMayusculas(ditPersona);
            final Example example = Example.create(ditPersona);
            if (Boolean.parseBoolean(filtro.getBusqAprox())) {
                example.enableLike(MatchMode.ANYWHERE);
            }

            criteria.add(example);
        }
    }

    /**
     * Metodo que busca todos los domicilios relacionados con un idPersona
     * 
     * @param idPersona
     * @return
     */
    @Override
    public List<Domicilio> buscarDomiciliosPersona(Long idPersona) {
        DitPersona ditPersona = em.find(DitPersona.class, idPersona);
        List<Domicilio> domicilios = new ArrayList<Domicilio>();

        for (DitPersonafDom ditPersonafDom : ditPersona.getDitPersonafDoms()) {
			Domicilio domAux = new Domicilio();
			domAux.setClave(new Long(ditPersonafDom.getDgDomicilioGeografico().getDomicilioId()).intValue());
			domicilios.add(domAux);
        }
        return domicilios;
    }
    
    /**
     * Servicio para actualizar una Persona, pero de momento s�lo se actualizar� la fecha de defunci�n
     * @param persona
     */
    public void actualizarPersona(final Fisica fisica) throws PersonaNoEncontradaException {

        final DitPersona ditPersona = em.find(DitPersona.class, fisica.getIdPersona());
        if (ditPersona == null) {
            throw new PersonaNoEncontradaException(fisica.getIdPersona());
        } else {
            // Fecha de defuncion
            if (fisica.getFechaDefuncion() != null) {
                //Si en los datos de entrada la fecha de defuncion 
                // es diferente de nulo hay que modificarla.
                ditPersona.setFecDefuncion(fisica.getFechaDefuncion());
            }

            // Lugar de nacimiento
            if(fisica.getLugarNacimiento() != null && StringUtils.isNotBlank(fisica.getLugarNacimiento().getClave()) && Utilerias.isNotBlank(new Integer(Integer.parseInt(fisica.getLugarNacimiento().getClave())))) {
                final DgCatEstado dgCatEstado = new DgCatEstado();
                dgCatEstado.setCveEnt(fisica.getLugarNacimiento().getClave());
                ditPersona.setDgCatEstado(dgCatEstado);
            }

            // Estado civil
            if(fisica.getEstadoCivil() != null && Utilerias.isNotBlank(fisica.getEstadoCivil().getIdEstadoCivil())) {
                final DicEstadoCivil dicEstadoCivil = new DicEstadoCivil();
                dicEstadoCivil.setCveIdEstadoCivil(fisica.getEstadoCivil().getIdEstadoCivil().longValue());
                ditPersona.setDicEstadoCivil(dicEstadoCivil);
            }

            // Fecha de nacimiento
            if(fisica.getFechaNacimiento() != null) {
                ditPersona.setFecNacimiento(fisica.getFechaNacimiento());
            }

            // Nombre completo
            if(fisica.getNombre() != null) {
                ditPersona.setNomNombre(fisica.getNombre());
            }
            if(fisica.getPrimerApellido() != null) {
                ditPersona.setNomPrimerApellido(fisica.getPrimerApellido());
            }
            if(fisica.getSegundoApellido() != null) {
                ditPersona.setNomSegundoApellido(fisica.getSegundoApellido());
            }

            // Sexo
            if(fisica.getSexo() != null && Utilerias.isNotBlank(fisica.getSexo().getIdSexo())) {
                final DicSexo dicSexo = new DicSexo();
                dicSexo.setCveIdSexo(fisica.getSexo().getIdSexo().longValue());
                ditPersona.setDicSexo(dicSexo);
            }
            
            
            //LUDS 12/12/2012: se agrega que se actualice el RFC y CURP ademas las calificaciones
            
            if(fisica.getCurp() != null && !fisica.getCurp().isEmpty()){
            	ditPersona.setCurp(fisica.getCurp());
            }
            
            if( fisica.getRfc() != null && !fisica.getRfc().isEmpty()){
            	ditPersona.setRfc(fisica.getRfc());
            }
            
            
            
            // Se tiene que revisar que se debe de hacer con las calificaciones....
			if (fisica.getPersonaCalificaciones() != null
					&& !fisica.getPersonaCalificaciones().isEmpty()) {
				
				Iterator<PersonaCalificacion> it = fisica.getPersonaCalificaciones().iterator();
				DitHistPersonaCalificacion ditCalificacion = null;
				while(it.hasNext()){
					PersonaCalificacion p  = it.next();
					DitHistPersonaCalificacionPK pk = new DitHistPersonaCalificacionPK();
					pk.setCveIdCalificacion(p.getCalificacion().getIdCalificacion());
					pk.setCveIdPersona(ditPersona.getCveIdPersona());
					
					
					ditCalificacion = this.em.find(DitHistPersonaCalificacion.class, pk);
					if(ditCalificacion == null){
						this.log.warn("La calificacion no existe, se debe de agregar");
						/*
						 * Si la calificacion no existe entonces debemos de agregar
						 * en las calificaciones
						 */
						
						ditCalificacion = new DitHistPersonaCalificacion();
						ditCalificacion.setId(pk);
						ditCalificacion.setFecRegistroActualizado(new Date());
						ditCalificacion.setFecRegistroAlta(new Date());
						ditCalificacion.setFecRegistroBaja(null);
						
						this.em.persist(ditCalificacion);
						
					}else{
						this.log.warn("La calificacion ya existe, se debe de agregar");
						/*
						 * Si la calificacion ya existe, se debe de actualizar solo la fecha 
						 * de actualizacion.
						 */
						
						ditCalificacion.setFecRegistroActualizado(new Date());
						
						
					}
					
					
					
				}
				
            	
            }
            
            
        }
    }
    
    public List<Serie> getSeriesNss(Long idDelegacion, Long idSubDelegacion){
    	List<Serie> listaSeries = null;
    	
    	List<DitAsignacionSerie> listaAsignacionSerieInicial = null;
    	
    	if (idDelegacion != null){
        	String sSql = "SELECT s FROM DitAsignacionSerie s " +
        					" WHERE s.dicDelegacion.cveIdDelegacion = " + idDelegacion +
        					" AND s.dicSubdelegacion.cveIdSubdelegacion is null";
        	Query queryDelegaciones = em.createQuery(sSql);
        	List<DitAsignacionSerie> listaDelegacion = (List<DitAsignacionSerie>)queryDelegaciones.getResultList();
        	listaAsignacionSerieInicial = agregaRegisttrosListaFinal(listaAsignacionSerieInicial, listaDelegacion);    		
    	}
    	
    	if (idDelegacion != null && idSubDelegacion != null){
        	String sSql = "SELECT s FROM DitAsignacionSerie s " +
        					" WHERE s.dicDelegacion.cveIdDelegacion = " + idDelegacion +
        					" AND s.dicSubdelegacion.cveIdSubdelegacion = " + idSubDelegacion;
        	Query querySubdelegaciones = em.createQuery(sSql);
        	List<DitAsignacionSerie> listaDelegacion = (List<DitAsignacionSerie>)querySubdelegaciones.getResultList();
        	listaAsignacionSerieInicial = agregaRegisttrosListaFinal(listaAsignacionSerieInicial, listaDelegacion);    		
    	}

    	String sSql = "SELECT s FROM DitAsignacionSerie s " +
    					" WHERE s.dicDelegacion.cveIdDelegacion is null" +
    					" AND s.dicSubdelegacion.cveIdSubdelegacion is null";
    	Query queryTodasLasDelegaciones = em.createQuery(sSql);
    	List<DitAsignacionSerie> listaDelegacion = (List<DitAsignacionSerie>)queryTodasLasDelegaciones.getResultList();
    	listaAsignacionSerieInicial = agregaRegisttrosListaFinal(listaAsignacionSerieInicial, listaDelegacion);    		
    	
    	
    	//REGRESAMOS LA LISTA DE SERIES Y CONFIGURAMOS LOS OBJETOS
    	if (listaAsignacionSerieInicial != null){
    		for (DitAsignacionSerie ditAsignacionSerie : listaAsignacionSerieInicial){
    			if (listaSeries == null){
    				listaSeries = new LinkedList();
    			}
    			Serie serie = new Serie();
    			serie.setIdSerie(ditAsignacionSerie.getDicSeriesNss().getCveIdSerie());
    			if (ditAsignacionSerie.getDicSeriesNss().getNumSerie() != null){
    				serie.setNumSerie(ditAsignacionSerie.getDicSeriesNss().getNumSerie().longValue());	
    			}
    			
    			TipoSerie tipoSerie = new TipoSerie();
    			tipoSerie.setIdTipoSerie(new Long(ditAsignacionSerie.getDicSeriesNss().getDicTipoSerie().getCveIdTipoSerie()).intValue());
    			//TODO: mover esta concatenacion a un 'SerieServiceUtility' dentro del proyecto de asegurados para tener estas operaciones fuera de esta clase 
    			tipoSerie.setDescripcion("[" + ditAsignacionSerie.getDicSeriesNss().getNumSerie().longValue() + "] " + ditAsignacionSerie.getDicSeriesNss().getDicTipoSerie().getDesTipoSerie());
    			serie.setTipoSerie(tipoSerie);
    			listaSeries.add(serie);
       		}
    	}
    	
    	return listaSeries;
    }
    
	public List<DitAsignacionSerie> agregaRegisttrosListaFinal(List<DitAsignacionSerie> listaInicial, List<DitAsignacionSerie> listaAgregar){
		Map<Long,DitAsignacionSerie> mapaFinal = new TreeMap<Long,DitAsignacionSerie>();
		LinkedList<DitAsignacionSerie> listaFinal = null;
		
		//AGREGAMOS LAS LISTAS "INICIAL" Y "AGREGAR" AL MAPA FINAL
		if (listaAgregar != null){
			for (DitAsignacionSerie objetoLista : listaAgregar){
				mapaFinal.put(objetoLista.getDicSeriesNss().getCveIdSerie(), objetoLista);
			}
		}
		if (listaInicial != null){
			for (DitAsignacionSerie objetoLista : listaInicial){
				if (mapaFinal.get(objetoLista.getDicSeriesNss().getCveIdSerie()) == null){
					mapaFinal.put(objetoLista.getDicSeriesNss().getCveIdSerie(), objetoLista);
				}
			}
		}
		
		//RECORREMOS EL MAPA FINAL EN ORDEN ASCENDENTE PARA OBTENER LA LISTA FINAL
		for (Map.Entry<Long, DitAsignacionSerie> entry : mapaFinal.entrySet()){
			if (listaFinal == null){
				listaFinal = new LinkedList<DitAsignacionSerie>();
			}
			listaFinal.add(entry.getValue());
		}
		
		return listaFinal;
	}
	
	/**
	 * Este metodo recibe un objeto Serie del cual extraemos el idSerie y regresamos un objeto Serie completo
	 * @param serie
	 * @return
	 */
    public Serie getSerie(Serie serie){
    	
    	List<DitAsignacionSerie> series = null;
    			
    	if (serie.getIdSerie() != null){
        	String sSql = "SELECT s FROM DitAsignacionSerie s WHERE s.dicSeriesNss.cveIdSerie = " + serie.getIdSerie();
        	Query querySerie = em.createQuery(sSql);
        	series = (List<DitAsignacionSerie>)querySerie.getResultList();
    	}
    	
    	if (series != null){
    		DitAsignacionSerie ditAsignacionSerie = series.get(0);
    		
    			
    			serie.setIdSerie(ditAsignacionSerie.getDicSeriesNss().getCveIdSerie());
    			if (ditAsignacionSerie.getDicSeriesNss().getNumSerie() != null){
    				serie.setNumSerie(ditAsignacionSerie.getDicSeriesNss().getNumSerie().longValue());
    				serie.setAnioRegistro(ditAsignacionSerie.getDicSeriesNss().getNumAnioRegistro().intValue());
    			}
    			
    			TipoSerie tipoSerie = new TipoSerie();
    			tipoSerie.setIdTipoSerie(new Long(ditAsignacionSerie.getDicSeriesNss().getDicTipoSerie().getCveIdTipoSerie()).intValue());
    			tipoSerie.setDescripcion("[" + ditAsignacionSerie.getDicSeriesNss().getNumSerie().longValue() + "] " + ditAsignacionSerie.getDicSeriesNss().getDicTipoSerie().getDesTipoSerie());
    			serie.setTipoSerie(tipoSerie);
    	}
    	this.log.warn("Serie encontrada :" + serie);
    	return serie;
    }
    
    
    /**
     * 191807 140912
     * Metodo encargado de registrar un identificador relacionado con una persona fisica
     * @param fisica
     */
    public List<DicTipoIdentificador> registrarIdentificador(Fisica fisica){
    	
    	List<DicTipoIdentificador> tiposIdentificador = null;
    	String sql = "SELECT ti FROM DicTipoIdentificador ti";
    	
    	try{
	    	Query queryTiposIdentificador = em.createQuery(sql);
	    	tiposIdentificador = (List<DicTipoIdentificador>) queryTiposIdentificador.getResultList();
    	}catch(Exception e){
    		System.out.println(e.getMessage());
    	}
    	
    	System.out.println(tiposIdentificador);
    	
    	return tiposIdentificador;
    }
    
    /**
     * 191807 081012
     * Metodo encargado de actualizar una persona fisica en BD 
     * @param personaFisica
     * @return
     */
    public Fisica actualizarPersonaFisica(final Fisica fisica) {
    	
        log.debug("PersonaEntity.actualizarPersonaFisica. Iniciando actualizacion de persona fisica en BDU... ");
        log.debug("El objeto a guardar en la BDU es: *** " + fisica + " *** ");

        Fisica fisicaResultado = null;

        if(fisica != null){
        	
        	// Si no se hace este find, entonces hibernate entiende que se tiene que hacer un INSERT y no un UPDATE
        	DitPersona ditPersona = em.find(DitPersona.class, fisica.getCveFisica());
        	
        	PersonaConversor.actualizarPersonaFisicaToEntity(ditPersona, fisica);
       	
//        	ditPersona.setNomNombre(fisica.getNombre());
//        	ditPersona.setNomPrimerApellido(fisica.getPrimerApellido());
//        	ditPersona.setNomSegundoApellido(fisica.getSegundoApellido());

        	if(ditPersona != null){
            	
                // Se validan los campor provenientes de catalogos
                realizarValidaciones(ditPersona);

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
                    
                    this.em.merge(ditDoctoPersona);

                }

                fisicaResultado = personaFisicaServiceUtility.transformarAModelo(ditPersona);

                log.debug("PersonaEntity.actualizarPersonaFisica. Finalizando actualizacion de persona fisica en BDU... ");
                log.debug("La persona fisica fue actualizada con exito ");
                
            }
        }
        
        return fisicaResultado;
    }

	/**
	 * 
	 */
	public Fisica buscarPersonaPorId(Long idPersona) {
		DitPersona ditPersona = em.find(DitPersona.class, idPersona);	
		
        if (ditPersona != null) {
            Fisica personaRet = new Fisica();
            
           personaRet = personaFisicaServiceUtility.transformarAModelSoloDatosPersonales(ditPersona);
            
            return personaRet;
        }	else {
        	return null;
        }
	}
	
	@Override
	public void afectarDatosPersona(AfectarDatosPersonaWrapper datosPersona)
			throws PersonaNoEncontradaException {

		Fisica fisica = datosPersona.getFisica();

		final DitPersona ditPersona = em.find(DitPersona.class,
				fisica.getIdPersona());

		if (ditPersona == null) {
			throw new PersonaNoEncontradaException(fisica.getIdPersona());
		} else {

			// Nombre completo
			if (datosPersona.getModificarNombre()) {
				ditPersona.setNomNombre(fisica.getNombre());
				ditPersona.setNomPrimerApellido(fisica.getPrimerApellido());
				ditPersona.setNomSegundoApellido(fisica.getSegundoApellido());
			}

			// CURP
			if (datosPersona.getModificarCURP()) {
				ditPersona.setCurp(fisica.getCurp());
			}

			// Sexo
			if (datosPersona.getModificarSexo()) {
				final DicSexo dicSexo = new DicSexo();
				dicSexo.setCveIdSexo(fisica.getSexo().getIdSexo().longValue());
				ditPersona.setDicSexo(dicSexo);
			}

			// Fecha de nacimiento
			if (datosPersona.getModificarFechaNacimiento()) {
				ditPersona.setFecNacimiento(fisica.getFechaNacimiento());
			}

			// Lugar de nacimiento
			if (datosPersona.getModificarLugarNacimiento()) {
				DgCatEstado dgCatEstado = new DgCatEstado();
				dgCatEstado.setCveEnt(fisica.getLugarNacimiento().getClave());
				ditPersona.setDgCatEstado(dgCatEstado);
				
				DicPai pais = new DicPai();
				pais.setCveIdPais(fisica.getPais().getIdPais());
				ditPersona.setDicPai(pais);
			}
			
			// RFC
			if(datosPersona.getModificarRFC()){
            	ditPersona.setRfc(fisica.getRfc());
            }
			
			// FECHA DE DEFUNCI�N
			if( datosPersona.getModificarFechaDefuncion() ){
				ditPersona.setFecDefuncion(fisica.getFechaDefuncion());
			}
			
			// Fecha actualizacion persona
			ditPersona.setFecRegistroActualizado(new Date());
			
			// MES Y ANIO DE NACIMIENTO
			if(datosPersona.getModificarMesYAnioNacimiento()) {
				if(datosPersona.getTomarMesYAnioDeFechaNacimiento() && fisica.getFechaNacimiento() != null) {
					Calendar calFechaNac = Calendar.getInstance();
					calFechaNac.setTime(fisica.getFechaNacimiento());
					//Se obtiene el mes de nacimiento de la fecha de nacimiento
					int mesNacimiento = calFechaNac.get(Calendar.MONTH) +1;
					//se obtiene lo que sobra de la division entre 100, por ejemplo 2016/100 el sobrante sera 16
					int anioNacimiento = calFechaNac.get(Calendar.YEAR)%100;
					log.debug("Se actualizara la persona " + fisica.getIdPersona() + ", "+fisica.getCurp()+" con el mes: " + mesNacimiento + " y el anio: " + anioNacimiento +
							" que se estan sacando de la fecha de nacimiento " + fisica.getFechaNacimiento());
					ditPersona.setNumMesNacReg(mesNacimiento);
					ditPersona.setNumAnioNacReg(anioNacimiento);
					
				} else {
					log.debug("Se actualizara la persona " + fisica.getIdPersona() + ", "+fisica.getCurp()+" con el mes: " + fisica.getMesRegistroNac() + " y el anio: " + 
							fisica.getAnioRegistroNac() + " que se sacan del objeto persona");
					ditPersona.setNumMesNacReg(fisica.getMesRegistroNac());
					ditPersona.setNumAnioNacReg(fisica.getAnioRegistroNac());
				}
			}
		}
    }
	
	@Override
	public void afectarCalificacionesPersona(AfectarDatosPersonaWrapper datosPersona) {
		
		Fisica fisica = datosPersona.getFisica();
		
		/*
		 * Se actualizan la calificaciones, si se viene del ICA, las
		 * calificaciones RENAPO/SAT se agregar/actualizan y la calificaci�n
		 * IMSS se da de baja; si se viene de la Modificaci�n manual se deben
		 * dar de baja las calificaciones existentes y s�lo dar de alta la
		 * calificaci�n "validado por IMSS"
		 */
		if (datosPersona.getTipoServicio() != null) { 
			if(datosPersona.getTipoServicio().getIdServicio().equals(TipoServicioModificacionEnum.ICA.getIdServicio())){
				try {
					if (datosPersona.getModificarDatosRENAPO() && datosPersona.getModificarDatosSAT()) {
						this.calificacionesPersonaBusinessService.calificarRENAPOySAT(fisica);
					} else if (datosPersona.getModificarDatosRENAPO()) {
						this.calificacionesPersonaBusinessService.calificarRENAPO(fisica);
					} else if (datosPersona.getModificarDatosSAT()) {
						this.calificacionesPersonaBusinessService.calificarSAT(fisica);
					}
				} catch(PersonaSinCalificacionesException e) {
					this.log.error(e);
				}
			}else if (datosPersona.getTipoServicio().getIdServicio().equals(
					TipoServicioModificacionEnum.MDM.getIdServicio())) {
				
				try {
					this.calificacionesPersonaBusinessService.calificarIMSS(fisica);
				} catch (PersonaSinCalificacionesException e) {
					this.log.error(e);
				}
			}
		}else {
			this.log.warn("No se afectar?n calificaci?n ya que no se cuenta con el tipo de servicio (ICA o MDM)");
		}
	}
	
	@Override
	public String obtenerNombrePersona(Persona persona) {
	
		StringBuffer nombrePersona = new StringBuffer();
		StringBuffer jpaQuery = new StringBuffer();
		boolean isMoral = false;
		
		if (persona.getTipoPersona() != null && persona
						.getTipoPersona().getIdTipoPersona().longValue() == TipoPersonaEnum.MORAL
						.getId()) {
			isMoral = true;
			
			jpaQuery.append("select pm.denominacionRazonSocial, pm.dicTipoSociedad.desTipoSociedadAbrev "); 
			jpaQuery.append("from DitPersonaMoral pm ");
			jpaQuery.append("left outer join pm.dicTipoSociedad ");
			jpaQuery.append("where pm.cveIdPersonaMoral = :cvePersona");
		} else {
			jpaQuery.append("select p.nomNombre, p.nomPrimerApellido, p.nomSegundoApellido ");
			jpaQuery.append("from DitPersona p ");
			jpaQuery.append("where p.cveIdPersona = :cvePersona");
		}
		
		Query query = this.em.createQuery(jpaQuery.toString());
		query.setParameter("cvePersona", persona.getIdPersona());
		
		Object[] resultado = (Object[]) query.getSingleResult();
		
		if(!isMoral){
			nombrePersona.append(resultado[0]).append(" ");
			nombrePersona.append(resultado[1]).append(" ");
			nombrePersona.append(resultado[2]);
		} else {
			nombrePersona.append(resultado[0]).append(" ");
			nombrePersona.append(resultado[1]);
		}
		
		return nombrePersona.toString();
	}

	public List <DitPersona> getPersonasConCalificacionByCurp(String curp){
		String strConsultaJpa = null;
		strConsultaJpa = "Select calHist.ditPersona from  DitHistPersonaCalificacion calHist " +
				"where calHist.ditPersona.curp = :curp" +
					" and calHist.id.cveIdCalificacion not in(:cveSinCalifiacion , :cveNoValidado, :cveEncontradoImss) "
					+ " and calHist.fecRegistroBaja is null"
					+ " ORDER BY calHist.ditPersona.fecRegistroActualizado ASC ";
		
		this.log.debug("haciendo el query con la entidad [" + strConsultaJpa+ "]");
		Query query = this.em.createQuery(strConsultaJpa);
		
		query.setParameter("curp", curp);
		query.setParameter("cveSinCalifiacion", CalificacionEnum.SIN_CALIFICACION.getCodigo());
		query.setParameter("cveNoValidado", CalificacionEnum.NO_VALIDADO.getCodigo());
		query.setParameter("cveEncontradoImss", CalificacionEnum.ENCONTRADO_IMSS.getCodigo());
		
		List<DitPersona> lstPersona = (List<DitPersona>) query.getResultList();
		
		return lstPersona;
	}


    public List <DitPersona> getPersonaEscVirtualByCurp(String curp){

        StringBuffer queryVirtual = new StringBuffer();
        List<DitPersona> lstPersona = new ArrayList<DitPersona>();

        queryVirtual.append("" +
                "WITH ANALISIS_PROPIEDADES AS " +
                "(" +
                "SELECT IDT.CVE_ID_PERSONA " +

                "   FROM ( " +
                "         SELECT CVE_ID_PERSONA, RFC, " +
                "                CASE WHEN ( SELECT COUNT(0) REGS FROM MGPBDTU9X.DIT_LLAVE_ASEGURADO ASG  WHERE ASG.CVE_ID_PERSONA = PER.CVE_ID_PERSONA ) > 0 THEN 1 ELSE 0 END ASEGURADO, " +
                "                CASE WHEN ( SELECT COUNT(0) REGS FROM MGPBDTU9X.DIT_LLAVE_PATRON PTR    WHERE PTR.CVE_ID_PERSONA = PER.CVE_ID_PERSONA ) > 0 THEN 1 ELSE 0 END PATRON, " +
                "                CASE WHEN ( SELECT COUNT(0) REGS FROM MGPBDTU9X.DIT_REPRESENTANTE_LEGAL RLG WHERE RLG.CVE_ID_PERSONA = PER.CVE_ID_PERSONA ) > 0 THEN 1 ELSE 0 END REPRESENTANTE_LEGAL, " +
                "                CASE WHEN ( SELECT NVL( MIN( CVE_ID_CALIFICACION ), 0 ) CAL FROM MGPBDTU9X.DIT_HIST_PERSONA_CALIFICACION HCL WHERE HCL.CVE_ID_PERSONA = PER.CVE_ID_PERSONA ) > 0 THEN 1 ELSE 0 END CALIFICACION, " +
                "                CASE WHEN ( SELECT COUNT(0) REGS FROM MGPBDTU9X.DIT_TRAMITE_PERSONA_FISICA PF INNER JOIN MGPBDTU9X.DIT_TRAMITE TRM ON PF.CVE_ID_TRAMITE = TRM.CVE_ID_TRAMITE WHERE PF.CVE_ID_PERSONA = PER.CVE_ID_PERSONA AND TRM.CVE_ID_TIPO_TRAMITE = 76 ) > 0 THEN 1 ELSE 0 END ESCRITORIO_VIRTUAL, " +
                "                CASE WHEN ( SELECT COUNT(0) REGS FROM MGPNDIC2.NDT_CONTADOR_PUBLICO_AUT CPA WHERE CPA.CVE_ID_PERSONA = PER.CVE_ID_PERSONA ) > 0 THEN 1 ELSE 0 END CONTADOR_PUBLICO " +
                "           FROM MGPBDTU9X.DIT_LLAVE_PERSONA PER " +
                "          WHERE PER.CURP IN ( :curp ) " +
                "       ) IDT " +
                "ORDER BY  " +
                "      CASE WHEN ESCRITORIO_VIRTUAL = 1 THEN 1 " +
                "       ELSE " +
                "          CASE WHEN PATRON = 1 THEN 2 " +
                "          ELSE " +
                "             CASE WHEN ASEGURADO = 1 THEN 3 " +
                "             ELSE " +
                "                CASE WHEN REPRESENTANTE_LEGAL = 1 THEN 4 " +
                "                ELSE " +
                "                   CASE WHEN CONTADOR_PUBLICO = 1 THEN 5 " +
                "                   ELSE " +
                "                      99 " +
                "                   END " +
                "                END " +
                "             END " +
                "          END " +
                "       END, CALIFICACION " +
                ") " +
                "SELECT AP.* " +
                "  FROM ANALISIS_PROPIEDADES AP " +
                "WHERE ROWNUM = 1 ");

        this.log.debug(" -- el query a ejecutar es : "+queryVirtual);

//        try{

            javax.persistence.Query query = em.createNativeQuery(queryVirtual.toString());
            query.setParameter("curp", curp);

            List<Object> cveIdPersonas =  query.getResultList();

            for(Object cveIdPersonaObject:cveIdPersonas) {
                BigDecimal objtc= (BigDecimal) cveIdPersonaObject;
                Long cveIdPersona = objtc.longValue();

                    if (cveIdPersona != null && cveIdPersona.intValue() > 0) {
                        DitPersona ditPersona = em.find(DitPersona.class, cveIdPersona);
                        this.log.info(" -- Persona con Fiel encontrada: " + cveIdPersona);
                        lstPersona.add(ditPersona);
                    }

            }

//        }catch(Exception nre){
//            log.error("ocurrio un error al consultar a la persona con registros de usuario", nre);
//            throw nre;
//        }


        return lstPersona;
    }


	@SuppressWarnings("unchecked")
	@Override
	public List<Fisica> obtenerPersonaNssByCurp(String curp) {


		List<Fisica> personasNSS = null;
		List<DitAsignacionNss> ditPersonasNSS = null;
		
		StringBuffer jpaQuery = new StringBuffer();
		jpaQuery.append("select new DitAsignacionNss(asig.numNss, asig.ditPersona) "); 
		jpaQuery.append("from DitAsignacionNss asig ");
		jpaQuery.append("where asig.ditPersona.curp = :curp ");
		jpaQuery.append("and asig.fecRegistroBaja is null ");
		jpaQuery.append("and (asig.indActivo = :indActivo or asig.indActivo is null)");
		
		Query query = this.em.createQuery(jpaQuery.toString());
		query.setParameter("curp", curp);
		query.setParameter("indActivo", BigDecimal.ONE);
		
		ditPersonasNSS = query.getResultList();
		
		if (!ditPersonasNSS.isEmpty()){
			Fisica fisica = null;
			personasNSS = new ArrayList<Fisica>();
			for (DitAsignacionNss ditAsignacionNss : ditPersonasNSS){
				fisica = this.personaFisicaServiceUtility.transformarAModelo(ditAsignacionNss.getDitPersona());
				fisica.setNss(ditAsignacionNss.getNumNss());
				personasNSS.add(fisica);
			}
		}

		return personasNSS;
	}

    @Override
    public List<Fisica> obtenerPersonaNssByCurpNoIndActivo(String curp) {

        List<Fisica> personasNSS = null;
        List<DitAsignacionNss> ditPersonasNSS = null;

        StringBuffer jpaQuery = new StringBuffer();
        jpaQuery.append("select new DitAsignacionNss(asig.numNss, asig.ditPersona) ");
        jpaQuery.append("from DitAsignacionNss asig ");
        jpaQuery.append("where asig.ditPersona.curp = :curp ");
        jpaQuery.append("and asig.fecRegistroBaja is null ");

        Query query = this.em.createQuery(jpaQuery.toString());
        query.setParameter("curp", curp);

        ditPersonasNSS = query.getResultList();

        if (!ditPersonasNSS.isEmpty()){
            Fisica fisica = null;
            personasNSS = new ArrayList<Fisica>();
            for (DitAsignacionNss ditAsignacionNss : ditPersonasNSS){
                fisica = this.personaFisicaServiceUtility.transformarAModelo(ditAsignacionNss.getDitPersona());
                fisica.setNss(ditAsignacionNss.getNumNss());
                personasNSS.add(fisica);
            }
        }

        return personasNSS;
    }

    @Override
	public List<AsignacionNSS> obtenerNsssByCurp(String curp) {

		List<AsignacionNSS> personasNSS = null;
		List<DitAsignacionNss> ditPersonasNSS = null;
		
		Criteria query = this.getSession().createCriteria(DitAsignacionNss.class);
		Criterion indActivoUno = Restrictions.eq("indActivo", BigDecimal.ONE);
		Criterion indActivoNull = Restrictions.isNull("indActivo");
		query.add(Restrictions.or(indActivoUno, indActivoNull));
		query.createAlias("ditPersona", "persona");
		query.add(Restrictions.eq("persona.curp", curp));
		
		ditPersonasNSS = query.list();
		
		if (!ditPersonasNSS.isEmpty()){
			AsignacionNSS fisica = null;
			personasNSS = new ArrayList<AsignacionNSS>();
			for (DitAsignacionNss ditAsignacionNss : ditPersonasNSS){
				fisica = this.personaFisicaServiceUtility.transformarNssToModel(ditAsignacionNss);
				personasNSS.add(fisica);
			}
		}

		return personasNSS;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> obtenerNssPersona(Long idPersona, boolean soloVigente)
			throws PersonaConVariosNSSException, PersonaSinNSSException {
		
		StringBuffer jpaQuery = new StringBuffer();
		jpaQuery.append("select asig.numNss "); 
		jpaQuery.append("from DitAsignacionNss asig ");
		jpaQuery.append("where asig.ditPersona.cveIdPersona = :idPersona ");
		if (soloVigente) {
			jpaQuery.append("and asig.fecRegistroBaja is null ");
			jpaQuery.append("and (asig.indActivo = :indActivo or asig.indActivo is null)");
		}
		
		Query query = this.em.createQuery(jpaQuery.toString());
		query.setParameter("idPersona", idPersona);
		if (soloVigente) {
			query.setParameter("indActivo", BigDecimal.ONE);	
		}
		
		List<String> listaNSS = query.getResultList();
		
		this.log.debug("Se obtuvieron " + listaNSS.size()
				+ " NSS para el idPersona " + idPersona + " [soloVigente="
				+ soloVigente + "]");

		return listaNSS;
	}

	@Override
	public String obtenerCurpPersona(Long idPersona) {
		StringBuffer jpaQuery = new StringBuffer();
		jpaQuery.append("select p.curp ");
		jpaQuery.append("from DitPersona p ");
		jpaQuery.append("where p.cveIdPersona = :cvePersona");

		Query query = this.em.createQuery(jpaQuery.toString());
		query.setParameter("cvePersona", idPersona);

		String resultado = (String) query.getSingleResult();

		return resultado;
	}

	@Override
	public Integer obtenerEdadPersona(Long idPersona) {
		Integer edad = -1;

		DitPersona ditPersona = em.find(DitPersona.class, idPersona);

		if (ditPersona != null && ditPersona.getFecNacimiento() != null) {
			edad = obtenerDiferenciaAnios(ditPersona.getFecNacimiento());
		}

		return edad;
	}
	
	private int obtenerDiferenciaAnios(Date fechaNacimiento) {
		Calendar calNacimiento = Calendar.getInstance();
		calNacimiento.setTime(fechaNacimiento);

		Calendar calFechaActual = Calendar.getInstance();

		int diferencia = calFechaActual.get(Calendar.YEAR) - calNacimiento.get(Calendar.YEAR);
		if (calNacimiento.get(Calendar.MONTH) > calFechaActual.get(Calendar.MONTH)
				|| (calNacimiento.get(Calendar.MONTH) == calFechaActual.get(Calendar.MONTH) 
				&& calNacimiento.get(Calendar.DATE) > calFechaActual.get(Calendar.DATE))) {
			diferencia--;
		}

		return diferencia;
	}

	private Fisica getPersonaSinCaracteresEspeciales (Fisica personaFisica) {
		Fisica personaCaracteres = null;

		String nombreCompleto = (personaFisica.getNombre() != null ? personaFisica.getNombre() : "") + " " +
		(StringUtils.isEmpty(personaFisica.getPrimerApellido()) ? "" : personaFisica.getPrimerApellido()) + " " +
		(StringUtils.isEmpty(personaFisica.getSegundoApellido()) ? "" : personaFisica.getSegundoApellido());

		log.debug("El nombre completo recibido es: " + nombreCompleto);
		
		boolean existe_n = nombreCompleto.contains("?") || nombreCompleto.contains("?");

		log.debug("Existe ?: " + existe_n + ", existe ?: " + existe_n);

		if(existe_n) {
		
		try {
			personaCaracteres = (Fisica) BeanUtils.cloneBean(personaFisica);
		} catch (IllegalAccessException e) {
			log.error(e);
		} catch (InstantiationException e) {
			log.error(e);
		} catch (InvocationTargetException e) {
			log.error(e);
		} catch (NoSuchMethodException e) {
			log.error(e);
		}

			
			
			if(!StringUtils.isEmpty(personaCaracteres.getNombre())) {
				personaCaracteres.setNombre(personaCaracteres.getNombre().replace("?", "#"));
				personaCaracteres.setNombre(personaCaracteres.getNombre().replace("?", "#"));

				log.debug("El nombre quedo de la siguiente manera: " + personaCaracteres.getNombre());
			}

			if(!StringUtils.isEmpty(personaCaracteres.getPrimerApellido())) {
				personaCaracteres.setPrimerApellido(personaCaracteres.getPrimerApellido().replace("?", "#"));
				personaCaracteres.setPrimerApellido(personaCaracteres.getPrimerApellido().replace("?", "#"));

				log.debug("El primer apellido quedo de la siguiente manera: " + personaCaracteres.getPrimerApellido());
			}

			if(!StringUtils.isEmpty(personaCaracteres.getSegundoApellido())) {
				personaCaracteres.setPrimerApellido(personaCaracteres.getSegundoApellido().replace("?", "#"));
				personaCaracteres.setPrimerApellido(personaCaracteres.getSegundoApellido().replace("?", "#"));

				log.debug("El segundo apellido quedo de la siguiente manera: " + personaCaracteres.getSegundoApellido());
			}
		}
		
		return personaCaracteres;
	}

	@Override
	public void registrarFiel(Persona persona) {
		Long idPersona = persona.getIdPersona();
		
		if(persona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA)){
			
			Fisica pFisica = (Fisica)persona;
			DitPersonaFisica pf = null;
					
			if(pFisica.getCveFisica()!=null){
				pf = this.em.find(DitPersonaFisica.class, ((Fisica)persona).getCveFisica());
			}else{
				try{
                    Long idPersonaFisica = personaFisicaServiceBusiness.obtenerIDPersonaFisicaEscVirtual(idPersona);
				    Query query=this.em.createQuery("select pf from DitPersonaFisica pf  where pf.cveIdPersonaFisica = "+idPersonaFisica);
					pf = (DitPersonaFisica)query.getSingleResult();
				}catch(NoResultException nre){
					log.error("No se encontr� persona f�sica para la persona, se insertar� la persona fisica");
					DitPersona ditPersona = this.em.find(DitPersona.class, idPersona);
					pf = new DitPersonaFisica();
					pf.setDitPersona(ditPersona);
					pf.setRfc(ditPersona.getRfc());
					pf.setFecRegistroAlta(new Date());
					this.em.persist(pf);
					if(persona instanceof Fisica)
						((Fisica)persona).setCveFisica(pf.getCveIdPersonaFisica());
				}catch(PersonaFisicaNoEncontradaException nre){
                    log.error("No se encontr� persona f�sica para la persona, se insertar� la persona fisica");
                    DitPersona ditPersona = this.em.find(DitPersona.class, idPersona);
                    pf = new DitPersonaFisica();
                    pf.setDitPersona(ditPersona);
                    pf.setRfc(ditPersona.getRfc());
                    pf.setFecRegistroAlta(new Date());
                    this.em.persist(pf);
                    if(persona instanceof Fisica)
                        ((Fisica)persona).setCveFisica(pf.getCveIdPersonaFisica());
                }
			}
			if(persona.getFiel()!=null){
				if(pf.getDitDatosCertificadoFiel()!=null){
					pf.getDitDatosCertificadoFiel().setFecFinVigencia(persona.getFiel().getFechaValidaFin());
					pf.getDitDatosCertificadoFiel().setFecIniVigencia(persona.getFiel().getFechaValidaInicio());
					pf.getDitDatosCertificadoFiel().setNumSerial(persona.getFiel().getClaveSerial());
					pf.getDitDatosCertificadoFiel().setFecRegistroActualizado(Calendar.getInstance().getTime());
				}else{
					DitDatosCertificadoFiel ditDatosCertFiel = new DitDatosCertificadoFiel();
					ditDatosCertFiel.setDitPersonaFisica(pf);
					ditDatosCertFiel.setFecIniVigencia(persona.getFiel().getFechaValidaInicio());
					ditDatosCertFiel.setFecFinVigencia(persona.getFiel().getFechaValidaFin());
					ditDatosCertFiel.setNumSerial(persona.getFiel().getClaveSerial());
					ditDatosCertFiel.setFecRegistroAlta(Calendar.getInstance().getTime());
					this.em.persist(ditDatosCertFiel);
				}				
			}
			
		}else{
			DitPersonaMoral pm = this.em.find(DitPersonaMoral.class, idPersona);
			if(persona.getFiel()!=null){
				if(pm.getDitDatosCertificadoFiel()!=null){
					pm.getDitDatosCertificadoFiel().setFecFinVigencia(persona.getFiel().getFechaValidaFin());
					pm.getDitDatosCertificadoFiel().setFecIniVigencia(persona.getFiel().getFechaValidaInicio());
					pm.getDitDatosCertificadoFiel().setNumSerial(persona.getFiel().getClaveSerial());
					pm.getDitDatosCertificadoFiel().setFecRegistroActualizado(Calendar.getInstance().getTime());
				}else{
					DitDatosCertificadoFiel ditDatosCertFiel = new DitDatosCertificadoFiel();
					ditDatosCertFiel.setDitPersonaMoral(pm);
					ditDatosCertFiel.setFecIniVigencia(persona.getFiel().getFechaValidaInicio());
					ditDatosCertFiel.setFecFinVigencia(persona.getFiel().getFechaValidaFin());
					ditDatosCertFiel.setNumSerial(persona.getFiel().getClaveSerial());
					ditDatosCertFiel.setFecRegistroAlta(Calendar.getInstance().getTime());
					this.em.persist(ditDatosCertFiel);
				}				
			}			
		}
		
		
	}

	/** 
	 * @Proyecto: GPersona
	 * @Archivo: PersonaEntity.java
	 * Funcionalidad: Metodo original para obtiener los datos de la tabla DIT_DATOS_CERTIFICADO_FIEL
	 * asociados a la persona, este metodo busca por medio del cve_id_persona
	 * @Motivo del cambio: Atenccion a la incidencia 4946384 / INC1052861 
	 * @Fecha: 22/06/2022
	 */
	@Override
	public Fiel obtenerDatosFielVersionOriginal(Persona persona) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select fiel from DitDatosCertificadoFiel fiel ");
		if(persona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA)){
			sql.append(" join fiel.ditPersonaFisica pf ");
			sql.append(" join  pf.ditPersona persona ");
			sql.append(" where persona.cveIdPersona = ").append(persona.getIdPersona());
			sql.append(" and fiel.fecRegistroBaja is null ");
		}else if (persona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_MORAL)){
			sql.append(" join fiel.ditPersonaMoral pm ");
			sql.append(" where pm.cveIdPersonaMoral = ").append(persona.getIdPersona());
			sql.append(" and fiel.fecRegistroBaja is null ");
		}
		Query query = this.em.createQuery(sql.toString());
		DitDatosCertificadoFiel ditFiel =null;
		try{
			ditFiel = (DitDatosCertificadoFiel)query.getSingleResult();
		}catch(NoResultException nre){
			System.err.println("No existen datos de fiel para la persona: "+persona.getIdPersona());
			return null;
		}
		Fiel fiel = new Fiel();
		fiel.setClaveSerial(ditFiel.getNumSerial());
		fiel.setFechaValidaFin(ditFiel.getFecFinVigencia());
		fiel.setFechaValidaInicio(ditFiel.getFecIniVigencia());
		fiel.setNombreCompleto(obtenerNombrePersona(persona));
		String rfc = ditFiel.getDitPersonaFisica()!=null ? ditFiel.getDitPersonaFisica().getRfc() :
			ditFiel.getDitPersonaMoral()!=null ? ditFiel.getDitPersonaMoral().getRfc() : null;
		fiel.setRfcAsociado(rfc);
		String curp =  ditFiel.getDitPersonaFisica()!=null ? 
				ditFiel.getDitPersonaFisica().getDitPersona().getCurp() : null;
		fiel.setCurpFiel(curp);
		
		return fiel;
	}
	
	/** 
	 * @Proyecto: GPersona
	 * @Archivo: PersonaEntity.java
	 * Funcionalidad: Obtiene los datos de la tabla DIT_DATOS_CERTIFICADO_FIEL
	 * asociados a la persona, este metodo busca por medio del cve_id_persona_fisica
	 * @Motivo del cambio: Atenccion a la incidencia 4946384 / INC1052861 
	 * @Fecha: 22/06/2022
	 */
	@Override
	public Fiel obtenerDatosFiel(Persona persona) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select fiel from DitDatosCertificadoFiel fiel ");
		if(persona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA) && persona.getIdPersonaFisica() != null){
			System.out.println("Validacion de persona fisica: "+persona.getIdPersonaFisica() + persona.getIdPersona());
			System.out.println("Validacion de persona fisica: "+persona.getIdPersonaFisica() + persona.getIdPersona());
			sql.append(" join fiel.ditPersonaFisica pf ");
			sql.append(" where pf.cveIdPersonaFisica = ").append(persona.getIdPersonaFisica());
			sql.append(" and fiel.fecRegistroBaja is null ");
		}else if (persona.getTipoPersona().getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_MORAL)){
			System.out.println("Validacion de persona moral: "+persona.getIdPersonaFisica() + persona.getIdPersona());
			sql.append(" join fiel.ditPersonaMoral pm ");
			sql.append(" where pm.cveIdPersonaMoral = ").append(persona.getIdPersona());
			sql.append(" and fiel.fecRegistroBaja is null ");
		}
		

		Query query = this.em.createQuery(sql.toString());
		System.out.println("Este es el query para obtener la persona fisica:" + query);
		DitDatosCertificadoFiel ditFiel =null;
		try{
			ditFiel = (DitDatosCertificadoFiel)query.getSingleResult();
			System.out.println("Datos de la ,consulta FIEL" + ditFiel);
		}catch(NoResultException nre){
			System.err.println("No existen datos de fiel para la persona: "+persona.getIdPersona());
			return null;
		}
		Fiel fiel = new Fiel();
		fiel.setClaveSerial(ditFiel.getNumSerial());
		fiel.setFechaValidaFin(ditFiel.getFecFinVigencia());
		fiel.setFechaValidaInicio(ditFiel.getFecIniVigencia());
		fiel.setNombreCompleto(obtenerNombrePersona(persona));
		String rfc = ditFiel.getDitPersonaFisica()!=null ? ditFiel.getDitPersonaFisica().getRfc() :
			ditFiel.getDitPersonaMoral()!=null ? ditFiel.getDitPersonaMoral().getRfc() : null;
		fiel.setRfcAsociado(rfc);
		String curp =  ditFiel.getDitPersonaFisica()!=null ? 
				ditFiel.getDitPersonaFisica().getDitPersona().getCurp() : null;
		fiel.setCurpFiel(curp);
		System.out.println("Datos FIEL  a mandar...." + fiel);
		return fiel;
	}

	@Override
	public void actualizarIndAcreditado(Persona persona) {
		if(persona instanceof Fisica){
			if(persona.getIdPersona()!=null){
				DitPersona ditPersona = em.find(DitPersona.class, persona.getIdPersona());
				
				if(ditPersona.getDitPersonaFisicas()!=null && !ditPersona.getDitPersonaFisicas().isEmpty()){
					DitPersonaFisica ditPersonaFisica = ditPersona.getDitPersonaFisicas().get(0);
					ditPersonaFisica.setIndAcreditado(new BigDecimal("1"));
				}
			}
		}
		else if(persona instanceof Moral){
			if(persona.getIdPersona()!=null){
				DitPersonaMoral ditPersonaMoral=em.find(DitPersonaMoral.class, persona.getIdPersona());
				ditPersonaMoral.setIndAcreditado(new BigDecimal("1"));
			}
		}
	}
	
	@Override
	public List<Fisica> buscarEnPersonaYGrupoFamiliar(final String curp, Long idAsignacionNss){
		
		List<Fisica> listaFisica = null;
		
		Criteria queryParentescoEstado = this.getSession().createCriteria(DitGrupoFamiliar.class);
		queryParentescoEstado.createAlias("ditPersona", "der");
		queryParentescoEstado.createAlias("ditAsignacionNss", "nss");
		
		queryParentescoEstado.add(Restrictions.eq("der.curp", curp));
		queryParentescoEstado.add(Restrictions.eq("nss.cveIdAsignacionNss", idAsignacionNss));
		
		
		@SuppressWarnings("unchecked")
		List<DitGrupoFamiliar> encontrados = queryParentescoEstado.list();
		
		if( encontrados.size() > 0 ){
			
			listaFisica = new ArrayList<Fisica>();
			
			for(DitGrupoFamiliar grupo : encontrados){
				listaFisica.add(this.personaFisicaServiceUtility.transformarAModelo(grupo.getDitPersona()));
			}
			
		}
		
		
		return listaFisica;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int totalRegistroPersonaFMPorRFC(Persona persona) {
		int total = 0;
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT p FROM ");
		if (persona.getTipoPersona().getIdTipoPersona().equals(TipoPersonaEnum.FISICA.getId())) {
			sql.append(" DitPersonaFisica p ");				
		}else {
			sql.append(" DitPersonaMoral p ");			
		}
		sql.append(" WHERE p.rfc = :rfcFM ");		
		Query query = this.em.createQuery(sql.toString());
		query.setParameter("rfcFM", persona.getRfc());
		if (persona.getTipoPersona().getIdTipoPersona().equals(TipoPersonaEnum.FISICA.getId())) {
			List<DitPersonaFisica> lista = query.getResultList();
			if(!CollectionUtils.isEmpty(lista)){
				total = lista.size();
			}
		}else{
			List<DitPersonaMoral> lista = query.getResultList();
			if(!CollectionUtils.isEmpty(lista)){
				total = lista.size();
			}
		}		
		return total;
	}

	@Override
	public boolean registradoConFiel(long idPersona) {
		boolean fielActiva = false;
		StringBuffer getTrueFalseFiel = new StringBuffer();
		getTrueFalseFiel.append("SELECT COUNT(S.CVE_ID_SOLICITUD) AS TOTAL "
				+ "FROM DIT_SOLICITUD S "
				+ "INNER JOIN DIT_TRAMITE T ON T.CVE_ID_SOLICITUD=S.CVE_ID_SOLICITUD "
				+ "INNER JOIN DIT_TRAMITE_PERSONA_FISICA TPF ON TPF.CVE_ID_TRAMITE=T.CVE_ID_TRAMITE "
				+ "WHERE S.CVE_ID_TIPO_SOLICITUD = '18' "
				+ "AND S.CVE_ID_ESTADO_SOLICITUD='2' "
				+ "AND T.CVE_ID_TIPO_TRAMITE='76' ");
		getTrueFalseFiel.append("AND TPF.CVE_ID_PERSONA = ").append(idPersona);
		
		this.log.info(" -- Class PersonaEntity: "+idPersona);
		this.log.info(" -- Implements...return");
		try{
			BigDecimal result = (BigDecimal) this.em.createNativeQuery(getTrueFalseFiel.toString()).getSingleResult();
			this.log.info(" -- FIEL: "+result.intValue());
			if(result.intValue() > 0){
				this.log.info(" -- Persona con Fiel encontrada: "+idPersona);
				fielActiva = true;
			}
		}catch(Exception nre){
			System.err.println("No existen datos de fiel para la persona con id: "+idPersona);
			nre.printStackTrace();
		}
		return fielActiva;
	}

	@Override
	public Object actualizaFechaBajaEntidad(Object entidad) {
		// TODO Auto-generated method stub
		if(entidad instanceof DitPersonaFisica){
			DitPersonaFisica fisica=(DitPersonaFisica) entidad;
			DitPersonaFisica co=em.find(DitPersonaFisica.class, fisica.getCveIdPersonaFisica());
			co.setFecRegistroBaja(fisica.getFecRegistroBaja());
			entidad=em.merge(co);			
		}else if(entidad instanceof DitPersonaMoral){
			DitPersonaMoral moral=(DitPersonaMoral) entidad;
			DitPersonaMoral co=em.find(DitPersonaMoral.class, moral.getCveIdPersonaMoral());
			co.setFecRegistroBaja(moral.getFecRegistroBaja());
			entidad=em.merge(co);
		}else if(entidad instanceof DitPersonaMDomFiscal){
			DitPersonaMDomFiscal persona=(DitPersonaMDomFiscal) entidad;
			DitPersonaMDomFiscal co=em.find(DitPersonaMDomFiscal.class, persona.getCveIdPmdomFiscal());
			co.setFecRegistroBaja(persona.getFecRegistroBaja());
			entidad=em.merge(co);
		}else if(entidad instanceof DitPersonaFDomFiscal){
			DitPersonaFDomFiscal persona=(DitPersonaFDomFiscal) entidad;
			DitPersonaFDomFiscal co=em.find(DitPersonaFDomFiscal.class,persona.getCveIdPfdomFiscal());
			co.setFecRegistroBaja(persona.getFecRegistroBaja());
			entidad=em.merge(co);
		}else if(entidad instanceof DitDomicilioSat){
			DitDomicilioSat persona=(DitDomicilioSat) entidad;
			DitDomicilioSat co=em.find(DitDomicilioSat.class,persona.getCveIdDomicilio());
			co.setFecRegistroBaja(persona.getFecRegistroBaja());
			entidad=em.merge(co);
		}else if(entidad instanceof DitPersonafContacto){
			DitPersonafContacto persona=(DitPersonafContacto) entidad;
			DitPersonafContacto co=em.find(DitPersonafContacto.class,persona.getCveIdPersonafContacto());
			co.setFecRegistroBaja(persona.getFecRegistroBaja());
			entidad=em.merge(co);			
		}else if(entidad instanceof DitPersonamContacto){
			DitPersonamContacto persona=(DitPersonamContacto) entidad;
			DitPersonamContacto co=em.find(DitPersonamContacto.class,persona.getCveIdPersonamContacto());
			co.setFecRegistroBaja(persona.getFecRegistroBaja());
			entidad=em.merge(co);
		}else if(entidad instanceof DitFormaContacto){
			DitFormaContacto persona=(DitFormaContacto) entidad;
			DitFormaContacto co=em.find(DitFormaContacto.class,persona.getCveIdFormaContacto());
			co.setFecRegistroBaja(persona.getFecRegistroBaja());
			entidad=em.merge(co);
		}
		return entidad;
	}
	
		@Override
    public Fisica obtenerPersonaPorId(Long idPersona) {

        DitPersona persona = null;
		Fisica fisica = null;
        try {
            Criteria criteria = this.getSession().createCriteria(DitPersona.class);
            criteria.add(Restrictions.eq("cveIdPersona", idPersona));
            persona = (DitPersona) criteria.uniqueResult();
        } catch (Exception e) {
            log.error("Error al realizar la consulta de la persona con id:" + idPersona, e);
        }
		
		
		return personaFisicaServiceUtility.transformarAModelo(persona);
         
    }
	
	@Override
    public Fisica obtenerInformacionDatosAsegurado(String nss, String curp) {

		DitPersona personaBD = null;
        log.debug("Entrando a obtenerDatosAsegurado");
        Criteria persona = this.getSession().createCriteria(DitPersona.class);
        persona.add(Restrictions.eq("curp", curp));
        persona.add(Restrictions.isNull("fecRegistroBaja"));
        Criteria asignacion = persona.createCriteria("ditAsignacionNsses");
        asignacion.add(Restrictions.eq("numNss", nss));
        asignacion.add(Restrictions.eq("indActivo", BigDecimal.ONE));
        asignacion.add(Restrictions.isNull("fecRegistroBaja"));
        personaBD = (DitPersona) persona.uniqueResult();
		
		return personaFisicaServiceUtility.transformarAModelo(personaBD);
    }
	
	
	@Override
	public Fisica getFisicaBySolicitudRegistroPortalConFiel(String curp) throws Exception{
		boolean fielActiva = false;
		StringBuffer queryConsultaPersona = new StringBuffer();
		
		queryConsultaPersona.append(" select * from ( "
		+ "SELECT TPF.CVE_ID_PERSONA " 
		 +" FROM DIT_TRAMITE_PERSONA_FISICA TPF " 
		 +" INNER JOIN DIT_TRAMITE T ON TPF.CVE_ID_TRAMITE = T.CVE_ID_TRAMITE "
		+" INNER JOIN DIC_TIPO_TRAMITE TT ON T.CVE_ID_TIPO_TRAMITE = TT.CVE_ID_TIPO_TRAMITE "
		+" WHERE TT.CVE_ID_TIPO_TRAMITE = 76 AND T.CVE_ID_ESTADO_TRAMITE = 2 "
		+" AND TPF.CVE_ID_PERSONA IN( "   
		+"   SELECT CVE_ID_PERSONA FROM MGPBDTU9X.DIT_LLAVE_PERSONA WHERE CURP = :curp "
		+" ) ORDER BY TPF.CVE_ID_TRAMITE DESC " 
		+" ) where  ROWNUM = 1");
		
		this.log.debug(" -- el query a ejecutar es : "+queryConsultaPersona);
		SQLQuery sqlQuery = getSession().createSQLQuery(queryConsultaPersona.toString());
		sqlQuery.setParameter("curp", curp);
		try{

			BigDecimal cveIdPersona = (BigDecimal) sqlQuery.uniqueResult();
			
			if(cveIdPersona != null && cveIdPersona.intValue() >0 ){
				this.log.info(" -- Persona con Fiel encontrada: "+ cveIdPersona);
				return this.obtenerPersonaPorId(cveIdPersona.longValue());
			}
			
		}catch(Exception nre){
			log.error("ocurrio un error al consultar a la persona con registros de usuario", nre);
			throw nre;
		}
		return null;
	}
	
}
