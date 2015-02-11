package Util;

import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;



@FacesConverter(value = "converter")
public class EntityConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null) {
            return this.getAttributesFrom(component).get(value);
        }
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null && !"".equals(value)) {
            BaseEntity entity = (BaseEntity) value;
            //adiciona item como atributo do componente
            this.addAttribute(component, entity);
            //System.out.println(entity.getId()+"-----");
            Long code = entity.getId();
            if (code != null) {
                return String.valueOf(code);
            }

        }
        return (String) value;
    }

    protected void addAttribute(UIComponent component, BaseEntity o) {
        String key = null;
        if(o != null){
            key = o.getId().toString(); //cÃ³digo da entidade como chave            
        }
        this.getAttributesFrom(component).put(key, o);
    }

    protected Map<String, Object> getAttributesFrom(UIComponent component) {
        return component.getAttributes();
    }
}
