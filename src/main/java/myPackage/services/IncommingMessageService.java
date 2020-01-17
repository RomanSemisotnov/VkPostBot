package myPackage.services;

import myPackage.DAO.IncommingMessageDao;
import myPackage.entities.IncommingMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IncommingMessageService {

    @Autowired
    private IncommingMessageDao incommingMessageDao;

    public IncommingMessage getLastByUserId(int user_id) {
        return incommingMessageDao.getLastByUserId(user_id);
    }

}
