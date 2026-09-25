<%@ include file="../../../general/taglibs.jsp"%>

<script type="text/javascript"
	src="<spring:url value="/static/resources/js/wizard/rtt/tycRtt.js" htmlEscape="true" />">
</script>
<script>
	var contextpath = "${contextpath}";
</script>

<style>
.justificado {
	text-align: justify;
}

.centrado {
	text-align: center;
}
</style>

<div class="col-sm-12">
	<div class="contenido row">
		<div class="col-sm-12">
			<p class="centrado">
				<strong> Carta de T&eacute;rminos y Condiciones para el Uso
					del Sistema de Consulta de Riesgos de Trabajo Terminados </strong>
			</p>
			<br />
			<p class="justificado">A trav&eacute;s de este instrumento se
				acepta realizar la consulta de riesgos de trabajo terminados
				sujet&aacute;ndose a los siguientes</p>
			<br />
			<p class="centrado"><strong>T&Eacute;RMINOS Y CONDICIONES</strong></p>
			<br />
			<ol>
				<li>
					<p class="justificado">El patr&oacute;n, o su representante
						legal, manifiestan y acepta conocer que la informaci&oacute;n
						contenida en el sistema de consulta de riesgos de trabajo
						terminados es &uacute;nica y exclusivamente de car&aacute;cter
						informativo y no debe considerarse como un acto de autoridad del
						Instituto Mexicano del Seguro Social, quien pone a su
						disposici&oacute;n la mencionada informaci&oacute;n con el fin de
						que complemente, en su caso, los controles de documentaci&oacute;n
						y el registro pormenorizado que est&aacute; obligado a llevar
						sobre dichos riesgos, en t&eacute;rminos de lo dispuesto por los
						art&iacute;culos 32, fracciones I y V, y 34 del Reglamento de la
						Ley del Seguro Social en Materia de Afiliaci&oacute;n,
						Clasificaci&oacute;n de Empresas, Recaudaci&oacute;n y
						Fiscalizaci&oacute;n, y 19 del Reglamento de Prestaciones
						M&eacute;dicas del Instituto Mexicano del Seguro Social.</p> <br />
				</li>
				<li>
					<p class="justificado">El patr&oacute;n, o representante legal
						es responsable del uso adecuado del sistema y la guarda de
						informaci&oacute;n, en t&eacute;rminos de las disposiciones en
						materia de protecci&oacute;n de datos personales en
						posesi&oacute;n de los particulares.</p> <br />
				</li>
				<li>
					<p class="justificado">Cuando el patr&oacute;n o representante
						legal cuente en sus registros con riesgos de trabajo terminados,
						adicionales a los enlistados en la consulta, deber&aacute;
						declararlos en su determinaci&oacute;n anual de la prima del
						Seguro de Riesgos de Trabajo, con motivo de la revisi&oacute;n de
						su siniestralidad, de conformidad con lo establecido en los
						art&iacute;culos 72 y 74 de la Ley del Seguro Social y 32,
						fracciones I y V, del Reglamento de la Ley del Seguro Social en
						Materia de Afiliaci&oacute;n, Clasificaci&oacute;n de Empresas,
						Recaudaci&oacute;n y Fiscalizaci&oacute;n.</p> <br />
				</li>
				<li>
					<p class="justificado">Si el Instituto identificara otros
						riesgos de trabajo que no aparezcan en la relaci&oacute;n de casos
						mostrados y que hayan ocurrido dentro del periodo de
						revisi&oacute;n de la siniestralidad que se consulta, lo
						har&aacute; de conocimiento del patr&oacute;n o representante
						legal mediante resoluci&oacute;n de determinaci&oacute;n o
						rectificaci&oacute;n de prima , en la cual se detallar&aacute; el
						riesgo de trabajo de que se trate, de acuerdo con lo dispuesto por
						el art&iacute;culo 32, fracci&oacute;n VI del Reglamento de la Ley
						del Seguro Social en Materia de Afiliaci&oacute;n,
						Clasificaci&oacute;n de Empresas, Recaudaci&oacute;n y
						Fiscalizaci&oacute;n.</p>
				</li>
			</ol>
			<br />
			<p class="justificado">Los t&eacute;rminos y condiciones antes
				se&ntilde;alados son aplicables a la aceptaci&oacute;n de los mismos que
				realice la persona f&iacute;sica o moral, al realizar la consulta de
				riesgos de trabajo terminados.</p>
			<div class="m-t-lg">
				<input type="checkbox" id="chkCartaTC"> Declaro que he
				le&iacute;do y conozco los alcances legales de los t&eacute;rminos y
				condiciones antes se&ntilde;alados, aceptando voluntariamente los mismos.
				<br />
			    <p class="centrado">${fechaCarta}</p>
			</div>
			
		</div>
	</div>
	<div class="pie row">
		<div class="opciones col-sm-6"></div>
		<div class="controles col-sm-6 text-right">
			<br />
			<button id="cancelarCartaTC" class="btn btn-default">Cancelar</button>
			<button id="aceptaCartaTC" class="btn btn-primary">Aceptar</button>
		</div>
	</div>
</div>
<c:if test="${rfc == null}" >
	<form:form action="${contextpath}/wizard/riesgosTrabajo/aceptar"
			   method="post" id="idForm">
	</form:form>
</c:if>

<c:if test="${rfc != null}" >
	<form:form action="${contextpath}/wizard/riesgosTrabajo/aceptarRfc"
			   method="post" id="idForm">
	</form:form>
</c:if>