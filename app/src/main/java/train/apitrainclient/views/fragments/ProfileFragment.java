package train.apitrainclient.views.fragments;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.soundcloud.android.crop.Crop;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;
import train.apitrainclient.R;
import train.apitrainclient.listeners.OnUpdateUserAccountCompletionListener;
import com.pttrackershared.models.eventbus.User;
import train.apitrainclient.networks.retrofit.RestApiManager;
import train.apitrainclient.plugins.FileUtils;
import train.apitrainclient.services.DialogUtils;
import train.apitrainclient.utils.AppUtils;
import train.apitrainclient.utils.Constants;
import train.apitrainclient.utils.SharedPrefManager;
import train.apitrainclient.utils.ValidatorUtils;
import train.apitrainclient.views.activities.UpdatePasswordActivity;
import train.apitrainclient.views.dialogs.PhotoSelectionDialog;

public class ProfileFragment extends BaseFragment implements OnUpdateUserAccountCompletionListener{

    @BindView(R.id.profile_name)
    TextView etName;
    @BindView(R.id.profile_picture)
    ImageView ivProfileImage;
    @BindView(R.id.profile_height)
    TextView etHeight;
    @BindView(R.id.profile_birthday)
    TextView etDob;
    @BindView(R.id.profile_phnumber)
    TextView etPhone;
    @BindView(R.id.profile_weight)
    TextView etWeight;
    @BindView(R.id.pinCode)
    TextView pinCode;
    @BindView(R.id.homeLayout)
    RelativeLayout homeLayout;

    @BindDrawable(R.drawable.ic_custom_back)
    Drawable backArrow;
    @BindColor(R.color.white)
    int white;

    User user;
    //endregion

    //region Other Variables
    private static final int PROFILE_PICTURE_RESULT_CODE = 1234;
    private Calendar calendarSelectedDate;
    private Calendar calendarTargetSelectedDate;
    private PhotoSelectionDialog photoSelectionDialog;
    private Uri mImageCaptureUri;
    private String profilePicturePath;
    private Date dob;
    private boolean isEditingEnabled = false;
    private boolean hasUnsavedChanges;
    private OnUpdateUserAccountCompletionListener onUpdateUserAccountCompletionListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_profile, container, false);
        this.rootView = rootView;
        ButterKnife.bind(this,rootView);
