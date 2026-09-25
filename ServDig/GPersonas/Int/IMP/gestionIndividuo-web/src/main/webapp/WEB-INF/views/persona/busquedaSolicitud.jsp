<%--@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"--%>
<%@ include file="taglibs.jsp" %>
<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<script type="text/javascript">
	$(document).ready(function() {

		$('#buscarBtn').click(function() {
			submit();
		});
			
		$("#idSolicitud").keypress(function(eventObject) {
			if(eventObject.which == 13) { // 13 == enter
				submit();
			}
		});
	});
	
	function submit() {
		if($('#idSolicitud').val() == '') {
			alert('Debe ingresar un folio de solicitud para hacer la consulta.');
			return false;
		} else {		
			var urlAction = "<c:out value='${contextpath}' />" + '/solicitud/busqueda/' + $('#idSolicitud').val();
			$('#busquedaSolicitudForm').attr('action', urlAction);
			$('#busquedaSolicitudForm').submit();
		}
	}
</script>

<div class="page_holder_no_height">
	<div class="post_entry_wide no-border">
		
		<div class="form-comment">
			
			<h2 style="font-size: 1.3em !important;">${mensajeExito}</h2>
			<br />

			<form id="busquedaSolicitudForm" method="post" action="">
				<div class="separadorseccion">B&uacute;squeda de Solicitud</div>
			
				<fieldset>
					<legend>
						<strong>&nbsp;B&uacute;squeda de Solicitud&nbsp;</strong>
					</legend>
					<label class="wide" for="idSolicitud">Folio*</label>
					<input type="text" maxlength="18" style="width: 300px" name="idSolicitud" id="idSolicitud" class="numerico">
					<br /><br /><br />					
				</fieldset>

				<div style="float: right;">
					<input type="button" value="Buscar Solicitud" class="mboton" id="buscarBtn"/>
				</div>		
				<br /><br /><br />
			</form>	
		
		</div>
	</div>
</div>
			