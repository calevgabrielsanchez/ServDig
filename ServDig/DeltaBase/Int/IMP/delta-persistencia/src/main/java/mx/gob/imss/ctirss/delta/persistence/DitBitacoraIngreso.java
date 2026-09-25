package mx.gob.imss.ctirss.delta.persistence;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name="DIT_BITACORA_INGRESO")
public class DitBitacoraIngreso implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name = "DIT_BITACORA_IN_GENERATOR", sequenceName = "SEQ_DITBITACORAINGRESO", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIT_BITACORA_IN_GENERATOR")
    @Column(name = "ID_BITACORA")
    private long idBitacora;
	

	@Column(name = "FEC_CONSULTA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaConsulta;

      
    @ManyToOne
	@JoinColumn(name="ID_RFC_BLOQ")
    private DitRfcBloqueados ditRfcBloqueados;


	public long getIdBitacora() {
		return this.idBitacora;
	}



	public void setIdBitacora(Long idBitacora) {
		this.idBitacora = idBitacora;
	}



	public Date getFechaConsulta() {
		return fechaConsulta;
	}



	public void setFechaConsulta(Date fechaConsulta) {
		this.fechaConsulta = fechaConsulta;
	}



	public DitRfcBloqueados getDitRfcBloqueados() {
		return ditRfcBloqueados;
	}



	public void setDitRfcBloqueados(DitRfcBloqueados ditRfcBloqueados) {
		this.ditRfcBloqueados = ditRfcBloqueados;
	}



	public void setIdBitacora(long idBitacora) {
		this.idBitacora = idBitacora;
	}}