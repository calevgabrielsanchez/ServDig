package mx.gob.imss.ctirss.sso.admonusuarios.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import mx.gob.imss.ctirss.admonusuarios.entidad.DicDelegacion;
import mx.gob.imss.ctirss.admonusuarios.entidad.DicSubdelegacion;
import mx.gob.imss.ctirss.admonusuarios.entidad.DicUmf;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoAccesomodulo;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoCatareanormativa;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoCatdepartamento;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoCatpuesto;
import mx.gob.imss.ctirss.sso.admonusuarios.baseservice.GenericService;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.AprobadorDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.AreaNormativaDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.DelegacionDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.DepartamentoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.ModuloDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.PuestoDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SubdelegacionDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UmfDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.UnidadMedicaDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.SolicitudDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.exceptions.AdmonUsuariosException;
import mx.gob.imss.ctirss.sso.admonusuarios.service.CatalogoServiceLocal;
import mx.gob.imss.ctirss.admonusuarios.entities.Solicitudes;
import mx.gob.imss.ctirss.admonusuarios.entities.Puesto;
import mx.gob.imss.ctirss.admonusuarios.entities.DeptoModulo;
import mx.gob.imss.ctirss.admonusuarios.entities.Departamento;
import mx.gob.imss.ctirss.admonusuarios.entities.AreaNormativa;
import mx.gob.imss.ctirss.admonusuarios.entities.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.admonusuarios.entities.Subdelegacion;
import mx.gob.imss.ctirss.admonusuarios.entities.Delegacion;
import mx.gob.imss.ctirss.admonusuarios.entities.Modulo;
import mx.gob.imss.ctirss.admonusuarios.entities.AccesoModulo;

import java.util.Collection;

@Stateless (name="catalogoCriteria", mappedName="catalogoCriteria")
public class CatalogoServiceImpl extends GenericService implements CatalogoServiceLocal{

	
	@Override
	public List<DelegacionDTO> listarDelegacion() throws AdmonUsuariosException {
		final List<DicDelegacion> listTemp = obtenerDelegacion();
        final List<DelegacionDTO> listResult = new ArrayList<DelegacionDTO>(listTemp.size());
        for (DicDelegacion tmp : listTemp) {
        	if(tmp.getCveIdDelegacion()!=35&&tmp.getCveIdDelegacion()!=36&&tmp.getCveIdDelegacion()!=37&&tmp.getCveIdDelegacion()!=38)
        	{
            	DelegacionDTO result = new DelegacionDTO();
            	result.setCveDelegacion(tmp.getCveIdDelegacion());
            	result.setNombreDelegacion(tmp.getDesDeleg());
            	listResult.add(result);
        	}
        }
        return listResult;
	}

	@Override
	public List<AreaNormativaDTO> listarAreaNormativa()throws AdmonUsuariosException {
		final List<SsoCatareanormativa> aN = obtenerAreasNormativas();
        final List<AreaNormativaDTO> areaNormativa = new ArrayList<AreaNormativaDTO>(aN.size());
        for (SsoCatareanormativa area : aN) {
        	AreaNormativaDTO an = new AreaNormativaDTO();
        	an.setCveSsoareanorma(area.getCveSsoareanorma());
        	an.setDesAreanorma(area.getDesAreanorma());
        	areaNormativa.add(an);
        }
        return areaNormativa;
	}
	
	@Override
	public List<AreaNormativaDTO> listarAreaNormativaPensiones()throws AdmonUsuariosException {
		final List<SsoCatareanormativa> aN = obtenerAreasNormativasPensiones();
        final List<AreaNormativaDTO> areaNormativa = new ArrayList<AreaNormativaDTO>(aN.size());
        for (SsoCatareanormativa area : aN) {
        	AreaNormativaDTO an = new AreaNormativaDTO();
        	an.setCveSsoareanorma(area.getCveSsoareanorma());
        	an.setDesAreanorma(area.getDesAreanorma());
        	areaNormativa.add(an);
        }
        return areaNormativa;
	}

