package mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula;

/**
 * 
 * @author Saúl Rosales Piedragil
 * @date 01/08/2012
 * @version 1.0.0
 * Objeto Visual para mostrar los registros de Correccion detalle
 */
public class CTCPCDResumenVO {

	private String descripcion;  
	private String totTipo;  
	private String totQtyRP;  
	private String totACOPConvSP;  
	private String totTACOPSP;  
	private String totTACOPAct;  
	private String totTACOPRec;  
	private String totTACOPTotal;  
	private String totTACOPMultas;  
	private String totATrabRevisados;  
	private String totATrabOmisos;  
	private String totATrabSub;  
	private String totTACOPSPPagada; 
	private String totTACOPPendientePago;  
	private String totARCVConvSP;  
	private String totTARCVSP;  
	private String totTARCVAct;  
	private String totTARCVRec;  
	private String totTARCVTotal;  
	private String totTARCVMultas;  
	private String totRCOPConvSP;  
	private String totTRCOPSP;  
	private String totTRCOPAct;  
	private String totTRCOPRec;  
	private String totTRCOPTotal;  
	private String totTRCOPMultas;  
	private String totRTrabRev;  
	private String totRTrabOmisos;  
	private String totRTrabSub;  
	private String totTRCOPSPPagada; 
	private String totTRCOPPendientePago;  
	private String totRRCVConvSP;  
	private String totTRRCVSP;  
	private String totTRRCVAct;  
	private String totTRRCVRec;  
	private String totTRRCVTotal;  
	private String totTRRCVMultas;
	
	public CTCPCDResumenVO(){}
	
	/**
	 * @author Saúl Rosales Piedragil
	 * @param descripcion
	 * @since 01/08/2012
	 * Constructor que inicializa sus campos a cero y pone descripción
	 */
	public CTCPCDResumenVO(String descripcion){
		this.descripcion = descripcion;  
		this.totTipo = "0.00";  
		this.totQtyRP = "0.00";  
		this.totACOPConvSP = "0.00";  
		this.totTACOPSP = "0.00";  
		this.totTACOPAct = "0.00";  
		this.totTACOPRec = "0.00";  
		this.totTACOPTotal = "0.00";  
		this.totTACOPMultas = "0.00";  
		this.totATrabRevisados = "0.00";  
		this.totATrabOmisos = "0.00";  
		this.totATrabSub = "0.00";  
		this.totTACOPSPPagada = "0.00"; 
		this.totTACOPPendientePago = "0.00";  
		this.totARCVConvSP = "0.00";  
		this.totTARCVSP = "0.00";  
		this.totTARCVAct = "0.00";  
		this.totTARCVRec = "0.00";  
		this.totTARCVTotal = "0.00";  
		this.totTARCVMultas = "0.00";  
		this.totRCOPConvSP = "0.00";  
		this.totTRCOPSP = "0.00";  
		this.totTRCOPAct = "0.00";  
		this.totTRCOPRec = "0.00";  
		this.totTRCOPTotal = "0.00";  
		this.totTRCOPMultas = "0.00";  
		this.totRTrabRev = "0.00";  
		this.totRTrabOmisos = "0.00";  
		this.totRTrabSub = "0.00";  
		this.totTRCOPSPPagada = "0.00"; 
		this.totTRCOPPendientePago = "0.00";  
		this.totRRCVConvSP = "0.00";  
		this.totTRRCVSP = "0.00";  
		this.totTRRCVAct = "0.00";  
		this.totTRRCVRec = "0.00";  
		this.totTRRCVTotal = "0.00";  
		this.totTRRCVMultas = "0.00";
	}
	
