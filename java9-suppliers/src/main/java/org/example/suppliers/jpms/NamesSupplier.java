package org.example.suppliers.jpms;

/**
 *
 * @author shing.shi
 * @since 2026/1/12
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Supplier;
import java.util.stream.Stream;

// 导入

public class NamesSupplier implements Supplier<Stream<String>> {
  private Path namesPath = Paths.get("java9-suppliers/src/main/resources/names.txt");

  @Override
  public Stream<String> get() {
    try{
      return Files.lines(namesPath);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}