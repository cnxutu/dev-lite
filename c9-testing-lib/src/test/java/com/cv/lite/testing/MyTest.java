package com.cv.lite.testing;

import com.cv.Main;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author: xutu
 * @since: 2026/3/2 14:22
 */
public class MyTest {
    private Main.Calculator calculator;
    
    @BeforeEach
    void setUp() {
        calculator = new Main.Calculator();
    }

    @Test
    public void test1() {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        arrayList.add(-1);
        arrayList.add(3);
        arrayList.add(3);
        arrayList.add(-5);
        arrayList.add(7);
        arrayList.add(4);
        arrayList.add(-9);
        arrayList.add(-7);
        System.out.println("原始数组:");
        System.out.println(arrayList);
        // void reverse(List list)：反转
        Collections.reverse(arrayList);
        System.out.println("Collections.reverse(arrayList):");
        System.out.println(arrayList);

        // void sort(List list),按自然排序的升序排序
        Collections.sort(arrayList);
        System.out.println("Collections.sort(arrayList):");
        System.out.println(arrayList);
        // 定制排序的用法
        Collections.sort(arrayList, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2.compareTo(o1);
            }
        });

        System.out.println("定制排序后：");
        System.out.println(arrayList);
    }
    
    @Test
    public void testCalculatorAdd() {
        int result = calculator.add(2, 3);
        assertEquals(5, result, "2 + 3 应该等于 5");
    }
    
    @Test
    public void testCalculatorSubtract() {
        int result = calculator.subtract(5, 2);
        assertEquals(3, result, "5 - 2 应该等于 3");
    }
    
    @Test
    public void testCalculatorMultiply() {
        int result = calculator.multiply(4, 5);
        assertEquals(20, result, "4 * 5 应该等于 20");
    }
    
    @Test
    public void testCalculatorDivide() {
        int result = calculator.divide(10, 2);
        assertEquals(5, result, "10 / 2 应该等于 5");
    }
    
    @Test
    public void testCalculatorDivideByZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.divide(10, 0);
        }, "除数为0时应该抛出IllegalArgumentException");
    }
    
    @Test
    public void testTestDataGenerator() {
        Main.TestDataGenerator generator = new Main.TestDataGenerator();
        
        // 测试随机字符串生成
        String randomString = generator.generateRandomString(10);
        assertNotNull(randomString);
        assertEquals(10, randomString.length());
        
        // 测试随机整数生成
        int randomInt = generator.generateRandomInt(1, 100);
        assertTrue(randomInt >= 1 && randomInt <= 100);
        
        // 测试随机列表生成
        List<Integer> randomList = generator.generateRandomList(5, 1, 100);
        assertNotNull(randomList);
        assertEquals(5, randomList.size());
        for (int num : randomList) {
            assertTrue(num >= 1 && num <= 100);
        }
    }
    
    @Test
    public void testMockito() {
        // 创建模拟对象
        Main.Calculator mockCalculator = Mockito.mock(Main.Calculator.class);
        
        // 设置模拟行为
        when(mockCalculator.add(2, 3)).thenReturn(5);
        when(mockCalculator.subtract(5, 2)).thenReturn(3);
        
        // 调用模拟对象
        int addResult = mockCalculator.add(2, 3);
        int subtractResult = mockCalculator.subtract(5, 2);
        
        // 验证结果
        assertEquals(5, addResult);
        assertEquals(3, subtractResult);
        
        // 验证方法是否被调用
        verify(mockCalculator).add(2, 3);
        verify(mockCalculator).subtract(5, 2);
    }
    
    @Test
    public void testAssertJ() {
        List<Integer> list = List.of(1, 2, 3, 4, 5);
        
        // 使用AssertJ进行断言
        assertThat(list)
            .hasSize(5)
            .contains(1, 2, 3)
            .doesNotContain(6)
            .startsWith(1)
            .endsWith(5);
        
        String str = "Hello, World!";
        assertThat(str)
            .startsWith("Hello")
            .endsWith("!")
            .contains("World")
            .hasLength(13);
    }
}
