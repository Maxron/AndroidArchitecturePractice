package com.maxron.domain.interactor;

import com.maxron.domain.repository.UserRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ListUserRepositoriesUseCaseTest {

    private UserRepository repository;
    private ListUserRepositoriesUseCase listUserRepositoriesUseCase;

    @Before
    public void setUp() throws Exception {
        repository = Mockito.mock(UserRepository.class);
        listUserRepositoriesUseCase = new ListUserRepositoriesUseCase(repository);
    }

    @Test
    public void shouldListUserRepositoriesByUser() {

    }
}