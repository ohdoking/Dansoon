/*
package com.ohdoking.dansoondiary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ohdoking.dansoondiary.R;

public class PasswordActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);


        Button b = (Button) findViewById(R.id.pass);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i;
                if(getVisitState()){
                    i = new Intent(PasswordActivity.this,IconListActivity.class);
                }
                else{
                    i = new Intent(PasswordActivity.this,MainListActivity.class);
                }
                startActivity(i);
                finish();
            }
        });


    }
}
*/


package com.ohdoking.dansoondiary.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ohdoking.dansoondiary.R;
import com.ohdoking.dansoondiary.common.DsStatic;

/**
 * Created by 혜영 on 2016-03-10.
 */


public class PasswordActivity extends BaseAppCompatActivity {

    String mPackageName;
    String string;
    TextView txt;
    Boolean on=false;
    Boolean clean=false;
    Integer SAVEPASSWORD = 1;
    //패스워드 검증
    String appPass = "1234";
    Intent beforeIntent;
    int state;

    String resultPassword="";
    int count = 0;
    String beforeResultPassword;

    ImageView mPass1,  mPass2,  mPass3,  mPass4;



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPass1 = (ImageView) findViewById(R.id.icon1);
        mPass2 = (ImageView) findViewById(R.id.icon2);
        mPass3 = (ImageView) findViewById(R.id.icon3);
        mPass4 = (ImageView) findViewById(R.id.icon4);

        //getIntent
        mPackageName = getIntent().getStringExtra("PACKAGE_NAME");

        //Connect Resources


        Button btn_cancel = (Button) findViewById(R.id.lock_cancel);
        btn_cancel.setOnClickListener(mClickListener);
        Button btn_change = (Button) findViewById(R.id.lock_ok);
        btn_change.setOnClickListener(mClickListener);
        txt =(TextView)findViewById(R.id.pw);

        beforeIntent = getIntent();
        state =  beforeIntent.getIntExtra(DsStatic.PASSWORDSATE, 0);
        if(state == DsStatic.CONFIRMPASSWORD){
            btn_change.setText("OK");
            txt.setText("비밀번호를 입력해주세요");

        }
        else if(state == DsStatic.SETPASSWORD){
            txt.setText("새로운 비밀번호를 입력해주세요");
            btn_change.setText("OK");

        }

