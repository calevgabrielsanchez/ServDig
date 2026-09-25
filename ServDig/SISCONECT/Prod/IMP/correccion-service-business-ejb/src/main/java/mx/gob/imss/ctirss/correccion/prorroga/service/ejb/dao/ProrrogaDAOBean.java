package mx.gob.imss.ctirss.correccion.prorroga.service.ejb.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.catalogos.model.CrcTipoCorr;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.base.repository.AbstractRespository;
import mx.gob.imss.ctirss.correccion.model.CrcStatus;
import mx.gob.imss.ctirss.correccion.model.CrtAnexosolcorrpat;
import mx.gob.imss.ctirss.correccion.model.CrtDetBaseCotOmitida;
import mx.gob.imss.ctirss.correccion.model.CrtProrroga;
import mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr;

@Stateless
public class ProrrogaDAOBean <T extends AbstractModel> extends AbstractRespository implements ProrrogaDAOLocal<T> {
	/**
	 * Logger
	 */
	private final static Logger logger = Logger.getLogger(ProrrogaDAOBean.class);
	
	public T agregar(T model) {
		try{
			this.getSession().saveOrUpdate(model);
			this.getSession().flush();
			return model;
		}catch(RuntimeException re){
			System.out.println(".-.ERROR:"+re);
			re.printStackTrace();
			throw new PersistenceException();
		}

	}


	public List<T> consultar(String regPatronal) {
		
		// , int iDisplayStart,int iDisplayLength
		List<T> resultado;
		
		StringBuffer hql = new StringBuffer();
		hql.append( "SELECT new   mx.gob.imss.ctirss.correccion.model.CrtAnexosolcorrpat(solicitud.nuFolio, patron.registroPatronal, solicitud.fecFechaLimite)  " +
				" from mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr solicitud, " +
				" mx.gob.imss.ctirss.correccion.model.SatPatron patron " +
				" WHERE patron.cvePK = solicitud.cvePatron " +
				" AND patron.registroPatronal = :inRegPatronal"  +
				" AND solicitud.fecFechaLimite >= :fechaActual" + 
				" AND solicitud.cveStatus = 2 " +
				" AND solicitud.cveSolicitudCorr NOT IN ( " +
				" select prorroga.cveSolicitudcorr  from mx.gob.imss.ctirss.correccion.model.CrtProrroga prorroga, " +
				" mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr correccion " +
				" WHERE correccion.cveSolicitudCorr = prorroga.cveSolicitudcorr ) " +
				" AND solicitud.cveSolicitudCorr NOT IN ( " +
				" select presenta.cveSolicitudcorr  from mx.gob.imss.ctirss.correccion.model.CrtPresentacorr presenta, " +
				" mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr solcorreccion " +
				" WHERE solcorreccion.cveSolicitudCorr = presenta.cveSolicitudcorr ) " +
				" ORDER BY solicitud.cveSolicitudCorr " 
				); 
		
		Query query = this.getSession().createQuery(hql.toString());
		query.setParameter("inRegPatronal",(regPatronal==null?regPatronal=="":regPatronal));
		query.setParameter("fechaActual",new Date());
		
		//  result = criteria.setFirstResult(params.getiDisplayStart()).setMaxResults(params.getiDisplayLength()).list();
		
		//query.setFirstResult(params.getiDisplayStart());
		//query.setMaxResults(params.getiDisplayLength());
		
		if(query.list().isEmpty())
			resultado = null;
		else
			resultado = (List<T>) query.list();
		
		return resultado;
	}

	
	public T consultarPorFolio(T filtro) {

		final StringBuffer hql = new StringBuffer();
		hql.append("SELECT solicitud from mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr solicitud ");
		hql.append(" WHERE solicitud.nuFolio = '");
		hql.append(((CrtAnexosolcorrpat) filtro).getNuFolio());
		hql.append("'");
		logger.debug("************ Query:  " + hql.toString());
		Query query = this.getSession().createQuery(hql.toString());

		if (query.list().isEmpty())
			filtro = null;
		else
			filtro = (T) query.list().get(0);

		return filtro;

	}


