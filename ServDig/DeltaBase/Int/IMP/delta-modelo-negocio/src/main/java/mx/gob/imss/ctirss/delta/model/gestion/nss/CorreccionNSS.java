/**
 * 
 */
package mx.gob.imss.ctirss.delta.model.gestion.nss;


import java.io.Serializable;
import java.util.List;
import mx.gob.imss.ctirss.delta.model.gestion.documento.probatorio.DocumentoProbatorio;

/**
 * @author Lucio Duran Silva
 *
 */
public class CorreccionNSS implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8063619841578554604L;
	private Long claveDetalleNssCda;
	private String nss;
	private List<DocumentoProbatorio> documentosProbatorios;
	private Long origen; 
	private Long idTipoNss;
	private String observaciones;
	
	
	private List<MovAclaracionNss> listaAclaraciones;

	public List<DocumentoProbatorio> getDocumentosProbatorios() {
		return documentosProbatorios;
	}

	public void setDocumentosProbatorios(
			List<DocumentoProbatorio> documentosProbatorios) {
		this.documentosProbatorios = documentosProbatorios;
	}
	
	
	public Long getOrigen() {
		return origen;
	}

	
	public void setOrigen(Long origen) {
		this.origen = origen;
	}
	
	public Long getClaveDetalleNssCda() {
		return claveDetalleNssCda;
	}

	
	public void setClaveDetalleNssCda(Long claveDetalleNssCda) {
		this.claveDetalleNssCda = claveDetalleNssCda;
	}
	
	public String getNss() {
		return nss;
	}

	
	public void setNss(String nss) {
		this.nss = nss;
	}

	public Long getIdTipoNss() {
		return idTipoNss;
	}

	public void setIdTipoNss(Long idTipoNss) {
		this.idTipoNss = idTipoNss;
	}

	public List<MovAclaracionNss> getListaAclaraciones() {
		return listaAclaraciones;
	}

	public void setListaAclaraciones(List<MovAclaracionNss> listaAclaraciones) {
		this.listaAclaraciones = listaAclaraciones;
	}

	public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
	
}
