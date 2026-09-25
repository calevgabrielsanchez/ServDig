package mx.gob.imss.cit.cda.web.vo;

import java.io.Serializable;

public class DomicilioAclaracionVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private DomicilioVO domicilioVO;
    private MotivoAclaracionVO motivoAclaracionVO;

    public DomicilioAclaracionVO() {
        super();
    }

    public DomicilioAclaracionVO(DomicilioVO domicilioVO,
            MotivoAclaracionVO motivoAclaracionVO) {
        super();
        this.domicilioVO = domicilioVO;
        this.motivoAclaracionVO = motivoAclaracionVO;
    }

    public DomicilioVO getDomicilioVO() {
        return domicilioVO;
    }

    public void setDomicilioVO(DomicilioVO domicilioVO) {
        this.domicilioVO = domicilioVO;
    }

    public MotivoAclaracionVO getMotivoAclaracionVO() {
        return motivoAclaracionVO;
    }

    public void setMotivoAclaracionVO(MotivoAclaracionVO motivoAclaracionVO) {
        this.motivoAclaracionVO = motivoAclaracionVO;
    }

}
