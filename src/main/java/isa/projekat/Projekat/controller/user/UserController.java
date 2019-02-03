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

    @RequestMapping(value = "api/user/updateInfo", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAnyRole('USER','ROLE_ADMIN','ROLE_ADMIN_AIRLINE','ROLE_ADMIN_HOTEL','ROLE_ADMIN_RENT')")
    public void updateInfo(@RequestBody UserData ud, HttpServletRequest req){
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        ud.setEmail(email);
        userService.updateUserData(ud);
    }

    @RequestMapping(value = "api/user/profile", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAnyRole('USER','ROLE_ADMIN','ROLE_ADMIN_AIRLINE','ROLE_ADMIN_HOTEL','ROLE_ADMIN_RENT')")
    public UserData getProfileData(HttpServletRequest req) {
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        System.out.println("GET DATA");
        System.out.println(email);
        return userService.getProfileData(email);
    }

    @RequestMapping(value = "api/user/userType", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public int getUserType(HttpServletRequest req) {
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        User user = userService.findByUsername(email);
        if(user == null)
            return -1;
        return user.getType();
    }

    @RequestMapping(value = "api/user/findAllFriends", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public List<UserData> findAllFriends(HttpServletRequest req) {
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
    public List<UserData> findAllFriendRequests(HttpServletRequest req) {
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        return userService.findAllFriendRequests(email);
    }

    @RequestMapping(value = "api/user/isEnabled", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_AIRLINE','ROLE_ADMIN_HOTEL','ROLE_ADMIN_RENT')")
    public Boolean isEnabled(HttpServletRequest req) {
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);

        return userService.isEnabled(email);
    }

    @RequestMapping(value = "api/user/sendFriendRequest", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public void sendFriendRequest(@RequestBody UserData ud, HttpServletRequest req){
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        userService.addFriendRequest(email, ud.getId());
    }

    @RequestMapping(value = "api/user/acceptFriendRequest", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public void acceptFriendRequest(@RequestBody UserData ud, HttpServletRequest req){
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        userService.confirmRequest(email, ud.getId());

    }

    @RequestMapping(value = "api/user/changePassword", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasAnyRole('ROLE_ADMIN_AIRLINE','ROLE_ADMIN_HOTEL','ROLE_ADMIN_RENT')")
    public void changePassword(@RequestBody UserData ud, HttpServletRequest req){
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);

        userService.changePassword(ud,email);

    }

    @RequestMapping(value = "api/user/denyFriendRequest", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public void denyFriendRequest(@RequestBody UserData ud, HttpServletRequest req){
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        userService.denyRequest(email, ud.getId());

    }

    @RequestMapping(value = "api/user/removeFriend", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PreAuthorize("hasRole('USER')")
    public void removeFriend(@RequestBody UserData ud, HttpServletRequest req){
        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);
        userService.removeFriend(email, ud.getId());

    }

    @RequestMapping(value = "api/user/{id}/friendStatus", method = RequestMethod.GET, produces = {MediaType.TEXT_PLAIN_VALUE})
    @PreAuthorize("hasRole('USER')")
    public String getUserFriendStatus(@PathVariable Long id, HttpServletRequest req){

        String authToken = jwtTokenUtils.getToken(req);
        String email = jwtTokenUtils.getUsernameFromToken(authToken);

        return userService.getFriendStatus(email,id);
    }
    @RequestMapping(value = "api/user/{id}/friends", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PermitAll
    public List<UserData> getUserFriends(@PathVariable Long id, HttpServletRequest req){
        return userService.findAllFriendsById(id);
    }

    @RequestMapping(value = "api/user/{id}/profile", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @PermitAll
    public UserData getUserData(@PathVariable Long id, HttpServletRequest req){

        return userService.getUserData(id);
    }

}
