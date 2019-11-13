package com.example.demo.Services;

import com.example.demo.Models.User;
import com.example.demo.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    private  final UserRepository userRepository;

    public UserServiceImp(UserRepository userRepository){ this.userRepository = userRepository; }

    @Override
    public List<User> findAll(){return  userRepository.findAll();}

    @Override
    public  User save(User user){return userRepository.save(user);}

    @Override
    public User findById(long id){return userRepository.findById(id);}

    @Override
    public User update(User user){return userRepository.save(user);}

    @Override
    public void delete(long id)throws Exception{userRepository.deleteById(id);}

    @Override
    public User findByEmailAndId(String email, String password){
        return null;
    }
}
