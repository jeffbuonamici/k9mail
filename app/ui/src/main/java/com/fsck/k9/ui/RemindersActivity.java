package com.fsck.k9.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.fsck.k9.Account;
import com.fsck.k9.Preferences;
import com.fsck.k9.activity.K9Activity;
import com.fsck.k9.controller.MessagingController;
import com.fsck.k9.controller.MessagingListener;
import com.fsck.k9.mail.Address;
import com.fsck.k9.mail.Message;
import com.fsck.k9.mail.internet.MimeMessage;
import com.fsck.k9.mail.internet.TextBody;

import org.apache.james.mime4j.util.MimeUtil;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

public class RemindersActivity extends K9Activity implements
        View.OnClickListener{

    Button btnDatePicker, btnTimePicker, submitReminder;
    EditText txtDate, txtTime, txtMessage;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setLayout(R.layout.activity_reminders);

        btnDatePicker=(Button)findViewById(R.id.btn_date);
        btnTimePicker=(Button)findViewById(R.id.btn_time);
        submitReminder=(Button)findViewById(R.id.submit_reminder);

        txtDate=(EditText)findViewById(R.id.in_date);
        txtTime=(EditText)findViewById(R.id.in_time);
        txtMessage=(EditText)findViewById(R.id.reminder_message);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        submitReminder.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }

        if(v==submitReminder) {
            String reminderDate = txtDate.getText().toString();
            String reminderTime = txtTime.getText().toString();
            String reminderMessage = txtMessage.getText().toString();


            sendReminder(reminderMessage);

            NavUtils.navigateUpFromSameTask(this);

        }
    }

    public void sendReminder(String reminderMessage){
        MessagingController mc = MessagingController.getInstance(this);

        Preferences pr = Preferences.getPreferences(this);

        List<Account> accounts = pr.getAccounts();
        Account myAccount = accounts.get(0);

        Message msg = new MimeMessage();
        msg.setHeader("headerName", "headerValue");
        msg.setSubject("Reminder");
        msg.setSender(new Address(myAccount.toString()));
        msg.setRecipient(Message.RecipientType.TO, new Address(myAccount.toString()));
        msg.setBody(new TextBody(reminderMessage));
        try{
            msg.setEncoding(MimeUtil.ENC_8BIT);
        }
        catch (Exception e){

        }


        Set<MessagingListener> listeners = mc.getListeners();
        MessagingListener listener = null;

        for (MessagingListener l : listeners) {
            listener = l;
            break;
        }


        mc.sendMessage(myAccount, msg, "This is a Reminder", listener);
    }


    public static void launch(Activity activity){
        Intent intent = new Intent(activity, RemindersActivity.class);
        activity.startActivity(intent);
    }
}
