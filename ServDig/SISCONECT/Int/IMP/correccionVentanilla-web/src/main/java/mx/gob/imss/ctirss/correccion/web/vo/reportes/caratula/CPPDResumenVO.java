package mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula;


/**
 * 
 * @author Saúl Rosales Piedragil
 * @date 30/07/2012
 * @version 1.0.0
 * Objeto Visual para mostrar los registros de Promoción detalle
 */
public class CPPDResumenVO {

	  private String descripcion;
	  private String totTipo;
	  private String totCOPConvSP;
	  private String totTCOPSP;
	  private String totTCOPAct;
	  private String totTCOPRec;
	  private String totTCOPTotal;
	  private String totTCOPMultas;
	  private String totTrabRevisados;
	  private String totTrabOmisos;
	  private String totTrabSub;
	  private String totTCOPSPPagada;
	  private String totTCOPPendientePago;
	  private String totRCVConvSP;
	  private String totTRCVSP;
	  private String totTRCVAct;
	  private String totTRCVRec;
	  private String totTRCVTotal;
	
	
	public CPPDResumenVO(){}
	
	/**
	 * @author Saúl Rosales Piedragil
	 * @param obj arreglo de objetos representando un registro de la información extraida de sql
	 * @since 30/07/2012
	 * Constructor que convierte de un arreglo de objetos a un pojo de esta clase.
	 */
	public CPPDResumenVO(Object[] obj){
				
		int i=0;
		descripcion = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
        totTipo = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
        totCOPConvSP = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
        totTCOPSP = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
        totTCOPAct = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
        totTCOPRec = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
        totTCOPTotal = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
        totTCOPMultas = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
        totTrabRevisados = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
        totTrabOmisos = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
        totTrabSub = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
        totTCOPSPPagada = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
        totTCOPPendientePago = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
        totRCVConvSP = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
        totTRCVSP = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
        totTRCVAct = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
        totTRCVRec = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
        totTRCVTotal = (obj[i]!=null ? String.valueOf(obj[i]):" "); i++;
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

	public String getTotCOPConvSP() {
		return totCOPConvSP;
	}

	public void setTotCOPConvSP(String totCOPConvSP) {
		this.totCOPConvSP = totCOPConvSP;
	}

	public String getTotTCOPSP() {
		return totTCOPSP;
	}

	public void setTotTCOPSP(String totTCOPSP) {
		this.totTCOPSP = totTCOPSP;
	}

	public String getTotTCOPAct() {
		return totTCOPAct;
	}

	public void setTotTCOPAct(String totTCOPAct) {
		this.totTCOPAct = totTCOPAct;
	}

	public String getTotTCOPRec() {
		return totTCOPRec;
	}

	public void setTotTCOPRec(String totTCOPRec) {
		this.totTCOPRec = totTCOPRec;
	}

	public String getTotTCOPTotal() {
		return totTCOPTotal;
	}

	public void setTotTCOPTotal(String totTCOPTotal) {
		this.totTCOPTotal = totTCOPTotal;
	}

	public String getTotTCOPMultas() {
		return totTCOPMultas;
	}

	public void setTotTCOPMultas(String totTCOPMultas) {
		this.totTCOPMultas = totTCOPMultas;
	}

	public String getTotTrabRevisados() {
		return totTrabRevisados;
	}

	public void setTotTrabRevisados(String totTrabRevisados) {
		this.totTrabRevisados = totTrabRevisados;
	}

	public String getTotTrabOmisos() {
		return totTrabOmisos;
	}

	public void setTotTrabOmisos(String totTrabOmisos) {
		this.totTrabOmisos = totTrabOmisos;
	}

	public String getTotTrabSub() {
		return totTrabSub;
	}

	public void setTotTrabSub(String totTrabSub) {
		this.totTrabSub = totTrabSub;
	}

	public String getTotTCOPSPPagada() {
		return totTCOPSPPagada;
	}

	public void setTotTCOPSPPagada(String totTCOPSPPagada) {
		this.totTCOPSPPagada = totTCOPSPPagada;
	}

	public String getTotTCOPPendientePago() {
		return totTCOPPendientePago;
	}

	public void setTotTCOPPendientePago(String totTCOPPendientePago) {
		this.totTCOPPendientePago = totTCOPPendientePago;
	}

	public String getTotRCVConvSP() {
		return totRCVConvSP;
	}

	public void setTotRCVConvSP(String totRCVConvSP) {
		this.totRCVConvSP = totRCVConvSP;
	}

	public String getTotTRCVSP() {
		return totTRCVSP;
	}

	public void setTotTRCVSP(String totTRCVSP) {
		this.totTRCVSP = totTRCVSP;
	}

	public String getTotTRCVAct() {
		return totTRCVAct;
	}

	public void setTotTRCVAct(String totTRCVAct) {
		this.totTRCVAct = totTRCVAct;
	}

	public String getTotTRCVRec() {
		return totTRCVRec;
	}

	public void setTotTRCVRec(String totTRCVRec) {
		this.totTRCVRec = totTRCVRec;
	}

	public String getTotTRCVTotal() {
		return totTRCVTotal;
	}

	public void setTotTRCVTotal(String totTRCVTotal) {
		this.totTRCVTotal = totTRCVTotal;
	}




	
}
