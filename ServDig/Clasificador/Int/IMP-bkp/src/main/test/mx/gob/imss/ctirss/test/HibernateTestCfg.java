/**
 * 
 */
package mx.gob.imss.ctirss.test;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mx.gob.imss.ctirss.clasificador.model.business.Equivalencia;
import mx.gob.imss.ctirss.clasificador.model.business.Fraccion;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

/**
 * @author lucio
 *
 */
public class HibernateTestCfg extends HibernateTestBase {

	@Test
	public void test() {
//		
//		
//		
//	//List<Division> result = 	(List<Division>)this.session.createQuery("from Division where indActivo = :indActivo ").setParameter("indActivo",true).list();
//	
//	
//	
//	List<Division> result = (List<Division>) 
//			this.session
//			.createQuery(
//					"select new Division(cveDivision, nomDivision) from Division where indActivo = :indActivo ")
//			.setParameter("indActivo", true).list();
//	
//	
//	Iterator<Division> it = result.iterator();
//	while(it.hasNext()){
//		
//		Division d = it.next();
//		System.out.println(d.getNomDivision() + d.getCveDivision());
//		
//	}
//	
//	
//	
//	
//	
//	
//	
//	System.out.println(result);
//	
//	
//	
//	List<Grupo> result2 = this
//			.session
//			.createQuery(
//					"select new Grupo( id.cveGrupo,id.cveDivision  , nomGrupo) from Grupo where id.cveDivision = :cveDivision")
//			.setParameter("cveDivision", new Integer(1)).list();
//	
//	System.out.println(result2);
//	
//	
//	
//	List<Fraccion> result3 = this
//			.session
//			.createQuery(
//					"from Fraccion where id.cveGrupo = :cveGrupo and indActivo = :indActivo")
//			.setParameter("cveGrupo", new Integer(4))
//			.setParameter("indActivo", Boolean.TRUE).list();
//	
//	System.out.println(result3);
//	
//	
//	
//	String palabraClave = "A";
//	
//	
//	Criteria criteria = this.session.createCriteria(Fraccion.class).add(
//			Restrictions.ilike("nomActividad","%"+ palabraClave+"%")).
//			add(
//					Restrictions.eq("indActivo", Boolean.FALSE));
//	
//	List<Fraccion> result4 = criteria.list();
//	
//	System.out.println(result4);
	
	
	
List<Fraccion> result = null;
		

String palabraClave = "EX";

		Criteria ce = this.session.createCriteria(Equivalencia.class);
		/*Se busca que las fracciones anteriores sean igual a la palabra clave*/
		ce.add(Restrictions.ilike("fraccionAnterior.nomActividad",palabraClave, MatchMode.ANYWHERE));
		
		List<Equivalencia> eqs = ce.list();
		
		if(eqs != null){
			result = new ArrayList<Fraccion>();
			Iterator< Equivalencia> ite = eqs.iterator();
			while(ite.hasNext()){
				
				Equivalencia e = ite.next();
				
				result.add(e.getFraccionNueva());
				
			}
		}
	
	
	
	}

}
