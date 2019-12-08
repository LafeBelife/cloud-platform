package cn.wbw.study.lambda;

import cn.hutool.json.JSONUtil;
import org.apache.poi.ss.formula.functions.T;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * lambda 学习
 *
 * @author wbw
 * @date 2019-12-08 12:31
 */
public class LambdaDemo01 {

    public static void main(String[] args) {
        Runnable runnable = () -> System.out.println("hello runnable");
        Consumer<T> consumer = co -> System.out.println(co.getClass());
        runnable.run();

        BinaryOperator<Long> addLongs = Long::min;
        System.out.println(addLongs.apply(1L, 2L));

        Stream<String> stream = Stream.of("xaxa", "asas", "b", "c", "d");
        List<String> collect = stream.collect(Collectors.toList());
        List<Boolean> collect1 = Stream.of("xaxa", "asas", "b", "c", "d").map(str -> str.contains("c")).collect(Collectors.toList());
        System.out.println(collect1);

        boolean b = Stream.of("xaxa", "asas", "b", "c", "d").anyMatch(e -> e.contains("b"));
        System.out.println(b);
        b = Stream.of("xaxa", "asas", "b", "c", "d").noneMatch(e -> e.contains("b"));
        System.out.println(b);

        Optional<String> first = Stream.of("xaxa", "asas", "b", "c", "d").filter(e -> e.contains("d") || e.contains("c")).findFirst();
        System.out.println(first.get());

        Stream.of("hello world! mr").map(word -> word.split("")).flatMap(Arrays::stream).distinct()
                .collect(Collectors.toList()).forEach(e -> System.out.println(Arrays.toString(new String[]{e})));
        System.out.println();

        System.out.println(Stream.of("张三", "李四", "旺旺").filter(e -> e.contains("三")).count());
        Stream.of("hello aaa").map(e -> e.split(" ")).flatMap(Arrays::stream).forEach(System.out::println);

        Stream.of("张三", "李四", "王五", "赵六", "张无忌", "张翼德","张啊", "张先知").filter(e -> e.startsWith("张"))
                .sorted(Comparator.naturalOrder()).collect(Collectors.toList()).forEach(System.out::println);

    }
}
