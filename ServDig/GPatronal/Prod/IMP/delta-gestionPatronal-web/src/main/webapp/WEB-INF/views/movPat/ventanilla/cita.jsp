<%--
  Created by IntelliJ IDEA.
  User: hsosa
  Date: 11/08/2023
  Time: 09:56 p. m.
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="../../general/taglibs.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" type="text/css" 	href='<spring:url value="/static/resources/estilos/imss/movPat/ventanilla.css" htmlEscape="true" />' />
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/validatesIMSS.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/messages_es.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/altaPatronUX/funcionesComunes.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/movPat/ventanilla/cita.js" htmlEscape="true" />"></script>
<div class="contenidoCita">
    <div id="divOpcionesCita">
        <button type="button" id="btnConCita" class="btn btn-default" >Con Cita</button>
        <button type="button" id="btnSinCita" class="btn btn-default">Sin Cita</button>
    </div>
    <div id="divCita">
        <form class="form-horizontal" id="formBuscarFolio" action="#" method="get">
            <div class="form-group">
                <label class="control-label col-sm-2" for="folioCita">
                    Folio de la Cita<span class="required">(*)</span>:
                </label>
                <div class="col-sm-5">
                    <input type="text" class="form-control" name="folioCita" id="folioCita" value="" maxlength="25" placeholder="Ingresa el Folio de la Cita"/>
                </div>
                <div class="col-sm-5">
                    <button type="button" id="btnBuscarFolio" class="btn btn-primary">Buscar</button>
                    <button type="button" id="btnCancelarFolio" class="btn btn-secondary">Cancelar</button>
                </div>
            </div>
        </form>
    </div>
</div>

