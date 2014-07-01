/**
 * Copyright (c) 2013 上海杰亦特信息科技有限公司-版权所有
 */

package com.jit.lib.util;

import android.app.Activity;
import android.graphics.Paint;
import android.text.Editable;
import android.view.View;
import android.widget.TextView;

/**
 * TextView工具类
 * @author Rocky
 *
 */
public class TextViewUtil extends ViewUtil
{
    /**
     * 设置textView 内容
     *
     * @param container
     * @param textViewId textVie id
     * @param text       值
     */
    public static TextView setText(Object container, int textViewId, CharSequence text)
    {
        TextView textView = (TextView) ViewUtil.findViewById(container, textViewId);
        if (textView != null) {
            textView.setText(text);
        }
        return textView;
    }

    public static TextView setText(Object container, int textViewId, int textId)
    {
        TextView textView = (TextView) ViewUtil.findViewById(container, textViewId);
        if (textView != null) {
            textView.setText(textId);
        }
        return textView;
    }

    /**
     * 获取 textView 的 内容
     *
     * @param container
     * @param textViewId
     */
    public static CharSequence getText(Object container, int textViewId)
    {
        TextView textView = (TextView) ViewUtil.findViewById(container, textViewId);
        if (textView != null) {
            return textView.getText();
        }
        return null;
    }


    public static String getTextString(TextView textView)
    {
        if (textView != null) {
            CharSequence charSequence = textView.getText();
            if (charSequence instanceof String) {
                return ((String) charSequence).trim();
            }
            else if (charSequence instanceof Editable) {
                Editable editable = (Editable) charSequence;
                return editable.toString().trim();
            }
            else {
                return charSequence.toString().trim();
            }
        }
        return null;
    }

    public static String getTextString(Object activity, int editTextId)
    {
        TextView textView = (TextView) ViewUtil.findViewById(activity, editTextId);
        return getTextString(textView);
    }

    public static String getTextString(Object activity, int editTextId, String defValue)
    {
        return getTextString(activity, editTextId);
    }

    public static int getTextInt(Object activity, int editTextId)
    {
        return getTextInt(activity, editTextId);
    }


    @Deprecated
    public static String getEditTextText(Object activity, int editTextId)
    {
        return getTextString(activity, editTextId);
    }

    /**
     * 设置textView 内容
     *
     * @param activity
     * @param textViewId textVie id
     * @param text       值
     * @param tf         字体样式
     */
    public static TextView setText(Object activity, int textViewId, CharSequence text, android.graphics.Typeface tf)
    {
        TextView textView = (TextView) ViewUtil.findViewById(activity, textViewId);
        if (textView != null) {
            textView.setText(String.valueOf(text));
            textView.setTypeface(tf);
        }
        return textView;
    }

    public static TextView setTextColor(Object activity, int textViewId, int color)
    {
        TextView textView = (TextView) ViewUtil.findViewById(activity, textViewId);
        if (textView != null) {
            textView.setTextColor(color);
        }
        return textView;
    }

    public static TextView setTextColorResource(Activity activity, int textViewId, int colorRes)
    {
        TextView textView = (TextView) activity.findViewById(textViewId);
        if (textView != null) {
            textView.setTextColor(activity.getResources().getColor(colorRes));
        }
        return textView;
    }

    public static TextView setTextColorResource(View view, int textViewId, int colorRes)
    {
        TextView textView = (TextView) view.findViewById(textViewId);
        if (textView != null) {
            textView.setTextColor(view.getResources().getColor(colorRes));
        }
        return textView;
    }

    /**
     * 设置中划线
     * @param view
     * @param textViewId
     * @return
     */
    public static TextView setTextCenterLine(View view, int textViewId){
        TextView textView = (TextView) view.findViewById(textViewId);
        if (textView != null) {
            textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);;
        }
        return textView;
    }

    /**
     *  设置中划线
     * @param view
     * @return
     */
    public static TextView setTextCenterLine(TextView view){
        if (view != null) {
            view.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);;
        }
        return view;
    }


}
