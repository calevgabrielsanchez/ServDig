function eliminaOpcionesSelect(selector){
	$(selector).html('');
	$(selector).html("<option value='-2'>--Por favor seleccione--</option>");
}

function comboCtrlSimple(pUrl, pEntidad, pIdHtml, pSelectedValueCombo){
	this.cargar = function(){
		
		$.getJSON(pUrl , {clazEntityName: pEntidad}, function (objData){
			 var options = "<option value='-1' >--Por favor seleccione--</option>";
			
			   for (var i = 0; i < objData.length; i++) {
				   if(pSelectedValueCombo!=null && pSelectedValueCombo!='null' && pSelectedValueCombo!="" && (((objData[i].id)+"" )==pSelectedValueCombo)){
					   options += "<option value='"+ objData[i].id +"' selected>"+ objData[i].descripcion +"</option>";
				   }else{
					   options += "<option value='"+ objData[i].id +"'>"+ objData[i].descripcion +"</option>";
				   }	     
		       }
			//Agrega las opciones al control
			$(pIdHtml).html(options);		
		});
		try{ $(pIdHtml).trigger('change'); }catch(err){}	
	}
}//comboCtrlSimple

function comboCtrlSimpleCustom(pUrl, pEntidad, pIdHtml, param, paramValue, overWriteId ){
	this.cargar = function(){
		$.getJSON(pUrl , {clazEntityName: pEntidad, param : param, paramValue:paramValue, paramValue1:overWriteId}, function (objData){
			 var options = "<option value='-1' >--Por favor seleccione--</option>";
			 if(objData!=null)
			   for (var i = 0; i < objData.length; i++) {
				   options += "<option value='"+ objData[i].id +"'>"+ objData[i].descripcion +"</option>";
		       }
			//Agrega las opciones al control
			$(pIdHtml).html(options);		
		});
		try{ $(pIdHtml).trigger('change'); }catch(err){}	
	}
}//comboCtrlSimple


function comboCtrlDependiente(pSelectedValueCombo, pUrl, pEntidad, pIdHtml, pEntidadPadre, pIdHtmlPadre){
	this.cargardep = function(){
		
		//Se modifica 11/11/11 para poder obtener multiples llaves y valores
		
		var valorPadre ="";
	
		if(pIdHtmlPadre.split(",").length>1){
				
			var dom = pIdHtmlPadre.substring(0,pIdHtmlPadre.lastIndexOf("#")+1);
			
			var variables = pIdHtmlPadre.substring(pIdHtmlPadre.lastIndexOf("#")+1,pIdHtmlPadre.length);
			
			var valPadresMultiples = variables.split(",");
			
			for(var countVal=0;countVal<valPadresMultiples.length;countVal++){
				
				if(valPadresMultiples[countVal].search("@")>-1){
					var valAux = valPadresMultiples[countVal];
					valAux = valAux.substring(0,valAux.lastIndexOf("@"));
					valPadresMultiples[countVal] = valAux;
				}
				
				if($(dom+valPadresMultiples[countVal]).val()==-1){
					eliminaOpcionesSelect($(dom+valPadresMultiples[countVal]).val());
				}
				valorPadre = valorPadre + $(dom+valPadresMultiples[countVal]).val()+",";
			}
			
			
		}else{
			
			valorPadre = $(pIdHtmlPadre).val();
			
		}
		
		
		if(valorPadre=="-1" && (pSelectedValueCombo==null || pSelectedValueCombo=='null' || pSelectedValueCombo=="")){
						
			eliminaOpcionesSelect(pIdHtml);
		}
		
		else{
			
			varSelectedVal ="";
			
			
			if(((($(pIdHtml).val())+"")=="0" || (($(pIdHtml).val())+"")=="-1") && (pSelectedValueCombo!=null && pSelectedValueCombo!='null' && pSelectedValueCombo!="")){
				
				var valPadresMultiplesSelected = pSelectedValueCombo.split(",");
				
				if(valPadresMultiplesSelected.length>1){
					
					valorPadre="";
					for(var countVal=0;countVal<valPadresMultiplesSelected.length;countVal++){
						if(countVal==0)
							varSelectedVal =valorPadre + valPadresMultiplesSelected[countVal] + ",";
						else
							valorPadre = valorPadre + valPadresMultiplesSelected[countVal] + ",";
					}
					valorPadre = valorPadre.substring(0,valorPadre.lastIndexOf(","));
					varSelectedVal = ""+varSelectedVal.substring(0,varSelectedVal.lastIndexOf(","))+"";
					
				
				}else{
					valorPadre = pSelectedValueCombo;
					varSelectedVal = ""+pSelectedValueCombo+"";
				}

			}
			
			$.getJSON(pUrl , {clazEntityName: pEntidad, entityParentName: pEntidadPadre, valueParent: valorPadre}, function (objData){
				
				//Se obtiene el valor ID del combo acual
				if(pSelectedValueCombo!=null && pSelectedValueCombo!='null' && pSelectedValueCombo!="")
					varSelectedVal = pSelectedValueCombo.substring(0,pSelectedValueCombo.indexOf(","));
				
				 var options = "";
				 
				 //Verificamos la INFO de base de datos, se carga una vez y posteriormente entra modificación sobre la misma
				 if(pSelectedValueCombo!=null && pSelectedValueCombo!='null' && pSelectedValueCombo!=""){
					 options = "<option value='-2' >--Por favor seleccione--</option>";
				 }else{
					 options = "<option value='-1' >--Por favor seleccione--</option>";
				 }
					 
				 if(objData!=null){
				   for (var i = 0; i < objData.length; i++) {

					   if(varSelectedVal!=null && varSelectedVal!='null' && varSelectedVal!="" && (((objData[i].id)+"" )==varSelectedVal)){
						   options += "<option value='"+ objData[i].id +"' selected>"+ objData[i].descripcion +"</option>";
					   }else{
						   options += "<option value='"+ objData[i].id +"'>"+ objData[i].descripcion +"</option>";
					   }	     	     
			       }
				 }
				 //Agrega las opciones al control
				 $(pIdHtml).html(options);		
			});
			try{ $(pIdHtml).trigger('change'); }catch(err){}				
		}

	}//cargardep
}

