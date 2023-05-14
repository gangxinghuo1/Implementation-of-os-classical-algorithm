package com.dhx.sample;

import com.dhx.algorithms.fifo.FIFO;
import com.dhx.algorithms.lfu.LFU;
import com.dhx.model.Element;

import java.util.ArrayList;
import java.util.List;

import static com.dhx.model.Constant.elements;

/**
 * @author dhx_
 * @className LFUSample
 * @date : 2023-5-9 10:48:43
 **/
public class LFUExample {

    public static void main(String[] args) {
        System.out.println("测试前准备测试页如下: ");
        for (Element element : elements) {
            System.out.println(element);
        }
        // 理论上LFU算法的缺页率会随着 物理块数量的增加而降低 , 如果不明显, 可以通过加大第二个参数(访问次数)尝试
        List<String> results = new ArrayList<>();
        results.add(testNBlock(3, 1000));
        results.add(testNBlock(4, 1000));
        results.add(testNBlock(6, 1000));
        results.add(testNBlock(8, 1000));
        results.add(testNBlock(10, 1000));
        results.forEach(System.out::println);
        /*
        实际测试情况
        测试时建议测试加大测试数据的容量，使得结果更加趋于一般效果
        [测试结束]物理块数: 3 缺页次数: 706 缺页率: 0.706
        [测试结束]物理块数: 4 缺页次数: 578 缺页率: 0.578
        [测试结束]物理块数: 6 缺页次数: 402 缺页率: 0.402
        [测试结束]物理块数: 8 缺页次数: 198 缺页率: 0.198
        [测试结束]物理块数: 10 缺页次数: 0 缺页率: 0.0
         */
    }

    /**
     * 随机调用十次页数
     * @param n 设置物理块数量为n
     * @param times 测试次数
     */
    static String testNBlock(int n,int times){
        System.out.println("\u001B[31m\u001B[1m[测试开始]\u001B[0m 本次测试物理块数量为 "+n);
        if(n>10){
            throw new RuntimeException("测试块数过多!");
        }
        // 设置LFU序列的容量为n
        LFU cache = new LFU(n);
        // 假设我们最开始直接放入3个页面到内存块中，
        for (int i = 1; i <= n; i++) {
            cache.put(elements[i]);
        }
        for (int i = 1; i <= times; i++) {
            int random = (int)(Math.random()*100)%10+1;
            cache.get(random);
        }
        System.out.println("\u001B[31m\u001B[1m[测试结束]\u001B[0m 缺页次数: "+cache.getMissingNum() + "缺页率: "+cache.getMissingNum()/(double)times + " 当前页面情况: " + cache.toString() );
        return "\u001B[31m\u001B[1m[测试结束]\u001B[0m物理块数: "+n+" 缺页次数: "+cache.getMissingNum() + " 缺页率: "+cache.getMissingNum()/(double)times;
    }
}
