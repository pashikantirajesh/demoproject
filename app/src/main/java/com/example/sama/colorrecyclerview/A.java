package com.example.sama.colorrecyclerview;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.webkit.HttpAuthHandler;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class A extends AppCompatActivity {
String filename;
    byte[] bbyte;
    EditText name_title_edit_text, charity_id_proof_edittext, mobile_number_edit_text, charity_fund_edittext,
            help_start_date_edit_text, help_end_date_edittext, posted_date_edit_text, address_edittext, discription_edittext,
            volunteer_names_edittext, volunteer_mobile_number_edittext;


//    JSONArray imagetype, imagestrng;

    Button post_charity;
    MediaController mediaControls;

    DatePickerDialog datePickerDialog;
//    RequestQueue requestQueue;
    Calendar mycalender;
    ImageView msssss;
    String type = "", encoded = "";

    int requestfrom;
    ArrayList<String> subcategorylist;
    int userid;
    Button uploadimage;
    String authtoken;
    static Toolbar toolbar;
    TextView titleTextV;
    RelativeLayout backLauout1;
    String username;
    ArrayList<Uri> mArrayUri;
    String imageEncoded;
    List<String> imagesEncodedList;
    private GridView gvGallery;
    String videotype = "", video64 = "";
    private int REQUEST_TAKE_GALLERY_VIDEO = 2;
    //    int cert_id;
    ScrollView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);





        mArrayUri = new ArrayList<>();
        gvGallery = findViewById(R.id.gv);





        uploadimage = findViewById(R.id.uploadimge);

        uploadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);

                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                intent.setType("*/*");
                startActivityForResult(intent, 12);
            }
        });






        post_charity = (Button) findViewById(R.id.post_charity);



        post_charity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                posss();
                //Post_charity();

            }
        });




    }

    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         // When an Image is picked
            if (requestCode == 12 && resultCode == RESULT_OK
                    && null != data) {

                Uri data1 = data.getData();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                FileInputStream fis = null;

//String loc=getPathFromUri(A.this,data1);
                String loc=FileUtils.getPath(A.this,data1);

filename= String.valueOf((new File(loc)).getName());
             //   Log.d("filename", String.valueOf(new File(data1.getPath()).getName()));

                try {
                    fis = new FileInputStream(new File(loc));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


                byte[] buf = new byte[1024];
                    int n;
                    try {
                        while (-1 != (n = fis.read(buf)))
                            baos.write(buf, 0, n);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                bbyte = baos.toByteArray();
                Log.d("size", String.valueOf(bbyte.length));



            }else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }


        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                return true;
            }

            default:
                return super.onOptionsItemSelected(item);

        }
    }


    @SuppressLint("NewApi")
    public static String getPathFromUri(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

// DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
// ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

// TODO handle non-primary volumes
            }
// DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);




            }
// MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
// MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

// Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
// File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {


        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }
public  void posss(){

    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.nerd_girl);

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
    byte[] imageBytes = bbyte;

    final HttpClient httpclient = new DefaultHttpClient();
    final HttpPost httpPost = new HttpPost("http://www.erp.pencaptech.info/app/pcd-insert-files");

    String boundary = "-------------" + System.currentTimeMillis();

    httpPost.setHeader("Content-type", "multipart/form-data; boundary="+boundary);

    ByteArrayBody bab = new ByteArrayBody(imageBytes, filename);
    StringBody sbOwner = new StringBody("43", ContentType.TEXT_PLAIN);
    StringBody sbGroup = new StringBody("unicsol", ContentType.TEXT_PLAIN);

    HttpEntity entity = MultipartEntityBuilder.create()
            .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
            .setBoundary(boundary)
            .addPart("projectId", sbOwner)
            .addPart("name", sbGroup)
            .addPart("doc_file", bab)
            .build();

    httpPost.setEntity(entity);
    Thread thread = new Thread(new Runnable() {

        @Override
        public void run() {
            try  {
                HttpResponse response = httpclient.execute(httpPost);





                StringBuilder sb = new StringBuilder();
                try {
                    BufferedReader reader =
                            new BufferedReader(new InputStreamReader(response.getEntity().getContent()), 65728);
                    String line = null;

                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                }
                catch (IOException e) { e.printStackTrace(); }
                catch (Exception e) { e.printStackTrace(); }


                System.out.println("finalResult " + sb.toString());
                //Log.d("respp", String.valueOf(response.getEntity().getContent()));




               // Toast.makeText(A.this, ""+response.toString(), Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });

    thread.start();

}

}
