YUI().use('node', function (Y) {
				
	Y.on("domready", function(){
		var dictamen = document.getElementById("dictamen").value;
	    if (dictamen == "true"){
			document.getElementById("buscarSiniestros").disabled = true;
	    	Y.one("#DivDictamen").setStyle("display", "")	
	    }else{
			document.getElementById("buscarSiniestros").disabled = false;
	    	Y.one("#DivDictamen").setStyle("display", "none");	
	    }
	});
		    		
	Y.one('#dictamen_0').delegate('click', function (e) {						
		Y.one("#DivDictamen").setStyle("display", "")
		document.getElementById("buscarSiniestros").disabled = true;
		/*document.getElementById("fechaDictamen").required = true;
		document.getElementById("DictamenAdjunto").required = true;
		document.getElementById("numeroDictamen").required = true;
		document.getElementById("DictamenAdjunto").required = true;*/
	}, 'input[type=radio]');

	Y.one('#dictamen_1').delegate('click', function (e) {	
		Y.one("#DivDictamen").setStyle("display", "none")
		document.getElementById("buscarSiniestros").disabled = false;
		document.getElementById("fechaDictamen").required = false;
		document.getElementById("DictamenAdjunto").required = false;
		document.getElementById("numeroDictamen").required = false;
		document.getElementById("DictamenAdjunto").required = false;
	}, 'input[type=radio]');
							  	
});
		