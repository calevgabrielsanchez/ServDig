package mx.gob.imss.ctirss.sso.admonusuarios.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import mx.gob.imss.ctirss.admonusuarios.entities.AccesoModulo;
import mx.gob.imss.ctirss.admonusuarios.entities.AreaNormativa;
import mx.gob.imss.ctirss.admonusuarios.entities.Delegacion;
import mx.gob.imss.ctirss.admonusuarios.entities.Departamento;
import mx.gob.imss.ctirss.admonusuarios.entities.DeptoModulo;
import mx.gob.imss.ctirss.admonusuarios.entities.Estatus;
import mx.gob.imss.ctirss.admonusuarios.entities.Modulo;
import mx.gob.imss.ctirss.admonusuarios.entities.PerfilesSolicitud;
import mx.gob.imss.ctirss.admonusuarios.entities.Puesto;
import mx.gob.imss.ctirss.admonusuarios.entities.Solicitudes;
import mx.gob.imss.ctirss.admonusuarios.entities.Subdelegacion;
import mx.gob.imss.ctirss.admonusuarios.entities.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.admonusuarios.entities.Usuario;
import mx.gob.imss.ctirss.admonusuarios.entities.UsuarioFuncionario;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.AprobadorDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.AreaNormativaDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.DelegacionDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.DepartamentoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.ModuloDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.PuestoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SolicitudDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SubdelegacionDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UmfDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UnidadMedicaDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;
import mx.gob.imss.ctirss.sso.admonusuarios.services.CatalogosSessionLocal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Session Bean implementation class CatalogosSession
 */
@Stateless (name="catalogosSession", mappedName="catalogosSession")
public class CatalogosSession implements CatalogosSessionLocal {
	
	/** Logger para el servicio de catalogos */
	private static final Log log = LogFactory.getLog(CatalogosSession.class);
	
	@PersistenceContext(unitName="DeltaUsuariosPU")
	private EntityManager em;

	@Override
	public List<DelegacionDTO> listarDelegacion() throws AdmonUsuariosException {
		final List<Delegacion> delegacionDB = obtenerDelegacion();
        final List<DelegacionDTO> delegacion = new ArrayList<DelegacionDTO>(delegacionDB.size());
        for (Delegacion del : delegacionDB) {
        	DelegacionDTO to = new DelegacionDTO();
        	to.setCveDelegacion(del.getIdDelegacion());
        	to.setNombreDelegacion(del.getDescripcionDelegacion());
            delegacion.add(to);
        }
        return delegacion;
    }
	
	@Override
	public List<AreaNormativaDTO> listarAreaNormativa () throws AdmonUsuariosException {
		final List<AreaNormativa> aN = obtenerAreasNormativas();
        final List<AreaNormativaDTO> areaNormativa = new ArrayList<AreaNormativaDTO>(aN.size());
        for (AreaNormativa area : aN) {
        	AreaNormativaDTO an = new AreaNormativaDTO();
        	an.setCveSsoareanorma(area.getIdAreaNormativa());
        	an.setDesAreanorma(area.getDescripcionAreaNormativa());
        	areaNormativa.add(an);
        }
        return areaNormativa;	
	}
	
