package mx.gob.imss.ctirss.idse.persistencia;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the IDR_BLOQUE_REGISTROS database table.
 * 
 */
@Entity
@Table(name="IDR_BLOQUE_REGISTROS")
public class IdrBloqueRegistro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="IDR_BLOQUE_REGISTROS_CVEBLOQUEREGISTRO_GENERATOR", sequenceName="SEQ_BLOQUE_REG_PAT", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="IDR_BLOQUE_REGISTROS_CVEBLOQUEREGISTRO_GENERATOR")
	@Column(name="CVE_BLOQUE_REGISTRO")
	private Long cveBloqueRegistro;

	//bi-directional many-to-one association to IdcEstatusRelacion
    @ManyToOne
	@JoinColumn(name="CVE_ESTATUS_RELACION")
	private IdcEstatusRelacion idcEstatusRelacion;

	//bi-directional many-to-one association to IdtRegistrosPatronale
    @ManyToOne
	@JoinColumn(name="CVE_REGISTRO_PATRONAL")
	private IdtRegistrosPatronale idtRegistrosPatronale;

	//bi-directional many-to-one association to IdtRepresentado
    @ManyToOne
	@JoinColumn(name="CVE_REPRESENTADOS")
	private IdtRepresentado idtRepresentado;

    public IdrBloqueRegistro() {
    }

	public Long getCveBloqueRegistro() {
		return this.cveBloqueRegistro;
	}

	public void setCveBloqueRegistro(Long cveBloqueRegistro) {
		this.cveBloqueRegistro = cveBloqueRegistro;
	}

	public IdcEstatusRelacion getIdcEstatusRelacion() {
		return this.idcEstatusRelacion;
	}

	public void setIdcEstatusRelacion(IdcEstatusRelacion idcEstatusRelacion) {
		this.idcEstatusRelacion = idcEstatusRelacion;
	}
	
	public IdtRegistrosPatronale getIdtRegistrosPatronale() {
		return this.idtRegistrosPatronale;
	}

	public void setIdtRegistrosPatronale(IdtRegistrosPatronale idtRegistrosPatronale) {
		this.idtRegistrosPatronale = idtRegistrosPatronale;
	}
	
	public IdtRepresentado getIdtRepresentado() {
		return this.idtRepresentado;
	}

	public void setIdtRepresentado(IdtRepresentado idtRepresentado) {
		this.idtRepresentado = idtRepresentado;
	}
	
}