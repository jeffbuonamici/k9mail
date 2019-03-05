	package com.fsck.k9.activity;

 import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

 import com.fsck.k9.ui.R;

 public class LabelPage extends K9Activity implements View.OnClickListener {

     @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_label_page);
        setButtonListeners();
    }

     private void setButtonListeners() {
        Button mLabelButtonWork = (Button)findViewById(R.id.label_work_tag);
        mLabelButtonWork.setOnClickListener(this);
        Button mLabelSchoolWork = (Button)findViewById(R.id.label_school_tag);
        mLabelSchoolWork.setOnClickListener(this);
        Button mLabelPersonalWork = (Button)findViewById(R.id.label_personal_tag);
        mLabelPersonalWork.setOnClickListener(this);
    }

     @Override
    public void onClick(View view) {
        int id = view.getId();
        System.out.print("%%%%%%%%%%%%");
        if(id == R.id.label_work_tag) {
            System.out.println("work");
        } else if(id == R.id.label_school_tag) {
            System.out.println("school");
        } else if(id == R.id.label_personal_tag) {
            System.out.println("personal");
        }
    }
}