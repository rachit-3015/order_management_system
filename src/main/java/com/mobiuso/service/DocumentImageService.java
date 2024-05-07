package com.mobiuso.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface DocumentImageService {
    String imageUpload(String path, MultipartFile multipartFile) throws IOException;
    void uploadMultipleFiles(String path, MultipartFile [] multipartFiles);

}
