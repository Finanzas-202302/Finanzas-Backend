package com.upc.Finanzas.service;

import com.upc.Finanzas.model.User;

import java.util.List;

public interface UserService {
    public abstract User create(User user);
    public abstract User getById(Long userId);
    public abstract List<User> getAll();
    public abstract User update(User user);
    public abstract void delete(Long userId);
}
