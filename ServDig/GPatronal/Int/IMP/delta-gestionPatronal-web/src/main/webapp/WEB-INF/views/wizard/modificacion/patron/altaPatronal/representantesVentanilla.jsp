<%@ include file="../../../../general/taglibs.jsp"%>

<script type="text/javascript" 
	src="<spring:url value="/static/resources/js/delta/afiliacion/common/commonMethods.js" htmlEscape="true" />"></script>
<script type="text/javascript" 
	src="<spring:url value="/static/resources/js/wizard/modificacion/patron/altaPatronal/representantesVentanilla.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/wizard/agregarRepresentanteLegal/commonRL.js" htmlEscape="true" />"></script>
	
<style>
label {
	display: inline;
}

.table_form table {
	margin: 15px auto;
}

.table_form table tr td {
	padding: 5px 10px;
}

textarea {
	height: 100%;
}

input,textarea,.uneditable-input {
	width: auto;
	text-transform: uppercase;
}

div.opcion {
    padding-top: 15px;
}
</style>

<script>
	var idSolicitud = ${idSolicitud};
	var rlRequerido = ${rlRequerido}
</script>



<div class="contenedor col-sm-12">
	
	<div class="contenido row">
		<div class="col-sm-12">
		<form action="" method="post" id="concluirForm">
					<input type="hidden" name="idSolicitud" id="idSolicitud" value="${idSolicitud}" />
					<input type="hidden" id="hdnFolioSolicitud" value="${folioSolicitud}"/>
					<input type="hidden" name="hndRepresentanteLegalSelected" id="hndRepresentanteLegalSelected" value="" />
					
					<div class="separadorseccion" style="margin-top: 0px;">
						<span>Representantes legales</span>
					</div>
					<p>Seleccione el representante legal que est&aacute; solicitando el tr&aacute;mite</p>
										
					<form:form modelAttribute="representante" id="rlFormPaginar">
					</form:form>
		
					<table style="width: 100%;">
						<tr>
							<td>
								<div id="lista" style="width: 100%;">
									<div id="tabla">
										<table id="tbRLs" style="width: 100%;"
											class="table table-striped table-bordered" cellpadding="0"
											cellspacing="0" border="0">
												<thead></thead><tbody style="width: 100%;"></tbody>
										</table>
									</div>
								</div>
							</td>
						</tr>
					</table>
					
			</form>
			
			<!-- BOTON PARA AGREGAR REPRESENTANTES LEGALES -->
			<div class="pull-left">
				<a id="agregarRepresentanteLegal" class="btn btn-default">
				<i class=" glyphicon glyphicon-plus"></i> Agregar</a>
			</div>
			
			</div>
	</div>
	
	<div class="pie row">
		<div class="opciones col-sm-6">
				<div class="btn-group">
					<a href="#" class="btn btn-primary"><spring:message code="label.menus.opciones" /></a> <a href="#"
						data-toggle="dropdown" class="btn btn-primary dropdown-toggle"><span
						class="caret"></span></a>
					<ul class="dropdown-menu">		
						<li><a id="btnConcluirRL"><i class="glyphicon glyphicon-ok"></i> Finalizar tr&aacute;mite</a></li>
						<li><a id="cancelarTramite"><i class="glyphicon glyphicon-trash"></i> Cancelar tr&aacute;mite</a></li>
					</ul>
				</div>
		</div>
		
		<div class="controles col-sm-6"> 
			<div class="pull-right">
				<a id="pasoPrevioRL" class="btn btn-default">
				<i class=" glyphicon glyphicon-step-backward"></i> Anterior</a>
			</div>
		</div>
	</div>
</div>

<div id="dialogoMensajes">
	<p><span id="textoMensaje"></span></p>
</div>

<div id="dialog-confirm-cancelar" title="Confirmar cancelaci&oacute;n de solicitud">
	<p>
		<span class="ui-icon ui-icon-alert"	style="float: left; margin: 0 7px 20px 0;">
			</span>&iquest;Desea cancelar la solicitud pendiente con folio: <strong>${folioSolicitud}</strong>?
	</p>
</div>

<div id="dialog-confirm" title="Mensaje">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"></span>
			<label id="mensajeDialogo"></label>
	</p>
</div>