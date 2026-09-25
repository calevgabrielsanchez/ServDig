/*Seccion de codigo a ejecutar en cuanto el
 * DOM envie la señar de que esta listo para procesar 
 * de modificaciones al DOM
 */
$(function(){
	
	elegirPortal();	
	
	/* 
	 * Primero se checa si el link para el portal empresa esta activo,
	 * de ser así no se le agrega el evento
	 */
	if (!$('li#portalEmpresaLink').hasClass('active')) {
		$('li#portalEmpresaLink').click(function(){
			irPortalEmpresa();
			return false;
		});
	}
	
	if (!$('li#portalAseguradoLink').hasClass('active')) {
		$('li#portalAseguradoLink').click(function(){
			irPortalAsegurado();
			return false;
		});
	}
	
});

/* 
 * Función que checa en qué portal estamos ubicados, para
 * mostrar ACTIVA la opción correspondiente en la navegación
 */
function elegirPortal() {
	var portalContext = $('input#portalContext').val();
	var cvePortalPersona = $('input#cvePortalPersona').val();
	var cvePortalEmpresa = $('input#cvePortalEmpresa').val();
	var cvePortalPatron = $('input#cvePortalPatron').val();
	var cvePortalAsegurado= $('input#cvePortalAsegurado').val();
	var cvePortalDerechohabiente = $('input#cvePortalDerechohabiente').val();
	
	if (portalContext == cvePortalPersona){
		$('li#portalPersonaLink').addClass('active');
		/* 
		 * Si estamos en el portal personal se quita
		 * la clase y atributos del dropdown-menu
		 */
		$('#nav-menu').removeClass('dropdown');
		$('#nav-menu > a').removeClass('dropdown-toggle');
		$('#nav-menu > a').removeAttr('data-toggle');
	} else if (portalContext == cvePortalEmpresa){
		$('li#portalEmpresaLink').addClass('active');
	} else if (portalContext == cvePortalPatron){
		$('li#portalPatronLink').addClass('active');
	} else if(portalContext == cvePortalAsegurado) {
		$('li#portalAseguradoLink').addClass('active');
	} 
}

// Define qué action se debe ejecutar y hace el submit
function irPortalEmpresa() {
	
	var form = $('form#formPortalEmpresaNavAux');
	
	if ($('input#idFiscalEmpresaMoral').size() > 0) {
		form.attr('action', '/portal-web/portal/persona/moral/ingresar/');
	} else {
		form.attr('action', '/portal-web/portal/persona/fisica/ingresar/');
	}
	
	form.submit();
}

function irPortalAsegurado() {
	var form = $('form#formPortalAseguradoAux');
	form.submit();
}