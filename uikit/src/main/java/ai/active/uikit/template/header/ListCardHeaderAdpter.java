package ai.active.uikit.template.header;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.morfeus.android.mfsdk.mfmedia.MFMediaSdk;
import com.morfeus.android.mfsdk.mfmedia.download.MfImageLoader;
import com.morfeus.android.mfsdk.ui.widget.bubble.view.template.MfNetworkImageView;

import java.util.List;

import ai.active.uikit.R;

public class ListCardHeaderAdpter extends RecyclerView.Adapter<ListCardHeaderAdpter.ListCardHeaderRecyclerviewHolder> {

    private OnClickItemListener mItemClickListener;
    private List<MFListCardHeaderTemplateModel.MFListItem> itemList;
    private String mBotID;


    public ListCardHeaderAdpter(List<MFListCardHeaderTemplateModel.MFListItem> itemList, String botId) {
        this.itemList = itemList;
        this.mBotID = botId;
    }


    @Override
    public ListCardHeaderRecyclerviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_card_header_item, parent, false);

        return new ListCardHeaderRecyclerviewHolder(view);

    }

    @Override
    public void onBindViewHolder(ListCardHeaderRecyclerviewHolder holder, int position) {

        MFListCardHeaderTemplateModel.MFListItem model = itemList.get(position);
        if (model == null)
            return;

        MFListCardHeaderTemplateModel.Image image = model.getHeaderImage();

        //set image
        if (model.getHeaderImage() != null) {

            holder.mHeaderImageView.setBotId(mBotID);
            final String[] imageAddress = {"header" + position, image.getImageUrl()};
            MfImageLoader imageLoader = MFMediaSdk.getInstance().getImageLoader();
            holder.mHeaderImageView.setImageUrl(imageAddress, imageLoader);

        }

        // set child items
        if (model.getInternalListItem() != null && model.getInternalListItem().size() > 0) {
            ListCardChildAdapter listCardChildAdapter = new ListCardChildAdapter(model.getInternalListItem());
            holder.mRvChildRecyclerView.setAdapter(listCardChildAdapter);
            listCardChildAdapter.setItemClickListener(mItemClickListener);
        }
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setItemClickListener(OnClickItemListener listener) {
        mItemClickListener = listener;
    }

    public class ListCardHeaderRecyclerviewHolder extends RecyclerView.ViewHolder {

        private RecyclerView mRvChildRecyclerView;
        private MfNetworkImageView mHeaderImageView;

        public ListCardHeaderRecyclerviewHolder(View view) {
            super(view);
            mRvChildRecyclerView = (RecyclerView) view.findViewById(R.id.rv_list_childview_header_list_card);
            mRvChildRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRvChildRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
            mHeaderImageView = (MfNetworkImageView) view.findViewById(R.id.iv_header_image_header_list_card);
            mHeaderImageView.setDefaultImageResId(com.morfeus.android.R.drawable.mf_image_default);
            mHeaderImageView.setErrorImageResId(com.morfeus.android.R.drawable.mf_image_error);
            DividerItemDecoration dividerItemDecoration
                    = new DividerItemDecoration(
                    ContextCompat.getDrawable(view.getContext(), R.drawable.line_divider_shape)
            );
            mRvChildRecyclerView.addItemDecoration(dividerItemDecoration);
        }

    }

}
