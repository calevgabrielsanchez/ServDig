<%@ include file="../general/taglibs.jsp"%>

<style>
	a.no-link:hover{
		text-decoration: none;
		cursor: text;
	}	
</style>

<script>
	var usuarioSesionPortal = '${usuario.usuario}';
	var idDialogoCerrarSesion = "#dgCerrarSesion";
	var oDialogoCerrarSesion;
	
	$(function(){
		
		oDialogoCerrarSesion = $(idDialogoCerrarSesion).dialog({
			autoOpen : false,
			resizable : false,
			height : 'auto',
			modal : true,
			buttons : {
				"Aceptar" : function(data) {
					$("#formCerrarSesion").submit();
				},
				'Cancelar' : function() {
					$(this).dialog("close");
				}
			}
		});
		
		$('#cerrarSesionLink').live( 'click' , function(){
			fnAbrirDialogoCerrarSesion();
		});
		
	});
	
	/*Funcion para abrir el dialogo cerrar sesion*/
	var fnAbrirDialogoCerrarSesion = function() {
		oDialogoCerrarSesion.dialog('open');
	};
</script>

<div class="well info-usuario">
	<div class="row">
		<div class="col-xs-11" style="padding-right: 0px;">
			<form class="form-horizontal">
				<div class="form-group">
					<label class="col-sm-2 control-label">Usuario:</label>
					<div class="col-sm-4">
						<p class="form-control-static">
							<c:out value='${usuario.usuario}' />
						</p>
						<input id="userCtrl" type="hidden" value="${usuario.usuario}" />
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">Delegaci&oacute;n:</label>
					<div class="col-sm-4">
						<p class="form-control-static">
							<c:out value='${usuario.usuarioFuncionario.delegacion.clave} - ${usuario.usuarioFuncionario.delegacion.descripcion}' />
						</p>
					</div>
					<label class="col-sm-2 control-label">Subdelegaci&oacute;n:</label>
					<div class="col-sm-4">
						<p class="form-control-static">
							<c:out value='${usuario.usuarioFuncionario.subdelegacion.clave} - ${usuario.usuarioFuncionario.subdelegacion.descripcion}' />
						</p>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">Fecha:</label>
					<div class="col-sm-4">
						<p class="form-control-static">
							<c:out value='${fechaSistema}' />
						</p>
					</div>
					<label class="col-sm-2 control-label">
						<spring:message code="label.version" text="Version" />:
					</label>
					<div class="col-sm-4">
						<p class="form-control-static">
							<spring:message code="version" text="1.0.0" />
						</p>
					</div>
				</div>
			</form>
		</div>
		<div class="col-xs-1" style="padding: 25px 0px;">
			<a href="<%=request.getContextPath()%>/portal"
				style="padding-right: 10px;">
				<i class="icon-home fa-lg"></i>
			</a>
			<a href="#" onclick="fnAbrirDialogoCerrarSesion();">
				<i class="glyphicon glyphicon-log-out  fa-lg"></i>
			</a> 
			
		</div>
	</div>
</div>

<div id="dgCerrarSesion" title="Cerrar Sesi&oacute;n" style="display: none;">
	<p style="margin-bottom: 0px;">
		<span class="ui-icon ui-icon-alert" style="float: left; margin-right: 7px; margin-top: 3px;"> </span> ¿Est&aacute;
		usted seguro de cerrar su sesi&oacute;n?
	</p>
</div>