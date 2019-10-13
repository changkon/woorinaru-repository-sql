package woorinaru.repository.sql.mapping.entity;

import woorinaru.repository.sql.entity.user.User;

public class UserCopy implements Copy<User> {
    @Override
    public void copy(User src, User dest) {
        dest.setId(src.getId());
        dest.setEmail(src.getEmail());
        dest.setFavouriteResources(src.getFavouriteResources());
        dest.setName(src.getName());
        dest.setNationality(src.getNationality());
        dest.setSignUpDateTime(src.getSignUpDateTime());
    }
}
