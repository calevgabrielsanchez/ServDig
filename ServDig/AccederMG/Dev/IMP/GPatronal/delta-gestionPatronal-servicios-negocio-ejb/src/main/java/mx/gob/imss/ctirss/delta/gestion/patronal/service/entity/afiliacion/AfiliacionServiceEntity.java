package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.afiliacion;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.util.CollectionUtils;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.framework.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.utility.SujetoObligadoUtilityLocal;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.enums.CausaBajaPatronEnum;
import mx.gob.imss.ctirss.delta.model.enums.ModalidadEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Moral;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.MovimientoAfiliatorio;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.persistence.DgCodigosPostale;
import mx.gob.imss.ctirss.delta.persistence.DicSubdelegacion;
import mx.gob.imss.ctirss.delta.persistence.DitMovtoPatSujOblig;
import mx.gob.imss.ctirss.delta.persistence.DitMunicipioImssInegi;
import mx.gob.imss.ctirss.delta.persistence.DitMunicipioSubdelegacion;
import mx.gob.imss.ctirss.delta.persistence.DitPatronGeneral;
import mx.gob.imss.ctirss.delta.persistence.DitPatronSujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaFisica;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaMoral;

/**
 * 
 * @author Hugo Martinez
 * Date: 25/07/2012 09:37:44
 * Project: delta-gestionPatronal-servicios-negocio-ejb
 */
@Stateless
public class AfiliacionServiceEntity extends AbstractServiceEntity implements AfiliacionServiceEntityLocal{
	
	@EJB
	SujetoObligadoUtilityLocal sujetoObligadoUtility;
	
	@Override
	public Fisica obtenerDatosFiscalesPersonaFisica(Long idPersona) {
		Query query = em
				.createQuery("select pf from DitPersonaFisica pf "
						+ " join pf.ditPersona persona "
						+ " where persona.cveIdPersona = "
						+ idPersona);
		try {
			DitPersonaFisica ditpersona = (DitPersonaFisica) query
					.getSingleResult();
			Fisica fisica = sujetoObligadoUtility.convertirEntityToModelPersonaFisica(ditpersona);
			return fisica;
		} catch (NoResultException nre) {
			super.log.debug("NO SE ENCONTRO NINGUNA PERSONA CON ESE IDENTIFICADOR ");
		}
		return null;
	}

	@Override
	public Moral obtenerDatosFiscalesPersonaMoral(Long idPersona) {
		Query query = em
				.createQuery("select pm from DitPersonaMoral pm "
						+ "where pm.cveIdPersonaMoral = "
						+ idPersona);
		try {
			DitPersonaMoral ditpersona = (DitPersonaMoral) query
					.getSingleResult();
			Moral moral = sujetoObligadoUtility.convertirEntityToModelPersonaMoral(ditpersona);
			return moral;
		} catch (NoResultException nre) {
			super.log.debug("NO SE ENCONTRO NINGUNA PERSONA CON ESE IDENTIFICADOR ");
		}
		return null;
	}

	@Override
	public Municipio obtenerMunicipioInegiPorCodigoPostal(String codigoPostal, String cveAsentamiento) {
		
		Query query = em
				.createQuery("select cp from DgCodigosPostale cp "
						+ "where cp.id.codigo = "
						+ codigoPostal +" and cp.id.cveAsen = " + cveAsentamiento);
		try {
			@SuppressWarnings("unchecked")
			List<DgCodigosPostale> cps = query.getResultList();
			if(cps!= null && cps.size() > 0){
				Municipio municipio = new Municipio();
				municipio.setClave(cps.get(0).getId().getCveMun());
				municipio.setEntidadFederativa(new EntidadFederativa());
				municipio.getEntidadFederativa().setClave(cps.get(0).getId().getCveEnt());
				return municipio;
			}
			
		} catch (NoResultException nre) {
			super.log.debug("NO SE ENCONTRO NINGUNA PERSONA CON ESE IDENTIFICADOR ");
		}
		return null;
	}

	@Override
	public Long obtenerIdentificadorMunicipioImssPorMunicipioInegi(
			Municipio municipioInegi) {
		Query query = em
				.createQuery("select munImssInegi from DitMunicipioImssInegi munImssInegi "
						+ "where munImssInegi.dgCatMunicipio.id.cveMun = "
						+ municipioInegi.getClave() +" and munImssInegi.dgCatMunicipio.id.cveEnt = " + 
						municipioInegi.getEntidadFederativa().getClave() + " and munImssInegi.dicMunicipioImss.fecBajaVigencia is null");
		try {
			@SuppressWarnings("unchecked")
			List<DitMunicipioImssInegi> listMunImssInegi = query.getResultList();
			if(listMunImssInegi!= null && listMunImssInegi.size() > 0){
				return listMunImssInegi.get(0).getDicMunicipioImss().getCveIdMunicipioImss();
			}
			
		} catch (NoResultException nre) {
			super.log.debug("NO SE ENCONTRO NINGUNA PERSONA CON ESE IDENTIFICADOR ");
		}
		return null;
	}

