package scanner.prototype.middleware;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.LinkedHashMap;

import com.bertramlabs.plugins.hcl4j.HCLParser;
import com.bertramlabs.plugins.hcl4j.HCLParserException;


public class TerraformParser {
    /**
     * 
     * @param tfClass
     * @return
     */
    public String networkParser(Map<?, ?> tfResults){
        TfNCP target = new TfNCP();

        tfResults.forEach((key, value)
            -> target.setEachBlock(key, (LinkedHashMap)value)
        );

        return null;
    }

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
        Map<?, ?> tfResults = new HCLParser().parse(tfFile, "UTF-8");

        networkParser(tfResults);

        return null;
    }
}
