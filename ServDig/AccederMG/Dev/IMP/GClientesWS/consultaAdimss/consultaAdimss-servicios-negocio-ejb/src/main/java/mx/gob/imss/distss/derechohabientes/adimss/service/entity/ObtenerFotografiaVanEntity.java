package mx.gob.imss.distss.derechohabientes.adimss.service.entity;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import mx.gob.imss.distss.derechohabientes.adimss.service.persistence.adimss.AdtCredencial;
import mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent.Controladimss;
import mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent.Documentuminterface;

@Stateless
public class ObtenerFotografiaVanEntity extends AbstractServiceEntityVan implements ObtenerFotografiaEntityVanLocal{

	@SuppressWarnings("unchecked")
	@Override
	public List<Documentuminterface> getObjetID(String nss, int calidad) {
		List<Documentuminterface> credenciales = new ArrayList<Documentuminterface>();
		StringBuilder st = new StringBuilder();
		st.append("select di from Documentuminterface di");
		st.append(" where di.imagetype.idimgtype=5");
		st.append(" and di.controladimss.numNssAsegurado='"+ nss +"'");
		st.append(" and di.controladimss.cveCalidadAseg=" + calidad);
		Query query = em.createQuery(st.toString());	
		credenciales = query.getResultList();
		return credenciales;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Documentuminterface> getObjetID(String nss, int calidad, String nombre, String apePat, String apeMat) {
		List<Documentuminterface> credenciales = new ArrayList<Documentuminterface>();
		StringBuilder st = new StringBuilder();
		st.append("select di from Documentuminterface di");
		st.append(" where di.imagetype.idimgtype=5");
		st.append(" and di.controladimss.numNssAsegurado='"+ nss +"'");
		st.append(" and di.controladimss.enrolfirstname='"+ nombre.toUpperCase() +"'");
		st.append(" and di.controladimss.enrollastname1='"+ apePat.toUpperCase() +"'");
		st.append(" and di.controladimss.enrollastname2='"+ apeMat.toUpperCase() +"'");
		if(calidad == 11 || calidad == 12){
			st.append(" and di.controladimss.cveCalidadAseg=" + calidad);
		}		
		Query query = em.createQuery(st.toString());	
		credenciales = query.getResultList();
		return credenciales;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Controladimss> getRuta(String nss, int calidad) {
		List<Controladimss> rutas = new ArrayList<Controladimss>();
		StringBuilder st = new StringBuilder();
		st.append("select ca from Controladimss ca");
		st.append(" where ca.numNssAsegurado='"+ nss +"'");
		st.append(" and ca.cveCalidadAseg=" + calidad);
		Query query = em.createQuery(st.toString());	
		rutas = query.getResultList();
		return rutas;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Controladimss> getRuta(String nss, int calidad, String nombre, String apePat, String apeMat) {
		List<Controladimss> rutas = new ArrayList<Controladimss>();
		StringBuilder st = new StringBuilder();
		st.append("select ca from Controladimss ca");
		st.append(" where ca.numNssAsegurado='"+ nss +"'");
		st.append(" and ca.cveCalidadAseg=" + calidad);
		st.append(" and ca.enrolfirstname='"+ nombre.toUpperCase() +"'");
		st.append(" and ca.enrollastname1='"+ apePat.toUpperCase() +"'");
		st.append(" and ca.enrollastname2='"+ apeMat.toUpperCase() +"'");
		if(calidad == 11 || calidad == 12){
			st.append(" and ca.cveCalidadAseg=" + calidad);
		}	
		Query query = em.createQuery(st.toString());	
		rutas = query.getResultList();
		return rutas;
	}

}
