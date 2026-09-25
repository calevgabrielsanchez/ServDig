package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.delta.persistence.SptGrupoFamPensNomina;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the SPC_PERIODO_PAGO_NOMINA database table.
 * 
 */
@Entity
@Table(name="SPC_PERIODO_PAGO_NOMINA")
public class SpcPeriodoPagoNomina implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SpcPeriodoPagoNominaPK id;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_FINAL")
	private Date fecFinal;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_INICIAL")
	private Date fecInicial;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to SptGrupoFamPensNomina
//	@OneToMany(mappedBy="spcPeriodoPagoNomina")  FIXME no despliega relacion
//	private List<SptGrupoFamPensNomina> sptGrupoFamPensNominas;

    public SpcPeriodoPagoNomina() {
    }

	public SpcPeriodoPagoNominaPK getId() {
		return this.id;
	}

	public void setId(SpcPeriodoPagoNominaPK id) {
		this.id = id;
	}
	
	public Date getFecFinal() {
		return this.fecFinal;
	}

	public void setFecFinal(Date fecFinal) {
		this.fecFinal = fecFinal;
	}

	public Date getFecInicial() {
		return this.fecInicial;
	}

	public void setFecInicial(Date fecInicial) {
		this.fecInicial = fecInicial;
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

//	public List<SptGrupoFamPensNomina> getSptGrupoFamPensNominas() {
//		return this.sptGrupoFamPensNominas;
//	}
//
//	public void setSptGrupoFamPensNominas(List<SptGrupoFamPensNomina> sptGrupoFamPensNominas) {
//		this.sptGrupoFamPensNominas = sptGrupoFamPensNominas;
//	}
	
}