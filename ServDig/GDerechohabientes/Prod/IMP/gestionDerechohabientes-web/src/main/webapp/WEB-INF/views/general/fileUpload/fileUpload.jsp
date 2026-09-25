<%@ include file="../taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="<spring:url value="/static/resources/js/uploadify3/uploadify.css" htmlEscape="true" />" />

<style  type="text/css">

     
#my_custom_button {
    width: 150px;
    height: 150px;
    background: url('/gestionDerechohabientes-web-admon/static/resources/imagenes/btn_buscar.png') 0 0 no-repeat;
    border:none;
    padding: 0 0 0 0;
    font-weight:bold
    cursor:pointer;
}
#my_custom_button:hover {
    background: url('/gestionDerechohabientes-web-admon/static/resources/imagenes/btn_buscar.png') 0 0 no-repeat;
    cursor:pointer;
}
#my_custom_button:active {
    background: url('/gestionDerechohabientes-web-admon/static/resources/imagenes/btn_buscar.png') 0 0 no-repeat;
    cursor:pointer;
}


#file_browse_wrapper {
    width: 200px;
    height:30px;
    background: url('/gestionDerechohabientes-web-admon/static/resources/imagenes/btn_buscar.png') 0 0 no-repeat;
    border:none;
    overflow:hidden;
    cursor:pointer;
    vertical-align: middle;
}
#file_browse_wrapper:hover {
    background: url('/gestionDerechohabientes-web-admon/static/resources/imagenes/btn_buscar.png') 0 0 no-repeat;
    cursor:pointer;
}
#file_browse_wrapper:active {
    background: url('/gestionDerechohabientes-web-admon/static/resources/imagenes/btn_buscar.png') 0 0 no-repeat;
    cursor:pointer;
}

#fileToUpload{
    margin-left:-145px;
    opacity:0.0;
    -ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=0)";
    filter: progid:DXImageTransform.Microsoft.Alpha(Opacity=0);
}

</style>

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/fileRead/fileRead.js" htmlEscape="true" />"></script>
<script type="text/javascript">


function ajaxFileUpload()
{
	var ext = $("#fileToUpload").val();
	var n=ext.split("\\");
	var nombreExtension = n[n.length -1];
	n=nombreExtension.split(".");
	nombreExtension = n[n.length -1];
	
	var urlDocumento= context_path+"/fileupload/uploadify";
	
	//'*.gif; *.jpg; *.png;*.pdf'
	if(nombreExtension=='gif' || nombreExtension=='jpg'|| nombreExtension=='png'|| nombreExtension=='pdf'){
		nombreExtension="";
		$("#loading")
		.ajaxStart(function(){
			$(this).show();
		})
		.ajaxComplete(function(){
			$(this).hide();
		});
	
		$.ajaxFileUpload
		(
				
			{
				url:urlDocumento,
				secureuri:false,
				fileElementId:'fileToUpload',
				dataType: 'json',
				data:{name:'logan', id:'id'},
				
				success: function (data, status)
				{	
					alert('Hola')
				},
				error: function (data, status, e)
				{
					alert('Hola 2')
					alert(e);
				}
			}
		)
	}else{
		mensageConfirmacion('Tipo de archivo no valido');
	}
	
	
	
	return false;

}

function mensageConfirmacion(mensaje){
	$ventana = $('<div></div');
	
	$ventana.append(mensaje);	
	$ventana.dialog({
		autoOpen : false,
		title: 'Mensaje',
		show: "blind",
		hide: "explode",
		modal: true,
		height: 150,
		width: 250,
		buttons: {
			"Aceptar": function() {
				cierraDialogo($(this));
			}
		}
	
		});
	
	
	$ventana.dialog('open');
}

function cierraDialogo($dialogo){
	$dialogo.dialog('close');
	$dialogo.dialog('destroy');
	$dialogo.html('');
}
</script>

<div class="form-comment" id="allFileUploadDiv">
<form action="">
			<fieldset id="fieldSelDoc">
				<legend>
					<b><spring:message code="label.fileUpload.seleccionDocumentos"/></b>
				</legend>
				<div class="alert alert-info" id="mensajeActasOpcionales"><spring:message code="message.fileUpload.actas"/></div>
    			<table style="width: 860px;">
					<tr>
						<td align="right" colspan="5">
							<br>
						</td>
					</tr>
					<tr> 
       					<td> 
				  			Tipo: <select  id="idTipoDocSelected" name="idTramite" onchange="cargaDocumentosDelTipo();"></select>			
						</td> 
						<td><spring:message code="label.fileUpload.documento"></spring:message></td> 
						<td> 
				  			<select  id="idDocSelected" name="idDocumento" onchange="showButtonCapturaCarga();"></select>       			
						</td> 
						<td>
							<button type="button" id="capturaDoc"  class="mboton" onclick="capturaDocfileUpload();"><spring:message code="button.fileUpload.capturaDocumento"/></button>
						</td>
						<td style="vertical-align: middle; display:none;" rowspan="2">	
							<div id="file_browse_wrapper" style="vertical-align: middle;">
								<input id="fileToUpload" type="file" name="file" onclick="ajaxFileUpload()" class="mboton">
							</div>
						</td>
					</tr> 
					
					
					
    			</table> 
 			<!--<center> <button type="button" id="cargaDoc" onclick="getLisDoc();" title="Button" class="mboton"><spring:message code="button.fileUpload.cargarDocumento"/></button> </center>-->
    	
  			</fieldset>
 			
 			 
 			 <div class="ui-widget-content ui-corner-all" id=erroresFileUploadDiv>
 			 <br>
				<div class="ui-state-error ui-corner-all" align="center">
					<span class="ui-icon ui-icon-alert"></span>
					<p class="ui-helper-reset ui-state-error-text" id="errorFileUploadDiv"></p>
				</div>
			</div>
			
 	 <fieldset id="divFileUploadDocs">
		<legend>
			<spring:message code="label.fileUpload.documentosCargados"/>
		</legend>
		
    	<div id="listaDocCargados" class="form-comment"> 
    	
		</div> 
		
	</fieldset>
	
	<div id="docProbTramDiv"></div>
	
  	<div id="fileUploadMessages"> 
	</div> 
	<center>
		<button type="button" id="guardaDocumentosButton" onclick="doSaveDocS();" title="Button" class="mboton">
			<spring:message code="button.fileUpload.guardar"/>
		</button>
	</center>
    
 </form>
    <script type="text/javascript">
		$('#allFileUploadDiv').hide();
	</script>
</div>