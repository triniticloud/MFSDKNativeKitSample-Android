package com.activeai.MFSDKNativeKitSample.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.activeai.MFSDKNativeKitSample.App;
import com.activeai.MFSDKNativeKitSample.MyMFSDKMessagingManager;
import com.activeai.MFSDKNativeKitSample.R;
import com.morfeus.android.mfsdk.MFSDKMessagingManager;
import com.morfeus.android.mfsdk.Template;
import com.morfeus.android.mfsdk.messenger.session.MFSDKSessionProperties;
import com.morfeus.android.mfsdk.messenger.source.exception.MfMsgModelException;

import ai.active.uikit.template.TemplateConstants;
import ai.active.uikit.template.header.MFListCardHeaderTemplateModel;
import ai.active.uikit.template.header.MFListCardHeaderTemplateView;
import ai.active.uikit.template.tabbed.MFListCardTabbedTemplateModel;
import ai.active.uikit.template.tabbed.MFListCardTabbedTemplateView;
import ai.active.uikit.template.textcard.MFInTextCardTemplateView;
import ai.active.uikit.template.textcard.MFOutTextCardTemplateView;
import ai.active.uikit.template.textcard.MFTextCardTemplateModel;

import static com.activeai.MFSDKNativeKitSample.utils.Constants.BOT_ID;

/**
 * MainActivity is the first screen of App
 * @author  Active.Ai
 * @version 1.0
 * @since   2020-01-09
 */
public class MainActivity extends AppCompatActivity {

    private Button mLoginButton;
    private Button mPostLoginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        onClicks();

    }

    private void init() {
        mLoginButton = findViewById(R.id.btn_login);
        mPostLoginButton = findViewById(R.id.btn_login_post);
    }

    private void onClicks() {
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openChatScreen(getSessionProperties());
            }
        });

        mPostLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostLoginActivity.start(MainActivity.this);
            }
        });
    }

    /**
     * This method is used for opening ChatScreen
     * @param sessionProperties the sessionProperties is passed to the MFSDKMessagingManager
     */
    private void openChatScreen(MFSDKSessionProperties sessionProperties) {
        registerMyTemplate(this, MyMFSDKMessagingManager.getInstance().getMFSDK());

        MyMFSDKMessagingManager.getInstance().getMFSDK()
                .showScreen(this, BOT_ID, sessionProperties);

    }

    private MFSDKSessionProperties getSessionProperties() {
        return new MFSDKSessionProperties.Builder()
                .setScreenToDisplay(MFSDKSessionProperties.MFScreen.MFScreenMessageVoice)
                .build();
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
