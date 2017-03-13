package edu.jmp.ui.frames.data;

import lombok.Data;

/**
 * Created by alex on 12.03.17.
 */
@Data
public class NewsListDisplayData extends NewsDisplayData {

    public NewsListDisplayData(final NewsDisplayData dd) {
        this.setText(dd.getText());
        this.setCaption(dd.getCaption());
        this.setAuthor(dd.getAuthor());
    }

    @Override
    public Object getView() {
        return this.toString();
    }

    @Override
    public String toString() {
        return "[" + getAuthor().getName() + "] \"" + getCaption() + "\" : " + getText() + "\n";
    }
}
