package com.deqiying.common.utils;

import com.deqiying.common.constant.Constants;
import com.deqiying.common.core.text.StrFormatter;
import org.springframework.util.AntPathMatcher;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 字符串工具类
 *
 * @author deqiying
 */
@SuppressWarnings(value = {"unused"})
public class StringUtils extends org.apache.commons.lang3.StringUtils {
    /**
     * 空字符串
     */
    private static final String NULL_STR = "";

    /**
     * 下划线
     */
    private static final char SEPARATOR = '_';

    /**
     * 填充限制
     */
    private static final int PAD_LIMIT = 8192;

    /**
     * 正则表达式，用于匹配需要移除的字符（特殊符号、颜文字等）
     */
    private static final String REGEX_REMOVE = "[^a-zA-Z0-9\\u4e00-\\u9fa5]";


    /**
     * 获取参数不为空值
     *
     * @param value 要判断的value
     * @param defaultValue 默认值
     * @return value 返回值
     */
    public static <T> T nvl(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }

    /**
     * 判断一个Collection是否为空， 包含List，Set，Queue
     *
     * @param coll 要判断的Collection
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(Collection<?> coll) {
        return isNull(coll) || coll.isEmpty();
    }

    /**
     * 判断一个Collection是否非空，包含List，Set，Queue
     *
     * @param coll 要判断的Collection
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }

    /**
     * 判断一个对象数组是否为空
     *
     * @param objects 要判断的对象数组
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(Object[] objects) {
        return isNull(objects) || (objects.length == 0);
    }

    /**
     * 判断一个对象数组是否非空
     *
     * @param objects 要判断的对象数组
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Object[] objects) {
        return !isEmpty(objects);
    }

    /**
     * 判断一个Map是否为空
     *
     * @param map 要判断的Map
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return isNull(map) || map.isEmpty();
    }

    /**
     * 判断一个Map是否为空
     *
     * @param map 要判断的Map
     * @return true：非空 false：空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * 判断一个字符串是否为空串
     *
     * @param str String
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(String str) {
        return isNull(str) || NULL_STR.equals(str.trim());
    }

    /**
     * 判断一个字符串是否为非空串
     *
     * @param str String
     * @return true：非空串 false：空串
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 判断一个对象是否为空
     *
     * @param object Object
     * @return true：为空 false：非空
     */
    public static boolean isNull(Object object) {
        return object == null;
    }

    /**
     * 判断一个对象是否非空
     *
     * @param object Object
     * @return true：非空 false：空
     */
    public static boolean isNotNull(Object object) {
        return !isNull(object);
    }

    /**
     * 判断一个对象是否是数组类型（Java基本型别的数组）
     *
     * @param object 对象
     * @return true：是数组 false：不是数组
     */
    public static boolean isArray(Object object) {
        return isNotNull(object) && object.getClass().isArray();
    }

    /**
     * 去空格
     */
    public static String trim(String str) {
        return (str == null ? "" : str.trim());
    }

    /**
     * 截取字符串
     *
     * @param str   字符串
     * @param start 开始
     * @return 结果
     */
    public static String substring(final String str, int start) {
        if (str == null) {
            return NULL_STR;
        }

        if (start < 0) {
            start = str.length() + start;
        }

        if (start < 0) {
            start = 0;
        }
        if (start > str.length()) {
            return NULL_STR;
        }

        return str.substring(start);
    }

    /**
     * 截取字符串
     *
     * @param str   字符串
     * @param start 开始
     * @param end   结束
     * @return 结果
     */
    public static String substring(final String str, int start, int end) {
        if (str == null) {
            return NULL_STR;
        }

        if (end < 0) {
            end = str.length() + end;
        }
        if (start < 0) {
            start = str.length() + start;
        }

        if (end > str.length()) {
            end = str.length();
        }

        if (start > end) {
            return NULL_STR;
        }

        if (start < 0) {
            start = 0;
        }
        if (end < 0) {
            end = 0;
        }

        return str.substring(start, end);
    }

