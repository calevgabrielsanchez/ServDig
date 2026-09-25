package mx.imss.ctirss.login.service.ejb.dao;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import mx.imss.ctirss.catalogos.model.DlcRol;
import mx.imss.ctirss.catalogos.model.DlcUsuario;
import mx.imss.ctirss.catalogos.model.DlcUsuarioFuncionario;
import mx.imss.ctirss.framework.base.model.AbstractModel;
import mx.imss.ctirss.framework.base.repository.AbstractRespository;
import mx.imss.ctirss.service.ejb.dao.CatalogoDAOLocal;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;


@Stateless
public class LoginDAOBean<T extends AbstractModel> extends AbstractRespository implements LoginDAOLocal<T> {
	
	/**
	 * Logger
	 */
	private final static Logger logger = Logger.getLogger(LoginDAOBean.class);
	
	@EJB CatalogoDAOLocal<T> daoDelta;
	
	
	@SuppressWarnings("unchecked")
	public DlcUsuario validarCredenciales(T filtro) {
		//r2737
		Criteria criteria = this.getSession().createCriteria(filtro.getClass());
		ArrayList listaPatrones = 	(ArrayList) criteria.list();
		DlcUsuario fUser = (DlcUsuario)filtro;
		DlcUsuario resp = null; 
		for(int i=0; i<listaPatrones.size(); i++){
			DlcUsuario uTemp = (DlcUsuario) listaPatrones.get(i);
			if(uTemp.getNomUsuarioSistema().equals(fUser.getNomUsuarioSistema())){
				resp = uTemp;
			}
		}
		
		if (resp!=null) {
			Criteria critUF = this.getSession().createCriteria(DlcUsuarioFuncionario.class);
			critUF.add(Restrictions.eq("dlcUsuario.cveIdUsuario", resp.getCveIdUsuario()));
			//critUF.add(Restrictions.eq("indVigencia", Boolean.TRUE));
			logger.debug("critUF :: " + critUF);
			List<DlcUsuarioFuncionario> tempUF = critUF.list();
			if (tempUF ==null || tempUF.size()!=1) {
				logger.warn("Debe existir un unico DlcUsuarioFuncionario vigente para el funcionario " + resp.getNomUsuarioSistema());
				return null;
			}else{
				resp.setUsuarioFuncionario(tempUF.get(0));
				Long idRol = consultaRolUsuario(((DlcUsuarioFuncionario)tempUF.get(0)).getDlcUsuario().getCveIdUsuario()).getCveRol();
				if(idRol.equals(1L)){
					resp.setCveSubdelegacion(((DlcUsuarioFuncionario)tempUF.get(0)).getDlcSubdelegacion().getCveSubdelegacion().intValue());
				}
			}
		//	resp.setDlcUsuarioFuncionarios(tempUF);
		//	resp.getUsuarioFuncionario().getSacDelegacion();
		//	resp.getUsuarioFuncionario().getSacSubdelegacion();
		}
		logger.debug("resp :: " + resp);
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	public DlcUsuario validarCredencialesFuncionario(T filtro) {
//		Criteria criteria = this.getSession().createCriteria(DlcUsuario.class);
		String lstrQuery = "from DlcUsuario usr where usr.nomUsuarioSistema=:nomUsr and usr.refPassword=:pwd";
		Query query =  this.getSession().createQuery(lstrQuery).setString("nomUsr", ((DlcUsuario) filtro).getNomUsuarioSistema())
				.setString("pwd", ((DlcUsuario) filtro).getRefPassword());
		
//		criteria.add(org.hibernate.criterion.Restrictions.eq("nomUsuarioSistema", ((DlcUsuario) filtro).getNomUsuarioSistema()));
	//	criteria.add(org.hibernate.criterion.Restrictions.eq("refPassword", ((DlcUsuario) filtro).getRefPassword()));
		
		logger.debug("[usuario=" + ((DlcUsuario) filtro).getNomUsuarioSistema() + ", pwd=" + ((DlcUsuario) filtro).getRefPassword() + "]");
		final DlcUsuario resp =  (DlcUsuario) query.uniqueResult();
		if (resp!=null) {
			Criteria critUF = this.getSession().createCriteria(DlcUsuarioFuncionario.class);
			critUF.add(Restrictions.eq("dlcUsuario.cveIdUsuario", resp.getCveIdUsuario()));
			logger.debug("critUF :: " + critUF);
			List<DlcUsuarioFuncionario> tempUF = critUF.list();
			if (tempUF ==null || tempUF.size()!=1) {
				logger.warn("Debe existir un unico DlcUsuarioFuncionario vigente para el funcionario " + resp.getNomUsuarioSistema());
				return null;
			}
			resp.setUsuarioFuncionario(tempUF.get(0));
			resp.getUsuarioFuncionario().getDlcDelegacion();
			resp.getUsuarioFuncionario().getDlcSubdelegacion();
		}else{
		  logger.debug("resp :: " + resp);
		}
		return resp;
	}
	
	public DlcRol consultaRolUsuario(Long idUsuario){
		DlcRol rol = null;
		DlcRol segRol= null;
		String queryRol="  SELECT" +
				"  pu.CVE_ROL , rol.desc_rol , rol.nom_nombre " +
				"    FROM " +
				"        dlc_perfil_usuario  pu , dlc_rol rol " +
				"    WHERE " +
				"        pu.cve_rol = rol.cve_rol " +
				"    AND " +
				"        CVE_ID_USUARIO ="+ idUsuario.toString();
		
		ArrayList listaFuncionarios = (ArrayList) daoDelta.consultaSQL(queryRol);

		
		if(listaFuncionarios != null && !listaFuncionarios.isEmpty()){
			segRol = new DlcRol();
			Object[] objRespuesta = (Object[])listaFuncionarios.get(0);
			segRol.setCveRol(((BigDecimal)objRespuesta[0]).longValue());
			segRol.setDescRol(""+objRespuesta[1]);
			
		}
		
		return segRol;
	 
	}
	
}
