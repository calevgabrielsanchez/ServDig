package mx.gob.imss.ctirss.delta.model.asegurado.cda;

import lombok.Getter;
import lombok.Setter;

public class ConteoReporteCDA extends BaseModel {

	private static final long serialVersionUID = 5893780217145590281L;
	
	private @Getter @Setter String descripcion;
	private @Getter @Setter String total;
}
