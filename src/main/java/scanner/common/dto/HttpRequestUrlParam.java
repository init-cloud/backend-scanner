package scanner.common.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HttpRequestUrlParam {
    private String urlQuery = null;
    private String urlParam = null;

    public void setUrlQuery(String query){
        if(urlQuery == null || !urlQuery.startsWith("?"))
            this.urlQuery = "?";

        this.urlQuery += query;
    }

    public void setUrlParam(String param){
        if(urlParam == null || !urlParam.startsWith("/"))
            this.urlParam = "/";

        this.urlParam += param;
    }

    public String getUri(){
        if(urlQuery == null)
            return this.urlParam;
        else if(urlParam == null)
            return this.urlQuery;
        else
            return this.urlParam + urlQuery;
    }
}
