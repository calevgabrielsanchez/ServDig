<%@ include file="../general/taglibs.jsp"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<style>
	.show-grid {
		margin-bottom: 15px;
	}
</style>

<div id="homecontenido" class="contenedor">
	<div class="row">
		<div class="col-xs-12">
			<div class="row show-grid" style="text-align: center;">
				<div class="col-xs-6">
					<h3 style="font-size: 1.8em !important; text-align: center;">
						Asignaci&oacute;n de
						N&uacute;mero de Seguridad Social</h3>
				</div>

				<%--
				<div class="col-xs-6">
					<h3 style="font-size: 1.8em !important; text-align: center;">
						Actualizaci&oacute;n de datos</h3>
				</div>
				--%>

			</div>
			<div class="row show-grid" style="text-align: center;">
				<div class="col-xs-6">
					<div class="textwidget">
						<p style="font-size: .9em;">
							<spring:message code="label.informacion.asignacion" />
						</p>
					</div>
				</div>

				<%--
				<div class="col-xs-6">
					<div class="textwidget">
						<p style="font-size: .9em;">
							<spring:message code="label.informacion.actualizacion" />
						</p>
					</div>
				</div>
				--%>
			</div>
			<div class="row show-grid" style="text-align: center;">
				<div class="col-xs-6">
					<form action="${contextpath}/tramite/iniciar" id="formConfirm"
						class="formNotBlock">
						<button type="submit" class="btn btn-primary">INICIAR</button>
					</form>
				</div>

				<%--
				<div class="col-xs-6">
					<form action="${contextpath}/tramite/actualiza/datos/iniciar"
						id="formConfirm" class="formNotBlock">
						<button type="submit" class="btn btn-primary">INICIAR</button>
					</form>
				</div>
				--%>
			</div>
		</div>


		<div class="col-xs-12">
			<div class="row show-grid">&nbsp;</div>
		</div>


		<div class="col-xs-12">
			<div class="row show-grid" style="text-align: center;">
				<%--
				<c:if test="${not empty SHOW_SIME}">
					<div class="col-xs-6">
						<h3 style="font-size: 1.8em !important; text-align: center;">
							Asignaci&oacute;n de
							N&uacute;mero de Seguridad Social a Estudiantes (SIE)</h3>
					</div>
				</c:if>
				--%>

				<c:if test="${not empty SHOW_SIME}">
					<div class="col-xs-6">
						<h3 style="font-size: 1.8em !important; text-align: center;">
							Asignaci&oacute;n de
							N&uacute;mero de Seguridad Social a Mexicanos en el Extranjero
							(SIME)</h3>
					</div>
				</c:if>
			</div>
			<div class="row show-grid" style="text-align: center;">
				<%--
				<c:if test="${not empty SHOW_SIME}">
					<div class="col-xs-6">
						<div class="textwidget">
							<p style="font-size: .9em;">
								<spring:message code="label.informacion.sie" />
							</p>
						</div>
					</div>
				</c:if>
				--%>

				<c:if test="${not empty SHOW_SIME}">
					<div class="col-xs-6">
						<div class="textwidget">
							<p style="font-size: .9em;">
								<spring:message code="label.informacion.sime" />
							</p>
						</div>
					</div>
				</c:if>
			</div>
			<div class="row show-grid" style="text-align: center;">
				<%--
				<c:if test="${not empty SHOW_SIME}">
					<div class="col-xs-6">
						<form action="${contextpath}/asignacion/inicio" id="formConfirm"
							class="formNotBlock">
							<button type="submit" class="btn btn-primary">INICIAR</button>
						</form>
					</div>
				</c:if>
				--%>

				<c:if test="${not empty SHOW_SIME}">
					<div class="col-xs-6">
						<form action="${contextpath}/sime/inicio" id="formConfirm"
							class="formNotBlock">
							<button type="submit" class="btn btn-primary">INICIAR</button>
						</form>
					</div>
				</c:if>
			</div>
		</div>
		
		<c:if test="${not empty SHOW_ADMON_SERIES}">
			<div class="col-xs-12">
				<div class="row show-grid">&nbsp;</div>
			</div>
			<div class="col-xs-12">
				<div class="row show-grid" style="text-align: center;">
					<div class="col-xs-6 col-xs-offset-3">
						<h3 style="font-size: 1.8em !important; text-align: center;">
							Administraci&oacute;n de series para
							Asignaci&oacute;n de N&uacute;mero de Seguridad Social</h3>
					</div>
				</div>
				<div class="row show-grid" style="text-align: center;">
					<div class="col-xs-6 col-xs-offset-3">
						<div class="textwidget">
							<p style="font-size: .9em;">
								<spring:message code="label.informacion.admon.series" />
							</p>
						</div>
					</div>
				</div>
				<div class="row show-grid" style="text-align: center;">
					<div class="col-xs-6 col-xs-offset-3">
						<form action="${contextpath}/serie/inicio" id="formConfirm"
							class="formNotBlock">
							<button type="submit" class="btn btn-primary">INICIAR</button>
						</form>
					</div>
				</div>
			</div>
		</c:if>
	</div>
</div>