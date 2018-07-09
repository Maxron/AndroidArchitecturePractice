package com.maxron.domain.interactor;

import com.maxron.domain.model.RepoInfo;
import com.maxron.domain.repository.UserRepository;

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
public class ListUserRepositoriesUseCaseTest {

    @Mock UserRepository repository;
    @InjectMocks ListUserRepositoriesUseCase listUserRepositoriesUseCase;
    private String username;

    @Before
    public void setUp() throws Exception {
        username = "alex";
    }

    @Test
    public void testShouldListUserRepositoryByUserName() {
        /**
         * BDD style : Given-when-then
         */
        List<RepoInfo> resultRepos = new ArrayList<>(1);
        resultRepos.add(new RepoInfo(1234L, "firstRepo"));

        // Given
        given(repository.listUserRepository(username)).willReturn(Single.just(resultRepos));

        // When
        listUserRepositoriesUseCase.execute(username)
                .test()
                .assertValue(resultRepos)
                .assertComplete();

        // Then
        verify(repository).listUserRepository(username);
    }
}