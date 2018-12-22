package com.example.android.flavor;

import android.content.Intent;
        import android.net.Uri;
        import android.os.Bundle;
        import android.os.Environment;
        import android.provider.MediaStore;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.text.InputType;
        import android.util.Log;
        import android.view.View;
        import android.view.inputmethod.EditorInfo;
        import android.widget.ArrayAdapter;
        import android.widget.EditText;
        import android.widget.Spinner;
        import android.widget.Toast;

        import java.io.File;
        import java.text.SimpleDateFormat;
        import java.util.Date;

//Adapteed from code written by Colette Kirwan. DCU Open Education

/** The OrderActivity class provides the java functionality code for the activity_order.xml code
 * and extends the class AppCompatActivity.
 *
 */
public class OrderActivity extends AppCompatActivity
{
    // The following variables are initialised
    Uri mPhotoURI;
    Spinner mSpinner;
    EditText mCustomerName;
    EditText meditOptional;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 2;
    private static final String TAG = "Assign3";

    @Override
    /**The onCreat method setups the activity and sets the values of some of the variables
    //initialised at the start. It also setup for recieving input data.
    //Here also an adapter inflates the spinner_days xml layout file with the arraylist
    // ui_time_entries stored in the strings file. The adapter is then used to populate the
    // elements of the spinner on then activity_order screen.
     */
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        meditOptional = (EditText) findViewById(R.id.editOptional);

        meditOptional.setImeOptions(EditorInfo.IME_ACTION_DONE);
        meditOptional.setRawInputType(InputType.TYPE_CLASS_TEXT);
        //initialise spinner using the integer array
        mSpinner = (Spinner) findViewById(R.id.spinner);
        mCustomerName = (EditText) findViewById(R.id.editCustomer);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.ui_time_entries, R.layout.spinner_days);
        mSpinner.setAdapter(adapter);
    }

    /* @param v
    This method triggers the launch of the phone camera and stores the image in the variable mPhotoURI
     */
    public void dispatchTakePictureIntent(View v)
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String imageFileName = "my_tshirt_image_" + timeStamp + ".jpg";

        Log.i(TAG, "imagefile");

        File file = new File(Environment.getExternalStorageDirectory(), imageFileName);

        mPhotoURI = Uri.fromFile(file);
        Log.i(TAG, mPhotoURI.toString());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoURI);
        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
        //incase of caching if it comes from the activity stack, just a precaution
        intent.removeExtra(MediaStore.EXTRA_OUTPUT);

    }

    /**
     * When the image is saved and the user returns an OK that they are happy with the image
     * this method generates both a Toast and a AlertDialog which indicate then image was taken and saved sucessfully.
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        //also can give user a message that everything went ok
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK)
        {
            //let user know that image saved
            //I have strings in strings.xml but have hardcoded here to copy/paste to students if needed
            CharSequence text = "Image Taken successfully";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
            //or perhaps do a dialog should only use one method i.e. toast or dialog, but have both code here for demo purposes
            //also I have strings in strings.xml but have hardcoded here to copy/paste to students if needed
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Notification!").setMessage("Image saved successfully.").setPositiveButton("OK", null).show();
        }
    }

    /**
     * Returns the Email Body Message.
     * <p> Email body message is created used prescription related data inputed from user </p>
     * The message is composed of user input and text which is stored in strings.
     * @return Email Body Message
     */
    private String createOrderSummary()
    {

        String orderMessage = getString(R.string.customer_name) + " " + mCustomerName.getText().toString();
        orderMessage += "\n" + "\n" + getString(R.string.order_message_1);
        String optionalInstructions = meditOptional.getText().toString();

        orderMessage += "\n" + getString(R.string.order_message_collect) + ((CharSequence) mSpinner.getSelectedItem()).toString() + " days";
        orderMessage += "\n" + getString(R.string.order_message_end) + "\n" + mCustomerName.getText().toString();
        return orderMessage;

        //update screen
    }

    /**
     * This method does a check to ensure there is input in teh Name field an dif there is not
     * a Toast is displayed to say the field is empty.
     * If there is a name present then an intent is run which launches an e-mail app on the
     * phone and also information is passed to the app including name, subject, photo and then number
     * of days selected from the spinner within which you wish to recieve you order.
     *
     */
    public void sendEmail(View v)
    {

        //check that Name is not empty, and ask do they want to continue
        String customerName = mCustomerName.getText().toString();
        if (customerName.matches(""))
        {
            Toast.makeText(this, getString(R.string.customer_name_blank), Toast.LENGTH_SHORT).show();

            /* we can also use a dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Notification!").setMessage("Customer Name not set.").setPositiveButton("OK", null).show();
            */
        } else
        {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("*/*");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.to_email)});
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject));
            intent.putExtra(Intent.EXTRA_STREAM, mPhotoURI);
            intent.putExtra(Intent.EXTRA_TEXT, createOrderSummary());
            if (intent.resolveActivity(getPackageManager()) != null)
            {
                startActivity(intent);
            }
        }
    }


}
