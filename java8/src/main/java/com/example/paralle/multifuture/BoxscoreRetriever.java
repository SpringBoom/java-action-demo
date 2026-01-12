package com.example.paralle.multifuture;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * BoxscoreRetriever 类来访问每个比赛链接中的 boxscore.json 文件
 *
 * @author shing.shi
 * @since 2026/1/12
 */
public class BoxscoreRetriever implements Function<List<String>, List<Result>> {

  private static final String BASE = "http://gd2.mlb.com/components/game/mlb/";

  private OkHttpClient client = new OkHttpClient();
  private Gson gson = new Gson();

  @SuppressWarnings("ConstantConditions")
  public Optional<Result> gamePattern2Result(String pattern) {
    // 省略代码
    String boxscoreUrl = BASE + pattern + "boxscore.json";
    Request post = new Request.Builder().url(boxscoreUrl).post(null).build();

    // 设置OkHttp库以创建网络调用
    try (Response response = client.newCall(post).execute()) {
      // 获取响应
      if (!response.isSuccessful()) {
        System.out.println("Box score not found for " + boxscoreUrl);
        return Optional.empty();
      }

      return Optional.ofNullable(
          gson.fromJson(response.body().charStream(), Result.class));
    } catch (IOException e) {
      e.printStackTrace();
      return Optional.empty();
    }
  }

  /**
   * apply 方法读取各场比赛的链接，将它们转换为相应的 Optional<Result>
   * 最后返回 Result 列表
   * @param strings the function argument
   * @return
   */
  @Override
  public List<Result> apply(List<String> strings) {
    return
        strings.parallelStream()
            .map(this::gamePattern2Result)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
  }
}
