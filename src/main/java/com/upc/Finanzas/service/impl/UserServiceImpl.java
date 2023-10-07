package com.upc.Finanzas.service.impl;

import com.upc.Finanzas.model.User;
import com.upc.Finanzas.repository.UserRepository;
import com.upc.Finanzas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public User create(User user){
        //validaciones
        return userRepository.save(user);
    }
    @Override
    public  User getById(Long userId){
        if(userRepository.existsById(userId)) return userRepository.getById(userId);
        else return null;
    }
    @Override
    public List<User> getAll(){
        return userRepository.findAll();
    }
    @Override
    public User update(User user){
        if(userRepository.existsById(user.getId())) return userRepository.save(user);
        else return null;
    }
    @Override
    public void delete(Long userId){
        userRepository.deleteById(userId);
    }
}
