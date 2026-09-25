package mx.gob.imss.ctirss.delta.model.asegurado.cda;

import java.util.List;
import lombok.Getter;
import lombok.Setter;


public class CuentaIndividual extends BaseModel {

	private @Getter @Setter static final long serialVersionUID = -5598880742193483377L;
	private @Getter @Setter Long idTramite;
	private @Getter @Setter String nombreDelegacionOrigen;
	private @Getter @Setter String nombreRP;
	private @Getter @Setter String numeroRP;
	private @Getter @Setter int claveCiz;
	private @Getter @Setter int indice;
	private @Getter @Setter List<PeriodoCuentaIndividual> periodos;
	private @Getter @Setter List<PeriodoCuentaIndividual> periodosNuevos;
  private @Getter @Setter List<PeriodoCuentaIndividual> periodosModificados;
  private @Getter @Setter List<PeriodoCuentaIndividual> periodosIncluidos;
  private @Getter @Setter List<PeriodoCuentaIndividual> periodosEliminados; 
	private @Getter @Setter String nssDestino;
	private @Getter @Setter List<String> listaNss;
	private @Getter @Setter int claveDelegacionOrigen;        
}
