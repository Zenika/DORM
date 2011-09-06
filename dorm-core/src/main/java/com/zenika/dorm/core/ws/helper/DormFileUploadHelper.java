package com.zenika.dorm.core.ws.helper;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * @author Lukasz Piliszczuk <lukasz.piliszczuk AT zenika.com>
 */
public final class DormFileUploadHelper {

    private static final String UPLOAD_DIR = "tmp/upload/";
    private static final File uploadFolder = new File(UPLOAD_DIR);

    public static File getUploadedFile(InputStream is) {

        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }

        File file = new File(UPLOAD_DIR + new Date().getTime());

        try {
            IOUtils.copy(is, new FileOutputStream(file));
        } catch (IOException e) {
            return null;
        }

        return file;
    }
}