	@Override
	public DelegacionDTO recuperarDelegacion(Long idDelegacion)
			throws AdmonUsuariosException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SubdelegacionDTO> listarSubdelegacion(Long idDelegacion)throws AdmonUsuariosException {
		final List<DicSubdelegacion> listTmp = obtenerCatSubdelegacionByDelegacion(idDelegacion);
        final List<SubdelegacionDTO> listResult = new ArrayList<SubdelegacionDTO>(listTmp.size());
        for (DicSubdelegacion tmp : listTmp) {
        	SubdelegacionDTO result = new SubdelegacionDTO();
        	result.setCveSubelegacion(tmp.getCveIdSubdelegacion());
        	result.setNombreSubelegacion(tmp.getDesSubdelegacion());
        	listResult.add(result);
        }
        return listResult;
	}
	
	@Override
	public List<UmfDTO> listarUmf(Long idSubdel)throws AdmonUsuariosException {
		final List<DicUmf> listTmp = obtenerCatUmfBySubdelegacion(idSubdel);
        final List<UmfDTO> listResult = new ArrayList<UmfDTO>(listTmp.size());
        for (DicUmf tmp : listTmp) {
        	UmfDTO result = new UmfDTO();
        	result.setCveUmf(tmp.getCveIdUmf());
        	result.setNombreUmf(tmp.getNomUnidad());
        	listResult.add(result);
        }
        return listResult;
	}

	@Override
	public List<UmfDTO> listarHospitales(Long idSubdel)throws AdmonUsuariosException {
		final List<DicUmf> listTmp = obtenerCatHospitalesBySubdelegacion(idSubdel);
        final List<UmfDTO> listResult = new ArrayList<UmfDTO>(listTmp.size());
        for (DicUmf tmp : listTmp) {
        	UmfDTO result = new UmfDTO();
        	result.setCveUmf(tmp.getCveIdUmf());
        	result.setNombreUmf(tmp.getNomUnidad());
        	listResult.add(result);
        }
        return listResult;
	}

	@Override
	public List<UmfDTO> listarUMFSinhospitales(Long idSubdel)throws AdmonUsuariosException {
		final List<DicUmf> listTmp = obtenerUMFCatSinHospitalesBySubdelegacion(idSubdel);
        final List<UmfDTO> listResult = new ArrayList<UmfDTO>(listTmp.size());
        for (DicUmf tmp : listTmp) {
        	UmfDTO result = new UmfDTO();
        	result.setCveUmf(tmp.getCveIdUmf());
        	result.setNombreUmf(tmp.getNomUnidad());
        	listResult.add(result);
        }
        return listResult;
	}

	@Override
	public SubdelegacionDTO recuperarSubdelegacion(Long idSubdelegacion)
			throws AdmonUsuariosException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UnidadMedicaDTO> listarUnidadMedica(Long idSubdelegacion)
			throws AdmonUsuariosException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PuestoDTO consultaPerfilBD(Long perfil) throws AdmonUsuariosException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UnidadMedicaDTO> listarUnidadMedicaBySubdelegacion(
			Long idSubdelegacion) throws AdmonUsuariosException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UnidadMedicaDTO recuperarUnidadMedica(Long idUmf)
			throws AdmonUsuariosException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ModuloDTO> listaModulo() throws AdmonUsuariosException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AprobadorDTO> listaUsuarioFuncionario()
			throws AdmonUsuariosException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DepartamentoDTO> listarDepartamentos()
			throws AdmonUsuariosException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DepartamentoDTO> listarDepartamentosByAreaNormativa(Long cveAreaNormativa) throws AdmonUsuariosException {
		final List<SsoCatdepartamento> listTmp = obtenerCatDepartamentopByAreaNorma(cveAreaNormativa);
        final List<DepartamentoDTO> listResult = new ArrayList<DepartamentoDTO>(listTmp.size());
        for (SsoCatdepartamento tmp : listTmp) {
        	DepartamentoDTO result = new DepartamentoDTO();
        	result.setCveSsodepto(tmp.getCveSsodepto());
        	result.setDesDepartamento(tmp.getDesDepartamento());
        	listResult.add(result);
        }
        return listResult;
	}
	
