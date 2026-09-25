<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<style>
    label.error { float: none; font-size:smaller; color: red; padding-left: .5em; vertical-align: top; }
    label  {  float: none; }
</style>
<link rel="stylesheet" type="text/css"  href="<%=request.getContextPath()%>/resources/estilos/styleTabs.css">
<link rel="icon" href="<%=request.getContextPath()%>/resources/images/favicon.ico">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/estilos/estilo.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/correcion.css">
<link type="text/css"    href="<%=request.getContextPath()%>/resources/css/ui-lightness/jquery-ui-1.8.14.custom.css"    rel="stylesheet" />
<link type="text/css"   href="<%=request.getContextPath()%>/resources/estilos/jquery/ui-lightness/jquery-ui.css" rel="stylesheet" />
<link rel="icon"                        href="<%=request.getContextPath()%>/resources/images/favicon.ico">
<link rel="stylesheet" type="text/css"  href="<%=request.getContextPath()%>/resources/estilos/estilo.css">
<link rel="stylesheet" type="text/css"  href="<%=request.getContextPath()%>/resources/css/ui-lightness/jquery-ui-1.8.14.custom.css" >

<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/delta/denuncia/denunciaLinea.js"></script>


<script type="text/javascript"
            src="<%=request.getContextPath()%>/resources/js/jquery/jquery-1.6.2.js"></script>
        <script type="text/javascript"
            src="<%=request.getContextPath()%>/resources/js/jquery/jquery-post-json.js"></script>
        <script type="text/javascript"
            src="<%=request.getContextPath()%>/resources/js/jquery/jquery-ui-1.8.14.custom.min.js"></script>
        <script type="text/javascript"
            src="<%=request.getContextPath()%>/resources/js/jquery/dtable/jquery.dataTables.js"></script>
        <script type="text/javascript"
            src="<%=request.getContextPath()%>/resources/js/jquery/dtable/jquery.dataTables.fnDisplayStart.js"></script>
        <script type="text/javascript"
            src="<%=request.getContextPath()%>/resources/js/jquery/json.min.js"></script>
        <script type="text/javascript"
            src="<%=request.getContextPath()%>/resources/js/jquery/form2Object/form2object.js"></script>
        <script type="text/javascript"
            src="<%=request.getContextPath()%>/resources/js/jquery/form2Object/jquery.toObject.js"></script>
        <script type="text/javascript"
            src="<%=request.getContextPath()%>/resources/js/delta/gestionCtrlSelect.js"></script>   
        <script type="text/javascript"
            src="<%=request.getContextPath()%>/resources/js/jquery/jquery.blockUI.1.33.js"></script>
        <script type="text/javascript"
            src="<%=request.getContextPath()%>/resources/js/jquery/validation/validator/jquery.validate.js"></script>
        <script type="text/javascript"
            src="<%=request.getContextPath()%>/resources/js/jquery/validation/validator/jquery.validate.min.js"></script>   
        <script type="text/javascript"
            src="<%=request.getContextPath()%>/resources/js/jquery/validation/validator/additional-methods.js"></script>    
        <script type="text/javascript"
            src="<%=request.getContextPath()%>/resources/js/jquery/validation/validator/messages_es.js"></script>
        <script type="text/javascript"
            src="<%=request.getContextPath()%>/resources/js/jquery/jquery.maskedinput-1.2.1.pack.js"></script>
        <script type="text/javascript"
            src="<%=request.getContextPath()%>/resources/js/jquery/mask/jquery.maskedinput-1.3.js"></script>            
        <script type="text/javascript"
            src="<%=request.getContextPath()%>/resources/js/delta/funcionesComunes.js"></script>
<script type="text/javascript"
    src="<%=request.getContextPath()%>/resources/js/jquery/jquery-post-json.js"></script>
<script type="text/javascript"
    src="<%=request.getContextPath()%>/resources/js/jquery/jquery-ui-1.8.14.custom.min.js"></script>
<script type="text/javascript"
    src="<%=request.getContextPath()%>/resources/js/jquery/dtable/jquery.dataTables.js"></script>
<script type="text/javascript"
    src="<%=request.getContextPath()%>/resources/js/jquery/dtable/jquery.dataTables.fnDisplayStart.js"></script>
<script type="text/javascript"
    src="<%=request.getContextPath()%>/resources/js/jquery/json.min.js"></script>
<script type="text/javascript"
    src="<%=request.getContextPath()%>/resources/js/jquery/form2Object/form2object.js"></script>
<script type="text/javascript"
    src="<%=request.getContextPath()%>/resources/js/jquery/form2Object/jquery.toObject.js"></script>
<script type="text/javascript"
    src="<%=request.getContextPath()%>/resources/js/delta/gestionCtrlSelect.js"></script>
<script type="text/javascript"
    src="<%=request.getContextPath()%>/resources/js/jquery/jquery.blockUI.1.33.js"></script>

<table border="0" cellpadding="0" cellspacing="0" width="100%">
	
	
	<tr>
		<td><form action="/sincronizacion" id="acuseS" method="post" name="acuseS"
				enctype="multipart/form-data">
				<input type="hidden" name="dispatch" /> <input type="hidden"
					name="documento" value="<%=request.getAttribute("documento")%>" />
				<input type="hidden" name="nombreArchivo"
					value="<%=request.getAttribute("nombreArchivo")%>" />
				<table class="tabla" width="100%" border="0" cellpadding="0"
					cellspacing="0" align="center">
					<tbody align="center">
						<tr class="impar">
							<td colspan="4">&nbsp;</td>
						</tr>
						<tr class="impar">
							<td colspan="2"><p style="text-align: justify;">
									<iframe id="izq"
										src="<%=request.getContextPath()%>/reporte/EnviaServletRat.do"
										width="100%" height="600" scrolling="auto" frameborder="0"></iframe>
									
							</p></td>
<!-- 							<td colspan="2"><p style="text-align: justify;"> -->
<!-- 									<iframe id="der" -->
<%-- 										src="<%=request.getContextPath()%>/reporte/EnviaServletDer.do" --%>
<!-- 										width="100%" height="600" scrolling="auto" frameborder="0"></iframe> -->
									
<!-- 							</p></td> -->
						</tr>
						</p>
						</td>
						</tr>

					</tbody>
				</table>
			
			</form>
		</td>
	</tr>
</table>

