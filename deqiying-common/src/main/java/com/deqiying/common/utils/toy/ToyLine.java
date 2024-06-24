package com.deqiying.common.utils.toy;

/**
 * @author deqiying
 * @date 2023-01-30
 */
final class ToyLine {
    String key;
    String value;

    ToyTableRegion region;
    String prefix = " ";
    String suffix = " ";
    String fill = " ";

    String render() {
        StringBuilder bodyBuilder = new StringBuilder();

        // body line key
        bodyBuilder.append(prefix);
        bodyBuilder.append(key);

        int appendNum = region.keyMaxLen - key.length();
        append(bodyBuilder, fill, appendNum);

        // body line key
        String keyValueFix = "|";
        bodyBuilder.append(keyValueFix);
        bodyBuilder.append(prefix);
        bodyBuilder.append(value);

        appendNum = region.valueMaxLen - value.length() - keyValueFix.length();
        append(bodyBuilder, fill, appendNum);

        bodyBuilder.append(suffix);
        return bodyBuilder.toString();
    }

    private void append(StringBuilder builder, String c, int num) {
        builder.append(String.valueOf(c).repeat(Math.max(0, num + 1)));
    }
}
