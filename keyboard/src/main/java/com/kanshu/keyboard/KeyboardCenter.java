package com.kanshu.keyboard;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kanshu.keyboard.model.Key;

import java.lang.reflect.Type;
import java.util.List;

public final class KeyboardCenter {

    //键的类型为输入型字符
    public static final int KEY_TYPE_INPUT_CHAR = 0;
    //功能键
    public static final int KEY_TYPE_OPERATOR = 1;
    //占位
    public static final int KEY_TYPE_HOLDER = 2;
    //切换（比如 大小写切换键）
    public static final int KEY_TYPE_SWITCH = 3;

    //特殊的功能键的id
    //删除
    public static final int OPERATOR_KEY_ID_DEL = 101;
    //清空
    public static final int OPERATOR_KEY_ID_CLEAR = 102;
    //确定
    public static final int OPERATOR_KEY_ID_CONFIRM = 103;
    //大小写切换键
    public static final int OPERATOR_KEY_ID_CHANGE_CASE = 104;


    //关于大小写切换的标记
    //当前为大写
    public static final int CHANGE_CASE_CAPITAL = 0;
    //当前为小写
    public static final int CHANGE_CASE_LOWER = 1;

    private static final String NUMBER_PAN_JSON = "[\n" +
            "   {\"id\":1,\"text\":\"1\",\"type\":0},\n" +
            " {\"id\":2,\"text\":\"2\",\"type\":0},\n" +
            " {\"id\":3,\"text\":\"3\",\"type\":0},\n" +
            " {\"id\":101,\"text\":\"删除\",\"type\":1},\n" +
            " {\"id\":4,\"text\":\"4\",\"type\":0},\n" +
            " {\"id\":5,\"text\":\"5\",\"type\":0},\n" +
            " {\"id\":6,\"text\":\"6\",\"type\":0},\n" +
            " {\"id\":102,\"text\":\"清空\",\"type\":1},\n" +
            " {\"id\":7,\"text\":\"7\",\"type\":0},\n" +
            " {\"id\":8,\"text\":\"8\",\"type\":0},\n" +
            " {\"id\":9,\"text\":\"9\",\"type\":0},\n" +
            " {\"id\":103,\"text\":\"确定\",\"type\":1},\n" +
            " {\"id\":-1,\"text\":\"占位\",\"type\":2},\n" +
            " {\"id\":0,\"text\":\"0\",\"type\":0}\n" +
            "]";
    private static final String B_NUMBER_PAN_JSON = "[\n" +
            "   {\"id\":1,\"text\":\"1\",\"type\":0},\n" +
            " {\"id\":2,\"text\":\"2\",\"type\":0},\n" +
            " {\"id\":3,\"text\":\"3\",\"type\":0},\n" +
            " {\"id\":101,\"text\":\"删除\",\"type\":1},\n" +
            " {\"id\":4,\"text\":\"4\",\"type\":0},\n" +
            " {\"id\":5,\"text\":\"5\",\"type\":0},\n" +
            " {\"id\":6,\"text\":\"6\",\"type\":0},\n" +
            " {\"id\":102,\"text\":\"清空\",\"type\":1},\n" +
            " {\"id\":7,\"text\":\"7\",\"type\":0},\n" +
            " {\"id\":8,\"text\":\"8\",\"type\":0},\n" +
            " {\"id\":9,\"text\":\"9\",\"type\":0},\n" +
            " {\"id\":103,\"text\":\"确定\",\"type\":1},\n" +
            " {\"id\":10,\"text\":\"B\",\"type\":0},\n" +
            " {\"id\":0,\"text\":\"0\",\"type\":0}\n" +
            "]";
    private static final String C_NUMBER_PAN_JSON = "[\n" +
            "   {\"id\":1,\"text\":\"1\",\"type\":0},\n" +
            " {\"id\":2,\"text\":\"2\",\"type\":0},\n" +
            " {\"id\":3,\"text\":\"3\",\"type\":0},\n" +
            " {\"id\":101,\"text\":\"删除\",\"type\":1},\n" +
            " {\"id\":4,\"text\":\"4\",\"type\":0},\n" +
            " {\"id\":5,\"text\":\"5\",\"type\":0},\n" +
            " {\"id\":6,\"text\":\"6\",\"type\":0},\n" +
            " {\"id\":102,\"text\":\"清空\",\"type\":1},\n" +
            " {\"id\":7,\"text\":\"7\",\"type\":0},\n" +
            " {\"id\":8,\"text\":\"8\",\"type\":0},\n" +
            " {\"id\":9,\"text\":\"9\",\"type\":0},\n" +
            " {\"id\":103,\"text\":\"确定\",\"type\":1},\n" +
            " {\"id\":10,\"text\":\"C\",\"type\":0},\n" +
            " {\"id\":0,\"text\":\"0\",\"type\":0}\n" +
            "]";
    private static final String DOT_NUMBER_PAN_JSON = "[\n" +
            "   {\"id\":1,\"text\":\"1\",\"type\":0},\n" +
            " {\"id\":2,\"text\":\"2\",\"type\":0},\n" +
            " {\"id\":3,\"text\":\"3\",\"type\":0},\n" +
            " {\"id\":101,\"text\":\"删除\",\"type\":1},\n" +
            " {\"id\":4,\"text\":\"4\",\"type\":0},\n" +
            " {\"id\":5,\"text\":\"5\",\"type\":0},\n" +
            " {\"id\":6,\"text\":\"6\",\"type\":0},\n" +
            " {\"id\":102,\"text\":\"清空\",\"type\":1},\n" +
            " {\"id\":7,\"text\":\"7\",\"type\":0},\n" +
            " {\"id\":8,\"text\":\"8\",\"type\":0},\n" +
            " {\"id\":9,\"text\":\"9\",\"type\":0},\n" +
            " {\"id\":103,\"text\":\"确定\",\"type\":1},\n" +
            " {\"id\":10,\"text\":\".\",\"type\":0},\n" +
            " {\"id\":0,\"text\":\"0\",\"type\":0}\n" +
            "]";

