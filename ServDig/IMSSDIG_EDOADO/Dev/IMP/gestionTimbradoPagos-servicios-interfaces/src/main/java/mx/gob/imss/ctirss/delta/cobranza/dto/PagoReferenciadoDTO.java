package mx.gob.imss.ctirss.delta.cobranza.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PagoReferenciadoDTO implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
    private String registroPatronal;
    private String rfc;
    private String razonSocial;
    private String periodo;
    private BigDecimal folioSUA;
    private BigDecimal subTotalIMSS;
    private BigDecimal recIMMS;
    private BigDecimal actIMSS;
    private BigDecimal subTotRCV;
    private BigDecimal recRCV;
    private BigDecimal actRCV;
    private String fecIngreso;
    private Date fecPago;
    private String estatusRegistro;
    private String entidadRecaudadora;
    private String uuid;
    private String formaPago;
    private String tipoRelacion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegistroPatronal() {
        return registroPatronal;
    }

    public void setRegistroPatronal(String registroPatronal) {
        this.registroPatronal = registroPatronal;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getFecIngreso() {
        return fecIngreso;
    }

    public void setFecIngreso(String fecIngreso) {
        this.fecIngreso = fecIngreso;
    }

    public String getEstatusRegistro() {
        return estatusRegistro;
    }

    public void setEstatusRegistro(String estatusRegistro) {
        this.estatusRegistro = estatusRegistro;
    }    

    public String getEntidadRecaudadora() {
        return entidadRecaudadora;
    }

    public void setEntidadRecaudadora(String entidadRecaudadora) {
        this.entidadRecaudadora = entidadRecaudadora;
    }

    public BigDecimal getFolioSUA() {
        return folioSUA;
    }

    public void setFolioSUA(BigDecimal folSUA) {
        this.folioSUA = folSUA;
    }

    public BigDecimal getSubTotalIMSS() {
        return subTotalIMSS;
    }

    public void setSubTotalIMSS(BigDecimal subTotalIMSS) {
        this.subTotalIMSS = subTotalIMSS;
    }

    public BigDecimal getRecIMMS() {
        return recIMMS;
    }

    public void setRecIMMS(BigDecimal recIMMS) {
        this.recIMMS = recIMMS;
    }

    public BigDecimal getActIMSS() {
        return actIMSS;
    }

    public void setActIMSS(BigDecimal actIMSS) {
        this.actIMSS = actIMSS;
    }

    public BigDecimal getSubTotRCV() {
        return subTotRCV;
    }

    public void setSubTotRCV(BigDecimal subTotRCV) {
        this.subTotRCV = subTotRCV;
    }

    public BigDecimal getRecRCV() {
        return recRCV;
    }

    public void setRecRCV(BigDecimal recRCV) {
        this.recRCV = recRCV;
    }

    public BigDecimal getActRCV() {
        return actRCV;
    }

    public void setActRCV(BigDecimal actRCV) {
        this.actRCV = actRCV;
    }

    public Date getFecPago() {
        return fecPago;
    }

    public void setFecPago(Date fecPago) {
        this.fecPago = fecPago;
    }

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	public String getTipoRelacion() {
		return tipoRelacion;
	}

	public void setTipoRelacion(String tipoRelacion) {
		this.tipoRelacion = tipoRelacion;
	}
}
