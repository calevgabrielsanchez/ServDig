package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.delta.persistence.SpcTipoMovimiento;
import mx.gob.imss.ctirss.delta.persistence.SptTramitePension;

import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the SPT_TITULAR_GRUPO_MOV database table.
 * 
 */
@Entity
@Table(name="SPT_TITULAR_GRUPO_MOV")
@NamedQuery(name="SptTitularGrupoMov.findAll", query="SELECT s FROM SptTitularGrupoMov s")
public class SptTitularGrupoMov implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SEQ_SPTTITULARGRUPOMOV", sequenceName = "SEQ_SPTTITULARGRUPOMOV")
	@GeneratedValue(generator = "SEQ_SPTTITULARGRUPOMOV")
	@Column(name="CVE_ID_TITULAR_GRUPO_MOV")
	private long cveIdTitularGrupoMov;

	@Column(name="CVE_CTA_PAGO")
	private String cveCtaPago;

	@Column(name="CVE_CTO_COSTO")
	private String cveCtoCosto;

	@Column(name="CVE_DELEGACION")
	private String cveDelegacion;

	@Column(name="CVE_DELEGACION_ORIGEN")
	private String cveDelegacionOrigen;

	@Column(name="CVE_ENTIDAD_PAGO")
	private String cveEntidadPago;

	@Column(name="CVE_LUGAR_PAGO")
	private String cveLugarPago;

	@Column(name="CVE_PERIODO_NOMINA")
	private BigDecimal cvePeriodoNomina;

	@Column(name="CVE_PERIODO_PAGO")
	private String cvePeriodoPago;

	@Column(name="CVE_PREI")
	private String cvePrei;

	@Column(name="CVE_SUBDELEGACION")
	private String cveSubdelegacion;

	@Column(name="CVE_SUCURSAL_CTA_PAGO")
	private String cveSucursalCtaPago;

	@Column(name="CVE_USUARIO")
	private String cveUsuario;

	@Column(name="DES_TIPO_CTA_PAGO")
	private String desTipoCtaPago;

	@Column(name="DOM_CALLE_NUMERO")
	private String domCalleNumero;

	@Column(name="DOM_COLONIA")
	private String domColonia;

	@Column(name="DOM_CP")
	private String domCp;

	@Column(name="DOM_MUNICIPIO_DELEGACION")
	private String domMunicipioDelegacion;

	@Column(name="ID_FECHA_MODIFICACION")
	private Timestamp idFechaModificacion;

	@Column(name="ID_GRUPO_FAMILIAR")
	private String idGrupoFamiliar;

	@Column(name="ID_NSS")
	private String idNss;

	@Column(name="IND_DERECHO_SERV_MED")
	private String indDerechoServMed;

	@Column(name="NOM_APELLIDO_MATERNO")
	private String nomApellidoMaterno;

	@Column(name="NOM_APELLIDO_PATERNO")
	private String nomApellidoPaterno;

	@Column(name="NOM_NOMBRE")
	private String nomNombre;

	//bi-directional many-to-one association to SpcTipoMovimiento
    @ManyToOne
	@JoinColumn(name="ID_TIPO_MOVIMIENTO")
	private SpcTipoMovimiento spcTipoMovimiento;

	//bi-directional many-to-one association to SptTramitePension
    @ManyToOne
	@JoinColumn(name="CVE_ID_TRAMITE_PENSION")
	private SptTramitePension sptTramitePension;

    public SptTitularGrupoMov() {
    }

	public long getCveIdTitularGrupoMov() {
		return this.cveIdTitularGrupoMov;
	}

	public void setCveIdTitularGrupoMov(long cveIdTitularGrupoMov) {
		this.cveIdTitularGrupoMov = cveIdTitularGrupoMov;
	}

	public String getCveCtaPago() {
		return this.cveCtaPago;
	}

	public void setCveCtaPago(String cveCtaPago) {
		this.cveCtaPago = cveCtaPago;
	}

	public String getCveCtoCosto() {
		return this.cveCtoCosto;
	}

	public void setCveCtoCosto(String cveCtoCosto) {
		this.cveCtoCosto = cveCtoCosto;
	}

	public String getCveDelegacion() {
		return this.cveDelegacion;
	}

	public void setCveDelegacion(String cveDelegacion) {
		this.cveDelegacion = cveDelegacion;
	}

	public String getCveDelegacionOrigen() {
		return this.cveDelegacionOrigen;
	}

	public void setCveDelegacionOrigen(String cveDelegacionOrigen) {
		this.cveDelegacionOrigen = cveDelegacionOrigen;
	}

	public String getCveEntidadPago() {
		return this.cveEntidadPago;
	}

	public void setCveEntidadPago(String cveEntidadPago) {
		this.cveEntidadPago = cveEntidadPago;
	}

	public String getCveLugarPago() {
		return this.cveLugarPago;
	}

	public void setCveLugarPago(String cveLugarPago) {
		this.cveLugarPago = cveLugarPago;
	}

	public BigDecimal getCvePeriodoNomina() {
		return this.cvePeriodoNomina;
	}

	public void setCvePeriodoNomina(BigDecimal cvePeriodoNomina) {
		this.cvePeriodoNomina = cvePeriodoNomina;
	}

	public String getCvePeriodoPago() {
		return this.cvePeriodoPago;
	}

	public void setCvePeriodoPago(String cvePeriodoPago) {
		this.cvePeriodoPago = cvePeriodoPago;
	}

	public String getCvePrei() {
		return this.cvePrei;
	}

	public void setCvePrei(String cvePrei) {
		this.cvePrei = cvePrei;
	}

	public String getCveSubdelegacion() {
		return this.cveSubdelegacion;
	}

	public void setCveSubdelegacion(String cveSubdelegacion) {
		this.cveSubdelegacion = cveSubdelegacion;
	}

	public String getCveSucursalCtaPago() {
		return this.cveSucursalCtaPago;
	}

	public void setCveSucursalCtaPago(String cveSucursalCtaPago) {
		this.cveSucursalCtaPago = cveSucursalCtaPago;
	}

	public String getCveUsuario() {
		return this.cveUsuario;
	}

	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}

	public String getDesTipoCtaPago() {
		return this.desTipoCtaPago;
	}

	public void setDesTipoCtaPago(String desTipoCtaPago) {
		this.desTipoCtaPago = desTipoCtaPago;
	}

	public String getDomCalleNumero() {
		return this.domCalleNumero;
	}

	public void setDomCalleNumero(String domCalleNumero) {
		this.domCalleNumero = domCalleNumero;
	}

	public String getDomColonia() {
		return this.domColonia;
	}

	public void setDomColonia(String domColonia) {
		this.domColonia = domColonia;
	}

	public String getDomCp() {
		return this.domCp;
	}

	public void setDomCp(String domCp) {
		this.domCp = domCp;
	}

	public String getDomMunicipioDelegacion() {
		return this.domMunicipioDelegacion;
	}

	public void setDomMunicipioDelegacion(String domMunicipioDelegacion) {
		this.domMunicipioDelegacion = domMunicipioDelegacion;
	}

	public Timestamp getIdFechaModificacion() {
		return this.idFechaModificacion;
	}

	public void setIdFechaModificacion(Timestamp idFechaModificacion) {
		this.idFechaModificacion = idFechaModificacion;
	}

	public String getIdGrupoFamiliar() {
		return this.idGrupoFamiliar;
	}

	public void setIdGrupoFamiliar(String idGrupoFamiliar) {
		this.idGrupoFamiliar = idGrupoFamiliar;
	}

	public String getIdNss() {
		return this.idNss;
	}

	public void setIdNss(String idNss) {
		this.idNss = idNss;
	}

	public String getIndDerechoServMed() {
		return this.indDerechoServMed;
	}

	public void setIndDerechoServMed(String indDerechoServMed) {
		this.indDerechoServMed = indDerechoServMed;
	}

	public String getNomApellidoMaterno() {
		return this.nomApellidoMaterno;
	}

	public void setNomApellidoMaterno(String nomApellidoMaterno) {
		this.nomApellidoMaterno = nomApellidoMaterno;
	}

	public String getNomApellidoPaterno() {
		return this.nomApellidoPaterno;
	}

	public void setNomApellidoPaterno(String nomApellidoPaterno) {
		this.nomApellidoPaterno = nomApellidoPaterno;
	}

	public String getNomNombre() {
		return this.nomNombre;
	}

	public void setNomNombre(String nomNombre) {
		this.nomNombre = nomNombre;
	}

	public SpcTipoMovimiento getSpcTipoMovimiento() {
		return this.spcTipoMovimiento;
	}

	public void setSpcTipoMovimiento(SpcTipoMovimiento spcTipoMovimiento) {
		this.spcTipoMovimiento = spcTipoMovimiento;
	}
	
	public SptTramitePension getSptTramitePension() {
		return this.sptTramitePension;
	}

	public void setSptTramitePension(SptTramitePension sptTramitePension) {
		this.sptTramitePension = sptTramitePension;
	}
	
}