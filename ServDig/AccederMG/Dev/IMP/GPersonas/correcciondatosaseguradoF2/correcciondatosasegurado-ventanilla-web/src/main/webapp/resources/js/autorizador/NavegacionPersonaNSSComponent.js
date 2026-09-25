function NavegacionPersonaNSSComponent( render ){
    this.render = render;
    this.model = this.render.module.controller.model.detalle;
}

NavegacionPersonaNSSComponent.prototype.draw = function (component) {
		var html="";	
	//    var html = '<div class= "row" ';
//    if( !this.render.isEmpty( component.id ) ){
//        html += 'id="navegacion_' + component.id + '" ';
//    }    
//    html += " >";
//    
//    html+= '<div class="col-md-12">';
//	    html+= '<div class="col-md-6">';
//	    //label panel RENAPO
//	    html += '<h4>Informaci&oacute;n para actualizaci&oacute;n</h4>' ;
//	    html += '<hr class="red"/>';
//	    html+= '</div>';
//	    
//	    html+= '<div class="col-md-6">';
//	    //label panel IMSS
//	    html += '<h4>Informaci&oacute;n en el IMSS</h4>' ;
//	    html += '<hr class="red"/>';
////	    if(this.model.informacionBDTU.errorSINDO === true){
////	    	html+= '<span class="error col-md-12" style="color: red;text-align: center; position: absolute; margin-top: -30px;">Movimiento 06 No Operado.</span>';
////	    }
//	    html+= '</div>';
//    html+= '</div>';
//    
//    html+= '<div class="col-md-6">';
//    //label vacio espacio en blanco
//    html += '<h4></h4>' ;
//    html+= '</div>';
//    
//    html+= '<div class="col-md-6">';
//		html+= '<div class="col-md-2">';
//		//boton anterior 
//		html+= '<button onclick="module.controller.anteriorPersona()" type="button" class="btn btn-default" id="btnAnteriorPersona"><span class="glyphicon glyphicon-undefined"></span>&lt;</button>';
//		html+= '</div>';
//		html+= '<div class="col-md-6">';
//		//label origen persona NSS
//		html += '<h4 ';
////		if(this.model.informacionBDTU.errorSINDO === true){
////			html += 'style="color: red;"';
////		} 
//		html+= '> ' + this.model.informacionBDTU.origen + '</h4>' ;
//		html+= '</div>';
//		html+= '<div class="col-md-2 pull-right">';
//		//boton siguiente
//		html += '<button onclick="module.controller.siguientePersona()" type="button" class="btn btn-default" id="btnSiguientePersona"><span class="glyphicon glyphicon-undefined"></span>&gt;</button>';
//		html+= '</div>';
//	html+= '</div>';
//	html+= '<div align="center" id="cambios" style="display:none; margin-top:20px" class="col-md-6"><h4>SIN CAMBIOS.</h4></div>';
//	html+= '<div align="center" id="cambiosB" style="display:none; margin-top:20px" class="col-md-6"><h4>SIN CAMBIOS.</h4></div>';
//    html += '</div>';
    return html;
};

NavegacionPersonaNSSComponent.prototype.notify = function( metadata, model ){
    this.metadata = metadata;
    this.model = this.render.module.controller.model[metadata.model];
    var html = this.draw(metadata);
    $("#navegacion_" + metadata.id).replaceWith( html );
	if( !this.render.isEmpty(metadata.postFetch ) ){
     	module.controller.mostrarDiferentes();
    }
};