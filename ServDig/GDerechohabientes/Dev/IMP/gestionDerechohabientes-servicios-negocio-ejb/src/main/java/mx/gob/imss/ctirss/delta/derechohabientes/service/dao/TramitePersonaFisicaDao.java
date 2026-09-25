/**
 * 
 */
package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.derechohabientes.service.bussiness.TramiteServiceLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.CircunscripcionForaneaParserServiceLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.CorreccionParserServiceLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.ProrrogaParserServiceLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.parser.RegistroParserServiceLocal;
import mx.gob.imss.ctirss.delta.derechohabientes.service.utility.interfaces.GestionDocumentalServiceRemote;
import mx.gob.imss.ctirss.delta.derechohabientes.util.DateUtils;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.TramiteParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.TramiteSimpleParser;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser.CertificadoSituacionCriticaParser;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser.ConstanciaEstudioParser;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser.DictamenIntegranteIncapacitadoParser;
import mx.gob.imss.ctirss.delta.gestion.documento.probatorio.utils.parser.ObstetricoParser;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.CabezaGrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Derechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.BeneficiarioSav005DTO;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.Sav005DTO;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.Sav007DTO;
import mx.gob.imss.ctirss.delta.model.derechohabientes.negocio.dto.Sav017DTO;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.enums.CaracterEnum;
import mx.gob.imss.ctirss.delta.model.enums.EstadoDerechohabienteEnum;
import mx.gob.imss.ctirss.delta.model.enums.SexoEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaInteresadaSolEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramiteEnum;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCircunscripcionForanea;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteCorreccionDerechohabiente;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteProrroga;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteRegistroDerechohabiente;
import mx.gob.imss.ctirss.delta.persistence.DicTipoTramite;
import mx.gob.imss.ctirss.delta.persistence.DitCertificadoSitCritica;
import mx.gob.imss.ctirss.delta.persistence.DitCircunscripcionForanea;
import mx.gob.imss.ctirss.delta.persistence.DitCorreccionDatoDerechohab;
import mx.gob.imss.ctirss.delta.persistence.DitDetalleTramite;
import mx.gob.imss.ctirss.delta.persistence.DitDictBeneficiarioInca;
import mx.gob.imss.ctirss.delta.persistence.DitDocumentacionTramite;
import mx.gob.imss.ctirss.delta.persistence.DitDocumentoProbatorio;
import mx.gob.imss.ctirss.delta.persistence.DitObstetrico;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitProrroga;
import mx.gob.imss.ctirss.delta.persistence.DitRegistroDerechohabiente;
import mx.gob.imss.ctirss.delta.persistence.DitSolicitudFirmaDigital;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;
import mx.gob.imss.ctirss.delta.persistence.DitTramitePersonaFisica;
import mx.gob.imss.ctirss.delta.persistence.DitTramitePersonaFisicaPK;
import mx.gob.imss.distss.digital.jaxb.util.JaxbUtil;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ghdolores
 * 
 */
@Stateless(name = "tramitePersonaFisicaDao", mappedName = "tramitePersonaFisicaDao")
public class TramitePersonaFisicaDao extends AbstractServiceEntity implements TramitePersonaFisicaDaoLocal {

	@EJB(name = "correccionDatoDerechohabienteParser") CorreccionParserServiceLocal correccionParser;
	@EJB(name = "circunscripcionForaneaService") CircunscripcionForaneaParserServiceLocal circunscripcionForaneaParserServiceLocal;
	@EJB(name = "tramiteService") TramiteServiceLocal tramiteServiceLocal;
	@EJB GrupoFamiliarDaoLocal grupoFamiliarDao;
	@EJB UmfCodigoPostalDaoLocal umfCodigoPostalDao;
	@EJB PatronDaoLocal patronDao;
	@EJB UsuarioDaoLocal usuarioDao;
	@EJB RegistroParserServiceLocal registroParserServiceLocal;
	@EJB ProrrogaParserServiceLocal prorrogaParserServiceLocal;
	
	
	
	
	@Autowired GestionDocumentalServiceRemote gestionDocumental;
	
	private final String formatoFechaSAV07 = "dd    |     MM     |   yyyy";
	private final String formatoFecha = "dd-MM-yyyy";
	private final String NO_DISPONIBLE = "NO DISPONIBLE";
	/**
	 * Obtiene DitTramitePersonaFisica para el ultimo tramite
	 * de una persona filtrado por tipo 
	 * @throws Exception 
	 */
	
	@SuppressWarnings("unchecked")
	private DitTramitePersonaFisica getUltimoTramite(Long idPersona,
			List<Long> tiposTramite) throws Exception {
		DitTramitePersonaFisica ditTramitePersonaFisica = null;
		try {
			Criteria queryTramitePersona = this.getSession().createCriteria(DitTramitePersonaFisica.class);
			queryTramitePersona.createAlias("ditPersona", "persona");
			queryTramitePersona.add(Restrictions.eq("persona.cveIdPersona", idPersona));
			
			Criteria queryTramite = queryTramitePersona.createCriteria("ditTramite");
			queryTramite.createAlias("dicTipoTramite", "tipo");
			queryTramite.add(Restrictions.in("tipo.cveIdTipoTramite", tiposTramite));
			queryTramite.addOrder(Order.desc("fecTramite"));
			
			List<DitTramitePersonaFisica> tramites = queryTramitePersona.list();
			/*
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<DitTramitePersonaFisica> cQuery = cb
					.createQuery(DitTramitePersonaFisica.class);

			Root<DitTramitePersonaFisica> root = cQuery
					.from(DitTramitePersonaFisica.class);
			Path<DitTramitePersonaFisica> path = null;
			cQuery.select(root);

			List<Predicate> predicateList = new ArrayList<Predicate>();

			Predicate conjunction2 = root.get("ditTramite").get("dicTipoTramite")
					.get("cveIdTipoTramite").as(Integer.class).in(tiposTramite);

			Predicate conjunction = cb.conjunction();// se crea unoa conjuntion para
														// poder hacer and
			path = root.get("ditPersona").get("cveIdPersona");
			conjunction.getExpressions().add(
					cb.equal(path.as(Integer.class), idPersona));

			predicateList.add(conjunction);
			predicateList.add(conjunction2);

			Predicate[] predicates = new Predicate[predicateList.size()];
			predicateList.toArray(predicates);

			cQuery.where(predicates);

			cQuery.orderBy(cb.desc(root.get("ditTramite").get("fecTramite")));

			
			// Ejecuta la consulta
			List<DitTramitePersonaFisica> tramites = em.createQuery(cQuery)
					.getResultList();*/
			if (tramites != null && tramites.size() > 0) {
				ditTramitePersonaFisica = tramites.get(0);/*em.createQuery(cQuery)
						.getResultList().get(0);*/

				// DitDocumentoProbatorio
				// tramite =
				// TramiteParser.persistTomodelCompleto(ditTramitePersonaFisica);

			}
		} catch (Exception e) {
			log.error("getUltimoTramite", e);
			throw e;
		}

		return ditTramitePersonaFisica;
	}

