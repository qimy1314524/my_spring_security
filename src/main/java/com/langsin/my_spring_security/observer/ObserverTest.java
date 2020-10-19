package com.langsin.my_spring_security.observer;

import java.util.Arrays;
import java.util.List;
import java.util.Observable;
import java.util.stream.Collectors;
import reactor.core.publisher.Flux;

/**
 * @author tey
 * @version V1.0
 * @date 2020/9/3- 10:59
 * @desc
 **/
public class ObserverTest extends Observable {

  public void fluxTest() {
    Flux.just();
    Integer[] arr = new Integer[]{1, 2, 3, 4};
    Flux.fromArray(arr).subscribe(System.out::println);
    List<Integer> list = Arrays.asList(1, 2, 3, 4);
    List<Integer> collect = list.stream().map((a) ->
       a * 2
    ).collect(Collectors.toList());
    collect.stream().forEach(System.out::print);
    Flux.fromIterable(list);
    Flux.fromStream(list.stream());
    Flux.error(new RuntimeException());
  }

  public void testObserver() {
    this.addObserver((o, arg) -> {
      System.out.println(o);
      System.out.println(arg);
      System.out.println("one==============");
    });
    this.addObserver((o, arg) -> {
      System.out.println(o);
      System.out.println(arg);
      System.out.println("two==============");
    });
    this.setChanged();
    this.notifyObservers();
  }


  public static void main(String[] args) {
    ObserverTest observer = new ObserverTest();
//    observer.testObserver();
    observer.fluxTest();
  }


}