	@Override
	public List<SolicitudDTO> listarSolicitudes () throws AdmonUsuariosException {  //Lista las solicitudes pendientes
		List<Solicitudes> solicitudes = obtenerSolicitudesPendientes();
		List<SolicitudDTO> listaSolicitudes = new ArrayList<SolicitudDTO>(solicitudes.size());
        for (Solicitudes solicitud : solicitudes) {
         	SolicitudDTO sol = new SolicitudDTO();
        	sol.setCveSsosolicitud(solicitud.getIdSolicitud());
        	sol.setNomNombre(solicitud.getNomNombre());
        	sol.setNomPaterno(solicitud.getNomPaterno());
        	sol.setNomMaterno(solicitud.getNomMaterno());
        	sol.setRefCorreoElectronico(solicitud.getNomCorreoElectonico());
        	sol.setFecRegistroAlta(solicitud.getRegistroAlta());
        	sol.setCveMatricula(solicitud.getNomMatricula());
        	//sol.setDesEstatus(solicitud.getEstatus().toString());
        	DelegacionDTO del =  new DelegacionDTO();
        	try{
        	 del.setCveDelegacion(solicitud.getDelegacion().getIdDelegacion());
        	}
        	catch(Exception e){
        	 del.setCveDelegacion(-99);
        	}
        	sol.setDelDTO(del);
        	SubdelegacionDTO sdel =  new SubdelegacionDTO();
        	try{
        	sdel.setCveSubelegacion(solicitud.getSubdelegacion().getIdSubdelegacion());
        	}catch(Exception e){
        		sdel.setCveSubelegacion(-99);
        	}
        	sol.setSubdelDTO(sdel);
        	UmfDTO umf = new UmfDTO();
        	try{
        	umf.setCveUmf(solicitud.getUnidadMedicaFamiliar().getIdUmf());
        	}
        	catch(Exception e){
        		umf.setCveUmf(new Long(-99));
        	}
        	sol.setUmfDTO(umf);
        	try{
        	sol.setFecUsrNacimiento(solicitud.getFechaNacimiento());
        	}
        	catch(Exception e){
        	}
        	sol.setDesUsrCurp(solicitud.getCurp());
        	DepartamentoDTO dep =  new DepartamentoDTO();
        	try{
        	dep.setCveSsodepto(solicitud.getDepartamento().getCveDepartamento());
        	}
        	catch(Exception e){
        		dep.setCveSsodepto(-99);
        	}  
        	PuestoDTO puesto = new PuestoDTO();
        	try{
        	puesto.setCvePuesto(solicitud.getPuesto().getCvePuesto().intValue());
        	}
        	catch(Exception e){
        		puesto.setCvePuesto(-99);
        	}
        	sol.setDptoDTO(dep);
        	sol.setPuestoDTO(puesto);
        	listaSolicitudes.add(sol);  
        }
        return listaSolicitudes;	
	}
	
