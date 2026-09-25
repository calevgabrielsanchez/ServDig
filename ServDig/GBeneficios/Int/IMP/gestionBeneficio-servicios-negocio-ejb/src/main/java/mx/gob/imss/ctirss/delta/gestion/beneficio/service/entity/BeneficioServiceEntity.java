package mx.gob.imss.ctirss.delta.gestion.beneficio.service.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.beneficio.model.MovimientoRissType;
import mx.gob.imss.ctirss.delta.exception.beneficio.BeneficioRissException;
import mx.gob.imss.ctirss.delta.exception.beneficio.PersonaSinBeneficiosException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.framework.exceptions.TransformacionException;
import mx.gob.imss.ctirss.delta.gestion.beneficio.service.utility.BeneficioServiceUtilityLocal;
import mx.gob.imss.ctirss.delta.model.beneficio.Beneficio;
import mx.gob.imss.ctirss.delta.model.enums.EstadoBeneficioEnum;
import mx.gob.imss.ctirss.delta.model.enums.MotivoCancelacionBeneficioEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoBeneficioEnum;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.persistence.DicBeneficioCancelacion;
import mx.gob.imss.ctirss.delta.persistence.DicDescuentoRiss;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoBeneficio;
import mx.gob.imss.ctirss.delta.persistence.DitAsignacionNss;
import mx.gob.imss.ctirss.delta.persistence.DitBeneficio;
import mx.gob.imss.ctirss.delta.persistence.DitBeneficioRiss;
import mx.gob.imss.ctirss.delta.persistence.DitDescuentoBeneficio;
import mx.gob.imss.ctirss.delta.persistence.DitPatSujObligBeneficio;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaBeneficio;
import mx.gob.imss.ctirss.delta.persistence.DitTramitePatSujObligado;
import mx.gob.imss.ctirss.delta.persistence.DitTramitePersonaFisica;

import org.springframework.util.CollectionUtils;

