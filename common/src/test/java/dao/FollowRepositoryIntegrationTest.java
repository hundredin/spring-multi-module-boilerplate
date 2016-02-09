package dao;

import com.spring.boilerplate.common.config.CommonConfig;
import com.spring.boilerplate.common.config.PersistenceConfig;
import com.spring.boilerplate.common.dao.FollowRepository;
import com.spring.boilerplate.common.dao.UserRepository;
import com.spring.boilerplate.common.domain.Follow;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CommonConfig.class, PersistenceConfig.class})
@Transactional
public class FollowRepositoryIntegrationTest {
    @Autowired
    FollowRepository followRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    public void test_insert() throws Exception {
        // given
        Follow follow = buildFollow(1, 2);

        // when
        followRepository.save(follow);

        // then
        assertThat(follow.getFollowId(), is(notNullValue()));
    }

    @Test
    public void test_find_user_follows() throws Exception {
        // given
        int userId = 1;
        Follow follow1 = buildFollow(userId, 2);
        Follow follow2 = buildFollow(userId, 3);
        Follow follow3 = buildFollow(userId, 4);

        // when
        followRepository.save(follow1);
        followRepository.save(follow2);
        followRepository.save(follow3);

        // then
        List<Follow> follows = followRepository.findUserFollow(userId);
        assertTrue(follows.contains(follow1));
        assertTrue(follows.contains(follow2));
        assertTrue(follows.contains(follow3));
    }

    private Follow buildFollow(Integer followerId, Integer followingId) {
        Follow follow = new Follow();
        follow.setFollowerId(followerId);
        follow.setFollowingId(followingId);
        follow.setFollowedAt(DateTime.now().toDate());
        return follow;
    }
}