	@Override
	public List<SolicitudDTO> listarSolicitudesGeneradas( ) throws AdmonUsuariosException {
		List<SolicitudDTO> listaSolicitudes = new ArrayList<SolicitudDTO>();
		Query query = em.createNamedQuery("Solicitudes.findAll");
		List<Solicitudes> lista = query.getResultList();
		Delegacion del;
		for(Solicitudes sol : lista){
			SolicitudDTO solicitud = new SolicitudDTO();
			solicitud.setCveSsosolicitud(sol.getIdSolicitud());
			solicitud.setNomNombre(sol.getNomNombre());
			solicitud.setNomPaterno(sol.getNomPaterno());
			solicitud.setNomMaterno(sol.getNomMaterno());
			solicitud.setRefCorreoElectronico(sol.getNomCorreoElectonico());
			solicitud.setFecRegistroAlta(sol.getRegistroAlta());
			solicitud.setCveMatricula(sol.getNomMatricula());
			//solicitud.setCveSsoEstatus(sol.getEstatus());
			try{
			//solicitud.setCve_id_delegacion(sol.getDelegacion().getIdDelegacion().intValue());
			}
			catch(Exception e){
			  //solicitud.setCve_id_delegacion(0);
			}
			try{
			    //solicitud.setCve_id_sub_delegacion(sol.getSubdelegacion().getIdSubdelegacion().intValue());
			}
			catch(Exception e){
				//solicitud.setCve_id_sub_delegacion(0);
			}
			try{
			   // solicitud.setCve_id_umf(sol.getUnidadMedicaFamiliar().getIdUmf().intValue());
			}
			catch(Exception e){
				//solicitud.setCve_id_umf(0);
			}
			solicitud.setFecUsrNacimiento(sol.getFechaNacimiento());
			try{
			//solicitud.setCve_id_entidad(sol.getDgCatEstado().getCveEntidad().intValue());
			}catch(Exception e){
				//solicitud.setCve_id_entidad(0);
			}
			solicitud.setDesUsrCurp(sol.getCurp());
			try{
			//solicitud.setDptoDTO(sol.getDepartamento());
			}
			catch(Exception e){
				//solicitud.setCve_ssodepto(0);
			}
			//solicitud.setClavePuesto(sol.getPuesto().getCvePuesto().intValue());
			query = em.createNamedQuery("Departamento.findById");
			query.setParameter("id",solicitud.getDptoDTO().getCveSsodepto());  
			Departamento dep = (Departamento) query.getSingleResult();
			
			query = em.createNamedQuery("AreaNormativa.findById");
			query.setParameter("id",dep.getAreaNormativa().getIdAreaNormativa()); 
			AreaNormativa an = (AreaNormativa) query.getSingleResult();
			
			query = em.createNamedQuery("Delegacion.findId");
			query.setParameter("id",solicitud.getDelDTO().getCveDelegacion());
			try{
			    del = (Delegacion) query.getSingleResult();
			}
			catch(Exception e){
				del = null;
			}
			try{
			query = em.createNamedQuery("Subdelegacion.findSubID");
			query.setParameter("id",solicitud.getDelDTO().getCveDelegacion());  
			Subdelegacion sdel = (Subdelegacion) query.getSingleResult();
			//solicitud.setSubdelDTO(sdel);
			}
			catch(Exception e){
				//solicitud.setDesSubdelegacion("");
			}
			query = em.createNamedQuery("Puesto.findByClave");
			query.setParameter("id",solicitud.getPuestoDTO().getCvePuesto());  
			Puesto pst = (Puesto) query.getSingleResult();
			
			query = em.createNamedQuery("Estatus.findByClave");
			query.setParameter("id",solicitud.getCveSsosolicitud());  
			Estatus estatus = (Estatus) query.getSingleResult();
			
			query = em.createNamedQuery("PerfilesSolicitud.findAllByClave");
			query.setParameter("id",solicitud.getCveSsosolicitud());  
			List<PerfilesSolicitud> listperSol = query.getResultList();
			int numPerfiles = 0;
			if(listperSol.size()>0){
			for(PerfilesSolicitud persol : listperSol){
				numPerfiles ++;
			   }
			}
			int numModulos = 0;
			query = em.createNamedQuery("AccesoModulo.findAllByClave");
			query.setParameter("id",solicitud.getCveSsosolicitud());  
			List<AccesoModulo> listmodSol = query.getResultList();
			if(listmodSol.size()>0){
			for(AccesoModulo mod : listmodSol){
				numModulos ++;
			  }
			}
		//	solicitud.setNombreCompleto(solicitud.getNombre()+" "+solicitud.getNomPaterno()+" "+solicitud.getNomMaterno());
		/*	if(an!=null)
			solicitud.setDesArea(an.getDescripcionAreaNormativa());
			if(dep!=null){
			solicitud.setDesDepartamento(dep.getNombreDepto());
			}
			else{
			 solicitud.setDesDepartamento("");
			}  */
			if(del!=null){
				DelegacionDTO deleg = new DelegacionDTO();
				deleg.setNombreDelegacion(del.getDescripcionDelegacion());
				solicitud.setDelDTO(deleg);
			}
			else{
				DelegacionDTO deleg = new DelegacionDTO();
				deleg.setNombreDelegacion("");
				solicitud.setDelDTO(deleg);
			}
			PuestoDTO rol = new PuestoDTO();
			rol.setNombrePuesto(pst.getNombrePuesto());
			solicitud.setPuestoDTO(rol);
			//solicitud.setDesEstatus(estatus.getNombreEstatus());
			//solicitud.setNumPerfiles(numPerfiles);
			//solicitud.setNumModulos(numModulos);
			listaSolicitudes.add(solicitud);
		}
		return listaSolicitudes;
	}
	
