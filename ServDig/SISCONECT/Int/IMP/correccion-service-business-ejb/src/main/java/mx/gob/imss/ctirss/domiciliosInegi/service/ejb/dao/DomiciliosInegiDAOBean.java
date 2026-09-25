package mx.gob.imss.ctirss.domiciliosInegi.service.ejb.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.PersistenceException;
import javax.transaction.UserTransaction;

import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.core.io.ClassPathResource;

import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosEntradaPaginador;
import mx.gob.imss.ctirss.correccion.base.paginador.model.DatosSalidaPaginador;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.base.repository.AbstractRespository;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.impl.CatalogoDAOBean;
import mx.gob.imss.ctirss.delta.exception.domicilio.DomicilioNoLocalizadoException;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.domiciliosInegi.model.DgDomicilioGeografico;
import mx.gob.imss.ctirss.domiciliosInegi.model.DgVialidad;

@Stateless
public class DomiciliosInegiDAOBean<T extends AbstractModel> extends AbstractRespository implements DomiciliosInegiDAOLocal<T> {
	

	 
	
	public T agrega(T model) throws PersistenceException{
		try{
			
			((DgDomicilioGeografico)model).getDgAsentamiento().getId().setCveEnt(((DgDomicilioGeografico)model).getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getCveEnt());
			((DgDomicilioGeografico)model).getDgAsentamiento().getId().setCveLoc(((DgDomicilioGeografico)model).getDgCatLocalidad().getId().getCveLoc());
			((DgDomicilioGeografico)model).getDgAsentamiento().getId().setCveMun(((DgDomicilioGeografico)model).getDgCatLocalidad().getDgCatMunicipio().getId().getCveMun());
			((DgDomicilioGeografico)model).getDgAsentamiento().getId().setCvePeriodo(1);
			
			((DgDomicilioGeografico)model).getDgCatLocalidad().getId().setCveEnt(((DgDomicilioGeografico)model).getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getCveEnt());
			((DgDomicilioGeografico)model).getDgCatLocalidad().getId().setCveMun(((DgDomicilioGeografico)model).getDgCatLocalidad().getDgCatMunicipio().getId().getCveMun());
			((DgDomicilioGeografico)model).getDgCatLocalidad().getId().setCvePeriodo(1);
			
			((DgDomicilioGeografico)model).getDgCatLocalidad().getDgCatMunicipio().getId().setCveEnt(((DgDomicilioGeografico)model).getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getCveEnt());
			
			((DgDomicilioGeografico)model).getDgCodigosPostales().getId().setCveAsen(((DgDomicilioGeografico)model).getDgAsentamiento().getId().getCveAsen());
			((DgDomicilioGeografico)model).getDgCodigosPostales().getId().setCveEnt(((DgDomicilioGeografico)model).getDgCatLocalidad().getDgCatMunicipio().getDgCatEstado().getCveEnt());
			((DgDomicilioGeografico)model).getDgCodigosPostales().getId().setCveLoc(((DgDomicilioGeografico)model).getDgCatLocalidad().getId().getCveLoc());
			((DgDomicilioGeografico)model).getDgCodigosPostales().getId().setCveMun(((DgDomicilioGeografico)model).getDgCatLocalidad().getDgCatMunicipio().getId().getCveMun());
			((DgDomicilioGeografico)model).getDgCodigosPostales().getId().setCvePeriodo(1);
			((DgDomicilioGeografico)model).getDgCodigosPostales().setDgAsentamiento(((DgDomicilioGeografico)model).getDgAsentamiento());

			DgVialidad dgVialiad3 = ((DgDomicilioGeografico)model).getDgVialidadByCveViaRef3();
			if(dgVialiad3==null || 
					(dgVialiad3!=null && dgVialiad3.getCveVia()==null) || 
					(dgVialiad3!=null && dgVialiad3.getCveVia()!=null && dgVialiad3.getCveVia().intValue()<=0)){
				
				((DgDomicilioGeografico)model).setDgVialidadByCveViaRef3(null);
				
			}
				
			
			
			this.getSession().saveOrUpdate(model);
			this.getSession().flush();
			return model;
		}catch(RuntimeException re){
			System.out.println(".-.ERROR:"+re);
			re.printStackTrace();
			throw new PersistenceException();
		}

	}
	
