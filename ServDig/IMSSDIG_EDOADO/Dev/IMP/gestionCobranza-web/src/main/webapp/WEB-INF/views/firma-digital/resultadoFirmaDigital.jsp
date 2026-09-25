<%@ include file="../general/taglibs.jsp"%>

<style type="text/css">
.cadenaOriginal {
	font-size: 12px;
}

.hero-unit p {
	font-size: small;
}
</style>

<c:if test="${empty firmaElectronica.errorFormGeneral}">
	<script type="text/javascript">
	
		var objCtrl = parent.FirmaDigitalCtrl;
		
		$(document).ready(function(){
			$('#btnAceptarExito').click(function(){
				$.postJSON('/gestionCobranza-web/firma-digital/generar-JSON-firma-digital', null, function(data) {
					objCtrl.setDatosSalida(data);
					objCtrl.cerrar();
				}).error(function(data){
					
					var objErrores = jQuery.parseJSON(data.responseText);
					
					$('#msgError').text(objErrores);
					$('#msgErrorDiv').show();
				});		
			});
			
			detalleTextoParrafoEstandar($('pre.cadenaOriginal'));
			detalleTextoCorrido($('#selloDigitalText'));
			
			$('span.details').hide();
			
			$('a.read-more').click(function() {
				$(this).hide().next('span.details').fadeIn();
				return false;
			});
			
			$('a.hide-detail').click(function() {
	            $(this).parent().fadeOut().prev('a.read-more').show();
	            return false;
	        });
		});
		
		/* 
		 * Funcion para generar la funcionalidad para mostrar/ocultar detalle
		 * en un texto est�ndar, es decir, con espacios, comas, etc
		 */
		function detalleTextoParrafoEstandar(elemento) {
			var slicePoint = 100;
			var widow = 4;
			var allText = $(elemento).html();
			var startText = allText.slice(0, slicePoint).replace(/\w+$/, '');
			var endText = allText.slice(startText.length);
	
			if (endText.replace(/\s+$/, '').split(' ').length > widow) {
				$(elemento).html([startText,
				              ' <a href="#" class="read-more">Ver detalle...</a>',
				              '<span class="details">', 
				              endText,
				              ' <a href="#" class="hide-detail">Ocultar detalle...</a></span>' ].join(''));
			}
		}
		
		/* 
		 * Funcion para generar la funcionalidad para mostrar/ocultar detalle
		 * en un texto corrido, es decir, que no tenga espacios, comas, etc.
		 */
		function detalleTextoCorrido(elemento) {
			var slicePoint = 100;
			var allText = $(elemento).html();
			var startText = allText.slice(0, slicePoint);
			var endText = allText.slice(startText.length);
			
			$(elemento).html([startText,
			              ' <a href="#" class="read-more">Ver detalle...</a>',
			              '<span class="details">', 
			              endText,
			              ' <a href="#" class="hide-detail">Ocultar detalle...</a></span>' ].join(''));
		}
	</script>
</c:if>

<div>
	<div class="row">
		<div class="cell">
			<c:choose>
				<c:when test="${not empty firmaElectronica.errorFormGeneral}">
					<div class="alert alert-danger" style="width: 90%; margin: 0 auto;">
						<button type="button" class="close" data-dismiss="alert">�</button>
						<strong>Error: </strong>${firmaElectronica.errorFormGeneral}
					</div>
					<br>
					<div style="float: right;">
						<input type="button" value="Aceptar" onclick="cancelar();"
							class="btn btn-default" />
					</div>
				</c:when>
				<c:otherwise>
					<div id="msgErrorDiv" class="alert alert-danger"
						style="margin: 15px auto; display: none; text-align: center;">
						<strong>Error: </strong><label id="msgError"
							style="display: inline;"></label>
					</div>


					<div class="row">
						<div class="cell">
							<p style="text-align: right;">
								<strong>Recibo Notarial:</strong> <span
									style="font-weight: bolder; font-size: medium; color: #157164">${firmaElectronica.reciboNotarial}</span>
							</p>
							<br>
						</div>
					</div>
					<div class="row">
						<div class="cell">
							<p>
								<strong>Cadena Original:</strong>
							</p>
							<c:choose>
								<c:when test="${firmaElectronica.firmarArchivo eq true }">
									<pre class="cadenaOriginal">${firmaElectronica.fileNameToSign}</pre>
								</c:when>
								<c:otherwise>
									<pre class="cadenaOriginal">${firmaElectronica.cadenaOriginal}</pre>
								</c:otherwise>
							</c:choose>
							<br>
						</div>
					</div>
					<div class="row">
						<div class="cell">
							<p>
								<strong>Sello digital:</strong>
							</p>
							<p style="word-wrap: break-word; font-size: smaller;" id="selloDigitalText">
								${firmaElectronica.sPKCS7}
							</p>
						</div>
					</div>

					<div style="float: right;">
						<input type="button" id="btnAceptarExito" value="Aceptar"
							class="btn btn-default" />
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</div>
