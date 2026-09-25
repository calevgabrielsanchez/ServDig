package mx.gob.imss.ctirss.delta.derechohabientes.service.entity;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.GenericDerechohabientesException;
import mx.gob.imss.ctirss.delta.model.derechohabiente.BloqueoDerechosArco;

import javax.ejb.Local;

@Local
public interface DerechosArcoEntityLocal {

    void bloquear(BloqueoDerechosArco bloqueoDerechosArco) throws GenericDerechohabientesException;
    void actualizar(BloqueoDerechosArco bloqueoDerechosArco) throws GenericDerechohabientesException;
    BloqueoDerechosArco consultarAseguradoBloqueado(Long cveIdAsignacion, Long cveTipoTramite) throws GenericDerechohabientesException;

}
