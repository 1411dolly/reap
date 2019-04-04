package com.ttn.reap.repository;

import com.ttn.reap.entity.Attachment;
import org.springframework.data.repository.CrudRepository;

public interface IFileRepository extends CrudRepository<Attachment,Long> {
    Attachment save(Attachment uploadFileResponse);
    Attachment findById(int id);
}