	@Override
	public List<DepartamentoDTO> listarDepartamentos() throws AdmonUsuariosException {
		final List<Departamento> deptos = obtenerDepartamentos();
        final List<DepartamentoDTO> departamentos = new ArrayList<DepartamentoDTO>(deptos.size());
        for (Departamento departamento : deptos) {
        	DepartamentoDTO depto = new DepartamentoDTO();
        	depto.setCveSsodepto(departamento.getCveDepartamento());
        	depto.setDesDepartamento(departamento.getNombreDepto());
        	departamentos.add(depto);
        }
        return departamentos;
	}

	@Override
	public List<DepartamentoDTO> listarDepartamentosByAreaNormativa(String cveAreaNormativa) throws AdmonUsuariosException {
		final List<Departamento> deptos = obtenerDepartamentoByAreaNormativa(cveAreaNormativa);
        final List<DepartamentoDTO> departamentos = new ArrayList<DepartamentoDTO>(deptos.size());
        for (Departamento departamento : deptos) {
        	DepartamentoDTO depto = new DepartamentoDTO();
        	depto.setCveSsodepto(departamento.getCveDepartamento());
        	depto.setDesDepartamento(departamento.getNombreDepto());
        	departamentos.add(depto);
        }
        return departamentos;
	}

	@Override
	public List<PuestoDTO> listarPuestos() throws AdmonUsuariosException {
		final List<Puesto> ptos = obtenerPuestos();
        final List<PuestoDTO> puestos = new ArrayList<PuestoDTO>(ptos.size());
        for (Puesto pto : ptos) {
        	PuestoDTO puesto = new PuestoDTO();
        	puesto.setCvePuesto(pto.getCvePuesto());
        	puesto.setNombrePuesto(pto.getNombrePuesto());
        	puestos.add(puesto);
        }
        return puestos;
	}

	@Override
	public List<PuestoDTO> listarPuestoByDepartamento(String cvePuesto) throws AdmonUsuariosException {
		final List<Puesto> ptos = obtenerPuestoByDepartamento(cvePuesto);
        List<PuestoDTO> puestos = new ArrayList<PuestoDTO>();
        for (Puesto pto : ptos) {
        	PuestoDTO puesto = new PuestoDTO();
        	puesto.setCvePuesto(pto.getCvePuesto());
        	puesto.setNombrePuesto(pto.getNombrePuesto());
        	puestos.add(puesto);
        }
        return puestos;
	}
	
	@Override
	public PuestoDTO listarPuestoByClave(int clavePuesto) throws AdmonUsuariosException{
		Query q = em.createNamedQuery("Puesto.findByClave");
		q.setParameter("id", clavePuesto);
	    Puesto p= (Puesto) q.getSingleResult();
	    PuestoDTO puesto = new PuestoDTO();
	    puesto.setCvePuesto(p.getCvePuesto());
	    puesto.setNombrePuesto(p.getNombrePuesto());
	    return puesto;
	}
	
	@Override
	public List<ModuloDTO> listarModulosByDepartamento(String cveDepartamento) throws AdmonUsuariosException {
		final List<DeptoModulo> deptosModulos = obtenerModulosByDepartamento(cveDepartamento);
        final List<ModuloDTO> modulos = new ArrayList<ModuloDTO>(deptosModulos.size());
        for (DeptoModulo deptoMod : deptosModulos) {
        	if(deptoMod!=null&&deptoMod.getModulo()!=null)
        	{
            	ModuloDTO modulo = new ModuloDTO();
            	modulo.setCveIdModulo(deptoMod.getModulo().getIdModulo().intValue());
            	modulo.setDesModulo(deptoMod.getModulo().getDescripcion());
            	modulos.add(modulo);
        	}
        }
        return modulos;
	}
	
	@Override
	public List<SubdelegacionDTO> listarSubdelegacion(final String idDelegacion)
			throws AdmonUsuariosException {
		final List<Subdelegacion> subdelegacionDB = obtenerSubdelegacion(idDelegacion);
        final List<SubdelegacionDTO> subdelegacion = new ArrayList<SubdelegacionDTO>(subdelegacionDB.size());
        for (Subdelegacion sub : subdelegacionDB) {
        	SubdelegacionDTO to = new SubdelegacionDTO();
        	to.setCveSubelegacion(sub.getIdSubdelegacion());
        	to.setNombreSubelegacion(sub.getDescripcionSubelegacion());
            subdelegacion.add(to);
        }
        return subdelegacion;
	}

