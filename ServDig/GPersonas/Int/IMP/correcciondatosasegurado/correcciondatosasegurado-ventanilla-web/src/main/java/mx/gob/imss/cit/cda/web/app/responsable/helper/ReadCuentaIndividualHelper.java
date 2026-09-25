package mx.gob.imss.cit.cda.web.app.responsable.helper;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mx.gob.imss.cit.cda.core.events.ReadEvent;
import mx.gob.imss.cit.cda.core.events.RequestReadEvent;
import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.service.interfaces.CuentaIndividualRemote;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;
import mx.gob.imss.cit.cda.web.app.responsable.model.CuentaIndividual;
import mx.gob.imss.cit.cda.web.app.responsable.model.RequestCuentaIndividualPage;
import mx.gob.imss.cit.cda.web.support.model.Page;
import mx.gob.imss.cit.cda.web.utils.ReadCuentaIndividualUtils;
import mx.gob.imss.cit.gestion.solicitud.flujo.model.DataPage;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.CuentaIndividualVO;

@Component(BeansConstants.READ_CUENTA_INDIVIDUAL_HELPER)
public class ReadCuentaIndividualHelper implements ReadHelper<RequestCuentaIndividualPage, Page<CuentaIndividual>> {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private CuentaIndividualRemote cuentaIndividualRemote;
	
	@Autowired
	private ReadCuentaIndividualUtils readCuentaIndividualUtils;

	@SuppressWarnings("unchecked")
	@Override
	public ReadEvent<Page<CuentaIndividual>> requestEvent(RequestReadEvent<RequestCuentaIndividualPage> requestReadEvent) {
		log.debug("---CDA Ventanilla--- obtener solicitud por idTramite: {}", "x");
		DataPage dataPage = new DataPage();
		try {
			List<CuentaIndividualVO> periodos = cuentaIndividualRemote
					.consultarInformacionMovimientosCuentaIndividual("01654314127");
			dataPage.setData(periodos);			
			Page<CuentaIndividual> page =  readCuentaIndividualUtils.convertirAmodelo(dataPage);
			return new ReadEvent<Page<CuentaIndividual>>(requestReadEvent.getKey(), page);
		} catch (Exception e) {
			log.debug("---CDA Ventanilla--- error al leer la solicitud con idTramite: ", e);
			return ReadEvent.notFound(requestReadEvent.getKey());
		}
	}

}
