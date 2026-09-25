<%@ include file="../../../general/taglibs.jsp"%>

<script type="text/javascript"
src="<spring:url value="/static/resources/js/wizard/rtt/inciarRtt.js" htmlEscape="true" />"></script>
<style>
    .ui-widget-overlay {
        position: fixed;
    }

    /*.ui-dialog-titlebar {
        display: none;
    }*/

    span.error-custom {
        float: none !important;
        vertical-align: super;
    }

    .required {
        color: red;
    }

    .filtros-busqueda .row {
        margin-bottom: 12px;
    }

    .filtros-busqueda .filtros .etiqueta {
        width: 25%;
    }

    input[type="text"] {
        margin-bottom: 0px;
    }

    .alert-temp {
        background-color: #f8f8f8;
        border-color: #d9d9d9;
        color: black;
    }

    .icono-tramite {
        font-size: 2em;
    }

    .icono-tramite a {
        color: #545454;
        text-decoration: none;
    }

    .icono-tramite a:hover {
        color: black;
    }
</style>

<div class="contenedor">
    <div class="row m-t-md">
        <div class="col-sm-12">
            <div class="col-md-12" style="margin: 0 auto;">
                <div class="row">
                    <div class="col-md-9">
                        <div class="col-md-3">
                            <label id="razon">Nombre o Raz&oacute;n Social:</label>
                        </div>
                        <div class="col-md-9" style="text-align: justify;">
                            <span>${patron.razonSocial}</span>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="col-md-4">
                            <label id="fecha">Fecha:</label>
                        </div>
                        <div class="col-md-5">
                            <fmt:formatDate value="${fechaTramite}" pattern="dd/MM/yyyy" />
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-9">
                        <div class="col-md-3">
                            <label id="rp">Registro Patronal:</label>
                        </div>
                        <div class="col-md-9">
                            <span>${patron.nrp}</span>
                        </div>
                    </div>
                    <div class="col-md-3"></div>
                </div>
            </div>
        </div>
    </div>
    <br>
    <p style="text-align: justify;">
        En atenci&oacute;n a su consulta, con motivo de la revisi&oacute;n anual de
        la siniestralidad
    <fmt:formatDate value="${fecSiniestra}" pattern="yyyy" />, a continuaci&oacute;n se enlista la relaci&oacute;n de casos de
    riesgos de trabajo terminados, cuyo registro corresponde al periodo
    que va del ${periodoConsulta}.
</p>
<br />

<h4>Relaci&oacute;n de Riesgos de Trabajo Terminados</h4>
<hr class="red">
<div class="row">
<div class="col-sm-12">
<jsp:include page="../common/tablaRiesgosTrabajo.jsp" flush="true"/>
</div>
</div>
<div class="row">
<div class="col-sm-12 text-right">
    <button id="cerrar" onclick="cerrar()"
            class="btn btn-danger">
        Cerrar
    </button>
    <button onclick="generarExcelRiesgosTrabajo()" class="btn btn-default">
                <span class="glyphicon glyphicon-download-alt"></span>Descargar
                Excel
            </button>
            <button id="crearPDF" onclick="validarSolicitud()" class="btn btn-primary ">
                <span class="glyphicon glyphicon-download-alt"></span>Descargar PDF
            </button>
</div>
</div>
</div>
<div id="divMensaje">
    <p>
        <span id="textoMensaje"></span>
    </p>
</div>