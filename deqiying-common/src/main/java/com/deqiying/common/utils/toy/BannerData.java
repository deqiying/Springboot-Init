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

        banner = """
                     _            __ _      _      _  _     _              __ _ \s
                  __| |    ___   / _` |    (_)    | || |   (_)    _ _     / _` |\s
                 / _` |   / -_)  \\__, |    | |     \\_, |   | |   | ' \\    \\__, |\s
                 \\__,_|   \\___|   __|_|   _|_|_   _|__/   _|_|_  |_||_|   |___/ \s
                _|""\"""|_|""\"""|_|""\"""|_|""\"""|_| ""\""|_|""\"""|_|""\"""|_|""\"""|\s
                "`-0-0-'"`-0-0-'"`-0-0-'"`-0-0-'"`-0-0-'"`-0-0-'"`-0-0-'"`-0-0-'\s
                """;
        bannerList.add(banner);

        banner = """
                .------..------..------..------..------..------..------..------.
                |D.--. ||E.--. ||Q.--. ||I.--. ||Y.--. ||I.--. ||N.--. ||G.--. |
                | :/\\: || (\\/) || (\\/) || (\\/) || (\\/) || (\\/) || :(): || :/\\: |
                | (__) || :\\/: || :\\/: || :\\/: || :\\/: || :\\/: || ()() || :\\/: |
                | '--'D|| '--'E|| '--'Q|| '--'I|| '--'Y|| '--'I|| '--'N|| '--'G|
                `------'`------'`------'`------'`------'`------'`------'`------'
                """;

        bannerList.add(banner);

        return bannerList;
    }
}
