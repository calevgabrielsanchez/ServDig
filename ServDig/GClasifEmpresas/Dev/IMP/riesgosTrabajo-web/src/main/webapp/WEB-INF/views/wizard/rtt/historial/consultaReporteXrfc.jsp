<%@ include file="../../../general/taglibs.jsp"%>

<c:set var="fechaActual" value="<%=new java.util.Date()%>" />

<style>
    .ui-widget-overlay {
        position: fixed;
    }

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
        color: #black;
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

    .contenedor {
        padding-top: 45px;
    }
</style>
<div class="col-sm-12">
<br>
<jsp:include page="../common/tablaSegXRfc.jsp" flush="true"/>
<div class="col-sm-5">
    <c:if test="${reportObtain[3] != null}" >
            <c:if test="${reportObtain[3] != '0'}">
            <div class="row">
                <div class="col-sm-6">
                    <button onclick="busquedaRTTCtrl.descargaExcelRfc('1')" class="btn btn-default">
                        <span class="glyphicon glyphicon-download-alt"></span>Descargar Excel
                    </button>
                </div>
            </div></c:if>
    </c:if>
</div>
</div>
