/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.framework.util.DateUtils;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ValidaVigenciaLocal;
import mx.gob.imss.ctirss.delta.persistence.PptPatronPlataforma;
import mx.gob.imss.digital.modelo.sindo.ModalidadTrabajador;
import mx.gob.imss.digital.modelo.sindo.RespuestaValidacionTrabajador;
import mx.gob.imss.digital.modelo.sindo.ResultadoVigenciaContVoluntaria;
import mx.gob.imss.digital.modelo.sindo.ResultadoVigenciaSeguroFamiliar;
import mx.gob.imss.digital.modelo.sindo.ResultadoVigenciaTrabajdor;
import mx.gob.imss.digital.modelo.sindo.VigenciaContVoluntaria;
import mx.gob.imss.digital.modelo.sindo.VigenciaSeguroFamiliar;
import mx.gob.imss.digital.modelo.sindo.VigenciaTrabajdor;


import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author NOVUTECK1
 * 
 */
@Stateless(name = "validaVigenciaEntity", mappedName = "validaVigenciaEntity")
public class ValidaVigenciaEntity implements ValidaVigenciaLocal {

    /**
     * Logger de la clase
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidaVigenciaEntity.class);
    
    /**
     * MOdalidades de una persona que lo hacen no valido para dquirir un seguro
     */
    private static final String[] MODALIDAD_PLATAFORMAS_DIGITALES = new String[] {"10"};
    
    /**
     * MOdalidades de una persona que lo hacen no valido para dquirir un seguro
     */
    private static final String[] MODALIDADES_NO_VALIDAS = new String[] {"10", "13", "14", "17",
            "30"};

    /**
     * MOdalidades de una persona que lo hacen no valido para dquirir un seguro
     * de una modaliad 35
     */
    private static final String[] MODALIDADES_NO_VALIDAS_35 = new String[] {"40","35"};
    
    /**
	 * Modalidades de una persona que lo hacen no valido para continuar un seguro
	 * de una modaliad 35
	 */
	private static final String[] MODALIDADES_NO_VALIDAS_CONTINUAR_35 = new String[] {"40"};
    
    /**
     * MOdalidades de una persona que lo hacen no valido para dquirir un seguro
     * de una modaliad 33
     */
	private static final String[] MODALIDADES_NO_VALIDAS_33 = new String[] {"30", "31", "32", "36", "38", "42"};
	
	/**
     * MOdalidades de una persona que lo hacen no valido para dquirir un seguro
     * de una modaliad 40
     */
	private static final String[] MODALIDADES_NO_VALIDAS_40 = new String[] {"42"};

    /**
     * MOdalidades de una persona que lo hacen no valido para dquirir un seguro
     * de una modaliad 43
     */
    private static final String[] MODALIDADES_NO_VALIDAS_43 = new String[] {"36","38","40","43"};

    /**
	 * Modalidades de una persona que lo hacen no valido para continuar un seguro
	 * de una modaliad 43
	 */
	private static final String[] MODALIDADES_NO_VALIDAS_CONTINUAR_43 = new String[] {"36","38","40"};

    /**
     * Modalidades de una persona que lo hacen no valido para aquirir un seguro
     * de una modaliad 44
     */
    private static final String[] MODALIDADES_NO_VALIDAS_44 = new String[] {"36","38","40","44"};

	/**
	 * Modalidades de una persona que lo hacen no valido para continuar un seguro
	 * de una modaliad 44
	 */
	private static final String[] MODALIDADES_NO_VALIDAS_CONTINUAR_44 = new String[] {"36","38","40"};

	/**
     * MOdalidades de una persona que lo hacen no valido para dquirir un seguro
     * de una modaliad 44
     */
    private static final String[] MODALIDADES_NO_VALIDAS_44_RENOVACION = new String[] {"31","36","38","40"};
	
    /**
     * MOdalidades de una persona que lo hacen no valido para dquirir un seguro
     * individual
     */
    private static final String[] MODALIDADES_IVRO = new String[] {"32", "34", "35", "43", "44"};

	/**
	 * Modalidades IVRO de una persona que lo hacen no valido para dquirir un seguro
	 * individual mod 40 CVRO
	 */
	private static final String[] MODALIDADES_IVRO_NO_40 = new String[] {"34", "35", "43", "44"};
    
    /**
     * MOdalidades que son consideradas para 
     * individual
     */
    private static final String[] MODALIDADES_IVRO_CUESTIONARIO = new String[] {"35", "36", "38", "42", "43","44"};
    
    /**
     * MOdalidades de una persona que lo hacen no valido para dquirir un seguro
     * individual
     */
    private static final String[] MODALIDADES_FACULTATIVO = new String[] {"32", "33"};
    /**
     * MOdalidades de una persona que lo hacen no valido para dquirir una Renovacion seguro
     * individual 
     */
    private static final String[] MODALIDADES_FACULTATIVO_RENOVACION = new String[] {"32"};

    /**
     * Numero de modalidad 35
     */
    private static final String MOD_35 = "35";
    /**
     * Numero de modalidad 33
     */
    private static final String MOD_33 = "33";
    /**
    * Numero de modalidad 34
    */
   private static final String MOD_34 = "34";
    /**
     * Numero de modalidad 40
     */
    private static final String MOD_40 = "40";
    /**
     * Numero de modalidad 43
     */
    private static final String MOD_43 = "43";
    /**
     * Numero de modalidad 44
     */
    private static final String MOD_44 = "44";
    /**
     * Numero de modalidad 10
     */
    private static final String MOD_10 = "10";
    /**
     * MInimo de semanas cotizadas
     */
    private static final Integer MIN_SEMANAS = 52;
    
    /**
     * MInimo de dias transcurridos despues de la fecha de baja de la ultima modalidad 33
     */
    private static final Integer MIN_DIAS_BAJA_SEGURO_FAMILIAR = 45;
    
    /**
     * MInimo de dias transcurridos despues de la fecha de baja de la ultima modalidad 33
     */
    private static final Integer MIN_ANIOS_BAJA_CONT_VOLUNTARIA = 5;
    
    /**
     * Mensaje por no poder contratar seguro
     */
    private static final String MSJ_NO_VALIDO = "Usted no puede realizar la solicitud de Incorporaci\u00F3n Voluntaria, ya que a la fecha se encuentra inscrito en el r\u00E9gimen obligatorio.";

    /**
     * Mensaje por no poder contratar seguro
     */
    private static final String MSJ_NO_VALIDO_MOD = "Usted no puede realizar la solicitud de Incorporaci\u00F3n Voluntaria, ya que a la fecha se encuentra inscrito en el r\u00E9gimen obligatorio.";

    /**
     * Mensaje por no poder contratar seguro
     */
    private static final String MSJ_NO_VALIDO_33 = "No es posible ingresar a la opci\u00F3n de Incorporaci\u00F3n al Seguro de Salud para la Familia, debido a que te encuentras vigente";
    
    private static final String MSJ_NO_VALIDO_RENOVA_33 = "No es posible agregar el integrante para su incorporaci\u00F3n al Seguro de Salud para la Familia, debido a que se encuentra vigente en otra modalidad de aseguramiento, acuda a la Subdelegaci\u00F3n en caso de requerir mayor aclaraci\u00F3n";
    
    private static final String MSJ_NO_VALIDO_RENOVA_34 = "El asegurado se encuentra vigente en el R\u00E9gimen Obligatorio. La Renovaci\u00F3n del Seguro de Trabajador Dom\u00E9stico no puede tramitarse";
    /**
     * Mensaje por no poder contratar seguro
     */
    private static final String MSJ_NO_BAJA_33 = "Usted no puede realizar la solicitud de Incorporaci\u00F3n al Seguro de Salud para la Familia ya que existe un periodo previo de aseguramiento en este seguro, en caso de requerir una renovaci\u00F3n deber\u00E1 acudir a su Subdelegaci\u00F3n";

