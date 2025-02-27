package com.blogging.bloggingApp.servicesimpl;

import com.blogging.bloggingApp.Exceptions.ResourceNotFoundException;
import com.blogging.bloggingApp.Repositories.UserRepo;
import com.blogging.bloggingApp.entities.User;
import com.blogging.bloggingApp.payloads.UserDto;
import com.blogging.bloggingApp.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceimpl implements UserService {
 //while saving to database UserDto needs to be converted to User class
    //we will use model mapper for that purpose
    @Autowired
    private UserRepo userRepo;
    //as we know UserRepo is interface so in runtime its object named proxy is created  and
    //injected into this reference

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userdto) {
        User user=this.dtoToEntity(userdto);
        User savedUser = this.userRepo.save(user);
        UserDto userDto=this.entityToDto(savedUser);
        return userDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        User updatedUser=this.userRepo.save(user);
        return  this.entityToDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return this.entityToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> allUsers = this.userRepo.findAll();
        List<UserDto> collect = allUsers.stream().map(user -> this.entityToDto(user)).collect(Collectors.toList());
        return collect;
    }

    @Override
    public boolean deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        this.userRepo.delete(user);
        return true;
    }
   //we need not do below mapping manually using model mapper we can achieve it
    private User dtoToEntity(UserDto user){
        return this.modelMapper.map(user,User.class);
    }

    private UserDto entityToDto(User user){
        return this.modelMapper.map(user,UserDto.class);
    }
}