	public T consultarPorClaveAnexoSol(T filtro) {

		Criteria criteria = this.getSession().createCriteria(filtro.getClass());
		
		
	          
		criteria.add(Restrictions.eq("cveSolicitudCorr",((CrtAnexosolcorrpat)filtro).getCveSolicitudCorr()))
								.add(Restrictions.isNull("cvePatronPr"));
		
		
		
		if( criteria.list().isEmpty())
			filtro = null;
		else
		filtro = (T) criteria.list().get(0);
		
		
    return filtro;
	}

	public boolean validaProrroga(CrtSolicitudcorr filtro) {
		
		boolean retVal = false;
		StringBuffer hql = new StringBuffer();
		hql.append( "SELECT solicitud from   mx.gob.imss.ctirss.correccion.model.CrtProrroga solicitud " +
				"  WHERE solicitud.cveSolicitudcorr = " +   filtro.getCveSolicitudCorr());
		System.out.println("ValidaProrroga************** Query:  "+hql.toString());
		Query query = this.getSession().createQuery(hql.toString());
		
		if(!query.list().isEmpty())
			retVal = true;
		
		System.out.println("ValidaProrroga************** Resultado:  "+retVal);

     return retVal;
	}
	
	public boolean validaPresentacionCorr(CrtSolicitudcorr filtro) {
		
		boolean retVal = false;
		StringBuffer hql = new StringBuffer();
		hql.append( "SELECT presenta from   mx.gob.imss.ctirss.correccion.model.CrtPresentacorr presenta " +
				"  WHERE presenta.cveSolicitudcorr = " +   filtro.getCveSolicitudCorr());
		System.out.println("ValidaPresentacionCorreccion************** Query:  "+hql.toString());
		Query query = this.getSession().createQuery(hql.toString());
		
		if(!query.list().isEmpty())
			retVal = true;
		
		System.out.println("ValidaPresentacionCorreccion************** Resultado:  "+retVal);

     return retVal;
	}

	public String obtenerRegPatronal(Integer cveSolicitud) {
		
		String retVal = "";
		StringBuffer hql = new StringBuffer();
		hql.append( "SELECT new java.lang.String(patron.registroPatronal) from   mx.gob.imss.ctirss.correccion.model.SatPatron patron," +
				" mx.gob.imss.ctirss.correccion.model.CrtSolicitudcorr solicitud " +
				" WHERE patron.cvePK = solicitud.cvePatron " +
				" AND solicitud.cveSolicitudCorr = :clave" );
		System.out.println("obtenerRegPatronal************** Query:  "+hql.toString());
		Query query = this.getSession().createQuery(hql.toString());
		query.setParameter("clave",cveSolicitud);
		
		if(!query.list().isEmpty())
			retVal = (String)query.list().get(0);
		
		System.out.println("obtenerRegPatronal************** Resultado:  "+retVal);

     return retVal;
	}
	
	public CrtAnexosolcorrpat consultarDom(long domicilioId) {
		
	CrtAnexosolcorrpat resultado;
	
	//String calle, String numExterior, 	String numExteriorAlfa, String numInterior, String numInteriorAlfa,
	//String colonia, String localidad, String entidadFederativa,
	//String codigoPostal, String municipio
		
		StringBuffer hql = new StringBuffer();
		hql.append( "SELECT new   mx.gob.imss.ctirss.correccion.model.CrtAnexosolcorrpat(domicilio.nomvial, domicilio.numextnum, " +
				" domicilio.numextalf, domicilio.numintnum, " +
				" domicilio.numintalf, asentamiento.nomAsen, localidad.nomLoc, estado.nomEnt, domicilio.codigo, municipio.nomMun )  " +
				" from mx.gob.imss.ctirss.domiciliosInegi.model.DgDomicilioGeografico domicilio, mx.gob.imss.ctirss.domiciliosInegi.model.DgCatEstado estado, " +
				" mx.gob.imss.ctirss.domiciliosInegi.model.DgCatMunicipio municipio, mx.gob.imss.ctirss.domiciliosInegi.model.DgCatLocalidad localidad, " +
				" mx.gob.imss.ctirss.domiciliosInegi.model.DgAsentamiento asentamiento " +
				" WHERE domicilio.cveEnt = estado.cveEnt " +
				" AND domicilio.cveMun = municipio.cveMun " +
				" AND domicilio.cveEnt = municipio.cveEnt "  +
				" AND domicilio.cveEnt = localidad.cveEnt " + 
				" AND domicilio.cveMun = localidad.cveMun " +
				" AND domicilio.cveLoc = localidad.cveLoc " +
				" AND domicilio.cveAsen = asentamiento.cveAsen" +
				" AND domicilio.domicilioId = :domicilioId" );
		
		System.out.println("consultarDom************ Query:  "+hql.toString());
		Query query = this.getSession().createQuery(hql.toString());
		query.setParameter("domicilioId",domicilioId);
		
		if(query.list().isEmpty())
			resultado = null;
		else
			resultado = (CrtAnexosolcorrpat) query.list().get(0);
		
		return resultado;
	}