    /**
     * Mensaje por no poder contratar seguro
     */
    private static final String MSJ_NO_VALIDO_MOD_33 = "Usted no puede realizar la solicitud de Incorporaci\u00F3n al Seguro de Salud para la Familia, ya que actualmente se encuentra vigente en esta modalidad de aseguramiento";

    /**
     * Mensaje por no poder contratar seguro
     */
    private static final String MSJ_NO_VALIDO_MOD_33_RENOVACION = "Usted no puede realizar la solicitud de Renovaci\u00F3n al Seguro de Salud para la Familia, ya que actualmente se encuentra vigente en esta modalidad de aseguramiento";

	/**
     * Mensaje de validacion cuando no se puede renovar modalidad 44 por modalidades incompatibles
     */
	private static final String MSJ_NO_VALIDO_MOD_44_RENOVACION = "La renovaci\u00F3n de su Incorporaci\u00F3n Voluntaria al R\u00E9gimen Obligatorio del Seguro Social no puede tramitarse.";

	/**
	 * Mensaje por no poder contratar seguro
	 */
	private static final String MSJ_NO_PENSIONADO_33 = "Usted no puede realizar la solicitud de Incorporaci\u00F3n al Seguro de Salud para la Familia, ya que actualmente se encuentra jubilado o pensionado o es un trabajador del IMSS";

    /**
     * Mensaje por no poder contratar seguro
     */
    private static final String MSJ_NO_VALIDO_40 = "No es posible ingresar a la opci\u00F3n de Continuaci\u00F3n Voluntaria, debido a que te encuentras vigente en el R\u00E9gimen Obligatorio del Seguro Social";

    /**
     * Mensaje por no poder contratar seguro
     */
    private static final String MSJ_MIN_SEMANAS_COTIZADAS = "No cumples con el requisito de las 52 semanas cotizadas en los \u00FAltimos 5 a\u00F1os, a la fecha de tu baja.";

    /**
     * Mensaje por no poder contratar seguro
     */
    private static final String MSJ_NO_BAJA_40 = "El periodo para solicitar la inscripci\u00F3n en la continuaci\u00F3n voluntaria ha terminado, \u00E9sta debi\u00F3 solicitarse dentro del plazo de cinco a\u00F1os a partir de la fecha de baja.";
	
    /**
	 * Mensaje por no poder contratar seguro
	 */
	private static final String MSJ_NO_PENSIONADO_40 = "Para inscribirte en la continuaci\u00F3n voluntaria, es necesario que no est\u00E9s disfrutando de una pensi\u00F3n de Invalidez, Cesant\u00EDa en Edad Avanzada o Vejez.";	
    /**
	 * Mensaje por no poder contratar seguro
	 */
	private static final String MSJ_NO_PENSIONADO_RENOVACION_40 = "Para tu reingreso en la continuaci\u00F3n voluntaria, es necesario que no est\u00E9s disfrutando de una pensi\u00F3n de Invalidez, Cesant\u00EDa en Edad Avanzada o Vejez.";
	
	
	private static final String MSJ_NO_TRAB_IMSS_40 = "Por este medio no es posible realizar la Inscripci\u00F3n en la Continuaci\u00F3n Voluntaria, por favor acude a tu Subdelegaci\u00F3n a realizar el tr\u00E1mite";

	/**
	 * Mensaje por no poder contratar seguro
	 */
	private static final String MSJ_PERIODO_ABIERTO_40 = "No es posible ingresar a la opci\u00F3n de Continuaci\u00F3n Voluntaria, debido a que ya te encuentras vigente en esta modalidad de aseguramiento.";
	
	/**
	 * Mensaje por no poder contratar seguro
	 */
	private static final String MSJ_PERIODO_BAJA_40 = "Usted no puede realizar la solicitud de Continuaci\u00F3n Voluntaria, ya que existe un periodo previo de aseguramiento en este seguro, en caso de que desee tramitar su reingreso en esta modalidad de aseguramiento, por favor acuda a su Subdelegaci\u00F3n";
	
  /**
	 * Mensaje por no poder pagar en 12 meses
	 */
	private static final String MSJ_E004_FECHA_BAJA = "El periodo para solicitar el reingreso en la Continuaci\u00F3n Voluntaria ha terminado.";
  
	/**
     * Formato del a fecha
     */
    private static final String FORMATO_FECHA = "dd/MM/yyyy";
    private static final String FORMATO_SECUNDARIO_FECHA = "yyyy-MM-dd";
    
	private static final int MIN_LONGITUD_REGPAT = 8;
	private static final int MIN_INDICE_REGPAT = 4;
	
	private static final String REG_PAT_99995 = "99995";
	private static final String REG_PAT_99998 = "99998";
	
	private static final String REG_PAT_09994 = "09994";
	private static final String REG_PAT_99994 = "99994";
	private static final String REG_PAT_09993 = "09993";
	private static final String REG_PAT_09998 = "09998";
	private static final String REG_PAT_99993 = "99993";
	
	private static final String REG_PAT_IMSS = "01051129";
	
	private static final String[] REG_PAT_NO_VALIDOS = new String[] {
			REG_PAT_99995, REG_PAT_99998, REG_PAT_09994, REG_PAT_99994,
			REG_PAT_09993, REG_PAT_09998, REG_PAT_99993 };

	private static final String TIPO_MOVIMIENTO_REINGRESO = "08";
	private static final String TIPO_MOVIMIENTO_BAJA = "02";

    /**
     * Obtiene el valor numerico, a partir de su representaion en string
     *
     * @param clave el valor a obtener su representacion numeica
     * @return el valor numerico de la cadena
     */
	public static final int getClaveNum(String clave) {
		int claveInt = 0;

		try {
			if (StringUtils.isNotBlank(clave)) {
				claveInt = Integer.valueOf(clave);
			} else {
				claveInt = 0;
			}
		} catch (Exception e) {
			claveInt = 0;
		}

		return claveInt;
	}
    
    /*
     * (non-Javadoc)
     *
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * ValidaVigenciaLocal
     * #validaVigenciaTrabajador(mx.gob.imss.digital.modelo.sindo
     * .VigenciaTrabajdor)
     */
    @Override
    public RespuestaValidacionTrabajador validaVigenciaTrabajador(
            VigenciaTrabajdor vigenciaTrabajador) {
        LOGGER.debug("datos recibidos {}", ReflectionToStringBuilder.toString(vigenciaTrabajador));
        int codigoError = getClaveNum(vigenciaTrabajador.getClaveError());
        RespuestaValidacionTrabajador respuesta = new RespuestaValidacionTrabajador();
        if (codigoError == 0) {
            respuesta = validaTrabajador(vigenciaTrabajador.getResultado(),
                    vigenciaTrabajador.getModalidadSolicitada());
            respuesta.setAplicaCuestionario(aplicaCuestionario(vigenciaTrabajador.getResultado()));
        } else {
            respuesta.setValido(false);
            respuesta.setAplicaCuestionario(false);
            respuesta.setMensajeValidacion(vigenciaTrabajador.getMensajeError());
        }
        return respuesta;
    }
 
