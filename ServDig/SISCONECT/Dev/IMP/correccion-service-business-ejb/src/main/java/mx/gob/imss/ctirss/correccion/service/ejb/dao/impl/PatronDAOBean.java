package mx.gob.imss.ctirss.correccion.service.ejb.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;
import mx.gob.imss.ctirss.correccion.framework.base.repository.AbstractRespository;
import mx.gob.imss.ctirss.correccion.model.SacMunicipio;
import mx.gob.imss.ctirss.correccion.model.SatPatron;
import mx.gob.imss.ctirss.correccion.model.SatUbicacion;
import mx.gob.imss.ctirss.correccion.service.ejb.dao.PatronDaoLocal;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

@Stateless
public class PatronDAOBean <T extends AbstractModel> extends AbstractRespository implements PatronDaoLocal<T>{

	public SatPatron saveOrUpdate(SatPatron model) {
		try{
			SatPatron actual = consultaPorClave(model);
			if(actual != null)
			{
				actual = actualizaRegistroPatronal(actual, model);
				this.getSession().update(actual.getUbicacion());
				this.getSession().update(actual);
				return actual;
			}
			else
			{
				this.getSession().save(model.getUbicacion());
				model.setFkUbicacion(model.getUbicacion().getCvePK());
				this.getSession().save(model);
			}
			this.getSession().flush();
			return model;
		}catch(RuntimeException re){
			re.printStackTrace();
			throw new PersistenceException();
		}
	}

	
	
	public SatPatron consultaPorClave(SatPatron filtro) {
		Criteria criteria = this.getSession().createCriteria(filtro.getClass()).add(Restrictions.eq("registroPatronal", filtro.getRegistroPatronal()));
		List l = criteria.list();
		if(l!=null&&l.size()>0)
		{
			SatPatron actual = (SatPatron)l.get(0);
			Criteria criteria2 = this.getSession().createCriteria(SatUbicacion.class).add(Restrictions.eq("cvePK", actual.getFkUbicacion()));
			List l2 = criteria2.list();
			if(l2!=null&&l2.size()>0)
			{
				SatUbicacion ubicacion = (SatUbicacion)l2.get(0);
				ubicacion.setMunicipio(getMunicipioById(ubicacion.getFkMunicipio()));
				actual.setUbicacion(ubicacion);
			}
			return actual;
		}
		return null;
	}
	
    /**
     * Recibe como parametro un objeto de tipo Patron obtenido desde el servicio de sindo y lo transforma
     * a un objeto de tipo Registro Patron que es nuestro entity dentro del proyecto para ingresarlo en base de datos
     * @param miPatronPrueba
     * @return
     */
    private SatPatron actualizaRegistroPatronal(SatPatron actual,SatPatron nuevo) {

        if(nuevo.getRazonSocial()!=null&&nuevo.getRazonSocial().trim().length()>0)
        	actual.setRazonSocial(nuevo.getRazonSocial());
        if(nuevo.getRfc()!=null&&nuevo.getRfc().length()>0)
        	actual.setRfc(nuevo.getRfc());
        if(nuevo.getCurp()!=null&&nuevo.getCurp().length()>0)
        	actual.setCurp(nuevo.getRfc());
        
        if(nuevo.getUbicacion().getCalle()!=null&&nuevo.getUbicacion().getCalle().trim().length()>0)
        	actual.getUbicacion().setCalle(nuevo.getUbicacion().getCalle());
        if(nuevo.getUbicacion().getNumeroInterior()!=null&&nuevo.getUbicacion().getNumeroInterior().trim().length()>0)
        	actual.getUbicacion().setNumeroInterior(nuevo.getUbicacion().getNumeroInterior());
        if(nuevo.getUbicacion().getNumeroExterior()!=null&&nuevo.getUbicacion().getNumeroExterior().trim().length()>0)
        	actual.getUbicacion().setNumeroExterior(nuevo.getUbicacion().getNumeroExterior());
        if(nuevo.getUbicacion().getColonia()!=null&&nuevo.getUbicacion().getColonia().trim().length()>0)
        	actual.getUbicacion().setColonia(nuevo.getUbicacion().getColonia());
        actual.getUbicacion().setCodigoPostal(nuevo.getUbicacion().getCodigoPostal());
        if(nuevo.getUbicacion().geteMail()!=null&&nuevo.getUbicacion().geteMail().trim().length()>0)
        	actual.getUbicacion().seteMail(nuevo.getUbicacion().geteMail());
        if(nuevo.getUbicacion().getTelefono()!=null&&nuevo.getUbicacion().getTelefono()>0)
        	actual.getUbicacion().setTelefono(nuevo.getUbicacion().getTelefono());

        actual.getUbicacion().setMunicipio(nuevo.getUbicacion().getMunicipio());
        actual.getUbicacion().setFkMunicipio(nuevo.getUbicacion().getFkMunicipio());
        
        return actual;
    }



	@Override
	public SacMunicipio obtenmunicipio(String cveMunicipio) {
		Criteria criteria = this.getSession().createCriteria(SacMunicipio.class).add(Restrictions.eq("codigo", cveMunicipio));
		List l = criteria.list();
		if(l!=null&&l.size()>0)
		{
			return (SacMunicipio)l.get(0);
		}
		return null;
	}

	public SacMunicipio getMunicipioById(Integer cveMunicipio) {
		Criteria criteria = this.getSession().createCriteria(SacMunicipio.class).add(Restrictions.eq("cvePK", cveMunicipio));
		List l = criteria.list();
		if(l!=null&&l.size()>0)
		{
			return (SacMunicipio)l.get(0);
		}
		return null;
	}


	@Override
	public SatPatron getById(Long cvePK) {
		Criteria criteria = this.getSession().createCriteria(SatPatron.class).add(Restrictions.eq("cvePK", cvePK));
		List l = criteria.list();
		if(l!=null&&l.size()>0)
		{
			SatPatron actual = (SatPatron)l.get(0);
			Criteria criteria2 = this.getSession().createCriteria(SatUbicacion.class).add(Restrictions.eq("cvePK", actual.getFkUbicacion()));
			List l2 = criteria2.list();
			if(l2!=null&&l2.size()>0)
			{
				SatUbicacion ubicacion = (SatUbicacion)l2.get(0);
				ubicacion.setMunicipio(getMunicipioById(ubicacion.getFkMunicipio()));
				actual.setUbicacion(ubicacion);
			}
			return actual;
		}
		return null;
	}
	
	/**
	 * Metodo que obtiene SATPatron por medio de su cve
	 */
	@Override
	public SatPatron getByRegistroPatronal(String registroPatronal) {
		Criteria criteria = this.getSession().createCriteria(SatPatron.class).add(Restrictions.eq("registroPatronal", registroPatronal));
		List l = criteria.list();
		if(l!=null&&l.size()>0)
		{
			SatPatron actual = (SatPatron)l.get(0);
			Criteria criteria2 = this.getSession().createCriteria(SatUbicacion.class).add(Restrictions.eq("cvePK", actual.getFkUbicacion()));
			List l2 = criteria2.list();
			if(l2!=null&&l2.size()>0)
			{
				SatUbicacion ubicacion = (SatUbicacion)l2.get(0);
				ubicacion.setMunicipio(getMunicipioById(ubicacion.getFkMunicipio()));
				actual.setUbicacion(ubicacion);
			}
			return actual;
		}
		return null;
	}


}
