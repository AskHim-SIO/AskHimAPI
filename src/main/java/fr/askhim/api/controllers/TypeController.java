package fr.askhim.api.controllers;

import fr.askhim.api.entity.Type;
import fr.askhim.api.repository.TypeRepository;
import fr.askhim.api.services.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/type")
public class TypeController {

    @Autowired
    private TypeRepository typeRepository;

    private final TypeService typeService;

    public TypeController(TypeService typeService){
        this.typeService = typeService;
    }

    @GetMapping("/get-types")
    public List<Type> getTypes(){
        return typeService.getAllTypes();
    }
}
