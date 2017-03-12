package edu.jmp.ui.frames.data;

import lombok.Data;

/**
 * Created by alex on 12.03.17.
 */
@Data
public class NewsTableDisplayData extends NewsDisplayData {

    public NewsTableDisplayData(final NewsDisplayData dd) {
        this.setText(dd.getText());
        this.setCaption(dd.getCaption());
        this.setAuthor(dd.getAuthor());
    }

    @Override
    public Object getView() {
        return this;
    }

    @Override
    public String toString() {
        return "[" + getAuthor().getName() + "] \"" + getCaption() + "\" : " + getText() + "\n";
    }
}
