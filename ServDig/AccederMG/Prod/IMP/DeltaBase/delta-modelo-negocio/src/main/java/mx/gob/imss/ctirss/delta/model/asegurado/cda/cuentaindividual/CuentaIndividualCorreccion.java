package mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.BaseModel;

public class CuentaIndividualCorreccion extends BaseModel {

  private static final long serialVersionUID = -3951180553320842451L;
  private @Getter @Setter Long cveIdCorreccionCuentaIndividualCda;
  private @Getter @Setter CuentaIndividualMovimiento origen = new CuentaIndividualMovimiento();
  private @Getter @Setter CuentaIndividualMovimiento destino = new CuentaIndividualMovimiento();
  private @Getter @Setter Long cveIdMovAclaracionNss;
  private @Getter @Setter Long indicadorConsecutivoMovimiento;
  private @Getter @Setter Long cveIdEstadoMovimientoSindo;
  private @Getter @Setter Date fecMovEnvSindo;

}
