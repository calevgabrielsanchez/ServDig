/**
 * 
 */
package mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import mx.gob.imss.ctirss.delta.exception.domicilio.UmfNoLocalizadaException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.NssRelacionadoVariasPersonasException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaConVariosNSSException;
import mx.gob.imss.ctirss.delta.exception.gestion.asegurado.PersonaSinNSSException;
import mx.gob.imss.ctirss.delta.exception.individuo.PersonasNoLocalizadasException;
import mx.gob.imss.ctirss.delta.framework.exceptions.PersonaNoEncontradaException;
import mx.gob.imss.ctirss.delta.gestion.domicilio.service.interfaces.domicilio.DomicilioServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.individuo.service.business.PersonaFisicaServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.patronal.service.interfaces.SujetoObligadoServiceBusinessRemote;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.exception.IvroException;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.GeneradorMovimientoLocal;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.TramiteIvroServiceLocal;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.model.DatosMovSeguro;
import mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.util.IvroConstants;
import mx.gob.imss.ctirss.delta.model.derechohabiente.UnidadMedicaFamiliar;
import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Domicilio;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.SujetoObligado;
import mx.gob.imss.ctirss.gestionpersonas.servicios.business.PersonaBusinessRemote;
import mx.gob.imss.digital.modelo.derechohabiente.UnidadMedicoFamiliar;
import mx.gob.imss.digital.modelo.persona.Fisica;
import mx.gob.imss.digital.modelo.sindo.MovimientoTrabajadorSindo;
import mx.gob.imss.digital.modelo.sindo.MovimientosTrabajadorSindo;

import org.apache.commons.lang.StringUtils;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementacion de los servicio para generar los objetos a usar en los
 * movimientos de sindo Altas y Bajas de trabajadores
 * 
 * @author NOVUTECK1
 * 
 */
@Stateless(name = "generadorMovimientoEntity", mappedName = "generadorMovimientoEntity")
public class GeneradorMovimientoEntity implements GeneradorMovimientoLocal {

    /**
     * Logger de la clase
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GeneradorMovimientoEntity.class);
    /**
     * Servicio para la consulta de patrones
     */
    @EJB(name = "sujetoObligadoServiceBusiness", mappedName = "sujetoObligadoServiceBusiness")
    private SujetoObligadoServiceBusinessRemote sujetoObligadoServiceBusiness;
    /**
     * Servicoi para la consulta de personas fisicas
     */
    @EJB(name = "personaFisicaServiceBusiness", mappedName = "personaFisicaServiceBusiness")
    private PersonaFisicaServiceBusinessRemote personaFisicaServiceBusiness;
    /**
     * Servicio de personas
     */
    @EJB(name = "personaBusiness", mappedName = "personaBusiness")
    private PersonaBusinessRemote personaBusinessRemote;
    /**
     * Servicios de domicilios
     */
    @EJB(name = "domicilioServiceBusiness", mappedName = "domicilioServiceBusiness")
    private DomicilioServiceBusinessRemote domicilioServiceBusiness;

	@EJB
    private TramiteIvroServiceLocal tramiteIvroService;
    
