<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="container-fluid empty-state">
	<div class="row">
		<!-- Imagen -->
		<div class="col-xs-12 imagen">
			<i class="fa fa-file-text"></i>
		</div>
	</div>
	<div class="row">
		<!-- Titulo -->
		<div class="col-xs-12 titulo">
					<spring:message
						code="label.wizard.tramite.pasos.titulo" />
		</div>
	</div>
	<br>
	</br>

	<!-- Opciones -->
	<div class="row opciones">
		<!-- Opcion -->
		<div class="col-xs-6">

			<div class=" opcion">
				<div class="">
				</div>
				<div class="">
					<p class="paso">
						<spring:message code="label.wizard.tramite.iniciar.titulo" />
					</p>
					<p class="descripcion">
						<spring:message code="label.wizard.tramite.iniciar.descripcion" />
					</p>
				</div>
			</div>
		</div>
		<!-- Opcion -->
		<div class="col-xs-6 ">

			<div class=" opcion">

				<div class="">
				</div>
				<div class="">
					<p class="paso">
						<spring:message code="label.wizard.tramite.capturar.titulo" />
					</p>
					<p class="descripcion">
						<spring:message
							code="label.wizard.tramite.capturar.descripcion" />
					</p>
				</div>

			</div>
		</div>
		
		<!-- Opcion -->
		<div class="col-xs-6 ">

			<div class="opcion">

				<div class="">
				</div>
				<div class="">
					<p class="paso">
						<spring:message code="label.wizard.tramite.finalizar.titulo" />
					</p>
					<p class="descripcion">
						<spring:message
							code="label.wizard.tramite.finalizar.descripcion" />
					</p>
				</div>

			</div>
		</div>

	</div>

</div>