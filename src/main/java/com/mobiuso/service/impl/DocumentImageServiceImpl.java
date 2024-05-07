package com.mobiuso.service.impl;

import com.mobiuso.jwt.JwtTokenProvider;
import com.mobiuso.service.DocumentImageService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Component
public class DocumentImageServiceImpl implements DocumentImageService {

    @Override
    public String imageUpload(String path, MultipartFile multipartFile) throws IOException {

        String fileName = multipartFile.getOriginalFilename();      //getting file name
        log.info("FileName :" + fileName);

        String modifiedFileName = UUID.randomUUID().toString().concat(fileName.substring(fileName.lastIndexOf(".")));

        //fullpath
        String filePath = path + File.separator + modifiedFileName;
        log.info("FilePath : "+ filePath);

        File file = new File(path);
        if(!file.exists()){
            file.mkdir();
        }

        Files.copy(multipartFile.getInputStream(), Paths.get(filePath));

        return filePath;
    }

    @Override
    public void uploadMultipleFiles(String path,MultipartFile[] multipartFiles) {


    }


}