	@Override
	public RespuestaValidacionTrabajador validaContinuarVigenciaTrabajador(
			VigenciaTrabajdor vigenciaTrabajador) {
		LOGGER.debug("datos recibidos {}", ReflectionToStringBuilder.toString(vigenciaTrabajador));
		int codigoError = getClaveNum(vigenciaTrabajador.getClaveError());
		RespuestaValidacionTrabajador respuesta = new RespuestaValidacionTrabajador();
		if (codigoError == 0) {
			respuesta = validaContinuarTrabajador(vigenciaTrabajador.getResultado(),
					vigenciaTrabajador.getModalidadSolicitada());
			respuesta.setAplicaCuestionario(aplicaCuestionario(vigenciaTrabajador.getResultado()));
		} else {
			respuesta.setValido(false);
			respuesta.setAplicaCuestionario(false);
			respuesta.setMensajeValidacion(vigenciaTrabajador.getMensajeError());
		}
		return respuesta;
	}
   
    @Override
    public RespuestaValidacionTrabajador validaVigenciaTrabajadorRenovacion(
            VigenciaTrabajdor vigenciaTrabajador) {
        LOGGER.debug("datos recibidos {}", ReflectionToStringBuilder.toString(vigenciaTrabajador));
        int codigoError = getClaveNum(vigenciaTrabajador.getClaveError());
        RespuestaValidacionTrabajador respuesta = new RespuestaValidacionTrabajador();
        if (codigoError == 0) {
            respuesta = validaTrabajadorRenovacion(vigenciaTrabajador.getResultado(),
                    vigenciaTrabajador.getModalidadSolicitada());
            respuesta.setAplicaCuestionario(aplicaCuestionario(vigenciaTrabajador.getResultado()));
        } else {
            respuesta.setValido(false);
            respuesta.setAplicaCuestionario(false);
            respuesta.setMensajeValidacion(vigenciaTrabajador.getMensajeError());
        }
        return respuesta;
    }
    
    @Override
    public RespuestaValidacionTrabajador validaVigenciaTrabajadorDomesticoRenovacion(
            VigenciaTrabajdor vigenciaTrabajador) {
        LOGGER.debug("datos recibidos {}", ReflectionToStringBuilder.toString(vigenciaTrabajador));
        int codigoError = getClaveNum(vigenciaTrabajador.getClaveError());
        RespuestaValidacionTrabajador respuesta = new RespuestaValidacionTrabajador();
        if (codigoError == 0) {
            respuesta = validaTrabajadorDomesticoRenovacion(vigenciaTrabajador.getResultado(),
            		MOD_34);
            respuesta.setAplicaCuestionario(aplicaCuestionario(vigenciaTrabajador.getResultado()));
        } else {
            respuesta.setValido(false);
            respuesta.setAplicaCuestionario(false);
            respuesta.setMensajeValidacion(vigenciaTrabajador.getMensajeError());
        }
        return respuesta;
    }

	@Override
	public RespuestaValidacionTrabajador validaVigenciaSeguroFamiliar(
			VigenciaSeguroFamiliar vigenciaSeguroFamiliar) {
		LOGGER.debug("datos recibidos {}", ReflectionToStringBuilder.toString(vigenciaSeguroFamiliar));
		int codigoError = vigenciaSeguroFamiliar.getClaveError();
		RespuestaValidacionTrabajador respuesta = new RespuestaValidacionTrabajador();

		if (codigoError == 0) {
			respuesta = validaTrabajadorSeguroFamiliar(vigenciaSeguroFamiliar.getResultado(), MOD_33);
			respuesta.setAplicaCuestionario(aplicaCuestionario(vigenciaSeguroFamiliar.getResultado()));
		} else {
			respuesta.setValido(false);
			respuesta.setAplicaCuestionario(false);
			respuesta.setMensajeValidacion(vigenciaSeguroFamiliar.getMensajeError());
		}

		return respuesta;
	}
  
  	@Override
	public RespuestaValidacionTrabajador validaVigenciaSeguroFamiliarRenovacion(
			VigenciaSeguroFamiliar vigenciaSeguroFamiliar) {
		LOGGER.debug("datos recibidos {}", ReflectionToStringBuilder.toString(vigenciaSeguroFamiliar));
		int codigoError = vigenciaSeguroFamiliar.getClaveError();
		RespuestaValidacionTrabajador respuesta = new RespuestaValidacionTrabajador();

		if (codigoError == 0) {
			respuesta = validaTrabajadorSeguroFamiliarRenovacion(vigenciaSeguroFamiliar.getResultado(), MOD_33);
			respuesta.setAplicaCuestionario(false);
			respuesta.setAplicaCuestionario(aplicaCuestionario(vigenciaSeguroFamiliar.getResultado()));
		} else {
			respuesta.setValido(false);
			respuesta.setAplicaCuestionario(false);
			respuesta.setMensajeValidacion(vigenciaSeguroFamiliar.getMensajeError());
		}

		return respuesta;
	}


	@Override
	public RespuestaValidacionTrabajador validaVigenciaContinuacionVoluntaria(
			VigenciaContVoluntaria vigenciaContVoluntaria) {
		LOGGER.debug("datos recibidos {}", ReflectionToStringBuilder.toString(vigenciaContVoluntaria));
		int codigoError = getClaveNum(vigenciaContVoluntaria.getClaveError());
		RespuestaValidacionTrabajador respuesta = new RespuestaValidacionTrabajador();

		if (codigoError == 0) {
			respuesta = validaTrabajadorContinuacionVoluntaria(vigenciaContVoluntaria.getModalidad40(), MOD_40);
			respuesta.setAplicaCuestionario(false);
		} else {
			respuesta.setValido(false);
            respuesta.setAplicaCuestionario(false);
            respuesta.setMensajeValidacion(vigenciaContVoluntaria.getMensajeError());
		}

		return respuesta;
	}
  
  @Override
	public RespuestaValidacionTrabajador validaVigenciaContinuacionVoluntariaRenovacion(
			VigenciaContVoluntaria vigenciaContVoluntaria) {
		LOGGER.debug("datos recibidos {}", ReflectionToStringBuilder.toString(vigenciaContVoluntaria));
		int codigoError = getClaveNum(vigenciaContVoluntaria.getClaveError());
		RespuestaValidacionTrabajador respuesta = new RespuestaValidacionTrabajador();

		if (codigoError == 0) {
			respuesta = validaTrabajadorContinuacionVoluntariaRenovacion(vigenciaContVoluntaria.getModalidad40(), MOD_40);
			respuesta.setAplicaCuestionario(false);
		} else {
			respuesta.setValido(false);
            respuesta.setAplicaCuestionario(false);
            respuesta.setMensajeValidacion(vigenciaContVoluntaria.getMensajeError());
		}

		return respuesta;
	}
  
  @Override
	public RespuestaValidacionTrabajador validaVigenciaContinuacionVoluntariaRenovacion(
			List<PptPatronPlataforma> listPatronesPlataformasDig, VigenciaContVoluntaria vigenciaContVoluntaria) {
		LOGGER.debug("datos recibidos {}", ReflectionToStringBuilder.toString(vigenciaContVoluntaria));
		int codigoError = getClaveNum(vigenciaContVoluntaria.getClaveError());
		RespuestaValidacionTrabajador respuesta = new RespuestaValidacionTrabajador();

		if (codigoError == 0) {
			respuesta = validaTrabajadorContinuacionVoluntariaRenovacion(listPatronesPlataformasDig,vigenciaContVoluntaria.getModalidad40(), MOD_40);
			respuesta.setAplicaCuestionario(false);
		} else {
			respuesta.setValido(false);
          respuesta.setAplicaCuestionario(false);
          respuesta.setMensajeValidacion(vigenciaContVoluntaria.getMensajeError());
		}

		return respuesta;
	}
  
  
	/*
     * (non-Javadoc)
     *
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.ValidaVigenciaLocal
     * #revisaCancelaSeguroCambiadoMod40(mx.gob.imss.digital.modelo.sindo.VigenciaContVoluntaria)
     */
	@Override
	public Boolean revisaCancelaSeguroCambiadoMod40(
			VigenciaContVoluntaria vigenciaContVoluntaria) {
		Boolean estaEnRegimenObliogatorio = false;

		ResultadoVigenciaContVoluntaria resultado = vigenciaContVoluntaria.getModalidad40();
		if (resultado.getEstadoVigencia() != 0) {
			if (modalidadContenida(resultado.getListModVigentes(), MODALIDADES_NO_VALIDAS)) {
				estaEnRegimenObliogatorio = true;
			}
		}

		return estaEnRegimenObliogatorio;
	}

