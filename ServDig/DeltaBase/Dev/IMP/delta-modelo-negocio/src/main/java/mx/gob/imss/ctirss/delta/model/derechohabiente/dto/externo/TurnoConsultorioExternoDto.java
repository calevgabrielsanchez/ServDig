package mx.gob.imss.ctirss.delta.model.derechohabiente.dto.externo;

import java.io.Serializable;

public class TurnoConsultorioExternoDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8554933891045278912L;
	private Long idTurno;
	private String desTurno;
	private Long numeroConsultorio;
	private Long idRelacionConsTurno;

	public TurnoConsultorioExternoDto() {
		super();
	}

	public TurnoConsultorioExternoDto(Long idTurno, String desTurno,
			Long numeroConsultorio, Long idRelacionConsTurno) {
		super();
		this.idTurno = idTurno;
		this.desTurno = desTurno;
		this.numeroConsultorio = numeroConsultorio;
		this.idRelacionConsTurno = idRelacionConsTurno;
	}

	public Long getIdTurno() {
		return idTurno;
	}
	
	public void setIdTurno(Long idTurno) {
		this.idTurno = idTurno;
	}
	
	public String getDesTurno() {
		return desTurno;
	}
	
	public void setDesTurno(String desTurno) {
		this.desTurno = desTurno;
	}
	
	public Long getNumeroConsultorio() {
		return numeroConsultorio;
	}
	
	public void setNumeroConsultorio(Long numeroConsultorio) {
		this.numeroConsultorio = numeroConsultorio;
	}
	
	public Long getIdRelacionConsTurno() {
		return idRelacionConsTurno;
	}
	
	public void setIdRelacionConsTurno(Long idRelacionConsTurno) {
		this.idRelacionConsTurno = idRelacionConsTurno;
	}	
}