	@Override
	public Subdelegacion obtenerSubdelegacionPorMunicipioImss(Long cveIdMunImss) {
		Query query = em
				.createQuery("select ditMunicipioSubdelegacion from DitMunicipioSubdelegacion ditMunicipioSubdelegacion "
						+ "where ditMunicipioSubdelegacion.dicMunicipioImss.cveIdMunicipioImss = "
						+ cveIdMunImss);
		try {
			@SuppressWarnings("unchecked")
			List<DitMunicipioSubdelegacion> listMunSubdelegacion = query.getResultList();
			if(listMunSubdelegacion!= null && listMunSubdelegacion.size() > 0){
				DitMunicipioSubdelegacion entity = listMunSubdelegacion.get(0);
				return sujetoObligadoUtility.convertirEntityToModelSubdelegacion(entity.getDicSubdelegacion());
			}
			
		} catch (NoResultException nre) {
			super.log.debug("NO SE ENCONTRO NINGUNA PERSONA CON ESE IDENTIFICADOR ");
		}
		return null;
	}

	@Override
	public Subdelegacion obtenerSubdelegacionPorId(Long idSubdelegacion) {
		DicSubdelegacion dicSubdel = this.em.find(DicSubdelegacion.class, idSubdelegacion);
		return sujetoObligadoUtility.convertirEntityToModelSubdelegacion(dicSubdel);
	}

