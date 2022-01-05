package com.example.car4u;

import static android.app.Activity.RESULT_CANCELED;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.car4u.Model.Car;
import com.example.car4u.Model.Model;
import com.example.car4u.Model.User;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;


public class CarEditFragment extends Fragment
{
    static final int REQUEST_IMAGE_CAPTURE = 1;
    final static int RESAULT_SUCCESS = 0;
    Car car;
    View view;
    User user;
    public String temp_owner=" ";
    public String temp_model=" ";
    public String temp_year=" ";
    public String temp_price=" ";
    public String temp_location=" ";
    public String temp_phone=" ";
    Bitmap bitmap;
    ImageView avatarImg;
    ProgressBar progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view =inflater.inflate(R.layout.fragment_car_edit, container, false);
        car=CarEditFragmentArgs.fromBundle(getArguments()).getCar();
        user=CarEditFragmentArgs.fromBundle(getArguments()).getUser();
        progressBar = view.findViewById(R.id.car_edit_progressBar);
        progressBar.setVisibility(View.GONE);

        avatarImg = view.findViewById(R.id.car_edit_imagev);
        avatarImg.setImageResource(R.drawable.avatar);
        if(car.getAvatarUrl() != null)
        {
            Picasso.get().load(car.getAvatarUrl()).placeholder(R.drawable.avatar).into(avatarImg);
        }

        EditText edit_owner = view.findViewById(R.id.car_edit_owner);
        edit_owner.setText(car.owner);

        EditText edit_model = view.findViewById(R.id.car_edit_model);
        edit_model.setText(car.model);

        EditText edit_year = view.findViewById(R.id.car_edit_year);
        edit_year.setText(car.year);

        EditText edit_price = view.findViewById(R.id.car_edit_price);
        edit_price.setText(car.price);

        EditText edit_location = view.findViewById(R.id.car_edit_location);
        edit_location.setText(car.location);

        EditText edit_phone = view.findViewById(R.id.car_edit_phone);
        edit_phone.setText(car.phone);


        Button continueBtn= view.findViewById(R.id.car_edit_continuebtn);
        Button cancelBtn= view.findViewById(R.id.car_edit_cancelbtn);
        Button deleteBtn= view.findViewById(R.id.car_edit_deletebtn);
        ImageButton editImagebTn = view.findViewById(R.id.car_edit_picturebtn);
        continueBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                temp_owner=edit_owner.getText().toString();
                temp_model=edit_model.getText().toString();
                temp_year=edit_year.getText().toString();
                temp_price=edit_price.getText().toString();
                temp_location=edit_location.getText().toString();
                temp_phone=edit_phone.getText().toString();
                if(bitmap == null)
                {
                    CarEditFragmentDirections.ActionCarEditFragmentToCarEditDescriptionFragment action = CarEditFragmentDirections.actionCarEditFragmentToCarEditDescriptionFragment(car,temp_owner,temp_model,temp_year,temp_price,temp_location,temp_phone, "");
                    Navigation.findNavController(v).navigate(action);
                }
                else
                {
                    Model.instance.saveImage(bitmap,temp_model, url ->
                    {
                        CarEditFragmentDirections.ActionCarEditFragmentToCarEditDescriptionFragment action = CarEditFragmentDirections.actionCarEditFragmentToCarEditDescriptionFragment(car,temp_owner,temp_model,temp_year,temp_price,temp_location,temp_phone, url);
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
        deleteBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                progressBar.setVisibility(View.VISIBLE);
                cancelBtn.setEnabled(false);
                continueBtn.setEnabled(false);
                edit_owner.setEnabled(false);
                edit_model.setEnabled(false);
                edit_year.setEnabled(false);
                edit_price.setEnabled(false);
                edit_location.setEnabled(false);
                edit_phone.setEnabled(false);
                Model.instance.removeCar(car,()->
                {
                    Navigation.findNavController(v).popBackStack();
                });
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
                        avatarImg.setImageBitmap(bitmap);
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
                                avatarImg.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }
                    }
                    break;
            }
        }
    }
}
