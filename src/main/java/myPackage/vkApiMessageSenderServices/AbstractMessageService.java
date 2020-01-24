package myPackage.vkApiMessageSenderServices;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public abstract class AbstractMessageService {

    @Autowired
    protected VkApiClient vkApiClient;

    @Autowired
    protected GroupActor myGroupActor;

}
