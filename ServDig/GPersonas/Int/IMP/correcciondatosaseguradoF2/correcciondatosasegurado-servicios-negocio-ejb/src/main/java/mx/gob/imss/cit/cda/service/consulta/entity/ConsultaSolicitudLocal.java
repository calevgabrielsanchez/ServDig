package mx.gob.imss.cit.cda.service.consulta.entity;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.persistence.DitDetalleNss;

@Local
public interface ConsultaSolicitudLocal {

    DitDetalleNss consultarOrigenNssPorTramite(String cveIdTramite);

}
