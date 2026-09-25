package mx.gob.imss.ctirss.correccion.login.service.ejb.dao;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.correccion.catalogos.model.SacDelegacion;
import mx.gob.imss.ctirss.correccion.catalogos.model.SacSubdelegacion;
import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.base.repository.AbstractRespository;
import mx.gob.imss.ctirss.correccion.framework.utils.ConstantesBusiness;
import mx.gob.imss.ctirss.correccion.login.model.SacCargo;
import mx.gob.imss.ctirss.correccion.login.model.SegPerfilUsuario;
import mx.gob.imss.ctirss.correccion.login.model.SegRol;
import mx.gob.imss.ctirss.correccion.login.model.SegUsuario;
import mx.gob.imss.ctirss.correccion.login.model.SegUsuarioFuncionario;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.CatalogoDAOLocal;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.Query;

@Stateless
public class LoginDAOBean<T extends AbstractModel> extends AbstractRespository implements LoginDAOLocal<T> {
	
	/**
	 * Logger
	 */
	private final static Logger logger = Logger.getLogger(LoginDAOBean.class);
	
	@EJB CatalogoDAOLocal<T> daoDelta;
	
	@SuppressWarnings("unchecked")
	public SegUsuario validarCredenciales(T filtro) {
		Criteria criteria = this.getSession().createCriteria(filtro.getClass());
		criteria.add(org.hibernate.criterion.Restrictions.eq("nomUsuarioSistema", ((SegUsuario) filtro).getNomUsuarioSistema()));
		criteria.add(org.hibernate.criterion.Restrictions.eq("refPassword", ((SegUsuario) filtro).getRefPassword()));
		final SegUsuario resp = (SegUsuario) criteria.uniqueResult();
		if (resp!=null) {
			Criteria critUF = this.getSession().createCriteria(SegUsuarioFuncionario.class);
			critUF.add(Restrictions.eq("segUsuario.cveIdUsuario", resp.getCveIdUsuario()));
			logger.debug("critUF :: " + critUF);
			List<SegUsuarioFuncionario> tempUF = critUF.list();
			if (tempUF ==null || tempUF.size()!=1) {
				logger.warn("Debe existir un unico SegUsuarioFuncionario vigente para el funcionario " + resp.getNomUsuarioSistema());
				return null;
			}
			resp.setUsuarioFuncionario(tempUF.get(0));
			resp.getUsuarioFuncionario().getSacDelegacion();
			resp.getUsuarioFuncionario().getSacSubdelegacion();
		}
		logger.debug("resp :: " + resp);
		return resp;
	}
	
	public SegRol consultaRolUsuario(Long idUsuario){
		SegRol rol = null;
		SegRol segRol= null;
		String queryRol="  SELECT" +
				"  pu.CVE_ROL , rol.desc_rol , rol.nom_nombre " +
				"    FROM " +
				"        seg_perfil_usuario  pu , seg_rol rol " +
				"    WHERE " +
				"        pu.cve_rol = rol.cve_rol " +
				"    AND " +
				"        CVE_ID_USUARIO ="+ idUsuario.toString();
		
		ArrayList listaFuncionarios = (ArrayList) daoDelta.consultaSQL(queryRol);

		
		if(listaFuncionarios != null && !listaFuncionarios.isEmpty()){
			segRol = new SegRol();
			Object[] objRespuesta = (Object[])listaFuncionarios.get(0);
			segRol.setCveRol(((BigDecimal)objRespuesta[0]).longValue());
			segRol.setDescRol((String)objRespuesta[1]);
			
		}
		
		
	
		return segRol;
		
		
		
	 
	}

