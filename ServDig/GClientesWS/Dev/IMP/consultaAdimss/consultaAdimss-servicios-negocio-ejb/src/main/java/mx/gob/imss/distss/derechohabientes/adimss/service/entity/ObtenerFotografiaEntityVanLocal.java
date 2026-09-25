package mx.gob.imss.distss.derechohabientes.adimss.service.entity;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent.Controladimss;
import mx.gob.imss.distss.derechohabientes.adimss.service.persistence.vangent.Documentuminterface;

@Local
public interface ObtenerFotografiaEntityVanLocal {

	public List<Documentuminterface> getObjetID(String nss, int calidad);
	
	public List<Documentuminterface> getObjetID(String nss, int calidad, String nombre, String apePat, String apeMat);
	
	public List<Controladimss> getRuta(String nss, int calidad);
	
	public List<Controladimss> getRuta(String nss, int calidad, String nombre, String apePat, String apeMat);
	
}
