package com.ecom.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileUserService {

	//uploading file

    String uploadFile(String path, MultipartFile file) throws Exception;

    //serving file


    InputStream getData(String path, String filename) throws FileNotFoundException;

    //delete file if exists

    boolean deleteFileIfExists(String filePath) throws IOException;
}
