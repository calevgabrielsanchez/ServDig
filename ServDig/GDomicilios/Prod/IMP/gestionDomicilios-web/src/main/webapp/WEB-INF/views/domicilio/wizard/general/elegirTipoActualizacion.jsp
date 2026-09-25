<%@ include file="../../../general/taglibs.jsp"%>
<script type="text/javascript"
	src="<spring:url value="/static/resources/js/delta/domicilios/wizard/general/funcionesComunes.js" htmlEscape="true" />"></script>

<style>
.btn-wrapper {
	text-align: center; 
	margin-top: 15px;
}
.detalleAsentamiento {
	margin-top: 15px;
}

.detalleAsentamiento address {
	margin-bottom: 0px;
}
</style>

<script type="text/javascript">
	wizardGeneralDomicilios.idPersona = ${idPersona};
	
	$(function(){
		var dialogo = $('#confirmarDialog').dialog({
			resizable: false,
			modal: true,
			autoOpen: false,
			dialogClass: "no-close",
		    closeOnEscape: false,
			buttons: {
			 	"Cancelar": function() {
					$(this).dialog('close');
			 	},
				"Aceptar": function() {
					$(this).dialog('close');
					wizardGeneralDomicilios.iniciarTramiteCP();
			 	}
			 }
		 });
		
		$('#btnInciaTramiteCPTmp').click(function(event){
			event.preventDefault();
			dialogo.dialog('open');
		});
	});

</script>

<div class="container-fluid">
	<div class="row">
		<div class="col-xs-12">
			<h3><spring:message code="label.tramite.actualizacion.dom.elegir"/></h3>
			<c:if test="${not empty ASENTAMIENTO }">
				<div class="row detalleAsentamiento">
					<div class="col-xs-6 col-xs-offset-3">
						<div class="panel panel-default">
							<div class="panel-body">
								<address>
									<strong><spring:message code="label.asentamiento"/></strong> <br>
									${ASENTAMIENTO.nombre } <br>
									<strong><spring:message code="label.municipio"/></strong> <br> 
									${ASENTAMIENTO.localidad.municipio.nombre } <br>
									<strong><spring:message code="label.entidadFederativa"/></strong> <br>
									${ASENTAMIENTO.localidad.municipio.entidadFederativa.nombre } <br>
									<strong><spring:message code="label.codigoPostal"/></strong> <br> 
									${ASENTAMIENTO.codigoPostal.codigoPostal}
								</address>
							</div>
						</div>
					</div>
				</div>
			</c:if>
			<div class="btn-wrapper">
				<div class="btn-group" data-toggle="buttons-radio">
					<button type="button" class="btn btn-default" id="btnInciaTramiteCPTmp"
						onclick="uid_call('imss.gestion.domicilios.general.tipoAct.btn_no','clickin');">No</button>
					<button type="button" class="btn btn-primary" id="btnInciaTramiteSinCP" 
						onclick="uid_call('imss.gestion.domicilios.general.tipoAct.btn_si','clickin');">Si</button>
					
				</div>
			</div>

		</div>
	</div>

	<div id="confirmarDialog" title="Mensaje confirmaci&oacute;n">
		<p>
			<spring:message code="label.tramite.actualizacion.dom.advertencia"/>
		</p>
	</div>
</div>

