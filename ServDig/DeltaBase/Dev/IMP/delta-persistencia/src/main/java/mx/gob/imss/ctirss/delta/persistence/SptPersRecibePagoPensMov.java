package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * The persistent class for the SPT_PERS_RECIBE_PAGO_PENS_MOV database table.
 * 
 */
@Entity
@Table(name="SPT_PERS_RECIBE_PAGO_PENS_MOV")
@NamedQuery(name = "SptPersRecibePagoPensMov.findAll", query = "SELECT s FROM SptPersRecibePagoPensMov s")
public class SptPersRecibePagoPensMov implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "SPT_PERSRECIBEPAGOPENSMOV", sequenceName = "SPT_PERSRECIBEPAGOPENSMOV")
	@GeneratedValue(generator = "SPT_PERSRECIBEPAGOPENSMOV")
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

	@Column(name="ID_FECHA_MODIFICACION")
	private Timestamp idFechaModificacion;

	@Column(name="ID_GRUPO_FAMILIAR")
	private String idGrupoFamiliar;

	@Column(name="ID_NSS")
	private String idNss;

	@Column(name="IND_DERECHO_SERV_MED")
	private String indDerechoServMed;

	//bi-directional many-to-one association to SptPersRecibePagoPen
    @ManyToOne
	@JoinColumn(name="CVE_ID_PERS_RECIBE_PAGO_PENS")
	private SptPersRecibePagoPen sptPersRecibePagoPen;

    public SptPersRecibePagoPensMov() {
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

	public SptPersRecibePagoPen getSptPersRecibePagoPen() {
		return this.sptPersRecibePagoPen;
	}

	public void setSptPersRecibePagoPen(SptPersRecibePagoPen sptPersRecibePagoPen) {
		this.sptPersRecibePagoPen = sptPersRecibePagoPen;
	}
	
}