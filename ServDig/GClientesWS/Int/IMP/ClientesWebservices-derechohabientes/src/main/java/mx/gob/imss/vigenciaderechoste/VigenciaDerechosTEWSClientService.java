package mx.gob.imss.vigenciaderechoste;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;

import javax.ejb.Stateless;
import javax.xml.bind.JAXBElement;

import mx.gob.imss.ctirss.delta.model.derechohabiente.GrupoFamiliarTE;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.vigenciaderechos.ComprobanteVigenciaDerechosTEDTO;
import org.apache.commons.lang.StringUtils;

import mx.gob.imss.ctirss.delta.exception.derechohabiente.DerechohabientesWebSserviceException;
import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Consultorio;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Derechohabiente;
import mx.gob.imss.ctirss.delta.model.derechohabiente.MedicoEnTurno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.Turno;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.derechohabiente.dto.vigenciaderechos.BeneficiarioDTO;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;

@Stateless(name = "vigenciaDerechosTEWSClientService", mappedName = "vigenciaDerechosTEWSClientService")
public class VigenciaDerechosTEWSClientService extends AbstractServiceBusiness implements VigenciaDerechosTEWSClientRemote {

    public final static String cpId = "2";
    public final static String defaultStringValue = "-";
    public final static Integer defaultIntegerValue = 0;
    public final static Date defaultDateValue = null;
    public final static SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
    public final static Integer CODIGO_ERROR_WS = 2;

    /**
     * {@inheritDoc}
     */
    @Override
    public ComprobanteVigenciaDerechosTEDTO getInfo(String nss) throws DerechohabientesWebSserviceException {

        return this.getInfo(nss, cpId);
    }

