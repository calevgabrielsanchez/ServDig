package mx.gob.imss.ctirss.gestionpersonas.servicios.hlda;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.model.persona.hlda.HldaDetalleVO;
import mx.gob.imss.ctirss.delta.model.persona.hlda.HldaPatronVO;
import mx.gob.imss.ctirss.delta.model.persona.hlda.HldaVO;
import mx.gob.imss.ctirss.delta.model.persona.hlda.exception.HldaIsBusyException;
import mx.gob.imss.ctirss.delta.model.persona.hlda.exception.HldaKnownErrorException;
import mx.gob.imss.ctirss.delta.model.persona.hlda.exception.WSHldaFaultException;
import mx.gob.imss.ctirss.gestionpersonas.servicios.hlda.interfaces.HldaClientServiceRemote;
import mx.gob.imss.ctirss.gestionpersonas.servicios.hlda.wsclient.HldaBean;
import mx.gob.imss.ctirss.gestionpersonas.servicios.hlda.wsclient.WsHlda;
import mx.gob.imss.ctirss.gestionpersonas.servicios.hlda.wsclient.WsHldaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless(name="hldaClientServiceBusiness", mappedName="hldaClientServiceBusiness")
public class HldaClientServiceBusiness implements HldaClientServiceRemote {

    private static final long serialVersionUID = -7946614152114212574L;

    private static final Logger log = LoggerFactory.getLogger(HldaClientServiceBusiness.class);

    private static final int LENGTH_NSS = 11;
    private static final int LENGTH_APEL_PAT = 35;
    private static final int LENGTH_APEL_MAT = 35;
    private static final int LENGTH_NOMBRE = 40;
    private static final int LENGTH_INTEGER = 6;
    private static final int LENGTH_YEAR = 4;
    private static final int LENGTH_DECIMAL_1 = 7;
    private static final int LENGTH_DECIMAL_2 = 9;
    private static final int LENGTH_REGISTRO_PATRONAL = 10;
    private static final int LENGTH_DATE = 10;
    private static final int TOTAL_PATRONES = 5;
    private static final int TOTAL_DETALLE = 50;
    private static final int LENGTH_NOMBRE_PATRON = 40;
    private static final int LENGTH_PATRON =
            LENGTH_DATE + LENGTH_DATE + LENGTH_DATE + LENGTH_NOMBRE_PATRON +
            LENGTH_DECIMAL_1 + LENGTH_DECIMAL_1;
    private static final int LENGTH_DETALLE = LENGTH_INTEGER + LENGTH_YEAR +
            LENGTH_DECIMAL_2 + LENGTH_DECIMAL_2;

    private WsHlda wsHlda;

    @PostConstruct
    protected void inicializaClientWS() {
        log.debug("inicializaClientWS");
        wsHlda = new WsHldaService().getWsHldaSoapPort();
    }

    @Override
    public HldaVO getHldaVO(String nss) {
        log.info("generando hldavo");
        HldaVO hldaVO = new HldaVO();
        HldaBean hldaBean = wsHlda.getHistoriaLaboral(nss);

        String value = findValueOrThrows(hldaBean);
        StringBuilder consumedChars = new StringBuilder();

        hldaVO.setNss(generateConsumedString(consumedChars, value, LENGTH_NSS));
        hldaVO.setApelPat(generateConsumedString(consumedChars, value, LENGTH_APEL_PAT));
        hldaVO.setApelMat(generateConsumedString(consumedChars, value, LENGTH_APEL_MAT));
        hldaVO.setNombre(generateConsumedString(consumedChars, value, LENGTH_NOMBRE));
        hldaVO.setTotSemCot(generateConsumedInteger(consumedChars, value, LENGTH_INTEGER));
        hldaVO.setSdo250(generateConsumedBigDecimal(consumedChars, value, LENGTH_DECIMAL_2));

        obtenerPatrones(hldaVO, consumedChars, value);
        obtenerDetalle(hldaVO, consumedChars, value);

        return hldaVO;
    }

    private String findValueOrThrows(HldaBean hldaBean) {
        if (hldaBean.getPantalla() == null) {
            throw new HldaIsBusyException();
        }

        //Los errores desconocidos contienen minusculas y mayusculas
        if (hldaBean.getPantalla().matches("^[A-Z ]+.*[a-z].*$")) {
            throw new WSHldaFaultException();
        }

        //Los errores conocidos solo contienen mayusculas
        if (hldaBean.getPantalla().matches("^(?=[A-Z ]).*")) {
            throw new HldaKnownErrorException(hldaBean.getPantalla());
        }

        return hldaBean.getPantalla();
    }

