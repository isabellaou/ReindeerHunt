
/**
 * This is a Reindeer Hunt matching application that has the functionality of adding new players to a new round, removing players from the round, and modify players in the round. 
 * To add a player, the user needs to enter the following information: first name, last name, grade (or teacher), home room. 
 * After adding players to a new round list, the program creates randomly matched pairs of players.
 * If the initial number of players is odd, one randomly selected player is automatically going to the next round. 
 * The program displays the round number and the list of the matched pairs of the entered players with corresponding players’ information (first name, last name, grade or teacher, home room). The program also allows the user to save the matched list in the file with the name: “round #number.txt”
 * 
 * Isabella Ou  
 * 2015/11/28
 */
import java.io.*;
import java.util.Scanner;
import java.util.Random;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
public class ReindeerHuntApplication extends JFrame
{
    //declare and create JTexField variables to read the user's input
    private static JTextField txtFirstName = new JTextField(10);
    private static JTextField txtLastName = new JTextField(10);
    private static JTextField txtGradeOrTeacher = new JTextField(10);
    private static JTextField txtHomeRoom = new JTextField(10);
    private static JTextField txtIndex = new JTextField(10);
    
    //declare a linked list to store all the players
    LinkedList<Player> players = new LinkedList<Player>();
    public ReindeerHuntApplication()
    { 
        //declare a font
        Font myFont = new Font("Times New Roman", Font.BOLD ,16);
        
        //declare JLabel variables
        JLabel lblDescription = new JLabel("<html>If you want to add a player, please fill in the following information except for Index.<BR>If you want to remove a player, please only fill in the index of player you want to remove. <BR>If you want to modify the information of a player, please fill in all modified information of player you want to modify.");
        JLabel lblFN = new JLabel ("Player's First Name",JLabel.CENTER);
        JLabel lblLN = new JLabel("Player's Last Name",JLabel.CENTER);
        JLabel lblGOT = new JLabel("Grade/Teacher",JLabel.CENTER);
        JLabel lblHR = new JLabel("Home Room Number",JLabel.CENTER);
        JLabel lblIndex = new JLabel("Index Of Player",JLabel.CENTER);
        
        //set the font for the label
        lblDescription.setFont(myFont);
        
        //declare JButton variables
        JButton jbtAdd = new JButton("Add");
        JButton jbtRemove = new JButton("Remove");
        JButton jbtModify = new JButton("Modify");
        JButton jbtSave = new JButton("Save");      
        
        //set layout manager for the panel 
        setLayout(new GridLayout(15,1));        
        
        //add components to the panel 
        add(lblDescription);
        add(lblFN);
        add(txtFirstName);
        add(lblLN);
        add(txtLastName);
        add(lblGOT);
        add(txtGradeOrTeacher);
        add(lblHR);
        add(txtHomeRoom);
        add(lblIndex);
        add(txtIndex);
        add(jbtAdd);
        add(jbtRemove);
        add(jbtModify);
        add(jbtSave);
        
        ActionListener listener =  new ButtonListener();
        jbtAdd.addActionListener(listener);
        jbtRemove.addActionListener(listener);
        jbtModify.addActionListener(listener);
        jbtSave.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                //save data into a file and display the file
                try{
                    displayFile();
                }
                catch(Exception ex){
                    System.out.println("Error");
                }
            }
        });       
    }
    public static void main(String[] args)
    {
        JFrame frame = new ReindeerHuntApplication();
        frame.setTitle("Reindeer Hunt Application"); //set title
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//close upon exit
        frame.setSize(800,900);//set the frame size
        frame.setVisible(true);//display the frame
    }
    class ButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(e.getActionCommand().equals("Add"))
            {
                String firstName = txtFirstName.getText();
                String lastName = txtLastName.getText();
                String gradeOrTeacher = txtGradeOrTeacher.getText();
                int homeRoom = Integer.parseInt(txtHomeRoom.getText());
                //add a player object to players list
                players.add(new Player(firstName,lastName,gradeOrTeacher,homeRoom));
            }
            if(e.getActionCommand().equals("Remove"))
            {
                int index = Integer.parseInt(txtIndex.getText());
                //remoce a player object from players list
                players.remove(index);
            }
            if(e.getActionCommand().equals("Modify"))
            {
                String firstName = txtFirstName.getText();
                String lastName = txtLastName.getText();
                String gradeOrTeacher = txtGradeOrTeacher.getText();
                int homeRoom = Integer.parseInt(txtHomeRoom.getText());
                int index = Integer.parseInt(txtIndex.getText());
                //modify a player object from players list
                players.add(index,new Player(firstName,lastName,gradeOrTeacher,homeRoom));
            }
        }
    }
    //check if the intial number of players is odd
    public static boolean checkNumber(LinkedList<Player> players)
    {
        int length = players.size();
        if(length%2 == 0)
        return true;
        else       
        return false;
    }
    //create a method to save data and display file
    public void displayFile() throws Exception
    {
        //create a File object
        File file = new File("Reindeer Hunt list.txt");
        //create a PrintWriter object for the file
        PrintWriter output = new PrintWriter(file);
        try{
            Collections.shuffle(players);
            for(int i = 0; i < players.size()/2;i++)
            {
                output.println("Pair " + i+1);
                output.println(players.get(i));
                output.println(players.get(players.size()/2+i));
            }
            //if the initial number of player is odd, randomly pick a player to go to the next round
            //if the inital number of player is 1, the player automatically goes to the next round
            if ( players.size()%2!=0 || players.size()==1)
            {
                output.println("Player " + players.get(players.size()-1) + " goes to the next round");
            }
            //close the file
            output.close();
        }
        catch(Exception ioException)
        { 
            JOptionPane.showMessageDialog(this,"Error reading File","Error1: ",JOptionPane.ERROR_MESSAGE); 
        } 
    }
    //design a player class 
    class Player
    {
        //fields
        private String firstName;
        private String lastName;
        private String gradeOrTeacher;
        private int homeRoom;
        
        //constructor
        public Player(String firstName, String lastName, String gradeOrTeacher, int homeRoom)
        {
            this.firstName = firstName;
            this.lastName = lastName;
            this.gradeOrTeacher = gradeOrTeacher;
            this.homeRoom = homeRoom;
        }
        //accessor
        public String getFirstName()
        {
            return this.firstName;
        }
        public String getLastName()
        {
            return this.lastName;
        }
        public String getGradeOrTeacher()
        {
            return this.gradeOrTeacher;
        }
        public int getHomeRoom()
        {
            return this.homeRoom;
        }
        //mutators
        public void setFirstName(String firstName)
        {
            this.firstName = firstName;
        }
        public void setLastName(String lastName)
        {
            this.lastName = lastName;
        }
        public void setGradeOrTeacher(String gradeOrTeacher)
        {
            this.gradeOrTeacher = gradeOrTeacher;
        }
        public void setHomeRoom(int homeRoom)
        {
            this.homeRoom = homeRoom;
        }
        //a String method to return all information about the player
        public String toString()
        {
            return("\nName: " + getFirstName() + " " + getLastName() + " Grade/Teacher: "
            + getGradeOrTeacher() + " Home Room #: " + getHomeRoom());
        }
    }    
}
    

    