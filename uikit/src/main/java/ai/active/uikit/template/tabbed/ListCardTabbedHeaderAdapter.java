package ai.active.uikit.template.tabbed;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ai.active.uikit.R;

public class ListCardTabbedHeaderAdapter extends RecyclerView.Adapter<ListCardTabbedHeaderAdapter.MFTabbedHeaderRecyclerViewHolder> {

    final private List<MFListCardTabbedTemplateModel.MFListItem> itemList;
    public ListCardTabbedHeaderAdapter.OnClickItemListener mItemClickListener;
    private Context mContext;

    public ListCardTabbedHeaderAdapter(List<MFListCardTabbedTemplateModel.MFListItem> mfListItems) {

        itemList = mfListItems;
    }

    /**
     * method to set selected item status
     * @param item
     */
    public void setSelectionItems(MFListCardTabbedTemplateModel.MFListItem item) {

        for (MFListCardTabbedTemplateModel.MFListItem listItem : itemList) {
            listItem.setSelected(false);
        }
        item.setSelected(true);
        notifyDataSetChanged();
    }

    @Override
    public MFTabbedHeaderRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_card_tabbed_header_item, parent, false);
        mContext = view.getContext();
        return new MFTabbedHeaderRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MFTabbedHeaderRecyclerViewHolder holder, int position) {

        final MFListCardTabbedTemplateModel.MFListItem item = itemList.get(position);
        if (item == null)
            return;

        if (!TextUtils.isEmpty(item.getHeader())) {
            holder.tvTitle.setText(item.getHeader());
        }

        if (item.isSelected()) {
            holder.tvTitle.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rounded_button_shape));
            holder.tvTitle.setTextColor(mContext.getResources().getColor(R.color.white));

        } else {
            holder.tvTitle.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.rounded_button_shape_disabled_tabbed));
            holder.tvTitle.setTextColor(mContext.getResources().getColor(R.color.gray));
        }

        holder.ll_RootContainer_header_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mItemClickListener.onClick(item);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setItemClickListener(OnClickItemListener listener) {
        mItemClickListener = listener;
    }

    public List<MFListCardTabbedTemplateModel.MFListItem> getItemList() {
        return itemList;
    }

    interface OnClickItemListener {
        void onClick(MFListCardTabbedTemplateModel.MFListItem item); // TODO pass action and payload associated with item
    }

    static class MFTabbedHeaderRecyclerViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout ll_RootContainer_header_item;
        private TextView tvTitle;

        public MFTabbedHeaderRecyclerViewHolder(View view) {
            super(view);

            ll_RootContainer_header_item = (LinearLayout) view.findViewById(R.id.ll_header_item_container);
            tvTitle = (TextView) view.findViewById(R.id.tv_heading_list_card_tabbed_header_item);

        }
    }

}
