<%@ include file="/WEB-INF/views/general/taglibs.jsp" %>
<%@ include file="/WEB-INF/views/general/impresionDocumentosImport.jsp" %>
<%@ include file="/WEB-INF/views/general/fileUpload/FileUploadImport.jsp" %>

<head>
</head>
<div class="form-comment">
<c:set var="contextpath" value="<%=request.getContextPath()%>" />
<script><!--
$(document).ready(function() {
	var tipo = $('#tipo').val();
	var tipoTramite = $('#tipoTramite').val();
	var idTramite = $('#tramite').val();	
	var patron = $('#patronImss').val();	
	var razon = $('#razonRegistro').val();
	
	if(tipo == "1"){		
		showCompRechazoSol('RECHAZO DE REGISTRO',tipoTramite);			          
		 $("#frmComprobantes").submit();	
	}else{	
		if(patron == "1"){
			if(tipo == "44" || tipo == "45" || tipo == "47"){
				showCompValRegistro(${idTramite},'REGISTRO DERECHOHABIENTE');	
			}else{
				if(tipo == "48"){
//					if(razon == "6"){
//						$("#msgMayor25").dialog({			 
//						      buttons : {
//						        "Aceptar" : function() {
//						        	$(this).dialog("close");
//						        }
//						      }
//						  }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();	
//					}else{
						showCompValRegistro(${idTramite},'REGISTRO DERECHOHABIENTE');
//					}					
				}else{
					$("#msgValidado").dialog({			 
					      buttons : {
					        "Aceptar" : function() {
					        	$(this).dialog("close");
					        }
					      }
					  }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
				}	
			}
		}else{ 
			if(tipo == "44" || tipo == "45" || tipo == "47" || tipo == "49"){			
				showCompValRegistro(${idTramite},'REGISTRO DERECHOHABIENTE');
			}else{
				if(tipo == "48"){
//					if(razon == "6"){
//						$("#msgMayor25").dialog({			 
//						      buttons : {
//						        "Aceptar" : function() {
//						        	$(this).dialog("close");
//						        }
//						      }
//						  }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();	
//					}else{
						showCompValRegistro(${idTramite},'REGISTRO DERECHOHABIENTE');
//					}
					
				}else{
					$("#msgValidado").dialog({			 
					      buttons : {
					        "Aceptar" : function() {
					        	$(this).dialog("close");
					        }
					      }
					  }).parent('.ui-dialog').find('.ui-dialog-titlebar-close').hide();
				}														
			}
		}
		$("#frmComprobantes").submit();
	}		
});
history.go(1);

--></script>

<form:form commandName="comprobantes" id="frmComprobantes" name="frmComprobantes" action="${contextpath}/inicio/grupoFamiliar" method="post">	
	<input type="hidden" id="tipo" name="tipo" value="<c:out value="${tipo}"/>"></input>
	<input type="hidden" id="tipoTramite" name="tipoTramite" value="<c:out value="${tipoTramite}"/>"></input>
	<input type="hidden" id="tramite" name="tramite" value="<c:out value="${idTramite}"/>"></input>
	<input type="hidden" id="patronImss" name="patronImss" value="<c:out value="${patronImss}"/>"></input>
	<input type="hidden" id="razonRegistro" name="razonRegistro" value="<c:out value="${idRazonRegistro}"/>"></input>
	
	<div id="msgValidado" title ="<spring:message code="titulo.mensajeAviso"/>" style="display:none"> 
		<spring:message code="msgValidado"/>		
	</div>	
	<div id="msgMayor25" title ="<spring:message code="titulo.mensajeAviso"/>" style="display:none"> 
		<spring:message code="msgMayor25"/>		
	</div>	
</form:form>
</div>