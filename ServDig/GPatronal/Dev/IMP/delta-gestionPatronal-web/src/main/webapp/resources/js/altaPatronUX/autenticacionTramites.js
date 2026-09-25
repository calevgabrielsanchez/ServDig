/**
 * 
 */
function auntenticaLogin(){
		AuthenticateSSO.authenticate("SAEM860110HDFNSR01", "10000000000100000004");
		AuthenticateSSO.setOnCloseCallback(function(){
			$("#formlogin").submit();
		});		
}
