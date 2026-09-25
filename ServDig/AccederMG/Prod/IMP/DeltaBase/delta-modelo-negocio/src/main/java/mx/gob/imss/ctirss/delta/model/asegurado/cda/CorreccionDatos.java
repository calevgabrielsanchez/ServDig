package mx.gob.imss.ctirss.delta.model.asegurado.cda;

import java.util.List;
import lombok.Getter;
import lombok.Setter;


/**
 * Objeto para informacion general
 * de la correcion de datos y consulta del asegurado
 * 
 * @author erik.ramirez
 *
 */
public class CorreccionDatos extends BaseModel{
	private @Getter @Setter static final long serialVersionUID = 4843265250271641426L;
	private @Getter @Setter String folioSolicitud;
	private @Getter @Setter String nssCertificador;
	private @Getter @Setter OrigenInformacion renapo;	
	private @Getter @Setter List<TramiteNss> listaNss;	
	private @Getter @Setter String usuarioCorreccion;
}
