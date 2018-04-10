package vn.javis.lineloginsample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.linecorp.linesdk.api.LineApiClient;
import com.linecorp.linesdk.api.LineApiClientBuilder;
import com.linecorp.linesdk.auth.LineLoginApi;
import com.linecorp.linesdk.auth.LineLoginResult;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String LINE_CHANEL_ID = "1573462307";
    public static final int RC_LN_SIGN_IN = 006;
    private static LineApiClient lineApiClient;

    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Todo Initialize the lineApiClient variable on your activity’s onCreate() method as shown below. The channel ID and the context are required for initialization.
        LineApiClientBuilder apiClientBuilder = new LineApiClientBuilder(getApplicationContext(),LINE_CHANEL_ID );
        lineApiClient = apiClientBuilder.build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = LineLoginApi.getLoginIntent(getApplicationContext(), LINE_CHANEL_ID);
                startActivityForResult(loginIntent,RC_LN_SIGN_IN);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_LN_SIGN_IN)
        {
            handleLoginResult(data);
        }else
        {
            Log.e(TAG, "onActivityResult: Unknown Request response");
        }
    }
    private void handleLoginResult(Intent data){
        //Todo Handling the login result
        LineLoginResult loginResult = LineLoginApi.getLoginResultFromIntent(data);
        switch (loginResult.getResponseCode())
        {
            case SUCCESS: //Todo example Get Username
                Toast.makeText(getApplicationContext(),"Login success: " + loginResult.getLineProfile().getDisplayName() ,Toast.LENGTH_SHORT).show();
                break;
            case CANCEL:
                Log.e("ERROR", "LINE Login Canceled by user!!");
                break;
            case SERVER_ERROR:
                break;
            case NETWORK_ERROR:
                break;
            case INTERNAL_ERROR:
                break;
            case AUTHENTICATION_AGENT_ERROR:
                break;
            default:
                Log.e("ERROR", "Login FAILED!");
                Log.e("ERROR", loginResult.getErrorData().toString());
        }
    }
}
