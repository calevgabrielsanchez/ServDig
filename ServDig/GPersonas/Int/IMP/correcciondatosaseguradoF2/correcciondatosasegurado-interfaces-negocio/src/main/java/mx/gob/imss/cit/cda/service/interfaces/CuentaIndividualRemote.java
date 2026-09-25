package mx.gob.imss.cit.cda.service.interfaces;

import java.util.List;

import javax.ejb.Remote;

import mx.gob.imss.ctirss.delta.model.asegurado.cda.Page;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.PageCuentaIndividualPeriodo;
import mx.gob.imss.ctirss.delta.framework.exceptions.TipoAclaracionCuentaIndividualException;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.CuentaIndividual;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.CuentaIndividualCorreccion;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.CuentaIndividualNss;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.CuentaIndividualRegistroPatronal;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.PeriodoCuentaIndividual;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.PeriodosRegistroPatronal;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CuentaIndividualVO;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.PeriodoMovimientoAfiliatorio;

@Remote
public interface CuentaIndividualRemote {

    List<PeriodoMovimientoAfiliatorio> consultarMovimientosCuentaIndividual (
            String nss) throws CuentaIndividualNoDisponibleException;

    List<PeriodoMovimientoAfiliatorio> consultarUltimoMovimientoCuentaIndividual(
            String nss);

    List<PeriodoCuentaIndividual> consultarInformacionMovimientosCuentaIndividual(String nss, Long cveIdTramite,Long idDetalleNss);

    List<CuentaIndividualVO> obtenerMovimientosActualizados(Long cveIdTramite);
    
    List<CuentaIndividualVO> consultarInformacionPreviaCuentaIndividual(String nss, Long cveIdTramite);
    
    CuentaIndividual save(CuentaIndividual cuentaIndividual);
    
    CuentaIndividual findByFolio(String folioSolitud) throws CuentaIndividualNoDisponibleException;
    
    CuentaIndividualNss findByFolioNss(String folioSolicitud, String nss) throws CuentaIndividualNoDisponibleException;
    
    String complete(String folio, String curp);
    
    void guardaMovimientoAclaracionCI(String folio) 
    		throws TipoAclaracionCuentaIndividualException;
    
    /*
     * Nuevos servicios para la captura parcial
     */
    CuentaIndividual obtenerCuentaIndividual( Long cveIdCorreccionDatosAsegurado );
    List<CuentaIndividualRegistroPatronal> obtenerListaCuentaIndividualRegistroPatronal( Long cveIdDetalleNssCda  );
    Long registrarCorreccionNssRegistroPatronal( CuentaIndividualRegistroPatronal registroPatronal ) throws MotivosAclaracionErroneosException;
    PageCuentaIndividualPeriodo obtenerPageCuentaIndividualPeriodo( CuentaIndividualRegistroPatronal registroPatronal, long page ); 
    PageCuentaIndividualPeriodo obtenerPageCuentaIndividualCorreccion( CuentaIndividualRegistroPatronal registroPatronal, long page ); 
    Long registrarListaCuentaIndividualCorreccion( PageCuentaIndividualPeriodo page ) throws MotivosAclaracionErroneosException; 
    
    CuentaIndividual existenMovimientoCI(String folioSolitud);
    
    boolean eliminarMovimientosCuentaIndividual(long cveIdCorreccion);
    
    List<Long> getListNssByFolioTramite(String folioTramite);

}
