<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../general/taglibs.jsp"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.solicitud.EstadoSolicitudEnum"%>
<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<script type="text/javascript">
	var contextPath="${contextpath}";
	var banderaContinuarTramite = "${banderaContinuarTramite}";
</script>
<c:set var="urlInformacionAdicional" value="${contextpath}/wizard/correccionDatosAsegurado/informacionAdicional/correcionSolicitud"></c:set>
<c:set var="urlCancelarInformacionAdicional"
	value="${contextpath}/wizard/correccionDatosAsegurado/informacionAdicional/cancelaInformacionAdicional"></c:set>

<!--<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/solicitud/seguimientoTramite.js" htmlEscape="true" />"></script>-->
<div id="info-paso" style="margin-bottom: 50px;">

	<div class="contenedor">
		<h3>
			<spring:message code="label.seguimiento.solicitud" />
		</h3>
		<hr class="red" style="margin-bottom: 20px;">
	</div>

	<!-- Forma de la consulta a RENAPO. -->
	<div class="row col-md-12">
		<label class="control-label"><spring:message
				code="label.datos.solicitante" />
		</label>
	</div>

	
		<div class="row col-md-2">
			<label class="control-label"><spring:message
					code="label.curp" />
			</label>
		</div>
		<div class="row col-md-3">${informacionConsulta.curp}</div>

		<div class="col-md-2">
			<label class="control-label"> <spring:message
					code="label.nombre" />
			</label>
		</div>
		<div class="col-md-5">${informacionConsulta.nombre}</div>	
	
    <div class="row col-md-12">
    <hr class="red" style="margin-bottom: 20px;">
            <h3>
		<spring:message
			code="label.solicitud.infoAdicional.requerimiento" />
                <!--Requerimiento de información para el Asegurado-->
            </h3>
            <hr class="red" style="margin-bottom: 20px;">
    </div>
    <div class="row col-md-12">
                   <label class="control-label">  <spring:message
			code="label.solicitud.informacion.faltante" />
                   </label>
    </div>
    <div class="row col-md-12">
                 <textarea  cols="133" disabled="">
                   ${informacionConsulta.infAdicional}
				</textarea>
    </div>
</div>

<form id="concluirSolicitudForm" class="formNotBlock" method="GET">
	<div class="col-md-12">
	
		<div class ="pull-right">
		<br>
		<br>
		<button type="button" class="btn btn-default" style="text-decoration: underline;" id="cancelarInformacionAdicional" >Cancelar</button>
                <button type="button" id="continuarInformacionAdicional" style="text-decoration: underline;"
                            class="btn btn-primary">
                            <spring:message code="label.continuar" />
                </button>
		
		
		</div>
		
		<div id="cancelarInformacionSolicitud" title="Cancelar solicitud" hidden="true">
					<p>
						<span class="ui-icon ui-icon-alert"	style="float: left; margin: 0 7px 20px 0;"> </span>
						<spring:message code="label.confirmacion.cancelar"/>		</p>
					<br />
		</div>
	</div>
	
</form>

<script>
      
     $('#continuarInformacionAdicional').click(function(e) {
        e.preventDefault();
		 var url = "${urlInformacionAdicional}" ;
		$('form#concluirSolicitudForm').attr('action', url);
		$('form#concluirSolicitudForm').submit();
      });
      
     objDialog = $('#cancelarInformacionSolicitud').dialog({
	        autoOpen:false,
	        resizable: false,
	        height:300,
	        width:600,
	        modal: true,
	        buttons: {
	            "Aceptar": function(data) {					
	       		 var url = "${urlCancelarInformacionAdicional}" ;
	       		$('form#concluirSolicitudForm').attr('action', url);
	       		$('form#concluirSolicitudForm').submit();					
					},
	            "Cancelar": function(data) {
	            	$( this ).dialog( "close" );
					return false;
	            }
	        }
	 });
     
	 $('#cancelarInformacionAdicional').click(function(e) {
		 e.preventDefault();
		objDialog.dialog('open');
	});
      
</script>
