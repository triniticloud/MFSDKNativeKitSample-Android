package ai.active.uikit.template.tabbed;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.morfeus.android.mfsdk.ui.entity.Postback;
import com.morfeus.android.mfsdk.ui.screen.adapter.TemplateViewHolder;
import com.morfeus.android.mfsdk.ui.widget.bubble.exception.IllegalTemplateModelException;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;
import com.morfeus.android.mfsdk.ui.widget.bubble.style.Style;
import com.morfeus.android.mfsdk.ui.widget.bubble.view.BaseView;
import com.morfeus.android.mfsdk.ui.widget.bubble.view.TemplateView;

import java.util.List;

import ai.active.uikit.R;
import ai.active.uikit.template.TemplateConstants;

public class MFListCardTabbedTemplateView extends TemplateView {

    public MFListCardTabbedTemplateView(Context context) {
        super(context);
    }

    @Override
    public TemplateView inflate(Context context) {
        return (TemplateView) LayoutInflater.from(context)
                .inflate(R.layout.templateview_list_card_tabbed_layout, this);
    }

    @Override
    public TemplateViewHolder createViewHolder(BaseView baseView) {
        return new ListCardChooseViewHolder((View) baseView);
    }

    @Override
    public BaseView create(Context context) {
        return new MFListCardTabbedTemplateView(context);
    }

    @Override
    protected void setStyle(@NonNull TemplateView templateView, @Nullable Style style) {

    }

    @NonNull
    @Override
    protected String getTemplateId() {
        return TemplateConstants.IDs.LIST_CARD_TABBED_CUSTOM_TEMPLATE;
    }

    public static class ListCardChooseViewHolder extends TemplateViewHolder {

        private RecyclerView mRvListCardTabbedHeaderList;
        private RecyclerView mRvListCardTabbedBodyList;
        private Context mContext;

        public ListCardChooseViewHolder(View view) {
            super(view);

            mRvListCardTabbedHeaderList = (RecyclerView) view.findViewById(R.id.rv_list_list_card_tabbed_header);
            mRvListCardTabbedBodyList = (RecyclerView) view.findViewById(R.id.rv_list_list_card_tabbed_body);
            mContext = view.getContext();
            mRvListCardTabbedBodyList.setLayoutManager(new LinearLayoutManager(mContext));
            mRvListCardTabbedBodyList.setItemAnimator(new DefaultItemAnimator());
            LinearLayoutManager HorizontallinearLayoutManager = new LinearLayoutManager(mContext);
            HorizontallinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            mRvListCardTabbedHeaderList.setLayoutManager(HorizontallinearLayoutManager);
            mRvListCardTabbedHeaderList.setItemAnimator(new DefaultItemAnimator());

        }

        @Override
        public void setData(@NonNull TemplateModel templateModel) {

            if (!(templateModel instanceof MFListCardTabbedTemplateModel)) {
                throw new IllegalTemplateModelException(
                        "Error: Invalid template model!"
                );
            }

            final MFListCardTabbedTemplateModel model = (MFListCardTabbedTemplateModel) templateModel;

            // set model items to header adapter
            if (model.getmItems().size() > 0) {

                final ListCardTabbedHeaderAdapter headerAdapter = new ListCardTabbedHeaderAdapter(model.getmItems());
                headerAdapter.setItemClickListener(new ListCardTabbedHeaderAdapter.OnClickItemListener() {
                    @Override
                    public void onClick(MFListCardTabbedTemplateModel.MFListItem item) {

                        headerAdapter.setSelectionItems(item);

                        if (item.getItems().size() > 0) {
                            mRvListCardTabbedBodyList.setAdapter(null);
                            mRvListCardTabbedBodyList.setAdapter(getAdapter(item, model));
                        }
                    }
                });
                mRvListCardTabbedHeaderList.setAdapter(headerAdapter);


                // show child item based on the selection of header item
                if (getSelectedItem(model.getmItems()) != null) {
                    mRvListCardTabbedBodyList.setAdapter(getAdapter(getSelectedItem(model.getmItems()), model));
                    if (getSelectedItemPosition(model.getmItems()) != -1) {
                        mRvListCardTabbedHeaderList.scrollToPosition(getSelectedItemPosition(model.getmItems()));
                    }
                    headerAdapter.notifyDataSetChanged();
                } else {
                    model.getmItems().get(0).setSelected(true);
                    mRvListCardTabbedBodyList.setAdapter(getAdapter(model.getmItems().get(0), model));
                    mRvListCardTabbedHeaderList.scrollToPosition(0);
                    headerAdapter.notifyDataSetChanged();
                }
            }

        }


        /**
         *
         * @param mfListItems
         * @return  selected item
         */
        private MFListCardTabbedTemplateModel.MFListItem getSelectedItem(List<MFListCardTabbedTemplateModel.MFListItem> mfListItems) {

            for (MFListCardTabbedTemplateModel.MFListItem mfListItem : mfListItems) {

                if (mfListItem.isSelected())
                    return mfListItem;
            }

            return null;
        }


        /**
         * @param mfListItems
         * @return selected item position
         */
        private int getSelectedItemPosition(List<MFListCardTabbedTemplateModel.MFListItem> mfListItems) {

            for (int i = 0; i < mfListItems.size(); i++) {
                MFListCardTabbedTemplateModel.MFListItem item = mfListItems.get(i);
                if (item.isSelected())
                    return i;

            }

            return -1;
        }

        /**
         * @param item
         * @param model
         * @return Adapter instance
         */
        private ListCardTabbedBodyAdapter getAdapter(MFListCardTabbedTemplateModel.MFListItem item, final MFListCardTabbedTemplateModel model) {

            ListCardTabbedBodyAdapter adapter = new ListCardTabbedBodyAdapter(item.getItems());
            adapter.setmItemClickListener(new ListCardTabbedBodyAdapter.OnClickItemListener() {
                @Override
                public void onClick(String payload, String action) {

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

            return adapter;
        }

    }

}
