package vn.com.nam.loda.view;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import vn.com.nam.loda.model.StringUtil;
import vn.com.nam.loda.model.User;
import vn.com.nam.loda.model.VoteTool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class App extends JFrame implements ActionListener {
    public static Color hex2Rgb(String colorStr) {
        return new Color(
                Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
                Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
                Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }

    private List<User> listUser;

    JTextField jtextField;
    JTextField jNumVote;
    JButton jbtn;
    Terminal terminal;
    public App(){
        initUI();
        initData();
    }
    private void initUI(){
        setSize(500,500);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);
        JPanel contentPane = new JPanel(new BorderLayout());
        // top panel
        JPanel panelTop = new JPanel(new FlowLayout());
        jbtn = new JButton("Vote");
        jbtn.addActionListener(this);
        jtextField = new JTextField(40);
        jNumVote = new JTextField(2);
        jNumVote.setText("5");
        panelTop.add(jtextField);
        panelTop.add(jNumVote);
        panelTop.add(jbtn);
        // bottom panel
        JPanel panelBottom = new JPanel(new FlowLayout());
        terminal = new Terminal(20,50);
        terminal.setBorder(BorderFactory.createTitledBorder("Terminal"));
        terminal.setForeground(hex2Rgb("#ced67c"));
        terminal.setBackground(hex2Rgb("#042b35"));
        terminal.append("Hello");

        panelBottom.add(terminal);
        //add content
        contentPane.add(panelTop,BorderLayout.PAGE_START);
        contentPane.add(panelBottom,BorderLayout.CENTER);
        setContentPane(contentPane);
        pack();
        validate();
    }

    public void initData(){
        Gson gson = new Gson();
        try {
            listUser = gson.fromJson(new JsonReader(new FileReader("data.json")), new TypeToken<ArrayList<User>>() {}.getType());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            listUser = new ArrayList<User>();
        }
        for(User user : listUser){
            try {
                String response = user.login();
                System.out.println(response);
                if(response.length() > 20){
                    terminal.append("Login: "+user.getUsername()+" success!");
                }else{
                    terminal.append("Login: "+user.getUsername()+" failed!");
                }
            } catch (UnirestException e) {
                e.printStackTrace();
            }
        }
    }

    public static <T> List<T> randomSubList(List<T> list, int newSize) {
        Collections.shuffle(list);
        return list.subList(0, newSize);
    }
    public void actionPerformed(ActionEvent actionEvent) {
        final String url = jtextField.getText();
        try {
            Integer.parseInt(jNumVote.getText());
        }catch (Exception e){
            terminal.append("Add number of vote!!!");
            return;
        }
        final int numVote = Integer.parseInt(jNumVote.getText());
        if(url!=null && url.length() > 3){
            new Thread(){
                @Override
                public void run() {
                    jtextField.setText("");
                    Collections.shuffle(listUser);
                    VoteTool voteTool = new VoteTool(listUser.subList(0,numVote));
                    final String name = "Thread "+this.getId();
                    voteTool.addListener(new VoteTool.VoteListener() {
                        public void onVoteResponse(JsonNode response, int num) {
                            terminal.append(name + " vote ["+num+"/"+numVote+"] response: "+response.toString() );
                        }
                    });
                    try {
                        voteTool.automaticVote(StringUtil.getIdFromUrl(url),3*1000);
                    } catch (UnirestException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();

        }else{
            terminal.append("[WARN] Add url link first!!!");
        }

    }



    public static void main(String[] args) {
        new App();
    }
}
