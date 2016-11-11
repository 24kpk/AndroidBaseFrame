package com.baseframe.core.utils;

import android.content.res.ColorStateList;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * author: C_CHEUNG
 * created on: 2016/11/11
 * description:改变字符串显示样式
 */
public class TextChange {
    private String mRes;//整体文本
    private SpannableString mSpannableString;

    /**
     * 设置需要的文本
     *
     * @param res 需要的文本
     */
    public TextChange prepare(String res) {
        this.mRes = res;
        mSpannableString = new SpannableString(res);
        return this;
    }


    /**
     * 文字改变
     *
     * @param res 资源的文字
     * @return 改变后的字符串
     */
    public String changeText(String res, Object... keys) {
        return String.format(res, keys);
    }

    /**
     * 改变文本指定文字的颜色
     *
     * @param key   指定改变的文字
     * @param color 指定改变文字的颜色
     * @return 改变后的文本
     */
    public TextChange changeKeyColorForString(String key, int color) {
        change(key, new ForegroundColorSpan(color));
        return this;
    }

    /**
     * 改变文本指定文字的大小，相对于这段文本的文字大小做改变
     *
     * @param key  指定的关键字
     * @param Rate 放大的比例
     * @return 改变后的文本
     */
    public TextChange changeKeySizeForRelative(String key, float Rate) {
        change(key, new RelativeSizeSpan(Rate));
        return this;
    }

    /**
     * 改变文本指定文字的大小，设置想显示的大小，
     * <P>
     * eg: size=12sp 那么显示的指定文字大小就为12sp
     * </p>
     *
     * @param key  指定的关键字
     * @param size 指定文字的大小 单位是像素
     * @return 返回改变后的文本
     */
    public TextChange changeKeySizeForAbsolute(String key, int size) {
        change(key, new AbsoluteSizeSpan(size));
        return this;
    }

    /**
     * 改变文本指定关键字的背景色
     *
     * @param key   指定的关键字
     * @param color 指定文字的背景色
     * @return 返回改变后的文本
     */
    public TextChange changeKeyBgColorForString(String key, int color) {
        change(key, new BackgroundColorSpan(color));
        return this;
    }

    /**
     * 改变文本指定关键字的样式
     *
     * @param key   指定的关键字
     * @param style 指定关键字的样式
     * @return 返回改变后的文本
     */
    public TextChange changeKeyStyleForString(String key, int style) {
        change(key, new StyleSpan(style));
        return this;
    }

    /**
     * 给文本指定的关键字设置下划线
     *
     * @param key 指定的关键字
     * @return 返回改变后的文本
     */
    public TextChange keySetUnderlineForStirng(String key) {
        change(key, new UnderlineSpan());
        return this;
    }

    /**
     * 给文本指定关键字设置删除线
     *
     * @param key 指定的关键字
     * @return 返回改变后的文本
     */
    public TextChange keySetDelLineForString(String key) {
        change(key, new StrikethroughSpan());
        return this;
    }

    /**
     * 给文本指定的文字设置下标
     *
     * @param key 指定的关键字
     * @return 返回改变的文本
     */
    public TextChange keySetSubForString(String key) {
        change(key, new SubscriptSpan());
        return this;
    }

    /**
     * 给文本指定的文字设置上标
     *
     * @param key 指定的关键字
     * @return 返回改变的文本
     */
    public TextChange keySetSupForString(String key) {
        change(key, new SuperscriptSpan());
        return this;
    }

    /**
     * 给文本的指定文字设置超链接
     *
     * @param key     指定的关键字
     * @param htmlAdd 超链接地址
     *                <p/>
     *                1, 打电话 tel:123456789
     *                <p/>
     *                2, 发邮件 mailto:webmaster@google.com
     *                <p/>
     *                3, 网页 http://www.baidu.com
     *                <p/>
     *                4, 发短信 sms:4155551212
     *                <p/>
     *                5, 发彩信 mms:4155551212
     *                <p/>
     *                6, 地图 geo:38.899533,-77.036476
     * @return
     */
    public TextChange keySetHtmlForString(String key,
                                          String htmlAdd) {
        change(key, new URLSpan(htmlAdd));
        return this;
    }

    /**
     * 改变文本指定文字的所有属性
     *
     * @param key       指定文字
     * @param family    设置超链接
     * @param style     设置字体样式
     * @param size      设置字体大小
     * @param color     设置字体颜色
     * @param linkColor 设置链接颜色
     * @return 返回改变后的文本
     */
    public TextChange keySetAllAttForString(String key,
                                            String family, int style, int size, ColorStateList color, ColorStateList linkColor) {
        change(key, new TextAppearanceSpan(family, style, size, color, linkColor));
        return this;
    }

    /**
     * 改变的核心代码
     *
     * @param key 指定的关键字
     * @param obj 指定改变的类型
     * @return 返回改变的
     */
    private void change(String key, Object obj) {
        try {

            Pattern pattern = Pattern.compile(key);
            Matcher matcher = pattern.matcher(mSpannableString);
            while (matcher.find()) {
                int start = matcher.start();
                int end = matcher.end();
                mSpannableString.setSpan(obj, start, end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        } catch (Exception e) {
        }
    }

    public SpannableString end() {
        return mSpannableString;
    }
    /**
     * TextChange textChange = new TextChange().prepare(price).changeKeySizeForRelative("￥", 0.8f);
     * priceTv.setText(textChange.end());
     */
}
