package woorinaru.repository.sql.dao.impl;

import woorinaru.core.dao.spi.AdminDao;
import woorinaru.core.model.user.Admin;

import java.util.List;
import java.util.Optional;

public class AdminDaoImpl implements AdminDao {
    @Override
    public void create(Admin admin) {

    }

    @Override
    public Optional<Admin> get(int i) {
        return Optional.empty();
    }

    @Override
    public void delete(Admin admin) {

    }

    @Override
    public void modify(Admin admin) {

    }

    @Override
    public List<Admin> getAll() {
        return null;
    }
}
