package com.maxron.domain.interactor;

import com.maxron.domain.model.RepoInfo;
import com.maxron.domain.repository.UserRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ListUserRepositoriesUseCaseTest {

    @Mock UserRepository repository;
    private ListUserRepositoriesUseCase listUserRepositoriesUseCase;
    private String username;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        listUserRepositoriesUseCase = new ListUserRepositoriesUseCase(repository);
        username = "alex";
    }

    @Test
    public void testShouldListUserRepositoryByUserName() {
        List<RepoInfo> resultRepos = new ArrayList<>(1);
        resultRepos.add(new RepoInfo(1234L, "firstRepo"));

        when(repository.listUserRepository(username))
                .thenReturn(Single.just(resultRepos));

        listUserRepositoriesUseCase.execute(username)
                .test()
                .assertValue(resultRepos)
                .assertComplete();

        verify(repository).listUserRepository(username);
    }
}