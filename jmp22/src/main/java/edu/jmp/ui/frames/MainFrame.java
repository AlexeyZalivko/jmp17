package edu.jmp.ui.frames;

import edu.jmp.ui.frames.data.NewsDisplayData;
import edu.jmp.ui.frames.data.NewsListDisplayData;
import edu.jmp.facade.NewsFacade;
import edu.jmp.facade.NewsFacadeImpl;
import edu.jmp.ui.frames.model.NewsTableModel;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Created by alex on 12.03.17.
 */
public class MainFrame extends JFrame {

    private static Logger log = Logger.getLogger(MainFrame.class.getName());

    private static String CAPTION = "News Box";

    private JList<NewsDisplayData> newsList;
    private DefaultListModel listModel = new DefaultListModel();

    private NewsFacade newsFacade;

    public MainFrame() throws HeadlessException {
        super(CAPTION);

        newsFacade = new NewsFacadeImpl();

        this.setBounds(300, 300, 300, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        final Container container = this.getContentPane();
        container.setLayout(new GridLayout(3, 2));

        initList(container);
        initTable(container);

        pack();
    }

    private void initTable(final Container container) {
        java.util.List<NewsDisplayData> data = null;
        try {
            data = newsFacade.getAllNews();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (data == null) {
            return;
        }

        final TableModel model = new NewsTableModel(data);
        final JTable table = new JTable(model);

        container.add(table);
    }

    public void initList(final Container container) {
        newsList = new JList<>(listModel);
        newsList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        newsList.setVisibleRowCount(-1);

        JScrollPane listScroll = new JScrollPane(newsList);
        listScroll.setPreferredSize(new Dimension(250, 80));

        container.add(listScroll);

        try {
            final java.util.List<NewsDisplayData> data = newsFacade.getAllNews();
            for (NewsDisplayData item : data) {
                final NewsListDisplayData dd = new NewsListDisplayData(item);
                listModel.addElement(dd.getView());
            }
        } catch (SQLException e) {
            log.warning(e.getMessage());
        }
    }
}
