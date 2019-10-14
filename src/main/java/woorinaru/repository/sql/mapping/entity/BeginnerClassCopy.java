package woorinaru.repository.sql.mapping.entity;

import woorinaru.repository.sql.entity.management.administration.BeginnerClass;

public class BeginnerClassCopy implements Copy<BeginnerClass> {

    private WooriClassCopy wooriClassCopy;

    public BeginnerClassCopy() {
        this(new WooriClassCopy());
    }

    public BeginnerClassCopy(WooriClassCopy wooriClassCopy) {
        this.wooriClassCopy = wooriClassCopy;
    }

    @Override
    public void copy(BeginnerClass src, BeginnerClass dest) {
        this.wooriClassCopy.copy(src, dest);
    }
}
