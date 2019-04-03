package com.ttn.reap.repository;

import com.ttn.reap.entity.Attachment;
import org.springframework.data.repository.CrudRepository;

public interface IFileRepository extends CrudRepository<Attachment,Long> {
    public Attachment save(Attachment uploadFileResponse);
    public Attachment findById(int id);
}
