package scanner.prototype.middleware;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.bertramlabs.plugins.hcl4j.HCLParser;
import com.bertramlabs.plugins.hcl4j.HCLParserException;


public class TerraformParser {
 
    /**
     * 구현 중
     * @param tfFile
     * @return
     * @throws HCLParserException
     * @throws IOException
     */
    public String parseToJsonString(File tfFile)
    throws HCLParserException, IOException
    {
        Map tfResults = new HCLParser().parse(tfFile, "UTF-8");

        /* 테스트 용 */
        //tfResults.forEach((key, value)
        //-> System.out.println(key + " " + value.getClass().getName()));
        
        return "";
    }
}
