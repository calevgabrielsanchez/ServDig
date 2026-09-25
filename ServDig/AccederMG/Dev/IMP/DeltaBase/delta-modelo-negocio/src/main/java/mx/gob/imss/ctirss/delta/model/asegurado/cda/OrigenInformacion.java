package mx.gob.imss.ctirss.delta.model.asegurado.cda;

import lombok.Getter;
import lombok.Setter;


/**
 * Informacion general del asegurado
 * para cada fuente de informacion
 * (RANAPO, CIZ, ..., CANASE, HISTORICO)
 * 
 * @author erik.ramirez
 *
 */
public class OrigenInformacion extends BaseModel {
	private @Getter @Setter static final long serialVersionUID = 1897649279861228368L;	
	private @Getter @Setter String tipoFuente;
	private @Getter @Setter String curp;
	private @Getter @Setter String apellidoPaterno;
	private @Getter @Setter String apellidoMaterno;
	private @Getter @Setter String nombre;
	private @Getter @Setter String sexo;
	private @Getter @Setter String fechaNacimiento;
	private @Getter @Setter String lugarNacimiento;
	private @Getter @Setter String idlugarNacimiento;
	private @Getter @Setter String curpsHistoricas;
	private @Getter @Setter String nacionalidad;
	private @Getter @Setter String datosDocumentoProbatorio;
}
