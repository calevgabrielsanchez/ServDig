package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;




/**
 * The persistent class for the DIC_DETALLE_NIVEL_EDUCATIVO database table.
 * 
 */
@Entity
@Table(name="DIT_DETALLE_NIVEL_EDUCATIVO")
public class DitDetalleNivelEducativo implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -1819976983259332558L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_DETALLE_NIVEL_EDUCATIVO", nullable=false, precision=22)
	private long cveIdDetalleNivelEducativo;
	

	@ManyToOne
	@JoinColumn(name="CVE_ID_NIVEL_EDUCATIVO")
	private DicNivelEducativo dicNivelEducativo;
	
	
	@ManyToOne
	@JoinColumn(name="CVE_ID_TIPO_NIVEL_EDUCATIVO")
	private DicTipoNivelEducativo dicTipoNivelEducativo;
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	public long getCveIdDetalleNivelEducativo() {
		return cveIdDetalleNivelEducativo;
	}

	public void setCveIdDetalleNivelEducativo(long cveIdDetalleNivelEducativo) {
		this.cveIdDetalleNivelEducativo = cveIdDetalleNivelEducativo;
	}

	public DicNivelEducativo getDicNivelEducativo() {
		return dicNivelEducativo;
	}

	public void setDicNivelEducativo(DicNivelEducativo dicNivelEducativo) {
		this.dicNivelEducativo = dicNivelEducativo;
	}

	public DicTipoNivelEducativo getDicTipoNivelEducativo() {
		return dicTipoNivelEducativo;
	}

	public void setDicTipoNivelEducativo(DicTipoNivelEducativo dicTipoNivelEducativo) {
		this.dicTipoNivelEducativo = dicTipoNivelEducativo;
	}

	public Date getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroBaja() {
		return fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}
	
	
	
	
}