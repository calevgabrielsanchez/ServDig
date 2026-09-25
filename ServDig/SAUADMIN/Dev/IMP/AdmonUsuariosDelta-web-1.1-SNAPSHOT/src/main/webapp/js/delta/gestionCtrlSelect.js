function eliminaOpcionesSelect(selector){
	$(selector).html('');
	$(selector).html("<option value=''>--Por favor seleccione--</option>");
}

function comboCtrlSimple(pUrl, pEntidad, pIdHtml){
	this.cargar = function(){
		$.getJSON(pUrl , {clazEntityName: pEntidad}, function (objData){
			 var options = "<option value='' >--Por favor seleccione--</option>";
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


function comboCtrlDependiente(pUrl, pEntidad, pIdHtml, pEntidadPadre, pIdHtmlPadre){
	this.cargardep = function(){
		var valorPadre = $(pIdHtmlPadre).val();
		if(valorPadre==-1){
			eliminaOpcionesSelect(pIdHtml);
		}
		else{
			$.getJSON(pUrl , {clazEntityName: pEntidad, entityParentName: pEntidadPadre, valueParent: valorPadre}, function (objData){
				 var options = "<option value='' >--Por favor seleccione--</option>";
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

