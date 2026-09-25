package mx.gob.imss.cit.cda.web.reportes.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import mx.gob.imss.cit.cda.core.events.RequestReadEvent;
import mx.gob.imss.cit.cda.web.reportes.vo.RequestTramitesReportesPage;

public class ReportesCDAUtils {

    public static ArrayList<HashMap<String, String>> crearFiltrosReporteCDA(
            RequestReadEvent<RequestTramitesReportesPage> requestReadEvent) {
        ArrayList<HashMap<String, String>> listFilter = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> filtros = new HashMap<String, String>();
        filtros = evaluarFiltrosGenerales(filtros, requestReadEvent);
        filtros = evaluarFiltrosFechas(filtros, requestReadEvent);
        filtros = evaluarFiltroVariable(filtros, requestReadEvent);
        listFilter.add(filtros);
        return listFilter;
    }

    public static HashMap<String, String> evaluarFiltrosGenerales(
            HashMap<String, String> filtros,
            RequestReadEvent<RequestTramitesReportesPage> requestReadEvent) {

        if (requestReadEvent.getData().getFilter() != null) {
            if (requestReadEvent.getData().getFilter().getDelegacion() != null
                    && !requestReadEvent.getData().getFilter().getDelegacion()
                            .equals("-1")) {
                filtros.put("delegacion",
                        requestReadEvent.getData().getFilter().getDelegacion());
            }
            if (requestReadEvent.getData().getFilter()
                    .getSubdelegacion() != null
                    && !requestReadEvent.getData().getFilter()
                            .getSubdelegacion().equals("-1")) {
                filtros.put("subdelegacion", requestReadEvent.getData()
                        .getFilter().getSubdelegacion());
            }
            if (requestReadEvent.getData().getFilter().getAutorizo() != null
                    && !requestReadEvent.getData().getFilter().getAutorizo()
                            .equals("-1")) {
                filtros.put("autorizo",
                        requestReadEvent.getData().getFilter().getAutorizo());
            }
            if (requestReadEvent.getData().getFilter().getResponsable() != null
                    && !requestReadEvent.getData().getFilter().getResponsable()
                            .equals("-1")) {
                filtros.put("responsable", requestReadEvent.getData()
                        .getFilter().getResponsable());
            }
            if (requestReadEvent.getData().getFilter().getOrigen() != null
                    && !requestReadEvent.getData().getFilter().getOrigen()
                            .equals("-1")) {
                filtros.put("origen",
                        requestReadEvent.getData().getFilter().getOrigen());
            }
            if (requestReadEvent.getData().getFilter().getFolio() != null
                    && !requestReadEvent.getData().getFilter().getFolio()
                            .equals("")) {
                filtros.put("folio",
                        requestReadEvent.getData().getFilter().getFolio());
            }
            if (requestReadEvent.getData().getFilter().getCurp() != null
                    && !requestReadEvent.getData().getFilter().getCurp()
                            .equals("")) {
                filtros.put("curp",
                        requestReadEvent.getData().getFilter().getCurp());
            }
            if (requestReadEvent.getData().getFilter()
                    .getNssInvolucrado() != null
                    && !requestReadEvent.getData().getFilter()
                            .getNssInvolucrado().equals("")) {
                filtros.put("nssInvolucrado", requestReadEvent.getData()
                        .getFilter().getNssInvolucrado());
            }
            if (requestReadEvent.getData().getFilter().getTipoTramite() != null
                    && !requestReadEvent.getData().getFilter().getTipoTramite()
                            .equals("-1")) {
                filtros.put("tipoTramite", requestReadEvent.getData()
                        .getFilter().getTipoTramite());
            }
            if (requestReadEvent.getData().getFilter().getEstado() != null
                    && !requestReadEvent.getData().getFilter().getEstado()
                            .equals("-1")) {
                filtros.put("estado",
                        requestReadEvent.getData().getFilter().getEstado());
            }
            if (requestReadEvent.getData().getFilter()
                    .getCurpBeneficiario() != null
                    && !requestReadEvent.getData().getFilter()
                            .getCurpBeneficiario().equals("")) {
                filtros.put("curpBeneficiario", requestReadEvent.getData()
                        .getFilter().getCurpBeneficiario());
            }
        }

        return filtros;
    }

    public static HashMap<String, String> evaluarFiltrosFechas(
            HashMap<String, String> filtros,
            RequestReadEvent<RequestTramitesReportesPage> requestReadEvent) {
        if (requestReadEvent.getData().getFilter() != null) {

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                    "dd/MM/yyyy");

            if (requestReadEvent.getData().getFilter().isVencida()) {
                filtros.put("vencida", String.valueOf(
                        requestReadEvent.getData().getFilter().isVencida()));
            }
            if (requestReadEvent.getData().getFilter()
                    .getFechaSolicitudDesde() != null) {
                filtros.put("fechaSolicitudDesde",
                        simpleDateFormat.format(requestReadEvent.getData()
                                .getFilter().getFechaSolicitudDesde())
                                .toString());
            }
            if (requestReadEvent.getData().getFilter()
                    .getFechaSolicitudHasta() != null) {
                filtros.put("fechaSolicitudHasta",
                        simpleDateFormat.format(requestReadEvent.getData()
                                .getFilter().getFechaSolicitudHasta())
                                .toString());
            }
            if (requestReadEvent.getData().getFilter()
                    .getFechaFinalizacionDesde() != null) {
                filtros.put("fechaFinalizacionDesde",
                        simpleDateFormat
                                .format(requestReadEvent.getData().getFilter()
                                        .getFechaFinalizacionDesde())
                                .toString());
            }
            if (requestReadEvent.getData().getFilter()
                    .getFechaFinalizacionHasta() != null) {
                filtros.put("fechaFinalizacionHasta",
                        simpleDateFormat
                                .format(requestReadEvent.getData().getFilter()
                                        .getFechaFinalizacionHasta())
                                .toString());
            }
            if (requestReadEvent.getData().getFilter()
                    .getFechaActualizacionDesde() != null) {
                filtros.put("fechaActualizacionDesde",
                        simpleDateFormat
                                .format(requestReadEvent.getData().getFilter()
                                        .getFechaActualizacionDesde())
                                .toString());
            }
            if (requestReadEvent.getData().getFilter()
                    .getFechaActualizacionHasta() != null) {
                filtros.put("fechaActualizacionHasta",
                        simpleDateFormat
                                .format(requestReadEvent.getData().getFilter()
                                        .getFechaActualizacionHasta())
                                .toString());
            }
        }
        return filtros;
    }

    public static HashMap<String, String> evaluarFiltroVariable(
            HashMap<String, String> filtros,
            RequestReadEvent<RequestTramitesReportesPage> requestReadEvent) {
        if (requestReadEvent.getData().getFilter() != null
                && requestReadEvent.getData().getFilter().getVariable() != null
                && !requestReadEvent.getData().getFilter().getVariable()
                        .equals("-1")) {
            filtros.put("variable",
                    requestReadEvent.getData().getFilter().getVariable());
        }
        return filtros;
    }
}
