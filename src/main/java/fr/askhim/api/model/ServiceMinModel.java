package fr.askhim.api.model;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class ServiceMinModel {

    private Long id;
    private String name;
    private Integer price;
    private List<PhotoModel> photos = new ArrayList<>();
}
