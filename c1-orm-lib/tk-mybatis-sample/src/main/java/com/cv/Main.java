package com.cv;

import com.cv.mapper.UserMapper;
import com.cv.model.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import tk.mybatis.mapper.entity.Config;
import tk.mybatis.mapper.mapperhelper.MapperHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author: xutu
 * @since: 2025/10/14 15:57
 */
public class Main {
    public static void main(String[] args) throws IOException {
        // Load MyBatis configuration
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        // Use tk.mybatis to enhance the mapper
        Config config = new Config();
        MapperHelper mapperHelper = new MapperHelper();
        mapperHelper.setConfig(config);
        mapperHelper.registerMapper(UserMapper.class);

        // Get a SqlSession
        try (SqlSession session = sqlSessionFactory.openSession()) {
            // Get the mapper
            UserMapper userMapper = session.getMapper(UserMapper.class);

            // Test SELECT all
            System.out.println("=== Select All Users ===");
            List<User> users = userMapper.selectAll();
            users.forEach(System.out::println);

            // Test SELECT by ID
            System.out.println("\n=== Select User by ID ===");
            User user = userMapper.selectByPrimaryKey(1L);
            System.out.println(user);

            // Test INSERT
            System.out.println("\n=== Insert New User ===");
            User newUser = new User();
            newUser.setName("David");
            newUser.setAge(40);
            newUser.setEmail("david@example.com");
            int insertResult = userMapper.insert(newUser);
            System.out.println("Insert result: " + insertResult);
            System.out.println("New user ID: " + newUser.getId());

            // Test UPDATE
            System.out.println("\n=== Update User ===");
            user.setName("Alice Smith");
            user.setAge(26);
            int updateResult = userMapper.updateByPrimaryKey(user);
            System.out.println("Update result: " + updateResult);

            // Test DELETE
            System.out.println("\n=== Delete User ===");
            int deleteResult = userMapper.deleteByPrimaryKey(2L);
            System.out.println("Delete result: " + deleteResult);

            // Test SELECT all again to see changes
            System.out.println("\n=== Select All Users (After Changes) ===");
            users = userMapper.selectAll();
            users.forEach(System.out::println);

            // Commit the transaction
            session.commit();
        }
    }
}