        Button btn1 = (Button) findViewById(R.id.button1);
        btn1.setOnClickListener(mClickListener);
        Button btn2 = (Button) findViewById(R.id.button2);
        btn2.setOnClickListener(mClickListener);
        Button btn3 = (Button) findViewById(R.id.button3);
        btn3.setOnClickListener(mClickListener);
        Button btn4 = (Button) findViewById(R.id.button4);
        btn4.setOnClickListener(mClickListener);
        Button btn5 = (Button) findViewById(R.id.button5);
        btn5.setOnClickListener(mClickListener);
        Button btn6 = (Button) findViewById(R.id.button6);
        btn6.setOnClickListener(mClickListener);
        Button btn7 = (Button) findViewById(R.id.button7);
        btn7.setOnClickListener(mClickListener);
        Button btn8 = (Button) findViewById(R.id.button8);
        btn8.setOnClickListener(mClickListener);
        Button btn9 = (Button) findViewById(R.id.button9);
        btn9.setOnClickListener(mClickListener);
        Button btn0 = (Button) findViewById(R.id.button0);
        btn0.setOnClickListener(mClickListener);


    }

    View.OnClickListener mClickListener = new View.OnClickListener()
    {

        @Override
        public void onClick(View v)
        {
            // TODO Auto-generated method stub
            switch(v.getId())
            {
                case  R.id.button1: function("1");
                    break;
                case  R.id.button2: function("2");
                    break;
                case  R.id.button3: function("3");
                    break;
                case  R.id.button4: function("4");
                    break;
                case  R.id.button5: function("5");
                    break;
                case  R.id.button6: function("6");
                    break;
                case  R.id.button7: function("7");
                    break;
                case  R.id.button8: function("8");
                    break;
                case  R.id.button9: function("9");
                    break;
                case  R.id.button0: function("0");
                    break;

                case R.id.lock_cancel:

                    String str = resultPassword.trim();


                    if(str.length()!=0){

                        if(str.length()== 1){
                            mPass1.setImageResource(R.drawable.pw_blank);
                        }
                        else if(str.length() == 2){
                            mPass2.setImageResource(R.drawable.pw_blank);
                        }
                        else if(str.length() == 3){
                            mPass3.setImageResource(R.drawable.pw_blank);
                        }
                        else if(str.length() == 4){
                            mPass4.setImageResource(R.drawable.pw_blank);
                        }

                        str  = str.substring( 0, str.length() - 1 );

                        resultPassword = str;
                    }

                    break;


                case R.id.lock_ok:
                    Log.i("test1234","!!!!`");
                    if(state == DsStatic.CONFIRMPASSWORD){
                        Integer appPass = getPassword();
                        Log.i("test1234",appPass+"");
                        if(count < 3){
                            Toast.makeText(getApplicationContext(),"비밀번호를 4자리 입력해주세요.",Toast.LENGTH_SHORT).show();
                        }
                        else if(appPass.equals(Integer.valueOf(resultPassword)))
                        {
//                            Toast.makeText(getApplicationContext(),resultPassword+"확인 되었습니다.",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(PasswordActivity.this,MainListActivity.class);

                            startActivity(intent);
                            //이 액티비티 종료
                            finish();
                        }
                        else
                        {
                            txt.setText("비밀번호가 틀렸습니다");
                            PasswordActivity.this.finish();
                        }
                        break;

                    }
                    else if(state == DsStatic.SETPASSWORD){
                        if(count == 0){
                            txt.setText("한 번 더 입력해주세요");
                            count++;
                            beforeResultPassword = resultPassword;
                            resultPassword = "";
                            mPass1.setImageResource(R.drawable.pw_blank);
                            mPass2.setImageResource(R.drawable.pw_blank);
                            mPass3.setImageResource(R.drawable.pw_blank);
                            mPass4.setImageResource(R.drawable.pw_blank);

                        }
                        else if(count < 3){
                            Toast.makeText(getApplicationContext(),"비밀번호를 4자리 입력해주세요.",Toast.LENGTH_SHORT).show();
                        }
                        else if(beforeResultPassword.equals(resultPassword)){
                            setPassword(Integer.valueOf(resultPassword));
                            Toast.makeText(getApplicationContext(),resultPassword+"(으)로 수정되었습니다.",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else{
                            txt.setText("비밀번호가 일치하지 않습니다."+System.getProperty("line.separator")+"처음부터 다시 시도해주세요");
                            resultPassword = "";
                            mPass1.setImageResource(R.drawable.pw_blank);
                            mPass2.setImageResource(R.drawable.pw_blank);
                            mPass3.setImageResource(R.drawable.pw_blank);
                            mPass4.setImageResource(R.drawable.pw_blank);
                            count =0;
                        }

                    }

                    //이 액티비티 종료
//                finish();

                    break;
            }
        }
    };

    void function(String a){

        Integer resultLength = resultPassword.length();
/*
        string=mPass.getText().toString();
        length=mPass.getText().toString().length();

*/

        Log.i("test12", resultPassword.length() + "");
        if(resultLength < 4){

            if(resultLength == 0){
                mPass1.setImageResource(R.drawable.pw_full);
            }
            else if(resultLength == 1){
                mPass2.setImageResource(R.drawable.pw_full);
            }
            else if(resultLength == 2){
                mPass3.setImageResource(R.drawable.pw_full);
            }
            else{
                mPass4.setImageResource(R.drawable.pw_full);
            }
            Log.i("test12", a);
            resultPassword = resultPassword+a;

        }
        else{
            Toast.makeText(getApplicationContext(),"비밀번호는 4자리입니다.",Toast.LENGTH_SHORT).show();
        }



        /*if(string.equals("")||string.equals("0")){
            mPass.setText(""+a);
            on=false;
        }
        else if(clean==true){
            mPass.setText(""+a);
            clean=false;
        }
        else{
            mPass.setText(string+a);
            on=false;
        }*/
    }

    public Integer getPassword(){

        SharedPreferences s = getSharedPreferences("password",MODE_PRIVATE);
        Integer pw = s.getInt("pw",0);

        return pw;
    }

    public void setPassword(Integer password){
        SharedPreferences s = getSharedPreferences("password",MODE_PRIVATE);
        SharedPreferences.Editor editor = s.edit();
        editor.putInt("pw", password);
        editor.commit();
    }

}