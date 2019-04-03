package com.ttn.reap.controller;


import com.ttn.reap.entity.Attachment;
import com.ttn.reap.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;

@RestController
public class AttachmentController {
    private static final Logger logger = LoggerFactory.getLogger(AttachmentController.class);

    @Autowired
    private FileStorageService fileStorageService;

    @RequestMapping(value="/attachment", method=RequestMethod.GET)
    public ModelAndView getForm()
    {
        ModelAndView model = new ModelAndView("attachment");
//        model.addObject("",new UploadFileResponse());
        return model;
    }

    @PostMapping("/uploadFile")
    public Attachment uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
        String newfileName =fileName;

        Attachment attach = new Attachment(newfileName,file.getContentType(),"resources/uploads",LocalDate.now());
        fileStorageService.insert(attach);
        return new Attachment(newfileName,file.getContentType());

    }



    @GetMapping("/downloadFile/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable int id, HttpServletRequest request) throws MalformedURLException {
        // Load file as Resource
        Attachment attach=fileStorageService.searchname(id);
        String name=attach.getFileName();
        System.out.println(name);
        Resource resource = fileStorageService.loadFileAsResource(name);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}



