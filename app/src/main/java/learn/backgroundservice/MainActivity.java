package learn.backgroundservice;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;



import common.socketFactory.SocketFactory;
import io.socket.emitter.Emitter;

public class MainActivity extends AppCompatActivity {

    private String TAG = "TEST";
    private Activity activity = this;


    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "------------------------------------");
                    Log.d(TAG, args[0].toString());
                    Log.d(TAG, "------------------------------------");
                }
            });
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SocketFactory socket = new SocketFactory();
        socket.getSocket("http://192.168.0.108:3000");
        socket.connect();
        socket.on("msg", onNewMessage);
        socket.emit("msg","Go go shot:)");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "+++++++++++++++++++++++++++++++++++");
//        socket.connect();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "-------------------------------------");
//        socket.connect();
    }

    public Activity getActivity() {
        return activity;
    }
}
