package dao;

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

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CommonConfig.class, PersistenceConfig.class})
@Transactional
public class LinkRepositoryIntegrationTest {
    @Autowired
    LinkRepository linkRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    public void test_insert() throws Exception {
        // given
        Link link = buildLink(Hasher.getHashId(), "test", 1, "http://google.com", "구글입니다.", 10, 10, 5);

        // when
        linkRepository.save(link);

        // then
        assertThat(link.getLinkId(), is(notNullValue()));
        assertThat(link.getHashId(), is(notNullValue()));
    }

    @Test
    public void test_update() throws Exception {
        // given
        Link link = buildLink(Hasher.getHashId(), "test", 1, "http://google.com", "구글입니다.", 10, 10, 5);
        linkRepository.save(link);

        String newTitle = "google";
        link.setTitle(newTitle);

        // when
        linkRepository.save(link);

        // then
        Link updatedLink = linkRepository.find(link.getLinkId());
        assertThat(updatedLink.getTitle(), is(newTitle));
    }

    @Test
    public void test_delete() throws Exception {
        // given
        Link link = buildLink(Hasher.getHashId(), "test", 1, "http://pling.com", "this is pling", 1, 1, 1);
        linkRepository.save(link);

        // when
        linkRepository.delete(link);

        // then
        Link deletedLink = linkRepository.find(link.getLinkId());

        assertThat(deletedLink, is(nullValue()));
    }

    @Test
    public void test_link_find() throws Exception {
        // given
        Link newLink = buildLink(Hasher.getHashId(), "test", 1, "http://pling.com", "this is pling", 1, 1, 1);

        User newUser = new User();
        newUser.setUsername("test_username");
        newUser.setEmail("test_email");
        newUser.setPassword("test_password");
        newUser.setCreatedAt(DateTime.now().toDate());
        userRepository.save(newUser);

        newLink.setUser(newUser);

        linkRepository.save(newLink);

        Link link = linkRepository.find(newLink.getLinkId());
        User user = link.getUser();

        assertThat(link.getLinkId(), is(newLink.getLinkId()));
        assertThat(link.getTitle(), is(newLink.getTitle()));
        assertThat(user, is(notNullValue()));
        assertThat(user.getUsername(), is("test_username"));
    }

    @Test
    public void test_link_find_by_date() throws Exception {
        DateTime toDate = DateTime.now();
        DateTime fromDate = DateTime.now().minusYears(1);

        List<Link> links = linkRepository.findByDate(fromDate.toDate(), toDate.toDate());

        assertThat(links.size(), is(not(0)));
    }

    @Test
    public void test_links_popular() throws Exception {
        List<Link> links = linkRepository.findBiggerThan(10, 10);

        assertThat(links.size(), is(not(0)));
    }

    @Test
    public void test_link_find_by_hashId() throws Exception {
        // given
        String hashId = Hasher.getHashId();
        Link link = buildLink(hashId, "test", 1, "http://testurl", "description", 1, 1, 1);

        linkRepository.save(link);

        // when
        Link foundLink = linkRepository.findByHashId(hashId);

        // then
        assertThat(foundLink.getHashId(), is(hashId));
    }

    @Test
    public void test_get_user_from_link() throws Exception {
        Link link1 = buildLink(Hasher.getHashId(), "test", 1, "http://pling.com", "this is pling", 1, 1, 1);
        Link link2 = buildLink(Hasher.getHashId(), "test", 2, "http://google.com", "this is google", 1, 1, 1);

        User newUser = new User();
        newUser.setUsername("test_username");
        newUser.setEmail("test_email");
        newUser.setPassword("test_password");
        newUser.setCreatedAt(DateTime.now().toDate());
        userRepository.save(newUser);

        link1.setUser(newUser);
        link2.setUser(newUser);

        linkRepository.save(link1);
        linkRepository.save(link2);

        Link link1Found = linkRepository.find(link1.getLinkId());
        Link link2Found = linkRepository.find(link2.getLinkId());

        assertThat(link1Found.getUser(), is(notNullValue()));
        assertThat(link2Found.getUser(), is(notNullValue()));
        assertThat(link1Found.getUser().getUsername(), is("test_username"));
        assertThat(link2Found.getUser().getUsername(), is("test_username"));

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
