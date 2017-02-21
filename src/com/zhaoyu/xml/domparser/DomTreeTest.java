package com.zhaoyu.xml.domparser;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.SwingWorker;
import javax.swing.event.TreeModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.CDATASection;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * 将一个xml按照图形化界面的树型结构显示，主要用到图形化界面的树型模型和树形模型呈现。这里dom解析说明的并不详细。
 *
 * @author xiaoE
 *
 */
public class DomTreeTest {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				JFrame frame = new DOMTreeFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
}

class DOMTreeFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final int DEFAULT_WIDTH = 400;
	private static final int DEFAULT_HEIGHT = 300;
	private DocumentBuilder builder;

	public DOMTreeFrame() {
		setTitle("DOMTreeTest");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		JMenu fileMenu = new JMenu("File");

		JMenuItem openItem = new JMenuItem("Open");
		openItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				openFile();
			}

		});

		fileMenu.add(openItem);

		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		fileMenu.add(exitItem);

		JMenuBar menuBar = new JMenuBar();
		menuBar.add(fileMenu);
		setJMenuBar(menuBar);
	}

	public void openFile() {
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File("D:\\javatest\\xml"));

		chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {

			@Override
			public boolean accept(File f) {
				return f.isDirectory() || f.getName().toLowerCase().endsWith(".xml");
			}

			@Override
			public String getDescription() {
				return "XML Files";
			}

		});

		int r = chooser.showOpenDialog(this);
		if (r != JFileChooser.APPROVE_OPTION) {
			return;
		}
		final File file = chooser.getSelectedFile();

		new SwingWorker<Document, Void>() {

			@Override
			protected Document doInBackground() throws Exception {
				if (builder == null) {
					// 读入一个XML文档，首先需要一个DocumentBuilder对象。对builder初始化。
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					builder = factory.newDocumentBuilder();
				}
				// 通过builder的parse方法得取Document对象。
				return builder.parse(file);
			}

			@Override
			protected void done() {
				try {
					Document doc = get();
					// 根据文档定义一个树形模型。
					JTree tree = new JTree(new DOMTreeModel(doc));
					// 使用树型模型显示器，呈现树形结构。
					tree.setCellRenderer(new DOMTreeCellRenderer());

					setContentPane(new JScrollPane(tree));
					validate();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(DOMTreeFrame.this, e);
				}
			}

		}.execute();
	}
}

/**
 * 下面是一个树型文档模型类。
 *
 * @author xiaoE
 *
 */
class DOMTreeModel implements TreeModel {

	private Document doc;

	public DOMTreeModel(Document doc) {
		this.doc = doc;
	}

	@Override
	public Object getRoot() {
		return doc.getDocumentElement();
	}

	@Override
	public Object getChild(Object parent, int index) {
		Node node = (Node) parent;
		NodeList list = node.getChildNodes();
		return list.item(index);
	}

	@Override
	public int getChildCount(Object parent) {
		Node node = (Node) parent;
		NodeList list = node.getChildNodes();
		return list.getLength();
	}

	@Override
	public boolean isLeaf(Object node) {
		return getChildCount(node) == 0;
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		Node node = (Node) parent;
		NodeList list = node.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			if (getChild(node, i) == child) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public void addTreeModelListener(TreeModelListener l) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		// TODO Auto-generated method stub

	}

}

class DOMTreeCellRenderer extends DefaultTreeCellRenderer {
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
			boolean leaf, int row, boolean hasFocus) {
		Node node = (Node) value;
		if (node instanceof Element) {
			return elementPanel((Element) node);
		}

		super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
		// CharacterData包括文本，注释，CDATA,空文本等。
		if (node instanceof CharacterData) {
			setText(characterString((CharacterData) node));
		} else {
			setText(node.getClass() + ": " + node.toString());
		}
		return this;
	}

	public static JPanel elementPanel(Element e) {
		JPanel panel = new JPanel();
		panel.add(new JLabel("Element: " + e.getTagName()));
		final NamedNodeMap map = e.getAttributes();
		panel.add(new JTable(new AbstractTableModel() {

			@Override
			public Object getValueAt(int r, int c) {
				return c == 0 ? map.item(r).getNodeName() : map.item(r).getNodeValue();
			}

			@Override
			public int getRowCount() {
				return map.getLength();
			}

			@Override
			public int getColumnCount() {
				return 2;
			}
		}));
		return panel;
	}

	public static String characterString(CharacterData node) {
		StringBuilder sb = new StringBuilder(node.getData());

		for (int i = 0; i < sb.length(); i++) {
			if (sb.charAt(i) == '\r') {
				sb.replace(i, i + 1, "\\r");
				i++;
			} else if (sb.charAt(i) == '\n') {
				sb.replace(i, i + 1, "\\n");
				i++;
			} else if (sb.charAt(i) == '\t') {
				sb.replace(i, i + 1, "\\t");
				i++;
			}
		}

		if (node instanceof CDATASection) {
			sb.insert(0, "CDATASection: ");
		} else if (node instanceof Text) {
			sb.insert(0, "Text: ");
		} else if (node instanceof Comment) {
			sb.insert(0, "Comment: ");
		}

		return sb.toString();
	}
}