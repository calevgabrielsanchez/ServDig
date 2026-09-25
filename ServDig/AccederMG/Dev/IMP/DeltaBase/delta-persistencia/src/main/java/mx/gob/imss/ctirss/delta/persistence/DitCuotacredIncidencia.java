package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the DIT_CUOTACRED_INCIDENCIA database table.
 * 
 */
@Entity
@Table(name="DIT_CUOTACRED_INCIDENCIA")
public class DitCuotacredIncidencia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_CUOTACRED_INCIDENCIA", nullable=false, precision=22)
	private long cveIdCuotacredIncidencia;

	//bi-directional many-to-one association to DirCuotaCredito
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_CUOTA_CREDITO")
	private DirCuotaCredito dirCuotaCredito;

	//bi-directional many-to-one association to DitIndicadorPatSujOblig
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_INDICADOR_PAT_SUJ_OBLIG")
	private DitIndicadorPatSujOblig ditIndicadorPatSujOblig;

    public DitCuotacredIncidencia() {
    }

	public long getCveIdCuotacredIncidencia() {
		return this.cveIdCuotacredIncidencia;
	}

	public void setCveIdCuotacredIncidencia(long cveIdCuotacredIncidencia) {
		this.cveIdCuotacredIncidencia = cveIdCuotacredIncidencia;
	}

	public DirCuotaCredito getDirCuotaCredito() {
		return this.dirCuotaCredito;
	}

	public void setDirCuotaCredito(DirCuotaCredito dirCuotaCredito) {
		this.dirCuotaCredito = dirCuotaCredito;
	}
	
	public DitIndicadorPatSujOblig getDitIndicadorPatSujOblig() {
		return this.ditIndicadorPatSujOblig;
	}

	public void setDitIndicadorPatSujOblig(DitIndicadorPatSujOblig ditIndicadorPatSujOblig) {
		this.ditIndicadorPatSujOblig = ditIndicadorPatSujOblig;
	}
	
}