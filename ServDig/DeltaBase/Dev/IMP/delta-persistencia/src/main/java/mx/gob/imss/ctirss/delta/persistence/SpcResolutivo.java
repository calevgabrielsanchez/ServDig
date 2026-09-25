package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the SPC_RESOLUTIVO database table.
 * 
 */
@Entity
@Table(name="SPC_RESOLUTIVO")
@NamedQuery(name="SpcResolutivo.findAll", query="SELECT s FROM SpcResolutivo s")
public class SpcResolutivo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SpcResolutivoPK id;

	@Column(name="DES_RESOLUTIVO")
	private String desResolutivo;

	@Column(name="ID_ARTICULO_167")
	private String idArticulo167;

	@Column(name="IND_MODIFICACION")
	private String indModificacion;

	@Column(name="IND_PAGO_INICIAL")
	private String indPagoInicial;

	@Column(name="IND_REGIMEN_73")
	private String indRegimen73;

	@Column(name="IND_REGIMEN_97")
	private String indRegimen97;

	@Column(name="IND_RETIRO_TOTAL")
	private String indRetiroTotal;

	//bi-directional many-to-one association to SpcRegimen
	@ManyToOne
	@JoinColumn(name="ID_REGIMEN",insertable=false, updatable=false)
	private SpcRegimen spcRegimen;

	//bi-directional many-to-one association to SptResolutivosResolucion
	@OneToMany(mappedBy="spcResolutivo")
	private List<SptResolutivosResolucion> sptResolutivosResolucions;

	public SpcResolutivo() {
	}

	public SpcResolutivoPK getId() {
		return this.id;
	}

	public void setId(SpcResolutivoPK id) {
		this.id = id;
	}

	public String getDesResolutivo() {
		return this.desResolutivo;
	}

	public void setDesResolutivo(String desResolutivo) {
		this.desResolutivo = desResolutivo;
	}

	public String getIdArticulo167() {
		return this.idArticulo167;
	}

	public void setIdArticulo167(String idArticulo167) {
		this.idArticulo167 = idArticulo167;
	}

	public String getIndModificacion() {
		return this.indModificacion;
	}

	public void setIndModificacion(String indModificacion) {
		this.indModificacion = indModificacion;
	}

	public String getIndPagoInicial() {
		return this.indPagoInicial;
	}

	public void setIndPagoInicial(String indPagoInicial) {
		this.indPagoInicial = indPagoInicial;
	}

	public String getIndRegimen73() {
		return this.indRegimen73;
	}

	public void setIndRegimen73(String indRegimen73) {
		this.indRegimen73 = indRegimen73;
	}

	public String getIndRegimen97() {
		return this.indRegimen97;
	}

	public void setIndRegimen97(String indRegimen97) {
		this.indRegimen97 = indRegimen97;
	}

	public String getIndRetiroTotal() {
		return this.indRetiroTotal;
	}

	public void setIndRetiroTotal(String indRetiroTotal) {
		this.indRetiroTotal = indRetiroTotal;
	}

	public SpcRegimen getSpcRegimen() {
		return this.spcRegimen;
	}

	public void setSpcRegimen(SpcRegimen spcRegimen) {
		this.spcRegimen = spcRegimen;
	}

	public List<SptResolutivosResolucion> getSptResolutivosResolucions() {
		return this.sptResolutivosResolucions;
	}

	public void setSptResolutivosResolucions(List<SptResolutivosResolucion> sptResolutivosResolucions) {
		this.sptResolutivosResolucions = sptResolutivosResolucions;
	}

	public SptResolutivosResolucion addSptResolutivosResolucion(SptResolutivosResolucion sptResolutivosResolucion) {
		getSptResolutivosResolucions().add(sptResolutivosResolucion);
		sptResolutivosResolucion.setSpcResolutivo(this);

		return sptResolutivosResolucion;
	}

	public SptResolutivosResolucion removeSptResolutivosResolucion(SptResolutivosResolucion sptResolutivosResolucion) {
		getSptResolutivosResolucions().remove(sptResolutivosResolucion);
		sptResolutivosResolucion.setSpcResolutivo(null);

		return sptResolutivosResolucion;
	}

}