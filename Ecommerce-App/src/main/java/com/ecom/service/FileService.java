package com.ecom.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {

    //uploading file

    String uploadFile(String path, MultipartFile file) throws Exception;

    //serving file


    InputStream getData(String path, String filename) throws FileNotFoundException;

    //delete file if exists

    boolean deleteFileIfExists(String filePath)throws IOException;
}
