package com.weebly.opus1269.smoothscroller.editor.listener;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.ScrollingModel;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.TextEditor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.event.InputEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

class SmoothScrollerMouseWheelListener implements MouseWheelListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(SmoothScrollerMouseWheelListener.class);

    // Scrolling model and editor
    private final ScrollingModel scrollingModel;
    private final Editor editor;

    /**
     * Constructor for the MouseWheelListener.
     *
     * @param fileEditor The file editor to which smooth scrolling is to be added.
     */
    public SmoothScrollerMouseWheelListener(@NotNull FileEditor fileEditor) {
        this.editor = ((TextEditor) fileEditor).getEditor();
        this.scrollingModel = editor.getScrollingModel();
        // Disable any existing animation to ensure immediate scrolling
        this.scrollingModel.disableAnimation();
        LOGGER.debug("SmoothScrollerMouseWheelListener initialized with animations disabled.");
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        // Determine if horizontal scrolling is requested (e.g., Shift key is pressed)
        boolean isHorizontal = (e.getModifiersEx() & InputEvent.SHIFT_DOWN_MASK) != 0;

        // Get the number of notches the mouse wheel was rotated
        int wheelNotches = e.getWheelRotation() * 3;

        if (isHorizontal) {
            // Handle horizontal scrolling
            scrollHorizontally(wheelNotches);
        } else {
            // Handle vertical scrolling
            scrollVertically(wheelNotches);
        }
    }

    /**
     * Scrolls the editor view horizontally by a fixed amount per wheel notch.
     *
     * @param wheelNotches The number of notches the wheel was rotated.
     */
    private void scrollHorizontally(int wheelNotches) {
        // Define the scroll amount per notch (pixels)
        final int SCROLL_AMOUNT = 20; // You can adjust this value as needed

        // Calculate the new horizontal offset
        int currentOffset = scrollingModel.getHorizontalScrollOffset();
        int newOffset = currentOffset + (wheelNotches * SCROLL_AMOUNT);

        // Ensure the new offset is not negative
        newOffset = Math.max(newOffset, 0);

        // Apply the new horizontal scroll offset
        scrollingModel.scrollHorizontally(newOffset);
        LOGGER.debug("Scrolled horizontally to offset: {}", newOffset);
    }

    /**
     * Scrolls the editor view vertically by one line per wheel notch.
     *
     * @param wheelNotches The number of notches the wheel was rotated.
     */
    private void scrollVertically(int wheelNotches) {
        // Get the height of a single line in pixels
        int lineHeight = editor.getLineHeight();

        // Calculate the new vertical offset
        int currentOffset = scrollingModel.getVerticalScrollOffset();
        int newOffset = currentOffset + (wheelNotches * lineHeight);

        // Ensure the new offset is not negative
        newOffset = Math.max(newOffset, 0);

        // Apply the new vertical scroll offset
        scrollingModel.scrollVertically(newOffset);
        LOGGER.debug("Scrolled vertically to offset: {}", newOffset);
    }
}
