package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the DIT_MUNICIPIOIMSS_SALARIO database table.
 * 
 */



@Entity
@Table(name="DIT_MUNICIPIOIMSS_SALARIO")
public class DitMunicipioImssSalario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_MUNICIPIOIMSS_SALARIO", nullable=false, precision=22)
	private long cveIdMunicipioImssSalario;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CVE_ID_SALARIO_GENERAL")
	private DitSalarioGeneral ditSalarioGeneral;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="CVE_ID_MUNICIPIO_IMSS")
	private DicMunicipioImss dicMunicipioImss;

    @Temporal( TemporalType.DATE)
    @Column(name="FEC_INI_VIGENCIA")
    private Date fecInicioVigencia;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FIN_VIGENCIA")
	private Date fecFinVigencia;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
    @Column(name="FEC_REGISTRO_BAJA")
    private Date fecRegistroBaja;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    public DitMunicipioImssSalario() {
    }

    public long getCveIdMunicipioImssSalario() {
        return cveIdMunicipioImssSalario;
    }

    public void setCveIdMunicipioImssSalario(long cveIdMunicipioImssSalario) {
        this.cveIdMunicipioImssSalario = cveIdMunicipioImssSalario;
    }

    public DitSalarioGeneral getDitSalarioGeneral() {
        return ditSalarioGeneral;
    }

    public void setDitSalarioGeneral(DitSalarioGeneral ditSalarioGeneral) {
        this.ditSalarioGeneral = ditSalarioGeneral;
    }

    public DicMunicipioImss getDicMunicipioImss() {
        return dicMunicipioImss;
    }

    public void setDicMunicipioImss(DicMunicipioImss dicMunicipioImss) {
        this.dicMunicipioImss = dicMunicipioImss;
    }

    public Date getFecInicioVigencia() {
        return fecInicioVigencia;
    }

    public void setFecInicioVigencia(Date fecInicioVigencia) {
        this.fecInicioVigencia = fecInicioVigencia;
    }

    public Date getFecFinVigencia() {
        return fecFinVigencia;
    }

    public void setFecFinVigencia(Date fecFinVigencia) {
        this.fecFinVigencia = fecFinVigencia;
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

    public Date getFecRegistroActualizado() {
        return fecRegistroActualizado;
    }

    public void setFecRegistroActualizado(Date fecRegistroActualizado) {
        this.fecRegistroActualizado = fecRegistroActualizado;
    }
}