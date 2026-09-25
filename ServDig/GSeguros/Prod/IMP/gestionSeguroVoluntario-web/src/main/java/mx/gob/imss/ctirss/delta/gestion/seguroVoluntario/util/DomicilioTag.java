package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.util;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang.StringUtils;

import mx.gob.imss.digital.modelo.domicilio.Domicilio;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DomicilioTag extends SimpleTagSupport {

	private final Log log = LogFactory.getLog(getClass());

	Domicilio domicilio;

	@Override
	public void doTag() throws JspException {

		PageContext pageContext = (PageContext) getJspContext();
		JspWriter out = pageContext.getOut();

		StringBuffer domicilioCompleto = new StringBuffer();
		if (domicilio.getVialidadPrimaria() == null) {
			domicilioCompleto.append(domicilio.getCalle() != null ? domicilio
					.getCalle() : "");
		} else {
			domicilioCompleto.append(domicilio.getVialidadPrimaria()
					.getNombre());
		}

		domicilioCompleto.append(domicilio.getNumExterior1() != null
				&& domicilio.getNumExterior1() != 0 ? (" #" + domicilio
				.getNumExterior1()) : " "
				+ (domicilio.getNumExteriorAlf() != null ? " "
						+ domicilio.getNumExteriorAlf() : ""));

		if (domicilio.getNumInterior() != null
				&& domicilio.getNumInterior() != 0) {
			domicilioCompleto.append(", interior "
					+ domicilio.getNumInterior()
					+ (domicilio.getNumInteriorAlf() != null ? " "
							+ domicilio.getNumInteriorAlf() : ""));
		} else if (StringUtils.isNotBlank(domicilio.getNumInteriorAlf())){
			domicilioCompleto.append(", ").append(domicilio.getNumInteriorAlf());
		}

		domicilioCompleto.append(", COLONIA ").append(domicilio.getColonia());

		if (domicilio.getLocalidad().getMunicipio() != null) {
			domicilioCompleto.append(", ").append(
					domicilio.getLocalidad().getMunicipio().getNombre());
			if (domicilio.getLocalidad().getMunicipio().getEntidadFederativa() != null) {
				domicilioCompleto.append(", ").append(domicilio.getLocalidad().getMunicipio()
								.getEntidadFederativa().getNombre());
			}
		}

		domicilioCompleto.append(", CP ").append(domicilio.getCodigoPostal());

		try {
			out.print(domicilioCompleto.toString());
		} catch (IOException e) {
			log.error(e);
		}
	}

	public Domicilio getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
	}

}
