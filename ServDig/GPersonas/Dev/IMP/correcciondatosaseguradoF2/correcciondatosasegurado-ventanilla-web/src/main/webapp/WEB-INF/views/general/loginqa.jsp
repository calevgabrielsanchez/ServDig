<%@ include file="../general/taglibs.jsp"%>

<div id="contenedor-login" class="contenedor">

	<div class="row">

		<div class="cell credenciales">
			<div class="credenciales-caja">
				<h2>
					<spring:message text="Ingresar" />
					${propertyConfigurer['url.welcome.redirect']}
				</h2>
				<c:set var="contextpath" value="<%=request.getContextPath()%>" />
				<div>
					<form:form modelAttribute="usuario"
						action="${contextpath}/loginqa/entrar" method="post" id="formlogin">


						<div id="usuario-contenedor">
							<form:errors path="usuario" cssClass="error" />
							<form:label path="usuario">
								<strong class="etiqueta"> Usuario </strong>
							</form:label>
							<form:input type="text" path="usuario" maxlength="20" />
						</div>

						<div id="password-contenedor">
							<form:errors path="password" cssClass="error" />
							<form:label path="password">
								<strong class="etiqueta"> Password </strong>
							</form:label>
							<form:input type="password" path="password" id="password" value=""
								maxlength="13" />
						</div>

						<br>

						<div class="derecha">
							<input type="submit" class="mboton"
								value="Ingresar">
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>

</div>