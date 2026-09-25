<%@ taglib prefix="combo" uri="http://www.serviciosdigitales.imss.gob.mx/tags/comboSelect"%>
<%@ include file="../general/taglibs.jsp"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!--Empieza contenido-->

<%@page import="mx.gob.imss.ctirss.delta.model.clasificacion.EstatusAnalisisEnum"%>
<%@page import="mx.gob.imss.ctirss.delta.model.clasificacion.CodigoRolClasificacion"%>
<%@page import="mx.gob.imss.ctirss.delta.gestion.clasificacion.web.utils.Constantes"%>
<%@page import="mx.gob.imss.ctirss.delta.model.gestion.patronal.TipoPersonaFiscal"%>

<%-- 
<link type="image/x-icon" href="http://firmadigitalqa.imss.gob.mx/firmaElectronicaWeb/TestFirmaDigital/images/favicon.ico" rel="icon" />
<link type="text/css" href="http://firmadigitalqa.imss.gob.mx/firmaElectronicaWeb/TestFirmaDigital/css/main.css" rel="stylesheet" />
 --%>
<link type="image/x-icon" href="${mvn.url.firmadigital}/firmaElectronicaWeb/TestFirmaDigital/images/favicon.ico" rel="icon" />
<link type="text/css" href="${mvn.url.firmadigital}/firmaElectronicaWeb/TestFirmaDigital/css/main.css" rel="stylesheet" />
 
<link type="text/css" rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/2.2.2/css/bootstrap.min.css">

<link href="/favicon.ico" rel="shortcut icon">
 

<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/consulta/consultaFirmaClem.js" htmlEscape="true" />"></script>


<c:set var="contextpath" value="<%=request.getContextPath()%>" />

<!-- Obtenemos el rol del usuario firmado -->
<c:set var="perfil" value="${usuario.perfilUsuario}"/>
<c:set var="rolU" value="${perfil.idPerfilUsuario}"/>
<c:set var="mensaje" value="${mensaje}"/>
<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<input type="hidden" id="rolU" name="rolU" value="${rolU}"/>
<input type="hidden" id="subdelegacionUser" name="subdelegacionUser" value="${usuario.usuarioFuncionario.subdelegacion.id}"/>
<input type="hidden" id="delegacionUser" name="delegacionUser" value="${usuario.usuarioFuncionario.delegacion.id}"/>


<div class="site_position_center">
    <div class="page_holder_no_height">
		<div id="mensaje">${mensaje}</div>
	</div>
</div>
	
<div class="page_holder_no_height">
	<div class="post_entry_wide no-border">

		<div class="form-comment">

<!-- 			<form id="formWidget" action="http://firmadigitalqa.imss.gob.mx/firmaElectronicaWeb/widget/chfecyn"
					method="post" target="formFirmaDigital" accept-charset="ISO-8859-1">
 -->
 			<form id="formWidget" action="${mvn.url.firmadigital}/firmaElectronicaWeb/widget/chfecyn"
					method="POST" target="formFirmaDigital" accept-charset="ISO-8859-1">
					<input id="idrfc" name="idrfc" value='${paramRFC}' type="hidden"> 
					<input id="listCO" name="listCO" value='' type="hidden"> 
					<input id="params" name="params" value='' type="hidden"> 
					<input type="submit" style="display: none" value="Firmar varias 'cadenas originales'">
				</form>
			<iframe class="custom-frame" #iFrameFirma id="formFirmaDigital" name="formFirmaDigital" width="850" height="400" frameborder="0"> </iframe>
		<br>
		<br>
		
			
		</div>
		
		
	</div>
