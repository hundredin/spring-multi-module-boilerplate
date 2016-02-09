package dao;

import com.google.common.collect.Lists;
import com.spring.boilerplate.common.config.CommonConfig;
import com.spring.boilerplate.common.config.PersistenceConfig;
import com.spring.boilerplate.common.dao.LinkRepository;
import com.spring.boilerplate.common.dao.UserRepository;
import com.spring.boilerplate.common.domain.Link;
import com.spring.boilerplate.common.domain.User;
import com.spring.boilerplate.common.util.Hasher;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CommonConfig.class, PersistenceConfig.class})
@Transactional
public class UserRepositoryIntegrationTest {
    @Autowired
    UserRepository userRepository;

    @Autowired
    LinkRepository linkRepository;

    @Test
    public void test_insert() throws Exception {
        // given
        String username = "test_username";
        String email = "test@pling.com";
        String password = "123456";
        String avatarUrl = "http://avatarUrl";
        String name = "test_name";
        Date createdAt = DateTime.now().toDate();
        Date updatedAt = DateTime.now().toDate();

        User user = buildUser(username, email, password, avatarUrl, name, createdAt, updatedAt);

        // when
        userRepository.save(user);

        User foundUser = userRepository.findUserByUsername(username);

        // then
        assertThat(foundUser.getUserId(), is(notNullValue()));
        assertThat(foundUser.getUsername(), is(username));
        assertThat(foundUser.getCreatedAt(), is(createdAt));
        assertThat(foundUser.getEmail(), is(email));
        assertThat(foundUser.getPassword(), is(password));
        assertThat(foundUser.getName(), is(name));
        assertThat(foundUser.getAvatarUrl(), is(avatarUrl));
    }

    @Test
    public void test_get_link_list_through_user() throws Exception {
        // given
        Link link1 = buildLink(Hasher.getHashId(), "test", 1, "http://pling.com", "this is pling", 1, 1, 1);
        Link link2 = buildLink(Hasher.getHashId(), "test", 2, "http://google.com", "this is google", 1, 1, 1);

        List<Link> newLinks = Lists.newArrayList(link1, link2);

        User newUser = new User();
        newUser.setUsername("test_username");
        newUser.setEmail("test_email");
        newUser.setPassword("test_password");
        newUser.setCreatedAt(DateTime.now().toDate());

        newUser.setLinks(newLinks);
        userRepository.save(newUser);

        // when
        User user = userRepository.find(newUser.getUserId());
        List<Link> links = user.getLinks();

        // then
        assertThat(links.size(), is(not(0)));
        assertTrue(links.contains(link1));
        assertTrue(links.contains(link2));
    }

    private User buildUser(String username, String email, String password, String avatarUrl, String name, Date createdAt, Date updatedAt) {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setAvatarUrl(avatarUrl);
        user.setName(name);
        user.setCreatedAt(createdAt);
        user.setUpdatedAt(updatedAt);
        return user;
    }

    private Link buildLink(String hashId, String title, int categoryId, String url, String description, int view, int like, int hate) {
        Link link = new Link();
        link.setTitle(title);
        link.setCategoryId(categoryId);
        link.setHashId(hashId);
        link.setUrl(url);
        link.setDescription(description);
        link.setView(view);
        link.setLike(like);
        link.setHate(hate);
        return link;
    }
}