    private void obtenerPatrones(HldaVO hldaVO, StringBuilder consumedChars, String value) {
        List<HldaPatronVO> patrones = new ArrayList<HldaPatronVO>();
        for (int i = 0; i < TOTAL_PATRONES ; i++) {
            String patronStr = consumeChars(consumedChars, value, LENGTH_PATRON);
            if (patronStr.startsWith("**********")) {
                log.debug("ignora patron vacio: {}", patronStr);
                continue;
            }
            log.debug("patron encontrado: {}", patronStr);
            patrones.add(parsePatron(patronStr));
        }
        hldaVO.setPatrones(patrones);
    }

    private void obtenerDetalle(HldaVO hldaVO, StringBuilder consumedChars, String value) {
        List<HldaDetalleVO> detalle = new ArrayList<HldaDetalleVO>();
        String detalleStr = null;
        for (int i = 0; i < TOTAL_DETALLE; i++) {
            detalleStr = consumeChars(consumedChars, value, LENGTH_DETALLE);
            if (detalleStr.startsWith("000000")) {
                log.debug("ignora detalleStr vacia");
                continue;
            }
            log.debug("string seman: {}", detalleStr);
            detalle.add(parseDetalle(detalleStr));
        }
        hldaVO.setDetalle(detalle);
    }

    private HldaPatronVO parsePatron(String patronStr) {
        StringBuilder consumedCharsPatron = new StringBuilder();
        HldaPatronVO hldaPatronVO = new HldaPatronVO();
        hldaPatronVO.setFecInis(formateaFecha(consumeChars(consumedCharsPatron, patronStr, LENGTH_DATE)));
        hldaPatronVO.setFecFini(formateaFecha(consumeChars(consumedCharsPatron, patronStr, LENGTH_DATE)));
        hldaPatronVO.setRegPat(consumeChars(consumedCharsPatron, patronStr, LENGTH_REGISTRO_PATRONAL));
        hldaPatronVO.setNomPat(generateConsumedString(consumedCharsPatron, patronStr, LENGTH_NOMBRE_PATRON));
        hldaPatronVO.setSalIni(generateConsumedBigDecimal(consumedCharsPatron, patronStr, LENGTH_DECIMAL_1));
        hldaPatronVO.setSalFin(generateConsumedBigDecimal(consumedCharsPatron, patronStr, LENGTH_DECIMAL_1));
        return hldaPatronVO;
    }

    private HldaDetalleVO parseDetalle(String detalleStr) {
        StringBuilder consumedCharsDetalle = new StringBuilder();
        HldaDetalleVO hldaDetalleVO = new HldaDetalleVO();
        hldaDetalleVO.setSemana(generateConsumedInteger(consumedCharsDetalle, detalleStr, LENGTH_INTEGER));
        hldaDetalleVO.setAnio(generateConsumedInteger(consumedCharsDetalle, detalleStr, LENGTH_YEAR));
        hldaDetalleVO.setSdoProm(generateConsumedBigDecimal(consumedCharsDetalle, detalleStr, LENGTH_DECIMAL_2));
        hldaDetalleVO.setSdoDic(generateConsumedBigDecimal(consumedCharsDetalle, detalleStr, LENGTH_DECIMAL_2));
        return hldaDetalleVO;
    }

    private String formateaFecha(String fechaAAAAMMDD) {
        return fechaAAAAMMDD.replaceFirst("(\\d{4})-(\\d{2})-(\\d{2})", "$3/$2/$1");
    }

    private String generateConsumedString(StringBuilder consumedChars, String value, int fieldLength) {
        String val = noStars(consumeChars(consumedChars, value, fieldLength));
        return val;
    }

    private Integer generateConsumedInteger(StringBuilder consumedChars, String value, int length) {
        String strInteger = consumeChars(consumedChars, value, length);
        return Integer.parseInt(strInteger.replaceFirst("^0+", "0"));
    }

    private BigDecimal generateConsumedBigDecimal(StringBuilder consumedChars, String value, int fieldLength) {
        String val = consumeChars(consumedChars, value, fieldLength);
        val = val.replaceFirst("^0+(\\d+)", "$1");
        return new BigDecimal(val);
    }

    private String consumeChars(StringBuilder consumedChars, String value, int fieldLength) {
        int currentCount = consumedChars.length();
        String fieldContent = value.substring(currentCount, currentCount + fieldLength);
        consumedChars.append(fieldContent);
        return fieldContent;
    }


    private String noStars(String staredString) {
        return staredString.replaceAll("\\*+$", "").replaceAll("\\*", " ");
    }

    
    
	
}

