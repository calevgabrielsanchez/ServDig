package mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual;

import lombok.Getter;
import lombok.Setter;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.BaseModel;

public class CuentaIndividualPeriodo extends BaseModel {

  private static final long serialVersionUID = -3951180553320842450L;

  private @Getter @Setter Long cveIdPeriodoCuentaIndividual;
  private @Getter @Setter CuentaIndividualNss cuentaIndividualNss;  
  private @Getter @Setter String fechaInicioMovimiento;
  private @Getter @Setter String numeroRegistroPatronal;
  private @Getter @Setter Long numeroConsecutivoPeriodos;    
  private @Getter @Setter String fechaFinalMovimiento;
  private @Getter @Setter char origenMovimientoInicial;
  private @Getter @Setter char origenMovimientoFinal;
  private @Getter @Setter Long tipoMovimientoInicial;
  private @Getter @Setter Long tipoMovimientoFinal;
  private @Getter @Setter String fechaRecepcionMovimiento;
  private @Getter @Setter Double salarioBase;
  private @Getter @Setter String tipoSalario;
  private @Getter @Setter String jornadaSemanal;
  private @Getter @Setter String eventual;
  private @Getter @Setter String subrogacionServicio;
  private @Getter @Setter String huelga;
  private @Getter @Setter String extemporaneoConvenioSuspension;
  private @Getter @Setter String fechaActualizacion;    
  private @Getter @Setter Long claveCiz;
  private @Getter @Setter String estatus;
  private @Getter @Setter String fechaCarga;
  private @Getter @Setter String fechaProceso;
  private @Getter @Setter Long cveIdPeriodoAnterior;   
  private @Getter @Setter int historico;

}
