package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIT_MOVIMIENTO_ASEGURADO database table.
 * 
 */
//TODO se quitan las relaciones contra cabeza de grupo familiar
@NamedQueries({
	@NamedQuery(name = "ultimoSalarioBaja", 
			query = "select ase "+ 
"from DitMovimientoAsegurado ase, DicTipoMovtoAsegurado tipo, DitAsegurado aseg, "+
//"    DitCabezaGrupoFamiliar cab, " +
"DitMovtoAsegAbierto SAL "+
"where  aseg.ditAsignacionNss.cveIdAsignacionNss =:idAsignacionNSS "+
"and ase.dicTipoMovtoAsegurado.cveIdTipoMovtoAsegurado = tipo.cveIdTipoMovtoAsegurado "+
					"and aseg.cveIdAsegurado = ase.ditAsegurado.cveIdAsegurado "+
					//"and aseg.ditAsignacionNss.cveIdAsignacionNss  = cab.cveIdAsignacionNss "+
					//"and aseg.ditPatronSujetoObligado.cveIdPatronSujetoObligado = cab.ditPatronSujetoObligado.cveIdPatronSujetoObligado "+
					"and ase.dicTipoMovtoAsegurado.cveIdTipoMovtoAsegurado IN (1,7,8) "+
					"and ase.cveIdMovimientoAsegurado = SAL.cveIdMovimientoAsegurado "+ 
					"order by ase.fecMovimiento desc"					
)
})


@Entity
@Table(name="DIT_MOVIMIENTO_ASEGURADO")
public class DitMovimientoAsegurado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_MOVIMIENTO_ASEGURADO", nullable=false, precision=22)
	private long cveIdMovimientoAsegurado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_MOVIMIENTO")
	private Date fecMovimiento;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="IND_EXTEMPORANEO", length=50)
	private String indExtemporaneo;

	//bi-directional many-to-one association to DitMovasegAjuste
	@OneToMany(mappedBy="ditMovimientoAsegurado")
	private List<DitMovasegAjuste> ditMovasegAjustes;

	//bi-directional many-to-one association to DitAsegurado
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_ASEGURADO")
	private DitAsegurado ditAsegurado;

	//bi-directional many-to-one association to DitMovimientoAsegurado
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_MOVTO_ASEGURADO_ORIG")
	private DitMovimientoAsegurado ditMovimientoAsegurado1;

	//bi-directional many-to-one association to DitMovimientoAsegurado
	@OneToMany(mappedBy="ditMovimientoAsegurado1")
	private List<DitMovimientoAsegurado> ditMovimientoAsegurados1;

	//bi-directional many-to-one association to DitMovimientoAsegurado
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_MOVTO_ASEGURADO_REEMP")
	private DitMovimientoAsegurado ditMovimientoAsegurado2;

	//bi-directional many-to-one association to DitMovimientoAsegurado
	@OneToMany(mappedBy="ditMovimientoAsegurado2")
	private List<DitMovimientoAsegurado> ditMovimientoAsegurados2;

	//bi-directional many-to-one association to DicTipoMovtoAsegurado
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_TIPO_MOVTO_ASEGURADO")
	private DicTipoMovtoAsegurado dicTipoMovtoAsegurado;

	//bi-directional many-to-one association to DicOrigenMovtoAsegurado
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_ORIGEN_MOVTO_ASEGURADO")
	private DicOrigenMovtoAsegurado dicOrigenMovtoAsegurado;

	//bi-directional many-to-one association to DitTipoTrabModalidad
	// XXX AL PARCER ESTA YA NO ES SOPORTADA NI REQUERIDA POR LA BD
	//Es requerida para que funcione correctamente el mapeo, se quitan las relaciones dependientes en ambas entidades o se conservan ambas.