    private static final String LOWER_LETTER_PAN_JSON = "[\n" +
            " {\"id\":1,\"text\":\"a\",\"type\":0},\n" +
            " {\"id\":2,\"text\":\"b\",\"type\":0},\n" +
            " {\"id\":3,\"text\":\"c\",\"type\":0},\n" +
            " {\"id\":4,\"text\":\"d\",\"type\":0},\n" +
            " {\"id\":5,\"text\":\"e\",\"type\":0},\n" +
            " {\"id\":6,\"text\":\"f\",\"type\":0},\n" +
            " {\"id\":7,\"text\":\"g\",\"type\":0},\n" +
            " {\"id\":8,\"text\":\"h\",\"type\":0},\n" +
            " {\"id\":9,\"text\":\"i\",\"type\":0},\n" +
            " {\"id\":10,\"text\":\"j\",\"type\":0},\n" +
            " {\"id\":11,\"text\":\"k\",\"type\":0},\n" +
            " {\"id\":12,\"text\":\"l\",\"type\":0},\n" +
            " {\"id\":13,\"text\":\"m\",\"type\":0},\n" +
            " {\"id\":14,\"text\":\"n\",\"type\":0},\n" +
            " {\"id\":15,\"text\":\"o\",\"type\":0},\n" +
            " {\"id\":16,\"text\":\"p\",\"type\":0},\n" +
            " {\"id\":17,\"text\":\"q\",\"type\":0},\n" +
            " {\"id\":18,\"text\":\"r\",\"type\":0},\n" +
            " {\"id\":19,\"text\":\"s\",\"type\":0},\n" +
            " {\"id\":20,\"text\":\"t\",\"type\":0},\n" +
            " {\"id\":21,\"text\":\"u\",\"type\":0},\n" +
            " {\"id\":22,\"text\":\"v\",\"type\":0},\n" +
            " {\"id\":23,\"text\":\"w\",\"type\":0},\n" +
            " {\"id\":24,\"text\":\"x\",\"type\":0},\n" +
            " {\"id\":25,\"text\":\"y\",\"type\":0},\n" +
            " {\"id\":26,\"text\":\"z\",\"type\":0},\n" +
            " {\"id\":101,\"text\":\"删除\",\"type\":1},\n" +
            " {\"id\":102,\"text\":\"清空\",\"type\":1},\n" +
            " {\"id\":103,\"text\":\"确定\",\"type\":1},\n" +
            " {\"id\":104,\"text\":\"大写\",\"type\":3}\n" +
            "]";

