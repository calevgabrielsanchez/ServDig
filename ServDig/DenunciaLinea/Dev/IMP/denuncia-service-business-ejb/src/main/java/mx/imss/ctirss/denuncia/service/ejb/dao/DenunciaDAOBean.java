package mx.imss.ctirss.denuncia.service.ejb.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

import mx.gob.imss.ctirss.domiciliosInegi.model.DgDomicilioGeografico;
import mx.imss.ctirss.catalogos.model.DlcGrupo;
import mx.imss.ctirss.catalogos.model.DlcStatus;
import mx.imss.ctirss.framework.base.model.AbstractModel;
import mx.imss.ctirss.framework.base.repository.AbstractRespository;
import mx.imss.ctirss.model.DltDenuncia;
import mx.imss.ctirss.model.DltNroFolio;
import mx.imss.ctirss.model.DltUsuarioden;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

@Stateless
public class DenunciaDAOBean<T extends AbstractModel> extends AbstractRespository  implements DenunciaDAOLocal<T>{
	
	
	/**
	 * Logger
	 */
	private final static Logger logger = Logger.getLogger(DenunciaDAOBean.class);
	
	public List<DltDenuncia> getDenuncias(){
		return null;
	}
	
	/* (non-Javadoc)
	 * @see mx.gob.imss.delta.service.ejb.dao.ICatalogoDAO#agrega(mx.gob.imss.delta.framework.base.model.AbstractModel)
	 */
	public T agrega(T model) throws PersistenceException{
		try{
			this.getSession().saveOrUpdate(model);
			this.getSession().flush();
			return model;
		}catch(RuntimeException re){
			re.printStackTrace();
			logger.error(re.getMessage());
			throw new PersistenceException();
		}
	}
	
	public T actualiza(T model) throws PersistenceException{
		try{
			this.getSession().merge(model);
			this.getSession().flush();
			return model;
		}catch(RuntimeException re){
			re.printStackTrace();
			logger.error(re.getMessage());
			throw new PersistenceException();
		}
	}

	@Override
	public List<DltDenuncia> findDenunciaByUsrDen(T model) {
		DltUsuarioden usrDen = (DltUsuarioden)model;
		List<DltDenuncia> denuncias = new ArrayList();
		try{		  
			Query query= getSession().getNamedQuery("DltDenuncia.findByCveUsrDen");
			query.setLong("cveUsrden", usrDen.getCveUsuarioden());
			denuncias=query.list();
			
		}catch(HibernateException he){
			he.printStackTrace();
		}catch(RuntimeException re){
			re.printStackTrace();
			logger.error(re.getMessage());
			throw new PersistenceException();
		}
		return denuncias;
	}

	@Override
	public List<DltUsuarioden> findUsrDenByMail(String email){
		List<DltUsuarioden> usuariosDen = null;
		try{
			Query query= getSession().getNamedQuery("DltUsuarioden.findByEmail");
			query.setString("email", email);
			usuariosDen=query.list();
			
		}catch(RuntimeException re){
			re.printStackTrace();
			logger.error(re.getMessage());
			throw new PersistenceException();
		}
		
		return usuariosDen;
	}
	
	
	public DltDenuncia findPatron(){
		return null;
	}

	@Override
	public DltDenuncia getDenunciaByClaveFolio(Long cveDenuncia) {
		// TODO Auto-generated method stub		
		StringBuilder query=new StringBuilder();
		query.append("FROM DltDenuncia where CVE_FOLIODENUNCIA=:cveDenuncia");
		Query querySql= getSession().createQuery(query.toString());
		querySql.setParameter("cveDenuncia", cveDenuncia);		
		return (DltDenuncia) querySql.uniqueResult();	
	}
	
	@Override
	public DltDenuncia getDenunciaByNumFolio(String numFolio) {
		// TODO Auto-generated method stub
		StringBuilder query=new StringBuilder();
		query.append("FROM DltDenuncia where NUM_FOLIODENUNCIA=:numFolio");
		Query querySql= getSession().createQuery(query.toString());
		querySql.setParameter("numFolio", numFolio);		
		return (DltDenuncia) querySql.uniqueResult();	
	}

	@Override
	public List<DltDenuncia> getSubdenuncias(DltDenuncia denuncia) {
		// TODO Auto-generated method stub
		StringBuilder query=new StringBuilder();
		query.append("FROM DltDenuncia where CVE_ORIGENDENUNCIA=:cveDenuncia");
		Query querySql= getSession().createQuery(query.toString());
		querySql.setParameter("cveDenuncia", denuncia.getCveFoliodenuncia());		
		return querySql.list();
	}
	
