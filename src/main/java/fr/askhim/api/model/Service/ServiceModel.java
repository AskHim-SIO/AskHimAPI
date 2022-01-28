package fr.askhim.api.model.Service;


import fr.askhim.api.model.LieuModel;
import fr.askhim.api.model.PhotoModel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Getter
@Setter
public class ServiceModel {

    private Long id;
    private String name;
    private String description;
    private Date dateStart;
    private Date dateEnd;
    private Boolean state;
    private Long price;
    private Date postDate;
    private LieuModel lieu;
    private List<PhotoModel> photos = new ArrayList<>();
}
