package mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual;

import lombok.Getter;
import lombok.Setter;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.BaseModel;

public class CuentaIndividualRegistroPatronal extends BaseModel {

  private static final long serialVersionUID = -3951180553320842450L;

  private @Getter @Setter Long cveIdDetalleNssCda;
  private @Getter @Setter String numeroRegistroPatronal;
  private @Getter @Setter String nombreRegistroPatronal;
  private @Getter @Setter Long claveDelegacionOrigen;
  private @Getter @Setter String claveModalidad;
  private @Getter @Setter String claveCiz;
  private @Getter @Setter String nombreDelegacionOrigen;
  private @Getter @Setter String fechaPeriodoInicial;
  private @Getter @Setter String fechaPeriodoFinal;
  private @Getter @Setter CuentaIndividualNss nssDestino;
  private @Getter @Setter Long nuevos;
  private @Getter @Setter Long incluidos;
  private @Getter @Setter Long eliminados;
  private @Getter @Setter Long modificados;

}
