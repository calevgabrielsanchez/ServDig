/**
 *  @Cliente: Instituto Mexicano del Seguro Social
 *  @Autor: Hugo Armando Mart�nez Cham�nica
 *  @Proyecto: delta
 *  @Archivo:SujetoObligadoServiceEntity.java
 *  @Paquete:mx.gob.imss.ctirss.delta.gestion.patronal.service.entity
 *  @Fecha:30/04/2012
 */
package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.NonUniqueResultException;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import mx.gob.imss.ctirss.delta.exception.medio.contacto.RegistrarMedioContactoException;
import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.medio.contacto.service.interfaces.MediosContactoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceEntityRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.SujetoObligadoUtilityLocal;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.clasificacion.actividad.economica.ClasificacionActividadEconomicaServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.TipoDomicilioEnum;
import mx.gob.imss.ctirss.delta.model.enums.CausaBajaPatronEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoPersonaEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Bien;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Clasificacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EquipoTransporte;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.EscrituraConstitutiva;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MaquinariaEquipo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MateriaPrima;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Personal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Proceso;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Producto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.RegistroSindicato;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSociedad;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteFisica;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TramiteMoral;
import mx.gob.imss.ctirss.delta.persistence.DgCatMunicipio;
import mx.gob.imss.ctirss.delta.persistence.DgCatMunicipioPK;
import mx.gob.imss.ctirss.delta.persistence.DicModalidad;
import mx.gob.imss.ctirss.delta.persistence.DicSubdelegacion;
import mx.gob.imss.ctirss.delta.persistence.DicTipoPersona;
import mx.gob.imss.ctirss.delta.persistence.DicTipoSociedad;
import mx.gob.imss.ctirss.delta.persistence.DitActaConstitutiva;
import mx.gob.imss.ctirss.delta.persistence.DitBiene;
import mx.gob.imss.ctirss.delta.persistence.DitClasificacion;
import mx.gob.imss.ctirss.delta.persistence.DitEquipoTransporte;
import mx.gob.imss.ctirss.delta.persistence.DitFormaContacto;
import mx.gob.imss.ctirss.delta.persistence.DitLlavePatron;
import mx.gob.imss.ctirss.delta.persistence.DitMaquinariaEquipo;
import mx.gob.imss.ctirss.delta.persistence.DitMateriaPrimaMaterial;
import mx.gob.imss.ctirss.delta.persistence.DitPatSujObligDomMigr;
import mx.gob.imss.ctirss.delta.persistence.DitPatSujObligDomicilio;
import mx.gob.imss.ctirss.delta.persistence.DitPatronGeneral;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaFisica;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaMoral;
import mx.gob.imss.ctirss.delta.persistence.DitPersonafContacto;
import mx.gob.imss.ctirss.delta.persistence.DitPersonafDom;
import mx.gob.imss.ctirss.delta.persistence.DitPersonal;
import mx.gob.imss.ctirss.delta.persistence.DitPersonamContacto;
import mx.gob.imss.ctirss.delta.persistence.DitPersonamDom;
import mx.gob.imss.ctirss.delta.persistence.DitProceso;
import mx.gob.imss.ctirss.delta.persistence.DitProducto;
import mx.gob.imss.ctirss.delta.persistence.DitRepresentanteLegal;
import mx.gob.imss.ctirss.delta.persistence.DitSindicato;
import mx.gob.imss.ctirss.delta.persistence.DitSubdelPatSujOblig;
import mx.gob.imss.ctirss.delta.persistence.PatronesTempInc;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.DomiciliosActivosException;

/**
 * 
 * 
 * @Cliente: Instituto Mexicano del Seguro Social
 * @Autor: Hugo Armando Mart�nez Cham�nica
 * @Proyecto: delta
 * @Archivo: SujetoObligadoServiceEntity.java
 * @Paquete: mx.gob.imss.ctirss.delta.gestion.patronal.service.entity
 * @Fecha: 17:59:02
 */
