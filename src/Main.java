import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
    private static final String SPECIFICATION_NAME = "C:\\Users\\efisanov\\IdeaProjects\\Task3\\src\\spec.txt";
    private static final String FILE_NAME = "C:\\Users\\efisanov\\IdeaProjects\\Task3\\src\\firefighterCarTechCard.fctc";

    public static void main(String[] args) throws ValidateException {
        checkFormat(FILE_NAME, SPECIFICATION_NAME);
        checkDataFormat(FILE_NAME, SPECIFICATION_NAME);
    }

    public static HashMap<Integer, String> loadSpecification(String fileName) {
        HashMap<Integer, String> specification = new HashMap<>();
        try {
            Path path = Paths.get(fileName);
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                String[] value = line.split(";");
                if (value.length == 1) {
                    specification.put(Integer.parseInt(value[0]), " ");
                } else {
                    specification.put(Integer.parseInt(value[0]), value[1]);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return specification;
    }

    public static List<String> loadRecords(String fileName) {
        List<String> records = new ArrayList<>();
        try {
            Path path = Paths.get(fileName);
            records = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }

    public static void checkFormat(String fileName, String specificationName) throws ValidateException {
        HashMap<Integer, String> specification = loadSpecification(specificationName);
        List<Integer> keys = new ArrayList<>(specification.keySet());
        List<String> fields = loadRecords(fileName);
        for (Integer key : keys) {
            if (!fields.get(key - 1).contains(specification.get(key)) && !specification.get(key).equals(" ")) {
                throw new ValidateException("Файл не соответствует установленному формату");
            }
        }



        System.out.println("Файл соответствует установленному формату");
    }

    public static void checkDataFormat(String fileName, String specificationName) {
        HashMap<Integer, String> specification = loadSpecification(specificationName);
        List<Integer> keys = new ArrayList<>(specification.keySet());
        List<String> fields = loadRecords(fileName);

        HashMap<Integer, String> data = new HashMap<>();

        for (int i = 0; i < fields.size(); i++) {
            if (fields.get(i).contains(": ")) {
                data.put(i, getValue(fields.get(i)));
            }
        }

    }

    public static String getValue(String source) {
        return source.substring(source.lastIndexOf(":")).substring(2);
    }

}