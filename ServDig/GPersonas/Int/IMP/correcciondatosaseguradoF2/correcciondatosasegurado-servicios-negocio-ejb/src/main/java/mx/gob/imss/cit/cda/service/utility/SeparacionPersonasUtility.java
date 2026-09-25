package mx.gob.imss.cit.cda.service.utility;

import mx.gob.imss.ctirss.delta.model.domicilio.EntidadFederativa;
import mx.gob.imss.ctirss.delta.model.domicilio.Pais;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.EstadoCivil;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica;
import mx.gob.imss.ctirss.delta.model.gestion.individuo.Sexo;
import mx.gob.imss.ctirss.delta.persistence.DitBitSeparacionPersonas;

import javax.ejb.Stateless;
import java.util.Date;

@Stateless
public class SeparacionPersonasUtility implements SeparacionPersonasUtilityLocal {

    @Override
    public DitBitSeparacionPersonas convertModelToEntity(Fisica fisica) {

        DitBitSeparacionPersonas bitacora = null;

        if (fisica != null) {
            bitacora = new DitBitSeparacionPersonas();
            bitacora.setCveIdAsignacionNss(fisica.getCveIdAsignacionNSS());
            bitacora.setCveIdPersonaAnterior(fisica.getIdPersona());
            bitacora.setCveIdPersonaNuevo(null);
            bitacora.setCveIdPais(fisica.getPais() != null ? fisica.getPais().getIdPais().longValue() : null);
            bitacora.setCveIdSexo(fisica.getSexo() != null ? fisica.getSexo().getIdSexo().longValue() : null);
            bitacora.setCveIdEstadoCivil(fisica.getEstadoCivil() != null ? fisica.getEstadoCivil().getIdEstadoCivil().longValue() : null);
            bitacora.setNomNombre(fisica.getNombre());
            bitacora.setNomPrimerApellido(fisica.getPrimerApellido());
            bitacora.setNomSegundoApellido(fisica.getSegundoApellido());
            bitacora.setCurp(fisica.getCurp());
            bitacora.setRfc(fisica.getRfc());
            bitacora.setFecNacimiento(fisica.getFechaNacimiento());
            bitacora.setObservaciones(null);
            bitacora.setIndPerAutorizada(1L);
            bitacora.setFecDefuncion(fisica.getFechaDefuncion());
            bitacora.setCveEnt(fisica.getLugarNacimiento() != null ? fisica.getLugarNacimiento().getClave() : null);
            bitacora.setNumAnioNacReg(fisica.getAnioRegistroNac());
            bitacora.setNumMesNacReg(fisica.getMesRegistroNac());
            bitacora.setFecRegistroAlta(new Date());
        }
        return bitacora;
    }

    @Override
    public Fisica convertEntityToModel(DitBitSeparacionPersonas bitacora) {

        Fisica fisica = null;

        if (bitacora != null) {
            fisica = new Fisica();
            fisica.setRfc(bitacora.getRfc());
            fisica.setNombre(bitacora.getNomNombre());
            fisica.setPrimerApellido(bitacora.getNomPrimerApellido());
            fisica.setSegundoApellido(bitacora.getNomSegundoApellido());
            fisica.setFechaNacimiento(bitacora.getFecNacimiento());
            fisica.setFechaDefuncion(bitacora.getFecDefuncion());
            fisica.setFechaRegistro(new Date());
            if (bitacora.getCveEnt() != null) {
                EntidadFederativa entidad = new EntidadFederativa();
                entidad.setClave(bitacora.getCveEnt());
                fisica.setLugarNacimiento(entidad);
            }
            if (bitacora.getCveIdPais() != null) {
                Pais pais = new Pais();
                pais.setIdPais(bitacora.getCveIdPais().intValue());
                fisica.setPais(pais);
            }
            if (bitacora.getCveIdSexo() != null) {
                Sexo sexo = new Sexo();
                sexo.setIdSexo(bitacora.getCveIdSexo().intValue());
                fisica.setSexo(sexo);
            }
            if (bitacora.getCveIdEstadoCivil() != null) {
                EstadoCivil estadoCivil = new EstadoCivil();
                estadoCivil.setIdEstadoCivil(bitacora.getCveIdEstadoCivil().intValue());
                fisica.setEstadoCivil(estadoCivil);
            }
            fisica.setMesRegistroNac(bitacora.getNumMesNacReg());
            fisica.setAnioRegistroNac(bitacora.getNumAnioNacReg());
        }

        return fisica;
    }
}