    private static final String CAPITAL_LETTER_PAN_JSON = "[\n" +
            " {\"id\":1,\"text\":\"a\",\"type\":0},\n" +
            " {\"id\":2,\"text\":\"b\",\"type\":0},\n" +
            " {\"id\":3,\"text\":\"c\",\"type\":0},\n" +
            " {\"id\":4,\"text\":\"d\",\"type\":0},\n" +
            " {\"id\":5,\"text\":\"e\",\"type\":0},\n" +
            " {\"id\":6,\"text\":\"f\",\"type\":0},\n" +
            " {\"id\":7,\"text\":\"g\",\"type\":0},\n" +
            " {\"id\":8,\"text\":\"h\",\"type\":0},\n" +
            " {\"id\":9,\"text\":\"i\",\"type\":0},\n" +
            " {\"id\":10,\"text\":\"j\",\"type\":0},\n" +
            " {\"id\":11,\"text\":\"k\",\"type\":0},\n" +
            " {\"id\":12,\"text\":\"l\",\"type\":0},\n" +
            " {\"id\":13,\"text\":\"m\",\"type\":0},\n" +
            " {\"id\":14,\"text\":\"n\",\"type\":0},\n" +
            " {\"id\":15,\"text\":\"o\",\"type\":0},\n" +
            " {\"id\":16,\"text\":\"p\",\"type\":0},\n" +
            " {\"id\":17,\"text\":\"q\",\"type\":0},\n" +
            " {\"id\":18,\"text\":\"r\",\"type\":0},\n" +
            " {\"id\":19,\"text\":\"s\",\"type\":0},\n" +
            " {\"id\":20,\"text\":\"t\",\"type\":0},\n" +
            " {\"id\":21,\"text\":\"u\",\"type\":0},\n" +
            " {\"id\":22,\"text\":\"v\",\"type\":0},\n" +
            " {\"id\":23,\"text\":\"w\",\"type\":0},\n" +
            " {\"id\":24,\"text\":\"x\",\"type\":0},\n" +
            " {\"id\":25,\"text\":\"y\",\"type\":0},\n" +
            " {\"id\":26,\"text\":\"z\",\"type\":0},\n" +
            " {\"id\":101,\"text\":\"删除\",\"type\":1},\n" +
            " {\"id\":102,\"text\":\"清空\",\"type\":1},\n" +
            " {\"id\":103,\"text\":\"确定\",\"type\":1},\n" +
            " {\"id\":104,\"text\":\"大写\",\"type\":3}\n" +
            "]";
    private static final String NUMBER_LOWER_LETTER_PAN_JSON = "[\n" +
            " {\"id\":1,\"text\":\"a\",\"type\":0},\n" +
            " {\"id\":2,\"text\":\"b\",\"type\":0},\n" +
            " {\"id\":3,\"text\":\"c\",\"type\":0},\n" +
            " {\"id\":4,\"text\":\"d\",\"type\":0},\n" +
            " {\"id\":5,\"text\":\"e\",\"type\":0},\n" +
            " {\"id\":6,\"text\":\"f\",\"type\":0},\n" +
            " {\"id\":7,\"text\":\"g\",\"type\":0},\n" +
            " {\"id\":8,\"text\":\"h\",\"type\":0},\n" +
            " {\"id\":9,\"text\":\"i\",\"type\":0},\n" +
            " {\"id\":10,\"text\":\"j\",\"type\":0},\n" +
            " {\"id\":11,\"text\":\"k\",\"type\":0},\n" +
            " {\"id\":12,\"text\":\"l\",\"type\":0},\n" +
            " {\"id\":13,\"text\":\"m\",\"type\":0},\n" +
            " {\"id\":14,\"text\":\"n\",\"type\":0},\n" +
            " {\"id\":15,\"text\":\"o\",\"type\":0},\n" +
            " {\"id\":16,\"text\":\"p\",\"type\":0},\n" +
            " {\"id\":17,\"text\":\"q\",\"type\":0},\n" +
            " {\"id\":18,\"text\":\"r\",\"type\":0},\n" +
            " {\"id\":19,\"text\":\"s\",\"type\":0},\n" +
            " {\"id\":20,\"text\":\"t\",\"type\":0},\n" +
            " {\"id\":21,\"text\":\"u\",\"type\":0},\n" +
            " {\"id\":22,\"text\":\"v\",\"type\":0},\n" +
            " {\"id\":23,\"text\":\"w\",\"type\":0},\n" +
            " {\"id\":24,\"text\":\"x\",\"type\":0},\n" +
            " {\"id\":25,\"text\":\"y\",\"type\":0},\n" +
            " {\"id\":26,\"text\":\"z\",\"type\":0},\n" +
            " {\"id\":27,\"text\":\"0\",\"type\":0},\n" +
            " {\"id\":28,\"text\":\"1\",\"type\":0},\n" +
            " {\"id\":29,\"text\":\"2\",\"type\":0},\n" +
            " {\"id\":30,\"text\":\"3\",\"type\":0},\n" +
            " {\"id\":31,\"text\":\"4\",\"type\":0},\n" +
            " {\"id\":31,\"text\":\"5\",\"type\":0},\n" +
            " {\"id\":32,\"text\":\"6\",\"type\":0},\n" +
            " {\"id\":33,\"text\":\"7\",\"type\":0},\n" +
            " {\"id\":34,\"text\":\"8\",\"type\":0},\n" +
            " {\"id\":35,\"text\":\"9\",\"type\":0},\n" +
            " {\"id\":101,\"text\":\"删除\",\"type\":1},\n" +
            " {\"id\":102,\"text\":\"清空\",\"type\":1},\n" +
            " {\"id\":103,\"text\":\"确定\",\"type\":1},\n" +
            " {\"id\":104,\"text\":\"大写\",\"type\":3}\n" +
            "]";
    private static final String EMAIL_PAN_JSON = "[\n" +
            " {\"id\":1,\"text\":\"a\",\"type\":0},\n" +
            " {\"id\":2,\"text\":\"b\",\"type\":0},\n" +
            " {\"id\":3,\"text\":\"c\",\"type\":0},\n" +
            " {\"id\":4,\"text\":\"d\",\"type\":0},\n" +
            " {\"id\":5,\"text\":\"e\",\"type\":0},\n" +
            " {\"id\":6,\"text\":\"f\",\"type\":0},\n" +
            " {\"id\":7,\"text\":\"g\",\"type\":0},\n" +
            " {\"id\":8,\"text\":\"h\",\"type\":0},\n" +
            " {\"id\":9,\"text\":\"i\",\"type\":0},\n" +
            " {\"id\":10,\"text\":\"j\",\"type\":0},\n" +
            " {\"id\":11,\"text\":\"k\",\"type\":0},\n" +
            " {\"id\":12,\"text\":\"l\",\"type\":0},\n" +
            " {\"id\":13,\"text\":\"m\",\"type\":0},\n" +
            " {\"id\":14,\"text\":\"n\",\"type\":0},\n" +
            " {\"id\":15,\"text\":\"o\",\"type\":0},\n" +
            " {\"id\":16,\"text\":\"p\",\"type\":0},\n" +
            " {\"id\":17,\"text\":\"q\",\"type\":0},\n" +
            " {\"id\":18,\"text\":\"r\",\"type\":0},\n" +
            " {\"id\":19,\"text\":\"s\",\"type\":0},\n" +
            " {\"id\":20,\"text\":\"t\",\"type\":0},\n" +
            " {\"id\":21,\"text\":\"u\",\"type\":0},\n" +
            " {\"id\":22,\"text\":\"v\",\"type\":0},\n" +
            " {\"id\":23,\"text\":\"w\",\"type\":0},\n" +
            " {\"id\":24,\"text\":\"x\",\"type\":0},\n" +
            " {\"id\":25,\"text\":\"y\",\"type\":0},\n" +
            " {\"id\":26,\"text\":\"z\",\"type\":0},\n" +
            " {\"id\":27,\"text\":\"0\",\"type\":0},\n" +
            " {\"id\":28,\"text\":\"1\",\"type\":0},\n" +
            " {\"id\":29,\"text\":\"2\",\"type\":0},\n" +
            " {\"id\":30,\"text\":\"3\",\"type\":0},\n" +
            " {\"id\":31,\"text\":\"4\",\"type\":0},\n" +
            " {\"id\":31,\"text\":\"5\",\"type\":0},\n" +
            " {\"id\":32,\"text\":\"6\",\"type\":0},\n" +
            " {\"id\":33,\"text\":\"7\",\"type\":0},\n" +
            " {\"id\":34,\"text\":\"8\",\"type\":0},\n" +
            " {\"id\":35,\"text\":\"9\",\"type\":0},\n" +
            " {\"id\":36,\"text\":\"@\",\"type\":0},\n" +
            " {\"id\":37,\"text\":\".\",\"type\":0},\n" +
            " {\"id\":38,\"text\":\"-\",\"type\":0},\n" +
            " {\"id\":39,\"text\":\"_\",\"type\":0},\n" +
            " {\"id\":-1,\"text\":\"占位\",\"type\":2},\n" +
            " {\"id\":-1,\"text\":\"占位\",\"type\":2},\n" +
            " {\"id\":101,\"text\":\"删除\",\"type\":1},\n" +
            " {\"id\":102,\"text\":\"清空\",\"type\":1},\n" +
            " {\"id\":103,\"text\":\"确定\",\"type\":1},\n" +
            " {\"id\":104,\"text\":\"大写\",\"type\":3}\n" +
            "]";
    public static final String PART_LOWER_LETTER_JSON = "[\n" +
            " {\"id\":1,\"text\":\"a\",\"type\":0},\n" +
            " {\"id\":2,\"text\":\"b\",\"type\":0},\n" +
            " {\"id\":3,\"text\":\"c\",\"type\":0},\n" +
            " {\"id\":4,\"text\":\"d\",\"type\":0},\n" +
            " {\"id\":5,\"text\":\"e\",\"type\":0},\n" +
            " {\"id\":6,\"text\":\"f\",\"type\":0},\n" +
            " {\"id\":7,\"text\":\"g\",\"type\":0},\n" +
            " {\"id\":8,\"text\":\"h\",\"type\":0},\n" +
            " {\"id\":9,\"text\":\"i\",\"type\":0},\n" +
            " {\"id\":10,\"text\":\"j\",\"type\":0},\n" +
            " {\"id\":11,\"text\":\"k\",\"type\":0},\n" +
            " {\"id\":12,\"text\":\"l\",\"type\":0},\n" +
            " {\"id\":13,\"text\":\"m\",\"type\":0},\n" +
            " {\"id\":14,\"text\":\"n\",\"type\":0},\n" +
            " {\"id\":15,\"text\":\"o\",\"type\":0},\n" +
            " {\"id\":16,\"text\":\"p\",\"type\":0},\n" +
            " {\"id\":17,\"text\":\"q\",\"type\":0},\n" +
            " {\"id\":18,\"text\":\"r\",\"type\":0},\n" +
            " {\"id\":19,\"text\":\"s\",\"type\":0},\n" +
            " {\"id\":20,\"text\":\"t\",\"type\":0},\n" +
            " {\"id\":21,\"text\":\"u\",\"type\":0},\n" +
            " {\"id\":22,\"text\":\"v\",\"type\":0},\n" +
            " {\"id\":23,\"text\":\"w\",\"type\":0},\n" +
            " {\"id\":24,\"text\":\"x\",\"type\":0},\n" +
            " {\"id\":25,\"text\":\"y\",\"type\":0},\n" +
            " {\"id\":26,\"text\":\"z\",\"type\":0}\n" +
            "]";
    public static final String PART_CAPITAL_LETTER_JSON = "[\n" +
            " {\"id\":1,\"text\":\"A\",\"type\":0},\n" +
            " {\"id\":2,\"text\":\"B\",\"type\":0},\n" +
            " {\"id\":3,\"text\":\"C\",\"type\":0},\n" +
            " {\"id\":4,\"text\":\"D\",\"type\":0},\n" +
            " {\"id\":5,\"text\":\"E\",\"type\":0},\n" +
            " {\"id\":6,\"text\":\"F\",\"type\":0},\n" +
            " {\"id\":7,\"text\":\"G\",\"type\":0},\n" +
            " {\"id\":8,\"text\":\"H\",\"type\":0},\n" +
            " {\"id\":9,\"text\":\"I\",\"type\":0},\n" +
            " {\"id\":10,\"text\":\"J\",\"type\":0},\n" +
            " {\"id\":11,\"text\":\"K\",\"type\":0},\n" +
            " {\"id\":12,\"text\":\"L\",\"type\":0},\n" +
            " {\"id\":13,\"text\":\"M\",\"type\":0},\n" +
            " {\"id\":14,\"text\":\"N\",\"type\":0},\n" +
            " {\"id\":15,\"text\":\"O\",\"type\":0},\n" +
            " {\"id\":16,\"text\":\"P\",\"type\":0},\n" +
            " {\"id\":17,\"text\":\"Q\",\"type\":0},\n" +
            " {\"id\":18,\"text\":\"R\",\"type\":0},\n" +
            " {\"id\":19,\"text\":\"S\",\"type\":0},\n" +
            " {\"id\":20,\"text\":\"T\",\"type\":0},\n" +
            " {\"id\":21,\"text\":\"U\",\"type\":0},\n" +
            " {\"id\":22,\"text\":\"V\",\"type\":0},\n" +
            " {\"id\":23,\"text\":\"W\",\"type\":0},\n" +
            " {\"id\":24,\"text\":\"X\",\"type\":0},\n" +
            " {\"id\":25,\"text\":\"Y\",\"type\":0},\n" +
            " {\"id\":26,\"text\":\"Z\",\"type\":0}\n" +
            "]";

