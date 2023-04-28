import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class SortingPanel extends JPanel {
    
    private static final long serialVersionUID = 1L;

    private Random random;
    private int[] array;
    private BubbleSort bubbleSort;
  
    private QuickSort quickSort;

    private JButton start;
    private JButton bubble;
 
    private JButton quick;
    private JButton reset;

    private boolean isBubble = false;
 
    private boolean isQuick = false;

    private boolean isRunning;

    int i = 0;

    public SortingPanel() {

        random = new Random();
        array = new int[80];
        this.setArray();

        bubbleSort = new BubbleSort(array);
       
        quickSort = new QuickSort(array);

        start = new JButton("start");
        bubble = new JButton("bubble");
      
        quick = new JButton("quick");
        reset = new JButton("reset");

        start.setModel(new ButtonModel());
        start.setBackground(Color.WHITE);
        start.setFocusPainted(false);
        start.setBorderPainted(false);
        start.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) { 
                try {
                    start.setBackground(Color.lightGray);
                    if (isRunning == false)
                        isRunning = true;
                        animate();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            } 
        });

        bubble.setModel(new ButtonModel());
        bubble.setBackground(Color.WHITE);
        bubble.setFocusPainted(false);
        bubble.setBorderPainted(false);
        bubble.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) { 
                try {
                    if (isRunning == false) {
                   
                    
                        isQuick = false;
                        isBubble = true;
                        setButtonBackground();
                        bubble.setBackground(Color.lightGray);
                    }   
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            } 
        });

   
        quick.setModel(new ButtonModel());
        quick.setBackground(Color.WHITE);
        quick.setFocusPainted(false);
        quick.setBorderPainted(false);
        quick.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) { 
                try {
                    if (isRunning == false) {
                        isBubble = false;
                     
                        isQuick = true;
                        setButtonBackground();
                        quick.setBackground(Color.lightGray);
                    }   
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            } 
        });

        reset.setModel(new ButtonModel());
        reset.setBackground(Color.WHITE);
        reset.setFocusPainted(false);
        reset.setBorderPainted(false);
        reset.addActionListener(new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) { 

                reset.setBackground(Color.lightGray);
                start.setBackground(Color.WHITE);

                setArray();

                // reset bubbleSort object
                bubbleSort.setCompareIndex(Integer.MAX_VALUE);
                bubbleSort.setArrayIndex(0);
                bubbleSort.setArray(array);

             
                // reset quickSort object
                quickSort.setSP(-1);
                quickSort.push(0);
                quickSort.push(79);
                quickSort.setArrayIndex(Integer.MAX_VALUE);
                quickSort.setCompareIndex(Integer.MAX_VALUE);
                quickSort.setPartition(-1);
                quickSort.setIsPartioning(false);

                isRunning = false;
                
                Timer timer  = new Timer(100, new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        reset.setBackground(Color.WHITE);
                        ((Timer)e.getSource()).stop();
                    }
                });

                timer.start();
                repaint();
            } 
        });

        this.add(start);
        this.add(bubble);
   
        this.add(quick);
        this.add(reset);
    }

    public void setButtonBackground() {
        bubble.setBackground(Color.WHITE);
      
        quick.setBackground(Color.WHITE);
    }

    public int[] getArray() {
        return this.array;
    }

    public void setArray() {
        for (int i = 0; i < this.array.length; i++) {
            this.array[i] = random.nextInt(510) + 40;
        }
    }

    public boolean isSorted() {

        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) {
                return false; 
            }
        }
    
        return true;
    }

    public void animate() throws Exception{

        if (isBubble) {

            bubbleSort.setCompareIndex(0);

            Timer bubbleTimer  = new Timer(10, new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {

                    if (isSorted() || isRunning == false) {
                        bubbleSort.setCompareIndex(Integer.MAX_VALUE);
                        isRunning = false;
                        start.setBackground(Color.WHITE);
                        ((Timer)e.getSource()).stop();
                    } 
                    else {
                        if (isRunning == true)
                            array = bubbleSort.sortOnlyOneItem();
                    }

                    repaint();
                }
            });

            bubbleTimer.start();
        }
        
   

        if (isQuick) {

            Timer quickTimer  = new Timer(10, new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {

                    if (isSorted() || isRunning == false) {
                        quickSort.setSP(-1);
                        quickSort.push(0);
                        quickSort.push(79);
                        quickSort.setArrayIndex(Integer.MAX_VALUE);
                        quickSort.setCompareIndex(Integer.MAX_VALUE);
                        quickSort.setPartition(-1);
                        quickSort.setIsPartioning(false);
                        isRunning = false;
                        start.setBackground(Color.WHITE);
                        ((Timer)e.getSource()).stop();
                    } 

                    else {
                        if (isRunning == true)
                            array = quickSort.sortOnlyOneItem();
                    }

                    repaint();
                }
            });

            quickTimer.start();
        }
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        this.setBackground(Color.BLACK);

        for (int i = 0; i < array.length; i++) {
            
            g.setColor(Color.BLACK);
            g.drawRect(i*15, 600 - array[i], 14, array[i]); 
            
            if (isBubble) {

                if (i == bubbleSort.getCompareIndex() || i == (bubbleSort.getCompareIndex() + 1)) {
                    g.setColor(Color.MAGENTA);
                }

            }

          
            if (isQuick) {
                
                if (i == quickSort.getArrayIndex()) {
                    g.setColor(Color.MAGENTA);
                }

                if (i == quickSort.getCompareIndex()) {
                    g.setColor(Color.BLUE);
                }

                if (i == quickSort.getPartition())
                    g.setColor(Color.GREEN);
            }
        
            if (g.getColor() != Color.MAGENTA && g.getColor() != Color.GREEN && g.getColor() != Color.BLUE && g.getColor() != Color.RED)
                g.setColor(Color.CYAN);

            g.fillRect(i*15, 600 - array[i], 14, array[i]);
        }
    }
}