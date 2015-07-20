import cl.adexus.isl.spm.*
import java.text.DateFormat
import java.text.SimpleDateFormat

fixture {
	
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	
	def p456789331=Prestador.findByPersonaJuridica(PersonaJuridica.findByRut('456789331'))
	def p567891232=Prestador.findByPersonaJuridica(PersonaJuridica.findByRut('567891232'))

	Convenio.findByPrestador(p456789331)?:(new Convenio(
		[
			cargoISL:'Prestadores',
			cargoResponsable: 'Tecnico',
			emailISL: 'qaprestadores@gmail.com',
			emailResponsable: 'qaprestadores@gmail.com',
			esActivo: true,
			fechaAdjudicacion: df.parse('01/11/2013'),
			fechaProximoReajuste: df.parse('01/11/2013'),
			fechaResolucion: df.parse('01/11/2013'),
			inicio: df.parse('10/11/2013'),
			nombre: 'Mutual',
			nombreISL: 'Pedro',
			nombreResponsable: 'Maria',
			numeroLicitacion: 'N12345',
			numeroResolucion: '1234',
			periodoReajustable: 'Periodo 2013',
			prestador: p456789331,
			recargoHorarioInhabil: 10,
			telefonoISL: '1234',
			telefonoResponsable: '1234567',
			termino: df.parse('31/12/2014'),
			tipoConvenio: TipoConvenio.findByCodigo('01')
			
		]
		)).save(flush:true)
	
	Convenio.findByPrestador(p567891232)?:(new Convenio(
		[
			cargoISL:'Prestadores',
			cargoResponsable: 'Tecnico',
			emailISL: 'qaprestadores@gmail.com',
			emailResponsable: 'qaprestadores@gmail.com',
			esActivo: true,
			fechaAdjudicacion: df.parse('01/11/2013'),
			fechaProximoReajuste: df.parse('01/11/2013'),
			fechaResolucion: df.parse('01/11/2013'),
			inicio: df.parse('10/11/2013'),
			nombre: 'Mutual',
			nombreISL: 'Pedro',
			nombreResponsable: 'Maria',
			numeroLicitacion: 'N12346',
			numeroResolucion: '1234',
			periodoReajustable: 'Periodo 2013',
			prestador: p567891232,
			recargoHorarioInhabil: 20,
			telefonoISL: '1234',
			telefonoResponsable: '1234567',
			termino: df.parse('31/12/2014'),
			tipoConvenio: TipoConvenio.findByCodigo('01')
			
		]
		)).save(flush:true)
		
}