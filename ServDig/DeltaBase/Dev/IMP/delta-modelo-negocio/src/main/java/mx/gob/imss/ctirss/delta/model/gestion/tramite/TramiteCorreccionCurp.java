package mx.gob.imss.ctirss.delta.model.gestion.tramite;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import mx.gob.imss.ctirss.delta.model.asegurado.cda.CertificacionNSS;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.DatosLaborales;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.MotivoAclaracion;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.ObservacionesSubdelegacion;
import mx.gob.imss.ctirss.delta.model.asegurado.cda.TipoRegularizacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.nss.CorreccionNSS;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Beneficiario;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TramiteCorreccionCurp extends Tramite implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<String> listaNSS;
	private List<String> listaNrpPatron;
	private List<MotivoAclaracion> motivosAclaracion;
	// bandera para saber si la solicitud se realiza en representacion de un
	// finado.
	private boolean indicadorDefuncion;
	private String desMotivoAclaracion;
	private CertificacionNSS certificacionNSS;
	// campo donde se guarda el detalle de la reasignacion ingresado en pantalla
	private String detalleReasignacion;
	private Fisica personaRENAPO;
	private TipoRegularizacion tipoRegularizacion;
	private List<DatosLaborales> datosLaborales;
	private List<ObservacionesSubdelegacion> observacionesSubdelegacion;
	private Long idTramiteCorreccionDatosAseg;
	private String idDocumentoCertificacion;
	private String idDocumentoAcuse;
    private boolean indicadorInfoAdicional;
    private Integer origenTramite;
	
	private List<CorreccionNSS> listaNssCorreccion;
	private Beneficiario beneficiario;
	private Fisica representante;
	private Fisica asegurado;
	
	private boolean soloCuentaIndividual;
	
	public boolean getsoloCuentaIndividual() {
		return soloCuentaIndividual;
	}

	public void setsoloCuentaIndividual(boolean soloCuentaIndividual) {
		this.soloCuentaIndividual = soloCuentaIndividual;
	}
	
	public Beneficiario getBeneficiario() {
		return beneficiario;
	}

	public void setBeneficiario(Beneficiario beneficiario) {
		this.beneficiario = beneficiario;
	}
	
	public Fisica getRepresentante() {
		return representante;
	}

	public void setRepresentante(Fisica representante) {
		this.representante = representante;
	}
	
	public Fisica getAsegurado() {
		return asegurado;
	}

	public void setAsegurado(Fisica asegurado) {
		this.asegurado = asegurado;
	}
	
	
	public List<CorreccionNSS> getListaNssCorreccion() {
		return listaNssCorreccion;
	}

	public void setListaNssCorreccion(List<CorreccionNSS> listaNssCorreccion) {
		this.listaNssCorreccion = listaNssCorreccion;
	}
 
	public List<MotivoAclaracion> getMotivosAclaracion() {
		return motivosAclaracion;
	}

	public void setMotivosAclaracion(List<MotivoAclaracion> motivosAclaracion) {
		this.motivosAclaracion = motivosAclaracion;
	}

	public String getDesMotivoAclaracion() {
		return desMotivoAclaracion;
	}

	public void setDesMotivoAclaracion(String desMotivoAclaracion) {
		this.desMotivoAclaracion = desMotivoAclaracion;
	}

	public List<String> getListaNSS() {
		return listaNSS;
	}

	public void setListaNSS(List<String> listaNSS) {
		this.listaNSS = listaNSS;
	}

	public List<String> getListaNrpPatron() {
		return listaNrpPatron;
	}

	public void setListaNrpPatron(List<String> listaNrpPatron) {
		this.listaNrpPatron = listaNrpPatron;
	}

	public boolean isIndicadorDefuncion() {
		return indicadorDefuncion;
	}

	public void setIndicadorDefuncion(boolean indicadorDefuncion) {
		this.indicadorDefuncion = indicadorDefuncion;
	}

	public String getDetalleReasignacion() {
		return detalleReasignacion;
	}

	public void setDetalleReasignacion(String detalleReasignacion) {
		this.detalleReasignacion = detalleReasignacion;
	}

	public Fisica getPersonaRENAPO() {
		return personaRENAPO;
	}

	public void setPersonaRENAPO(Fisica personaRENAPO) {
		this.personaRENAPO = personaRENAPO;
	}

	public TipoRegularizacion getTipoRegularizacion() {
		return tipoRegularizacion;
	}

	public void setTipoRegularizacion(TipoRegularizacion tipoRegularizacion) {
		this.tipoRegularizacion = tipoRegularizacion;
	}

	public List<DatosLaborales> getDatosLaborales() {
		return datosLaborales;
	}

	public void setDatosLaborales(List<DatosLaborales> datosLaborales) {
		this.datosLaborales = datosLaborales;
	}

	public CertificacionNSS getCertificacionNSS() {
		return certificacionNSS;
	}

	public void setCertificacionNSS(CertificacionNSS certificacionNSS) {
		this.certificacionNSS = certificacionNSS;
	}

	public List<ObservacionesSubdelegacion> getObservacionesSubdelegacion() {
		return observacionesSubdelegacion;
	}

	public void setObservacionesSubdelegacion(List<ObservacionesSubdelegacion> observacionesSubdelegacion) {
		
		this.observacionesSubdelegacion = observacionesSubdelegacion;
		
	}

	public Long getIdTramiteCorreccionDatosAseg() {
		return idTramiteCorreccionDatosAseg;
	}

	public void setIdTramiteCorreccionDatosAseg(Long idTramiteCorreccionDatosAseg) {
		this.idTramiteCorreccionDatosAseg = idTramiteCorreccionDatosAseg;
	}

	public String getIdDocumentoCertificacion() {
		return idDocumentoCertificacion;
	}

	public void setIdDocumentoCertificacion(String idDocumentoCertificacion) {
		this.idDocumentoCertificacion = idDocumentoCertificacion;
	}

	public String getIdDocumentoAcuse() {
		return idDocumentoAcuse;
	}

	public void setIdDocumentoAcuse(String idDocumentoAcuse) {
		this.idDocumentoAcuse = idDocumentoAcuse;
	}

        public boolean isIndicadorInfoAdicional() {
            return indicadorInfoAdicional;
        }

        public void setIndicadorInfoAdicional(boolean indicadorInfoAdicional) {
            this.indicadorInfoAdicional = indicadorInfoAdicional;
        }

        public Integer getOrigenTramite() {
            return origenTramite;
        }

        public void setOrigenTramite(Integer origenTramite) {
            this.origenTramite = origenTramite;
        }
	
        
	
}
