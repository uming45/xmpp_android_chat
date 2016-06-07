package com.gnomesys.poc.xmppandroidchat.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gnomesys.poc.xmppandroidchat.R;
import com.gnomesys.poc.xmppandroidchat.component.chat.MessagingManager;
import com.gnomesys.poc.xmppandroidchat.component.helper.ResultCallback;
import com.gnomesys.poc.xmppandroidchat.service.ChatService;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.packet.Message;

import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private ChatService chatService = null;
    private ServiceConnection xmppServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("XMPP", "Android Xmpp service connected...");
            ChatService.XmppServiceBinder binder = (ChatService.XmppServiceBinder) iBinder;
            MainActivity.this.chatService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d("XMPP", "Xmpp service disconnected...");
            MainActivity.this.chatService = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnLogin = (Button) this.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.this.chatService != null) {
                    try {
                        MainActivity.this.chatService.login("aungthawaye", "password", "aungthawaye",
                                new ResultCallback() {
                                    @Override
                                    public void onSuccess() {
                                        Log.d("XAC", "Login successful...");
                                    }

                                    @Override
                                    public void onFailure(Throwable e) {
                                        Log.d("XAC", "Login failed : " + Log.getStackTraceString(e));
                                    }
                                });
                    } catch (MessagingManager.ServiceUnavailableException e) {
                        Toast.makeText(MainActivity.this, "Xmpp not available...", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        Button btnSendMessage = (Button) this.findViewById(R.id.btnSendMessage);
        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message = new Message();
                message.setBody("Hi Admin");
                message.setType(Message.Type.chat);
                message.setStanzaId(UUID.randomUUID().toString());
                try {
                    MainActivity.this.chatService.sendMessage("admin@im.gnomesys.com", message);
                } catch (SmackException.NotConnectedException e) {
                    Toast.makeText(MainActivity.this, "XMPP not connected...", Toast.LENGTH_SHORT).show();
                } catch (MessagingManager.ServiceUnavailableException e) {
                    Toast.makeText(MainActivity.this, "Xmpp not available...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (this.chatService != null) {
            this.unbindService(this.xmppServiceConnection);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, ChatService.class);
        this.bindService(intent, this.xmppServiceConnection, BIND_AUTO_CREATE);
    }

    private void login() {

    }
}
