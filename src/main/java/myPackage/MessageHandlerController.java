package myPackage;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class MessageHandlerController {

    @PostMapping
    public String execute(@RequestParam(name = "type") String type) {
        return "ok";
    }

}
