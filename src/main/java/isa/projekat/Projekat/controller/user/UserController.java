package isa.projekat.Projekat.controller.user;

import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.model.user.UserData;
import isa.projekat.Projekat.security.TokenUtils;
import isa.projekat.Projekat.service.user_auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private TokenUtils jwtTokenUtils;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "api/user/updateInfo", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public void updateInfo(@RequestBody UserData ud, HttpServletRequest req){
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        ud.setEmail(email);
        userService.updateUserData(ud);
    }

    @RequestMapping(value = "api/user/profile", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public UserData getProfileData(HttpServletRequest req) {
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        System.out.println("GET DATA");
        System.out.println(email);
        return userService.getProfileData(email);
    }

    @RequestMapping(value = "api/user/findAllFriends", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public List<User> findAllFriends(HttpServletRequest req) {
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        return userService.findAllFriends(email);
    }

    @RequestMapping(value = "api/user/findSpecificFriends", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public List<User> findAllFriends(@RequestBody String search, HttpServletRequest req) {
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        return userService.findSpecificFriends(email,search);
    }

    @RequestMapping(value = "api/user/findAllFriendRequests", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public List<User> findAllFriendRequests(HttpServletRequest req) {
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        return userService.findAllFriendRequests(email);
    }

    @RequestMapping(value = "api/user/sendFriendRequest", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public void sendFriendRequest(@RequestBody String targetEmail, HttpServletRequest req){
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        userService.addFriendRequest(email, targetEmail);

    }

    @RequestMapping(value = "api/user/acceptFriendRequest", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public void acceptFriendRequest(@RequestBody String targetEmail, HttpServletRequest req){
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        userService.confirmRequest(email, targetEmail);

    }

    @RequestMapping(value = "api/user/denyFriendRequest", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public void denyFriendRequest(@RequestBody String targetEmail, HttpServletRequest req){
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        userService.denyRequest(email, targetEmail);

    }

    @RequestMapping(value = "api/user/removeFriend", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public void removeFriend(@RequestBody String targetEmail, HttpServletRequest req){
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        userService.removeFriend(email, targetEmail);

    }


}
