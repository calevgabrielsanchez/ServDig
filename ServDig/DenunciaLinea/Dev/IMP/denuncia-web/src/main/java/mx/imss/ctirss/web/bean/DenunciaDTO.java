package mx.imss.ctirss.web.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import mx.imss.ctirss.catalogos.model.DlcMotivodenuncia;
import mx.imss.ctirss.denuncia.vo.FormaPagoVO;
import mx.imss.ctirss.model.DltDatospatron;
import mx.imss.ctirss.model.DltDenuncia;
import mx.imss.ctirss.model.DltFormapago;
import mx.imss.ctirss.model.DltInfotrabajo;
import mx.imss.ctirss.model.DltPersona;
import mx.imss.ctirss.web.utils.MotivoDenunciaWrapper;

public class DenunciaDTO implements Serializable{

	/**
	 * 
	 */

	
	private static final long serialVersionUID = 1L;
	
	public static final String SES_NAME="denunciaDTO";
	
	private DltDenuncia dltDenuncia;

	private DltPersona dltPersonaT;
	
	private DltPersona dltPersonaB;
	
	private DltPersona dltPersonaRL;	
	
	private List<MotivoDenunciaWrapper> motivosDenuncia;
	
	private ValmdDTO valoresMotivoDenuncia;
	
	private List<Object> patrones;
	
	private DltDatospatron dltPatron;
	
	private boolean patronPrincipal;
	
	private DltInfotrabajo dltInfotrabajo;
	
	private String strTipoDenunciante;
	
	private String strTipoDocTr;
	
	private String strTipoDocB;
	
	private String strTipoDocRL;
	
	private int enviar;
	
	private String paso;
	
	private DltFormapago dltFormapagoPP;
	
	private DltFormapago dltFormapagoCP;
	
	private List<DltDenuncia> denuncias;
	
	private DltFormapago dltFormapagoFP;
	
	private FormaPagoVO formaPagoVO;
		

	public DenunciaDTO(){
	}
	
	public DltPersona getDltPersonaT() {
		return dltPersonaT;
	}

	public void setDltPersonaT(DltPersona dltPersonaT) {
		this.dltPersonaT = dltPersonaT;
	}

	public DltPersona getDltPersonaB() {
		return dltPersonaB;
	}

	public void setDltPersonaB(DltPersona dltPersonaB) {
		this.dltPersonaB = dltPersonaB;
	}

	public DltPersona getDltPersonaRL() {
		return dltPersonaRL;
	}

	public void setDltPersonaRL(DltPersona dltPersonaRL) {
		this.dltPersonaRL = dltPersonaRL;
	}

	public DltDenuncia getDltDenuncia() {
		return dltDenuncia;
	}

	public void setDltDenuncia(DltDenuncia dltDenuncia) {
		this.dltDenuncia = dltDenuncia;
	}

	public List<MotivoDenunciaWrapper> getMotivosDenuncia() {
		return motivosDenuncia;
	}

	public void setMotivosDenuncia(List<MotivoDenunciaWrapper> motivosDenuncia) {
		this.motivosDenuncia = motivosDenuncia;
	}
	
	public ValmdDTO getValoresMotivoDenuncia() {
		return valoresMotivoDenuncia;
	}

	public void setValoresMotivoDenuncia(ValmdDTO valoresMotivoDenuncia) {
		this.valoresMotivoDenuncia = valoresMotivoDenuncia;
	}

	public List<Object> getPatrones() {
		return patrones;
	}

	public void setPatrones(List<Object> patrones) {
		this.patrones = patrones;
	}

	
	public DltDatospatron getDltPatron() {
		return dltPatron;
	}

	public void setDltPatron(DltDatospatron dltPatron) {
		this.dltPatron = dltPatron;
	}

	public boolean isPatronPrincipal() {
		return patronPrincipal;
	}

	public void setPatronPrincipal(boolean patronPrincipal) {
		this.patronPrincipal = patronPrincipal;
	}
	
	
	public DltInfotrabajo getDltInfotrabajo() {
		return dltInfotrabajo;
	}

	public void setDltInfotrabajo(DltInfotrabajo dltInfotrabajo) {
		this.dltInfotrabajo = dltInfotrabajo;
	}

	public DltFormapago getDltFormapagoPP() {
		return dltFormapagoPP;
	}

	public void setDltFormapagoPP(DltFormapago dltFormapagoPP) {
		this.dltFormapagoPP = dltFormapagoPP;
	}

	public DltFormapago getDltFormapagoCP() {
		return dltFormapagoCP;
	}

	public void setDltFormapagoCP(DltFormapago dltFormapagoCP) {
		this.dltFormapagoCP = dltFormapagoCP;
	}

	public DltFormapago getDltFormapagoFP() {
		return dltFormapagoFP;
	}

	public void setDltFormapagoFP(DltFormapago dltFormapagoFP) {
		this.dltFormapagoFP = dltFormapagoFP;
	}
	
	public String getPaso() {
		return paso;
	}

	public void setPaso(String paso) {
		this.paso = paso;
	}

	public List<DltDenuncia> getDenuncias() {
		return denuncias;
	}

	public void setDenuncias(List<DltDenuncia> denuncias) {
		this.denuncias = denuncias;
	}

	public int getEnviar() {
		return enviar;
	}

	public void setEnviar(int enviar) {
		this.enviar = enviar;
	}

	public String getStrTipoDenunciante() {
		return strTipoDenunciante;
	}

	public String getStrTipoDocTr() {
		return strTipoDocTr;
	}

	public void setStrTipoDocTr(String strTipoDocTr) {
		this.strTipoDocTr = strTipoDocTr;
	}

	public void setStrTipoDenunciante(String strTipoDenunciante) {
		this.strTipoDenunciante = strTipoDenunciante;
	}

	public String getStrTipoDocB() {
		return strTipoDocB;
	}

	public void setStrTipoDocB(String strTipoDocB) {
		this.strTipoDocB = strTipoDocB;
	}

	public String getStrTipoDocRL() {
		return strTipoDocRL;
	}

	public void setStrTipoDocRL(String strTipoDocRL) {
		this.strTipoDocRL = strTipoDocRL;
	}

	public FormaPagoVO getFormaPagoVO() {
		return formaPagoVO;
	}

	public void setFormaPagoVO(FormaPagoVO formaPagoVO) {
		this.formaPagoVO = formaPagoVO;
	}

}
