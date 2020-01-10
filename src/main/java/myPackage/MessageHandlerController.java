package myPackage;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MessageHandlerController {

    @PostMapping
    public String execute() {
        return "ok";
    }
    
}
