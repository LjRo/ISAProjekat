package isa.projekat.Projekat.controller.user;

import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.model.user.UserData;
import isa.projekat.Projekat.security.TokenUtils;
import isa.projekat.Projekat.service.user_auth.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private TokenUtils jwtTokenUtils;
    @Autowired
    private UserService userService;

    @PutMapping(value = "api/user/updateInfo", produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAnyRole('USER','ROLE_ADMIN','ROLE_ADMIN_AIRLINE','ROLE_ADMIN_HOTEL','ROLE_ADMIN_RENT')")
    public void updateInfo(@RequestBody UserData ud, HttpServletRequest req){
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        ud.setEmail(email);
        userService.updateUserData(ud);
    }

    @GetMapping(value = "api/user/profile",produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAnyRole('USER','ROLE_ADMIN','ROLE_ADMIN_AIRLINE','ROLE_ADMIN_HOTEL','ROLE_ADMIN_RENT')")
    public UserData getProfileData(HttpServletRequest req) {
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        System.out.println("GET DATA");
        System.out.println(email);
        return userService.getProfileData(email);
    }

    @GetMapping(value = "api/user/userType", produces = {MediaType.APPLICATION_JSON_VALUE})
    public int getUserType(HttpServletRequest req) {
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        User user = userService.findByUsername(email);
        if(user == null)
            return -1;
        return user.getType();
    }

    @GetMapping(value = "api/user/findAllFriends",produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public List<UserData> findAllFriends(HttpServletRequest req) {
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        return userService.findAllFriends(email);
    }

    @GetMapping(value = "api/user/findAllUsers", produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public List<User> findAllNormalusers(HttpServletRequest req) {
        return userService.getAllNormalUsers();
    }


    @GetMapping(value = "api/user/findSpecificFriends", produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public List<User> findAllFriends(@RequestBody String search, HttpServletRequest req) {
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        return userService.findSpecificFriends(email,search);
    }

    @GetMapping(value = "api/user/findAllFriendRequests", produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public List<UserData> findAllFriendRequests(HttpServletRequest req) {
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        return userService.findAllFriendRequests(email);
    }

    @GetMapping(value = "api/user/isEnabled", produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_AIRLINE','ROLE_ADMIN_HOTEL','ROLE_ADMIN_RENT')")
    public Boolean isEnabled(HttpServletRequest req) {
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);

        return userService.isEnabled(email);
    }

    @PostMapping(value = "api/user/sendFriendRequest", produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public void sendFriendRequest(@RequestBody UserData ud, HttpServletRequest req){
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        userService.addFriendRequest(email, ud.getId());
    }

    @PostMapping(value = "api/user/acceptFriendRequest", produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public void acceptFriendRequest(@RequestBody UserData ud, HttpServletRequest req){
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        userService.confirmRequest(email, ud.getId());

    }

    @PostMapping(value = "api/user/changePassword", produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_AIRLINE','ROLE_ADMIN_HOTEL','ROLE_ADMIN_RENT')")
    public void changePassword(@RequestBody UserData ud, HttpServletRequest req){
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);

        userService.changePassword(ud,email);

    }

    @PostMapping(value = "api/user/denyFriendRequest", produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public void denyFriendRequest(@RequestBody UserData ud, HttpServletRequest req){
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        userService.denyRequest(email, ud.getId());

    }

    @PostMapping(value = "api/user/removeFriend", produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public void removeFriend(@RequestBody UserData ud, HttpServletRequest req){
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        userService.removeFriend(email, ud.getId());

    }

    @GetMapping(value = "api/user/{id}/friendStatus", produces = {MediaType.TEXT_PLAIN_VALUE})
    @PreAuthorize("hasRole('USER')")
    public String getUserFriendStatus(@PathVariable Long id, HttpServletRequest req){

        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);

        return userService.getFriendStatus(email,id);
    }
    @GetMapping(value = "api/user/{id}/friends", produces = {MediaType.APPLICATION_JSON_VALUE})
    @PermitAll
    public List<UserData> getUserFriends(@PathVariable Long id, HttpServletRequest req){
        return userService.findAllFriendsById(id);
    }

    @GetMapping(value = "api/user/{id}/profile", produces = {MediaType.APPLICATION_JSON_VALUE})
    @PermitAll
    public UserData getUserData(@PathVariable Long id, HttpServletRequest req){

        return userService.getUserData(id);
    }

}
