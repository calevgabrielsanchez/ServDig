package mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual;

import lombok.Getter;
import lombok.Setter;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.BaseModel;
import mx.gob.imss.ctirss.delta.model.enums.TipoRegularizacionPeriodoEnum;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Modelo que representa un periodo de un registro patronal para Cuenta Individual
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PeriodoCuentaIndividual extends BaseModel {

    private static final long serialVersionUID = -7745943101941458642L;    
    private @Getter @Setter String nss;   
    private @Getter @Setter String fechaInicioMovimiento;
    private @Getter @Setter String numeroRegistroPatronal;
    private @Getter @Setter int indicadorConsecutivoMovimiento;
    private @Getter @Setter int numeroConsecutivoPeriodos;    
    private @Getter @Setter String fechaFinalMovimiento;
    private @Getter @Setter String origenMovimientoInicial;
    private @Getter @Setter String origenMovimientoFinal;
    private @Getter @Setter int tipoMovimientoInicial;
    private @Getter @Setter int tipoMovimientoFinal;
    private @Getter @Setter String fechaRecepcionMovimiento;
    private @Getter @Setter Double salarioBase;
    private @Getter @Setter String tipoSalario;
    private @Getter @Setter String jornadaSemanal;
    private @Getter @Setter String eventual;
    private @Getter @Setter String subrogacionServicio;
    private @Getter @Setter String huelga;
    private @Getter @Setter String extemporaneoConvenioSuspension;
    private @Getter @Setter String fechaActualizacion;    
    private @Getter @Setter int claveCiz;
    private @Getter @Setter String estatus;
    private @Getter @Setter String fechaCarga;
    private @Getter @Setter TipoRegularizacionPeriodoEnum tipoRegularizacionPeriodo;
    private @Getter @Setter Long cveIdPeriodoCuentaIndividual;
    private @Getter @Setter String fechaProceso;
    private @Getter @Setter Long cveIdPeriodoAnterior;   
    private @Getter @Setter int historico;
    private @Getter @Setter int claveDelegacionOrigen;

}
