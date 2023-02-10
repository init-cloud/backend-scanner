package scanner.common.dto;


import lombok.*;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HttpParam {

    @Getter
    @Setter
    public static class Header{
        List<String> keys;
        List<String> values;

        public Header(){
            this.keys = new ArrayList<>();
            this.values = new ArrayList<>();
        }

        public Header(String key, String value){
            this.keys = new ArrayList<>();
            this.values = new ArrayList<>();

            keys.add(key);
            values.add(value);
        }

        public void add(String key, String value){
            keys.add(key);
            values.add(value);
        }
    }

    @Getter
    @Setter
    public static class Path{
        private List<String> values;

        public Path(){
            this.values = new ArrayList<>();
        }

        public Path(String value){
            this.values = new ArrayList<>();
            values.add(value);
        }

        public void add(String value){
            values.add(value);
        }

        public String getPath(){
            StringBuilder sb = new StringBuilder();

            for(String path : this.values)
                sb.append("/" + path);

            return sb.toString();
        }
    }

    @Getter
    @Setter
    public static class Query{

        private List<String> keys;
        private List<String> values;

        public Query(){
            this.keys = new ArrayList<>();
            this.values = new ArrayList<>();
        }

        public Query(String key, String value){
            this.keys = new ArrayList<>();
            this.values = new ArrayList<>();

            keys.add(key);
            values.add(value);
        }

        public void add(String key, String value){
            keys.add(key);
            values.add(value);
        }

        public String getQuery(){
            StringBuilder sb = new StringBuilder();
            sb.append("?");

            for(int i = 0 ; i < this.keys.size() ; i++)
                sb.append(this.keys.get(i) + "=" + this.values.get(i) + "&");

            return sb.toString();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Body<T>{
        private String contentType;
        private T data;
    }
}