    /**
     * 输入类型
     */
    public enum InputMethod {
        //纯数字
        ONLY_NUMBER(NUMBER_PAN_JSON, 4),
        //含有大写字母B和数字
        B_NUMBER(B_NUMBER_PAN_JSON, 4),
        //含有大写字母C和数字
        C_NUMBER(C_NUMBER_PAN_JSON, 4),
        //含有小数点.和数字
        DOT_NUMBER(DOT_NUMBER_PAN_JSON, 4),
        //纯字母(大写)
        LETTER_CAPITAL(CAPITAL_LETTER_PAN_JSON, 6),
        //纯字母(小写)
        LETTER_LOWER(LOWER_LETTER_PAN_JSON, 6),
        //数字和字母(小写)
        NUMBER_LOWER_LETTER(NUMBER_LOWER_LETTER_PAN_JSON, 6),
        //邮箱(小写)
        EMAIL(EMAIL_PAN_JSON, 6);


        private static final String TAG = "InputMethod";
        private String keyJson;
        private int spanCount;

        public List<Key> getKeyList() {
            Log.i(TAG, "getKeyList:" + keyJson);
            Type type = new TypeToken<List<Key>>() {
            }.getType();
            return new Gson().fromJson(keyJson, type);
        }

        public int getSpanCount() {
            return spanCount;
        }


        /**
         * @param keyJson   键盘 按键
         * @param spanCount 键盘列数
         */
        InputMethod(String keyJson, int spanCount) {
            this.keyJson = keyJson;
            this.spanCount = spanCount;
        }

    }
}
