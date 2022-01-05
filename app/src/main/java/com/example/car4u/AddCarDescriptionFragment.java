package com.example.car4u;

import static android.app.Activity.RESULT_CANCELED;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

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

import java.util.LinkedList;
import java.util.List;


public class AddCarDescriptionFragment extends Fragment
{
    static final int REQUEST_IMAGE_CAPTURE = 1;
    final static int RESAULT_SUCCESS = 0;
    View view;
    User user;
    public String temp_owner=" ";
    public String temp_model=" ";
    public String temp_year=" ";
    public String temp_price=" ";
    public String temp_location=" ";
    public String temp_phone=" ";
    Bitmap bitmap;
    ImageView avatar;
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_add_car_description, container, false);
        temp_owner= AddCarDescriptionFragmentArgs.fromBundle(getArguments()).getTempOwner();
        temp_model= AddCarDescriptionFragmentArgs.fromBundle(getArguments()).getTempModel();
        temp_year= AddCarDescriptionFragmentArgs.fromBundle(getArguments()).getTempYear();
        temp_price= AddCarDescriptionFragmentArgs.fromBundle(getArguments()).getTempPrice();
        temp_location= AddCarDescriptionFragmentArgs.fromBundle(getArguments()).getTempLocation();
        temp_phone= AddCarDescriptionFragmentArgs.fromBundle(getArguments()).getTempPhone();
        user= AddCarDescriptionFragmentArgs.fromBundle(getArguments()).getUser();
        avatar = view.findViewById(R.id.addcar_image);
        progressBar = view.findViewById(R.id.addcar_des_progressbar);
        progressBar.setVisibility(View.GONE);
        EditText description = view.findViewById(R.id.addcar_des_description_et);
        Button saveBtn= view.findViewById(R.id.addcar_des_save_btn);
        Button cancelBtn= view.findViewById(R.id.addcar_des_cancel_btn);
        ImageButton editImagebTn = view.findViewById(R.id.addcar_imagev_btn);
        saveBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                progressBar.setVisibility(View.VISIBLE);
                cancelBtn.setEnabled(false);
                description.setEnabled(false);
                Car car = new Car();
                car.setOwner(temp_owner);
                car.setModel(temp_model);
                car.setYear(temp_year);
                car.setPrice(temp_price);
                car.setLocation(temp_location);
                car.setPhone(temp_phone);
                car.setCar_username(user.name);
                car.setDescription(description.getText().toString());
                if(bitmap == null)
                {
                    Model.instance.addCar(car,()->
                    {
                        Toast.makeText(getActivity(), car.car_username + " Added New Car!!", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(v).navigate(R.id.action_addCarDescriptionFragment_pop);
                    });
                }
                else
                {
                    Model.instance.saveImage(bitmap,car.getCar_username(), url ->
                    {
                        car.setAvatarUrl(url);
                        Model.instance.addCar(car,()->
                        {
                            Toast.makeText(getActivity(), car.car_username + " Added New Car!!", Toast.LENGTH_SHORT).show();
                            Navigation.findNavController(v).navigate(R.id.action_addCarDescriptionFragment_pop);
                        });
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

