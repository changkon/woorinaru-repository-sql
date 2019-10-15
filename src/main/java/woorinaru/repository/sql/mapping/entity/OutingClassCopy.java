package woorinaru.repository.sql.mapping.entity;

import woorinaru.repository.sql.entity.management.administration.OutingClass;

public class OutingClassCopy implements Copy<OutingClass> {

    private WooriClassCopy wooriClassCopy;

    public OutingClassCopy() {
        this(new WooriClassCopy());
    }

    public OutingClassCopy(WooriClassCopy wooriClassCopy) {
        this.wooriClassCopy = wooriClassCopy;
    }

    @Override
    public void copy(OutingClass src, OutingClass dest) {
        this.wooriClassCopy.copy(src, dest);
    }
}
