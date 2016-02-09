package util;

import com.spring.boilerplate.common.util.Hasher;
import org.junit.Test;

public class HasherTest {

    @Test
    public void testName() throws Exception {
        Hasher hasher = new Hasher(8);
        String hashId = hasher.get();
        System.out.println(hashId);
    }

    @Test
    public void test_hahsid() throws Exception {
        String hashId = Hasher.getHashId();
        System.out.println(hashId);
    }
}
