package com.activeai.MFSDKNativeKitSample.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.activeai.MFSDKNativeKitSample.App;
import com.activeai.MFSDKNativeKitSample.MyMFSDKMessagingManager;
import com.activeai.MFSDKNativeKitSample.R;
import com.morfeus.android.mfsdk.MFSDKMessagingManager;
import com.morfeus.android.mfsdk.Template;
import com.morfeus.android.mfsdk.messenger.session.MFSDKSessionProperties;
import com.morfeus.android.mfsdk.messenger.source.exception.MfMsgModelException;

import java.util.HashMap;

import ai.active.uikit.template.TemplateConstants;
import ai.active.uikit.template.header.MFListCardHeaderTemplateModel;
import ai.active.uikit.template.header.MFListCardHeaderTemplateView;
import ai.active.uikit.template.tabbed.MFListCardTabbedTemplateModel;
import ai.active.uikit.template.tabbed.MFListCardTabbedTemplateView;
import ai.active.uikit.template.textcard.MFInTextCardTemplateView;
import ai.active.uikit.template.textcard.MFOutTextCardTemplateView;
import ai.active.uikit.template.textcard.MFTextCardTemplateModel;

import static com.activeai.MFSDKNativeKitSample.utils.Constants.BOT_ID;
import static com.activeai.MFSDKNativeKitSample.utils.Constants.CUSTOMER_ID;
import static com.activeai.MFSDKNativeKitSample.utils.Constants.SESSION_ID;

/**
 * PostLoginActivity is the second screen of App
 * @author  Active.Ai
 * @version 1.0
 * @since   2020-01-09
 */
public class PostLoginActivity extends AppCompatActivity {

    private EditText mCustomerID;
    private EditText mSessionID;
    private Button mLogin;

    public static void start(Context context) {
        Intent intent = new Intent(context, PostLoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_login);

        init();
        onClick();

    }

    private void init() {
        mCustomerID = findViewById(R.id.et_customerID);
        mSessionID = findViewById(R.id.et_sessionID);
        mLogin = findViewById(R.id.btn_post_login);
    }

    private void onClick() {
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChatScreeen(getSessionProperties());
            }
        });
    }

    /**
     * This method is used for opening ChatScreen
     * @param sessionProperties the sessionProperties is passed to the MFSDKMessagingManager
     */
    private void openChatScreeen(MFSDKSessionProperties sessionProperties) {
        registerMyTemplate(this, MyMFSDKMessagingManager.getInstance().getMFSDK());

        MyMFSDKMessagingManager.getInstance().getMFSDK()
                .showScreen(this, BOT_ID, sessionProperties);
    }

    /**
     * Return an {@link MFSDKSessionProperties} object that can be used by MFSDK
     * @return the SDK Session Properties
     */
    private MFSDKSessionProperties getSessionProperties() {
        return new MFSDKSessionProperties.Builder()
                .setScreenToDisplay(MFSDKSessionProperties.MFScreen.MFScreenMessageVoice)
//                .setUserInfo(getUserInfo())
                .build();
    }


    /**
     * Return an {@link HashMap < String ,  String >} object that contains userInfo like
     * @see CUSTOMER_ID,
     * @see SESSION_ID
     * @return userInfo
     */
    private HashMap<String, String> getUserInfo() {
        HashMap<String, String> userInfo = new HashMap<>();

        String customerID = mCustomerID.getText().toString();
        if (!TextUtils.isEmpty(customerID)) {
            userInfo.put(CUSTOMER_ID, customerID);
        }

        String sessionID = mSessionID.getText().toString();
        if (!TextUtils.isEmpty(sessionID)) {
            userInfo.put(SESSION_ID, sessionID);
        }

        return userInfo;
    }

    /**
     * This method is used for register custom templates
     * @param ctx is an application Context
     * @param MFSdk is intstance of {@link MFSDKMessagingManager}
     */
    private void registerMyTemplate(Context ctx, MFSDKMessagingManager MFSdk) {
        try {
            Template headerlistCardTemplate = new Template(
                    TemplateConstants.IDs.LIST_CARD_HEADER_CUSTOM_TEMPLATE,
                    new MFListCardHeaderTemplateView(ctx),
                    new MFListCardHeaderTemplateModel(TemplateConstants.IDs.LIST_CARD_HEADER_CUSTOM_TEMPLATE));


            MFSdk.registerTemplate(headerlistCardTemplate);

            Template listCardTabbed = new Template(
                    TemplateConstants.IDs.LIST_CARD_TABBED_CUSTOM_TEMPLATE,
                    new MFListCardTabbedTemplateView(ctx),
                    new MFListCardTabbedTemplateModel(TemplateConstants.IDs.LIST_CARD_TABBED_CUSTOM_TEMPLATE));

            MFSdk.registerTemplate(listCardTabbed);

            Template textTemplateIn = new Template(
                    TemplateConstants.IDs.TEXT_CARD_TEMPLATE_IN,
                    new MFInTextCardTemplateView(ctx),
                    new MFTextCardTemplateModel(TemplateConstants.IDs.TEXT_CARD_TEMPLATE_IN));

            MFSdk.registerTemplate(textTemplateIn);

            Template textTemplateOut = new Template(
                    TemplateConstants.IDs.TEXT_CARD_TEMPLATE_OUT,
                    new MFOutTextCardTemplateView(ctx),
                    new MFTextCardTemplateModel(TemplateConstants.IDs.TEXT_CARD_TEMPLATE_OUT));

            MFSdk.registerTemplate(textTemplateOut);

        } catch (MfMsgModelException e) {
            Log.e(App.TAG, e.toString());
        }
    }
}
