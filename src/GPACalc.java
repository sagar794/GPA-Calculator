//Sagar Ali
//GPA Calculator

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import net.miginfocom.swing.MigLayout;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.regex.*;

public class GPACalc{
 private ArrayList<JButton> removeButtons = new ArrayList<JButton>();
 private ArrayList<JTextField> courseNames = new ArrayList<JTextField>();
 private ArrayList<JComboBox<Integer>> creditHours = new ArrayList<JComboBox<Integer>>();
 private ArrayList<JComboBox<String>> letterGrades = new ArrayList<JComboBox<String>>();
 private JLabel overallGPA = new JLabel("0.0");
 private JLabel semesterGPA = new JLabel("0.0");
 private JFrame frame  = new JFrame();
 private JTextField currentGPAField = new JTextField(5);
 private JTextField hoursField = new JTextField(5);
 private JPanel innerPanel; 
 private JPanel innerPanel2; 
 private JPanel panel;

 public void go(){
  MigLayout layout = new MigLayout("","","[]20[]");
  panel = new JPanel(layout);
  panel.setBackground(new Color(80,0,0)); 

  //add title of application
  JLabel titleLabel = new JLabel("GPA CALCULATOR");
  titleLabel.setForeground(Color.white);
  titleLabel.setFont(new Font("Impact",titleLabel.getFont().getStyle(),60));
  panel.add(titleLabel,"span 2, align center,wrap");
  
  //add restore button
  JButton restoreButton = new JButton("Restore Previous Entry");
  restoreButton.addActionListener(new restorePrev());
  panel.add(restoreButton, "skip,split 2");

  //add clear button
  JButton clearButton = new JButton("Clear All");
  clearButton.addActionListener(new clearButtonListener());
  panel.add(clearButton, "wrap");

  //add current gpa label
  JLabel currentGPALabel = new JLabel("Current Cumulative GPA");
  currentGPALabel.setForeground(Color.white);
  currentGPALabel.setFont(new Font("Arial",currentGPALabel.getFont().getStyle(),18));
  panel.add(currentGPALabel);
   
  //add current gpa textfield
  panel.add(currentGPAField, "wrap");
  currentGPAField.setInputVerifier(new GPAVerifier());
 
  //add total hours label
  JLabel hoursLabel = new JLabel("Total GPA Hours");
  hoursLabel.setForeground(Color.white);
  hoursLabel.setFont(new Font("Arial",hoursLabel.getFont().getStyle(),18));
  panel.add(hoursLabel);
   
  //add total hours textfield
  panel.add(hoursField, "wrap");
  hoursField.setInputVerifier(new HoursVerifier());

  MigLayout layout2 =  new MigLayout("","[]20[]60[]60[]","");
  innerPanel = new JPanel(layout2);
  innerPanel.setBackground(new Color(80,0,0)); 
  
  //add course name label
  JLabel courseNameLabel = new JLabel("Current Courses (Optional)");
  courseNameLabel.setForeground(Color.white);
  courseNameLabel.setFont(new Font("Arial",courseNameLabel.getFont().getStyle(),18));
  innerPanel.add(courseNameLabel,"skip");

  //add credit hour label
  JLabel creditHoursLabel = new JLabel("# of credit hours");
  creditHoursLabel.setForeground(Color.white);
  creditHoursLabel.setFont(new Font("Arial",creditHoursLabel.getFont().getStyle(),18));
  innerPanel.add(creditHoursLabel);

  //add letter grade label
  JLabel gradeLabel = new JLabel("Expected letter grade");
  gradeLabel.setForeground(Color.white);
  gradeLabel.setFont(new Font("Arial",gradeLabel.getFont().getStyle(),18));
  innerPanel.add(gradeLabel, "wrap");

  //add in comboboxes/textfields to take user input for current courses
  for(int i=0;i<4;i++){
   JButton removeRow = new JButton();
   try {
     Image cross = ImageIO.read(getClass().getResource("images/cross.png"));
     removeRow.setIcon(new ImageIcon(cross));
     removeRow.addActionListener(new removeCourse());
     removeRow.setActionCommand(Integer.toString(removeButtons.size()));
     removeButtons.add(removeRow);
     innerPanel.add(removeRow);
   } catch (IOException ex) {
   }
   JTextField courseName = new JTextField(15);
   courseNames.add(courseName);
   innerPanel.add(courseName);

   JComboBox<Integer> creditHour = new JComboBox<Integer>();
   creditHour.addItem(null);
   creditHour.addItem(1);
   creditHour.addItem(2);
   creditHour.addItem(3);
   creditHour.addItem(4);
   creditHour.addItem(5);
   creditHours.add(creditHour);
   innerPanel.add(creditHour);

   JComboBox<String> letterGrade = new JComboBox<String>();
   letterGrade.addItem(null);
   letterGrade.addItem("A");
   letterGrade.addItem("A-");
   letterGrade.addItem("B+");
   letterGrade.addItem("B");
   letterGrade.addItem("B-");
   letterGrade.addItem("C+");
   letterGrade.addItem("C");
   letterGrade.addItem("C-");
   letterGrade.addItem("D+");
   letterGrade.addItem("D");
   letterGrade.addItem("D-");
   letterGrade.addItem("F");
   letterGrades.add(letterGrade);
   innerPanel.add(letterGrade, "wrap");
  }
 
  innerPanel2 = new JPanel(layout2);
  innerPanel2.setBackground(new Color(80,0,0)); 
  
  //add button to add new row for courses
  JButton addRow = new JButton("+");
  addRow.addActionListener(new addCourseListener());
  innerPanel2.add(addRow,"align right ,wrap");

  //add submit button
  JButton submit = new JButton("Submit");
  submit.addActionListener(new calculateInput());
  innerPanel2.add(submit,"skip 3,wrap");

  //add semester gpa label
  JLabel semesterLabel = new JLabel("Sem GPA");
  semesterLabel.setForeground(Color.white);
  semesterLabel.setFont(new Font("Arial",semesterLabel.getFont().getStyle(),18));
  semesterGPA.setForeground(Color.white);
  semesterGPA.setFont(new Font("Arial",semesterGPA.getFont().getStyle(),18));
  innerPanel2.add(semesterLabel);
  innerPanel2.add(semesterGPA,"wrap");
  
  //add overall gpa label
  JLabel overallLabel = new JLabel("Overall GPA");
  overallLabel.setForeground(Color.white);
  overallLabel.setFont(new Font("Arial",overallLabel.getFont().getStyle(),18));
  overallGPA.setForeground(Color.white);
  overallGPA.setFont(new Font("Arial",overallGPA.getFont().getStyle(),18));
  innerPanel2.add(overallLabel);
  innerPanel2.add(overallGPA);
 
  //combine all the panels and put them in a scroll pane
  innerPanel.add(innerPanel2,"span 3");
  panel.add(innerPanel,"span 2");
  JScrollPane scrollPane = new JScrollPane(panel);

  frame.getContentPane().add(BorderLayout.NORTH, scrollPane);
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  frame.setSize(750,600);
  frame.setVisible(true);
 }

