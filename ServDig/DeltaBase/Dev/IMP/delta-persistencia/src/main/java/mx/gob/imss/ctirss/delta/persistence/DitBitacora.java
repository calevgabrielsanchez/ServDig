package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the DIT_BITACORA database table.
 * 
 */
@Entity
@Table(name="DIT_BITACORA")
public class DitBitacora implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CVE_ID_BITACORA", nullable=false, precision=22)
	private long cveIdBitacora;

	@Column(name="CVE_ID_IDENTIFICADOR", precision=22)
	private BigDecimal cveIdIdentificador;

    @Lob()
	@Column(name="DES_ACTIVIDAD")
	private String desActividad;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_ACTIVIDAD")
	private Date fecActividad;

    @Temporal( TemporalType.TIMESTAMP)
	@Column(name="FEC_REGISTRO_ACTUALIZADO")
	private Date fecRegistroActualizado;

	@Column(name="NOM_TABLA", length=50)
	private String nomTabla;

	//bi-directional many-to-one association to DicActividadUsuario
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_ACTIVIDAD_USUARIO")
	private DicActividadUsuario dicActividadUsuario;

	//bi-directional many-to-one association to DitUsuario
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="CVE_ID_USUARIO")
	private DitUsuario ditUsuario;
	
	
	
	public DitBitacora() {
    }

	public long getCveIdBitacora() {
		return this.cveIdBitacora;
	}

	public void setCveIdBitacora(long cveIdBitacora) {
		this.cveIdBitacora = cveIdBitacora;
	}

	public BigDecimal getCveIdIdentificador() {
		return this.cveIdIdentificador;
	}

	public void setCveIdIdentificador(BigDecimal cveIdIdentificador) {
		this.cveIdIdentificador = cveIdIdentificador;
	}

	public String getDesActividad() {
		return this.desActividad;
	}

	public void setDesActividad(String desActividad) {
		this.desActividad = desActividad;
	}

	public Date getFecActividad() {
		return this.fecActividad;
	}

	public void setFecActividad(Date fecActividad) {
		this.fecActividad = fecActividad;
	}

	public Date getFecRegistroActualizado() {
		return this.fecRegistroActualizado;
	}

	public void setFecRegistroActualizado(Date fecRegistroActualizado) {
		this.fecRegistroActualizado = fecRegistroActualizado;
	}

	public String getNomTabla() {
		return this.nomTabla;
	}

	public void setNomTabla(String nomTabla) {
		this.nomTabla = nomTabla;
	}

	public DicActividadUsuario getDicActividadUsuario() {
		return this.dicActividadUsuario;
	}

	public void setDicActividadUsuario(DicActividadUsuario dicActividadUsuario) {
		this.dicActividadUsuario = dicActividadUsuario;
	}
	
	public DitUsuario getDitUsuario() {
		return this.ditUsuario;
	}

	public void setDitUsuario(DitUsuario ditUsuario) {
		this.ditUsuario = ditUsuario;
	}
		
 

}