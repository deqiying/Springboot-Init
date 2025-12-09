package com.deqiying.common.utils;

import com.deqiying.common.constant.Constants;
import com.deqiying.common.core.text.StrFormatter;
import org.springframework.util.AntPathMatcher;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

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
     * AntPathMatcher 实例复用，避免重复创建
     */
    private static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();

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

    // ========== 预编译的 Pattern，避免在热点方法中重复编译 ==========
    private static final Pattern REMOVE_PATTERN = Pattern.compile(REGEX_REMOVE);
    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s+");
    private static final Pattern INTEGER_PATTERN = Pattern.compile("^[+-]?\\d+$");
    private static final Pattern DECIMAL_PATTERN = Pattern.compile("^[+-]?\\d+(\\.\\d+)?$");
    private static final Pattern NUMERIC_PATTERN = Pattern.compile("^\\d+$");


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
     * 判断一个字符串是否为空串（包含仅空白字符的情况）
     *
     * @param str String
     * @return true：为空 false：非空
     */
    public static boolean isEmpty(String str) {
        // 使用 isBlank 避免 trim() 创建临时对象
        return str == null || str.isBlank();
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
     * @param start 开始（支持负数，表示从末尾倒数）
     * @param end   结束（支持负数，表示从末尾倒数）
     * @return 结果
     */
    public static String substring(final String str, int start, int end) {
        if (str == null) {
            return NULL_STR;
        }

        int len = str.length();

        // 处理负数索引
        if (start < 0) {
            start = Math.max(0, len + start);
        }
        if (end < 0) {
            end = Math.max(0, len + end);
        }

        // 边界修正
        start = Math.min(start, len);
        end = Math.min(end, len);

        if (start > end) {
            return NULL_STR;
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
        // isEmpty 已包含 isBlank 检查，无需重复判断
        if (StringUtils.isEmpty(str)) {
            return new ArrayList<>();
        }

        if (sep == null || sep.isEmpty()) {
            // 不指定分隔符，返回原字符串作为单元素
            return new ArrayList<>(Collections.singletonList(trim ? str.trim() : str));
        }

        // 使用 Pattern.quote 确保分隔符按字面含义分割（避免被当作正则）
        String[] split = str.split(Pattern.quote(sep));
        List<String> list = new ArrayList<>(split.length);
        for (String string : split) {
            if (filterBlank && StringUtils.isBlank(string)) {
                continue;
            }
            list.add(trim ? string.trim() : string);
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
     * 例如：userName -> user_name, XMLParser -> xml_parser
     */
    public static String toUnderScoreCase(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        int len = str.length();
        StringBuilder sb = new StringBuilder(len + len / 2);

        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            boolean isUpperCase = Character.isUpperCase(c);

            if (isUpperCase && i > 0) {
                boolean prevIsLower = Character.isLowerCase(str.charAt(i - 1));
                boolean nextIsLower = (i < len - 1) && Character.isLowerCase(str.charAt(i + 1));

                // 在大写字母前添加下划线的条件：
                // 1. 前一个字符是小写（如 userName 中的 N）
                // 2. 当前是连续大写的最后一个且后面是小写（如 XMLParser 中的 L）
                if (prevIsLower || nextIsLower) {
                    sb.append(SEPARATOR);
                }
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
        if (!StringUtils.isEmpty(str) && spit != null && !spit.isEmpty() && str.endsWith(spit)) {
            // 修复：之前只删除了最后一个字符，应该删除 spit 的长度
            return str.substring(0, str.length() - spit.length());
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
     * 驼峰式命名法（小驼峰）
     * 例如：user_name -> userName
     * 功能与 {@link #underscoreToCamelCase(String)} 相同
     *
     * @see #underscoreToCamelCase(String)
     */
    public static String toCamelCase(String s) {
        return underscoreToCamelCase(s);
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
        // 复用静态 AntPathMatcher 实例，避免重复创建对象
        return ANT_PATH_MATCHER.match(pattern, url);
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
        // 使用预编译的 Pattern 避免每次调用都编译正则
        return REMOVE_PATTERN.matcher(original).replaceAll("");
    }

    /**
     * 分割字符串，支持多个分隔符
     *
     * @param str        原始字符串
     * @param delimiters 分隔符列表
     * @return 分割后的字符串列表
     */
    public static List<String> split(CharSequence str, String... delimiters) {
        if (str == null || delimiters == null || delimiters.length == 0) {
            return Collections.emptyList();
        }
        // 将多个分隔符拼接成正则表达式，使用 Pattern.quote 正确转义
        String regex = Arrays.stream(delimiters)
                .filter(Objects::nonNull)
                .filter(d -> !d.isEmpty())
                .map(Pattern::quote)
                .reduce((a, b) -> a + "|" + b)
                .orElse("");
        if (regex.isEmpty()) {
            return Collections.singletonList(str.toString());
        }
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
        final int[] newCodePoints = new int[strLen];
        int outOffset = 0;
        for (int i = 0; i < strLen; ) {
            final int oldCodePoint = str.codePointAt(i);
            final int newCodePoint;
            if (Character.isUpperCase(oldCodePoint) || Character.isTitleCase(oldCodePoint)) {
                newCodePoint = Character.toLowerCase(oldCodePoint);
            } else if (Character.isLowerCase(oldCodePoint)) {
                newCodePoint = Character.toUpperCase(oldCodePoint);
            } else {
                newCodePoint = oldCodePoint;
            }
            newCodePoints[outOffset++] = newCodePoint;
            // 使用原始码点计算字符数，而非转换后的码点
            i += Character.charCount(oldCodePoint);
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
        boolean first = true;
        for (Object next : iterable) {
            if (!first) {
                sb.append(actualDelimiter);
            }
            if (next != null) {
                sb.append(next);
            }
            first = false;
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
        // 复用 Iterable 版本的 join 方法
        return join(Arrays.asList(array), delimiter);
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
        // 使用预编译 Pattern
        return INTEGER_PATTERN.matcher(str).matches();
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
        return DECIMAL_PATTERN.matcher(str).matches();
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
        // 使用预编译 Pattern 提高性能
        return WHITESPACE_PATTERN.matcher(str.trim()).replaceAll(" ");
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
        return WHITESPACE_PATTERN.matcher(str).replaceAll("");
    }

    /**
     * 判断字符串是否为回文串（忽略大小写和非字母数字字符）
     *
     * @param str 待判断的字符串
     * @return true：是回文 false：不是
     */
    public static boolean isPalindrome(final String str) {
        if (isEmpty(str)) {
            return true;
        }
        String cleaned = str.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        int len = cleaned.length();
        for (int i = 0; i < len / 2; i++) {
            if (cleaned.charAt(i) != cleaned.charAt(len - 1 - i)) {
                return false;
            }
        }
        return true;
    }

}
