package ai.active.uikit.template.tabbed;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;

import java.util.ArrayList;
import java.util.List;

import ai.active.uikit.template.TemplateConstants;

import static ai.active.uikit.template.TemplateConstants.JSON_KEY.KEY_ACTION;
import static ai.active.uikit.template.TemplateConstants.JSON_KEY.KEY_ALLOW_MULTIPLE_CLICKS;
import static ai.active.uikit.template.TemplateConstants.JSON_KEY.KEY_HEADER;
import static ai.active.uikit.template.TemplateConstants.JSON_KEY.KEY_ITEMS;
import static ai.active.uikit.template.TemplateConstants.JSON_KEY.KEY_PAYLOAD;
import static ai.active.uikit.template.TemplateConstants.JSON_KEY.KEY_RADIO;
import static ai.active.uikit.template.TemplateConstants.JSON_KEY.KEY_TITLE;


public class MFListCardTabbedTemplateModel extends TemplateModel {

    private List<MFListItem> mItems;

    private List<MFBodyItems> mfBodyItems;

    public MFListCardTabbedTemplateModel(String templateID) {

        this.templateID = templateID;

    }

    public MFListCardTabbedTemplateModel(MFListCardTabbedTemplateModel copyObject) {

        super(copyObject);
        this.templateID = copyObject.templateID;

        if (copyObject.mItems != null) {
            this.mItems = new ArrayList<>(copyObject.mItems);
        }

        if (copyObject.mfBodyItems != null) {
            this.mfBodyItems = new ArrayList<>(copyObject.mfBodyItems);
        }
    }

    public TemplateModel mfClone() {

        return new MFListCardTabbedTemplateModel(this);
    }

    public List<MFListItem> getmItems() {
        return mItems;
    }

    @Override
    public void deserialize(JsonArray jsonArray) {

        mItems = new ArrayList<>();
        mfBodyItems = new ArrayList<>();
        mItems.clear();
        mfBodyItems.clear();

        JsonObject rootJO = jsonArray.get(0).getAsJsonObject();
        JsonObject cardJO = null;
        JsonObject payloadJO = null;

        // return if payload not has  card object
        if (!rootJO.has(TemplateConstants.JSON_KEY.KEY_CARD)) {
            return;
        }

        // check if payload has card object
        if (rootJO.has(TemplateConstants.JSON_KEY.KEY_CARD)) {
            cardJO = rootJO.get(TemplateConstants.JSON_KEY.KEY_CARD).getAsJsonObject();
        }

        if (rootJO.has(KEY_PAYLOAD)) {
            payloadJO = rootJO.get(KEY_PAYLOAD).getAsJsonObject();
        }

        // payload must not be null
        if (!payloadJO.has(TemplateConstants.JSON_KEY.KEY_TAB_CONTENT))
            return;

        JsonArray TabcontentJA = payloadJO.getAsJsonArray(TemplateConstants.JSON_KEY.KEY_TAB_CONTENT);


        // tab content must not be empty
        if (TabcontentJA.size() < 1)
            return;


        for (int i = 0; i < TabcontentJA.size(); i++) {

            String Header = "";

            List<MFBodyItems> bodyItems = new ArrayList<>();

            JsonObject tabelementContainerJO = TabcontentJA.get(i).getAsJsonObject();

            if (tabelementContainerJO.has(KEY_HEADER)) {

                Header = tabelementContainerJO.get(KEY_HEADER).getAsString();
            }
            if (!tabelementContainerJO.has(KEY_ITEMS))
                continue;

            JsonArray itemsJA = tabelementContainerJO.getAsJsonArray(KEY_ITEMS);

            String title = "";
            String payload = null;
            String action = null;
            boolean allowMultipleClick = true;
            boolean radio = false;

            for (int j = 0; j < itemsJA.size(); j++) {

                JsonObject itemsContainerJO = itemsJA.get(j).getAsJsonObject();
                MFBodyItems mfBodyItem = new MFBodyItems();

                // Get title
                if (itemsContainerJO.has(KEY_TITLE)) {
                    title = itemsContainerJO.get(KEY_TITLE).getAsString();
                }


                // Get payload
                if (itemsContainerJO.has(KEY_PAYLOAD)) {
                    payload = itemsContainerJO.get(KEY_PAYLOAD).getAsString();
                }


                // Get action
                if (itemsContainerJO.has(KEY_ACTION)) {
                    action = itemsContainerJO.get(KEY_ACTION).getAsString();
                }

                // Get allow multiple click
                if (itemsContainerJO.has(KEY_ALLOW_MULTIPLE_CLICKS)) {
                    allowMultipleClick = itemsContainerJO.get(KEY_ALLOW_MULTIPLE_CLICKS).getAsBoolean();
                }

                // Get radio
                if (itemsContainerJO.has(KEY_RADIO)) {
                    radio = itemsContainerJO.get(KEY_RADIO).getAsBoolean();
                }

                mfBodyItem.setAction(action);
                mfBodyItem.setTitle(title);
                mfBodyItem.setAllowMultipleClicks(allowMultipleClick);
                mfBodyItem.setRadio(radio);
                mfBodyItem.setPayload(payload);
                if (j == itemsJA.size() - 1) {
                    mfBodyItem.setLast(true);
                }
                bodyItems.add(mfBodyItem);

            }

            mItems.add(new MFListItem(Header, bodyItems));

        }


    }

