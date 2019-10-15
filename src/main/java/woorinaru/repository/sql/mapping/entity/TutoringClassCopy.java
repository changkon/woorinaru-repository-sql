package woorinaru.repository.sql.mapping.entity;

import woorinaru.repository.sql.entity.management.administration.TutoringClass;

public class TutoringClassCopy implements Copy<TutoringClass> {

    private WooriClassCopy wooriClassCopy;

    public TutoringClassCopy() {
        this(new WooriClassCopy());
    }

    public TutoringClassCopy(WooriClassCopy wooriClassCopy) {
        this.wooriClassCopy = wooriClassCopy;
    }

    @Override
    public void copy(TutoringClass src, TutoringClass dest) {
        wooriClassCopy.copy(src, dest);
    }
}
