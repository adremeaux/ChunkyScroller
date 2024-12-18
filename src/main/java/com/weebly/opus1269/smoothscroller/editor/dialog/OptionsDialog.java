/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2020 DeflatedPickle
 * Copyright (c) 2016 Michael A Updike
 * Copyright (c) 2013 Hugo Campos
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.weebly.opus1269.smoothscroller.editor.dialog;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.project.Project;
import com.weebly.opus1269.smoothscroller.editor.form.OptionsForm;
import com.weebly.opus1269.smoothscroller.property.Props;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;


public class OptionsDialog extends DialogWrapper {
    private final OptionsForm mOptionsForm;

    public OptionsDialog() {
        super((Project)null, true);

        this.mOptionsForm = new OptionsForm();

        init();

        setTitle("Smooth Scroller Options");
    }

    @Override
    public void show() {
        this.mOptionsForm.setFromProps();

        super.show();

        if (getExitCode() == DialogWrapper.OK_EXIT_CODE && this.mOptionsForm.isModified()) {
            this.mOptionsForm.setToProps();
            Props.storeProperties();
        }
    }

    @NotNull
    @Override
    protected JComponent createCenterPanel() {
        return this.mOptionsForm.getRoot();
    }
}