    /**
     * Model to represent the list Item
     */

    public static class MFListItem {

        private String Header;
        private List<MFBodyItems> items;
        private boolean isSelected = false;

        public MFListItem(String title, List<MFBodyItems> items) {
            Header = title;
            this.items = items;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public String getHeader() {
            return Header;
        }

        public List<MFBodyItems> getItems() {
            return items;
        }


    }

    /**
     * Model to represent the BodyItems
     */

    public static class MFBodyItems {

        private String title;
        private String payload;
        private String action;
        private Boolean Selected = false;
        private Boolean allowMultipleClicks;
        private Boolean radio;
        private Boolean isLast = false;
        private Boolean isEnabled = true;

        public Boolean isLast() {
            return isLast;
        }

        public void setLast(Boolean last) {
            isLast = last;
        }

        public Boolean isEnabled() {
            return isEnabled;
        }

        public void setEnabled(Boolean enabled) {
            isEnabled = enabled;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPayload() {
            return payload;
        }

        public void setPayload(String payload) {
            this.payload = payload;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public Boolean isSelected() {
            return Selected;
        }

        public void setSelected(Boolean selected) {
            Selected = selected;
        }

        public Boolean isAllowMultipleClicks() {
            return allowMultipleClicks;
        }

        public void setAllowMultipleClicks(Boolean allowMultipleClicks) {
            this.allowMultipleClicks = allowMultipleClicks;
        }

        public Boolean isRadio() {
            return radio;
        }

        public void setRadio(Boolean radio) {
            this.radio = radio;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            MFBodyItems that = (MFBodyItems) o;

            if (title != null ? !title.equals(that.title) : that.title != null) return false;
            if (payload != null ? !payload.equals(that.payload) : that.payload != null)
                return false;
            if (action != null ? !action.equals(that.action) : that.action != null) return false;
            if (Selected != null ? !Selected.equals(that.Selected) : that.Selected != null)
                return false;
            if (allowMultipleClicks != null ? !allowMultipleClicks.equals(that.allowMultipleClicks) : that.allowMultipleClicks != null)
                return false;
            return radio != null ? radio.equals(that.radio) : that.radio == null;
        }

        @Override
        public int hashCode() {
            int result = title != null ? title.hashCode() : 0;
            result = 31 * result + (payload != null ? payload.hashCode() : 0);
            result = 31 * result + (action != null ? action.hashCode() : 0);
            result = 31 * result + (Selected != null ? Selected.hashCode() : 0);
            result = 31 * result + (allowMultipleClicks != null ? allowMultipleClicks.hashCode() : 0);
            result = 31 * result + (radio != null ? radio.hashCode() : 0);
            return result;
        }
    }

}