	@Override
	public List<DepartamentoDTO> listarDepartamentosByAreaNormativaPensiones(Long cveAreaNormativa) throws AdmonUsuariosException {
		final List<SsoCatdepartamento> listTmp = obtenerCatDepartamentopByAreaNormaPensiones(cveAreaNormativa);
        final List<DepartamentoDTO> listResult = new ArrayList<DepartamentoDTO>(listTmp.size());
        for (SsoCatdepartamento tmp : listTmp) {
        	DepartamentoDTO result = new DepartamentoDTO();
        	result.setCveSsodepto(tmp.getCveSsodepto());
        	result.setDesDepartamento(tmp.getDesDepartamento());
        	listResult.add(result);
        }
        return listResult;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PuestoDTO> listarPuestoByDepartamento(Long cveDepartamento)throws AdmonUsuariosException {
		final List<SsoCatpuesto> listTmp = obtenerCatPuestoByDepartamento(cveDepartamento);
        final List<PuestoDTO> listResult = new ArrayList<PuestoDTO>(listTmp.size());
        for (SsoCatpuesto tmp : listTmp) {
        	PuestoDTO result = new PuestoDTO();
        	result.setCvePuesto(tmp.getCveSsopuesto());
        	result.setNombrePuesto(tmp.getDesPuesto());
        	result.setCveArea((int)tmp.getSsoCatdepartamento().getSsoCatareanormativa().getCveSsoareanorma());
        	result.setCveDepartamento((int)tmp.getSsoCatdepartamento().getCveSsodepto());
        	listResult.add(result);
        }
        return listResult;
	}

	@Override
	public PuestoDTO listarPuestoByClave(Long clavePuesto)
			throws AdmonUsuariosException {
		// TODO Auto-generated method stub
		return null;
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
	public List<ModuloDTO> listarModulosByDepartamento(Long cveDepartamento)
			throws AdmonUsuariosException {
		// TODO Auto-generated method stub
		return null;
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
	public List<ModuloDTO> listarAccesoModulosByDepartamento(Long cveSol) throws AdmonUsuariosException {
		final List<SsoAccesomodulo> accsModulos = obtenerAccesoModulosBySolicitud(cveSol);
        final List<ModuloDTO> modulos = new ArrayList<ModuloDTO>(accsModulos.size());
        for (SsoAccesomodulo accMod : accsModulos) {
        	if(accMod!=null&&accMod.getSsoCatdeptomodulo().getDicModulo()!=null)
        	{
            	ModuloDTO modulo = new ModuloDTO();
            	modulo.setCveIdModulo(accMod.getSsoCatdeptomodulo().getDicModulo().getCveIdModulo());
            	modulo.setDesModulo(accMod.getSsoCatdeptomodulo().getDicModulo().getDesModulo());
            	modulo.setCveAccesoModulo(accMod.getCveSsoaccesomodulo());
            	modulos.add(modulo);
        	}
        }
        return modulos;
	}

	
	@Override
	public List<PuestoDTO> listarRolesRelacioados(Long idSolicitud)
			throws AdmonUsuariosException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ModuloDTO> listarModulosRelacioados(Long idSolicitud)
			throws AdmonUsuariosException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SsoCatareanormativa> obtenerAreasNormativas()throws AdmonUsuariosException {
		 final Query q = em.createQuery("select a from SsoCatareanormativa a");
		 return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SsoCatareanormativa> obtenerAreasNormativasPensiones()throws AdmonUsuariosException {
		 final Query q = em.createQuery("select a from SsoCatareanormativa a where a.cveSsoareanorma in (1, 2, 3, 4, 10)");
		 return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DicDelegacion> obtenerDelegacion() throws AdmonUsuariosException {
		final Query q = em.createQuery("select a from DicDelegacion a");
	     return q.getResultList();
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<DicSubdelegacion> obtenerCatSubdelegacionByDelegacion(final Long idDelegacion)throws AdmonUsuariosException {
		 final Query q = em.createQuery("select s from DicSubdelegacion s where s.dicDelegacion.cveIdDelegacion = :id");
		 q.setParameter("id", Long.valueOf(idDelegacion));
		 return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SsoCatdepartamento> obtenerCatDepartamentopByAreaNorma(final Long idDelegacion)throws AdmonUsuariosException {
		 final Query q = em.createQuery("select s from SsoCatdepartamento s where s.ssoCatareanormativa.cveSsoareanorma = :id");
		 q.setParameter("id", Long.valueOf(idDelegacion));
		 return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SsoCatdepartamento> obtenerCatDepartamentopByAreaNormaPensiones(final Long idDelegacion)throws AdmonUsuariosException {
		 final Query q = em.createQuery("select s from SsoCatdepartamento s where s.ssoCatareanormativa.cveSsoareanorma = :id and s.cveSsodepto in (36, 38, 39, 40, 41, 42, 43, 44, 45, 46, 65, 66, 67)");
		 q.setParameter("id", Long.valueOf(idDelegacion));
		 return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SsoCatpuesto> obtenerCatPuestoByDepartamento(final Long idDepto)throws AdmonUsuariosException {
		 final Query q = em.createQuery("select s from SsoCatpuesto s where s.ssoCatdepartamento.cveSsodepto = :id");
		 q.setParameter("id", Long.valueOf(idDepto));
		 return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DicUmf> obtenerCatUmfBySubdelegacion(final Long idSubdel)throws AdmonUsuariosException {
		 final Query q = em.createQuery("select s from DicUmf s where s.dicSubdelegacion.cveIdSubdelegacion = :id");
		 q.setParameter("id", Long.valueOf(idSubdel));
		 return q.getResultList();
	}


	
	@SuppressWarnings("unchecked")
	@Override
	public List<DicUmf> obtenerCatHospitalesBySubdelegacion(final Long idSubdel)throws AdmonUsuariosException {
		 final Query q = em.createQuery("select s from DicUmf s where s.dicSubdelegacion.cveIdSubdelegacion = :id and (s.cveIdNivelAtencion = 2 or s.cveIdNivelAtencion = 3) and s.fecRegistroBaja is null");
		 q.setParameter("id", Long.valueOf(idSubdel));
		 return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DicUmf> obtenerUMFCatSinHospitalesBySubdelegacion(final Long idSubdel)throws AdmonUsuariosException {
		 final Query q = em.createQuery("select s from DicUmf s where s.dicSubdelegacion.cveIdSubdelegacion = :id and s.cveIdNivelAtencion = 1  and s.fecRegistroBaja is null");
		 q.setParameter("id", Long.valueOf(idSubdel));
		 return q.getResultList();
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
	public List<SolicitudDTO> listarSolicitudesRecuperacion(int claveDelegacion, int claveSubDelegacion, int claveUMF) throws AdmonUsuariosException {  //Lista las solicitudes pendientes
		List<Solicitudes> solicitudes = obtenerSolicitudesBaja();
		boolean flagAgregar = false;
		List<SolicitudDTO> listaSolicitudes = new ArrayList<SolicitudDTO>(solicitudes.size());
        for (Solicitudes solicitud : solicitudes) {
        	flagAgregar = false;
         	SolicitudDTO sol = new SolicitudDTO();
        	sol.setCveSsosolicitud(solicitud.getIdSolicitud());
        	sol.setNomNombre(solicitud.getNomNombre());
        	sol.setNomPaterno(solicitud.getNomPaterno());
        	sol.setNomMaterno(solicitud.getNomMaterno());
        	sol.setRefCorreoElectronico(solicitud.getNomCorreoElectonico());
        	sol.setFecRegistroAlta(solicitud.getRegistroAlta());
        	sol.setCveMatricula(solicitud.getNomMatricula());
        	DelegacionDTO del =  new DelegacionDTO();
        	try{
        	 del= obtenerDelegacionByClave((int)solicitud.getDelegacion().getIdDelegacion().intValue());
        	}
        	catch(Exception e){
        	 del.setCveDelegacion(-99);
        	 del.setNombreDelegacion("");
        	}
        	sol.setDelDTO(del);
        	SubdelegacionDTO sdel =  new SubdelegacionDTO();
        	try{
        	sdel = obtenerSubDelegacionByClave(solicitud.getSubdelegacion().getIdSubdelegacion().intValue());
        	}catch(Exception e){
        		sdel.setCveSubelegacion(-99);
        		sdel.setNombreSubelegacion("");
        	}
        	sol.setSubdelDTO(sdel);
        	UmfDTO umf = new UmfDTO();
        	try{
        	umf = obtenerUmfByClave(solicitud.getUnidadMedicaFamiliar().getIdUmf().intValue());
        	}
        	catch(Exception e){
        		umf.setCveUmf(new Long(-99));
        		umf.setNombreUmf("");
        	}
        	sol.setUmfDTO(umf);
        	try{
        	sol.setFecUsrNacimiento(solicitud.getFechaNacimiento());
        	}
        	catch(Exception e){
        	}
        	sol.setDesUsrCurp(solicitud.getCurp());
        	DepartamentoDTO dep =  new DepartamentoDTO();
        	if(claveUMF != -99){
        		 if(claveUMF == umf.getCveUmf())
        			 flagAgregar = true;
        	}
        	if(claveSubDelegacion != -99){
       		 if(claveSubDelegacion == sdel.getCveSubelegacion())
       			 flagAgregar = true;
       	    } 
        	
        	if(claveDelegacion != -99){
          		 if(claveDelegacion == del.getCveDelegacion())
          			 flagAgregar = true;
          	} 
        	
        	if(flagAgregar)
        	    listaSolicitudes.add(sol);  
        }
        return listaSolicitudes;	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Solicitudes> obtenerSolicitudesPendientes( )throws AdmonUsuariosException {
		 Query q = em.createNamedQuery("Solicitudes.findAllPending");
		 return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Solicitudes> obtenerSolicitudesBaja( )throws AdmonUsuariosException {
		 Query q = em.createNamedQuery("Solicitudes.findAllDeleted");
		 return q.getResultList();
	}
	
	@Override
	public List<PuestoDTO> listarPuestoByDepartamentoPrincipal(String cvePuesto) throws AdmonUsuariosException {
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
	public List<AreaNormativaDTO> listarAreaNormativaPrincipal () throws AdmonUsuariosException {
		final List<AreaNormativa> aN = obtenerAreasNormativasPrincipal();
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
	public List<DepartamentoDTO> listarDepartamentoPrincipal( ) throws AdmonUsuariosException {
		final List<Departamento> dep = obtenerDepartamentosPrincipal();
        final List<DepartamentoDTO> listaDepartamentos = new ArrayList<DepartamentoDTO>(dep.size());
        for (Departamento departamento : dep) {
        	DepartamentoDTO dp = new DepartamentoDTO();
        	dp.setCveSsodepto(departamento.getCveDepartamento());
        	dp.setDesDepartamento(departamento.getNombreDepto());
        	listaDepartamentos.add(dp);
        }
        return listaDepartamentos;	
	}
	
	@Override
	public List<PuestoDTO> listarPuestoPrincipal( ) throws AdmonUsuariosException {
		final List<Puesto> pst = obtenerPuestosPrincipal();
        final List<PuestoDTO> listaPuestos = new ArrayList<PuestoDTO>(pst.size());
        for (Puesto p : pst) {
        	PuestoDTO puesto = new PuestoDTO();
        	puesto.setCvePuesto(p.getCvePuesto());
        	puesto.setNombrePuesto(p.getNombrePuesto());
        	listaPuestos.add(puesto);
        }
        return listaPuestos;	
	}
	
	@Override
	public List<DelegacionDTO> listarDelegacionPrincipal( ) throws AdmonUsuariosException {
		final List<Delegacion> delegaciones = obtenerDelegacionesPrincipal();
        final List<DelegacionDTO> listaDelegaciones = new ArrayList<DelegacionDTO>(delegaciones.size());
        for (Delegacion d : delegaciones) {
        	DelegacionDTO del = new DelegacionDTO();
        	del.setCveDelegacion(d.getIdDelegacion());
        	del.setNombreDelegacion(d.getDescripcionDelegacion());
        	listaDelegaciones.add(del);
        }
        return listaDelegaciones;	
	}
	
	@Override
	public List<SubdelegacionDTO> listarSubDelegacionPrincipal( ) throws AdmonUsuariosException {
		final List<Subdelegacion> subdelegaciones = obtenerSubDelegacionesPrincipal();
        final List<SubdelegacionDTO> listaSubDelegaciones = new ArrayList<SubdelegacionDTO>(subdelegaciones.size());
        for (Subdelegacion sd : subdelegaciones) {
        	SubdelegacionDTO sdel = new SubdelegacionDTO();
        	sdel.setCveSubelegacion(sd.getIdSubdelegacion());
        	sdel.setNombreSubelegacion(sd.getDescripcionSubelegacion());
        	listaSubDelegaciones.add(sdel);
        }
        return listaSubDelegaciones;	
	}
	
	@Override
	public List<UmfDTO> listarUMFPrincipal( ) throws AdmonUsuariosException {
		final List<UnidadMedicaFamiliar> unidadesmed = obtenerUMFsPrincipal();
        final List<UmfDTO> listaUnidades = new ArrayList<UmfDTO>(unidadesmed.size());
        for (UnidadMedicaFamiliar umf : unidadesmed) {
        	UmfDTO um = new UmfDTO();
        	um.setCveUmf(umf.getIdUmf());
        	um.setNombreUmf(umf.getDescripcionUmf());
        	listaUnidades.add(um);
        }
        return listaUnidades;	
	}
	
	@Override
	public AreaNormativaDTO listarAreaNormativaPrincipalPorClave(int clave) throws AdmonUsuariosException {
		AreaNormativa aNormativa = obtenerAreasNormativasPrincipalByClave(clave); 
        AreaNormativaDTO an = new AreaNormativaDTO();
        an.setCveSsoareanorma(aNormativa.getIdAreaNormativa());
        an.setDesAreanorma(aNormativa.getDescripcionAreaNormativa());
        return an;	
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
	public AreaNormativa obtenerAreasNormativasPrincipalByClave(int clave)throws AdmonUsuariosException {
		 final Query q = em.createNamedQuery("AreaNormativa.findById");
		 q.setParameter("id", clave);
		 return (AreaNormativa) q.getSingleResult();
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
	public List<AreaNormativa> obtenerAreasNormativasPrincipal()throws AdmonUsuariosException {
		 final Query q = em.createNamedQuery("AreaNormativa.findAll");
		 return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Departamento> obtenerDepartamentosPrincipal()throws AdmonUsuariosException {
		 final Query q = em.createNamedQuery("Departamento.findAll");
		 return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Puesto> obtenerPuestosPrincipal()throws AdmonUsuariosException {
		 final Query q = em.createNamedQuery("Puesto.findAll");
		 return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Delegacion> obtenerDelegacionesPrincipal()throws AdmonUsuariosException {
		 final Query q = em.createNamedQuery("Delegacion.findAll");
		 return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Subdelegacion> obtenerSubDelegacionesPrincipal()throws AdmonUsuariosException {
		 final Query q = em.createNamedQuery("Subdelegacion.findAll");
		 return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UnidadMedicaFamiliar> obtenerUMFsPrincipal()throws AdmonUsuariosException {
		 final Query q = em.createNamedQuery("UnidadMedicaFamiliar.findAll");
		 return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DeptoModulo> obtenerModulosByDepartamento(String cveDepartamento)throws AdmonUsuariosException {
		 final Query q = em.createNamedQuery("DeptoModulo.findIdByDepartamento");
		 q.setParameter("id", Long.valueOf(cveDepartamento));
		 return q.getResultList();
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<SsoAccesomodulo> obtenerAccesoModulosBySolicitud(long cveSol)throws AdmonUsuariosException {
		 final Query q = em.createNamedQuery("select d from SsoAccesomodulo d where d.ssoSolicitud.cveSsosolicitud = :idsol");
		 q.setParameter("idSol", Long.valueOf(cveSol));
		 return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public DelegacionDTO obtenerDelegacionByClave(int cveDeleg)throws AdmonUsuariosException {
		 final Query q = em.createNamedQuery("Delegacion.findId");
		 q.setParameter("id", cveDeleg);
		 Delegacion del = (Delegacion) q.getSingleResult();
		 DelegacionDTO delegacion = new DelegacionDTO();
		 delegacion.setCveDelegacion(del.getIdDelegacion());
		 delegacion.setNombreDelegacion(del.getDescripcionDelegacion());
		 return delegacion;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public SubdelegacionDTO obtenerSubDelegacionByClave(int cveSubDeleg)throws AdmonUsuariosException {
		 final Query q = em.createNamedQuery("Subdelegacion.findSubID");
		 q.setParameter("id", cveSubDeleg);
		 Subdelegacion sdel = (Subdelegacion) q.getSingleResult();
		 SubdelegacionDTO sdelegacion = new SubdelegacionDTO();
		 sdelegacion.setCveSubelegacion(sdel.getIdSubdelegacion());
		 sdelegacion.setNombreSubelegacion(sdel.getDescripcionSubelegacion());
		 return sdelegacion;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public UmfDTO obtenerUmfByClave(int cveUmf)throws AdmonUsuariosException {
		 final Query q = em.createNamedQuery("UnidadMedicaFamiliar.findUmfID");
		 q.setParameter("id", cveUmf);
		 UnidadMedicaFamiliar umf = (UnidadMedicaFamiliar) q.getSingleResult();
		 UmfDTO umedicofamiliar = new UmfDTO();
		 umedicofamiliar.setCveUmf(umf.getIdUmf());
		 umedicofamiliar.setNombreUmf(umf.getNombreUnidad());
		 return umedicofamiliar;
	}
	
	public List<ModuloDTO> cargaModulosExistentes() throws AdmonUsuariosException
	{
		List<ModuloDTO> lista = new ArrayList<ModuloDTO>();
		Query q = em.createNamedQuery("Modulo.findAll");
		List<Modulo> modulos = q.getResultList();
		for(Modulo mod: modulos){
			ModuloDTO nmod = new ModuloDTO();
			nmod.setCveIdModulo(mod.getIdModulo());
			nmod.setDesModulo(mod.getDescripcion());
			lista.add(nmod);
		}
		return lista;
	}
	
	public List<ModuloDTO> cargaModulosRelacionados(int cveDep) throws AdmonUsuariosException
	{
		List<ModuloDTO> lista = new ArrayList<ModuloDTO>();
		Query q = em.createNamedQuery("DeptoModulo.findIdByDepartamento");
		q.setParameter("id", cveDep);
		List<DeptoModulo> deptomodulos = q.getResultList();
		for(DeptoModulo dm: deptomodulos){
			q = em.createNamedQuery("Modulo.findById");
			q.setParameter("id", dm.getModulo().getIdModulo());
			Modulo modulo = (Modulo) q.getSingleResult();
			ModuloDTO nmod = new ModuloDTO();
			nmod.setCveIdModulo(modulo.getIdModulo());
			nmod.setDesModulo(modulo.getDescripcion());
			lista.add(nmod);
		}
		return lista;
	}
	
	public void agregaModuloADep(int cveDep, int claveModuloAgregar) throws AdmonUsuariosException
	{
		Query q = em.createNamedQuery("Departamento.findById");
		q.setParameter("id", cveDep);
		Departamento dep = (Departamento) q.getSingleResult();
		
		q = em.createNamedQuery("Modulo.findById");
		q.setParameter("id", claveModuloAgregar);
		Modulo mod = (Modulo) q.getSingleResult();
		
		DeptoModulo dm = new DeptoModulo();
		dm.setDepartamento(dep);
		dm.setModulo(mod);
		
		em.persist(dm);
	}
	
	public void eliminaModuloADep(int cveDep, int claveModuloEliminar) throws AdmonUsuariosException
	{
		Query q = em.createNamedQuery("DeptoModulo.findByClaves");
		q.setParameter("cveDepto", cveDep);
		q.setParameter("cveModulo", claveModuloEliminar);
		List<DeptoModulo> listadm = q.getResultList();
		
		for(DeptoModulo dm: listadm){
		   q = em.createNamedQuery("DeptoModulo.deleteById");
		   q.setParameter("id", dm.getCveDeptoModulo());
		   int results = q.executeUpdate();
		   em.flush();
		}
	}
	
	public boolean validarDependencias(int cveDep, int claveModuloEliminar) throws AdmonUsuariosException
	{
		boolean res = false;
		Query q = em.createNamedQuery("DeptoModulo.findByClaves");
		q.setParameter("cveDepto", cveDep);  
		q.setParameter("cveModulo", claveModuloEliminar); 
		DeptoModulo dm = (DeptoModulo) q.getSingleResult();
		
		q = em.createNamedQuery("AccesoModulo.findAllByDeptoMod");
		q.setParameter("id", dm.getCveDeptoModulo());
		List<AccesoModulo> lista;
		try{
			lista =  q.getResultList();
			if(lista.size()>0)
				res = true;
			else
				res = false;
		   }
		catch(Exception e){
			res = false;
		   }
		return res;
	}
}