	@Override
	public List<UnidadMedicaDTO> listarUnidadMedica(final String idSubdelegacion)throws AdmonUsuariosException {
		final List<UnidadMedicaFamiliar> umfDB = obtenerUnidadMedica(idSubdelegacion);
        final List<UnidadMedicaDTO> unidadMedica = new ArrayList<UnidadMedicaDTO>(umfDB.size());
        for (UnidadMedicaFamiliar unidad : umfDB) {
        	UnidadMedicaDTO to = new UnidadMedicaDTO();
        	to.setCveUmf(String.valueOf(unidad.getIdUmf()));
        	to.setNombreUmf(unidad.getDescripcionUmf());
        	unidadMedica.add(to);
        }
        return unidadMedica;
	}
	
	@Override
	public  PuestoDTO consultaPerfilBD(String perfil) throws AdmonUsuariosException
	{
		Query q = em.createNamedQuery("Puesto.findByDescripcion");
		q.setParameter("nomPuesto", perfil);
	    Puesto p= (Puesto) q.getSingleResult();
	    
	    q = em.createNamedQuery("Departamento.findById");
		q.setParameter("id", p.getDepartamento().getCveDepartamento());
	    Departamento d= (Departamento) q.getSingleResult();
	    
	    q = em.createNamedQuery("AreaNormativa.findById");
		q.setParameter("id", d.getAreaNormativa().getIdAreaNormativa());
		AreaNormativa an= (AreaNormativa) q.getSingleResult();
		PuestoDTO rol = new PuestoDTO(an.getIdAreaNormativa().intValue(), an.getDescripcionAreaNormativa(), d.getCveDepartamento().intValue(), d.getNombreDepto(), p.getCvePuesto().intValue(), p.getNombrePuesto(),"Si");
	    return rol;
	}

