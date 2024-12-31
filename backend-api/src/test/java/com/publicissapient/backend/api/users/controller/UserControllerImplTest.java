package com.publicissapient.backend.api.users.controller;

import com.publicissapient.backend.api.user.controller.impl.UserControllerImpl;
import com.publicissapient.backend.api.user.dto.UserResponseDto;
import com.publicissapient.backend.api.user.model.*;
import com.publicissapient.backend.api.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import com.publicissapient.backend.api.common.LoadResult;
import com.publicissapient.backend.api.user.dto.LoadUsersResponseDto;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerImplTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserControllerImpl userController;

    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        user1 = createDummyUser();
        user1.setId(1L);
        user1.setAge(25);
        user1.setRole("ADMIN");

        user2 = createDummyUser();
        user2.setId(2L);
        user2.setAge(30);
        user2.setRole("USER");
    }

    @Test
    void loadUsers_WhenSuccessful_ReturnsOkResponse() {
        LoadResult successResult = new LoadResult(5, "Users loaded successfully");
        when(userService.loadUsers()).thenReturn(successResult);

        ResponseEntity response = userController.loadUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        LoadUsersResponseDto responseBody = (LoadUsersResponseDto) response.getBody();
        assertNotNull(responseBody);
        assertEquals(5, responseBody.getUsersLoaded());
        assertEquals("SUCCESS", responseBody.getStatus());
        assertEquals("Users loaded successfully", responseBody.getMessage());
    }

    @Test
    void loadUsers_WhenFailed_ReturnsInternalServerError() {
        LoadResult failedResult = new LoadResult(0, "Failed to load users");
        when(userService.loadUsers()).thenReturn(failedResult);

        ResponseEntity response = userController.loadUsers();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        LoadUsersResponseDto responseBody = (LoadUsersResponseDto) response.getBody();
        assertNotNull(responseBody);
        assertEquals(0, responseBody.getUsersLoaded());
        assertEquals("FAILED", responseBody.getStatus());
    }

    @Test
    void getAllUsers_ReturnsAllUsers() {
        List<User> userList = Arrays.asList(user1, user2);
        when(userService.getAllUsers()).thenReturn(userList);

        ResponseEntity response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        UserResponseDto responseBody = (UserResponseDto) response.getBody();
        assertNotNull(responseBody);
        assertEquals(2, responseBody.getTotal());
        assertEquals(userList, responseBody.getUsers());
    }

    @Test
    void getAllUsers_WhenEmpty_ReturnsEmptyList() {
        when(userService.getAllUsers()).thenReturn(Collections.emptyList());

        ResponseEntity response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        UserResponseDto responseBody = (UserResponseDto) response.getBody();
        assertNotNull(responseBody);
        assertEquals(0, responseBody.getTotal());
        assertTrue(responseBody.getUsers().isEmpty());
    }

    @Test
    void getUsersByRole_ReturnsFilteredUsers() {
        List<User> adminUsers = Collections.singletonList(user1);
        when(userService.getUsersByRole("ADMIN")).thenReturn(adminUsers);

        ResponseEntity response = userController.getUsersByRole("ADMIN");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        UserResponseDto responseBody = (UserResponseDto) response.getBody();
        assertNotNull(responseBody);
        assertEquals(1, responseBody.getTotal());
        assertEquals(adminUsers, responseBody.getUsers());
    }

    @Test
    void getUsersSortedByAge_Ascending_ReturnsSortedUsers() {
        List<User> sortedUsers = Arrays.asList(user1, user2);
        when(userService.getUsersSortedByAge(true)).thenReturn(sortedUsers);

        ResponseEntity response = userController.getUsersSortedByAge(true);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        UserResponseDto responseBody = (UserResponseDto) response.getBody();
        assertNotNull(responseBody);
        assertEquals(2, responseBody.getTotal());
        assertEquals(sortedUsers, responseBody.getUsers());
    }

    @Test
    void searchUsers_WhenUserFound_ReturnsUser() {
        when(userService.searchUsers(1L)).thenReturn(Optional.of(user1));

        ResponseEntity<Optional<User>> response = userController.searchUsers(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Optional<User> responseBody = response.getBody();
        assertEquals(user1, responseBody.get());
    }

    @Test
    void searchUsers_WhenUserNotFound_ReturnsEmptyOptional() {
        when(userService.searchUsers(999L)).thenReturn(Optional.empty());

        ResponseEntity<Optional<User>> response = userController.searchUsers(999L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Optional<User> responseBody = response.getBody();
        assertFalse(responseBody.isPresent());
    }

    public static User createDummyUser() {
        Coordinates userCoordinates = new Coordinates();
        userCoordinates.setLat(74.074918);
        userCoordinates.setLng(-25.312703);

        Coordinates companyCoordinates = new Coordinates();
        companyCoordinates.setLat(-67.45208);
        companyCoordinates.setLng(-23.209886);

        Address userAddress = new Address();
        userAddress.setAddress("466 Pine Street");
        userAddress.setCity("San Antonio");
        userAddress.setState("Louisiana");
        userAddress.setStateCode("LA");
        userAddress.setPostalCode("72360");
        userAddress.setCoordinates(userCoordinates);
        userAddress.setCountry("United States");

        Address companyAddress = new Address();
        companyAddress.setAddress("1597 Oak Street");
        companyAddress.setCity("Chicago");
        companyAddress.setState("Florida");
        companyAddress.setStateCode("FL");
        companyAddress.setPostalCode("28100");
        companyAddress.setCoordinates(companyCoordinates);
        companyAddress.setCountry("United States");

        Hair userHair = new Hair();
        userHair.setColor("Purple");
        userHair.setType("Curly");

        Bank userBank = new Bank();
        userBank.setCardExpire("02/25");
        userBank.setCardNumber("7183482484317509");
        userBank.setCardType("Visa");
        userBank.setCurrency("CAD");
        userBank.setIban("CW5U5KS23U7JYD22TVQL7SIH");

        Company userCompany = new Company();
        userCompany.setDepartment("Support");
        userCompany.setName("Gorczany - Gottlieb");
        userCompany.setTitle("Legal Counsel");
        userCompany.setAddress(companyAddress);

        Crypto userCrypto = new Crypto();
        userCrypto.setCoin("Bitcoin");
        userCrypto.setWallet("0xb9fc2fe63b2a6c003f1c324c3bfa53259162181a");
        userCrypto.setNetwork("Ethereum (ERC20)");

        User user = new User();
        user.setId(9L);
        user.setFirstName("Ethan");
        user.setLastName("Martinez");
        user.setMaidenName("");
        user.setAge(33);
        user.setGender("male");
        user.setEmail("ethan.martinez@x.dummyjson.com");
        user.setPhone("+92 933-608-5081");
        user.setUsername("ethanm");
        user.setPassword("ethanmpass");
        user.setBirthDate("1991-2-12");
        user.setImage("https://dummyjson.com/icon/ethanm/128");
        user.setBloodGroup("AB+");
        user.setHeight(159.19);
        user.setWeight(68.81);
        user.setEyeColor("Hazel");
        user.setHair(userHair);
        user.setIp("63.191.127.71");
        user.setAddress(userAddress);
        user.setMacAddress("59:e:9e:e3:29:da");
        user.setUniversity("Syracuse University");
        user.setBank(userBank);
        user.setCompany(userCompany);
        user.setEin("790-434");
        user.setSsn("569-650-348");
        user.setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.69 Safari/537.36");
        user.setCrypto(userCrypto);
        user.setRole("moderator");

        return user;
    }
}