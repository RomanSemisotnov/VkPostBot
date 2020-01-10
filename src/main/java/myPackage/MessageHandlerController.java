package myPackage;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/")
public class MessageHandlerController {

    @PostMapping
    public Map<String, Object> execute(@RequestBody Map<String, Object> params) {
        return params;
    }

}