 // listener for clear button
 class clearButtonListener implements ActionListener {
  public void actionPerformed(ActionEvent event){
   for(int i=0; i<courseNames.size(); i++){
    courseNames.get(i).setText(null);
    creditHours.get(i).setSelectedItem(null);
    letterGrades.get(i).setSelectedItem(null);
    semesterGPA.setText("0.0");
    overallGPA.setText("0.0");
    currentGPAField.setText(null);
    hoursField.setText(null);
   }
  }
 } 

 // listener for when the add course button
 class addCourseListener implements ActionListener {
  public void actionPerformed(ActionEvent event){
   JButton removeRow = new JButton();
   try {
     Image cross = ImageIO.read(getClass().getResource("images/cross.png"));
     removeRow.setIcon(new ImageIcon(cross));
     removeRow.addActionListener(new removeCourse());
     removeRow.setActionCommand(Integer.toString(removeButtons.size()));
     removeButtons.add(removeRow);
     innerPanel.add(removeRow);
   } catch (IOException ex) {
   }

   innerPanel.remove(innerPanel2);
   JTextField courseName = new JTextField(15);
   courseNames.add(courseName);
   innerPanel.add(courseName);

   JComboBox<Integer> creditHour = new JComboBox<Integer>();
   creditHour.addItem(null);
   creditHour.addItem(1);
   creditHour.addItem(2);
   creditHour.addItem(3);
   creditHour.addItem(4);
   creditHour.addItem(5);
   creditHours.add(creditHour);
   innerPanel.add(creditHour);

   JComboBox<String> letterGrade = new JComboBox<String>();
   letterGrade.addItem(null);
   letterGrade.addItem("A");
   letterGrade.addItem("A-");
   letterGrade.addItem("B+");
   letterGrade.addItem("B");
   letterGrade.addItem("B-");
   letterGrade.addItem("C+");
   letterGrade.addItem("C");
   letterGrade.addItem("C-");
   letterGrade.addItem("D+");
   letterGrade.addItem("D");
   letterGrade.addItem("D-");
   letterGrade.addItem("F");
   letterGrades.add(letterGrade);
   innerPanel.add(letterGrade, "wrap");

   innerPanel.add(innerPanel2,"span 3");
   
   innerPanel.revalidate();
   innerPanel.repaint();
  
  }
 }