	@Override
	public SegUsuario validarVigencia(SegUsuario segUsuario) {
		// TODO Auto-generated method stub
		Map<Long,String> perfiles=segUsuario.getPerfilesDisponibles();
		SegUsuario user=null;
		StringBuffer consulta = new StringBuffer();
		consulta.append("SELECT usuario FROM SegUsuario usuario,SegUsuarioFuncionario funcionario where ");
		consulta.append("usuario.cveIdUsuario=funcionario.segUsuario.cveIdUsuario and ");
		consulta.append("usuario.nomUsuarioSistema=:nomUsuarioSistema and ");
//		consulta.append("usuario.refPassword=:refPassword and ");
//		consulta.append("usuario.nomNombre=:nomNombre and ");
//		consulta.append("usuario.nomMaterno=:nomMaterno	and ");
//		consulta.append("usuario.nomPaterno=:nomPaterno and ");
		consulta.append("funcionario.indVigencia=:indVigencia and ");
		consulta.append("funcionario.sacDelegacion.cvePk=:cveDelegacion ");
		
		System.out.println("NomUsuarioSistema "+segUsuario.getNomUsuarioSistema());
		System.out.println("RefPassword "+segUsuario.getRefPassword());
		System.out.println("Paterno  "+segUsuario.getNomPaterno());
		System.out.println("Delegacion "+segUsuario.getUsuarioFuncionario().getSacSubdelegacion().getUsuarioFirmado().getCveCodigoDelegacion());
		System.out.println("SubDelegacion "+segUsuario.getUsuarioFuncionario().getSacSubdelegacion().getUsuarioFirmado().getCveCodigoSubDelegacion());
		
		
		
		Query querySec = this.getSession().createQuery(consulta.toString());
		Query query = this.getSession().createQuery(consulta.append(" and funcionario.sacSubdelegacion.cvePk=:cveSubdelegacion " ).toString());
		
		
		
		query.setParameter("nomUsuarioSistema", segUsuario.getNomUsuarioSistema());
//		query.setParameter("refPassword", segUsuario.getRefPassword());
//		query.setParameter("nomNombre", segUsuario.getNomNombre());
//		query.setParameter("nomMaterno", segUsuario.getNomMaterno());
//		query.setParameter("nomPaterno",segUsuario.getNomPaterno());
		query.setParameter("indVigencia", 1);
		
		query.setParameter("cveDelegacion",segUsuario.getUsuarioFuncionario().getSacSubdelegacion().getUsuarioFirmado().getCveCodigoDelegacion());
		query.setParameter("cveSubdelegacion",segUsuario.getUsuarioFuncionario().getSacSubdelegacion().getUsuarioFirmado().getCveCodigoSubDelegacion());
		
		//caso haya cambiado de subdelegacion
		querySec.setParameter("nomUsuarioSistema", segUsuario.getNomUsuarioSistema());
//		querySec.setParameter("refPassword", segUsuario.getRefPassword());
//		querySec.setParameter("nomNombre", segUsuario.getNomNombre());
//		querySec.setParameter("nomMaterno", segUsuario.getNomMaterno());
//		querySec.setParameter("nomPaterno",segUsuario.getNomPaterno());
		querySec.setParameter("indVigencia", 1);
		
		querySec.setParameter("cveDelegacion",segUsuario.getUsuarioFuncionario().getSacSubdelegacion().getUsuarioFirmado().getCveCodigoDelegacion());
		
		
	
		SegUsuario us=(SegUsuario) query.uniqueResult();
		
		if(us!=null){
			//Coicide delegacion y subdelegacion
			String recFunc="FROM SegUsuarioFuncionario func where func.segUsuario.cveIdUsuario=:idUsuario";
			Query qr=this.getSession().createQuery(recFunc);
			qr.setParameter("idUsuario", us.getCveIdUsuario());
			us.setUsuarioFuncionario((SegUsuarioFuncionario) qr.uniqueResult());	
			user=us;
			//return us;
		}else{
			
			us=(SegUsuario) querySec.uniqueResult();
			if(us!=null){
				//Coincide delegacion
				Query usuFunc=this.getSession().createQuery("SELECT funcionario FROM SegUsuarioFuncionario funcionario  where funcionario.segUsuario.cveIdUsuario=:idUsuario");
				Query perfil=this.getSession().createQuery("SELECT perfil FROM SegPerfilUsuario perfil where perfil.segUsuario.cveIdUsuario=:cveIdUsuario");
				usuFunc.setParameter("idUsuario",us.getCveIdUsuario());
				
				perfil.setParameter("cveIdUsuario", us.getCveIdUsuario());
				SegPerfilUsuario perUsuario=(SegPerfilUsuario) perfil.uniqueResult();
				SegUsuarioFuncionario fn=(SegUsuarioFuncionario) usuFunc.uniqueResult();
				fn.setIndVigencia(false);				
				daoDelta.actualiza((T) fn);
			
				SegUsuario usa=generarNuevoUsuarioVigente(segUsuario, true);
				perUsuario.setSegUsuario(usa);
				daoDelta.actualiza((T) perUsuario);
				//return 	usa;	
				user=usa;
			}else{
				//No existe en la BD
				user=generarNuevoUsuarioVigente(segUsuario, false);
			}			
		}
		
			//Seccion de usuarios
		String queryPerfi="FROM SegPerfilUsuario per where per.segUsuario.cveIdUsuario=:idUsuario" ;
		logger.info("Recuperando Perfiles Actuales");
		Query consultaPer=this.getSession().createQuery(queryPerfi);
		consultaPer.setParameter("idUsuario", user.getCveIdUsuario());
		List<SegPerfilUsuario> lista=consultaPer.list();
		for(int s=0;s<lista.size();s++){			
			this.getSession().delete(lista.get(s));
		}
		
		
		String queryRol="FROM SegRol where NOM_NOMBRE=:descripcion";
		Query queryConsultaRol ;
		SegRol rol=null;
		Iterator it=perfiles.entrySet().iterator();
		
		while(it.hasNext()){
			logger.info("Generando Rol");
			Map.Entry e = (Map.Entry)it.next();					
			queryConsultaRol=this.getSession().createQuery(queryRol);
			queryConsultaRol.setParameter("descripcion",e.getValue());
			rol=(SegRol) queryConsultaRol.uniqueResult();
			SegPerfilUsuario prfUsr=new  SegPerfilUsuario();
			prfUsr.setSegUsuario(user);
			prfUsr.setSegRol(rol);
			prfUsr.setTipPerfil(new BigDecimal(2));
			prfUsr.setFecRegistroAlta(Calendar.getInstance().getTime());
			if(rol!=null){
				prfUsr.setDesPerfilUsuario(rol.getDescRol());
			}
			
			daoDelta.agrega((T) prfUsr);
			
		}
		
		logger.info("Devolviendo Usuario Recupeado");
		return user;
	}
	
	
	public SegUsuario generarNuevoUsuarioVigente(SegUsuario usuario, boolean tienePerfil){
		
		SegUsuario seg=(SegUsuario) daoDelta.agrega((T) usuario);
		
		
		SegUsuarioFuncionario fun=new SegUsuarioFuncionario();	
		
		SacDelegacion delegacion=new SacDelegacion();
		SacSubdelegacion subdelegacion=new SacSubdelegacion();
		SacCargo cargo=new SacCargo();
		cargo.setCvePk(2);//subdelegado
		logger.info("Codigo delegacion "+usuario.getUsuarioFuncionario().getSacSubdelegacion().getUsuarioFirmado().getCveCodigoDelegacion());
		if(usuario.getUsuarioFuncionario().getSacSubdelegacion()!=null 
		   && usuario.getUsuarioFuncionario().getSacSubdelegacion().getUsuarioFirmado().getCveCodigoDelegacion()!=null 
		   && !usuario.getUsuarioFuncionario().getSacSubdelegacion().getUsuarioFirmado().getCveCodigoDelegacion().equals("") 
		   && usuario.getUsuarioFuncionario().getSacSubdelegacion().getUsuarioFirmado().getCveCodigoSubDelegacion()!=null
		   && !usuario.getUsuarioFuncionario().getSacSubdelegacion().getUsuarioFirmado().getCveCodigoSubDelegacion().equals("")
				){
			delegacion.setCvePk(Long.valueOf(usuario.getUsuarioFuncionario().getSacSubdelegacion().getUsuarioFirmado().getCveCodigoDelegacion()));	
			subdelegacion.setCvePk(Long.valueOf(usuario.getUsuarioFuncionario().getSacSubdelegacion().getUsuarioFirmado().getCveCodigoSubDelegacion()));
		}
		
		
		
		
		fun.setSegUsuario(seg);
		fun.setSacDelegacion(delegacion);
		fun.setSacSubdelegacion(subdelegacion);
		fun.setSacCargo(cargo);
		fun.setIndVigencia(true);
		seg.setUsuarioFuncionario(fun);		
		daoDelta.agrega((T) fun);
		
		if(!tienePerfil){
			SegPerfilUsuario perfilUsuario=new SegPerfilUsuario();
			SegRol rol=(SegRol) this.getSession().createQuery("SELECT rol FROM SegRol rol where rol.cveRol="+ConstantesBusiness.ROL_AUDITOR).uniqueResult();			
			perfilUsuario.setSegUsuario(seg);
			perfilUsuario.setSegRol(rol);
			perfilUsuario.setTipPerfil(new BigDecimal("2"));
			perfilUsuario.setDesPerfilUsuario(rol.getDescRol());
			daoDelta.agrega((T) perfilUsuario);
		}
		return seg;
	}
}
