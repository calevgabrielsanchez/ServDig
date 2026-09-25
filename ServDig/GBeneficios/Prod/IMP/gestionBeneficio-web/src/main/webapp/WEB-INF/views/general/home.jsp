<%@ include file="../general/taglibs.jsp"%>


<div id="homecontenido" class="contenedor">
	<div class="row" style="height: 500px;">

		<div class="cell" style="padding: 20px;">

			<div class="row">

				<h3>Bienvenido</h3>
				<div class="textwidget">
					<p style="font-size: .9em;">
						<spring:message code="label.informacion.sistema" />
					</p>
				</div>
				</br> <img
					src="<spring:url value="/static/resources/imagenes/tramites.jpg" htmlEscape="true" />"
					title="Portal IMSS" />

			</div>
			<div class="row">

				<div class="cell avisos" >
								<div class="box">
									<h3>Aviso 1</h3>
									<div class="textwidget">
									<p style="font-size: .9em;"><spring:message
										code="label.informacion.sistema" /></p>
									</div>
								</div>
								<div class="box">
									<h3>Aviso 4</h3>
									<div class="textwidget">
									<p style="font-size: .9em;"><spring:message
										code="label.informacion.sistema" /></p>
									</div>
								</div>
								<div class="box">
									<h3>Aviso 3</h3>
									<div class="textwidget">
									<p style="font-size: .9em;"><spring:message
										code="label.informacion.sistema" /></p>
									</div>
								</div>
								
					</div>
				

			</div>


		</div>
		<div class="cell contenedor-usuario-home">

			<h2>Mis Solicitudes</h2>
			<p style="font-size: .9em;">
				<spring:message code="label.gestionBeneficio.informacion.usuario.nuevo" />
			</p>


		</div>
	</div>
</div>
