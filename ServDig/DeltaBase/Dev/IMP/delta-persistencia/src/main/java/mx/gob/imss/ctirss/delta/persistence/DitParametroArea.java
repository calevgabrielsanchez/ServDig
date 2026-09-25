package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the DIT_PARAMETRO_AREA database table.
 * 
 */
@Entity
@Table(name="DIT_PARAMETRO_AREA")
public class DitParametroArea implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_PARAMETRO_AREA", nullable=false, precision=22)
	private long cveIdParametroArea;

    @Lob()
	@Column(name="DES_ESTRUCTURA_PARAMETRO")
	private String desEstructuraParametro;

	@Column(name="DES_PARAMETRO_AREA", length=100)
	private String desParametroArea;

    @Lob()
	@Column(name="DES_VALOR_PARAMETRO")
	private String desValorParametro;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FIN_PARAMETRO")
	private Date fecFinParametro;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_INICIO_PARAMETRO")
	private Date fecInicioParametro;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DicAreaSistema
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_AREA_SISTEMA")
	private DicAreaSistema dicAreaSistema;

    public DitParametroArea() {
    }

	public long getCveIdParametroArea() {
		return this.cveIdParametroArea;
	}

	public void setCveIdParametroArea(long cveIdParametroArea) {
		this.cveIdParametroArea = cveIdParametroArea;
	}

	public String getDesEstructuraParametro() {
		return this.desEstructuraParametro;
	}

	public void setDesEstructuraParametro(String desEstructuraParametro) {
		this.desEstructuraParametro = desEstructuraParametro;
	}

	public String getDesParametroArea() {
		return this.desParametroArea;
	}

	public void setDesParametroArea(String desParametroArea) {
		this.desParametroArea = desParametroArea;
	}

	public String getDesValorParametro() {
		return this.desValorParametro;
	}

	public void setDesValorParametro(String desValorParametro) {
		this.desValorParametro = desValorParametro;
	}

	public Date getFecFinParametro() {
		return this.fecFinParametro;
	}

	public void setFecFinParametro(Date fecFinParametro) {
		this.fecFinParametro = fecFinParametro;
	}

	public Date getFecInicioParametro() {
		return this.fecInicioParametro;
	}

	public void setFecInicioParametro(Date fecInicioParametro) {
		this.fecInicioParametro = fecInicioParametro;
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

	public DicAreaSistema getDicAreaSistema() {
		return this.dicAreaSistema;
	}

	public void setDicAreaSistema(DicAreaSistema dicAreaSistema) {
		this.dicAreaSistema = dicAreaSistema;
	}
	
}