    /**
     * Regresa la respuesta de validacion de un trabajador con sus vigencias, es decir si es valido para
     * contratar un seguro y si aplica cuestionario medico en la contratacion
     * @param resultado los datos de vigencia del trabajador
     * @param modalidad la modalidad solicitada para el seguro del trabajador
     * @return la respuesta de la validacion del trabajador
     */
    private RespuestaValidacionTrabajador validaTrabajador(ResultadoVigenciaTrabajdor resultado,
            String modalidad) {
        RespuestaValidacionTrabajador respuesta = new RespuestaValidacionTrabajador();
        respuesta.setValido(true);
        if (resultado.getIndicadorVigente()) {
            if (modalidadContenida(resultado.getModalidadesFechaVigente(), MODALIDADES_NO_VALIDAS)) {
                respuesta.setValido(false);
                respuesta.setMensajeValidacion(MSJ_NO_VALIDO);
            }
            if (modalidadContenidaPorTipo(resultado.getModalidadesFechaVigente(), modalidad)) {
                respuesta.setValido(false);
                respuesta.setMensajeValidacion(MSJ_NO_VALIDO_MOD);
            }
        }
        return respuesta;
    }

	/**
	 * Regresa la respuesta de validacion de un trabajador con sus vigencias, es decir si es valido para
	 * continuar un seguro
	 * @param resultado los datos de vigencia del trabajador
	 * @param modalidad la modalidad solicitada a continuar en el seguro del trabajador
	 * @return la respuesta de la validacion del trabajador
	 */
	private RespuestaValidacionTrabajador validaContinuarTrabajador(ResultadoVigenciaTrabajdor resultado,
														   String modalidad) {
		RespuestaValidacionTrabajador respuesta = new RespuestaValidacionTrabajador();
		respuesta.setValido(true);
		if (resultado.getIndicadorVigente()) {
			if (modalidadContenida(resultado.getModalidadesFechaVigente(), MODALIDADES_NO_VALIDAS)) {
				respuesta.setValido(false);
				respuesta.setMensajeValidacion(MSJ_NO_VALIDO);
			}
			if (continuaModalidadContenidaPorTipo(resultado.getModalidadesFechaVigente(), modalidad)) {
				respuesta.setValido(false);
				respuesta.setMensajeValidacion(MSJ_NO_VALIDO_MOD);
			}
		}
		return respuesta;
	}
	
	private RespuestaValidacionTrabajador validaTrabajadorRenovacion(ResultadoVigenciaTrabajdor resultado,
            String modalidad) {
        RespuestaValidacionTrabajador respuesta = new RespuestaValidacionTrabajador();
        respuesta.setValido(true);
		LOGGER.debug("resultado.getIndicadorVigente() {}", resultado.getIndicadorVigente());
        if (resultado.getIndicadorVigente()) {
			LOGGER.debug("resultado.getIndicadorVigente() {}", resultado.getIndicadorVigente());			
            if (modalidadContenida(resultado.getModalidadesFechaVigente(), MODALIDADES_NO_VALIDAS)) {
                respuesta.setValido(false);
                respuesta.setMensajeValidacion(MSJ_NO_VALIDO);
            }
			if (modalidadContenida(resultado.getModalidadesFechaVigente(), MODALIDADES_NO_VALIDAS_44_RENOVACION)) {
                respuesta.setValido(false);
                respuesta.setMensajeValidacion(MSJ_NO_VALIDO_MOD_44_RENOVACION);
            }
            
        }
        return respuesta;
    }
	

	private RespuestaValidacionTrabajador validaTrabajadorDomesticoRenovacion(ResultadoVigenciaTrabajdor resultado,
            String modalidad) {
        RespuestaValidacionTrabajador respuesta = new RespuestaValidacionTrabajador();
        respuesta.setValido(true);
		LOGGER.debug("resultado.getIndicadorVigente() {}", resultado.getIndicadorVigente());
        if (resultado.getIndicadorVigente()) {
			LOGGER.debug("resultado.getIndicadorVigente() {}", resultado.getIndicadorVigente());			
            if (modalidadContenida(resultado.getModalidadesFechaVigente(), MODALIDADES_NO_VALIDAS)) {
                respuesta.setValido(false);
                respuesta.setMensajeValidacion(MSJ_NO_VALIDO_RENOVA_34);
            }
			           
        }
        
        return respuesta;
	}
    

	private RespuestaValidacionTrabajador validaTrabajadorSeguroFamiliar(
			ResultadoVigenciaSeguroFamiliar resultado, String modalidad) {
		RespuestaValidacionTrabajador respuesta = new RespuestaValidacionTrabajador();
		respuesta.setValido(true);

            if (getClaveNum(resultado.getEstadoVigencia()) != 0) {
                if (modalidadContenida(resultado.getListModVigentes(), MODALIDADES_NO_VALIDAS)
                        || modalidadContenidaPorTipo(resultado.getListModVigentes(), modalidad)) {
                    respuesta.setValido(false);
                    respuesta.setMensajeValidacion(MSJ_NO_VALIDO_33);
                }

                if (modalidadContenida(resultado.getListModVigentes(), MODALIDADES_FACULTATIVO)) {
                    respuesta.setValido(false);
                    respuesta.setMensajeValidacion(MSJ_NO_VALIDO_MOD_33);
                }
            }

            if (respuesta.getValido() && !bajaMenorFechaSolicitud(resultado.getFecUltimaBajaObligatorio(), modalidad)) {
                respuesta.setValido(false);
                respuesta.setMensajeValidacion(MSJ_NO_VALIDO_33);
            }

//            if (respuesta.getValido() && !fechaBajaSeguroFamiliarValida(resultado.getFecUltimaBajaMod33())) {
//                respuesta.setValido(false);
//                respuesta.setMensajeValidacion(MSJ_NO_BAJA_33);
//            }

            if (respuesta.getValido() && (getClaveNum(resultado.getIndPension()) != 0
                    || getClaveNum(resultado.getIndTrabajadorIMSS()) != 0)) {
                respuesta.setValido(false);
                respuesta.setMensajeValidacion(MSJ_NO_PENSIONADO_33);
            }

            return respuesta;
	}
  
  private RespuestaValidacionTrabajador validaTrabajadorSeguroFamiliarRenovacion(
            ResultadoVigenciaSeguroFamiliar resultado, String modalidad) {
        RespuestaValidacionTrabajador respuesta = new RespuestaValidacionTrabajador();
        respuesta.setValido(true);

        if (getClaveNum(resultado.getEstadoVigencia()) != 0) {
            if (modalidadContenida(resultado.getListModVigentes(), MODALIDADES_NO_VALIDAS)
                    || modalidadContenidaPorTipo(resultado.getListModVigentes(), modalidad)) {
                respuesta.setValido(false);
                respuesta.setMensajeValidacion(MSJ_NO_VALIDO_RENOVA_33);
            }
            if (modalidadContenida(resultado.getListModVigentes(), MODALIDADES_FACULTATIVO_RENOVACION)) {
                respuesta.setValido(false);
                respuesta.setMensajeValidacion(MSJ_NO_VALIDO_RENOVA_33);
            }

        }

        if (respuesta.getValido() && !bajaMenorFechaSolicitud(resultado.getFecUltimaBajaObligatorio(), modalidad)) {
            respuesta.setValido(false);
            respuesta.setMensajeValidacion(MSJ_NO_VALIDO_33);
        }

        // Modificar para incluir solo 30 dias antes del vencimiento
//        if (respuesta.getValido() && !fechaBajaSeguroFamiliarValida(resultado.getFecUltimaBajaMod33())) {
//            respuesta.setValido(false);
//            respuesta.setMensajeValidacion(MSJ_NO_BAJA_33);
//        }

        if (respuesta.getValido() && (getClaveNum(resultado.getIndPension()) != 0
                || getClaveNum(resultado.getIndTrabajadorIMSS()) != 0)) {
            respuesta.setValido(false);
            respuesta.setMensajeValidacion(MSJ_NO_PENSIONADO_33);
        }

        return respuesta;
	}
  
