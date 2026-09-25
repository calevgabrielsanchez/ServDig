
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<html lang="sp">

<jsp:include page="main/head.jsp" />

<body>
	<div id="cuerpo_principal" style="width: 600px !important;">
		<div id="encabezado" style="width: 600px !important;">
			<div id="firma_busqueda_recortada" style="width: 100%"></div>
		</div>

		<div id="cuerpo" style="width: 600px !important; padding: 0px;">
			<div id="fraccion">
				<form:form modelAttribute="fraccion" id="fraccionForm">
					<fieldset style="border: none;">
						<fieldset>

							<table style="color: #222222; opacity: 0.7 !important;">
								<tr>
									<td style="width: 10%;"><label
										style="font-weight: bold; font-size: .75em">Fracci&oacute;n:</label></td>
									<td><form:label path="desFraccion"
											cssStyle="font-size: .9em !important;">
										${fraccion.desFraccion}
									</form:label></td>
								</tr>
								<tr>
									<td style="width: inherit;"><label
										style="font-weight: bold; font-size: .75em">Clase:</label></td>
									<td><form:label path="cveClase"
											cssStyle="font-size: .9em !important;">
										${fraccion.cveClase}
									</form:label></td>
								</tr>
								<tr>
									<td colspan="2">&nbsp;</td>
								</tr>
								<tr>
									<td colspan="2"><label
										style="font-weight: bold; font-size: .75em">
											Actividad:</label></td>
								</tr>
								<tr>
									<td colspan="2"><form:label path="nomActividad"
											cssStyle="font-size: .9em !important;">
										${fraccion.nomActividad}
									</form:label></td>
								</tr>
								<tr>
									<td colspan="2">&nbsp;</td>
								</tr>
								<tr style="text-align: justify !important;">
									<td colspan="2"><form:label path="desActividad"
											cssStyle="font-size: .7em !important;">
										${fraccion.desActividad}
									</form:label></td>
								</tr>
							</table>

						</fieldset>
					</fieldset>
				</form:form>
				<div
					style="text-align: justify !important; display: inline; color: #222222; opacity: 0.7 !important;">
					<fieldset style="border: none;">
						<fieldset>
							<span style="font-weight: bold; font-size: .6em;">IMPORTANTE:
							</span> <span style="font-size: .6em;">El presente documento es
								de car&aacute;cter informativo, por lo cual no es limitativo ni
								constituye propuesta por parte del Instituto, para la
								autoclasificaci&oacute;n que deber&aacute; realizar conforme al
								Cat&aacute;logo de Actividades establecido en el art&iacute;culo
								196 del Reglamento de la Ley del Seguro Social en Materia de
								Afiliaci&oacute;n, Clasificaci&oacute;n de Empresas,
								Recaudaci&oacute;n y Fiscalizaci&oacute;n.</span>
						</fieldset>
					</fieldset>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		window.print();
	</script>
</body>
</html>
