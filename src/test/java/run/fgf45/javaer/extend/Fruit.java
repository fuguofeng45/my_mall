package run.fgf45.javaer.extend;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Fruit {

    public static void main(String[] args) {
        List<String> string = new ArrayList<String>();
        string.add("11");
        string.add("12");
        string.add("22");
        string.add("32");
        string.add("42");
        string.add("115");

        string = string.stream().filter(arg -> arg.startsWith("1")).map(arg -> arg + "321").collect(Collectors.toList());
        System.out.println(string);
    }

}
