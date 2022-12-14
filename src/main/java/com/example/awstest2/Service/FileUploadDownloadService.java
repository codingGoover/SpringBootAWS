package com.example.awstest2.Service;

import com.example.awstest2.FileDownloadException;
import com.example.awstest2.FileUploadException;
import com.example.awstest2.FileUploadProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileUploadDownloadService {

    private final Path fileLocation;

    @Autowired
    public FileUploadDownloadService(FileUploadProperties prop) {
        this.fileLocation = Paths.get(prop.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileLocation);
        }catch(Exception e) {
            throw new FileUploadException("파일을 업로드할 디렉토리를 생성하지 못했습니다.", e);
        }
    }
/*
    public String storeFileCafeteria(MultipartFile file){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        Path path = this.fileLocation.resolve("cafeteria"); //폴더 경로
        File Folder = new File(path.toUri());

        // 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
        if (!Folder.exists()) {
            try{
                Folder.mkdir(); //폴더 생성합니다.
                System.out.println("make folder");
            }
            catch(Exception e){
                e.getStackTrace();
            }
        }else {
            System.out.println("already folder");
        }

        try {
            // 파일명에 부적합 문자가 있는지 확인한다.
            if(fileName.contains(".."))
                throw new FileUploadException("파일명에 부적합 문자가 포함되어 있습니다. " + fileName);

            Path targetLocation = this.fileLocation.resolve("cafeteria").resolve(fileName);
            System.out.println("targetLocation: "+ targetLocation.toString());
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        }catch(Exception e) {
            throw new FileUploadException("["+fileName+"] 파일 업로드에 실패하였습니다. 다시 시도하십시오.",e);
        }
    }*/

    public String storeFile(MultipartFile file, String type) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        Path path = this.fileLocation.resolve(type); //폴더 경로
        File Folder = new File(path.toUri());

        // 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
        if (!Folder.exists()) {
            try{
                Folder.mkdir(); //폴더 생성합니다.
                System.out.println("make folder");
            }
            catch(Exception e){
                e.getStackTrace();
            }
        }else {
            System.out.println("already folder");
        }

        try {
            // 파일명에 부적합 문자가 있는지 확인한다.
            if(fileName.contains(".."))
                throw new FileUploadException("파일명에 부적합 문자가 포함되어 있습니다. " + fileName);

            Path targetLocation = path.resolve(fileName);
            System.out.println("targetLocation: "+ targetLocation.toString());
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        }catch(Exception e) {
            throw new FileUploadException("["+fileName+"] 파일 업로드에 실패하였습니다. 다시 시도하십시오.",e);
        }
    }
/*
    public Resource loadFileCafeteriaAsResource(String fileName){
        try {
            Path filePath = this.fileLocation.resolve("cafeteria").resolve(fileName).normalize();
            System.out.println("filePath: "+ filePath.toString());
            System.out.println("filePath.Uri: "+ filePath.toUri().toString());

            Resource resource = new UrlResource(filePath.toUri());
            System.out.println("resource.getURL() : "+ resource.getURL().toString());

            if(resource.exists()) {
                return resource;
            }else {
                throw new FileDownloadException(fileName + " 파일을 찾을 수 없습니다.");
            }
        }catch(MalformedURLException e) {
            throw new FileDownloadException(fileName + " 파일을 찾을 수 없습니다.", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }*/
    public Resource loadFileAsResource(String fileName,String type) {
        try {
            Path filePath = this.fileLocation.resolve(type).resolve(fileName).normalize();
            System.out.println("filePath: "+ filePath.toString());
            System.out.println("filePath.Uri: "+ filePath.toUri().toString());

            Resource resource = new UrlResource(filePath.toUri());
            System.out.println("resource.getURL() : "+ resource.getURL().toString());

            if(resource.exists()) {
                return resource;
            }else {
                throw new FileDownloadException(fileName + " 파일을 찾을 수 없습니다.");
            }
        }catch(MalformedURLException e) {
            throw new FileDownloadException(fileName + " 파일을 찾을 수 없습니다.", e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
