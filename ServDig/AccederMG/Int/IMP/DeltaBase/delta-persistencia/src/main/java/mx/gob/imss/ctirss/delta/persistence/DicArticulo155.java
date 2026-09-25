package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the DIC_ARTICULO_155 database table.
 * 
 */
@Entity
@Table(name="DIC_ARTICULO_155")
public class DicArticulo155 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_ARTICULO_155")
	private long cveIdArticulo155;

	@Column(name="DES_FRACCION")
	private String desFraccion;

	@Column(name="DES_INCISO")
	private String desInciso;

	//bi-directional many-to-one association to DicSubdelegacion
    @ManyToOne
	@JoinColumn(name="CVE_ID_SUBDELEGACION")
	private DicSubdelegacion dicSubdelegacion;

    public DicArticulo155() {
    }

	public long getCveIdArticulo155() {
		return this.cveIdArticulo155;
	}

	public void setCveIdArticulo155(long cveIdArticulo155) {
		this.cveIdArticulo155 = cveIdArticulo155;
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

	public DicSubdelegacion getDicSubdelegacion() {
		return this.dicSubdelegacion;
	}

	public void setDicSubdelegacion(DicSubdelegacion dicSubdelegacion) {
		this.dicSubdelegacion = dicSubdelegacion;
	}
	
}