    @Override
    public GrupoFamiliarTE getInfoAsegurado(String nss) throws DerechohabientesWebSserviceException {

        GrupoFamiliarTE grupo = new GrupoFamiliarTE();

        if (!StringUtils.isBlank(nss) && nss.length() == 11) {
            nss = nss.substring(0, 10);
        }

        try {
            WSVigenciaXNSS_Service service = new WSVigenciaXNSS_Service();
            WSVigenciaXNSS ws = service.getWSVigenciaXNSSPort();

            Return respuesta = ws.getInfo(nss, cpId);

            if (respuesta != null && !respuesta.getCodigoError().equals(CODIGO_ERROR_WS)) {
                AsignacionNSS asignacion = new AsignacionNSS();
                asignacion.setNss(nss);
                asignacion.setNssStr(nss);
                asignacion.setEstadoInconsistencia(respuesta.getCodigoError());
                asignacion.setCurp(getStringValue(respuesta.getCurp()));
                asignacion.setNombre(getStringValue(respuesta.getNombre()));
                asignacion.setPrimerApellido(getStringValue(respuesta.getPaterno()));
                asignacion.setSegundoApellido(getStringValue(respuesta.getMaterno()));
                asignacion.setSexo(new Sexo(getStringValue(respuesta.getSexo())));
                asignacion.setIdPersona(getIntegerValue(respuesta.getIdPersona()).longValue());
                asignacion.setFechaNacimientoFormateada(getStringValue(respuesta.getFechaNacimiento()));
                asignacion.setTipoPension(getStringValue(respuesta.getTipoPension()));
                grupo.setAsignacionNSS(asignacion);
                grupo.setAgregadoMedico(getStringValue(respuesta.getAgregadoMedico()));
                grupo.setMedicoEnTurno(new MedicoEnTurno());
                grupo.getMedicoEnTurno().setConsultorio(new Consultorio(getStringValue(respuesta.getConsultorio())));
                grupo.getMedicoEnTurno().setTurno(new Turno(getStringValue(respuesta.getTurno())));
                grupo.getMedicoEnTurno().setUnidadMedicaFamiliar(new UnidadMedicaFamiliar());
                grupo.getMedicoEnTurno().getUnidadMedicaFamiliar().setDescripcion(getStringValue(respuesta.getDhUMF()));
                grupo.getMedicoEnTurno().getUnidadMedicaFamiliar().setNombreCorto(getStringValue(respuesta.getDhUMF()));
                grupo.getMedicoEnTurno().getUnidadMedicaFamiliar().setSubdelegacion(new Subdelegacion());
                grupo.getMedicoEnTurno().getUnidadMedicaFamiliar().getSubdelegacion().setDelegacion(new Delegacion());
                grupo.getMedicoEnTurno().getUnidadMedicaFamiliar().getSubdelegacion().getDelegacion().setDescripcion(getStringValue(respuesta.getDhDeleg()));
                grupo.setDerechohabiente(new Derechohabiente());
                grupo.getDerechohabiente().setAsignacionNSS(asignacion);
                grupo.getDerechohabiente().setCurp(getStringValue(respuesta.getCurp()));
                grupo.getDerechohabiente().setNombre(getStringValue(respuesta.getNombre()));
                grupo.getDerechohabiente().setPrimerApellido(getStringValue(respuesta.getPaterno()));
                grupo.getDerechohabiente().setSegundoApellido(getStringValue(respuesta.getMaterno()));
                grupo.getDerechohabiente().setExpedienteElectronico(getStringValue(respuesta.getIdee()));
                grupo.getDerechohabiente().setSexo(new Sexo(getStringValue(respuesta.getSexo())));
                grupo.getDerechohabiente().setIdPersona(getIntegerValue(respuesta.getIdPersona()).longValue());
                grupo.getDerechohabiente().setFechaNacimientoFormateada(getStringValue(respuesta.getFechaNacimiento()));
                grupo.setConDerechoInc(getStringValue(respuesta.getConDerechoInc()));
                grupo.setConDerechoSm(getStringValue(respuesta.getConDerechoSm()));
                grupo.setArticulo82(respuesta.getArticulo82().getValue().equalsIgnoreCase("SI"));
                grupo.setArticulo83(respuesta.getArticulo83().getValue().equalsIgnoreCase("SI"));
                grupo.setArticulo84(respuesta.getArticulo84().getValue().equalsIgnoreCase("SI"));
                grupo.setArticulo85(respuesta.getArticulo85().getValue().equalsIgnoreCase("SI"));
                grupo.setTiemposEspera(getStringValue(respuesta.getTiemposEspera()));
            } else {
                throw new DerechohabientesWebSserviceException((respuesta != null ? respuesta.getCodigoError() : "") + ", " + (respuesta != null ? respuesta.getMensajeError() : ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
            DerechohabientesWebSserviceException.throwException(e.getMessage());
        }

        return grupo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ComprobanteVigenciaDerechosTEDTO getInfo(String nss, String cpId) throws DerechohabientesWebSserviceException {

        ComprobanteVigenciaDerechosTEDTO comprobante = new ComprobanteVigenciaDerechosTEDTO();
        if (!StringUtils.isBlank(nss) && nss.length() == 11) {
            nss = nss.substring(0, 10);
        }

        try {
            WSVigenciaXNSS_Service service = new WSVigenciaXNSS_Service();
            WSVigenciaXNSS ws = service.getWSVigenciaXNSSPort();

            Return respuesta = ws.getInfo(nss, cpId);

            if (respuesta != null && respuesta.getCodigoError().intValue() != CODIGO_ERROR_WS.intValue()) {
                comprobante.setAgregadoMedico(getStringValue(respuesta.getAgregadoMedico()));
                comprobante.setConsultorio(getStringValue(respuesta.getConsultorio()));
                comprobante.setCurp(getStringValue(respuesta.getCurp()));
                comprobante.setDelegacion(getStringValue(respuesta.getDhDeleg()));
                comprobante.setUmf(getStringValue(respuesta.getDhUMF()));
                comprobante.setFechaNacimientoAsegurado(getStringValue(respuesta.getFechaNacimiento()));
                comprobante.setSegundoApellidoAsegurado(getStringValue(respuesta.getMaterno()));
                comprobante.setNombreAsegurado(getStringValue(respuesta.getNombre()));
                comprobante.setNss(getStringValue(respuesta.getNss()));
                comprobante.setPrimerApellidoAsegurado(getStringValue(respuesta.getPaterno()));
                comprobante.setRegistroPatronal(getStringValue(respuesta.getRegistroPatronal()));
                comprobante.setSexoAsegurado(getStringValue(respuesta.getSexo()));
                comprobante.setTurno(getStringValue(respuesta.getTurno()));
                comprobante.setFechaValidezConstancia(getDateValue(respuesta.getVigenteHasta()));
                comprobante.setIdee(getStringValue(respuesta.getIdee()));
                comprobante.setIdPersona(getIntegerValue(respuesta.getIdPersona()));
                comprobante.setServicioMedico(getStringValue(respuesta.getConDerechoSm()));
                List<BeneficiarioDTO> beneficiarios = new ArrayList<BeneficiarioDTO>();
                if (respuesta.getBeneficiarios() != null && respuesta.getBeneficiarios().size() > 0) {
                    BeneficiarioDTO beneficiario;
                    for (InfoAseguradoVO aseguradoVO : respuesta.getBeneficiarios()) {
                        beneficiario = new BeneficiarioDTO();
                        beneficiario.setNombreBen(getStringValue(aseguradoVO.getNombre()));
                        beneficiario.setPrimerApellidoBen(getStringValue(aseguradoVO.getPaterno()));
                        beneficiario.setSegundoApellidoBen(getStringValue(aseguradoVO.getMaterno()));
                        beneficiario.setFechaNacimientoBen(getDateValue(aseguradoVO.getFechaNacimiento()));
                        beneficiario.setSexoBen(getStringValue(aseguradoVO.getSexo()));
                        beneficiario.setDelegacionBen(getStringValue(aseguradoVO.getDhDeleg()));
                        beneficiario.setUmfBen(getStringValue(aseguradoVO.getDhUMF()));
                        beneficiario.setConsultorio(getStringValue(aseguradoVO.getConsultorio()));
                        beneficiario.setTurno(getStringValue(aseguradoVO.getTurno()));
                        beneficiario.setServicioMedico(getStringValue(aseguradoVO.getConDerechoSm()));
                        beneficiario.setAgregadoMedico(getStringValue(aseguradoVO.getAgregadoMedico()));
                        beneficiario.setIdee(getStringValue(aseguradoVO.getIdee()));
                        beneficiario.setIdPersona(getIntegerValue(aseguradoVO.getIdPersona()));
                        // -----------------------------------------------------------
                        // Se agregara despues al WS
                        // -----------------------------------------------------------
                        beneficiario.setParentescoBen("HIJO");
                        beneficiarios.add(beneficiario);
                    }
                    comprobante.setBeneficiarios(beneficiarios);
                    comprobante.setArticulo82(respuesta.getArticulo82().getValue().equalsIgnoreCase("SI"));
                    comprobante.setArticulo83(respuesta.getArticulo83().getValue().equalsIgnoreCase("SI"));
                    comprobante.setArticulo84(respuesta.getArticulo84().getValue().equalsIgnoreCase("SI"));
                    comprobante.setArticulo85(respuesta.getArticulo85().getValue().equalsIgnoreCase("SI"));
                    comprobante.setTiemposEspera(getStringValue(respuesta.getTiemposEspera()));
                }
            } else {
                throw new DerechohabientesWebSserviceException((respuesta != null ? respuesta.getCodigoError() : "") + ", " + (respuesta != null ? respuesta.getMensajeError() : ""));
            }

        } catch (Exception e) {
            DerechohabientesWebSserviceException.throwException(e.getMessage());
        }

        return comprobante;
    }

    private String getStringValue(JAXBElement<String> value) {

        if (value == null)
            return defaultStringValue;

        if (value.getValue() != null) {
            return value.getValue();
        } else {
            return defaultStringValue;
        }
    }

    private Integer getIntegerValue(JAXBElement<Integer> value) {

        if (value == null)
            return defaultIntegerValue;

        return value.getValue();
    }

    private Date getDateValue(JAXBElement<String> value) {

        try {
            if (value != null && value.getValue() != null)
                return formatter.parse(value.getValue());
        } catch (ParseException e) {
            log.error(e);
        }

        return defaultDateValue;
    }

    @Override
    public String getAgregadoMedico(String nss, Long idPersona) throws DerechohabientesWebSserviceException {

        String resultado = null;
        if (nss == null || "".equals(nss) || idPersona == null || idPersona.intValue() == 0) {
            throw new DerechohabientesWebSserviceException("Parámetros incompletos nss=" + nss + " idPersona:" + idPersona);
        }
        if (nss.length() == 11) {
            nss = nss.substring(0, 10);
        }
        ComprobanteVigenciaDerechosTEDTO comprobanteVigencia = getInfo(nss);
        if (idPersona.intValue() == comprobanteVigencia.getIdPersona()) {
            resultado = comprobanteVigencia.getAgregadoMedico();
        } else {
            for (BeneficiarioDTO beneficiario : comprobanteVigencia.getBeneficiarios()) {
                if (idPersona.intValue() == beneficiario.getIdPersona()) {
                    resultado = beneficiario.getAgregadoMedico();
                    break;
                }
            }
        }
        return resultado;
    }

    @Override
    public SimpleEntry<Integer, String> validarConsistencia(String nss) {

        if (nss.length() == 11) {
            nss = nss.substring(0, 10);
        }
        WSVigenciaXNSS_Service service = new WSVigenciaXNSS_Service();
        WSVigenciaXNSS ws = service.getWSVigenciaXNSSPort();
        Return respuesta = ws.getInfo(nss, cpId);
        return new SimpleEntry<Integer, String>(respuesta.getCodigoError(), respuesta.getMensajeError());
    }

}

