package ai.active.uikit.template.header;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.morfeus.android.mfsdk.ui.entity.Postback;
import com.morfeus.android.mfsdk.ui.screen.adapter.TemplateViewHolder;
import com.morfeus.android.mfsdk.ui.widget.bubble.exception.IllegalTemplateModelException;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.Style;
import com.morfeus.android.mfsdk.ui.widget.bubble.view.BaseView;
import com.morfeus.android.mfsdk.ui.widget.bubble.view.TemplateView;

import ai.active.uikit.R;
import ai.active.uikit.template.TemplateConstants;

public class MFListCardHeaderTemplateView extends TemplateView {


    public MFListCardHeaderTemplateView(Context context) {
        super(context);
    }

    @Override
    public TemplateView inflate(Context context) {
        return (TemplateView) LayoutInflater.from(context)
                .inflate(R.layout.templateview_list_card_header_layout, this);
    }

    @Override
    public TemplateViewHolder createViewHolder(BaseView baseView) {
        return new ListCardHeaderViewHolder((View) baseView);
    }

    @Override
    public BaseView create(Context context) {
        return new MFListCardHeaderTemplateView(context);
    }

    @Override
    protected void setStyle(@NonNull TemplateView templateView, @Nullable Style style) {

    }

    @NonNull
    @Override
    protected String getTemplateId() {
        return TemplateConstants.IDs.LIST_CARD_HEADER_CUSTOM_TEMPLATE;
    }

    public static class ListCardHeaderViewHolder extends TemplateViewHolder {

        MFListCardHeaderTemplateModel model;
        private RecyclerView mRvRootRecyclerview;
        private Context mContext;
        private LinearLayout botIconHeader;

        public ListCardHeaderViewHolder(View view) {
            super(view);
            mContext = view.getContext();
            mRvRootRecyclerview = (RecyclerView) view.findViewById(R.id.rv_list_rootview_header_list_card);
            LinearLayoutManager horizontallayoutmanager = new LinearLayoutManager(mContext);
            horizontallayoutmanager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mRvRootRecyclerview.setLayoutManager(horizontallayoutmanager);
            mRvRootRecyclerview.setItemAnimator(new DefaultItemAnimator());
            botIconHeader = (LinearLayout) view.findViewById(R.id.ll_bot_icon_layout);
            mRvRootRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                }

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (recyclerView.getLayoutManager() != null) {
                        LinearLayoutManager layoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());
                        if (layoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
                            botIconHeader.setVisibility(VISIBLE);
                        } else {
                            botIconHeader.setVisibility(GONE);
                        }
                    }
                }
            });

        }

        @Override
        public void setData(@NonNull TemplateModel templateModel) {

            if (!(templateModel instanceof MFListCardHeaderTemplateModel)) {
                throw new IllegalTemplateModelException(
                        "Error: Invalid template model!"
                );
            }

            model = (MFListCardHeaderTemplateModel) templateModel;

            if (!model.haveItems()) {
                return;
            }

            ListCardHeaderAdpter listCardHeaderAdpter = new ListCardHeaderAdpter(model.getItemList(), model.getBotId());
            mRvRootRecyclerview.setAdapter(listCardHeaderAdpter);
            listCardHeaderAdpter.setItemClickListener(new OnClickItemListener() {
                @Override
                public void onClick(@NonNull String payload, @NonNull String action) {

                    model.sendPostback(
                            new Postback(
                                    model.getBotId(),
                                    payload,
                                    action,
                                    null
                            )
                    );
                }
            });


        }

    }

}