    private RespuestaValidacionTrabajador validaTrabajadorContinuacionVoluntariaRenovacion(
            ResultadoVigenciaContVoluntaria resultado, String modalidad) {
        RespuestaValidacionTrabajador respuesta = new RespuestaValidacionTrabajador();
        respuesta.setValido(true);

        if (resultado.getEstadoVigencia() != 0) {
            if (modalidadContenida(resultado.getListModVigentes(), MODALIDADES_NO_VALIDAS)
                    || modalidadContenidaPorTipo(resultado.getListModVigentes(), modalidad)) {
                respuesta.setValido(false);
                respuesta.setMensajeValidacion(MSJ_NO_VALIDO_40);
            }
        }
        /*
		if (respuesta.getValido() && (resultado.getSemanasCotizadas() == null || resultado.getSemanasCotizadas() < MIN_SEMANAS)) {
			respuesta.setValido(false);
			respuesta.setMensajeValidacion(MSJ_MIN_SEMANAS_COTIZADAS);
		}*/
 /*
		if (respuesta.getValido() && !bajaMenorFechaSolicitud(resultado.getFecMovObligatorio(), modalidad)) {
			respuesta.setValido(false);
			respuesta.setMensajeValidacion(MSJ_NO_BAJA_40);
		}*/
        if (respuesta.getValido() && resultado.getIndPension() != 0) {
            respuesta.setValido(false);
            respuesta.setMensajeValidacion(MSJ_NO_PENSIONADO_RENOVACION_40);
        }

        if (respuesta.getValido() && (resultado.getIndTrabajadorIMSS() != 0
                || obtenerIndicadorEmpleadoImss(resultado))) {
            respuesta.setValido(false);
            respuesta.setMensajeValidacion(MSJ_NO_TRAB_IMSS_40);
        }

        if (respuesta.getValido() && existePeriodoAbierto(resultado)) {
            respuesta.setValido(false);
            respuesta.setMensajeValidacion(MSJ_PERIODO_ABIERTO_40);
        }

        Date fechaUltimoMovimiento = DateUtils.sumaDias(DateUtils.dateToDateConFormato(resultado.getFecUltimoMov(), FORMATO_SECUNDARIO_FECHA),1);

        Calendar fechControl = Calendar.getInstance();
        fechControl.add(Calendar.YEAR, -1);
        // Que la fecha de baja tenga mas de un anio
        if (respuesta.getValido() && existeBajaPreviaSeguro(resultado)
                && fechaUltimoMovimiento.before(fechControl.getTime())) {
            respuesta.setValido(false);
            respuesta.setMensajeValidacion(MSJ_E004_FECHA_BAJA);
        }

        return respuesta;
    }
    
    private RespuestaValidacionTrabajador validaTrabajadorContinuacionVoluntariaRenovacion(
    		List<PptPatronPlataforma> listPatronesPlataformasDig, ResultadoVigenciaContVoluntaria resultado, String modalidad) {
        RespuestaValidacionTrabajador respuesta = new RespuestaValidacionTrabajador();
        respuesta.setValido(true);

        if (resultado.getEstadoVigencia() != 0) {
            if (modalidadContenida(resultado.getListModVigentes(), MODALIDADES_NO_VALIDAS)
                    || modalidadContenidaPorTipo(resultado.getListModVigentes(), modalidad)) {
            	
            	respuesta.setValido(false);
                respuesta.setMensajeValidacion(MSJ_NO_VALIDO_40);
            	
            	if (modalidadContenida(resultado.getListModVigentes(), MODALIDAD_PLATAFORMAS_DIGITALES)){
            		
            		if(registroPatronalContenido(listPatronesPlataformasDig,resultado.getListModVigentes())) {
            	        respuesta.setValido(true);
            	        respuesta.setMensajeValidacion(null);
            		}
            		
            	}
            	
            	
                
            }
        }
        /*
		if (respuesta.getValido() && (resultado.getSemanasCotizadas() == null || resultado.getSemanasCotizadas() < MIN_SEMANAS)) {
			respuesta.setValido(false);
			respuesta.setMensajeValidacion(MSJ_MIN_SEMANAS_COTIZADAS);
		}*/
 /*
		if (respuesta.getValido() && !bajaMenorFechaSolicitud(resultado.getFecMovObligatorio(), modalidad)) {
			respuesta.setValido(false);
			respuesta.setMensajeValidacion(MSJ_NO_BAJA_40);
		}*/
        if (respuesta.getValido() && resultado.getIndPension() != 0) {
            respuesta.setValido(false);
            respuesta.setMensajeValidacion(MSJ_NO_PENSIONADO_RENOVACION_40);
        }

        if (respuesta.getValido() && (resultado.getIndTrabajadorIMSS() != 0
                || obtenerIndicadorEmpleadoImss(resultado))) {
            respuesta.setValido(false);
            respuesta.setMensajeValidacion(MSJ_NO_TRAB_IMSS_40);
        }

        if (respuesta.getValido() && existePeriodoAbierto(resultado)) {
            respuesta.setValido(false);
            respuesta.setMensajeValidacion(MSJ_PERIODO_ABIERTO_40);
        }

        Date fechaUltimoMovimiento = DateUtils.sumaDias(DateUtils.dateToDateConFormato(resultado.getFecUltimoMov(), FORMATO_SECUNDARIO_FECHA),1);

        Calendar fechControl = Calendar.getInstance();
        fechControl.add(Calendar.YEAR, -1);
        // Que la fecha de baja tenga mas de un anio
        if (respuesta.getValido() && existeBajaPreviaSeguro(resultado)
                && fechaUltimoMovimiento.before(fechControl.getTime())) {
            respuesta.setValido(false);
            respuesta.setMensajeValidacion(MSJ_E004_FECHA_BAJA);
        }

        return respuesta;
    }

