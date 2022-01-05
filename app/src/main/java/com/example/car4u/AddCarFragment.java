package com.example.car4u;

import static android.app.Activity.RESULT_CANCELED;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.car4u.Model.Car;
import com.example.car4u.Model.Model;
import com.example.car4u.Model.User;


public class AddCarFragment extends Fragment
{
    static final int REQUEST_IMAGE_CAPTURE = 1;
    final static int RESAULT_SUCCESS = 0;
    public String temp_owner=" ";
    public String temp_model=" ";
    public String temp_year=" ";
    public String temp_price=" ";
    public String temp_location=" ";
    public String temp_phone=" ";
    Bitmap bitmap;
    ImageView avatar;
    View view;
    User user;
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_add_car, container, false);
        user= AddCarFragmentArgs.fromBundle(getArguments()).getUser();
        progressBar = view.findViewById(R.id.addcar_progressbar);
        progressBar.setVisibility(View.GONE);
        EditText owner = view.findViewById(R.id.addcar_owner_et);
        EditText model = view.findViewById(R.id.addcar_model_et);
        EditText year = view.findViewById(R.id.addcar_year_et);
        EditText price = view.findViewById(R.id.addcar_price_et);
        EditText location = view.findViewById(R.id.addcar_location_et);
        EditText phone = view.findViewById(R.id.addcar_phone_et);
        avatar = view.findViewById(R.id.addcar_image);
        Button continueBtn= view.findViewById(R.id.addcar_continue_btn);
        Button cancelBtn= view.findViewById(R.id.addcar_cancel_btn);
        ImageButton editImagebTn = view.findViewById(R.id.addcar_imagev_btn);
        continueBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                editImagebTn.setEnabled(false);
                cancelBtn.setEnabled(false);
                owner.setEnabled(false);
                model.setEnabled(false);
                year.setEnabled(false);
                price.setEnabled(false);
                location.setEnabled(false);
                phone.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);
                temp_owner=owner.getText().toString();
                temp_model=model.getText().toString();
                temp_year=year.getText().toString();
                temp_price=price.getText().toString();
                temp_location=location.getText().toString();
                temp_phone=phone.getText().toString();
                if(bitmap == null)
                {
                    AddCarFragmentDirections.ActionAddCarFragmentToAddCarDescriptionFragment action = AddCarFragmentDirections.actionAddCarFragmentToAddCarDescriptionFragment(temp_owner,temp_model,temp_year,temp_price,temp_location,temp_phone,user,"");
                    Navigation.findNavController(v).navigate(action);
                }
                else
                {
                    Model.instance.saveImage(bitmap,temp_model, url ->
                    {
                        AddCarFragmentDirections.ActionAddCarFragmentToAddCarDescriptionFragment action = AddCarFragmentDirections.actionAddCarFragmentToAddCarDescriptionFragment(temp_owner,temp_model,temp_year,temp_price,temp_location,temp_phone,user,url);
                        Navigation.findNavController(v).navigate(action);
                    });
                }
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Navigation.findNavController(v).popBackStack();
            }
        });
        editImagebTn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                selectImage();
            }
        });
        return view;
    }

    public void selectImage()
    {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose your car picture");

        builder.setItems(options, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int item)
            {

                if (options[item].equals("Take Photo"))
                {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery"))
                {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);

                } else if (options[item].equals("Cancel"))
                {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode != RESULT_CANCELED)
        {
            switch (requestCode) {
                case 0://return from camera
                    if (resultCode == getActivity().RESULT_OK && data != null) {
                        bitmap = (Bitmap) data.getExtras().get("data");
                        avatar.setImageBitmap(bitmap);
                    }

                    break;
                case 1://return from gallery
                    if (resultCode == getActivity().RESULT_OK && data != null)
                    {
                        Uri selectedImage =  data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null)
                        {
                            Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                            if (cursor != null)
                            {
                                cursor.moveToFirst();
                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                avatar.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }
                    }
                    break;
            }
        }
    }
}