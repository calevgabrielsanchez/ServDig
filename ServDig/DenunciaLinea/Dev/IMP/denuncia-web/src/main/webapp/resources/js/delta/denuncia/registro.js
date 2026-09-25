var dtDenuncias;
var claveFolioDenuncia=null;
var listadoDenuncias=null;
$(document).ready(function() {

	$('#lblj_captcha_response').html('');
	$('#lblDesEmail').html('');
	$('#lblDesPassword').html('');
	
	$('input#log_j_captcha_response').val('');
	$('input#desEmail').val('');
	$('input#desPassword').val('');
	llenaComboPreguntas();
	
	
	



	

	
	  
 	

	 
//   dtDenuncias = $("#dtDenuncias").dataTable({
//	"bJQueryUI" : true,
//	"bPaginate": true,
//	"bAutoWidth" : true,
//	"bServerSide" :	true,
//	"bProcessing": true,
//    "iDeferLoading": 0,	
//	"bFilter": false,
//	"bInfo": true,
//	"aoColumnDefs": [
//	                 {  
//	                	fnRender :function(oObj){
//							var retVal = '<input type="radio" value="' +
//							oObj.aData['cveFoliodenuncia'] +'" id="radioDenuncias" class="radioClase" name="radioDenuncias" /> ';
//							return retVal;				
//	                	},"aTargets": [0], "mDataProp" :"cveFoliodenuncia" },
//	              	{"aTargets": [1], "mDataProp" :"numFoliodenuncia" },
//	              	{"aTargets": [2], "mDataProp" :"fecRegistro" },
//	              	{"aTargets": [3],  "mDataProp" :"persona.nombreCompleto"},
//	              	{"aTargets": [4], "mDataProp" :"patron.desNomrazonsocial" },
//	              	{"aTargets": [5], "mDataProp" :"dlcStatus.desStatus" }
//	              	
//	             
//	          ],
//	 "sAjaxSource" : 'consultarDenuncias.do',
//	 "fnServerData" : function(sSource, aoData, fnCallback) {
//		 aoData.push({
//			
//				"name" : "sSearch",
//				"value" : "hola"
//			});
//			var wrapper = new Object();
//			wrapper.aoData = aoData;
//			$.postJSON(sSource, wrapper, function(data) {
//				fnCallback(data);
//			});
//		}
//	});
//	
//	 var oTable = $(dtDenuncias).dataTable();
//     oTable.fnDraw();	
    
    
//    //ENcabezados
//    <!--   <th colspan="1" rowspan="1" width="2%" align="center"></th>
//    <th style="text-align: center;" colspan="1" rowspan="1" width="10%" align="center">Folio</th>
//    <th style="text-align: center;" colspan="1" rowspan="1" width="10%" align="center">Fecha de Registro</th>
//    <th style="text-align: center;" colspan="1" rowspan="1" width="10%" align="center">Nombre del Denunciante</th>
//    <th style="text-align: center;" colspan="1" rowspan="1" width="10%" align="center">Nombre del Patr&oacute;n Denunciado</th>
//    <th style="text-align: center;" colspan="1" rowspan="1" width="10%" align="center">Estatus</th> -->
     
 	
	
     
	
}); 


function limpiaLblCaptcha(){
	$('#lblj_captcha_response').html('');
}

function limpiaDesMail(){
	$('#lblDesEmail').html('');
}

function limpiaDesPass(){
	$('#lblDesPassword').html('');
}

