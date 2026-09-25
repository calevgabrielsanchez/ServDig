package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;




/**
 * The persistent class for the DIC_TIPO_NIVEL_EDUCATIVO database table.
 * 
 */
@Entity
@Table(name="DIC_TIPO_NIVEL_EDUCATIVO")
public class DicTipoNivelEducativo implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 4027253619286582808L;


	@Id
	@Column(name="CVE_ID_TIPO_NIVEL_EDUCATIVO", nullable=false)
	private long cveIdTipoNivelEducativo;
	
	
	@Column(name="DES_TIPO_NIVEL_EDUCATIVO", nullable=false,length=50)
	private String desTipoNivelEducativo;
	
	@Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;
	
    @ManyToMany
    @JoinTable(
        name="DIT_DETALLE_NIVEL_EDUCATIVO"
        , joinColumns={
            @JoinColumn(name="CVE_ID_TIPO_NIVEL_EDUCATIVO", nullable=false)
            }
        , inverseJoinColumns={
            @JoinColumn(name="CVE_ID_NIVEL_EDUCATIVO",nullable=false)
            }
        )
    private List<DicNivelEducativo> dicNivelEducativo = new ArrayList<DicNivelEducativo>();
    

	public List<DicNivelEducativo> getDicNivelEducativo() {
		return dicNivelEducativo;
	}

	public void setDicNivelEducativo(List<DicNivelEducativo> dicNivelEducativo) {
		this.dicNivelEducativo = dicNivelEducativo;
	}

	public long getCveIdTipoNivelEducativo() {
		return cveIdTipoNivelEducativo;
	}

	public void setCveIdTipoNivelEducativo(long cveIdTipoNivelEducativo) {
		this.cveIdTipoNivelEducativo = cveIdTipoNivelEducativo;
	}

	public String getDesTipoNivelEducativo() {
		return desTipoNivelEducativo;
	}

	public void setDesTipoNivelEducativo(String desTipoNivelEducativo) {
		this.desTipoNivelEducativo = desTipoNivelEducativo;
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