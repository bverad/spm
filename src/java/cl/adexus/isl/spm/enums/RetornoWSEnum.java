package cl.adexus.isl.spm.enums;

import java.util.HashMap;
import java.util.Map;

public enum RetornoWSEnum {
    CODE_21("-21", "Documento no valido : XML mal formado."),
    CODE_22("-22", "Documento no valido : Mensaje de Schema XML"),
    CODE_23("-23", "Firma no valida: La firma del documento no es valida"),
    CODE_24("-24", "Firma no valida: Algunas zonas del documento no han sido incluidas en la firma (excluyendo la zona O)."),
    CODE_25("-25", "Certificado no valido:  Certificado X509 de la firma es invalido."),
    CODE_31("-31", "Documento no valido: El rut del empleador no existe o es invalido (Modulo 11)."),
    CODE_32("-32", "Documento no valido: El rut del trabajador no existe o es invalido (Modulo 11)."),
    CODE_33("-33", "Documento no valido: El rut del denunciante no existe o es invalido (Modulo 11)."),
    CODE_34("-34", "Documento no valido: La fecha de diagnostico debe ser mayor a la fecha de ingreso al trabajo actual."),
    CODE_36("-36", "Documento no valido: La fecha del accidente debe ser mayor a la fecha de ingreso del trabajo actual."),
    CODE_38("-38", "Documento no valido: El rut del medico no existe o es invalido (Modulo 11)"),
    CODE_39("-39", "Documento no valido: El rut de la entidad no existe o es invalido. No corresponde por tipo de origen de la ultima entidad ingresada"),
    CODE_311("-311", "Documento no valido: La fecha de termino de la incapacidad temporal debe ser mayor o igual a la fecha de inicio de la incapacidad temporal."),
    CODE_312("-312", "Documento no valido: El numero de dias de incapacidad temporal debe ser igual a la diferencia entre la fecha de termino de la incapacidad temporal menos la fecha de inicio de la incapacidad temporal."),
    CODE_313("-313", "Documento no valido: El Rut del calificador no existe o es invalido (modulo 11)."),
    CODE_314("-314", "Funcion no corresponde: La funcion no es ingreso de documentos XML."),
    CODE_315("-315", "Tipo de documento no existe: El tipo de documento no corresponde: 1= DIAT OA;2= DIEP OA;3= DIAT OE; 4= DIEP OE; 5= DIAT OT; 6= DIEP OT; 7= RECA; 8= RELA;9= ALLA; 10 = ALME; 11= REIP;."),
    CODE_316("-316", "Documento no valido: Fecha de emision ser menor o igual a la fecha actual."),
    CODE_317("-317", "Documento no valido: Nombre calle del empleador no pueden estar vacios."),
    CODE_318("-318", "Documento no valido: Nombres o Apellido Paterno o Materno del empleado no pueden estar vacios."),
    CODE_319("-319", "Documento no valido: Fecha Nacimiento del empleado debe ser menor a la fecha actual."),
    CODE_320("-320", "Documento no valido: Nombre Calle del empleado no pueden estar vacios."),
    CODE_321("-321", "Documento no valido: La fecha de incorporacion del trabajador es mayor a la fecha actual."),
    CODE_327("-327", "Documento no valido: El Rut del ministro de fe no existe o es invalido (modulo 11)."),
    CODE_328("-328", "Documento no valido: Fecha Nacimiento del ministro de fe debe ser menor o igual a la fecha actual."),
    CODE_330("-330", "Documento no valido: Rut del encargado comision invalido."),
    CODE_331("-331", "Documento no valido: Fecha Nacimiento del encargado comision debe ser menor o igual a la fecha actual."),
    CODE_332("-332", "Documento no valido: La fecha de nacimiento del calificador debe ser menor a la fecha actual."),
    CODE_334("-334", "Documento no valido: Fecha de emision del documento debe mayor o igual a la fecha minima permitida (01/01/2005)."),
    CODE_335("-335", "Documento no valido: Fecha Accidente debe ser mayor o igual a la fecha minima permitida (60 anios antes de la fecha actual)."),
    CODE_336("-336", "Documento no valido: El codigo emisor del documento no corresponde al organismo administrador que invoco el webservice."),
    CODE_337("-337", "Documento no valido: CUN del documento no coincide con CUN de llamada al webservice."),
    CODE_338("-338", "Documento no valido: Documento no puede tener zona de accidente y zona de enfermedad a la vez."),
    CODE_339("-339", "Documento no valido: El documento debe tener la zona de accidente si en el caso hay DIEP y se califico como accidente de trabajo, de trayecto, o laboral sin incapacidad."),
    CODE_340("-340", "Documento no valido: El documento debe tener la zona de enfermedad si en el caso hay DIAT y se califico como enfermedad profesional o laboral sin incapacidad."),
    CODE_341("-341", "Documento no valido: Fecha de nacimiento del trabajador debe ser mayor o igual al 1/1/1900."),
    CODE_342("-342", "Documento no valido: Fecha de ingreso al trabajo debe ser mayor o igual al 1/1/1900."),
    CODE_343("-343", "Documento no valido: Fecha de accidente debe ser menor o igual a la fecha actual."),
    CODE_344("-344", "Documento no valido: Fecha de sintoma enfermedad debe ser mayor o igual al 1/1/1900."),
    CODE_345("-345", "Documento no valido: Fecha de sintoma enfermedad debe ser menor o igual a la fecha actual."),
    CODE_346("-346", "Documento no valido: Fecha de exposicion agente enfermedad debe ser mayor o igual al 1/1/1900."),
    CODE_347("-347", "Documento no valido: Fecha de exposicion agente enfermedad debe ser menor o igual a la fecha actual."),
    CODE_348("-348", "Documento no valido: Fecha de diagnostico debe ser menor o igual a la fecha actual."),
    CODE_349("-349", "Documento no valido: La fecha de nacimiento del calificador debe ser mayor o igual al 1/1/1900."),
    CODE_3110("-3110", "Documento no valido: El tipo de trayecto no aplica en accidentes de trabajo."),
    CODE_40("-40", "Documento valido: Ingreso de documento ha sido exitoso."),
    CODE_41("-41", "Se ha producido un error de sistema. Comuniquese con la mesa de ayuda SUSESO."),
    CODE_44("-44", "Documento no valido: DIAT ya existe."),
    CODE_45("-45", "Documento no valido: DIEP ya existe."),
    CODE_48("-48", "Documento no valido: No existe denuncia. Para completar el proceso de ingreso del documento debe existir un DIAT OA o DIEP OA antes de realizar esta accion."),
    CODE_410("-410", "Acceso: Problemas al accesar la base de datos."),
    CODE_411("-411", "Acceso: Problema al instanciar parser XML."),
    CODE_412("-412", "Acceso: Error al Grabar en Bitacora.."),
    CODE_413("-413", "Documento no valido: CUN no existe."),
    CODE_416("-416", "Documento no valido: Fecha de inicio incapacidad debe ser mayor o igual a la fecha de accidente del caso."),
    CODE_417("-417", "Documento no valido: Fecha de alta laboral debe ser mayor a la fecha de accidente del caso, cuando el expediente tiene RELA."),
    CODE_51("-51", "Los argumentos recibidos no corresponden con la funcion indicada."),
    CODE_52("-52", "Argumentos sin valores. En documentos RECA y posteriores, puede significar que no viene CUN en la llamada al servicio o dentro del documento XML."),
    CODE_53("-53", "Usuario sin perfil para realizar la funcion."),
    CODE_55("-55", "Usuario con Password vencida. EN DESUSO."),
    CODE_56("-56", "Sistema no disponible."),
    CODE_57("-57", "Problemas al llamar a WS Privilegio."),
    CODE_59("-59", "Cta usuario y/o clave no validos.");
    
    
    
    RetornoWSEnum(String codigo, String descripcion){
    	this.codigo = codigo;
    	this.descripcion = descripcion;	
    }  
 
    
    private String codigo;
    private String descripcion;
    private static final Map<String,RetornoWSEnum> map;
    
    /**
     * 
     */
    static {
        map = new HashMap<String,RetornoWSEnum>();
        for (RetornoWSEnum v : RetornoWSEnum.values()) {
            map.put(v.codigo, v);
        }
    }
    public static RetornoWSEnum findByKey(String key) {
        return map.get(key);
    }
    
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}  
}
