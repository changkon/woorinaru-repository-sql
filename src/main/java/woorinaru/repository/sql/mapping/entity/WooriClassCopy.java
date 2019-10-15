package woorinaru.repository.sql.mapping.entity;

import woorinaru.repository.sql.entity.management.administration.WooriClass;

public class WooriClassCopy implements Copy<WooriClass> {

    @Override
    public void copy(WooriClass src, WooriClass dest) {
        dest.setId(src.getId());
        dest.setResources(src.getResources());
        dest.setStaff(src.getStaff());
        dest.setStudents(src.getStudents());
        dest.setEvent(src.getEvent());
    }
}
