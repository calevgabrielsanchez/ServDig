package mx.gob.imss.cit.cda.service.entity;

import java.util.List;

import javax.ejb.Local;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.CuentaIndividualRegistroPatronal;
import mx.gob.imss.ctirss.delta.model.enums.TipoNSSCorreccionEnum;

import mx.gob.imss.ctirss.delta.model.gestion.patronal.CuentaIndividualVO;
import mx.gob.imss.ctirss.delta.persistence.DitCorreccionCtaIndCda;
import mx.gob.imss.ctirss.delta.persistence.DitCtaIndNssCda;
import mx.gob.imss.ctirss.delta.persistence.DitDetalleNss;
import mx.gob.imss.ctirss.delta.persistence.DitMovAclaracionNssCda;

@Local
public interface CuentaIndividualLocal {

    List<CuentaIndividualVO> obtenerListaNSS(Long cveIdTramite);

    boolean existenDatosIdtramite(Long cveIdTramite);
    
    DitCtaIndNssCda guardarPeriodo(DitCtaIndNssCda periodo);
    
    void actualizarPeriodo(DitCtaIndNssCda periodo);
    
    Long getNumeroPeriodosPorNss(Long cveNssDetalle);
    
    List<DitCtaIndNssCda> getPeriodosGuardadosVentanilla(Long cveNssDetalle);
    
    void bajaPeriodo(DitCtaIndNssCda periodo);
    
    List<DitCtaIndNssCda> getPeriodosGuardadosIndividual(Long cveNssDetalle);
    
    List<DitCtaIndNssCda> getPeriodosGuardados(Long cveNssDetalle);
    
    List<DitCorreccionCtaIndCda> getPeriodosEliminados(Long cveNssDetalle);
    List<DitCorreccionCtaIndCda> getPeriodosAgregados(Long cveNssDetalle);
    List<DitCorreccionCtaIndCda> getPeriodosPorOperacionOrigen(Long cveNssDetalle, String idMovOperacionOrigen, String idMovOperacionDestino);
    List<DitCorreccionCtaIndCda> getPeriodosPorOperacionDestino(Long cveNssDetalle, String idMovOperacionOrigen, String idMovOperacionDestino);
    
    Long findbyNss(Long cveDetalleNss, String nss);
    
    
    List<CuentaIndividualRegistroPatronal> getPeriodosRegistroPatronalByIdDetalle( Long cveIdDetalleNssCda );

  public List<DitCtaIndNssCda> obtenerPeriodosByCveIdDetalleNssCdaAndRegistroPatronal(
          Long cveIdDetalleNssCda, String registroPatronal);

  public void crearPeriodo(DitCtaIndNssCda periodo);

  public int eliminarCorreccionesBycveIdDetalleNssCdaAndRegistroPatronal(
          Long cveIdDetalleNssCda, String registroPatronal);

  public void crearCorreccion(DitCorreccionCtaIndCda correccion);

  public Long obtenerConsecutivoMovimientosByNss(String nss, Long cveIdDetalleNssCda);

  public Long obtenerTotalPeriodosByCveIdDetalleNssCdaAndRegistroPatronal(
          Long cveIdDetalleNssCda, String numeroRegistroPatronal);

  public List<DitCtaIndNssCda> obtenerPeriodosByCveIdDetalleNssCdaAndRegistroPatronal(
          Long cveIdDetalleNssCda, String numeroRegistroPatronal, long page);

  public List<DitCorreccionCtaIndCda> obtenerCorreccionesByIdsCtaInd(
          List<Long> ids);

  public Long obtenerTotalPeriodosCorreccionByCveIdDetalleNssCdaAndRegistroPatronal(
          Long cveIdDetalleNssCda, String numeroRegistroPatronal);

  public List<DitCtaIndNssCda> obtenerPeriodosCorreccionByCveIdDetalleNssCdaAndRegistroPatronal(
          Long cveIdDetalleNssCda, String numeroRegistroPatronal, long pageNumber);
  
  Long eliminarCorreccionesByIdPeriodoOriginal(List<Long> ids);

  public List<DitMovAclaracionNssCda> obtenerMovimientosAclaracionByCveIdDetalleNssCda(
          Long cveIdDetalleNssCda);

  public List<DitDetalleNss> getListNssByCveIdCorreccionDatosAsegurado(
          Long cveIdCorreccionDatosAsegurado);

  public List<DitDetalleNss> getListNssByCveIdCorreccionDatosAsegurado(
          Long cveIdCorreccionDatosAsegurado,
          TipoNSSCorreccionEnum tipoNSSCorreccionEnum);

  public DitCtaIndNssCda obtenerPeriodoById(Long cveIdPeriodoCuentaIndividual);

  public List<DitCtaIndNssCda> obtenerPeriodosNuevosByCveIdDetalleNssCdaAndRegistroPatronal(
          Long cveIdDetalleNssCda, String numeroRegistroPatronal,
          long pageNumber);

  public Long eliminarNuevosByCveIdDetalleNssCdaAndRegistroPatronal(
          Long cveIdDetalleNssCda, String numeroRegistroPatronal);
  
  
	boolean eliminarMovimientosCuentaIndividual(long cveIdCorreccion);

	List<Long> getListNssByFolioTramite(String folioTramite);

  public DitDetalleNss findById(Long cveIdDetalleNssCda);
    
}
