

function habilitarTabs(load){	

	//Si load=true, se considera que esta cargando la página por primera vez. En caso contrario muestra el tab de Acta Constitutiva
	if (tramiteEscrituraConstitutivaActivo){
		if (load){
			$("#tab_6_contents_").css("display","none");							
		}
		else{		
			$("#tab_6_contents_").css("display","none");				
			$("#tab_5_contents_").css("display","block");				
			$("#tab_5_contents_").addClass("active").show();
			$("#tab_6_contents_").removeClass("active");
			$("#tab_6_contents").removeClass("tab_contents_active");
			$("#tab_5_contents").addClass("tab_contents tab_contents_active").fadeIn();		        
			actualizarEC();
		}
	}
	//Si load=true, se considera que esta cargando la página por primera vez. En caso contrario muestra el tab de Registro Sindicato
	if (tramiteRegistroSindicatoActivo){				
		if (load){
			$("#tab_5_contents_").css("display","none");				
		}
		else{
			$("#tab_5_contents_").css("display","none");				
			$("#tab_6_contents_").addClass("active").show();
			$("#tab_5_contents_").removeClass("active");
			$("#tab_5_contents").removeClass("tab_contents_active");
			$("#tab_6_contents").addClass("tab_contents tab_contents_active").fadeIn();		        
			actualizarRS();
		}
	}
	//No se tienen registros ni de acta constitutiva ni de registro de sindicato
	if (!tramiteRegistroSindicatoActivo && !tramiteEscrituraConstitutivaActivo){
		if (vigenteActaConstitutivaActivo){
			$("#tab_5_contents_").css("display","block");		
			$("#tab_6_contents_").css("display","none");
		}
		if (vigenteRegistroSindicatoActivo){
			$("#tab_5_contents_").css("display","none");		
			$("#tab_6_contents_").css("display","block");
		}
		if (!vigenteActaConstitutivaActivo && !vigenteRegistroSindicatoActivo){
			$("#tab_5_contents_").css("display","block");		
			$("#tab_6_contents_").css("display","block");
		}
		
	}
	
	
}