	@Override
	public List<UnidadMedicaDTO> listarUnidadMedicaBySubdelegacion(final String cveIdSubdelegacion)throws AdmonUsuariosException {
		final List<UnidadMedicaFamiliar> umfDB = obtenerUnidadMedica(cveIdSubdelegacion);
        final List<UnidadMedicaDTO> unidadMedica = new ArrayList<UnidadMedicaDTO>(umfDB.size());
        for (UnidadMedicaFamiliar unidad : umfDB) {
        	UnidadMedicaDTO to = new UnidadMedicaDTO();
        	to.setCveUmf(String.valueOf(unidad.getIdUmf()));
        	to.setNombreUmf(unidad.getDescripcionUmf());
        	unidadMedica.add(to);
        }
        return unidadMedica;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Delegacion> obtenerDelegacion() throws AdmonUsuariosException {
		 final Query q = em.createNamedQuery("Delegacion.findAll");
	     return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Subdelegacion> obtenerSubdelegacion(final String idDelegacion)
			throws AdmonUsuariosException {
		 final Query q = em.createNamedQuery("Subdelegacion.findByID");
		 q.setParameter("id", Long.valueOf(idDelegacion));
		 return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UnidadMedicaFamiliar> obtenerUnidadMedica(final String idSubdelegacion)
			throws AdmonUsuariosException {
		 final Query q = em.createNamedQuery("UnidadMedicaFamiliar.findByID");
		 q.setParameter("id", Long.valueOf(idSubdelegacion));
		 return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AreaNormativa> obtenerAreasNormativas()throws AdmonUsuariosException {
		 final Query q = em.createNamedQuery("AreaNormativa.findAll");
		 return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Departamento> obtenerDepartamentos()throws AdmonUsuariosException {
		 final Query q = em.createNamedQuery("Departamento.findAll");
		 return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Departamento> obtenerDepartamentoByAreaNormativa(String cveAreaNormativa)throws AdmonUsuariosException {
		 final Query q = em.createNamedQuery("Departamento.findIdByArea");
		 q.setParameter("id", Long.valueOf(cveAreaNormativa));
		 return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Solicitudes> obtenerSolicitudesPendientes( )throws AdmonUsuariosException {
		 Query q = em.createNamedQuery("Solicitudes.findAllPending");
		 return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Puesto> obtenerPuestos()throws AdmonUsuariosException {
		 final Query q = em.createNamedQuery("Puesto.findAll");
		 return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Puesto> obtenerPuestoByDepartamento(String cveDepartamento)throws AdmonUsuariosException {
		 final Query q = em.createNamedQuery("Puesto.findIdByDepartamento");
		 q.setParameter("id", Integer.parseInt(cveDepartamento));
		 return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DeptoModulo> obtenerModulosByDepartamento(String cveDepartamento)throws AdmonUsuariosException {
		 final Query q = em.createNamedQuery("DeptoModulo.findIdByDepartamento");
		 q.setParameter("id", Long.valueOf(cveDepartamento));
		 return q.getResultList();
	}

	@Override
	public Delegacion obtenerDelegacionByID(String idDelegacion)
			throws AdmonUsuariosException {
		 final Query q = em.createNamedQuery("Delegacion.findId");
		 q.setParameter("id", Long.valueOf(idDelegacion));
		 return (Delegacion)q.getSingleResult();
	}

	@Override
	public Subdelegacion obtenerSubdelegacionByID(String idSubdelegacion)
			throws AdmonUsuariosException {
		 final Query q = em.createNamedQuery("Subdelegacion.findSubID");
		 q.setParameter("id", Long.valueOf(idSubdelegacion));
		 return (Subdelegacion)q.getSingleResult();
	}

	@Override
	public UnidadMedicaFamiliar obtenerUnidadMedicaByID(String idUmf)
			throws AdmonUsuariosException {
		 final Query q = em.createNamedQuery("UnidadMedicaFamiliar.findUmfID");
		 q.setParameter("id", Long.valueOf(idUmf));
		 return (UnidadMedicaFamiliar)q.getSingleResult();
	}

	@Override
	public List<UnidadMedicaFamiliar> obtenerUnidadMedicaBySubdelegacion(String cveIdSubdelegacion)throws AdmonUsuariosException {
		 final Query q = em.createNamedQuery("UnidadMedicaFamiliar.findByID");
		 q.setParameter("id", Long.valueOf(cveIdSubdelegacion));
		 return q.getResultList();
	}

	@Override
	public DelegacionDTO recuperarDelegacion(String idDelegacion)
			throws AdmonUsuariosException {
		final Delegacion del = obtenerDelegacionByID(idDelegacion);
        	DelegacionDTO to = new DelegacionDTO();
        	to.setCveDelegacion(del.getIdDelegacion());
        	to.setNombreDelegacion(del.getDescripcionDelegacion());
        
        return to;
	}

	@Override
	public SubdelegacionDTO recuperarSubdelegacion(String idSubdelegacion)
			throws AdmonUsuariosException {
		final Subdelegacion sub = obtenerSubdelegacionByID(idSubdelegacion);
		SubdelegacionDTO to = new SubdelegacionDTO();
    	to.setCveSubelegacion(sub.getIdSubdelegacion());
    	to.setNombreSubelegacion(sub.getDescripcionSubelegacion());  
        return to;
	}

	@Override
	public UnidadMedicaDTO recuperarUnidadMedica(String idUmf)
			throws AdmonUsuariosException {
		final UnidadMedicaFamiliar uni = obtenerUnidadMedicaByID(idUmf);
		UnidadMedicaDTO to = new UnidadMedicaDTO();
    	to.setCveUmf(String.valueOf(uni.getIdUmf()));
    	to.setNombreUmf(uni.getDescripcionUmf());  
        return to;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Modulo> obtenerModulo() throws AdmonUsuariosException {
		 final Query q = em.createNamedQuery("Modulo.findAll");
	     return q.getResultList();
	}

	@Override
	public List<ModuloDTO> listaModulo() throws AdmonUsuariosException {
		final List<Modulo> moduloDB = obtenerModulo();
        final List<ModuloDTO> modulo = new ArrayList<ModuloDTO>(moduloDB.size());
        for (Modulo mod : moduloDB) {
        	ModuloDTO to = new ModuloDTO();
        	to.setCveIdModulo(mod.getIdModulo().intValue());
        	to.setDesModulo(mod.getDescripcion());
            modulo.add(to);
        }
        return modulo;		
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<UsuarioFuncionario> obtenerUsuarioFuncionario() throws AdmonUsuariosException {
		final Query q = em.createNamedQuery("UsuarioFuncionario.findAll");
		return q.getResultList();
	}
	
	@Override
	public List<PuestoDTO> listarRolesRelacioados(Long idSolicitud) throws AdmonUsuariosException {
		 List<PuestoDTO> listaRoles = new ArrayList<PuestoDTO>();
		 Query q = em.createNamedQuery("PerfilesSolicitud.findAllByClave");
		 q.setParameter("id", idSolicitud);
	     List<PerfilesSolicitud> listaPerfiles = q.getResultList();
	     for(PerfilesSolicitud perfil : listaPerfiles){
	    	 q = em.createNamedQuery("Puesto.findByClave");
	    	 q.setParameter("id", perfil.getPuesto().getCvePuesto());
	    	 Puesto p = (Puesto)q.getSingleResult();
	    	 
	    	 q = em.createNamedQuery("Departamento.findById");
	    	 q.setParameter("id", p.getDepartamento().getCveDepartamento());
	    	 Departamento d = (Departamento)q.getSingleResult();
	    	 
	    	 q = em.createNamedQuery("AreaNormativa.findById");
	    	 q.setParameter("id", d.getAreaNormativa().getIdAreaNormativa());
	    	 AreaNormativa an = (AreaNormativa)q.getSingleResult();
	    	 
	    	 PuestoDTO rol = new PuestoDTO(an.getIdAreaNormativa().intValue(), an.getDescripcionAreaNormativa(), d.getCveDepartamento().intValue(), d.getNombreDepto(), p.getCvePuesto().intValue(), p.getNombrePuesto(),"Si");
	    	 listaRoles.add(rol);
	     }
	     return listaRoles;
	}
	
	@Override
	public List<ModuloDTO> listarModulosRelacioados(Long idSolicitud) throws AdmonUsuariosException {
		List<ModuloDTO> listaModulos = new ArrayList<ModuloDTO>();
		 Query q = em.createNamedQuery("AccesoModulo.findAllByClave");
		 q.setParameter("id", idSolicitud);
	     List<AccesoModulo> listaAccesoMod = q.getResultList();
	     for(AccesoModulo accesoMod : listaAccesoMod){
	    	 q = em.createNamedQuery("DeptoModulo.findById");
	    	 q.setParameter("id", accesoMod.getDeptoModulo().getCveDeptoModulo());
	    	 DeptoModulo dm = (DeptoModulo) q.getSingleResult();
	    	 
	    	 q = em.createNamedQuery("Modulo.findById");
	    	 q.setParameter("id", dm.getModulo().getIdModulo());
	    	 Modulo m = (Modulo) q.getSingleResult();
	    	 
	    	 q = em.createNamedQuery("Departamento.findById");
	    	 q.setParameter("id", dm.getDepartamento().getCveDepartamento());
	    	 Departamento d = (Departamento)q.getSingleResult();
	    	 
	    	 q = em.createNamedQuery("AreaNormativa.findById");
	    	 q.setParameter("id", d.getAreaNormativa().getIdAreaNormativa());
	    	 AreaNormativa an = (AreaNormativa)q.getSingleResult();
	    	 
	    	 ModuloDTO mod = new ModuloDTO(an.getIdAreaNormativa().intValue(), an.getDescripcionAreaNormativa(), d.getCveDepartamento().intValue(), d.getNombreDepto(), m.getIdModulo().intValue(), m.getDescripcion());
	    	 listaModulos.add(mod);
	     }
	     return listaModulos;
	
	}
	
}
