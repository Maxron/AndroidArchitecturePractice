package com.maxron.androidarchitecturepractice.data;

import com.maxron.androidarchitecturepractice.data.mapper.RepoDetailTestData;
import com.maxron.androidarchitecturepractice.data.mapper.RepoModelMapper;
import com.maxron.androidarchitecturepractice.data.mapper.RepoModelMapperImpl;
import com.maxron.androidarchitecturepractice.data.remote.GithubServiceApi;
import com.maxron.androidarchitecturepractice.data.remote.model.RepoDetail;
import com.maxron.domain.model.RepoInfo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Predicate;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryImplTest {

    @Mock GithubServiceApi githubService;
    @Mock RepoModelMapper mapper;
    @InjectMocks UserRepositoryImpl userRepository;
    private String user;

    @Before
    public void setUp() throws Exception {
        user = "user";
    }

    @Test
    public void testShouldlistUserRepository() {
        RepoDetail dummyRepoDetail = new RepoDetail();
        List<RepoDetail> response = new ArrayList<>();
        response.add(dummyRepoDetail);

        String repoName = "javaSample";
        Long repoId = 1234L;
        RepoInfo dummyRepoInfo = new RepoInfo(repoId, repoName);
        List<RepoInfo> repos = new ArrayList<>();
        repos.add(dummyRepoInfo);

        // Given
        given(mapper.repodetailToRepoInfo(dummyRepoDetail)).willReturn(dummyRepoInfo);
        given(githubService.listUserRepository(user)).willReturn(Single.just(response));

        // When
        userRepository.listUserRepository(user)
                .test()
                .assertComplete();

        // Then
        // Verify githubService has called.
        verify(githubService).listUserRepository(user);
        // Verify mapper has called.
        verify(mapper).repodetailToRepoInfo(dummyRepoDetail);
    }

    @Test
    public void testShouldlistUserRepositoryWithRealMapper() {
        mapper = new RepoModelMapperImpl();
        userRepository = new UserRepositoryImpl(githubService, mapper);

        final RepoDetail repoDetail = new RepoDetailTestData().stub();
        List<RepoDetail> response = new ArrayList<>();
        response.add(repoDetail);

        // Given
        given(githubService.listUserRepository(user)).willReturn(Single.just(response));

        // When
        userRepository.listUserRepository(user)
                .test()
                .assertValue(new Predicate<List<RepoInfo>>() {
                    @Override
                    public boolean test(List<RepoInfo> repoInfos) throws Exception {

                        // Then
                        assertEquals(repoDetail.getId(), repoInfos.get(0).id.intValue());
                        assertEquals(repoDetail.getName(), repoInfos.get(0).name);

                        return repoInfos.get(0).id.intValue() == repoDetail.getId() &&
                                repoInfos.get(0).name.equals(repoDetail.getName());
                    }
                })
                .assertComplete();

        // Then
        // Verify githubService has called.
        verify(githubService).listUserRepository(user);
    }

    @After
    public void tearDown() {
        userRepository = null;
    }
}