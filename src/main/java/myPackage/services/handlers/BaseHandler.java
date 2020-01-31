package myPackage.services.handlers;

import myPackage.enums.Action;
import myPackage.services.vkMessageSenders.MessageSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public abstract class BaseHandler {

    @Autowired
    protected ConcurrentHashMap<Integer, Action> userActionMap;

    @Autowired
    protected MessageSenderService messageSenderService;

    @Autowired
    protected ConcurrentHashMap<Integer, Integer> lastIncommingAttachmentMap;

}
