package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the DIC_TIPO_IDENTIFICADOR database table.
 * 
 */
@Entity
@Table(name="DIC_TIPO_IDENTIFICADOR")
public class DicTipoIdentificador implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="DIC_TIPO_IDENTIFICADOR_CVEIDTIPOIDENTIFICADOR_GENERATOR", sequenceName="SEQ_DICTIPOIDENTIFICADOR")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="DIC_TIPO_IDENTIFICADOR_CVEIDTIPOIDENTIFICADOR_GENERATOR")
	@Column(name="CVE_ID_TIPO_IDENTIFICADOR")
	private long cveIdTipoIdentificador;

	@Column(name="DES_IDENTIFICADOR")
	private String desIdentificador;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

    @Temporal( TemporalType.DATE)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	//bi-directional many-to-one association to DitIdentificador
	@OneToMany(mappedBy="dicTipoIdentificador")
	private Set<DitIdentificador> ditIdentificadors;
	
	//bi-directional many-to-one association to DitIdentificadorMoral
	@OneToMany(mappedBy="dicTipoIdentificador")
	private List<DitIdentificadorMoral> ditIdentificadoresMoral;

    public DicTipoIdentificador() {
    }

	public long getCveIdTipoIdentificador() {
		return this.cveIdTipoIdentificador;
	}

	public void setCveIdTipoIdentificador(long cveIdTipoIdentificador) {
		this.cveIdTipoIdentificador = cveIdTipoIdentificador;
	}

	public String getDesIdentificador() {
		return this.desIdentificador;
	}

	public void setDesIdentificador(String desIdentificador) {
		this.desIdentificador = desIdentificador;
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

	public Set<DitIdentificador> getDitIdentificadors() {
		return this.ditIdentificadors;
	}

	public void setDitIdentificadors(Set<DitIdentificador> ditIdentificadors) {
		this.ditIdentificadors = ditIdentificadors;
	}

	public List<DitIdentificadorMoral> getDitIdentificadoresMoral() {
		return ditIdentificadoresMoral;
	}

	public void setDitIdentificadoresMoral(
			List<DitIdentificadorMoral> ditIdentificadoresMoral) {
		this.ditIdentificadoresMoral = ditIdentificadoresMoral;
	}
	
}