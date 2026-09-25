package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({

@NamedQuery(name = "DitMovAclaracionNssCda.existeMovimientosOperadosPorSolicitud", query = "SELECT count(1) FROM DitMovAclaracionNssCda A where A.cveIdEstadoMovSindo = 3 "
		+ "and A.cveIdDetalleNssCda.correccionDatosAsegurado.tramite.ditSolicitud.cveIdSolicitud = :idSolicitud "),
@NamedQuery(name = "DitMovAclaracionNssCda.movimientosDatosBasicosPorSolicitud", query = "SELECT A FROM DitMovAclaracionNssCda A where A.cveIdEstadoMovSindo = 3 "
		+ "and A.cveIdTipoTramCorrecNss.cveIdTipoTramCorrecNss = 2 "
		+ "and A.cveIdDetalleNssCda.correccionDatosAsegurado.tramite.ditSolicitud.cveIdSolicitud = :idSolicitud ") })
@Table(name = "DIT_MOV_ACLARACION_NSS_CDA")
public class DitMovAclaracionNssCda implements Serializable {
	private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "SEQDITMOVACLARACIONNSSCDA", sequenceName = "SEQDITMOVACLARACIONNSSCDA")
    @GeneratedValue(generator = "SEQDITMOVACLARACIONNSSCDA")
	@Column(name = "CVE_ID_MOV_ACLARACION_NSS")
	private Long cveIdMovAclaracionNss;

	@JoinColumn(name = "CVE_ID_TIPO_NSS_ACLARACION", referencedColumnName = "CVE_ID_TIPO_NSS_ACLARACION")
    @ManyToOne(fetch = FetchType.LAZY)
	private DicTipoNssAclaracion cveIdTipoNssAclaracion;

	@JoinColumn(name = "CVE_ID_DETALLE_NSS_CDA", referencedColumnName = "CVE_ID_DETALLE_NSS_CDA")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
	private DitDetalleNss cveIdDetalleNssCda;
		
	//@JoinColumn(name = "CVE_ID_ESTADO_MOV_ENV_SINDO", referencedColumnName = "CVE_ID_ESTADO_MOV_ENV_SINDO")
  //@ManyToOne(optional = true, fetch = FetchType.LAZY)
  //@Transient
  @Column(name = "CVE_ID_ESTADO_MOV_ENV_SINDO")
	private Long cveIdEstadoMovSindo;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ALTA", nullable=false)
	private Date fecRegistroAlta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_MOV_ENV_SINDO")
	private Date fecMovEnvSindo;

	@JoinColumn(name = "CVE_ID_TIPO_TRAM_CORREC_NSS", referencedColumnName = "CVE_ID_TIPO_TRAM_CORREC_NSS")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
	private DicTipoTramCorreccionNss cveIdTipoTramCorrecNss;

	@Column(name = "IND_ENV_SINDO")
	private Character indEnvSindo;

	@Column(name = "IND_MOV_SISTEMA")
	private Character indMovSistema;

    public Long getCveIdMovAclaracionNss() {
        return cveIdMovAclaracionNss;
    }

    public void setCveIdMovAclaracionNss(Long cveIdMovAclaracionNss) {
        this.cveIdMovAclaracionNss = cveIdMovAclaracionNss;
    }

    public DitDetalleNss getCveIdDetalleNssCda() {
        return cveIdDetalleNssCda;
    }

    public void setCveIdDetalleNssCda(DitDetalleNss cveIdDetalleNssCda) {
        this.cveIdDetalleNssCda = cveIdDetalleNssCda;
    }

    public Long getCveIdEstadoMovSindo() {
        return cveIdEstadoMovSindo;
    }

    public void setCveIdEstadoMovSindo(Long cveIdEstadoMovSindo) {
        this.cveIdEstadoMovSindo = cveIdEstadoMovSindo;
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

    public Date getFecMovEnvSindo() {
        return fecMovEnvSindo;
    }

    public void setFecMovEnvSindo(Date fecMovEnvSindo) {
        this.fecMovEnvSindo = fecMovEnvSindo;
    }

    public DicTipoTramCorreccionNss getCveIdTipoTramCorrecNss() {
        return cveIdTipoTramCorrecNss;
    }

    public void setCveIdTipoTramCorrecNss(
            DicTipoTramCorreccionNss cveIdTipoTramCorrecNss) {
        this.cveIdTipoTramCorrecNss = cveIdTipoTramCorrecNss;
    }

    public DicTipoNssAclaracion getCveIdTipoNssAclaracion() {
        return cveIdTipoNssAclaracion;
    }

    public void setCveIdTipoNssAclaracion(
            DicTipoNssAclaracion cveIdTipoNssAclaracion) {
        this.cveIdTipoNssAclaracion = cveIdTipoNssAclaracion;
    }

	public Character getIndEnvSindo() {
		return indEnvSindo;
	}

	public void setIndEnvSindo(Character indEnvSindo) {
		this.indEnvSindo = indEnvSindo;
	}

	public Character getIndMovSistema() {
		return indMovSistema;
	}

	public void setIndMovSistema(Character indMovSistema) {
		this.indMovSistema = indMovSistema;
	}

}
