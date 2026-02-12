package com.example.myfridge.common.constants;

import java.util.Map;
import static java.util.Map.entry;

public class PreferTagMapping {
    public static final Map<Integer, String> TAG_NAME_MAP = Map.ofEntries(
            entry(1, "한식"),
            entry(2, "일식"),
            entry(3, "양식"),
            entry(4, "중식"),
            entry(5, "아시안"),
            entry(6, "볶음"),
            entry(7, "국 찌개"),
            entry(8, "구이"),
            entry(9, "생식"),
            entry(10, "조림 찜"),
            entry(11, "초간단"),
            entry(12, "한그릇"),
            entry(13, "술안주"),
            entry(14, "도시락"),
            entry(15, "다이어트"));

    public static String getTagName(Integer id) {
        return TAG_NAME_MAP.getOrDefault(id, "");
    }
}
