package com.maxron.domain.interactor;

import com.maxron.domain.model.RepoInfo;
import com.maxron.domain.repository.UserRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import io.reactivex.subscribers.TestSubscriber;

public class ListUserRepositoriesUseCaseTest {

    private UserRepository repository;
    private ListUserRepositoriesUseCase listUserRepositoriesUseCase;
    private TestSubscriber<List<RepoInfo>> testSubscriber;

    @Before
    public void setUp() throws Exception {
        repository = Mockito.mock(UserRepository.class);
        listUserRepositoriesUseCase = new ListUserRepositoriesUseCase(repository);
        testSubscriber = new TestSubscriber<>();
    }

    @Test
    public void shouldListUserRepositoriesByUser() {

    }
}