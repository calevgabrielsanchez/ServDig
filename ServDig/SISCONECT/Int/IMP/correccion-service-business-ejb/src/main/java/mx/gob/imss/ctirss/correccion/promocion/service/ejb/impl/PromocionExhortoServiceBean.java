package mx.gob.imss.ctirss.correccion.promocion.service.ejb.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.correccion.catalogos.model.CrtNroFolio;
import mx.gob.imss.ctirss.correccion.catalogos.model.SacSubdelegacion;
import mx.gob.imss.ctirss.correccion.framework.utils.ConstantesBusiness;
import mx.gob.imss.ctirss.correccion.framework.utils.TipoCorreccion;
import mx.gob.imss.ctirss.correccion.model.CgcCatOrigen;
import mx.gob.imss.ctirss.correccion.model.CgcCatTipo;
import mx.gob.imss.ctirss.correccion.model.CgtCatCriterioSeleccion;
import mx.gob.imss.ctirss.correccion.model.CgtPromocion;
import mx.gob.imss.ctirss.correccion.model.CrtSelector;
import mx.gob.imss.ctirss.correccion.model.SatPatron;
import mx.gob.imss.ctirss.correccion.presentacion.service.ejb.dao.SatPatronDAO;
import mx.gob.imss.ctirss.correccion.promocion.model.CrtPromocion;
import mx.gob.imss.ctirss.correccion.promocion.service.ejb.PromocionExhortoServiceRemote;
import mx.gob.imss.ctirss.correccion.promocion.service.ejb.dao.SelectorDAO;

import org.hibernate.criterion.Restrictions;

@Stateless(name = "promocionExhortoService", mappedName = "promocionExhortoService")
public class PromocionExhortoServiceBean implements PromocionExhortoServiceRemote {
	@EJB 
	private SelectorDAO selectorDao;
	
	@EJB 
	private SatPatronDAO satPatronDao;
	
	public List<CrtSelector> obtenerCriterioSelector(Long delegacion, Long idSubdelegacion, Long idSelector) {
		List<CrtSelector> lista = selectorDao.obtenerCriteriosSeleccion(delegacion, idSubdelegacion, idSelector);
		return lista;
	}