function comboCtrlDependienteRelacion(	pUrl, 
										pEntidad,
										pIdHtml,
										pEntidadPadre,
										pIdHtmlPadre,
										pEntidadRelacion,
										idHtmlRelacion,
										pCompValueRelacion
										
										){
	this.cargardeprel = function(){
		
		var valorPadre = $(pIdHtmlPadre).val();
		var valor = $(pIdHtml).val();
		
		if(valorPadre==-1){
			eliminaOpcionesSelect(pIdHtml);
		}
		else{
			$.getJSON(pUrl , {	clazEntityName: pEntidad,
								entityParentName: pEntidadPadre,
								valueParent: pIdHtmlPadre,
								entityRelationName : pEntidadRelacion,
								idRelation : idHtmlRelacion,
								fieldRelation : pCompValueRelacion,
								valueRelation : valorPadre
								
								}, function (objData){
				 var options = "<option value='-1' >--Por favor seleccione--</option>";
				 if(objData!=null)
				   for (var i = 0; i < objData.length; i++) {
					  	 options += "<option value='"+ objData[i].id +"'>"+ objData[i].descripcion +"</option>";	     
			       }
				 //Agrega las opciones al control
				 $(pIdHtml).html(options);		
			});
			try{ $(pIdHtml).trigger('change'); }catch(err){}				
		}

	}//cargardep
}

	function comboCtrlDblDependiente(pUrl, pEntidad, pIdHtml, pEntidadPadre, pIdHtmlPadre, pEntidadPadre2, pIdHtmlPadre2){
		this.cargardbldep = function(){
	
			var valorPadre = $(pIdHtmlPadre).val();
			var valorPadre2 = $(pIdHtmlPadre2).val();
			
			
			var valorPadre2 = $(pIdHtmlPadre2).val();
			var valorpIdHtml = $(pIdHtml).val();
			
			if(valorPadre==-1){
				eliminaOpcionesSelect(pIdHtml);
			}
			if(valorPadre2 ==-1){
				eliminaOpcionesSelect(pIdHtml);
			}
			else{
				$.getJSON(pUrl , {clazEntityName: pEntidad, entityParentName: pEntidadPadre, valueParent: valorPadre, entityParentName2: pEntidadPadre2, valueParent2: valorPadre2}, function (objData){
					 var options = "<option value='-1' >--Por favor seleccione--</option>";
					 if(objData!=null)
					   for (var i = 0; i < objData.length; i++) {
				         options += "<option value='"+ objData[i].id +"'>"+ objData[i].descripcion +"</option>";		     
				       }
					 //Agrega las opciones al control
					 $(pIdHtml).html(options);		
				});
				try{ $(pIdHtml).trigger('change'); }catch(err){}				
			}

		}//cargardep
}//comboCtrlDependiente