</div>
			
			
			<div class="container top-buffer">
	
			
		<div class="modal fade" id="msgModalFirma" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalCenterTitle">Mensaje</h5>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="col-md-12">
								<center><p>Se realiz&oacute; correctamente el firmado de los documentos.</p></center>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="closeRef();">Aceptar</button>
					</div>
				</div>
			</div>
		</div>

		<div class="modal fade" id="msgModalFirmaError" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalCenterTitle">Mensaje</h5>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="col-md-12">
								<center><p>Problema con el firmado de los documentos.</p></center>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-primary" data-dismiss="modal"
							onclick="closeRef();">Aceptar</button>
					</div>
				</div>
			</div>
		</div>

		<div class="modal fade" id="modalBarra" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalCenterTitle">Firma de documentos</h5>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="col-md-12">
								<center><label id="firmando">Firmando documentos:</label></center> <br>
								<label id="docsFirmados"></label>
							</div>
							<div class="col-md-12">
								<div class="progress" style="height: 20px;">
									<div id="bar" class="progress-bar" role="progressbar"
										style="width: 0%;" aria-valuenow="25" aria-valuemin="0"
										aria-valuemax="100"></div>
								</div>
							</div>
							<div id="btnBar" class="modal-footer" style="display: none;">
								<button type="button" class="btn btn-primary"
									data-dismiss="modal" id="btnBar2" onclick="closeRef();">Aceptar</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

	
		</div>

    	<form id="formRegresar">
    	</form>

<script type="text/javascript">
		
console.log("Ejecuto codigo A");

var cadOri1 ='';

<c:forEach var="firmas" items="${parametrosFirma}">

cadOri1 += convertirHTMLaString('<c:out value="${firmas.cadOriginal}"/>' + "$");

</c:forEach>

//var cadOri1 = "|Identificador_1234|NoFolioCE-17-27-17/02/2020/1111-S|Patron_MARTHA LOPEZ PEREZ|Registro patronal_C8726099103|Prima patron_1.13065|Prima Propuesta_ 4.65325|Delegacion_Regional Michoacán|Subdelegacion_ Lázaro Cárdenas|Titular_CARLOS ALBERTO RAMÍREZ RAMÍREZ|";
//var cadOri2 = "|Identificador_5678|NoFolioCE-17-27-17/02/2020/2222-S|Patron_ARMANDO HAMED|Registro patronal_C8726099103|Prima patron_1.13065|Prima Propuesta_ 4.65325|Delegacion_Regional Michoacán|Subdelegacion_ Lázaro Cárdenas|Titular_JONATHAN SANCHES MONTIEL|";
//var cadOri3 = "|Identificador_9012|NoFolioCE-17-27-17/02/2020/2222-S|Patron_ALEJANDRO CAMPOS RAMIREZ|Registro patronal_C8726099103|Prima patron_1.13065|Prima Propuesta_ 4.65325|Delegacion_Regional Michoacán|Subdelegacion_ Lázaro Cárdenas|Titular_ANTONIO CARLOS SANTOS|";

tempListCadena = [];
splittedListCadena = [];
var listaCadenas = [];
console.log("Termino codigo A");

console.log("Ejecuto codigo B");

console.log("armando cadena original: " + cadOri1.substr(0,cadOri1.length - 1));
//se recuperan las cadenas originales recibidas para colocar como lo espera la cadena de operación
document.getElementById("listCO").value = cadOri1.substr(0,cadOri1.length - 1); //+ "$" + cadOri2 + "$" + cadOri3;
var listaCadenas = document.getElementById("listCO").value.split('$');
var numCadenas = listaCadenas.length;
console.log("::: Numero de cadenas a firmar: " + numCadenas);

