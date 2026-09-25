package mx.gob.imss.ctirss.delta.model.asegurado.cda;

import lombok.Getter;
import lombok.Setter;

public class DetalleCorreccionNss extends BaseModel {
	
	private static final long serialVersionUID = -9040540076602736550L;
	
	private @Getter @Setter String origenDato;
	private @Getter @Setter String datos;
	private @Getter @Setter String infoImss;
	private @Getter @Setter String infoActual;
	private @Getter @Setter String estatus;
	private @Getter @Setter String fechaProceso;
	private @Getter @Setter String curp;
	private @Getter @Setter String nombre;
	private @Getter @Setter String apellidoPaterno;
	private @Getter @Setter String apellidoMaterno;
	private @Getter @Setter String fechaNacimiento;
	private @Getter @Setter String sexo;
	
	
}
