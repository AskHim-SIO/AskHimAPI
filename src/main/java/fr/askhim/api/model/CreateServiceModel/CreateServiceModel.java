package fr.askhim.api.model.CreateServiceModel;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CreateServiceModel {

    private String name;
    private String description;
    private Date dateStart;
    private Date dateEnd;
    private Long price;
    private String userToken;
    private String lieuStr;

}
