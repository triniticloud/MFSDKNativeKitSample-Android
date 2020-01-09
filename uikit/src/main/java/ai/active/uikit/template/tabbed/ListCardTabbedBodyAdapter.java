package ai.active.uikit.template.tabbed;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ai.active.uikit.R;


public class ListCardTabbedBodyAdapter extends RecyclerView.Adapter<ListCardTabbedBodyAdapter.MFTabbedBodyViewHolder> {

    private List<MFListCardTabbedTemplateModel.MFBodyItems> items;
    private ListCardTabbedBodyAdapter.OnClickItemListener mItemClickListener;

    public ListCardTabbedBodyAdapter(List<MFListCardTabbedTemplateModel.MFBodyItems> items) {

        this.items = items;
    }

    @Override
    public MFTabbedBodyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_card_tabbed_body_item, parent, false);
        return new MFTabbedBodyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MFTabbedBodyViewHolder holder, int position) {

        final MFListCardTabbedTemplateModel.MFBodyItems item = items.get(position);

        // return if item is null
        if (item == null)
            return;

        // set title
        if (!TextUtils.isEmpty(item.getTitle())) {
            holder.tvTitle.setText(item.getTitle());
        }

        //hide divider if it is last item
        if (item.isLast()) {
            holder.divider.setVisibility(View.GONE);
        }

        // change the drawable based on the selection property
        if (item.isSelected()) {
            holder.ivStatusImage.setImageDrawable(getDrawable(holder, R.drawable.ic_check_selected));
            holder.tvTitle.setTypeface(holder.tvTitle.getTypeface(), Typeface.BOLD);

        } else {
            holder.ivStatusImage.setImageDrawable(getDrawable(holder, R.drawable.ic_check_unselected));
            holder.tvTitle.setTypeface(holder.tvTitle.getTypeface(), Typeface.NORMAL);
        }


        if (!item.isEnabled()) {
            holder.llBodyContainer.setEnabled(false);
        } else {
            holder.llBodyContainer.setEnabled(true);
        }


        holder.llBodyContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(item.getPayload())
                        || TextUtils.isEmpty(item.getAction())) {
                    return;
                }

                if (!item.isEnabled())
                    return;

//                if (!item.isAllowMultipleClicks())
//                    item.setEnabled(false);
//
//                if (item.isRadio()) {
//                    disableOtherButtons(items, item);
//                }

                if (mItemClickListener != null) {

                    mItemClickListener.onClick(item.getPayload(), item.getAction());
                    setSelectionItem(item);
                }


            }
        });

    }

    /**
     * Method to set selection item status
     * @param item
     */
    private void setSelectionItem(MFListCardTabbedTemplateModel.MFBodyItems item) {

        for (MFListCardTabbedTemplateModel.MFBodyItems bodyItem : items) {
            bodyItem.setSelected(false);
        }
        item.setSelected(true);
        notifyDataSetChanged();
    }


    private void disableOtherButtons(List<MFListCardTabbedTemplateModel.MFBodyItems> items, MFListCardTabbedTemplateModel.MFBodyItems item) {

        for (MFListCardTabbedTemplateModel.MFBodyItems bodyItem : items) {
            if (!bodyItem.equals(item))
                bodyItem.setEnabled(false);
        }

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setmItemClickListener(OnClickItemListener itemClickListener) {

        mItemClickListener = itemClickListener;

    }

    /**
     * Method to get drawable
     */
    private Drawable getDrawable(ListCardTabbedBodyAdapter.MFTabbedBodyViewHolder holder, int ic_check_selected) {
        return ContextCompat.getDrawable(holder.context, ic_check_selected);
    }

    interface OnClickItemListener {
        void onClick(String payload, String action); // TODO pass action and payload associated with item
    }

    static class MFTabbedBodyViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivStatusImage;
        private TextView tvTitle;
        private LinearLayout llBodyContainer;
        private Context context;
        private View divider;

        public MFTabbedBodyViewHolder(View view) {
            super(view);
            context = view.getContext();
            ivStatusImage = (ImageView) view.findViewById(R.id.iv_check_list_card_tabbed_body_item);
            tvTitle = (TextView) view.findViewById(R.id.tv_text_list_card_tabbed_body_item);
            llBodyContainer = (LinearLayout) view.findViewById(R.id.ll_body_container_list_card_tabbed_item);
            divider = (View) view.findViewById(R.id.view_divider);
        }
    }

}
