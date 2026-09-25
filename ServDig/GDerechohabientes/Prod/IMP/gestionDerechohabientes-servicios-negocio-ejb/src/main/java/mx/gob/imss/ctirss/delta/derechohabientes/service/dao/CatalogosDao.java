package mx.gob.imss.ctirss.delta.derechohabientes.service.dao;


import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.CaracterParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.EntidadFederativaParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.EstadoCivilParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.PaisParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.ParentescoParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.RazonRegistroParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.SexoParser;
import mx.gob.imss.ctirss.delta.derechohabientes.util.parser.TipoTramiteParser;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesBusinessException;
import mx.gob.imss.ctirss.delta.exception.derechohabiente.ExceptionMessages;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Caracter;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Parentesco;
import mx.gob.imss.ctirss.delta.model.derechohabiente.RazonRegistro;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Pais;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.EstadoCivil;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.persistence.DgCatEstado;
import mx.gob.imss.ctirss.delta.persistence.DicCalidadParentesco;
import mx.gob.imss.ctirss.delta.persistence.DicCaracter;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoCivil;
import mx.gob.imss.ctirss.delta.persistence.DicPai;
import mx.gob.imss.ctirss.delta.persistence.DicRazonRegistro;
import mx.gob.imss.ctirss.delta.persistence.DicSexo;
import mx.gob.imss.ctirss.delta.persistence.DicTipoTramite;

