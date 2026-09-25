package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIT_INDICADOR_PAT_SUJ_OBLIG database table.
 * 
 */
@Entity
@Table(name="DIT_INDICADOR_PAT_SUJ_OBLIG")
public class DitIndicadorPatSujOblig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_INDICADOR_PAT_SUJ_OBLIG", nullable=false, precision=22)
	private long cveIdIndicadorPatSujOblig;

	@Column(name="DES_OBSERVACIONES", length=255)
	private String desObservaciones;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FIN_INDICADOR")
	private Date fecFinIndicador;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_INICIO_INDICADOR")
	private Date fecInicioIndicador;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitCuotacredIncidencia
	@OneToMany(mappedBy="ditIndicadorPatSujOblig")
	private List<DitCuotacredIncidencia> ditCuotacredIncidencias;

	//bi-directional many-to-one association to DitPatronSujetoObligado
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_PATRON_SUJETO_OBLIGADO")
	private DitPatronSujetoObligado ditPatronSujetoObligado;

	//bi-directional many-to-one association to DicTipoIndPatSujOblig
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_TIPO_IND_PAT_SUJ_OBLIG")
	private DicTipoIndPatSujOblig dicTipoIndPatSujOblig;

    public DitIndicadorPatSujOblig() {
    }

	public long getCveIdIndicadorPatSujOblig() {
		return this.cveIdIndicadorPatSujOblig;
	}

	public void setCveIdIndicadorPatSujOblig(long cveIdIndicadorPatSujOblig) {
		this.cveIdIndicadorPatSujOblig = cveIdIndicadorPatSujOblig;
	}

	public String getDesObservaciones() {
		return this.desObservaciones;
	}

	public void setDesObservaciones(String desObservaciones) {
		this.desObservaciones = desObservaciones;
	}

	public Date getFecFinIndicador() {
		return this.fecFinIndicador;
	}

	public void setFecFinIndicador(Date fecFinIndicador) {
		this.fecFinIndicador = fecFinIndicador;
	}

	public Date getFecInicioIndicador() {
		return this.fecInicioIndicador;
	}

	public void setFecInicioIndicador(Date fecInicioIndicador) {
		this.fecInicioIndicador = fecInicioIndicador;
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

	public List<DitCuotacredIncidencia> getDitCuotacredIncidencias() {
		return this.ditCuotacredIncidencias;
	}

	public void setDitCuotacredIncidencias(List<DitCuotacredIncidencia> ditCuotacredIncidencias) {
		this.ditCuotacredIncidencias = ditCuotacredIncidencias;
	}
	
	public DitPatronSujetoObligado getDitPatronSujetoObligado() {
		return this.ditPatronSujetoObligado;
	}

	public void setDitPatronSujetoObligado(DitPatronSujetoObligado ditPatronSujetoObligado) {
		this.ditPatronSujetoObligado = ditPatronSujetoObligado;
	}
	
	public DicTipoIndPatSujOblig getDicTipoIndPatSujOblig() {
		return this.dicTipoIndPatSujOblig;
	}

	public void setDicTipoIndPatSujOblig(DicTipoIndPatSujOblig dicTipoIndPatSujOblig) {
		this.dicTipoIndPatSujOblig = dicTipoIndPatSujOblig;
	}
	
}