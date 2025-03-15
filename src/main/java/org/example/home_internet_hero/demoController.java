package org.example.home_internet_hero;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
public class demoController {

    private Map<String,plans> obj = new HashMap<>() {{
        put("1",new plans("fido",30,1));
        put("2",new plans("rogers",40,2));
        put("3",new plans("telus",50,3));
    }};

    @GetMapping("/")
    public String hello() {
        return "Hello World";
    }

    @GetMapping("/plans")
    public Collection<plans> plans() {
        return obj.values();
    }

    @GetMapping("/plans/{id}")
    public plans get(@PathVariable String id) {
        plans foundPlan = obj.get(id);
        if (foundPlan == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Plan not found");
        }
        return foundPlan;
    }

    @GetMapping("/plans/delete/{id}")
    public void delete(@PathVariable String id) {
//        obj.remove(id);
        plans foundPlan = obj.remove(id);
        if (foundPlan == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Plan not found");
        }
    }
}
