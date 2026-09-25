package mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PDVO {
	
	private String idPago;
	private String folioCorreccion;
	private String proceso;
	private String registroPatronal;
	private String folioSUA;
	private String ordenIngreso;
	private String nuCredito;
	private String fechaPago;
	private String periodo;
	private String SP;
	private String act;
	private String rec;
	private String total;
	private String multas;
	private String RCVperiodo;
	private String RCVSP;
	private String RCVAct;
	private String RCVRec;
	private String RCVTotal;
	
	
	
	public PDVO(){}
	
	public PDVO(Object[] obj){

		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		int i=0;
		
		this.setIdPago(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		this.setFolioCorreccion(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		this.setProceso(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		this.setRegistroPatronal(obj[i++]!=null ? (String)obj[i-1] : " ");
		this.setFolioSUA(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		this.setOrdenIngreso(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		this.setNuCredito(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		this.setFechaPago(obj[i++]!=null ? formater.format((Date)obj[i-1]):" ");
		
		this.setPeriodo(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		this.setSP(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		this.setAct(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		this.setRec(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		
		this.setTotal(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		this.setMultas(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		this.setRCVperiodo(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		
		this.setRCVSP(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		this.setRCVAct(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		this.setRCVRec(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		this.setRCVTotal(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");       
	

	}


	public String getFolioCorreccion() {
		return folioCorreccion;
	}


	public void setFolioCorreccion(String folioCorreccion) {
		this.folioCorreccion = folioCorreccion;
	}


	public String getRegistroPatronal() {
		return registroPatronal;
	}


	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}


	public String getIdPago() {
		return idPago;
	}

	public void setIdPago(String idPago) {
		this.idPago = idPago;
	}

	public String getProceso() {
		return proceso;
	}

	public void setProceso(String proceso) {
		this.proceso = proceso;
	}

	public String getFolioSUA() {
		return folioSUA;
	}

	public void setFolioSUA(String folioSUA) {
		this.folioSUA = folioSUA;
	}

	public String getOrdenIngreso() {
		return ordenIngreso;
	}

	public void setOrdenIngreso(String ordenIngreso) {
		this.ordenIngreso = ordenIngreso;
	}

	public String getNuCredito() {
		return nuCredito;
	}

	public void setNuCredito(String nuCredito) {
		this.nuCredito = nuCredito;
	}

	public String getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(String fechaPago) {
		this.fechaPago = fechaPago;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getSP() {
		return SP;
	}

	public void setSP(String sP) {
		SP = sP;
	}

	public String getAct() {
		return act;
	}

	public void setAct(String act) {
		this.act = act;
	}

	public String getRec() {
		return rec;
	}

	public void setRec(String rec) {
		this.rec = rec;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getMultas() {
		return multas;
	}

	public void setMultas(String multas) {
		this.multas = multas;
	}

	public String getRCVperiodo() {
		return RCVperiodo;
	}

	public void setRCVperiodo(String rCVperiodo) {
		RCVperiodo = rCVperiodo;
	}

	public String getRCVSP() {
		return RCVSP;
	}

	public void setRCVSP(String rCVSP) {
		RCVSP = rCVSP;
	}

	public String getRCVAct() {
		return RCVAct;
	}

	public void setRCVAct(String rCVAct) {
		RCVAct = rCVAct;
	}

	public String getRCVRec() {
		return RCVRec;
	}

	public void setRCVRec(String rCVRec) {
		RCVRec = rCVRec;
	}

	public String getRCVTotal() {
		return RCVTotal;
	}

	public void setRCVTotal(String rCVTotal) {
		RCVTotal = rCVTotal;
	}

	
	
}
