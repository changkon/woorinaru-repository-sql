package woorinaru.repository.sql.mapping.entity;

import woorinaru.repository.sql.entity.management.administration.IntermediateClass;

public class IntermediateClassCopy implements Copy<IntermediateClass> {

    private WooriClassCopy wooriClassCopy;

    public IntermediateClassCopy() {
        this(new WooriClassCopy());
    }

    public IntermediateClassCopy(WooriClassCopy wooriClassCopy) {
        this.wooriClassCopy = wooriClassCopy;
    }

    @Override
    public void copy(IntermediateClass src, IntermediateClass dest) {
        this.wooriClassCopy.copy(src, dest);
    }
}