    /**
     * 截取字符串
     *
     * @param str    字符串
     * @param length 截取的长度
     * @return 结果
     */
    public static String subLength(final String str, int length) {
        if (str == null || str.isEmpty()) {
            return NULL_STR;
        }

        if (length < 0) {
            length = str.length() + length;
        }
        if (length > str.length()) {
            length = str.length();
        }
        return str.substring(0, length);
    }

    /**
     * 格式化文本, {} 表示占位符<br>
     * 此方法只是简单将占位符 {} 按照顺序替换为参数<br>
     * 如果想输出 {} 使用 \\转义 { 即可，如果想输出 {} 之前的 \ 使用双转义符 \\\\ 即可<br>
     * 例：<br>
     * 通常使用：format("this is {} for {}", "a", "b") -> this is a for b<br>
     * 转义{}： format("this is \\{} for {}", "a", "b") -> this is \{} for a<br>
     * 转义\： format("this is \\\\{} for {}", "a", "b") -> this is \a for b<br>
     *
     * @param template 文本模板，被替换的部分用 {} 表示
     * @param params   参数值
     * @return 格式化后的文本
     */
    public static String format(String template, Object... params) {
        if (isEmpty(params) || isEmpty(template)) {
            return template;
        }
        return StrFormatter.format(template, params);
    }

    /**
     * 是否为http(s)://开头
     *
     * @param link 链接
     * @return 结果
     */
    public static boolean ishttp(String link) {
        return StringUtils.startsWithAny(link, Constants.HTTP, Constants.HTTPS);
    }

    /**
     * 字符串转set
     *
     * @param str 字符串
     * @param sep 分隔符
     * @return set集合
     */
    public static Set<String> str2Set(String str, String sep) {
        return new HashSet<>(str2List(str, sep, true, false));
    }

    /**
     * 字符串转list
     *
     * @param str         字符串
     * @param sep         分隔符
     * @param filterBlank 过滤纯空白
     * @param trim        去掉首尾空白
     * @return list集合
     */
    public static List<String> str2List(String str, String sep, boolean filterBlank, boolean trim) {
        List<String> list = new ArrayList<>();
        if (StringUtils.isEmpty(str)) {
            return list;
        }

        // 过滤空白字符串
        if (filterBlank && StringUtils.isBlank(str)) {
            return list;
        }
        String[] split = str.split(sep);
        for (String string : split) {
            if (filterBlank && StringUtils.isBlank(string)) {
                continue;
            }
            if (trim) {
                string = string.trim();
            }
            list.add(string);
        }

        return list;
    }

