package mx.gob.imss.ctirss.admonusuarios.entidad;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import mx.gob.imss.ctirss.admonusuarios.abstractModel.AbstractModel;


/**
 * The persistent class for the DIC_MODULO database table.
 * 
 */
@Entity
@Table(name="DIC_MODULO")
public class DicModulo  extends AbstractModel {
	private static final long serialVersionUID = 1L;
	private long cveIdModulo;
	private String desModulo;
	private Date fecRegistroActualizado;
	private Date fecRegistroAlta;
	private Date fecRegistroBaja;
//	private Set<SsoAprobador> ssoAprobadors;
	

    public DicModulo() {
    }


	@Id
	@Column(name="CVE_ID_MODULO", unique=true, nullable=false)
	public long getCveIdModulo() {
		return this.cveIdModulo;
	}

	public void setCveIdModulo(long cveIdModulo) {
		this.cveIdModulo = cveIdModulo;
	}


	@Column(name="DES_MODULO", length=20)
	public String getDesModulo() {
		return this.desModulo;
	}

	public void setDesModulo(String desModulo) {
		this.desModulo = desModulo;
	}


    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}


    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA")
	public Date getFecRegistroAlta() {
		return this.fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}


    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_BAJA")
	public Date getFecRegistroBaja() {
		return this.fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}


	//bi-directional many-to-one association to SsoAprobador
//	@OneToMany(mappedBy="dicModulo")
//	public Set<SsoAprobador> getSsoAprobadors() {
//		return this.ssoAprobadors;
//	}
//
//	public void setSsoAprobadors(Set<SsoAprobador> ssoAprobadors) {
//		this.ssoAprobadors = ssoAprobadors;
//	}
//	

	
	
}