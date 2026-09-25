/**
 *  
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Lucio Duran Silva
 *  @Proyecto: delta
 *  @Archivo:SolicitudPersonaEntity.java
 *  @Paquete:mx.gob.imss.ctirss.gestionpersonas.servicios.entity
 *  @Fecha:22/02/2012
 */
package mx.gob.imss.ctirss.gestionpersonas.servicios.entity;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.xml.bind.JAXBException;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.individuo.util.Utilerias;
import mx.gob.imss.ctirss.delta.gestion.individuo.util.WebserviceTools;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.CalificacionEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EstadoSolicitud;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DicRazonResultado;
import mx.gob.imss.ctirss.delta.persistence.DitDetalleTramite;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaMoral;
import mx.gob.imss.ctirss.delta.persistence.DitSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;
import mx.gob.imss.ctirss.delta.persistence.DitTramitePersonaFisica;
import mx.gob.imss.ctirss.delta.persistence.DitTramitePersonaFisicaPK;
import mx.gob.imss.ctirss.delta.persistence.DitTramitePersonaMoral;
import mx.gob.imss.ctirss.delta.persistence.DitTramitePersonaMoralPK;
import mx.gob.imss.ctirss.delta.persistence.DitUmfTurno;
import mx.gob.imss.ctirss.delta.persistence.DitUmfTurnoPK;
import mx.gob.imss.ctirss.gestionpersonas.servicios.utility.DomiciliosServiceUtilityLocal;

import org.apache.commons.lang.StringUtils;

@Stateless
public class SolicitudPersonaEntity extends AbstractServiceEntity implements SolicitudPersonaEntityLocal {
	
	@EJB
    private DomiciliosServiceUtilityLocal domiciliosServiceUtilityLocal;

