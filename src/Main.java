import exceptionFolder.DataException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    static class DataF{
        private String i1;
        private int i2;

        public DataF( String i1,int i2){
            this.i1= i1;
            this.i2= i2;
        }

        public String getI1(){
            return i1;
        }

        public int getI2(){
            return i2;
        }

    }
    public static void main(String[] args) {
        String s = "C:\\Users\\BILIL\\Desktop\\FinalTest.csv";
        List<DataF> data = readData(s);
        Optional<Map.Entry<String, Integer> >val1 =data.stream().collect(Collectors.groupingBy(DataF::getI1,Collectors.summingInt(DataF::getI2)))
                .entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .findAny();
        List<Integer> val2 =data.stream().collect(Collectors.groupingBy(DataF::getI1,Collectors.summingInt(DataF::getI2)))
                .entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getValue).collect(Collectors.toList());
        val1.ifPresent(x -> System.out.println(x.getKey()+"\n"+x.getValue()));

        val2.forEach(System.out::print);
    }
    public static List<DataF> readData(String s) {
        Path path = Paths.get(s);

        List<DataF> dd = new ArrayList<>();
        List<String> cc = null;
        if (Files.exists(path)) {
            try  {
                Stream<String> stream = Files.lines(path);
                cc = stream.collect(Collectors.toList());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int i = 0;
        for(String c: cc){

            String [] a = c.split(",");

            if(a.length==2) {
                DataF d = new DataF(a[0], Integer.parseInt(a[1]));
                dd.add(d);
            }
        }
        return dd;
    }
}
