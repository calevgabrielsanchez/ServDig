package mx.gob.imss.ctirss.delta.model.asegurado.cda.cuentaindividual;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.BaseModel;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PeriodosRegistroPatronal extends BaseModel {

  private static final long serialVersionUID = -5598880742193483377L;    
  private @Getter @Setter String nombreDelegacionOrigen;
  private @Getter @Setter String nombreRegistroPatronal;
  private @Getter @Setter String numeroRegistroPatronal;
  private @Getter @Setter String claveModalidad;
  private @Getter @Setter String curp;
  private @Getter @Setter int claveCiz;
  private @Getter @Setter int claveDelegacionOrigen;
  private @Getter @Setter List<PeriodoCuentaIndividual> periodos = new ArrayList<PeriodoCuentaIndividual>();
  private @Getter @Setter List<PeriodoCuentaIndividual> periodosNuevos = new ArrayList<PeriodoCuentaIndividual>();
  private @Getter @Setter List<PeriodoCuentaIndividual> periodosModificados = new ArrayList<PeriodoCuentaIndividual>();
  private @Getter @Setter List<PeriodoCuentaIndividual> periodosEliminados = new ArrayList<PeriodoCuentaIndividual>();
  private @Getter @Setter List<PeriodoCuentaIndividual> periodosIncluidos = new ArrayList<PeriodoCuentaIndividual>();
  private @Getter @Setter String nssDestino;
  private @Getter @Setter List<String> listaNss;
}