	/**
	 * Obtiene DitTramitePersonaFisica para el ultimo tramite
	 * de una persona filtrado por tipo 
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public DitTramitePersonaFisica getUltimoTramiteByEstado(Long idPersona,
			List<Long> tiposTramite, Long estado) throws Exception {
		DitTramitePersonaFisica ditTramitePersonaFisica = null;
		try {
			Criteria queryTramitePersona = this.getSession().createCriteria(DitTramitePersonaFisica.class);
			queryTramitePersona.createAlias("ditPersona", "persona");
			queryTramitePersona.add(Restrictions.eq("persona.cveIdPersona", idPersona));
			
			Criteria queryTramite = queryTramitePersona.createCriteria("ditTramite");
			queryTramite.createAlias("dicTipoTramite", "tipo");
			queryTramite.createAlias("dicEstadoTramite", "estado");
			queryTramite.add(Restrictions.in("tipo.cveIdTipoTramite", tiposTramite));
			queryTramite.add(Restrictions.eq("estado.cveIdEstadoTramite", estado));
			queryTramite.addOrder(Order.desc("fecTramite"));
			
			List<DitTramitePersonaFisica> tramites = queryTramitePersona.list();
			
			
			/*
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<DitTramitePersonaFisica> cQuery = cb
					.createQuery(DitTramitePersonaFisica.class);

			Root<DitTramitePersonaFisica> root = cQuery
					.from(DitTramitePersonaFisica.class);
			Path<DitTramitePersonaFisica> path = null;
			cQuery.select(root);

			List<Predicate> predicateList = new ArrayList<Predicate>();

			Predicate conjunction3 = root.get("ditTramite").get("dicEstadoTramite")
			.get("cveIdEstadoTramite").as(Integer.class).in(estado);
			
			Predicate conjunction2 = root.get("ditTramite").get("dicTipoTramite")
					.get("cveIdTipoTramite").as(Integer.class).in(tiposTramite);

			Predicate conjunction = cb.conjunction();// se crea unoa conjuntion para
														// poder hacer and
			path = root.get("ditPersona").get("cveIdPersona");
			conjunction.getExpressions().add(cb.equal(path.as(Integer.class), idPersona));

			predicateList.add(conjunction);
			predicateList.add(conjunction2);
			predicateList.add(conjunction3);

			Predicate[] predicates = new Predicate[predicateList.size()];
			predicateList.toArray(predicates);

			cQuery.where(predicates);

			cQuery.orderBy(cb.desc(root.get("ditTramite").get("fecTramite")));

			
			// Ejecuta la consulta
			List<DitTramitePersonaFisica> tramites = em.createQuery(cQuery)
					.getResultList();*/
			if (tramites != null && tramites.size() > 0) {
				ditTramitePersonaFisica = tramites.get(0);

				// DitDocumentoProbatorio
				// tramite =
				// TramiteParser.persistTomodelCompleto(ditTramitePersonaFisica);

			}
		} catch (Exception e) {
			log.error("getUltimoTramite", e);
			throw e;
		}

		return ditTramitePersonaFisica;
	}
	

	/**
	 * Obtiene DitTramitePersonaFisica para el ultimo tramite
	 * de una persona filtrado por tipo 
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Tramite getUltimoTramitePersona(Long idPersona,
			List<Long> tiposTramite) throws Exception {

		Tramite tramite = null;
		//CriteriaBuilder cb = em.getCriteriaBuilder();
		try {
			Criteria queryTramitePersona = this.getSession().createCriteria(DitTramitePersonaFisica.class);
			queryTramitePersona.createAlias("ditPersona", "persona");
			queryTramitePersona.add(Restrictions.eq("persona.cveIdPersona", idPersona));
			
			Criteria queryTramite = queryTramitePersona.createCriteria("ditTramite");
			queryTramite.createAlias("dicTipoTramite", "tipo");
			queryTramite.add(Restrictions.in("tipo.cveIdTipoTramite", tiposTramite));
			
			queryTramite.addOrder(Order.desc("fecTramite"));
			
			List<DitTramitePersonaFisica> tramites = queryTramitePersona.list();
			/*
			
			CriteriaQuery<DitTramitePersonaFisica> cQuery = cb
			.createQuery(DitTramitePersonaFisica.class);

			Root<DitTramitePersonaFisica> root = cQuery
					.from(DitTramitePersonaFisica.class);
			Path<DitTramitePersonaFisica> path = null;
			cQuery.select(root);
		
			List<Predicate> predicateList = new ArrayList<Predicate>();
		
			Predicate conjunction2 = root.get("ditTramite").get("dicTipoTramite")
					.get("cveIdTipoTramite").as(Integer.class).in(tiposTramite);
		
			Predicate conjunction = cb.conjunction();// se crea unoa conjuntion para
														// poder hacer and
			path = root.get("ditPersona").get("cveIdPersona");
			conjunction.getExpressions().add(
					cb.equal(path.as(Integer.class), idPersona));
		
			predicateList.add(conjunction);
			predicateList.add(conjunction2);

			Predicate[] predicates = new Predicate[predicateList.size()];
			predicateList.toArray(predicates);
		
			cQuery.where(predicates);
		
			cQuery.orderBy(cb.desc(root.get("ditTramite").get("fecTramite")));
		
			
			
			// Ejecuta la consulta
			List<DitTramitePersonaFisica> tramites = em.createQuery(cQuery)
			.getResultList();
	*/
			DitTramitePersonaFisica ditTramitePersonaFisica = null;
			if (tramites != null && tramites.size() > 0) {
				ditTramitePersonaFisica = tramites.get(0);
	
				 tramite =TramiteParser.persistTomodelCompleto(ditTramitePersonaFisica);
	
			} 
		} catch (Exception e) {
			log.error("getUltimoTramitePersona", e);
			throw e;
		}
		return tramite;
	}
		
	/**
	 * Obtiene el ultimo tramite de tipo Sav005DTO de una perona 
	 * filtrado por tipo
	 * @throws Exception 
	 */
	@Override
	public Sav005DTO getUltimoTramiteSAV005(AsignacionNSS nss, Long idPersona,
			List<Long> tiposTramite, Long idEstadoTramite, Long idOrigenSolicitud) throws Exception {
		
		
		Sav005DTO sav05 = new Sav005DTO();
		
		try{
			
		//Buca el ultimo trámite de tipo correccion para la persona indicada
		//DitTramitePersonaFisica tramitePersona = getUltimoTramite(idPersona,tiposTramite);
		DitTramitePersonaFisica tramitePersona = getUltimoTramiteByEstado(idPersona,tiposTramite,idEstadoTramite);
		
		DitPersona p = tramitePersona.getDitPersona();
		DitTramite t = tramitePersona.getDitTramite();
		
		if(!t.getDitSolicitud().getDitSolicitudFirmaDigitals().isEmpty()) {
			
			DitSolicitudFirmaDigital firmaElectronica = t.getDitSolicitud().getDitSolicitudFirmaDigitals().get(0);
			
			sav05.setCadenaOriginal(firmaElectronica.getNumCadenaOriginal());
			sav05.setSelloDigital(firmaElectronica.getNumSelloDigital());
			sav05.setSecuenciaNotarial(firmaElectronica.getNumSecNotaria());
			sav05.setNumeroSerie(firmaElectronica.getRefNumSerieCertificado());
			
		}
		
		//Obtiene la informacion del integrante del grupo familiar
		GrupoFamiliar asegurado = grupoFamiliarDao.getIntegranteGrupoFamiliar(nss.getIdAsignacionNSS(), nss.getIdPersona());
		
		// Obtine la cabeza del grupo familiar
		CabezaGrupoFamiliar cabeza = grupoFamiliarDao.getCabezaGrupoFamiliar(nss.getIdAsignacionNSS());
		//SujetoObligado patron = patronDao.getPatronSujeto(nss.getIdAsignacionNSS());
		GrupoFamiliar integrante = grupoFamiliarDao.getIntegranteGrupoFamiliar(nss.getIdAsignacionNSS(), idPersona);
		
		
		TramiteCorreccionDerechohabiente c = null;
		if(idEstadoTramite.longValue() == EstadoTramiteEnum.CERRADO.getCodigo().longValue())
			c = getTramiteCorreccion(t.getCveIdTramite());
		else
			c = tramiteServiceLocal.getCorreccion(t.getCveIdTramite());
			
		sav05.setaMaterno(validaString(asegurado.getDerechohabiente().getSegundoApellido()));
		sav05.setaPaterno(validaString(asegurado.getDerechohabiente().getPrimerApellido()));
		sav05.setNombre(validaString(asegurado.getDerechohabiente().getNombre()));
		
		if(idEstadoTramite.longValue() == EstadoTramiteEnum.CERRADO.getCodigo().longValue()) {
			sav05.setcActual(integrante.getMedicoEnTurno().getUnidadMedicaFamiliar().getNombreCorto());
			//UMF anterior
			if(integrante.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF().equals(c.getMedicoEnTurno().getUnidadMedicaFamiliar().getIdUMF()))
			{
				sav05.setcAnterior(asegurado.getMedicoEnTurno().getUnidadMedicaFamiliar().getNombreCorto());
			} else {
				if(c != null && c.getMedicoEnTurno() != null)
					sav05.setcAnterior(c.getMedicoEnTurno().getUnidadMedicaFamiliar().getNombreCorto());
			}
			sav05.setDomicilio(this.obtenerCadenaDomicilio(integrante.getDomicilio()));
		} else {
			sav05.setcAnterior(integrante.getMedicoEnTurno().getUnidadMedicaFamiliar().getNombreCorto());
			sav05.setcActual(c.getMedicoEnTurno().getUnidadMedicaFamiliar().getNombreCorto());
			sav05.setDomicilio(this.obtenerCadenaDomicilio(c.getDomicilio()));
		}
		
		sav05.setCurp(validaString(asegurado.getDerechohabiente().getCurp()));
		
		
		if(idOrigenSolicitud.equals(OrigenSolicitudEnum.INTERNET.getId()))
			sav05.setEmpleado("TRÁMITE NO PRESENCIAL CONCLUIDO CON FIEL SAT.");
		
		sav05.setFechaUM(DateUtils.dateFormatCustom(cabeza.getFechaUltimoMovAfiliacion(), formatoFecha));
		
		sav05.setFecha( DateUtils.dateFormatCustom(t.getFecTramite(), "dd MMMMM yyyy").toUpperCase() );
		
		if(integrante.getDomicilio() != null) {
			sav05.setLugar(integrante.getDomicilio().getAsentamiento().getLocalidad().getMunicipio().getNombre()+", "+
				integrante.getDomicilio().getAsentamiento().getLocalidad().getMunicipio().getEntidadFederativa().getNombre());
		}
		
		sav05.setNss(nss.getNssStr());
		sav05.setsDelegacion(""+asegurado.getMedicoEnTurno().getUnidadMedicaFamiliar().getSubdelegacion().getDescripcion());
		sav05.setUltimoMov(""+cabeza.getTipoMovtoAsegurado().getIdTipoMvtoAsegurado());		
		
		
		sav05.setRegistroPatronal("");
		try{
			sav05.setRegistroPatronal(cabeza.getPatronSujetoObligado().getNumeroRegistroPatronal()+cabeza.getPatronSujetoObligado().getModalidad().getNumModalidad());
		}catch(NullPointerException e){
			
			// -----------------------------------------------------
			// El pensionado puede no tener un patrón
			// -----------------------------------------------------
			log.debug("Sujeto Obligado NULL, idAsignacion:"+nss.getIdAsignacionNSS());
		}
		
		
		sav05.setCurpBeneficiario(validaString(p.getCurp()));
		//obtenemos el agregado de identidad
		String agregado = integrante.getAgregadoAfiliacion();
		//verificamos que no venga nulo par apoder usarlo
		sav05.setAgregado( agregado != null ? agregado.substring(0, agregado.length()-1) : "");
		//ponemos el digito verificador
		sav05.setdVerificador(""+(agregado != null ? agregado.charAt(agregado.length() -1) : ""));
		
		if( p.getFecNacimiento() != null ){
			sav05.setMesNacimiento(DateUtils.dateFormatCustom(p.getFecNacimiento(), "MM"));
		}else{
			String mesNacimiento = "" +( integrante.getDerechohabiente().getMesRegistroNac() != null ? integrante.getDerechohabiente().getMesRegistroNac() : "");
			mesNacimiento = mesNacimiento.trim().length() != 0 ? (mesNacimiento.trim().length() == 1 ? ("0"+mesNacimiento) : mesNacimiento) : "";
			sav05.setMesNacimiento(mesNacimiento);
		}
		
		
		sav05.setNombreBeneficiario(validaString(p.getNomNombre()) +" " + validaString(p.getNomPrimerApellido()) +" "
				+ validaString(p.getNomSegundoApellido()));
		
		
	
		// ------------------------------------------------------------
		// Integrantes del grupo familiar modificados en el trámite
		// ------------------------------------------------------------
		
			// -------------------------------------------------------------------------
			// Si el trámite no contiene candidatos, se toma el integrante al 
			// que se le realizo el trámite
			// -------------------------------------------------------------------------
			try{
				sav05.addBeneficiario(obtenerBeneficiarioActivo(integrante));
			}catch(DerechohabientesBusinessException e){
				// ------------------------------------------
				// Integrante en baja
				// ------------------------------------------
				log.debug(e);
			}
			
		
		
			if( c.getCandidatosCambioClinica() == null )
				c = tramiteServiceLocal.getCorreccion(t.getCveIdTramite());
			
			if( c.getCandidatosCambioClinica() != null ){
				
				for(Long idCandidato :  c.getCandidatosCambioClinica()  ){
					try{
						
						if(  !idCandidato.equals(idPersona) ){
							GrupoFamiliar candidato = grupoFamiliarDao.getIntegranteGrupoFamiliar(nss.getIdAsignacionNSS(), idCandidato);
							sav05.addBeneficiario(obtenerBeneficiarioActivo(candidato));
						}
						
					}catch(DerechohabientesBusinessException e){
						// ------------------------------------------------------------------
						// Si el candidato no esta activo 
						// ------------------------------------------------------------------
						log.debug(e);
					}catch( Exception e ){
						log.debug(e);
					}
					
				}
			}
		
		
		}catch (Exception e){
			log.error("getUltimoTramiteSAV005" , e);
			throw e;
		}
		
		if(idPersona.equals(nss.getIdPersona()))
			sav05.setCambioParcial(false);
		else
			sav05.setCambioParcial(true);
		
		
		
		
		
		
		
		
		return sav05;
	}
	
	private String validaString(String dato){
		
		return (dato != null ? dato.toUpperCase() : "");
	}
	/**
	 * Obtiene una benficiario para el reporte sav005
	 * 
	 * @param nss
	 * @param idCandidato
	 * @return
	 * @throws DerechohabientesBusinessException
	 * @throws Exception
	 */
	private BeneficiarioSav005DTO obtenerBeneficiarioActivo(GrupoFamiliar candidato) throws DerechohabientesBusinessException{
		
		try{
		
			Derechohabiente derehohabiente = candidato.getDerechohabiente(); 
			
			// -------------------------------------------------
			// Solamente activos
			// -------------------------------------------------
			Long estadoDerechohabiente = candidato.getEstadoDerechohabiente().getIdEstadoDerechohabiente();
			if( !estadoDerechohabiente.equals(EstadoDerechohabienteEnum.BAJA.getId()) &&
					!estadoDerechohabiente.equals(EstadoDerechohabienteEnum.FALLECIDO.getId())	){
			
				BeneficiarioSav005DTO beneficiario = new BeneficiarioSav005DTO(); 
				
				beneficiario.setCurpBeneficiario(validaString(derehohabiente.getCurp()));
				beneficiario.setNombreBeneficiario(
						validaString(derehohabiente.getNombre()) +" "
						+ validaString(derehohabiente.getPrimerApellido())+" "
						+ validaString(derehohabiente.getSegundoApellido())
				);
				
				//se valida el agregado medico
				String agregado = candidato.getAgregadoAfiliacion();
				//se pone el campo del agregado
				beneficiario.setAgregado( agregado != null ? agregado.substring(0, agregado.length()-1) : "");
				//se pone el digito verificador
				beneficiario.setdVerificador(""+(agregado != null ? agregado.charAt(agregado.length() -1) : ""));
				
				if(derehohabiente.getFechaNacimiento() != null){
					beneficiario.setMesNacimiento(DateUtils.dateFormatCustom(derehohabiente.getFechaNacimiento(), "MM"));
				}else{
					String mesNacimiento = "" +( derehohabiente.getMesRegistroNac() != null ? derehohabiente.getMesRegistroNac() : "");
					mesNacimiento = mesNacimiento.trim().length() != 0 ? (mesNacimiento.trim().length() == 1 ? ("0"+mesNacimiento) : mesNacimiento) : "";
					beneficiario.setMesNacimiento(mesNacimiento);
				}
		
				return beneficiario;
			
			
			}
			
		}catch(Exception e){
			log.debug(e);
		}

		// ---------------------------------------------
		// Si esta en baja o se lanzo un excepcion
		// ---------------------------------------------
		throw new DerechohabientesBusinessException(); 
		
		
	}
	
	private String obtenerCadenaDomicilio(Domicilio domicilio) {
		
		
		String dom = "";
		
		if(domicilio != null) {
            if(domicilio.getVialidadPrimaria()!=null && domicilio.getVialidadPrimaria().getNombre()!=null){
                dom = "CALLE "+domicilio.getVialidadPrimaria().getNombre().toUpperCase();
            }

        dom += (domicilio.getNumExterior1() != null ? ", " + domicilio.getNumExterior1() + " " : "")
            + (!StringUtils.isBlank(domicilio.getNumExteriorAlf()) ? " " + domicilio.getNumExteriorAlf() + ", " : ", ")
            + (domicilio.getNumInterior() != null ? ", " + domicilio.getNumInterior()+", " : "")
            + (!StringUtils.isBlank(domicilio.getNumInteriorAlf()) ? " " + domicilio.getNumInteriorAlf()+", " : " ")
            + ", COLONIA ";

        if(domicilio.getAsentamiento() != null && domicilio.getAsentamiento().getNombre() != null){
          dom += domicilio.getAsentamiento().getNombre().toUpperCase();
        }

        if(domicilio.getCodigoPostal() != null && domicilio.getCodigoPostal().getCodigoPostal() != null)
            dom += ", C.P. "+domicilio.getCodigoPostal().getCodigoPostal();
		}
		return dom;
	}
	
	/**
	 * Obtiene el ultimo tramite de tipo Sav007DTO de una perona 
	 * filtrado por tipo
	 * @throws Exception 
	 */
	@Override
	public Sav007DTO getUltimoTramiteSAV007(AsignacionNSS nss, Long idPersona,
			List<Long> tiposTramite) throws Exception {

		DitTramitePersonaFisica tramitePersona = getUltimoTramiteByEstado(idPersona,
				tiposTramite, EstadoTramiteEnum.CERRADO.getCodigo().longValue());
		String observaciones = "";
		Sav007DTO sav007 = null;
		try {
			if (tramitePersona != null ) {
				//Obtiene la informacion del asegurado
				GrupoFamiliar asegurado = grupoFamiliarDao
						.getIntegranteGrupoFamiliar(nss.getIdAsignacionNSS(),
								nss.getIdPersona());
				
				//Obtiene el tramite
				DitTramite tramite = tramitePersona.getDitTramite();
				//obtiene la persona
				DitPersona persona = tramitePersona.getDitPersona();
			
				//Obtener la prorroga
				TramiteProrroga prorroga = getTramiteProrroga(tramitePersona.getDitTramite().getCveIdTramite());
				prorroga = prorroga == null ? (TramiteProrroga)JaxbUtil.xmlToObject(tramite.getDitDetalleTramite().getRefDatosTramiteXml()) : prorroga;
				GrupoFamiliar beneficiario = grupoFamiliarDao
				.getIntegranteGrupoFamiliar(nss.getIdAsignacionNSS(),
						persona.getCveIdPersona());
				
				//Obtine los datos de la UMF
				
//				DitUmfCodPo umf = umfCodigoPostalDao.getUmfCodPosByIdUmf(tramite.getDitSolicitud().getDitUmfTurno()
//						.getDicUmf().getCveIdUmf());
				//Inicia carga del onjeto SAV007DTO
				
				sav007 = new Sav007DTO();
	
				sav007.setaMaterno(asegurado.getAsignacionNSS()
						.getSegundoApellido());
				sav007.setaPaterno(asegurado.getAsignacionNSS().getPrimerApellido());
				sav007.setNombre(asegurado.getAsignacionNSS().getNombre());
				sav007.setNss(asegurado.getAsignacionNSS().getNssStr());// este sale
																		// null
				sav007.setCurp(asegurado.getAsignacionNSS().getCurp());
	
//				sav007.setFechaSolicitud(DateUtils.dateFormatCustom(tramite.getDitSolicitud().getFecSolicitud(), formatoFecha));
				sav007.setFechaSolicitud(DateUtils.dateFormatCustom(new Date(), formatoFecha));
				sav007.setHombre(persona.getDicSexo().getCveIdSexo() == SexoEnum.HOMBRE
						.getId() ? true : false);
	
				sav007.setNombreB(persona.getNomNombre());
				sav007.setaMaternoB(persona.getNomSegundoApellido());
				sav007.setaPaternoB(persona.getNomPrimerApellido());
				
				sav007.setCalidad("" +beneficiario.getCalidad());
				
				
				sav007.setMesNacimiento("");
				sav007.setAnioNacimiento("");
				if( persona.getFecNacimiento() != null ){
					sav007.setMesNacimiento(DateUtils.dateFormatCustom(
							persona.getFecNacimiento(), "MM"));
					sav007.setAnioNacimiento(DateUtils.dateFormatCustom(
							persona.getFecNacimiento(), "yyyy"));
		
				}
				
				
				DitDocumentoProbatorio dp = new DitDocumentoProbatorio();
				if(tramitePersona.getDitTramite().getDitDocumentoProbatorios() != null  && tramitePersona.getDitTramite().getDitDocumentoProbatorios().size() > 0 ){
					dp = (DitDocumentoProbatorio)tramitePersona.getDitTramite().getDitDocumentoProbatorios().get(0);
				}else{
					dp.setFecExpedicion(new Date());
				}
				
				
				if(!tramite.getDitSolicitud().getDitSolicitudFirmaDigitals().isEmpty()) {
					DitSolicitudFirmaDigital firmaElectronica = tramite.getDitSolicitud().getDitSolicitudFirmaDigitals().get(0);
					
					sav007.setCadenaOriginal(firmaElectronica.getNumCadenaOriginal());
					sav007.setSelloDigital(firmaElectronica.getNumSelloDigital());
					sav007.setSecuenciaNotarial(firmaElectronica.getNumSecNotaria());
					sav007.setNumeroSerie(firmaElectronica.getRefNumSerieCertificado());
					
				}
				
				 
				sav007.setFechaElaboracion(DateUtils.dateFormatCustom(new Date(), "dd MMMMM yyyy").toUpperCase());
				 
				//sav007.setLugar(+" A");
				if( tramite.getDitSolicitud().getDitUmfTurno() != null ){
					sav007.setDelegacion(tramite.getDitSolicitud().getDitUmfTurno()
							.getDicUmf().getDicSubdelegacion().getDicDelegacion()
							.getClaveDelegacion());
					sav007.setSubDelegacion(tramite.getDitSolicitud().getDitUmfTurno()
							.getDicUmf().getDicSubdelegacion().getClaveSubdelegacion());
					sav007.setUmf(tramite.getDitSolicitud().getDitUmfTurno()
							.getDicUmf().getNomCorto());
				}
	
				
				if (tramite.getDicTipoTramite().getCveIdTipoTramite() == TipoTramiteEnum.PRORROGA_ENFERMEDAD
						.getCodigo().longValue()) {
					
					long edad = 0;
					
					if( persona.getFecNacimiento() != null  ){
						
						// Se calcula la edad del integrante
						edad = DateUtils.getEdad(persona.getFecNacimiento());
						
						
					}else{
						
						// -------------------------------------------------------------------
						// Tomará el primer día del siguiente mes como la fecha de nacimiento
						// -------------------------------------------------------------------
						edad = DateUtils.getEdadSinDia(persona.getNumMesNacReg(), persona.getNumAnioNacReg());
						
					}
					
					
					if(edad >= 25){
						
						DitDictBeneficiarioInca di = null;
						DitDocumentoProbatorio docProbatorio = null;
						
						if(tramitePersona.getDitTramite().getDitDocumentoProbatorios() != null && tramitePersona.getDitTramite()
						.getDitDocumentoProbatorios().size() > 0){
							docProbatorio = tramitePersona.getDitTramite()
							.getDitDocumentoProbatorios().get(0) != null ? tramitePersona.getDitTramite()
									.getDitDocumentoProbatorios().get(0) : null;
						}
						
						if(docProbatorio != null){
							di = tramitePersona.getDitTramite()
							.getDitDocumentoProbatorios().get(0).getDitDictBeneficiarioInca();
							
							if(di != null ){
								if(prorroga.getCaracter().getIdCaracter().longValue() == CaracterEnum.DEFINITIVO.getId()){
									sav007.setFechaRevision("PERMANENTE");
								}else{
									sav007.setFechaRevision(DateUtils.dateFormatCustom(
										prorroga.getFechaFinProrroga(), formatoFechaSAV07));
								}
								sav007.setIncapacidad(true);
								if(di.getDitMedicoEspecialidad()!= null){
									sav007.setMatricula(di.getDitMedicoEspecialidad().getDicMedico().getNumMedfamMatricula());
									sav007.setMedicoResponsable(di.getDitMedicoEspecialidad().getDicMedico().getNomNombre()
											+ " "
											+ di.getDitMedicoEspecialidad().getDicMedico().getNomPrimerApellido()
											+ " "
											+ di.getDitMedicoEspecialidad().getDicMedico().getNomSegundoApellido());
								}else{
									sav007.setMatricula("N/D");
									sav007.setMedicoResponsable(NO_DISPONIBLE);
								}
							}
						}
						
					}else{
						DitCertificadoSitCritica di = null;
						DitDocumentoProbatorio docProbatorio = null; 
						
						if(tramitePersona.getDitTramite().getDitDocumentoProbatorios().size() > 0){
							if(tramitePersona.getDitTramite().getDitDocumentoProbatorios().get(0) != null)
								docProbatorio = tramitePersona.getDitTramite().getDitDocumentoProbatorios().get(0);
						}
						 
						
						if( docProbatorio != null){
							di =	tramitePersona.getDitTramite()
							.getDitDocumentoProbatorios().get(0).getDitCertificadoSitCritica();
							if(prorroga.getCaracter().getIdCaracter().longValue() == CaracterEnum.DEFINITIVO.getId()){
								sav007.setFechaRevision("PERMANENTE");
							}else{
								sav007.setFechaRevision(DateUtils.dateFormatCustom(
									prorroga.getFechaFinProrroga(), formatoFechaSAV07));
							}
							sav007.setIncapacidad(true);
							if(di.getDitMedicoEspecialidad()!= null){
								sav007.setMatricula(di.getDitMedicoEspecialidad().getDicMedico().getNumMedfamMatricula());
								sav007.setMedicoResponsable(di.getDitMedicoEspecialidad().getDicMedico().getNomNombre()
										+ " "
										+ di.getDitMedicoEspecialidad().getDicMedico().getNomPrimerApellido()
										+ " "
										+ di.getDitMedicoEspecialidad().getDicMedico().getNomSegundoApellido());
							}else{
								sav007.setMatricula("N/D");
								sav007.setMedicoResponsable(NO_DISPONIBLE);
							}
							
						
						}
						
					}
					
					//Ley del IMSS aplicada
					observaciones = "Aplicación del artículo 84 fracción VI LSS";				
	
				} else if (tramite.getDicTipoTramite().getCveIdTipoTramite() == TipoTramiteEnum.PRORROGA_ESTUDIOS
						.getCodigo().longValue()) {
//					DitConstanciaEstudio ce = tramitePersona.getDitTramite()
//							.getDitDocumentoProbatorios().get(0)
//							.getDitConstanciaEstudio();
	
					// Se supone que la prorroga es autorizada
					sav007.setProrrogaAut(true);
					//System.out.println(" fecha ini"+prorroga.getFechaInicioProrroga());
					//System.out.println(" fecha fin"+prorroga.getFechaFinProrroga());
					
					sav007.setFechaInicioProrroga(DateUtils.dateFormatCustom(prorroga.getFechaInicioProrroga(), formatoFechaSAV07));
					sav007.setFechaFinProrroga(DateUtils.dateFormatCustom(prorroga.getFechaFinProrroga(), formatoFechaSAV07));
					observaciones = " ";
				
				} else if (tramite.getDicTipoTramite().getCveIdTipoTramite() == TipoTramiteEnum.PRORROGA_OBSTETRICOS
						.getCodigo().longValue()) {
					DitObstetrico o = tramitePersona.getDitTramite()
							.getDitDocumentoProbatorios().get(0).getDitObstetrico();
	
					sav007.setFechaConcepcion(DateUtils.dateFormatCustom(o.getFecProbConcepcion(), formatoFechaSAV07));
					sav007.setFechaParto(DateUtils.dateFormatCustom(o.getFecParto(), formatoFechaSAV07));
					
					
					if(o.getDitMedicoEspecialidad()!= null){
						sav007.setMatricula(o.getDitMedicoEspecialidad().getDicMedico().getNumMedfamMatricula());
						sav007.setMedicoResponsable(o.getDitMedicoEspecialidad().getDicMedico().getNomNombre()
								+ " "
								+ o.getDitMedicoEspecialidad().getDicMedico().getNomPrimerApellido()
								+ " "
								+ o.getDitMedicoEspecialidad().getDicMedico().getNomSegundoApellido());
					}else{
						sav007.setMatricula("N/D");
						sav007.setMedicoResponsable(NO_DISPONIBLE);
					}
					
					//Ley del IMSS aplicada
					observaciones = "Aplicación del Acuerdo 196 del 25 mayo de 2005";
					
				}else if (tramite.getDicTipoTramite().getCveIdTipoTramite() == TipoTramiteEnum.PRORROGA_VIGENCIA_PERMANENTE
						.getCodigo().longValue()) {
					//Ley del IMSS aplicada
					observaciones = "Aplicación del articulo 93 LLS97";
					sav007.setFechaProbTermino("PERMANENTE");
					
				}else {// este pora el la prorroga de tipo trataamiento medico
					    // que por el momento no se considera.
					sav007.setDiagnostico("Diagnostico");
					sav007.setEnfermedad(1);
					sav007.setExisteEnfermedad(Boolean.FALSE);
					observaciones = " ";
				}

                if (tramite.getDicTipoTramite().getCveIdTipoTramite() == TipoTramiteEnum.PRORROGA_ACUERDOS.getCodigo().longValue()){
                      sav007.setFechaInicioProrrogaAcuerdo(DateUtils.dateFormatCustom(prorroga.getFechaInicioProrroga(), formatoFechaSAV07));
                    if(prorroga.getCaracter().getIdCaracter().longValue() == CaracterEnum.DEFINITIVO.getId()){
						sav007.setFechaFinProrrogaAcuerdo("PERMANENTE");
					}else{
						sav007.setFechaFinProrrogaAcuerdo(DateUtils.dateFormatCustom(
							prorroga.getFechaFinProrroga(), formatoFechaSAV07));
					}

                }
                
               if (tramite.getDicTipoTramite().getCveIdTipoTramite() == TipoTramiteEnum.PRORROGA_LAUDOS.getCodigo().longValue()){
            	     sav007.setFechaInicioProrrogaLaudo(DateUtils.dateFormatCustom(prorroga.getFechaInicioProrroga(), formatoFechaSAV07));
                     if(prorroga.getCaracter().getIdCaracter().longValue() == CaracterEnum.DEFINITIVO.getId()){
 						sav007.setFechaFinProrrogaLaudo("PERMANENTE");
 					}else{
 						sav007.setFechaFinProrrogaLaudo(DateUtils.dateFormatCustom(
 							prorroga.getFechaFinProrroga(), formatoFechaSAV07));
 					}
                }
				
				sav007.setObservaciones((tramite.getRefObservacion() != null ? tramite.getRefObservacion().toUpperCase() : " ") +"<br><br>"+ (observaciones!=null ? observaciones : " "));	
				
			}
		} catch (Exception e) {
			log.error("getUltimoTramiteSAV007", e);
			throw e;
		}

		return sav007;
	}

	
	/**
	 * Obtiene el ultimo tramite de tipo Sav007DTO de una perona 
	 * filtrado por tipo
	 * @throws Exception 
	 */
	@Override
	public Sav017DTO getUltimoTramiteSAV017(AsignacionNSS nss, Long idPersona,
			List<Long> tiposTramite) throws Exception {

		//Buca el ultimo trámite de tipo correccion para la persona indicada
		@SuppressWarnings("unused")
		DitTramitePersonaFisica tramitePersona = getUltimoTramite(idPersona,
				tiposTramite);

		Sav017DTO sav017 = new Sav017DTO();
		try {
			sav017.setAnio("2012");
			sav017.setBeneficiario("GRANDE JUAREZ JONHI");
			sav017.setCalidad("14");
			sav017.setCurp("000000000000000000");
			sav017.setMes("10");
			sav017.setSexo("H");
		} catch (Exception e) {
			log.error("getUltimoTramiteSAV017", e);
			throw e;
		}
		return sav017;
	}
	
	
	
	@Override
	public Tramite saveTramite(Tramite tramite)
			throws Exception {

		// Guardamos solo el tramite
		DitTramite ditTramite = TramiteSimpleParser.modelToPersist(tramite);
		try {
			em.persist(ditTramite);
			// Colocamos el id del tramite que se obtuvo al guardar
			tramite.setTramiteId(ditTramite.getCveIdTramite());
			// guardamos a la persona relacionada con el tramite
			em.persist(TramiteParser.modelToPersistPartial(tramite));
			em.flush();
		} catch (Exception e) {
			log.error("saveTramite", e);
			throw e;
		}
		
		return tramite;
	}
	
	@Override
	public Tramite saveTramitePersonaFisica(Tramite tramite)
			throws DerechohabientesBusinessException, Exception {		
		try {
			em.persist(TramiteParser.modelToPersistPartial(tramite));
			em.flush();
		} catch (Exception e) {
			log.error("saveTramite", e);
			throw e;
		}
		
		return tramite;
	}	
	
	@Override
	public Tramite saveTramiteInternet(Tramite tramite)
			throws DerechohabientesBusinessException, Exception {
		// Guardamos solo el tramite
		DitTramite ditTramite = TramiteSimpleParser.modelToPersist(tramite);
		try {
			em.persist(ditTramite);			
			// Colocamos el id del tramite que se obtuvo al guardar
			tramite.setTramiteId(ditTramite.getCveIdTramite());
			DitDetalleTramite ditDetalleT = TramiteParser.modelToPersistDetalle(tramite);
			em.persist(ditDetalleT);
			em.flush();
		} catch (Exception e) {
			log.error("saveTramite", e);
			throw e;
		}
		
		return tramite;
	}

	@Override
	public void updateTramite(Tramite tramite) throws DerechohabientesBusinessException,Exception {
		List<DitDocumentoProbatorio> ditDocumentoProbatorios=null;
		
		DitTramite ditTramite = TramiteSimpleParser.modelToPersist(tramite);
		try {
			ditDocumentoProbatorios = findDocsProbatorios(tramite.getTramiteId());
			if (ditDocumentoProbatorios != null && ditDocumentoProbatorios.size() > 0  ){
				ditTramite.setDitDocumentoProbatorios(ditDocumentoProbatorios);
			}
			
			em.merge(ditTramite);
			em.flush();
		} catch (Exception e) {
			log.error("updateTramite", e);
			throw e;
		}
		
	}
	
	@Override
	public boolean existeTramitePersonaF(Tramite tramite)
			throws DerechohabientesBusinessException, Exception {
		boolean resp = false;
		DitTramitePersonaFisica ditTramitePersonaF = null;
		DitTramitePersonaFisicaPK ditTramitePPK = new DitTramitePersonaFisicaPK();
		ditTramitePPK.setCveIdPersona(tramite.getPersona().getIdPersona());
		ditTramitePPK.setCveIdTramite(tramite.getTramiteId());
		
		try {
			ditTramitePersonaF = (DitTramitePersonaFisica) em.find(DitTramitePersonaFisica.class, ditTramitePPK);			
		} catch(NoResultException e){
			ditTramitePersonaF = null;			
		} catch (Exception e) {
			log.error("existeTramitePersonaF", e);
			throw e;
		}
		
		if(ditTramitePersonaF != null){
			resp = true;
		}
		
	return resp;	
	}

	@SuppressWarnings("unchecked")
	private List<DitDocumentoProbatorio> findDocsProbatorios(Long idTramite) throws Exception{
		List<DitDocumentoProbatorio> ditDocumentoProbatorios=null;
		try {
			Criteria queryDocumentos = this.getSession().createCriteria(DitDocumentacionTramite.class);
			queryDocumentos.setProjection(Projections.property("ditDocumentoProbatorio"));
			queryDocumentos.createAlias("ditTramite", "tramite");
			queryDocumentos.add(Restrictions.eq("tramite.cveIdTramite", idTramite));
			
			ditDocumentoProbatorios = queryDocumentos.list();
			/*
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<DitDocumentoProbatorio> cQuery = cb.createQuery(DitDocumentoProbatorio.class);
			Root<DitDocumentacionTramite> root = cQuery.from(DitDocumentacionTramite.class);
			
			cQuery.select(root.get("ditDocumentoProbatorio").as(DitDocumentoProbatorio.class));
			Predicate conjunction = cb.conjunction();
			
			conjunction.getExpressions().add(cb.equal(root.get("ditTramite").get("cveIdTramite").as(Integer.class),idTramite.intValue()));
			
			cQuery.where(conjunction);
			
			ditDocumentoProbatorios=em.createQuery(cQuery).getResultList();*/
		} catch (Exception e) {
			log.error("findDocsProbatorios", e);
			throw e;
		}
				
		return ditDocumentoProbatorios;
	}
	
	@Override
	public void otroSaveTramite(Tramite tramite) throws DerechohabientesBusinessException,Exception {
		DitTramitePersonaFisica ditTramite = TramiteParser
				.modelToPersist(tramite);
		try {
			em.persist(ditTramite);
			em.flush();
		} catch (Exception e) {
			log.error("otroSaveTramite", e);
			throw e;
		}
		
	}

	@Override
	public TramiteRegistroDerechohabiente getTramiteRegistro(Long idTramite) throws DerechohabientesBusinessException,Exception {
		// TODO Auto-generated method stub
		TramiteRegistroDerechohabiente registroDerechohabiente = null;
		DitRegistroDerechohabiente encontrado = null;
		try {
			encontrado = em.find(
					DitRegistroDerechohabiente.class, idTramite);
		} catch (Exception e) {
			log.error("getTramiteRegistro", e);
			throw e;
		}
		
		if (encontrado != null) {
			registroDerechohabiente = registroParserServiceLocal.persisToModel(encontrado);
		}

		return registroDerechohabiente;
	}

	
	@Override
	public TramiteProrroga getTramiteProrroga(Long idTramite) throws DerechohabientesBusinessException,Exception {
		TramiteProrroga prorroga = null;
		DitProrroga encontrado = null;
		try {
			Query query = em.createQuery("FROM DitProrroga p WHERE p.ditTramite = :idTramite");
			query.setParameter("idTramite", idTramite);
			encontrado = (DitProrroga) query.getSingleResult();
//			encontrado = em.find(DitProrroga.class, idTramite);
		} catch (Exception e) {
			log.error("getTramiteProrroga", e);
			throw e;
		}
				
		if (encontrado != null) {
			prorroga = prorrogaParserServiceLocal.persisToModel(encontrado);
		}
		
		return prorroga;
	}

	@Override
	public TramiteCorreccionDerechohabiente getTramiteCorreccion(Long idTramite) throws DerechohabientesBusinessException, Exception {
		// TODO Auto-generated method stub
		TramiteCorreccionDerechohabiente correccionDatoDerechohabiente = null;
		DitCorreccionDatoDerechohab encontrado = null;
		try {
			Query query = em.createQuery( "FROM DitCorreccionDatoDerechohab c WHERE c.ditTramite = :idTramite" );
			query.setParameter("idTramite", idTramite);
			encontrado = (DitCorreccionDatoDerechohab) query.getSingleResult();
//			encontrado = em.find(DitCorreccionDatoDerechohab.class, idTramite);
		} catch (Exception e) {
			log.error("Ocurrio un error", e);
			throw e;
		}
		
		if (encontrado != null)
			correccionDatoDerechohabiente = correccionParser.persistToModel(encontrado);

		return correccionDatoDerechohabiente;
	}

	@Override
	public void saveCorreccionDerechohabiente(TramiteCorreccionDerechohabiente correccion) 
		throws Exception {
		DitCorreccionDatoDerechohab ditCorreccion = null;
		
		ditCorreccion = correccionParser.modelToPersist(correccion);
		try {
			em.persist(ditCorreccion);
			em.flush();
		} catch (Exception e) {
			log.error("saveCorreccionDerechohabiente", e);
			throw e;
		}
	}
	
	
	@Override
	public void updateCorreccionDerechohabiente(
			TramiteCorreccionDerechohabiente correccion) throws DerechohabientesBusinessException,Exception {
		// TODO Auto-generated method stub
		DitCorreccionDatoDerechohab ditCorreccion = correccionParser
		.modelToPersist(correccion);
		try {
			em.merge(ditCorreccion);
		} catch (Exception e) {
			log.error("updateCorreccionDerechohabiente", e);
			throw e;
		}
		
	}

	@Override
	public void saveCircunscripcionForanea(
			TramiteCircunscripcionForanea circunscripcion) throws DerechohabientesBusinessException,Exception {
		// TODO Auto-generated method stub
		DitCircunscripcionForanea ditCircunscripcion = circunscripcionForaneaParserServiceLocal.modelToPersist(circunscripcion);
		try {
			em.persist(ditCircunscripcion);
			em.flush();
		} catch (Exception e) {
			log.error("saveCircunscripcionForanea", e);
			throw e;
		}
	}

	
	@Override
	public void updateCircunscripcionForanea(
			TramiteCircunscripcionForanea circunscripcion) throws DerechohabientesBusinessException,Exception {
		// TODO Auto-generated method stub
		DitCircunscripcionForanea ditCircunscripcionForanea = circunscripcionForaneaParserServiceLocal.modelToPersist(circunscripcion);
		try {
			em.merge(ditCircunscripcionForanea);
		} catch (Exception e) {
			log.error("updateCircunscripcionForanea", e);
			throw e;
		}
		
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tramite> getTramitaByEstado(Long idPersona,Long idEstadoTramite) throws DerechohabientesBusinessException, Exception{
		List<DitTramite> ditTramites=null;
		List<Tramite> resultado =null;
		try{
			
			
			Criteria queryTramitePersona = this.getSession().createCriteria(DitTramitePersonaFisica.class);
			queryTramitePersona.createAlias("ditPersona", "persona");
			queryTramitePersona.setProjection(Projections.property("ditTramite"));
			queryTramitePersona.add(Restrictions.eq("persona.cveIdPersona", idPersona));
			
			Criteria queryTramite = queryTramitePersona.createCriteria("ditTramite");
			queryTramite.createAlias("dicEstadoTramite", "estado");
			queryTramite.add(Restrictions.eq("estado.cveIdEstadoTramite", idEstadoTramite));
			queryTramite.addOrder(Order.asc("cveIdTramite"));
			
			ditTramites = queryTramitePersona.list();
			/*List<DitTramitePersonaFisica> tramites = queryTramitePersona.list();
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<DitTramite> query = cb.createQuery(DitTramite.class);//resulado
			Root<DitTramite> root = query.from(DitTramite.class);//from
			query.select(root);//select
			Predicate conj=cb.conjunction();
			//delegacion
			conj.getExpressions().add(cb.equal(root.get("dicEstadoTramite").get("cveIdEstadoTramite").as(Integer.class), idEstadoTramite));
			//conj.getExpressions().add(cb.equal(root.get("ditTramitePersonaFisica").get("ditPersona").get("cveIdPersona").as(Integer.class), idPersona));
			
			Subquery<Integer> queryTramitePersona = query.subquery(Integer.class);
			Root<DitTramitePersonaFisica> tablaTramitePersona = queryTramitePersona.from(DitTramitePersonaFisica.class);
			Path<Integer> selecTramitePersona = tablaTramitePersona .get("ditTramite").get("cveIdTramite");
			queryTramitePersona.select(selecTramitePersona);
			Predicate parametrosTramitePersona = cb.conjunction();
			parametrosTramitePersona.getExpressions().add(cb.equal(tablaTramitePersona.get("ditPersona").get("cveIdPersona").as(Integer.class), idPersona));
			queryTramitePersona.where(parametrosTramitePersona);
			
			conj.getExpressions().add(cb.in(root.get("cveIdTramite")).value(queryTramitePersona));
			query.where(conj);
			query.orderBy(cb.asc(root.get("cveIdTramite")));

			ditTramites=em.createQuery(query).getResultList();*/
		}catch (NoResultException e) {
			return null;
		}catch (Exception e){
			log.error("getTramitaByEstado", e);
			throw e;
		}
		resultado = TramiteSimpleParser.persistToModelList(ditTramites);
		
		return resultado;
		
	}
	
	@Override
	public Tramite getTramite(Long idTramite) throws DerechohabientesBusinessException, Exception{
		DitTramite ditTramite = null;
		try {
			ditTramite=   em.find(DitTramite.class, idTramite);
		} catch (Exception e) {
			log.error("getTramite", e);
			throw e;
		}
		 
		Tramite tramite =TramiteParser.persistTomodelCompleto(ditTramite);
		return tramite;
	}

	@Override
	public Tramite getTramiteInternet(Long idTramite) throws DerechohabientesBusinessException, Exception{
		DitTramite ditTramite = null;
		try {
			ditTramite=   em.find(DitTramite.class, idTramite);
		} catch (Exception e) {
			log.error("getTramite", e);
			throw e;
		}
		 
		Tramite tramite =TramiteParser.persistTomodelSimple(ditTramite);
		return tramite;
	}
	
	@Override
	public Object getDocumentoProbatorioProrroga(Long idTramite,long tipoTramite) throws Exception{
		DitTramite ditTramite = null;
		try {
			ditTramite=   em.find(DitTramite.class, idTramite);
		} catch (Exception e) {
			log.error("getDocumentoProbatorioProrroga", e);
			throw e;
		}
		 
		DitDocumentoProbatorio documento =ditTramite.getDitDocumentoProbatorios().get(0);
		Object salida=null;					
		
		if(TipoTramiteEnum.PRORROGA_ESTUDIOS.getCodigo().longValue()==tipoTramite){
			salida = ConstanciaEstudioParser.persisToModel(documento.getDitConstanciaEstudio());
		}else if(TipoTramiteEnum.PRORROGA_ENFERMEDAD.getCodigo().longValue()==tipoTramite){
			
			salida = DictamenIntegranteIncapacitadoParser.persisToModel(documento.getDitDictBeneficiarioInca());
			if(salida == null){
				salida = CertificadoSituacionCriticaParser.persistToModel(documento.getDitCertificadoSitCritica());
			}
			
		}else if(TipoTramiteEnum.PRORROGA_OBSTETRICOS.getCodigo().longValue()==tipoTramite){
			salida = ObstetricoParser.persisToModel(documento.getDitObstetrico());
		}	
		
		
		return salida;
	}
	
	/**
	 * Obtiene una lista de tramites correcpondentes a un grupo de personas y un grupo de estados
	 * @param personas
	 * @param estados
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Tramite> getTramitesPersonas(List<Long> personas, List<Long> estados, Long idPersonaAsegurado) 
	throws Exception{
		List<Tramite> tramites =  null;
		//Si no hay personas no ejecuta la consulta
		if(personas != null && personas.size() > 0){
			try {
				Criteria queryTramitePersona = this.getSession().createCriteria(DitTramitePersonaFisica.class);
				queryTramitePersona.createAlias("ditPersona", "persona");
				queryTramitePersona.add(Restrictions.in("persona.cveIdPersona", personas));
				
				Criteria queryTramite = queryTramitePersona.createCriteria("ditTramite");
				queryTramite.createAlias("dicEstadoTramite", "estadoTram");
				queryTramite.add(Restrictions.in("estadoTram.cveIdEstadoTramite", estados));
				queryTramite.addOrder(Order.desc("fecTramite"));
				queryTramite.setMaxResults(10);
				
				Criteria queryPersonaInteresada = queryTramite.createCriteria("ditSolicitud").createCriteria("ditPersonaInteresadaSols");
				queryPersonaInteresada.createAlias("dicTipoPersonaInteresadaSol", "tipoPerInt");
				queryPersonaInteresada.createAlias("ditPersona", "perInt");
				queryPersonaInteresada.add(Restrictions.eq("perInt.cveIdPersona", idPersonaAsegurado));
				queryPersonaInteresada.add(Restrictions.eq("tipoPerInt.cveTipoInteresadaSol", TipoPersonaInteresadaSolEnum.ASEGURADO_PENSIONADO.getId()));
				
				
				queryTramitePersona.setMaxResults(10);
				
				List<DitTramitePersonaFisica> tramitesPersona = queryTramitePersona.list();
				/*CriteriaBuilder cb = em.getCriteriaBuilder();
				CriteriaQuery<DitTramitePersonaFisica> cQuery = cb
						.createQuery(DitTramitePersonaFisica.class);
		
				Root<DitTramitePersonaFisica> root = cQuery
						.from(DitTramitePersonaFisica.class);
			
				cQuery.select(root);
		
				List<Predicate> predicateList = new ArrayList<Predicate>();
		
				Predicate conjunction1 = root.get("ditTramite").get("dicEstadoTramite").get("cveIdEstadoTramite")
						.as(Integer.class).in(estados);
				
				Predicate conjunction2 = root.get("ditPersona").get("cveIdPersona")
				.as(Integer.class).in(personas);
		
				
				predicateList.add(conjunction1);
				predicateList.add(conjunction2);
		
				Subquery<Integer> queryPerSol = cQuery.subquery(Integer.class);
				Root<DitPersonaInteresadaSol> tablaPerSol = queryPerSol.from(DitPersonaInteresadaSol.class);
				Path<Integer> selecPerSol = tablaPerSol.get("ditSolicitud").get("cveIdSolicitud");
				queryPerSol.select(selecPerSol).distinct(true);
				
				Predicate parametrosPerSol = cb.conjunction();
				parametrosPerSol.getExpressions().add(cb.equal(tablaPerSol.get("ditPersona").get("cveIdPersona").as(Integer.class),idPersonaAsegurado));
				//parametrosPerSol.getExpressions().add(cb.equal(tablaPerSol.get("ditSolicitud").get("dicEstadoSolicitud").get("cveIdEstadoSolicitud").as(Integer.class),EstadoSolicitudEnum.ATENDIDA.getId()));
				parametrosPerSol.getExpressions().add(cb.equal(tablaPerSol.get("dicTipoPersonaInteresadaSol").get("cveTipoInteresadaSol").as(Integer.class),TipoPersonaInteresadaSolEnum.ASEGURADO_PENSIONADO.getId()));
				
				queryPerSol.where(parametrosPerSol);
				
				Predicate conjuntion3 = root.get("ditTramite").get("ditSolicitud").get("cveIdSolicitud").in(queryPerSol);
				predicateList.add(conjuntion3);
				
				Predicate[] predicates = new Predicate[predicateList.size()];
				predicateList.toArray(predicates);
		
				cQuery.where(predicates);
		
				cQuery.orderBy(cb.desc(root.get("ditTramite").get("fecTramite")));
		
				
				// Ejecuta la consulta
				List<DitTramitePersonaFisica> tramitesPersona = em.createQuery(cQuery)
						.setMaxResults(10).getResultList();*/
				
					if (tramitesPersona != null && tramitesPersona.size() > 0) {
						Tramite tramite = null;
						tramites =  new ArrayList<Tramite>();
						for(DitTramitePersonaFisica pf : tramitesPersona){
							 tramite = TramiteParser.persistTomodelCompleto(pf);
							 tramites.add(tramite);
						}
					} 
			} catch (Exception e) {
				log.error("getTramitesPersonas", e);
				throw e;
			}
			
		}		
		
		return tramites;
	}

	@Override
	public TramiteCircunscripcionForanea getCircunscripcionForanea(Long idTramite)
			throws Exception {
		DitCircunscripcionForanea ditCircunscripcion = null;
		
		try {
			ditCircunscripcion = em.find(DitCircunscripcionForanea.class,idTramite);
		} catch (Exception e) {
			log.error("getCircunscripcionForanea", e);
			throw e;
		}
		
		return circunscripcionForaneaParserServiceLocal.persistToModel(ditCircunscripcion);
	}
		
	
	@Override
	public TramiteCircunscripcionForanea getSuspencionCircunscripcionForane(
			Long idTramiteSuspencion) throws Exception {
		
		DitCircunscripcionForanea ditCircunscripcion = null;
		
		try {
			Criteria queryCircunscripcion = this.getSession().createCriteria(DitCircunscripcionForanea.class);
			queryCircunscripcion.createAlias("ditTramiteSuspension", "suspension");
			queryCircunscripcion.add(Restrictions.eq("suspension.cveIdTramite", idTramiteSuspencion));
			
			ditCircunscripcion = (DitCircunscripcionForanea) queryCircunscripcion.uniqueResult();
			/*
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<DitCircunscripcionForanea> circunscripcion = cb.createQuery(DitCircunscripcionForanea.class);
			//Le indicamos de que tabla se seleccionra el contenido
			Root<DitCircunscripcionForanea> tablaCircunscripcion = circunscripcion.from(DitCircunscripcionForanea.class);
			circunscripcion.select(tablaCircunscripcion);
			
			Predicate parametros = cb.conjunction();
			parametros.getExpressions().add(cb.equal(tablaCircunscripcion.get("ditTramiteSuspension").get("cveIdTramite").as(Integer.class), idTramiteSuspencion));
			
			circunscripcion.where(parametros);
			
			ditCircunscripcion = em.createQuery(circunscripcion).getSingleResult();*/
		} catch (Exception e) {
			log.error("getSuspencionCircunscripcionForane", e);
			throw e;
		}
		
		return circunscripcionForaneaParserServiceLocal.persistToModel(ditCircunscripcion);
	}

	@SuppressWarnings("unchecked")
	@Override
	public TramiteCircunscripcionForanea getCircunscripcionForanea(Long idPersona,
			AsignacionNSS nss, boolean autorizacion) throws DerechohabientesBusinessException, Exception {
		
		DitCircunscripcionForanea circunscripcionF = null;
		try {
			Criteria queryCircunscripcion = this.getSession().createCriteria(DitCircunscripcionForanea.class);
			
			if(!autorizacion){
				queryCircunscripcion.createAlias("ditTramiteSuspension", "tramiteS");
				queryCircunscripcion.addOrder(Order.desc("fecFinCircunscripcion"));
			}
			
			if(autorizacion){
				queryCircunscripcion.add(Restrictions.isNull("fecFinCircunscripcion"));
				queryCircunscripcion.add(Restrictions.eq("indCircunscripcionActiva", 1));
			} else {
				queryCircunscripcion.add(Restrictions.isNotNull("tramiteS.cveIdTramite"));
			}
			
			Criteria queryTramite = queryCircunscripcion.createCriteria("ditTramite");
			Criteria queryTramitePersonaFisica = queryTramite.createCriteria("ditTramitePersonaFisica");
			queryTramitePersonaFisica.createAlias("ditPersona", "persona");
			queryTramitePersonaFisica.add(Restrictions.eq("persona.cveIdPersona", idPersona));
			
			Criteria queryPersonaInteresada = queryTramite.createCriteria("ditSolicitud").createCriteria("ditPersonaInteresadaSols");
			queryPersonaInteresada.createAlias("dicTipoPersonaInteresadaSol", "tipoPerInt");
			queryPersonaInteresada.createAlias("ditPersona", "perInt");
			queryPersonaInteresada.add(Restrictions.eq("perInt.cveIdPersona", nss.getIdPersona()));
			queryPersonaInteresada.add(Restrictions.eq("tipoPerInt.cveTipoInteresadaSol", TipoPersonaInteresadaSolEnum.ASEGURADO_PENSIONADO.getId()));
			
			List<DitCircunscripcionForanea> circunscripciones = queryCircunscripcion.list();
			
			/*CriteriaBuilder cb = em.getCriteriaBuilder();
			//Establecemos lo que deseamos obtener
			CriteriaQuery<DitCircunscripcionForanea> circunscripcion = cb.createQuery(DitCircunscripcionForanea.class);
			//Le indicamos de que tabla se seleccionra el contenido
			Root<DitCircunscripcionForanea> tablaCircunscripcion = circunscripcion.from(DitCircunscripcionForanea.class);
			//Le indicamos que seleccionaremos
			circunscripcion.select(tablaCircunscripcion).distinct(true);
			if(!autorizacion)
				circunscripcion.orderBy(cb.desc(tablaCircunscripcion.get("fecFinCircunscripcion")));
			//Le indicamos los parametros de busqueda
			Predicate parametrosCircunscripcion = cb.conjunction();
			
			//buscamos a la persona dentro de los tramites de circunscripcion
			parametrosCircunscripcion.getExpressions().add(cb.equal(tablaCircunscripcion.get("ditTramite")
					.get("ditTramitePersonaFisica").get("ditPersona").get("cveIdPersona").as(Integer.class), idPersona));
			//Checamos si buscamos una autorizacion
			if(autorizacion) {
				//parametrosCircunscripcion.getExpressions().add(cb.equal(tablaCircunscripcion.get("indCircunscripcionActiva"), 1));
				parametrosCircunscripcion.getExpressions().add(cb.isNull(tablaCircunscripcion.get("fecFinCircunscripcion")));
				//parametrosCircunscripcion.getExpressions().add(cb.isNull(tablaCircunscripcion.get("ditTramiteSuspension").get("cveIdTramite")));
			}
			//Esto se ejecuta si queremos una suspension de derechohabiente
			else {
				//parametrosCircunscripcion.getExpressions().add(cb.equal(tablaCircunscripcion.get("indCircunscripcionActiva"), 0));
				//parametrosCircunscripcion.getExpressions().add(cb.isNotNull(tablaCircunscripcion.get("fecFinCircunscripcion")));
				parametrosCircunscripcion.getExpressions().add(cb.isNotNull(tablaCircunscripcion.get("ditTramiteSuspension").get("cveIdTramite")));
			}
			
			//Buscamos lassolicitudesde tipo de circunscripcion
			Subquery<Integer> queryPerSol = circunscripcion.subquery(Integer.class);
			Root<DitPersonaInteresadaSol> tablaPerSol = queryPerSol.from(DitPersonaInteresadaSol.class);
			Path<Integer> selecPerSol = tablaPerSol.get("ditSolicitud").get("cveIdSolicitud");
			queryPerSol.select(selecPerSol).distinct(true);
			
			Predicate parametrosPerSol = cb.conjunction();
			parametrosPerSol.getExpressions().add(cb.equal(tablaPerSol.get("ditPersona").get("cveIdPersona").as(Integer.class),nss.getIdPersona()));
			//parametrosPerSol.getExpressions().add(cb.equal(tablaPerSol.get("ditSolicitud").get("dicEstadoSolicitud").get("cveIdEstadoSolicitud").as(Integer.class),EstadoSolicitudEnum.ATENDIDA.getId()));
			parametrosPerSol.getExpressions().add(cb.equal(tablaPerSol.get("dicTipoPersonaInteresadaSol").get("cveTipoInteresadaSol").as(Integer.class),TipoPersonaInteresadaSolEnum.ASEGURADO_PENSIONADO.getId()));
			//parametrosPerSol.getExpressions().add(cb.equal(tablaPerSol.get("ditSolicitud").get("dicTipoSolicitud").get("cveIdTipoSolicitud").as(Integer.class),TipoSolicitudEnum.CORRECCION.getId()));
			
			queryPerSol.where(parametrosPerSol);
			
			Subquery<Integer> queryTramitePersona = circunscripcion.subquery(Integer.class);
			Root<DitTramitePersonaFisica> tablaTramitePersona = queryTramitePersona.from(DitTramitePersonaFisica.class);
			Path<Integer> selecTramitePersona = tablaTramitePersona .get("ditTramite").get("cveIdTramite");
			queryTramitePersona.select(selecTramitePersona);
			Predicate parametrosTramitePersona = cb.conjunction();
			parametrosTramitePersona.getExpressions().add(cb.equal(tablaTramitePersona.get("ditPersona").get("cveIdPersona").as(Integer.class), idPersona));
			queryTramitePersona.where(parametrosTramitePersona);
			
			parametrosCircunscripcion.getExpressions().add(cb.in(tablaCircunscripcion.get("ditTramite").get("cveIdTramite")).value(queryTramitePersona));
			parametrosCircunscripcion.getExpressions().add(cb.in(tablaCircunscripcion.get("ditTramite").get("ditSolicitud").get("cveIdSolicitud")).value(queryPerSol));
			circunscripcion.where(parametrosCircunscripcion);
			
			
			List<DitCircunscripcionForanea> circunscripciones= em.createQuery(circunscripcion).getResultList();*/
			
			if(circunscripciones !=null && circunscripciones.size() > 0)
				circunscripcionF = circunscripciones.get(0);
		} catch (Exception e) {
			log.error("getCircunscripcionForanea", e);
			throw e;
		}
		
		
		return circunscripcionForaneaParserServiceLocal.persistToModel(circunscripcionF);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Tramite> getTramitesAbierto(List<Long> idPersonas , Long idPersonaAseguradoPensionado) throws Exception{
		
		List<DitTramitePersonaFisica> tramitesPersona = null;
		//Si no hay personas no ejecuta la consulta
			try {
				
				List<Long> estadosCerrados = new ArrayList<Long>();
				estadosCerrados.add(EstadoTramiteEnum.CERRADO.getCodigo().longValue());
				estadosCerrados.add(EstadoTramiteEnum.CANCELADO.getCodigo().longValue());
		
				Criteria queryTramitePersona = this.getSession().createCriteria(DitTramitePersonaFisica.class);
				queryTramitePersona.createAlias("ditPersona", "persona");
				queryTramitePersona.add(Restrictions.in("persona.cveIdPersona", idPersonas));
				
				Criteria queryTramite = queryTramitePersona.createCriteria("ditTramite");
				queryTramite.createAlias("dicEstadoTramite", "estado");
				queryTramite.add(Restrictions.not(Restrictions.in("estado.cveIdEstadoTramite", estadosCerrados)));
				
				Criteria queryPersonaInteresada = queryTramite.createCriteria("ditSolicitud").createCriteria("ditPersonaInteresadaSols");
				queryPersonaInteresada.createAlias("dicTipoPersonaInteresadaSol", "tipoPerInt");
				queryPersonaInteresada.createAlias("ditPersona", "perInt");
				queryPersonaInteresada.add(Restrictions.eq("perInt.cveIdPersona", idPersonaAseguradoPensionado));
				queryPersonaInteresada.add(Restrictions.eq("tipoPerInt.cveTipoInteresadaSol", TipoPersonaInteresadaSolEnum.ASEGURADO_PENSIONADO.getId()));
				
				tramitesPersona = queryTramitePersona.list();
				/*CriteriaBuilder cb = em.getCriteriaBuilder();
				CriteriaQuery<DitTramitePersonaFisica> cQuery = cb.createQuery(DitTramitePersonaFisica.class);
		
				Root<DitTramitePersonaFisica> root = cQuery.from(DitTramitePersonaFisica.class);
				cQuery.select(root).distinct(true);
				
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Predicate conjunction1 = root.get("ditPersona").get("cveIdPersona").as(Integer.class).in(idPersonas);
				predicateList.add(conjunction1);
				Predicate conjunction3 = cb.notEqual(root.get("ditTramite").get("dicEstadoTramite").get("cveIdEstadoTramite").as(Integer.class),EstadoTramiteEnum.CERRADO.getId());
				predicateList.add(conjunction3);

				Subquery<Integer> queryPerSol = cQuery.subquery(Integer.class);
				Root<DitPersonaInteresadaSol> tablaPerSol = queryPerSol.from(DitPersonaInteresadaSol.class);
				Path<Integer> selecPerSol = tablaPerSol.get("ditSolicitud").get("cveIdSolicitud");
				queryPerSol.select(selecPerSol).distinct(true);
				
				Predicate parametrosPerSol = cb.conjunction();
				parametrosPerSol.getExpressions().add(cb.equal(tablaPerSol.get("ditPersona").get("cveIdPersona").as(Integer.class), idPersonaAseguradoPensionado));
				parametrosPerSol.getExpressions().add(cb.equal(tablaPerSol.get("dicTipoPersonaInteresadaSol").get("cveTipoInteresadaSol").as(Integer.class),TipoPersonaInteresadaSolEnum.ASEGURADO_PENSIONADO.getId()));
				
				queryPerSol.where(parametrosPerSol);
				Predicate conjunction6 = cb.in(root.get("ditTramite").get("ditSolicitud").get("cveIdSolicitud")).value(queryPerSol);
				predicateList.add(conjunction6);
			
				Predicate[] predicates = new Predicate[predicateList.size()];
				predicateList.toArray(predicates);
		
				cQuery.where(predicates);
				//cQuery.orderBy(cb.desc(root.get("ditTramite").get("fecRegistroAlta")));
				tramitesPersona = em.createQuery(cQuery).getResultList();*/
			} catch (Exception e) {
				log.error("getTramitesByNss", e);
				throw e;
			}
			
			return TramiteParser.persistTomodelList(tramitesPersona);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Tramite getUltimoTramiteAbierto(Long idPersona , Long idPersonaAseguradoPensionado) throws Exception{
		
		List<DitTramitePersonaFisica> tramitesPersona = null;
		//Si no hay personas no ejecuta la consulta
			try {
				
				List<Long> estadosCerrados = new ArrayList<Long>();
				estadosCerrados.add(EstadoTramiteEnum.CERRADO.getCodigo().longValue());
				estadosCerrados.add(EstadoTramiteEnum.CANCELADO.getCodigo().longValue());
				
				Criteria queryTramitePersona = this.getSession().createCriteria(DitTramitePersonaFisica.class);
				queryTramitePersona.createAlias("ditPersona", "persona");
				queryTramitePersona.add(Restrictions.eq("persona.cveIdPersona", idPersona));
				
				Criteria queryTramite = queryTramitePersona.createCriteria("ditTramite");
				queryTramite.createAlias("dicEstadoTramite", "estado");
				queryTramite.add(Restrictions.not(Restrictions.in("estado.cveIdEstadoTramite",estadosCerrados)));
				
				Criteria queryPersonaInteresada = queryTramite.createCriteria("ditSolicitud").createCriteria("ditPersonaInteresadaSols");
				queryPersonaInteresada.createAlias("dicTipoPersonaInteresadaSol", "tipoPerInt");
				queryPersonaInteresada.createAlias("ditPersona", "perInt");
				queryPersonaInteresada.add(Restrictions.eq("perInt.cveIdPersona", idPersonaAseguradoPensionado));
				queryPersonaInteresada.add(Restrictions.eq("tipoPerInt.cveTipoInteresadaSol", TipoPersonaInteresadaSolEnum.ASEGURADO_PENSIONADO.getId()));
				
				tramitesPersona = queryTramitePersona.list();
				/*CriteriaBuilder cb = em.getCriteriaBuilder();
				CriteriaQuery<DitTramitePersonaFisica> cQuery = cb.createQuery(DitTramitePersonaFisica.class);
		
				Root<DitTramitePersonaFisica> root = cQuery.from(DitTramitePersonaFisica.class);
				cQuery.select(root).distinct(true);
				
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Predicate conjunction1 = cb.equal(root.get("ditPersona").get("cveIdPersona").as(Integer.class),idPersona);
				predicateList.add(conjunction1);
				Predicate conjunction3 = cb.notEqual(root.get("ditTramite").get("dicEstadoTramite").get("cveIdEstadoTramite").as(Integer.class),EstadoTramiteEnum.CERRADO.getId());
				predicateList.add(conjunction3);

				Subquery<Integer> queryPerSol = cQuery.subquery(Integer.class);
				Root<DitPersonaInteresadaSol> tablaPerSol = queryPerSol.from(DitPersonaInteresadaSol.class);
				Path<Integer> selecPerSol = tablaPerSol.get("ditSolicitud").get("cveIdSolicitud");
				queryPerSol.select(selecPerSol).distinct(true);
				
				Predicate parametrosPerSol = cb.conjunction();
				parametrosPerSol.getExpressions().add(cb.equal(tablaPerSol.get("ditPersona").get("cveIdPersona").as(Integer.class), idPersonaAseguradoPensionado));
				parametrosPerSol.getExpressions().add(cb.equal(tablaPerSol.get("dicTipoPersonaInteresadaSol").get("cveTipoInteresadaSol").as(Integer.class),TipoPersonaInteresadaSolEnum.ASEGURADO_PENSIONADO.getId()));
				
				queryPerSol.where(parametrosPerSol);
				Predicate conjunction6 = cb.in(root.get("ditTramite").get("ditSolicitud").get("cveIdSolicitud")).value(queryPerSol);
				predicateList.add(conjunction6);
			
				Predicate[] predicates = new Predicate[predicateList.size()];
				predicateList.toArray(predicates);
		
				cQuery.where(predicates);
				//cQuery.orderBy(cb.desc(root.get("ditTramite").get("fecRegistroAlta")));
				tramitesPersona = em.createQuery(cQuery).getResultList();*/
			} catch (Exception e) {
				log.error("getTramitesByNss", e);
				throw e;
			}
			
			if(tramitesPersona.size() > 0)
				return TramiteParser.persistTomodel(tramitesPersona.get(0));
			else
				return null;
	}

	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Tramite> getTramitePersona(List<Long> idPersonas, List<Long> tipoTramites,
			List<Long> estadoTramites, Long indResultado, Long razonResultado, Long idPersonaInt, Boolean tramiteEnLista)
			throws Exception {
			List<Tramite> tramites =  null;
		
		List<DitTramitePersonaFisica> tramitesPersona = null;
		//Si no hay personas no ejecuta la consulta
			try {
				
				Criteria queryTramitePersona = this.getSession().createCriteria(DitTramitePersonaFisica.class);
				queryTramitePersona.createAlias("ditPersona", "persona");
				queryTramitePersona.add(Restrictions.in("persona.cveIdPersona", idPersonas));
				
				Criteria queryTramite = queryTramitePersona.createCriteria("ditTramite");

				if(tipoTramites != null) {
					queryTramite.createAlias("dicTipoTramite", "tipo");
					if(tramiteEnLista)
						queryTramite.add(Restrictions.in("tipo.cveIdTipoTramite",tipoTramites));
					else
						queryTramite.add(Restrictions.not(Restrictions.in("tipo.cveIdTipoTramite",tipoTramites)));
				}
				if(estadoTramites != null) {
					queryTramite.createAlias("dicEstadoTramite", "estado");
					queryTramite.add(Restrictions.in("estado.cveIdEstadoTramite", estadoTramites));
				}
				
				if(indResultado != null) {
					queryTramite.add(Restrictions.eq("indResultado", new BigDecimal(indResultado)));
				}
				
				if(razonResultado != null) {
					queryTramite.createAlias("dicRazonResultado", "razon");
					queryTramite.add(Restrictions.eq("razon.cveIdRazonResultado",razonResultado));
				}
				
				
				Criteria queryPersonaInteresada = queryTramite.createCriteria("ditSolicitud").createCriteria("ditPersonaInteresadaSols");
				queryPersonaInteresada.createAlias("dicTipoPersonaInteresadaSol", "tipoPerInt");
				queryPersonaInteresada.createAlias("ditPersona", "perInt");
				queryPersonaInteresada.add(Restrictions.eq("perInt.cveIdPersona", idPersonaInt));
				queryPersonaInteresada.add(Restrictions.eq("tipoPerInt.cveTipoInteresadaSol", TipoPersonaInteresadaSolEnum.ASEGURADO_PENSIONADO.getId()));
				
				tramitesPersona = queryTramitePersona.list();
				
	/*			
				CriteriaBuilder cb = em.getCriteriaBuilder();
				CriteriaQuery<DitTramitePersonaFisica> cQuery = cb.createQuery(DitTramitePersonaFisica.class);
		
				Root<DitTramitePersonaFisica> root = cQuery.from(DitTramitePersonaFisica.class);
				cQuery.select(root).distinct(true);
				
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Predicate conjunction1 = root.get("ditPersona").get("cveIdPersona").as(Integer.class).in(idPersonas);
				predicateList.add(conjunction1);
				if(tipoTramites != null) {
					
					Predicate conjunction2 = null;
					if(tramiteEnLista)
						conjunction2 = root.get("ditTramite").get("dicTipoTramite").get("cveIdTipoTramite").as(Integer.class).in(tipoTramites);
					else
						conjunction2 = cb.not(root.get("ditTramite").get("dicTipoTramite").get("cveIdTipoTramite").as(Integer.class).in(tipoTramites));
					
					predicateList.add(conjunction2);
				}
				if(estadoTramites != null) {
					Predicate conjunction3 = root.get("ditTramite").get("dicEstadoTramite").get("cveIdEstadoTramite").as(Integer.class).in(estadoTramites);
					predicateList.add(conjunction3);
				}
				
				if(indResultado != null) {
					Predicate conjunction4 = cb.equal(root.get("ditTramite").get("indResultado").as(Integer.class),indResultado);
					predicateList.add(conjunction4);
				}
				
				if(razonResultado != null) {
					Predicate conjunction5 = cb.equal(root.get("ditTramite").get("dicRazonResultado").get("cveIdRazonResultado").as(Integer.class), razonResultado);
					predicateList.add(conjunction5);
				}
				
				Subquery<Integer> queryPerSol = cQuery.subquery(Integer.class);
				Root<DitPersonaInteresadaSol> tablaPerSol = queryPerSol.from(DitPersonaInteresadaSol.class);
				Path<Integer> selecPerSol = tablaPerSol.get("ditSolicitud").get("cveIdSolicitud");
				queryPerSol.select(selecPerSol).distinct(true);
				
				Predicate parametrosPerSol = cb.conjunction();
				parametrosPerSol.getExpressions().add(cb.equal(tablaPerSol.get("ditPersona").get("cveIdPersona").as(Integer.class), idPersonaInt));
				parametrosPerSol.getExpressions().add(cb.equal(tablaPerSol.get("dicTipoPersonaInteresadaSol").get("cveTipoInteresadaSol").as(Integer.class),TipoPersonaInteresadaSolEnum.ASEGURADO_PENSIONADO.getId()));
				
				queryPerSol.where(parametrosPerSol);
				Predicate conjunction6 = cb.in(root.get("ditTramite").get("ditSolicitud").get("cveIdSolicitud")).value(queryPerSol);
				predicateList.add(conjunction6);
			
				Predicate[] predicates = new Predicate[predicateList.size()];
				predicateList.toArray(predicates);
		
				cQuery.where(predicates);
				tramitesPersona = em.createQuery(cQuery).getResultList();*/
			} catch (Exception e) {
				log.error("getTramitesByNss", e);
				throw e;
			}
			
			tramites= TramiteParser.persistTomodelList(tramitesPersona); 
			
			
		return tramites;
	}

	
	@Override
	public Tramite saveDetalleTramite(Long cveTramite, String detalleTramiteXML) throws Exception{
		Tramite tramite = null;
		log.debug("xml detalle "+detalleTramiteXML);
		DitDetalleTramite detalleTramite = new DitDetalleTramite();
		detalleTramite.setCveIdTramite(cveTramite);
		detalleTramite.setFecRegistroAlta(new Date());
		detalleTramite.setRefDatosTramiteXml(detalleTramiteXML);
		em.persist(detalleTramite);
		em.flush();
		
		return tramite;
	}
	/**
	 * Actualiza el detalle de un tramite (XML)
	 * @param idTramite0
	 * @param detalleTramiteXML
	 */
	@Override
	public void actualizaDetalleTramite(Long idTramite, String detalleTramiteXML)
			throws Exception {
		log.debug("xml detalle "+detalleTramiteXML);
		DitDetalleTramite detalleTramite = new DitDetalleTramite();
		detalleTramite.setCveIdTramite(idTramite);
		detalleTramite.setRefDatosTramiteXml(detalleTramiteXML);
		detalleTramite.setFecRegistroActualizado(new Date());
		em.merge(detalleTramite);
		em.flush();
	}

	/**
	 * @param gestionDocumental the gestionDocumental to set
	 */
	public void setGestionDocumental(
			GestionDocumentalServiceRemote gestionDocumental) {
		this.gestionDocumental = gestionDocumental;
	}

	private String stringTresPosiciones(String original, int tam){
		String resultado = original;
		
		while(resultado.length() < tam){
			resultado = "0"+resultado; 
		}
		
		return resultado;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tramite> findTramitesXPersona(Long idPersonas ,List<Long> estadoTramites) throws DerechohabientesBusinessException,Exception {
		
		List<Tramite> tramites =  null;
		
		List<DitTramitePersonaFisica> tramitesPersona = null;
		//Si no hay personas no ejecuta la consulta
			try {
				
				
				Criteria queryTramitePersona = this.getSession().createCriteria(DitTramitePersonaFisica.class);
				queryTramitePersona.createAlias("ditPersona", "persona");
				queryTramitePersona.add(Restrictions.eq("persona.cveIdPersona", idPersonas));
				
				if(estadoTramites != null) {
					Criteria queryTramite = queryTramitePersona.createCriteria("ditTramite");
					queryTramite.createAlias("dicEstadoTramite", "estado");
					queryTramite.add(Restrictions.in("estado.cveIdEstadoTramite", estadoTramites));
				}
				
				tramitesPersona = queryTramitePersona.list();
				
				/*CriteriaBuilder cb = em.getCriteriaBuilder();
				CriteriaQuery<DitTramitePersonaFisica> cQuery = cb.createQuery(DitTramitePersonaFisica.class);
		
				Root<DitTramitePersonaFisica> root = cQuery.from(DitTramitePersonaFisica.class);
				cQuery.select(root).distinct(true);
				
				List<Predicate> predicateList = new ArrayList<Predicate>();
				Predicate conjunction1 = root.get("ditPersona").get("cveIdPersona").as(Integer.class).in(idPersonas);
				predicateList.add(conjunction1);
				
				if(estadoTramites != null) {
					Predicate conjunction3 = root.get("ditTramite").get("dicEstadoTramite").get("cveIdEstadoTramite").as(Integer.class).in(estadoTramites);
					predicateList.add(conjunction3);
				}
			
				Predicate[] predicates = new Predicate[predicateList.size()];
				predicateList.toArray(predicates);
		
				cQuery.where(predicates);
				tramitesPersona = em.createQuery(cQuery).getResultList();*/
			} catch (Exception e) {
				log.error("findTramitesXPersona", e);
				throw e;
			}
			
			tramites= TramiteParser.persistTomodelList(tramitesPersona); 
			
			
		return tramites;	
	}

	@Override
	public String getDescripcionTipoTramiteFromDic(Long idTipoTramite) throws Exception {
		DicTipoTramite dicTipoTramite = null;
		try {
			dicTipoTramite =  em.find(DicTipoTramite.class, idTipoTramite);
		} catch (Exception e) {
			log.error(">>> Error al consultar datos del diccionario de tipos de trámite.", e);
			throw e;
		}
		return dicTipoTramite.getDesTipoTramite();
	}	

}
