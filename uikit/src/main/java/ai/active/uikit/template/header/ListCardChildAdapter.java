package ai.active.uikit.template.header;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Stack;

import ai.active.uikit.R;

import static android.view.View.GONE;

public class ListCardChildAdapter extends RecyclerView.Adapter<ListCardChildAdapter.ListCardChildViewHolder> {

    private List<MFListCardHeaderTemplateModel.MFInternalListItem> internalListItem;
    private OnClickItemListener onItemClickListener;

    ListCardChildAdapter(List<MFListCardHeaderTemplateModel.MFInternalListItem> internalListItem) {
        this.internalListItem = internalListItem;
    }

    @Override
    public ListCardChildViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_card_child_item, parent, false);
        return new ListCardChildViewHolder(view);
    }

    public void setItemClickListener(OnClickItemListener listener) {
        onItemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(ListCardChildViewHolder holder, int position) {

        MFListCardHeaderTemplateModel.MFInternalListItem model = internalListItem.get(position);

        // Set title
        if (TextUtils.isEmpty(model.getTitle())) {
            holder.tvTitle.setVisibility(GONE);
        } else {
            holder.tvTitle.setText(model.getTitle());
        }

        // Set sub title
        if (TextUtils.isEmpty(model.getSubTitle())) {
            holder.tvSubTitle.setVisibility(GONE);
        } else {
            holder.tvSubTitle.setText(model.getSubTitle());
        }

        // Set buttons
        if (!shouldShowButtons(model)) {

            hideButtons(holder);
        } else {
            // Item containing itemButtons
            final List<MFListCardHeaderTemplateModel.Button> buttonModels = model.getButtons();

            final Stack<Button> holderButtons = new Stack<>();

            holderButtons.add(holder.btnFirst);
            holderButtons.add(holder.btnSecond);
            holderButtons.add(holder.btnThird);

            for (int i = 0; i < buttonModels.size(); i++) {

                if (holderButtons.isEmpty())
                    continue;

                final Button btn = holderButtons.pop();

                final MFListCardHeaderTemplateModel.Button btnItem = buttonModels.get(i);

                btn.setText(btnItem.getText());

                if (!btnItem.isEnabled()) {
                    btn.setEnabled(false);
                } else {
                    btn.setEnabled(true);
                }

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (TextUtils.isEmpty(btnItem.getPayload())
                                || TextUtils.isEmpty(btnItem.getAction())) {
                            return;
                        }

                        if (!btnItem.isEnabled())
                            return;

                        if (!btnItem.isAllowMultipleClicks())
                            btnItem.setEnabled(false);

                        if (btnItem.isRadio()) {
                            disableOtherButtons(buttonModels, btnItem);
                        }
                        if (onItemClickListener != null)
                            onItemClickListener.onClick(btnItem.getPayload(), btnItem.getAction());

                    }
                });

            }

            // Hide extra buttons
            for (Button btn : holderButtons) {
                btn.setVisibility(GONE);
            }

        }

    }

    /**
     *  Method to disable other buttons based on allow multiple click property
     */
    private void disableOtherButtons(List<MFListCardHeaderTemplateModel.Button> buttons,
                                     MFListCardHeaderTemplateModel.Button skipBtn) {
        for (MFListCardHeaderTemplateModel.Button btn : buttons) {
            if (!btn.equals(skipBtn))
                btn.setEnabled(false);
        }
    }

    /**
     * Method to hide buttons layout
     **/
    private void hideButtons(ListCardChildViewHolder holder) {
        holder.llBtnContainer.setVisibility(GONE);
    }

    private boolean shouldShowButtons(MFListCardHeaderTemplateModel.MFInternalListItem item) {
        return !(item.getButtons() == null || item.getButtons().size() < 1);
    }

    @Override
    public int getItemCount() {
        return internalListItem.size();
    }

    public static class ListCardChildViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;

        private TextView tvSubTitle;

        private LinearLayout llBtnContainer;

        private Button btnFirst;

        private Button btnSecond;

        private Button btnThird;

        private View dividerview;


        public ListCardChildViewHolder(View view) {
            super(view);

            tvTitle = (TextView) view.findViewById(R.id.tv_title_list_card_child_item);
            tvSubTitle = (TextView) view.findViewById(R.id.tv_sub_title_list_card_child_item);
            llBtnContainer = (LinearLayout) view.findViewById(R.id.ll_btn_container_list_card_child_item);
            btnFirst = (Button) view.findViewById(R.id.btn_first_list_card_child_item);
            btnSecond = (Button) view.findViewById(R.id.btn_second_list_card_child_item);
            btnThird = (Button) view.findViewById(R.id.btn_third_list_card_child_item);


        }
    }

}
