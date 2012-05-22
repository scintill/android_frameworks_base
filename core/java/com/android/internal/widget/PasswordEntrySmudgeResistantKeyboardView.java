package com.android.internal.widget;

import java.util.Arrays;
import java.util.Collections;

import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.util.AttributeSet;

public class PasswordEntrySmudgeResistantKeyboardView extends PasswordEntryKeyboardView {

    public PasswordEntrySmudgeResistantKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PasswordEntrySmudgeResistantKeyboardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setKeyboard(Keyboard keyboard) {
        // if numeric, randomize the 1-9 and 0 keys. TODO better way to detect numeric?
        if (keyboard.getKeys().size() == 12) {
            // gather up keys
            Key[] keys = new Key[10];
            int i = 0;
            for (Key key : keyboard.getKeys()) {
                if (key.codes.length == 1 && Character.isDigit(key.codes[0])) {
                    keys[i++] = key;
                }
            }

            // shuffle keys, then reassign their labels and text to match their new value
            try {
                Collections.shuffle(Arrays.asList(keys));
            } catch (UnsupportedOperationException e) {
                // should not happen
                throw new RuntimeException(e);
            }

            for (i = 0; i < keys.length; i++) {
                if (keys[i] != null) {
                    keys[i].codes = new int[] { '0' + i };

                    // get rid of character hint labels, because they just get distracting on a randomized keypad
                    keys[i].icon = null;
                    keys[i].label = Integer.toString(i);
                }
            }
        }

        super.setKeyboard(keyboard);
    }

}
