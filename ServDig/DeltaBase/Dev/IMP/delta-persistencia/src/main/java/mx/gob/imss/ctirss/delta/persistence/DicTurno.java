package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import mx.gob.imss.ctirss.delta.framework.annotations.ComponentComboCampoDescripcion;
import mx.gob.imss.ctirss.delta.framework.annotations.OnSearchLlavePrimaria;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DIC_TURNO database table.
 * 
 */
@Entity
@Table(name="DIC_TURNO")
@OnSearchLlavePrimaria        (atributos={"cveIdTurno"})
@ComponentComboCampoDescripcion	(atributo="desDescripcion" )
public class DicTurno implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_TURNO", nullable=false, precision=22)
	private long cveIdTurno;

	@Column(name="DES_DESCRIPCION", nullable=false, length=255)
	private String desDescripcion;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;
    
    @Column(name="REF_HORA_INICIO_TURNO")
	private String refHoraInicioTurno;

    
    @Column(name="REF_HORA_FIN_TURNO")
	private String refHoraFinTurno;
    



	//bi-directional many-to-one association to DitUmfTurno
	@OneToMany(mappedBy="dicTurno")
	private List<DitUmfTurno> ditUmfTurnos;

	//bi-directional many-to-one association to DitUmfConsultorioTurno
		@OneToMany(mappedBy="dicTurno")
		private List<DitUmfConsultorioTurno> ditUmfConsultorioTurnos;
	
	
	
    public String getRefHoraInicioTurno() {
		return refHoraInicioTurno;
	}

	public void setRefHoraInicioTurno(String refHoraInicioTurno) {
		this.refHoraInicioTurno = refHoraInicioTurno;
	}

	public String getRefHoraFinTurno() {
		return refHoraFinTurno;
	}

	public void setRefHoraFinTurno(String refHoraFinTurno) {
		this.refHoraFinTurno = refHoraFinTurno;
	}

	public DicTurno() {
    }

	public long getCveIdTurno() {
		return this.cveIdTurno;
	}

	public void setCveIdTurno(long cveIdTurno) {
		this.cveIdTurno = cveIdTurno;
	}

	public String getDesDescripcion() {
		return this.desDescripcion;
	}

	public void setDesDescripcion(String desDescripcion) {
		this.desDescripcion = desDescripcion;
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

	public List<DitUmfConsultorioTurno> getDitUmfConsultorioTurnos() {
		return this.ditUmfConsultorioTurnos;
	}

	public void setDitUmfConsultorioTurnos(List<DitUmfConsultorioTurno> ditUmfConsultorioTurnos) {
		this.ditUmfConsultorioTurnos = ditUmfConsultorioTurnos;
	}
	
	public List<DitUmfTurno> getDitUmfTurnos() {
		return this.ditUmfTurnos;
	}

	public void setDitUmfTurnos(List<DitUmfTurno> ditUmfTurnos) {
		this.ditUmfTurnos = ditUmfTurnos;
	}
	
}