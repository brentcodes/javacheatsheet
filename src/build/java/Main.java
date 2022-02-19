import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        System.out.println("\tCreating Cheatsheets...");
        final File sourceDir = new File("src/main/java/tocheatsheet");
        for (File file : sourceDir.listFiles()) {
            if (!file.isDirectory()) continue;
            try {
                createCheatSheet(file);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        System.out.println("\tCheatsheet Creation Complete");
    }

    static File createCheatSheet(File dir) throws Exception {
        System.out.println("\tCreating Cheatsheet for " + dir.getName());
        List<File> javaFiles = Stream.of(dir.listFiles())
                .filter(file -> !file.isDirectory() && file.getName().endsWith(".java"))
                .collect(Collectors.toList());

        List<String> javaClasses = new ArrayList<>();
        Set<String> imports = new HashSet<>();
        javaClasses.add("");

        for (File javaFile : javaFiles) {
            BufferedReader reader = new BufferedReader(new FileReader(javaFile));
            StringBuffer clazz = new StringBuffer();
            reader.lines().forEach(line -> {
                line = line.trim();
                if (line.startsWith("package")) return;
                if (line.startsWith("//")) return;
                if (line.startsWith("import")) {
                    imports.add(line);
                    return;
                }
                clazz.append(line + "\t");
            });
            javaClasses.add(clazz.toString());
        }

        javaClasses.set(0, "\t\t" + String.join(" ", imports));
        final String fileOutput = String.join("\n\n", javaClasses);
        Files.write(
                Paths.get("cheatsheets/" + dir.getName() + ".txt"),
                fileOutput.getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.CREATE
        );

        return dir;
    }
}
