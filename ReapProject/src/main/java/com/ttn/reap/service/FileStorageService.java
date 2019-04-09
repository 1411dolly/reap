package com.ttn.reap.service;

import com.ttn.reap.entity.Attachment;
import com.ttn.reap.property.FileStorageProperties;
import com.ttn.reap.repository.IFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
    private final Path fileStorageLocation;
    @Autowired
    private IFileRepository iFileRepository;

    @Autowired
    public FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void insert(Attachment entity) {
        iFileRepository.save(entity);
    }

    public Attachment searchname(int id) {
        return iFileRepository.findById(id);
    }

    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = file.getOriginalFilename();

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                System.out.println("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            ex.printStackTrace();
            return "Could not store file \" + fileName + \". Please try again!";
        }
    }

    public Resource loadFileAsResource(String fileName) throws MalformedURLException {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            System.out.println(filePath);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                System.out.println("File not found " + fileName);
                return resource;
            }
        } catch (MalformedURLException ex) {
            System.out.println("File not found " + fileName);
            ex.printStackTrace();
//            return new Resource("eoor");
//            throw new MyFileNotFoundException("File not found " + fileName, ex);
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            return resource;
        }
    }

    public Attachment findAttachmentById(long id) {
        return iFileRepository.findById(id);
    }
}