	public CrtPromocion promocionarExhortoOrdinario(CrtPromocion promocion, String delegacion, String subDelegacion) {
		Long del = new Long(delegacion);
		Long subDel = new Long(subDelegacion);
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(promocion.getFecFechaemisionpro());
			
			CrtSelector selector = selectorDao.findById(promocion.getCveSelector(), false);
			
			CrtNroFolio folio = selectorDao.obtenerSiguienteFolio(del, subDel, ConstantesBusiness.EXHORTO_ORDINARIO, promocion.getFecFechaemisionpro());
			
			StringBuilder builder = new StringBuilder();
			if(del < 10)
				builder.append("0");
			builder.append(del);
			if(subDel < 10)
				builder.append("0");
			builder.append(subDel);
			builder.append("/EXO/");
			builder.append(cal.get(Calendar.YEAR));
			builder.append("/");
			if(folio.getNumNumero().intValue() < 10){
				builder.append("000");
			}else if(folio.getNumNumero().intValue() >= 10 && folio.getNumNumero().intValue() < 100){
				builder.append("00");
			}else if(folio.getNumNumero().intValue() >= 100 && folio.getNumNumero().intValue() < 999){
				builder.append("0");
			}
			builder.append(folio.getNumNumero());
			
			promocion.setNuFoliopromocion(builder.toString());
			promocion.setSdelegOrig(selector.getSacSubdelegacion().getCvePk());
			promocion.setIdCriterioSeleccion(selector.getCgcCatcriterioseleccion().getIdCriterioseleccion());
			
			List<SatPatron> patrones = satPatronDao.findByCriteria(Restrictions.eq("registroPatronal", promocion.getRegPatron()));
			if (patrones.size() > 0) {
				promocion.setCveFkPatron(patrones.get(0).getCvePK());
			}
			promocion = selectorDao.guardarPromocion(promocion);
		} catch (EJBException e) {
			e.printStackTrace();
		}
		return promocion;
	}

	
	public CrtPromocion promocionarExhortoOrdinarioIndividual(CrtPromocion promocion, String delegacion, String subDelegacion) {
		Long del = new Long(delegacion);
		Long subDel = new Long(subDelegacion);
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(promocion.getFecFechaemisionpro());
			
			//CrtSelector selector = selectorDao.findById(promocion.getCveSelector(), false);
			
			CrtNroFolio folio = selectorDao.obtenerSiguienteFolio(del, subDel, ConstantesBusiness.EXHORTO_ORDINARIO, promocion.getFecFechaemisionpro());
			
			StringBuilder builder = new StringBuilder();
			if(del < 10)
				builder.append("0");
			builder.append(del);
			if(subDel < 10)
				builder.append("0");
			builder.append(subDel);
			builder.append("/EXO/");
			builder.append(cal.get(Calendar.YEAR));
			builder.append("/");
			if(folio.getNumNumero().intValue() < 10){
				builder.append("000");
			}else if(folio.getNumNumero().intValue() >= 10 && folio.getNumNumero().intValue() < 100){
				builder.append("00");
			}else if(folio.getNumNumero().intValue() >= 100 && folio.getNumNumero().intValue() < 999){
				builder.append("0");
			}
			builder.append(folio.getNumNumero());
			
			promocion.setNuFoliopromocion(builder.toString());
			promocion.setSdelegOrig(subDel);
			//promocion.setIdCriterioSeleccion(selector.getCgcCatcriterioseleccion().getIdCriterioseleccion());
			
			List<SatPatron> patrones = satPatronDao.findByCriteria(Restrictions.eq("registroPatronal", promocion.getRegPatron()));
			if (patrones.size() > 0) {
				promocion.setCveFkPatron(patrones.get(0).getCvePK());
			}
			promocion = selectorDao.guardarPromocion(promocion);
		} catch (EJBException e) {
			e.printStackTrace();
		}
		return promocion;
	}

	
	
	@Override
	public CgtPromocion obtenerReplicaPromocion(CrtPromocion promocion) {
		CgtPromocion promocionReplica = new CgtPromocion();
		promocionReplica.setFolio(promocion.getNuFoliopromocion());
		promocionReplica.setSacSubdelegacion(new SacSubdelegacion());
		promocionReplica.getSacSubdelegacion().setCvePk(promocion.getSdelegOrig());
		promocionReplica.setUbicacion(promocion.getDomicilio());
		
		CrtSelector selector = selectorDao.findById(promocion.getCveSelector(), false);
		CgtCatCriterioSeleccion criterio = new CgtCatCriterioSeleccion();
		criterio.setIdCriterioseleccion(selector.getCgcCatcriterioseleccion().getIdCriterioseleccion());
		promocionReplica.setCgtCatCriterioSeleccion(criterio);
		
		promocionReplica.setCgcCatTipo(new CgcCatTipo());
		promocionReplica.getCgcCatTipo().setIdTipo(TipoCorreccion.EXHORTO_DE_LO_ORDINARIO.getEquivalenciaCaratula());
		promocionReplica.setCgcCatOrigen(new CgcCatOrigen());
		promocionReplica.getCgcCatOrigen().setIdOrigen(selector.getCgcCatcriterioseleccion().getIdOrigen());
		
		if(promocion.getCveFkPatron()!=null){
			SatPatron patron = satPatronDao.findById(promocion.getCveFkPatron(), false);				
			promocionReplica.setCvePatron(patron.getRegistroPatronal());
			promocionReplica.setNombre(patron.getRazonSocial());
		}
		
		promocionReplica.setDv(new BigDecimal(1));
		promocionReplica.setAfil15(promocion.getCveNroregobraSatic()!=null?promocion.getCveNroregobraSatic().toString():"");
		promocionReplica.setOpe(promocion.getFecFechaemisionpro());
		promocionReplica.setRnooficioope(promocion.getNuOficiopro());
		promocionReplica.setNop(promocion.getFecFechanotif());
		promocionReplica.setAop(promocion.getFecFechaAtencion());
		promocionReplica.setPai(promocion.getFecFechapai());
		promocionReplica.setPr(promocion.getFecFecharegulariza());
		promocionReplica.setObservaciones(promocion.getTxObservaciones());
		promocionReplica.setC(promocion.getFecFechaCancela());
		promocionReplica.setIdMotivocancelacion(promocion.getIdMotivoCancelacion());
		promocionReplica.setNooficioc(promocion.getNuVolanteCancela());
		promocionReplica.setFecFechareg(promocion.getFecFechareg());
		promocionReplica.setCveUsuario(promocion.getCveUsuario());
		return promocionReplica;
	}

	@Override
	public CgtPromocion obtenerReplicaPromocionIndividual(CrtPromocion promocion) {
		CgtPromocion promocionReplica = new CgtPromocion();
		promocionReplica.setFolio(promocion.getNuFoliopromocion());
		promocionReplica.setSacSubdelegacion(new SacSubdelegacion());
		promocionReplica.getSacSubdelegacion().setCvePk(promocion.getSdelegOrig());
		promocionReplica.setUbicacion(promocion.getDomicilio());
		
		//CrtSelector selector = selectorDao.findById(promocion.getCveSelector(), false);
		CgtCatCriterioSeleccion criterio = new CgtCatCriterioSeleccion();
		criterio.setIdCriterioseleccion(promocion.getIdCriterioSeleccion());
		promocionReplica.setCgtCatCriterioSeleccion(criterio);
		
		promocionReplica.setCgcCatTipo(new CgcCatTipo());
		promocionReplica.getCgcCatTipo().setIdTipo(TipoCorreccion.EXHORTO_DE_LO_ORDINARIO.getEquivalenciaCaratula());
		promocionReplica.setCgcCatOrigen(new CgcCatOrigen());
		promocionReplica.getCgcCatOrigen().setIdOrigen(5L);
		
		if(promocion.getCveFkPatron()!=null){
			SatPatron patron = satPatronDao.findById(promocion.getCveFkPatron(), false);				
			promocionReplica.setCvePatron(patron.getRegistroPatronal());
			promocionReplica.setNombre(patron.getRazonSocial());
		}
		
		promocionReplica.setDv(new BigDecimal(1));
		promocionReplica.setAfil15(promocion.getCveNroregobraSatic()!=null?promocion.getCveNroregobraSatic().toString():"");
		promocionReplica.setOpe(promocion.getFecFechaemisionpro());
		promocionReplica.setRnooficioope(promocion.getNuOficiopro());
		promocionReplica.setNop(promocion.getFecFechanotif());
		promocionReplica.setAop(promocion.getFecFechaAtencion());
		promocionReplica.setPai(promocion.getFecFechapai());
		promocionReplica.setPr(promocion.getFecFecharegulariza());
		promocionReplica.setObservaciones(promocion.getTxObservaciones());
		promocionReplica.setC(promocion.getFecFechaCancela());
		promocionReplica.setIdMotivocancelacion(promocion.getIdMotivoCancelacion());
		promocionReplica.setNooficioc(promocion.getNuVolanteCancela());
		promocionReplica.setFecFechareg(promocion.getFecFechareg());
		promocionReplica.setCveUsuario(promocion.getCveUsuario());
		return promocionReplica;
	}
	
	
	@Override
	public void replicaPromocionExhortoOrdinario(CgtPromocion replica) {
		try{
			selectorDao.guardarPromocionReplica(replica);
		}catch (EJBException e) {
			e.printStackTrace();
		}
		
	}
}