function logueo(){
	$('#lblj_captcha_response').html('');
	$('#lblDesEmail').html('');
	$('#lblDesPassword').html('');
	var captcha = $('input#log_j_captcha_response').val();
	var desMail = $('input#desEmail').val();
	var desPass = $('input#desPassword').val();
	var prefijoServer="registro/";
	
	if(document.URL.indexOf("registro") !== -1){
		prefijoServer="";
	}
	
		if(captcha == ''){
			$('#lblj_captcha_response').html('<span class="required">Los caracteres de la imagen no coinciden con los capturados</span>');
			return false;
		}
		if(desMail == ''){
			$('#lblDesEmail').html('<span class="required">El campo es requerido</span>');
			return false;
		}
		if(desPass == ''){
			
			$('#lblDesPassword').html('<span class="required">El campo es requerido</span>');
			return false;
		}
		
		var sUsuario = '{' +
		'"desEmail": "'+desMail+ '",' +
		'"desPassword": "'+desPass+ '",' +
		'"captcha": "'+captcha+'"}';
		
		var usuario = jQuery.parseJSON(sUsuario);
				
		$.postJSON(prefijoServer+"validaCaptcha.do", usuario, function(data) {				
			if(data==2){
				$.postJSON(prefijoServer+"verificaCorreo.do", usuario, function(ver) {
				if(ver==1){
					$("#LoginForm").submit(); 
				}else if(ver == 2){
					nuevaImagenLog();
					alert('La contrase\u00f1a capturada no coincide con la registrada');
				}else{
					alert('El correo electr\u00f3nico capturado no se encuentra registrado');
				}
				
				}).error(function(data){ 
					
				}).complete(function(){
					// Instrucciones para el 'complete'
				}); 	
			
				
				

			}else{
				nuevaImagenLog();
				alert('Los caracteres de la imagen no coinciden con los capturados');
			}	
		}).error(function(data){ 
			
		}).complete(function(){
			// Instrucciones para el 'complete'
		}); 	
	
		
		
		
		
}




function siguiente(){
	var actionO = $("#frmMain").attr("action");	
	$("#frmMain").attr("action",getAppContextParaJS() + actionO );				
	$("#frmMain").submit(); 	 	 
}
function actucap(){
	var obj=document.getElementById("imgCaptchaRec");
    if (!obj) obj=window.document.all.cap;
    if (obj){
    //	obj.src='http://localhost:7001/denuncia-web/captchaController/captcha.htm?'+Math.random();
   //   obj.src='http://172.24.116.52:11000/denuncia-web/captchaController/captcha.htm?'+Math.random();
      obj.src='/denunciaenlinea/captchaController/captcha.htm?'+Math.random();
    }
  }
function nuevaImagen(){
	
	var obj=document.getElementById("imgCaptcha");
    if (!obj) obj=window.document.all.cap;
    if (obj){
    	//obj.src='http://localhost:7001/denuncia-web/captchaController/captcha.htm?'+Math.random();
      //obj.src='http://172.24.116.52:11000/denuncia-web/captchaController/captcha.htm?'+Math.random();
      obj.src='/denunciaenlinea/captchaController/captcha.htm?'+Math.random();
    }
	
	
}
function nuevaImagenLog(){
	
	var obj=document.getElementById("imgCaptchaLog");
    if (!obj) obj=window.document.all.cap;
    if (obj){
    	//obj.src='http://localhost:7001/denuncia-web/captchaController/captcha.htm?'+Math.random();
     // obj.src='http://172.24.116.52:11000/denuncia-web/captchaController/captcha.htm?'+Math.random();
      obj.src='/denunciaenlinea/captchaController/captcha.htm?'+Math.random();
    }
	
	
}

function reloads(){
	alert('reload');
	location.reload();
}

function irInicio(){
	$("#wlForm").submit(); 
}
function irRecuperarConf(){
	$("#irRecuperaConfForm").submit(); 
}
function irLogin(){
	$("#irLoginForm").submit(); 
}
function irRecuperar(){
	$("#irRecuperarForm").submit(); 
}
function irRegistro(){
	$("#registroForm").submit(); 
}

function loguear(){
	$("#registroForm").submit(); 
}



function llenaComboPreguntas(){
	$.postJSON("obtenerPreguntas.do", '', function(dataC) {
		 var options = "<option value='' >--Por favor seleccione--</option>";
		 if(dataC!=null)
		   for (var i = 0; i < dataC.length; i++) {
	         options += "<option value='"+ dataC[i].cvePregunta +"'>"+ dataC[i].desPregunta +"</option>";		     
	       }
		 $('select#pregunta').html(options);
		 
	  }).error(function(dataC){ 
		  (dataC);
	  }).complete(function(){
		//Instrucciones para el 'complete'
	  });
}