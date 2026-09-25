package mx.gob.imss.ctirss.delta.gestion.clasificacion.service.entity.domicilio;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.domicilio.Asentamiento;
import mx.gob.imss.ctirss.delta.model.domicilio.Localidad;
import mx.gob.imss.ctirss.delta.model.domicilio.Municipio;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CentroTrabajo;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.delta.persistence.DitMunicipioPatSujOblig;

@Stateless
public class DomicilioMigrServiceEntity extends AbstractServiceEntity implements DomicilioMigrServiceEntityLocal{
	@Override
	public SujetoObligado obtenerMunicipioMigr(Long cveIdPatronSujetoObligado){
		SujetoObligado sujetoObligado=new SujetoObligado();
		CentroTrabajo centroTrabajo=new CentroTrabajo();
		Asentamiento asentamiento=new Asentamiento();
		Localidad localidad=new Localidad();
		Municipio municipio=new Municipio();
		List<DitMunicipioPatSujOblig> lstDitMunicipioPatSujOblig=new ArrayList<DitMunicipioPatSujOblig>();
		Query query=null;
		try{
			query=em.createQuery(" from DitMunicipioPatSujOblig dmpso "
					+ " where dmpso.cveIdPatronSujetoObligado= :cveIdPatronSujetoObligado ");
			query.setParameter("cveIdPatronSujetoObligado", cveIdPatronSujetoObligado);
			lstDitMunicipioPatSujOblig=(List<DitMunicipioPatSujOblig>)query.getResultList();
			if(!lstDitMunicipioPatSujOblig.isEmpty()){
				for(DitMunicipioPatSujOblig mpso:lstDitMunicipioPatSujOblig){
					municipio.setNombre(mpso.getDicMunicipioImss().getNomMunicipioImss());
					localidad.setMunicipio(municipio);
					asentamiento.setLocalidad(localidad);
					centroTrabajo.setAsentamiento(asentamiento);
					sujetoObligado.setCntroTrabajo(centroTrabajo);
					break;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return sujetoObligado;
	}
}
