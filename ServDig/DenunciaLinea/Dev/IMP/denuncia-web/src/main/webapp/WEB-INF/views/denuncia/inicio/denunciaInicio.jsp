<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
	"http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style>
    label.error { float: none; font-size:smaller; color: red; padding-left: .5em; vertical-align: top; }
    label  {  float: none; }
</style>
<link rel="stylesheet" type="text/css"  href="<%=request.getContextPath()%>/resources/estilos/styleTabs.css">
<link rel="icon" href="<%=request.getContextPath()%>/resources/images/favicon.ico">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/estilos/estilo.css">
<link rel="stylesheet" type="text/css" 	href="<%=request.getContextPath()%>/resources/estilos/imss/style.css"  />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/correcion.css">
<link type="text/css"    href="<%=request.getContextPath()%>/resources/css/ui-lightness/jquery-ui-1.8.14.custom.css" rel="stylesheet" />
<link type="text/css"   href="<%=request.getContextPath()%>/resources/estilos/jquery/ui-lightness/jquery-ui.css" rel="stylesheet" />
<link rel="stylesheet" type="text/css"  href="<%=request.getContextPath()%>/resources/css/ui-lightness/jquery-ui-1.8.14.custom.css" >

<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/delta/denuncia/reglasValidacionDenuncia.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/delta/denuncia/navegadorUtils.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/delta/denuncia/denunciaLinea.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/delta/denuncia/funcionesDenunciaLinea.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/resources/js/delta/denuncia/datosTrabajador.js"></script>


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

<c:set var="slctDenuncia" value="<%=session.getAttribute(\"idDenuncia\")%>" />
<c:set var="datosDenuncia" value="<%=session.getAttribute(\"datosDenuncia\")%>" />
<script type="text/javascript">
var txtDenuncia='${datosDenuncia}';
var jsConsultaDenuncia=jQuery.parseJSON(txtDenuncia);
</script>    

<form action="" method="post" id="denunciaForm" name="denunciaForm">
<table>
<tr>
<div style="width: auto; overflow: auto; ">
   <input id="hdIdDenuncia" name="hdIdDenuncia" type="hidden" /> 
   <input id="hdIdDenunciaCarga" name="hdIdDenunciaCarga" type="hidden"  value="${slctDenuncia}"/>
      <input id="hdFechaServidor" name="hdFechaServidor" type="hidden" /> 
        <table  style="width: 900px; overflow: auto;" align="left" >
             <tr> <td> <br/></td> </tr>
            <tr >
                <td colspan="3" align="center">         
                    <ul class="tabs">
                        <li class="etiqueta2" id="trabajadorTAB_LI">          <a href="#trabajadorHash" id="trabajadorTABLink"> Datos del Trabajador</a></li>
                        <li class="etiqueta2" id="patronTAB_LI">        <a href="#patronHash" id="patronTABLink">   Datos del Patr&oacute;n</a></li>
                        <li class="etiqueta2" id="trabajoTAB_LI">        <a href="#trabajoHash" id="trabajoTABLink"> Datos Generales del Trabajo</a></li>
                       </ul>       
                </td> 
            </tr>
             <tr> <td> <br/></td> </tr>
            <tr>
                <td >
                    <div id="trabajadorHash"           class="tab_content">  <jsp:include page="datosTrabajadorMain.jsp"></jsp:include> </div>
                    <div id="patronHash"         class="tab_content"><jsp:include page="datosPatronMain.jsp"></jsp:include></div>
                    <div id="trabajoHash"         class="tab_content">   <jsp:include page="datosTrabajoMain.jsp"></jsp:include></div>
               		<br></br>
               		<br></br>
               		
               		<div id="divAclaraciones"><label  class="etiqueta2">Aclaraciones</label><br>
               		 <textarea class="cajita" name="txtAclara" id="txtAclara" rows="5" cols="90" style="height: 30; width: 90" onkeyup="conMayusculas(this);"></textarea>
               		 </div>
               
              		
                </td>
            </tr>
        </table>         
    </div>
</tr>

</table>

<table style="width: 900px; overflow: auto;" align="center">
    <tr> <td> <br/></td> </tr>
    <tr> <td> <br/></td> </tr>
    <tr>
        <td ><button type="button"  value="Guardar" id="btnGuardar" class="mboton" > Guardar </button></td>
        <td ><button type="button"  value="Enviar" id="btnEnviar" class="mboton" onclick="enviarDenuncia();"> Enviar Denuncia </button></td>
        <td ><button type="button"  value="Inicio" id="btnCancelar" class="mboton" > Cancelar </button></td>
    </tr>
</table>
</form>

<form id="frmEnviar" name="frmEnviar" ></form>

  <form:form modelAttribute="dlcUsuarioFuncionario"
    action="../login/seleccionarPeril.do" method="post" id="pagConsultaForma"></form:form>