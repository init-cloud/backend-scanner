package scanner.prototype.middleware;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.bertramlabs.plugins.hcl4j.RuntimeSymbols.ListPrimitiveType;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;


@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class TfNCP {
    LinkedHashMap<String, Object>  data;
    LinkedHashMap<String, Object>  local;
    LinkedHashMap<String, Object>  module;
    LinkedHashMap<String, Object>  output;
    LinkedHashMap<String, Object>  provider;
    LinkedHashMap<String, Object>  resource;
    LinkedHashMap<String, Object>  terraform;
    LinkedHashMap<String, Object>  variable;

    public void parseArrayList(ArrayList<?> value){
        for(int i = 0 ; i < value.size() ; i++){
            if(value.get(i) instanceof LinkedHashMap){
            }
            else if(value.get(i) instanceof ArrayList){
                parseArrayList((ArrayList<?>)value.get(i));
            }
            else if(value.get(i) instanceof String){
            }
            else if(value.get(i) instanceof ListPrimitiveType){
            }
            else{
            }
        }
    }
    
    public void parseType(String keys, Object values){
        if(values instanceof LinkedHashMap){
        }
        else if(values instanceof ArrayList){
        }
        else if(values instanceof String){
        }
        else if(values instanceof ListPrimitiveType){
        }
        else{
        }
    }

    public void setEachBlock(Object key, LinkedHashMap<?, ?> value){
        value.forEach((keys, values)
            -> {
                String keyStr = (String)keys;
                switch(keyStr){
                    case "data":
                        data.put(keyStr, values);
                    case "local":
                        local.put(keyStr, values);
                    case "module":
                        module.put(keyStr, values);
                    case "output":
                        output.put(keyStr, values);
                    case "provider":
                        provider.put(keyStr, values);
                    case "resource":
                        resource.put(keyStr, values);
                    case "terraform":
                        terraform.put(keyStr, values);
                    case "variable":
                        variable.put(keyStr, values);
                    default:
                }
            }
        );
    }
}