 //listner for the remove course button
 class removeCourse implements ActionListener{
  public void actionPerformed(ActionEvent event){
   int index = Integer.parseInt(event.getActionCommand());
   innerPanel.remove(courseNames.get(index));
   innerPanel.remove(creditHours.get(index));
   innerPanel.remove(letterGrades.get(index));
   innerPanel.remove(removeButtons.get(index));
  
   courseNames.remove(index);
   creditHours.remove(index);
   letterGrades.remove(index);
   removeButtons.remove(index);

   for(int i=index; i<removeButtons.size(); i++){
     String actionCommand = removeButtons.get(i).getActionCommand();
     int newActionCommand  = Integer.parseInt(actionCommand) - 1;
     removeButtons.get(i).setActionCommand(Integer.toString(newActionCommand));
   }

   innerPanel.revalidate();
   innerPanel.repaint();
  }

 }

 //listener for the submit button
 class calculateInput implements ActionListener{
  public void actionPerformed(ActionEvent event){
   if(currentGPAField.getText().equals("")){
    JOptionPane.showMessageDialog(frame, "Please input current cumulative GPA.");
    return;
   }
   if(hoursField.getText().equals("")){
    JOptionPane.showMessageDialog(frame, "Please input total current hours.");
    return;
   }
   for(int i=0; i<courseNames.size(); i++){
     if(creditHours.get(i).getSelectedItem() == null && letterGrades.get(i).getSelectedItem() != null
       || creditHours.get(i).getSelectedItem() != null && letterGrades.get(i).getSelectedItem() == null){
     JOptionPane.showMessageDialog(frame, "Please complete all course information.");
       return;
      }
   }
   double currentGPA = Double.parseDouble(currentGPAField.getText());
   int hours = Integer.parseInt(hoursField.getText());
   double currentGradePoints = currentGPA * hours;
   
   double semGPA = 0;
   double semesterHours = 0;
   double semGradePoints = 0;
   for(int i=0; i<courseNames.size(); i++){
    if(creditHours.get(i).getSelectedItem() != null && letterGrades.get(i).getSelectedItem() != null) {
     semesterHours += (int)creditHours.get(i).getSelectedItem();
     switch((String)letterGrades.get(i).getSelectedItem()){
      case "A": semGradePoints += 4 * (int)creditHours.get(i).getSelectedItem();break;
      case "A-": semGradePoints += 3.67 * (int)creditHours.get(i).getSelectedItem();break;
      case "B+": semGradePoints += 3.33 * (int)creditHours.get(i).getSelectedItem();break;
      case "B": semGradePoints += 3 * (int)creditHours.get(i).getSelectedItem();break;
      case "B-": semGradePoints += 2.67 * (int)creditHours.get(i).getSelectedItem();break;
      case "C+": semGradePoints += 2.33 * (int)creditHours.get(i).getSelectedItem();break;
      case "C": semGradePoints += 2 * (int)creditHours.get(i).getSelectedItem();break;
      case "C-": semGradePoints += 1.67 * (int)creditHours.get(i).getSelectedItem();break;
      case "D+": semGradePoints += 1.33 * (int)creditHours.get(i).getSelectedItem();break;
      case "D": semGradePoints += 1 * (int)creditHours.get(i).getSelectedItem();break;
      case "D-": semGradePoints += 0.67 * (int)creditHours.get(i).getSelectedItem();break;
      case "F": semGradePoints += 0;break;
      default: throw new IllegalArgumentException("Invalid letter grade!");
     }
    }
   }
   if(semesterHours != 0){
   semGPA = Math.round((semGradePoints/semesterHours) * 1000.0)/1000.0; 
   double newGPA = Math.round((currentGradePoints + semGradePoints)/(hours + semesterHours)*1000.0)/1000.0;
   
   semesterGPA.setText(Double.toString(semGPA));
   overallGPA.setText(Double.toString(newGPA));
   
   try { 
    FileWriter file  = new FileWriter("restore.txt");
    BufferedWriter writer = new BufferedWriter(file);
    writer.write(currentGPAField.getText());
    writer.newLine();
    writer.write(hoursField.getText());
    writer.newLine();
    writer.write(semesterGPA.getText());
    writer.newLine();
    writer.write(overallGPA.getText());
    writer.newLine();
    writer.write(Integer.toString(courseNames.size()));
    writer.newLine();
    for(int i=0; i<courseNames.size(); i++){
     writer.write(courseNames.get(i).getText());
     writer.newLine();
     writer.write(Integer.toString(creditHours.get(i).getSelectedIndex()) + " " + Integer.toString(letterGrades.get(i).getSelectedIndex()));
     writer.newLine();
    }
    writer.close();
   } catch(IOException ex){System.out.println("Error when saving entries");}
   }
  } 
 }

