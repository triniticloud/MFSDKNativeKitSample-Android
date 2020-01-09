package ai.active.uikit.template.textcard;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.morfeus.android.mfsdk.ui.screen.adapter.TemplateViewHolder;
import com.morfeus.android.mfsdk.ui.utils.Utils;
import com.morfeus.android.mfsdk.ui.widget.bubble.exception.IllegalTemplateModelException;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.Style;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.StyleNotFoundException;
import com.morfeus.android.mfsdk.ui.widget.bubble.view.BaseView;
import com.morfeus.android.mfsdk.ui.widget.bubble.view.TemplateView;
import com.morfeus.android.mfsdk.ui.widget.bubble.view.template.TemplateViewUtils;
import com.morfeus.android.mfsdk.ui.widget.faq.customFeedback.FeedBackView;
import com.morfeus.android.mfsdk.ui.widget.faq.customFeedback.FeedbackModel;

import ai.active.uikit.R;
import ai.active.uikit.template.TemplateConstants;
import ai.active.uikit.template.util.TextUtil;

import static com.google.common.base.Preconditions.checkNotNull;

public class MFInTextCardTemplateView extends TemplateView {

    public MFInTextCardTemplateView(Context context) {
        super(context);
    }

    @Override
    public TemplateView inflate(Context context) {
        return (MFInTextCardTemplateView) LayoutInflater.from(context)
                .inflate(R.layout.templateview_text_in_layout, this);
    }

    @Override
    public BaseView create(Context context) {
        return new MFInTextCardTemplateView(context);
    }

    @Override
    public TemplateViewHolder createViewHolder(BaseView view) {
        return new MFInTextCardTemplateViewHolder((View) view);
    }

    @Override
    protected void setStyle(@NonNull TemplateView view, @Nullable Style style) {
        if (style == null)
            return;

        String bg = style.getBackgroundImage();

        if (!TextUtils.isEmpty(bg)) {
            view.findViewById(R.id.fl_main_container_templateview_text_in).setBackgroundResource(
                    TemplateViewUtils.getDrawableResourceId(view.getContext(), bg));
        }
    }

    @NonNull
    @Override
    protected String getTemplateId() {
        return TemplateConstants.IDs.TEXT_CARD_TEMPLATE_IN;
    }

    public static class MFInTextCardTemplateViewHolder extends TemplateViewHolder {
        private static final String TAG = MFInTextCardTemplateViewHolder.class.getName();

        private static final int MAX_WIDTH_IN_DP = 230;
        private final TextView mTvTime;
        private final ImageView mIVTick;
        private final FeedBackView mFeedbackView;
        private Context mContext;
        private TextView mTVTextMessage;

        MFInTextCardTemplateViewHolder(View itemView) {
            super(itemView);

            mContext = itemView.getContext();
            mTVTextMessage = (TextView) itemView.findViewById(R.id.tv_msg_in);
            mTvTime = (TextView) itemView.findViewById(R.id.tv_bubble_time);
            mIVTick = (ImageView) itemView.findViewById(R.id.iv_bubble_tick);
            mIVTick.setVisibility(GONE);
            mFeedbackView = (FeedBackView) itemView.findViewById(com.morfeus.android.R.id.fb_customView);
            mFeedbackView.show(false);
        }

        @Override
        public void setData(@NonNull final TemplateModel model) {
            if (!(checkNotNull(model) instanceof MFTextCardTemplateModel)) {
                throw new IllegalTemplateModelException(
                        "Error: Invalid template model!"
                );
            }

            //TODO move time setting to messenger module
            if (!TextUtils.isEmpty(model.getTime())) {
                mTvTime.setText(model.getTime());
            } else {
                String time = Utils.getTime();
                model.setTime(time);
                mTvTime.setText(time);
            }

            MFTextCardTemplateModel textCardTemplateModel = (MFTextCardTemplateModel) model;
//            applyDefaultCardStyle(textCardTemplateModel.isIncoming());

            final String message = textCardTemplateModel.getTextMessage();
            if (!TextUtils.isEmpty(message)) {
                mTVTextMessage.setText(Html.fromHtml(TextUtil.appendNonBreakingSpace(message)),
                        TextView.BufferType.SPANNABLE);
                if (model.isTextToSpeech()) {
//                    EventBus.getDefault().post(new TextToSpeechEvent(mTVTextMessage.getText().toString()));
                }

                // TODO:KEEP DON"T REMOVE
//                mRLContainer.setOnLongClickListener(new OnLongClickListener() {
//                    @Override
//                    public boolean onLongClick(View view) {
//                        ActionbarCopyStateEvent copyClipboardEvent = new ActionbarCopyStateEvent(message);
//                        EventBus.getDefault().post(copyClipboardEvent);
//                        return false;
//                    }
//                });
            }

            // decide to to show feedback view
            if (textCardTemplateModel.isIncoming() && model.isFAQ()) {
                FeedbackModel feedbackModel = model.getFeedbackModel();
                if (feedbackModel == null) {
                    feedbackModel = new FeedbackModel(model.getBotId(), true, true);
                }
                mFeedbackView.show(true);
                mFeedbackView.notifyChange(feedbackModel);
                mFeedbackView.setFeedbackListener(new FeedBackView.OnFeebackSubmit() {
                    @Override
                    public void onSubmitted(FeedbackModel feedbackModel) {
                        model.setFeedbackModel(feedbackModel);
                    }

                    @Override
                    public void onLike(FeedbackModel feedbackModel) {
                        model.setFeedbackModel(feedbackModel);
                    }
                });
            } else {
                mFeedbackView.show(false);
            }

        }

        // TODO-KEEP: DON'T REMOVE
//        private void setMsgStatus(int msgStatus) {
//            switch (msgStatus) {
//                case MessageStatus.UNSEND:
//                    mIvTickImage.setImageResource(R.drawable.tick_msg_sent_read);
//                    break;
//                case MessageStatus.SEND:
//                    // TODO change to double tick
//                    mIvTickImage.setImageResource(R.drawable.tick_msg_sent_read);
//                    break;
//                case MessageStatus.FAILED:
//                    mIvTickImage.setImageResource(R.drawable.tick_msg_pending_or_failed);
//                    break;
//            }
//        }


        private void applyDefaultCardStyle(boolean incoming) {

            // Get default style

            // Set bubble background image

            //

            String styleTemplateId
                    = getTemplateStyleId(incoming, TemplateConstants.IDs.TEXT_CARD_TEMPLATE);

            try {
                Style defaultCardStyle = mStyleFactory.getStyle(styleTemplateId);

                if (defaultCardStyle != null) {

                    /* max width is hardcoded because max-width property
                    is removed from MFCardStyle.json. */
                    mTVTextMessage.setMaxWidth(convertDpToPixel(MAX_WIDTH_IN_DP, mContext));
                }
            } catch (StyleNotFoundException e) {
                Log.e(TAG, e.toString());
            }

        }
    }
}
