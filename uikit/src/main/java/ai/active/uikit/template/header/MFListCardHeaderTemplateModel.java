package ai.active.uikit.template.header;

import android.text.TextUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;

import java.util.ArrayList;
import java.util.List;

import ai.active.uikit.template.TemplateConstants;

public class MFListCardHeaderTemplateModel extends TemplateModel {

    private List<MFListItem> itemList;

    public MFListCardHeaderTemplateModel(String templateID) {
        this.templateID = templateID;
    }

    public MFListCardHeaderTemplateModel(MFListCardHeaderTemplateModel copyObject) {

        super(copyObject);
        this.templateID = copyObject.templateID;

        if (copyObject.itemList != null) {
            itemList = new ArrayList<>(copyObject.itemList);
        }
    }

    public TemplateModel mfClone() {

        return new MFListCardHeaderTemplateModel(this);
    }

    public List<MFListItem> getItemList() {
        return itemList;
    }

    @Override
    public void deserialize(JsonArray jsonArray) {
        itemList = new ArrayList<>();
        if (jsonArray == null || jsonArray.size() < 1)
            return;

        JsonObject rootJO = jsonArray.get(0).getAsJsonObject();
        JsonObject payloadJO = null;

        // get payload object
        if (rootJO.has(TemplateConstants.JSON_KEY.KEY_PAYLOAD)) {
            payloadJO = rootJO.get(TemplateConstants.JSON_KEY.KEY_PAYLOAD).getAsJsonObject();
        }
        // check payload object;
        if (!rootJO.has(TemplateConstants.JSON_KEY.KEY_PAYLOAD)) {
            return;
        }

        // return if payload not has key items
        if (!payloadJO.has(TemplateConstants.JSON_KEY.KEY_ITEMS))
            return;

        JsonArray itemsJA = payloadJO.getAsJsonArray(TemplateConstants.JSON_KEY.KEY_ITEMS);

        if (itemsJA.size() < 1)
            return;

        for (int i = 0; i < itemsJA.size(); i++) {

            JsonObject itemJO = itemsJA.get(i).getAsJsonObject();
            String ImageUrl = "";

            if (itemJO.has(TemplateConstants.JSON_KEY.KEY_IMAGE)) {

                JsonObject ImageJO = itemJO.get(TemplateConstants.JSON_KEY.KEY_IMAGE).getAsJsonObject();

                if (ImageJO.has(TemplateConstants.JSON_KEY.KEY_IMAGE_URL)) {
                    ImageUrl = ImageJO.get(TemplateConstants.JSON_KEY.KEY_IMAGE_URL).getAsString();
                }
            }

            List<MFInternalListItem> mfInternalListItems = new ArrayList<>();


            if (itemJO.has(TemplateConstants.JSON_KEY.KEY_SUB_CONTENT)) {


                JsonArray SubcontentJA = itemJO.get(TemplateConstants.JSON_KEY.KEY_SUB_CONTENT).getAsJsonArray();
                if (SubcontentJA.size() < 1) {
                    continue;
                }
                for (int j = 0; j < SubcontentJA.size(); j++) {

                    JsonObject SubContentContainerObject = SubcontentJA.get(j).getAsJsonObject();

                    if (!SubContentContainerObject.has(TemplateConstants.JSON_KEY.KEY_ELEMENT))
                        continue;

                    JsonObject elementJO
                            = SubContentContainerObject.getAsJsonObject(TemplateConstants.JSON_KEY.KEY_ELEMENT);


                    String title = "";
                    String subTitle = "";

                    // Get title
                    if (elementJO.has(TemplateConstants.JSON_KEY.KEY_TITLE)) {
                        JsonObject titleJO = elementJO.get(TemplateConstants.JSON_KEY.KEY_TITLE).getAsJsonObject();

                        if (titleJO.has(TemplateConstants.JSON_KEY.KEY_TEXT)) {
                            title = titleJO.get(TemplateConstants.JSON_KEY.KEY_TEXT).getAsString();
                        }
                    }

                    // Get subtitle
                    if (elementJO.has(TemplateConstants.JSON_KEY.KEY_SUB_tITLE)) {
                        JsonObject subTitleJO = elementJO.get(TemplateConstants.JSON_KEY.KEY_SUB_tITLE).getAsJsonObject();

                        if (subTitleJO.has(TemplateConstants.JSON_KEY.KEY_TEXT)) {
                            subTitle = subTitleJO.get(TemplateConstants.JSON_KEY.KEY_TEXT).getAsString();
                        }
                    }

                    List<Button> buttons = new ArrayList<>();

                    if (elementJO.has(TemplateConstants.JSON_KEY.KEY_BUTTONS)) {
                        JsonArray buttonsJA = elementJO.getAsJsonArray(TemplateConstants.JSON_KEY.KEY_BUTTONS);
                        if (buttonsJA.size() > 0) {

                            for (int k = 0; k < buttonsJA.size(); k++) {

                                JsonObject buttonJO = buttonsJA.get(k).getAsJsonObject();

                                String btnpayload = null;
                                String btnaction = null;
                                String btntext = "";
                                boolean btnallowMultipleClick = true;
                                boolean btnradio = false;
                                Button button = new Button();

                                if (buttonJO.has(TemplateConstants.JSON_KEY.KEY_PAYLOAD)) {
                                    btnpayload = buttonJO.get(TemplateConstants.JSON_KEY.KEY_PAYLOAD).getAsString();
                                }

                                // Get action
                                if (buttonJO.has(TemplateConstants.JSON_KEY.KEY_ACTION)) {
                                    btnaction = buttonJO.get(TemplateConstants.JSON_KEY.KEY_ACTION).getAsString();
                                }

                                // Get allowmultiple click
                                if (buttonJO.has(TemplateConstants.JSON_KEY.KEY_ALLOW_MULTIPLE_CLICKS)) {
                                    btnallowMultipleClick
                                            = buttonJO.get(TemplateConstants.JSON_KEY.KEY_ALLOW_MULTIPLE_CLICKS).getAsBoolean();
                                }
                                // Get radio
                                if (buttonJO.has(TemplateConstants.JSON_KEY.KEY_RADIO)) {
                                    btnradio = buttonJO.get(TemplateConstants.JSON_KEY.KEY_RADIO).getAsBoolean();
                                }

                                //Get Text
                                if (buttonJO.has(TemplateConstants.JSON_KEY.KEY_TEXT)) {
                                    btntext = buttonJO.get(TemplateConstants.JSON_KEY.KEY_TEXT).getAsString();
                                }

                                button.setText(btntext);
                                button.setAllowMultipleClicks(btnallowMultipleClick);
                                button.setAction(btnaction);
                                button.setRadio(btnradio);
                                button.setPayload(btnpayload);
                                buttons.add(button);
                            }
                        }

                    }

                    MFInternalListItem mfInternalListItem = new MFInternalListItem(title, subTitle);
                    mfInternalListItem.setButtons(buttons);
                    mfInternalListItems.add(mfInternalListItem);

                }
            }

            MFListItem listItem = new MFListItem();
            listItem.setHeaderImage(new Image(ImageUrl));
            listItem.setInternalListItem(mfInternalListItems);
            itemList.add(listItem);

        }

    }

