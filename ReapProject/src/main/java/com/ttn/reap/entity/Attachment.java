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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public Date getUpload_date() {
        return upload_date;
    }

    public void setUpload_date(Date upload_date) {
        this.upload_date = upload_date;
    }

    @Override
    public String toString() {
        return "Attachment{" +
                "id=" + id +
                ", file_path='" + file_path + '\'' +
                ", upload_date=" + upload_date +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                '}';
    }
}
