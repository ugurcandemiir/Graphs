package lab11;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.function.DoubleFunction;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class ShowGraph {
	JFrame frame;
	GraphPanel graphPanel;
	PointGen pointgen;
	HashMap<String, DoubleFunction<Double>> fnList;
	JComboBox<String> fnListD;
	
	public ShowGraph() {
		fnList=new HashMap<String, DoubleFunction<Double>>();
		fnList.put("x->x", x->x);
		pointgen = new PointGen(300,0.0,10.0,fnList.get("x->x"));
		GraphPanel graphPanel=new GraphPanel(pointgen.genPoints());
		frame = new JFrame("DrawGraph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
        JPanel controlPanel = new JPanel();
        
        controlPanel.add(new JLabel("Function: "));
        String[] x = new String[0];
        fnListD = new JComboBox<String>(fnList.keySet().toArray(x));
        fnListD.addActionListener(e->{
        	String key=(String) fnListD.getSelectedItem();
        	pointgen.setFunc(fnList.get(key));
        });
        controlPanel.add(fnListD);
        
        controlPanel.add(new JLabel("Number of Points: "));
        JTextField nPoints = new JTextField(""+pointgen.getnPoints(),6);
        nPoints.setHorizontalAlignment(JTextField.RIGHT);
        nPoints.getDocument().addDocumentListener((SimpleDocumentListener) e->{
        	pointgen.setnPoints(readInt(nPoints,100));
        });
        controlPanel.add(nPoints);
        
        controlPanel.add(new JLabel("Minimum X value: "));
        JTextField minx = new JTextField(""+pointgen.getXmin(),6);
        minx.setHorizontalAlignment(JTextField.RIGHT);
        minx.getDocument().addDocumentListener((SimpleDocumentListener) e->{
        	pointgen.setXmin(readDouble(minx,0.0));
        });
        controlPanel.add(minx);
        
        controlPanel.add(new JLabel("Maximum X value"));
        JTextField maxx = new JTextField("" + pointgen.getXmax(),6);
        maxx.setHorizontalAlignment(JTextField.RIGHT);
        maxx.getDocument().addDocumentListener((SimpleDocumentListener) e-> {
        	pointgen.setXmax(readDouble(maxx,10.0));
        });
        controlPanel.add(maxx);
        
        JButton redraw = new JButton("Redraw");
        redraw.addActionListener(e->{
        	graphPanel.setPoints(pointgen.genPoints());
        });
        controlPanel.add(redraw);
        
        
        frame.getContentPane().add(controlPanel,BorderLayout.SOUTH);
        frame.getContentPane().add(graphPanel,BorderLayout.CENTER);
	}
	
	public void addFunction(String desc,DoubleFunction<Double> fn) {
		fnList.put(desc,fn);
        fnListD.addItem(desc);
	}
	
	public static int readInt(JTextField fld,int errVal) {
		String txt=fld.getText();
		int answer=errVal;
		try {
			answer=Integer.parseInt(txt);
		} catch (NumberFormatException e) {
			System.out.println("Invalid integer in " + txt + "ignored");
			// fld.setText(""+answer); Causes illegal state exception
		}
		return answer;
	}
	
	public static double readDouble(JTextField fld, double errVal) {
		double answer=errVal;
		String txt=fld.getText();
		try {
			answer=Double.parseDouble(txt);
		} catch( NumberFormatException e) {
			System.out.println("Invalid double value in " + txt + " ignored");
		}
		return answer;
	}
	
	public void createAndShowGui() {
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

	public static void main(String[] args) {
		ShowGraph graph = new ShowGraph();
		graph.addFunction("square", x->x*x);
		graph.addFunction("sin", x -> Math.sin(x));
        graph.addFunction("cos", x -> Math.cos(x));
        graph.addFunction("tan", x -> Math.tan(x));
		SwingUtilities.invokeLater(()->graph.createAndShowGui());
	}

}