	@Override
	public String obtenerDomicilioPorId(Long idDomicilio) {
		ArrayList lista = null;
		DgDomicilioGeografico dom = null;
		String query = "from DgDomicilioGeografico dom where dom.domicilioId=:cveDomicilio";
		Query consultaDom = getSession().createQuery(query);
		consultaDom.setParameter("cveDomicilio", idDomicilio);
		lista = (ArrayList) consultaDom.list();
		if (lista != null && lista.size() > 0) {
			dom = (DgDomicilioGeografico) lista.get(0);
		}
		

		String domicilio = "";
		if(dom.getDgVialidadByCveViaPrin()!=null){
			domicilio += getStringVar(dom.getDgVialidadByCveViaPrin().getNomVia()).trim() + ", ";
		}
		if(dom.getNumextnum()>0){
			domicilio += "Num Ext " + dom.getNumextnum() ;
		}else{
			domicilio += "Num Ext ";
		}
		
		if(dom.getNumextalf()!=null && !dom.getNumextalf().equals("")){
			domicilio += getStringVar(dom.getNumextalf()).trim();
		}
		if(dom.getNumintnum()!=null && dom.getNumintnum()>0){
			domicilio += ", Num Int "+ dom.getNumintnum();
		}else{
			domicilio += ", Num Int ";
		}
		if(dom.getNumintalf()!=null && !dom.getNumintalf().equals("")){
			domicilio += getStringVar(dom.getNumintalf()).trim();
		}
		if(dom.getDgAsentamiento()!=null){
			domicilio += ", "+getStringVar(dom.getDgAsentamiento().getNomAsen()).trim();
		}
		if(dom.getDgCodigosPostales()!=null && dom.getDgCodigosPostales().getId()!=null){
			domicilio += ", " + getStringVar(dom.getDgCodigosPostales().getId().getCodigo()).trim() ;
		}
		domicilio += ", " + getStringVar(dom.getDgCatLocalidad().getDgCatMunicipio().getNomMun()).trim() + ", " +getStringVar(dom.getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getNomEnt()).trim();
		
		if(dom.getDescripc()!=null){
			domicilio += " (" + dom.getDescripc().trim() + ")";
		}
		//+ jQuery.trim(objDomicilio.asentamiento.localidad.municipio.entidadFederativa.nombre);  

		return  domicilio;
	}

	
	public String getStringVar(String var){
		if(var==null){
			return "";
		}else{
			return var;
		}
	}
	@Override
	public DlcStatus getStatusByClve(Long idEstatus) {
		// TODO Auto-generated method stub
		StringBuilder query=new StringBuilder();
		query.append("FROM DlcStatus where ID_STATUS=:estatus");
		Query querySql= getSession().createQuery(query.toString());
		querySql.setParameter("estatus", idEstatus);		
		return (DlcStatus) querySql.uniqueResult();
	}

	@Override
	public DlcGrupo getGrupoByActv(Long cveActividad) {
		// TODO Auto-generated method stub
		StringBuilder query=new StringBuilder();
		query.append("SELECT grupo FROM DlcGrupo grupo ,DlcActEconomica act where act.cveGrupo=grupo.cveGrupo and act.cveActEconomica=:cveActEconomica");
		Query querySql= getSession().createQuery(query.toString());
		querySql.setParameter("cveActEconomica", cveActividad);		
		return (DlcGrupo) querySql.uniqueResult();
	}

	@Override
	public DltNroFolio recuperaSiguienteFolio(Long anio) {
		// TODO Auto-generated method stub		
		StringBuilder query=new StringBuilder();
		query.append("FROM DltNroFolio where NUM_ANIO=:numAnio  order by NUM_NUMERO desc");
		Query querySql= getSession().createQuery(query.toString());
		querySql.setParameter("numAnio", anio);		
    	List<DltNroFolio> lista=(List<DltNroFolio>) querySql.list();	
		if(!lista.isEmpty()){
			return lista.get(0);
		}else{
			return null;
		}

	}


//	PUBLIC LIST<T> CONSULTA(T FILTRO, STRING NUMFOLIO, LONG TIPODENUNCIANTE) {
//		CRITERIA CRITERIA = THIS.GETSESSION().CREATECRITERIA(FILTRO.GETCLASS());
//		CRITERIA.ADD(RESTRICTIONS.EQ("NUMFOLIODENUNCIA", NUMFOLIO));
//		CRITERIA.SETFETCHMODE("PERSONA", FETCHMODE.JOIN);
////		STRINGBUFFER QUERY = NEW STRINGBUFFER();
////		QUERY = "SELECT DLTPERSONA P FROM DLTPERSONA P, DLTDENUNCIA D WHERE P.CVEFOLIODENUNCIA = D.CVEFOLIODENUNCIA AND D.NUMFOLIODENUNCIA = " + FILTRO.GETCLASS().;
//		LIST<T> RESULT = CRITERIA.LIST();
//		RETURN R;
//		
//	}
}