	private RespuestaValidacionTrabajador validaTrabajadorContinuacionVoluntaria(
			ResultadoVigenciaContVoluntaria resultado, String modalidad) {
		RespuestaValidacionTrabajador respuesta = new RespuestaValidacionTrabajador();
		respuesta.setValido(true);

		if (resultado.getEstadoVigencia() != 0) {
			if (modalidadContenida(resultado.getListModVigentes(), MODALIDADES_NO_VALIDAS)
					|| modalidadContenidaPorTipo(resultado.getListModVigentes(), modalidad)) {
                respuesta.setValido(false);
                respuesta.setMensajeValidacion(MSJ_NO_VALIDO_40);
            }
		}

		if (respuesta.getValido() && (resultado.getSemanasCotizadas() == null || resultado.getSemanasCotizadas() < MIN_SEMANAS)) {
			respuesta.setValido(false);
			respuesta.setMensajeValidacion(MSJ_MIN_SEMANAS_COTIZADAS);
		}

		if (respuesta.getValido() && !bajaMenorFechaSolicitud(resultado.getFecMovObligatorio(), modalidad)) {
			respuesta.setValido(false);
			respuesta.setMensajeValidacion(MSJ_NO_BAJA_40);
		}

		if(respuesta.getValido() && resultado.getIndPension() != 0) {
			respuesta.setValido(false);
			respuesta.setMensajeValidacion(MSJ_NO_PENSIONADO_40);
		}

		if (respuesta.getValido() && (resultado.getIndTrabajadorIMSS() != 0
				|| obtenerIndicadorEmpleadoImss(resultado))) {
			respuesta.setValido(false);
			respuesta.setMensajeValidacion(MSJ_NO_TRAB_IMSS_40);
		}

		if (respuesta.getValido() && existePeriodoAbierto(resultado)) {
			respuesta.setValido(false);
			respuesta.setMensajeValidacion(MSJ_PERIODO_ABIERTO_40);
		}
		
		
		if (respuesta.getValido() && existeBajaPreviaSeguro(resultado)) {
			respuesta.setValido(false);
			respuesta.setMensajeValidacion(MSJ_PERIODO_BAJA_40);
		}
		return respuesta;
	}

    /**
     * Verifica si un trabajador aplica cuestionario dada sus condiciones de
     * vigencia
     *
     * @param resultado condciones de vigencia de un trabajador
     * @return true si aplica cuestionario
     */
    private Boolean aplicaCuestionario(ResultadoVigenciaTrabajdor resultado) {
        boolean noAplica = true;
        boolean semanasValids = true;
        if (resultado.getNumeroSemanaAseguramientoBaja() != null
                && resultado.getNumeroSemanaAseguramientoBaja() < MIN_SEMANAS) {
            semanasValids = false;
        }

        ModalidadTrabajador[] modBajas = resultado.getModalidadesFechaBaja();
        if (resultado.getIndicadorVigente()) {
            noAplica = semanasValids
                    && modalidadContenida(resultado.getModalidadesFechaVigente(), MODALIDADES_IVRO);
        } else {
            noAplica = bajaMenorAnio(modBajas, MODALIDADES_FACULTATIVO)
                    || (bajaMenorAnio(modBajas, MODALIDADES_NO_VALIDAS) && semanasValids)
                    || (bajaMenorAnio(modBajas, MODALIDADES_IVRO_CUESTIONARIO) && semanasValids);
        }

        return !noAplica;
    }

    private Boolean aplicaCuestionario(ResultadoVigenciaSeguroFamiliar resultado) {
        boolean aplica = true;
        boolean semanasValidas = false;
		boolean fechaMenorAnio = false;
		boolean fechaMenosCincoAnios = false;

        if (getClaveNum(resultado.getSemanasCotizadas()) >= MIN_SEMANAS) {
            semanasValidas = true;
        }

        Calendar fechaControl = Calendar.getInstance();
        fechaControl.setTime(new Date());
        fechaControl.add(Calendar.YEAR, -1);

        Calendar fechaControlCincoAnios = Calendar.getInstance();
        fechaControlCincoAnios.setTime(new Date());
        fechaControlCincoAnios.add(Calendar.YEAR, -5);

        Date fechaBajaObligatorio = fechaMasReciente(resultado.getFecUltimaBajaObligatorio(), resultado.getFecUltimaBajaMod33());

        if(fechaBajaObligatorio != null && fechaBajaObligatorio.after(fechaControl.getTime())){
			fechaMenorAnio = true;
		}

		if(fechaBajaObligatorio != null && fechaBajaObligatorio.after(fechaControlCincoAnios.getTime())){
			fechaMenosCincoAnios = true;
		}

		LOGGER.info("Es menor a un anio: "+fechaMenorAnio);
        LOGGER.info("Es menor a cinco anios: "+fechaMenosCincoAnios);
        LOGGER.info("Semanas validas: "+semanasValidas);


		//Validaciones para mostrar el cuestionario:

        //1. Si es menor a un año no pide cuestionario
        if(fechaMenorAnio){
            aplica = false;
        }
        //2.Si es entre uno y 5 años, depende de las semanas cotizadas
        else if (fechaMenosCincoAnios){
            //Si son mas de 52 semanas no lo pide
            if(semanasValidas){
                aplica = false;
            }
        }
        //3.Por default si no es menor a un año y tampoco tiene menos de 5, pide cuesionario

        return aplica;
    }


    private Date fechaMasReciente(String fecUltimaBajaObligatorio, String fecUltimaBajaMod33){

    	if(fecUltimaBajaObligatorio==null && fecUltimaBajaMod33 == null){
    		return null;
    	}

    	if(fecUltimaBajaObligatorio==null && fecUltimaBajaMod33 != null){
    		return DateUtils.dateToDateConFormato(
        			fecUltimaBajaMod33, FORMATO_SECUNDARIO_FECHA);
    	}

    	if(fecUltimaBajaObligatorio!=null && fecUltimaBajaMod33 == null){
    		return DateUtils.dateToDateConFormato(
					fecUltimaBajaObligatorio, FORMATO_SECUNDARIO_FECHA);
    	}

    	Date fechaBajaObligatorio = DateUtils.dateToDateConFormato(
				fecUltimaBajaObligatorio, FORMATO_SECUNDARIO_FECHA);

    	Date fechaBajaMod33 = DateUtils.dateToDateConFormato(
    			fecUltimaBajaMod33, FORMATO_SECUNDARIO_FECHA);

    	if(fechaBajaObligatorio.after(fechaBajaMod33)){
    		return fechaBajaObligatorio;
    	}else{
    		return fechaBajaMod33;
    	}
    }

    /**
     * Indica si un trabajador estubo dado de baja antes de un a?o en aluguna de
     * las modalidades indicadas
     *
     * @param mods modaliaddes del trabajador con la fecha
     * @param modalidades modaliades del trabajador que nos interesan saber su fecha de baja menor
     * @return true si el trabajador cuenta con una modalidad valiad y con menos de un a?o de baja
     */
    private boolean bajaMenorAnio(ModalidadTrabajador[] mods, String[] modalidades) {
        boolean aplica = false;
        if (mods != null) {
            Calendar fechControl = Calendar.getInstance();
            fechControl.add(Calendar.YEAR, -1);
            for (ModalidadTrabajador mod : mods) {
                Date fecha = DateUtils.dateToDateConFormato(mod.getFecha(), FORMATO_FECHA);
                if (ArrayUtils.contains(modalidades, mod.getModalidad())
                        && fecha.after(fechControl.getTime())) {
                    aplica = true;
                }
            }
        }
        return aplica;
    }
	
    private boolean bajaMenorFechaSolicitud(String strFecha, String modalidad) {
        boolean aplica = false;

        if (StringUtils.isNotBlank(strFecha)) {
            if (StringUtils.isNotBlank(modalidad) && StringUtils.equals(modalidad, MOD_33)) {
                Calendar fechaSistema = Calendar.getInstance();
                Date fecha = DateUtils.dateToDateConFormato(strFecha, FORMATO_SECUNDARIO_FECHA);
                Date fechaControl = DateUtils.getFechaCeroHoras(fechaSistema.getTime());

                if (fecha.before(fechaControl)) {
                    aplica = true;
                }
            } else if (StringUtils.isNotBlank(modalidad) && StringUtils.equals(modalidad, MOD_40)) {
                Calendar fechaControl = Calendar.getInstance();
                fechaControl.add(Calendar.YEAR, (MIN_ANIOS_BAJA_CONT_VOLUNTARIA * -1));
                Date fecha = DateUtils.dateToDateConFormato(strFecha, FORMATO_SECUNDARIO_FECHA);

                if (fecha.after(fechaControl.getTime())) {
                    aplica = true;
                }
            } else {
                aplica = true;
            }
        } else {
            aplica = true;
        }

        return aplica;
    }