@Stateless(name = "catalogoGenericoDao", mappedName = "catalogoGenericoDao")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class CatalogosDao extends AbstractServiceEntity implements CatalogosDaoLocal {
	
	@Override
	public TipoTramite getTipoTramite(Long idTipoTramite) throws DerechohabientesBusinessException{

		TipoTramite tipoTramite = null;
		DicTipoTramite dictipotramite = null;
		
		dictipotramite = em.find(DicTipoTramite.class, idTipoTramite);
		
		if(dictipotramite != null) {
			tipoTramite = TipoTramiteParser.persisToModel(dictipotramite);
		}
		return tipoTramite;
	}

	@Override
	public Sexo getCatalogoSexo(long idCatalogo)  throws DerechohabientesBusinessException{
		Sexo unSexo = null;
		DicSexo ditSexo = null;
		try {
			ditSexo= em.find(DicSexo.class, idCatalogo);
		} catch (NoResultException e) {
			unSexo = null;
		} catch (Exception e) {
			throw new DerechohabientesBusinessException(ExceptionMessages.ERROR_DATOS);
		}
			
		unSexo = SexoParser.persisToModel(ditSexo);
		return unSexo;
	}
	
	
	@Override
	public List<Sexo> getCatalogoSexo()  throws DerechohabientesBusinessException{
		List<Sexo> lstSexo = null;
		
		try {
			
			Criteria query = this.getSession().createCriteria(DicSexo.class);
			query.add(Restrictions.isNull("fecRegistroBaja"));
			List<DicSexo> dicSexoLst = query.list();
			if(dicSexoLst != null && !dicSexoLst.isEmpty()) {
				lstSexo = SexoParser.persisToModelList(dicSexoLst);
			}
			return lstSexo;
			
		} catch (NoResultException e) {
			return  lstSexo;
		} catch (Exception e) {
			throw new DerechohabientesBusinessException(e.getMessage());
		}
		
	}

	
	@Override
	public List<Parentesco> getCatalogoParentescos()
			throws DerechohabientesBusinessException, Exception {
		
		List<Parentesco> parentescos = null;
		
		Criteria query = this.getSession().createCriteria(DicCalidadParentesco.class);
		query.add(Restrictions.isNull("fecRegistroBaja"));
		
		List<DicCalidadParentesco> dicParentescos = query.list();
		if(dicParentescos != null && !dicParentescos.isEmpty()) {
			parentescos = ParentescoParser.persisToModelList(dicParentescos);
		}
		
		return parentescos;
	}

	@Override
	public Parentesco getCatalogoParentesco(long idCatalogo)  throws Exception{
		DicCalidadParentesco entrada = null;
		try {
			entrada= em.find(DicCalidadParentesco.class, idCatalogo);
		} catch (NoResultException e) {
			entrada = null;
		} catch (Exception e){
			log.error("Error - getCatalogoParentesco", e);
			throw e;
		}
				
		return ParentescoParser.persisToModel(entrada);
	}

	@Override
	public EntidadFederativa getCatalogoEntidadFed(String idCatalogo)  throws DerechohabientesBusinessException,Exception {
		DgCatEstado entrada = null;
		try {
			entrada= em.find(DgCatEstado.class, idCatalogo);
		} catch (NoResultException e) {
			entrada = null;
		} catch (Exception e){
			log.error("Error - getCatalogoEntidadFed", e);
			throw e;
		}
				
		return EntidadFederativaParser.persisToModel(entrada);
	}
	
	
	@Override
	public List<EntidadFederativa> getCatalogoEntidadFed()
			throws DerechohabientesBusinessException{
		
		List<EntidadFederativa> lstEntidad = null;
		Criteria query = this.getSession().createCriteria(DgCatEstado.class);
		query.add(Restrictions.isNull("fecRegistroBaja"));
		query.add(Restrictions.eq("indEdoGeografico" , new Boolean(true)));
		
		List<DgCatEstado> dgCatEstado = query.list();
		if(dgCatEstado != null && !dgCatEstado.isEmpty()) {
			lstEntidad = EntidadFederativaParser.persistToModelList(dgCatEstado);
		}
		
		return lstEntidad;
	}
	

	@Override
	public EstadoCivil getCatalogoEstadoCivil(long idCatalogo)  throws DerechohabientesBusinessException, Exception{
		DicEstadoCivil entrada = null;
		try {
			entrada= em.find(DicEstadoCivil.class, idCatalogo);		
		} catch (NoResultException e) {
			entrada = null;
		} catch (Exception e){
			log.error("Error - getCatalogoEstadoCivil", e);
			throw e;
		}
		
		return EstadoCivilParser.persisToModel(entrada);
	}
	
	@Override
	public List<EstadoCivil> getCatalogoEstadoCivil()
			throws DerechohabientesBusinessException{
		
		List<EstadoCivil> lstEstadoCivil = null;
		Criteria query = this.getSession().createCriteria(DicEstadoCivil.class);
		query.add(Restrictions.isNull("fecRegistroBaja"));
		
		List<DicEstadoCivil> lstDicEstadoCivil = query.list();
		if(lstDicEstadoCivil != null && !lstDicEstadoCivil.isEmpty()) {
			lstEstadoCivil = EstadoCivilParser.persistToModelList(lstDicEstadoCivil);
		}
		
		return lstEstadoCivil;
	}
	
	
	

	@Override
	public RazonRegistro getCatalogoRazonRegistro(long idCatalogo)  throws DerechohabientesBusinessException,Exception {
		DicRazonRegistro entrada = null;
		try {
			entrada= em.find(DicRazonRegistro.class, idCatalogo);
		} catch (NoResultException e) {
			entrada = null;
		} catch (Exception e){
			log.error("Error - getCatalogoRazonRegistro", e);
			throw e;
		}
				
		return RazonRegistroParser.persisToModel(entrada);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Parentesco> findParentesco()  throws DerechohabientesBusinessException,Exception{
		List<Parentesco> datos = null;
		List<DicCalidadParentesco> dicCalidadParentesco = null;
		try {
			Query query = em.createQuery("select c from DicCalidadParentesco c ");			
			dicCalidadParentesco = query.getResultList();
		} catch (NoResultException e) {
			dicCalidadParentesco = null;
		} catch (Exception e){
			log.error("Error - findParentesco", e);
			throw e;
		}
		
		datos = ParentescoParser.persisToModelList(dicCalidadParentesco);
		return datos;
	}

	@Override
	public Caracter getCatalogoCaracter(Long idCaracter)
			throws DerechohabientesBusinessException, Exception {
		DicCaracter entrada = null;
		try {
			entrada= em.find(DicCaracter.class, idCaracter);
		} catch (NoResultException e) {
			entrada = null;
		} catch (Exception e){
			log.error("Error - getCatalogoRazonRegistro", e);
			throw e;
		}
				
		return CaracterParser.persisToModel(entrada);
	}
	
	@Override
	public List<Caracter> getCatalogoCaracter()
			throws DerechohabientesBusinessException {
		List<Caracter> lstCaracter = null;
		try {
			Criteria query = this.getSession().createCriteria(DicCaracter.class);
			query.add(Restrictions.isNull("fecRegistroBaja"));
			
			@SuppressWarnings("unchecked")
			List<DicCaracter> lstDicCaracter =  query.list();
			if(lstDicCaracter != null && !lstDicCaracter.isEmpty()) {
				lstCaracter = new ArrayList<Caracter>();
				for(DicCaracter obj: lstDicCaracter){
					lstCaracter.add(CaracterParser.persisToModel(obj));
				}
			}
		
		} catch (NoResultException e) {
			lstCaracter = null;
		} catch (Exception e){
			log.error("Error - getCatalogoRazonRegistro", e);
			throw new DerechohabientesBusinessException(e.getMessage());
		}
		return lstCaracter;
	}
	
	@Override
	public Pais getPaisById(Long idCatalogo)  throws DerechohabientesBusinessException{
		   
		DicPai entrada = null;
		try {
			entrada= em.find(DicPai.class, idCatalogo);		
		} catch (NoResultException e) {
			entrada = null;
		} catch (Exception e){
			log.error("Error - getPaisById", e);
			throw new DerechohabientesBusinessException(e.getMessage());
		}
		
		return PaisParser.persisToModel(entrada);
	}
	
	@Override
	public List<Pais> getCatalogoPais() throws DerechohabientesBusinessException{
		
		List<Pais> lstPais = null;
		Criteria query = this.getSession().createCriteria(DicPai.class);
		query.add(Restrictions.isNull("fecRegistroBaja"));
		
		List<DicPai> lstDicPais = query.list();
		if(lstDicPais != null && !lstDicPais.isEmpty()) {
			lstPais = PaisParser.persisToModelList(lstDicPais);
		}
		
		return lstPais;
	}

	public boolean getCircunscripcion(Long asegurado, Long integrante) throws IllegalArgumentException{
		boolean  bresultado = false;
		Session session = this.getSession();
		String query = "select CVE_ID_TRAMITE from DIT_DETALLE_TRAMITE where CVE_ID_TRAMITE in " +
			"(select CVE_ID_TRAMITE from DIT_TRAMITE where CVE_ID_TIPO_TRAMITE=154 and CVE_ID_ESTADO_TRAMITE=2 and " +
			"CVE_ID_SOLICITUD in (select CVE_ID_SOLICITUD from DIT_PERSONA_INTERESADA_SOL where CVE_ID_PERSONA =" + asegurado + ")) and " +
			"instr(REF_DATOS_TRAMITE_XML,'<idPersona>" + integrante + "</idPersona>')>0";
		SQLQuery queryCirc = session.createSQLQuery(query);
		@SuppressWarnings("unchecked")
		List<String> resultado = (List<String>)queryCirc.list();
		if(!resultado.isEmpty()) {
			bresultado = true;
		}
		return bresultado;
	}

}