	public void elimina(T model) {
		model = (this.consultaPorClave(model));
		this.getSession().delete(model);
		this.getSession().flush();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> consulta(T filtro) {
		Criteria criteria = this.getSession().createCriteria(filtro.getClass());
		Example e = DomiciliosInegiDAOBean.createExampleOf(filtro);
		criteria.add(e);
		List<T> resultados = criteria.list();
		
		return resultados;
	}
	
	public T modifica(T model) {
		this.getSession().merge(model);
		this.getSession().flush();
		return model;
	}
	
	public T consultaPorClave(T filtro) {
//		Criteria criteria = this.getSession().createCriteria(filtro.getClass())
//											 .add(Restrictions.eq("domicilioId", ((DgDomicilioGeografico)filtro).getDomicilioId()));
//		System.out.println(((DgDomicilioGeografico)filtro).getDomicilioId());
//		try{
//			
//			filtro = (T) criteria.list().get(0);
//			
//		}catch(Exception e){ filtro = null;
//		e.printStackTrace();
//		}
		
		return filtro;
	}
	
		
	public DatosSalidaPaginador<T> pagina(DatosEntradaPaginador<T> params) {
		DatosSalidaPaginador<T> response = new DatosSalidaPaginador<T>();		

		List<T> result = null;
		
		Criteria criteria = this.getSession().createCriteria(params.getModelo().getClass());	
		Example e = CatalogoDAOBean.createExampleOf(params.getModelo());
		criteria.add(e);
		
		/*Se valida si la busqueda debera restringir los resultados por baja logica*/
		List<String> lsFiltrosBajLogica = CatalogoDAOBean.getFiltrosBajaLogica(params.getModelo());
		for(String sFiltro:lsFiltrosBajLogica){
			criteria.add(Restrictions.isNull(sFiltro));
		}

		/**
		 * Total records, before filtering (i.e. the total number of records in
		 * the database)
		 */
		int iTotalRecords = 0;
		/*Se debe de obtener el numero total de registros en la base de datos*/
		iTotalRecords = ((Long) criteria.setProjection(Projections.rowCount())
				.list().get(0)).intValue();
		
		criteria.setProjection(null);

		/**
		 * Total records, after filtering (i.e. the total number of records
		 * after filtering has been applied - not just the number of records
		 * being returned in this result set)
		 */
		int iTotalDisplayRecords = 0;
		
		iTotalDisplayRecords = ((Long)criteria.setProjection(Projections.rowCount()).list().get(0)).intValue();
		 criteria.setProjection(null);

		 criteria.setResultTransformer(Criteria.ROOT_ENTITY);
		 
		 result = criteria.setFirstResult(params.getiDisplayStart())
				.setMaxResults(params.getiDisplayLength()).list();
	     
		response.setAaData(result);
		response.setiTotalDisplayRecords(iTotalDisplayRecords);
		response.setiTotalRecords(iTotalRecords);
		
		return response;
	}



	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public DgDomicilioGeografico getDomicilioBDTU(DgDomicilioGeografico domicilio) {
		
		logger.info("Recuperando Servicio Domicilios");
		if(domicilio.getDomicilioId()==0){
			
			return null;
		}
		
		Hashtable<String, String> h = new Hashtable<String, String>(7);
		Object ob = null;
		h.put(Context.INITIAL_CONTEXT_FACTORY, getPropiedad("service.provider.context"));
		h.put(Context.PROVIDER_URL, getPropiedad("service.provider.url"));
		h.put(Context.SECURITY_PRINCIPAL, getPropiedad("service.security.principal"));
		h.put(Context.SECURITY_CREDENTIALS, getPropiedad("service.security.credentials"));
		DomicilioServiceBusinessRemote domicilioServiceRemote;
		InitialContext context = null;
		try {
			context = new InitialContext(h);
			ob = context.lookup(getPropiedad("service.jndi.bean"));
			
			if(ob!=null){
				domicilioServiceRemote=(DomicilioServiceBusinessRemote)ob;	
				Domicilio dom=new Domicilio();
				System.out.println("Recuperando "+domicilio.getDomicilioId());
				dom.setClave(new Long(domicilio.getDomicilioId()).intValue());
				System.out.println("Invocanto");
				Object d=domicilioServiceRemote.consultarDomicilio(dom);
				System.out.println("Antes del Cast");
				dom=(Domicilio) d;
				domicilio.setDomicilioBDTU(dom);
				
			}	
			context.close();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			domicilio=null;
		} catch (DomicilioNoLocalizadoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			domicilio=null;
		}finally{
			try {
				context.close();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		
		return domicilio;
	}
	
	  private String getPropiedad(String propiedad) {
          String propertie = null;
          Properties properties = new Properties();         
          try {
               InputStream is = new ClassPathResource("config.properties").getInputStream();
               properties.load(is);
               is.close();
               propertie = properties.getProperty(propiedad);
          } catch (IOException e) {
                  e.printStackTrace();
                  return "";
          } catch (Exception e) {
                  e.printStackTrace();
                  return "";
          }
          return propertie;
	  }
	
}
