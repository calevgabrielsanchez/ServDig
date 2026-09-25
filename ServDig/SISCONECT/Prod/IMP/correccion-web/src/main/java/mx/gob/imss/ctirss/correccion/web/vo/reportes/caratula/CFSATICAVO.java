package mx.gob.imss.ctirss.correccion.web.vo.reportes.caratula;


import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class CFSATICAVO {

	private String folioCorreccion;
	private String registroPatronal;
	private String nombre;
	private String ubicacion;
	private String tipoObra;	
	private String noRO;
	private String OPE;
	private String noOficio;
	private String NOP;
	private String SE;
	private String CTC;
	private String PEA;
	private String ETR;
	private String AOP;
	private String PAI;
	private String observaciones;
	
	public CFSATICAVO(){}
	
	public CFSATICAVO(Object[] obj){

		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		
		int i=0;
		
		setFolioCorreccion(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		setRegistroPatronal(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		setNombre(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		setUbicacion(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		setTipoObra(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		setNoRO(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		setOPE(obj[i++]!=null ? formater.format((Timestamp)obj[i-1]) : " ");
		setNoOficio(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		setNOP(obj[i++]!=null ? formater.format((Timestamp)obj[i-1]) : " ");
		setSE(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		setCTC(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		setPEA(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		setETR(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		setAOP(obj[i++]!=null ? formater.format((Timestamp)obj[i-1]) : " ");
		setPAI(obj[i++]!=null ? formater.format((Timestamp)obj[i-1]) : " ");
		setObservaciones(obj[i++]!=null ? String.valueOf(obj[i-1]) : " ");
		

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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getTipoObra() {
		return tipoObra;
	}

	public void setTipoObra(String tipoObra) {
		this.tipoObra = tipoObra;
	}

	public String getNoRO() {
		return noRO;
	}

	public void setNoRO(String noRO) {
		this.noRO = noRO;
	}

	public String getOPE() {
		return OPE;
	}

	public void setOPE(String oPE) {
		OPE = oPE;
	}

	public String getNoOficio() {
		return noOficio;
	}

	public void setNoOficio(String noOficio) {
		this.noOficio = noOficio;
	}

	public String getNOP() {
		return NOP;
	}

	public void setNOP(String nOP) {
		NOP = nOP;
	}

	public String getSE() {
		return SE;
	}

	public void setSE(String sE) {
		SE = sE;
	}

	public String getCTC() {
		return CTC;
	}

	public void setCTC(String cTC) {
		CTC = cTC;
	}

	public String getPEA() {
		return PEA;
	}

	public void setPEA(String pEA) {
		PEA = pEA;
	}

	public String getETR() {
		return ETR;
	}

	public void setETR(String eTR) {
		ETR = eTR;
	}

	public String getAOP() {
		return AOP;
	}

	public void setAOP(String aOP) {
		AOP = aOP;
	}

	public String getPAI() {
		return PAI;
	}

	public void setPAI(String pAI) {
		PAI = pAI;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	
	
}
