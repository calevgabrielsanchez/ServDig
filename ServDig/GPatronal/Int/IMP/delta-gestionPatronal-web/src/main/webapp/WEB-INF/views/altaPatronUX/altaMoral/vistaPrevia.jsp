<%@ include file="../../general/taglibs.jsp"%>

<script type="text/javascript" src="<spring:url value="/static/resources/js/altaPatronUX/vistaPrevia.js" htmlEscape="true" />"></script>

<div class="row">
	<div class="col-sm-12">
		<div class="row">
			<div class="col-sm-12">
				<h4>8. Generar tu Número de Registro Patronal</h4>
				<h5>Paso 1 de 2: Vista previa</h5>
			</div>
		</div>
		<p align="justify">
			Comprueba que todos los datos capturados,están correctos.
		</p>
		
		<iframe id="archivoFrame" width="100%" height="600" ></iframe>
		
		</br>
		</br>
		<p align="right">
			¿Est&aacute;s de acuerdo con la informaci&oacute;n proporcionada para generar tu N&uacute;mero de Registro Patronal?
		</p>
		</br>
		<div class="row">
			<div class="col-sm-12 text-right">
				<button id="idBtnNoDeAcuerdo" class="btn btn-default" style="width: 90px">No</button>
				<button id="idBtnSiDeAcuerdo" class="btn btn-primary" style="width: 90px">S&iacute;</button>
			</div>
		</div>
</div>
</div>