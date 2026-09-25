package mx.gob.imss.ctirss.delta.gestion.solicitud.service.business.integration;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.base.service.AbstractServiceBusiness;
import mx.gob.imss.ctirss.delta.gestion.solicitud.service.interfaces.MovimientoAsignacionSIMEBusinessRemote;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sime.MovimientoAsignacionSIMEType;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sime.util.CampoTextoSIME;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.apache.commons.lang.StringUtils.repeat;
import static mx.gob.imss.ctirss.delta.model.gestion.tramite.integracion.sime.util.CampoTextoSIMEBuilder.newCampo;


@Stateless(name="movimientoAsignacionSIMEBusiness", mappedName="movimientoAsignacionSIMEBusiness")
public class MovimientoAsignacionSIMEBusiness extends AbstractServiceBusiness 
    implements MovimientoAsignacionSIMEBusinessRemote {

    private static final Logger log = LoggerFactory.getLogger(MovimientoAsignacionSIMEBusiness.class);

    public String procesarMovimientoAsignacionSIME(MovimientoAsignacionSIMEType movimiento) {

        List<CampoTextoSIME<?>> listaCampos = crearCampos(movimiento);

        StringBuilder resultado = new StringBuilder();

        for (CampoTextoSIME<?> campoTexto:listaCampos) {
            resultado.append(campoTexto.generarValorFormateado());
        }

        return resultado.toString();
    }

    private List<String> procesarRegistros(List<MovimientoAsignacionSIMEType> movimientos) {
        List<String> resultados = new ArrayList<String>();
        for(int i = 0; i < movimientos.size(); i++) {
            log.debug("Movimiento en indice: {}", i);
            resultados.add(procesarMovimientoAsignacionSIME(movimientos.get(i)));
        }
        return resultados;
    }

    private List<CampoTextoSIME<?>> crearCampos(MovimientoAsignacionSIMEType movimiento) {
        List<CampoTextoSIME<?>> listaCampos = new ArrayList<CampoTextoSIME<?>>();
        listaCampos.add(newCampo(movimiento.getRegistroPatronal()        , 11));
        listaCampos.add(newCampo(movimiento.getNss()                     , 11));
        listaCampos.add(newCampo(movimiento.getPrimerApellido()          , 27));
        listaCampos.add(newCampo(movimiento.getSegundoApellido()         , 27));
        listaCampos.add(newCampo(movimiento.getNombre()                  , 27));
        listaCampos.add(newCampo(movimiento.getSalarioBase()             ,  6));
        listaCampos.add(newCampo(movimiento.getCampoGenerico()           ,  6));
        listaCampos.add(newCampo(movimiento.getTipoTrabajor()            ,  1));
        listaCampos.add(newCampo(movimiento.getTipoSalario()             ,  1));
        listaCampos.add(newCampo(movimiento.getJornadaReducida()         ,  1));
        listaCampos.add(newCampo(movimiento.getFechaMovimiento())); //Fecha tiene un ancho fijo de 8 caracteres
        listaCampos.add(newCampo(movimiento.getUnidadMedica()            ,  3));
        listaCampos.add(newCampo(movimiento.getCampoGenerico2()          ,  2));
        listaCampos.add(newCampo(movimiento.getTipoMovimiento()          ,  2));
        listaCampos.add(newCampo(movimiento.getGuia()                    ,  5));
        listaCampos.add(newCampo(movimiento.getClave()                   , 10));
        listaCampos.add(newCampo(movimiento.getCampoGenerico3()          ,  1));
        listaCampos.add(newCampo(movimiento.getCurp()                    , 18));
        listaCampos.add(newCampo(movimiento.getIdentificadorFormato()    ,  1));

        log.warn("Total de campos: {}", listaCampos.size());
        return listaCampos;
    }


    public List<String> procesarMovimientosAsignacionSIME(List<MovimientoAsignacionSIMEType> movimientos) {
        MovimientoAsignacionSIMEType movimiento = movimientos.get(0);
        int identificadorFormato = movimiento.getIdentificadorFormato();
        int guia = movimiento.getGuia();
        List<String> resultados = procesarRegistros(movimientos);
        resultados.add(generarResumen(resultados.size(), identificadorFormato, guia));
        return resultados;
    }

    private String generarResumen(int total, int identificadorFormato, int guia) {
        List<CampoTextoSIME<?>> listaCampos = crearCamposResumen(total, identificadorFormato, guia);
        StringBuilder resumen = new StringBuilder();
        for(CampoTextoSIME<?> campoTexto:listaCampos) {
            resumen.append(campoTexto.generarValorFormateado());
        }

        log.info("{}", resumen);
        return resumen.toString();
    }

    private List<CampoTextoSIME<?>> crearCamposResumen(int total, int identificadorFormato, int guia) {
        List<CampoTextoSIME<?>> listaCampos = new ArrayList<CampoTextoSIME<?>>();
        listaCampos.add(newCampo(repeat("*", 13)       , 13)); // Asteristicos              AN     1 a  13   13}
        listaCampos.add(newCampo(repeat(" ", 43)       , 43)); // Filler                    AN    14 a  56   43
        listaCampos.add(newCampo(total                 ,  6)); // Total de registros        N     57 a  62    6
        listaCampos.add(newCampo(repeat(" ", 71)       , 71)); // Filler                    A     63 a 133   71
        listaCampos.add(newCampo(guia                  ,  5)); // Núm.de Guía               N    134 a 138    5
        listaCampos.add(newCampo(repeat(" ", 29)       , 29)); // Filler                    A    139 a 167   29
        listaCampos.add(newCampo(identificadorFormato  ,  1)); // Identificador de formato  N    168          1
        return listaCampos;
    }
}

