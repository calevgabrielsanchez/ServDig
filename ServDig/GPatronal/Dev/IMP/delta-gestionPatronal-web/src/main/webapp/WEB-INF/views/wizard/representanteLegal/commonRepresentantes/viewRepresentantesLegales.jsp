<%@ include file="/WEB-INF/views/general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/wizard/commonRepresentantes/viewRepresentantesLegales.js" htmlEscape="true" />"></script>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/wizard/agregarRepresentanteLegal/commonRL.js" htmlEscape="true" />"></script>
			
<script>	
	var idSolicitud = ${idSolicitud};
	var folioSolicitud = ${folioSolicitud};
	var rlRequerido = ${rlRequerido};	
</script>

<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div class="col-sm-12">
			<form action="" method="post" id="concluirForm">
				<input type="hidden" id="idSolicitud" value="${idSolicitud}" />
				<input type="hidden" id="folioSolicitud" value="${folioSolicitud}" />
				<input type="hidden" name="hndRepresentanteLegalSelected" id="hndRepresentanteLegalSelected" value="" />
				
					<div class="separadorseccion" style="margin-top: 0px;">
						<span>Representantes Legales</span>
					</div>
					<p>Seleccione el Representante Legal que est&aacute; solicitando el tr&aacute;mite</p>
										
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
			<div class="btn-group dropup">
				<a href="#" class="btn btn-primary">Acciones</a>
				<a href="#" data-toggle="dropdown" class="btn btn-primary dropdown-toggle">
					<span class="caret"></span>
				</a>
				<ul class="dropdown-menu">
					<li>
						<a id="btnConcluirRL">
							<i class="glyphicon glyphicon-ok"></i>
							Finalizar Tr&aacute;mite
						</a>
					</li>
					<li>
						<a id="cancelarTramite">
							<i class="glyphicon glyphicon-trash"></i>
							Cancelar Tr&aacute;mite
						</a>
					</li>
				</ul>
			</div>
		</div>

		<div class="controles col-sm-6">
			<div class="pull-right">
				<button class="btn btn-default" id="cerrarWizard">CERRAR</button>
			</div>
		</div>
	</div>
</div>

<div id="dialogoMensajes">
	<p>
		<span id="textoMensaje"></span>
	</p>
</div>

<div id="dialog-confirm-cancelar" title="Confirmar cancelaci&oacute;n de solicitud">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;">
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