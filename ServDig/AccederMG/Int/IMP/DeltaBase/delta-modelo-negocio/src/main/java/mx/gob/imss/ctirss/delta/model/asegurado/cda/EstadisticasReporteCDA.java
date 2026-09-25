package mx.gob.imss.ctirss.delta.model.asegurado.cda;

import lombok.Getter;
import lombok.Setter;


public class EstadisticasReporteCDA extends BaseModel{

	private static final long serialVersionUID = -7613753229198029244L;
	
	private @Getter @Setter String numeroInternet;
	private @Getter @Setter String numeroVentanilla;
	private @Getter @Setter String total;
	private @Getter @Setter String descripcion;
	
	
	
}