	private boolean fechaBajaSeguroFamiliarValida(String strFecha){
		boolean isFechaValida = false;

		if (StringUtils.isNotBlank(strFecha)) {
			Date fechaControl = DateUtils.sumaDias(Calendar.getInstance().getTime(),
					(MIN_DIAS_BAJA_SEGURO_FAMILIAR * -1));
			Date fecha = DateUtils.dateToDateConFormato(strFecha,
					FORMATO_SECUNDARIO_FECHA);

			if (fecha.before(fechaControl)) {
				isFechaValida = true;
			}
		} else {
			isFechaValida = true;
		}

		return isFechaValida;
	}

    /**
     * Verifica si una modalidad esta contenida en un arreglo de modalidades
     *
     * @param modalidades lista de modaliades a buscar si se encuentran contenidas dentro de las vigentes
     * @param modalidadesVig lista de modaliaddes vigentes
     * @return true si alguna modaliad esta contenida
     */
    private boolean modalidadContenida(ModalidadTrabajador[] modalidadesVig, String[] modalidades) {
        boolean contenida = false;
        if (modalidadesVig != null) {
            for (ModalidadTrabajador mod : modalidadesVig) {
				LOGGER.debug("   mod.getModalidad() {}", mod.getModalidad());			
                if (ArrayUtils.contains(modalidades, mod.getModalidad())) {
                    contenida = true;
                }
            }
        }
        return contenida;
    }

    /**
     * Indica si la modalidad esta contenida dentro de un arreglo de modalidades
     * @param modalidadesVig lista de modalidades vigentes
     * @param modalidad la modaliad a verificar si esta vigente
     * @return true si la modaliad a buscar esta vigente
     */
	private boolean modalidadContenidaPorTipo(ModalidadTrabajador[] modalidadesVig, String modalidad) {
		boolean contenida = false;

        if(StringUtils.isNotBlank(modalidad)){
            if (StringUtils.equals(modalidad, MOD_35)) {
                contenida = modalidadContenida(modalidadesVig, MODALIDADES_NO_VALIDAS_35);
            } else if (StringUtils.equals(modalidad, MOD_33)) {
                contenida = (modalidadContenida(modalidadesVig, MODALIDADES_IVRO) || modalidadContenida(modalidadesVig, MODALIDADES_NO_VALIDAS_33));
            } else if (StringUtils.equals(modalidad, MOD_40)) {
                contenida = (modalidadContenida(modalidadesVig, MODALIDADES_IVRO_NO_40)
                        || modalidadContenida(modalidadesVig, MODALIDADES_NO_VALIDAS_40));
            } else if (StringUtils.equals(modalidad, MOD_43)) {
                contenida = modalidadContenida(modalidadesVig, MODALIDADES_NO_VALIDAS_43);
            } else if (StringUtils.equals(modalidad, MOD_44)) {
                contenida = modalidadContenida(modalidadesVig, MODALIDADES_NO_VALIDAS_44);
            }
        }

		return contenida;
	}
	
	/**
	 * Indica si la modalidad esta contenida dentro de un arreglo de modalidades
	 * @param modalidadesVig lista de modalidades vigentes
	 * @param modalidad la modaliad a verificar si esta vigente
	 * @return true si la modaliad a buscar esta vigente
	 */
	private boolean continuaModalidadContenidaPorTipo(ModalidadTrabajador[] modalidadesVig, String modalidad) {
		boolean contenida = false;

		if(StringUtils.isNotBlank(modalidad)){
			if (StringUtils.equals(modalidad, MOD_35)) {
				contenida = modalidadContenida(modalidadesVig, MODALIDADES_NO_VALIDAS_CONTINUAR_35);
			} else if (StringUtils.equals(modalidad, MOD_43)) {
				contenida = modalidadContenida(modalidadesVig, MODALIDADES_NO_VALIDAS_CONTINUAR_43);
			} else if (StringUtils.equals(modalidad, MOD_44)) {
				contenida = modalidadContenida(modalidadesVig, MODALIDADES_NO_VALIDAS_CONTINUAR_44);
			}
		}

		return contenida;
	}
	
	private boolean existePeriodoAbierto(
			ResultadoVigenciaContVoluntaria resultado) {
		boolean existePeriodoAbierto = false;
		Date fechaUltimoMovimiento = DateUtils.dateToDateConFormato(resultado.getFecUltimoMov(), FORMATO_SECUNDARIO_FECHA);
		String modalidadUltimoMovimiento = resultado.getModUltimoMov();
		String tipoMovimiento = resultado.getTipoUltimoMov();

		Date fechaMovObligatorio = DateUtils.dateToDateConFormato(resultado.getFecMovObligatorio(), FORMATO_SECUNDARIO_FECHA);

		if (StringUtils.isNotBlank(modalidadUltimoMovimiento)
				&& StringUtils.equals(modalidadUltimoMovimiento, MOD_40)
				&& fechaUltimoMovimiento != null && fechaMovObligatorio != null
				&& fechaUltimoMovimiento.after(fechaMovObligatorio)
				&& StringUtils.isNotBlank(tipoMovimiento)
				&& StringUtils.equals(tipoMovimiento, TIPO_MOVIMIENTO_REINGRESO)) {
			existePeriodoAbierto = true;
		}

		return existePeriodoAbierto;
	}

	private boolean existeBajaPreviaSeguro(
			ResultadoVigenciaContVoluntaria resultado) {
		boolean existePeriodoAbierto = false;
		Date fechaUltimoMovimiento = DateUtils.dateToDateConFormato(resultado.getFecUltimoMov(), FORMATO_SECUNDARIO_FECHA);
		String modalidadUltimoMovimiento = resultado.getModUltimoMov();
		String tipoMovimiento = resultado.getTipoUltimoMov();

		//Date fechaMovObligatorio = DateUtils.dateToDateConFormato(resultado.getFecMovObligatorio(), FORMATO_SECUNDARIO_FECHA);
		Date fechaMovObligatorio = fechaUltimoMovimiento;//DateUtils.dateToDateConFormato(resultado.getFecMovObligatorio(), FORMATO_SECUNDARIO_FECHA);

		if (StringUtils.isNotBlank(modalidadUltimoMovimiento)
				&& StringUtils.equals(modalidadUltimoMovimiento, MOD_40)
				&& fechaUltimoMovimiento != null && fechaMovObligatorio != null
				&& fechaUltimoMovimiento.after(fechaMovObligatorio)
				&& StringUtils.isNotBlank(tipoMovimiento)
				&& StringUtils.equals(tipoMovimiento, TIPO_MOVIMIENTO_BAJA)) {
			existePeriodoAbierto = true;
		}

		return existePeriodoAbierto;
	}

