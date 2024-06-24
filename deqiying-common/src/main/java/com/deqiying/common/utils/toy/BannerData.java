package com.deqiying.common.utils.toy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author deqiying
 * @date 2023-01-30
 */
final class BannerData {

    List<String> listData() {
        List<String> bannerList = new ArrayList<>();

        String banner;

        banner = """
                 _______   ________   ______   ______  __      __  ______  __    __   ______ \s
                /       \\ /        | /      \\ /      |/  \\    /  |/      |/  \\  /  | /      \\\s
                $$$$$$$  |$$$$$$$$/ /$$$$$$  |$$$$$$/ $$  \\  /$$/ $$$$$$/ $$  \\ $$ |/$$$$$$  |
                $$ |  $$ |$$ |__    $$ |  $$ |  $$ |   $$  \\/$$/    $$ |  $$$  \\$$ |$$ | _$$/\s
                $$ |  $$ |$$    |   $$ |  $$ |  $$ |    $$  $$/     $$ |  $$$$  $$ |$$ |/    |
                $$ |  $$ |$$$$$/    $$ |_ $$ |  $$ |     $$$$/      $$ |  $$ $$ $$ |$$ |$$$$ |
                $$ |__$$ |$$ |_____ $$ / \\$$ | _$$ |_     $$ |     _$$ |_ $$ |$$$$ |$$ \\__$$ |
                $$    $$/ $$       |$$ $$ $$< / $$   |    $$ |    / $$   |$$ | $$$ |$$    $$/\s
                $$$$$$$/  $$$$$$$$/  $$$$$$  |$$$$$$/     $$/     $$$$$$/ $$/   $$/  $$$$$$/ \s
                                         $$$/                                                \s
                """;

        bannerList.add(banner);

        return bannerList;
    }
}
