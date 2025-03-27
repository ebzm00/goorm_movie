package com.shakkib.netflixclone.services.impl;

import com.shakkib.netflixclone.dtoes.UserDTO;
import com.shakkib.netflixclone.entity.User;
import com.shakkib.netflixclone.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
 
    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // getAllUsers() 메소드에 대한 테스트 - 모든 사용자 조회
    @Test
    @DisplayName("모든 사용자 조회")
    public void testGetAllUsers_All() {
        // given
        User user1 = mock(User.class);
        when(user1.getEmail()).thenReturn("test1@example.com");

        User user2 = mock(User.class);
        when(user2.getEmail()).thenReturn("test2@example.com");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));


        //when
        List<UserDTO> result = userService.getAllUsers(null);

        //then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getEmail()).isEqualTo("test1@example.com");
        assertThat(result.get(1).getEmail()).isEqualTo("test2@example.com");
        verify(userRepository, times(1)).findAll();
    }

    // getAllUsers() 메소드에 대한 테스트 - 활성 사용자 조회
    @Test
    @DisplayName("활성 사용자 조회")
    public void testGetAllUsers_Active() {
        //given
        User activeUser = new User(1L,"active@example.com","ActiveUser",LocalDateTime.now(),false);
        when(userRepository.findByDeleteFlag(false)).thenReturn(Arrays.asList(activeUser));

        //when
        List<UserDTO> result = userService.getAllUsers(true);

        //then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNickname()).isEqualTo("ActiveUser");
        assertThat(result.get(0).getEmail()).isEqualTo("active@example.com");

        verify(userRepository, times(1)).findByDeleteFlag(false);
    }

    // getAllUsers() 메소드에 대한 테스트 - 비활성 사용자 조회
    @Test
    @DisplayName("비활성 사용자 조회")
    public void testGetAllUsers_Inactive() {
        User inactiveUser = new User(2L, "inactive@example.com", "InactiveUser", LocalDateTime.now(), true);
        when(userRepository.findByDeleteFlag(true)).thenReturn(Arrays.asList(inactiveUser));

        List<UserDTO> result = userService.getAllUsers(false);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNickname()).isEqualTo("InactiveUser");
        verify(userRepository, times(1)).findByDeleteFlag(true);
    }
}
