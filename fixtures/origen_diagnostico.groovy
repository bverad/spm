import cl.adexus.isl.spm.*

fixture {
	
	OrigenDiagnostico.findByCodigo('1')?:(new OrigenDiagnostico([codigo:'1',descripcion: 'Ficha Médica prestador en convenio'])).save(flush:true)
	OrigenDiagnostico.findByCodigo('2')?:(new OrigenDiagnostico([codigo:'2',descripcion: 'Informe OPA prestador en convenio'])).save(flush:true)
	OrigenDiagnostico.findByCodigo('3')?:(new OrigenDiagnostico([codigo:'3',descripcion: 'Dato de urgencia prestador no en convenio'])).save(flush:true)
	OrigenDiagnostico.findByCodigo('4')?:(new OrigenDiagnostico([codigo:'4',descripcion: 'Otra fuente'])).save(flush:true)
	OrigenDiagnostico.findByCodigo('5')?:(new OrigenDiagnostico([codigo:'5',descripcion: 'ALLA'])).save(flush:true)
	OrigenDiagnostico.findByCodigo('6')?:(new OrigenDiagnostico([codigo:'6',descripcion: 'ALME'])).save(flush:true)
	OrigenDiagnostico.findByCodigo('7')?:(new OrigenDiagnostico([codigo:'7',descripcion: 'RELA'])).save(flush:true)
	
}