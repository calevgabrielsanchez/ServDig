<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoTramiteEnum"%>

<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<c:set var="socio" value="<%=TipoTramiteEnum.ACTUALIZACION_SOCIO.getCodigo().intValue()%>" />

<script type="text/javascript">
	$(document).ready(function() {
		$('#generarAviso').click(function() {
			recuperaAvisoMod();
		});
		
		$('#cerrarWizard').click(function() {
			cerrarWizard();
		});

		$('#formaGeneraAviso').submit(function() {
			$.unblockUI();
			$('ul li.dropable').remove();
			//cerrarWizard();
		});
	});

	function recuperaAvisoMod() {
		$("#formaGeneraAviso").submit();
	}

	function cerrarWizard() {	
		parent.WizardModificacionPatronClasificacionCtrl.cerrar();
	}
</script>

<div class="contenedor">
	<div class="contenido" style="width: 100%;">
		<div class="row" id="divSujetoObligado">
			<br>
			<fieldset style="width: 90%;">
				<h2>
					<spring:message code="label.solicitud.concluida" />
				</h2>
				<table style="width: 100% !important; border: none !important;">
					<tr>
						<td style="border: none !important;" colspan="4">
							<div id="fechasError" style="color: red;"></div>
							<table>
								<tr class="fielsetgris">
									<td align="center"><spring:message code="texto.clasificacion.solicitud.concluida" /> <br /> <br />
										<spring:message code="texto.clasificacion.solicitud.concluida.complemento" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<br>
				<br>
			</fieldset>
		</div>

	</div>

	<div class="pie">
		<div class="opciones">
			<div class="pull-right">
			<button class="btn btn-danger" id="cerrarWizard" onclick="uid_call('imss.gestion.patronal.modificaciones.srt.btn_cerrar','clickout');">Cerrar</button>
			<c:if test="${empty mensajeError}">
				<div class="btn-group">
					<a href="#" class="btn btn-primary" onclick="uid_call('imss.gestion.patronal.modificaciones.srt.btn_opciones','clickin');"><spring:message code="label.menus.opciones" /></a>	
					<a href="#" data-toggle="dropdown" class="btn btn-primary dropdown-toggle" onclick="uid_call('imss.gestion.patronal.modificaciones.srt.btn_opciones','clickin');><span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li class="dropable"><a id="generarAviso" onclick="uid_call('imss.gestion.patronal.modificaciones.srt.btn_opciones','PDF');><i class="icon-printing"></i>Generar aviso</a></li>
					</ul>
				</div>
			</c:if>
			</div>
			
		</div>
		<div class="controles"></div>
	</div>
</div>

<form id="formaGeneraAviso" name="formaGeneraAviso" action="/delta-gestionPatronal-web/clasificacion/mostrarAviso" method="POST" target="_blank"></form>



