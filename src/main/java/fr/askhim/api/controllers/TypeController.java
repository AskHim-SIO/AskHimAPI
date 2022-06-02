package fr.askhim.api.controllers;

import fr.askhim.api.entity.Type;
import fr.askhim.api.repository.TypeRepository;
import fr.askhim.api.services.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/type")
public class TypeController {

    Logger logger = Logger.getLogger(TypeController.class.getPackage().getName());

    @Autowired
    private TypeRepository typeRepository;

    private final TypeService typeService;

    public TypeController(TypeService typeService){
        this.typeService = typeService;
    }

    @GetMapping("/get-types")
    public List<Type> getTypes(HttpServletRequest request){
        logger.info("[" + request.getRemoteHost() + "] " + request.getRequestURL());
        return typeService.getAllTypes();
    }
}
