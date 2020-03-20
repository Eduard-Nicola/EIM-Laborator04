package lab04.eim.systems.cs.pub.ro;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class ContactsManagerActivity extends AppCompatActivity {

    private static final int CONTACTS_MANAGER_REQUEST_CODE = 1;
    private EditText editTextName;
    private EditText editTextNumber;
    private EditText editTextEmail;
    private EditText editTextAddress;
    private Button buttonSave;
    private Button buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_manager);

        editTextName = findViewById(R.id.editTextName);
        editTextNumber = findViewById(R.id.editTextNumber);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextAddress = findViewById(R.id.editTextAddress);
        buttonSave = findViewById(R.id.buttonSave);
        buttonCancel = findViewById(R.id.buttonCancel);

        buttonSave.setOnClickListener(new ButtonClickListener('s'));
        buttonCancel.setOnClickListener(new ButtonClickListener('c'));

        Intent intent = getIntent();
        if (intent != null) {
            String phone = intent.getStringExtra("lab04.eim.systems.cs.pub.ro.PHONE_NUMBER_KEY");
            if (phone != null) {
                editTextNumber.setText(phone);
            } else {
                Toast.makeText(this, getResources().getString(R.string.phone_error), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch(requestCode) {
            case CONTACTS_MANAGER_REQUEST_CODE:
                setResult(resultCode, new Intent());
                finish();
                break;
        }
    }

    public class ButtonClickListener implements View.OnClickListener {
        private char button;

        public ButtonClickListener(char button) {
            this.button = button;
        }

        @Override
        public void onClick(View v) {
            if (button == 's') {
                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                if (editTextName != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.NAME, editTextName.getText().toString());
                }
                if (editTextNumber != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE, editTextNumber.getText().toString());
                }
                if (editTextEmail != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.EMAIL, editTextEmail.getText().toString());
                }
                if (editTextAddress != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.POSTAL, editTextAddress.getText().toString());
                }

//                startActivity(intent);
                startActivityForResult(intent, CONTACTS_MANAGER_REQUEST_CODE);
            }
            if (button == 'c') {
                setResult(Activity.RESULT_CANCELED, new Intent());
                finish();
            }
        }
    };
}
