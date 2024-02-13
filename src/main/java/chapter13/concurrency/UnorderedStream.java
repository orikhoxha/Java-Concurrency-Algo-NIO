package chapter13.concurrency;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Stream;

public class UnorderedStream {


    public static void main(String[] args) {
        Stream.of(1,2,3,4,5,6).unordered();

        System.out.println(List.of(1,2,3,4,5,6).parallelStream().reduce(0, (a, b) -> a - b));

        Stream<String> stream = Stream.of("w","o","l","f").parallel();
        SortedSet<String> set = stream.collect(ConcurrentSkipListSet::new, Set::add, Set::addAll);

    }
}
