package org.example;

import org.example.model.Person;
import org.example.model.Product;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        Person person1 = new Person(1, "Victor", LocalDate.of(1996, 3, 22));
        Person person2 = new Person(2, "Carlos", LocalDate.of(1996, 3, 22));
        Person person3 = new Person(3, "Pedro", LocalDate.of(1996, 3, 22));
        Person person4 = new Person(4, "Karol", LocalDate.of(1996, 3, 22));
      Person person5 = new Person(5, "Paola", LocalDate.of(1996, 3, 22));

        Product product1 = new Product(1, "Ceviche1", 15.0);
        Product product2 = new Product(2, "Ceviche2", 15.0);
        Product product3 = new Product(3, "Ceviche3", 15.0);
        Product product4 = new Product(4, "Ceviche4", 15.0);

        List<Person> persons = Arrays.asList(person1, person2, person3, person4, person5);
        List<Product> products = Arrays.asList(product1, product2, product3, product4);

        /*for(Person person : persons){
            System.out.println(person);
        }*/

        // Lambda funcion de flecha
//        persons.forEach(p -> System.out.println(p));
        // persons.forEach(System.out::println);

        // Filter (param: Predicate)
        List<Person> adults = persons.stream()
                .filter(p -> App.getAge(p.getBirthDate()) >= 18)
                .collect(Collectors.toList());

        // App.printList(adults);

        // Map (param: Function)
        List<Integer> ages = persons.stream()
                .map(p -> App.getAge(p.getBirthDate()))
                .collect(Collectors.toList());
       //  App.printList(ages);

        // Sorted (param: Comparator)
        Comparator<Person> byNameAsc = (Person a, Person b)-> a.getName().compareTo(b.getName());
        List<Person> namesOrderAsc = persons.stream()
                .sorted(byNameAsc)
                .collect(Collectors.toList());
        // App.printList(namesOrderAsc);

        // Match (param: Predicate)

        // anyMatch: No evalua todo el stream , termina en la coincidencia
        boolean respuesta1 = persons.stream()
                .anyMatch(p-> p.getName().startsWith("V"));
        // System.out.println(respuesta1);

        //allMatch: Evalua todo el stream bajo la condicion
        boolean respuesta2 = persons.stream()
                .allMatch(p-> p.getName().startsWith("V"));
        // System.out.println(respuesta2);

        //noneMatch: Evalua todo el stream bajo la condicion
        boolean respuesta3 = persons.stream()
                .noneMatch(p-> p.getName().startsWith("V"));
        // System.out.println(respuesta3);

        // Limit / Skip
        int pageNumber = 2;
        int pageSize = 2;
        List<Person> filteredList = persons.stream()
                .skip(pageNumber*pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
        System.out.println(filteredList);

        // Collectors
        // GroupBy
        Map<Double, List<Product>> collect1 = products.stream()
                .filter(p -> p.getPrice() <20 )
                .collect(Collectors.groupingBy(Product::getPrice));
        // System.out.println(collect1);

        // Counting
        Map<String, Long> collect2 = products.stream()
                .collect(Collectors.groupingBy(Product::getName, Collectors.counting()));
         // System.out.println(collect2);

        // Agrupando  por nombre producto y sumando
        Map<String, Double> collect3 = products.stream()
                .collect(Collectors.groupingBy(Product::getName, Collectors.summingDouble(Product::getPrice)));
        System.out.println(collect3);

        // Obteniendo suma y resumen
        DoubleSummaryStatistics collect4 = products.stream()
                .collect(Collectors.summarizingDouble(Product::getPrice));
        // System.out.println(collect4);

        // reduce
        Optional<Double> collect5 = products.stream()
                        .map(Product::getPrice)
                                .reduce(Double::sum);
        System.out.println(collect5.get());

    }
    public static int getAge (LocalDate birthDate){
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
    public static void printList (List<?> list){
        list.forEach(System.out::println);
    }
}
