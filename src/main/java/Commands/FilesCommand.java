package Commands;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilesCommand {
    private String dir;
    private Set<String> filesInDir;
    private static FilesCommand instance;

    public FilesCommand(String dir) {
        this.dir = dir;
        this.filesInDir = listFilesUsingJavaIO();
    }

    public static FilesCommand getInstance(String dir) {
        if (instance == null) {
            instance = new FilesCommand(dir);
        }
        return instance;
    }

    private Set<String> listFilesUsingJavaIO() {
        return Stream.of(Objects.requireNonNull(new File(dir).listFiles()))
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());
    }

    public String getFileContent(String path) throws IOException {
        return Files.readString(Paths.get(dir+path), StandardCharsets.UTF_8);
    }

    public boolean writeToFile(String path, String content) {
        try {
            File file = new File(dir+path);
            Writer output = new BufferedWriter(new FileWriter(file));
            output.write(content);
            output.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
