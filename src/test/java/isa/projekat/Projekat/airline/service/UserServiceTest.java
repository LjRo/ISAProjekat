package isa.projekat.Projekat.airline.service;

import isa.projekat.Projekat.model.user.User;
import isa.projekat.Projekat.model.user.UserData;
import isa.projekat.Projekat.repository.UserRepository;
import isa.projekat.Projekat.service.user_auth.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Optional;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    public static final Long DB_ID = 1L;
    public static final Long DB_ID_2 = 2L;

    @Mock
    private User userMock;

    @Mock
    private User userMock2;

    @Mock
    private UserRepository userRepositoryMock;

    @InjectMocks
    private UserService userService;

    @Test
    public void editUserTestPass() {
        when(userRepositoryMock.findByUsername("pera@peric.com")).thenReturn(userMock);

        UserData ud = new UserData();
        ud.setFirstName("Ime");
        ud.setEmail("pera@peric.com");
        ud.setLastName("Prezime");
        ud.setAddress("Adr");
        ud.setPassword(null);

        assertTrue(userService.updateUserData(ud));
    }

    @Test
    public void editUserTestFail() {
        when(userRepositoryMock.findByUsername("pera@peric.com")).thenReturn(null);

        UserData ud = new UserData();
        ud.setPassword(null);

        assertFalse(userService.updateUserData(ud));
    }

    @Test
    public void addFriendRequestTestPass() {
        when(userRepositoryMock.findByUsername("pera@peric.com")).thenReturn(userMock);
        when(userRepositoryMock.findById(DB_ID)).thenReturn(Optional.of(userMock2));

        assertTrue(userService.addFriendRequest("pera@peric.com",DB_ID));
    }

    @Test
    public void addFriendRequestTestHasFriend() {
        when(userRepositoryMock.findByUsername("pera@peric.com")).thenReturn(userMock);
        when(userRepositoryMock.findById(DB_ID)).thenReturn(Optional.of(userMock2));
        when(userMock2.getFriends()).thenReturn(Arrays.asList(userMock));

        assertFalse(userService.addFriendRequest("pera@peric.com",DB_ID));
    }

    @Test
    public void addFriendRequestTestHasRequest() {
        when(userRepositoryMock.findByUsername("pera@peric.com")).thenReturn(userMock);
        when(userRepositoryMock.findById(DB_ID)).thenReturn(Optional.of(userMock2));
        when(userMock.getFriendRequests()).thenReturn(Arrays.asList(userMock2));

        assertFalse(userService.addFriendRequest("pera@peric.com",DB_ID));
    }

    @Test
    public void getUserDataTest() {
        when(userRepositoryMock.findById(DB_ID)).thenReturn(Optional.of(userMock));
        when(userMock.getFirstName()).thenReturn("Pera");
        assertEquals(userService.getUserData(DB_ID).getFirstName(),"Pera");
    }

}
