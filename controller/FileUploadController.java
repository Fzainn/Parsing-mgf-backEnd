package com.example.file.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.example.file.FileProcessing;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileUploadController
{
    @RequestMapping(value = "/upload",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)

    public ResponseEntity<ArrayList<FileProcessing.model>> fileUpload(@RequestParam("file") MultipartFile file) throws IOException
    {
        File convertFile = new File("C:\\Users\\compu\\Desktop\\NASH.mgf" + file.getOriginalFilename());
        convertFile.createNewFile();

        try (FileOutputStream fout = new FileOutputStream(convertFile))
        {
            fout.write(file.getBytes());
        }
        catch (Exception exe)
        {
            exe.printStackTrace();
        }
        FileProcessing fileProcessing = new FileProcessing();
        ArrayList<FileProcessing.model> data = fileProcessing.FileProcessingFunc(convertFile.getPath());
        System.out.println("File has uploaded successfully");
        return ResponseEntity.ok(data);
    }
}