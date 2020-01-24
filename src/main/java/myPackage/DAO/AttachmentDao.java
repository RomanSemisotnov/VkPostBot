package myPackage.DAO;

import myPackage.entities.Attachment;
import org.springframework.stereotype.Component;

@Component
public class AttachmentDao extends AbstractDAO<Attachment> {

    public AttachmentDao() {
        setEntityClass(Attachment.class);
    }

}
