<%@ include file="taglibs.jsp" %>

<head>
	<style type="text/css">
		#concluirTramitesSubmit{
			position:relative;
			left:820px;
		}
	</style>
	
	<script type="text/javascript">
	
		// CUADRO DE DIALOGO DE ERROR AL CREAR LA SOLICITUD
		var oDialogoCerrarSesion;
		
		$(document).ready(function(){
			$('#concluirTramitesSubmit').click(function(){
				if(iTotalDisplayRecords > 0){
					$('#concluirTramitesForm').submit();
				}else{
					oDialogoCerrarSesion.dialog('open');
					return false;
				}
		
			});
			
			// Inicializacion del dialogo de error al crear la solicitud
			 oDialogoCerrarSesion = $('#dgError').dialog({
			        autoOpen:false,
			        resizable: false,
			        height:195,
			        modal: true,
			        buttons: {
			            "Aceptar": function(data) {
			            	$( this ).dialog( "close" );
			            }
			        }
			 });
			
		});
		
	</script>
</head>

<c:set var="context_path" value="<%=request.getContextPath()%>" />

<c:choose>
	<c:when test="${isFisica}">
		<jsp:include page="orquestadorFisica.jsp" />
		<c:set var="url_registro_captura" value="${context_path}/persona/fisica/registro-captura" />
	</c:when>
	<c:otherwise>
		<c:if test="${isMoral}">
			<jsp:include page="orquestadorMoral.jsp" />
			<c:set var="url_registro_captura" value="${context_path}/persona/moral/registro-captura" />
		</c:if>
	</c:otherwise>
</c:choose>

<div id="formulario">
	<form action="${url_registro_captura}" id="concluirTramitesForm">
		<input type="button" value="Concluir todos los Tr&aacute;mites" id="concluirTramitesSubmit" class="mboton" />
	</form>
</div>

<!-- DIV de query para poder desplegar un cuadro de dialogo tipo alert -->
<div id="dgError" title="Error al crear Solicitud" >
	<p>
		<span class="ui-icon ui-icon-alert" style="float: left; margin: 0 7px 20px 0;"> </span>
		 No se puede concluir una solicitud vac&iacute;a. <br /><br />Al menos debe existir un tr&aacute;mite
	</p>
</div>
<!--  -->
