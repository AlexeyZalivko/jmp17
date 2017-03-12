package edu.jmp.ui.frames.data;

import edu.jmp.dao.data.Author;
import lombok.Data;

/**
 * Created by alex on 12.03.17.
 */
@Data
public class NewsDisplayData {
    private Author author;
    private String caption;
    private String text;

    public Object getView() {
        return "Author: " + author.getName() + "; Caption: " + caption + "; Text: " + text;
    }
}
