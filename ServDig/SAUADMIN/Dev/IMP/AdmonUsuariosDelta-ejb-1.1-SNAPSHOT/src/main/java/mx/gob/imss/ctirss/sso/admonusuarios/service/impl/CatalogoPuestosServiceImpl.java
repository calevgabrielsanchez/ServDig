package mx.gob.imss.ctirss.sso.admonusuarios.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import mx.gob.imss.ctirss.admonusuarios.entidad.SsoCatdepartamento;
import mx.gob.imss.ctirss.admonusuarios.entidad.SsoCatpuesto;
import mx.gob.imss.ctirss.sso.admonusuarios.dto.PuestosDTO;
import mx.gob.imss.ctirss.sso.admonusuarios.service.CatalogoPuestosServiceLocale;

@Stateless (name="catalogoPuestosServiceImpl", mappedName="catalogoPuestosServiceImpl")
public class CatalogoPuestosServiceImpl implements CatalogoPuestosServiceLocale {

	@PersistenceContext
    private EntityManager em;
	
	private static String SQL_ALL_PUESTOS = "select p  from SsoCatpuesto p WHERE p.ssoCatdepartamento.cveSsodepto = :depto";
	
	private static String SQL_ALL_DEPTOS = "select d from SsoCatdepartamento d WHERE d.cveSsodepto = :depto";
	
	private static String SQL_DELETE_PUESTO = "DELETE FROM SsoCatpuesto p WHERE p.cveSsopuesto = :puesto and p.ssoCatdepartamento.cveSsodepto = :depto";
	
	private static String SQL_UPDATE_PUESTO = "UPDATE SsoCatpuesto p SET p.desPuesto = :dato " +
							"WHERE p.cveSsopuesto = :puesto and p.ssoCatdepartamento.cveSsodepto = :deptoP";	
	
	@Override
	public List<PuestosDTO> findAll(long idDepto) throws Exception {
		Query query = em.createQuery(SQL_ALL_PUESTOS);
		query.setParameter("depto", idDepto);
		List<PuestosDTO> back = null;
		List<SsoCatpuesto> lista = (List<SsoCatpuesto>)query.getResultList();
		
		if (lista != null && lista.size() > 0) {
			back = new ArrayList<PuestosDTO>();

			for (int i = 0; i < lista.size(); i++) {
				PuestosDTO dato = new PuestosDTO();
				dato.setCveSsopuesto(lista.get(i).getCveSsopuesto());
				dato.setDesPuesto(lista.get(i).getDesPuesto());
				back.add(i, dato);
			}
		}
		return back;
	}

	private SsoCatdepartamento getDeptoo(long iddep) {
		Query query = em.createQuery(SQL_ALL_DEPTOS);
		query.setParameter("depto", iddep);
		return (SsoCatdepartamento)query.getSingleResult();
	}
	
	@Override
	public PuestosDTO findOne(PuestosDTO clase) throws Exception {

		return null;
	}

	@Override
	public void update(PuestosDTO clase, long Iddep) throws Exception {
		Query query = em.createQuery(SQL_UPDATE_PUESTO);		
		query.setParameter("dato", clase.getDesPuesto());
		query.setParameter("puesto", clase.getCveSsopuesto());
		query.setParameter("deptoP", Iddep);
		int cuantos = query.executeUpdate();
		System.out.println("Numero de registros modificados " + cuantos);
	}

	@Override
	public void create(PuestosDTO clase, long Iddep) throws Exception {
		SsoCatpuesto pue = new SsoCatpuesto();		 
		pue.setSsoCatdepartamento(getDeptoo(Iddep));
		pue.setDesPuesto(clase.getDesPuesto());
		pue.setCveSsopuesto(null);
		em.persist(pue);
	}

	@Override
	public void delete(PuestosDTO clase, long Iddep) throws Exception {
		Query query = em.createQuery(SQL_DELETE_PUESTO);
		query.setParameter("puesto", clase.getCveSsopuesto());
		query.setParameter("depto", Iddep);
		int cuantos = query.executeUpdate();
		System.out.println("Numero de registros borrados " + cuantos);
	}

}
