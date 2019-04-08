package com.ttn.reap.repository;

import com.ttn.reap.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface IFileRepository extends JpaRepository<Attachment,Long> {
    Attachment save(Attachment uploadFileResponse);
    Attachment findById(long id);
}
