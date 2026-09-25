package mx.gob.imss.cit.cda.service.certificacion.entity;

import mx.gob.imss.ctirss.delta.model.asegurado.cda.DetalleNssCda;

import java.util.List;
import javax.ejb.Local;

@Local
public interface CertificacionLocal {

    List<DetalleNssCda> obtenerListaNSS(Long idTramite);
}
