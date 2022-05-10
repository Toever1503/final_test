package com.utils;

import com.config.WebConfiguration;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.IOException;

public class FileUtilsService implements WebMvcConfigurer {

    public static String uploadFile(MultipartFile file, String type) throws IOException {
        if(file.isEmpty()) return null;
        String fileName = file.getOriginalFilename().replaceAll("\\s{2,}", " ");
        StringBuffer folder = new StringBuffer(WebConfiguration.ROOT_CONTENT_SYS);

        File refreshFolder = new File(folder.toString());
        if (!refreshFolder.exists())
            refreshFolder.mkdirs();
        String uploadFile = folder + fileName.toString();
        int i = 0;
        while (true) {
            if (!new File(uploadFile).exists())
                break;
            else
                uploadFile = folder + String.valueOf(i++).concat("-").concat(fileName);
        }
        file.transferTo(new File(uploadFile.toString()));

        uploadFile = uploadFile.replace(WebConfiguration.ROOT_CONTENT_SYS, "");
        System.out.println(uploadFile);
        return uploadFile;
    }

}
