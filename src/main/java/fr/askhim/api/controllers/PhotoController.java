package fr.askhim.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/photo")
public class PhotoController {

    @PostMapping("save-photo")
    public String savePhoto(HttpServletResponse response, @RequestParam(required = false) String photoName, @RequestPart(value = "file", required = true) MultipartFile file) throws IOException {

        return "";
    }

}
