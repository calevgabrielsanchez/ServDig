package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="DIT_TRAMITE_PAT_SUJ_OBLIGADO")
public class DitTramitePatSujObligado implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	
	@EmbeddedId
	private DitTramitePatSujObligadoPK id;
	
	//bi-directional many-to-one association to DicEstadoTramite
    @ManyToOne
	@JoinColumn(name="CVE_ID_PATRON_SUJETO_OBLIGADO",insertable=false,updatable=false)
	private DitPatronSujetoObligado ditPatronSujetoObligado;
    
    @ManyToOne
    @JoinColumn(name="CVE_ID_TRAMITE",insertable=false,updatable=false)
    private DitTramite ditTramite;

	public DitTramite getDitTramite() {
		return ditTramite;
	}


	public void setDitTramite(DitTramite ditTramite) {
		this.ditTramite = ditTramite;
	}


	public DitPatronSujetoObligado getDitPatronSujetoObligado() {
		return ditPatronSujetoObligado;
	}

	public void setDitPatronSujetoObligado(
			DitPatronSujetoObligado ditPatronSujetoObligado) {
		this.ditPatronSujetoObligado = ditPatronSujetoObligado;
	}
	
	public DitTramitePatSujObligadoPK getId() {
		return id;
	}


	public void setId(DitTramitePatSujObligadoPK id) {
		this.id = id;
	}	
	
}