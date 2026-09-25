package mx.gob.imss.ws.vo.services.impl;

import mx.gob.imss.digital.modelo.sindo.VigenciaSeguroFamiliar;
import mx.gob.imss.ws.vo.Modalidad33VO;
import mx.gob.imss.ws.vo.Return;
import mx.gob.imss.ws.vo.helper.EstatusVigenciaHelper;
import mx.gob.imss.ws.vo.services.EstatusVigenciaService;

public class EstatusVigenciaServiceImpl implements EstatusVigenciaService {
	
	private Modalidad33VO modalidad33vo;
	
    /**
     *
     * @param idAsignacionNSS
     * @return
     */
    @Override
    public VigenciaSeguroFamiliar validaVigenciaSeguroFamiliar(
            String idAsignacionNSS) {
        Return retur = modalidad33vo.getConsultaMod33(idAsignacionNSS);
        VigenciaSeguroFamiliar vigencia = new VigenciaSeguroFamiliar();
        EstatusVigenciaHelper.convertirVigenciaSeguroFamiliar(retur, vigencia);
        if (vigencia.getClaveError() == 0) {
            return vigencia;
        }
        return null;

    }

	public Modalidad33VO getModalidad33vo() {
		return modalidad33vo;
	}

	public void setModalidad33vo(Modalidad33VO modalidad33vo) {
		this.modalidad33vo = modalidad33vo;
	}

}