//	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="CVE_ID_TIPO_TRAB_MODALIDAD")
//	private DitTipoTrabModalidad ditTipoTrabModalidad;

	//bi-directional one-to-one association to DitMovtoAsegAbierto
	@OneToOne(mappedBy="ditMovimientoAsegurado", fetch=FetchType.LAZY)
	private DitMovtoAsegAbierto ditMovtoAsegAbierto;

	//bi-directional one-to-one association to DitMovtoAsegCierre
	@OneToOne(mappedBy="ditMovimientoAsegurado", fetch=FetchType.LAZY)
	private DitMovtoAsegCierre ditMovtoAsegCierre;

    public DitMovimientoAsegurado() {
    }

	public long getCveIdMovimientoAsegurado() {
		return this.cveIdMovimientoAsegurado;
	}

	public void setCveIdMovimientoAsegurado(long cveIdMovimientoAsegurado) {
		this.cveIdMovimientoAsegurado = cveIdMovimientoAsegurado;
	}

	public Date getFecMovimiento() {
		return this.fecMovimiento;
	}

	public void setFecMovimiento(Date fecMovimiento) {
		this.fecMovimiento = fecMovimiento;
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

	public String getIndExtemporaneo() {
		return this.indExtemporaneo;
	}

	public void setIndExtemporaneo(String indExtemporaneo) {
		this.indExtemporaneo = indExtemporaneo;
	}

	public List<DitMovasegAjuste> getDitMovasegAjustes() {
		return this.ditMovasegAjustes;
	}

	public void setDitMovasegAjustes(List<DitMovasegAjuste> ditMovasegAjustes) {
		this.ditMovasegAjustes = ditMovasegAjustes;
	}
	
	public DitAsegurado getDitAsegurado() {
		return this.ditAsegurado;
	}

	public void setDitAsegurado(DitAsegurado ditAsegurado) {
		this.ditAsegurado = ditAsegurado;
	}
	
	public DitMovimientoAsegurado getDitMovimientoAsegurado1() {
		return this.ditMovimientoAsegurado1;
	}

	public void setDitMovimientoAsegurado1(DitMovimientoAsegurado ditMovimientoAsegurado1) {
		this.ditMovimientoAsegurado1 = ditMovimientoAsegurado1;
	}
	
	public List<DitMovimientoAsegurado> getDitMovimientoAsegurados1() {
		return this.ditMovimientoAsegurados1;
	}

	public void setDitMovimientoAsegurados1(List<DitMovimientoAsegurado> ditMovimientoAsegurados1) {
		this.ditMovimientoAsegurados1 = ditMovimientoAsegurados1;
	}
	
	public DitMovimientoAsegurado getDitMovimientoAsegurado2() {
		return this.ditMovimientoAsegurado2;
	}

	public void setDitMovimientoAsegurado2(DitMovimientoAsegurado ditMovimientoAsegurado2) {
		this.ditMovimientoAsegurado2 = ditMovimientoAsegurado2;
	}
	
	public List<DitMovimientoAsegurado> getDitMovimientoAsegurados2() {
		return this.ditMovimientoAsegurados2;
	}

	public void setDitMovimientoAsegurados2(List<DitMovimientoAsegurado> ditMovimientoAsegurados2) {
		this.ditMovimientoAsegurados2 = ditMovimientoAsegurados2;
	}
	
	public DicTipoMovtoAsegurado getDicTipoMovtoAsegurado() {
		return this.dicTipoMovtoAsegurado;
	}

	public void setDicTipoMovtoAsegurado(DicTipoMovtoAsegurado dicTipoMovtoAsegurado) {
		this.dicTipoMovtoAsegurado = dicTipoMovtoAsegurado;
	}
	
	public DicOrigenMovtoAsegurado getDicOrigenMovtoAsegurado() {
		return this.dicOrigenMovtoAsegurado;
	}

	public void setDicOrigenMovtoAsegurado(DicOrigenMovtoAsegurado dicOrigenMovtoAsegurado) {
		this.dicOrigenMovtoAsegurado = dicOrigenMovtoAsegurado;
	}
	
//	public DitTipoTrabModalidad getDitTipoTrabModalidad() {
//		return this.ditTipoTrabModalidad;
//	}
//
//	public void setDitTipoTrabModalidad(DitTipoTrabModalidad ditTipoTrabModalidad) {
//		this.ditTipoTrabModalidad = ditTipoTrabModalidad;
//	}
	
	public DitMovtoAsegAbierto getDitMovtoAsegAbierto() {
		return this.ditMovtoAsegAbierto;
	}

	public void setDitMovtoAsegAbierto(DitMovtoAsegAbierto ditMovtoAsegAbierto) {
		this.ditMovtoAsegAbierto = ditMovtoAsegAbierto;
	}
	
	public DitMovtoAsegCierre getDitMovtoAsegCierre() {
		return this.ditMovtoAsegCierre;
	}

	public void setDitMovtoAsegCierre(DitMovtoAsegCierre ditMovtoAsegCierre) {
		this.ditMovtoAsegCierre = ditMovtoAsegCierre;
	}
	
	/**
	 * @return the ditTipoTrabModalidad
	 */
//	public DitTipoTrabModalidad getDitTipoTrabModalidad() {
//		return ditTipoTrabModalidad;
//	}

	/**
	 * @param ditTipoTrabModalidad the ditTipoTrabModalidad to set
	 */
//	public void setDitTipoTrabModalidad(DitTipoTrabModalidad ditTipoTrabModalidad) {
//		this.ditTipoTrabModalidad = ditTipoTrabModalidad;
//	}
	
}