    /**
     * 判断给定的collection列表中是否包含数组array 判断给定的数组array中是否包含给定的元素value
     *
     * @param collection 给定的集合
     * @param array      给定的数组
     * @return boolean 结果
     */
    public static boolean containsAny(Collection<String> collection, String... array) {
        if (!isEmpty(collection) && !isEmpty(array)) {
            for (String str : array) {
                if (collection.contains(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 查找指定字符串是否包含指定字符串列表中的任意一个字符串同时串忽略大小写
     *
     * @param cs                  指定字符串
     * @param searchCharSequences 需要检查的字符串数组
     * @return 是否包含任意一个字符串
     */
    public static boolean containsAnyIgnoreCase(CharSequence cs, CharSequence... searchCharSequences) {
        if (isEmpty(cs) || isEmpty(searchCharSequences)) {
            return false;
        }
        for (CharSequence testStr : searchCharSequences) {
            if (containsIgnoreCase(cs, testStr)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 驼峰转下划线命名
     */
    public static String toUnderScoreCase(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        // 前置字符是否大写
        boolean preCharIsUpperCase;
        // 当前字符是否大写
        boolean curreCharIsUpperCase;
        // 下一字符是否大写
        boolean nexteCharIsUpperCase = true;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (i > 0) {
                preCharIsUpperCase = Character.isUpperCase(str.charAt(i - 1));
            } else {
                preCharIsUpperCase = false;
            }

            curreCharIsUpperCase = Character.isUpperCase(c);

            if (i < (str.length() - 1)) {
                nexteCharIsUpperCase = Character.isUpperCase(str.charAt(i + 1));
            }

            if (preCharIsUpperCase && curreCharIsUpperCase && !nexteCharIsUpperCase) {
                sb.append(SEPARATOR);
            } else if ((i != 0 && !preCharIsUpperCase) && curreCharIsUpperCase) {
                sb.append(SEPARATOR);
            }
            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    /**
     * 是否包含字符串
     *
     * @param str  验证字符串
     * @param strs 字符串组
     * @return 包含返回true
     */
    public static boolean inStringIgnoreCase(String str, String... strs) {
        if (str != null && strs != null) {
            for (String s : strs) {
                if (str.equalsIgnoreCase(trim(s))) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 删除最后一个字符串
     *
     * @param str  输入字符串
     * @param spit 以什么类型结尾的
     * @return 截取后的字符串
     */
    public static String lastStringDel(String str, String spit) {
        if (!StringUtils.isEmpty(str) && str.endsWith(spit)) {
            return str.subSequence(0, str.length() - 1).toString();
        }
        return str;
    }

    /**
     * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。 例如：HELLO_WORLD->HelloWorld
     *
     * @param name 转换前的下划线大写方式命名的字符串
     * @return 转换后的驼峰式命名的字符串
     */
    public static String convertToCamelCase(String name) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (name == null || name.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!name.contains("_")) {
            // 不含下划线，仅将首字母大写
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        // 用下划线将原始字符串分割
        String[] camels = name.split("_");
        for (String camel : camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 首字母大写
            result.append(camel.substring(0, 1).toUpperCase());
            result.append(camel.substring(1).toLowerCase());
        }
        return result.toString();
    }

    /**
     * 将下划线命名转换为驼峰命名
     *
     * @param str 下划线命名字符串
     * @return 驼峰命名字符串
     */
    public static String underscoreToCamelCase(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        StringBuilder result = new StringBuilder();
        boolean capitalizeNext = false;
        for (char c : str.toCharArray()) {
            if (c == '_') {
                capitalizeNext = true;
            } else if (capitalizeNext) {
                result.append(Character.toUpperCase(c));
                capitalizeNext = false;
            } else {
                result.append(Character.toLowerCase(c));
            }
        }
        return result.toString();
    }

    /**
     * 驼峰式命名法
     * 例如：user_name->userName
     */
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }
        if (s.indexOf(SEPARATOR) == -1) {
            return s;
        }
        s = s.toLowerCase();
        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 查找指定字符串是否匹配指定字符串列表中的任意一个字符串
     *
     * @param str  指定字符串
     * @param strs 需要检查的字符串数组
     * @return 是否匹配
     */
    public static boolean matches(String str, List<String> strs) {
        if (isEmpty(str) || isEmpty(strs)) {
            return false;
        }
        for (String pattern : strs) {
            if (isMatch(pattern, str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断url是否与规则配置:
     * ? 表示单个字符;
     * * 表示一层路径内的任意字符串，不可跨层级;
     * ** 表示任意层路径;
     *
     * @param pattern 匹配规则
     * @param url     需要匹配的url
     * @return 匹配结果
     */
    public static boolean isMatch(String pattern, String url) {
        AntPathMatcher matcher = new AntPathMatcher();
        return matcher.match(pattern, url);
    }

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) {
        return (T) obj;
    }

    /**
     * 数字左边补齐0，使之达到指定长度。注意，如果数字转换为字符串后，长度大于size，则只保留 最后size个字符。
     *
     * @param num  数字对象
     * @param size 字符串指定长度
     * @return 返回数字的字符串格式，该字符串为指定长度。
     */
    public static String padl(final Number num, final int size) {
        return padl(num.toString(), size, '0');
    }

    /**
     * 字符串左补齐。如果原始字符串s长度大于size，则只保留最后size个字符。
     *
     * @param s    原始字符串
     * @param size 字符串指定长度
     * @param c    用于补齐的字符
     * @return 返回指定长度的字符串，由原字符串左补齐或截取得到。
     */
    public static String padl(final String s, final int size, final char c) {
        final StringBuilder sb = new StringBuilder(size);
        if (s != null) {
            final int len = s.length();
            if (s.length() <= size) {
                sb.append(String.valueOf(c).repeat(size - len));
                sb.append(s);
            } else {
                return s.substring(len - size, len);
            }
        } else {
            sb.append(String.valueOf(c).repeat(Math.max(0, size)));
        }
        return sb.toString();
    }


    /**
     * 清理字符串特殊字符：移除特殊符号、颜文字和空格
     *
     * @param original 原始字符串
     * @return 清理后的名称
     */
    public static String cleanSpecialCharacters(String original) {
        if (original == null || original.isEmpty()) {
            return "";
        }
        // 移除特殊符号、颜文字和空格
        return original.replaceAll(REGEX_REMOVE, "");
    }

    /**
     * 分割字符串，支持多个分隔符
     *
     * @param str        原始字符串
     * @param delimiters 分隔符列表
     * @return 分割后的字符串列表
     */
    public static List<String> split(CharSequence str, String... delimiters) {
        if (delimiters.length == 0) {
            return Collections.emptyList();
        }
        if (str == null) {
            return Collections.emptyList();
        }
        // 将多个分隔符拼接成正则表达式
        String regex = Arrays.stream(delimiters)
                .filter(Objects::nonNull)
                .map(d -> "\\" + d) // 转义正则特殊字符
                .reduce((a, b) -> a + "|" + b)
                .orElse("");
        return Arrays.asList(str.toString().split(regex));
    }

    public static String rightPad(final String str, final int size, String padStr) {
        if (str == null) {
            return null;
        }
        if (isEmpty(padStr)) {
            padStr = SPACE;
        }
        final int padLen = padStr.length();
        final int strLen = str.length();
        final int pads = size - strLen;
        if (pads <= 0) {
            return str; // returns original String when possible
        }
        if (padLen == 1 && pads <= PAD_LIMIT) {
            return rightPad(str, size, padStr.charAt(0));
        }

        if (pads == padLen) {
            return str.concat(padStr);
        }
        if (pads < padLen) {
            return str.concat(padStr.substring(0, pads));
        }
        final char[] padding = new char[pads];
        final char[] padChars = padStr.toCharArray();
        for (int i = 0; i < pads; i++) {
            padding[i] = padChars[i % padLen];
        }
        return str.concat(new String(padding));
    }

    /**
     * 大小写反转
     *
     * @param str 字符串
     * @return 反转后的字符串
     */
    public static String swapCase(final String str) {
        if (isEmpty(str)) {
            return str;
        }

        final int strLen = str.length();
        final int[] newCodePoints = new int[strLen]; // cannot be longer than the char array
        int outOffset = 0;
        for (int i = 0; i < strLen; ) {
            final int oldCodepoint = str.codePointAt(i);
            final int newCodePoint;
            if (Character.isUpperCase(oldCodepoint) || Character.isTitleCase(oldCodepoint)) {
                newCodePoint = Character.toLowerCase(oldCodepoint);
            } else if (Character.isLowerCase(oldCodepoint)) {
                newCodePoint = Character.toUpperCase(oldCodepoint);
            } else {
                newCodePoint = oldCodepoint;
            }
            newCodePoints[outOffset++] = newCodePoint;
            i += Character.charCount(newCodePoint);
        }
        return new String(newCodePoints, 0, outOffset);
    }

    // ========================= 新增常用方法 =========================

    /**
     * 当字符串为 null、空串或仅空白时，返回默认值
     *
     * @param str         原字符串
     * @param defaultStr  默认值
     * @return 非空白字符串
     */
    public static String defaultIfBlank(final String str, final String defaultStr) {
        return isBlank(str) ? defaultStr : str;
    }

    /**
     * 将 null 转为空串 ""
     *
     * @param str 原字符串
     * @return 非 null 字符串
     */
    public static String nullToEmpty(final String str) {
        return str == null ? "" : str;
    }

    /**
     * 将空串或仅空白的字符串转换为 null
     *
     * @param str 原字符串
     * @return 若为空白则返回 null，否则原字符串
     */
    public static String emptyToNull(final String str) {
        return isBlank(str) ? null : str;
    }

    /**
     * 安全去除两端空白字符（输入为 null 时返回 null）
     *
     * @param str 原字符串
     * @return 去除空白后的字符串或 null
     */
    public static String safeTrim(final String str) {
        return str == null ? null : str.trim();
    }

    /**
     * 使用分隔符连接可迭代对象
     *
     * @param iterable  可迭代对象
     * @param delimiter 分隔符
     * @return 连接后的字符串（iterable 或元素全为空时返回空串）
     */
    public static String join(final Iterable<?> iterable, final String delimiter) {
        if (iterable == null) {
            return "";
        }
        String actualDelimiter = delimiter == null ? "" : delimiter;
        StringBuilder sb = new StringBuilder();
        Iterator<?> it = iterable.iterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (next != null) {
                sb.append(next);
            }
            if (it.hasNext()) {
                sb.append(actualDelimiter);
            }
        }
        return sb.toString();
    }

    /**
     * 是否等于任意一个（忽略大小写）
     *
     * @param str   原字符串
     * @param array 候选数组
     * @return 是否匹配
     */
    public static boolean equalsAnyIgnoreCase(final String str, final String... array) {
        if (array == null) {
            return false;
        }
        for (String s : array) {
            if (equalsIgnoreCase(str, s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否以任意一个前缀开头（忽略大小写）
     *
     * @param str    原字符串
     * @param prefixes 前缀数组
     * @return 是否匹配
     */
    public static boolean startsWithAnyIgnoreCase(final String str, final String... prefixes) {
        if (str == null || prefixes == null || prefixes.length == 0) {
            return false;
        }
        for (String p : prefixes) {
            if (p != null && startsWithIgnoreCase(str, p)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 超长省略显示（结尾使用省略号 ...）
     *
     * @param str   原字符串
     * @param maxLen 最大保留长度（必须 >= 0）
     * @return 处理后的字符串
     */
    public static String abbreviateWithEllipsis(final String str, final int maxLen) {
        if (str == null) {
            return null;
        }
        if (maxLen < 0) {
            throw new IllegalArgumentException("maxLen 不能小于 0");
        }
        if (str.length() <= maxLen) {
            return str;
        }
        if (maxLen <= 3) {
            // 不足放置完整省略号，直接截断
            return str.substring(0, maxLen);
        }
        return str.substring(0, maxLen - 3) + "...";
    }

    /**
     * 左侧补齐（别名：padStart）
     *
     * @param str  原字符串
     * @param size 目标长度
     * @param padChar 补齐字符
     * @return 处理后的字符串
     */
    public static String padStart(final String str, final int size, final char padChar) {
        return leftPad(str, size, padChar);
    }

    /**
     * 右侧补齐（别名：padEnd）
     *
     * @param str  原字符串
     * @param size 目标长度
     * @param padChar 补齐字符
     * @return 处理后的字符串
     */
    public static String padEnd(final String str, final int size, final char padChar) {
        return rightPad(str, size, padChar);
    }

    /**
     * 移除 Emoji 与非常见符号，仅保留中文、英文字母与数字
     *
     * @param str 原字符串
     * @return 清理后的字符串（null 输入返回空串）
     */
    public static String removeEmojiAndSymbols(final String str) {
        if (str == null || str.isEmpty()) {
            return "";
        }
        return str.replaceAll(REGEX_REMOVE, "");
    }

    // ============ 追加的常用方法开始 ============

    /**
     * 使用分隔符连接数组
     *
     * @param array     数组
     * @param delimiter 分隔符
     * @return 连接后的字符串
     */
    public static String join(final Object[] array, final String delimiter) {
        if (array == null || array.length == 0) {
            return "";
        }
        String actualDelimiter = delimiter == null ? "" : delimiter;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            Object o = array[i];
            if (o != null) {
                sb.append(o);
            }
            if (i < array.length - 1) {
                sb.append(actualDelimiter);
            }
        }
        return sb.toString();
    }


    // -------------- UUID 与随机字符串 --------------

    /**
     * 生成标准 UUID（含连字符）
     */
    public static String uuid() {
        return java.util.UUID.randomUUID().toString();
    }

    /**
     * 生成简化 UUID（去除连字符）
     */
    public static String simpleUuid() {
        return uuid().replace("-", "");
    }

    /**
     * 生成随机字母串（包含大小写字母）
     *
     * @param length 生成的字符串长度
     * @return 随机字母串
     */
    public static String randomAlphabetic(int length) {
        if (length <= 0) return "";
        String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder(length);
        ThreadLocalRandom r = ThreadLocalRandom.current();
        for (int i = 0; i < length; i++) {
            sb.append(letters.charAt(r.nextInt(letters.length())));
        }
        return sb.toString();
    }

    /**
     * 生成随机字母数字串
     *
     * @param length 生成的字符串长度
     * @return 随机字母数字串
     */
    public static String randomAlphanumeric(int length) {
        if (length <= 0) return "";
        String chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder(length);
        ThreadLocalRandom r = ThreadLocalRandom.current();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(r.nextInt(chars.length())));
        }
        return sb.toString();
    }

    // -------------- 数值判断与解析 --------------

    /**
     * 判断字符串是否为整数（可带正负号）
     *
     * @param str 待判断的字符串
     * @return true：是整数 false：不是整数
     */
    public static boolean isInteger(final String str) {
        if (isBlank(str)) {
            return false;
        }
        return str.matches("^[+-]?\\d+$");
    }

    /**
     * 判断字符串是否为小数或浮点数（可带正负号）
     *
     * @param str 待判断的字符串
     * @return true：是小数 false：不是小数
     */
    public static boolean isDecimal(final String str) {
        if (isBlank(str)) {
            return false;
        }
        return str.matches("^[+-]?\\d+(\\.\\d+)?$");
    }

    /**
     * 安全转换字符串为 int 类型，转换失败返回默认值
     *
     * @param str 待转换的字符串
     * @param defaultVal 默认值
     * @return 转换后的整数或默认值
     */
    public static int toInt(final String str, final int defaultVal) {
        if (isBlank(str)) {
            return defaultVal;
        }
        try {
            return Integer.parseInt(str.trim());
        } catch (Exception e) {
            return defaultVal;
        }
    }

    /**
     * 安全转换字符串为 long 类型，转换失败返回默认值
     *
     * @param str 待转换的字符串
     * @param defaultVal 默认值
     * @return 转换后的长整数或默认值
     */
    public static long toLong(final String str, final long defaultVal) {
        if (isBlank(str)) {
            return defaultVal;
        }
        try {
            return Long.parseLong(str.trim());
        } catch (Exception e) {
            return defaultVal;
        }
    }

    /**
     * 安全转换字符串为 double 类型，转换失败返回默认值
     *
     * @param str 待转换的字符串
     * @param defaultVal 默认值
     * @return 转换后的双精度浮点数或默认值
     */
    public static double toDouble(final String str, final double defaultVal) {
        if (isBlank(str)) {
            return defaultVal;
        }
        try {
            return Double.parseDouble(str.trim());
        } catch (Exception e) {
            return defaultVal;
        }
    }

    // -------------- 统计与规范化 --------------

    /**
     * 统计子串在字符串中出现的次数（不重叠计数）
     *
     * @param str 原字符串
     * @param sub 子串
     * @return 出现次数
     */
    public static int countOccurrences(final String str, final String sub) {
        if (isEmpty(str) || isEmpty(sub)) {
            return 0;
        }
        int count = 0;
        int fromIndex = 0;
        while (true) {
            int idx = str.indexOf(sub, fromIndex);
            if (idx == -1) break;
            count++;
            fromIndex = idx + sub.length();
        }
        return count;
    }

    /**
     * 归一化空白字符：压缩连续空白为单个空格，并去掉首尾空白
     *
     * @param str 原字符串
     * @return 归一化后的字符串
     */
    public static String normalizeWhitespace(final String str) {
        if (str == null) return null;
        return str.trim().replaceAll("\\s+", " ");
    }

    // -------------- 新增实用方法 --------------

    /**
     * 判断字符串是否为纯数字（不包含正负号、小数点）
     *
     * @param str 待判断的字符串
     * @return true：是纯数字 false：不是纯数字
     */
    public static boolean isNumeric(final String str) {
        if (isBlank(str)) {
            return false;
        }
        return str.matches("^\\d+$");
    }

    /**
     * 首字母转大写
     *
     * @param str 原字符串
     * @return 首字母大写的字符串
     */
    public static String capitalizeFirst(final String str) {
        if (isEmpty(str)) {
            return str;
        }
        if (str.length() == 1) {
            return str.toUpperCase();
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }

    /**
     * 首字母转小写
     *
     * @param str 原字符串
     * @return 首字母小写的字符串
     */
    public static String uncapitalizeFirst(final String str) {
        if (isEmpty(str)) {
            return str;
        }
        if (str.length() == 1) {
            return str.toLowerCase();
        }
        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }

    /**
     * 反转字符串
     *
     * @param str 原字符串
     * @return 反转后的字符串
     */
    public static String reverse(final String str) {
        if (str == null) {
            return null;
        }
        return new StringBuilder(str).reverse().toString();
    }

    /**
     * 移除字符串中的所有空白字符（包括空格、制表符、换行符等）
     *
     * @param str 原字符串
     * @return 移除空白字符后的字符串
     */
    public static String removeAllWhitespace(final String str) {
        if (str == null) {
            return null;
        }
        return str.replaceAll("\\s+", "");
    }

    /**
     * 判断字符串是否以任意一个后缀结尾
     *
     * @param str 原字符串
     * @param suffixes 后缀数组
     * @return true：以任意后缀结尾 false：不以任何后缀结尾
     */
    public static boolean endsWithAny(final String str, final String... suffixes) {
        if (str == null || suffixes == null || suffixes.length == 0) {
            return false;
        }
        for (String suffix : suffixes) {
            if (suffix != null && str.endsWith(suffix)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串是否以任意一个后缀结尾（忽略大小写）
     *
     * @param str 原字符串
     * @param suffixes 后缀数组
     * @return true：以任意后缀结尾 false：不以任何后缀结尾
     */
    public static boolean endsWithAnyIgnoreCase(final String str, final String... suffixes) {
        if (str == null || suffixes == null || suffixes.length == 0) {
            return false;
        }
        for (String suffix : suffixes) {
            if (suffix != null && endsWithIgnoreCase(str, suffix)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 重复字符串 n 次
     *
     * @param str 原字符串
     * @param count 重复次数
     * @return 重复后的字符串
     */
    public static String repeat(final String str, final int count) {
        if (str == null) {
            return null;
        }
        if (count <= 0) {
            return "";
        }
        if (count == 1) {
            return str;
        }
        return str.repeat(count);
    }

    /**
     * 安全的字符串比较（处理 null 情况）
     *
     * @param str1 字符串1
     * @param str2 字符串2
     * @return 比较结果：负数表示 str1 < str2，0 表示相等，正数表示 str1 > str2
     */
    public static int compareNullSafe(final String str1, final String str2) {
        if (str1 == str2) {
            return 0;
        }
        if (str1 == null) {
            return -1;
        }
        if (str2 == null) {
            return 1;
        }
        return str1.compareTo(str2);
    }

    /**
     * 字符串脱敏处理（中间部分用星号替换）
     *
     * @param str 原字符串
     * @param prefixLen 保留前面字符的长度
     * @param suffixLen 保留后面字符的长度
     * @return 脱敏后的字符串
     */
    public static String desensitize(final String str, final int prefixLen, final int suffixLen) {
        if (isEmpty(str)) {
            return str;
        }
        int len = str.length();
        if (len <= prefixLen + suffixLen) {
            return str;
        }
        String prefix = str.substring(0, prefixLen);
        String suffix = str.substring(len - suffixLen);
        int maskLen = len - prefixLen - suffixLen;
        return prefix + "*".repeat(maskLen) + suffix;
    }

    /**
     * 电话号码脱敏（保留前3位和后4位）
     *
     * @param phone 电话号码
     * @return 脱敏后的电话号码
     */
    public static String desensitizePhone(final String phone) {
        if (isEmpty(phone) || phone.length() < 7) {
            return phone;
        }
        return desensitize(phone, 3, 4);
    }

    /**
     * 身份证号脱敏（保留前6位和后4位）
     *
     * @param idCard 身份证号
     * @return 脱敏后的身份证号
     */
    public static String desensitizeIdCard(final String idCard) {
        if (isEmpty(idCard) || idCard.length() < 10) {
            return idCard;
        }
        return desensitize(idCard, 6, 4);
    }


}
