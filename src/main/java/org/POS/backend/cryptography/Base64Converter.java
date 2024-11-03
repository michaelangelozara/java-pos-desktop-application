package org.POS.backend.cryptography;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

public class Base64Converter {

    private String base64;

    public void setConvertFileToBase64(File file) throws IOException {

        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] fileByte = new byte[(int) file.length()];
        fileInputStream.read(fileByte);
        fileInputStream.close();

        this.base64 = Base64.getEncoder().encodeToString(fileByte);
    }

    public String getBase64(){
        return this.base64;
    }
}
