package mx.gob.imss.distss.delta.rtt.service.util;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Date;
import java.util.Calendar;
import java.util.Locale;
import java.text.SimpleDateFormat;

import mx.gob.imss.ctirss.delta.model.domicilio.Delegacion;
import mx.gob.imss.ctirss.delta.model.domicilio.Subdelegacion;
import mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.OrigenSolicitudEnum;
import mx.gob.imss.ctirss.delta.model.gestion.solicitud.Solicitud;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.Tramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.EstadoTramite;
import mx.gob.imss.ctirss.delta.model.gestion.tramite.TipoTramite;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.PatronRiesgosTrabajo;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.ReporteRiesgoTrabajo;
import mx.gob.imss.ctirss.delta.model.riesgosTrabajo.RiesgoTrabajo;
import mx.gob.imss.ctirss.delta.persistence.DicDelegacion;
import mx.gob.imss.ctirss.delta.persistence.DicEstadoTramite;
import mx.gob.imss.ctirss.delta.persistence.DicSubdelegacion;
import mx.gob.imss.ctirss.delta.persistence.DicTipoTramite;
import mx.gob.imss.ctirss.delta.persistence.DitLlavePatron;
import mx.gob.imss.ctirss.delta.persistence.DitSolicitud;
import mx.gob.imss.ctirss.delta.persistence.DitTramite;
import mx.gob.imss.ctirss.delta.persistence.RttRegistro;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaMoral;
import mx.gob.imss.ctirss.delta.persistence.DitPersonaFisica;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilRTT {

    public static final String FORMAT_DATE = "dd/MMM/yyyy hh:mm:ss";
    public static final String[] COLUMNAS_REPORTE = {"Folio(s) Escrito Desacuerdo", "Registro Patronal", "Nombre o Raz\u00F3n Social", "Delegaci\u00F3n", "SubDelegaci\u00F3n", "Materia", "Fecha de Presentaci\u00F3n", "Materia de Prima", "Motivo"};

    private static final int DECENA = 10;

    private static final int DEFUNCION = 4;

    private static final int RECAIDA = 5;

    private static final int RECAIDA2 = 6;

    private static final int PERIODOHISTORIAL = 5;

    private static final Logger LOGGER = LoggerFactory.getLogger(UtilRTT.class);

    /**
     * Metodo para convetir una lista de RttRegistro a una lista de
     * RiesgoTrabajoDTO
     *
     * @param riesgosTrabajo
     * @return
     */
    public static List<RiesgoTrabajo> getDataRiesgosTrabajo(List<RttRegistro> riesgosTrabajo) {

        List<RiesgoTrabajo> riesgos = new ArrayList<RiesgoTrabajo>();
        int contador = 1;

        for (RttRegistro riesgotrabajo : riesgosTrabajo) {

            RiesgoTrabajo riesgoTrabajoDTO = new RiesgoTrabajo();

            //Seteamos los datos al objeto RiesgosTrabajo
            riesgoTrabajoDTO.setConsec(String.valueOf(contador++));
            riesgoTrabajoDTO.setDv(calcularDigitoVerificador(riesgotrabajo.getRttNumNss()));
            riesgoTrabajoDTO.setRfc(riesgotrabajo.getRttNumNss());
            riesgoTrabajoDTO.setNumSegSocial(riesgotrabajo.getRttNumNss());
            riesgoTrabajoDTO.setCurp(riesgotrabajo.getRttRefCurp());
            riesgoTrabajoDTO.setNombreAsegurado(armarNombreAsegurado(riesgotrabajo));
            riesgoTrabajoDTO.setRecaidaRevaluacion(validarRecaida(riesgotrabajo.getRttNumConsecuencia()));
            riesgoTrabajoDTO.setFechaAccidente(riesgotrabajo
                    .getRttFecInicioAccidente());
            riesgoTrabajoDTO.setTipoRiesgo(String.valueOf(riesgotrabajo.getRttNumTipoRiesgo()));
            riesgoTrabajoDTO.setDiasSubsidiados(String.valueOf(riesgotrabajo.getRttDiasSubsidiados()));
            riesgoTrabajoDTO.setPorcentajeIncapac(String.valueOf(riesgotrabajo.getRttPorIncapacidad()));
            riesgoTrabajoDTO.setDefuncion(validarDefuncion(riesgotrabajo.getRttNumConsecuencia()));
            riesgoTrabajoDTO.setFechaAlta(riesgotrabajo
                    .getRttFecFinAccidente());

            //agregamos el riesgo de trabajpo a la lista
            riesgos.add(riesgoTrabajoDTO);
        }

        return riesgos;
    }

    /**
     * Metodo para convetir una lista de RttRegistro a una lista de
     * RiesgoTrabajoDTO
     *
     * @param riesgosTrabajo
     * @return
     */
    public static List<RiesgoTrabajo> getDataRiesgosTrabajoXRfc(List<RttRegistro> riesgosTrabajo) {

        List<RiesgoTrabajo> riesgos = new ArrayList<RiesgoTrabajo>();
        int contador = 1;

        for (RttRegistro riesgotrabajo : riesgosTrabajo) {
            RiesgoTrabajo riesgoTrabajoDTO = new RiesgoTrabajo();

            //Seteamos los datos al objeto RiesgosTrabajo
            riesgoTrabajoDTO.setConsec(String.valueOf(contador++));
            riesgoTrabajoDTO.setDv(calcularDigitoVerificador(riesgotrabajo.getRttNumNss()));
            riesgoTrabajoDTO.setRfc(riesgotrabajo.getRttNumNss());
            riesgoTrabajoDTO.setNumSegSocial(riesgotrabajo.getRttNumNss());
            riesgoTrabajoDTO.setCurp(riesgotrabajo.getRttRefCurp());
            riesgoTrabajoDTO.setNombreAsegurado(armarNombreAsegurado(riesgotrabajo));
            riesgoTrabajoDTO.setRecaidaRevaluacion(validarRecaida(riesgotrabajo.getRttNumConsecuencia()));
            riesgoTrabajoDTO.setFechaAccidente(riesgotrabajo.getRttFecInicioAccidente());
            riesgoTrabajoDTO.setTipoRiesgo(String.valueOf(riesgotrabajo.getRttNumTipoRiesgo()));
            riesgoTrabajoDTO.setDiasSubsidiados(String.valueOf(riesgotrabajo.getRttDiasSubsidiados()));
            riesgoTrabajoDTO.setPorcentajeIncapac(String.valueOf(riesgotrabajo.getRttPorIncapacidad()));
            riesgoTrabajoDTO.setDefuncion(validarDefuncion(riesgotrabajo.getRttNumConsecuencia()));
            riesgoTrabajoDTO.setFechaAlta(riesgotrabajo.getRttFecFinAccidente());
            riesgoTrabajoDTO.setRpReg(String.valueOf(riesgotrabajo.getRttCvePatron()).substring(0, 8));

            //agregamos el riesgo de trabajpo a la lista
            riesgos.add(riesgoTrabajoDTO);
        }

        return riesgos;
    }

    /**
     * Metodo que valida la consecuencia
     *
     * @param recaida
     * @return
     */
    public static String validarRecaida(int recaida) {
        //Si la recaida es 5 o 6 imprime * si no mostramos nada
        return recaida == RECAIDA ? "*" : recaida == RECAIDA2 ? "*" : "";
    }

    /**
     * Metodo que valida la defuncion
     *
     * @param consecuencia
     * @return
     */
    public static String validarDefuncion(int consecuencia) {
        //Si la consecuencia es 4 mostramos una D sino no mostramos nada
        return consecuencia == DEFUNCION ? "D" : "";
    }

    /**
     * Obtiene el periodo en que se hace la solicitud para mandarla al reporte
     *
     * @param fechaFin
     * @param fechaInicio
     * @return
     */
    public static String obetenerPeriodo(Date fechaFin, Date fechaInicio) {

        Locale locMEX = new Locale("es", "MX");

        Calendar fechaFinal = Calendar.getInstance(locMEX);
        fechaFinal.setTime(fechaFin);

        Calendar fechaInicial = Calendar.getInstance(locMEX);
        fechaInicial.setTime(fechaInicio);

        SimpleDateFormat formatInicio = new SimpleDateFormat("dd' de 'MMMM", locMEX);
        SimpleDateFormat formatFin = new SimpleDateFormat("dd' de 'MMMM' del 'yyyy", locMEX);

        //Convertimos la fecha al formato
        String cadenaInicio = (formatInicio.format(fechaInicial.getTime())).replaceAll("/", " de ");
        String cadenaFin = (formatFin.format(fechaFinal.getTime())).replaceAll("/", " de ");

        //Creamos la cadena del periodo
        return cadenaInicio + " al " + cadenaFin;
    }

    /**
     * Obtiene el periodo en que se hace la solicitud para mandarla al reporte
     *
     * @param fechaFin
     * @param fechaInicio
     * @return
     */
    public static String periodoSinRTT(Date fechaFin, Date fechaInicio) {

        Locale locMEX = new Locale("es", "MX");
        SimpleDateFormat formatoAn = new SimpleDateFormat("yyyy", locMEX);
        String respuesta = "";

        Calendar fechaFinal = Calendar.getInstance(locMEX);
        fechaFinal.setTime(fechaFin);
        int anFin = Integer.parseInt(formatoAn.format(fechaFinal.getTime()));

        Calendar fechaInicial = Calendar.getInstance(locMEX);
        fechaInicial.setTime(fechaInicio);
        int anInicio = Integer.parseInt(formatoAn.format(fechaInicial.getTime()));

        //Creamos la cadena del periodo
        respuesta = anFin == anInicio ? String.valueOf(anFin) : anInicio + " - " + anFin;

        return respuesta;
    }

    /**
     * Calcula el digito verificador del NSS
     *
     * @param nss
     * @return
     */
    public static String calcularDigitoVerificador(String nss) {

        //Quitamos cualquier espacio en blanco
        nss = nss.trim();
        char[] digitos = nss.toCharArray();
        return String.valueOf("" + digitos[10]);
    }

    /**
     * Obtenga los 10 digitos del nss
     *
     * @param nss
     * @return
     */
    public static String obtenerNSS(String nss) {

        //Quitamos cualquier espacio en blanco      
        return nss.substring(0, nss.length() - 1);
    }

    /**
     * Se concatena el nombre del asegurado
     *
     * @param riesgoTrabajo
     * @return
     */
    public static String armarNombreAsegurado(RttRegistro riesgoTrabajo) {

        StringBuilder nombreCompleto = new StringBuilder();

        //Seteamos el nombre
        nombreCompleto.append(riesgoTrabajo.getRttNombre());

        //Si tiene apellido paterno lo seteamos
        if (riesgoTrabajo.getRttRefApellidoPaterno() != null) {
            nombreCompleto.append(" ").append(riesgoTrabajo.getRttRefApellidoPaterno());
        }

        //Si tiene apellido materno lo seteamos
        if (riesgoTrabajo.getRttRefApellidoMaterno() != null) {
            nombreCompleto.append(" ").append(riesgoTrabajo.getRttRefApellidoMaterno());
        }

        return nombreCompleto.toString();
    }

    /**
     * Obtener las fecha de inicio y fin del periodo
     *
     * @param origen
     * @return
     */
    public static List<Date> obtenerPeriodoCorrespondiente(OrigenSolicitudEnum origen, Integer periodo) {

        Locale locale = new Locale("es", "MX");
        Calendar fechaActual = Calendar.getInstance(locale);
        Date fechaFin;
        Date fechaInicio;

        if (periodo == null || periodo == 0) {
            if (fechaActual.get(Calendar.MONTH) <= 2) {
                //Si la fecha actual es febrero o enero el periodo es del a�o pasado
                fechaActual.set(fechaActual.get(Calendar.YEAR) - 1, Calendar.DECEMBER, 31, 23, 59, 59);
                fechaFin = fechaActual.getTime();
            } else {
                //El fin de periodo es el ultimo dia del mes pasado
                fechaActual.set(fechaActual.get(Calendar.YEAR), fechaActual.get(Calendar.MONTH) - 1, fechaActual.get(Calendar.DATE));
                fechaActual.set(fechaActual.get(Calendar.YEAR), fechaActual.get(Calendar.MONTH), fechaActual.getActualMaximum(Calendar.DAY_OF_MONTH), 23, 59, 59);
                fechaFin = fechaActual.getTime();
            }

            if (origen.equals(OrigenSolicitudEnum.INTERNET)) {
                //Si el origen de la solicitud es internet el inicio de periodo es enero
                //del periodo actual
                fechaActual.set(fechaActual.get(Calendar.YEAR), Calendar.JANUARY, 1, 0, 0, 0);
                fechaInicio = fechaActual.getTime();
            } else {
                //Si el origen de la solicitud es ventanilla el inicio de periodo es enero
                //de 5 a�os atras
                fechaActual.set(fechaActual.get(Calendar.YEAR) - PERIODOHISTORIAL, Calendar.JANUARY, 1, 0, 0, 0);
                fechaInicio = fechaActual.getTime();
            }
        } else {
            boolean anioActual = fechaActual.get(Calendar.YEAR) == periodo;
            Integer periodoInicio = periodo;


            if (fechaActual.get(Calendar.MONTH) <= 2 && anioActual) {
                //Si la fecha actual es febrero o enero el periodo es del a�o pasado
                fechaActual.set(fechaActual.get(Calendar.YEAR) - 1, Calendar.DECEMBER, 31, 23, 59, 59);
                periodoInicio = periodo - 1;
                fechaFin = fechaActual.getTime();
            } else {
                //El fin de periodo es el ultimo dia del mes pasado
                fechaActual.set(periodo, Calendar.DECEMBER, 31, 23, 59, 59);
                fechaFin = fechaActual.getTime();
            }
            //Si el origen de la solicitud es ventanilla el inicio de periodo es enero
            //de 5 a�os atras
            fechaActual.set(periodoInicio, Calendar.JANUARY, 1, 0, 0, 0);
            fechaInicio = fechaActual.getTime();
        }

        //Seteamos las fechas a la lista
        List<Date> listaFechas = new ArrayList<Date>();
        listaFechas.add(fechaInicio);
        listaFechas.add(fechaFin);

        return listaFechas;
    }

    /**
     * Metodo para convetir un DitLlavePatron a una objeto
     * PatronRiesgosTrabajo
     *
     * @param llavePatron
     * @return
     */
    public static PatronRiesgosTrabajo converterPatronRiesgosTrabajo(DitLlavePatron llavePatron) {

        PatronRiesgosTrabajo patron = new PatronRiesgosTrabajo();
        String razonSocial;

        //Determinamos el tipo de persona que es el patron
        if (llavePatron.getDicTipoPersona().getCveIdTipoPersona() == 1) {
            //Si es una persona fisica seteamos su nombre
            if (llavePatron.getDitPersona().getNomPrimerApellido() != null || llavePatron.getDitPersona().getNomSegundoApellido() != null) {
                razonSocial = llavePatron.getDitPersona().getNomNombre() + " " + llavePatron.getDitPersona().getNomPrimerApellido() + " " + llavePatron.getDitPersona().getNomSegundoApellido();
            } else {
                razonSocial = llavePatron.getDitPersona().getNomNombre();
            }
        } else {
            //si es una persona moral seteamos su razon social
            razonSocial = llavePatron.getDitPersonaMoral().getDenominacionRazonSocial();
        }
        patron.setIdPersona(llavePatron.getDitPatronSujetoObligado().getCveIdPatronSujetoObligado());
        patron.setIdPatronSujetoObligado(llavePatron.getDitPatronSujetoObligado().getCveIdPatronSujetoObligado());
        //seteamos los datos del patron
        patron.setNrp(llavePatron.getRefBusca());
        patron.setRazonSocial(razonSocial);
        patron.setTipoPersona(llavePatron.getDicTipoPersona().getCveIdTipoPersona());
        DicSubdelegacion dicSubdel = llavePatron.getDitPatronSujetoObligado().getDitSubdelPatSujOblig().getDicSubdelegacion();
        DicDelegacion dicDelegacion = dicSubdel.getDicDelegacion();
        patron.setDelegacion(llavePatron.getDitPatronSujetoObligado().getDitSubdelPatSujOblig().getDicSubdelegacion().getCveIdSubdelegacion());

        patron.setSubdelegacion(new Subdelegacion());
        patron.getSubdelegacion().setId(dicSubdel.getCveIdSubdelegacion());
        patron.getSubdelegacion().setClave(dicSubdel.getClaveSubdelegacion());
        patron.getSubdelegacion().setDescripcion(dicSubdel.getDesSubdelegacion());
        patron.getSubdelegacion().setDelegacion(new Delegacion());
        patron.getSubdelegacion().getDelegacion().setId(dicDelegacion.getCveIdDelegacion());
        patron.getSubdelegacion().getDelegacion().setClave(dicDelegacion.getClaveDelegacion());
        patron.getSubdelegacion().getDelegacion().setDescripcion(dicDelegacion.getDesDeleg());

        return patron;
    }

    public static PatronRiesgosTrabajo converterPersonaMoral(DitPersonaMoral personaMoral) {
        PatronRiesgosTrabajo patron = new PatronRiesgosTrabajo();
        patron.setIdPersona(personaMoral.getCveIdPersonaMoral());
        //seteamos los datos de la persona
        patron.setNrp(personaMoral.getRfc());
        patron.setRazonSocial(personaMoral.getDenominacionRazonSocial());

        return patron;
    }

    public static PatronRiesgosTrabajo converterPersonaFisica(DitPersonaFisica personaFisica) {
        PatronRiesgosTrabajo patron = new PatronRiesgosTrabajo();
        patron.setIdPersona(personaFisica.getCveIdPersonaFisica());
        //seteamos los datos de la persona
        patron.setNrp(personaFisica.getRfc());

        return patron;
    }

    /**
     * Metodo para traer los digitos de la fecha
     *
     * @param fecha
     * @param campo
     * @return
     */
    public static String obtenerFechaFormateada(Date fecha, int campo) {
        SimpleDateFormat formato = new SimpleDateFormat();
        switch (campo) {
            case 1:
                //Si queremos los digitos del dia
                formato = new SimpleDateFormat("dd");
                break;
            case 2:
                //Si queremos los digitos del mes
                formato = new SimpleDateFormat("MM");
                break;
            case 3:
                //Si queremos los digitos del ano
                formato = new SimpleDateFormat("yyyy");
                break;
        }
        return formato.format(fecha);
    }

    /**
     * Metodo para obtener el tipo de persona
     *
     * @param tipoPersonal
     * @return
     */
    public static TipoPersonaFiscal obtenerTipoPersona(long tipoPersonal) {
        return tipoPersonal == 1 ? TipoPersonaFiscal.FISICA : TipoPersonaFiscal.MORAL;
    }

    /**
     * Metodo converi un DitSolicitud a solicitud
     *
     * @param ditSolicitudes
     * @return
     */
    public static List<Solicitud> convertirSolicitud(List<DitSolicitud> ditSolicitudes) {

        List<Solicitud> solicitudes = new ArrayList<Solicitud>();

        for (DitSolicitud ditSolicitud : ditSolicitudes) {
            Solicitud solicitud = new Solicitud();
            solicitud.setObservacion(ditSolicitud.getRefObservacion());
            solicitud.setFechaSolicitud(ditSolicitud.getFecSolicitud());
            solicitud.setSolicitudId(ditSolicitud.getCveIdSolicitud());
            if (ditSolicitud.getDitTramites() != null) {
                solicitud.setTramites(convertirTramite(ditSolicitud.getDitTramites()));
            }
            solicitudes.add(solicitud);
        }

        return solicitudes;
    }

    /**
     * Convertir Tramites
     *
     * @param ditTramites
     * @return
     */
    private static List<Tramite> convertirTramite(List<DitTramite> ditTramites) {
        List<Tramite> tramites = new ArrayList<Tramite>();
        for (DitTramite ditTramite : ditTramites) {
            Tramite tramite = new Tramite();
            tramite.setTramiteId(ditTramite.getCveIdTramite());
            if (ditTramite.getDicTipoTramite() != null) {
                tramite.setTipoTramite(convertirTipoTramite(ditTramite.getDicTipoTramite()));
            }
            if (ditTramite.getDicEstadoTramite() != null) {
                tramite.setEstadoTramite(convertirEstadoTramite(ditTramite.getDicEstadoTramite()));
            }
            tramites.add(tramite);
        }
        return tramites;
    }

    /**
     * Convertir EstadoTramite
     *
     * @param dicEstadoTramite
     * @return
     */
    private static EstadoTramite convertirEstadoTramite(DicEstadoTramite dicEstadoTramite) {
        EstadoTramite estadoTramite = new EstadoTramite();
        estadoTramite.setIdEstadoTramitePersona(dicEstadoTramite.getCveIdEstadoTramite() != null ? dicEstadoTramite.getCveIdEstadoTramite().intValue() : null);
        return estadoTramite;
    }

    /**
     * Convertir TipoTramite
     *
     * @param dicTipoTramite
     * @return
     */
    private static TipoTramite convertirTipoTramite(DicTipoTramite dicTipoTramite) {
        TipoTramite tipoTramite = new TipoTramite();
        tipoTramite.setIdTipoTramite(dicTipoTramite.getCveIdTipoTramite() != null ? dicTipoTramite.getCveIdTipoTramite().intValue() : null);
        return tipoTramite;
    }

    public static ReporteRiesgoTrabajo reporteObjectToModel(Object[] escritoObj) throws SQLException {

        ReporteRiesgoTrabajo reporte = null;

        if (escritoObj != null) {
            reporte = new ReporteRiesgoTrabajo();
            reporte.setRfc(parseString(escritoObj[0]));
            reporte.setFechaAlta(parseDate(escritoObj[1]));
            if (escritoObj[2] != null) {
                reporte.setFechaActualiza(parseDate(escritoObj[2]));
            }
            if (escritoObj[3] != null) {
                reporte.setFechaBaja(parseDate(escritoObj[3]));
            }
            if (escritoObj[4] != null) {
                reporte.setUrlReporte(parseString(escritoObj[4]));
            }
            reporte.setGeneraReporte(parseLong(escritoObj[5]));
            reporte.setEstadoReporte(parseLong(escritoObj[6]));

            if (escritoObj[7] != null) {
                Blob docBlob = (Blob) escritoObj[7];
                reporte.setDocumento(docBlob.getBytes(1l, (int) docBlob.length()));
            }
            reporte.setIdOrigen(parseLong(escritoObj[8]));
        }

        return reporte;
    }

    public static ReporteRiesgoTrabajo reporteObjectToModelEdo(Object[] escritoObj) throws SQLException {

        ReporteRiesgoTrabajo reporte = null;

        if (escritoObj != null) {
            reporte = new ReporteRiesgoTrabajo();
            reporte.setRfc(parseString(escritoObj[0]));
            reporte.setFechaAlta(parseDate(escritoObj[1]));
            if (escritoObj[2] != null) {
                reporte.setFechaActualiza(parseDate(escritoObj[2]));
            }
            if (escritoObj[3] != null) {
                reporte.setFechaBaja(parseDate(escritoObj[3]));
            }
            if (escritoObj[4] != null) {
                reporte.setUrlReporte(parseString(escritoObj[4]));
            }
            reporte.setGeneraReporte(parseLong(escritoObj[5]));
            reporte.setEstadoReporte(parseLong(escritoObj[6]));
            reporte.setIdOrigen(parseLong(escritoObj[7]));

            String datoNoVacio = "1";
            reporte.setDocumento(datoNoVacio.getBytes());
        }

        return reporte;
    }

    public static List<RiesgoTrabajo> riegosObjToMod(List<Object[]> objetos) {
        List<RiesgoTrabajo> riesgos = new ArrayList<RiesgoTrabajo>();
        for (Object[] obj : objetos) {
            RiesgoTrabajo riesg = new RiesgoTrabajo();
            riesg.setRpReg(parseString(obj[1]));
            riesg.setModalidad(parseLong(obj[2]).toString());
            riesg.setDvRp(String.valueOf(obj[3]));
            riesgos.add(riesg);
        }
        return riesgos;
    }

    public static List<String> patronesObjToMod(List<Object[]> objetos) {
        List<String> patrones = new ArrayList<String>();
        //Forma número 1 (Uso de Maps).
        Map<String, Object> mapObjects = new HashMap<String, Object>(objetos.size());

        //Aquí está la magia
        for (Object[] p : objetos) {
            mapObjects.put(parseString(p[0]), p[0]);
        }

        //Agrego cada elemento del map a una nueva lista y muestro cada elemento.
        for (Entry<String, Object> p : mapObjects.entrySet()) {
            String patron = parseString(p.getValue());
            patrones.add(patron);
        }

        return patrones;
    }

    /**
     * Obtener las el año de siniestralidad
     *
     * @return
     */
    public static Date obtenerFechaSiniestralidad() {
        Locale locale = new Locale("es", "MX");
        Calendar fechaCalHoy = Calendar.getInstance(locale);
        Calendar fechaCalCorte = Calendar.getInstance(locale);
        Date fechaSiniestralidad = new Date();
        fechaCalCorte.set(fechaCalHoy.get(Calendar.YEAR), Calendar.MARCH, 01);

        if (fechaCalCorte.getTime().after(fechaCalHoy.getTime())){
            fechaCalHoy.set(fechaCalHoy.get(Calendar.YEAR) - 1, fechaCalHoy.get(Calendar.MONTH), fechaCalHoy.get(Calendar.DAY_OF_MONTH));
            fechaSiniestralidad = fechaCalHoy.getTime();
        }

        return fechaSiniestralidad;
    }

    private static String parseString(Object obj) {
        return (String) obj;
    }

    private static Long parseLong(Object obj) {
        return ((BigDecimal) obj).longValue();
    }

    private static Date parseDate(Object obj) {
        return new Date(((Timestamp) obj).getTime());
    }

    public static final StringBuffer FIND_REPORTE_RTT = new StringBuffer();

    public static final StringBuffer CREAR_REPORTE_RTT = new StringBuffer();

    public static final StringBuffer UPDATE_REPORTE_RTT = new StringBuffer();

    public static final StringBuffer UPDATE_REPORTE_ONLY_DOC_RTT = new StringBuffer();

    public static final StringBuffer BAJA_REPORTE_RTT = new StringBuffer();

    public static final StringBuffer FIND_DOCUMENTO_RTT = new StringBuffer();

    public static final StringBuffer FIND_REPORTE_EDO_RTT = new StringBuffer();

    static {
        FIND_REPORTE_RTT
                .append("select REF_RFC, FEC_REGISTRO_ALTA, FEC_REGISTRO_ACTUALIZADO, FEC_REGISTRO_BAJA, URL_REPORTE, GENERA_REPORTE, EDO_REPORTE, DOCUMENTO, CVE_ID_ORIGEN ")
                .append("from RTT_REPORTE_RIESGO where REF_RFC = :rfc and FEC_REGISTRO_BAJA is null and CVE_ID_ORIGEN = :origen");

        CREAR_REPORTE_RTT
                .append("INSERT INTO RTT_REPORTE_RIESGO (CVE_ID_REPORTE, REF_RFC, FEC_REGISTRO_ALTA, GENERA_REPORTE, EDO_REPORTE, CVE_ID_ORIGEN) ")
                .append("VALUES ((SELECT count(*)+1 AS id FROM RTT_REPORTE_RIESGO),:rfc, SYSDATE, :generaReporte, :estado, :origen)");

        UPDATE_REPORTE_RTT
                .append("UPDATE RTT_REPORTE_RIESGO SET URL_REPORTE = :urlReporte , FEC_REGISTRO_ACTUALIZADO = SYSDATE, GENERA_REPORTE = :generaReporte, ")
                .append("EDO_REPORTE = :estado WHERE REF_RFC = :rfc and FEC_REGISTRO_BAJA is null and CVE_ID_ORIGEN = :origen");

        BAJA_REPORTE_RTT
                .append("UPDATE RTT_REPORTE_RIESGO SET FEC_REGISTRO_BAJA = sysdate, FEC_REGISTRO_ACTUALIZADO = sysdate ")
                .append("WHERE REF_RFC = :rfc and FEC_REGISTRO_BAJA is null and CVE_ID_ORIGEN = :origen");

        UPDATE_REPORTE_ONLY_DOC_RTT
                .append("UPDATE RTT_REPORTE_RIESGO SET DOCUMENTO = :documento WHERE REF_RFC = :rfc and FEC_REGISTRO_BAJA is null and CVE_ID_ORIGEN = :origen");

        FIND_DOCUMENTO_RTT
                .append("select REF_RFC, DOCUMENTO ")
                .append("from RTT_REPORTE_RIESGO where REF_RFC = :rfc and FEC_REGISTRO_BAJA is null and CVE_ID_ORIGEN = :origen");

        FIND_REPORTE_EDO_RTT
                .append("select REF_RFC, FEC_REGISTRO_ALTA, FEC_REGISTRO_ACTUALIZADO, FEC_REGISTRO_BAJA, URL_REPORTE, GENERA_REPORTE, EDO_REPORTE, CVE_ID_ORIGEN ")
                .append("from RTT_REPORTE_RIESGO where REF_RFC = :rfc and FEC_REGISTRO_BAJA is null and CVE_ID_ORIGEN = :origen");
    }
}