	@Override
	public List<T> consultarAP(T model) {
		List<T> lstResult = new ArrayList<T>();
		
		CrtProrroga prorroga = (CrtProrroga) model;
		if(prorroga != null && prorroga.getFecInicio() != null || prorroga.getFecFinal() != null){
			
			Criteria criteria = this.getSession().createCriteria(model.getClass());
			//criteria.add(Restrictions.isNull("cveStatus"));
			criteria.add(Restrictions.or(Restrictions.isNull("cveStatus"), Restrictions.eq("cveStatus", 1L)));
			System.out.println("Estatusee");
			if(prorroga != null && prorroga.getFecInicio() != null ){
				criteria.add(Restrictions.ge("fecElaborasolpro", this.parserDate(prorroga.getFecInicio())));
			}
			if(prorroga != null && prorroga.getFecFinal() != null){
				criteria.add(Restrictions.le("fecElaborasolpro", this.parserDate(prorroga.getFecFinal())));
			}			
				
			if (criteria.list().size() > 0)
				lstResult =  criteria.list();
		}
	
	return lstResult;
	}


	@SuppressWarnings({"unchecked","rawtypes"})
	@Override
	public List<T> llenarStatus() {
		
		List lst = new ArrayList();
		lst.add(2L);
		lst.add(3L);
		Criteria criteria = this.getSession().createCriteria(CrcStatus.class);
		criteria.add(Restrictions.in("cveStatus", lst));
		
		return criteria.list();
	}


	@Override
	public T buscaTipoCorr(Long id) {
		
		Criteria criteria = this.getSession().createCriteria(CrcTipoCorr.class);
		criteria.add(Restrictions.eq("cveTipocorr", id));
		
		return (T) criteria.uniqueResult();
	}
	
	private Date parserDate(String fecha){
		
		Date myDate = null;
		// Format the current time.
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		// Parse the string into a Date.
		
		try {
			myDate = formatter.parse(fecha);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println("#### Fecha : " + myDate);
		return myDate;
	}


	@Override
	public void guardaStatus(List<T> lst) {

		List<CrtProrroga> lstProrroga = (List<CrtProrroga>) lst;
		if(lstProrroga != null && lstProrroga.size() > 0){
			for (Iterator iterator = lstProrroga.iterator(); iterator.hasNext();) {
				CrtProrroga crtProrroga = (CrtProrroga) iterator.next();
				String status = crtProrroga.getStatus();
				String cveUsuario = crtProrroga.getCveUser();
				if(crtProrroga.getCveSolprorroga() != 0L){
					Criteria criteria = this.getSession().createCriteria(CrtProrroga.class);
					criteria.add(Restrictions.eq("cveSolprorroga", crtProrroga.getCveSolprorroga()));
					crtProrroga = (CrtProrroga) criteria.uniqueResult();
					// busca id status
					Criteria criterias = this.getSession().createCriteria(CrcStatus.class);
					criterias.add(Restrictions.eq("cveStatus", new Long(status)));
					CrcStatus crcStatus = (CrcStatus) criterias.uniqueResult();
					
					crtProrroga.setCveStatus(crcStatus.getCveStatus());
					crtProrroga.setCveUser(cveUsuario);
					crtProrroga.setFecFechareg(new Date());
					// guardamos
					this.getSession().saveOrUpdate(crtProrroga);
					this.getSession().flush();
				}
				
			}
		}
		
		
		
	}
	
}
