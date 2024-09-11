import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
    private static final String SPECIFICATION_NAME = "C:\\Users\\efisanov\\IdeaProjects\\Task3\\src\\spec.txt";
    private static final String DATA_SPECIFICATION_NAME = "C:\\Users\\efisanov\\IdeaProjects\\Task3\\src\\dataSpec.txt";
    private static final String FILE_NAME = "C:\\Users\\efisanov\\IdeaProjects\\Task3\\src\\firefighterCarTechCard.fctc";

    public static void main(String[] args) throws ValidateException {
        checkFileFormat(FILE_NAME, SPECIFICATION_NAME);
        checkDataFormat(FILE_NAME, DATA_SPECIFICATION_NAME);
        System.out.println("Файл соответствует установленному формату");
    }

    public static void checkFileFormat(String fileName, String specificationName) throws ValidateException {
        HashMap<Integer, String> specification = loadSpecification(specificationName);
        List<Integer> keys = new ArrayList<>(specification.keySet());
        List<String> fields = loadRecords(fileName);
        for (Integer key : keys) {
            if (fields.get(key - 1).contains(specification.get(key))) {
                continue;
            } else if (fields.get(key - 1).isEmpty() && specification.get(key).equals(" ")) {
                continue;
            } else {
                throw new ValidateException("Файл не соответствует установленному формату");
            }
        }
    }

    public static void checkDataFormat(String fileName, String dataSpecificationName) throws ValidateException {
        HashMap<Integer, String> dataSpecification = loadSpecification(dataSpecificationName);
        List<Integer> keys = new ArrayList<>(dataSpecification.keySet());
        List<String> fields = loadRecords(fileName);
        HashMap<Integer, String> data = getDataMap(fields);

        for (Integer key : keys) {
            if (data.get(key).matches(dataSpecification.get(key))) {
                continue;
            }
            throw new ValidateException("Формат введённых значений не соответствует установленному");
        }
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

        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return specification;
    }

    public static List<String> loadRecords(String fileName) {
        List<String> records = new ArrayList<>();
        try {
            Path path = Paths.get(fileName);
            records = Files.readAllLines(path);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return records;
    }

    public static HashMap<Integer, String> getDataMap(List<String> fields) {
        HashMap<Integer, String> data = new HashMap<>();
        for (int i = 0; i < fields.size(); i++) {
            if (fields.get(i).contains(": ")) {
                data.put(i, getFieldValue(fields.get(i)));
            }
        }
        return data;
    }

    public static String getFieldValue(String source) {
        return source.substring(source.lastIndexOf(":")).substring(2);
    }

}