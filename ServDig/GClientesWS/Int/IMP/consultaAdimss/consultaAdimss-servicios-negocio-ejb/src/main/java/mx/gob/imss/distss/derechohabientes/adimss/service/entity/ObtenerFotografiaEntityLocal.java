package mx.gob.imss.distss.derechohabientes.adimss.service.entity;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.distss.derechohabientes.adimss.service.persistence.adimss.AdtCredencial;

@Local
public interface ObtenerFotografiaEntityLocal {

	String getTest();
	
	public List<AdtCredencial> getImagenId(String nss, int calidad);
	
	public List<AdtCredencial> getImagenId(String nss, int calidad, String nombre, String apePat, String apeMat);
	
}