@Stateless
public class SujetoObligadoServiceEntity extends AbstractServiceEntity
		implements SujetoObligadoServiceEntityLocal,SujetoObligadoServiceEntityRemote {

    private static final Logger log = LoggerFactory.getLogger(SujetoObligadoServiceEntity.class);

	@EJB
	private SujetoObligadoUtilityLocal sujetoObligadoUtility;

	@EJB
	private MediosContactoServiceBusinessRemote mediosContactoService;

	@EJB
	ClasificacionActividadEconomicaServiceUtilityLocal clasificacionUtlity;

	
	@Override
	public Boolean isRepresentanteLegal(Long idPersona) {
		
		Criteria queryRL = this.getSession().createCriteria(DitRepresentanteLegal.class);
		queryRL.createAlias("ditPersona", "persona");
		queryRL.add(Restrictions.eq("persona.cveIdPersona", idPersona));
		queryRL.add(Restrictions.isNull("fecRegistroBaja"));
		
		List<DitRepresentanteLegal> lista = (List<DitRepresentanteLegal>) queryRL.list();
		
		if(lista != null && !lista.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}



	@Override
	public SujetoObligado consultarDetalleSujetoObligado(
			SujetoObligado sujetoObligado) {
		log.error("Se consulta el sujeto obligado con id: "
				+ sujetoObligado.getCveIdSujetoObligado());
		// DitPatronSujetoObligado detallePatron = (DitPatronSujetoObligado)
		// this
		// .getSession().load(DitPatronSujetoObligado.class,
		// sujetoObligado.getCveIdSujetoObligado());
		DitPatronSujetoObligado detallePatron = this.em.find(
				DitPatronSujetoObligado.class,
				sujetoObligado.getCveIdSujetoObligado());
		if (detallePatron.getDitPatronGenerals() == null) {
			log
					.error("Se consulta a mano el patron general::: Sucede en el proceso de alta");
			Query query = em
					.createQuery("SELECT pg from DitPatronGeneral pg "
							+ "where pg.ditPatronSujetoObligado.cveIdPatronSujetoObligado = "
							+ sujetoObligado.getCveIdSujetoObligado());
			try {
				DitPatronGeneral patronGeneral = (DitPatronGeneral) query
						.getSingleResult();
				detallePatron
						.setDitPatronGenerals(new ArrayList<DitPatronGeneral>());
				detallePatron.getDitPatronGenerals().add(patronGeneral);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("No se obtuvo resultado para patron general");
			}

		}

		sujetoObligado = sujetoObligadoUtility.convertirEntityToModel(
				detallePatron, sujetoObligado.getTipoPersonaFiscal());

		return sujetoObligado;
	}

	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Long> consultarIdsPatronesSOPorRfcTipoPersona(String rfc,
			TipoPersonaEnum tipoPersona) {
		List<Long> cvesPatrones = null;
		Criteria consulta = this.getSession().createCriteria(DitPatronSujetoObligado.class);
		consulta.setProjection(Projections.property("cveIdPatronSujetoObligado"));
		
		if(tipoPersona.getId() == TipoPersonaEnum.FISICA.getId()) {
			consulta.createAlias("ditPersonaFisica", "ditPersonaFisica");
			consulta.add(Restrictions.eq("ditPersonaFisica.rfc", rfc));
			consulta.add(Restrictions.isNull("ditPersonaFisica.fecRegistroBaja"));
		} else {
			consulta.createAlias("ditPersonaMoral", "ditPersonaMoral");
			consulta.add(Restrictions.eq("ditPersonaMoral.rfc", rfc));
			consulta.add(Restrictions.isNull("ditPersonaMoral.fecRegistroBaja"));
		}
		
		consulta.add(Restrictions.isNull("fecRegistroBaja"));

		cvesPatrones = consulta.list();
		
		return cvesPatrones;
	}



	@Override
	public SujetoObligado consultarPrimerSujetoObligadoByRfc(String rfc,
			TipoPersonaEnum tipoPersona) {
		
		SujetoObligado sujetoEncontrado = null;
		DitPatronSujetoObligado ditPatron = null;
		//Reemplazamos la 񠥮 caso de que el rfc la contenga
		String rfcSinN = this.reemplazarCaracteres(rfc);
		
		Criteria consulta = this.getSession().createCriteria(DitPatronSujetoObligado.class);
		
		//Se verifica el tipo de persona
		if(tipoPersona.getId() == TipoPersonaEnum.FISICA.getId()) {
			consulta.createAlias("ditPersonaFisica", "ditPersonaFisica");
			//Si el rfc sin Ѡes nulo es que no existia el caracter en el rfc
			if(rfcSinN == null) {
				consulta.add(Restrictions.eq("ditPersonaFisica.rfc", rfc));
			} else {
				/*Si el rfc es diferente de null quiere decir que si se reemplazo la Ѡ
				y buscamos donde contenga el rfc con 񠹠sin */
				Criterion consultaConN = Restrictions.eq("ditPersonaFisica.rfc", rfc);
				Criterion consultaSinN = Restrictions.eq("ditPersonaFisica.rfc", rfcSinN);
				
				consulta.add(Restrictions.or(consultaConN, consultaSinN));
			}
			
			consulta.add(Restrictions.isNull("ditPersonaFisica.fecRegistroBaja"));
		} else {
			
			consulta.createAlias("ditPersonaMoral", "ditPersonaMoral");
			
			if(rfcSinN == null) {
				consulta.add(Restrictions.eq("ditPersonaMoral.rfc", rfc));
			} else {
				Criterion consultaConN = Restrictions.eq("ditPersonaMoral.rfc", rfc);
				Criterion consultaSinN = Restrictions.eq("ditPersonaMoral.rfc", rfcSinN);
				
				consulta.add(Restrictions.or(consultaConN, consultaSinN));
				
			}
			consulta.add(Restrictions.isNull("ditPersonaMoral.fecRegistroBaja"));
		}
		
		//Verificamos que el patron no tenga fecha de baja
		consulta.add(Restrictions.isNull("fecRegistroBaja"));
		//Solo obtenemos al prmer patron que corresponda con el rfc
		consulta.setMaxResults(1);

		try {
			ditPatron = (DitPatronSujetoObligado) consulta.uniqueResult();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		
		if(ditPatron != null) {
			sujetoEncontrado = sujetoObligadoUtility.convertirEntityToModel(ditPatron, null);
		}
		
		return sujetoEncontrado;
	}

	private String reemplazarCaracteres(String rfc) {
		String cadenaSinN = null;
		
		cadenaSinN = rfc.replace('\u00f1', '#');
		cadenaSinN = cadenaSinN.replace('\u00d1', '#');
		
		if(cadenaSinN.equals(rfc)) {
			return null;
		}
		
		return cadenaSinN;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seemx.gob.imss.ctirss.delta.gestion.patronal.service.entity.
	 * SujetoObligadoServiceEntityLocal #(mx
	 * .gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SujetoObligado> consultarDetalleSujetoObligadoRFCFisica(
			SujetoObligado sujetoObligado) {
		
		return consultarDetalleSujetoObligadoFisicaCommon(sujetoObligado, false);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SujetoObligado> consultarDetalleSujetoObligadoIDFisica(
			SujetoObligado sujetoObligado) {

		return consultarDetalleSujetoObligadoFisicaCommon(sujetoObligado, true);
	}
	
	/**
	 * Obtiene Registros Patronales Activos/Vigentes
	 * 
	 * Incluye valores para filtrar por: Por IdPersona o por RFC /  
	 * 	Modalidades y Si se requieren los RPs con Baja. 
	 * 
	 * @param sujetoObligado
	 * @param esFiltroPorIdPersona
	 * @return
	 */
	public List<SujetoObligado> consultarDetalleSujetoObligadoFisicaCommon(
			SujetoObligado sujetoObligado, boolean esFiltroPorIdPersona) {
		
		log.error("Consultando detalle de sujeto obligado Persona Fisica");
		
		StringBuffer queryBaja = new StringBuffer();
		queryBaja.append("select registroPatronal from DitPatronSujetoObligado registroPatronal");
		queryBaja.append(" join registroPatronal.ditPatronGenerals pg");
		queryBaja.append(" left outer join pg.ditDtsExtraPatron dep");
		//Filtros por Modalidad
		if((!CollectionUtils.isEmpty(sujetoObligado.getIdsModalidadesConsulta())))
			queryBaja.append(" join registroPatronal.dicModalidad modalidad ");
				
		queryBaja.append(" join registroPatronal.ditPersonaFisica fisica");
		//Filtro por ID o RFC  Persona
		if(esFiltroPorIdPersona){
			queryBaja.append(" join fisica.ditPersona persona ");
			queryBaja.append(" WHERE persona.cveIdPersona = :idFiltro ");
		}else{
			queryBaja.append(" WHERE fisica.rfc = :rfcFiltro ");
		}
		
		queryBaja.append(" AND fisica.fecRegistroBaja IS NULL ");
		
		//Filtro de RPs con Baja
		if(sujetoObligado.getObtenerRPsConBaja()==false){
			queryBaja.append(" AND registroPatronal.fecRegistroBaja IS NULL ");		
			queryBaja.append(" AND ( dep.cveTipoMovto IS NULL OR dep.cveTipoMovto <> :claveBaja ) ");			
		}		
		//Filtros por Modalidad
		if((!CollectionUtils.isEmpty(sujetoObligado.getIdsModalidadesConsulta())))
			queryBaja.append(" AND modalidad.cveIdModalidad IN (:listaModalidades) ");
		
		Query query = this.em.createQuery(queryBaja.toString());		
		
		//Parametros por ID o RFC  Persona
		if(esFiltroPorIdPersona){
			query.setParameter("idFiltro", sujetoObligado.getFisica().getIdPersona());
		}else{
			query.setParameter("rfcFiltro", sujetoObligado.getFisica().getRfc());
		}
		//Parametros para RPs con Baja
		if(sujetoObligado.getObtenerRPsConBaja()==false)
			query.setParameter("claveBaja", new BigDecimal(CausaBajaPatronEnum.BAJA.getClave()));
		//Parametros por Modalidad
		if((!CollectionUtils.isEmpty(sujetoObligado.getIdsModalidadesConsulta())))
			query.setParameter("listaModalidades", sujetoObligado.getIdsModalidadesConsulta());
		
		
		List<DitPatronSujetoObligado> ditSujetosObligados = query.getResultList();		
		List<SujetoObligado> sObligados = new ArrayList<SujetoObligado>();
		if(!CollectionUtils.isEmpty(ditSujetosObligados)){
			for (DitPatronSujetoObligado ditSujetoObligado : ditSujetosObligados) {
				log.error("Conviertiendo sujeto obligado");
				sujetoObligado = sujetoObligadoUtility.convertirEntityToModel(
						ditSujetoObligado,
						sujetoObligado.getTipoPersonaFiscal());
				log.error("Finaliza conviertiendo sujeto obligado");
				sObligados.add(sujetoObligado);
			}
		} else
			return null;
		
		return sObligados;
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @seemx.gob.imss.ctirss.delta.gestion.patronal.service.entity.
	 * SujetoObligadoServiceEntityLocal
	 * #consultarDetalleSujetoObligadoRFCMoral(mx
	 * .gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SujetoObligado> consultarDetalleSujetoObligadoRFCMoral(
			SujetoObligado sujetoObligado) {
		
		return consultarDetalleSujetoObligadoMoralCommon(sujetoObligado, false);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SujetoObligado> consultarDetalleSujetoObligadoIDMoral(
			SujetoObligado sujetoObligado) {
		
		return consultarDetalleSujetoObligadoMoralCommon(sujetoObligado, true);
	}
	
	/**
	 * Obtiene Registros Patronales Activos/Vigentes
	 * 
	 * Incluye valores para filtrar por: Por IdPersona o por RFC /  
	 * 	Modalidades y Si se requieren los RPs con Baja. 
	 * 
	 * @param sujetoObligado
	 * @param esFiltroPorIdPersona
	 * @return
	 */
	private List<SujetoObligado> consultarDetalleSujetoObligadoMoralCommon(
			SujetoObligado sujetoObligado, boolean esFiltroPorIdPersona) {
		
		log.error("Consultando detalle de sujeto obligado Persona Moral ");		
		StringBuffer queryBaja = new StringBuffer();
		queryBaja.append("select registroPatronal from DitPatronSujetoObligado registroPatronal");		
		queryBaja.append(" join registroPatronal.ditPatronGenerals pg");
		queryBaja.append(" left outer join pg.ditDtsExtraPatron dep");
		//Filtros por Modalidad
		if((!CollectionUtils.isEmpty(sujetoObligado.getIdsModalidadesConsulta())))
			queryBaja.append(" join registroPatronal.dicModalidad modalidad ");		
		
		queryBaja.append(" join registroPatronal.ditPersonaMoral moral");
		if(esFiltroPorIdPersona){
			queryBaja.append(" WHERE moral.cveIdPersonaMoral = :idFiltro ");	
		}else{
			queryBaja.append(" WHERE moral.rfc = :rfcFiltro ");	
		}
		queryBaja.append(" AND moral.fecRegistroBaja IS NULL ");
		
		//Filtro de RPs con Baja
		if(sujetoObligado.getObtenerRPsConBaja()==false){
			queryBaja.append(" AND registroPatronal.fecRegistroBaja IS NULL ");		
			queryBaja.append(" AND ( dep.cveTipoMovto IS NULL OR dep.cveTipoMovto <> :claveBaja ) ");
		}
		//Filtros por Modalidad
		if((!CollectionUtils.isEmpty(sujetoObligado.getIdsModalidadesConsulta())))
			queryBaja.append(" AND modalidad.cveIdModalidad IN (:listaModalidades) ");	
		
		Query query = this.em.createQuery(queryBaja.toString());		
		
		//Parametros por ID o RFC  Persona
		if(esFiltroPorIdPersona){
			query.setParameter("idFiltro", sujetoObligado.getMoral().getIdPersona());
		}else{
			query.setParameter("rfcFiltro", sujetoObligado.getMoral().getRfc());
		}
		//Parametros para RPs con Baja		
		if(sujetoObligado.getObtenerRPsConBaja()==false)
			query.setParameter("claveBaja", new BigDecimal(CausaBajaPatronEnum.BAJA.getClave()));
		//Parametros Modalidad
		if((!CollectionUtils.isEmpty(sujetoObligado.getIdsModalidadesConsulta())))
			query.setParameter("listaModalidades", sujetoObligado.getIdsModalidadesConsulta());
		
		
		List<DitPatronSujetoObligado> ditSujetosObligados = query.getResultList();		
		List<SujetoObligado> sObligados = new ArrayList<SujetoObligado>();
		if(!CollectionUtils.isEmpty(ditSujetosObligados)){
			for (DitPatronSujetoObligado ditSujetoObligado : ditSujetosObligados) {
				sujetoObligado = sujetoObligadoUtility.convertirEntityToModel(
						ditSujetoObligado,
						sujetoObligado.getTipoPersonaFiscal());
				sObligados.add(sujetoObligado);
			}
		} else
			return null;

		return sObligados;
	}
	
	/**
	 * Obtiene la informaci�n relacionada al acta constitutiva y registro
	 * sindicato. Esto solo aplica para persona moral.
	 * 
	 * Los datos que se obtienen son los siguientes:
	 * 
	 * ActaConstitutiva: Clave escritura, fecha de expedici�n, n�mero de
	 * folio mercantil, lugar de expedici�n (pendiente), n�mero de la
	 * escritura y n�mero de notaria.
	 * 
	 * Registro Sindicato: Autoridad Laboral, clave registro sindicato, fecha de
	 * registro, n�mero de referencia del registro.
	 * 
	 */
	@Override
	public SujetoObligado obtenerDatosGeneralesPatron(
			SujetoObligado sujetoObligado) {
		EscrituraConstitutiva escrituraConstitutiva = null;
		RegistroSindicato registroSindicato = null;

		if (sujetoObligado.getTipoPersonaFiscal() == TipoPersonaFiscal.FISICA) {
			DitPatronSujetoObligado entity = this.em.find(
					DitPatronSujetoObligado.class,
					sujetoObligado.getCveIdSujetoObligado());
			SujetoObligado aux = sujetoObligadoUtility.convertirEntityToModel(
					entity, sujetoObligado.getTipoPersonaFiscal());
			sujetoObligado.setFisica(aux.getFisica());
		}

		// TODO: para acta o Escritura constitutiva solo aplica para persona
		// moral?
		// TODO: para Sindicato solo aplica para persona moral?
		if (sujetoObligado.getTipoPersonaFiscal() == TipoPersonaFiscal.MORAL) {
			escrituraConstitutiva = llenarEscrituraConstitutiva(sujetoObligado);
			registroSindicato = llenarRegistroSindicato(sujetoObligado);
		}

        System.out.println("\n\n\n\n SUJETO ANTES DE LLENAR PERSONA MORAL.."
                + sujetoObligado);
		sujetoObligado = sujetoObligadoUtility.llenarDatosPersonaMoral(
				sujetoObligado, escrituraConstitutiva, registroSindicato);
        System.out.println("\n\n\n\n SUJETO DESPUES DE LLENAR PERSONA MORAL.."
                + sujetoObligado);
		return sujetoObligado;
	}

    private EscrituraConstitutiva llenarEscrituraConstitutiva(SujetoObligado sujetoObligado) {
        EscrituraConstitutiva escrituraConstitutiva = new EscrituraConstitutiva();
        Criteria criteria1 = this.getSession().createCriteria(
                DitActaConstitutiva.class);
        criteria1.add(
                Restrictions.eq("ditPersonaMoral.cveIdPersonaMoral",
                        sujetoObligado.getMoral().getIdPersona())).add(
                Restrictions.isNull("fecRegistroBaja"));

        List<DitActaConstitutiva> ditActaConstitutivas = criteria1.list();
        
        DitActaConstitutiva ditActaConstitutiva = null;
        
        if(ditActaConstitutivas!=null && ditActaConstitutivas.size()>0)
        	ditActaConstitutiva = ditActaConstitutivas.get(0);
        
		if (ditActaConstitutiva != null) {
			escrituraConstitutiva
					.setCveEscrituraConstitutiva(Long
							.valueOf(ditActaConstitutiva
									.getCveIdActConstitutiva()));
			escrituraConstitutiva.setFechaExpedicion(ditActaConstitutiva
					.getFecExpedicionActa());
			escrituraConstitutiva.setFolioMercantil(ditActaConstitutiva
					.getNumFolioMercantil());

			escrituraConstitutiva.setNumEscritura(ditActaConstitutiva
					.getNumEscritura() != null ? ditActaConstitutiva
					.getNumEscritura() : "0");

			escrituraConstitutiva.setNumNotaria(ditActaConstitutiva
					.getNumNotaria());

			escrituraConstitutiva.setCveIdPersonaMoral(ditActaConstitutiva
					.getDitPersonaMoral().getCveIdPersonaMoral());
			// hacer new Municipio

			Municipio m = new Municipio();
			m.setEntidadFederativa(new EntidadFederativa());
			escrituraConstitutiva.setLugarExpedicion(m);
			escrituraConstitutiva.getLugarExpedicion()
					.getEntidadFederativa()
					.setClave(ditActaConstitutiva.getCveEnt());

			escrituraConstitutiva.getLugarExpedicion().setClave(
					ditActaConstitutiva.getCveMun());
			escrituraConstitutiva
					.setCveIdPatronSujetoObligado(sujetoObligado
							.getCveIdSujetoObligado());

			escrituraConstitutiva.setSeccion(ditActaConstitutiva
					.getNumSeccion());
			escrituraConstitutiva.setFoja(ditActaConstitutiva.getNumFoja());
			escrituraConstitutiva.setVolumen(ditActaConstitutiva
					.getNumVolumen());
			escrituraConstitutiva.setPartida(ditActaConstitutiva
					.getNumPartida());

			// Se agrega c�digo para mostrar la descripcion de entidad y
			// municipio
			if (ditActaConstitutiva.getCveMun() != null) {
				DgCatMunicipioPK id = new DgCatMunicipioPK();
				id.setCveEnt(ditActaConstitutiva.getCveEnt());
				id.setCveMun(ditActaConstitutiva.getCveMun());
				DgCatMunicipio mun = (DgCatMunicipio) this.getSession()
						.load(DgCatMunicipio.class, id);
				escrituraConstitutiva.getLugarExpedicion()
						.getEntidadFederativa()
						.setNombre(mun.getDgCatEstado().getNomEnt());
				escrituraConstitutiva.getLugarExpedicion().setNombre(
						mun.getNomMun());
			}
        }
        return escrituraConstitutiva;
    }

    private RegistroSindicato llenarRegistroSindicato(SujetoObligado sujetoObligado) {
        RegistroSindicato registroSindicato = new RegistroSindicato();

		Criteria criteria2 = this.getSession().createCriteria(
				DitSindicato.class);
		criteria2.add(
				Restrictions.eq("ditPersonaMoral.cveIdPersonaMoral",
						sujetoObligado.getMoral().getIdPersona())).add(
				Restrictions.isNull("fecRegistroBaja"));

		DitSindicato ditSindicato = null;
				
		List<DitSindicato> sindicatos = criteria2.list();
		if(sindicatos!=null && sindicatos.size()>0)
			ditSindicato = sindicatos.get(0);

		if (ditSindicato != null) {
			registroSindicato.setAutoridadLaboral(ditSindicato
					.getDesAutLab());
			registroSindicato.setCveRegistroSindicato(ditSindicato
					.getCveIdSindicato());
			registroSindicato.setFechaRegistro(ditSindicato
					.getFecDocRegistro());
			registroSindicato.setNumReferenciadocRegistro(ditSindicato
					.getNumRefRegistro() );
			registroSindicato.setCveIdPersonaMoral(ditSindicato
					.getDitPersonaMoral().getCveIdPersonaMoral());
			registroSindicato.setCveIdPatronSujetoObligado(sujetoObligado
					.getCveIdSujetoObligado());
		}

        return registroSindicato;
    }

    /**
     * Similar a obtenerDatosGeneralesPatron pero sin cargar datos de persona fisica o moral
     * unicamente carga escrituraConstitutiva y registroSindicato
     * @param sujetoObligado
     * SujetoObligado para llenar escrituraConstitutiva y registroSindicato
     */
    public SujetoObligado completarDatosGeneralesPatron(SujetoObligado sujetoObligado) {
		EscrituraConstitutiva escrituraConstitutiva = null;
		RegistroSindicato registroSindicato = null;

		if (sujetoObligado.getTipoPersonaFiscal() == TipoPersonaFiscal.MORAL) {
			escrituraConstitutiva = llenarEscrituraConstitutiva(sujetoObligado);
			registroSindicato = llenarRegistroSindicato(sujetoObligado);
		}

        log.info("sujeto obligado antes de llenar persona moral {}", sujetoObligado);
		sujetoObligado = sujetoObligadoUtility.llenarDatosPersonaMoral(
				sujetoObligado, escrituraConstitutiva, registroSindicato);
        log.info("sujeto obligado despues de llenar persona moral {}", sujetoObligado);
		return sujetoObligado;
    }


    
	@Override
	public Long getCveIdSujetoObligadoPorCvePatronGeneral(Long cveIdPatronGeneral) {
		
		Criteria queryPatronGeneral = this.getSession().createCriteria(DitPatronGeneral.class);
		queryPatronGeneral.setProjection(Projections.property("ditPatronSujetoObligado"));
		queryPatronGeneral.add(Restrictions.eq("cveIdPatronGeneral",cveIdPatronGeneral));
		
		DitPatronSujetoObligado ditPatron = (DitPatronSujetoObligado) queryPatronGeneral.uniqueResult();
		
		if(ditPatron != null) {
			return ditPatron.getCveIdPatronSujetoObligado();
		}
		
		return null;
	}


	@Override
	public Long getCveIdSujetoObligadoPorRP(String rp) {
		Criteria queryPatronGeneral = this.getSession().createCriteria(DitPatronGeneral.class);
		queryPatronGeneral.createAlias("ditPatronSujetoObligado", "sujetoO");
		queryPatronGeneral.setProjection(Projections.property("sujetoO.cveIdPatronSujetoObligado"));
		queryPatronGeneral.add(Restrictions.eq("regPatron",rp));
		
		List<Long> idsPatronSO = queryPatronGeneral.list();
		
		if(idsPatronSO != null && !idsPatronSO.isEmpty()) {
			return idsPatronSO.get(0);
		}
		
		return null;
	}



	@Override
	public Modalidad getModalidadPorNumModalidad(String numModaliad) {
		
		Criteria queryModalidad = this.getSession().createCriteria(DicModalidad.class);
		queryModalidad.add(Restrictions.eq("numModalidad", numModaliad));
		
		
		List<DicModalidad> modalidad = queryModalidad.list();
		
		if(modalidad != null && !modalidad.isEmpty()) {
			return sujetoObligadoUtility.convertirEntityToModelModalidad(modalidad.get(0));
		}
	
		return null;
	}
	
	@Override
	public List<Modalidad> getModalidadades() {
		
		Criteria queryModalidad = this.getSession().createCriteria(DicModalidad.class);
		queryModalidad.add(Restrictions.isNull("fecRegistroBaja"));
		
		List<DicModalidad> modalidad =  queryModalidad.list();
		
		List<Modalidad> lstModalidades = new ArrayList<Modalidad>();
		if(modalidad != null && !modalidad.isEmpty()) {
			for(DicModalidad modalidadBd :modalidad ) {
			  lstModalidades.add(sujetoObligadoUtility.convertirEntityToModelModalidad(modalidadBd));
			}
			return lstModalidades;
		}else
			return null;
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @seemx.gob.imss.ctirss.delta.gestion.patronal.service.entity.
	 * SujetoObligadoServiceEntityLocal
	 * #obtenerDetallesRegistroPatronal(mx.gob.imss
	 * .ctirss.delta.model.gestion.patronal.SujetoObligado)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public SujetoObligado obtenerDetallesRegistroPatronal(
			SujetoObligado sujetoObligado) {
		Criteria criteria = this.getSession().createCriteria(
				DitClasificacion.class);
		criteria.createAlias("ditPatronSujetoObligado",
				"ditPatronSujetoObligado").add(
				Restrictions.eq(
						"ditPatronSujetoObligado.cveIdPatronSujetoObligado",
						sujetoObligado.getCveIdSujetoObligado()));

		Criteria criteriaPatron = this.getSession().createCriteria(
				DitPatronSujetoObligado.class);
		criteriaPatron.add(Restrictions.eq("cveIdPatronSujetoObligado",
				sujetoObligado.getCveIdSujetoObligado()));
		DitPatronSujetoObligado patron = (DitPatronSujetoObligado) criteriaPatron
				.uniqueResult();
		if(patron.getDitPersonaFisica()!=null)
			sujetoObligado.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);
		else if(patron.getDitPersonaMoral()!=null)
			sujetoObligado.setTipoPersonaFiscal(TipoPersonaFiscal.MORAL);
		
		if(StringUtils.isBlank(sujetoObligado.getNumeroRegistroPatronal()) ){
			sujetoObligado = sujetoObligadoUtility.convertirEntityToModelBasic(
					patron, sujetoObligado.getTipoPersonaFiscal());
		}
		if (sujetoObligado.getTipoPersonaFiscal().equals(
				TipoPersonaFiscal.FISICA)) {
			if (sujetoObligado.getFisica() == null) {
				sujetoObligado.setFisica(new Fisica());
				sujetoObligado.getFisica().setIdPersona(
						patron.getDitPersonaFisica().getDitPersona()
								.getCveIdPersona());
				sujetoObligado.getFisica().setCveFisica(
						patron.getDitPersonaFisica().getCveIdPersonaFisica());
			}
		} else if (sujetoObligado.getTipoPersonaFiscal().equals(
				TipoPersonaFiscal.MORAL)) {
			if (sujetoObligado.getMoral() == null) {
				sujetoObligado.setMoral(new Moral());
				sujetoObligado.getMoral().setIdPersona(
						patron.getDitPersonaMoral().getCveIdPersonaMoral());
			}
		}

		List<Clasificacion> clasificaciones = new ArrayList<Clasificacion>();
		List<DitClasificacion> dtClasificaciones = criteria.list();
		for (DitClasificacion entityClasificacion : dtClasificaciones) {
			clasificaciones.add(clasificacionUtlity
					.convertirEntityToModelClasificacion(entityClasificacion));
		}

		Clasificacion clasificacion = new Clasificacion();
		if (clasificaciones.size() > 0)
			clasificacion = clasificaciones.get(0);
		else
			clasificacion = null;

		sujetoObligado.setClasificacion(clasificacion);

		return sujetoObligado;
	}
	
	@Override
	public SujetoObligado obtenerDetallesRegistroPatronalDictamen(
			SujetoObligado sujetoObligado) throws Exception{ 
		Criteria criteria = this.getSession().createCriteria(
				DitClasificacion.class);
		criteria.createAlias("ditPatronSujetoObligado",
				"ditPatronSujetoObligado").add(
				Restrictions.eq(
						"ditPatronSujetoObligado.cveIdPatronSujetoObligado",
						sujetoObligado.getCveIdSujetoObligado()));

		Criteria criteriaPatron = this.getSession().createCriteria(
				DitPatronSujetoObligado.class);
		criteriaPatron.add(Restrictions.eq("cveIdPatronSujetoObligado",
				sujetoObligado.getCveIdSujetoObligado()));
		DitPatronSujetoObligado patron = (DitPatronSujetoObligado) criteriaPatron
				.uniqueResult();
		if(patron.getDitPersonaFisica()!=null) {
			sujetoObligado.setTipoPersonaFiscal(TipoPersonaFiscal.FISICA);
		} else if(patron.getDitPersonaMoral()!=null) {
			sujetoObligado.setTipoPersonaFiscal(TipoPersonaFiscal.MORAL);
		}
			
		
		if(StringUtils.isBlank(sujetoObligado.getNumeroRegistroPatronal()) ){
		
			sujetoObligado = sujetoObligadoUtility.convertirEntityToModelBasicDictamen(
					patron, sujetoObligado.getTipoPersonaFiscal());
		}
		
		List<Clasificacion> clasificaciones = new ArrayList<Clasificacion>();
		List<DitClasificacion> dtClasificaciones = criteria.list();
		for (DitClasificacion entityClasificacion : dtClasificaciones) {
			clasificaciones.add(clasificacionUtlity
					.convertirEntityToModelClasificacion(entityClasificacion));
		}

		Clasificacion clasificacion = new Clasificacion();
		if (clasificaciones.size() > 0){
			clasificacion = clasificaciones.get(0);
		} else {
			clasificacion = null;
		}
			

		sujetoObligado.setClasificacion(clasificacion);
		
		return sujetoObligado;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @seemx.gob.imss.ctirss.delta.gestion.patronal.service.entity.
	 * SujetoObligadoServiceEntityLocal # SujetoObligadoIdPersonaFisica
	 * (mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado)
	 */
	@Override
	public SujetoObligado consultarDetalleSujetoObligadoIdPersonaFisica(
			SujetoObligado sujetoObligado) {
		log.error("consultando detalle de sujeto obligado persona fisica");
		DitPatronSujetoObligado ditPatron = null;
		Criteria criteria = this.getSession().createCriteria(
				DitPatronSujetoObligado.class);
		criteria.createAlias("ditPersonaFisica", "ditPersonaFisica");
		criteria.createAlias("ditPersonaFisica.ditPersona", "ditPersona");
		criteria.add(Restrictions.eq("ditPersona.cveIdPersona", sujetoObligado
				.getFisica().getIdPersona()));
		criteria.setMaxResults(1);

		try {
			ditPatron = (DitPatronSujetoObligado) criteria.uniqueResult();
		} catch(HibernateException e) {
			log.error("error al buscar a un patron", e);
		}
		
		if (ditPatron != null) {
			sujetoObligado = sujetoObligadoUtility.convertirEntityToModel(
					ditPatron,
					sujetoObligado.getTipoPersonaFiscal());
		}

		return sujetoObligado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seemx.gob.imss.ctirss.delta.gestion.patronal.service.entity.
	 * SujetoObligadoServiceEntityLocal
	 * #consultarDetalleSujetoObligadoIdPersonaMoral
	 * (mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado)
	 */
	@Override
	public SujetoObligado consultarDetalleSujetoObligadoIdPersonaMoral(
			SujetoObligado sujetoObligado) {
		log.error("consultando detalle de sujeto obligado persona moral");
		DitPatronSujetoObligado ditPatron = null;
		Criteria criteria = this.getSession().createCriteria(
				DitPatronSujetoObligado.class);
		criteria.createAlias("ditPersonaMoral", "ditPersonaMoral").add(
				Restrictions.eq("ditPersonaMoral.cveIdPersonaMoral",
						sujetoObligado.getMoral().getIdPersona()));
		criteria.setMaxResults(1);
		
		
		try {
			ditPatron = (DitPatronSujetoObligado) criteria.uniqueResult();
		} catch(HibernateException e) {
			log.error("error al buscar a un patron", e);
		}
		
		if (ditPatron != null) {
			sujetoObligado = sujetoObligadoUtility.convertirEntityToModel(
					ditPatron,
					sujetoObligado.getTipoPersonaFiscal());
		}

		return sujetoObligado;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.
	 * SujetoObligadoServiceEntityLocal#consultarListaProductos(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Producto> consultarListaProductos(Long idPatronSujetoObligado) {
		Criteria criteria = this.getSession().createCriteria(DitProducto.class);
		criteria.createAlias("ditPatronSujetoObligado",
				"ditPatronSujetoObligado").add(
				Restrictions.eq(
						"ditPatronSujetoObligado.cveIdPatronSujetoObligado",
						idPatronSujetoObligado));
		List<DitProducto> productos = criteria.list();
		List<Producto> listaProductos = Collections.EMPTY_LIST;
		if (productos != null && productos.size() > 0) {
			listaProductos = new ArrayList<Producto>();
			for (DitProducto producto : productos) {
				listaProductos.add(clasificacionUtlity
						.convertirEntityToModelProducto(producto));
			}
		}
		return listaProductos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.
	 * SujetoObligadoServiceEntityLocal
	 * #consultarMateriaPrimaMateriales(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MateriaPrima> consultarMateriaPrimaMateriales(
			Long idPatronSujetoObligado) {
		Criteria criteria = this.getSession().createCriteria(
				DitMateriaPrimaMaterial.class);
		criteria.createAlias("ditPatronSujetoObligado",
				"ditPatronSujetoObligado").add(
				Restrictions.eq(
						"ditPatronSujetoObligado.cveIdPatronSujetoObligado",
						idPatronSujetoObligado));
		List<DitMateriaPrimaMaterial> materiales = criteria.list();
		List<MateriaPrima> listaMateriales = Collections.EMPTY_LIST;
		if (materiales != null && materiales.size() > 0) {
			listaMateriales = new ArrayList<MateriaPrima>();
			for (DitMateriaPrimaMaterial material : materiales) {
				listaMateriales.add(clasificacionUtlity
						.convertirEntityToModelMateriaPrima(material));
			}
		}
		return listaMateriales;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.
	 * SujetoObligadoServiceEntityLocal
	 * #consultarMaquinariaEquipo(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MaquinariaEquipo> consultarMaquinariaEquipo(
			Long idPatronSujetoObligado) {
		Criteria criteria = this.getSession().createCriteria(
				DitMaquinariaEquipo.class);
		criteria.createAlias("ditPatronSujetoObligado",
				"ditPatronSujetoObligado").add(
				Restrictions.eq(
						"ditPatronSujetoObligado.cveIdPatronSujetoObligado",
						idPatronSujetoObligado));
		List<DitMaquinariaEquipo> equipos = criteria.list();
		List<MaquinariaEquipo> listaEquipos = Collections.EMPTY_LIST;
		if (equipos != null && equipos.size() > 0) {
			listaEquipos = new ArrayList<MaquinariaEquipo>();
			for (DitMaquinariaEquipo equipo : equipos) {
				listaEquipos.add(clasificacionUtlity
						.convertirEntityToModelMaquinariaEquipo(equipo));
			}
		}
		return listaEquipos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.
	 * SujetoObligadoServiceEntityLocal#consultarEquipoTranporte(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<EquipoTransporte> consultarEquipoTranporte(
			Long idPatronSujetoObligado) {

		Criteria criteria = this.getSession().createCriteria(
				DitEquipoTransporte.class);
		criteria.createAlias("ditPatronSujetoObligado",
				"ditPatronSujetoObligado").add(
				Restrictions.eq(
						"ditPatronSujetoObligado.cveIdPatronSujetoObligado",
						idPatronSujetoObligado));
		List<DitEquipoTransporte> equipos = criteria.list();
		List<EquipoTransporte> listaEquipos = Collections.EMPTY_LIST;
		if (equipos != null && equipos.size() > 0) {
			listaEquipos = new ArrayList<EquipoTransporte>();
			for (DitEquipoTransporte equipo : equipos) {
				listaEquipos.add(clasificacionUtlity
						.convertirEntityToModelEquipoTransporte(equipo));
			}
		}
		return listaEquipos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.
	 * SujetoObligadoServiceEntityLocal#consultarProcesos(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Proceso> consultarProcesos(Long idPatronSujetoObligado) {
		Criteria criteria = this.getSession().createCriteria(DitProceso.class);
		criteria.createAlias("ditPatronSujetoObligado",
				"ditPatronSujetoObligado").add(
				Restrictions.eq(
						"ditPatronSujetoObligado.cveIdPatronSujetoObligado",
						idPatronSujetoObligado));
		List<DitProceso> procesos = criteria.list();
		List<Proceso> listaProcesos = Collections.EMPTY_LIST;
		if (procesos != null && procesos.size() > 0) {
			listaProcesos = new ArrayList<Proceso>();
			for (DitProceso proceso : procesos) {
				listaProcesos.add(clasificacionUtlity
						.convertirEntityToModelProceso(proceso));
			}
		}

		return listaProcesos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.
	 * SujetoObligadoServiceEntityLocal#consultarPersonal(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Personal> consultarPersonal(Long idPatronSujetoObligado) {
		Criteria criteria = this.getSession().createCriteria(DitPersonal.class);
		criteria.createAlias("ditPatronSujetoObligado",
				"ditPatronSujetoObligado").add(
				Restrictions.eq(
						"ditPatronSujetoObligado.cveIdPatronSujetoObligado",
						idPatronSujetoObligado));
		List<DitPersonal> trabajadores = criteria.list();
		List<Personal> listaPersonal = Collections.EMPTY_LIST;
		if (trabajadores != null && trabajadores.size() > 0) {
			listaPersonal = new ArrayList<Personal>();
			for (DitPersonal trabajador : trabajadores) {
				listaPersonal.add(clasificacionUtlity
						.convertirEntityToModelPersonal(trabajador));
			}
		}
		return listaPersonal;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.
	 * SujetoObligadoServiceEntityLocal
	 * #consultarClaveDomicilioFiscal(java.lang.Long,
	 * mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal)
	 */
	@Override
	public Long consultarClaveDomicilioFiscal(Long idPersona,
			TipoPersonaFiscal tipoPersona) {
		Criteria criteria = null;
		if (tipoPersona.equals(TipoPersonaFiscal.FISICA)) {
			criteria = this.getSession().createCriteria(DitPersonafDom.class);
			criteria.createAlias("ditPersona", "ditPersona").add(
					Restrictions.eq("ditPersona.cveIdPersona", idPersona));
			criteria.createAlias("dicTipoDomicilio", "dicTipoDomicilio").add(
					Restrictions.eq("dicTipoDomicilio.cveIdTipoDomicilio",
							TipoDomicilioEnum.FISCAL.getCodigo()));
			DitPersonafDom pfDom = (DitPersonafDom) criteria.uniqueResult();
			Long idDomicilio = pfDom != null
					&& pfDom.getDgDomicilioGeografico() != null ? pfDom
					.getDgDomicilioGeografico().getDomicilioId() : null;
			return idDomicilio;
		}

		if (tipoPersona.equals(TipoPersonaFiscal.MORAL)) {
			criteria = this.getSession().createCriteria(DitPersonamDom.class);
			criteria.createAlias("ditPersonaMoral", "ditPersonaMoral").add(
					Restrictions.eq("ditPersonaMoral.cveIdPersonaMoral",
							idPersona));
			DitPersonamDom pMDom = (DitPersonamDom) criteria.uniqueResult();
			Long idDomicilio = pMDom != null
					&& pMDom.getDgDomicilioGeografico() != null ? pMDom
					.getDgDomicilioGeografico().getDomicilioId() : null;
			return idDomicilio;
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.
	 * SujetoObligadoServiceEntityLocal
	 * #actualizarNombreComercialFisica(java.lang.Long, java.lang.String)
	 */
	@Override
	public void actualizarNombreComercialFisica(TramiteFisica tFisica) {
		DitPersona ditPersona = (DitPersona) super.getSession().load(
				DitPersona.class, tFisica.getFisica().getIdPersona());

		ditPersona
				.setNomPrimerApellido(tFisica.getFisica().getPrimerApellido());
		ditPersona.setNomSegundoApellido(tFisica.getFisica()
				.getSegundoApellido());
		ditPersona.setNomNombre(tFisica.getFisica().getNombre());
		ditPersona.setCurp(tFisica.getFisica().getCurp());
		ditPersona.setRfc(tFisica.getFisica().getRfc());

		this.getSession().saveOrUpdate(ditPersona);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.
	 * SujetoObligadoServiceEntityLocal
	 * #actualizarNombreComercialMoral(java.lang.Long, java.lang.String)
	 */
	@Override
	public void actualizarNombreComercialMoral(TramiteMoral tMoral) {

		DicTipoSociedad dicTipoSociedad = null;

		if (tMoral.getMoral().getTipoSociedad().getIdTipoSociedad() != null) {
			dicTipoSociedad = (DicTipoSociedad) super.getSession().load(
					DicTipoSociedad.class,
					tMoral.getMoral().getTipoSociedad().getIdTipoSociedad()
							.intValue());
		}

		DitPersonaMoral persona = (DitPersonaMoral) super.getSession().load(
				DitPersonaMoral.class, tMoral.getMoral().getIdPersona());

		// persona.setNombreComercial(tMoral.getMoral().getNombreComercial());
		persona.setRfc(tMoral.getMoral().getRfc());
		persona.setDenominacionRazonSocial(tMoral.getMoral().getRazonSocial());
		persona.setDicTipoSociedad(dicTipoSociedad);

		this.getSession().saveOrUpdate(persona);

	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.
	 * SujetoObligadoServiceEntityLocal
	 * #consultarPorRegistroPatronal(java.lang.String,
	 * mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal)
	 */
	@Override
	public SujetoObligado consultarPorRegistroPatronal(String registroPatronal,
			TipoPersonaFiscal tipoPersona) {
        DitPatronSujetoObligado ditSujeto = consultarPorRegistroPatronalCommon(registroPatronal, tipoPersona);
		if (ditSujeto == null) {
			return null;
		}

		log.error("Patron general: " + ditSujeto.getDitPatronGenerals());

		SujetoObligado sujetoObligado = sujetoObligadoUtility
				.convertirEntityToModel(ditSujeto, tipoPersona);
		
//		if(ditSujeto.getDitPatronGenerals()==null){
//			String queryPg = "select pg from DitPatronGeneral pg where pg.ditPatronSujetoObligado.cveIdPatronSujetoObligado = "+ditSujeto.getCveIdPatronSujetoObligado();
//			Query qPg = em.createQuery(queryPg);
//			DitPatronGeneral ditPatrongeneral = (DitPatronGeneral)qPg.getSingleResult();
//			sujetoObligado.setNumeroRegistroPatronal(ditPatrongeneral.getRegPatron());
//			sujetoObligado.setDigVerificador(ditPatrongeneral.getDigVer());
//		}
		
//		sujetoObligado.setNumeroRegistroPatronal(ditSujeto.get);
		return sujetoObligado;
	}
	
	@Override
	public SujetoObligado consultarPorRegistroPatronalDictamen(String registroPatronal,
			TipoPersonaFiscal tipoPersona) {
        DitPatronSujetoObligado ditSujeto = consultarPorRegistroPatronalCommon(registroPatronal, tipoPersona);
		if (ditSujeto == null) {
			return null;
		}

		log.error("Patron general: " + ditSujeto.getDitPatronGenerals());

		SujetoObligado sujetoObligado = sujetoObligadoUtility
				.convertirEntityToModelDictamen(ditSujeto, tipoPersona);
		return sujetoObligado;
	}

    private DitPatronSujetoObligado consultarPorRegistroPatronalCommon(String registroPatronal, TipoPersonaFiscal tipoPersona) {
		System.out.println("Registro Patronal a consultar: " + registroPatronal);
		System.err.println("Registro Patronal a consultar: " + registroPatronal);
		log.debug("Registro Patronal a consultar: " + registroPatronal);

		String regPatronal = registroPatronal.substring(0, 8);
		log.debug("Registro Patronal a 8 posiciones: " + regPatronal);

		StringBuffer query = new StringBuffer();
		query.append("select pso from DitPatronSujetoObligado pso join pso.ditPatronGenerals pg where ");
		query.append(" pso.fecRegistroBaja is null and ");
		query.append(" pg.regPatron = '" + regPatronal + "'");

		// Criteria c =
		// this.getSession().createCriteria(DitPatronGeneral.class);
		// c.add(Restrictions.eq("regPatron", regPatronal));
		//
		if (registroPatronal.length() > 8) {
			String modalidad = registroPatronal.substring(8, 10);
			log.debug("modalidad: " + modalidad);
			query.append(" and pso.dicModalidad.numModalidad = '" + modalidad
					+ "'");
			// c.createAlias("ditPatronSujetoObligado", "pso");
			// c.createAlias("pso.dicModalidad", "modalidad");
			// c.add(Restrictions.eq("modalidad.numModalidad", modalidad));
		}
		if (registroPatronal.length() == 11) {
			log.debug("Digito verificador: " + registroPatronal.length());
			query.append(" and pg.digVer = '"
					+ registroPatronal.substring(registroPatronal.length() - 1)
					+ "'");
			// c.add(Restrictions.eq("digVer",
			// registroPatronal.substring(registroPatronal.length()-1)));
		}

		Query queryRegistroPatronal = this.em.createQuery(query.toString());

		// DitPatronGeneral patronGeneral = (DitPatronGeneral) c.uniqueResult();
		DitPatronSujetoObligado ditPatronSujetoObligado = null;
		try{
			ditPatronSujetoObligado = (DitPatronSujetoObligado) queryRegistroPatronal.getSingleResult();
		}catch(NoResultException nre){
			log.error("No se encontr󠥬 registron patronal proporcionado");
			return null;
		}

		return ditPatronSujetoObligado;
    }

    @Override
    public SujetoObligado consultarPorRegistroPatronalBasic(String registroPatronal, TipoPersonaFiscal tipoPersona) {
        DitPatronSujetoObligado ditSujeto = consultarPorRegistroPatronalCommon(registroPatronal, tipoPersona);
		if (ditSujeto == null) {
			return null;
		}

        SujetoObligado sujetoObligado = sujetoObligadoUtility.convertirEntityToModelBasic(
                ditSujeto, tipoPersona);

        return sujetoObligado;
    }
	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.
	 * SujetoObligadoServiceEntityLocal#asociarContactos(java.util.List,
	 * mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal,
	 * mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona)
	 */
	@Override
	public void asociarMediosContacto(List<MedioContacto> mediosContacto,
			TipoPersonaFiscal tipoPersona, Persona persona) {
		for (MedioContacto mc : mediosContacto) {
			if (tipoPersona.equals(TipoPersonaFiscal.FISICA)) {
				DitPersonafContacto entity = sujetoObligadoUtility
						.construirPersonaFisicaContactoEntity(mc, persona);

				this.getSession().save(entity);
			} else if (tipoPersona.equals(TipoPersonaFiscal.MORAL)) {
				DitPersonamContacto entity = sujetoObligadoUtility
						.construirPersonaMoralContactoEntity(mc, persona);
				DitFormaContacto formaContactoLoaded = (DitFormaContacto) this
						.getSession().load(
								DitFormaContacto.class,
								entity.getDitFormaContacto()
										.getCveIdFormaContacto());
				entity.setDitFormaContacto(formaContactoLoaded);
				DitPersonaMoral moral = (DitPersonaMoral) this.getSession()
						.load(DitPersonaMoral.class,
								entity.getDitPersonaMoral()
										.getCveIdPersonaMoral());
				entity.setDitPersonaMoral(moral);
				this.getSession().save(entity);
			}

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.
	 * SujetoObligadoServiceEntityLocal#consultarSubdelegacion(java.lang.Long)
	 */
	@Override
	public Subdelegacion consultarSubdelegacion(Long idSujetoObligado) {

		Query query = em
				.createQuery("select sujetoSubdelegacion from DitSubdelPatSujOblig sujetoSubdelegacion "
						+ "where sujetoSubdelegacion.cveIdPatronSujetoObligado = "
						+ idSujetoObligado);
		Subdelegacion subdelegacion = null;
		try {
			DitSubdelPatSujOblig patronSubdelegacion = (DitSubdelPatSujOblig) query
					.getSingleResult();
			subdelegacion = sujetoObligadoUtility
					.convertirEntityToModelSubdelegacion(patronSubdelegacion
							.getDicSubdelegacion());
			return subdelegacion;
		} catch (NoResultException nre) {
			log
					.debug("NO SE ENCONTRO NINGUNA PERSONA CON ESE IDENTIFICADOR ");
		}

		return subdelegacion;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.
	 * SujetoObligadoServiceEntityLocal
	 * #consultarSujetosRepresentadosPorRepresentanteLegal(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<SujetoObligado> consultarSujetosRepresentadosPorRepresentanteLegal(
			Long cveIdPersona) {
		Query query = em.createQuery("SELECT S from DitPatronSujetoObligado S "
				+ "join S.ditRepresentanteLegals RL " + "join RL.ditPersona P "
				+ "where P.cveIdPersona = " + cveIdPersona
				+ " and RL.fecRegistroBaja is null");
		List<SujetoObligado> sujetos = new ArrayList<SujetoObligado>();
		List<DitPatronSujetoObligado> ditSujetos = (List<DitPatronSujetoObligado>) query
				.getResultList();
		for (DitPatronSujetoObligado entity : ditSujetos) {
			TipoPersonaFiscal tipoPersonaFiscal = null;
			if (entity.getDitPersonaFisica() != null)
				tipoPersonaFiscal = TipoPersonaFiscal.FISICA;
			if (entity.getDitPersonaMoral() != null)
				tipoPersonaFiscal = TipoPersonaFiscal.MORAL;
			sujetos.add(sujetoObligadoUtility.convertirEntityToModel(entity,
					tipoPersonaFiscal));
		}

		return sujetos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.
	 * SujetoObligadoServiceEntityLocal#obtenerPersona(java.lang.Long)
	 */
	@Override
	public Persona obtenerPersona(Long cveIdPersona) {
		DitPersona persona = em.find(DitPersona.class, cveIdPersona);

		Fisica fisica = null;
		if (persona.getDitPersonaFisicas() != null
				&& persona.getDitPersonaFisicas().size() > 0)
			fisica = sujetoObligadoUtility
					.convertirEntityToModelPersonaFisica(persona
							.getDitPersonaFisicas().get(0));
		Fisica personaRetorno = new Fisica();
		if (fisica == null) {
			log.info("No encontre a la persona fisica");
			personaRetorno.setIdPersona(persona.getCveIdPersona());
			personaRetorno.setNombre(persona.getNomNombre());
			personaRetorno.setPrimerApellido(persona.getNomPrimerApellido());
			personaRetorno.setSegundoApellido(persona.getNomSegundoApellido());
			personaRetorno.setRfc(persona.getRfc());
			TipoPersona tipoPersona = new TipoPersona();
			tipoPersona.setIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);
			personaRetorno.setTipoPersona(tipoPersona);
		} else {
			log.info("Encontre a la persona fisica");
			personaRetorno = fisica;
		}
		return personaRetorno;
	}

	@Override
	public Persona obtenerPersonaMoral(Long cveIdPersona) {
		DitPersonaMoral persona = em.find(DitPersonaMoral.class, cveIdPersona);
		Moral moral = null;
		if (persona != null) {
			moral = sujetoObligadoUtility
					.convertirEntityToModelPersonaMoral(persona);
		}

		return moral;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.
	 * SujetoObligadoServiceEntityLocal
	 * #claveDomicilioCentroTrabajo(java.lang.Long)
	 */
	@Override
	public Long consultarClaveDomicilioCentroTrabajo(Long idSujetoObligado) {
		Query query = em
				.createQuery("SELECT SOD from DitPatSujObligDomicilio SOD "
						+ "join SOD.ditPatronSujetoObligado SO "
						+ "join SOD.dicTipoDomicilio TP "
						+ "where SO.cveIdPatronSujetoObligado = "
						+ idSujetoObligado
						+ " and SOD.fecRegistroBaja IS NULL and TP.cveIdTipoDomicilio = "
						+ TipoDomicilioEnum.CENTRO_TRABAJO.getCodigo());
			try {
				DitPatSujObligDomicilio sod = (DitPatSujObligDomicilio) query
						.getSingleResult();
				return sod.getDgDomicilioGeografico().getDomicilioId();
			} catch (NoResultException nre) {
				log.debug("NO SE ENCONTRO NINGUN CENTRO DE TRABAJO ");
			}   catch (javax.persistence.NonUniqueResultException nure) {
				log.debug("Entity, El patr�n tiene dos domicilios activos, favor de revisar");
				throw new DomiciliosActivosException("El patr�n tiene dos domicilios activos, favor de revisar.");
			}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.
	 * SujetoObligadoServiceEntityLocal#consultarPersonal(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Bien> consultarBienes(Long idPatronSujetoObligado) {
		Criteria criteria = this.getSession().createCriteria(DitBiene.class);
		criteria.createAlias("ditPatronSujetoObligado",
				"ditPatronSujetoObligado").add(
				Restrictions.eq(
						"ditPatronSujetoObligado.cveIdPatronSujetoObligado",
						idPatronSujetoObligado));
		List<DitBiene> bienes = criteria.list();
		List<Bien> listaBienes = Collections.EMPTY_LIST;
		if (bienes != null && bienes.size() > 0) {
			listaBienes = new ArrayList<Bien>();
			for (DitBiene bien : bienes) {
				listaBienes.add(clasificacionUtlity
						.convertirEntityToModelBien(bien));
			}
		}
		return listaBienes;
	}

	@Override
	public void actualizarDatosGeneralesFisca(Fisica fisica) {
		DitPersonaFisica persona = (DitPersonaFisica) super.getSession().load(
				DitPersonaFisica.class, fisica.getCveFisica());

		persona.getDitPersona().setCurp(fisica.getCurp());
		persona.getDitPersona().setRfc(fisica.getRfc());
		persona.getDitPersona().setNomNombre(fisica.getNombre());
		persona.getDitPersona()
				.setNomPrimerApellido(fisica.getPrimerApellido());
		persona.getDitPersona().setNomSegundoApellido(
				fisica.getSegundoApellido());

		this.getSession().saveOrUpdate(persona);

	}

	@Override
	public void actualizarDatosGeneralesMoral(Moral moral) {
		DitPersonaMoral persona = (DitPersonaMoral) super.getSession().load(
				DitPersonaMoral.class, moral.getIdPersona());

		persona.setDenominacionRazonSocial(moral.getRazonSocial());
		DicTipoSociedad tipoSociedad = new DicTipoSociedad();
		tipoSociedad.setCveIdTipoSociedad(moral.getTipoSociedad()
				.getIdTipoSociedad().intValue());
		persona.setDicTipoSociedad(tipoSociedad);
		persona.setRfc(moral.getRfc());

		this.getSession().saveOrUpdate(persona);
	}

	@Override
	public void eliminarEscrituraConstitutivaDePersona(Long idPersona) {
		DitPersonaMoral pm = em.find(DitPersonaMoral.class, idPersona);
		List<DitActaConstitutiva> actas = pm.getDitActaConstitutivas();
		if (actas != null)
			for (DitActaConstitutiva acta : actas) {
				em.remove(acta);
			}
	}

	@Override
	public void eliminarRegistroSindicatoDePersona(Long idPersona) {
		DitPersonaMoral pm = em.find(DitPersonaMoral.class, idPersona);
		List<DitSindicato> sindicatos = pm.getDitSindicatos();
		if (sindicatos != null)
			for (DitSindicato sindicato : sindicatos) {
				em.remove(sindicato);
			}
	}

	@Override
	public DatosSalidaPaginador<SujetoObligado> consultarRegistrosPatronalesPersonaFisica(
			DatosEntradaPaginador<SujetoObligado> input) {

		StringBuffer bfrPersona = new StringBuffer();
		bfrPersona
				.append("select sujetoObligado from DitPatronSujetoObligado sujetoObligado ");
		bfrPersona.append("join sujetoObligado.ditPersonaFisica fisica ");
		// bfrPersona.append("join fisica.ditPersona persona ");
		bfrPersona.append("where fisica.rfc = '"
				+ input.getModelo().getFisica().getRfc() + "' ");
		bfrPersona.append("and sujetoObligado.fecRegistroBaja is null");

		Query queryTotal = this.em.createQuery(bfrPersona.toString());
		@SuppressWarnings("unchecked")
		List<DitPatronSujetoObligado> ditSujetosObligados = queryTotal
				.getResultList();
		int totalResult = ditSujetosObligados.size();

		// Query query = this.em.createQuery(bfrPersona.toString());
		// query.setFirstResult(input.getiDisplayStart());
		// query.setMaxResults(input.getiDisplayLength());

		// List<DitPatronSujetoObligado> ditSujetosObligados =
		// query.getResultList();
		List<SujetoObligado> sObligados = new ArrayList<SujetoObligado>();
		if (ditSujetosObligados.size() > 0) {
			for (DitPatronSujetoObligado ditSujetoObligado : ditSujetosObligados) {
				SujetoObligado sujetoObligado = sujetoObligadoUtility
						.convertirEntityToModel(ditSujetoObligado,
								TipoPersonaFiscal.FISICA);
				sObligados.add(sujetoObligado);
			}
		} else
			return null;

		DatosSalidaPaginador<SujetoObligado> output = new DatosSalidaPaginador<SujetoObligado>();
		output.setAaData(sObligados);
		output.setiTotalRecords(totalResult);
		output.setiTotalDisplayRecords(totalResult);

		return output;
	}

	@Override
	public DatosSalidaPaginador<SujetoObligado> consultarRegistrosPatronalesPersonaMoral(
			DatosEntradaPaginador<SujetoObligado> input) {
		StringBuffer bfrPersona = new StringBuffer();
		bfrPersona
				.append("select sujetoObligado from DitPatronSujetoObligado sujetoObligado ");
		bfrPersona.append("join sujetoObligado.ditPersonaMoral moral ");
		bfrPersona.append("where moral.rfc = '"
				+ input.getModelo().getMoral().getRfc() + "' ");
		bfrPersona.append("and sujetoObligado.fecRegistroBaja is null");

		Query queryTotal = this.em.createQuery(bfrPersona.toString());
		@SuppressWarnings("unchecked")
		List<DitPatronSujetoObligado> ditSujetosObligados = queryTotal
				.getResultList();
		int totalResult = ditSujetosObligados.size();

		// Query query = this.em.createQuery(bfrPersona.toString());
		// query.setFirstResult(input.getiDisplayStart());
		// query.setMaxResults(input.getiDisplayLength());

		// List<DitPatronSujetoObligado> ditSujetosObligados =
		// query.getResultList();
		List<SujetoObligado> sObligados = new ArrayList<SujetoObligado>();
		if (ditSujetosObligados.size() > 0) {
			for (DitPatronSujetoObligado ditSujetoObligado : ditSujetosObligados) {
				SujetoObligado sujetoObligado = sujetoObligadoUtility
						.convertirEntityToModel(ditSujetoObligado,
								TipoPersonaFiscal.MORAL);
				sObligados.add(sujetoObligado);
			}
		} else
			return null;

		DatosSalidaPaginador<SujetoObligado> output = new DatosSalidaPaginador<SujetoObligado>();
		output.setAaData(sObligados);
		output.setiTotalRecords(totalResult);
		output.setiTotalDisplayRecords(totalResult);

		return output;
	}

	@Override
	public void actualizarNombreComercial(SujetoObligado sujetoObligado) {
		String hql = "update DIT_PATRON_SUJETO_OBLIGADO set DES_NOMBRE_COMERCIAL = :nombreComercial where CVE_ID_PATRON_SUJETO_OBLIGADO = :cveIdPatronSujetoObligado";
		Query query = this.em.createNativeQuery(hql);
		query.setParameter("nombreComercial",
				sujetoObligado.getNombreComercial());
		query.setParameter("cveIdPatronSujetoObligado",
				sujetoObligado.getCveIdSujetoObligado());
		int rowCount = query.executeUpdate();
		System.err.println("Se actualizaron este n�mero de registros: "
				+ rowCount);
	}

	@Override
	public List<MedioContacto> reemplazarMediosContactoDePersona(Persona persona) {
		List<MedioContacto> mediosDeContacto = new ArrayList<MedioContacto>();
		TipoPersonaFiscal tipoPersona = persona.getTipoPersona()
				.getIdTipoPersona().equals(TipoPersona.TIPO_PERSONA_FISICA) ? TipoPersonaFiscal.FISICA
				: TipoPersonaFiscal.MORAL;
		if (persona.getMediosContacto() != null) {
			eliminarMediosDePersona(persona);
			for (MedioContacto medio : persona.getMediosContacto()) {
				medio.setClave(null);
				mediosDeContacto.add(medio);
			}
			try {
				mediosDeContacto = mediosContactoService
						.registrarMedioDeContacto(mediosDeContacto);
				asociarMediosContacto(mediosDeContacto, tipoPersona, persona);
			} catch (RegistrarMedioContactoException e) {
				e.printStackTrace();
			}
		}

		return mediosDeContacto;
	}

	private void eliminarMediosDePersona(Persona persona) {
		if (persona.getTipoPersona().getIdTipoPersona()
				.equals(TipoPersona.TIPO_PERSONA_FISICA)) {
			DitPersona ditPersona = (DitPersona) this.getSession().load(
					DitPersona.class, persona.getIdPersona());
			for (DitPersonafContacto personaContacto : ditPersona
					.getDitPersonafContactos()) {
				this.getSession().delete(personaContacto);
			}
		} else if (persona.getTipoPersona().getIdTipoPersona()
				.equals(TipoPersona.TIPO_PERSONA_MORAL)) {
			DitPersonaMoral ditPersona = (DitPersonaMoral) this.getSession()
					.load(DitPersonaMoral.class, persona.getIdPersona());
			for (DitPersonamContacto personaContacto : ditPersona
					.getDitPersonamContactos()) {
				this.getSession().delete(personaContacto);
			}
		}
	}

	@Override
	public String obtenerDomicilioMigrado(Long cveIdPatronSujetoObligado) {
		DitPatSujObligDomMigr domicilioMigrado = em.find(
				DitPatSujObligDomMigr.class, cveIdPatronSujetoObligado);
		if (domicilioMigrado == null)
			return "";
		StringBuffer domCompleto = new StringBuffer();
		domCompleto.append(domicilioMigrado.getDesDomicilio());
		domCompleto.append(", ");
		domCompleto.append(domicilioMigrado.getDesLocalidad());
		domCompleto.append(", ");
		if (domicilioMigrado.getRefCodigoPostal() != null
				&& !domicilioMigrado.getRefCodigoPostal().equals("")
				&& !domicilioMigrado.getRefCodigoPostal().equals("0")) {
			domCompleto.append("C.P.");
			domCompleto.append(domicilioMigrado.getRefCodigoPostal());
		}
		return domCompleto.toString();
	}

	@Override
	public List<SujetoObligado> consultarRegistrosPatronalesPersonaFisica(
			Long idPersona) {

		StringBuffer bfrPersona = new StringBuffer();
		bfrPersona
				.append("select sujetoObligado from DitPatronSujetoObligado sujetoObligado ");
		bfrPersona.append("join sujetoObligado.ditPatronGenerals pg ");
		bfrPersona.append("left outer join pg.ditDtsExtraPatron dep  ");
		bfrPersona.append("join sujetoObligado.ditPersonaFisica fisica ");
		bfrPersona.append("join fisica.ditPersona persona ");
		bfrPersona.append("where persona.cveIdPersona = " + idPersona + " ");
		bfrPersona.append("and ((sujetoObligado.fecRegistroBaja is null and dep.cveTipoMovto is null) or ");
		bfrPersona.append("(sujetoObligado.fecRegistroBaja is null and dep.cveTipoMovto is not null and dep.cveTipoMovto !=:cveBaja))");

		Query queryTotal = this.em.createQuery(bfrPersona.toString());
		queryTotal.setParameter("cveBaja", new BigDecimal(CausaBajaPatronEnum.BAJA.getClave()));
		@SuppressWarnings("unchecked")
		List<DitPatronSujetoObligado> ditSujetosObligados = queryTotal
				.getResultList();

		List<SujetoObligado> sObligados = new ArrayList<SujetoObligado>();
		if (ditSujetosObligados.size() > 0) {
			for(DitPatronSujetoObligado ditSujetoObligado : ditSujetosObligados) {
				SujetoObligado sujetoObligado = sujetoObligadoUtility
						.convertirEntityToModel(ditSujetoObligado,
								TipoPersonaFiscal.FISICA);
				sObligados.add(sujetoObligado);
			}
		} else
			return new ArrayList<SujetoObligado>();

		return sObligados;
	}

	@Override
	public List<SujetoObligado> consultarRegistrosPatronalesPersonaMoral(
			Long idPersona) {
		StringBuffer bfrPersona = new StringBuffer();
		bfrPersona
				.append("select sujetoObligado from DitPatronSujetoObligado sujetoObligado ");
		bfrPersona.append("join sujetoObligado.ditPatronGenerals pg ");
		bfrPersona.append("left outer join pg.ditDtsExtraPatron dep  ");
		bfrPersona.append("join sujetoObligado.ditPersonaMoral moral ");
		bfrPersona.append("where moral.cveIdPersonaMoral = " + idPersona + " ");
		bfrPersona.append("and ((sujetoObligado.fecRegistroBaja is null and dep.cveTipoMovto is null) or ");
		bfrPersona.append("(sujetoObligado.fecRegistroBaja is null and dep.cveTipoMovto is not null and dep.cveTipoMovto !=:cveBaja))");

		Query queryTotal = this.em.createQuery(bfrPersona.toString());
		queryTotal.setParameter("cveBaja", CausaBajaPatronEnum.BAJA.getClave());
		@SuppressWarnings("unchecked")
		List<DitPatronSujetoObligado> ditSujetosObligados = queryTotal
				.getResultList();

		List<SujetoObligado> sObligados = new ArrayList<SujetoObligado>();
		if (ditSujetosObligados.size() > 0) {
			for (DitPatronSujetoObligado ditSujetoObligado : ditSujetosObligados) {
				SujetoObligado sujetoObligado = sujetoObligadoUtility
						.convertirEntityToModel(ditSujetoObligado,
								TipoPersonaFiscal.MORAL);
				sObligados.add(sujetoObligado);
			}
		} else
			return new ArrayList<SujetoObligado>();

		return sObligados;
	}

	@Override
	public List<SujetoObligado> consultarRegistrosPatronalesPersonaFisicaSoloDatosBase(
			Long idPersona) {

		StringBuffer bfrPersona = new StringBuffer();
		bfrPersona
				.append("select sujetoObligado from DitPatronSujetoObligado sujetoObligado ");
		bfrPersona.append("join sujetoObligado.ditPersonaFisica fisica ");
		bfrPersona.append("join fisica.ditPersona persona ");
		bfrPersona.append("where persona.cveIdPersona = " + idPersona + " ");
		bfrPersona.append("and sujetoObligado.fecRegistroBaja is null");

		Query queryTotal = this.em.createQuery(bfrPersona.toString());
		@SuppressWarnings("unchecked")
		List<DitPatronSujetoObligado> ditSujetosObligados = queryTotal
				.getResultList();

		List<SujetoObligado> sObligados = new ArrayList<SujetoObligado>();
		PatronesTempInc patronesTempInc = null;
		
		if (ditSujetosObligados.size() > 0) {
			for (DitPatronSujetoObligado ditSujetoObligado : ditSujetosObligados) {
				
				Modalidad modalidad = sujetoObligadoUtility.convertirEntityToModelModalidad(ditSujetoObligado.getDicModalidad());
				
				DitPatronGeneral ditPatronGeneral = ditSujetoObligado.getDitPatronGenerals().get(0);
				
				patronesTempInc = getPatronesTempInc(String.valueOf(ditPatronGeneral.getRegPatron()), 
													 String.valueOf(modalidad.getNumModalidad()),
													 String.valueOf(ditPatronGeneral.getDigVer()));
				
				SujetoObligado sujetoObligado = sujetoObligadoUtility
						.convertirEntityToModelSinPersona(ditSujetoObligado, patronesTempInc);
				sObligados.add(sujetoObligado);
			}
		} else
			return new ArrayList<SujetoObligado>();

		return sObligados;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<SujetoObligado> consultarSujetosRepresentadosPorRepresentanteLegalDatosBase(
			Long idPersona) {
		Query query = em.createQuery("SELECT S from DitPatronSujetoObligado S "
				+ "join S.ditRepresentanteLegals RL " + "join RL.ditPersona P "
				+ "where P.cveIdPersona = " + idPersona
				+ " and RL.fecRegistroBaja is null");
		List<SujetoObligado> sujetos = new ArrayList<SujetoObligado>();
		List<DitPatronSujetoObligado> ditSujetos = (List<DitPatronSujetoObligado>) query
				.getResultList();
		for (DitPatronSujetoObligado entity : ditSujetos) {
			TipoPersonaFiscal tipoPersonaFiscal = null;
			if (entity.getDitPersonaFisica() != null)
				tipoPersonaFiscal = TipoPersonaFiscal.FISICA;
			if (entity.getDitPersonaMoral() != null)
				tipoPersonaFiscal = TipoPersonaFiscal.MORAL;
			sujetos.add(sujetoObligadoUtility.convertirEntityToModelDatosPersona(entity, tipoPersonaFiscal));
		}

		return sujetos;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SujetoObligado> consultarSujetosRepresentadosPorRepresentanteLegalDatosBaseFisica(
			Long idPersona) {
		
		List<SujetoObligado> sujetos = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" select new mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado( ");
		sql.append("   S.cveIdPatronSujetoObligado,PF.cveIdPersonaFisica,PERS.cveIdPersona,PF.rfc,PERS.nomNombre,");
		sql.append("   PERS.nomPrimerApellido,PERS.nomSegundoApellido");
		sql.append(") ");
		sql.append(" from DitPatronSujetoObligado as S ");
		sql.append(" join S.ditRepresentanteLegals RL ");
		sql.append(" join RL.ditPersona P ");
		sql.append(" join S.ditPersonaFisica PF ");
		sql.append(" join PF.ditPersona PERS");
		sql.append(" where P.cveIdPersona = " + idPersona);
		sql.append(" and RL.fecRegistroBaja is null");
		
		org.hibernate.Query query = this.getSession().createQuery(sql.toString());
		sujetos = query.list();

		return sujetos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SujetoObligado> consultarSujetosRepresentadosPorRepresentanteLegalDatosBaseMoral(
			Long idPersona) {
		
		List<SujetoObligado> sujetos = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" select new mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado( ");
		sql.append("   S.cveIdPatronSujetoObligado,");
		sql.append("   PM.cveIdPersonaMoral,PM.rfc,PM.denominacionRazonSocial");
		sql.append(") ");
		sql.append(" from DitPatronSujetoObligado as S ");
		sql.append(" join S.ditRepresentanteLegals RL ");
		sql.append(" join RL.ditPersona P ");
		sql.append(" join S.ditPersonaMoral PM ");
		sql.append(" where P.cveIdPersona = " + idPersona);
		sql.append(" and RL.fecRegistroBaja is null");
		
		org.hibernate.Query query = this.getSession().createQuery(sql.toString());
		sujetos = query.list();

		return sujetos;
	}
	
	@Override
	public List<Fisica> consultarRepresentadosFisicosPorRepresentanteLegal(
			Long idPersona) {
		
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select new mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica(PR.cveIdPersona, PFR.cveIdPersonaFisica,PR.nomNombre, PR.nomPrimerApellido, PR.nomSegundoApellido, ");
		sql.append(" PR.rfc, PR.curp)");
		sql.append(" from DitRepresentanteLegal as RL ");
		sql.append(" join RL.ditPersona P ");
		sql.append(" join RL.ditPersonaFisicaRepresentada PFR ");
		sql.append(" join PFR.ditPersona PR ");
		sql.append(" where P.cveIdPersona = " + idPersona);
		sql.append(" and RL.fecRegistroBaja is null");
		
		org.hibernate.Query query = this.getSession().createQuery(sql.toString());
		List<Fisica> personasFisicasRepresentadas = query.list();

		return personasFisicasRepresentadas;
	}
	
	@Override
	public List<Moral> consultarRepresentadosMoralesPorRepresentanteLegal(
			Long idPersona) {
		
		List<SujetoObligado> sujetos = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" select new mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral(PMR.cveIdPersonaMoral, PMR.rfc, ");
		sql.append(" PMR.denominacionRazonSocial )");
		sql.append(" from DitRepresentanteLegal as RL ");
		sql.append(" join RL.ditPersona P ");
		sql.append(" join RL.ditPersonaMoralRepresentada PMR ");
		sql.append(" where P.cveIdPersona = " + idPersona);
		sql.append(" and RL.fecRegistroBaja is null");
		
		org.hibernate.Query query = this.getSession().createQuery(sql.toString());
		List<Moral> personasMoralesRepresentadas = query.list();

		return personasMoralesRepresentadas;
	}
	
	
	@Override
	public List<SujetoObligado> consultarRegistrosPatronalesPersonaMoralSoloDatosBase(
			Long idPersona) {
		StringBuffer bfrPersona = new StringBuffer();
		bfrPersona
				.append("select sujetoObligado from DitPatronSujetoObligado sujetoObligado ");
		bfrPersona.append("join sujetoObligado.ditPersonaMoral moral ");
		bfrPersona.append("where moral.cveIdPersonaMoral = " + idPersona + " ");
		bfrPersona.append("and sujetoObligado.fecRegistroBaja is null");

		Query queryTotal = this.em.createQuery(bfrPersona.toString());
		@SuppressWarnings("unchecked")
		List<DitPatronSujetoObligado> ditSujetosObligados = queryTotal
				.getResultList();

		List<SujetoObligado> sObligados = new ArrayList<SujetoObligado>();
		PatronesTempInc patronesTempInc = null;
		
		if (ditSujetosObligados.size() > 0) {
			for (DitPatronSujetoObligado ditSujetoObligado : ditSujetosObligados) {
				
				Modalidad modalidad = sujetoObligadoUtility.convertirEntityToModelModalidad(ditSujetoObligado.getDicModalidad());
				DitPatronGeneral ditPatronGeneral = ditSujetoObligado.getDitPatronGenerals().get(0);
				
				patronesTempInc = getPatronesTempInc(String.valueOf(ditPatronGeneral.getRegPatron()), 
													 String.valueOf(modalidad.getNumModalidad()),
													 String.valueOf(ditPatronGeneral.getDigVer()));
				
				SujetoObligado sujetoObligado = sujetoObligadoUtility
						.convertirEntityToModelSinPersona(ditSujetoObligado, patronesTempInc);
				sObligados.add(sujetoObligado);
			}
		} else
			return new ArrayList<SujetoObligado>();

		return sObligados;
	}

	
	
	@Override
	public SujetoObligado getSujetoObligadoByNrpyCvePersonaFisicaMoral(
			SujetoObligado obligado) {
		SujetoObligado encontrado = null;
		String rp = obligado.getNumeroRegistroPatronal().substring(0, 8);
		String mod = obligado.getNumeroRegistroPatronal().substring(8, 10);
		String digV = obligado.getNumeroRegistroPatronal().substring(10);

		Criteria consultaPatronSO = this.getSession().createCriteria(DitPatronSujetoObligado.class);
		consultaPatronSO.createAlias("ditPatronGenerals", "patronG");
		consultaPatronSO.createAlias("dicModalidad", "modalidad");
		consultaPatronSO.add(Restrictions.isNull("fecRegistroBaja"));

		if (obligado.getFisica() != null) {
			Criteria consultaFisica = consultaPatronSO.createCriteria("ditPersonaFisica");
			consultaFisica.createAlias("ditPersona", "persona");
			consultaFisica.add(Restrictions.eq("persona.cveIdPersona", obligado
					.getFisica().getIdPersona()));
		} else {
			Criteria consultaMoral = consultaPatronSO.createCriteria("ditPersonaMoral");
			consultaMoral.add(Restrictions.eq("cveIdPersonaMoral", obligado.getMoral().getIdPersona()));
		}

		// parametros del RP y digito verificador
		consultaPatronSO.add(Restrictions.eq("patronG.regPatron", rp));
		consultaPatronSO.add(Restrictions.eq("patronG.digVer", digV));
		// parametos de la modalidad
		consultaPatronSO.add(Restrictions.eq("modalidad.numModalidad", mod));
		
		DitPatronSujetoObligado dPatron = null;
		try {
			dPatron = (DitPatronSujetoObligado) consultaPatronSO.uniqueResult();
		} catch (NonUniqueResultException e) {
			e.printStackTrace();
		} catch (NoResultException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (dPatron != null) {
			encontrado = sujetoObligadoUtility.convertirEntityToModel(dPatron,
					null);
		}

		return encontrado;
	}



	@Override
	public SujetoObligado getSujetoObligadoByDatosPatronyRfc(
			SujetoObligado obligado) {

		SujetoObligado encontrado = null;
		String rp = obligado.getNumeroRegistroPatronal().substring(0, 8);
		String mod = obligado.getNumeroRegistroPatronal().substring(8, 10);
		String digV = obligado.getNumeroRegistroPatronal().substring(10);

		String clasificacion = obligado.getStringClasificacion();
		String numDivision = "" + clasificacion.charAt(0);
		String grupo = "" + clasificacion.charAt(1);
		Long actividad = new Long("" + clasificacion.charAt(2) + ""
				+ clasificacion.charAt(3));
		//Long clase = new Long("" + clasificacion.charAt(4));

		Criteria consultaPatronSO = this.getSession().createCriteria(
				DitPatronSujetoObligado.class);
		consultaPatronSO.createAlias("ditPatronGenerals", "patronG");
		consultaPatronSO.createAlias("dicModalidad", "modalidad");
		Criteria consultaPatSubDelegacion = consultaPatronSO
				.createCriteria("ditSubdelPatSujOblig");
		Criteria consultaSubDelegacion = consultaPatSubDelegacion
				.createCriteria("dicSubdelegacion");
		Criteria consultaClasificacion = consultaPatronSO.createCriteria(
				"ditClasificacions").createCriteria("dicFraccionClase");
		consultaPatronSO.add(Restrictions.isNull("fecRegistroBaja"));

		if (obligado.getFisica() != null) {
			Criteria consultaFisica = consultaPatronSO
					.createCriteria("ditPersonaFisica");
			consultaFisica.add(Restrictions.eq("rfc", obligado.getFisica()
					.getRfc()));
			consultaFisica.createAlias("ditPersona", "persona");
			consultaFisica.add(Restrictions.ne("persona.cveIdPersona", obligado
					.getFisica().getIdPersona()));
			// consultaFisica.add(Restrictions.eq("persona.rfc",
			// obligado.getFisica().getRfc()));
		} else {
			Criteria consultaMoral = consultaPatronSO
					.createCriteria("ditPersonaMoral");
			consultaMoral.add(Restrictions.ne("cveIdPersonaMoral", obligado
					.getMoral().getIdPersona()));
			consultaMoral.add(Restrictions.eq("rfc", obligado.getMoral()
					.getRfc()));
		}

		// parametros del RP y digito verificador
		consultaPatronSO.add(Restrictions.eq("patronG.regPatron", rp));
		consultaPatronSO.add(Restrictions.eq("patronG.digVer", digV));
		// parametos de la modalidad
		consultaPatronSO.add(Restrictions.eq("modalidad.numModalidad", mod));
		// parametros de la delegacion
		consultaSubDelegacion.createAlias("dicDelegacion", "delegacion");
		consultaSubDelegacion.add(Restrictions.eq("delegacion.cveIdDelegacion",
				obligado.getSubdelegacion().getDelegacion().getId()));
		consultaSubDelegacion.add(Restrictions.eq("cveIdSubdelegacion",
				obligado.getSubdelegacion().getId()));
		
		/*// parametos de la clasificacion - clase
		consultaClasificacion.createAlias("dicClase", "clase");
		consultaClasificacion.add(Restrictions.eq("clase.cveIdClase", clase));*/
		
		// parametos de la clasificacion actividad economica
		Criteria consultaClasi = consultaClasificacion
				.createCriteria("dicFraccion");
		consultaClasi.add(Restrictions.eq("numFraccion", actividad.toString()));
		// parametros de la clasificacio - grupo
		Criteria consultaGrupo = consultaClasi.createCriteria("dicGrupo");
		consultaGrupo.add(Restrictions.eq("numGrupo", grupo));
		// parametos de la clasificacion - division
		consultaGrupo.createAlias("dicDivision", "division");
		consultaGrupo.add(Restrictions.eq("division.numDivision", numDivision));

		DitPatronSujetoObligado dPatron = null;

		try {
			dPatron = (DitPatronSujetoObligado) consultaPatronSO.uniqueResult();
		} catch (NonUniqueResultException e) {
			e.printStackTrace();
		} catch (NoResultException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (dPatron != null) {
			encontrado = sujetoObligadoUtility.convertirEntityToModel(dPatron,
					null);
		}

		return encontrado;
	}

	@Override
	public void updateRelacionSujetoObligadoPersonaMoralFisica(
			SujetoObligado sujeto) {
		DitPatronSujetoObligado ditPatronSujetoObligado = null;
		ditPatronSujetoObligado = em.find(DitPatronSujetoObligado.class,
				sujeto.getCveIdSujetoObligado());
		ditPatronSujetoObligado.setFecRegistroActualizado(new Date());
		ditPatronSujetoObligado.setNombreComercial(sujeto.getNombreComercial());
		ditPatronSujetoObligado.setIndPatronConfirmado(sujeto.getIndPatronConfirmado());
		
		if (sujeto.getFisica() != null) {
			Criteria personaFisica = this.getSession().createCriteria(
					DitPersonaFisica.class);
			personaFisica.createAlias("ditPersona", "persona");
			personaFisica.add(Restrictions.eq("persona.cveIdPersona", sujeto
					.getFisica().getIdPersona()));

			DitPersonaFisica ditPersonaFisica = (DitPersonaFisica) personaFisica
					.uniqueResult();
			ditPatronSujetoObligado.setDitPersonaFisica(ditPersonaFisica);
			ditPatronSujetoObligado.setDitPersonaMoral(null);

			em.merge(ditPatronSujetoObligado);
			updateLlavePatron(ditPatronSujetoObligado);
		} else {
			ditPatronSujetoObligado.setDitPersonaMoral(new DitPersonaMoral());
			ditPatronSujetoObligado.getDitPersonaMoral().setCveIdPersonaMoral(
					sujeto.getMoral().getIdPersona());
			ditPatronSujetoObligado.setDitPersonaFisica(null);

			em.merge(ditPatronSujetoObligado);
			updateLlavePatron(ditPatronSujetoObligado);
		}

	}

	@Override
	public Integer consultarNumeroDeRegistrosPatronalesPorPersonaMunicipioYFraccion(
			Long idPersona, Long idTipoPersona, Long idMunicipioImss,
			Long idFraccion, Long idRegistroPatronalActual) {
		StringBuffer query = new StringBuffer();
		query.append("select pso.* from dit_patron_sujeto_obligado pso ");
		query.append("join dit_municipio_pat_suj_oblig mso on pso.cve_id_patron_sujeto_obligado = mso.cve_id_patron_sujeto_obligado ");
		query.append("join dit_clasificacion cl on cl.cve_id_patron_sujeto_obligado = pso.cve_id_patron_sujeto_obligado ");
		query.append("join dic_fraccion_clase fc on fc.cve_id_fraccion_clase = cl.cve_id_fraccion_clase ");
		query.append("where mso.cve_id_municipio_imss=" + idMunicipioImss);
		query.append(" and fc.cve_id_fraccion = " + idFraccion);

		if (idTipoPersona.equals(TipoPersona.TIPO_PERSONA_FISICA)) {
			query.append(" and pso.cve_id_persona_fisica = " + idPersona);
		} else {
			query.append(" and pso.cve_id_persona_moral = " + idPersona);
		}
		
		if(idRegistroPatronalActual!=null)//Descarta el registro patronal sobre el cual se esta trabajando este valor viene null cuando es un alta
			query.append(" and pso.CVE_ID_PATRON_SUJETO_OBLIGADO != " + idRegistroPatronalActual);
		
		Query emQuery = em.createNativeQuery(query.toString());

		@SuppressWarnings("unchecked")
		List<DitPatronSujetoObligado> registrosPatronales = emQuery
				.getResultList();

		return registrosPatronales != null ? registrosPatronales.size() : 0;
	}

	@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	@Override
	public List<SujetoObligado> getSujetoObligadoByRfcClaseMunicipioModalidad(
			SujetoObligado obligado) {

		// datos grales NRP
		String rp = null;
		String mod = null;
		String digV = null;

		// datos de clasificacion

		String numDivision = null;
		String grupo = null;
		Long actividad = null;
		Long clase = null;

		List<SujetoObligado> encontrados = null;

		Criteria consultaPatronSO = this.getSession().createCriteria(
				DitPatronSujetoObligado.class);
		this.log.debug("entre al metodo con un objeto [" + obligado + "]");
		// filtro para modalidad y que el patrón este vigente
		consultaPatronSO.createAlias("dicModalidad", "modalidad");
		mod = obligado.getModalidad().getNumModalidad();
		consultaPatronSO.add(Restrictions.eq("modalidad.numModalidad", mod));
		consultaPatronSO.add(Restrictions.isNull("fecRegistroBaja"));

		// parametros del RP y digito verificador
		if (obligado.getNumeroRegistroPatronal() != null
				&& !obligado.getNumeroRegistroPatronal().isEmpty()) {
			consultaPatronSO.createAlias("ditPatronGenerals", "patronG");
			rp = obligado.getNumeroRegistroPatronal().substring(0, 8);
			digV = obligado.getNumeroRegistroPatronal().substring(10);
			consultaPatronSO.add(Restrictions.eq("patronG.regPatron", rp));
			consultaPatronSO.add(Restrictions.eq("patronG.digVer", digV));

		}

		// parametos para consulta de rfc de persona fisica o moral
		if (obligado.getFisica() != null) {
			Criteria consultaFisica = consultaPatronSO
					.createCriteria("ditPersonaFisica");
			consultaFisica.add(Restrictions.eq("rfc", obligado.getFisica()
					.getRfc()));
			consultaFisica.createAlias("ditPersona", "persona");
			consultaFisica.add(Restrictions.ne("persona.cveIdPersona", obligado
					.getFisica().getIdPersona()));
			// consultaFisica.add(Restrictions.eq("persona.rfc",
			// obligado.getFisica().getRfc()));
		} else {
			Criteria consultaMoral = consultaPatronSO
					.createCriteria("ditPersonaMoral");
			consultaMoral.add(Restrictions.ne("cveIdPersonaMoral", obligado
					.getMoral().getIdPersona()));
			consultaMoral.add(Restrictions.eq("rfc", obligado.getMoral()
					.getRfc()));
		}

		// parametros par ael municipio IMSS
		if (obligado.getMunicipioIMSS() != null) {
			Criteria consultaPatMunicipio = consultaPatronSO
					.createCriteria("ditMunicipioPatSujOblig");
			consultaPatMunicipio.createAlias("dicMunicipioImss", "municipio");
			// Criteria consultaMunicipioIMSS =
			// consultaPatMunicipio.createCriteria("dicMunicipioImss");

			consultaPatMunicipio.add(Restrictions.eq(
					"municipio.cveIdMunicipioImss", obligado.getMunicipioIMSS()
							.getIdMunicipio()));
		}
		// parametos de la clasificacion - clase
		Clasificacion clasi = obligado.getClasificacion();
		Criteria consultaClasificacion = consultaPatronSO.createCriteria(
				"ditClasificacions").createCriteria("dicFraccionClase");
		consultaClasificacion.createAlias("dicClase", "clase");
		this.log.debug("la clase es "
				+ clasi.getFraccion().getClase().getClave());
		consultaClasificacion.add(Restrictions.eq("clase.desClase", clasi
				.getFraccion().getClase().getDescripcion()));
		// parametos de la clasificacion actividad economica
		Criteria consultaClasi = consultaClasificacion
				.createCriteria("dicFraccion");
		this.log.debug("la fraccion es " + clasi.getFraccion().getId());
		consultaClasi.add(Restrictions.eq("cveIdFraccion", clasi.getFraccion()
				.getId()));

		// List <DitPatronSujetoObligado> lstPatrones = new ArrayList();
		try {
			List<DitPatronSujetoObligado> lstPatrones = consultaPatronSO.list();

			if (lstPatrones != null && !lstPatrones.isEmpty()) {
				encontrados = new ArrayList();
				for (DitPatronSujetoObligado dPatron : lstPatrones) {
					encontrados.add(sujetoObligadoUtility
							.convertirEntityToModel(dPatron, null));
				}
			}

		} catch (NoResultException e) {
			e.printStackTrace();

		} catch (Exception e) {
			e.printStackTrace();
			this.log.error("ocurrio un error al momento de hacer la consulta ",
					e);
		}

		return encontrados;
	}

	@Override
	public List<SujetoObligado> obtenerRegistrosPatronalesExceptoUltimo(
			SujetoObligado sujetoObligado) {

		StringBuffer queryUltimoPatron = new StringBuffer();
		queryUltimoPatron
				.append("select pso.cveIdPatronSujetoObligado from dit_patron_sujeto_obligado pso where ");
		if (sujetoObligado.getTipoPersonaFiscal().equals(
				TipoPersonaFiscal.FISICA))
			queryUltimoPatron
					.append("ditPersonaFisica.ditPersona.cveIdPersona = "
							+ sujetoObligado.getFisica().getIdPersona());
		else
			queryUltimoPatron.append("ditPersonaMoral.cveIdPersonaMoral = "
					+ sujetoObligado.getMoral().getIdPersona());
		queryUltimoPatron
				.append(" and fecRegistroBaja is null order by cveIdPatronSujetoObligado desc");

		Query upQuery = em.createNativeQuery(queryUltimoPatron.toString());
		upQuery.setMaxResults(1);

		Long cveIdUltimoPatron = (Long) upQuery.getSingleResult();

		StringBuffer query = new StringBuffer();
		query.append("select pso.* from dit_patron_sujeto_obligado pso where ");
		if (sujetoObligado.getTipoPersonaFiscal().equals(
				TipoPersonaFiscal.FISICA))
			query.append("ditPersonaFisica.ditPersona.cveIdPersona =  "
					+ sujetoObligado.getFisica().getIdPersona());
		else
			query.append("ditPersonaMoral.cveIdPersonaMoral = "
					+ sujetoObligado.getMoral().getIdPersona());
		query.append(" and fecRegistroBaja is null and cveIdPatronSujetoObligado != :ultimoPatron");

		Query emQuery = em.createNativeQuery(query.toString());
		emQuery.setParameter("ultimoPatron", cveIdUltimoPatron);

		@SuppressWarnings("unchecked")
		List<DitPatronSujetoObligado> registrosPatronales = emQuery
				.getResultList();

		List<SujetoObligado> listaRegistrosPatronales = new ArrayList<SujetoObligado>();

		if (registrosPatronales != null && registrosPatronales.size() > 0) {
			for (DitPatronSujetoObligado ditPatron : registrosPatronales) {
				SujetoObligado regPatronal = sujetoObligadoUtility
						.convertirEntityToModel(ditPatron,
								sujetoObligado.getTipoPersonaFiscal());
				listaRegistrosPatronales.add(regPatronal);
			}
		}

		return listaRegistrosPatronales;
	}

	@Override
	public SujetoObligado consultarRegistroPatronalPorRFCMunicipioYFraccion(
			String rfc, Long idTipoPersona, Long idMunicipioImss,
			Long idFraccion, Long idRegistroPatronalActual) {
		TipoPersonaFiscal tipoPersona =null;
		StringBuffer query = new StringBuffer();
		query.append("select pso.* from dit_patron_sujeto_obligado pso ");
		query.append("join dit_municipio_pat_suj_oblig mso on pso.cve_id_patron_sujeto_obligado = mso.cve_id_patron_sujeto_obligado ");
		query.append("join dit_clasificacion cl on cl.cve_id_patron_sujeto_obligado = pso.cve_id_patron_sujeto_obligado ");
		query.append("join dic_fraccion_clase fc on fc.cve_id_fraccion_clase = cl.cve_id_fraccion_clase ");
		
		if (idTipoPersona.equals(TipoPersona.TIPO_PERSONA_FISICA)) {
			query.append("join dit_persona_fisica pf on pf.cve_id_persona_fisica = pso.cve_id_persona_fisica ");
		}else{
			query.append("join dit_persona_moral pm on pm.cve_id_persona_moral = pso.cve_id_persona_moral ");
		}
		
		query.append("where mso.cve_id_municipio_imss=" + idMunicipioImss);
		query.append(" and fc.cve_id_fraccion = " + idFraccion);

		if (idTipoPersona.equals(TipoPersona.TIPO_PERSONA_FISICA)) {
			query.append(" and pf.rfc = '" + rfc+"'");
			tipoPersona = TipoPersonaFiscal.FISICA;
		} else {
			query.append(" and pm.rfc = '" + rfc+"'");
			tipoPersona = TipoPersonaFiscal.MORAL;
		}
		
		if(idRegistroPatronalActual!=null)//Descarta el registro patronal sobre el cual se esta trabajando este valor viene null cuando es un alta
			query.append(" and pso.CVE_ID_PATRON_SUJETO_OBLIGADO != " + idRegistroPatronalActual);
		
		Query emQuery = em.createNativeQuery(query.toString(), DitPatronSujetoObligado.class);

		@SuppressWarnings("unchecked")
		List<DitPatronSujetoObligado> registrosPatronales = emQuery
				.getResultList();
		
		if(registrosPatronales != null && registrosPatronales.size() > 0){
			log.error("Encontrado: "+registrosPatronales.get(0));
			DitPatronSujetoObligado pso = (DitPatronSujetoObligado)registrosPatronales.get(0);
			
			return sujetoObligadoUtility.convertirEntityToModel(pso, tipoPersona);
		}
		return null;
	}
	
	// Se agrega por INC398780 para obtener NRP del mismo municipio y RFC
	@Override
	public List<SujetoObligado> consultarNRPPorRFCMunicipioYRfcYFraccion(
			String rfc, Long idTipoPersona, Long idMunicipioImss,
			Long idFraccion, Long idRegistroPatronalActual) {
		List<SujetoObligado> psoL = new  ArrayList<SujetoObligado>();
		TipoPersonaFiscal tipoPersona =null;
		StringBuffer query = new StringBuffer();
		query.append("select pso.* from dit_patron_sujeto_obligado pso ");
		query.append("join dit_municipio_pat_suj_oblig mso on pso.cve_id_patron_sujeto_obligado = mso.cve_id_patron_sujeto_obligado ");
		query.append("join dit_clasificacion cl on cl.cve_id_patron_sujeto_obligado = pso.cve_id_patron_sujeto_obligado ");
		query.append("join dic_fraccion_clase fc on fc.cve_id_fraccion_clase = cl.cve_id_fraccion_clase ");
		
		if (idTipoPersona.equals(TipoPersona.TIPO_PERSONA_FISICA)) {
			query.append("join dit_persona_fisica pf on pf.cve_id_persona_fisica = pso.cve_id_persona_fisica ");
		}else{
			query.append("join dit_persona_moral pm on pm.cve_id_persona_moral = pso.cve_id_persona_moral ");
		}
		
		query.append("where mso.cve_id_municipio_imss=" + idMunicipioImss);
		query.append(" and fc.cve_id_fraccion = " + idFraccion);
		
		if (idTipoPersona.equals(TipoPersona.TIPO_PERSONA_FISICA)) {
			query.append(" and pf.rfc = '" + rfc+"'");
			tipoPersona = TipoPersonaFiscal.FISICA;
		} else {
			query.append(" and pm.rfc = '" + rfc+"'");
			tipoPersona = TipoPersonaFiscal.MORAL;
		}
		
		if(idRegistroPatronalActual!=null)//Descarta el registro patronal sobre el cual se esta trabajando este valor viene null cuando es un alta
			query.append(" and pso.CVE_ID_PATRON_SUJETO_OBLIGADO != " + idRegistroPatronalActual);
		
		Query emQuery = em.createNativeQuery(query.toString(), DitPatronSujetoObligado.class);

		@SuppressWarnings("unchecked")
		List<DitPatronSujetoObligado> registrosPatronales = emQuery.getResultList();
		
		if(registrosPatronales != null && registrosPatronales.size() > 0){
			log.error("::: NRP encontrados: " + registrosPatronales.get(0));
			DitPatronSujetoObligado pso = null;
			for (Iterator<DitPatronSujetoObligado> iterator = registrosPatronales.iterator(); iterator.hasNext();) {
				pso = iterator.next();
				psoL.add(sujetoObligadoUtility.convertirEntityToModel(pso, tipoPersona)); 
			}
			return psoL;
		}
		log.debug("::: No se encontraron NRP para " + rfc);
		return null;
	}

	private void updateLlavePatron(DitPatronSujetoObligado ditPatronSujetoObligado) {
		// Se actualiza la llave Patronal
		List<DitPatronGeneral> patronGrals = ditPatronSujetoObligado.getDitPatronGenerals();
		DitPatronGeneral patrongeneral = patronGrals.get(0);
		String nrp = patrongeneral.getRegPatron();
		DicModalidad dicModalidad = ditPatronSujetoObligado.getDicModalidad();
		nrp = nrp + dicModalidad.getNumModalidad();

		DitLlavePatron ditLlavePatron = em.find(DitLlavePatron.class, nrp);
		if (ditLlavePatron == null) {
			ditLlavePatron = new DitLlavePatron();
			ditLlavePatron.setRefBusca(nrp);
		}
		ditLlavePatron.setDitPatronSujetoObligado(ditPatronSujetoObligado);
		ditLlavePatron.setDitPatronGeneral(patrongeneral);

		DicTipoPersona dicTipoPersona = new DicTipoPersona();
		if (ditPatronSujetoObligado.getDitPersonaFisica() != null) {
			dicTipoPersona.setCveIdTipoPersona(TipoPersona.TIPO_PERSONA_FISICA);

			ditLlavePatron.setDitPersonaFisica(ditPatronSujetoObligado.getDitPersonaFisica());
			ditLlavePatron.setDitPersona(ditPatronSujetoObligado.getDitPersonaFisica().getDitPersona());
			ditLlavePatron.setDitPersonaMoral(null);
		} else {
			dicTipoPersona.setCveIdTipoPersona(TipoPersona.TIPO_PERSONA_MORAL);

			ditLlavePatron.setDitPersonaFisica(null);
			ditLlavePatron.setDitPersona(null);
			ditLlavePatron.setDitPersonaMoral(ditPatronSujetoObligado.getDitPersonaMoral());
		}
		ditLlavePatron.setDicTipoPersona(dicTipoPersona);

		em.merge(ditLlavePatron);
	}
	
	@Override
	public Integer validaCveIdPersonaPorRegistroPatronalClaseActivo(String rfc, Integer cveTipoPersona) {
		StringBuffer query = new StringBuffer();
		Integer response = new Integer("0");
		
		try {
			query.append("SELECT distinct(pso.cve_id_patron_sujeto_obligado) from DIT_PATRON_SUJETO_OBLIGADO pso ");
			query.append("JOIN DIT_CLASIFICACION cl ON pso.CVE_ID_PATRON_SUJETO_OBLIGADO = cl.CVE_ID_PATRON_SUJETO_OBLIGADO ");
			query.append("JOIN DIT_PATRON_GENERAL PG ON PG.CVE_ID_PATRON_SUJETO_OBLIGADO = pso.CVE_ID_PATRON_SUJETO_OBLIGADO ");
			query.append("LEFT OUTER JOIN DIT_DTS_EXTRA_PATRON EP ON EP.CVE_ID_PATRON_GENERAL = PG.CVE_ID_PATRON_GENERAL ");
			if (cveTipoPersona.equals(TipoPersona.TIPO_PERSONA_FISICA.intValue())) {
				query.append(" JOIN DIT_PERSONA_FISICA PF ON PF.cve_id_persona_fisica= pso.cve_id_persona_fisica ");
			} else {
				query.append(" JOIN DIT_PERSONA_MORAL PM ON PM.cve_id_persona_moral= pso.cve_id_persona_moral ");
			}
			//Para los registros con la marca RPC
			query.append(" WHERE cl.IND_REG_PAT_CLASE = 1 ");
			
			if (cveTipoPersona.equals(TipoPersona.TIPO_PERSONA_FISICA.intValue())) {
				query.append(" and PF.rfc = '" +rfc+"'");
			} else {
				query.append(" and PM.rfc = '" + rfc+"'");
			}
			
			query.append(" and ( (pso.FEC_REGISTRO_BAJA is null AND ep.CVE_TIPO_MOVTO IS NULL) ");
			query.append(" or  (pso.FEC_REGISTRO_BAJA is null AND ep.CVE_TIPO_MOVTO IS NOT NULL AND  ep.CVE_TIPO_MOVTO!="+CausaBajaPatronEnum.BAJA.getClave()+" ) )");
			
			
			
			Query emQuery = em.createNativeQuery(query.toString());
			@SuppressWarnings("unchecked")
			List<BigDecimal> rpConRPC = emQuery.getResultList();
			
			if (rpConRPC!=null && rpConRPC.size()>0) {
				 response = rpConRPC.size();
			}
			return  response;

		} catch (NoResultException e) {
			// TODO: handle exception
			return response;
		}
	}


	@Override
	public Modalidad getModalidadPorIdSujetoObligado(Long idPatronSujetoObligado) {
		Modalidad modalidad = null;
		
		try {
			Criteria queryModalidad = this.getSession().createCriteria(DitPatronSujetoObligado.class);
			queryModalidad.setProjection(Projections.property("dicModalidad"));
			queryModalidad.add(Restrictions.eq("cveIdPatronSujetoObligado", idPatronSujetoObligado));
			
			DicModalidad dicModalidad = (DicModalidad) queryModalidad.uniqueResult();
			
			if(dicModalidad != null) {
				modalidad = new Modalidad();
				modalidad.setIdModalidad(dicModalidad.getCveIdModalidad());
				modalidad.setNumModalidad(dicModalidad.getNumModalidad());
				modalidad.setDescripcion(dicModalidad.getDesModalidad());
				modalidad.setSiglaAgregadoMedico(dicModalidad.getSiglaAgregadoMedico());
			}
		} catch(HibernateException e) {
			log.error("Ocurrio un error al consultar la modalidad del patron " + idPatronSujetoObligado, e);
		}
		
		
		return modalidad;
	}
	
	
	/**
	 * Consulta la tabla PATRONES_TEMP_INC con los par᭥tros que recibe para obtener el número de trabajadores asociados al Registro Patronal
	 * que se consulta.
	 * 
	 * @param cveRegistroPatronal
	 * @param cveModalidad
	 * @param digitoVerificador
	 * @return PatronesTempInc
	 */
	@Override
	public PatronesTempInc getPatronesTempInc(String cveRegistroPatronal, String cveModalidad, String digitoVerificador) {
		PatronesTempInc patronesTempInc = null;
		
		try{
			Criteria queryPatronesTempInc = this.getSession().createCriteria(PatronesTempInc.class);
			
			queryPatronesTempInc.add(Restrictions.eq("cvePatron", cveRegistroPatronal));
			queryPatronesTempInc.add(Restrictions.eq("cveModalidad", cveModalidad));
			queryPatronesTempInc.add(Restrictions.eq("digVerificador", digitoVerificador));
			
			List<PatronesTempInc> listaPatronesTempInc = (List<PatronesTempInc>) queryPatronesTempInc.list();
			
			if(listaPatronesTempInc != null && listaPatronesTempInc.size() > 0)
				patronesTempInc = listaPatronesTempInc.get(0);
		}catch(HibernateException e){
			log.error("Ocurrio un error al consultar PatronesTempInc con cveRegistroPatronal: " + cveRegistroPatronal+
					"; cveModalidad: "+cveModalidad+"; digitoVerificador: "+digitoVerificador, e);
		}

		return patronesTempInc;
	}

	/**
	 * Consulta basica para obtener solo los datos basicos del patron
	 * 
	 * Registro Patronal
	 * Modalidad(id, descripcion, num, siglas agregado medico)
	 * Digito Verigicador
	 * Datos de la persona fisica (si aplica)
	 * Razon social (persona modal - Si aplica)
	 * @param idPatronGeneral
	 * @return
	 */
	@Override
	public SujetoObligado getDatosBasicosSOporIdPatronGeneral(
			Long idPatronGeneral) {
		SujetoObligado sujeto = null;
		
		String query = "SELECT sujeto.cve_id_patron_sujeto_obligado,pat_gen.REG_PATRON, modalidad.CVE_ID_MODALIDAD, modalidad.NUM_MODALIDAD,"+
				"modalidad.DES_MODALIDAD, modalidad.REF_SIGLA_AGREGADO_MED, pat_gen.DIG_VER, "+
				"persona.NOM_NOMBRE fis_nombre, persona.NOM_PRIMER_APELLIDO fis_pa, "+
				"persona.NOM_SEGUNDO_APELLIDO fis_sa, moral.DENOMINACION_RAZON_SOCIAL nom_moral, sujeto.IND_MIGR_DOM, tipo_soc.DES_TIPO_SOCIEDAD_ABREV  "+
				"from DIT_PATRON_GENERAL pat_gen "+
				"inner join DIT_PATRON_SUJETO_OBLIGADO sujeto on pat_gen.CVE_ID_PATRON_SUJETO_OBLIGADO = sujeto.CVE_ID_PATRON_SUJETO_OBLIGADO "+
				"inner join DIC_MODALIDAD modalidad on sujeto.CVE_ID_MODALIDAD = modalidad.CVE_ID_MODALIDAD "+
				"left outer join DIT_PERSONA_FISICA fisica on sujeto.CVE_ID_PERSONA_FISICA = fisica.CVE_ID_PERSONA_FISICA "+
				"left outer join DIT_PERSONA persona on persona.CVE_ID_PERSONA = fisica.CVE_ID_PERSONA "+
				"left outer join DIT_PERSONA_MORAL moral on sujeto.CVE_ID_PERSONA_MORAL = moral.CVE_ID_PERSONA_MORAL " + 
				"left outer join DIC_TIPO_SOCIEDAD tipo_soc on moral.cve_id_tipo_sociedad  = tipo_soc.cve_id_tipo_sociedad " +
				"where pat_gen.CVE_ID_PATRON_GENERAL = :id";
		SQLQuery queryIDEES = this.getSession().createSQLQuery(query);
		queryIDEES.setParameter("id", idPatronGeneral);
		@SuppressWarnings("unchecked")
		
		List<Object[]> resultado = (List<Object[]>)queryIDEES.list();
		
		if(!resultado.isEmpty()) {
			Object[] patron = resultado.get(0);
			sujeto = this.construirPatron(patron);
			
		} else {
			log.debug("No se encontro el patron");
		}
		return sujeto;
	}



	@Override
	public SujetoObligado getDatosBasicosSOporIdPatronSO(
			Long idPatronSujetoObligado) {
		SujetoObligado sujeto = null;
		
		String query = "SELECT sujeto.cve_id_patron_sujeto_obligado,pat_gen.REG_PATRON, modalidad.CVE_ID_MODALIDAD, modalidad.NUM_MODALIDAD,"+
				"modalidad.DES_MODALIDAD, modalidad.REF_SIGLA_AGREGADO_MED, pat_gen.DIG_VER, "+
				"persona.NOM_NOMBRE fis_nombre, persona.NOM_PRIMER_APELLIDO fis_pa, "+
				"persona.NOM_SEGUNDO_APELLIDO fis_sa, moral.DENOMINACION_RAZON_SOCIAL nom_moral, sujeto.IND_MIGR_DOM , tipo_soc.DES_TIPO_SOCIEDAD_ABREV  "+
				"from DIT_PATRON_GENERAL pat_gen "+
				"inner join DIT_PATRON_SUJETO_OBLIGADO sujeto on pat_gen.CVE_ID_PATRON_SUJETO_OBLIGADO = sujeto.CVE_ID_PATRON_SUJETO_OBLIGADO "+
				"inner join DIC_MODALIDAD modalidad on sujeto.CVE_ID_MODALIDAD = modalidad.CVE_ID_MODALIDAD "+
				"left outer join DIT_PERSONA_FISICA fisica on sujeto.CVE_ID_PERSONA_FISICA = fisica.CVE_ID_PERSONA_FISICA "+
				"left outer join DIT_PERSONA persona on persona.CVE_ID_PERSONA = fisica.CVE_ID_PERSONA "+
				"left outer join DIT_PERSONA_MORAL moral on sujeto.CVE_ID_PERSONA_MORAL = moral.CVE_ID_PERSONA_MORAL "+
				"left outer join DIC_TIPO_SOCIEDAD tipo_soc on moral.cve_id_tipo_sociedad  = tipo_soc.cve_id_tipo_sociedad " +
				"where sujeto.CVE_ID_PATRON_SUJETO_OBLIGADO = :id" ;
		SQLQuery queryIDEES = this.getSession().createSQLQuery(query);
		queryIDEES.setParameter("id", idPatronSujetoObligado);
		@SuppressWarnings("unchecked")
		
		List<Object[]> resultado = (List<Object[]>)queryIDEES.list();
		
		if(!resultado.isEmpty()) {
			Object[] patron = resultado.get(0);
			sujeto = this.construirPatron(patron);
			
		} else {
			log.debug("No se encontro el patron");
		}
		return sujeto;
	}
	
	private SujetoObligado construirPatron(Object[] patron) {
		SujetoObligado sujeto = new SujetoObligado();
		
		Long idPatronSujetoO = ((BigDecimal) patron[0]).longValue();
		String registroPatronal = (String) patron[1];
		Long idModalidad = ((BigDecimal) patron[2]).longValue();
		String numModalidad = (String) patron[3];
		String desModalidad = (String) patron[4];
		String siglaAgredado = (String)patron[5];
		String digitoVerificador = patron[6] != null ? ((Character)patron[6]).toString() :  null;
		String nombreFisica = (String) patron[7];
		String primerApeFisica = (String) patron[8];
		String segundoApeFisica = (String) patron[9];
		String razonSocialMoral = (String) patron[10];
		Long indDomMigrado = patron[11] != null ? ((BigDecimal) patron[11]).longValue(): new Long(0);
		String tipoSociedad = (String) patron[12];
		
		sujeto.setCveIdSujetoObligado(idPatronSujetoO);
		sujeto.setNumeroRegistroPatronal(registroPatronal);
		sujeto.setDigVerificador(digitoVerificador);
		sujeto.setModalidad(new Modalidad());
		sujeto.getModalidad().setIdModalidad(idModalidad);
		sujeto.getModalidad().setNumModalidad(numModalidad);
		sujeto.getModalidad().setDescripcion(desModalidad);
		sujeto.getModalidad().setSiglaAgregadoMedico(siglaAgredado);
		sujeto.setIndMigrDom(indDomMigrado.intValue());
		
		if(razonSocialMoral != null) {
			sujeto.setMoral(new Moral());
			sujeto.getMoral().setRazonSocial(razonSocialMoral);
			
			if(tipoSociedad != null) {
				sujeto.getMoral().setTipoSociedad(new TipoSociedad());
				sujeto.getMoral().getTipoSociedad().setDescripcionAbreviada(tipoSociedad);
			}
		} else if(nombreFisica != null) {
			sujeto.setFisica(new Fisica());
			sujeto.getFisica().setNombre(nombreFisica);
			sujeto.getFisica().setPrimerApellido(primerApeFisica);
			sujeto.getFisica().setSegundoApellido(segundoApeFisica);
		}
		
		return sujeto;
	}



	@Override
	public List<SujetoObligado> consultarSujetosRepresentadosPorRepresentanteLegalRemote(
			Long cveIdPersona) {
		// TODO Auto-generated method stub
		return this.consultarSujetosRepresentadosPorRepresentanteLegal(cveIdPersona);
	}
	
	@Override
	public List<SujetoObligado> consultarRegistrosPatronalesRFCPersonaFisica(
			String strRFC) {

		StringBuffer bfrPersona = new StringBuffer();
		bfrPersona
				.append("select sujetoObligado from DitPatronSujetoObligado sujetoObligado ");
		bfrPersona.append("join sujetoObligado.ditPatronGenerals pg ");
		bfrPersona.append("join sujetoObligado.ditPersonaFisica fisica ");
		bfrPersona.append("where fisica.rfc =  '" + strRFC + "' ");
		bfrPersona.append("and sujetoObligado.fecRegistroBaja is null");
		Query queryTotal = this.em.createQuery(bfrPersona.toString());
		@SuppressWarnings("unchecked")
		List<DitPatronSujetoObligado> ditSujetosObligados = queryTotal
				.getResultList();

		List<SujetoObligado> sObligados = new ArrayList<SujetoObligado>();
		if (ditSujetosObligados.size() > 0) {
			for(DitPatronSujetoObligado ditSujetoObligado : ditSujetosObligados) {
				SujetoObligado sujetoObligado = sujetoObligadoUtility
						.convertirEntityToModel(ditSujetoObligado,
								TipoPersonaFiscal.FISICA);
				sObligados.add(sujetoObligado);
			}
		} else
			return new ArrayList<SujetoObligado>();

		return sObligados;
	}

	@Override
	public List<SujetoObligado> consultarRegistrosPatronalesRFCPersonaMoral(
			String strRFC) {
		StringBuffer bfrPersona = new StringBuffer();
		bfrPersona
				.append("select sujetoObligado from DitPatronSujetoObligado sujetoObligado ");
		bfrPersona.append("join sujetoObligado.ditPatronGenerals pg ");
		bfrPersona.append("join sujetoObligado.ditPersonaMoral moral ");
		bfrPersona.append("where moral.rfc =  '" + strRFC + "' ");
		bfrPersona.append("and sujetoObligado.fecRegistroBaja is null"); 
		Query queryTotal = this.em.createQuery(bfrPersona.toString());
		@SuppressWarnings("unchecked")
		List<DitPatronSujetoObligado> ditSujetosObligados = queryTotal
				.getResultList();

		List<SujetoObligado> sObligados = new ArrayList<SujetoObligado>();
		if (ditSujetosObligados.size() > 0) {
			for (DitPatronSujetoObligado ditSujetoObligado : ditSujetosObligados) {
				SujetoObligado sujetoObligado = sujetoObligadoUtility
						.convertirEntityToModel(ditSujetoObligado,
								TipoPersonaFiscal.MORAL);
				sObligados.add(sujetoObligado);
			}
		} else
			return new ArrayList<SujetoObligado>();

		return sObligados;
	}



	@Override
	public List<SujetoObligado> consultarRegistrosPatronalesRFC(String strRFC, Long tipoPersona) {
		List<SujetoObligado> patrones = new ArrayList<SujetoObligado>();
		StringBuffer query = new StringBuffer();
		query.append("select pat_gen.REG_PATRON, moda.NUM_MODALIDAD, pat_gen.DIG_VER, sujeto.DES_NOMBRE_COMERCIAL, ");
		query.append("sujeto.FEC_REGISTRO_ALTA, pat_gen.CVE_ID_TIPO_REG_PATRON, dts_extra.CVE_TIPO_MOVTO, dts_extra.FEC_INI_HUELGA, ");
		query.append("clasifi.IND_REG_PAT_CLASE, clasifi.CVE_ID_CLASIFICACION ");
		query.append("from DIT_PATRON_SUJETO_OBLIGADO sujeto ");
		query.append("inner join DIC_MODALIDAD moda on sujeto.CVE_ID_MODALIDAD = moda.CVE_ID_MODALIDAD ");
		query.append("inner join DIT_CLASIFICACION clasifi on clasifi.CVE_ID_CLASIFICACION = ( ");
		query.append("select MAX(clasificacion.CVE_ID_CLASIFICACION) from dit_clasificacion clasificacion ");
		query.append("where clasificacion.CVE_ID_PATRON_SUJETO_OBLIGADO = sujeto.CVE_ID_PATRON_SUJETO_OBLIGADO) ");
		query.append("inner join DIT_PATRON_GENERAL pat_gen on sujeto.CVE_ID_PATRON_SUJETO_OBLIGADO = pat_gen.CVE_ID_PATRON_SUJETO_OBLIGADO ");
		
		if (tipoPersona.equals(TipoPersona.TIPO_PERSONA_MORAL)) {
			query.append("inner join DIT_PERSONA_MORAL persona on sujeto.CVE_ID_PERSONA_MORAL = persona.CVE_ID_PERSONA_MORAL ");
		}else{
			query.append("inner join DIT_PERSONA_FISICA persona on sujeto.CVE_ID_PERSONA_FISICA = persona.CVE_ID_PERSONA_FISICA ");
		}
		
		query.append("left outer join DIT_DTS_EXTRA_PATRON dts_extra on pat_gen.CVE_ID_PATRON_GENERAL = dts_extra.CVE_ID_PATRON_GENERAL ");
		query.append("where persona.RFC='"+strRFC+"' and sujeto.FEC_REGISTRO_BAJA is null");
		
		SQLQuery queryPatrones = this.getSession().createSQLQuery(query.toString());
		@SuppressWarnings("unchecked")
		
		List<Object[]> resultado = (List<Object[]>)queryPatrones.list();
		
		if(!resultado.isEmpty()) {
			 
			for(Object[] patronObject: resultado) {
				SujetoObligado patron = new SujetoObligado();
				patron.setNumeroRegistroPatronal((String)patronObject[0]);
				patron.setModalidad(new Modalidad());
				patron.getModalidad().setNumModalidad((String)patronObject[1]);
				patron.setDigVerificador(((Character)patronObject[2]).toString());
				patron.setNombreComercial((String)patronObject[3]);
				//patron.setFechaAlta((Date)patronObject[4]);
				patron.setIdTipoRegPatron(patronObject[5] != null ? ((BigDecimal)patronObject[5]).longValue() :1L );
				//verificamos si se tiene el movimiento de baja
				if(patronObject[6] != null) {
					int tipoMovimiento = ((BigDecimal)patronObject[6]).intValue();
					if(tipoMovimiento == CausaBajaPatronEnum.BAJA.getClave()){ 
						patron.setDescSituacionBaja(CausaBajaPatronEnum.BAJA.getDescripcion());
					}
				} else if(patronObject[7] != null) {
					SimpleDateFormat fechaBase = new SimpleDateFormat("yyyy/MM/dd");
					String fechaHuelga = fechaBase.format((Date)patronObject[7]);
					if (!fechaHuelga.equalsIgnoreCase(CausaBajaPatronEnum.FECHA_DE_HUELGA.getDescripcion())){
						patron.setDescSituacionBaja(CausaBajaPatronEnum.HUELGA.getDescripcion());
					}
				}
				
				patron.setClasificacion(new Clasificacion());
				patron.getClasificacion().setId(patronObject[8] != null ? ((BigDecimal)patronObject[8]).longValue() : null);
				patron.getClasificacion().setIndRegPatClase(patronObject[9] != null ? ((BigDecimal)patronObject[9]).intValue() : 0);
				
				patrones.add(patron);
			}
		} else {
			log.debug("No se encontro el patron");
		}
		return patrones;
	}	
	
	@Override
	public String getDescDomicilioSubdelegacion(Long cveIdSubdelegacion) {
		String refDomicilio = null;
		try {
			DicSubdelegacion subdel =em.find(DicSubdelegacion.class,  new Long(cveIdSubdelegacion));
			if(subdel != null)
				refDomicilio =  subdel.getRefDomicilio();
		}catch (Exception e) {
			log.error("ocurio un error al consultar el catalogo subdelegacion" + cveIdSubdelegacion, e );
		}
		return refDomicilio;	
	}; 
	
	//Consulta de historico de la prima, Mm AMSRT-2 - WO436058
	@Override
	public String consultaPrimaHistorica(String nrp, String fechaSurteEfecfto) {
		log.debug("::: Buscando prima historica, nrp: " + nrp + ", fechaSurteEfecfto: " + fechaSurteEfecfto);
		String primaH = "";
		StringBuffer query = new StringBuffer();
		if(nrp.length() > 8) {
			nrp = nrp.substring(0,8);
		}
		
		// con base la fecha surte efecto se busca el valor de la prima
		// se ordena descendente para que el primer valor sea el mas cercano
		// a la fecha surte efecto
		query.append("select ph.prima ");
		query.append("from MGCARGA1.SSCP_CLASIF_PATRON ph ");
		query.append("where ph.reg_patron = '" + nrp + "' ");
		query.append("and trunc(ph.fec_camb_cla) <=  to_date('"+fechaSurteEfecfto+"','YYYY/MM/DD') ");
		query.append("order by ph.fec_camb_cla desc");
		
		SQLQuery queryPatrones = this.getSession().createSQLQuery(query.toString());
		@SuppressWarnings("unchecked")
		List<Object[]> resultado = (List<Object[]>)queryPatrones.list();
		
		if(!resultado.isEmpty()) {
			Object[] res = resultado.get(0);
			primaH = (String)res[0];
		} else {
			log.debug("::: No se encontro prima historica para el patron " + nrp);
		}
		return primaH;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean isPatronPlataforma(String nrp) throws GestionPatronalBusinessException {
		log.debug("llegue al metodo para validar patron de plataforma NRP" + nrp );
		String query = "select CVE_REG_PATRON FROM PPT_PATRON_PLATAFORMA "
				+ " WHERE CVE_REG_PATRON = :nrp AND CVE_MODAL = :mdalidad"
				+ " and FEC_BAJA is null";
		try {
			SQLQuery sqlQuery = getSession().createSQLQuery(query);
			sqlQuery.setParameter("nrp", nrp.substring(0,8));
			sqlQuery.setParameter("mdalidad", nrp.substring(8,10));
			
			List<Object> lstPatron = (List<Object>)sqlQuery.list();
			if(lstPatron!= null && !lstPatron.isEmpty()) {
				log.debug(" la cosulta de patron plataforma si trae registros "+ lstPatron.size());
				return  true;
			}else 
				return false;
		}catch (Exception e) {
			log.error("error al consultar el patron de plataformas ", e);
			throw new GestionPatronalBusinessException("Error al consultar el patron"
					+ " de plataformas " + nrp + " error" + e.getMessage());
		}
		
	}; 
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean isPatronListaBlanca(String nrp) throws GestionPatronalBusinessException {
		log.debug("llegue al metodo para validar patron de plataforma NRP" + nrp);
		String query = "select CVE_REG_PATRON FROM PPT_PATRON_LISTA_BLANCA_ST "
				+ " WHERE CVE_REG_PATRON = :nrp AND CVE_MODAL = :mdalidad" + " and FEC_BAJA is null";
		try {
			SQLQuery sqlQuery = getSession().createSQLQuery(query);
			sqlQuery.setParameter("nrp", nrp.substring(0, 8));
			sqlQuery.setParameter("mdalidad", nrp.substring(8, 10));

			List<Object> lstPatron = (List<Object>) sqlQuery.list();
			if (lstPatron != null && !lstPatron.isEmpty()) {
				log.debug(" la cosulta de patron lista blanca si trae registros " + lstPatron.size());
				return true;
			} else
				return false;
		} catch (Exception e) {
			log.error("error al consultar el patron lista blanca ", e);
			throw new GestionPatronalBusinessException(
					"Error al consultar el patron" + " de listas blanco " + nrp + " error" + e.getMessage());
		}

	};
	
	@Override
	public Date obtenerFechaDespliegue(String nrp) throws GestionPatronalBusinessException {

		log.debug("Llegué al método obtenerFechaDespliegue por NRP: {}", nrp);

		String query = "SELECT FEC_DESPLIEGUE " + "FROM PPT_PATRON_LISTA_BLANCA_ST " + "WHERE CVE_REG_PATRON = :nrp "
				+ "AND CVE_MODAL = :modalidad " + "AND FEC_BAJA IS NULL";

		try {
			SQLQuery sqlQuery = getSession().createSQLQuery(query);

			sqlQuery.setParameter("nrp", nrp.substring(0, 8));
			sqlQuery.setParameter("modalidad", nrp.substring(8, 10));

			Object resultado = sqlQuery.uniqueResult();

			if (resultado != null) {
				log.debug("Se encontró la fecha de despliegue: {}", resultado);
				return (Date) resultado;
			}

			log.debug("No se encontró fecha de despliegue para el NRP: {}", nrp);
			return null;

		} catch (Exception e) {
			log.error("Error al consultar el patrón de lista blanca: {}", nrp, e);

			throw new GestionPatronalBusinessException(
					"Error al consultar el patrón de lista blanca " + nrp + ": " + e.getMessage());
		}
	}
		
}