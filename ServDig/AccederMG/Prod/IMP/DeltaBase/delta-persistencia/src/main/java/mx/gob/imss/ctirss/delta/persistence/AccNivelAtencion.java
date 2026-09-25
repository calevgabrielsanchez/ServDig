package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ACC_NIVEL_ATENCION database table.
 * 
 */
@Entity
@Table(name="ACC_NIVEL_ATENCION")
public class AccNivelAtencion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_NIVEL_ATENCION", nullable=false, precision=22)
	private long cveIdNivelAtencion;

	@Column(name="CVE_NIVEL_ATENCION", length=4)
	private String cveNivelAtencion;

	@Column(name="DES_NIVEL_ATENCION", length=20)
	private String desNivelAtencion;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_HORA_REGISTRO")
	private Date fecHoraRegistro;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	@Column(name="NUM_MATRICULA", length=20)
	private String numMatricula;

	//bi-directional many-to-one association to DicUmf
	@OneToMany(mappedBy="accNivelAtencion")
	private List<DicUmf> dicUmfs;

    public AccNivelAtencion() {
    }

	public long getCveIdNivelAtencion() {
		return this.cveIdNivelAtencion;
	}

	public void setCveIdNivelAtencion(long cveIdNivelAtencion) {
		this.cveIdNivelAtencion = cveIdNivelAtencion;
	}

	public String getCveNivelAtencion() {
		return this.cveNivelAtencion;
	}

	public void setCveNivelAtencion(String cveNivelAtencion) {
		this.cveNivelAtencion = cveNivelAtencion;
	}

	public String getDesNivelAtencion() {
		return this.desNivelAtencion;
	}

	public void setDesNivelAtencion(String desNivelAtencion) {
		this.desNivelAtencion = desNivelAtencion;
	}

	public Date getFecHoraRegistro() {
		return this.fecHoraRegistro;
	}

	public void setFecHoraRegistro(Date fecHoraRegistro) {
		this.fecHoraRegistro = fecHoraRegistro;
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

	public String getNumMatricula() {
		return this.numMatricula;
	}

	public void setNumMatricula(String numMatricula) {
		this.numMatricula = numMatricula;
	}

	public List<DicUmf> getDicUmfs() {
		return this.dicUmfs;
	}

	public void setDicUmfs(List<DicUmf> dicUmfs) {
		this.dicUmfs = dicUmfs;
	}
	
}