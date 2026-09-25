package mx.gob.imss.ctirss.delta.model.asegurado.cda;

import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


/**
 * Informacion que clasifica el NSS
 * y contiene los datos de cada cubeta 
 *  
 * @author erik.ramirez
 *
 */
public class TramiteNss extends BaseModel {
	private @Getter @Setter static final long serialVersionUID = -6856001758521231457L;
	private @Getter @Setter Long idTramite;
	private @Getter @Setter String nss;
	private @Getter @Setter Long idCorreccion;
	private @Getter @Setter Long idDetalle;
	private @Getter @Setter Long idTipoNss;
	private @Getter @Setter boolean convencional;
	private @Getter @Setter TipoNss tipoNss;
	private @Getter @Setter String origen;
	private @Getter @Setter TipoAclaracion tipoAclaracion;
	private @Getter @Setter OrigenInformacion canase;
	private @Getter @Setter OrigenInformacion cizUno;
	private @Getter @Setter OrigenInformacion cizDos;
	private @Getter @Setter OrigenInformacion cizTres;
	private @Getter @Setter OrigenInformacion historico;
	private @Getter @Setter OrigenInformacion bdtu;
	private @Getter @Setter List<TipoNSSCorreccion> listaAclaraciones;
}

	