	@Override
	public List<SujetoObligado> obtenerInformacionBasicaDeRegistrosPatronalesDelPatron(
			SujetoObligado sujetoObligado) {
		Query query = null;
		Long idPersonaConsultar=null;
		TipoPersonaFiscal tipoPersona = sujetoObligado.getTipoPersonaFiscal();
		if(sujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.FISICA)){
			Long idPersonaFisica = sujetoObligado.getFisica().getCveFisica();
			Long idPersona = sujetoObligado.getFisica().getIdPersona();
			if(idPersonaFisica==null){
				DitPersona persona = this.em.find(DitPersona.class, idPersona);
				DitPersonaFisica ditPersonaFisica =  persona.getDitPersonaFisicas().get(0);
				idPersonaFisica = ditPersonaFisica.getCveIdPersonaFisica();
			}
			idPersonaConsultar=idPersonaFisica;
			query = this.em.createQuery("select registroPatronal from DitPatronSujetoObligado registroPatronal where registroPatronal.ditPersonaFisica.cveIdPersonaFisica =:idPersona");
		}else if(sujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.MORAL)){
			query = this.em.createQuery("select registroPatronal from DitPatronSujetoObligado registroPatronal where registroPatronal.ditPersonaMoral.cveIdPersonaMoral =:idPersona");
			idPersonaConsultar=sujetoObligado.getMoral().getIdPersona();
		}
		
		query.setParameter("idPersona", idPersonaConsultar);
		
		@SuppressWarnings("unchecked")
		List<DitPatronSujetoObligado> rps= query.getResultList();
		List<SujetoObligado> listaRP = null;
		if(rps!=null & rps.size()>0){
			listaRP = new ArrayList<SujetoObligado>();
			
			for(DitPatronSujetoObligado rp : rps){
				SujetoObligado registroPatronal = sujetoObligadoUtility.convertirEntityToModel(rp, tipoPersona);
				listaRP.add(registroPatronal);
			}
			
		}
		
		return listaRP;
	}

	@Override
	public List<SujetoObligado> obtenerRegistrosPatronalesConBaja(String rfc) {
		StringBuffer queryBaja = new StringBuffer();
		queryBaja.append("select registroPatronal from DitPatronSujetoObligado registroPatronal");
		queryBaja.append(" join registroPatronal.ditPersonaFisica fisica");
		queryBaja.append(" join fisica.ditPersona persona");
		queryBaja.append(" join registroPatronal.ditPatronGenerals pg");
		queryBaja.append(" left outer join pg.ditDtsExtraPatron dep");
		queryBaja.append(" where persona.rfc = '").append(rfc).append("'");
		queryBaja.append(" and (registroPatronal.fecRegistroBaja is not null or dep.cveTipoMovto = ").append(new BigDecimal(CausaBajaPatronEnum.BAJA.getClave())).append(")");
		Query query = this.em.createQuery(queryBaja.toString());
		List<DitPatronSujetoObligado> patronesConBaja = query.getResultList();
		
		List<SujetoObligado> registrosPatronales = new ArrayList<SujetoObligado>();
		TipoPersonaFiscal tp = rfc.length() > 12 ? TipoPersonaFiscal.FISICA : TipoPersonaFiscal.MORAL;
		
		if(patronesConBaja!=null && !patronesConBaja.isEmpty()){
			for(DitPatronSujetoObligado pso:patronesConBaja){
				SujetoObligado registroPatronal = sujetoObligadoUtility.convertirEntityToModelBasic(pso, tp);
				registrosPatronales.add(registroPatronal);
			}
		}else{
			super.log.debug("NO SE ENCONTRO NINGUN NRP DADO DE BAJA");
		}
		
		return registrosPatronales;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SujetoObligado> obtenerRegistrosPatronalesConBaja(SujetoObligado sujetoObligado) {
		StringBuilder queryBaja = new StringBuilder();
		TipoPersonaFiscal tp = TipoPersonaFiscal.FISICA;
		if(sujetoObligado.getTipoPersonaFiscal().equals(TipoPersonaFiscal.MORAL)){
			tp = TipoPersonaFiscal.MORAL;
		}
		
		queryBaja.append("select registroPatronal from DitPatronSujetoObligado registroPatronal");
		if(tp.equals(TipoPersonaFiscal.FISICA)){
			queryBaja.append(" join registroPatronal.ditPersonaFisica fisica");
			queryBaja.append(" join fisica.ditPersona persona");
		}else{
			queryBaja.append(" join registroPatronal.ditPersonaMoral moral");
		}		
		queryBaja.append(" join registroPatronal.ditPatronGenerals pg");
		queryBaja.append(" left outer join pg.ditDtsExtraPatron dep");
		if((!CollectionUtils.isEmpty(sujetoObligado.getIdsModalidadesConsulta())))
			queryBaja.append(" join registroPatronal.dicModalidad modalidad ");	
				
		if(tp.equals(TipoPersonaFiscal.FISICA)){
			queryBaja.append(" where persona.rfc = :rfcPatron ");
		}else{
			queryBaja.append(" where moral.rfc = :rfcPatron ");
		}
		queryBaja.append(" and (registroPatronal.fecRegistroBaja is not null or dep.cveTipoMovto = :claveBaja )");
		if((!CollectionUtils.isEmpty(sujetoObligado.getIdsModalidadesConsulta())))
			queryBaja.append(" and modalidad.cveIdModalidad IN (:listaModalidades) ");
		
		Query query = this.em.createQuery(queryBaja.toString());		
		if(tp.equals(TipoPersonaFiscal.FISICA)){
			query.setParameter("rfcPatron", sujetoObligado.getFisica().getRfc());
		}else{
			query.setParameter("rfcPatron", sujetoObligado.getMoral().getRfc());
		}
		query.setParameter("claveBaja", new BigDecimal(CausaBajaPatronEnum.BAJA.getClave()));
		if((!CollectionUtils.isEmpty(sujetoObligado.getIdsModalidadesConsulta())))
			query.setParameter("listaModalidades", sujetoObligado.getIdsModalidadesConsulta());
		
		List<DitPatronSujetoObligado> patronesConBaja = query.getResultList();
		List<SujetoObligado> registrosPatronales = new ArrayList<SujetoObligado>();
		if(patronesConBaja!=null && !patronesConBaja.isEmpty()){
			for(DitPatronSujetoObligado pso:patronesConBaja){
				SujetoObligado registroPatronal = sujetoObligadoUtility.convertirEntityToModelBasic(pso, tp);
				registrosPatronales.add(registroPatronal);
			}
		}else{
			super.log.debug("NO SE ENCONTRO NINGUN NRP DADO DE BAJA");
		}
		return registrosPatronales;
	}

	@SuppressWarnings("unchecked")
	@Override
	public MovimientoAfiliatorio obtenerUltimoMovimiento(Long cveIdPatron) {
		Query query = em.createQuery("select movimiento from DitMovtoPatSujOblig movimiento "
				+ " where  movimiento.ditPatronSujetoObligado.cveIdPatronSujetoObligado = "+cveIdPatron
				+ " and movimiento.fecMovimiento is not null "
				+ " order by movimiento.fecMovimiento desc");
		List<DitMovtoPatSujOblig> movimientos = query.getResultList();
		
		if(movimientos!=null && !movimientos.isEmpty()){
			MovimientoAfiliatorio movimiento = new MovimientoAfiliatorio();
			DitMovtoPatSujOblig ultimoMovimientoAfiliatorio = movimientos.get(0);
			if(ultimoMovimientoAfiliatorio.getDicCausa()!=null)
				movimiento.setCveCausa(ultimoMovimientoAfiliatorio.getDicCausa().getCveIdCausa());
			movimiento.setFechaMovimiento(ultimoMovimientoAfiliatorio.getFecMovimiento());
			if(ultimoMovimientoAfiliatorio.getDicTipoMovtoPatSujoblig()!=null)
				movimiento.setTipoMovimiento(ultimoMovimientoAfiliatorio.getDicTipoMovtoPatSujoblig().getCveIdTipoMovtoPatSujoblig());
			return movimiento;
		}
		
		return null;
	}	

	@Override
	public List<SujetoObligado> obtenerRegistrosPatronalesCanerosSinEventualesporPersona(Long idPersona) {
		Query query = em.createQuery("select pso from DitPatronSujetoObligado pso " +
				"join pso.ditPersonaMoral moral " +
				"join pso.dicModalidad modalidad " +
				"join pso.ditPatronGenerals pg " +
				"left outer join pg.ditDtsExtraPatron dep "+ 
				"where modalidad.cveIdModalidad = " + ModalidadEnum.TREINTA.getId() +
				" and moral.cveIdPersonaMoral = " + idPersona +
				"pso.fecRegistroBaja is null and " +
				"(dep.cveTipoMovto IS NULL OR dep.cveTipoMovto <> "
				+ new BigDecimal(CausaBajaPatronEnum.BAJA.getClave()) +")");
		
		@SuppressWarnings("unchecked")
		List<DitPatronSujetoObligado> nrpCaneros = query.getResultList();
		List<SujetoObligado> patronesCaneros= new ArrayList<SujetoObligado>();
		
		for(DitPatronSujetoObligado nrp:nrpCaneros){
			Query queryEventuales = em.createQuery("select pg from DitPatronGeneral pg " +
					"join pg.ditPatronGeneral pr " +
					"where pr.cveIdPatronGeneral = "+nrp.getDitPatronGenerals().get(0).getCveIdPatronGeneral());
			
			@SuppressWarnings("unchecked")
			List<DitPatronGeneral> nrpCanerosEventuales = queryEventuales.getResultList();
			if(nrpCanerosEventuales==null 
					|| (nrpCanerosEventuales!=null && nrpCanerosEventuales.size()==0)
					){//Es decir si no tiene rp eventuales registrados
				SujetoObligado so = sujetoObligadoUtility.convertirEntityToModel(nrp, TipoPersonaFiscal.MORAL);
				patronesCaneros.add(so);
			}
		}
		
		return patronesCaneros;
	}
	
	@Override
	public Fisica obtenerDatosBasicosPersonaFisica(Long idPersona) {
		Query query = em
				.createQuery("select pf from DitPersonaFisica pf "
						+ " join pf.ditPersona persona "
						+ " where persona.cveIdPersona = "
						+ idPersona);
		try {
			DitPersonaFisica ditpersona = (DitPersonaFisica) query
					.getSingleResult();
			Fisica fisica = sujetoObligadoUtility.convertirEntityToModelPFDatosBasicos(ditpersona);
			return fisica;
		} catch (NoResultException nre) {
			super.log.debug("NO SE ENCONTRO NINGUNA PERSONA CON ESE IDENTIFICADOR ");
		}
		return null;
	}	
	
	@Override
	public Moral obtenerDatosBasicosPersonaMoral(Long idPersona) {
		super.log.debug("::: Buscando la persona Moral en IMSS-BDTU: obtenerDatosBasicosPersonaMoral");
		Query query = em
				.createQuery("select pm from DitPersonaMoral pm "
						+ "where pm.cveIdPersonaMoral = "
						+ idPersona);
		try {
			DitPersonaMoral ditpersona = (DitPersonaMoral) query
					.getSingleResult();
			Moral moral = sujetoObligadoUtility.convertirEntityToModelPMDatosBasicos(ditpersona);
			return moral;
		} catch (NoResultException nre) {
			super.log.debug("NO SE ENCONTRO NINGUNA PERSONA CON ESE IDENTIFICADOR ");
		}
		return null;
	}
	
}