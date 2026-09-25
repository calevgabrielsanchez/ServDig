package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.entity.TramiteModalidadEntityLocal;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.TramiteModalidadServiceRemote;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.Modalidad;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;

@Stateless( name = "tramiteModalidadService", mappedName = "tramiteModalidadService")
public class TramiteModalidadService extends AbstractServiceBusiness implements TramiteModalidadServiceLocal,TramiteModalidadServiceRemote{

	@EJB TramiteModalidadEntityLocal tramiteModalidadEntityLocal;

	@Override
	public List<TipoTramite> getTiposTramiteByModalidad(Long idModalidad)
			throws IllegalArgumentException {
		
		if(idModalidad == null) {
			throw new IllegalArgumentException("El id de la modalidad no debe ser null");
		}
		
		Modalidad modalidad = new Modalidad();
		modalidad.setIdModalidad(idModalidad);
		
		return getTiposTramiteByModalidad(modalidad);
	}
	
	@Override
	public List<TipoTramite> getTiposTramiteByModalidad(Modalidad modalidad)
			throws IllegalArgumentException {
		
		if(modalidad == null || modalidad.getIdModalidad() == null) {
			throw new IllegalArgumentException("El objeto de modalidad debe incluir el atributo idModalidad");
		}
		
		List<Modalidad> modalidades = new ArrayList<Modalidad>();
		modalidades.add(modalidad);
		
		return getTiposTramiteByModalidades(modalidades);
	}


	@Override
	public List<TipoTramite> getTiposTramiteByModalidades(
			List<Modalidad> modalidades) throws IllegalArgumentException {
		
		List<TipoTramite> tiposTramites = null;
		
		if(modalidades == null || modalidades.isEmpty()) {
			throw new IllegalArgumentException("La lista de modalidades no debe estar vacia o ser nula");
		}
		
		tiposTramites = this.tramiteModalidadEntityLocal.getTipoTramiteByModalidades(modalidades);
		
		return tiposTramites;
	}
		
}
