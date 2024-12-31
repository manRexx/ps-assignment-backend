package com.publicissapient.backend.api.users.service;

import com.publicissapient.backend.api.common.LoadResult;
import com.publicissapient.backend.api.user.dto.ExternalApiResponse;
import com.publicissapient.backend.api.user.model.*;
import com.publicissapient.backend.api.user.repository.UserRepository;
import com.publicissapient.backend.api.user.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private UserServiceImpl userService;

    private User user1;
    private User user2;
    private String testApiUrl = "http://test-api.com/users";

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

        ReflectionTestUtils.setField(userService, "usersDataUrl", testApiUrl);
    }

    @Test
    void loadUsers_WhenApiCallSuccessful_SavesAndReturnsUsers() {
        List<User> users = Arrays.asList(user1, user2);
        ExternalApiResponse apiResponse = new ExternalApiResponse();
        apiResponse.setUsers(users);
        apiResponse.setTotal(2);

        when(restTemplate.getForObject(testApiUrl, ExternalApiResponse.class))
                .thenReturn(apiResponse);
        when(userRepository.saveAll(users)).thenReturn(users);

        LoadResult result = userService.loadUsers();

        assertEquals(2, result.getDataLoaded());
        assertTrue(result.getMessage().contains("Successfully loaded 2 users"));
        verify(userRepository).saveAll(users);
    }

    @Test
    void loadUsers_WhenApiReturnsNull_ReturnsErrorResult() {
        when(restTemplate.getForObject(testApiUrl, ExternalApiResponse.class))
                .thenReturn(null);

        LoadResult result = userService.loadUsers();

        assertEquals(0, result.getDataLoaded());
        assertEquals("No response from external API", result.getMessage());
        verify(userRepository, never()).saveAll(any());
    }

    @Test
    void loadUsers_WhenApiThrowsException_ReturnsErrorResult() {
        when(restTemplate.getForObject(testApiUrl, ExternalApiResponse.class))
                .thenThrow(new RestClientException("Connection refused"));

        LoadResult result = userService.loadUsers();

        assertEquals(0, result.getDataLoaded());
        assertTrue(result.getMessage().contains("Failed to load users"));
        verify(userRepository, never()).saveAll(any());
    }

    @Test
    void getAllUsers_ReturnsAllUsers() {
        List<User> expectedUsers = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(expectedUsers);

        List<User> actualUsers = userService.getAllUsers();

        assertEquals(expectedUsers, actualUsers);
        verify(userRepository).findAll();
    }

    @Test
    void getAllUsers_WhenNoUsers_ReturnsEmptyList() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<User> users = userService.getAllUsers();

        assertTrue(users.isEmpty());
        verify(userRepository).findAll();
    }

    @Test
    void getUsersByRole_ReturnsFilteredUsers() {
        List<User> adminUsers = Collections.singletonList(user1);
        when(userRepository.findByRole("ADMIN")).thenReturn(adminUsers);

        List<User> result = userService.getUsersByRole("ADMIN");

        assertEquals(adminUsers, result);
        verify(userRepository).findByRole("ADMIN");
    }

    @Test
    void getUsersSortedByAge_WhenAscending_ReturnsSortedUsers() {
        List<User> sortedUsers = Arrays.asList(user1, user2); // user1 is younger
        when(userRepository.findAllByOrderByAgeAsc()).thenReturn(sortedUsers);

        List<User> result = userService.getUsersSortedByAge(true);

        assertEquals(sortedUsers, result);
        verify(userRepository).findAllByOrderByAgeAsc();
        verify(userRepository, never()).findAllByOrderByAgeDesc();
    }

    @Test
    void getUsersSortedByAge_WhenDescending_ReturnsSortedUsers() {
        List<User> sortedUsers = Arrays.asList(user2, user1); // user2 is older
        when(userRepository.findAllByOrderByAgeDesc()).thenReturn(sortedUsers);

        List<User> result = userService.getUsersSortedByAge(false);

        assertEquals(sortedUsers, result);
        verify(userRepository).findAllByOrderByAgeDesc();
        verify(userRepository, never()).findAllByOrderByAgeAsc();
    }

    @Test
    void searchUsers_WhenUserExists_ReturnsUser() {
        when(userRepository.searchUserById(1L)).thenReturn(Optional.of(user1));

        Optional<User> result = userService.searchUsers(1L);

        assertTrue(result.isPresent());
        assertEquals(user1, result.get());
        verify(userRepository).searchUserById(1L);
    }

    @Test
    void searchUsers_WhenUserDoesNotExist_ReturnsEmptyOptional() {
        when(userRepository.searchUserById(999L)).thenReturn(Optional.empty());

        Optional<User> result = userService.searchUsers(999L);

        assertFalse(result.isPresent());
        verify(userRepository).searchUserById(999L);
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
