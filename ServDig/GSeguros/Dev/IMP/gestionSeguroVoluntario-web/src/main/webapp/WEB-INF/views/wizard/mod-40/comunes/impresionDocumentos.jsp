<%@ include file="../../../general/taglibs.jsp"%>
<script type="text/javascript" src="<spring:url value="/static/resources/js/wizard/mod-40/comunes/wizardCVROalta.js" htmlEscape="true" />"></script>
<c:set var="contextPath" value="<%=request.getContextPath()%>" />

<style>
	.ui-selectable li {
	    padding: 15px 25px;
	}
	
	.sub-header {
  		padding-bottom: 10px;
  		border-bottom: 2px solid #eee;
	}
	
	table.table {
		font-size: initial !important;
	}
</style>

<script type="text/javascript">
	$(function(){
		
	});
</script>
<div class="contenedor col-sm-12">
	<div class="contenido row">
		<div class="col-sm-12">
             
             <div class="titulo">
				<span>Impresi&oacute;n de Documentos</span>
				<hr class="red m-b-md">
			</div>
			<br>
			<div class="col-sm-12 alert alert-info">
				<p style="text-align: justify;">
				Imprime el comprobante del tr&aacute;mite y las l&iacute;neas de captura para realizar los 
				pagos correspondientes en cualquiera se los siguientes bancos.
				</p>
				<br>
				<p style="text-align: left;">
				<b>BANCOMER, BANAMEX, BANORTE, HSBC, SANTANDER, INVERLAT, BAJÍO, AFIRME, INBURSA, BANSI.</b>
				</p>
				<br>
				<p style="text-align: justify;">
					Una vez realizado el pago antes de la fecha l&iacute;mite, habr&aacute;s concluido el tr&aacute;mite de Continuaci&oacute;n
					Voluntaria en el R&eacute;gimen Obligatorio del IMSS. Asegurate de realizar los pagos siguientes antes
					de su fecha l&iacute;mite de vencimiento.
				</p>
				<br>
			</div>
			
			<br><br>
			<div class="titulo">
				<span>Pagos</span>
				<hr class="red m-b-md">
			</div>
			<br>
			
			<div>
                  <div>
                       <div class="table-responsive">
                         <table class="table table-striped table-bordered">
                           <thead>
                            <tr>
                              <th>Inicio del periodo</th>
                              <th>T&eacute;rmino del periodo</th>
                              <th>Fecha l&iacute;mite de pago</th>
							  <th>Costo</th>
							  <th>Imprimir</th>
							  <th>Estado</th>
                            </tr>
                          </thead>
                           <tbody>
                                <tr>
                                    <td>08/09/2015</td>
                                    <td>30/09/2015</td>
                                    <td>30/10/2015</td>
                                    <td>$ 5,296.93</td>
                                    <td style="text-align: center;"><a style="color:#000000" href="https://github.com/byosonet/god/blob/master/god-doc/Q261ALY34BHC242HO232400086UZ0007PF300000000000000344J.pdf?raw=true" target="_parent"><span class="glyphicon glyphicon-print"></span></a></td>
                                    <td><span class="label label-warning">PENDIENTE</span></td>
                                 </tr>
                                 
                                  <tr>
                                    <td>01/10/2015</td>
                                    <td>30/11/2015</td>
                                    <td>15/11/2015</td>
                                    <td>$ 5,473.50</td>
                                    <td style="text-align: center;"><a style="color:#000000" href="https://github.com/byosonet/god/blob/master/god-doc/Q261ALY34BHC242HO232400086UZ0007PF300000000000000344J.pdf?raw=true" target="_parent"><span class="glyphicon glyphicon-print"></span></a></td>
                                    <td><span class="label label-warning">PENDIENTE</span></td>
                                 </tr>
                                 
                                 <tr>
                                    <td>01/12/2015</td>
                                    <td>31/01/2016</td>
                                    <td>15/02/2016</td>
                                    <td>$ 5,473.50</td>
                                    <td style="text-align: center;"><a style="color:#000000" href="https://github.com/byosonet/god/blob/master/god-doc/Q261ALY34BHC242HO232400086UZ0007PF300000000000000344J.pdf?raw=true" target="_parent"><span class="glyphicon glyphicon-print"></span></a></td>
                                    <td><span class="label label-warning">PENDIENTE</span></td>
                                 </tr>
                                 
                                 <tr>
                                    <td>01/03/2016</td>
                                    <td>30/04/2016</td>
                                    <td>15/05/2016</td>
                                    <td>$ 5,473.50</td>
                                    <td style="text-align: center;"><a style="color:#000000" href="https://github.com/byosonet/god/blob/master/god-doc/Q261ALY34BHC242HO232400086UZ0007PF300000000000000344J.pdf?raw=true" target="_parent"><span class="glyphicon glyphicon-print"></span></a></td>
                                    <td><span class="label label-warning">PENDIENTE</span></td>
                                 </tr>
                                 
                                 <tr>
                                    <td>01/05/2016</td>
                                    <td>31/07/2016</td>
                                    <td>15/08/2016</td>
                                    <td>$ 5,473.50</td>
                                    <td style="text-align: center;"><a style="color:#000000" href="https://github.com/byosonet/god/blob/master/god-doc/Q261ALY34BHC242HO232400086UZ0007PF300000000000000344J.pdf?raw=true" target="_parent"><span class="glyphicon glyphicon-print"></span></a></td>
                                    <td><span class="label label-warning">PENDIENTE</span></td>
                                 </tr>
                                 
                                 <tr>
                                    <td>01/08/2016</td>
                                    <td>30/09/2016</td>
                                    <td>15/09/2016</td>
                                    <td>$ 5,473.50</td>
                                    <td style="text-align: center;"><a style="color:#000000" href="https://github.com/byosonet/god/blob/master/god-doc/Q261ALY34BHC242HO232400086UZ0007PF300000000000000344J.pdf?raw=true" target="_parent"><span class="glyphicon glyphicon-print"></span></a></td>
                                    <td><span class="label label-warning">PENDIENTE</span></td>
                                 </tr>
                           </tbody>
                         </table>
                       </div>
                </div>
           </div>
			
			
		</div>
	</div>
	<br>

	<div class="pie row">
		<div class="opciones col-sm-6">
			<div class="btn-group dropup">
				<button type="button" class="btn btn-primary">Acciones</button>
				<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
					<span class="caret"></span>
				</button>
				<ul class="dropdown-menu">
					<li><a id="imprimir" href="https://github.com/byosonet/god/blob/master/god-doc/comprobante.pdf?raw=true" target="_parent"><i class="glyphicon glyphicon-save"></i> Imprimir Comprobante</a></li>
				</ul>
			</div>
		</div>
		<div class="controles col-sm-6">
			<div class="pull-right">
				<a id="cancelarTramite" class="btn btn-default"> Cerrar</a>
			</div>
		</div>
	</div>
</div>
