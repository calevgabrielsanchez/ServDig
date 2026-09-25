package mx.gob.imss.ctirss.delta.persistence;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
@Table(name="DIC_ESTADO_ACUERDO_DH")
public class DicEstadoAcuerdoDH implements Serializable{

	private static final long serialVersionUID = 3544604963311903369L;


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_ESTADO_ACUERDO_DH", nullable=false, precision=22)
	private Long cveIdEstadoAcuerdoDH;
	
	
	@Column(name="DES_ESTADO_ACUERDO", nullable=false, length=255)
	private String desEstadoAuerdo;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ALTA", nullable=false)
	private Date fecRegistroAlta;
	
	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

    @Temporal( TemporalType.TIMESTAMP)    
	@Column(name="FEC_REGISTRO_BAJA")
	private Date fecRegistroBaja;

    public DicEstadoAcuerdoDH() {
    	
    }
    
	public DicEstadoAcuerdoDH(Long cveIdEstadoAcuerdoDH) {
		super();
		this.cveIdEstadoAcuerdoDH = cveIdEstadoAcuerdoDH;
	}

	public Long getCveIdEstadoAcuerdoDH() {
		return cveIdEstadoAcuerdoDH;
	}

	public void setCveIdEstadoAcuerdoDH(Long cveIdEstadoAcuerdoDH) {
		this.cveIdEstadoAcuerdoDH = cveIdEstadoAcuerdoDH;
	}

	public String getDesEstadoAuerdo() {
		return desEstadoAuerdo;
	}

	public void setDesEstadoAuerdo(String desEstadoAuerdo) {
		this.desEstadoAuerdo = desEstadoAuerdo;
	}

	public Date getFecRegistroAlta() {
		return fecRegistroAlta;
	}

	public void setFecRegistroAlta(Date fecRegistroAlta) {
		this.fecRegistroAlta = fecRegistroAlta;
	}

	public Date getFecRegistroActualizado() {
		return fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public Date getFecRegistroBaja() {
		return fecRegistroBaja;
	}

	public void setFecRegistroBaja(Date fecRegistroBaja) {
		this.fecRegistroBaja = fecRegistroBaja;
	}
	
	
}

