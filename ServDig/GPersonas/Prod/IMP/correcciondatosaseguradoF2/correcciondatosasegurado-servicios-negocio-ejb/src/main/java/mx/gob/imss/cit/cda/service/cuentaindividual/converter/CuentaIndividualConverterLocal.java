package mx.gob.imss.cit.cda.service.cuentaindividual.converter;

import java.util.List;

import javax.ejb.Local;

import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.CuentaIndividualNss;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.MovimientosCuentaIndividual;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.PeriodoCuentaIndividual;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual.PeriodosRegistroPatronal;
import mx.gob.imss.ctirss.delta.model.enums.TipoRegularizacionPeriodoEnum;
import mx.gob.imss.ctirss.delta.persistence.DitCorreccionCtaIndCda;
import mx.gob.imss.ctirss.delta.persistence.DitCtaIndNssCda;
import mx.gob.imss.ctirss.delta.persistence.DitMovAclaracionNssCda;

@Local
public interface CuentaIndividualConverterLocal {
 
  DitCtaIndNssCda modelToEntity( PeriodoCuentaIndividual model, PeriodosRegistroPatronal periodosRegistroPatronal,CuentaIndividualNss cuentaIndividualNss);
  PeriodoCuentaIndividual entityToModel(DitCtaIndNssCda entity);
    List<DitCtaIndNssCda> convertModelToEntity(CuentaIndividualNss cuentaIndividualNss);
    DitCorreccionCtaIndCda convertMovimientiIndividualEntity(MovimientosCuentaIndividual movimientosCuentaIndividual);
    PeriodoCuentaIndividual convertEntityToModel(DitCtaIndNssCda entity, int historico, TipoRegularizacionPeriodoEnum opOrigen, TipoRegularizacionPeriodoEnum opDestino);
    DitMovAclaracionNssCda convertAclaracionMovimientosEntity(DitCorreccionCtaIndCda correccion, Long tipoAclaracion);
}
