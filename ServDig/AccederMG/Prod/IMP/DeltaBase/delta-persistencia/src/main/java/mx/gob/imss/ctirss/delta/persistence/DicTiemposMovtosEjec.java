package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the DIC_TIEMPOS_MOVTOS_EJEC database table.
 * 
 */
@Entity
@Table(name="DIC_TIEMPOS_MOVTOS_EJEC")
public class DicTiemposMovtosEjec implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DicTiemposMovtosEjecPK id;

	@Column(name="CVE_USUARIO", length=8)
	private String cveUsuario;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_CAPTURA")
	private Date fecCaptura;

    @Temporal( TemporalType.DATE)
	@Column(name="HORA_CAPTURA")
	private Date horaCaptura;

    public DicTiemposMovtosEjec() {
    }

	public DicTiemposMovtosEjecPK getId() {
		return this.id;
	}

	public void setId(DicTiemposMovtosEjecPK id) {
		this.id = id;
	}
	
	public String getCveUsuario() {
		return this.cveUsuario;
	}

	public void setCveUsuario(String cveUsuario) {
		this.cveUsuario = cveUsuario;
	}

	public Date getFecCaptura() {
		return this.fecCaptura;
	}

	public void setFecCaptura(Date fecCaptura) {
		this.fecCaptura = fecCaptura;
	}

	public Date getHoraCaptura() {
		return this.horaCaptura;
	}

	public void setHoraCaptura(Date horaCaptura) {
		this.horaCaptura = horaCaptura;
	}

}