	/**
	 * @author Saúl Rosales Piedragil
	 * @param obj arreglo de objetos representando un registro de la información extraida de sql
	 * @since 01/08/2012
	 * Constructor que convierte de un arreglo de objetos a un pojo de esta clase.
	 */
	public CTCPCDResumenVO(Object[] obj){
				
		int i=0;
		descripcion = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;  
		totTipo = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;  
		totQtyRP = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;  
		totACOPConvSP = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;  
		totTACOPSP = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;  
		totTACOPAct = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;  
		totTACOPRec = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;  
		totTACOPTotal = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;  
		totTACOPMultas = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;  
		totATrabRevisados = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;  
		totATrabOmisos = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;  
		totATrabSub = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;  
		totTACOPSPPagada = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++; 
		totTACOPPendientePago = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;  
		totARCVConvSP = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;  
		totTARCVSP = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;  
		totTARCVAct = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;  
		totTARCVRec = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;  
		totTARCVTotal = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;  
		totTARCVMultas = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;  
		totRCOPConvSP = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;  
		totTRCOPSP = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;  
		totTRCOPAct = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;  
		totTRCOPRec = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;  
		totTRCOPTotal = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;  
		totTRCOPMultas = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;  
		totRTrabRev = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;  
		totRTrabOmisos = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;  
		totRTrabSub = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;  
		totTRCOPSPPagada = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++; 
		totTRCOPPendientePago = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;  
		totRRCVConvSP = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;  
		totTRRCVSP = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;  
		totTRRCVAct = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;  
		totTRRCVRec = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;  
		totTRRCVTotal = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;  
		totTRRCVMultas = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTotTipo() {
		return totTipo;
	}

	public void setTotTipo(String totTipo) {
		this.totTipo = totTipo;
	}

	public String getTotQtyRP() {
		return totQtyRP;
	}

	public void setTotQtyRP(String totQtyRP) {
		this.totQtyRP = totQtyRP;
	}

	public String getTotACOPConvSP() {
		return totACOPConvSP;
	}

	public void setTotACOPConvSP(String totACOPConvSP) {
		this.totACOPConvSP = totACOPConvSP;
	}

	public String getTotTACOPSP() {
		return totTACOPSP;
	}

	public void setTotTACOPSP(String totTACOPSP) {
		this.totTACOPSP = totTACOPSP;
	}

	public String getTotTACOPAct() {
		return totTACOPAct;
	}

	public void setTotTACOPAct(String totTACOPAct) {
		this.totTACOPAct = totTACOPAct;
	}

	public String getTotTACOPRec() {
		return totTACOPRec;
	}

	public void setTotTACOPRec(String totTACOPRec) {
		this.totTACOPRec = totTACOPRec;
	}

	public String getTotTACOPTotal() {
		return totTACOPTotal;
	}

	public void setTotTACOPTotal(String totTACOPTotal) {
		this.totTACOPTotal = totTACOPTotal;
	}

	public String getTotTACOPMultas() {
		return totTACOPMultas;
	}

	public void setTotTACOPMultas(String totTACOPMultas) {
		this.totTACOPMultas = totTACOPMultas;
	}

	public String getTotATrabRevisados() {
		return totATrabRevisados;
	}

	public void setTotATrabRevisados(String totATrabRevisados) {
		this.totATrabRevisados = totATrabRevisados;
	}

	public String getTotATrabOmisos() {
		return totATrabOmisos;
	}

	public void setTotATrabOmisos(String totATrabOmisos) {
		this.totATrabOmisos = totATrabOmisos;
	}

	public String getTotATrabSub() {
		return totATrabSub;
	}

	public void setTotATrabSub(String totATrabSub) {
		this.totATrabSub = totATrabSub;
	}

	public String getTotTACOPSPPagada() {
		return totTACOPSPPagada;
	}

	public void setTotTACOPSPPagada(String totTACOPSPPagada) {
		this.totTACOPSPPagada = totTACOPSPPagada;
	}

	public String getTotTACOPPendientePago() {
		return totTACOPPendientePago;
	}

	public void setTotTACOPPendientePago(String totTACOPPendientePago) {
		this.totTACOPPendientePago = totTACOPPendientePago;
	}

	public String getTotARCVConvSP() {
		return totARCVConvSP;
	}

	public void setTotARCVConvSP(String totARCVConvSP) {
		this.totARCVConvSP = totARCVConvSP;
	}

	public String getTotTARCVSP() {
		return totTARCVSP;
	}

	public void setTotTARCVSP(String totTARCVSP) {
		this.totTARCVSP = totTARCVSP;
	}

	public String getTotTARCVAct() {
		return totTARCVAct;
	}

	public void setTotTARCVAct(String totTARCVAct) {
		this.totTARCVAct = totTARCVAct;
	}

	public String getTotTARCVRec() {
		return totTARCVRec;
	}

	public void setTotTARCVRec(String totTARCVRec) {
		this.totTARCVRec = totTARCVRec;
	}

	public String getTotTARCVTotal() {
		return totTARCVTotal;
	}

	public void setTotTARCVTotal(String totTARCVTotal) {
		this.totTARCVTotal = totTARCVTotal;
	}

	public String getTotTARCVMultas() {
		return totTARCVMultas;
	}

	public void setTotTARCVMultas(String totTARCVMultas) {
		this.totTARCVMultas = totTARCVMultas;
	}

	public String getTotRCOPConvSP() {
		return totRCOPConvSP;
	}

	public void setTotRCOPConvSP(String totRCOPConvSP) {
		this.totRCOPConvSP = totRCOPConvSP;
	}

	public String getTotTRCOPSP() {
		return totTRCOPSP;
	}

	public void setTotTRCOPSP(String totTRCOPSP) {
		this.totTRCOPSP = totTRCOPSP;
	}

	public String getTotTRCOPAct() {
		return totTRCOPAct;
	}

	public void setTotTRCOPAct(String totTRCOPAct) {
		this.totTRCOPAct = totTRCOPAct;
	}

	public String getTotTRCOPRec() {
		return totTRCOPRec;
	}

	public void setTotTRCOPRec(String totTRCOPRec) {
		this.totTRCOPRec = totTRCOPRec;
	}

	public String getTotTRCOPTotal() {
		return totTRCOPTotal;
	}

	public void setTotTRCOPTotal(String totTRCOPTotal) {
		this.totTRCOPTotal = totTRCOPTotal;
	}

	public String getTotTRCOPMultas() {
		return totTRCOPMultas;
	}

	public void setTotTRCOPMultas(String totTRCOPMultas) {
		this.totTRCOPMultas = totTRCOPMultas;
	}

	public String getTotRTrabRev() {
		return totRTrabRev;
	}

	public void setTotRTrabRev(String totRTrabRev) {
		this.totRTrabRev = totRTrabRev;
	}

	public String getTotRTrabOmisos() {
		return totRTrabOmisos;
	}

	public void setTotRTrabOmisos(String totRTrabOmisos) {
		this.totRTrabOmisos = totRTrabOmisos;
	}

	public String getTotRTrabSub() {
		return totRTrabSub;
	}

	public void setTotRTrabSub(String totRTrabSub) {
		this.totRTrabSub = totRTrabSub;
	}

	public String getTotTRCOPSPPagada() {
		return totTRCOPSPPagada;
	}

	public void setTotTRCOPSPPagada(String totTRCOPSPPagada) {
		this.totTRCOPSPPagada = totTRCOPSPPagada;
	}

	public String getTotTRCOPPendientePago() {
		return totTRCOPPendientePago;
	}

	public void setTotTRCOPPendientePago(String totTRCOPPendientePago) {
		this.totTRCOPPendientePago = totTRCOPPendientePago;
	}

	public String getTotRRCVConvSP() {
		return totRRCVConvSP;
	}

	public void setTotRRCVConvSP(String totRRCVConvSP) {
		this.totRRCVConvSP = totRRCVConvSP;
	}

	public String getTotTRRCVSP() {
		return totTRRCVSP;
	}

	public void setTotTRRCVSP(String totTRRCVSP) {
		this.totTRRCVSP = totTRRCVSP;
	}

	public String getTotTRRCVAct() {
		return totTRRCVAct;
	}

	public void setTotTRRCVAct(String totTRRCVAct) {
		this.totTRRCVAct = totTRRCVAct;
	}

	public String getTotTRRCVRec() {
		return totTRRCVRec;
	}

	public void setTotTRRCVRec(String totTRRCVRec) {
		this.totTRRCVRec = totTRRCVRec;
	}

	public String getTotTRRCVTotal() {
		return totTRRCVTotal;
	}

	public void setTotTRRCVTotal(String totTRRCVTotal) {
		this.totTRRCVTotal = totTRRCVTotal;
	}

	public String getTotTRRCVMultas() {
		return totTRRCVMultas;
	}

	public void setTotTRRCVMultas(String totTRRCVMultas) {
		this.totTRRCVMultas = totTRRCVMultas;
	}

	
}
