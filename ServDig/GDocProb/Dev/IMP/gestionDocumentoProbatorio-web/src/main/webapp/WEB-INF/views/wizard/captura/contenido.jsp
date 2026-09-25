<%@ include file="../../general/taglibs.jsp"%>

<%@ include file="/WEB-INF/views/common/llenarRazonRegistro.jsp" %>
<%@ include file="/WEB-INF/views/common/llenarTipoDocumentoProbatorio.jsp" %>
<%@ include file="/WEB-INF/views/common/llenaTipoTramite.jsp" %>

<c:set var="scheme" value="<%= request.getScheme()%>"/>
<c:set var="server" value="<%= request.getServerName()%>"/>

<script type="text/javascript" src="<spring:url value="/static/resources/js/jquery/validation/validator/jquery.validate.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/uploadify3/swfobject.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/uploadify3/jquery.uploadify-3.1.min.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/fileUpload/fileUpload.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/fileUpload/capturaDocs.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/fileUpload/ajaxfileupload.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/fileUpload/jquery.filestyle.js" htmlEscape="true" />"></script>
<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/contenido.js" htmlEscape="true" />"></script>


<style type="text/css">

#file_browse_wrapper {
	width: 200px;
	height: 30px;
	background:
		url('${scheme}://${server}/gestionDerechohabientes-web/static/resources/imagenes/btn_buscar.png')
		0 0 no-repeat;
	border: none;
	overflow: hidden;
	cursor: pointer;
	vertical-align: middle;
}

#file_browse_wrapper:hover {
	background:
		url('${scheme}://${server}/gestionDerechohabientes-web/static/resources/imagenes/btn_buscar.png')
		0 0 no-repeat;
	cursor: pointer;
}

#file_browse_wrapper:active {
	background:
		url('${scheme}://${server}/gestionDerechohabientes-web/static/resources/imagenes/btn_buscar.png')
		0 0 no-repeat;
	cursor: pointer;
}

#fileToUpload {
	margin-left: -145px;
	opacity: 0.0;
	-ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=0)";
	filter: progid : DXImageTransform.Microsoft.Alpha ( Opacity = 0 );
}
</style>

<input type="hidden" id="hdnIdTipoTramite" value = "${idTipoTramite}"/>
<input type="hidden" id="hdnIdTramite" value = "${idTramite}"/>
<input type="hidden" id="hdnIdRazonRegistro" value = "${idRazonRegistro}"/>


<script type="text/javascript">
	
	function ajaxFileUpload() {
		var ext = $("#fileToUpload").val();
		var n = ext.split("\\");
		var nombreExtension = n[n.length - 1];
		n = nombreExtension.split(".");
		nombreExtension = n[n.length - 1];

		var urlDocumento = context_path+"/fileupload/uploadify";

		//'*.gif; *.jpg; *.png;*.pdf'
		if (nombreExtension == 'gif' || nombreExtension == 'jpg'
				|| nombreExtension == 'png' || nombreExtension == 'pdf') {
			nombreExtension = "";
			$("#loading").ajaxStart(function() {
				$(this).show();
			}).ajaxComplete(function() {
				$(this).hide();
			});

			$.ajaxFileUpload(

			{
				url : urlDocumento,
				secureuri : false,
				fileElementId : 'fileToUpload',
				dataType : 'json',
				data : {
					name : 'logan',
					id : 'id'
				},

				success : function(data, status) {
					alert('Hola')
				},
				error : function(data, status, e) {
					alert('Hola 2')
					alert(e);
				}
			})
		} else {
			mensageConfirmacion('Tipo de archivo no valido');
		}

		return false;

	}

	function mensageConfirmacion(mensaje) {
		$ventana = $('<div></div');

		$ventana.append(mensaje);
		$ventana.dialog({
			autoOpen : false,
			title : 'Mensaje',
			show : "blind",
			hide : "explode",
			modal : true,
			height : 150,
			width : 250,
			buttons : {
				"Aceptar" : function() {
					cierraDialogo($(this));
				}
			}

		});

		$ventana.dialog('open');
	}

	function cierraDialogo($dialogo) {
		$dialogo.dialog('close');
		$dialogo.dialog('destroy');
		$dialogo.html('');
	}
</script>

<div class="col-sm-12">
<div class="form-comment" id="allFileUploadDiv">
<form action="" id="formdocumentgeneral" role="form">
	<div id="fieldSelDoc">
	<div class="separadorseccion">
		<span>
			<spring:message code="label.fileUpload.seleccionDocumentos" />
		</span>
	</div>
	<div class="alert alert-danger" id="erroresFileUploadDiv" style="display:none;">
</div>
	<table class="table table-striped table-bordered">
		<thead>
			<tr>
				<th>
					<label class="control-label" for="idTramite">
					Tipo<span class="required">*</span>:
					</label>
				</th>
				<th>
					<label class="control-label" for="idDocumento">
					Documento<span class="required">*</span>: 
					</label>
				</th>
				<th colspan="2"></th>
			</tr>
		</thead>
		<tbody>
		<tr>
			<td>
				 <select id="idTipoDocSelected" name="idTramite" onchange="cargaDocumentosDelTipo();" class="form-control">
					<option value="-1">--Selecciona por favor--</option>
				</select>
			</td>
			<td>
				<select id="idDocSelected" name="idDocumento" onchange="showButtonCapturaCarga();" class="form-control">
					<option value="-1">--Selecciona por favor--</option>
				</select>
			</td>
			<td>
				<div class="pull-right">
					<button type="button" id="capturaDoc" title="Button" class="btn btn-primary"
					onclick="capturaDocfileUpload();"><spring:message
					code="button.fileUpload.capturaDocumento" /></button>
					
					<div id="file_browse_wrapper" style="vertical-align: middle;; display: none;"><input
					id="fileToUpload" type="file" name="file" onclick="ajaxFileUpload()"
					class="mboton"></div>
				</div>
			</td>
			
		</tr>
		</tbody>
	</table>
	</div>
<input type="hidden" id="curpCap" value="${curp}"/>
<input type="hidden" id="idUmf" value="${idUmf}"/>



<div id="divFileUploadDocs">
	<div class="separadorseccion">
		<span>
			<spring:message code="label.fileUpload.documentosCargados" />
		</span>
	</div>
	<div id="listaDocCargados" class="form-comment"></div>
</div>
<br>
<div id="fileUploadMessages"></div>

</form>

<div class="row">
		<div class="col-sm-4">
			<div style="float: left; padding: 11px 0px;"><span class="required" id="labelCamposObligatoriosGeneral">*</span>Campos obligatorios</div>
		</div>
		<div class="col-sm-8">
			<div class="pull-right">
				<button class="btn btn-danger" id="cerrarWizardDocumentos">Cerrar</button>
				<button class="btn btn-primary" id="aceptarDocumentos">Aceptar</button>
			</div>
		</div>
</div>
<script type="text/javascript">
	$('#allFileUploadDiv').hide();
</script></div>
</div>

<div id="msgDocumentosProb"
	title="<spring:message code="titulo.mensajeAviso"/>"
	style="display: none"><spring:message code="msgDocumentosProb" />
</div>

<div id="docProbTramDiv"></div>
<div id="mensajeError"></div>
