package com.cv;

import com.cv.mapper.UserMapper;
import com.cv.model.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tk.mybatis.mapper.mapperhelper.MapperHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author: xutu
 * @since: 2026/3/2 14:20
 */
public class mybatis {
    private SqlSessionFactory sqlSessionFactory;
    private SqlSession session;
    private UserMapper userMapper;

    @Before
    public void setUp() throws IOException {
        // Load MyBatis configuration
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        // Get a SqlSession
        session = sqlSessionFactory.openSession();

        // Get the mapper
        userMapper = session.getMapper(UserMapper.class);
    }

    @After
    public void tearDown() {
        if (session != null) {
            session.close();
        }
    }

    @Test
    public void testSelectAll() {
        List<User> users = userMapper.selectAll();
        assertNotNull(users);
        assertTrue(users.size() >= 3); // At least 3 initial users
    }

    @Test
    public void testSelectByPrimaryKey() {
        User user = userMapper.selectByPrimaryKey(1L);
        assertNotNull(user);
        assertEquals("Alice", user.getName());
    }

    @Test
    public void testInsert() {
        User newUser = new User();
        newUser.setName("David");
        newUser.setAge(40);
        newUser.setEmail("david@example.com");

        int result = userMapper.insert(newUser);
        assertEquals(1, result);
        assertNotNull(newUser.getId());
        assertTrue(newUser.getId() > 0);

        // Verify the user was inserted
        User insertedUser = userMapper.selectByPrimaryKey(newUser.getId());
        assertNotNull(insertedUser);
        assertEquals("David", insertedUser.getName());
    }

    @Test
    public void testUpdate() {
        User user = userMapper.selectByPrimaryKey(1L);
        assertNotNull(user);

        // Update the user
        user.setName("Alice Smith");
        user.setAge(26);

        int result = userMapper.updateByPrimaryKey(user);
        assertEquals(1, result);

        // Verify the update
        User updatedUser = userMapper.selectByPrimaryKey(1L);
        assertNotNull(updatedUser);
        assertEquals("Alice Smith", updatedUser.getName());
        assertEquals(Integer.valueOf(26), updatedUser.getAge());
    }

    @Test
    public void testDelete() {
        // Insert a user to delete
        User newUser = new User();
        newUser.setName("Test User");
        newUser.setAge(25);
        newUser.setEmail("test@example.com");
        userMapper.insert(newUser);

        // Delete the user
        int result = userMapper.deleteByPrimaryKey(newUser.getId());
        assertEquals(1, result);

        // Verify the user was deleted
        User deletedUser = userMapper.selectByPrimaryKey(newUser.getId());
        assertNull(deletedUser);
    }
}
