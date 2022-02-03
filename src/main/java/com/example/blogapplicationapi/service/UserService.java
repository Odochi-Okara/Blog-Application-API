package com.example.blogapplicationapi.service;

import com.example.blogapplicationapi.model.Person;

import java.util.List;

public interface UserService {
    public boolean createUser(Person person);

    public void delete(Long user_id);

    public List<Person> findPersonsByEmail(String email);

    public Person getPersonByEmail(String email);

    public String addFriend(Long user_id, Long friend_id);

    public List<Person> getFriends(Long user_id);

    public Person getByResetPasswordToken(String token);

    public void updatePassword(Person person, String newPassword);

    public void updateResetPasswordToken(String token, String email) throws Exception;
}