    public Solicitud modificar(final Solicitud solicitud) {

        DitSolicitud ditSolicitud = em.find(DitSolicitud.class, solicitud.getIdSolicitud());

        if (ditSolicitud != null) {
            //ACTUALIZAMOS LA SOLICITUD
            Date fechaActualizacion = new Date();
            ditSolicitud.setDicEstadoSolicitud(new DicEstadoSolicitud(solicitud.getIdEstadoSolicitud()));
            ditSolicitud.setFecRegistroActualizado(fechaActualizacion);

            for (Tramite tramite : solicitud.getTramite()) {
                DitTramite ditTramite = em.find(DitTramite.class, tramite.getIdTramite());
                em.detach(ditTramite);
                ditTramite = em.find(DitTramite.class, tramite.getIdTramite());
                ditTramite.setFecRegistroActualizado(fechaActualizacion);
                ditTramite.setDitSolicitud(ditSolicitud);
                
                String sXml= "";
                try {
                	//GENERAMOS EL XML DEL TRAMITE
                    sXml = WebserviceTools.getStringXml(SolicitudConversor.convertirModelToXml(tramite));
                } catch (JAXBException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
                DitDetalleTramite ditDetalleTramite = ditTramite.getDitDetalleTramite();
                ditDetalleTramite.setFecRegistroActualizado(fechaActualizacion);
                ditDetalleTramite.setFecRegistroAlta(fechaActualizacion);
                ditDetalleTramite.setRefDatosTramiteXml(sXml);
                ditDetalleTramite.setDitTramite(ditTramite);
                ditDetalleTramite.setCveIdTramite(ditTramite.getCveIdTramite());
                if (Utilerias.isNotBlank(tramite.getIdRazonResultado())) {
                    final DicRazonResultado dicRazonResultado = new DicRazonResultado();
                    dicRazonResultado.setCveIdRazonResultado(tramite.getIdRazonResultado());
                    dicRazonResultado.setDesRazonResultado(tramite.getDesRazonResultado());
                    ditTramite.setDicRazonResultado(dicRazonResultado);
                }
                
                // 191807 231012
                // Ahora se genera la relacion entre un tramite y la persona (ya sea fisica o moral), asi como ya se hace en Asegurados
                crearRelacionTramitePersona(tramite);

            }

            em.flush();
        }

        return solicitud;
    }

    public Solicitud alta(final Solicitud solicitud) {
    	
        Solicitud solicitudNueva = null;
        final Date fechaAlta = new Date();

        // INICIALIZA EL ENTITY SOLICITUD
        final DitSolicitud ditSolicitud = new DitSolicitud();
        ditSolicitud.setRefFolio("0");
                
        //ditSolicitud.setFecRegistroAlta(new java.sql.Timestamp(fechaAlta.getTime()) );
        ditSolicitud.setFecRegistroAlta(fechaAlta );
        ditSolicitud.setFecRegistroActualizado(fechaAlta);
        ditSolicitud.setFecSolicitud(fechaAlta);

        //INICIALIZA EL TURNO
        DitUmfTurno ditUmfTurno = new DitUmfTurno();
        DitUmfTurnoPK ditUmfTurnoPK = new DitUmfTurnoPK();
        ditUmfTurnoPK.setCveIdTurno(1);
        ditUmfTurnoPK.setCveIdUmf(1);
        ditUmfTurno.setId(ditUmfTurnoPK);

        //OBTIENE EL CATALOGO DE TURNOS
        DitUmfTurno catalogoTurnoEncontrado = em.find(DitUmfTurno.class, ditUmfTurnoPK);
        ditSolicitud.setDitUmfTurno(catalogoTurnoEncontrado);
        ditSolicitud.setDicEstadoSolicitud(new DicEstadoSolicitud(solicitud.getIdEstadoSolicitud()));

        em.persist(ditSolicitud);
        
        //RECUPERAMOS LA LLAVE GENERADA
        solicitud.setIdSolicitud(ditSolicitud.getCveIdSolicitud());
        solicitud.setFechaRegistro(fechaAlta);
        String sXml = null;
        int tramiteIndex = 0;
        
        for (Tramite tramite : solicitud.getTramite()) {
            DitTramite ditTramite = TramiteConversor.fromModelToEntity(tramite);
            ditTramite.setFecRegistroAlta(fechaAlta);
            ditTramite.setFecRegistroActualizado(fechaAlta);
            ditTramite.setDitSolicitud(ditSolicitud);
            em.persist(ditTramite);
            
            //OBTENEMOS EL ID-TRAMITE GENERADO
            tramite.setIdTramite(ditTramite.getCveIdTramite());
            try {
            	//GENERAMOS EL XML DEL TRAMITE
                sXml = WebserviceTools.getStringXml(SolicitudConversor.convertirModelToXml(tramite));
            } catch (JAXBException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            DitDetalleTramite ditDetalleTramite = new DitDetalleTramite();
            ditDetalleTramite.setFecRegistroActualizado(fechaAlta);
            ditDetalleTramite.setFecRegistroAlta(fechaAlta);
            ditDetalleTramite.setRefDatosTramiteXml(sXml);
            ditDetalleTramite.setDitTramite(ditTramite);
            ditDetalleTramite.setCveIdTramite(ditTramite.getCveIdTramite());
            em.persist(ditDetalleTramite);

            solicitud.getTramite().get(tramiteIndex++).setIdTramite(ditTramite.getCveIdTramite());
            
        }
        em.flush();
        solicitudNueva = solicitud;

        return solicitudNueva;
    }

    public List<Solicitud> getSolicitudesVencidas() {
        List<Solicitud> listaSolicitud = null; // NOPMD
        try {
            final Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, -2);
            
	        String  strQuery="SELECT s FROM DitSolicitud s WHERE s.fecSolicitud <= :hoyMenosDosDias AND s.dicEstadoSolicitud.cveIdEstadoSolicitud = " + EstadoSolicitudEnum.REGISTRADA.getValor();
            
            final TypedQuery<DitSolicitud> query = em.createQuery(strQuery, DitSolicitud.class).setParameter("hoyMenosDosDias", calendar.getTime());
            final List<DitSolicitud> resultado = query.getResultList();

            if (resultado != null) {
                listaSolicitud = new LinkedList<Solicitud>();
                for (DitSolicitud ditSolicitud : resultado) {
                    final Solicitud solicitud = new Solicitud();
                    solicitud.setIdSolicitud(ditSolicitud.getCveIdSolicitud());
                    solicitud.setNumSolicitud(ditSolicitud.getRefFolio());
                    listaSolicitud.add(solicitud);
                }
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return listaSolicitud;
    }

    public List<Solicitud> getSolicitudesRegistradas() {
        List<Solicitud> listaSolicitud = null; // NOPMD
        try {

            final TypedQuery<DitSolicitud> query = em.createNamedQuery("DitSolicitud.getSolicitudesRegistradas", DitSolicitud.class);
            final List<DitSolicitud> resultado = query.getResultList();

            if (resultado != null) {
                listaSolicitud = new LinkedList<Solicitud>();
                for (DitSolicitud ditSolicitud : resultado) {
                    final Solicitud solicitud = new Solicitud();
                    solicitud.setIdSolicitud(ditSolicitud.getCveIdSolicitud());
                    solicitud.setNumSolicitud(ditSolicitud.getRefFolio());
                    listaSolicitud.add(solicitud);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaSolicitud;
    }

    public Solicitud getSolicitud(final Long idSolicitud) throws SolicitudNoEncontradaException {
    	this.log.debug("getSolicitud ..." + idSolicitud);
        Solicitud solicitudRespuesta = null; // NOPMD
        if (idSolicitud != null) {
            try {
                final DitSolicitud ditSolicitud = em.find(DitSolicitud.class, idSolicitud);
                this.log.debug("Solicitud encontrada para el reporte ..." + ditSolicitud);
                if (ditSolicitud != null) {

                    if (ditSolicitud.getDitTramites() != null && !ditSolicitud.getDitTramites().isEmpty() && StringUtils.isNotBlank(ditSolicitud.getDitTramites().get(0).getDitDetalleTramite().getRefDatosTramiteXml())) {
                        //ASIGNAMOS SOLICITUD
                        solicitudRespuesta = new Solicitud();
                        solicitudRespuesta.setIdSolicitud(ditSolicitud.getCveIdSolicitud());
                        solicitudRespuesta.setIdEstadoSolicitud(ditSolicitud.getDicEstadoSolicitud().getCveIdEstadoSolicitud());
                        solicitudRespuesta.setFechaRegistro(ditSolicitud.getFecSolicitud());
                        solicitudRespuesta.setDesEstadoSolicitud(ditSolicitud.getDicEstadoSolicitud().getDesEstadoSolicitud());

                        //ASIGNAMOS TRAMITES
                        if (ditSolicitud.getDitTramites() != null) {
                            for (DitTramite ditTramite : ditSolicitud.getDitTramites()) {
                                mx.gob.imss.ctirss.delta.model.xml.solicitud.Tramite tramiteXml = (mx.gob.imss.ctirss.delta.model.xml.solicitud.Tramite) WebserviceTools.getXml(ditTramite.getDitDetalleTramite().getRefDatosTramiteXml(), mx.gob.imss.ctirss.delta.model.xml.solicitud.Tramite.class);
                                solicitudRespuesta.getTramite().add(SolicitudConversor.convertirXmlToModel(tramiteXml));
                            }
                        }
                        solicitudRespuesta.setIdEstadoSolicitud(ditSolicitud.getDicEstadoSolicitud().getCveIdEstadoSolicitud());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                this.log.error(e.getMessage(), e);
            }
        }
        if (solicitudRespuesta == null) {
        	this.log.debug("la solicitud de respuesta es nula ...");
            throw new SolicitudNoEncontradaException(idSolicitud);
        }
        return solicitudRespuesta;
    }
    
	/**
	 * 191807 251012
	 * Metodo encargado de generar una relacion entre una persona y un tramite. Pero, solo se hara si la persona fue dada de alta en BD, o sea, 
	 * si se localizo en SAT o RENAPO no importando si es por ventanilla o por internet. En caso de tener estatus No Validado (4) no se podra hacer 
	 * esta relacion
	 * @param tramite
	 */
	void crearRelacionTramitePersona(Tramite tramite) {
		
		boolean relacionTamitePersonaExitosa = false;
		long idPersona = 0, idTramite = tramite.getIdTramite();
		
		try{
	        // El valor regresado por las funciones, 'relacionTamitePersonaExitosa', en realidad indica si ya existe previamente la relacion entre el tramite y 
			// la persona, o sea:
	        // true		=>	ya existe la relacion y por lo tanto ya no se creo
	        // false	=>	no existe la relacion y por lo tanto se creo una		
			if(tramite.getPersonaFisica() != null){
				// Si la calificacion es diferente a No Validado(4) quiere decir que sí se dio de alta a la persona en BD y por lo tanto se puede crear la relacion
				// con su tramite
				if(tramite.getPersonaFisica().getPersonaCalificaciones() != null && (tramite.getPersonaFisica().getPersonaCalificaciones().get(0).getCalificacion().getIdCalificacion().longValue() != CalificacionEnum.NO_VALIDADO.getCodigo().longValue())){
					idPersona = tramite.getPersonaFisica().getIdPersona();
					relacionTamitePersonaExitosa = crearRelacionTramitePersonaFisica(tramite);
					
					// Se imprime el resultado de la relacion entre tramite y persona
			        if(!relacionTamitePersonaExitosa){
			        	log.debug("Se genero correctamente la relacion entre la persona fisica: " + idPersona + " y el tramite: " + idTramite);
			        }else{
			        	log.debug("NO se genero la relacion entre la persona fisica: " + idPersona + " y el tramite: " + idTramite + " porque ya existia previamente");
			        }
				}else{
					log.debug("Dado que no se genero a la persona fisica en BD por tener calificacion No Validado(4), no se hara la relacion entre el tramite y la persona ya que esta ultima no tiene aun id. Se deben terminar los tramites No Validados(4) para crearla");
				}
			}else if(tramite.getPersonaMoral() != null){
				// Si la calificacion es diferente a No Validado(4) quiere decir que sí se dio de alta a la persona en BD y por lo tanto se puede crear la relacion
				// con su tramite
				if(tramite.getPersonaMoral().getPersonaCalificaciones() != null && (tramite.getPersonaMoral().getPersonaCalificaciones().get(0).getCalificacion().getIdCalificacion().longValue() != CalificacionEnum.NO_VALIDADO.getCodigo().longValue())){
					idPersona = tramite.getPersonaMoral().getIdPersona();
					relacionTamitePersonaExitosa = crearRelacionTramitePersonaMoral(tramite);
					
					// Se imprime el resultado de la relacion entre tramite y persona
			        if(!relacionTamitePersonaExitosa){
			        	log.debug("Se genero correctamente la relacion entre la persona moral: " + idPersona + " y el tramite: " + idTramite);
			        }else{
			        	log.debug("NO se genero la relacion entre la persona moral: " + idPersona + " y el tramite: " + idTramite + " porque ya existia previamente");
			        }
				}else{
					log.debug("Dado que no se genero a la persona moral en BD por tener calificacion No Validado(4), no se hara la relacion entre el tramite y la persona ya que esta ultima no tiene aun id. Se deben terminar los tramites No Validados(4) para crearla");
				}
			}else{
				log.error("El tramite no contiene ni persona fisica ni persona moral... debe existir una de las dos");
			}
		}catch(Exception e){
			log.error("NO se genero la relacion entre la persona y el tramite: " + idTramite + " debido a la siguiente excepcion: " + e.getMessage());
		}
		
	}
    
	/**
	 * 191807 251012
	 * Metodo encargado de generar una relacion entre una persona fisica y un tramite (esto solo se hacia en asegurados...
	 * ... checar SolicitudEntity.generarRelacionTramitePersona)
	 * @param tramite
	 */
	boolean crearRelacionTramitePersonaFisica(Tramite tramite){
		
		Boolean existeRelacionTramitePersona = Boolean.FALSE;
		
		if(tramite.getPersonaFisica().getIdPersona() == null){
			log.info("No se guardara en tabla de relacion la persona fisica debido a que no existe la clave de la persona...");
		}else{
			
			DitPersona ditPersona = em.find(DitPersona.class, tramite.getPersonaFisica().getIdPersona());
			
			DitTramitePersonaFisica ditTramitePersonaFisica = new DitTramitePersonaFisica();
			ditTramitePersonaFisica.setDitPersona(ditPersona);
			
			DitTramitePersonaFisicaPK idTramitePersona = new DitTramitePersonaFisicaPK();
			idTramitePersona.setCveIdPersona(tramite.getPersonaFisica().getIdPersona());
			idTramitePersona.setCveIdTramite(tramite.getIdTramite());
			
			// Se comprueba que no exista previamente la relacion entre el tramite y la pesona. Si no existe, entonces se crea; sino pues no
			existeRelacionTramitePersona = em.find(DitTramitePersonaFisica.class, idTramitePersona) != null;
			
			if(!existeRelacionTramitePersona){
				ditTramitePersonaFisica.setId(idTramitePersona);
				em.persist(ditTramitePersonaFisica);
			}
			
		}
		
		return existeRelacionTramitePersona.booleanValue();

	}
	
	/**
	 * 191807 251012
	 * Metodo encargado de generar una relacion entre una persona moral y un tramite (esto solo se hacia en asegurados...
	 * ... checar SolicitudEntity.generarRelacionTramitePersona)
	 * @param tramite
	 */
	boolean crearRelacionTramitePersonaMoral(Tramite tramite){
		
		Boolean existeRelacionTramitePersonaMoral = Boolean.FALSE;
		
		if(tramite.getPersonaMoral().getIdPersona() == null){
			log.info("No se guardara en tabla de relacion la persona moral debido a que no existe la clave de la persona...");
		}else{
			
			DitPersonaMoral ditPersonaMoral = em.find(DitPersonaMoral.class, tramite.getPersonaMoral().getIdPersona());
			
			DitTramitePersonaMoral ditTramitePersonaMoral= new DitTramitePersonaMoral();
			ditTramitePersonaMoral.setDitPersonaMoral(ditPersonaMoral);
			
			DitTramitePersonaMoralPK idTramitePersonaMoral = new DitTramitePersonaMoralPK();
			idTramitePersonaMoral.setCveIdPersonaMoral(tramite.getPersonaMoral().getIdPersona());
			idTramitePersonaMoral.setCveIdTramite(tramite.getIdTramite());
			
			// Se comprueba que no exista previamente la relacion entre el tramite y la persona. Si no existe, entonces se crea; sino pues no
			existeRelacionTramitePersonaMoral = em.find(DitTramitePersonaMoral.class, idTramitePersonaMoral) != null;
			
			if(!existeRelacionTramitePersonaMoral){
				ditTramitePersonaMoral.setId(idTramitePersonaMoral);
				em.persist(ditTramitePersonaMoral);
			}
			
		}
		
		return existeRelacionTramitePersonaMoral.booleanValue();

	}
	
	@Override
    public List<String> consultarNombresPatrones(List<String> registroPatronal) {

        List<Object[]> tuples = new ArrayList<Object[]>();
        try {
            StringBuilder queryM = new StringBuilder();
			queryM.append(" SELECT  DPG.regPatron, DCM.numModalidad, PM.denominacionRazonSocial  FROM DitPatronGeneral DPG ");
            queryM.append(" JOIN DPG.ditPatronSujetoObligado DPSO");
            queryM.append(" JOIN DPSO.ditPersonaMoral PM ");
			queryM.append(" JOIN DPSO.dicModalidad DCM ");
            queryM.append(" WHERE DPG.regPatron IN ( :rpm ) ");
            Query morales = em.createQuery(queryM.toString());
            morales.setParameter("rpm", registroPatronal);
            tuples.addAll(morales.getResultList());

            StringBuilder queryF = new StringBuilder();
            queryF.append(" SELECT DPG2.regPatron, DCM.numModalidad, DP.nomNombre, DP.nomPrimerApellido, DP.nomSegundoApellido ");
            queryF.append(" FROM DitPatronGeneral DPG2 ");
            queryF.append(" JOIN DPG2.ditPatronSujetoObligado DPSO2 ");
            queryF.append(" JOIN DPSO2.ditPersonaFisica PF ");
            queryF.append(" JOIN PF.ditPersona DP ");
			queryF.append(" JOIN DPSO2.dicModalidad DCM ");
            queryF.append(" WHERE DPG2.regPatron IN ( :rpf ) ");
            Query fisicas = em.createQuery(queryF.toString());
            fisicas.setParameter("rpf", registroPatronal);
            tuples.addAll(fisicas.getResultList());

        } catch (NoResultException ge) {
            log.error("No se encontró registron patronal proporcionado" + ge);
        } catch (Exception nre) {
            log.error("No se encontró registron patronal proporcionado" + nre);
        }

        return domiciliosServiceUtilityLocal.armarRegistros(tuples);
    }
	
	public List<String> consultarNombrePatronPorRPYModalidad(String registroPatronal, String modalidad){
		List<Object[]> resulSet = new ArrayList<Object[]>();
        try {
            StringBuilder queryM = new StringBuilder();
			queryM.append(" SELECT DPG.regPatron, PM.denominacionRazonSocial  FROM DitPatronGeneral DPG ");
            queryM.append(" JOIN DPG.ditPatronSujetoObligado DPSO");
            queryM.append(" JOIN DPSO.ditPersonaMoral PM ");
			queryM.append(" JOIN DPSO.dicModalidad DCM ");
            queryM.append(" WHERE DPG.regPatron = :rpm  ");
            queryM.append(" AND DCM.numModalidad = :modalidad  ");
            Query morales = em.createQuery(queryM.toString());
            morales.setParameter("rpm", registroPatronal);
            morales.setParameter("modalidad", modalidad);
            resulSet.addAll(morales.getResultList());

            StringBuilder queryF = new StringBuilder();
            queryF.append(" SELECT DP.nomNombre, DP.nomPrimerApellido, DP.nomSegundoApellido ");
            queryF.append(" FROM DitPatronGeneral DPG2 ");
            queryF.append(" JOIN DPG2.ditPatronSujetoObligado DPSO2 ");
            queryF.append(" JOIN DPSO2.ditPersonaFisica PF ");
            queryF.append(" JOIN PF.ditPersona DP ");
			queryF.append(" JOIN DPSO2.dicModalidad DCM ");
			queryF.append(" WHERE DPG2.regPatron = :rpf  ");
			queryF.append(" AND DCM.numModalidad = :modalidad  ");
            Query fisicas = em.createQuery(queryF.toString());
            fisicas.setParameter("rpf", registroPatronal);
            fisicas.setParameter("modalidad", modalidad);
            resulSet.addAll(fisicas.getResultList());

        } catch (NoResultException ge) {
            log.error("No se encontró registro patronal proporcionado" + ge);
        } catch (Exception nre) {
            log.error("No se encontró registro patronal proporcionado" + nre);
        }

        return domiciliosServiceUtilityLocal.armarNombrePatron(resulSet);
	}

}
