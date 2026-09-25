<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="folio"  value="${solicitudModel.noFolioSolicitud}" ></c:set>
<div class="page_holder">
	<div class="contenedor">
		<div class="row">
			<div class="cell">
				<h2 style="font-size: 14px;padding-top: 25px;">La solicitud fue finalizada satisfactoriamente</h2>
				<div style="margin:0 auto 0 auto; width:850px;text-align: center;">
					<label>
						<strong>Folio:</strong>&nbsp;&nbsp;&nbsp;<span>${folio}</span>
					</label>
				</div>
				<br />
				<div style="margin:0 auto 0 auto; width:850px;">
<!-- 					<p style="text-align: justify;">Mauris mauris ante, blandit et, -->
<!-- 					ultrices a, suscipit eget, quam. Integer ut neque. Vivamus nisi -->
<!-- 					metus, molestie vel, gravida in, condimentum sit amet, nunc. Nam a -->
<!-- 					nibh. Donec suscipit eros. Nam mi. Proin viverra leo ut odio. -->
<!-- 					Curabitur malesuada. Vestibulum a velit eu ante scelerisque -->
<!-- 					vulputate.</p> -->
				</div>
				<br />
				<div style="margin:0 auto 0 auto; width:850px;text-align: center;">
				<form action="">
					<input type="button" class="mboton" name="goBack" id="goBack" value="Regresar">
				</form>
				</div>
			</div>
		</div>
	</div>
</div>

<c:set var="idSolicitud"  value="${firmaElectronicaModel.idSolicitud}" ></c:set>
<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<script type="text/javascript">
<!--
$(function() {
	$('#goBack').click(function() {		
		history.go(-4);
	});
});
//-->
</script>
