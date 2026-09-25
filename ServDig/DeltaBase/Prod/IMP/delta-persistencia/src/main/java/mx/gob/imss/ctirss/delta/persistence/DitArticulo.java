package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;

/**
 * The persistent class for the DIT_ARTICULO database table.
 * 
 */
@Entity
@Table(name="DIT_ARTICULO")
public class DitArticulo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "DIT_ARTICULO_GENERATOR", sequenceName = "SEQ_DITARTICULO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_ARTICULO_GENERATOR")
	@Column(name="CVE_ID_ARTICULO")
	private long cveIdArticulo;
	
	@Column(name="DES_FRACCION")
	private String desFraccion;

	@Column(name="DES_INCISO")
	private String desInciso;

	@Column(name="NUM_ARTICULO")
	private BigDecimal numArticulo;

	//bi-directional many-to-one association to DitDatosClem
    @ManyToOne
	@JoinColumn(name="CVE_ID_CLEM")
	private DitDatosClem ditDatosClem;
    
	public long getCveIdArticulo() {
		return this.cveIdArticulo;
	}

	public void setCveIdArticulo(long cveIdArticulo) {
		this.cveIdArticulo = cveIdArticulo;
	}

	public String getDesFraccion() {
		return this.desFraccion;
	}

	public void setDesFraccion(String desFraccion) {
		this.desFraccion = desFraccion;
	}

	public String getDesInciso() {
		return this.desInciso;
	}

	public void setDesInciso(String desInciso) {
		this.desInciso = desInciso;
	}

	public BigDecimal getNumArticulo() {
		return this.numArticulo;
	}

	public void setNumArticulo(BigDecimal numArticulo) {
		this.numArticulo = numArticulo;
	}

	public DitDatosClem getDitDatosClem() {
		return this.ditDatosClem;
	}

	public void setDitDatosClem(DitDatosClem ditDatosClem) {
		this.ditDatosClem = ditDatosClem;
	}
	
}