//        AppUtils.checkForGenderView(getActivity(),homeLayout);
        onUpdateUserAccountCompletionListener = (OnUpdateUserAccountCompletionListener) getActivity();

        context = getActivity();
        setHasOptionsMenu(true);
        initData();
        initViews();
        showUserInfo();

        photoSelectionDialog.setOnOptionSelectionListener(new PhotoSelectionDialog.OnOptionSelectedListener() {
            @Override
            public void optionsSelected(int option) {
                if (option == PhotoSelectionDialog.ACCESS_CAMERA) {
                    openCamera();
                } else if (option == PhotoSelectionDialog.ACCESS_GALLERY) {
                    openGallery();
                }
            }
        });

        return rootView;
    }

    public void initData() {
        calendarSelectedDate = Calendar.getInstance();
        calendarTargetSelectedDate = Calendar.getInstance();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home, menu);

        MenuItem item = menu.findItem(R.id.action_add);
        item.setVisible(false);

        MenuItem add_client = menu.findItem(R.id.action_add_client);
        add_client.setVisible(false);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void initViews() {
        photoSelectionDialog = new PhotoSelectionDialog(context);
    }

    private void showUserInfo() {
        user = SharedPrefManager.getUser(context);
        Date date = new Date();
        date.getTime();
        calendarTargetSelectedDate.setTime(date);
        etName.setText(user.getFirstname());

        if (!ValidatorUtils.IsNullOrEmpty(user.getSurname())) {
            etName.setText(user.getFirstname() + " " + user.getSurname());
        }

        if (!ValidatorUtils.IsNullOrEmpty(user.getHeight())) {
            etHeight.setText(user.getHeight() + " " + user.getHeightMeasurement());
        }

        if (!ValidatorUtils.IsNullOrEmpty(user.getWeight())) {
            etWeight.setText(user.getWeight() + " " + user.getWeightMeasurement());
        }

        if (user.getDobString() != null) {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss'Z'");
            try {
                Date dateDob = format.parse(user.getDobString());
                calendarSelectedDate.setTime(dateDob);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            setDob();
        }

        if (!ValidatorUtils.IsNullOrEmpty(user.getTelephone())) {
            etPhone.setText(user.getTelephone());
        }

        pinCode.setText(user.getCode());

        if (!ValidatorUtils.IsNullOrEmpty(user.getImage())) {
            String imageP = user.getImage();

            String imageName = "";
            if (imageP.contains("/")) {
                imageName = imageP.substring(imageP.lastIndexOf("/"), imageP.length());
            } else {
                imageName = user.getImage();
            }

            if (imageName.contains("{")){
                String imageSplit[] = imageName.split("=");
               imageName = imageSplit[1].substring(0,imageSplit[1].length() - 1);

            }

            if (!imageName.contains(".jpg")){
                if (imageName.contains(".jpeg")){
                    String imageSplit[] = imageName.split(".jpeg");
                    imageName = imageSplit[0] + ".jpg";
                }else
                    imageName = imageName + ".jpg";
            }

            Picasso.with(context).load(Constants.IMAGE_URL +
                    imageName).
                    error(R.drawable.avatar).into(ivProfileImage);
        } else {
            if (user.getGender().equalsIgnoreCase("2")) {
                ivProfileImage.setImageResource(R.drawable.avatar);
            } else {
                ivProfileImage.setImageResource(R.drawable.avatar);
            }

        }
    }

    private void setDob() {

        String bDay[] = user.getDobString().split("-");
        String month = getMonth(bDay[1]);
        String birthDay = bDay[2] + " " + month + " " + bDay[0];
        etDob.setText(birthDay);
    }


    public String getMonth(String month){
        int monthNum = Integer.parseInt(month);
        String months = "";
        switch (monthNum){
            case 1: months =  "Jan";
            break;
            case 2: months = "Feb";
            break;
            case 3: months = "Mar";
            break;
            case 4: months = "Apr";
            break;
            case 5: months = "May";
            break;
            case 6: months = "Jun";
            break;
            case 7: months = "Jul";
            break;
            case 8: months = "Aug";
            break;
            case 9: months = "Sep";
            break;
            case 10: months = "Oct";
            break;
            case 11: months = "Nov";
            break;
            case 12: months = "Dec";
            break;
        }
        return months;
    }

    private void updateUserAccount() {
        String nameSurname[] = etName.getText().toString().split(" ");
        String name = nameSurname[0];
        String surname = nameSurname[1];
        SharedPrefManager.setUser(getActivity(),user);
    }

    private void uploadProfileImage() {
        DialogUtils.ShowProgress(context, "Uploading profile image, please wait...");
        RestApiManager.updateProfileImage(context, user,new File(profilePicturePath), new OnUpdateUserAccountCompletionListener() {
            @Override
            public void onSuccess(String  image) {
                URLEncoder.encode(image);
                user.setImage(image);
                SharedPrefManager.setUser(getActivity(),user);
                DialogUtils.HideDialog();
                updateUserAccount();
                profilePicturePath = null;
                onUpdateUserAccountCompletionListener.onSuccess(image);
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                DialogUtils.HideDialog();
                DialogUtils.ShowSnackbarAlert(context, errorMessage);
            }
        });
    }

    @OnClick(R.id.changeImage)
    public void onProfileImageClicked(View view) {
        showPhotoSelectionDialog();
    }

    @OnClick(R.id.updatePass)
    public void updatePass(View view) {
        Intent intent = new Intent(getActivity(), UpdatePasswordActivity.class);
        startActivity(intent);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PROFILE_PICTURE_RESULT_CODE && resultCode == getActivity().RESULT_OK) {
            beginCrop(mImageCaptureUri);
        }
        if (requestCode == Crop.REQUEST_PICK) {
            beginCrop(data.getData());
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == getActivity().RESULT_OK) {
                Uri resultUri = result.getUri();
                ivProfileImage.setImageURI(resultUri);
                profilePicturePath = resultUri.getPath();
                uploadProfileImage();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                DialogUtils.ShowSnackbarAlert(context, error.getMessage());
            }
        }
    }

    //region Actions
    private void showPhotoSelectionDialog() {
        final String permission = android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
        if (!Nammu.checkPermission(permission) && Nammu.isIgnoredPermission(permission) && !Nammu.shouldShowRequestPermissionRationale(getActivity(), permission)) {
            showAppSettingsDialog(getString(R.string.error_message_permission_denied));
            return;
        }
        Nammu.askForPermission(getActivity(), permission, new PermissionCallback() {
            @Override
            public void permissionGranted() {
                photoSelectionDialog.show();
            }

            @Override
            public void permissionRefused() {
                if (!Nammu.shouldShowRequestPermissionRationale(getActivity(), permission) && !Nammu.isIgnoredPermission(permission)) {
                    Nammu.ignorePermission(permission);
                    showAppSettingsDialog(getString(R.string.error_message_permission_denied));
                } else {
                    DialogUtils.ShowSnackbarAlert(context, getString(R.string.error_message_gallery_permission));
                }
            }
        });
    }

    private void openGallery() {
        Crop.pickImage(getContext(),this);
    }

    private void openCamera() {
        final String[] permissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (!Nammu.checkPermission(permissions[0])
                && Nammu.isIgnoredPermission(permissions[0])
                && !Nammu.shouldShowRequestPermissionRationale(getActivity(), permissions[0])) {
            showAppSettingsDialog(getString(R.string.error_message_permission_denied));
            return;
        }

        if (!Nammu.checkPermission(permissions[1])
                && Nammu.isIgnoredPermission(permissions[1])
                && !Nammu.shouldShowRequestPermissionRationale(getActivity(), permissions[1])) {
            showAppSettingsDialog(getString(R.string.error_message_permission_denied));
            return;
        }
        Nammu.askForPermission(getActivity(), permissions, new PermissionCallback() {
            @Override
            public void permissionGranted() {
                boolean hasCamera = context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
                if (hasCamera) {
                    try {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        File file = new File(FileUtils.GetTempDirectory() + File.separator + "tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg");

                        file.getParentFile().mkdirs();
                        file.delete();
                        file.createNewFile();

                        File noMediaFile = new File(FileUtils.GetTempDirectory() + File.separator + ".nomedia");
                        noMediaFile.createNewFile();

                        mImageCaptureUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", file);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                        try {
                            intent.putExtra("return-data", true);
                            startActivityForResult(intent, PROFILE_PICTURE_RESULT_CODE);
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    DialogUtils.ShowSnackbarAlert(context, "This device doesn't have camera");
                }
            }

            @Override
            public void permissionRefused() {
                if (!Nammu.checkPermission(permissions[0])
                        && !Nammu.shouldShowRequestPermissionRationale(getActivity(), permissions[0])
                        && !Nammu.isIgnoredPermission(permissions[0])) {
                    Nammu.ignorePermission(permissions[0]);
                    showAppSettingsDialog(getString(R.string.error_message_permission_denied));
                } else if (!Nammu.checkPermission(permissions[1])
                        && !Nammu.shouldShowRequestPermissionRationale(getActivity(), permissions[1])
                        && !Nammu.isIgnoredPermission(permissions[1])) {
                    Nammu.ignorePermission(permissions[1]);
                    showAppSettingsDialog(getString(R.string.error_message_permission_denied));
                } else {
                    DialogUtils.ShowSnackbarAlert(context, getString(R.string.error_message_gallery_permission));
                }
            }
        });
    }

    private void beginCrop(Uri source) {
        CropImage.activity(source)
                .setGuidelines(CropImageView.Guidelines.OFF)
                .setAspectRatio(1, 1)
                .start(getContext(),this);
    }

    private void showAppSettingsDialog(String message) {
        DialogUtils.ShowSnackbarAlert(context, message, new DialogUtils.OnActionSelectedListener() {
            @Override
            public void onActionSelected() {
                startInstalledAppDetailsActivity(context);
            }
        });
    }

    public static void startInstalledAppDetailsActivity(final Context context) {
        if (context == null) {
            return;
        }
        final Intent i = new Intent();
        i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.setData(Uri.parse("package:" + context.getPackageName()));
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(i);
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void onSuccess(String image) {

    }

    @Override
    public void onFailure(int errorCode, String errorMessage) {

    }
}
