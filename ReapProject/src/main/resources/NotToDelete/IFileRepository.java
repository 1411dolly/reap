package NotToDelete;

import com.ttn.reap.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFileRepository extends JpaRepository<Attachment,Long> {
    Attachment save(Attachment uploadFileResponse);
    Attachment findById(long id);
}
