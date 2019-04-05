package com.ttn.reap.entity;

import javax.persistence.*;
//import java.sql.Date;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "attachment")
public class Attachment {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private String file_path;
    @Column
    private Date upload_date;
    @Column(name = "filename")
    private String fileName;
    private String fileType;

    public Attachment() {
    }


    public Attachment(String fileName, String fileType) {
        this.fileName = fileName;
        this.fileType = fileType;
    }

    public Attachment(String fileName, String fileType,String file_path, Date upload_date) {
        this.file_path = file_path;
        this.upload_date = upload_date;
        this.fileName = fileName;
        this.fileType = fileType;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


}
