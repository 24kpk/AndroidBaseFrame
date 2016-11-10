package com.baseframe.core.utils;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 操作输入法的工具类。可以方便的关闭和显示输入法.
 */
public final class KeyBoardUtil {
    private KeyBoardUtil() { }

    /**
     * 强制显示输入法
     */
    public static void show(Activity activity) {
        show(activity.getWindow().getCurrentFocus());
    }

    public static void show(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    /**
     * 强制关闭输入法
     */
    public static void hide(Activity activity) {
        hide(activity.getWindow().getCurrentFocus());
    }

    public static void hide(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 如果输入法已经显示，那么就隐藏它；如果输入法现在没显示，那么就显示它
     */
    public static void toggle(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }


    /**
     * 切换为英文输入模式
     */
    public static void changeToEnglishInputType(EditText editText) {
        editText.setInputType(EditorInfo.TYPE_TEXT_VARIATION_URI);
    }

    /**
     * 切换为中文输入模式
     */
    public static void changeToChineseInputType(EditText editText) {
        editText.setInputType(EditorInfo.TYPE_CLASS_TEXT);
    }

    /**
     * 监听输入法的回车按键
     */
    public static void setEnterKeyListener(EditText editText, final View.OnClickListener listener) {
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // 这两个条件必须同时成立，如果仅仅用了enter判断，就会执行两次
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    listener.onClick(v);
                    return true;
                }
                return false;
            }
        });
    }
}
