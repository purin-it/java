package com.example.demo;

import org.springframework.lang.Nullable;
import java.beans.PropertyEditorSupport;
import org.apache.commons.lang3.StringEscapeUtils;

public class DemoStringConverter extends PropertyEditorSupport {

    private final boolean emptyAsNull;

    /**
     * コンストラクタ
     * @param emptyAsNull 空文字をnullに変換するか
     */
    public DemoStringConverter(boolean emptyAsNull) {
        this.emptyAsNull = emptyAsNull;
    }

    @Override
    public void setAsText(@Nullable String text) {
        if (text == null) {
            setValue(null);
        }
        else {
            //文字列の空白を取り除き、サニタイジングする
            String value = StringEscapeUtils.escapeHtml4(text.trim());
            if (this.emptyAsNull && "".equals(value)) {
                setValue(null);
            }
            else {
                setValue(value);
            }
        }
    }

    @Override
    public String getAsText() {
        Object value = getValue();
        return (value != null ? value.toString() : "");
    }
}
