package mx.gob.imss.ctirss.delta.gestion.patronal.service.entity.centro.trabajo;

import java.util.List;

import javax.ejb.Local;


import mx.gob.imss.ctirss.delta.framework.base.exception.GestionPatronalBusinessException;
import mx.gob.imss.ctirss.delta.model.domicilio.MunicipioIMSS;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.medio.contacto.MedioContacto;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CentroTrabajo;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;

@Local
public interface CentroTrabajoServiceEntityLocal {
	
	CentroTrabajo actualizarCentroTrabajo(CentroTrabajo centroTrabajo) throws GestionPatronalBusinessException;

	boolean validarSubDelOrigenSubDelDestino(long idSubdelegacionOrigen, long idSubdelegacionDestino);
	
	List<Subdelegacion> obtenerSubdelegacionesCompatibles(Long cveIdSubdelegacion);

    Domicilio findDomicilio(Long idDomicilio);
    
    void actualizarSubdelegacionDelRegistroPatronal(Long cveIdPatronSujetoObligado, Long idSubdelegacion);
    
    void actualizarMunicipioIMSSDelRegistroPatronal(Long cveIdPatronSujetoObligado, String idMunicipioIMSS); 
    
    MunicipioIMSS consultarMunicipioIMSSPorRegistroPatronal(Long cveIdPatron);
    
    List<MedioContacto> consultarMediosContactoPorIdPatron(Long idPatron);
}
