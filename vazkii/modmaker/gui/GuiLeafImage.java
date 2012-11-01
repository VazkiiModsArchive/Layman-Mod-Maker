package vazkii.modmaker.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

import vazkii.modmaker.tree.TreeLeaf;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;

public class GuiLeafImage extends GuiLeafEdit<File> {

	File currentFile;
	GuiButton fileButton;
	JFileChooser fileChooser;

	public GuiLeafImage(GuiScreen parent, TreeLeaf<File> leaf, String propName) {
		super(parent, leaf, propName);
	}

	@Override
	public String getError() {
		if (currentFile == null) return "You must select a file!";
		return null;
	}

	@Override
	public void initGui() {
		super.initGui();
		contentsField = null;
		fileButton = createCenteredButton(2, 100, "Select File");
		controlList.add(fileButton);
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		if (par1GuiButton == fileButton) {
			new ImageSelectPanel(this);
			return;
		}
		super.actionPerformed(par1GuiButton);
	}

	public void selectImage(File f) {
		currentFile = f;
	}

	@Override
	public File getValue() {
		return currentFile;
	}

	public class ImageSelectPanel extends JFrame implements ActionListener {

		JButton selectButton;
		JFileChooser fileChooser;
		JPanel panel = new JPanel();
		GuiLeafImage parent;

		static final int WIDTH = 200;
		static final int HEIGHT = 75;

		public ImageSelectPanel(GuiLeafImage parent) {
			super("Select an Image");
			this.parent = parent;

			Dimension screenResolution = Toolkit.getDefaultToolkit().getScreenSize();
			setBounds(screenResolution.width / 2 - WIDTH / 2, screenResolution.height / 2 - HEIGHT, WIDTH, HEIGHT);

			setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			fileChooser = new JFileChooser();
			fileChooser.addChoosableFileFilter(new FileFilter() {
				@Override
				public String getDescription() {
					return "PNG Images Only";
				}

				@Override
				public boolean accept(File f) {
					return f.isDirectory() || f.getName().endsWith(".png");
				}
			});

			Container container = getContentPane();
			container.add(panel);
			selectButton = new JButton("Select an Image");
			panel.add(selectButton);
			selectButton.requestFocus();
			selectButton.addActionListener(this);
			setAlwaysOnTop(true);
			setVisible(true);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			int i = fileChooser.showOpenDialog(this);

			if (i == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				parent.selectImage(file);
				dispose();
			}
		}
	}
}
