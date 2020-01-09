package com.activeai.MFSDKNativeKitSample;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.morfeus.android.mfsdk.MFRequestData;
import com.morfeus.android.mfsdk.MFResponseData;
import com.morfeus.android.mfsdk.MFSDKInitializationException;
import com.morfeus.android.mfsdk.MFSDKMessagingHandler;
import com.morfeus.android.mfsdk.MFSDKMessagingManager;
import com.morfeus.android.mfsdk.MFSDKMessagingManagerKit;
import com.morfeus.android.mfsdk.MFSDKProperties;
import com.morfeus.android.mfsdk.Template;
import com.morfeus.android.mfsdk.messenger.session.MFSDKSessionProperties;
import com.morfeus.android.mfsdk.messenger.source.exception.MfMsgModelException;
import com.morfeus.android.mfsdk.push.model.PushModel;

import ai.active.uikit.template.TemplateConstants;
import ai.active.uikit.template.header.MFListCardHeaderTemplateModel;
import ai.active.uikit.template.header.MFListCardHeaderTemplateView;
import ai.active.uikit.template.tabbed.MFListCardTabbedTemplateModel;
import ai.active.uikit.template.tabbed.MFListCardTabbedTemplateView;
import ai.active.uikit.template.textcard.MFInTextCardTemplateView;
import ai.active.uikit.template.textcard.MFOutTextCardTemplateView;
import ai.active.uikit.template.textcard.MFTextCardTemplateModel;

import static com.activeai.MFSDKNativeKitSample.utils.Constants.BOT_ID;
import static com.activeai.MFSDKNativeKitSample.utils.Constants.BOT_NAME;
import static com.activeai.MFSDKNativeKitSample.utils.Constants.BOT_URL;
import static com.activeai.MFSDKNativeKitSample.utils.Constants.DISABLE_SCREENSHOT;
import static com.activeai.MFSDKNativeKitSample.utils.Constants.ENABLE_ROOTED_DEVICE;
import static com.activeai.MFSDKNativeKitSample.utils.Constants.ENABLE_SSL;
import static com.activeai.MFSDKNativeKitSample.utils.Constants.SPEECH_API_KEY;


/**
 * Helper class to provide instance of MfSdk
 */
public class MyMFSDKMessagingManager {

    private static final String TAG = MyMFSDKMessagingManager.class.getSimpleName();

    private static MyMFSDKMessagingManager sInstance;
    private MFSDKMessagingManager mMFSDKMessagingManager;

    /**
     * This method is used to initialize the {@link MFSDKMessagingManagerKit}
     * @param ctx is an application Context
     */
    private MyMFSDKMessagingManager(Context ctx) {
        Log.d(App.TAG, " >>> Creating MyMFSDKMessagingManager instance");
        try {
            final MFSDKProperties properties = getMFSDKProperties(ctx);

            mMFSDKMessagingManager = new MFSDKMessagingManagerKit.Builder(ctx)
                    .setSdkProperties(properties)
                    .setOnBoarding(true)
                    .build();
            mMFSDKMessagingManager.initWithProperties();
        } catch (MFSDKInitializationException e) {
            Log.e(App.TAG, " >>> Failed to creating instance of MFSDKMessagingManager"
                    + e.getLocalizedMessage());
        }
    }

    /**
     * This method is used to creating the instance of {@link MyMFSDKMessagingManager}
     * @param ctx is an application Context
     */
    static void createInstance(Context ctx) {
        if (sInstance == null)
            sInstance = new MyMFSDKMessagingManager(ctx);
    }

    /**
     * Return an {@link MyMFSDKMessagingManager} object
     * @return sInstance
     */
    public static MyMFSDKMessagingManager getInstance() {
        return sInstance;
    }

    /**
     * Return an {@link MFSDKMessagingManager} instance of MFSDKMessagingManagerKit
     * @return mMFSDKMessagingManager
     */
    public MFSDKMessagingManager getMFSDK() {
        Log.d(App.TAG, " >>> Getting MFSDKMessagingManager instance");
        return mMFSDKMessagingManager;
    }

    /**
     * Return an {@link MFSDKProperties} object that is pass to the {@link MFSDKMessagingManagerKit}
     * @return MFSDKProperties
     */
    private MFSDKProperties getMFSDKProperties(final Context ctx) {
        Log.d(App.TAG, " >>> Getting MFSDKProperties");
        return new MFSDKProperties.Builder(BOT_URL)
                .addBot(BOT_ID, BOT_NAME)
                .setGoogleVoiceKey(SPEECH_API_KEY)
                .setSuportMultilanguage(true)
                .setSupportRetry(true)
                .enableSSL(ENABLE_SSL)
                .disableScreenShot(DISABLE_SCREENSHOT)
                .enableRootedDeviceCheck(ENABLE_ROOTED_DEVICE)
                .setMFSDKMessagingHandler(new MFSDKMessagingHandler() {
                    @Override
                    public void onPushClick(PushModel pushModel) {
                        Log.d(TAG, "push received. \n PushModel: " + pushModel.toString());
                        String applicationId = "3456743223";
                        String customerId = null;

                        // Do some operation
                        MFSDKSessionProperties mfsdkSessionProperties = new MFSDKSessionProperties
                                .Builder(applicationId, customerId)
                                .setPushModel(pushModel)
                                .setScreenToDisplay(MFSDKSessionProperties.MFScreen.MFScreenMessageVoice)
                                .build();


                        registerMyTemplate(ctx, mMFSDKMessagingManager);
                        mMFSDKMessagingManager.showScreen(
                                ctx, BOT_ID, mfsdkSessionProperties);
                    }

                    @Override
                    public void keepMainAppAlive() {

                    }

                    @Override
                    public boolean shouldKeepMorfeusSDKAlive() {
                        return false;
                    }

                    @Override
                    public boolean onLoginRequest(Context context, int i) {
                        return false;
                    }

                    @NonNull
                    @Override
                    public MFRequestData onRequest(MFRequestData mfRequestData) {
                        return mfRequestData;
                    }

                    @Override
                    public void onResponse(MFResponseData mfResponseData) {

                    }
                })
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
            Log.e(TAG, e.toString());
        }
    }
}
