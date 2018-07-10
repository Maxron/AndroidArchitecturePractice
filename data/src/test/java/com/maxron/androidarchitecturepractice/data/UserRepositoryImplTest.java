package com.maxron.androidarchitecturepractice.data;

import com.maxron.androidarchitecturepractice.data.remote.GithubServiceApi;
import com.maxron.domain.model.RepoInfo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryImplTest {

    @Mock GithubServiceApi githubService;
    @InjectMocks UserRepositoryImpl userRepository;
    private String user;

    @Before
    public void setUp() throws Exception {
        user = "user";
    }

    @Test
    public void testShouldlistUserRepository() {
        List<RepoInfo> response = new ArrayList<>();

        // Given
        given(githubService.listUserRepository(user)).willReturn(Single.just(response));

        // When
        userRepository.listUserRepository(user)
                .test()
                .assertValue(response)  // Then
                .assertComplete();      // Then

        // Then
        verify(githubService).listUserRepository(user);
    }
}