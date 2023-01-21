package scanner.common.utils;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileDigest  {

    private FileDigest() {
        throw new IllegalStateException("Utility class");
    }

    public static byte[] createChecksum(String filename)
    throws IOException, NoSuchAlgorithmException
    {
        InputStream fis =  new FileInputStream(filename);

        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance("SHA-256");
        int numRead;

        do {
            numRead = fis.read(buffer);
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);

        fis.close();
        return complete.digest();
    }

    public static String getChecksum(String filename)
    throws IOException, NoSuchAlgorithmException
    {
        byte[] b = createChecksum(filename);
        StringBuilder result = new StringBuilder();

        for (int i=0; i < b.length; i++)
            result.append(Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring(1));

        return result.toString();
    }
}