for(var j = 0; j < listaCadenas.length; j++){

	<c:forEach var="firmas" items="${parametrosFirma}">
		
		var regPatron = '<c:out value="${firmas.clemVO.regPatronal}"/>' + "";
		var cadOr = listaCadenas[j];
		
		//busca saltos de linea para que se impriman de manera correcta al firma documento
		var mot = '<c:out value="${firmas.clemVO.motivos}"/>' + "";
		mot = mot.replace(/&amp;#13;/gi,'&#13;');
		var razonSocial = convertirHTMLaString('<c:out value="${firmas.clemVO.razonSocial}" default="null"/>');
		var sup = '<c:out value="${firmas.clemVO.suplente}" default=""/>'+""; 
	
		console.log('Cadena Original actual: ' + cadOr);
		console.log('Motivos: ' + mot);
		console.log("Buscando patron: " + regPatron);
		
		if(cadOr.indexOf(regPatron) != -1){
	    	console.log('Agregando datos del patron: ' + '<c:out value="${firmas.clemVO.regPatronal}"/>' + ', al request de firma');
	
			tempListCadena.push(
					'{"cad_original": "' + cadOr + '" ,' + 
					'"clemMac":' + 
						'[{' +
						  '"folioClem":'+'"<c:out value="${firmas.clemVO.folioClem}" default="null"/>",'+
						  '"delegacion":'+'"<c:out value="${firmas.clemVO.delegacion}" default="null"/>",'+
						  '"subdelegacion":'+'"<c:out value="${firmas.clemVO.subdelegacion}" default="null"/>",'+
						  '"razonSocial":"'+razonSocial+'" ,'+
						  '"domicilio":'+'"<c:out value="${firmas.clemVO.domicilio}" default="null"/>",'+
						  '"municipioDelegacion":'+'"<c:out value="${firmas.clemVO.municipioDelegacion}" default="null"/>",'+
						  '"regPatronal":'+'"<c:out value="${firmas.clemVO.regPatronal}" default="null"/>",'+
						  '"fechaAviso":'+'"null",'+
						  '"idDivisionPatron":'+'"<c:out value="${firmas.clemVO.idDivisionPatron}" default="null"/>",'+
						  '"idGrupoPatron":'+'"<c:out value="${firmas.clemVO.idGrupoPatron}" default="null"/>",'+
						  '"idFraccionPatron":'+'"<c:out value="${firmas.clemVO.idFraccionPatron}" default="null"/>",'+
						  '"denominacionFraccion":'+'"<c:out value="${firmas.clemVO.denominacionFraccion}" default="null"/>",'+
						  '"clase":'+'"<c:out value="${firmas.clemVO.clase}" default="null"/>",'+
						  '"prima":'+'"<c:out value="${firmas.clemVO.prima}" default="null"/>",'+
						  '"fechaTramite":'+'"<c:out value="${firmas.clemVO.fechaTramite}" default="null"/>",'+
						  '"motivos": "' + mot + '" ,' +						  
						  '"idDivisionPropuesta":'+'"<c:out value="${firmas.clemVO.idDivisionPropuesta}" default="null"/>",'+
						  '"idFraccionPropuesta":'+'"<c:out value="${firmas.clemVO.idFraccionPropuesta}" default="null"/>",'+
						  '"idGrupoPropuesta":'+'"<c:out value="${firmas.clemVO.idGrupoPropuesta}" default="null"/>",'+
						  '"divisionPropuesta":'+'"<c:out value="${firmas.clemVO.divisionPropuesta}" default="null"/>",'+
						  '"grupoPropuesta":'+'"<c:out value="${firmas.clemVO.grupoPropuesta}" default="null"/>",' +
						  '"clasePropuesta":'+'"<c:out value="${firmas.clemVO.clasePropuesta}" default="null"/>",'+
						  '"primaPropuesta":'+'"<c:out value="${firmas.clemVO.primaPropuesta}" default="null"/>",'+
						  '"fraccionPropuesta":'+'"<c:out value="${firmas.clemVO.fraccionPropuesta}" default="null"/>",'+
						  '"fraccion115":'+'"<c:out value="${firmas.clemVO.fraccion115}" default="null"/>",'+
						  '"incisio115":'+'"<c:out value="${firmas.clemVO.incisio115}" default="null"/>",'+
						  '"titular":'+'"<c:out value="${firmas.clemVO.titular}" default="null"/>",'+
						  '"suplente":' + (sup == ""?  'null,' :'"' + sup + '",') +
						  '"puesto":'+'"<c:out value="${firmas.clemVO.puesto}" default="null"/>",'+
						  '"lugarFechaExpedicion":'+'"<c:out value="${firmas.clemVO.lugarFechaExpedicion}" default="null"/>",'+
						  '"pspArt15A":'+'"<c:out value="${firmas.clemVO.pspArt15A}" default="null"/>",'+
						  '"pspArt19":'+'"<c:out value="${firmas.clemVO.pspArt19}" default="null"/>",'+
						  '"fraccionArticulo20":'+'"<c:out value="${firmas.clemVO.fraccionArticulo20}" default="null"/>",'+
						  '"fraccionArticulo26":'+'"<c:out value="${firmas.clemVO.fraccionArticulo26}" default="null"/>",'+
						  '"fraccionArticulo28":'+'"<c:out value="${firmas.clemVO.fraccionArticulo28}" default="null"/>",'+
						  '"fechaSurteEfecto":'+'"<c:out value="${firmas.clemVO.fechaSurteEfecto}" default="null"/>",'+
						  '"tipoPersona":'+'"<c:out value="${firmas.clemVO.tipoPersona}" default="null"/>"'+
							'}]' +
							'}'	
			);
		}
	 
	</c:forEach>

  	if (j <  numCadenas) {
  		console.log("----Agregando cadena para clem");
      	splittedListCadena.push(tempListCadena);
      	tempListCadena = [];
    }

}

/* console.log("tempListCadena.length: " + tempListCadena.length);
console.log("tempListCadena: " + tempListCadena); 
 */
console.log("splittedListCadena.length: " + splittedListCadena.length);
console.log("splittedListCadena: " + splittedListCadena); 
console.log("Termino codigo B");
	
console.log("Ejecuto codigo C");
////////Firmado
var rfc = document.getElementById("idrfc").value;

var cadenaOperacion = '{"operacion": "firmaMasivaCMS",' +
					  ' "aplicacion": "MAC_IMSS",' +
					  ' "rfc": "' + cambiarUni(rfc) + '",' +
					  ' "acuse": ' + '"<c:out value="${tipoClemFirma}"/>",' +
					  ' "firmas": [' + splittedListCadena + '],' + 
					  ' "salida": "cadori, acuse, firma, folio"' +
					  '}';
					  
document.getElementById("params").value = cadenaOperacion;

console.log("Termine de ejecutar codigo C");

console.log("CADENA: " + document.getElementById("params").value);
	
function cambiarUni(cadena) {
	var result = "";
	for ( var i = 0; i < cadena.length; i++) {
		// Assumption: all characters are < 0xffff
		if (cadena[i] == "á" || cadena[i] == "é" || cadena[i] == "í"
				|| cadena[i] == "ó" || cadena[i] == "ú"
				|| cadena[i] == "Á" || cadena[i] == "É"
				|| cadena[i] == "Í" || cadena[i] == "Ó"
				|| cadena[i] == "Ú" || cadena[i] == "ñ"
				|| cadena[i] == "Ñ" || cadena[i] == "&" 
				|| cadena[i] == "'" || cadena[i] == "\"") {
			result += "\\u"
					+ ("000" + cadena[i].charCodeAt(0).toString(16))
							.substr(-4);
		} else {
			result += cadena[i];
		}
	}
	return result;
};	

function convertirHTMLaString (texto) {
	
	var dicccionarioHTML = [{html:/&Ntilde;/gi ,valor:"Ñ"}, 
	                        {html:/&ntilde;/gi,valor:"ñ"}, 
	                        {html:/&aacute;/gi,valor:"á"}, 
	                        {html:/&Aacute;/gi,valor:"Á"}, 
	                        {html:/&eacute;/gi,valor:"é"}, {html:/&Eacute;/gi,valor:"É"},
	        				{html:/&amp;/gi,valor:"&"},{html:/&iacute;/gi,valor:"í"}, 
	        				{html:/&Iacute;/gi,valor:"Í"}, {html:/&Oacute;/gi,valor:"Ó"}, 
	        				{html:/&oacute;/gi,valor:"ó"}, {html:/&Uacute;/gi,valor:"Ú"}, 
	        				{html:/&uacute;/gi,valor:"ú"}, {html:/&quot;/gi,valor:"\""},
	        				{html:/&#034;/gi,valor:"\\\""}, {html:/&#039;/gi,valor:"'"}	        				
	        				];
	
	for(var d = 0; d<dicccionarioHTML.length; d++) {
		texto = texto.replace(dicccionarioHTML[d].html, dicccionarioHTML[d].valor);
		
	}
	
	return texto;
}
		
function closeRef() {
	//location.href = "./firma.jsp";
	//location.href = "#";
	//alert('Me voy de regreso al inicio');
	
	var idForm = "#formRegresar";
	$(idForm).attr('action',
			context_path + '/modulo/firma/ver/firmadas');
	$(idForm).submit();	
}
</script>

<!-- 
<script src="https://framework-gb.cdn.gob.mx/gobmx.js"></script>

 -->

	<script type="text/javascript" src="<spring:url value="/static/resources/js/delta/consulta/mainMasiva.js" htmlEscape="true" />"></script>

<!--	


	<script type="text/javascript" src="resources/datatables.js"></script>
	-->
	
	<script>
		document.getElementById("formWidget").submit();
	</script>