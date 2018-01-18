package cl.anpetrus.smartprice.views.products.partials;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.frosquivel.magicalcamera.MagicalCamera;
import com.frosquivel.magicalcamera.MagicalPermissions;

import java.util.Date;

import cl.anpetrus.smartprice.R;
import cl.anpetrus.smartprice.views.products.add.FieldCallBack;

import static cl.anpetrus.smartprice.data.ImageProcessor.getResizedBitmap;

/**
 * Created by Petrus on 15-11-2017.
 */

public class PhotoField {
    public static final String MESSAGE_PERMISSION_DENIED = "Favor dar permisos";
    public static final String FOLDER_NAME_IMAGES = "SmartPriceApp";
    public static final int NEW_WIDTH_600 = 600;
    public static final String MESSAGE_UNCOMPLETE = "Favor toma una foto";
    public static final String TEXT_BUTTON_OTHER_PHOTO = "Tomar otra";
    public static final String VOID_STR = "";
    public static final String MESSAGE_STATUS = "Favor tome una foto del producto";
    private RelativeLayout root;
    private Button button;
    private FloatingActionButton floatingActionButton;
    private ImageView imageView;
    private Bitmap imageFull;
    private MagicalPermissions magicalPermissions;
    private MagicalCamera magicalCamera;
    private FieldCallBack callBack;
    private Activity activity;

    public PhotoField(ViewGroup viewGroup, Activity activity) {

        this.activity = activity;
        LayoutInflater layoutInflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        root = (RelativeLayout) layoutInflater.inflate(R.layout.partial_photo, viewGroup, false);
        imageView = (ImageView) root.getChildAt(0);
        button = (Button) root.getChildAt(1);
        floatingActionButton = (FloatingActionButton) root.getChildAt(2);

        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
        magicalPermissions = new MagicalPermissions(activity, permissions); //magical no controla si se da permiso solo a una solicitud
        magicalCamera = new MagicalCamera(activity, 100, magicalPermissions);
    }

    public View getView() {
        return root;
    }

    public void setCallBack(FieldCallBack callBack) {
        this.callBack = callBack;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                magicalCamera.takePhoto();
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageFull = magicalCamera.rotatePicture(imageFull, MagicalCamera.ORIENTATION_ROTATE_90);
                imageView.setImageBitmap(imageFull);
            }
        });
    }

    public void getPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        magicalPermissions.permissionResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
            Toast.makeText(this.activity, MESSAGE_PERMISSION_DENIED, Toast.LENGTH_LONG).show();
        }
    }

    public void getPhotoResult(int requestCode, int resultCode, Intent data) {

        magicalCamera.resultPhoto(requestCode, resultCode, data);
        Bitmap photo = magicalCamera.getPhoto();
        String location = magicalCamera.savePhotoInMemoryDevice(photo, FOLDER_NAME_IMAGES, String.valueOf(new Date().getTime()), MagicalCamera.JPEG, true);
        if (location != null) {
            this.imageFull = getResizedBitmap(photo, NEW_WIDTH_600);
            imageView.setImageBitmap(photo);
            imageView.setTag(location);
            imageView.setVisibility(View.VISIBLE);
            floatingActionButton.setVisibility(View.VISIBLE);
            button.setText(TEXT_BUTTON_OTHER_PHOTO);
            callBack.completed();
        } else {
            callBack.uncompleted(MESSAGE_UNCOMPLETE);
        }
    }


    public String getStatus() {
        String location = (String) imageView.getTag();
        if (VOID_STR.equals(location)) {
            return MESSAGE_STATUS;
        }
        return null;
    }

    public String getResult() {
        return (String) imageView.getTag();
    }

    public Bitmap getImageFull() {
        return imageFull;
    }

}
