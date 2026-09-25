package mx.gob.imss.ctirss.gestionpersonas.servicios.business;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.framework.exceptions.SolicitudNoEncontradaException;


@Local
public interface ReporteBusinessLocal {
    byte[] getComprobanteOperacion(Long folioSolicitud, String sNombreUsuarioReporte) throws SolicitudNoEncontradaException;
}
