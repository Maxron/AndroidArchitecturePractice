package com.maxron.androidarchitecturepractice.data;

import com.maxron.androidarchitecturepractice.data.mapper.RepoModelMapper;
import com.maxron.androidarchitecturepractice.data.remote.GithubServiceApi;
import com.maxron.androidarchitecturepractice.data.remote.model.RepoDetail;
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
    @Mock RepoModelMapper mapper;
    @InjectMocks UserRepositoryImpl userRepository;
    private String user;

    @Before
    public void setUp() throws Exception {
        user = "user";
    }

    @Test
    public void testShouldlistUserRepository() {
        RepoDetail mRepoDetail = new RepoDetail();
        List<RepoDetail> response = new ArrayList<>();
        response.add(mRepoDetail);

        String repoName = "javaSample";
        Long repoId = 1234L;
        RepoInfo mRepoInfo = new RepoInfo(repoId, repoName);
        List<RepoInfo> repos = new ArrayList<>();
        repos.add(mRepoInfo);

        // Given
        given(mapper.repodetailToRepoInfo(mRepoDetail)).willReturn(mRepoInfo);
        given(githubService.listUserRepository(user)).willReturn(Single.just(response));

        // When
        userRepository.listUserRepository(user)
                .test()
                .assertComplete();

        // Then
        // Verify githubService has called.
        verify(githubService).listUserRepository(user);
        // Verify mapper has called.
        verify(mapper).repodetailToRepoInfo(mRepoDetail);
    }
}