package com.example.blogapplicationapi.controller;

import com.example.blogapplicationapi.POJO.LoginPojo;
import com.example.blogapplicationapi.model.Person;
import com.example.blogapplicationapi.repository.UserRepository;
import com.example.blogapplicationapi.service.serviceImplementation.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class UserController {

    private final UserServiceImp userServiceImp;

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserServiceImp userServiceImp, UserRepository userRepository) {
        this.userServiceImp = userServiceImp;
        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Person person, HttpSession session){

        if(userServiceImp.createUser(person)){
            session.setAttribute("id", userRepository.getById(person.getUser_id()));
            return new ResponseEntity<>("User has been added", HttpStatus.OK);
        }
        return new ResponseEntity<>("User already exists",HttpStatus.BAD_REQUEST);
    }

    @PostMapping("login")
    public ResponseEntity<?> loginProcess(HttpServletRequest request, @RequestBody LoginPojo login){

        Boolean exists = userRepository.existsByEmail(login.getEmail());
        String password1 = userServiceImp.getPersonByEmail(login.getEmail()).getPassword();

        if(exists && password1.equals(login.getPassword())){
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("id", userRepository.getPersonByEmail(login.getEmail()).getUser_id());
            httpSession.setAttribute("username", userRepository.getPersonByEmail(login.getEmail()).getUsername());
            return new ResponseEntity<>("Welcome " + userRepository.getPersonByEmail(login.getEmail()).getUsername(),HttpStatus.OK);
        }
        return new ResponseEntity<>("Wrong credentials", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/addPerson/{friend_id}")
    public ResponseEntity<?> addFriend(HttpSession session, @PathVariable Long friend_id ){
        Long id = (Long) session.getAttribute("id");
        if(id == friend_id){
            return new ResponseEntity<>("You cannot add yourself", HttpStatus.BAD_REQUEST);
        }
        userServiceImp.addFriend(id,friend_id);
        return new ResponseEntity<>("Successful", HttpStatus.OK);
    }

    @GetMapping("/listOfFriends")
    public ResponseEntity<?> listOfFriends(HttpSession session){
        Long id = (Long) session.getAttribute("id");
        List<Person> friends = userServiceImp.getFriends(id);
        return new ResponseEntity<>(friends, HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser")
    public  ResponseEntity<?> deleteUser(HttpSession session){
        Long id = (Long) session.getAttribute("id");
        userServiceImp.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> home( HttpSession session){
        Long id = (Long) session.getAttribute("id");
        Person person = userRepository.getById(id);
        session.invalidate();
        return new ResponseEntity<>(person.getUsername() + " is" + " logged out", HttpStatus.OK);
    }


}
