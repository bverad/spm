import cl.adexus.isl.spm.*

fixture {
	
	TipoConvenio.findByCodigo('01')?:(new TipoConvenio([codigo:'01', descripcion: 'Prestaciones clínicas hospitalizadas y ambulatorias'])).save(flush:true)
	TipoConvenio.findByCodigo('02')?:(new TipoConvenio([codigo:'02', descripcion: 'Rehabilitación traumatológica y terapía ocupacional ambulatoria'])).save(flush:true)
	TipoConvenio.findByCodigo('03')?:(new TipoConvenio([codigo:'03', descripcion: 'Neurorehabilitación y terapía ocupacional pacientes con lesiones medulares/tec ambulatorias'])).save(flush:true)
	TipoConvenio.findByCodigo('04')?:(new TipoConvenio([codigo:'04', descripcion: 'Prestaciones ambulatorias'])).save(flush:true)
	TipoConvenio.findByCodigo('05')?:(new TipoConvenio([codigo:'05', descripcion: 'Cuidados de pacientes'])).save(flush:true)
	TipoConvenio.findByCodigo('06')?:(new TipoConvenio([codigo:'06', descripcion: 'Traslado de pacientes'])).save(flush:true)
	TipoConvenio.findByCodigo('07')?:(new TipoConvenio([codigo:'07', descripcion: 'Salud mental'])).save(flush:true)
	TipoConvenio.findByCodigo('08')?:(new TipoConvenio([codigo:'08', descripcion: 'Ayudas técnicas e insumos'])).save(flush:true)
	TipoConvenio.findByCodigo('09')?:(new TipoConvenio([codigo:'09', descripcion: 'Neurohabilitación y terapía ocupacional hospitalización'])).save(flush:true)
	TipoConvenio.findByCodigo('10')?:(new TipoConvenio([codigo:'10', descripcion: 'Exámenes ocupacionales'])).save(flush:true)
	TipoConvenio.findByCodigo('11')?:(new TipoConvenio([codigo:'11', descripcion: 'Enfermedad profesional'])).save(flush:true)	
	
}