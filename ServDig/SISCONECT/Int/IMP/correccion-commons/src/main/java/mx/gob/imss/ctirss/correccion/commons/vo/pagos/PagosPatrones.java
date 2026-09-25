package mx.gob.imss.ctirss.correccion.commons.vo.pagos;

import java.text.DecimalFormat;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import mx.gob.imss.ctirss.correccion.framework.base.model.AbstractModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PagosPatrones extends AbstractModel{


	private static final long serialVersionUID = 1L;
	private Integer cveSolicitudCorreccion;
	private String registroPatronal;
	private String cuotaIMSS;
	private String actualizacionIMSS;
	private String recargosIMSS;
	private String totalIMSS;
	
	private String cuotaRCV;
	private String actualizacionRCV;
	private String recargosRCV;
	private String totalRCV;
	
	
	public PagosPatrones(){
	}
	
	public PagosPatrones(Object[] obj){
		int i=0;
		
		setRegistroPatronal(String.valueOf(obj[i++]));
		DecimalFormat myFormatter = new DecimalFormat("###,###.###");
		
		
		
		setCuotaIMSS((String.valueOf(myFormatter.format(obj[i++]))));
		setActualizacionIMSS((String.valueOf(myFormatter.format(obj[i++]))));
		setRecargosIMSS((String.valueOf(myFormatter.format(obj[i++]))));
		setTotalIMSS((String.valueOf(myFormatter.format(obj[i++]))));
		
		setCuotaRCV((String.valueOf(myFormatter.format(obj[i++]))));
		setActualizacionRCV((String.valueOf(myFormatter.format(obj[i++]))));
		setRecargosRCV((String.valueOf(myFormatter.format(obj[i++]))));
		setTotalRCV((String.valueOf(myFormatter.format(obj[i++]))));
		
	}
	
	public PagosPatrones(List<?> copsSumarizadas){
		int i=0;
		
		if(copsSumarizadas!=null && !copsSumarizadas.isEmpty()){
			Object[] obj = (Object[])copsSumarizadas.get(0);
			
			setRegistroPatronal("NA");
			try{
				setCuotaIMSS((String.valueOf(obj[i++])));
				setActualizacionIMSS((String.valueOf(obj[i++])));
				setRecargosIMSS((String.valueOf(obj[i++])));
				setTotalIMSS((String.valueOf(obj[i++])));
				
				setCuotaRCV((String.valueOf(obj[i++])));
				setActualizacionRCV((String.valueOf(obj[i++])));
				setRecargosRCV((String.valueOf(obj[i++])));
				setTotalRCV((String.valueOf(obj[i++])));
			}catch(Exception e){
				setCuotaIMSS("0.0F");
				setActualizacionIMSS("0.0F");
				setRecargosIMSS("0.0F");
				setTotalIMSS("0.0F");
				
				setCuotaRCV("0.0F");
				setActualizacionRCV("0.0F");
				setRecargosRCV("0.0F");
				setTotalRCV("0.0F");
			}
			
		}
		
	}
	
	public String getRegistroPatronal() {
		return registroPatronal;
	}
	public void setRegistroPatronal(String registroPatronal) {
		this.registroPatronal = registroPatronal;
	}
	public String getCuotaIMSS() {
		return cuotaIMSS;
	}
	public void setCuotaIMSS(String cuotaIMSS) {
		this.cuotaIMSS = cuotaIMSS;
	}
	public String getActualizacionIMSS() {
		return actualizacionIMSS;
	}
	public void setActualizacionIMSS(String actualizacionIMSS) {
		this.actualizacionIMSS = actualizacionIMSS;
	}
	public String getRecargosIMSS() {
		return recargosIMSS;
	}
	public void setRecargosIMSS(String recargosIMSS) {
		this.recargosIMSS = recargosIMSS;
	}
	public String getTotalIMSS() {
		return totalIMSS;
	}
	public void setTotalIMSS(String totalIMSS) {
		this.totalIMSS = totalIMSS;
	}
	public String getCuotaRCV() {
		return cuotaRCV;
	}
	public void setCuotaRCV(String cuotaRCV) {
		this.cuotaRCV = cuotaRCV;
	}
	public String getActualizacionRCV() {
		return actualizacionRCV;
	}
	public void setActualizacionRCV(String actualizacionRCV) {
		this.actualizacionRCV = actualizacionRCV;
	}
	public String getRecargosRCV() {
		return recargosRCV;
	}
	public void setRecargosRCV(String recargosRCV) {
		this.recargosRCV = recargosRCV;
	}
	public String getTotalRCV() {
		return this.totalRCV;
	}
	
	public void setTotalRCV(String totalRCV) {
		this.totalRCV = totalRCV;
	}

	public Integer getCveSolicitudCorreccion() {
		return cveSolicitudCorreccion;
	}

	public void setCveSolicitudCorreccion(Integer cveSolicitudCorreccion) {
		this.cveSolicitudCorreccion = cveSolicitudCorreccion;
	}
	
}
