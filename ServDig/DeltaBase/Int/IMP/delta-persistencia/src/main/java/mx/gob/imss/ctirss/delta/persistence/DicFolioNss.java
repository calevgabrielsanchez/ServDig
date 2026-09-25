package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the DIC_FOLIO_NSS database table.
 * 
 */
@Entity
@Table(name = "DIC_FOLIO_NSS")
public class DicFolioNss implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private DicFolioNssPK id;

    @Temporal( TemporalType.TIMESTAMP)
    @Column(name = "FEC_REGISTRO_ACTUALIZADO")
    private Date fecRegistroActualizado;

    @Temporal( TemporalType.TIMESTAMP)
    @Column(name = "FEC_REGISTRO_ALTA")
    private Date fecRegistroAlta;

    @Temporal( TemporalType.TIMESTAMP)
    @Column(name = "FEC_REGISTRO_BAJA")
    private Date fecRegistroBaja;

    @Column(name = "NUM_FOLIO")
    private BigDecimal numFolio;
    
	@Column(name = "NOM_SECUENCIA_NSS", length = 20)
	private String nomSecuenciaNss;

    //bi-directional many-to-one association to DicSeriesNss
    @ManyToOne
    @JoinColumn(name = "CVE_ID_SERIE", insertable = false, updatable = false)
    private DicSeriesNss dicSeriesNss;

    public DicFolioNss() {
    }

    public DicFolioNssPK getId() {
        return this.id;
    }

    public void setId(DicFolioNssPK id) {
        this.id = id;
    }

    public Date getFecRegistroActualizado() {
        return this.fecRegistroActualizado;
    }

    public void setFecRegistroActualizado(Date fecRegistroActualizado) {
        this.fecRegistroActualizado = fecRegistroActualizado;
    }

    public Date getFecRegistroAlta() {
        return this.fecRegistroAlta;
    }

    public void setFecRegistroAlta(Date fecRegistroAlta) {
        this.fecRegistroAlta = fecRegistroAlta;
    }

    public Date getFecRegistroBaja() {
        return this.fecRegistroBaja;
    }

    public void setFecRegistroBaja(Date fecRegistroBaja) {
        this.fecRegistroBaja = fecRegistroBaja;
    }

    public BigDecimal getNumFolio() {
        return this.numFolio;
    }

    public void setNumFolio(BigDecimal numFolio) {
        this.numFolio = numFolio;
    }

    public DicSeriesNss getDicSeriesNss() {
        return this.dicSeriesNss;
    }

    public void setDicSeriesNss(DicSeriesNss dicSeriesNss) {
        this.dicSeriesNss = dicSeriesNss;
    }

	public String getNomSecuenciaNss() {
		return nomSecuenciaNss;
	}

	public void setNomSecuenciaNss(String nomSecuenciaNss) {
		this.nomSecuenciaNss = nomSecuenciaNss;
	}

}