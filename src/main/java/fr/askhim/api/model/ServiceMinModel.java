package fr.askhim.api.model;


import fr.askhim.api.models.entity.Lieu;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Getter
@Setter
public class ServiceMinModel {

    private Long id;
    private String name;
    private Integer price;
    private Date postDate;
    private Lieu lieu;
    private List<PhotoModel> photos = new ArrayList<>();
}
