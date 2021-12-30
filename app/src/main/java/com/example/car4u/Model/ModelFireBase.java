package com.example.car4u.Model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;

public class ModelFireBase
{
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public void getAllCars(Long since, Model.getAllCarsListener listener)
    {
        db.collection("cars").whereGreaterThanOrEqualTo(Car.LAST_UPDATED, new Timestamp(since, 0)).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                LinkedList<Car> carList = new LinkedList<Car>();
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot doc: task.getResult())
                    {
                        Car c = Car.fromJson(doc.getData());
                        if(c!=null)
                            carList.add(c);
                    }
                }
                listener.onComplete(carList);
            }
        }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                listener.onComplete(null);
            }
        });
    }

    public void getCarByOwner(String owner, Model.getCarByOwnerListener listener)
    {
        DocumentReference docRef = db.collection("cars").document(owner);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task)
            {
                if (task.isSuccessful())
                {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists())
                    {
                        Car c = Car.fromJson(document.getData());
                        if(c!=null)
                            listener.onComplete(c);
                    }
                    else
                    {
                        listener.onComplete(null);
                    }
                }
                else
                {
                    Log.d("TAG", "get failed with ", task.getException());
                    listener.onComplete(null);
                }
            }
        });
    }

    public void addCar(Car car, Model.addCarListener listener)
    {
        db.collection("cars")
                .document((String.valueOf(car.getId_key()))).set(car.toJson())
                .addOnSuccessListener(new OnSuccessListener<Void>()
                {
                    @Override
                    public void onSuccess(@NonNull Void unused)
                    {
                        listener.onComplete();
                    }
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Log.d("TAG", e.getMessage());
                    }
                });
    }

    public void removeCar(Car car, Model.removeCarListener listener)
    {
        db.collection("cars").document((String.valueOf(car.getId_key()))).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>()
                {
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                        listener.onComplete();
                    }
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Log.d("TAG", e.getMessage());
                    }
                });
    }

    public void getAllUsers(Model.getAllUsersListener listener)
    {
        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                LinkedList<User> userList = new LinkedList<User>();
                if(task.isSuccessful())
                {
                    for(QueryDocumentSnapshot doc: task.getResult())
                    {
                        User u = User.fromJson(doc.getData());
                        if(u!=null)
                            userList.add(u);
                    }
                }
                listener.onComplete(userList);
            }
        }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                listener.onComplete(null);
            }
        });
    }

    public void getUserByUsername(String username, Model.getUserByUsernameListener listener)
    {
        DocumentReference docRef = db.collection("users").document(username);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task)
            {
                if (task.isSuccessful())
                {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists())
                    {
                        User u = User.fromJson(document.getData());
                        if(u!=null)
                            listener.onComplete(u);
                    }
                    else
                    {
                        listener.onComplete(null);
                    }
                }
                else
                {
                    Log.d("TAG", "get failed with ", task.getException());
                    listener.onComplete(null);
                }
            }
        });
    }

    public void addUser(User user, Model.addUserListener listener)
    {
        db.collection("users")
                .document(user.getUsername()).set(user.toJson())
                .addOnSuccessListener(new OnSuccessListener<Void>()
                {
                    @Override
                    public void onSuccess(@NonNull Void unused)
                    {
                        listener.onComplete();
                    }
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Log.d("TAG", e.getMessage());
                    }
                });
    }
}