    /**
     * @return true if list have items
     */
    public boolean haveItems() {

        if (itemList.size() > 0)
            return true;

        return false;
    }

    public static class MFListItem {

        private Image headerImage;
        private List<MFInternalListItem> internalListItem;

        public Image getHeaderImage() {
            return headerImage;
        }

        public void setHeaderImage(Image headerImage) {
            this.headerImage = headerImage;
        }

        public List<MFInternalListItem> getInternalListItem() {
            return internalListItem;
        }

        public void setInternalListItem(List<MFInternalListItem> internalListItem) {
            this.internalListItem = internalListItem;
        }
    }

    public static class MFInternalListItem {

        private String title;
        private String SubTitle;
        private List<Button> buttons;
        private boolean isLast;

        public MFInternalListItem(String title, String subTitle) {
            this.title = title;
            SubTitle = subTitle;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubTitle() {
            return SubTitle;
        }

        public void setSubTitle(String subTitle) {
            SubTitle = subTitle;
        }

        public boolean isLast() {
            return isLast;
        }

        public void setLast(boolean last) {
            isLast = last;
        }

        public List<Button> getButtons() {
            return buttons;
        }

        public void setButtons(List<Button> buttons) {
            this.buttons = buttons;
        }
    }

    /**
     * This model represents the button data inside info card
     */
    public class Button {

        @SerializedName("text")
        @Expose
        private String text;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("payload")
        @Expose
        private String payload;
        @SerializedName("action")
        @Expose
        private String action;
        @SerializedName("allowMultipleClicks")
        @Expose
        private Boolean allowMultipleClicks;
        @SerializedName("radio")
        @Expose
        private Boolean radio;

        private boolean isEnabled = true;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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


        public void setAllowMultipleClicks(Boolean allowMultipleClicks) {
            this.allowMultipleClicks = allowMultipleClicks;
        }

        public Boolean isAllowMultipleClicks() {
            return allowMultipleClicks;
        }

        public Boolean isRadio() {
            return radio;
        }

        public void setRadio(Boolean radio) {
            this.radio = radio;
        }

        public boolean isEnabled() {
            return isEnabled;
        }

        public void setEnabled(boolean enabled) {
            isEnabled = enabled;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Button button = (Button) o;

            if (isEnabled != button.isEnabled) return false;
            if (text != null ? !text.equals(button.text) : button.text != null) return false;
            if (type != null ? !type.equals(button.type) : button.type != null) return false;
            if (payload != null ? !payload.equals(button.payload) : button.payload != null)
                return false;
            if (action != null ? !action.equals(button.action) : button.action != null)
                return false;
            if (allowMultipleClicks != null ? !allowMultipleClicks.equals(button.allowMultipleClicks) : button.allowMultipleClicks != null)
                return false;
            return radio != null ? radio.equals(button.radio) : button.radio == null;
        }

        @Override
        public int hashCode() {
            int result = text != null ? text.hashCode() : 0;
            result = 31 * result + (type != null ? type.hashCode() : 0);
            result = 31 * result + (payload != null ? payload.hashCode() : 0);
            result = 31 * result + (action != null ? action.hashCode() : 0);
            result = 31 * result + (allowMultipleClicks != null ? allowMultipleClicks.hashCode() : 0);
            result = 31 * result + (radio != null ? radio.hashCode() : 0);
            result = 31 * result + (isEnabled ? 1 : 0);
            return result;
        }
    }


    /**
     * This model represents image inside Info card
     */
    public class Image {

        private String imageUrl;

        public Image(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public boolean isValid() {
            return !TextUtils.isEmpty(imageUrl);
        }
    }


}
