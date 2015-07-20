YUI().use("node", "transition", "panel", "io", "dump", "json-parse",  function (Y) {  // loading escape only for security on this page

    var desactivarRun = function(e) {
    	var tipoDenuncianteCodigo=Y.one("#tipoDenunciante").get('value');
    	if (tipoDenuncianteCodigo=='2'){ //Si es trabajador
    	    Y.one("#run").set('value',Y.one("#trabajador_run").get('value'));
    	    Y.one("#run").set("disabled",true)
    	}else{
    	    Y.one("#run").set("disabled",false);
    	    Y.one("#run").set('value','');
    	}
    }
    Y.one("#tipoDenunciante").on("change", desactivarRun);
});