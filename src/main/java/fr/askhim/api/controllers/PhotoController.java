package fr.askhim.api.controllers;

import fr.askhim.api.entity.Photo;
import fr.askhim.api.entity.Service;
import fr.askhim.api.entity.Token;
import fr.askhim.api.entity.User;
import fr.askhim.api.payload.ApiResponse;
import fr.askhim.api.repository.PhotoRepository;
import fr.askhim.api.repository.UserRepository;
import fr.askhim.api.services.ServiceService;
import fr.askhim.api.services.TokenService;
import fr.askhim.api.services.UserService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/photo")
public class PhotoController {

    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private UserRepository userRepository;

    private final ServiceService serviceService;

    private final UserService userService;

    private final TokenService tokenService;

    public PhotoController(ServiceService serviceService, UserService userService, TokenService tokenService){
        this.serviceService = serviceService;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("/ajouter-photo-dans-cdn")
    public String ajouterPhotoDansCDN(HttpServletResponse response, @RequestParam(required = false) String photoName, @RequestPart(value = "file", required = true) MultipartFile file) throws IOException {
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

    @PostMapping("/save-photo-to-service")
    public ResponseEntity savePhotoToService(@RequestParam(required = true) long serviceId, @RequestPart(value = "file", required = true) MultipartFile file) throws IOException {
        if(!serviceService.serviceExist(serviceId)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "UNKNOWN_SERVICE", "Le service spécifié n'existe pas"));
        }
        String pathOrigin = "/var/www/html/cdn/";
        String pathBuild = pathOrigin;
        String fileNameBuild = "";
        UUID uuid = UUID.randomUUID();
        fileNameBuild = uuid + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        pathBuild += fileNameBuild;
        file.transferTo(new File(pathBuild));
        Service service = serviceService.getServiceById(serviceId);
        Photo photo = new Photo();
        photo.setLibelle("http://cdn.askhim.ctrempe.fr/" + fileNameBuild);
        photo.setService(service);
        photoRepository.save(photo);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(true, "PHOTO_SAVED", "La photo a été enregistrée avec succès !"));
    }

    @PostMapping("/save-photo-to-user")
    public ResponseEntity savePhotoToUser(@RequestParam(required = true) String token, @RequestPart(value = "file", required = true) MultipartFile file) throws IOException {
        if(!tokenService.tokenExist(UUID.fromString(token))){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, "UNKNOWN_SERVICE", "Le token spécifié n'existe pas"));
        }
        User user = tokenService.getUserByToken(UUID.fromString(token));

        String pathOrigin = "/var/www/html/cdn/";
        String pathBuild = pathOrigin;
        String fileNameBuild = "";
        UUID uuid = UUID.randomUUID();
        fileNameBuild = uuid + "." + FilenameUtils.getExtension(file.getOriginalFilename());
        pathBuild += fileNameBuild;
        file.transferTo(new File(pathBuild));
        user.setProfilPicture("http://cdn.askhim.ctrempe.fr/" + fileNameBuild);
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(true, "PHOTO_SAVED", "La photo à été modifié avec succès !"));
    }

}
