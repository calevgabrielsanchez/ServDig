package mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.BaseModel;
import mx.gob.imss.ctirss.delta.model.enums.TipoNSSCorreccionEnum;
import mx.gob.imss.ctirss.delta.model.enums.TipoRegularizacionNSSEnum;

public class CuentaIndividualNss extends BaseModel {

  private static final long serialVersionUID = -3951180553320842449L;
  private @Getter @Setter Long cveIdDetalleNssCda;
  private @Getter @Setter String nss;
  private @Getter @Setter Long movimientoAclaracionHomonimia;
  private @Getter @Setter Long movimientoAclaracionInvasion;
  private @Getter @Setter Long movimientoAclaracionCuentaIlogica;  
  private @Getter @Setter List<CuentaIndividualNss> listaNssDestino = new ArrayList<CuentaIndividualNss>();
  
  private @Getter @Setter List<TipoRegularizacionNSSEnum> tipoRegularizacion = new ArrayList<TipoRegularizacionNSSEnum>();
  private @Getter @Setter List<String> listaTipoRegularizacion = new ArrayList<String>(); 
  private @Getter @Setter TipoNSSCorreccionEnum tipoCorreccion;
  private @Getter @Setter List<PeriodosRegistroPatronal> listaPeriodosRegistroPatronal = new ArrayList<PeriodosRegistroPatronal>();

}
