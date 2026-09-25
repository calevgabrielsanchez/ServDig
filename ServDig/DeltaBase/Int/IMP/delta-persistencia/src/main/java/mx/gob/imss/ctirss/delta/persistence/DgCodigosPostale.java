package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the DG_CODIGOS_POSTALES database table.
 * 
 */
@Entity
@Table(name="DG_CODIGOS_POSTALES")


@NamedQueries({ 
	
	@NamedQuery(name = "codigoPostaleByAsentamiento", 
			query = "from DgCodigosPostale as obj where " +
					"obj.dgAsentamiento.id.cveAsen = :cveAsen " +
					"and obj.dgAsentamiento.id.cveMun = :cveMun " +
					"and obj.dgAsentamiento.id.cveEnt = :cveEnt " )
	
})

public class DgCodigosPostale implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DgCodigosPostalePK id;

	@Column(length=1)
	private String agregado;

	//bi-directional many-to-one association to DgAsentamiento
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="CVE_ASEN", referencedColumnName="CVE_ASEN", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="CVE_ENT", referencedColumnName="CVE_ENT", nullable=false, insertable=false, updatable=false),
		@JoinColumn(name="CVE_MUN", referencedColumnName="CVE_MUN", nullable=false, insertable=false, updatable=false)
		})
	private DgAsentamiento dgAsentamiento;

	//bi-directional many-to-one association to DgDomicilioGeografico
	@OneToMany(mappedBy="dgCodigosPostale", fetch=FetchType.LAZY )
	private List<DgDomicilioGeografico> dgDomicilioGeograficos;

	//bi-directional many-to-one association to DitUmfCodPo
	@OneToMany(mappedBy="dgCodigosPostale", fetch=FetchType.LAZY )
	private List<DitUmfCodPo> ditUmfCodPos;
	
    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA", nullable=false)
	private Date fecRegistroAlta;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;
	

    public DgCodigosPostale() {
    }

	public DgCodigosPostalePK getId() {
		return this.id;
	}

	public void setId(DgCodigosPostalePK id) {
		this.id = id;
	}
	
	public String getAgregado() {
		return this.agregado;
	}

	public void setAgregado(String agregado) {
		this.agregado = agregado;
	}

	public DgAsentamiento getDgAsentamiento() {
		return this.dgAsentamiento;
	}

	public void setDgAsentamiento(DgAsentamiento dgAsentamiento) {
		this.dgAsentamiento = dgAsentamiento;
	}
	
	public List<DgDomicilioGeografico> getDgDomicilioGeograficos() {
		return this.dgDomicilioGeograficos;
	}

	public void setDgDomicilioGeograficos(List<DgDomicilioGeografico> dgDomicilioGeograficos) {
		this.dgDomicilioGeograficos = dgDomicilioGeograficos;
	}
	
	public List<DitUmfCodPo> getDitUmfCodPos() {
		return this.ditUmfCodPos;
	}

	public void setDitUmfCodPos(List<DitUmfCodPo> ditUmfCodPos) {
		this.ditUmfCodPos = ditUmfCodPos;
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

	
}