	private boolean obtenerIndicadorEmpleadoImss(
			ResultadoVigenciaContVoluntaria resultado) {
		boolean isEmpleadoImss = false;

		String modalidadUltimoMovimiento = resultado.getModUltimoMov();
		String regPatUltimoMovimiento = resultado.getRegPatUltimoMov();

		String modalidadMovObligatorio = resultado.getModUltimoObligatorio();
		String regPatMovObligatorio = resultado.getRegPatUltimoObligatorio();

		if (StringUtils.isNotBlank(modalidadMovObligatorio)
				&& StringUtils.equals(modalidadMovObligatorio, MOD_10)
				&& StringUtils.isNotBlank(regPatMovObligatorio)
				&& regPatMovObligatorio.length() >= MIN_LONGITUD_REGPAT) {
			String registroPatronal = regPatMovObligatorio.substring(
					MIN_INDICE_REGPAT - 1, MIN_LONGITUD_REGPAT);

			if (ArrayUtils.contains(REG_PAT_NO_VALIDOS, registroPatronal)) {
				isEmpleadoImss = true;
			} else if (regPatMovObligatorio.equals(REG_PAT_IMSS)) {
				isEmpleadoImss = true;
			}
		}

		if (StringUtils.isNotBlank(modalidadUltimoMovimiento)
				&& StringUtils.equals(modalidadUltimoMovimiento, MOD_10)
				&& StringUtils.isNotBlank(regPatUltimoMovimiento)
				&& regPatUltimoMovimiento.length() >= MIN_LONGITUD_REGPAT) {
			String registroPatronal = regPatUltimoMovimiento.substring(
					MIN_INDICE_REGPAT - 1, MIN_LONGITUD_REGPAT);

			if (ArrayUtils.contains(REG_PAT_NO_VALIDOS, registroPatronal)) {
				isEmpleadoImss = true;
			} else if (regPatUltimoMovimiento.equals(REG_PAT_IMSS)) {
				isEmpleadoImss = true;
			}
		}

		return isEmpleadoImss;
	}

	/** valida si la fecha actual es mayor a 12 meses de la fecha de baja
	 * 
	 */
	private boolean valida12MesesDespues(String fechaBaja){
		Date baja = DateUtils.dateToDateConFormato(fechaBaja, FORMATO_SECUNDARIO_FECHA);
		Calendar actual = Calendar.getInstance();
		actual.setTime(new Date());
		actual.add(Calendar.MONTH, -12);
		return baja.after(actual.getTime());
	}
	
	
	/**
     * Verifica si un registro patronal esta contenida en un arreglo de patrones de plataformas digitales
     *
     * @param registrosPatronales lista de registros patronales que son de plataformas digitales
     * @param regPatVigentes lista de registros patronales vigentes
     * @return true si alguna modaliad esta contenida
     */
    private boolean registroPatronalContenido(List<PptPatronPlataforma> regPatPlatDig, ModalidadTrabajador[] regPatVigentes) {
    	boolean resultado = false;
    	boolean esPlataformaDigital = false;
        
        if (regPatVigentes != null) {
        	
        	
            for (ModalidadTrabajador regPatVig : regPatVigentes) {
				LOGGER.debug("   mod.getRegistroPatronal {}", regPatVig.getRegistroPatronal());	
				
				if(!regPatVig.getModalidad().trim().equals(MOD_10) &&!regPatVig.getModalidad().trim().equals(MOD_40)
						&& !regPatVig.getModalidad().trim().equals("32")&&!regPatVig.getModalidad().trim().equals("33")
						&&!regPatVig.getModalidad().trim().equals("36")&&!regPatVig.getModalidad().trim().equals("38")
						){
					esPlataformaDigital = false;
					break;
				}
				
				esPlataformaDigital = false;
				for(PptPatronPlataforma regPatPD: regPatPlatDig) {
					
					LOGGER.debug("lista RegistroPatronal {}", regPatPD.getCveRegPatron());
					
					
					if (regPatVig.getModalidad().trim().equals(MOD_40)
							||regPatVig.getModalidad().trim().equals("32")||regPatVig.getModalidad().trim().equals("33")
							||regPatVig.getModalidad().trim().equals("36")||regPatVig.getModalidad().trim().equals("38")
							){
						LOGGER.info("Es mod40, renovacion, modalidades compatibles te deja pasar");
						esPlataformaDigital = true;
						break;
					}else if(regPatVig.getRegistroPatronal().trim().toUpperCase().equals(regPatPD.getCveRegPatron().trim().toUpperCase())) {
						LOGGER.info("***Se encontro una modalidad 10 que se encuentra dentro de los Registros Patronales de Plataformas Digitales: "+regPatVig.getRegistroPatronal());
						esPlataformaDigital = true;		
						break;
					}
				
				}
				
				if(esPlataformaDigital == false){
					break;
				}
				
            }
        }
        
        LOGGER.info("resultado: "+esPlataformaDigital);
        resultado = esPlataformaDigital;
        
        
        return resultado;
    }

    
//    public static void main(String []argv){
//    	ModalidadTrabajador[] regPatVigentes = new ModalidadTrabajador[2];
//    	
//    	ModalidadTrabajador trabajador1 = new ModalidadTrabajador();
//    	trabajador1.setModalidad(MOD_10);
//    	trabajador1.setRegistroPatronal("Y6128902");
//    	
//    	ModalidadTrabajador trabajador2 = new ModalidadTrabajador();
//    	trabajador2.setModalidad(MOD_10);
//    	trabajador2.setRegistroPatronal("F5411362");
//    	
//    	//ModalidadTrabajador trabajador3 = new ModalidadTrabajador();
//    	//trabajador3.setModalidad(MOD_10);
//    	//trabajador3.setRegistroPatronal("Y6128902");
//    	
//    	regPatVigentes[0] = trabajador1;
//    	regPatVigentes[1] = trabajador2;
//    	//regPatVigentes[2] = trabajador3;
//    	
//    	List<PptPatronPlataforma> listaPatrones = new ArrayList<PptPatronPlataforma>();
//    	
//    	PptPatronPlataforma patron1 = new PptPatronPlataforma();
//    	patron1.setCveModalidad(10);
//    	patron1.setCveRegPatron("Y5299994");
//    	patron1.setNumDigVer(5);
//    	listaPatrones.add(patron1);
//    	
//    	PptPatronPlataforma patron2 = new PptPatronPlataforma();
//    	patron2.setCveModalidad(10);
//    	patron2.setCveRegPatron("Y6128903");
//    	patron2.setNumDigVer(8);
//    	listaPatrones.add(patron2);
//    	
//    	PptPatronPlataforma patron3 = new PptPatronPlataforma();
//    	patron3.setCveModalidad(10);
//    	patron3.setCveRegPatron("Y6065390");
//    	patron3.setNumDigVer(3);
//    	listaPatrones.add(patron3);
//    	
//    	PptPatronPlataforma patron4 = new PptPatronPlataforma();
//    	patron4.setCveModalidad(10);
//    	patron4.setCveRegPatron("Y6061999");
//    	patron4.setNumDigVer(5);
//    	listaPatrones.add(patron4);
//    	
//    	PptPatronPlataforma patron5 = new PptPatronPlataforma();
//    	patron5.setCveModalidad(10);
//    	patron5.setCveRegPatron("Y5068924");
//    	patron5.setNumDigVer(8);
//    	listaPatrones.add(patron5);
//    	
//    	PptPatronPlataforma patron6 = new PptPatronPlataforma();
//    	patron6.setCveModalidad(10);
//    	patron6.setCveRegPatron("Y5487972");
//    	patron6.setNumDigVer(0);
//    	listaPatrones.add(patron6);
//    	
//    	PptPatronPlataforma patron7 = new PptPatronPlataforma();
//    	patron7.setCveModalidad(10);
//    	patron7.setCveRegPatron("Y5487973");
//    	patron7.setNumDigVer(8);
//    	listaPatrones.add(patron7);
//    	
//    	PptPatronPlataforma patron8 = new PptPatronPlataforma();
//    	patron8.setCveModalidad(10);
//    	patron8.setCveRegPatron("Y6128902");
//    	patron8.setNumDigVer(0);
//    	listaPatrones.add(patron8);
//    	
//    	ValidaVigenciaEntity inicio = new ValidaVigenciaEntity();
//    	boolean resultado = inicio.registroPatronalContenido(listaPatrones, regPatVigentes);
//    	
//    	System.out.println("resultado: "+resultado);
//    	
//    }
}
