package mx.gob.imss.ctirss.delta.model.clasificacion;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import mx.gob.imss.ctirss.delta.framework.base.model.AbstractModel;

public class Comentario extends AbstractModel {
	
	private BigDecimal cveIdComentario;    
	private BigDecimal cveIdAnalisis;    
	private BigDecimal cveIdEstatusAnalisis;    
    private String usuario;
    private String descripcion;
    private Date fecha;
    private String estatusMovimiento;
    
    private List<AnalisisClasificacionEmpresas> analisisClasificacionEmpresasList;

    public BigDecimal getCveIdComentario() {
		return cveIdComentario;
	}

	public void setCveIdComentario(BigDecimal cveIdComentario) {
		this.cveIdComentario = cveIdComentario;
	}

	public BigDecimal getCveIdAnalisis() {
		return cveIdAnalisis;
	}

	public void setCveIdAnalisis(BigDecimal cveIdAnalisis) {
		this.cveIdAnalisis = cveIdAnalisis;
	}

	public BigDecimal getCveIdEstatusAnalisis() {
		return cveIdEstatusAnalisis;
	}

	public void setCveIdEstatusAnalisis(BigDecimal cveIdEstatusAnalisis) {
		this.cveIdEstatusAnalisis = cveIdEstatusAnalisis;
	}

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

	/**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the estatusMovimiento
     */
    public String getEstatusMovimiento() {
        return estatusMovimiento;
    }

    /**
     * @param estatusMovimiento the estatusMovimiento to set
     */
    public void setEstatusMovimiento(String estatusMovimiento) {
        this.estatusMovimiento = estatusMovimiento;
    }

    /**
     * @return the analisisClasificacionEmpresasList
     */
    public List<AnalisisClasificacionEmpresas> getAnalisisClasificacionEmpresasList() {
        return analisisClasificacionEmpresasList;
    }

    /**
     * @param analisisClasificacionEmpresasList the analisisClasificacionEmpresasList to set
     */
    public void setAnalisisClasificacionEmpresasList(List<AnalisisClasificacionEmpresas> analisisClasificacionEmpresasList) {
        this.analisisClasificacionEmpresasList = analisisClasificacionEmpresasList;
    }

	@Override
	public String toString() {
		return "Comentario [cveIdComentario=" + cveIdComentario + ", fecha="
				+ fecha + ", usuario=" + usuario + ", descripcion="
				+ descripcion + ", estatusMovimiento=" + estatusMovimiento
				+ ", cveIdAnalisis=" + cveIdAnalisis
				+ ", cveIdEstatusAnalisis=" + cveIdEstatusAnalisis
				+ ", analisisClasificacionEmpresasList="
				+ analisisClasificacionEmpresasList + "]";
	}
    
    
}
