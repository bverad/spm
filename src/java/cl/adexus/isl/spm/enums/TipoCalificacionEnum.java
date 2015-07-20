package cl.adexus.isl.spm.enums;

import java.util.HashMap;
import java.util.Map;

public enum TipoCalificacionEnum {
    CODE_01("01", "Accidente del trabajo"),
    CODE_02("02", "Accidente de Trayecto"),
    CODE_03("03", "Enfermedad Profesional"),
    CODE_04("04", "Accidente ocurrido a causa o con ocasion del trabajo con alta inmediata"),
    CODE_05("05", "Enfermedad Laboral con Alta inmediata y/o sin Incapacidad Permanente"),
    CODE_06("06", "Accidente Comun"),
    CODE_07("07", "Enfermedad Comun."),
    CODE_08("08", "Siniestro de trabajador no protegido por la Ley 16.744"),
    CODE_09("09", "Accidente ocurrido en el trayecto con alta inmediata"),
    CODE_10("10", "Accidente de dirigente sindical en cometido gremial"),
    CODE_11("11", "Accidente debido a fuerza mayor ajena al trabajo"),
    CODE_12("12", "No se detecta enfermedad"),
    CODE_13("13", "Derivacion a otro organismo administrador");
    
    TipoCalificacionEnum(String codigo, String descripcion){
    	this.codigo = codigo;
    	this.descripcion = descripcion;	
    }  
 
    
    private String codigo;
    private String descripcion;
    private static final Map<String,TipoCalificacionEnum> map;
    
    /**
     * 
     */
    static {
        map = new HashMap<String,TipoCalificacionEnum>();
        for (TipoCalificacionEnum v : TipoCalificacionEnum.values()) {
            map.put(v.codigo, v);
        }
    }
    public static TipoCalificacionEnum findByKey(String key) {
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
