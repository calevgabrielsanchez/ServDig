function eliminaOpcionesSelect(selector){
	$(selector).html('');
	$(selector).html("<option value='-1'>--Por favor seleccione--</option>");
}

function comboCtrlSimple(pUrl, pEntidad, pIdHtml, pValueSelected,
		pValidaVigencia, pCampoVigencia){
	this.cargar = function(){
		$.getJSON(pUrl , {clazEntityName: pEntidad,mostrarSoloActivos : pValidaVigencia,
			campovigencia : pCampoVigencia}, function (objData){
			 var options = "<option value='-1' >--Por favor seleccione--</option>";
			 if(objData!=null)
			   for (var i = 0; i < objData.length; i++) {
				   
				   if( objData[i].id == pValueSelected){
					   options += "<option value='"+ objData[i].id +"' selected='selected' >"+ objData[i].descripcion +"</option>";
				   }else{
					   options += "<option value='"+ objData[i].id +"'>"+ objData[i].descripcion +"</option>";
				   } 
				   
		         		     
		       }
			//Agrega las opciones al control
			$(pIdHtml).html(options);	
			
			
			
			
		});
		try{ $(pIdHtml).trigger('change'); }catch(err){}
	};
}//comboCtrlSimple

function comboCtrlSpecial(pUrl, pEntidad, pIdHtml, pValueSelected, pRegEspecial){
	this.cargar = function(){
		$.getJSON(pUrl , {clazEntityName: pEntidad, regEspecial: pRegEspecial, valorCombo: pValueSelected}, function (objData){
			var options ="";
			if(pEntidad != "mx.gob.imss.ctirss.delta.persistence.DicRazonRegistro2" && (pRegEspecial == 0 || pRegEspecial == 1)){
				options = "<option value='-1' >--Por favor seleccione--</option>";
			}
			 if(objData!=null)
			   for (var i = 0; i < objData.length; i++) {
				   
				   if( objData[i].id == pValueSelected){
					   options += "<option value='"+ objData[i].id +"' selected='selected' >"+ objData[i].descripcion +"</option>";
				   }else{
					   options += "<option value='"+ objData[i].id +"'>"+ objData[i].descripcion +"</option>";
				   } 
	     
		       }
			//Agrega las opciones al control
			$(pIdHtml).html(options);	

		});
		try{ $(pIdHtml).trigger('change'); }catch(err){}
	};
}//comboCtrlSpecial

function comboCtrlDependiente(pUrl, pEntidad, pIdHtml, pEntidadPadre, pIdHtmlPadre, pValueSelected,
		pValidaVigencia, pCampoVigencia){
	this.cargardep = function(){
		var valorPadre = $(pIdHtmlPadre).val();
		if(valorPadre==-1){
			eliminaOpcionesSelect(pIdHtml);
		}
		else{
			$.getJSON(pUrl , {clazEntityName: pEntidad, entityParentName: pEntidadPadre, valueParent: valorPadre,mostrarSoloActivos : pValidaVigencia,
				campovigencia : pCampoVigencia}, function (objData){
				 var options = "<option value='-1' >--Por favor seleccione--</option>";
				 if(objData!=null)
				   for (var i = 0; i < objData.length; i++) {
					   
					   
					   if( objData[i].id == pValueSelected){
						   options += "<option value='"+ objData[i].id +"' selected='selected' >"+ objData[i].descripcion +"</option>";
					   }else{
						   options += "<option value='"+ objData[i].id +"'>"+ objData[i].descripcion +"</option>";
					   } 
					   
			       }
				 //Agrega las opciones al control
				 $(pIdHtml).html(options);		
			});
			try{ $(pIdHtml).trigger('change'); }catch(err){}				
		}

	};//cargardep
}//comboCtrlDependiente

function comboCtrlDep2(pUrl, pEntidad, pIdHtml, pIdHtmlPadre, pValueSelected, pRegEspecial, pActor){
	this.cargardep = function(){
		var valorPadre = $(pIdHtmlPadre).val();
		if(valorPadre==-1){
			eliminaOpcionesSelect(pIdHtml);
		}
		else{
			$.getJSON(pUrl , {clazEntityName: pEntidad, valueParent: valorPadre, regEspecial: pRegEspecial, actor: pActor}, function (objData){				 
				 var options ="";
					if(pEntidad=="mx.gob.imss.ctirss.delta.persistence.DicEstadoCivil" && (valorPadre == 1 || valorPadre == 5 || valorPadre == 6)){
						options = "<option value='-1' >--Por favor seleccione--</option>";
					}
				 if(objData!=null)
				   for (var i = 0; i < objData.length; i++) {
					   
					   
					   if( objData[i].id == pValueSelected){
						   options += "<option value='"+ objData[i].id +"' selected='selected' >"+ objData[i].descripcion +"</option>";
					   }else{
						   options += "<option value='"+ objData[i].id +"'>"+ objData[i].descripcion +"</option>";
					   } 
					   
			       }
				 //Agrega las opciones al control
				 $(pIdHtml).html(options);		
			});
			try{ $(pIdHtml).trigger('change'); }catch(err){}				
		}

	};//cargardep
}//comboCtrlDep2

