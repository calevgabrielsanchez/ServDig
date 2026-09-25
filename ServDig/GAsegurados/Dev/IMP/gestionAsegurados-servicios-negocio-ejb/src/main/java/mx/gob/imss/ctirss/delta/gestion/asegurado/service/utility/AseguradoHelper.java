/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.ctirss.delta.gestion.asegurado.service.utility;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


import mx.gob.imss.ctirss.delta.model.asegurado.AsignacionNSS;
import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.EstadoCivil;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Persona;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.TipoPersona;
import mx.gob.imss.ctirss.delta.persistence.DgCatEstado;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoCivil;
import mx.gob.imss.ctirss.delta.persistence.DicSexo;
import mx.gob.imss.ctirss.delta.persistence.DitAsignacionNss;
import mx.gob.imss.ctirss.delta.persistence.DitPersona;

/**
 * @author cesar.leon
 */
public class AseguradoHelper {

    private static final Logger logger = Logger.getLogger(AseguradoHelper.class);

    private AseguradoHelper() {
        throw new IllegalAccessError("Utility class");
    }

    public static AsignacionNSS persisToModel(DitAsignacionNss entrada)  {

        AsignacionNSS salida = null;

        if (entrada != null) {
            try {
                salida = new AsignacionNSS();
                salida.setIdAsignacionNSS(entrada.getCveIdAsignacionNss());
                salida.setNss(entrada.getNumNss());
                salida.setNssStr(entrada.getNumNss());
                DitPersona persona = entrada.getDitPersona();
                if (!StringUtils.isBlank(persona.getNomNombre())) {
                    salida.setNombre(persona.getNomNombre());
                }

                if (!StringUtils.isBlank(persona.getNomPrimerApellido())) {
                    salida.setPrimerApellido(persona.getNomPrimerApellido());
                }

                if (!StringUtils.isBlank(persona.getNomSegundoApellido())) {
                    salida.setSegundoApellido(persona.getNomSegundoApellido());
                }
                salida.setIdPersona(persona.getCveIdPersona());
                salida.setCurp(persona.getCurp());
                salida.setRfc(persona.getRfc());
                salida.setSexo(AseguradoHelper.persisToModel(persona.getDicSexo()));
                salida.setEstadoCivil(AseguradoHelper.persisToModel(entrada.getDitPersona().getDicEstadoCivil()));
                salida.setFechaNacimiento(entrada.getDitPersona().getFecNacimiento());
                salida.setMesRegistroNac(entrada.getDitPersona().getNumMesNacReg());
                salida.setAnioRegistroNac(entrada.getDitPersona().getNumAnioNacReg());
                salida.setNombreCompleto(
                        salida.getNombre() + " " + salida.getPrimerApellido() + " " + salida.getSegundoApellido());
            } catch (Exception e) {
                logger.error("error al parsear asignacion nss", e);
            }

        }
        return salida;
    }

    public static Sexo persisToModel(DicSexo entrada) {

        Sexo salida = null;
        if (entrada != null) {
            salida = new Sexo();
            try {
                salida.setIdSexo(new Integer("" + entrada.getCveIdSexo()));
                salida.setDescripcion(entrada.getDesSexo());
            } catch (Exception e) {
                logger.error("error al parsear el sexo", e);
            }

        }
        return salida;
    }

    public static EstadoCivil persisToModel(DicEstadoCivil entrada)  {

        EstadoCivil salida = null;
        if (entrada != null) {
            try {
                salida = new EstadoCivil();
                salida.setIdEstadoCivil(new Integer("" + entrada.getCveIdEstadoCivil()));
                salida.setDescripcion(entrada.getDesEstadoCivil());
            } catch (Exception e) {
                logger.error("error al parsear el estado civil", e);

            }
        }
        return salida;
    }


}
