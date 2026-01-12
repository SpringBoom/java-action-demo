package com.example.paralle.multifuture;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.concurrent.CompletableFuture;

/**
 *
 * @author shing.shi
 * @since 2026/1/12
 */
public class GamePageParser {
  public static void main(String[] args) {
    GamePageParser gamePageParser = new GamePageParser();
    gamePageParser.printGames(LocalDate.of(2017, Month.MAY,5),3);
  }

  public void printGames(LocalDate startDate, int days) {
    CompletableFuture<List<Result>> future =
        CompletableFuture.supplyAsync(
                new GamePageLinksSupplier(startDate, days))
            .thenApply(new BoxscoreRetriever());

    CompletableFuture<Void> futureWrite =
        future.thenAcceptAsync(this::saveResultList)
            .exceptionally(ex -> {
              System.err.println(ex.getMessage());
              return null;
            });

    CompletableFuture<OptionalInt> futureMaxScore =
        future.thenApplyAsync(this::getMaxScore);
    CompletableFuture<Optional<Result>> futureMaxGame =
        future.thenApplyAsync(this::getMaxGame);
    CompletableFuture<String> futureMax =
        futureMaxScore.thenCombineAsync(futureMaxGame,
            (score, result) ->
                String.format("Highest score: %d, Max Game: %s",
                    score.orElse(0), result.orElse(null)));

    // 使用 allOf() 等待所有异步处理完成
    CompletableFuture.allOf(futureWrite, futureMax).join();

    future.join().forEach(System.out::println);
    System.out.println(futureMax.join());
  }

  private Optional<Result> getMaxGame(List<Result> results) {
    return results.stream().max(Comparator.comparingInt(Result::getScore));
  }

  private OptionalInt getMaxScore(List<Result> results) {
    return results.stream().map(Result::getScore).mapToInt(Integer::intValue).max();
  }

  private void saveResultList(List<Result> results) {
    results.parallelStream().forEach(this::saveResultToFile);
  }

  public void saveResultToFile(Result result) {
    try {
      File file = new File("results" + File.separator + result.getName() + ".json");
      Files.write(file.toPath().toAbsolutePath(), new Gson().toJson(result).getBytes(Charset.defaultCharset()));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


}
