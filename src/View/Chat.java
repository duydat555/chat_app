package View;

import View.Chat_Body;
import View.Chat_Bottom;
import View.Chat_Title;
import Controller.EventChat;
import Controller.PublicEvent;
import Model.Model_Receive_Message;
import Model.Model_Send_Message;
import Model.Model_User_Account;
import java.util.ArrayList;
import java.util.List;
import net.miginfocom.swing.MigLayout;

public class Chat extends javax.swing.JPanel {

  
    private Chat_Title chatTitle;
    private Chat_Body chatBody;
    private Chat_Bottom chatBottom;
    private Model_User_Account user;
    private List<Model_Receive_Message> pendingMessages;

    public Chat() {
         initComponents();
        init();
        pendingMessages = new ArrayList<>();
        clearChat();
    }

    private void init() {
        setLayout(new MigLayout("fillx", "0[fill]0", "0[]0[100%, fill]0[shrink 0]0"));
        chatTitle = new Chat_Title();
        chatBody = new Chat_Body();
        chatBottom = new Chat_Bottom();
        PublicEvent.getInstance().addEventChat(new EventChat() {
            @Override
            public void sendMessage(Model_Send_Message data) {
                // Display message sent by current user
                chatBody.addItemRight(data);
            }

            @Override
            public void receiveMessage(Model_Receive_Message data) {
                // Display message received from other user
                if (user != null && chatTitle.getUser() != null && chatTitle.getUser().getUserID() == data.getFromUserID()) {
                    chatBody.addItemLeft(data);
                    clearChat();
                } else {
                    pendingMessages.add(data);
                }
            }
        });
        add(chatTitle, "wrap");
        add(chatBody, "wrap");
        add(chatBottom, "h ::50%");
        clearChat();
    }

    public void setUser(Model_User_Account user) {
        this.user = user;
        chatTitle.setUserName(user);
        chatBottom.setUser(user);
        chatBody.clearChat();

        // Display pending messages from other users
        for (Model_Receive_Message message : pendingMessages) {
            if (chatTitle != null && chatTitle.getUser() != null && chatTitle.getUser().getUserID() == message.getFromUserID()) {
                chatBody.addItemLeft(message);
            }
        }
        //pendingMessages.clear();
        clearChat();
    }

    public void updateUser(Model_User_Account user) {
        chatTitle.updateUser(user);
        clearChat();
    }

    public void clearChat() {
        repaint();
        revalidate();
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 727, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 681, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
