package com.ttn.reap.entity;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name="attachment")
public class Attachment {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column
    private String file_path;
    @Column
    private Date upload_date;
    @Column(name="filename")
    private String fileName;
//    private String fileDownloadUri;
    private String fileType;
//    private long size;
    public Attachment()
    {}

    public Attachment(String newfileName, String contentType, String s, LocalDate now) {
        this.fileName=newfileName;
        this.fileType=contentType;
        this.file_path=s;
        this.upload_date= Date.valueOf(now);
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Attachment(String fileName, String fileType) {
        this.fileName = fileName;
//        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
//        this.size = size;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

//    public String getFileDownloadUri() {
//        return fileDownloadUri;
//    }

//    public void setFileDownloadUri(String fileDownloadUri) {
//        this.fileDownloadUri = fileDownloadUri;
//    }


}
