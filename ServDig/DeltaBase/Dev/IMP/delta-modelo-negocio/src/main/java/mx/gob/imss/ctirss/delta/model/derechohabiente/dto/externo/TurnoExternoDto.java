package mx.gob.imss.ctirss.delta.model.derechohabiente.dto.externo;
import java.io.Serializable;


public class TurnoExternoDto  implements Serializable {

    private static final long serialVersionUID = -7996262209427113508L;
    protected Long idTurno;
    protected String descripcion;
    protected String horaInicioTurno;
    protected String horaFinTurno;

    public TurnoExternoDto() {
    	super();
    }
    
    
    
    public TurnoExternoDto(Long idTurno, String descripcion,
			String horaInicioTurno, String horaFinTurno) {
		super();
		this.idTurno = idTurno;
		this.descripcion = descripcion;
		this.horaInicioTurno = horaInicioTurno;
		this.horaFinTurno = horaFinTurno;
	}



	public TurnoExternoDto(Long idTurno) {
    	this.idTurno = idTurno;
    }
    
    public TurnoExternoDto(String descripcion) {
    	this.descripcion = descripcion;
    }
    
    public TurnoExternoDto(Long idTurno, String descripcion) {
    	this.idTurno = idTurno;
    	this.descripcion = descripcion;
    }
    
    public Long getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(final Long idTurno) {
        this.idTurno = idTurno;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(final String descripcion) {
        this.descripcion = descripcion;
    }

    public String getHoraInicioTurno() {
        return horaInicioTurno;
    }

    public void setHoraInicioTurno(final String horaInicioTurno) {
        this.horaInicioTurno = horaInicioTurno;
    }

    public String getHoraFinTurno() {
        return horaFinTurno;
    }

    public void setHoraFinTurno(final String horaFinTurno) {
        this.horaFinTurno = horaFinTurno;
    }

}
