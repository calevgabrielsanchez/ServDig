package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the DIC_SERIES_NSS database table.
 * 
 */
@Entity
@Table(name = "DIC_SERIES_NSS")
public class DicSeriesNss implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "DIC_SERIES_NSS_CVEIDSERIE_GENERATOR", sequenceName = "SEQ_DICSERIESNSS")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIC_SERIES_NSS_CVEIDSERIE_GENERATOR")
    @Column(name = "CVE_ID_SERIE")
    private long cveIdSerie;

    @Temporal(TemporalType.DATE)
    @Column(name = "FEC_REGISTRO_ACTUALIZADO")
    private Date fecRegistroActualizado;

    @Temporal(TemporalType.DATE)
    @Column(name = "FEC_REGISTRO_ALTA")
    private Date fecRegistroAlta;

    @Temporal(TemporalType.DATE)
    @Column(name = "FEC_REGISTRO_BAJA")
    private Date fecRegistroBaja;

    @Column(name = "NUM_ANIO_REGISTRO")
    private BigDecimal numAnioRegistro;

    @Column(name = "NUM_SERIE")
    private BigDecimal numSerie;

    //bi-directional many-to-one association to DicTipoSerie
    @ManyToOne
    @JoinColumn(name = "CVE_ID_TIPO_SERIE")
    private DicTipoSerie dicTipoSerie;

    //bi-directional many-to-one association to DitAsignacionSery
    @OneToMany(mappedBy = "dicSeriesNss")
    private List<DitAsignacionSerie> ditAsignacionSeries;

    //bi-directional many-to-one association to DicFolioNss
    @OneToMany(mappedBy = "dicSeriesNss")
    private List<DicFolioNss> dicFolioNsses;

    public DicSeriesNss() {
    }

    public long getCveIdSerie() {
        return this.cveIdSerie;
    }

    public void setCveIdSerie(long cveIdSerie) {
        this.cveIdSerie = cveIdSerie;
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

    public BigDecimal getNumAnioRegistro() {
        return this.numAnioRegistro;
    }

    public void setNumAnioRegistro(BigDecimal numAnioRegistro) {
        this.numAnioRegistro = numAnioRegistro;
    }

    public BigDecimal getNumSerie() {
        return this.numSerie;
    }

    public void setNumSerie(BigDecimal numSerie) {
        this.numSerie = numSerie;
    }

    public DicTipoSerie getDicTipoSerie() {
        return this.dicTipoSerie;
    }

    public void setDicTipoSerie(DicTipoSerie dicTipoSerie) {
        this.dicTipoSerie = dicTipoSerie;
    }

    public List<DitAsignacionSerie> getDitAsignacionSeries() {
        return this.ditAsignacionSeries;
    }

    public void setDitAsignacionSeries(List<DitAsignacionSerie> ditAsignacionSeries) {
        this.ditAsignacionSeries = ditAsignacionSeries;
    }

    public List<DicFolioNss> getDicFolioNsses() {
        return this.dicFolioNsses;
    }

    public void setDicFolioNsses(List<DicFolioNss> dicFolioNsses) {
        this.dicFolioNsses = dicFolioNsses;
    }

}