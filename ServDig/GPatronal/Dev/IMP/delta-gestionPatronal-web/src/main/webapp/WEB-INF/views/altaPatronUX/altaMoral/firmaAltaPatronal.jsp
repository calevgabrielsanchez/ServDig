<%@ include file="../../general/taglibs.jsp"%>
<div class="row">
	<div class="col-sm-12">
		<div class="row">
			<div class="col-sm-12">
				<h4>8. Generar tu N&uacute;mero de Registro Patronal</h4>
				<h5>Paso 2 de 3 : Firma del tr&aacute;mite de alta patronal para personas moral.</h5>
				<p>Para finalizar el tr&aacute;mite es necesario que el representante legal firme con su FIEL.</p>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<div id="firmaElectronicaAP"></div>
			</div>
		</div>
		<div class="row" id="divProcesandoSolicitud" style="display:none">
			<div class="col-sm-12">
				<div class="well m-t-lg m-b-lg">
					<p style="font-size: large; font-weight: bold; text-align: center;">
						La solicitud <span id="folioProcesando"></span> est&aacute; en proceso ...
					</p>
			
					<div style="text-align: center; vertical-align: middle;">
						<img alt="" src="${staticResourcesPath}/imagenes/loading.gif" />
					</div>
			
					<p style="margin-top: 18px;">
						Esta p&aacute;gina se recargar&aacute; autom&aacute;ticamente en cuanto su solicitud haya sido procesada. Se
						recomienda <strong>no</strong> cerrar esta ventana.
					</p>
				</div>
			</div>
		</div>
	</div>
</div>