    /**
     * Mapa que contendra los patrones que encontremos para los registros, este
     * de debe inicializar al iniciar las transformaciones para evitar cache en
     * distintas llamadas, pero que en la misma sea mas eficiente dado que los
     * existen patrones convenciones que se pueden estar repitiendo
     */
    private Map<String, SujetoObligado> patrones = new HashMap<String, SujetoObligado>();
    /**
     * Indica si el movmiento aplica para un trabajador con beneficio
     */
    private static final int CON_BENEFICIO = 1;
    /**
     * Indica si el movmiento aplica para un trabajador sin beneficio
     */
    private static final int SIN_BENEFICIO = 0;
    /**
     * Movimiento de alta de trabajador
     */
    private static final int ALTA = 8;
    /**
     * Movimiento de baja de un trabajador
     */
    private static final int BAJA = 2;
    /**
     * Tipo de movimientos para asegurados
     */
    private static final int MOV_ASEGURADOS = 2;
    /**
     * Origen del movimiento
     */
    private static final int ORIGEN = 0;
    /**
     * Sufijo del folio
     */
    private static final String SUFIJO_FOLIO = "411";
    /**
     * valor para el campo argumento en una alta
     */
    private static final int ARGUMENTO_ALTA = 9;
    /**
     * valor para el campo argumento en una baja
     */
    private static final int ARGUMENTO_BAJA = 0;
    /**
     * valor para el campo argumento en una baja modalidad 35
     */
    private static final int ARGUMENTO_BAJA_MOD_35 = 7;
    /**
     * valor del tipo de traajador
     */
    private static final int TIPO_TRABAJADOR = 1;
    /**
     * Separador al concatenar nombres
     */
    private static final String SEPARADOR_NOMBRE = "$";
    /**
     * UMF default si al trabajdor no se le encunetra una asignada
     */
    private static final int UMF_DEFAULT = 0;
    /**
     * MOdaliad 10
     */
    private static final String MOD_10 = "10";
    /**
     * MOdaliad 13
     */
    private static final String MOD_13 = "13";
    /**
     * MOdaliad 35
     */
    private static final String MOD_35 = "35";

    /**
     * Indica la clave para tipo de pago anual
     */
    private static final int PAGO_ANUAL = 3;

