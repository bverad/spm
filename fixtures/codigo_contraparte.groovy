import cl.adexus.isl.spm.*

fixture {
	
	CodigoContraparte.findByCodigo('1.1')?:(new CodigoContraparte([codigo:'1.1',descripcion: 'Persona a pie'])).save(flush:true)
	CodigoContraparte.findByCodigo('1.2')?:(new CodigoContraparte([codigo:'1.2',descripcion: 'Persona que use un dispositivo de transporte peatonal'])).save(flush:true)
	CodigoContraparte.findByCodigo('2')?:(new CodigoContraparte([codigo:'2',descripcion: 'Vehículo de pedal'])).save(flush:true)
	CodigoContraparte.findByCodigo('3.1')?:(new CodigoContraparte([codigo:'3.1',descripcion: 'Vehículo de tracción animal'])).save(flush:true)
	CodigoContraparte.findByCodigo('3.2')?:(new CodigoContraparte([codigo:'3.2',descripcion: 'Animal montado'])).save(flush:true)
	CodigoContraparte.findByCodigo('3.8')?:(new CodigoContraparte([codigo:'3.8',descripcion: 'Otro dispositivo de transporte no motorizado especificado'])).save(flush:true)
	CodigoContraparte.findByCodigo('3.9')?:(new CodigoContraparte([codigo:'3.9',descripcion: 'Dispositivo de transporte no motorizado no especificado'])).save(flush:true)
	CodigoContraparte.findByCodigo('4.1')?:(new CodigoContraparte([codigo:'4.1',descripcion: 'Bicicleta motorizada'])).save(flush:true)
	CodigoContraparte.findByCodigo('4.2')?:(new CodigoContraparte([codigo:'4.2',descripcion: 'Motocicleta'])).save(flush:true)
	CodigoContraparte.findByCodigo('4.8')?:(new CodigoContraparte([codigo:'4.8',descripcion: 'Otro vehículo motorizado de dos ruedas especificado'])).save(flush:true)
	CodigoContraparte.findByCodigo('4.9')?:(new CodigoContraparte([codigo:'4.9',descripcion: 'Vehículo motorizado de dos ruedas no especificado'])).save(flush:true)
	CodigoContraparte.findByCodigo('5')?:(new CodigoContraparte([codigo:'5',descripcion: 'Vehículo motorizado de tres ruedas'])).save(flush:true)
	CodigoContraparte.findByCodigo('6.1')?:(new CodigoContraparte([codigo:'6.1',descripcion: 'Carro motorizado, vehículo station wagon, furgoneta pequeña para pasajeros, vehículo tipo jeep, vehículo utilitario deportivo, 4x4'])).save(flush:true)
	CodigoContraparte.findByCodigo('6.2')?:(new CodigoContraparte([codigo:'6.2',descripcion: 'minibús, furgoneta de pasajeros'])).save(flush:true)
	CodigoContraparte.findByCodigo('6.3')?:(new CodigoContraparte([codigo:'6.3',descripcion: 'Camioneta de platón, furgoneta para transportar bienes o de trabajo, ambulancia, carro casa'])).save(flush:true)
	CodigoContraparte.findByCodigo('6.4')?:(new CodigoContraparte([codigo:'6.4',descripcion: 'Vehículo de transporte liviano de cuatro o más ruedas utilizado en actividades deportivas y de tiempo libre'])).save(flush:true)
	CodigoContraparte.findByCodigo('6.8')?:(new CodigoContraparte([codigo:'6.8',descripcion: 'Otro vehículo de transporte liviano de cuatro o más ruedas especificado'])).save(flush:true)
	CodigoContraparte.findByCodigo('6.9')?:(new CodigoContraparte([codigo:'6.9',descripcion: 'Vehículo de transporte liviano de cuatro o más ruedas no especificado'])).save(flush:true)
	CodigoContraparte.findByCodigo('7.1')?:(new CodigoContraparte([codigo:'7.1',descripcion: 'Bus'])).save(flush:true)
	CodigoContraparte.findByCodigo('7.2')?:(new CodigoContraparte([codigo:'7.2',descripcion: 'Camión'])).save(flush:true)
	CodigoContraparte.findByCodigo('7.8')?:(new CodigoContraparte([codigo:'7.8',descripcion: 'Otro vehículo de transporte pesado especificado'])).save(flush:true)
	CodigoContraparte.findByCodigo('7.9')?:(new CodigoContraparte([codigo:'7.9',descripcion: 'Vehículo de transporte pesado no especificado'])).save(flush:true)
	CodigoContraparte.findByCodigo('8.1')?:(new CodigoContraparte([codigo:'8.1',descripcion: 'Tren ferrocarril'])).save(flush:true)
	CodigoContraparte.findByCodigo('8.2')?:(new CodigoContraparte([codigo:'8.2',descripcion: 'Tranvía'])).save(flush:true)
	CodigoContraparte.findByCodigo('8.3')?:(new CodigoContraparte([codigo:'8.3',descripcion: 'Funicular, monocarril'])).save(flush:true)
	CodigoContraparte.findByCodigo('8.8')?:(new CodigoContraparte([codigo:'8.8',descripcion: 'Otro vehículo férreo  especificado'])).save(flush:true)
	CodigoContraparte.findByCodigo('8.9')?:(new CodigoContraparte([codigo:'8.9',descripcion: 'Vehículo férreo  no especificado'])).save(flush:true)
	CodigoContraparte.findByCodigo('9.1')?:(new CodigoContraparte([codigo:'9.1',descripcion: 'Vehículo especial usado en la industria'])).save(flush:true)
	CodigoContraparte.findByCodigo('9.2')?:(new CodigoContraparte([codigo:'9.2',descripcion: 'Vehículo especial usado en la agricultura'])).save(flush:true)
	CodigoContraparte.findByCodigo('9.3')?:(new CodigoContraparte([codigo:'9.3',descripcion: 'Vehículo especial usado en la construcción'])).save(flush:true)
	CodigoContraparte.findByCodigo('10.1')?:(new CodigoContraparte([codigo:'10.1',descripcion: 'Motonieve'])).save(flush:true)
	CodigoContraparte.findByCodigo('10.2')?:(new CodigoContraparte([codigo:'10.2',descripcion: 'Aerodeslizador que transite en el suelo o en pantanos'])).save(flush:true)
	CodigoContraparte.findByCodigo('10.8')?:(new CodigoContraparte([codigo:'10.8',descripcion: 'Otro vehículo todo terreno especificado'])).save(flush:true)
	CodigoContraparte.findByCodigo('10.9')?:(new CodigoContraparte([codigo:'10.9',descripcion: 'Vehículo todo terreno no especificado'])).save(flush:true)
	CodigoContraparte.findByCodigo('11.1')?:(new CodigoContraparte([codigo:'11.1',descripcion: 'Barco mercante'])).save(flush:true)
	CodigoContraparte.findByCodigo('11.2')?:(new CodigoContraparte([codigo:'11.2',descripcion: 'Barco de servicio público (de pasajeros)'])).save(flush:true)
	CodigoContraparte.findByCodigo('11.3')?:(new CodigoContraparte([codigo:'11.3',descripcion: 'Bote de pesca, barco de arrastre'])).save(flush:true)
	CodigoContraparte.findByCodigo('11.4')?:(new CodigoContraparte([codigo:'11.4',descripcion: 'Otro vehículo acuático motorizado especificado'])).save(flush:true)
	CodigoContraparte.findByCodigo('11.5')?:(new CodigoContraparte([codigo:'11.5',descripcion: 'Velero, yate sin motor'])).save(flush:true)
	CodigoContraparte.findByCodigo('11.8')?:(new CodigoContraparte([codigo:'11.8',descripcion: 'Otro vehículo acuático sin motor'])).save(flush:true)
	CodigoContraparte.findByCodigo('11.9')?:(new CodigoContraparte([codigo:'11.9',descripcion: 'Vehículo acuático, no especificado como motorizado o sin motor'])).save(flush:true)
	CodigoContraparte.findByCodigo('12.1')?:(new CodigoContraparte([codigo:'12.1',descripcion: 'Aeronave con motor'])).save(flush:true)
	CodigoContraparte.findByCodigo('12.2')?:(new CodigoContraparte([codigo:'12.2',descripcion: 'Aeronave sin motor'])).save(flush:true)
	CodigoContraparte.findByCodigo('12.4')?:(new CodigoContraparte([codigo:'12.4',descripcion: 'Nave espacial'])).save(flush:true)
	CodigoContraparte.findByCodigo('12.5')?:(new CodigoContraparte([codigo:'12.5',descripcion: 'Paracaídas utilizado al saltar de una aeronave con averías'])).save(flush:true)
	CodigoContraparte.findByCodigo('12.6')?:(new CodigoContraparte([codigo:'12.6',descripcion: 'Paracaídas utilizado al saltar de una aeronave en buenas condiciones'])).save(flush:true)
	CodigoContraparte.findByCodigo('12.9')?:(new CodigoContraparte([codigo:'12.9',descripcion: 'Aeronave no especificada'])).save(flush:true)
	CodigoContraparte.findByCodigo('13.1')?:(new CodigoContraparte([codigo:'13.1',descripcion: 'Vehículo estacionado a un lado de la carretera o en un estacionamiento de vehículos'])).save(flush:true)
	CodigoContraparte.findByCodigo('13.2')?:(new CodigoContraparte([codigo:'13.2',descripcion: 'Objeto pequeño desprendido'])).save(flush:true)
	CodigoContraparte.findByCodigo('13.3')?:(new CodigoContraparte([codigo:'13.3',descripcion: 'Objeto fijo pequeño o ligero'])).save(flush:true)
	CodigoContraparte.findByCodigo('13.4')?:(new CodigoContraparte([codigo:'13.4',descripcion: 'Objeto fijo grande o pesado'])).save(flush:true)
	CodigoContraparte.findByCodigo('13.8')?:(new CodigoContraparte([codigo:'13.8',descripcion: 'Otro objeto estacionario o fijo especificado'])).save(flush:true)
	CodigoContraparte.findByCodigo('13.9')?:(new CodigoContraparte([codigo:'13.9',descripcion: 'Objeto estacionario o fijo no especificado'])).save(flush:true)
	CodigoContraparte.findByCodigo('14.1')?:(new CodigoContraparte([codigo:'14.1',descripcion: 'Animal descuidado'])).save(flush:true)
	CodigoContraparte.findByCodigo('14.2')?:(new CodigoContraparte([codigo:'14.2',descripcion: 'Animal arreado'])).save(flush:true)
	CodigoContraparte.findByCodigo('14.8')?:(new CodigoContraparte([codigo:'14.8',descripcion: 'Otro animal especificado'])).save(flush:true)
	CodigoContraparte.findByCodigo('14.9')?:(new CodigoContraparte([codigo:'14.9',descripcion: 'Animal no especificado'])).save(flush:true)
	CodigoContraparte.findByCodigo('15.1')?:(new CodigoContraparte([codigo:'15.1',descripcion: 'Movimiento repentino de un vehículo, sin colisión, que resulte en lesión'])).save(flush:true)
	CodigoContraparte.findByCodigo('15.2')?:(new CodigoContraparte([codigo:'15.2',descripcion: 'Volcada de un vehículo sin colisión'])).save(flush:true)
	CodigoContraparte.findByCodigo('15.9')?:(new CodigoContraparte([codigo:'15.9',descripcion: 'Sin contraparte: no especificado'])).save(flush:true)
	CodigoContraparte.findByCodigo('98')?:(new CodigoContraparte([codigo:'98',descripcion: 'Otra contraparte especificada'])).save(flush:true)
	CodigoContraparte.findByCodigo('99')?:(new CodigoContraparte([codigo:'99',descripcion: 'Contraparte no especificada'])).save(flush:true)

}
