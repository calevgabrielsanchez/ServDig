<%@ include file="../general/taglibs.jsp"%>

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
					<label class="col-sm-2 control-label">Fecha:</label>
					<div class="col-sm-4">
						<p class="form-control-static">
							<c:out value='${fechaSistema}' />
						</p>
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
					<label class="col-sm-2 col-sm-offset-6 control-label">
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

<div id="dgCerrarSesion" title="Cerrar Sesi&oacute;n" style="">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 0 7px 20px 0;"> </span>
		&iquest;Est&aacute; Ud. seguro de cerrar su sesi&oacute;n?
	</p>
</div>