    /**
     * Indica el tipo de pago bimestral
     */
    private static final int PAGO_BIMESTRAL = 5;
    /**
     * Indica el tipo de pago SIN PAGO
     */
    private static final int PAGO_MENSUAL = 0;
    /**
     * Numero maximo de digitos en el nss a enviar a sindo
     */
    private static final int NUM_DIG_NSS = 10;
    /**
     * posicion del digito verificaor en el nss
     */
    private static final int NUM_DIG_VER_NSS = 11;
    /**
     * Numero maximo de digitos en el nrp a enviar a sindo
     */
    private static final int NUM_DIG_NRP = 10;

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * GeneradorMovimientoLocal#generaMovimientosAlta(java.util.List)
     */
    @Override
    public MovimientosTrabajadorSindo generaMovimientosAlta(List<DatosMovSeguro> datos)
            throws IvroException {
        return generaMovimientosAltaS(datos);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * mx.gob.imss.ctirss.delta.gestion.seguroVoluntario.service.interfaces.
     * GeneradorMovimientoLocal#generaMovimientosBaja(java.util.List)
     */
    @Override
    public List<MovimientoTrabajadorSindo> generaMovimientosBaja(List<DatosMovSeguro> datos)
            throws IvroException {
        return generaMovimientos(datos, false);
    }

    /**
     * Servicio para generar el objeto a tilizar en el envio de informacion a
     * sindo de bajas y altas a partir de los datos de los trabajadores que
     * pagaron su segurs o vencieron sus seguros
     * 
     * @param datos
     *            los datos del trabajador y patron
     * @param alta
     *            indicador del tipo e movimiento
     * @return la lista de movimientos del trabajador
     * @throws IvroException Error al generar los movimientos
     */
    private List<MovimientoTrabajadorSindo> generaMovimientos(List<DatosMovSeguro> datos,
            boolean alta) throws IvroException {
        List<MovimientoTrabajadorSindo> movimientos = new ArrayList<MovimientoTrabajadorSindo>();
        patrones = new HashMap<String, SujetoObligado>();
        for (DatosMovSeguro dato : datos) {
            try {
            	SujetoObligado patron = getSujetoObligado(dato.getNrp());
                
            	boolean esDomestico = patron.getModalidad().getNumModalidad().equalsIgnoreCase("34");
                boolean esContVoluntaria = patron.getModalidad().getNumModalidad().equalsIgnoreCase("40");
                boolean esFamiliar = patron.getModalidad().getNumModalidad().equalsIgnoreCase("33");
                Log.error("generando mov sindo - es patron domestico: "+esDomestico);
                Log.error("generando mov sindo - es Cont Voluntaria: "+esContVoluntaria);
                Log.error("generando mov sindo - es Cont Voluntaria: "+esFamiliar);
            	
            	Fisica asegurado = buscaTrabajador(dato.getPersona());
                
            	if(esFamiliar) {
            		asegurado.setUmfAsociado(dato.getPersona().getUmfAsociado());
            	}
            	
            	mx.gob.imss.digital.modelo.domicilio.Domicilio domicilioSeguro = dato.getDomicilioSeguro();
                
                MovimientoTrabajadorSindo movimiento;
				if (dato.isAplicaMovBajaIntegrado()) {
					movimiento = genraMovimiento(patron, asegurado, false, domicilioSeguro);
				} else {
					movimiento = genraMovimiento(patron, asegurado, alta, domicilioSeguro);
				}

                int marcaBeneficio=SIN_BENEFICIO;
                
				if (esDomestico || esContVoluntaria || esFamiliar) {
					marcaBeneficio = SIN_BENEFICIO;
				}

				if (dato.isAplicaMovBajaIntegrado() || alta) {
					movimiento.setfMovto(dato.getFechaInicio());
				} else {
					movimiento.setfMovto(Calendar.getInstance().getTime());
				}

                movimiento.setfRecepMovi(dato.getFechaMovimiento());
                movimiento.setSalBase(dato.getSalario());

				if (esContVoluntaria) {
					movimiento.setReducPago(PAGO_MENSUAL);
				} else if (dato.isPagoBimestral()) {
					movimiento.setReducPago(PAGO_BIMESTRAL);

					if (!esDomestico) {
						marcaBeneficio = CON_BENEFICIO;
					}
                } else {
                    movimiento.setReducPago(PAGO_ANUAL);
                }
                if (dato.isExtemporaneo()) {
                    movimiento.setIdExtemp(1);
                } else {
                    movimiento.setIdExtemp(0);
                }

                movimiento.setDigVrNssCorr(marcaBeneficio);
                
                if (StringUtils.trimToNull(dato.getNrp35()) != null) {
                    movimiento.setDigVrPat(getClaveNumerica(StringUtils.substring(dato.getNrp35(),
                            NUM_DIG_NRP)));
                }
                movimiento.setIdEventual(dato.getIdTipoTrabajador());
                movimientos.add(movimiento);
            } catch (Exception e) {
                LOGGER.error("No se puede generar el movimiento");
            }
        }
        return movimientos;
    }

    /**
     * Genera los movimients de alta
     * 
     * @param datos lista de los movimientos a generar
     * @return la lista de movimientos con formato para sindo
     * @throws IvroException error al proceasr los movimientos
     */
    private MovimientosTrabajadorSindo generaMovimientosAltaS(List<DatosMovSeguro> datos)
            throws IvroException {
        List<DatosMovSeguro> movActuales = new ArrayList<DatosMovSeguro>();
        List<DatosMovSeguro> movFuturos = new ArrayList<DatosMovSeguro>();
        for (DatosMovSeguro dato : datos) {
            if (dato.isMovimientoFuturo()) {
                LOGGER.debug("Movimiento futuro");
                movFuturos.add(dato);
            } else {
                LOGGER.debug("Movimiento normal");
                movActuales.add(dato);
            }
        }

        List<MovimientoTrabajadorSindo> movSindoActuales = generaMovimientos(movActuales, true);
        List<MovimientoTrabajadorSindo> movSindoFuturos = generaMovimientos(movFuturos, true);
        MovimientosTrabajadorSindo movimientos = new MovimientosTrabajadorSindo();
        movimientos.setMovimientoTrabajadorSindo(movSindoActuales
                .toArray(new MovimientoTrabajadorSindo[movSindoActuales.size()]));
        movimientos.setMovimientoTrabajadorSindoFuturo(movSindoFuturos
                .toArray(new MovimientoTrabajadorSindo[movSindoFuturos.size()]));
        return movimientos;
    }

    /**
     * Obtiene el Sujeto obligado a partir del registro patronal
     * 
     * @param rp
     *            el numero de registro patronal
     * @return el patron encontrado
     * @throws IvroException
     *             error al no eonctrar el patron
     */
    private SujetoObligado getSujetoObligado(String rp) throws IvroException {
        // BUscamos el sujeto obligado por su registro patronal
        LOGGER.debug("BUscado al patron {}", new Date());
        if (patrones.containsKey(rp)) {
            return patrones.get(rp);
        }
        SujetoObligado patron = sujetoObligadoServiceBusiness
                .consultarPorNumeroRegistroPatronal(rp);
        LOGGER.debug("Patron encontrado {}", patron);
        if (patron == null) {
            throw new IvroException(IvroConstants.COD_NO_PATRON, IvroConstants.MSG_NO_PATRON);
        }
        LOGGER.debug("Se enontro y se busca su detalle al patron {}", new Date());
        // Agregamos los datos complementarios del sujeto obligado
        patron = sujetoObligadoServiceBusiness.obtenerDetalleRP(patron);
        patrones.put(rp, patron);
        LOGGER.debug("patron  completo{}", new Date());
        return patron;
    }

    /**
     * Busca un trabajador por su nss o id
     * 
     * @param persona
     *            la persona a buscar
     * @return la persona enonctrada
     * @throws IvroException
     *             errores al no encontrar lapersona
     */
    private Fisica buscaTrabajador(Fisica persona) throws IvroException {
        mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica trabajador = null;
        if (StringUtils.trimToNull(persona.getNss()) != null) {
            try {
                trabajador = personaFisicaServiceBusiness.localizarPersonaFisicaPorNss(persona
                        .getNss());
            } catch (PersonasNoLocalizadasException e) {
                LOGGER.error("No se ecuntra al trabajador por su nss ", e);
            } catch (NssRelacionadoVariasPersonasException e) {
                LOGGER.error("El NSS del trabajdor se encuetra varias veces", e);
            }
        } else if (persona.getIdPersona() != null) {
            mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica personaBusqueda = 
                    new mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica();
            personaBusqueda.setIdPersona(persona.getIdPersona());
            try {
                trabajador = (mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica) personaBusinessRemote
                        .buscarPersnaPorID(persona.getIdPersona());
            } catch (PersonaNoEncontradaException e1) {
                LOGGER.warn(
                        "NO debe existir errores de consulta persona ya que se validaron antes", e1);
            }

            if (trabajador != null) {
                try {
                    String nss = personaBusinessRemote.obtenerNssPersona(trabajador.getIdPersona());
                    trabajador.setNss(nss);
                } catch (PersonaConVariosNSSException e) {
                    LOGGER.warn("NO debe existir errores de nss ya que se validaron antes", e);
                } catch (PersonaSinNSSException e) {
                    LOGGER.warn("NO debe existir errores de nss ya que se validaron antes", e);
                }
            }
        }
        Fisica encontrado = null;
        if (trabajador != null) {
            encontrado = copiaValores(trabajador);
        } else {
            throw new IvroException(IvroConstants.COD_NO_TRABAJADOR,
                    IvroConstants.MSG_NO_TRABAJADOR);
        }

        return encontrado;

    }

    /**
     * Genera el objeto de movimiento a partir de los datos del patron y del
     * trabajador
     * 
     * @param patron
     *            el patron asociado al movmiento
     * @param persona
     *            la persona asociada al movimiento
     * @param alta
     *            indicador del tipo e movimiento
     * @param domicilioSeguro 
     * @return el movimiento generado
     */
    private MovimientoTrabajadorSindo genraMovimiento(SujetoObligado patron, Fisica persona,
            boolean alta, mx.gob.imss.digital.modelo.domicilio.Domicilio domicilioSeguro) {
        MovimientoTrabajadorSindo movimiento = new MovimientoTrabajadorSindo();
        Subdelegacion subdelegacion = patron.getSubdelegacion();
        Delegacion delegacion = subdelegacion.getDelegacion();
        movimiento.setDelOrig(getClaveNumerica(delegacion.getClave()));
        movimiento.setCiz(delegacion.getCiz() != null ? delegacion.getCiz() : 0);
        String cveSubdelegacion = subdelegacion.getClave();
        movimiento.setSubOrig(getClaveNumerica(cveSubdelegacion));
        movimiento.setCveAplic(MOV_ASEGURADOS);
        movimiento.setTpMovto(alta ? ALTA : BAJA);
        movimiento.setOrigenMov(ORIGEN);
        String folio = cveSubdelegacion + SUFIJO_FOLIO;
        movimiento.setNumFolio(getClaveNumerica(folio));
        
        String modalidad = patron.getModalidad().getNumModalidad();
        if (modalidad.equalsIgnoreCase(MOD_10) || modalidad.equalsIgnoreCase(MOD_13)) {
            modalidad = MOD_35;
        }
        
		if (alta) {
			movimiento.setArgumento(ARGUMENTO_ALTA);
		} else if (!alta && modalidad.equals(MOD_35)) {
			movimiento.setArgumento(ARGUMENTO_BAJA_MOD_35);
		} else {
			movimiento.setArgumento(ARGUMENTO_BAJA);
		}
        
        String nrp = patron.getNumeroRegistroPatronal() + modalidad;
        movimiento.setRegPatron(StringUtils.substring(nrp, 0, NUM_DIG_NRP));
        movimiento.setDigVrPat(getClaveNumerica(patron.getDigVerificador()));

        movimiento.setIdEventual(TIPO_TRABAJADOR);
        String nss = persona.getNss();
        movimiento.setNumSegSoc(StringUtils.substring(nss, 0, NUM_DIG_NSS));
        movimiento.setDigVrNss(getClaveNumerica(StringUtils.substring(nss, NUM_DIG_NSS, NUM_DIG_VER_NSS)));
        movimiento.setNomAseg(generaNombre(persona.getNombre(), persona.getPrimerApellido(),
                persona.getSegundoApellido()));

        boolean esContVoluntaria = patron.getModalidad().getNumModalidad().equalsIgnoreCase("40");
        boolean esFamiliar = patron.getModalidad().getNumModalidad().equalsIgnoreCase("33");

		int umf = UMF_DEFAULT;

		UnidadMedicaFamiliar umfTrabajador = null;
		if (!esContVoluntaria && !esFamiliar) {
			// Se valida si el trabajador tiene una UMF asociada. Si no tiene UMF
			// asociada se coloca la UMF del centro de trabajo
			LOGGER.debug("========================>> Comienza Validacion de UMF");
			umfTrabajador = getUmfByIdPersona(persona.getIdPersona());
			LOGGER.debug("==>> UMF Trabajador" + umfTrabajador);
		} else if (esContVoluntaria){
			LOGGER.debug("========================>> Comienza Validacion de UMF (mod 40)");
			umfTrabajador = getUmfByDomicilio(domicilioSeguro);
			LOGGER.debug("==>> UMF Trabajador" + umfTrabajador);
		} else if (esFamiliar) {
			LOGGER.debug("========================>> Comienza Validacion de UMF (mod 33)");

			UnidadMedicoFamiliar umfAsociado = persona.getUmfAsociado();
			if (umfAsociado != null) {
				umfTrabajador = new UnidadMedicaFamiliar();

				umfTrabajador.setIdUMF(umfAsociado.getIdUMF());
				umfTrabajador.setNoEconomico(umfAsociado.getNoEconomico());
				umfTrabajador.setSubdelegacion(new Subdelegacion());
				umfTrabajador.getSubdelegacion().setId(umfAsociado.getSubdelegacion().getId());
				umfTrabajador.getSubdelegacion().setClave(umfAsociado.getSubdelegacion().getClave());
				umfTrabajador.getSubdelegacion().setDelegacion(new Delegacion());
				umfTrabajador.getSubdelegacion().getDelegacion().setId(umfAsociado.getSubdelegacion().getDelegacion().getId());
				umfTrabajador.getSubdelegacion().getDelegacion().setClave(umfAsociado.getSubdelegacion().getDelegacion().getClave());
				umfTrabajador.getSubdelegacion().getDelegacion().setCiz(umfAsociado.getSubdelegacion().getDelegacion().getCiz());	
			} else {
				umfTrabajador = getUmfByDomicilio(domicilioSeguro);
			}

			LOGGER.debug("==>> UMF Trabajador" + umfTrabajador);
		}

		if (umfTrabajador != null) {
			Integer cizTrabajador = umfTrabajador.getSubdelegacion().getDelegacion().getCiz();
			Integer cizCentroTrabajo = movimiento.getCiz();

			// Se valida la umf del trabajador. Si el ciz de la UMF localizada
			// coinicide con el ciz del Centro de Trabajo, se asocia la UMF del
			// trabajador; en caso contrario se asocia la UMF del Centro de
			// Trabajo
			if (cizTrabajador != null && cizTrabajador != 0
					&& cizCentroTrabajo != 0
					&& cizTrabajador.equals(cizCentroTrabajo)) {
				umf = umfTrabajador.getNoEconomico().intValue();
				LOGGER.debug("El trabajador tiene asociada la UMF " + umf
						+ " y coincide con el CIZ del patron: ");
			} else {
				UnidadMedicaFamiliar umfCentroTrabajo = getUmfByCentroTrabajo(patron);
				if (umfCentroTrabajo != null) {
					umf = umfCentroTrabajo.getNoEconomico().intValue();
					LOGGER.debug("El trabajador tiene asociada la UMF pero NO coincide con el CIZ del patron, se utilizara la UMF: "
							+ umf);
				}
			}
		} else {
			UnidadMedicaFamiliar umfCentroTrabajo = getUmfByCentroTrabajo(patron);
			if (umfCentroTrabajo != null) {
				umf = umfCentroTrabajo.getNoEconomico().intValue();
				LOGGER.debug("El trabajador tiene asociada la UMF del patron, se utilizara la UMF: "
						+ umf);
			}
		}

		movimiento.setUmf(umf);

		return movimiento;
    }

	/**
     * GEnera una persona fisica del modelo nuevo a partir de los datos de la persona en el modelo viejo
     * @param modeloAnterior persona en el modelo viejo
     * @return la persona en el modelo de imss digital
     */
    private Fisica copiaValores(
            mx.gob.imss.ctirss.delta.model.gestion.individuo.Fisica modeloAnterior) {
        Fisica nuevo = new Fisica();
        nuevo.setIdPersona(modeloAnterior.getIdPersona());
        nuevo.setNss(modeloAnterior.getNss());
        nuevo.setNombre(modeloAnterior.getNombre());
        nuevo.setPrimerApellido(modeloAnterior.getPrimerApellido());
        nuevo.setSegundoApellido(modeloAnterior.getSegundoApellido());
        return nuevo;
    }

    /**
     * Obtiene la clave de una entidad a partir de una cadena convirtiendola en
     * un int
     * 
     * @param clave
     *            la clave a convertir en int
     * @return el valor numerico de la clave
     */
    private int getClaveNumerica(String clave) {
        int claveInt = 0;
        try {
            claveInt = Integer.valueOf(clave);
        } catch (NumberFormatException e) {
            LOGGER.info("La clave de la entidad no es numerica   es = {}", clave);
        }
        return claveInt;
    }

    /**
     * Concatena los nombres de una persona,
     * 
     * @param nombre
     *            el nombre de la persona
     * @param primApellido
     *            el primer apellido de la persona
     * @param segApellido
     *            el segundo apellido de la persona
     * @return el nombre concatenado (Nombre PrimerApellido SegundoApellido)
     */
    private String generaNombre(String nombre, String primApellido, String segApellido) {
        StringBuilder nombreCompleto = new StringBuilder(StringUtils.trimToEmpty(primApellido));
        nombreCompleto.append(SEPARADOR_NOMBRE).append(StringUtils.trimToEmpty(segApellido))
                .append(SEPARADOR_NOMBRE).append(StringUtils.trimToEmpty(nombre));
        return nombreCompleto.toString();
    }

	private UnidadMedicaFamiliar getUmfByIdPersona(Long idPersona) {
		UnidadMedicaFamiliar unidadMedicaFamiliar = null;

		try {
			String nss = personaBusinessRemote.obtenerNssPersona(idPersona);
			unidadMedicaFamiliar = tramiteIvroService.getUnidadMedicoFamiliarByAsignacion(nss, idPersona);
		} catch (PersonaConVariosNSSException e) {
			LOGGER.error("La persona cuenta con mas de un nss, no se devuelve la umf");
		} catch (PersonaSinNSSException e) {
			LOGGER.error("La persona cuenta con mas de un nss, no se devuelve la umf");
		}

		return unidadMedicaFamiliar;
	}
	
	private UnidadMedicaFamiliar getUmfByDomicilio(
			mx.gob.imss.digital.modelo.domicilio.Domicilio domicilioSeguro) {
		UnidadMedicaFamiliar unidadMedicaFamiliar = null;

		if (domicilioSeguro != null
				&& StringUtils.isNotBlank(domicilioSeguro.getCodigoPostal())) {
			List<UnidadMedicaFamiliar> listaUmf;
			try {
				listaUmf = domicilioServiceBusiness
						.getUmfByCodigoPostal(domicilioSeguro.getCodigoPostal());

				if (listaUmf != null && !listaUmf.isEmpty()) {
					unidadMedicaFamiliar = listaUmf.get(0);
				}
			} catch (UmfNoLocalizadaException e) {
				LOGGER.error("El domicilio asociado no cuenta con alguna UMF, no se devuelve la umf");
			}

		}

		return unidadMedicaFamiliar;
	}

	private UnidadMedicaFamiliar getUmfByCentroTrabajo(SujetoObligado patron){
		UnidadMedicaFamiliar unidadMedicaFamiliar = null;

		/*
		 * Se busca UMF a partir del domicilio del patrón, para este caso el
		 * patrón siempre es persona física
		 */
		if (patron.getCntroTrabajo() != null) {
			LOGGER.debug("Centro de trabajo domestico: " + patron.getCntroTrabajo());
			Domicilio domPatron = patron.getCntroTrabajo();
			String cp = null;

			if (domPatron.getCodigoPostal() != null
					&& StringUtils.isNotEmpty(domPatron.getCodigoPostal().getCodigoPostal())) {
				cp = domPatron.getCodigoPostal().getCodigoPostal();
				LOGGER.debug("Codigo postal 1: " + cp);
			} else if (domPatron.getAsentamiento() != null
					&& domPatron.getAsentamiento().getCodigoPostal() != null
					&& StringUtils.isNotEmpty(domPatron.getAsentamiento()
							.getCodigoPostal().getCodigoPostal())) {
				cp = domPatron.getAsentamiento().getCodigoPostal().getCodigoPostal();
				LOGGER.debug("Codigo postal 2: " + cp);
			} else {
				LOGGER.debug("Codigo postal 3");
			}

			if (StringUtils.isNotEmpty(cp)) {
				LOGGER.debug("Se va a buscar UMF con el código postal " + cp
						+ " del domicilio del patrón");
				try {
					List<UnidadMedicaFamiliar> umfs = domicilioServiceBusiness.getUmfByCodigoPostal(cp);
					unidadMedicaFamiliar = umfs.get(0);

					LOGGER.debug("Se localiza la UMF para el código postal " + cp);
				} catch (Exception e) {
					LOGGER.warn(e.getMessage());
				}
			} else {
				LOGGER.debug("El patrón no cuenta con código postal en su domicilio para poder obtener la UMF, se asigna la UMF por default");
			}
		} else {
			LOGGER.debug("El patrón no cuenta con domicilio para obtener la UMF, se asigna la UMF por default");
		}

		return unidadMedicaFamiliar;
	}
}