@Stateless(mappedName = "beneficioServiceEntity", name = "beneficioServiceEntity")
public class BeneficioServiceEntity extends AbstractServiceEntity implements
		BeneficioServiceEntityLocal {

	@EJB
	private BeneficioServiceUtilityLocal beneficioServiceUtility;

	@Override
	public Beneficio guardarBeneficio(Beneficio beneficio, Date fechaActual, Date fechaSatRif, 
			Date FechaInicioRifImss) throws BeneficioRissException {		
		DitBeneficio entity = this.beneficioServiceUtility.prepararBeneficioRiss(beneficio, 
			obtenerDicDescuentosRiss(), fechaActual, fechaSatRif, FechaInicioRifImss);		
		this.em.persist(entity);
		if(entity.getCveIdBeneficio()!=null){			
			beneficio.setIdBeneficio(entity.getCveIdBeneficio().intValue());			
			log.debug("Id Beneficio Generado ["+entity.getCveIdBeneficio()+"]");					
		}		
		return beneficio;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Beneficio> obtenerBeneficiosPersona(Fisica fisica,
			List<Integer> estadosBeneficio){
		StringBuffer jpaQuery = new StringBuffer();
		jpaQuery.append("select pBenef.ditBeneficio ");
		jpaQuery.append("from DitPersonaBeneficio pBenef ");
		jpaQuery.append("where pBenef.ditPersona.cveIdPersona = :cveIdPersona ");
		jpaQuery.append("and pBenef.ditBeneficio.dicTipoBeneficio.cveIdTipoBeneficio = :tipoBeneficioRiss ");
		if(!CollectionUtils.isEmpty(estadosBeneficio)){
			jpaQuery.append("and pBenef.dicEstadoBeneficio.cveIdEstadoBeneficio in (:estadosBenef)");
		}
		Query query = this.em.createQuery(jpaQuery.toString());
		query.setParameter("cveIdPersona", fisica.getIdPersona());
		query.setParameter("tipoBeneficioRiss", TipoBeneficioEnum.RIF.getClave());
		if(!CollectionUtils.isEmpty(estadosBeneficio)){
			query.setParameter("estadosBenef", estadosBeneficio);
		}
		List<DitBeneficio> listaDitBeneficio = query.getResultList();
		return transformarListaResultados(listaDitBeneficio);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Beneficio> obtenerBeneficiosPorIdsPersonas(List<Long> getIdsPersonasFisicas,
			List<Integer> estadosBeneficio){
		StringBuffer jpaQuery = new StringBuffer();
		jpaQuery.append("select pBenef.ditBeneficio ");
		jpaQuery.append("from DitPersonaBeneficio pBenef ");
		jpaQuery.append("where pBenef.ditPersona.cveIdPersona in (:idsPersonas) ");
		jpaQuery.append("and pBenef.ditBeneficio.dicTipoBeneficio.cveIdTipoBeneficio = :tipoBeneficioRiss ");
		if(!CollectionUtils.isEmpty(estadosBeneficio)){
			jpaQuery.append("and pBenef.dicEstadoBeneficio.cveIdEstadoBeneficio in (:estadosBenef)");
		}
		Query query = this.em.createQuery(jpaQuery.toString());
		query.setParameter("idsPersonas", getIdsPersonasFisicas);
		query.setParameter("tipoBeneficioRiss", TipoBeneficioEnum.RIF.getClave());
		if(!CollectionUtils.isEmpty(estadosBeneficio)){
			query.setParameter("estadosBenef", estadosBeneficio);
		}
		List<DitBeneficio> listaDitBeneficio = query.getResultList();
		return transformarListaResultados(listaDitBeneficio);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Beneficio> obtenerBeneficiosSujetoObligado(
			SujetoObligado sujetoObligado, List<Integer> estadosBeneficio){
		StringBuffer jpaQuery = new StringBuffer();
		jpaQuery.append("select sujObligBenef.ditBeneficio ");
		jpaQuery.append("from DitPatSujObligBeneficio sujObligBenef ");
		jpaQuery.append("where sujObligBenef.ditPatronSujetoObligado.cveIdPatronSujetoObligado = :cveIdSujOblig ");
		jpaQuery.append("and sujObligBenef.ditBeneficio.dicTipoBeneficio.cveIdTipoBeneficio = :tipoBeneficioRiss ");
		if(!CollectionUtils.isEmpty(estadosBeneficio)){
			jpaQuery.append("and sujObligBenef.dicEstadoBeneficio.cveIdEstadoBeneficio in (:estadosBenef)");
		}
		Query query = this.em.createQuery(jpaQuery.toString());
		query.setParameter("cveIdSujOblig", sujetoObligado.getCveIdSujetoObligado());
		query.setParameter("tipoBeneficioRiss", TipoBeneficioEnum.RIF.getClave());
		if(!CollectionUtils.isEmpty(estadosBeneficio)){
			query.setParameter("estadosBenef", estadosBeneficio);
		}
		List<DitBeneficio> beneficiosTmp = query.getResultList();
		return transformarListaResultados(beneficiosTmp);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Beneficio> obtenerBeneficiosPorIdsSujetosObligados(
			List<Long> getIdsSujetosObligados, List<Integer> estadosBeneficio){
		StringBuffer jpaQuery = new StringBuffer();
		jpaQuery.append("select sujObligBenef.ditBeneficio ");
		jpaQuery.append("from DitPatSujObligBeneficio sujObligBenef ");
		jpaQuery.append("where sujObligBenef.ditPatronSujetoObligado.cveIdPatronSujetoObligado in (:idsSujObligados) ");
		jpaQuery.append("and sujObligBenef.ditBeneficio.dicTipoBeneficio.cveIdTipoBeneficio = :tipoBeneficioRiss ");
		if(!CollectionUtils.isEmpty(estadosBeneficio)){
			jpaQuery.append("and sujObligBenef.dicEstadoBeneficio.cveIdEstadoBeneficio in (:estadosBenef)");
		}
		Query query = this.em.createQuery(jpaQuery.toString());
		query.setParameter("idsSujObligados", getIdsSujetosObligados);
		query.setParameter("tipoBeneficioRiss", TipoBeneficioEnum.RIF.getClave());
		if(!CollectionUtils.isEmpty(estadosBeneficio)){
			query.setParameter("estadosBenef", estadosBeneficio);
		}
		List<DitBeneficio> beneficiosTmp = query.getResultList();
		return transformarListaResultados(beneficiosTmp);
	}	

	@SuppressWarnings("unchecked")
	@Override
	public List<Beneficio> obtenerBeneficiosPersonaPorTipo(Fisica fisica,
			List<Integer> tiposBeneficio, List<Integer> estadosBeneficio)
			throws PersonaSinBeneficiosException {
		StringBuffer jpaQuery = new StringBuffer();
		jpaQuery.append("select new DitBeneficio(pBenef.ditBeneficio.cveIdBeneficio, ");
		jpaQuery.append("pBenef.ditBeneficio.fecInicioVigencia, pBenef.ditBeneficio.fecFinVigencia, ");
		jpaQuery.append("pBenef.ditBeneficio.fecRegistroAlta, pBenef.ditBeneficio.fecRegistroBaja, ");
		jpaQuery.append("pBenef.ditBeneficio.fecRegistroActualizado, benefCanc, ");
		jpaQuery.append("pBenef.ditBeneficio.dicTipoBeneficio) ");
		jpaQuery.append("from DitPersonaBeneficio pBenef ");
		jpaQuery.append("left join pBenef.ditBeneficio.dicBeneficioCancelacion benefCanc ");
		jpaQuery.append("where pBenef.ditPersona.cveIdPersona = :cveIdPersona ");
		jpaQuery.append("and pBenef.ditBeneficio.dicTipoBeneficio.cveIdTipoBeneficio in (:tiposBeneficio) ");
		if(!CollectionUtils.isEmpty(estadosBeneficio)){
			jpaQuery.append("and pBenef.dicEstadoBeneficio.cveIdEstadoBeneficio in (:estadosBenef)");
		}
		Query query = this.em.createQuery(jpaQuery.toString());
		query.setParameter("cveIdPersona", fisica.getIdPersona());
		query.setParameter("tiposBeneficio", tiposBeneficio);
		if(!CollectionUtils.isEmpty(estadosBeneficio)){
			query.setParameter("estadosBenef", estadosBeneficio);
		}
		List<DitBeneficio> beneficiosTmp = query.getResultList();
		return transformarListaResultados(beneficiosTmp);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Beneficio> obtenerBeneficiosSujetoObligadoPorTipo(
			SujetoObligado sujetoObligado, List<Integer> tiposBeneficio,
			List<Integer> estadosBeneficio)
			throws PersonaSinBeneficiosException {
		StringBuffer jpaQuery = new StringBuffer();
		jpaQuery.append("select new DitBeneficio(sujObligBenef.ditBeneficio.cveIdBeneficio, ");
		jpaQuery.append("sujObligBenef.ditBeneficio.fecInicioVigencia, sujObligBenef.ditBeneficio.fecFinVigencia, ");
		jpaQuery.append("sujObligBenef.ditBeneficio.fecRegistroAlta, sujObligBenef.ditBeneficio.fecRegistroBaja, ");
		jpaQuery.append("sujObligBenef.ditBeneficio.fecRegistroActualizado, benefCanc, ");
		jpaQuery.append("sujObligBenef.ditBeneficio.dicTipoBeneficio) ");
		jpaQuery.append("from DitPatSujObligBeneficio sujObligBenef ");
		jpaQuery.append("left join sujObligBenef.ditBeneficio.dicBeneficioCancelacion benefCanc ");
		jpaQuery.append("where sujObligBenef.ditPatronSujetoObligado.cveIdPatronSujetoObligado = :cveIdSujOblig ");
		jpaQuery.append("and sujObligBenef.ditBeneficio.dicTipoBeneficio.cveIdTipoBeneficio in (:tiposBeneficio) ");
		if(!CollectionUtils.isEmpty(estadosBeneficio)){
			jpaQuery.append("and sujObligBenef.dicEstadoBeneficio.cveIdEstadoBeneficio in (:estadosBenef)");
		}
		Query query = this.em.createQuery(jpaQuery.toString());
		query.setParameter("cveIdSujOblig", sujetoObligado.getCveIdSujetoObligado());
		query.setParameter("tiposBeneficio", tiposBeneficio);
		if(!CollectionUtils.isEmpty(estadosBeneficio)){
			query.setParameter("estadosBenef", estadosBeneficio);
		}
		List<DitBeneficio> beneficiosTmp = query.getResultList();
		return transformarListaResultados(beneficiosTmp);
	}

	@Override
	public void cancelarBeneficiosRiss(List<Beneficio> listaBeneficios, List<Integer> estadosBeneficio, 
			Date fechaBaja, MotivoCancelacionBeneficioEnum motivoCancelacion, int claveEstadoCancelado){		

		List<DitBeneficio> listaDitBeneficio = obtenerBeneficiosPorId(listaBeneficios);
		if(!CollectionUtils.isEmpty(listaDitBeneficio)){
			for(DitBeneficio ditBeneficio : listaDitBeneficio){	
				cancelarBeneficioRiss(ditBeneficio, 
						fechaBaja, motivoCancelacion, claveEstadoCancelado);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DicDescuentoRiss> obtenerDicDescuentosRiss(){
		StringBuffer jpaQuery = new StringBuffer();
		jpaQuery.append("SELECT dr from DicDescuentoRiss dr ");
		jpaQuery.append("WHERE dr.fecRegistroBaja IS NULL");
		Query query = this.em.createQuery(jpaQuery.toString());		
		List<DicDescuentoRiss> listaDescuentosRiss = query.getResultList();
		
		return listaDescuentosRiss;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Long obtenerIdSolicitudPatron(List<Long> idsSujetosObligados){
		List<Integer> estadosValidar = new ArrayList<Integer>();
		estadosValidar.add(EstadoSolicitudEnum.ATENDIDA.getCodigo());
		
		Query query =  prepararConsultaSolicitudPatron(idsSujetosObligados, estadosValidar, true,null);
		List<DitTramitePatSujObligado> lista = query.getResultList();
		if(!CollectionUtils.isEmpty(lista)){
			for(DitTramitePatSujObligado tramitePatSujObligado : lista){	
				if(tramitePatSujObligado.getDitTramite()!=null 
					&& tramitePatSujObligado.getDitTramite().getDitSolicitud()!=null){
					return tramitePatSujObligado.getDitTramite().getDitSolicitud().getCveIdSolicitud();
				}
			}
		}
		return 0L;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Long solicitudEnProcesoPatron(
			List<Long> idsSujetosObligados, OrigenSolicitudEnum origenSolicitud){		
		List<Integer> estadosValidar = new ArrayList<Integer>();
		estadosValidar.add(EstadoSolicitudEnum.REGISTRADA.getCodigo());
		estadosValidar.add(EstadoSolicitudEnum.PARA_PROCESAR_BACKOFFICE.getCodigo());
		estadosValidar.add(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo());
		
		Query query =  prepararConsultaSolicitudPatron(idsSujetosObligados, 
				estadosValidar, false, origenSolicitud);
		List<DitTramitePatSujObligado> lista = query.getResultList();
		if(!CollectionUtils.isEmpty(lista)){
			return lista.get(0).getDitTramite().getDitSolicitud().getCveIdSolicitud();
		}
		return 0L;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Long obtenerIdSolicitudFisicas(Long idPersona){
		List<Integer> estadosValidar = new ArrayList<Integer>();
		estadosValidar.add(EstadoSolicitudEnum.ATENDIDA.getCodigo());
		
		Query query = prepararConsultaSolicitudFisicas(idPersona, estadosValidar, true,null);			
		List<DitTramitePersonaFisica> lista = query.getResultList();
		if(!CollectionUtils.isEmpty(lista)){
			for(DitTramitePersonaFisica tramitePersonaFisica : lista){	
				if(tramitePersonaFisica.getDitTramite()!=null 
					&& tramitePersonaFisica.getDitTramite().getDitSolicitud()!=null){
					return tramitePersonaFisica.getDitTramite().getDitSolicitud().getCveIdSolicitud();
				}
			}
		}
		return 0L;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Long solicitudEnProcesoFisicas(
			Long idPersona, OrigenSolicitudEnum origenSolicitud){		
		List<Integer> estadosValidar = new ArrayList<Integer>();
		estadosValidar.add(EstadoSolicitudEnum.REGISTRADA.getCodigo());
		estadosValidar.add(EstadoSolicitudEnum.PARA_PROCESAR_BACKOFFICE.getCodigo());
		estadosValidar.add(EstadoSolicitudEnum.PENDIENTE_AUTORIZACION.getCodigo());
		
		Query query = prepararConsultaSolicitudFisicas(idPersona,
				estadosValidar, false, origenSolicitud);	
		List<DitTramitePersonaFisica> lista = query.getResultList();
		if(!CollectionUtils.isEmpty(lista)){
			return lista.get(0).getDitTramite().getDitSolicitud().getCveIdSolicitud();
		}
		return 0L;
	}
	
	@Override
	public List<MovimientoRissType> prepararLayoutMovimientoBajaRiss(List<Beneficio> listaBeneficios,
			int motivoBaja, Date fechaBaja, String patronGeneral) throws BeneficioRissException{
		//Obtener relaciones completas por medio de los IDs de Beneficio.
		List<DitBeneficio> listaDitBeneficio = obtenerBeneficiosPorId(listaBeneficios);
		if(!CollectionUtils.isEmpty(listaDitBeneficio)){
			//Obtener la persona fisica, si se trata de SO, obtener la PF relacionada.
			DitPersona personaMovimiento = null;
			for(DitBeneficio ditBeneficio : listaDitBeneficio){
				if(!CollectionUtils.isEmpty(ditBeneficio.getDitPersonaBeneficios())){
					DitPersonaBeneficio persona = ditBeneficio.getDitPersonaBeneficios().get(0);
					personaMovimiento = persona.getDitPersona();
					break;
				}else if(!CollectionUtils.isEmpty(ditBeneficio.getDitPatSujObligBeneficios())){
					DitPatSujObligBeneficio patron = ditBeneficio.getDitPatSujObligBeneficios().get(0);
					personaMovimiento = patron.getDitPatronSujetoObligado().getDitPersonaFisica().getDitPersona();
					break;
				}
			}
			String nss = obtenerNssPersonaMovimiento(personaMovimiento.getCveIdPersona());
			return beneficioServiceUtility.prepararLayoutMovimientoBajaBeneficio(listaDitBeneficio, 
				personaMovimiento, nss, motivoBaja, fechaBaja, patronGeneral);						
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DitPatSujObligBeneficio> obtenerBeneficioActivoPatrones(Long idPersona){
		StringBuffer jpaQuery = new StringBuffer();
		jpaQuery.append("SELECT t from DitPatSujObligBeneficio t ");
		jpaQuery.append("WHERE t.ditPatronSujetoObligado.ditPersonaFisica.ditPersona.cveIdPersona  = :idPersona ");
		jpaQuery.append("AND t.ditBeneficio.dicTipoBeneficio.cveIdTipoBeneficio = :tipoBeneficioRiss ");
		jpaQuery.append("AND t.dicEstadoBeneficio.cveIdEstadoBeneficio = :estadosBeneficio ");
		
		Query query = this.em.createQuery(jpaQuery.toString());
		query.setParameter("idPersona", idPersona);
		query.setParameter("tipoBeneficioRiss", TipoBeneficioEnum.RIF.getClave());
		query.setParameter("estadosBeneficio", EstadoBeneficioEnum.ACTIVO.getClave());
		
		List<DitPatSujObligBeneficio> listaBeneficios = query.getResultList();
		return listaBeneficios;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DitPatSujObligBeneficio> obtenerBeneficioPatronxIdBeneficio(Long idBeneficio){
		StringBuffer jpaQuery = new StringBuffer();
		jpaQuery.append("SELECT t from DitPatSujObligBeneficio t ");
		jpaQuery.append("WHERE t.ditBeneficio.cveIdBeneficio  = :idBeneficio ");
		
		Query query = this.em.createQuery(jpaQuery.toString());
		query.setParameter("idBeneficio", idBeneficio);
		
		List<DitPatSujObligBeneficio> listaBeneficios = query.getResultList();
		return listaBeneficios;
	}
		
	@SuppressWarnings("unchecked")
	@Override
	public List<DitPersonaBeneficio> obtenerBeneficioActivoPersona(Long idPersona){
		StringBuffer jpaQuery = new StringBuffer();
		jpaQuery.append("SELECT t from DitPersonaBeneficio t ");
		jpaQuery.append("WHERE t.ditPersona.cveIdPersona  = :idPersona ");
		jpaQuery.append("AND t.ditBeneficio.dicTipoBeneficio.cveIdTipoBeneficio = :tipoBeneficioRiss ");
		jpaQuery.append("AND t.dicEstadoBeneficio.cveIdEstadoBeneficio = :estadosBeneficio ");
		
		Query query = this.em.createQuery(jpaQuery.toString());
		query.setParameter("idPersona", idPersona);
		query.setParameter("tipoBeneficioRiss", TipoBeneficioEnum.RIF.getClave());
		query.setParameter("estadosBeneficio", EstadoBeneficioEnum.ACTIVO.getClave());
		
		List<DitPersonaBeneficio> listaBeneficios = query.getResultList();
		return listaBeneficios;
	}
	
	@Override
	public void guardarDitPatSujObligBeneficio(DitPatSujObligBeneficio entity) {
		if (entity != null) {
			this.em.persist(entity);
		}
	}
	

	//Metodos privados
	private Query prepararConsultaSolicitudFisicas(Long idPersona, 
			List<Integer> estadosSolicitud, boolean orderByAsc, OrigenSolicitudEnum origenSolicitud){
		
		StringBuffer jpaQuery = new StringBuffer();
		jpaQuery.append("SELECT t from DitTramitePersonaFisica t ");
		jpaQuery.append("WHERE t.ditTramite.ditSolicitud.dicTipoSolicitud.cveIdTipoSolicitud = :tipoSolicitudRiss ");
		jpaQuery.append("AND t.ditPersona.cveIdPersona in (:idPersona) ");
		if (!CollectionUtils.isEmpty(estadosSolicitud)) {
			jpaQuery.append(" AND t.ditTramite.ditSolicitud.dicEstadoSolicitud.cveIdEstadoSolicitud IN :idEstadoSolicitud ");
		}
		if (origenSolicitud != null) {
			jpaQuery.append(" AND t.ditTramite.ditSolicitud.dicOrigenSolicitud.cveIdOrigenSolicitud = :idOrigenSolicitud ");
		}
		if (orderByAsc) {
			jpaQuery.append(" ORDER BY t.id.cveIdTramite ASC ");
		}
		
		Query query = this.em.createQuery(jpaQuery.toString());
		query.setParameter("tipoSolicitudRiss", TipoSolicitudEnum.INCORPORACION_BENEFICIO.getValor());
		query.setParameter("idPersona", idPersona);	
		
		if (!CollectionUtils.isEmpty(estadosSolicitud)) {
			query.setParameter("idEstadoSolicitud", estadosSolicitud);
		}
		if (origenSolicitud != null) {
			query.setParameter("idOrigenSolicitud", origenSolicitud.getId().longValue());
		}
		
		return query;
	}
	
	private Query prepararConsultaSolicitudPatron(List<Long> idsSujetosObligados, 
			List<Integer> estadosSolicitud, boolean orderByAsc, OrigenSolicitudEnum origenSolicitud){
		
		StringBuffer jpaQuery = new StringBuffer();
		jpaQuery.append("SELECT t from DitTramitePatSujObligado t ");
		jpaQuery.append("WHERE t.ditTramite.ditSolicitud.dicTipoSolicitud.cveIdTipoSolicitud = :tipoSolicitudRiss ");
		jpaQuery.append("AND t.ditPatronSujetoObligado.cveIdPatronSujetoObligado in (:idsSujetosObligados) ");
		if (!CollectionUtils.isEmpty(estadosSolicitud)) {
			jpaQuery.append(" AND t.ditTramite.ditSolicitud.dicEstadoSolicitud.cveIdEstadoSolicitud IN :idEstadoSolicitud ");
		}
		if (origenSolicitud != null) {
			jpaQuery.append(" AND t.ditTramite.ditSolicitud.dicOrigenSolicitud.cveIdOrigenSolicitud = :idOrigenSolicitud ");
		}
		if (orderByAsc) {
			jpaQuery.append(" ORDER BY t.id.cveIdTramite ASC ");
		}
		
		Query query = this.em.createQuery(jpaQuery.toString());
		query.setParameter("tipoSolicitudRiss", TipoSolicitudEnum.INCORPORACION_BENEFICIO.getValor());
		query.setParameter("idsSujetosObligados", idsSujetosObligados);		
		if (!CollectionUtils.isEmpty(estadosSolicitud)) {
			query.setParameter("idEstadoSolicitud", estadosSolicitud);
		}
		if (origenSolicitud != null) {
			query.setParameter("idOrigenSolicitud", origenSolicitud.getId().longValue());
		}
		
		return query;
	}
	
	private List<Beneficio> transformarListaResultados(List<DitBeneficio> litaDitBeneficio){
		List<Beneficio> beneficios = new ArrayList<Beneficio>();
		if(!CollectionUtils.isEmpty(litaDitBeneficio)){
			for (DitBeneficio ditBeneficio : litaDitBeneficio) {
				try {
					beneficios.add(this.beneficioServiceUtility
							.convertirEntityToModel(ditBeneficio));
				} catch (TransformacionException e) {
					this.log.error("Error al transformar el beneficio", e);
				}
			}
		}
		return beneficios;
	}
		
	private void cancelarBeneficioRiss(DitBeneficio ditBeneficio, Date fechaBaja, 
			MotivoCancelacionBeneficioEnum motivoCancelacion, int claveEstadoCancelado){
		
		if( ditBeneficio!=null && ditBeneficio.getDicTipoBeneficio()!=null
			&& ditBeneficio.getDicTipoBeneficio().getCveIdTipoBeneficio()
				.intValue()==TipoBeneficioEnum.RIF.getClave()){			
			this.log.debug("Cancelar Beneficio - ID " + ditBeneficio.getCveIdBeneficio());
			boolean tienePFAsociada = (!CollectionUtils.isEmpty(ditBeneficio.getDitPersonaBeneficios())) ? true : false;
			boolean tieneSOAsociados = (!CollectionUtils.isEmpty(ditBeneficio.getDitPatSujObligBeneficios())) ? true : false;
			
			//Cancelar DitBeneficioRiss
			cancelarDitBeneficiosRiss(ditBeneficio.getDitBeneficiosRiss(), fechaBaja);
			//Cancelar DitDescuentoBeneficios
			cancelarDitDescuentosBeneficios(ditBeneficio.getDitDescuentoBeneficios(), fechaBaja);				
			//Cancelar DitBeneficio
			cancelarDitBeneficio(ditBeneficio, fechaBaja, motivoCancelacion);
			if(tienePFAsociada){
				//Cancelar DitPersonaBeneficio
				cancelarDitPersonaBeneficio(ditBeneficio, fechaBaja, claveEstadoCancelado);
			}
			if(tieneSOAsociados){
				//Cancelar DitPatSujObligBeneficio
				cancelarDitPatSujObligBeneficio(ditBeneficio, fechaBaja, claveEstadoCancelado);
			}			
		}
	}
	
	private void cancelarDitBeneficiosRiss(List<DitBeneficioRiss> listaDitBeneficioRiss, 
			Date fechaBaja){
		if(!CollectionUtils.isEmpty(listaDitBeneficioRiss)){
			for(DitBeneficioRiss ditBeneficioRiss : listaDitBeneficioRiss){
				this.log.debug("Cancelar DitBeneficioRiss - ID " + 
					ditBeneficioRiss.getCveIdBeneficioRiss());
				ditBeneficioRiss.setFecRegistroBaja(fechaBaja);	
				ditBeneficioRiss.setFecRegistroActualizado(new Date());
				this.em.merge(ditBeneficioRiss);
			}
		}
	}
	
	private void cancelarDitDescuentosBeneficios(List<DitDescuentoBeneficio> listaDescuentosBeneficios, 
			Date fechaBaja){
		if(!CollectionUtils.isEmpty(listaDescuentosBeneficios)){
			for(DitDescuentoBeneficio ditDescuentoBeneficio : listaDescuentosBeneficios){
				this.log.debug("Cancelar DitDescuentoBeneficio - ID " + 
					ditDescuentoBeneficio.getCveIdDescuentoBeneficio());
				ditDescuentoBeneficio.setFecRegistroBaja(fechaBaja);
				ditDescuentoBeneficio.setFecRegistroActualizado(new Date());
				this.em.merge(ditDescuentoBeneficio);
			}
		}
	}
	
	private void cancelarDitBeneficio(DitBeneficio ditBeneficio, 
			Date fechaBaja, MotivoCancelacionBeneficioEnum motivoCancelacion){
		if(ditBeneficio!=null){
			this.log.debug("Cancelar DitBeneficio - ID " + ditBeneficio.getCveIdBeneficio());
			if(motivoCancelacion!=null){
				DicBeneficioCancelacion cancelacionBeneficio = new DicBeneficioCancelacion();
				cancelacionBeneficio.setCveIdBeneficioCancelacion(motivoCancelacion.getClave());
				ditBeneficio.setDicBeneficioCancelacion(cancelacionBeneficio);	
			}			
			ditBeneficio.setFecRegistroBaja(fechaBaja);		
			ditBeneficio.setFecRegistroActualizado(new Date());
			this.em.merge(ditBeneficio);
		}
	}
	
	private void cancelarDitPatSujObligBeneficio(DitBeneficio ditBeneficio, Date fechaBaja, int claveEstadoCancelado){
		if(!CollectionUtils.isEmpty(ditBeneficio.getDitPatSujObligBeneficios())){
			for(DitPatSujObligBeneficio ditPatSujObligBeneficio : ditBeneficio.getDitPatSujObligBeneficios()){
				//Cancelar solo lo que estan Activos
				if(ditPatSujObligBeneficio.getDicEstadoBeneficio()!= null &&
					ditPatSujObligBeneficio.getDicEstadoBeneficio().getCveIdEstadoBeneficio()!= null &&
					ditPatSujObligBeneficio.getDicEstadoBeneficio().getCveIdEstadoBeneficio()
					.intValue()==EstadoBeneficioEnum.ACTIVO.getClave()){					
						ditPatSujObligBeneficio.setFecRegistroBaja(fechaBaja);
						ditPatSujObligBeneficio.setFecRegistroActualizado(new Date());
						DicEstadoBeneficio estadoBeneficio = new DicEstadoBeneficio();
						estadoBeneficio.setCveIdEstadoBeneficio((long)claveEstadoCancelado);
						ditPatSujObligBeneficio.setDicEstadoBeneficio(estadoBeneficio);		
						this.em.merge(ditPatSujObligBeneficio);					
				}
			}
		}
	}
	
	private void cancelarDitPersonaBeneficio(DitBeneficio ditBeneficio, Date fechaBaja, int claveEstadoCancelado){
		if(!CollectionUtils.isEmpty(ditBeneficio.getDitPersonaBeneficios())){
			for(DitPersonaBeneficio ditPersonaBeneficio : ditBeneficio.getDitPersonaBeneficios()){
				//Cancelar solo lo que estan Activos
				if(ditPersonaBeneficio.getDicEstadoBeneficio()!= null &&
					ditPersonaBeneficio.getDicEstadoBeneficio().getCveIdEstadoBeneficio()!= null &&
					ditPersonaBeneficio.getDicEstadoBeneficio().getCveIdEstadoBeneficio()
					.intValue()==EstadoBeneficioEnum.ACTIVO.getClave()){	
						ditPersonaBeneficio.setFecRegistroBaja(fechaBaja);
						ditPersonaBeneficio.setFecRegistroActualizado(new Date());
						DicEstadoBeneficio estadoBeneficio = new DicEstadoBeneficio();
						estadoBeneficio.setCveIdEstadoBeneficio((long)claveEstadoCancelado);
						ditPersonaBeneficio.setDicEstadoBeneficio(estadoBeneficio);	
						this.em.merge(ditPersonaBeneficio);
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private List<DitBeneficio> obtenerBeneficiosPorId(List<Beneficio> listaBeneficios){		
		StringBuffer jpaQuery = new StringBuffer();
		jpaQuery.append("SELECT b FROM DitBeneficio b ");
		jpaQuery.append("WHERE b.cveIdBeneficio in (:iDsBeneficios) ");		
		Query query = this.em.createQuery(jpaQuery.toString());
		query.setParameter("iDsBeneficios", beneficioServiceUtility
			.getIdsBeneficios(listaBeneficios));		
		return (List<DitBeneficio>)query.getResultList();		
	} 

	@SuppressWarnings("unchecked")
	private String obtenerNssPersonaMovimiento(Long cveIdPersona){
		StringBuffer jpaQuery = new StringBuffer();
		jpaQuery.append("select a from DitAsignacionNss a where a.ditPersona.cveIdPersona = :idPersona");
		Query query = this.em.createQuery(jpaQuery.toString());
		query.setParameter("idPersona", cveIdPersona);
		
		List<DitAsignacionNss> listaNss = query.getResultList();
		if(!CollectionUtils.isEmpty(listaNss)){
			return listaNss.get(0).getNumNss();
		}
		return null;
	}
	
	
}
