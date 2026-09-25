package mx.gob.imss.ctirss.delta.derechohabientes.service.entity;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceEntity;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.persistence.DicTramiteModalidad;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

@Stateless(name = "tramiteModalidadEntity" , mappedName = "tramiteModalidadEntity")
public class TramiteModalidadEntity extends AbstractServiceEntity implements
		TramiteModalidadEntityLocal {

	@Override
	public Boolean tramitePermitidoPorModalidades(Long idTipoTramite,
			List<Long> idModalidades, Boolean isPensionado) {
		Boolean permitido = false;
		
		if(isPensionado) {
			permitido = this.tramitePermitidoParaPensionado(idTipoTramite);
		} else {
			permitido = this.tramitePermitidoPorIdsModalidades(idTipoTramite, idModalidades);
		}
		
		return permitido;
	}

	@Override
	public Boolean tramitePermitidoPorModalidad(Long idTipoTramite,
			Long idModalidad, Boolean isPensionado) {
		Boolean permitido = false;
		List<Long> idModalidades = new ArrayList<Long>();
		idModalidades.add(idModalidad);
		
		if(isPensionado) {
			permitido = this.tramitePermitidoParaPensionado(idTipoTramite);
		} else {
			permitido = this.tramitePermitidoPorIdsModalidades(idTipoTramite, idModalidades);
		}
		
		return permitido;
	}

	@Override
	public Boolean tramitePermitidoPorModalidad(Long idTipoTramite,
			Modalidad modalidad, Boolean isPensionado) {
		
		Boolean permitido = false;
		List<Long> idModalidades = new ArrayList<Long>();
		idModalidades.add(modalidad.getIdModalidad());
		
		if(isPensionado) {
			permitido = this.tramitePermitidoParaPensionado(idTipoTramite);
		} else {
			permitido = this.tramitePermitidoPorIdsModalidades(idTipoTramite, idModalidades);
		}
		
		return permitido;
	}

	@Override
	public Boolean tramitePermitidoPorNumModalidad(Long idTipoTramite,
			String numModalidad, Boolean isPensionado) {
		Boolean permitido = false;
		List<String> numModalidades = new ArrayList<String>();
		numModalidades.add(numModalidad);
		
		if(isPensionado) {
			permitido = this.tramitePermitidoParaPensionado(idTipoTramite);
		} else {
			permitido = this.tramitePermitidoPorNumModalidades(idTipoTramite, numModalidades);
		}
		
		return permitido;
	}

	@Override
	public Boolean tramitePermitidoPorNumModalidades(Long idTipoTramite,
			List<String> numModalidad, Boolean isPensionado) {
		Boolean permitido = false;
		
		if(isPensionado) {
			permitido = this.tramitePermitidoParaPensionado(idTipoTramite);
		} else {
			permitido = this.tramitePermitidoPorNumModalidades(idTipoTramite, numModalidad);
		}
		
		return permitido;
	}

	@Override
	public Boolean tramitePermitidoParaPensionado(Long idTipoTramite) {
		
		return true;
	}

	/**
	 * Metodo que indica si un tipo de tramite esta permitido para cierto tipo de ids modalidades
	 * @param idTipoTramite el tipo de tramite a verificar
	 * @param modalidades Las modalidades
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Boolean tramitePermitidoPorIdsModalidades(Long idTipoTramite, List<Long> modalidades) {

		List<DicTramiteModalidad> resultados = null;
		
		Criteria query = this.getSession().createCriteria(DicTramiteModalidad.class);
		query.createAlias("dicTipoTramite", "tipoTramite");
		query.add(Restrictions.isNull("fecRegistroBaja"));
		query.add(Restrictions.eq("tipoTramite.cveIdTipoTramite", idTipoTramite));
		query.createAlias("dicModalidad", "modalidad");
		
		
		if(modalidades.size() == 1) {
			query.add(Restrictions.eq("modalidad.cveIdModalidad", modalidades.get(0)));
		} else {
			query.add(Restrictions.in("modalidad.cveIdModalidad", modalidades));
		}
		
		resultados = query.list();
		
		if(resultados != null && !resultados.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Metodo para verificar si para un tipo de modalidad se puede hacer un tramite, este metodo recibe el numero de la 
	 * modalidad y no el id
	 * @param idTipoTramite
	 * @param numModalidades
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Boolean tramitePermitidoPorNumModalidades(Long idTipoTramite, List<String> numModalidades) {
		List<DicTramiteModalidad> resultados = null;
		
		Criteria query = this.getSession().createCriteria(DicTramiteModalidad.class);
		query.add(Restrictions.isNull("fecRegistroBaja"));
		query.createAlias("dicTipoTramite", "tipoTramite");
		query.add(Restrictions.eq("tipoTramite.cveIdTipoTramite", idTipoTramite));
		query.createAlias("dicModalidad", "modalidad");
		
		if(numModalidades.size() == 1) {
			query.add(Restrictions.eq("modalidad.numModalidad", numModalidades.get(0)));
		} else {
			query.add(Restrictions.in("modalidad.numModalidad", numModalidades));
		}
		
		resultados = query.list();
		
		if(resultados != null && !resultados.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
}
