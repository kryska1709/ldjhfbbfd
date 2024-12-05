import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelFileCopy {
    public static void copyFile(String sourcePath, String destPath) {
        ExecutorService executor = Executors.newFixedThreadPool(4); // Пул потоков

        executor.submit(() -> {
            try (FileInputStream in = new FileInputStream(sourcePath);
                 FileOutputStream out = new FileOutputStream(destPath)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        executor.shutdown();
        System.out.println("Файл скопирован параллельно.");
    }

    public static void main(String[] args) {
        copyFile("source.txt", "destination.txt");
    }
}
