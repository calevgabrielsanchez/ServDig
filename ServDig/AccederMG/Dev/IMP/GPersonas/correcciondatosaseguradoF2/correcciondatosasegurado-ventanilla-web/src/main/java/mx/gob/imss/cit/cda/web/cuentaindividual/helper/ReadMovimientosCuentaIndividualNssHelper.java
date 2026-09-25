package mx.gob.imss.cit.cda.web.cuentaindividual.helper;

import java.util.List;

import mx.gob.imss.cit.cda.core.events.ReadEvent;
import mx.gob.imss.cit.cda.core.events.RequestReadEvent;
import mx.gob.imss.cit.cda.core.helper.ReadHelper;
import mx.gob.imss.cit.cda.service.interfaces.CuentaIndividualRemote;
import mx.gob.imss.cit.cda.web.app.constants.BeansConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(BeansConstants.READ_MOVIMIENTOS_CUENTA_INDIVIDUAL_NSS_HELPER)
public class ReadMovimientosCuentaIndividualNssHelper implements
		ReadHelper<String, Long> {

	@Autowired
	private CuentaIndividualRemote cuentaIndividualBussines;

	@Override
	public final ReadEvent<Long> requestEvent(
			final RequestReadEvent<String> requestReadEvent) {

		List<Long> detalles = cuentaIndividualBussines
				.getListNssByFolioTramite(requestReadEvent.getData());

		Long idDetalle = 0L;

		if (detalles != null && !detalles.isEmpty()) {
			idDetalle = detalles.get(0);
		}

		return new ReadEvent<Long>(requestReadEvent.getKey(), idDetalle);
	}
}