 // listener for the restore button
 public class restorePrev implements ActionListener{
  public void actionPerformed(ActionEvent event){
   try{
     File myFile = new File("restore.txt");
     FileReader fileReader = new FileReader(myFile);
     BufferedReader reader = new BufferedReader(fileReader);
     String input = null;

     input = reader.readLine();
     currentGPAField.setText(input);

     input = reader.readLine();
     hoursField.setText(input);

     input = reader.readLine();
     semesterGPA.setText(input);

     input = reader.readLine();
     overallGPA.setText(input);
     
     input = reader.readLine();
     int numCourses = Integer.parseInt(input);

     if(numCourses > courseNames.size()){
       addCourseListener addCourse = new addCourseListener();
      while(numCourses != courseNames.size()){
       addCourse.actionPerformed(null);
      }
     }
     else if(numCourses < courseNames.size()){
      while(numCourses != courseNames.size()){
       innerPanel.remove(removeButtons.get(removeButtons.size()-1));
       innerPanel.remove(courseNames.get(courseNames.size()-1));
       innerPanel.remove(creditHours.get(creditHours.size()-1));
       innerPanel.remove(letterGrades.get(letterGrades.size()-1));

       innerPanel.revalidate();
       innerPanel.repaint();

       removeButtons.remove(removeButtons.size()-1);
       courseNames.remove(courseNames.size()-1);
       creditHours.remove(creditHours.size()-1);
       letterGrades.remove(letterGrades.size()-1);
      }
     }
     for(int i=0; i<numCourses; i++){
     input = reader.readLine();
     courseNames.get(i).setText(input);
     input = reader.readLine();
     String [] course =  input.split(" ");
      creditHours.get(i).setSelectedIndex(Integer.parseInt(course[0]));
      letterGrades.get(i).setSelectedIndex(Integer.parseInt(course[1]));
     }
     reader.close();
   } catch(IOException ex){System.out.println("Error when trying to restore previous entry");}
  }
 }

 class GPAVerifier extends InputVerifier{
  public boolean isNum(String string){
   Pattern p = Pattern.compile("\\d+(\\.\\d+)?");
   Matcher m = p.matcher(string);
   if(m.find()){
    return true;
   } else {return false;}
  }
  public boolean verify(JComponent input){
   JTextField tf = (JTextField)input;
   String gpa = tf.getText();
   try{
   if(gpa.equals("") || isNum(gpa) && Double.parseDouble(gpa)>=0 && Double.parseDouble(gpa)<=4){
    return true; 
   } 
   else {
    JOptionPane.showMessageDialog(frame, "Invalid input. GPA must be between 0 - 4.");
    return false;
   }
  }
   catch(NumberFormatException ex){
    JOptionPane.showMessageDialog(frame, "Invalid input. GPA must be between 0 - 4.");
    return false;
   }
  }
 }

 class HoursVerifier extends InputVerifier{
  public boolean isNum(String string){
   for(int i=0; i<string.length(); i++){
    if(!Character.isDigit(string.charAt(i))){
    JOptionPane.showMessageDialog(frame, "Invalid input. Number of hours must be a postive integer.");
     return false;
    }
   }
   return true;
  }
  public boolean verify(JComponent input){
   JTextField tf = (JTextField)input;
   String hours = tf.getText();
   return isNum(hours);
  }
 }

 public static void main(String[] args){
   GPACalc calc = new GPACalc();
   calc.go();
 }

}
