package train.apitrainclient.views.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchaseHistoryResponseListener;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import train.apitrainclient.R;
import train.apitrainclient.listeners.OnUserPackageTypeListener;
import train.apitrainclient.listeners.OnUserPackageUpdateListener;

import com.pttrackershared.models.eventbus.PackageTypeModel;
import com.pttrackershared.models.eventbus.User;
import train.apitrainclient.networks.retrofit.RestApiManager;
import train.apitrainclient.services.DialogUtils;
import train.apitrainclient.utils.SharedPrefManager;
import train.apitrainclient.utils.TimeUtils;

public class UpgradeActivity extends AppCompatActivity {

    @BindView(R.id.membershipBadge)
    ImageView membershipBadge;
    @BindView(R.id.joiningDateValue)
    TextView joiningDateValue;
    @BindView(R.id.referralCodeValue)
    TextView referralCodeValue;
    @BindView(R.id.trialPeriodValue)
    TextView trialPeriodValue;
    @BindView(R.id.smartWatchPinValue)
    TextView smartWatchPinValue;
    @BindView(R.id.startDateValue)
    TextView startDateValue;
    @BindView(R.id.endDateValue)
    TextView endDateValue;
    @BindView(R.id.currentMembership)
    TextView currentMembership;
    @BindView(R.id.price1)
    TextView price1;
    @BindView(R.id.price2)
    TextView price2;
    @BindView(R.id.spinner1)
    Spinner spinner1;
    @BindView(R.id.spinner2)
    Spinner spinner2;
    @BindView(R.id.checkbox1)
    CheckBox checkbox1;
    @BindView(R.id.checkbox2)
    CheckBox checkbox2;
    @BindView(R.id.btn_upgrade)
    Button btn_upgrade;
    BillingClient mBillingClient;
    User user;
    List<PackageTypeModel> packageTypeJsonModel;
    List<PackageTypeModel> selectedList = new ArrayList<>();
    public int selectedPackagePlan = 0;
    static final String ITEM_SKU = "product_1";
    Date date;
    Date newDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);
        ButterKnife.bind(this);
        user = SharedPrefManager.getUser(this);
        loadPackageTypeList();
        setDefalutMemberValue();
        createSpinners();
        showInfo();

        checkbox1.performClick();

        mBillingClient = BillingClient.newBuilder(this).setListener(new PurchasesUpdatedListener() {
            @Override
            public void onPurchasesUpdated(int responseCode, @Nullable List<Purchase> purchases) {
                Log.d("nitincheck", "onPurchasesUpdated:" + purchases);
                if (responseCode == 0) {
                    try {
                        updatePackagePayment(purchases.get(0));
                        DialogUtils.ShowSnackbarAlert(UpgradeActivity.this, "Item successfully purchase. ProductId=" + purchases.get(0).getOrderId());
                    } catch (Exception e) {
                        DialogUtils.ShowSnackbarAlert(UpgradeActivity.this, "Item successfully purchase.");
                    }
                } else {
                    DialogUtils.ShowSnackbarAlert(UpgradeActivity.this, "Item purchasing failed.");
                }
            }
        }).build();

        mBillingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@BillingClient.BillingResponse int billingResponseCode) {
                if (billingResponseCode == BillingClient.BillingResponse.OK) {
                    Log.d("nitincheck", "onBillingSetupFinished:" + billingResponseCode);
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
            }
        });

    }

    private void updatePackagePayment(Purchase purchase) {
        RestApiManager.updatePackagePayment(this, purchase, selectedList.get(selectedPackagePlan).getPrice(), selectedList.get(selectedPackagePlan).getPrice()
                , new OnUserPackageUpdateListener() {
                    @Override
                    public void onSuccess(Object object) {
                        updatePackage();
                    }

                    @Override
                    public void onFailure(int errorCode, String errorMessage) {

                    }
                });
    }


    public void showInfo(){
        String dateString = user.getTrn_date();
        SimpleDateFormat formatIn = new SimpleDateFormat("yyyy-mm-dd");
        SimpleDateFormat formatOut = new SimpleDateFormat("dd/mm/yyyy");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(formatIn.parse(dateString));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String newDate = formatOut.format(calendar.getTime());

        joiningDateValue.setText(newDate);
        referralCodeValue.setText("No code");
        smartWatchPinValue.setText("No PIN");
        startDateValue.setText(newDate);
        if (user.getPackage_item() == 2 || user.getPackage_item() == 4 && user.getPackage_item() == 7 || user.getPackage_item() == 9
        || user.getPackage_item() == 12 || user.getPackage_item() == 14){
            endDateValue.setText(findFutureDate(user.getTrn_date(),6));
        }else {
            endDateValue.setText(findFutureDate(user.getTrn_date(),12));
        }

        upgradeProcess();
    }

    public void createSpinners(){
        ArrayList<String>duration = new ArrayList<>();
        duration.add("Monthly");
        duration.add("Yearly");

        ArrayAdapter<String> spinner1Adapter = new ArrayAdapter<String>(UpgradeActivity.this,
                R.layout.sprinner_heightweight_layout,duration);
        spinner1.setAdapter(spinner1Adapter);
        ArrayAdapter<String> spinner2Adapter = new ArrayAdapter<String>(UpgradeActivity.this,
                R.layout.sprinner_heightweight_layout,duration);
        spinner2.setAdapter(spinner2Adapter);
    }

    private void loadPackageTypeList() {
        packageTypeJsonModel = new ArrayList<>();
        user = SharedPrefManager.getUser(this);
        DialogUtils.ShowProgress(this, "Please wait..");
        RestApiManager.getUserPackageType(this, user.getUserId(), new OnUserPackageTypeListener() {
            @Override
            public void onSuccess(List<PackageTypeModel> packageTypeList) {
                packageTypeJsonModel.addAll(packageTypeList);
                selectedList.add(packageTypeJsonModel.get(1));
                DialogUtils.HideDialog();
            }

            @Override
            public void onFailure(int errorCode, String errorMessage) {
                DialogUtils.HideDialog();
                DialogUtils.ShowSnackbarAlert(UpgradeActivity.this, errorMessage);
            }
        });
    }


    public long getPassedDays(String dateString){

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format.parse(dateString);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date now = new Date();
        long difference = now.getTime() - date.getTime();
        long differenceDays = difference / (1000 * 60 * 60 * 24);
        return differenceDays;
    }

    void setDefalutMemberValue() {

        startDateValue.setText(user.getTrn_date());

        if (user.getPackage_item() == 1 || user.getPackage_item() == 6 || user.getPackage_item() == 11) {
            currentMembership.setText(getResources().getString(R.string.txt_current_membership) + "TRIAL");
            membershipBadge.setImageResource(R.drawable.apitraintrial);
        } else if (user.getPackage_item() == 2 || user.getPackage_item() == 3 || user.getPackage_item() == 7 ||
                user.getPackage_item() == 8 || user.getPackage_item() == 12 || user.getPackage_item() == 13) {
            currentMembership.setText(getResources().getString(R.string.txt_current_membership) + "LITE");
            membershipBadge.setImageResource(R.drawable.apitrainlite);
        } else if (user.getPackage_item() == 4 || user.getPackage_item() == 5 || user.getPackage_item() == 9 ||
                user.getPackage_item() == 10 || user.getPackage_item() == 14 || user.getPackage_item() == 15
        ) {
            currentMembership.setText(getResources().getString(R.string.txt_current_membership) + "PRO");
            membershipBadge.setImageResource(R.drawable.apitrainpro);
        }

        long days = getPassedDays(user.getTrn_date());
        if (user.getPackage_item() == 1 || user.getPackage_item() == 6 || user.getPackage_item() == 11){
            if (days <= 15){
                trialPeriodValue.setText(days + "");
            }else {
                trialPeriodValue.setText("Expired");
            }
        }


        if (user.getPackage_item() == 2 || user.getPackage_item() == 4 || user.getPackage_item() == 7 ||
                user.getPackage_item() == 9 || user.getPackage_item() == 11 || user.getPackage_item() == 12 || user.getPackage_item() == 14) {
//            tvPeriod.setText(getResources().getString(R.string.txt_period) + "Monthly");
        } else if (user.getPackage_item() == 3 || user.getPackage_item() == 5 ||
                user.getPackage_item() == 8 || user.getPackage_item() == 10 || user.getPackage_item() == 13 || user.getPackage_item() == 15) {
//            tvPeriod.setText(getResources().getString(R.string.txt_period) + "Yearly");
        } else if (user.getPackage_item() == 1 || user.getPackage_item() == 6) {
//            tvPeriod.setText(getResources().getString(R.string.txt_period) + "");
        }

        if (user.getPackage_item() == 1 || user.getPackage_item() == 6 || user.getPackage_item() == 11) {
            endDateValue.setText(TimeUtils.dateAfterDay(user.getTrn_date(), 10));
        } else if (user.getPackage_item() == 2 || user.getPackage_item() == 4 || user.getPackage_item() == 7 ||
                user.getPackage_item() == 9 || user.getPackage_item() == 12 || user.getPackage_item() == 14) {
            endDateValue.setText(TimeUtils.dateAfterMonth(user.getTrn_date(), 1));
        } else if (user.getPackage_item() == 3 || user.getPackage_item() == 5 || user.getPackage_item() == 8 ||
                user.getPackage_item() == 10 || user.getPackage_item() == 13 || user.getPackage_item() == 15
        ) {
            endDateValue.setText(TimeUtils.dateAfterYear(user.getTrn_date(), 1));
        }


        if (user.getUser_group() == 3) {
//            btnUpgrade.setVisibility(View.GONE);
        }

    }


    private void updatePackage() {
        DialogUtils.ShowProgress(UpgradeActivity.this, "Please wait..");
        RestApiManager.updatePackage(UpgradeActivity.this, user.getUserId(), selectedList.get(selectedPackagePlan).getId(),
                selectedList.get(selectedPackagePlan).getPid(), new OnUserPackageUpdateListener() {
                    @Override
                    public void onSuccess(Object object) {
                        DialogUtils.HideDialog();
                        setDefalutMemberValue();
                        DialogUtils.ShowSnackbarAlert(UpgradeActivity.this, "Package Update Successfully.");
                    }

                    @Override
                    public void onFailure(int errorCode, String errorMessage) {
                        DialogUtils.HideDialog();
                        DialogUtils.ShowSnackbarAlert(UpgradeActivity.this, errorMessage);
                    }
                });
    }


    public void upgradeProcess(){

        selectedList.clear();
        if (packageTypeJsonModel != null && packageTypeJsonModel.size() > 0) {
            for (int index = 0; index < packageTypeJsonModel.size(); index++) {
                if (packageTypeJsonModel.get(index).getDuration() == 1) {
                    selectedList.add(packageTypeJsonModel.get(index));
                }
            }
        }

        if (packageTypeJsonModel != null && packageTypeJsonModel.size() > 0) {
            price1.setText(selectedList.get(0).getPrice() + "");
            price2.setText(selectedList.get(1).getPrice() + "");
        }


        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedList.clear();
                if (packageTypeJsonModel != null && packageTypeJsonModel.size() > 0) {
                    for (int index = 0; index < packageTypeJsonModel.size(); index++) {
                        if (packageTypeJsonModel.get(index).getDuration() == 1) {
                            selectedList.add(packageTypeJsonModel.get(index));
                        }
                    }
                }
                if (packageTypeJsonModel != null && packageTypeJsonModel.size() > 0) {
                    price1.setText(selectedList.get(0).getPrice() + "");
                    price2.setText(selectedList.get(1).getPrice() + "");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedList.clear();
                if (packageTypeJsonModel != null && packageTypeJsonModel.size() > 0) {
                    for (int index = 0; index < packageTypeJsonModel.size(); index++) {
                        if (packageTypeJsonModel.get(index).getDuration() == 1) {
                            selectedList.add(packageTypeJsonModel.get(index));
                        }
                    }
                }
                if (packageTypeJsonModel != null && packageTypeJsonModel.size() > 0) {
                    price1.setText(selectedList.get(0).getPrice() + "");
                    price2.setText(selectedList.get(1).getPrice() + "");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String packageName[] = {"PT Lite Monthly", "PT Lite Yearly", "PT Pro Monthly", "PT Pro Yearly",
                        "Client Lite Monthly", "Client Lite Yearly", "Client Pro Monthly", "Client Pro Yearly"};

                String inappPurchaseProductName[] = {"pt_lite_monthly", "pt_lite_yearly", "pt_pro_monthly", "pt_pro_yearly",
                        "client_lite_monthly", "client_lite_yearly", "client_pro_monthly", "client_pro_yearly"};

                String finaliUpgradeInappPurchaseProductName = "";
                if (packageTypeJsonModel != null && packageTypeJsonModel.size() > 0) {
                    for (int i = 0; i < packageName.length; i++) {
                        if (selectedList.get(selectedPackagePlan).getName().equalsIgnoreCase(packageName[i])) {
                            finaliUpgradeInappPurchaseProductName = inappPurchaseProductName[i];
                        }
                    }
                }
                Log.d("nitincheck", "finaliUpgradeInappPurchaseProductName:" + finaliUpgradeInappPurchaseProductName);

                BillingFlowParams.Builder builder = BillingFlowParams.newBuilder()
                        .setSku(finaliUpgradeInappPurchaseProductName).setType(BillingClient.SkuType.SUBS);
                int responseCode = mBillingClient.launchBillingFlow(UpgradeActivity.this, builder.build());

                mBillingClient.consumeAsync("purchaseToken", listener);


                List<String> skuList = new ArrayList<>();
                skuList.add("product_1");
                skuList.add("product_2");
                SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
                params.setSkusList(skuList).setType(BillingClient.SkuType.INAPP);
                mBillingClient.querySkuDetailsAsync(params.build(),
                        new SkuDetailsResponseListener() {
                            @Override
                            public void onSkuDetailsResponse(int responseCode, List<SkuDetails> skuDetailsList) {
                                Log.d("nitincheck", "onSkuDetailsResponse:" + skuDetailsList);
                                if (responseCode == BillingClient.BillingResponse.OK
                                        && skuDetailsList != null) {
                                    for (SkuDetails skuDetails : skuDetailsList) {
                                        String sku = skuDetails.getSku();
                                        String price = skuDetails.getPrice();
                                        if ("product_1".equals(sku)) {
//                                    Toast.makeText(SettingsActivity.this, "Product 1 -" + price, Toast.LENGTH_SHORT).show();
                                        } else if ("product_2".equals(sku)) {
//                                    Toast.makeText(SettingsActivity.this, "Product 2-" + price, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }

                            }
                        });


                mBillingClient.queryPurchaseHistoryAsync(BillingClient.SkuType.INAPP,
                        new PurchaseHistoryResponseListener() {
                            @Override
                            public void onPurchaseHistoryResponse(@BillingClient.BillingResponse int responseCode,
                                                                  List<Purchase> purchasesList) {
                                Log.d("nitincheck", "onPurchaseHistoryResponse:" + purchasesList);
                                if (responseCode == BillingClient.BillingResponse.OK
                                        && purchasesList != null) {
                                    for (Purchase purchase : purchasesList) {
                                        // Process the result.
                                    }
                                }
                            }
                        });



            }
        });

    }

    final ConsumeResponseListener listener = new ConsumeResponseListener() {
        @Override
        public void onConsumeResponse(@BillingClient.BillingResponse int responseCode, String outToken) {
            if (responseCode == BillingClient.BillingResponse.OK) {
                Log.d("nitincheck", "onConsumeResponse:" + outToken);
                // Handle the success of the consume operation.
                // For example, increase the number of coins inside the user's basket.
            }
        }
    };

    @OnClick(R.id.backButton)
    public void Back(){
        finish();
    }

    @OnClick(R.id.checkbox1)
    public void checkBox1(){
            checkbox2.setChecked(false);
            checkbox1.setChecked(true);
            selectedPackagePlan = 0;
    }

    @OnClick(R.id.checkbox2)
    public void checkBox2(){
            checkbox2.setChecked(true);
            checkbox1.setChecked(false);
            selectedPackagePlan = 1;
    }


    public String findFutureDate(String date,int period){
        String dates[] = date.split("-");
        String futureDate = "";
        int month = Integer.parseInt(dates[1]);
        int year = Integer.parseInt(dates[2]);
        int maxMonth = 12;
        if (period == 6){
            if (month + 6 > maxMonth){
                month = maxMonth - month;
            }else if (month + 6 == maxMonth){
                month = maxMonth;
            }else {
                month = month + 6;
            }
        }else {
            year = year + 1;
        }

         futureDate = dates[0] + "/" + month + "/" + year;

        return futureDate;
    }
}
