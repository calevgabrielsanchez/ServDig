
	
	function generaTabla(data){	
		listadoDenuncias=data;
		 $("#dtDenuncias").dataTable({
				"aaData": listadoDenuncias,
				"bAutoWidth" : true,
				bFilter : false,
				bJQueryUI : true,
				"bDestroy": true,
				bSort: false,
				'iDisplayLength': 10,
				"aoColumns" : [{				
					"sTitle" : "  ",
					"sWidth": "10%",
					"sClass": "dtCenterClassColumn",
					"fnRender": function ( o, val ) {
						var val='<input type="radio" name="rdCveFolioDenuncia" value="'+o.aData['numClaveFolio']+'">';
						return val;
				        }
				},{				
					"sTitle" : "Folio",
					"sWidth": "10%",
					"mDataProp" : "numFolio",
					"sClass": "dtCenterClassColumn"
				},{
						
					"sTitle" : "Fecha de Registro",
					"sWidth": "10%",
					"mDataProp" : "fechaRegistro",
					"sClass": "dtCenterClassColumn"
				},{
						
					"sTitle" : "Nombre del Denunciante",
					"sWidth": "10%",
					"mDataProp" : "nombreDenunciante",
					"sClass": "dtCenterClassColumn"
				},{
						
					"sTitle" : "Nombre del Patr\u00f3n Denunciado",
					"sWidth": "10%",
					"mDataProp" : "nombrePatronDenunciado",
					"sClass": "dtCenterClassColumn"
				},{
						
					"sTitle" : "Estatus",
					"sWidth": "10%",
					"mDataProp" : "estatus",
					
					"sClass": "dtCenterClassColumn"
				},{
						
					"sTitle" : "Acuse",
					"sWidth": "10%",
					"sClass": "dtCenterClassColumn",
					"fnRender": function ( o, val ) {
						var val= "";
						if(o.aData['idEstatus'] != 2){
							val='<input type="button"   class="mboton" value="Acuse" onclick="consultaAcuse('+o.aData['numClaveFolio']+')"/>'					
						}
						return val;
				        }
				}]
		    });  
	}

	
	function muestraPDF(){
		urlImagen = getAppContextParaJS() + "/servlet/EnviaArchivoServlet";
		window.open(urlImagen,"AcuseDenuncia","menubar=1,resizable=1,width=500,height=500");
//		  $("#denunciaForm").attr("action",getAppContextParaJS() + "/denuncia/muestraPDF.do" );		
//		  $("#denunciaForm").submit();
	}
	
function consultaAcuse(val){
	var scveFoliodenuncia = '"cveFoliodenuncia":'+'"' + val + '"';	
	var sDenuncia = '{'+ scveFoliodenuncia +'}';
	var jDenuncia = jQuery.parseJSON(sDenuncia);
	  
		$.postJSON(getAppContextParaJS() +  "/denuncia/recuperaDenunciaFinalizada/" + val + ".do", jDenuncia, function(denuncia) {
			objDenuncia=denuncia;
			objDenuncia.consultaAcuse=true;
			$.postJSON( getAppContextParaJS() + "/denuncia/guardarDenuncia.do", objDenuncia, function(respuesta) {
				objDenuncia = respuesta;
				//console.log("clave denuncia Guardada="+objDenuncia.cveDenuncia);	
				
			}).error(function(denuncia){ 
				alert("Error consulta denuncia");
			}).complete(function(){
			//	alert ("Denuncia enviada exitosamente");
				desbloquear();
				//muestraPDF();
				//$('#pagConsultaForma').submit();
			//	alert("Denuncia Finalizada");
//				$("#denunciaForm").attr("action",getAppContextParaJS() + "/denuncia/muestraPDF.do" );		
//				$("#denunciaForm").submit();
				muestraPDF();
				
			});

		}).error(function(denuncia){ 
			//alert("error generando denuncia" + denuncia);
		}).complete(function(){ 
			//alert("complete cargando denuncia" + denuncia);
		});
	  
	  
	
}
	function agregaDenuncia() {	
		$("#ndForm").attr("action",getAppContextParaJS() + "/denuncia/nueva/nueva.do" );
		$("#ndForm").submit(); 
	}

	function consultarDenuncia(){
		var idDenunciaRdSelect;
		idDenunciaRdSelect = $('input:radio[name=rdCveFolioDenuncia]:checked').val();
		
		if (idDenunciaRdSelect==null || idDenunciaRdSelect==undefined) {
			alert ("Seleccione una denuncia");
			return;
		}else if(!validaEstatusDenuncia(idDenunciaRdSelect)){
			return;		
		}
		var scveFoliodenuncia = '"cveFoliodenuncia":'+'"' + idDenunciaRdSelect + '"';	
		var sDenuncia = '{'+ scveFoliodenuncia +'}';
		var jDenuncia = jQuery.parseJSON(sDenuncia);

		  $("#ndForm").attr("action",getAppContextParaJS() + "/denuncia/consultarDenuncia/" + idDenunciaRdSelect + ".do" );		
		  $("#ndForm").submit();
	}



	function validaEstatusDenuncia(id){
		var flag=true;
		for(var s=0;s<dataDenuncias.length;s++){
			if(dataDenuncias[s].numClaveFolio==id){
				if(dataDenuncias[s].estatus!='En captura'){
					flag=false;
					alert("La denuncia ya se encuentra "+dataDenuncias[s].estatus);
				}
			}
		}
		return flag;
	}

	function seleccionaDenuncia(){
		var radios = document.getElementsByName("radioDenuncias");
		var seleccionado = 0;
		var idDenunciaRdSelect;
		
		for (i=0;i<radios.length;i++){
			if(radios[i].checked){
				idDenunciaRdSelect = radios[i].value;
			}
		}
		
		var scveFoliodenuncia = '"cveFoliodenuncia":'+'"' + idDenunciaRdSelect + '"';	
		var sDenuncia = '{'+ scveFoliodenuncia +'}';
		var jDenuncia = jQuery.parseJSON(sDenuncia);
		
		$.postJSON(getAppContextParaJS() + "/denuncia/cargaDenuncia.do", jDenuncia, function(denuncia) {
			  if(denuncia != null){	    		
				  if(denuncia.idStatus == '1'){
					  $("#ndForm").attr("action",getAppContextParaJS() + "/denuncia/nueva/" + idDenunciaRdSelect + ".do" );		
					  $("#ndForm").submit(); 
				  }else{
					  alert("No es posible tener acceso al detalle de la denuncia debido a que ya fue enviada al Instituto");				  
				  }			  		          						 
			  }
		}).error(function(denuncia){ 
			//alert("error generando denuncia" + denuncia);
		}).complete(function(){ 
			//alert("complete cargando denuncia" + denuncia);
		});
		
	}

	
	
	
	
	