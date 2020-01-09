package ai.active.uikit.template.textcard;

import android.support.annotation.NonNull;

import com.google.gson.JsonArray;
import com.morfeus.android.mfsdk.ui.widget.bubble.model.TemplateModel;

public final class MFTextCardTemplateModel extends TemplateModel {

    public MFTextCardTemplateModel(@NonNull String templateId) {
        this.templateID = templateId;
        setTextTemplate(true);
    }

    public MFTextCardTemplateModel(MFTextCardTemplateModel copyObject) {

        super(copyObject);
        this.templateID = copyObject.templateID;
    }

    public TemplateModel mfClone() {

        return new MFTextCardTemplateModel(this);
    }

    @Override
    public void deserialize(JsonArray jsonArray) {

    }


}
