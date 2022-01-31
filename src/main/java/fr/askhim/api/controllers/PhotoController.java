package fr.askhim.api.controllers;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/photo")
public class PhotoController {

    @PostMapping("save-photo")
    public String savePhoto(HttpServletResponse response, @RequestParam(required = false) String photoName, @RequestPart(value = "file", required = true) MultipartFile file) throws IOException {
        String pathOrigin = "/var/www/html/cdn/";
        String pathBuild = pathOrigin;
        String fileNameBuild = "";
        if(photoName == null){
            UUID uuid = UUID.randomUUID();
            fileNameBuild = uuid + "." + FilenameUtils.getExtension(file.getOriginalFilename());
            pathBuild += fileNameBuild;
        }else{
            fileNameBuild = photoName + "." + FilenameUtils.getExtension(file.getOriginalFilename());
            pathBuild += fileNameBuild;
            if(new File(pathBuild).exists()){
                response.setStatus(HttpStatus.CONFLICT.value());
                return "Un fichier portant ce nom existe déjà dans le CDN";
            }
        }
        file.transferTo(new File(pathBuild));
        return "http://cdn.askhim.ctrempe.fr/" + fileNameBuild;
    }

}
