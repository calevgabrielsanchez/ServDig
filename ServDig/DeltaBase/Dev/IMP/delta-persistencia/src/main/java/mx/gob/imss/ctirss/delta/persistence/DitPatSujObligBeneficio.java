package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;

@Entity
@Table(name = "DIT_PAT_SUJ_OBLIG_BENEFICIO")
public class DitPatSujObligBeneficio implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "DIT_PAT_SUJ_OBLIG_BENEFICIO_CVEIDPATSUJOBLIGBENEFICIO_GENERATOR", sequenceName = "SEQ_DITPATSUJOBLBENEFICIO", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_PAT_SUJ_OBLIG_BENEFICIO_CVEIDPATSUJOBLIGBENEFICIO_GENERATOR")
	@Column(name = "CVE_ID_PAT_SUJ_OBLIG_BENEFICIO")
	private long cveIdPatSujObligBeneficio;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_ALTA")
	private Date fecRegistroAlta;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

	// bi-directional many-to-one association to DitBeneficio
	@ManyToOne
	@JoinColumn(name = "CVE_ID_BENEFICIO")
	private DitBeneficio ditBeneficio;

	// bi-directional many-to-one association to DitPatronSujetoObligado
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "CVE_ID_PATRON_SUJETO_OBLIGADO")
	private DitPatronSujetoObligado ditPatronSujetoObligado;

	// bi-directional many-to-one association to DicEstadoBeneficio
	@ManyToOne
	@JoinColumn(name = "CVE_ID_ESTADO_BENEFICIO")
	private DicEstadoBeneficio dicEstadoBeneficio;
	
	
	public DitPatSujObligBeneficio() {
	}

	public long getCveIdPatSujObligBeneficio() {
		return this.cveIdPatSujObligBeneficio;
	}

	public void setCveIdPatSujObligBeneficio(long cveIdPatSujObligBeneficio) {
		this.cveIdPatSujObligBeneficio = cveIdPatSujObligBeneficio;
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

	public DitBeneficio getDitBeneficio() {
		return this.ditBeneficio;
	}

	public void setDitBeneficio(DitBeneficio ditBeneficio) {
		this.ditBeneficio = ditBeneficio;
	}

	public DitPatronSujetoObligado getDitPatronSujetoObligado() {
		return this.ditPatronSujetoObligado;
	}

	public void setDitPatronSujetoObligado(
			DitPatronSujetoObligado ditPatronSujetoObligado) {
		this.ditPatronSujetoObligado = ditPatronSujetoObligado;
	}
	
	public DicEstadoBeneficio getDicEstadoBeneficio() {
		return this.dicEstadoBeneficio;
	}

	public void setDicEstadoBeneficio(DicEstadoBeneficio dicEstadoBeneficio) {
		this.dicEstadoBeneficio = dicEstadoBeneficio;
	}

	
    @Override
    public Object clone(){
    	Object obj = null;    	
    	try {
			obj = super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return obj;
    }

	@Override
	public String toString() {
		return "DitPatSujObligBeneficio [cveIdPatSujObligBeneficio=" + cveIdPatSujObligBeneficio 
			+ ", fecRegistroActualizado=" + fecRegistroActualizado 
			+ ", fecRegistroAlta=" + fecRegistroAlta 
			+ ", fecRegistroBaja=" + fecRegistroBaja
			+ ", ditBeneficio=" + ditBeneficio
			+ ", ditPatronSujetoObligado=" + ditPatronSujetoObligado
			+ ", dicEstadoBeneficio=" + dicEstadoBeneficio + "]";
	}
    
    
}