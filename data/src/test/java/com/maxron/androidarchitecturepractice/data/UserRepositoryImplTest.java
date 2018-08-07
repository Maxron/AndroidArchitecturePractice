package com.maxron.androidarchitecturepractice.data;

import com.maxron.androidarchitecturepractice.data.local.RepoDao;
import com.maxron.androidarchitecturepractice.data.local.entity.RepoModel;
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
    @Mock RepoDao repoDao;
    @InjectMocks UserRepositoryImpl userRepository;
    private String user = "user";
    private long repoId = 1234L;
    private String repoName = "SimpleRepoName";

    @Before
    public void setUp() throws Exception {
    }

    private RepoInfo getRepoInfoStub() {
        return new RepoInfo(repoId, repoName);
    }

    private RepoDetail getDummyRepoDetail() {
        return new RepoDetail();
    }

    @Test
    public void testShouldlistUserRepository() {
        List<RepoModel> dummyRepoModels = new ArrayList<>();

        RepoDetail dummyRepoDetail = getDummyRepoDetail();
        List<RepoDetail> dummyRepoDetails = new ArrayList<>();
        dummyRepoDetails.add(dummyRepoDetail);

        RepoInfo repoInfoStub = getRepoInfoStub();

        // Given
        given(repoDao.getAllRepos()).willReturn(dummyRepoModels);
        given(mapper.repodetailToRepoInfo(dummyRepoDetail)).willReturn(repoInfoStub);
        given(githubService.listUserRepository(user)).willReturn(Single.just(dummyRepoDetails));

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
        userRepository = new UserRepositoryImpl(githubService, mapper, repoDao);

        List<RepoModel> emptyRepoModels = new ArrayList<>(0);

        final RepoDetail repoDetailStub = new RepoDetailTestData().stub();
        List<RepoDetail> repoDetailsStub = new ArrayList<>();
        repoDetailsStub.add(repoDetailStub);

        // Given
        given(repoDao.getAllRepos()).willReturn(emptyRepoModels); // Will fetch from Api when local is empty
        given(githubService.listUserRepository(user)).willReturn(Single.just(repoDetailsStub));

        // When
        userRepository.listUserRepository(user)
                .test()
                .assertValue(new Predicate<List<RepoInfo>>() {
                    @Override
                    public boolean test(List<RepoInfo> repoInfos) throws Exception {

                        // Then
                        assertEquals(repoDetailStub.getId(), repoInfos.get(0).id.intValue());
                        assertEquals(repoDetailStub.getName(), repoInfos.get(0).name);

                        return repoInfos.get(0).id.intValue() == repoDetailStub.getId() &&
                                repoInfos.get(0).name.equals(repoDetailStub.getName());
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